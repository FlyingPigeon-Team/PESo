package com.peso.Sliding;

/**
 *�������ǩ������ת�Ľ���
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

public class PersonalitysignatureActivity extends Activity {
	private ImageView P_return;
	private Button save_btn;// ���水ť
	private EditText changeSignature;// �����ǳƵ��ı���

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personality_signature);
		changeSignature = (EditText) findViewById(R.id.signature1);
		P_return = (ImageView) findViewById(R.id.sign_arrow1);
		save_btn = (Button) findViewById(R.id.save_button1);
		// ����¼�
		save_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent data = new Intent();
				data.putExtra("data", changeSignature.getText()
						.toString());
				// ������edittext��������Ϊdata����
				setResult(4, data);
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
