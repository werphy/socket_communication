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
					System.out.println("线程启动");
					ObjectInputStream ois =new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
					System.out.println("获取到输入流");
					SocketMessage sm = (SocketMessage) ois.readObject();
					System.out.println("类名："+sm.getClassName() + "，方法名" + sm.getMethodName());
					for (Object string : sm.getArgs()) {
						System.out.println("参数："+string);
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
