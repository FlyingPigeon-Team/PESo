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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.peso.MaininterfaceActivity;
import com.peso.R;

@SuppressLint("NewApi")
public class LoginActivity extends Activity {
	private TextView register1;
	private TextView register2;
	private TextView title;
	private Button login;
	private EditText userName;
	private EditText passWord;
	private CheckBox checkBox;
	SharedPreferences sharedPreferences;
	TextView user_text;
	List<String> user = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		/*-------------��id---------------*/
		// title = (TextView) findViewById(R.id.login_title);
		register1 = (TextView) findViewById(R.id.registr_text1);
		register2 = (TextView) findViewById(R.id.registr_text2);
		login = (Button) findViewById(R.id.login_button);
		login.getBackground().setAlpha(100);// 0~255͸����ֵ
		userName = (EditText) findViewById(R.id.login_username);
		passWord = (EditText) findViewById(R.id.login_password);
		user_text = (EditText) findViewById(R.id.user_text);
		/*-------------��id---------------*/
		Typeface typeface = Typeface.createFromAsset(getAssets(),
				"fonts/GothamRounded-Medium.otf");
		// title.setTypeface(typeface);
		userName.setTypeface(typeface);
		passWord.setTypeface(typeface);
		register1.setTypeface(typeface);
		register2.setTypeface(typeface);
		login.setTypeface(typeface);

		/*-----------------ע������¼�-----------------*/
		register2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});
		/*-----------------ע������¼�-----------------*/

		/*-----------------�ж��û����������Ƿ���ȷ����-----------------*/
		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (userName.getText().toString().isEmpty()
						|| passWord.getText()
								.toString()
								.isEmpty()) {
					Toast.makeText(LoginActivity.this,
							"�뽫��Ϣ��д����",
							Toast.LENGTH_SHORT)
							.show();
				}
				user = new ArrayList<String>();
				user.add("login");
				user.add(userName.getText().toString());
				user.add(passWord.getText().toString());

				sharedPreferences = getSharedPreferences(
						"UserInfo", MODE_PRIVATE);
				String User = user.toString();
				Log.i("TAG", User);
				CommThread usermessage = new CommThread(User);
				usermessage.start();
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (usermessage.recMsg.equals("1")) {
					Toast.makeText(getApplicationContext(),
							"��½�ɹ�",
							Toast.LENGTH_SHORT)
							.show();
					Editor editor = sharedPreferences
							.edit();
					editor.putString("username", userName
							.getText().toString());
					editor.putString("password", passWord
							.getText().toString());
					editor.commit();
					Intent i = new Intent(
							LoginActivity.this,
							MaininterfaceActivity.class);
					startActivity(i);

				}
				if (usermessage.recMsg.equals("0")) {
					Toast.makeText(getApplicationContext(),
							"�˺����벻ƥ��",
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
		/*-----------------�ж��û����������Ƿ���ȷ����-----------------*/
	}

	public List<String> getUserinfo() {
		return user;
	}
}
