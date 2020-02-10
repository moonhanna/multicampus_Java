package day01;

class MyThread extends Thread{
	
	String name;
		
	public MyThread() {
		
	}

	public MyThread(String name) {
		this.name = name;
	}

	
	@Override
	public void run() { //돌아가는 영역
		for(int i = 1; i<= 100; i++)
		{
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			//yield();
			System.out.println(name + " : " + i);
		}
	}
	
}

public class T1 {

	public static void main(String[] args) {

		Thread t1 = new MyThread("T1");
		Thread t2 = new MyThread("T2");
		t1.setPriority(1);
		t2.setPriority(10); //우선순위, 1~10까지. 숫자클수록 우선순위 높음
		t1.start();
		t2.start();
		
	}

}
