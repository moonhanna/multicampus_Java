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

		//인터페이스이기 때문에 객체생성x, 때문에 쓰레드 객체 안에서 생성
		//Thread t1 = new Thread(new MyT("T1"));
		//t1.start();
		
		Runnable r = new MyT("T1");
		Thread t1 = new Thread(r); 
		t1.start();
		
		//익명클래스로도 가능하지만, 자바에서는 거의 안씀. 안드로이드에서 많이 씀
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
			}
		});
		t2.start();
		
	}

}
