package tcpip1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

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
			while(true)
			{
			System.out.println("Server Ready");
			socket = serversocket.accept();
			System.out.println("Connected...");
			// 3. Make Stream

				ir = new InputStreamReader(socket.getInputStream());
				br = new BufferedReader(ir);

				String msg = "";
				while ((msg = br.readLine()) != null) {
					System.out.println(msg);
				}
				
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (br != null) {
				br.close();
			}
			if (socket != null) {
				socket.close();
			}
		}
		//System.out.println("End Server");
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
