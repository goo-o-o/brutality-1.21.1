#version 150

uniform int Amount;
uniform float GameTime;
uniform float Speed;

in vec4 vertexColor;
in vec2 texCoord0;
out vec4 fragColor;

#define TIME mod(GameTime * 1200.0, 1000.0)


vec2 mod289(vec2 x) {
    return x - floor(x * (1.0 / 289.0)) * 289.0;
}

vec3 mod289(vec3 x) {
    return x - floor(x * (1.0 / 289.0)) * 289.0;
}

vec4 mod289(vec4 x) {
    return x - floor(x * (1.0 / 289.0)) * 289.0;
}

vec3 permute(vec3 x) {
    return mod289(((x * 34.0) + 1.0) * x);
}

vec4 permute(vec4 x) {
    return mod((34.0 * x + 1.0) * x, 289.0);
}

vec4 taylorInvSqrt(vec4 r) {
    return 1.79284291400159 - 0.85373472095314 * r;
}

float snoise(vec2 v) {
    const vec4 C = vec4(0.211324865405187, 0.366025403784439, -0.577350269189626, 0.024390243902439);
    vec2 i = floor(v + dot(v, C.yy));
    vec2 x0 = v - i + dot(i, C.xx);

    vec2 i1 = (x0.x > x0.y) ? vec2(1.0, 0.0) : vec2(0.0, 1.0);
    vec4 x12 = x0.xyxy + C.xxzz;
    x12.xy -= i1;

    i = mod289(i);
    vec3 p = permute(permute(i.y + vec3(0.0, i1.y, 1.0))
    + i.x + vec3(0.0, i1.x, 1.0));

    vec3 m = max(0.5 - vec3(dot(x0, x0), dot(x12.xy, x12.xy), dot(x12.zw, x12.zw)), 0.0);
    m = m * m;
    m = m * m;

    vec3 x = 2.0 * fract(p * C.www) - 1.0;
    vec3 h = abs(x) - 0.5;
    vec3 ox = floor(x + 0.5);
    vec3 a0 = x - ox;

    m *= 1.79284291400159 - 0.85373472095314 * (a0 * a0 + h * h);

    vec3 g;
    g.x = a0.x * x0.x + h.x * x0.y;
    g.yz = a0.yz * x12.xz + h.yz * x12.yw;

    return 130.0 * dot(m, g);
}

float cellular2x2(vec2 P) {
    #define K 0.142857142857
    #define K2 0.0714285714285
    #define jitter 0.8

    vec2 Pi = mod(floor(P), 289.0);
    vec2 Pf = fract(P);
    vec4 Pfx = Pf.x + vec4(-0.5, -1.5, -0.5, -1.5);
    vec4 Pfy = Pf.y + vec4(-0.5, -0.5, -1.5, -1.5);
    vec4 p = permute(Pi.x + vec4(0.0, 1.0, 0.0, 1.0));
    p = permute(p + Pi.y + vec4(0.0, 0.0, 1.0, 1.0));
    vec4 ox = mod(p, 7.0) * K + K2;
    vec4 oy = mod(floor(p * K), 7.0) * K + K2;
    vec4 dx = Pfx + jitter * ox;
    vec4 dy = Pfy + jitter * oy;
    vec4 d = dx * dx + dy * dy;

    d.xy = min(d.xy, d.zw);
    d.x = min(d.x, d.y);
    return d.x;
}

float fbm(vec2 p) {
    float f = 0.0;
    float w = 0.5;
    for (int i = 0; i < 5; i++) {
        f += w * snoise(p);
        p *= 2.0;
        w *= 0.5;
    }
    return f;
}


void main() {
    vec2 uv = texCoord0;
    uv *= 0.5;

    // generate dynamic noise fog with higher base brightness and variance
    // animate noise sampling over time so the bright fog pockets slowly drift
    float noise = fbm(uv * 1.2 + vec2(iTime * 0.05, -iTime * 0.03));

    // map noise range to create bright patches (0.65 to 1.15)
    float bgFog = 0.7 + (noise * 0.3);

    vec2 GA;
    GA.x -= iTime * 1.8;
    GA.y += iTime * 0.9;
    GA *= Speed;

    // snow layers
    float F1 = 1.0 - cellular2x2((uv + (GA * 0.1)) * 8.0);
    float N1 = smoothstep(0.998, 1.0, F1) * 1.0;

    float F2 = 1.0 - cellular2x2((uv + (GA * 0.2)) * 6.0);
    float N2 = smoothstep(0.995, 1.0, F2) * 0.85;

    float F3 = 1.0 - cellular2x2((uv + (GA * 0.4)) * 4.0);
    float N3 = smoothstep(0.99, 1.0, F3) * 0.65;

    float F4 = 1.0 - cellular2x2((uv + (GA * 0.6)) * 3.0);
    float N4 = smoothstep(0.98, 1.0, F4) * 0.4;

    float F5 = 1.0 - cellular2x2((uv + (GA)) * 1.2);
    float N5 = smoothstep(0.98, 1.0, F5) * 0.25;

    float snow = N1 + N2 + N3 + N4 + N5;

    // combine brighter foggy base with snow layers
    float Snowout = bgFog + snow;

    fragColor = vec4(Snowout * 0.95, Snowout, Snowout * 1.08, 1.0);}