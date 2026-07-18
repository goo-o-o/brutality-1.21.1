#version 150

// author : Aurukel

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;
uniform float GameTime;

in vec4 vertexColor;
in vec2 texCoord0;
in vec2 texCoord2;
in vec2 screenPos;

out vec4 fragColor;

void main() {
    float textureScale = 0.08;
    vec2 scrollSpeed = vec2(52.8, -52.8);
    float waveSpeed = 15.0;
    float waveFreq = 0.2;
    float waveAmp = 0.25;
    float overlayOpacity = 0.6;

    vec4 fontColor = texture(Sampler0, texCoord0);
    if (fontColor.a < 0.1) {
        discard;
    }

    vec2 reefUV = screenPos * textureScale;
    reefUV += GameTime * scrollSpeed;

    reefUV.x += sin(GameTime * waveSpeed + screenPos.y * waveFreq) * waveAmp;
    reefUV.y += cos(GameTime * waveSpeed + screenPos.x * waveFreq) * waveAmp;

    vec4 reefColor = texture(Sampler1, reefUV);

    vec4 finalColor = fontColor * vertexColor;
    vec3 waterOverlay = mix(finalColor.rgb, (reefColor.rgb * 1.5) * vertexColor.rgb, overlayOpacity * fontColor.a);

    fragColor = vec4(waterOverlay, finalColor.a);
}