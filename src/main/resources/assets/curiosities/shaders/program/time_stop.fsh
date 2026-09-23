#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D SphereMask;
uniform sampler2D SceneDepth;
uniform sampler2D SphereDepth;

uniform vec2 OutSize;
uniform float GameTime; // continuous tick time
uniform float IsCameraInside;

in vec2 texCoord;
out vec4 fragColor;

float getLuma(vec3 color) {
    return dot(color, vec3(0.299, 0.587, 0.114));
}

float linearDepth(float d, float near, float far) {
    float z = d * 2.0 - 1.0;
    return (2.0 * near * far) / (far + near - z * (far - near));
}

void main() {
    // ── Slow Continuous Wave Speed ──────────────────────────────────────
    // 20 ticks = 1 second. Multiplying by 0.08 gives a slow, steady drift
    float waveTime = GameTime * 2000.0;


    // ── Animated Distortion ─────────────────────────────────────────────
    vec2 aspectVec = vec2(OutSize.x / OutSize.y, 1.0);
    vec2 centeredUv = (texCoord - 0.5) * aspectVec;
    float distFromCenter = length(centeredUv);
    vec2 distortionDir = normalize(centeredUv + vec2(0.0001));

    // smooth wave flow across space and time
    float wave = sin(distFromCenter * 25.0 - waveTime) * 0.002;

    vec4 worldRaw = texture(DiffuseSampler, texCoord);
    vec4 sphereDataUnfiltered = texture(SphereMask, texCoord);

    // Early exit check
    if (sphereDataUnfiltered.a <= 0.0001 && IsCameraInside <= 0.5) {
        vec2 texelCheck = 1.0 / OutSize;
        float nearTap = texture(SphereMask, texCoord + vec2(texelCheck.x * 3.0, 0.0)).a +
        texture(SphereMask, texCoord - vec2(texelCheck.x * 3.0, 0.0)).a;
        if (nearTap <= 0.0001) {
            fragColor = vec4(worldRaw.rgb, 1.0);
            return;
        }
    }

    // ── Distort UVs for Screen & Outline Together ──────────────────────
    float rawMaskUnfiltered = clamp(sphereDataUnfiltered.a, 0.0, 1.0);
    float maskUnfiltered = (IsCameraInside > 0.5) ? 1.0 : rawMaskUnfiltered;
    vec2 distortedUv = clamp(texCoord + (distortionDir * wave * maskUnfiltered), 0.0, 1.0);

    vec4 world = texture(DiffuseSampler, distortedUv);
    vec4 sphereData = texture(SphereMask, distortedUv);

    float rawMask = clamp(sphereData.a, 0.0, 1.0);
    float mask = (IsCameraInside > 0.5) ? 1.0 : rawMask;

    // ── Distorted 2D Silhouette Outline ──────────────────────────────────
    vec2 texel = 1.0 / OutSize;
    float edgeWidth = 3.0;

    float aL = texture(SphereMask, distortedUv - vec2(texel.x * edgeWidth, 0.0)).a;
    float aR = texture(SphereMask, distortedUv + vec2(texel.x * edgeWidth, 0.0)).a;
    float aU = texture(SphereMask, distortedUv + vec2(0.0, texel.y * edgeWidth)).a;
    float aD = texture(SphereMask, distortedUv - vec2(0.0, texel.y * edgeWidth)).a;

    float aTL = texture(SphereMask, distortedUv + vec2(-texel.x, texel.y) * edgeWidth * 0.7).a;
    float aTR = texture(SphereMask, distortedUv + vec2( texel.x, texel.y) * edgeWidth * 0.7).a;
    float aBL = texture(SphereMask, distortedUv + vec2(-texel.x, -texel.y) * edgeWidth * 0.7).a;
    float aBR = texture(SphereMask, distortedUv + vec2( texel.x, -texel.y) * edgeWidth * 0.7).a;

    float edgeRaw = abs(rawMask - aL) + abs(rawMask - aR) + abs(rawMask - aU) + abs(rawMask - aD)
    + (abs(rawMask - aTL) + abs(rawMask - aTR) + abs(rawMask - aBL) + abs(rawMask - aBR)) * 0.5;

    float silhouette = smoothstep(0.0, 3.0, edgeRaw);

    float near = 0.05;
    float far = 256.0;

    // ── Distorted Depth Intersection Glow ───────────────────────────────
    float rawScene  = texture(SceneDepth, distortedUv).r;
    float rawSphere = texture(SphereDepth, distortedUv).r;
    float sceneLin  = linearDepth(rawScene, near, far);
    float sphereLin = linearDepth(rawSphere, near, far);

    float thickness = 0.4;
    float intersect = 1.0 - clamp((sceneLin - sphereLin) / thickness, 0.0, 1.0);
    intersect *= rawMask;

    float rim = max(silhouette, intersect);

    // ── Chromatic Aberration ─────────────────────────────────────────────
    float spread = (0.0025 + distFromCenter * 0.01) * mask;
    float rChannel = texture(DiffuseSampler, clamp(distortedUv + distortionDir * spread, 0.0, 1.0)).r;
    float gChannel = texture(DiffuseSampler, distortedUv).g;
    float bChannel = texture(DiffuseSampler, clamp(distortedUv - distortionDir * spread, 0.0, 1.0)).b;
    vec3 distortedColor = vec3(rChannel, gChannel, bChannel);

    // ── Darkened Neutral Desaturation ───────────────────────────────────
    float luma = getLuma(distortedColor);
    vec3 freezeColor = mix(vec3(luma), distortedColor, 0.20);
    freezeColor = smoothstep(0.0, 1.0, freezeColor);
    freezeColor *= 0.70; // 30% darker

    // ── Combine Output ───────────────────────────────────────────────────
    vec3 rimColor = vec3(0.0);
    vec3 base = mix(world.rgb, freezeColor, mask);
    vec3 finalColor = mix(base, rimColor, rim);

    fragColor = vec4(finalColor, 1.0);
}