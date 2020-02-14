package tcpip1;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

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
						new Receiver(socket).start();
						System.out.println("Á¢¼Ó : " + socket.getInetAddress());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		};

		new Thread(r).start();

	}

	class Receiver extends Thread {

		Socket socket;
		InputStream is;
		DataInputStream dis;

		public Receiver() {

		}

		public Receiver(Socket socket) throws IOException {

			this.socket = socket;
			is = socket.getInputStream();
			dis = new DataInputStream(is);

		}

		@Override
		public void run() {

			while (dis != null) {
				try {
					String msg = dis.readUTF();
					System.out.println(msg);
					if (msg.equals("q")) {
						System.out.println("exit(q) : " + socket.getInetAddress());
						break;
					}
				} catch (IOException e) {
					System.out.println("exit : " + socket.getInetAddress());
					break;
				}
			}
			try {
				if (dis != null) {
					dis.close();
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
			server = new Server(8888);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
