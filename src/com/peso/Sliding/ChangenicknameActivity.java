package com.peso.Sliding;

/**
 *����ҵ��ǳƺ���ת�Ľ���
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.peso.R;

public class ChangenicknameActivity extends Activity {
	private ImageView P_return;
	private Button save_btn;

	private EditText changeNikname;// �����ǳƵ��ı���

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changenickname);
		changeNikname = (EditText) findViewById(R.id.usernameView);
		P_return = (ImageView) findViewById(R.id.sign_arrow);
		save_btn = (Button) findViewById(R.id.save_button);
		// ����¼�
		save_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent data = new Intent();
				data.putExtra("data", changeNikname.getText()
						.toString());
				// ������edittext��������Ϊdata����
				setResult(2, data);//
				finish();
			}
		});
		P_return.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

	}
}
