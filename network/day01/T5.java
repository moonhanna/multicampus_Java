package day01;

import java.util.Scanner;

class Customer implements Cus{
	
	String name;

	public Customer() {
		
	}

	public Customer(String name) {
		this.name = name;
	}
	
	//register
	Thread rthread = new Thread() {
		@Override
		public void run() {
			int i = 3;
			while (i > 0) {
				System.out.println(name);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				i--;
				if (i == 0) {
					System.out.println("�Ϸ�");
				}
			}
		}
	};
	
	//login
	Thread lthread = new Thread() {
		@Override
		public void run() {
			System.out.println("�α���");
		}
	};
	
	//logout
	Thread othread = new Thread() {
		@Override
		public void run() {
			System.out.println("�α׾ƿ�");
		}
	};

	@Override
	public void register() {
		rthread.start();
	}

	@Override
	public void login() {
		lthread.start();
	}

	@Override
	public void logout() {
		othread.start();
	}
	
}

public class T5 {

	public static void main(String[] args) {
		// ������ �� : r, l, o, q
		// r�϶��� name�Է� �޾� ����
		// l - �α���, o - �α׾ƿ�
		// q�� ���� ����	
		Scanner s = new Scanner(System.in);
		Cus c = null;
		
		while(true)
		{
			System.out.println("r - ȸ������, l - �α���, o - �α׾ƿ�, q - ����");
			String input = s.nextLine();
			switch (input) {
			case "r":
				System.out.println("�̸��� �Է��ϼ���");
				String name = s.nextLine();
				c = new Customer(name);
				c.register();
				//Customer r = new Customer(name);
				//r.rthread.run();
				break;
			case "l":
				c = new Customer();
				c.login();
				break;	
			case "o":
				c = new Customer();
				c.logout();
				break;	
			case "q":
				System.out.println("����");
				s.close();
				System.exit(0);
				break;	
			default:
				break;
			}
			
		}
		
	}

}
