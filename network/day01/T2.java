package day01;

class MyT implements Runnable{
	
	String name;

	public MyT() {
		
	}

	public MyT(String name) {
		this.name = name;
	}

	@Override
	public void run() {

		for(int i = 1; i <= 10; i++)
		{
			System.out.println(name + " : " + i);
		}
		
	}
	
}

public class T2 {

	public static void main(String[] args) {

		//�������̽��̱� ������ ��ü����x, ������ ������ ��ü �ȿ��� ����
		//Thread t1 = new Thread(new MyT("T1"));
		//t1.start();
		
		Runnable r = new MyT("T1");
		Thread t1 = new Thread(r); 
		t1.start();
		
		//�͸�Ŭ�����ε� ����������, �ڹٿ����� ���� �Ⱦ�. �ȵ���̵忡�� ���� ��
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
			}
		});
		t2.start();
		
	}

}
