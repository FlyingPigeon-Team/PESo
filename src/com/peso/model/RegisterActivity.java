package com.peso.model;

import java.util.ArrayList;
import java.util.List;

import Thread.CommThread;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.peso.MainActivity;
import com.peso.R;

public class RegisterActivity extends Activity {

	private TextView mLoginTextView1;
	private TextView mLoginTextView2;
	private TextView mtitleTextView;
	private Button mRegisterButton;
	private EditText mPasswordEditText;
	private EditText mEmailEditText;
	private EditText mUsernameEditText;
	String message = "";
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		mRegisterButton = (Button) findViewById(R.id.register_button);
		mRegisterButton.getBackground().setAlpha(100);// 0~255͸����ֵ
		mLoginTextView1 = (TextView) findViewById(R.id.login_text1);
		mLoginTextView2 = (TextView) findViewById(R.id.login_text2);
		mUsernameEditText = (EditText) findViewById(R.id.uesrname);
		mPasswordEditText = (EditText) findViewById(R.id.register_mima);
		mEmailEditText = (EditText) findViewById(R.id.e_mail_edittext);
		Typeface typeface = Typeface.createFromAsset(getAssets(),
				"fonts/GothamRounded-Medium.otf");
		mUsernameEditText.setTypeface(typeface);
		mPasswordEditText.setTypeface(typeface);
		mEmailEditText.setTypeface(typeface);
		mLoginTextView1.setTypeface(typeface);
		mLoginTextView2.setTypeface(typeface);
		mRegisterButton.setTypeface(typeface);
		/**
		 * �Ѿ����˺� ���ص�¼����
		 **/

		mLoginTextView2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(i);
			}
		});

		/******************************************** ע��ɹ���ʧ�ܼ�����ʾ ******************************************************/
		mRegisterButton.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				if (mUsernameEditText.getText().toString()
						.isEmpty()
						|| mEmailEditText.getText()
								.toString()
								.isEmpty()
						|| mPasswordEditText.getText()
								.toString()
								.isEmpty()) {
					// �ж��������Ϣ�Ƿ�Ϊ��
					Toast.makeText(getBaseContext(),
							"�뽫��Ϣ��д����",
							Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (mPasswordEditText.getText().toString()
						.length() < 6) {
					// �ж����������Ƿ�С��6λ
					Toast.makeText(getApplicationContext(),
							"��������Ϊ6-20λ�����ֻ�����ĸ",
							Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (!(mEmailEditText.getText().toString()
						.matches("(.*?)@(.*?)\\.com"))) {
					Toast.makeText(getApplicationContext(),
							"�����ʽ����",
							Toast.LENGTH_SHORT)
							.show();
					return;
				}

				// �ж��˺��Ƿ����
				List<String> user = new ArrayList<String>();
				user.add("register");
				user.add(mUsernameEditText.getText().toString());
				user.add(mEmailEditText.getText().toString());
				user.add(mPasswordEditText.getText().toString());

				String User = user.toString();
				Log.i("TAG", User);
				CommThread usermessage = new CommThread(User);
				usermessage.start();
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch
					// block
					e.printStackTrace();
				}
				if (usermessage.recMsg.equals("0")) {
					Toast.makeText(getApplicationContext(),
							"��ϲ����ע��ɹ�",
							Toast.LENGTH_SHORT)
							.show();
					/* sharedpreference */
					sharedPreferences = getSharedPreferences(
							"UserInfo",
							MODE_PRIVATE);
					Editor editor = sharedPreferences
							.edit();
					editor.putString(
							"username",
							mUsernameEditText
									.getText()
									.toString());
					editor.putString(
							"password",
							mPasswordEditText
									.getText()
									.toString());// ����Ϣ��ʾδ��½
					editor.commit();
					/* //sharedpreference */

					Intent i = new Intent(
							RegisterActivity.this,
							MainActivity.class);
					startActivity(i);
				}
				if (usermessage.recMsg.equals("1")) {
					Toast.makeText(getApplicationContext(),
							"�˺��Ѿ����ڣ���һ������",
							Toast.LENGTH_SHORT)
							.show();
				}
				if (usermessage.recMsg == "") {
					Toast.makeText(getApplicationContext(),
							"����������ʧ��",
							Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
	}
}

/******************************************** ע��ɹ���ʧ�ܼ�����ʾ ******************************************************/
