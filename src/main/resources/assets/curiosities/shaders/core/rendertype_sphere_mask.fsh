#version 150

in vec3 viewNormal;
in vec4 vertexColor;
out vec4 fragColor;

void main() {
    vec3 n = normalize(viewNormal);
    fragColor = vec4(n * 0.5 + 0.5, 1.0);
}