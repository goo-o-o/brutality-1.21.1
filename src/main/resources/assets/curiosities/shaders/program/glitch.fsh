#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;
uniform float Time;

uniform float ChromAberrAmountX;
uniform float ChromAberrAmountY;
uniform float RightStripesAmount;
uniform float RightStripesFill;
uniform float LeftStripesAmount;
uniform float LeftStripesFill;
uniform vec4 DisplacementAmount;
uniform float WavyDisplFreq;
uniform float GlitchEffect;

in vec2 texCoord;
out vec4 fragColor;

float rand(vec2 co) {
    return fract(sin(dot(co, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
    vec2 _ChromAberrAmount = vec2(ChromAberrAmountX, ChromAberrAmountY) * GlitchEffect;
    vec4 displAmount = DisplacementAmount * GlitchEffect;

    // quantize time so stripes jump sharply every few frames
    float stepTime = floor(Time * 20.0);

    // 1. stripes calculation
    float stripesRight = floor(texCoord.y * RightStripesAmount);
    stripesRight = step(RightStripesFill, rand(vec2(stripesRight, stepTime)));

    float stripesLeft = floor(texCoord.y * LeftStripesAmount);
    stripesLeft = step(LeftStripesFill, rand(vec2(stripesLeft, stepTime + 1.0)));

    // 2. wavy displacement mask
    float wavyVal = (sin(texCoord.y * WavyDisplFreq) + 1.0) / 2.0;
    vec4 wavyDispl = mix(vec4(1.0, 0.0, 0.0, 1.0), vec4(0.0, 1.0, 0.0, 1.0), wavyVal);

    // 3. calculate uv displacement
    vec2 displUV = (displAmount.xy * stripesRight) - (displAmount.xy * stripesLeft);
    displUV += (displAmount.zw * wavyDispl.r) - (displAmount.zw * wavyDispl.g);

    // 4. sample chromatic aberration
    float chromR = texture(DiffuseSampler, texCoord + displUV + _ChromAberrAmount).r;
    float chromG = texture(DiffuseSampler, texCoord + displUV).g;
    float chromB = texture(DiffuseSampler, texCoord + displUV - _ChromAberrAmount).b;

    fragColor = vec4(chromR, chromG, chromB, 1.0);
}