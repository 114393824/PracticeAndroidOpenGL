package com.opengl.sample5_11_mine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

public class TrianglePair {
	private int mProgram;// 自定义渲染管线着色器程序id
	private int muMVPMatrixHandle;// 总变换矩阵引用
	private int maPositionHandle; // 顶点位置属性引用
	private int maColorHandle; // 顶点颜色属性引用
	private String mVertexShader;// 顶点着色器
	private String mFragmentShader;// 片元着色器

	private FloatBuffer mVertexBuffer;// 顶点坐标数据缓冲
	private FloatBuffer mColorBuffer;// 顶点着色数据缓冲
	private int vCount = 0;

	public TrianglePair(MySurfaceView mv) {
		this.initVertexData();
		this.initShader(mv);
	}

	private void initVertexData() {
		// 顶点坐标数据的初始化================begin============================
		vCount = 6;
		float vertices[] = new float[] { -8 * Constant.UNIT_SIZE,
				10 * Constant.UNIT_SIZE, 0, -2 * Constant.UNIT_SIZE,
				2 * Constant.UNIT_SIZE, 0, -8 * Constant.UNIT_SIZE,
				2 * Constant.UNIT_SIZE, 0,

				8 * Constant.UNIT_SIZE, 2 * Constant.UNIT_SIZE, 0,
				8 * Constant.UNIT_SIZE, 10 * Constant.UNIT_SIZE, 0,
				2 * Constant.UNIT_SIZE, 10 * Constant.UNIT_SIZE, 0 };

		// 创建顶点坐标数据缓冲
		// vertices.length*4是因为一个整数四个字节
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());// 设置字节顺序
		mVertexBuffer = vbb.asFloatBuffer();// 转换为Float型缓冲
		mVertexBuffer.put(vertices);// 向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);// 设置缓冲区起始位置
		// 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
		// 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
		// 顶点坐标数据的初始化================end============================

		// 顶点着色数据的初始化================begin============================
		float colors[] = new float[]// 顶点颜色值数组，每个顶点4个色彩值RGBA
		{ 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0,
				0 };
		// 创建顶点着色数据缓冲
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());// 设置字节顺序
		mColorBuffer = cbb.asFloatBuffer();// 转换为Float型缓冲
		mColorBuffer.put(colors);// 向缓冲区中放入顶点着色数据
		mColorBuffer.position(0);// 设置缓冲区起始位置
		// 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
		// 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
		// 顶点着色数据的初始化================end============================
	}

	private void initShader(MySurfaceView mv) {
		// 加载顶点着色器的脚本内容
		mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh",
				mv.getResources());
		// 加载片元着色器的脚本内容
		mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh",
				mv.getResources());
		// 基于顶点着色器与片元着色器创建程序
		mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
		// 获取程序中顶点位置属性引用
		maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		// 获取程序中顶点颜色属性引用
		maColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
		// 获取程序中总变换矩阵引用
		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
	}

	public void drawSelf() {
		// 制定使用某套shader程序
		GLES20.glUseProgram(mProgram);
		// 将最终变换矩阵传入shader程序
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				MatrixState.getFinalMatrix(), 0);
		// 为画笔指定顶点位置数据
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
				false, 3 * 4, mVertexBuffer);
		// 为画笔指定顶点着色数据
		GLES20.glVertexAttribPointer(maColorHandle, 4, GLES20.GL_FLOAT, false,
				4 * 4, mColorBuffer);
		// 允许顶点位置数据数组
		GLES20.glEnableVertexAttribArray(maPositionHandle);
		GLES20.glEnableVertexAttribArray(maColorHandle);
		// 绘制三角形
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
	}
}
