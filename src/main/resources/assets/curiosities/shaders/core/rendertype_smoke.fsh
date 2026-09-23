#version 150

uniform float GameTime;
uniform float Intensity;

in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;

#define TIME (GameTime * 1000.0)

vec2 hash22(vec2 x)
{
    return fract(sin(x * mat2(43.37862, 24.58974, 32.37621, 53.32761)) * 4534.3897);
}

float hash12(vec2 x)
{
    return fract(sin(dot(x, vec2(43.37861, 34.58761))) * 342.538772);
}

vec2 getCellPoint(vec2 cell)
{
    float time = TIME * (hash12(cell + 0.123) - 0.5) * 0.5;
    float c = cos(time), s = sin(time);
    vec2 hash = (hash22(cell) - 0.5) * mat2(c, s, -s, c) + 0.5;
    return hash + cell;
}

float getCellValue(vec2 cell)
{
    return hash12(cell);
}

float makeSmooth(float x)
{
    float blend = 1.0 - (cos(TIME) * 0.5 + 0.5);
    return mix(x * x * (3.0 - 2.0 * x), sqrt(x), blend);
}

float modifiedVoronoiNoise12(vec2 uv)
{
    vec2 rootCell = floor(uv);
    float value = 0.0;

    for (float x = -1.0; x <= 1.0; x++)
    {
        for (float y = -1.0; y <= 1.0; y++)
        {
            vec2 cell = rootCell + vec2(x, y);
            vec2 cellPoint = getCellPoint(cell);
            float cellValue = getCellValue(cell);
            float cellDist = distance(uv, cellPoint);
            value += makeSmooth(clamp(1.0 - cellDist, 0.0, 1.0)) * cellValue;
        }
    }
    return value * 0.5;
}

float layeredNoise12(vec2 x)
{
    float sum = 0.0;
    float maxValue = 0.0;

    for (float i = 1.0; i <= 2.0; i *= 2.0)
    {
        float noise = modifiedVoronoiNoise12(x * i) / i;
        sum += noise;
        maxValue += 1.0 / i;
    }
    return sum / maxValue;
}

void main()
{
    vec2 uv = texCoord0;


    float uvChangeX = length(dFdx(uv));
    float uvChangeY = length(dFdy(uv));

    // map uv space directly to physical pixel units
    uv /= vec2(uvChangeX, uvChangeY);

    // zoom aka cell size, bigger is bigger smaller is smaller
    float cellSizeInPixels = 100.0;
    uv /= cellSizeInPixels;

    // uv not normalized to 0 to 1, meaning we have to use texCoord
    vec2 stretchedUV = texCoord0 - 0.5;
    float vignette = smoothstep(0.5, 0.0, length(stretchedUV));

    // upward shift
    uv.y -= TIME;

    uv += layeredNoise12(uv);
    float noise = layeredNoise12(uv);
    noise *= vignette;

    // smooth out the smoke first!!!
    // intensity lowers threshold
    float smokeDensity = smoothstep(0.1 - (Intensity * 0.01), 1.1, noise);

    smokeDensity *= Intensity;
    // use colors passed from vertex
    fragColor = vertexColor;

    // multiply rgb to make shading (low areas become gray and vise versa)
    fragColor.rgb *= smokeDensity;
    fragColor.a *= clamp(smokeDensity, 0.0, 1.0);
}