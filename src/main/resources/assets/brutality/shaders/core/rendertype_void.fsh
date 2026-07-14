#version 150

// https://neort.io/en/art/bq2s6r43p9fefb927440?index=-1

uniform float GameTime;
in vec2 texCoord0;
in vec4 vertexColor;
out vec4 fragColor;

#define TAU 6.28318530718
#define PI 3.14159265359
#define Octaves 8

float circ(vec2 p) {
    float r = length(p);
    r = 0.5 * log(r);
    return abs(mod(r * 4.0, TAU) - 3.14) * 2.0 + 2.0;
}

void main() {
    float quadWidth = 1.0 / length(dFdx(texCoord0));
    float quadHeight = 1.0 / length(dFdy(texCoord0));

    vec2 uv = (texCoord0 - 0.5) * vec2(quadWidth / quadHeight, 1.0);
    vec2 p = uv * 12.0;

    float time = GameTime * 250.0;

    vec2 pixuv = vec2((texCoord0 - 0.5).x * 1.0, (texCoord0 - 0.5).y * 1.0);

    vec2 p2 = mod(pixuv * TAU, TAU) - 250.0;
    vec2 s = vec2(p2);
    float c = 1.0;
    float inten = 0.005;

    for (int n = 0; n < Octaves; n++) {
        float t = time * (1.0 - (3.0 / float(n + 1)));
        s = p + vec2(cos(t - s.x) + sin(t + s.y), sin(t - s.y) + cos(t + s.x));
        c += 1.0 / length(vec2(p2.x / (sin(s.x + t) / inten), p2.y / (cos(s.y + t) / inten)));
    }
    c /= float(Octaves);
    c = 1.17 - pow(c, 1.4);

    float colr = pow(abs(c), 8.0);

    p /= exp(mod((time * 0.3) * 5.0, PI));
    colr *= pow(abs((0.1 - circ(p))), 3.5);

    vec3 col = vec3(1.0, 1.0, 1.0) / colr;
    col = pow(col, vec3(1.0, 1.0, 0.8));

    fragColor = vec4(col, 1.0);
}