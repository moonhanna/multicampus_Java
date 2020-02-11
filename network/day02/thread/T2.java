package thread;

import java.util.Scanner;

class SendThread implements Runnable{

	String str;
	
	public SendThread() {
		super();
	}

	public SendThread(String str) {
		super();
		this.str = str;
	}

	@Override
	public void run() {
		for(int i = 1; i < 50; i++)
		{
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print(str);
		}
	}

}

public class T2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		while(true)
		{
			System.out.println("input cmd..");
			String cmd = sc.nextLine();
			if(cmd.equals("q"))
			{
				break;
			}
			else
			{
				new Thread(new SendThread(cmd)).start();
			}
		}
		System.out.println("Stoped Server...");
		sc.close();
	}

}
