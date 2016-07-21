package com.opengl.sample3_1;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class TDView extends GLSurfaceView {

	private SceneRenderer mRenderer;
	private RotateThread rthread;

	public TDView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setEGLContextClientVersion(2);
		this.mRenderer = new SceneRenderer();
		this.setRenderer(this.mRenderer);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	private class SceneRenderer implements GLSurfaceView.Renderer {
		Triangle tle;

		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT
					| GLES20.GL_DEPTH_BUFFER_BIT);
			tle.drawSelf();
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			GLES20.glViewport(0, 0, width, height);
			float ratio = (float) width / height;
			Matrix.frustumM(Triangle.mProjMatrix, 0, -ratio, ratio, -1, 1, 1,
					10);
			Matrix.setLookAtM(Triangle.mVMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f,
					1.0f, 0.0f);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			GLES20.glClearColor(0, 0, 0, 1.0f);
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);
			tle = new Triangle(TDView.this);
			rthread = new RotateThread();
			rthread.start();
		}

	}

	final float ANGLE_SPAN = 0.375f;

	public class RotateThread extends Thread {
		public boolean flag = true;

		@Override
		public void run() {
			while (flag) {
				mRenderer.tle.xAngle = mRenderer.tle.xAngle + ANGLE_SPAN;
				try {
					Thread.sleep(20);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
