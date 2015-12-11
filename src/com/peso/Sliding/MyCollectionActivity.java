package com.peso.Sliding;

import java.util.List;

import Thread.CommThread;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.peso.R;

import dbmodels.Publication;

public class MyCollectionActivity extends Activity implements OnClickListener {
	private ImageView M_return;
	private ListView listView;
	private Publication publication = new Publication();
	private Publication publication1 = new Publication();
	private MyCollActivityAdapter mcAdapter;
	private boolean firstRun = true;
	private int cycle_count;
	private List<Publication> pubList = null;
	private String userName = "";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycollection);
		M_return = (ImageView) findViewById(R.id.mc_return);
		listView = (ListView) findViewById(R.id.mycoll_listview);
		M_return.setOnClickListener(this);

		// publication.setTitle("�������");
		// publication.setAuthorStr("��������");
		// pubList.add(publication);

		SharedPreferences spPreferences = getSharedPreferences(
				"UserInfo", MODE_PRIVATE);
		userName = spPreferences.getString("username",
				"passager_null_name");

		GetListFromServer();
		// System.out.println("�յ�������msg"+Msg);
	}

	/**
	 * ˢ��listView�б� �ӷ���������publist
	 */
	private void GetListFromServer() {
		// TODO Auto-generated method stub
		final CommThread favs = new CommThread("FAVSUSERNAME"
				+ userName);
		favs.start();
		// �����Ѿ���ʼ��ʱ�ȴ�
		cycle_count=0;
		final Handler handler = new Handler();
		handler.postDelayed(new Thread() {// �첽�������ݣ���ʱ��ʾ
					public void run() {
						cycle_count++;
						if (cycle_count < 5) {
							handler.postDelayed(
									this,
									1000);
						} else {
							Toast.makeText(MyCollectionActivity.this,
									"���粻̫��",
									Toast.LENGTH_SHORT)
									.show();
							handler.removeCallbacks(this);//ֹͣ�
						}
						
						//���¿϶���ִ�е�
						if (firstRun) {
							pubList = favs.publist;
							if (!pubList.isEmpty()) {
								mcAdapter = new MyCollActivityAdapter(
										MyCollectionActivity.this,
										pubList);
								listView.setAdapter(mcAdapter);
								handler.removeCallbacks(this);
								firstRun = false;
							} 
							if(cycle_count>=8)
							{
								
								Toast.makeText(MyCollectionActivity.this,
										"�ղ��б�Ϊ��",
										Toast.LENGTH_SHORT)
										.show();
								handler.removeCallbacks(this);
								firstRun = false;
							}
						} else {
							mcAdapter.notifyDataSetChanged();
						}

					}
				}, 1000);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mc_return:
				finish();
				overridePendingTransition(R.anim.push_left_inn,
						R.anim.push_left_outt);
				break;
			default:
				break;
		}

	}
}