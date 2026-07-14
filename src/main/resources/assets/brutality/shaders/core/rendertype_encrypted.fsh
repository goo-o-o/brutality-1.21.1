#version 150

#define GRID_INTENSITY   0.15
#define GRID_SCALE       0.1

uniform float GameTime;
uniform float CellSize;

in vec4 vertexColor;

out vec4 fragColor;


void main()
{
    // screen resolution reconstruction
    float screenWidth = 1.0 / length(dFdx(gl_FragCoord.xy));
    vec2 resolution = vec2(screenWidth, 1.0 / length(dFdy(gl_FragCoord.xy)));

    // normalized aspect-corrected screen uv
    vec2 screenUV = gl_FragCoord.xy / resolution.xy;

    // grid uv scaling
    vec2 uv = (gl_FragCoord.xy / resolution.y) * GRID_SCALE;
    uv += GameTime * 24000.0 * 0.1;

    // smooth mathematical vignette mask
    float vignette = screenUV.x * screenUV.y * (1.0 - screenUV.x) * (1.0 - screenUV.y);
    vignette = clamp(16.0 * vignette, 0.0, 1.0);
    vignette = pow(vignette, 0.5);

    // cell space tiling transformations
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

    // apply emissive profiles to color
    vec3 col = vertexColor.rgb * finalGlowProfile;

    // compress outer edges via vignette shadow
    float vignetteDarkness = 0.0;
    col *= mix(vignetteDarkness, 1.0, vignette);

    // final engine alpha composition
    fragColor = vec4(col, vertexColor.a);
}