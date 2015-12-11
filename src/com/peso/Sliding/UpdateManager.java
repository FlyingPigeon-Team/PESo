package com.peso.Sliding;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.peso.R;
 
public class UpdateManager {
 
    private Context mContext;
     
    //��ʾ��
    private String updateMsg = "PESO�����µ������Ŷ���׿����ذ�~";
     
    //���صİ�װ��url
    private String apkUrl = "http://apkegg.mumayi.com/cooperation/2015/11/20/0/9/wodezhuomianMyLauncher_V1.3.3_mumayi_61cb3.apk";
     
     
    private Dialog noticeDialog;
     
    private Dialog downloadDialog;
     /* ���ذ���װ·�� */
    private static final String savePath = "/sdcard/updatedemo/";
     
    private static final String saveFileName = savePath + "UpdateDemoRelease.apk";
 
    /* ��������֪ͨuiˢ�µ�handler��msg���� */
    private ProgressBar mProgress;
 
     
    private static final int DOWN_UPDATE = 1;
     
    private static final int DOWN_OVER = 2;
     
    private int progress=0;
     
    private Thread downLoadThread;
     
    private boolean interceptFlag = false;
     
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case DOWN_UPDATE:
                mProgress.setProgress(progress);
                break;
            case DOWN_OVER:
                 
                installApk();
                break;
            default:
                break;
            }
        };
    };

	protected Handler handler;
     
    public UpdateManager(Context context) {
        this.mContext = context;
    }
     
    //�ⲿ�ӿ�����Activity����
    public void checkUpdateInfo(){
        showNoticeDialog();
    }
     
     
    private void showNoticeDialog(){
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("����汾����");
        builder.setMessage(updateMsg);
        builder.setPositiveButton("����", new OnClickListener() {        
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();          
            }
        });
        builder.setNegativeButton("�Ժ���˵", new OnClickListener() {          
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();              
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }
     
    private void showDownloadDialog(){
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("����汾����");
         
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.progres, null);
        mProgress = (ProgressBar)v.findViewById(R.id.apkdownloadprogress);
         
        builder.setView(v);
        builder.setNegativeButton("ȡ��", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;
            }
        });
        downloadDialog = builder.create();
        downloadDialog.show();
         
        downloadApk();
    }
     
    private Runnable mdownApkRunnable = new Runnable() {   
     

		@Override
        public void run() {
            try {
                URL url = new URL(apkUrl);
             
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();
                System.out.println("���ص�ַ���Ӻ���");
                File file = new File(savePath);
                if(!file.exists()){
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);
                 
                int count = 0;
                byte buf[] = new byte[1024];
                 
                do{                
                    int numread = is.read(buf);
                    count += numread;
                    progress =(int)(((float)count / length) * 100);
                    //���½���
                    System.out.println("����do");
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if(numread <= 0){   
                        //�������֪ͨ��װ
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf,0,numread);
                }while(!interceptFlag);//���ȡ����ֹͣ����.
                 
                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }
             
        }
    };
          /*      HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
                conn.connect();  
                int length = conn.getContentLength();  
                InputStream is = conn.getInputStream();  
                File file = new File(apkUrl);  
                System.out.println("���������ҵ���");
                if(!file.exists()){  
                    //����ļ��в�����,�򴴽�  
                    file.mkdir();  
                }  
                //���ط��������°汾�����д�ļ���  
                String apkFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/updateApkFile/" + "peso";  
                File ApkFile = new File(savePath);  
                System.out.println("file apkfile����");
                FileOutputStream fos = new FileOutputStream(ApkFile);  
                System.out.println("fos����");
                int count = 0;  
                byte buf[] = new byte[1024];  
                System.out.println("while֮ǰ");
				do{
                    int numRead = is.read(buf);  
                    count += numRead;  
                    System.out.println("���½��ȵ���");
                    //���½�����  
                    progress = (int) (((float) count / length) * 100);  
                    handler.sendEmptyMessage(0);  
                    if(numRead <= 0){  
                        //�������֪ͨ��װ  
                        handler.sendEmptyMessage(1);  
                        System.out.println("�������");
                        break;  
                    }  
                    fos.write(buf,0,numRead);  
                    //�����ȡ��ʱ����ֹͣ����  
                } while(!interceptFlag);
				}
             catch (MalformedURLException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    
};  */
     
     /**
     * ����apk
     * @param url
     */
     
    private void downloadApk(){
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
        System.out.println("downloadApk �ҵ���������");
    }
     /**
     * ��װapk
     * @param url
     */
    private void installApk(){
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }   
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
     
    }
}