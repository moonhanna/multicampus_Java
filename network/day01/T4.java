package day01;

import java.util.Scanner;

class Register extends Thread {

	String name;

	public Register() {

	}

	public Register(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		// 회원가입 진행 시간은 10초가 걸림
		// 회원가입 하는 도중에 이름이 계속 출력되며 10초 후에 완료가 출력된다.
		// 단) Thread.sleep(1000);으로 작업
		int i = 10;
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

}

public class T4 {

	public static void main(String[] args) {
		// 무한 loop를 동작으로 항상 사용자가 요청을 할 수 있다.
		// 사용자의 요청은 이름을 입력하면 회원가입이 진행됩니다.
		Scanner s = new Scanner(System.in);
		while (true) {
			System.out.println("Ready...");
			String input = s.nextLine();
			if (input.equals("q")) {
				break;
			}
			Thread r = new Register(input);
			r.start();
		}
		s.close();
	}

}
