// https://www.shadertoy.com/view/33KyzW

#version 150

uniform int Amount;
uniform float GameTime;

in vec4 vertexColor;
in vec2 texCoord0;
out vec4 fragColor;

#define TIME mod(GameTime * 1200.0, 1000.0)

float hash21(vec2 p) {
    return fract(sin(dot(p, vec2(127.1, 311.7))) * 43758.5453123);
}

float noise2d(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    vec2 u = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash21(i + vec2(0.0, 0.0)), hash21(i + vec2(1.0, 0.0)), u.x),
            mix(hash21(i + vec2(0.0, 1.0)), hash21(i + vec2(1.0, 1.0)), u.x), u.y);
}

float fbm(vec2 p) {
    float v = 0.0;
    float a = 0.5;
    vec2 shift = vec2(100.0);
    mat2 rot = mat2(cos(0.5), sin(0.5), -sin(0.5), cos(0.5));
    for (int i = 0; i < 3; ++i) {
        v += a * noise2d(p);
        p = rot * p * 2.0 + shift;
        a *= 0.5;
    }
    return v;
}

float rand21(vec2 p){
    float d = sin(dot(p, p + 31.546));
    d = sin(p.x + d * 112.4345) * sin(p.y + d * 782.123) * 589.32;
    return fract(d);
}

mat2 rot2d(float a){
    float c = cos(a), s = sin(a);
    return mat2(c, -s, s, c);
}

float embers(vec2 uv, vec2 noi, float tiling, vec2 aspect, float angle, float time, int amount){
    vec2 centered = uv - 0.5;
    centered = centered * rot2d(angle);
    vec2 rotatedUv = centered + 0.5;

    vec2 tuv = (rotatedUv + noi) * tiling / aspect;
    tuv.y -= sin(time) * 0.2 + time;

    vec2 gv = fract(tuv);
    vec2 id = floor(tuv);

    float totalD = 0.0;

    // Loop to draw 3 distinct embers inside this single cell
    for (int i = 0; i < amount; i++) {
        vec2 cgv = gv - 0.5;

        // Generate custom variation offsets for each loop index iteration
        float seedModifier = float(i) * 45.32;
        float randomSpeedX = rand21(id + vec2(seedModifier, 0.0));
        float randomSpeedY = rand21(id + vec2(0.0, seedModifier));

        cgv.x += sin(TIME * randomSpeedX) * 0.4;
        cgv.y += cos(TIME * randomSpeedY) * 0.4;

        float d = smoothstep(0.002, 0.001, dot(cgv, cgv));
        totalD = max(totalD, d);
    }

    return totalD;
}

void main()
{
    vec2 uv = texCoord0;
    uv *= 0.5;
    float noiScale = 0.3;
    float noiStrength = 0.05;
    vec2 noiCoord = uv * noiScale + TIME * 0.05;
    vec2 noi = vec2(noise2d(noiCoord), noise2d(noiCoord + vec2(5.2, 1.3))) * noiStrength;

    // Layered Embers
    float d = embers(uv, noi.xy, 40.0, vec2(1.0, 8.0), 0.0, TIME * 2.0, Amount);
    d += embers(uv, noi.xy * 1.5, 30.0, vec2(1.0, 5.0), 0.05, TIME * 2.5, Amount);
    d += embers(uv, noi.xy * 3.0, 50.0, vec2(1.0, 6.0), -0.15, TIME * 4.1, Amount);
    d += embers(uv, noi.xy * 2.0, 60.0, vec2(1.0, 7.0), 0.15, TIME * 4.1, Amount);

    // Procedural Fade Map
    float fade = fbm(uv * 3.0);
    fade *= smoothstep(0.8, 0.0, uv.y);

    // Background Glow
    vec3 bg = smoothstep(.5 + (sin(TIME * 20.0) + 1.) * .02, 0.0, uv.y) * vertexColor.rgb * (0.15 + (sin(TIME * 35.) + 1.) * 0.015);

    vec3 col = vec3(d * fade * 2.0) * vertexColor.rgb + bg;

    fragColor = vec4(col, vertexColor.a);
}