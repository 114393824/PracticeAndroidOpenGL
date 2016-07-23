package com.opengl.sample5_11_mine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class MySurfaceView extends GLSurfaceView {

	private SceneRenderer mRenderer;// 场景渲染器
	private boolean cullFaceFlag = false;// 是否开启背面剪裁的标志位
	private boolean cwCcwFlag = false;// 是否打开自定义卷绕的标志位

	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setEGLContextClientVersion(2);
		this.mRenderer = new SceneRenderer();
		this.setRenderer(this.mRenderer);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	// 设置是否开启背面剪裁的标志位
	public void setCullFace(boolean flag) {
		this.cullFaceFlag = flag;
	}

	// 设置是否打开自定义卷绕的标志位
	public void setCwOrCcw(boolean flag) {
		this.cwCcwFlag = flag;
	}

	private class SceneRenderer implements GLSurfaceView.Renderer {
		TrianglePair tp;// 三角形对

		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			if (cullFaceFlag) {
				GLES20.glEnable(GLES20.GL_CULL_FACE);
			} else {
				GLES20.glDisable(GLES20.GL_CULL_FACE);
			}
			if (cwCcwFlag) {
				GLES20.glFrontFace(GLES20.GL_CCW);
			} else {
				GLES20.glFrontFace(GLES20.GL_CW);
			}
			GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT
					| GLES20.GL_COLOR_BUFFER_BIT);
			MatrixState.pushMatrix();
			MatrixState.translate(0, -1.4f, 0);
			tp.drawSelf();
			MatrixState.popMatrix();
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			GLES20.glViewport(0, 0, width, height);
			Constant.ratio = (float) width / height;
			MatrixState.setProjectFrustum(-Constant.ratio, Constant.ratio, -1,
					1, 10, 100);
			MatrixState.setCamera(0, 0, 20, 0, 0, 0, 0, 1, 0);
			MatrixState.setInitStack();
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);
			tp = new TrianglePair(MySurfaceView.this);
		}

	}
}
