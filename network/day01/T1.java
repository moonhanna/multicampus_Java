package day01;

class MyThread extends Thread{
	
	String name;
		
	public MyThread() {
		
	}

	public MyThread(String name) {
		this.name = name;
	}

	
	@Override
	public void run() { //���ư��� ����
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
		t2.setPriority(10); //�켱����, 1~10����. ����Ŭ���� �켱���� ����
		t1.start();
		t2.start();
		
	}

}
