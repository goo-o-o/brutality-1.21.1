#version 150

#define UV_SCALE 0.001
//#define InnerBrightnessMultiplier 1.15
//#define StrikeFadeout 0.6
//#define StrikeChance 0.7
//#define Speed 0.5
//#define TendrilVolatility
uniform float GameTime;
uniform float InnerBrightnessMultiplier; // inner lightning brightness mult
uniform float StrikeFadeout; // lightning strike fadeout
uniform float StrikeChance; // chance for lightning to strike for each check
uniform float Speed; // higher values make lightning strike more often and strikes themselves shorter
uniform float TendrilVolatility; // movement of tendrils

in vec2 texCoord0;
in vec4 vertexColor;
out vec4 fragColor;

#define TIME (GameTime * 1000.0)

float hash11(float p)
{
    p = fract(p * .1031);
    p *= p + 33.33;
    p *= p + p;
    return fract(p);
}

float hash12(vec2 p)
{
    vec3 p3 = fract(vec3(p.xyx) * .1031);
    p3 += dot(p3, p3.yzx + 33.33);
    return fract((p3.x + p3.y) * p3.z);
}

mat2 rotate2d(float theta)
{
    float c = cos(theta);
    float s = sin(theta);
    return mat2(c, -s, s, c);
}

float noise(vec2 p)
{
    vec2 ip = floor(p);
    vec2 fp = fract(p);
    float a = hash12(ip);
    float b = hash12(ip + vec2(1, 0));
    float c = hash12(ip + vec2(0, 1));
    float d = hash12(ip + vec2(1, 1));

    vec2 t = smoothstep(0.0, 1.0, fp);
    return mix(mix(a, b, t.x), mix(c, d, t.x), t.y);
}

float fbm(vec2 p, int octaveCount)
{
    float value = 0.0;
    float amplitude = 0.5;
    mat2 r = rotate2d(0.45);
    for (int i = 0; i < octaveCount; ++i)
    {
        value += amplitude * noise(p);
        p = r * p * 2.0;
        amplitude *= 0.5;
    }
    return value;
}

void main()
{
    // strike timing and early exit
    float strikeCycle = fract(TIME * Speed);
    float strikeMask = smoothstep(StrikeFadeout, 0.0, strikeCycle);

    float rhythmicFlicker = hash11(floor(TIME * Speed));
    strikeMask *= step(1.0 - StrikeChance, rhythmicFlicker);

    if (strikeMask <= 0.0) {
        fragColor = vec4(0.0, 0.0, 0.0, 1.0);
        return;
    }

    // screen locked space but per quad scaling
    float dx = length(dFdx(texCoord0));
    float dy = length(dFdy(texCoord0));
    vec2 quadPixelSize = vec2(1.0 / dx, 1.0 / dy);

    // Grab absolute screen pixels
    vec2 uv = gl_FragCoord.xy;

    // Scale the screen pixels relative to the quad's height dimension
    uv /= quadPixelSize.y;

    // Centers the coordinate space and adjusts density
    uv = (uv - 0.5) * 2.5;
    uv *= 0.1;
    // generate lightning field
    float lightningField = 0.0;
    float t = TIME * TendrilVolatility;

    for (int i = 0; i < 3; i++) {
        float angle = float(i) * 1.25 + t * 0.05;
        vec2 dUv = uv * rotate2d(angle);
        dUv *= 2.0 + float(i) * 0.5;

        dUv += 1.5 * fbm(dUv + vec2(t * 0.4, -t * 0.2), 6) - 0.75;

        float lines = abs(fract(dUv.x) - 0.5);
        float flicker = mix(0.005, 0.04, hash11(t * 2.0 + float(i)));
        lightningField += pow(flicker / lines, 1.2);
    }

    // color and contrast output
    vec3 electricBlue = vertexColor.rgb;
    vec3 coreColor = vertexColor.rgb * InnerBrightnessMultiplier;

    float sharpLightning = pow(lightningField * strikeMask, 2.5);
    vec3 col = electricBlue * sharpLightning + coreColor * pow(lightningField * strikeMask * 0.4, 4.0);

    float skyGlow = noise(uv * 1.5 + t) * 0.015 * smoothstep(0.1, 0.5, lightningField) * strikeMask;
    col += electricBlue * skyGlow;

    col = pow(col, vec3(0.9));
    fragColor = vec4(col, 1.0);
}