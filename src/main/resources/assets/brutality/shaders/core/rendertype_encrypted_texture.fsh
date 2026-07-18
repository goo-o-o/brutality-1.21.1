#version 150

#define GRID_INTENSITY   0.15
#define GRID_SCALE       0.1

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float GameTime;
uniform float CellSize;

in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main()
{
    // check underlying alpha to discard empty transparent texture pixels
    vec4 texSample = texture(Sampler0, texCoord0);
    if (texSample.a < 0.01) {
        discard;
    }

    // grab the current sheet texture size (e.g., 64x64 or 128x128)
    vec2 currentTexSize = vec2(textureSize(Sampler0, 0));

    // convert normalized UV coordinates into pixel coordinates relative to the entity skin
    vec2 entityPixelPos = texCoord0 * currentTexSize;

    // apply scaling and time offsets to the local surface uv position
    vec2 uv = entityPixelPos * GRID_SCALE;
    uv += GameTime * 24000.0 * 0.1;

    // cell space tiling transformations local to the entity surface
    vec2 cellCoord = fract(uv / CellSize) * CellSize;

    // distance field bounds mapping
    vec2 distToLine = min(cellCoord, CellSize - cellCoord);
    float distanceToClosestEdge = min(distToLine.x, distToLine.y);

    // bright interior wire
    float coreLine = smoothstep(0.4, 0.0, distanceToClosestEdge);

    // soft wide visual light bleed
    float glowMask = smoothstep(2.5, 0.0, distanceToClosestEdge) * 0.55;

    // merge elements and amplify core power
    float finalGlowProfile = max(coreLine * 2.5, glowMask);

    // factor in the engine modulated colors and vertex profiles
    vec3 col = vertexColor.rgb * finalGlowProfile * ColorModulator.rgb;
    float finalAlpha = vertexColor.a * clamp(finalGlowProfile, 0.0, 1.0) * ColorModulator.a;

    fragColor = vec4(col, finalAlpha);
}