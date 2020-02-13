package down;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Scanner;

class DownThread implements Runnable{

	String fname;
	String status;
	
	public DownThread(String fname) {
		this.fname = fname;
	}
	public String getStatus() {
		return status;
	}
	@Override
	public void run() {
		String urlstr = 
		"url/test/file/";
		urlstr += fname;
		URL url = null;
		BufferedInputStream bi = null;
		BufferedOutputStream bo = null;
		try {
			url = new URL(urlstr);
			bi = new BufferedInputStream(
					url.openStream());
			bo = new BufferedOutputStream(
					new FileOutputStream(fname)
					);
			int data = 0;
			while((data = bi.read()) != -1) {
				bo.write(data);
				System.out.println(fname+"...");
			}
			status = "End ...";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

class control extends Thread{
	
	String fname;
	
	public control() {
		super();
	}

	public control(String fname) {
		this.fname = fname;
	}

	@Override
	public void run() {

		while(true) {

			Thread t1 = new Thread(new DownThread(fname));

			t1.start();
			
		}

		
	}
	
}

public class Down {

	public static void main(String[] args) throws InterruptedException {
		
		Scanner sc = new Scanner(System.in);

		while(true) {
			System.out.println("Input File Name ..");
			String fname = sc.nextLine();
			if(fname.equals("q")) {
				break;
			}
			new control(fname).start();
			
		}
		System.out.println("End");
		sc.close();

	}

}





