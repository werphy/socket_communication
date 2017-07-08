package service;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cilent.SocketMessage;

public class Socket_Service {
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(12345);
		 ExecutorService cachedThreadPool = Executors.newCachedThreadPool();  
		while(true){
		Socket s =ss.accept();
		cachedThreadPool.submit(new Runnable() {
			public void run() {
				Socket socket = s; 
				try {
					System.out.println("�߳�����");
					ObjectInputStream ois =new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
					System.out.println("��ȡ��������");
					SocketMessage sm = (SocketMessage) ois.readObject();
					System.out.println("������"+sm.getClassName() + "��������" + sm.getMethodName());
					for (Object string : sm.getArgs()) {
						System.out.println("������"+string);
					}
					BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					bw.flush();
					bw.write("success");
					bw.flush();
					ois.close();
					bw.close();
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		}
	}
}
