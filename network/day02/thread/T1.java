package thread;

import java.util.Scanner;

class Th1 extends Thread{

	boolean stop;
	boolean sus;
	
	public void setStop(boolean b) {
		stop = b;
	}
	
	public void setSus(boolean b)
	{
		sus = b;
	}
	
	@Override
	public void run() {

		while(true)
		{
			if(stop == true)
			{
				System.out.println("release...");
				break;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			if(!sus) {
			System.out.println("Th1...");
			}
		}
		System.out.println("Th1 End...");
	}	
	
}

public class T1 {

	public static void main(String[] args) throws InterruptedException {

		Th1 th1 = new Th1();
		th1.start();
		System.out.println("ing...");
		Scanner sc = new Scanner(System.in);
		while(true)
		{
			System.out.println("Input cmd...");
			String cmd = sc.nextLine();
			if(cmd.equals("s")) { //stop
				th1.setStop(true);
			}
			else if(cmd.equals("b")){ //break
				th1.setSus(true);
			}
			else if(cmd.equals("r")){ //restart
				th1.setSus(false);
			}
			else if(cmd.equals("q")){ //quit
				break;
			}
		}
		sc.close();
	}
	
}
