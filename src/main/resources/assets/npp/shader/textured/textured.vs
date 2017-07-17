#version 120

attribute vec3 position;
attribute vec2 uv;

varying vec2 fragUv;

uniform mat4 projectionMatrix;
uniform mat4 objectMatrix;

void main() {
	fragUv = uv;
	gl_Position = projectionMatrix * objectMatrix * vec4(position, 1.0);
}