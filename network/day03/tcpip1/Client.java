package tcpip1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	int port = 9999;
	String ip = "ip";

	Socket socket;
	OutputStreamWriter osw;
	BufferedWriter bw;

	public Client() {

	}

	public void connect() throws InterruptedException {

		try {
			socket = new Socket(ip, port);
			System.out.println("Connected...");
		} catch (Exception e) {
			while (true) {
				Thread.sleep(2000);
				try {
					socket = new Socket(ip, port);
					System.out.println("Connected...");
					break;
				} catch (Exception e1) {
					System.out.println("Reconnected...");
				}
			}
		}

	}

	public void request() throws IOException {

		Scanner sc = new Scanner(System.in);

		try {
			osw = new OutputStreamWriter(socket.getOutputStream());
			bw = new BufferedWriter(osw);
			System.out.println("ют╥б : ");
			String send = sc.nextLine();
			bw.write(send);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				bw.close();
			}
			if (socket != null) {
				socket.close();
			}
		}

	}

	public static void main(String[] args) {

		Client client = new Client();
		try {
			client.connect();
			client.request();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
