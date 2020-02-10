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
					System.out.println("완료");
				}
			}
		}
	};
	
	//login
	Thread lthread = new Thread() {
		@Override
		public void run() {
			System.out.println("로그인");
		}
	};
	
	//logout
	Thread othread = new Thread() {
		@Override
		public void run() {
			System.out.println("로그아웃");
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
		// 구현할 것 : r, l, o, q
		// r일때만 name입력 받아 진행
		// l - 로그인, o - 로그아웃
		// q는 서버 종료	
		Scanner s = new Scanner(System.in);
		Cus c = null;
		
		while(true)
		{
			System.out.println("r - 회원가입, l - 로그인, o - 로그아웃, q - 종료");
			String input = s.nextLine();
			switch (input) {
			case "r":
				System.out.println("이름을 입력하세요");
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
				System.out.println("종료");
				s.close();
				System.exit(0);
				break;	
			default:
				break;
			}
			
		}
		
	}

}
