#version 150

#define GRID_SCALE       1.0
uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float GameTime;
uniform float CellSize;
uniform int EntityID;

in vec4 vertexColor;
in vec2 texCoord0;
#define TIME GameTime * 1000.0
out vec4 fragColor;

float hash14(vec4 p4) {
    p4 = fract(p4 * vec4(.1031, .11369, .13787, .09987));
    p4 += dot(p4, p4.wzxy + 33.33);
    return fract((p4.x + p4.y) * (p4.z + p4.w));
}

void main()
{
    vec4 texSample = texture(Sampler0, texCoord0);
    if (texSample.a < 0.01) {
        discard;
    }

    float entityRandomSeed = hash14(vec4(vertexColor.rgb, EntityID));

    // grab the current sheet texture size (e.g., 64x64 or 128x128)
    vec2 currentTexSize = vec2(textureSize(Sampler0, 0));

    // --- ENFORCE STRICT PIXEL PARITY ---
    // 1. Convert the smooth game time into a discrete pixel movement step

    // 2. Map coordinates relative to raw entity skin pixels
    vec2 entityPixelPos = texCoord0 * currentTexSize;

    // 3. Combine base position, entity offset, and stepping animation in raw pixel space
    vec2 totalPixelCoords = (entityPixelPos * GRID_SCALE)
    + vec2(entityRandomSeed * 75.0, entityRandomSeed * 140.0)
    + vec2(TIME, 0.0); // pans horizontally along texel steps

    // 4. Floor the entire coordinate space so the grid can never render between skin pixels
    vec2 uv = floor(totalPixelCoords);

    // cell space tiling transformations local to the pixelated entity surface
    vec2 cellCoord = fract(uv / CellSize) * CellSize;

    // distance field bounds mapping
    vec2 distToLine = min(cellCoord, CellSize - cellCoord);
    float distanceToClosestEdge = min(distToLine.x, distToLine.y);

    // bright interior wire
    float coreLine = smoothstep(0.4, 0.0, distanceToClosestEdge);

    // soft wide visual light bleed
    float glowMask = smoothstep(2.5, 0.0, distanceToClosestEdge) * 0.85;

    // merge elements and amplify core power
    float finalGlowProfile = max(coreLine * 2.5, glowMask);

    // factor in the engine modulated colors and vertex profiles
    vec3 col = vertexColor.rgb * finalGlowProfile * ColorModulator.rgb;
    float finalAlpha = vertexColor.a * clamp(finalGlowProfile, 0.0, 1.0) * ColorModulator.a;

    fragColor = vec4(col, finalAlpha);
}