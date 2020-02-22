package tcpip2;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Server {

	HashMap<String, ObjectOutputStream>
	maps = new HashMap<>();
	HashMap<String, String>
	ids = new HashMap<>();
	
	ServerSocket serverSocket;
	boolean aflag = true;
	
	public Server() {}
	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println("Start Server");
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while(aflag) {
					Socket socket = null;
					try {
						System.out.println("Server Ready..");
						socket = 
								serverSocket.accept();
						new Receiver(socket).start();
						System.out.println(socket.getInetAddress());
					} catch (IOException e) {
						break;
						//e.printStackTrace();
					}
				}
			}
		};
		if(serverSocket != null) {
			new Thread(r).start();
		}
		
	}
	public void sendId() {
		Collection<String> 
		id = ids.values();
		Iterator<String> it = id.iterator();
		ArrayList<String> list = new ArrayList<>();
		while(it.hasNext()) {
			list.add(it.next());
		}
	}
	public void sendIp() {
		Set<String> 
		keys = maps.keySet();
		Iterator<String>
		its = keys.iterator();
		ArrayList<String> list = new ArrayList<>();
		while(its.hasNext()) {
			list.add(its.next());
		}
	}
	
	class Receiver extends Thread{
		
		InputStream is;
		ObjectInputStream ois;
		
		OutputStream os;
		ObjectOutputStream oos;
		
		Socket socket;
		public Receiver(Socket socket) throws IOException {
			this.socket = socket;
			is = socket.getInputStream();
			ois = new ObjectInputStream(is);
			
			os = socket.getOutputStream();
			oos = new ObjectOutputStream(os);
			maps.put(socket.getInetAddress().toString(), 
					oos);
			try {
				Msg msg = (Msg) ois.readObject();
				ids.put(socket.getInetAddress().toString(),
						msg.getId());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while(ois != null) {
				Msg msg = null;
				try {
					
					msg = (Msg) ois.readObject();
					System.out.println(
						msg.getId()+":"+msg.getTxt());
					if(msg.getTxt().equals("q")) {
						System.out.println(
								ids.get(socket.getInetAddress().toString())+":Exit ..");
						
						maps.remove(
								socket.getInetAddress().toString()
								);
					
						ids.remove(socket.getInetAddress().toString()
								);
					
						System.out.println("�����ڼ�:"+maps.size());
						break;
					}
					sendMsg(msg);
				} catch (Exception e) {
					maps.remove(
							socket.getInetAddress().toString()
							);
					System.out.println(
							ids.get(socket.getInetAddress().toString())+":Exit ..");
					
					ids.remove(socket.getInetAddress().toString()
							);
					System.out.println("�����ڼ�:"+maps.size());
  				    break;
				}	
			} // end while
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
	
	class Sender extends Thread{
		Msg msg;
		public Sender(Msg msg) {
			this.msg = msg;
		}
		@Override
		public void run() {

			Collection<ObjectOutputStream> 
			cols = maps.values();
			Iterator<ObjectOutputStream>
			its = cols.iterator();
			while(its.hasNext()) {
				try {
					its.next().writeObject(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	class Sender2 extends Thread{
		Msg msg;
		public Sender2(Msg msg) {
			this.msg = msg;
		}
		@Override
		public void run() {
			String tid = msg.getTid();
			try {
				Collection<String> 
				col = ids.keySet();
				Iterator<String> it = col.iterator();
				String sip = "";
				while(it.hasNext()) {
					String key = it.next();
					if(ids.get(key).equals(tid)) {
						sip = key;
					}
				}
				System.out.println(sip);
				maps.get(sip).writeObject(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void sendMsg(Msg msg) {
		String tid = msg.getTid();
		
		if(tid == null || tid.equals("")) {
			Sender sender = 
					new Sender(msg);
			sender.start();
		}else {
			Sender2 sender2 = 
					new Sender2(msg);
			sender2.start();
		}
		
	} // end sendMsg
	
	
	public static void main(String[] args) {
		Server server = null;
		try {
			server = new Server(8888);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}







