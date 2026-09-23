#version 150

#define PI 3.14159265359
#define TWO_PI 6.28318530718
#define SCOPE_SIZE 6.0
#define PETROLEUM_SCALE 1.0
#define PIXEL_SIZE 1.0

uniform sampler2D Sampler0;
uniform float GameTime;

in vec2 texCoord0;
out vec4 fragColor;

vec2 viewport(in vec2 uv, in vec2 r) {
    return (uv * 2. - r) / min(r.x, r.y);
}

float rand(in float x, in int s) {
    return fract(sin(x + float(s)) * 43758.5453123);
}

float rand(in vec2 uv, in int seed) {
    return fract(sin(dot(uv.xy, vec2(12.9898, 78.233)) + float(seed)) * 43758.5453123);
}

float noise(in float x, in int s) {
    float xi = floor(x);
    float xf = fract(x);
    return mix(rand(xi, s), rand(xi + 1., s), smoothstep(0., 1., xf));
}

float noise(in vec2 p, in int s) {
    vec2 pi = floor(p);
    vec2 pf = fract(p);
    vec2 o = vec2(0, 1);

    float bl = rand(pi, s);
    float br = rand(pi + o.yx, s);
    float tl = rand(pi + o.xy, s);
    float tr = rand(pi + o.yy, s);

    vec2 w = smoothstep(0., 1., pf);
    float t = mix(tl, tr, w.x);
    float b = mix(bl, br, w.x);

    return mix(b, t, w.y);
}

float cosine(in float x, in float s) {
    float y = cos(fract(x) * PI);
    return floor(x) + .5 - (.5 * pow(abs(y), 1. / s) * sign(y));
}

vec3 gradient(in float t, in vec3 a, in vec3 b, in vec3 c, in vec3 d) {
    return a + b * cos(TWO_PI * (c * t + d));
}

vec3 c1 = vec3(1.);
vec3 c2 = vec3(.1);
vec3 c3 = vec3(1.);
vec3 c4 = vec3(.5, .6, .7);

void main() {
    vec4 texSample = texture(Sampler0, texCoord0);

    if (texSample.a < 0.01) {
        discard;
    }

    vec2 currentTexSize = vec2(textureSize(Sampler0, 0));
    vec2 pixelatedUV = (floor(texCoord0 * currentTexSize / PIXEL_SIZE) * PIXEL_SIZE + (PIXEL_SIZE * 0.5)) / currentTexSize;

    // centered uv calculation using pixel-consistent UVs
    vec2 uv = (pixelatedUV - 0.5) * 2.0 * (1.0 / PETROLEUM_SCALE);

    // minecraft GameTime conversion
    float t = (GameTime * 1200.0) / 16.;
    t += noise(t, 0);
    t += cosine(t, 2.);
    uv *= 2.5;

    vec2 uvf = fract(uv) - .5;
    vec2 uvi = floor(uv);
    vec2 n2 = (vec2(noise(uv + t, 0), noise(uv - t, 1)) - .5) * SCOPE_SIZE;
    uvi += n2;

    vec3 col = vec3(0);

    for (float i = -SCOPE_SIZE; i <= SCOPE_SIZE; i++) {
        for (float j = -SCOPE_SIZE; j <= SCOPE_SIZE; j++) {
            vec2 off = vec2(i, j);
            float n = noise(uvi - off + t * 2., 0) * 4.;
            float s = exp2(n);
            float d = length(uvf + off + n2);
            d = .025 / d / s;
            col = max(col, gradient(d + uvi.x + uvi.y, c1, c2, c3, c4) * sqrt(d));
        }
    }

    fragColor = vec4(col, texSample.a);
}