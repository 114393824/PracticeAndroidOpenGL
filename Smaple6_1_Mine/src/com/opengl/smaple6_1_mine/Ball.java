package com.opengl.smaple6_1_mine;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.opengl.GLES20;

public class Ball {
	private String mVertexShader;
	private String mFragmentShader;
	private FloatBuffer mVertexBuffer;

	private int mProgram;
	private int muMVPMatrixHandle;
	private int maPositionHandle;
	private int muRHandle;

	private int vCount = 0;
	public float yAngle = 0;
	public float xAngle = 0;
	private float zAngle = 0;
	private float r = 0.8f;

	public Ball(MySurfaceView mv) {
		this.initVertexData();
		this.initShader(mv);
	}

	private void initVertexData() {
		ArrayList<Float> alVertix = new ArrayList<Float>();
		final int angleSpan = 10;
		for (int vAngle = -90; vAngle < 90; vAngle = vAngle + angleSpan) {
			for (int hAngle = 0; hAngle <= 360; hAngle = hAngle + angleSpan) {

			}
		}
	}

	private void initShader(MySurfaceView mv) {
		this.mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh",
				mv.getResources());
		this.mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh",
				mv.getResources());

		this.mProgram = ShaderUtil.createProgram(this.mVertexShader,
				this.mFragmentShader);
		this.maPositionHandle = GLES20.glGetAttribLocation(this.mProgram,
				"aPosition");
		this.muMVPMatrixHandle = GLES20.glGetUniformLocation(this.mProgram,
				"uMVPMatrix");
		this.muRHandle = GLES20.glGetUniformLocation(this.mProgram, "uR");
	}

	public void drawSelf() {
		MatrixState.rotate(xAngle, 1, 0, 0);
		MatrixState.rotate(yAngle, 0, 1, 0);
		MatrixState.rotate(zAngle, 0, 0, 1);

		GLES20.glUseProgram(this.mProgram);
		GLES20.glUniformMatrix4fv(this.muMVPMatrixHandle, 1, false,
				MatrixState.getFinalMatrix(), 0);
		GLES20.glUniform1f(this.muRHandle, this.r * Constant.UNIT_SIZE);
		GLES20.glVertexAttribPointer(this.maPositionHandle, 3, GLES20.GL_FLOAT,
				false, 3 * 4, this.mVertexBuffer);
		GLES20.glEnableVertexAttribArray(this.maPositionHandle);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
	}
}
