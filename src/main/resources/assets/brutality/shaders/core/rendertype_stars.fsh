#version 150

#define BG vec3(0.0)
#define NEBULA_A vec3(17.0 / 255.0, 0.0, 81.0 / 255.0)
#define NEBULA_B vec3(81.0 / 255.0, 0.0, 188.0 / 255.0)
#define STAR_CORE vec3(1.0, 0.92, 1.0)
#define STAR_BLOOM vec3(200.0 / 255.0, 136.0 / 255.0, 231.0 / 255.0)
#define TIME GameTime * 1600.0

uniform float GameTime;
uniform float StarChance;
uniform float StarScale;
uniform float RotationSpeed;
uniform float BloomSize;

in vec4 vertexColor;
in vec2 texCoord0;
out vec4 fragColor;

vec2 hash22(vec2 p) {
    p = vec2(dot(p, vec2(127.1, 311.7)), dot(p, vec2(269.5, 183.3)));
    return fract(sin(p) * 43758.5453123);
}

float noise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    vec2 u = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash22(i + vec2(0.0, 0.0)).x, hash22(i + vec2(1.0, 0.0)).x, u.x),
            mix(hash22(i + vec2(0.0, 1.0)).x, hash22(i + vec2(1.0, 1.0)).x, u.x), u.y);
}

float fbm(vec2 p, float time) {
    vec2 q = vec2(noise(p + vec2(0.0)), noise(p + vec2(1.2)));
    vec2 r = vec2(noise(p + 4.0 * q + vec2(1.7, 9.2) + time * 0.1),
            noise(p + 4.0 * q + vec2(8.3, 2.8) + time * 0.05));
    return noise(p + 2.0 * r);
}

mat2 rot(float a) {
    float s = sin(a);
    float c = cos(a);
    return mat2(c, -s, s, c);
}
float circleBloom(vec2 p, float radius) {
    float d = length(p);
    return exp(-d * d * 2.0 / (radius * radius));
}

void main() {
    float dx = length(dFdx(texCoord0));
    float dy = length(dFdy(texCoord0));
    vec2 quadPixelSize = vec2(1.0 / dx, 1.0 / dy);

    // Screen-locked position scaled by quad size
    vec2 uv = gl_FragCoord.xy;
    uv /= quadPixelSize.y;

    // nebula
    float mask = fbm(uv * 1.5, TIME);
    vec3 color = mix(BG, NEBULA_A, mask);
    color = mix(color, NEBULA_B, pow(mask, 2.5));

    // stars
    vec3 stars = vec3(0.0);
    vec3 bloom = vec3(0.0);
    vec2 starUV = uv / StarScale;
    vec2 id = floor(starUV);  // MOVED OUTSIDE
    vec2 gv = fract(starUV) - 0.5;

    // sample current and neighbors
    for (int x = -1; x <= 2; x++) {
        for (int y = -1; y <= 2; y++) {
            vec2 neighborId = id + vec2(float(x), float(y));
            vec2 rand = hash22(neighborId);
            if (rand.x < StarChance) {
                vec2 starPos = (rand - 0.5) * 0.7;
                vec2 neighborGv = gv - vec2(float(x), float(y));
                vec2 offsetFromStar = neighborGv - starPos;

                // Random visibility - some stars just don't show
                float visible = step(1.0 - StarChance, rand.y);

                if (visible > 0.5) {
                    float twinkle = 0.5 + 0.5 * sin(TIME * (1.0 + rand.y * 2.0) + rand.x * 6.28);

                    float angle = TIME * RotationSpeed + rand.y * 6.28;
                    vec2 rotated = rot(angle) * offsetFromStar;
                    float d = length(rotated);

                    float core = smoothstep(0.08, 0.0, d);
                    float rays = smoothstep(0.04, 0.0, abs(rotated.x * rotated.y) * 20.0) * smoothstep(0.4, 0.0, d);
                    stars += (core + rays * 0.6) * twinkle * STAR_CORE;

                    float bloomRadius = 0.3 + rand.y * 0.4;
                    float glow = circleBloom(offsetFromStar, bloomRadius) * BloomSize;
                    float wideGlow = circleBloom(offsetFromStar, bloomRadius * 2.0) * 0.4;
                    bloom += (glow + wideGlow) * twinkle * STAR_BLOOM;
                }
            }
        }
    }

    color += stars;
    color += bloom * 0.8;

    vec4 outColor = vec4(color, 1.0) * vec4(vertexColor.rgb, 1.0);
    fragColor = outColor * vertexColor.a;
}
