package com.peso;

/**
 * ����ڣ�����app��ӭ���棬��ʱ���ܺ��жϹ���
 */

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.peso.model.WelcomeActivity;

public class MainActivity extends Activity {
	private SharedPreferences sharepreferences;// ʵ���� SharedPreferences
	private SharedPreferences.Editor editor;

	@SuppressLint("CommitPrefEdits")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sharepreferences = this.getSharedPreferences("check",
				MODE_PRIVATE);// ��ʼ��
						// SharedPreferences
						// ����
		editor = sharepreferences.edit();// ��SharedPreferences ���� �ɱ༭��

		final Intent it = new Intent(this, WelcomeActivity.class); // ��ʼ����תwelcomeActivity
		final Intent ittomain = new Intent(this,
				MaininterfaceActivity.class);// ��ʼ����תMaininterfaceActivity
		Timer timer = new Timer(); // ����һ����ʱ��ʵ��
		firstloadjudge(timer, it, ittomain); // ���� �жϵ�һ��app�����ķ���

	}

	public void firstloadjudge(Timer timer, Intent it, Intent ittomain) {
		boolean fristload = sharepreferences.getBoolean("fristload",
				true);// ��SharedPreferences�л�ȡ�Ƿ��һ������
		if (fristload) {
			delaytowel(timer, it);// ��һ�ν����ӳ�2������WelcomeActivity
			editor.putBoolean("fristload", false);// ��һ�������󣬽�firstload
								// ��Ϊfalse
								// �Ա��Ժ�ֱ�ӽ��������治����ʾ��ӭ����
			editor.commit(); // �ύ��ִ�в���
		} else {
			// Toast.makeText(MainActivity.this,
			// "�㲻�ǵ�һ�ν��룬Ӧ����app������",
			// Toast.LENGTH_SHORT).show();
			delaytomain(timer, ittomain); // ��ʱ���ҽ���������
		}

	}

	public void delaytowel(Timer timer, final Intent it)// ��ʱ������welcome�ķ���
	{
		TimerTask task = new TimerTask() {
			@Override
			public void run() {

				startActivity(it); // ִ�н���
			}
		};
		timer.schedule(task, 1000 * 1);// 1���ͽ���welcome
	}

	public void delaytomain(Timer timer, final Intent ittomain)// ��ʱ������������ķ���
	{
		TimerTask task = new TimerTask() {
			@Override
			public void run() {

				startActivity(ittomain); // ִ�н���
			}
		};
		timer.schedule(task, 1000 * 1);// 1���ͽ���main
	}

}
