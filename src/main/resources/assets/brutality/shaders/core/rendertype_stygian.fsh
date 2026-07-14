#version 150

uniform float GameTime;
uniform int Iterations;
uniform float Brightness;

in vec2 texCoord0;
in vec4 vertexColor;
out vec4 fragColor;


#define TIME GameTime * 1000.0

float fractal(vec3 p) {
    float s, w, l;
    p.xy *= mat2(cos(.3 * TIME + p.z - vec4(0, 33, 11, 0)));
    p += cos(TIME + p.yzx * 12.) * .07;
    p.y -= 1.6;
    for (s = 0., w = 1.; s++ < 7.; p *= l, w *= l)
    p = abs(sin(p)) - 1.,
    l = 1.3 / dot(p, p);
    return length(p) / w;
}

void main() {
    float stepDistance = 0.1;
    float totalDistance = 0.0;
    float currentIteration = 0.0;

    float quadWidth  = 1.0 / length(dFdx(texCoord0));
    float quadHeight = 1.0 / length(dFdy(texCoord0));

    vec2 uv = (texCoord0 - 0.5) * vec2(quadWidth / quadHeight, 1.0);
    vec4 sceneColor = vec4(1.0);

    for (; currentIteration++ < Iterations;) {
        vec3 rayPos = vec3(uv * totalDistance, totalDistance);

        stepDistance = fractal(rayPos);
        totalDistance += stepDistance;

        float glowIntensity = 1.0 / max(stepDistance, 0.001);
        sceneColor.rgb += (0.7 * Brightness) * glowIntensity;
        sceneColor.b += (0.2 * Brightness) * glowIntensity;
    }

    vec4 colorTint = vertexColor;
    sceneColor *= colorTint;

    float distanceDampening = max(length(uv), 0.001);
    fragColor = tanh(sceneColor / 1e5 / distanceDampening);
}