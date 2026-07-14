#version 150

uniform float GameTime;
uniform float GuiScale;
uniform float Amount;

in vec2 texCoord0;
in vec4 vertexColor;
out vec4 fragColor;


#define COLOR_BASE vec3(0.89, 0.482, 0.373)
#define COLOR_TIP  vec3(0.824, 0.855, 0.659)
#define INTENSITY Amount * 10.0
#define TIME GameTime * 1000.0

void main() {
    // calculate the actual width and height of the quad in screen pixels
    // adding a tiny epsilon eliminates rounding errors from screen-space derivatives
    float quadWidth = (1.0 / length(dFdx(texCoord0.x))) + 0.005;
    float quadHeight = (1.0 / length(dFdy(texCoord0.y))) + 0.005;

    float targetBlockSize = max(1.0, GuiScale);

    // ensure the grid counts are strictly rounded integers
    vec2 totalRetroPixels = round(vec2(quadWidth, quadHeight) / targetBlockSize);

    // apply pixelation filter safely using the normalized integer step counts
    vec2 pixelatedTex = floor(texCoord0 * totalRetroPixels) / totalRetroPixels;

    // push coordinates slightly inward toward cell centers to prevent boundary bleeding
    pixelatedTex += 0.5 / totalRetroPixels;
    // 2. perform all math using the uniform pixelated coordinates
    vec2 uv;
    uv.x = pixelatedTex.x - 0.5;
    uv.y = 1.0 - pixelatedTex.y;
    uv.y -= 1.0;

    float localY = (1.0 - uv.y);

    // distort using pixelated coordinates
    vec2 animUV = uv;
    animUV += animUV * 0.01 * sin(-1.123 * uv.x + 0.2 * TIME)
    * cos(5.333 * uv.y + TIME * 0.931);

    // continuous time scroll factor
    float rawScroll = mod(4.0 * TIME, 10000.0) - 5000.0;
    vec2 flameScroll = vec2(0.0, rawScroll);

    float scaleFac = 7.5 + 3.0 / (2.0 + 2.0 * INTENSITY);
    vec2 sv = animUV * scaleFac + flameScroll;
    float speed = 20.781 + sin(TIME) * cos(TIME * 0.151);
    vec2 sv2 = vec2(0.0);

    // procedural noise loop
    for (int i = 0; i < 5; i++) {
        float flip = mod(float(i), 2.0) > 1.0 ? -1.0 : 1.0;
        sv2 += sv + 0.05 * sv2.yx * flip
        + 0.3 * (cos(length(sv) * 0.411)
        + 0.3344 * sin(length(sv))
        - 0.23 * cos(length(sv)));
        sv += 0.5 * vec2(
                cos(cos(sv2.y) + speed * 0.0812) * sin(3.22 + sv2.x - speed * 0.1531),
                sin(-sv2.x * 1.21222 + 0.113785 * speed) * cos(sv2.y * 0.91213 - 0.13582 * speed)
        );
    }

    // calculate fire shape density
    float smoke = max(0.0,
            (length((sv - flameScroll) / scaleFac * 5.0) + 0.1 * (length(uv) - 0.5))
            * (2.0 / (2.0 + INTENSITY * 0.2))
    );

    smoke += max(0.0, 2.0 - 0.3 * INTENSITY)
    * max(0.0, 2.0 * (localY - 0.5) * (localY - 0.5));

    if (abs(uv.x) > 0.45) {
        smoke += 10.0 * (abs(uv.x) - 0.35);
    }

    // 3. posterize the final output mask to eliminate gradient aliasing
    smoke = floor(smoke * 8.0) / 8.0;

    if (smoke >= 1.0) discard;

    // shading pass
    vec3 color = COLOR_BASE * vertexColor.rgb;
    if (localY < 0.15) {
        float t = (0.15 - localY) / 0.15;
        vec3 tip = COLOR_TIP * vertexColor.rgb;
        color = mix(color, tip * 2.5, t);
        color += color * (-2.0 + 0.5 * INTENSITY * smoke) * t;
    }

    fragColor = vec4(color, 1.0);
}