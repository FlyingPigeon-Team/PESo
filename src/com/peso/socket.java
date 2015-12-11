package com.peso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class socket {
	private static final int PORT = 6000;
	private String ip = "192.168.1.105";
	private static BufferedWriter writer;
	private InetSocketAddress isa = null;

	// ���ӷ�����
	public Socket connecttoserver() throws UnknownHostException,
			IOException {

		return RequestSocket(ip, PORT);
	}

	private Socket RequestSocket(String host, int port)
			throws UnknownHostException, IOException {

		Socket consocket = new Socket();
		// �����׽��ֵ�ַ������ IP ��ַΪͨ�����ַ���˿ں�Ϊָ��ֵ��
		// ��Ч�˿�ֵ���� 0 �� 65535 ֮�䡣�˿ں� zero ����ϵͳ�� bind ��������ѡ��ʱ�Ķ˿ڡ�
		isa = new InetSocketAddress(host, port);
		// ����������
		consocket.connect(isa);
		// ����һ��Զ������
		if (!consocket.isConnected()) {
			Log.e("error", "����������ʧ��");
		} else {
			Log.i("Tag", "���������ӳɹ�");
		}
		return consocket;
	}

	// �������������Ϣ
	public void SendMsg(Socket Socket, String msg) throws IOException {
		writer = new BufferedWriter(new OutputStreamWriter(
				Socket.getOutputStream()));
		Log.i("msg", msg.replace("\n", "") + "/n");//
		writer.write(msg.replace("\n", " ") + "\n");
		Log.i("writer", writer.toString());
		writer.flush();
	}

	public void CloseSocket(Socket socket) throws IOException {
		writer.close();
		socket.close();
		System.out.println("�ͻ���һ�߳��ѹر�..");
	}

	// ���ܷ�������Ϣ
	public String ReceiveMsg(Socket Socket) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(Socket.getInputStream()));
		String line;
		String txt = "";
		while ((line = reader.readLine()) != null) {
			return line;
		}
		Log.i("Tag", "���յ�����Ϣ" + txt);
		return txt;
	}
}
