package cilent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

public  class proxyUtil {
	/**
	 * 创建代理对象
	 * @param interfaceClass 
	 * @param implClass 传入需要实例化的对象在invoke方法调用时产生对象 并调用相应方法的invoke
	 * @throws Exception
	 */
	Class<?> interfaceClass=null;
	public  Object getProxyInstance(final Class<?> interfaceClass)throws Exception {
		this.interfaceClass=interfaceClass;
		return Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class[] { interfaceClass }, new InvocationHandler() {
			/**
			 * 当调用方法时会调用InvocationHandler中的invoke方法，然后可以对方法进行判断拦截。
			 */
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {		
				String className = interfaceClass.getName().substring(interfaceClass.getName().lastIndexOf('.')+1);
				String methodName = method.getName();
				SocketMessage sm = new SocketMessage(); 
				sm.setClassName(className);
				sm.setMethodName(methodName);
				sm.setArgs(args);
				Socket socket = new Socket("127.0.0.1", 12345);
				ObjectOutputStream os =new ObjectOutputStream(socket.getOutputStream());
				os.writeObject(sm);
				os.flush();
				InputStream is=socket.getInputStream();  
				BufferedReader br=new BufferedReader(new InputStreamReader(is));  
				String answer = null;
				while((answer=br.readLine())!=null)
				{
					System.out.println("服务器返回结果为："+answer); 
					if(answer.equals("success")){
						os.close();
						br.close();
						socket.close();
						return true;	
					}
				}
				os.close();
				br.close();
				socket.close();
				return false;
			}
		});

	}
}
