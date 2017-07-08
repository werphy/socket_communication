package cilent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Socket_Cilent {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		 ExecutorService cachedThreadPool = Executors.newCachedThreadPool();  
		 cachedThreadPool.submit(new Runnable() {
			public void run() {
				proxyUtil proxyutil= new proxyUtil();
				try {
					Pay_Money pm= (Pay_Money) proxyutil.getProxyInstance(Pay_Money.class);
					 boolean b = pm.pay("werphy", "123456", 25);
					 if(b){
						 System.out.println("Ö§¸¶³É¹¦");	 
					 }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
	}
}
