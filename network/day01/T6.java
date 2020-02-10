package day01;

import java.util.Scanner;

class Calc extends Thread{
	
	int cnt;
	int sum;
	
	public Calc()
	{
		
	}
	
	public Calc(int cnt) {
		this.cnt = cnt;
	}

	@Override
	public void run() {
		//1부터 cnt까지의 합
		//Thread.sleep(20);
		for(int i = 1; i <= cnt; i++)
		{
			sum = sum+i;
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}

public class T6 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		int input = s.nextInt();
		Calc c = new Calc(input);
		c.start();
		//sum값을 출력하시오
		try {
			c.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("sum : " + c.sum);
		s.close();
	
	}

}
