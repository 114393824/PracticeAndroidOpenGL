package com.opengl.sample5_11_mine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class TrianglePair {
	private int mProgram;// �Զ�����Ⱦ������ɫ������id
	private int muMVPMatrixHandle;// �ܱ任��������
	private int maPositionHandle; // ����λ����������
	private int maColorHandle; // ������ɫ��������
	private String mVertexShader;// ������ɫ��
	private String mFragmentShader;// ƬԪ��ɫ��

	private FloatBuffer mVertexBuffer;// �����������ݻ���
	private FloatBuffer mColorBuffer;// ������ɫ���ݻ���
	private int vCount = 0;

	public TrianglePair(MySurfaceView mv) {
		this.initVertexData();
		this.initShader(mv);
	}

	private void initVertexData() {
		// �����������ݵĳ�ʼ��================begin============================
		vCount = 6;
		float vertices[] = new float[] { -8 * Constant.UNIT_SIZE,
				10 * Constant.UNIT_SIZE, 0, -2 * Constant.UNIT_SIZE,
				2 * Constant.UNIT_SIZE, 0, -8 * Constant.UNIT_SIZE,
				2 * Constant.UNIT_SIZE, 0,

				8 * Constant.UNIT_SIZE, 2 * Constant.UNIT_SIZE, 0,
				8 * Constant.UNIT_SIZE, 10 * Constant.UNIT_SIZE, 0,
				2 * Constant.UNIT_SIZE, 10 * Constant.UNIT_SIZE, 0 };

		// ���������������ݻ���
		// vertices.length*4����Ϊһ�������ĸ��ֽ�
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());// �����ֽ�˳��
		mVertexBuffer = vbb.asFloatBuffer();// ת��ΪFloat�ͻ���
		mVertexBuffer.put(vertices);// �򻺳����з��붥����������
		mVertexBuffer.position(0);// ���û�������ʼλ��
		// �ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
		// ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
		// �����������ݵĳ�ʼ��================end============================

		// ������ɫ���ݵĳ�ʼ��================begin============================
		float colors[] = new float[]// ������ɫֵ���飬ÿ������4��ɫ��ֵRGBA
		{ 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0,
				0 };
		// ����������ɫ���ݻ���
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());// �����ֽ�˳��
		mColorBuffer = cbb.asFloatBuffer();// ת��ΪFloat�ͻ���
		mColorBuffer.put(colors);// �򻺳����з��붥����ɫ����
		mColorBuffer.position(0);// ���û�������ʼλ��
		// �ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
		// ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
		// ������ɫ���ݵĳ�ʼ��================end============================
	}

	private void initShader(MySurfaceView mv) {
		// ���ض�����ɫ���Ľű�����
		mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh",
				mv.getResources());
		// ����ƬԪ��ɫ���Ľű�����
		mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh",
				mv.getResources());
		// ���ڶ�����ɫ����ƬԪ��ɫ����������
		mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
		// ��ȡ�����ж���λ����������
		maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		// ��ȡ�����ж�����ɫ��������
		maColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
		// ��ȡ�������ܱ任��������
		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
	}

	public void drawSelf() {
		// �ƶ�ʹ��ĳ��shader����
		GLES20.glUseProgram(mProgram);
		// �����ձ任������shader����
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				MatrixState.getFinalMatrix(), 0);
		// Ϊ����ָ������λ������
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
				false, 3 * 4, mVertexBuffer);
		// Ϊ����ָ��������ɫ����
		GLES20.glVertexAttribPointer(maColorHandle, 4, GLES20.GL_FLOAT, false,
				4 * 4, mColorBuffer);
		// ������λ����������
		GLES20.glEnableVertexAttribArray(maPositionHandle);
		GLES20.glEnableVertexAttribArray(maColorHandle);
		// ����������
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
	}
}
