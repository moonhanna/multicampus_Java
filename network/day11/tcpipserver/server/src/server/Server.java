package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import msg.Msg;

public class Server {

	HashMap<String, ObjectOutputStream> maps = new HashMap<>();

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
						System.out.println("Server Ready..");
						socket = serverSocket.accept();
						System.out.println("Client Ready..");
						System.out.println(socket.getInetAddress());
						makeOut(socket);
						System.out.println("makeOut");
						new Receiver(socket).start();
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
		maps.put(socket.getInetAddress().toString(), oos);
		System.out.println("접속자수:" + maps.size());

	}

	class Receiver extends Thread {

		InputStream is;
		ObjectInputStream ois;

		Socket socket;

		public Receiver(Socket socket) {

			System.out.println("hi Receiver");

			this.socket = socket;

			try {
				is = socket.getInputStream();
				ois = new ObjectInputStream(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("접속자수:" + maps.size());
		}

		@Override
		public void run() {
			Msg msg = null;
			while (ois != null) {
				try {
					msg = (Msg) ois.readObject();
					System.out.println(msg.getId() + ":" + msg.getTxt());
					if (msg.getTxt().equals("q")) {
						System.out.println(msg.getId() + ":Exit ..");
						maps.remove(socket.getInetAddress().toString());
						System.out.println("접속자수:" + maps.size());
						break;
					}
					sendMsg(msg);
				} catch (Exception e) {
					maps.remove(socket.getInetAddress().toString());
					System.out.println(socket.getInetAddress() + ":Exit ..");
					System.out.println("접속자수:" + maps.size());
					break;
				}
			}
			// sendMsg(msg);
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

	class Sender extends Thread {
		Msg msg;

		public Sender(Msg msg) {
			this.msg = msg;
		}

		@Override
		public void run() {
			// HashMap에 있는 oos를 꺼낸다음
			// for문을 돌리면서 전송 한다.
			Collection<ObjectOutputStream> cols = maps.values();
			Iterator<ObjectOutputStream> its = cols.iterator();
			while (its.hasNext()) {
				try {
					its.next().writeObject(msg);
					System.out.println("sender : " + msg.getTxt());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	class Sender2 extends Thread {
		Msg msg;

		public Sender2(Msg msg) {
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
		System.out.println("hihi");
		String ip = msg.getTid();

		Sender sender = new Sender(msg);
		sender.start();

	}

	public static void main(String[] args) {
		Server server = null;
		try {
			server = new Server(7777);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
