package tcpip2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

	Socket socket;
	Sender sender;
	
	public Client() {}
	public Client(String address,int port) throws IOException {
		try {
			socket = new Socket(address, port);
		
		}catch(Exception e) {
			while(true) {
				System.out.println("Retry..");
				try {
					Thread.sleep(1000);
					socket = new Socket(address, port);
					break;
				} catch (Exception e1) {
					//e1.printStackTrace();
				}
			}
		}
		
		System.out.println("Connected Server:"+address);
		
		sender = new Sender(socket);
		
		Msg msg = new Msg("Moon", null, null);
		sender.setMsg(msg);
		new Thread(sender).start();
		
		new Receiver(socket).start();
	}
	
	class Receiver extends Thread{
		InputStream is;
		ObjectInputStream ois;
		
		public Receiver(Socket socket) throws IOException {
			is = socket.getInputStream();
			ois = new ObjectInputStream(is);
		}

		@Override
		public void run() {
			while(ois != null) {
				Msg msg = null;
				try {
					msg = (Msg) ois.readObject();
					
					if(msg.getIps() == null || msg.getIps().size() == 0) {
						System.out.println(
								msg.getId()+":"+msg.getTxt()	
									);
					}else {
						ArrayList<String> list
						= msg.getIps();
						System.out.println(list);
					}
					
					
				}catch(Exception e) {
					System.out.println("Server Die");
					break;
				}
			}
			
			try {
				if(ois != null) {
					ois.close();
				}
				if(socket != null) {
					socket.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	class Sender implements Runnable{

		OutputStream os;
		ObjectOutputStream oos;
		Msg msg;
		
		public Sender(Socket socket) throws IOException {
			os = socket.getOutputStream();
			oos = new ObjectOutputStream(os);
		}
		public void setMsg(Msg msg) {
			this.msg = msg;
		}
		@Override
		public void run() {
			if(oos != null) {
				try {
					oos.writeObject(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	public void startClient() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Input TID & TXT");
			String tid = sc.nextLine();
			String txt = sc.nextLine();
			
			Msg msg = null;
			if(tid == null || tid.equals("")) {
				msg = new Msg("Moon",txt,null); 
			}else {
				msg = new Msg("Moon",txt,tid); 
			}
			
			sender.setMsg(msg);
			new Thread(sender).start();
			
			if(txt.equals("q")) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("End Client.");
		sc.close();
	}
	
	
	public static void main(String[] args) {
		Client client = null;
		try {
			client = new Client("ip", 8888);
		} catch (IOException e) {
			e.printStackTrace();
		}
		client.startClient();
	}

}





