package day01;

public class P1 {

	//½Ì±Û ¾²·¹µå
	public static void main(String[] args) throws Exception {

		int data = 0;
		
		for(int i=1; i<=10; i++)
		{
			data += i;
			System.out.println("FOR1 : "+i);
			Thread.sleep(200);
		}
		
		for(int i=1; i<=10; i++)
		{
			data += i;
			System.out.println("FOR2 : "+i);
			Thread.sleep(200);
		}
		
		System.out.println("data : " + data);
		
	}

}
