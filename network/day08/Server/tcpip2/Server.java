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
import java.util.Scanner;

public class Server {

	HashMap<String, ObjectOutputStream> maps = new HashMap();
	ArrayList<String> iplist = new ArrayList<String>();

	int port;
	ServerSocket serverSocket;
	boolean aflag = true;

	public Server() {

	}

	public Server(int port) throws IOException {

		serverSocket = new ServerSocket(port);
		System.out.println("Start Server");

		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (aflag) {
					Socket socket = null;
					try {
						System.out.println("Server Ready");
						socket = serverSocket.accept();
						//new Receiver(socket).start();
						makeOut(socket);
						System.out.println("접속 : " + socket.getInetAddress());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		};

		new Thread(r).start();

	}
	
	public void makeOut(Socket socket) throws IOException {
		
		OutputStream os;
		ObjectOutputStream oos;
		
		os = socket.getOutputStream();
		oos = new ObjectOutputStream(os);
		
		maps.put(socket.getInetAddress().toString(),oos);
		System.out.println("접속자 수 : "+maps.size());
		
		
	}

	class SendIP extends Thread {

		public SendIP() {

		}

		@Override
		public void run() {

			Collection<ObjectOutputStream> cols = maps.values();
			Iterator<ObjectOutputStream> its = cols.iterator();

			while (its.hasNext()) {
				try {

					for (int i = 0; i < iplist.size(); i++) {
						its.next().writeObject(iplist.get(i));
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	class Sender extends Thread {

		Msg msg;

		public Sender() {
			super();
		}

		public Sender(Msg msg) {
			super();
			this.msg = msg;
		}

		@Override
		public void run() {

			// HashMap에 있는 oos를 꺼낸 다음
			// for문을 돌리면서 전송한다.
			Collection<ObjectOutputStream> cols = maps.values();
			Iterator<ObjectOutputStream> its = cols.iterator();

			while (its.hasNext()) {
				try {

					its.next().writeObject(msg);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	// 귓속말
	class Sender2 extends Thread {

		Msg msg;

		public Sender2() {
			super();
		}

		public Sender2(Msg msg) {
			super();
			this.msg = msg;
		}

		@Override
		public void run() {

			String ip = msg.getTid();
			try {
				maps.get(ip).writeObject(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public void sendMsg(Msg msg) {

		String ip = msg.getTid();

		if (ip == null || ip.equals("")) {
			Sender sender = new Sender(msg);
			sender.start();
		} else {
			Sender2 sender2 = new Sender2(msg);
			sender2.start();
		}

	}

	public void serverStart() {

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Input Msg & IP : ");
			String ip = sc.nextLine();
			String txt = sc.nextLine();
			if (txt.equals("q")) {
				break;
			}
			Msg msg = null;
			if (ip == null || ip.equals("")) {
				msg = new Msg("Admin", txt, null);
			} else {
				msg = new Msg("Admin", txt, ip.trim());
			}
			sendMsg(msg);
		}
		sc.close();

	}

	class Receiver extends Thread {

		Socket socket;
		InputStream is;
		ObjectInputStream ois;

		OutputStream os;
		ObjectOutputStream oos;

		public Receiver() {

		}

		public Receiver(Socket socket) throws IOException {

			this.socket = socket;
			is = socket.getInputStream();
			ois = new ObjectInputStream(is);

			os = socket.getOutputStream();
			oos = new ObjectOutputStream(os);
			maps.put(socket.getInputStream().toString(), oos);
			iplist.add(socket.getInetAddress().toString());

			System.out.println("접속자 수 : " + maps.size());

		}

		@Override
		public void run() {

			while (ois != null) {
				Msg msg = null;
				try {
					msg = (Msg) ois.readObject();
					System.out.println(msg.getId() + ":" + msg.getTxt());
					if (msg.getTxt().equals("q")) {
						System.out.println("exit(정상종료) : " + msg.getId());
						maps.remove(socket.getInetAddress().toString());
						System.out.println("접속자 수 : " + maps.size());
						break;
					}
					sendMsg(msg);

				} catch (Exception e) {
					System.out.println("exit(비정상종료) : " + socket.getInetAddress());
					maps.remove(socket.getInetAddress().toString());
					System.out.println("접속자 수 : " + maps.size());
					break;
				}
			}
			try {
				if (ois != null) {
					ois.close();
				}
				if (socket != null) {
					socket.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {

		Server server = null;

		try {
			server = new Server(7878);
			server.serverStart();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
