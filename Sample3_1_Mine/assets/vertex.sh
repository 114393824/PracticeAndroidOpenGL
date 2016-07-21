uniform mat4 vMVPMatrix;
attribute vec3 aPosition;
attribute vec4 aColor;
varying vec4 vColor;

void main()
{
	gl_Position = vMVPMatrix * vec4(aPosition,1);
	vColor = aColor;
}
