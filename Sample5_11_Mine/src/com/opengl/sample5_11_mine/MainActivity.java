package com.opengl.sample5_11_mine;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends Activity {
	private MySurfaceView mGLSurfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ����Ϊȫ��
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ����Ϊ����
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// ��ʼ��GLSurfaceView
		this.mGLSurfaceView = new MySurfaceView(this);

		this.setContentView(R.layout.main);

		LinearLayout ll = (LinearLayout) findViewById(R.id.main_liner);
		ll.addView(mGLSurfaceView);
		this.mGLSurfaceView.requestFocus();// ��ȡ����
		this.mGLSurfaceView.setFocusableInTouchMode(true);// ����Ϊ�ɴ���
		RadioButton rb = (RadioButton) findViewById(R.id.RadioButton01);
		
		rb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mGLSurfaceView.setCwOrCcw(isChecked);
			}
		});
		rb = (RadioButton) findViewById(R.id.RadioButton03);
        // RadioButton��Ӽ�����
		rb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mGLSurfaceView.setCullFace(isChecked);
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.mGLSurfaceView.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.mGLSurfaceView.onResume();
	}

}
