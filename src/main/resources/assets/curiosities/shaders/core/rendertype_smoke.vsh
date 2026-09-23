#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0; // <--- CRITICAL: Matches the POSITION_TEX_COLOR layout format

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec2 texCoord0;
out vec4 vertexColor;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    texCoord0 = UV0;       // <--- Passes the 0.0 to 1.0 UVs to the .fsh
    vertexColor = Color;   // <--- Passes vertex colors down
}