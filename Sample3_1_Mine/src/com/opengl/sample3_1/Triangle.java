package com.opengl.sample3_1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Triangle {
	public static float[] mProjMatrix = new float[16];
	public static float[] mVMatrix = new float[16];
	public static float[] mMVPMatrix;
	public float xAngle = 0;

	private static float[] mMMatrix = new float[16];

	private int vCount = 0;
	private FloatBuffer mVertexBuffer;
	private FloatBuffer mColorBuffer;
	private String mVertexShader;
	private String mFragmentShader;
	private int mProgram;
	private int maPositionHandle;
	private int maColorHandle;
	private int muMVPMatrixHandle;

	public Triangle(TDView view) {
		this.initVertexData();
		this.initShader(view);
	}

	private void initVertexData() {
		this.vCount = 3;
		final float UNIT_SIZE = 0.2f;
		float vertices[] = new float[] { -4 * UNIT_SIZE, 0, 0, 0,
				-4 * UNIT_SIZE, 0, 0, 0, 4 * UNIT_SIZE };

		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		this.mVertexBuffer = vbb.asFloatBuffer();
		this.mVertexBuffer.put(vertices);
		this.mVertexBuffer.position(0);

		float colors[] = new float[] { 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0 };

		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		this.mColorBuffer = cbb.asFloatBuffer();
		this.mColorBuffer.put(colors);
		this.mColorBuffer.position(0);
	}

	private void initShader(TDView view) {
		this.mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh",
				view.getResources());
		this.mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh",
				view.getResources());

		this.mProgram = ShaderUtil.createProgram(this.mVertexShader,
				this.mFragmentShader);
		this.maPositionHandle = GLES20.glGetAttribLocation(this.mProgram,
				"aPosition");
		this.maColorHandle = GLES20
				.glGetAttribLocation(this.mProgram, "aColor");
		this.muMVPMatrixHandle = GLES20.glGetUniformLocation(this.mProgram,
				"vMVPMatrix");

	}

	public void drawSelf() {
		GLES20.glUseProgram(this.mProgram);
		Matrix.setRotateM(Triangle.mMMatrix, 0, 0, 0, 1, 0);
		Matrix.translateM(Triangle.mMMatrix, 0, 0, 0, 1);
		Matrix.rotateM(Triangle.mMMatrix, 0, this.xAngle, 1, 0, 0);

		GLES20.glUniformMatrix4fv(this.muMVPMatrixHandle, 1, false,
				getFianlMatrix(Triangle.mMMatrix), 0);

		GLES20.glVertexAttribPointer(this.maPositionHandle, 3, GLES20.GL_FLOAT,
				false, 3 * 4, this.mVertexBuffer);
		GLES20.glVertexAttribPointer(maColorHandle, 4, GLES20.GL_FLOAT, false,
				4 * 4, mColorBuffer);
		GLES20.glEnableVertexAttribArray(this.maPositionHandle);
		GLES20.glEnableVertexAttribArray(this.maColorHandle);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, this.vCount);
	}

	public static float[] getFianlMatrix(float[] spec) {
		mMVPMatrix = new float[16];
		Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0);
		Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
		return mMVPMatrix;
	}
}
