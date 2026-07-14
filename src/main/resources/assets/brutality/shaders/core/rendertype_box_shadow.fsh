#version 150

uniform float Margin;
in vec2 texCoord0;
in vec4 vertexColor;
out vec4 fragColor;

void main() {
    float quadWidth = 1.0 / length(dFdx(texCoord0));
    float quadHeight = 1.0 / length(dFdy(texCoord0));

    vec2 pixelPos = texCoord0 * vec2(quadWidth, quadHeight);
    vec2 center = vec2(quadWidth / 2.0, quadHeight / 2.0);

    vec2 dist = abs(pixelPos - center);
    vec2 innerSize = vec2(quadWidth / 2.0 - Margin, quadHeight / 2.0 - Margin);

    // Calculate shadow per axis
    float shadowX = 1.0 - smoothstep(innerSize.x - Margin, innerSize.x + Margin, dist.x);
    float shadowY = 1.0 - smoothstep(innerSize.y - Margin, innerSize.y + Margin, dist.y);

    // Multiply for smooth box shadow (no corner artifacts)
    float mask = shadowX * shadowY;

    fragColor = vec4(vertexColor.rgb, vertexColor.a * mask);
}