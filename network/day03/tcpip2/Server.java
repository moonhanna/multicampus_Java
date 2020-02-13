package tcpip2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

class down extends Thread {

	String fname;

	public down() {
		super();
	}

	public down(String fname) {
		super();
		this.fname = fname;
	}

	@Override
	public void run() {

		System.out.println("down thread");
		
		String urlstr = "url/test/file/";
		urlstr += fname;
		URL url = null;
		BufferedInputStream bi = null;
		BufferedOutputStream bo = null;
		try {
			url = new URL(urlstr);
			bi = new BufferedInputStream(url.openStream());
			bo = new BufferedOutputStream(new FileOutputStream(fname));
			int data = 0;
			while ((data = bi.read()) != -1) {
				bo.write(data);
				//System.out.println(fname + "...");
			}
			
			System.out.println("END");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

class mythread extends Thread {

	Socket socket;

	InputStreamReader ir;
	BufferedReader br;

	public mythread() {
		super();
	}

	public mythread(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			ir = new InputStreamReader(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(ir);

		String msg = "";
		char b;
		try {
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
				b = msg.charAt(msg.length()-4);
				//System.out.println(b);
				if (b == '.') {
					//System.out.println(b);
					down th2 = new down(msg);
					th2.start();
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}

public class Server {

	int port = 9999;
	ServerSocket serversocket;
	Socket socket;

	InputStreamReader ir;
	BufferedReader br;

	public Server() {

	}

	public void startServer() throws Exception {

		System.out.println("Start Server");

		try {
			// 1. ServerSocket
			serversocket = new ServerSocket(port);
			// 2. Socket, ready
			while (true) {
				System.out.println("Server Ready");
				socket = serversocket.accept();
				System.out.println("Connected...");
				// 3. Make Stream
				mythread th = new mythread(socket);
				th.start();
			}

		} catch (Exception e) {
			throw e;
		}
		// System.out.println("End Server");
	}

	public static void main(String[] args) {

		Server server = new Server();
		try {
			server.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
