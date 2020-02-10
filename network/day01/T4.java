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
		// ȸ������ ���� �ð��� 10�ʰ� �ɸ�
		// ȸ������ �ϴ� ���߿� �̸��� ��� ��µǸ� 10�� �Ŀ� �Ϸᰡ ��µȴ�.
		// ��) Thread.sleep(1000);���� �۾�
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
				System.out.println("�Ϸ�");
			}
		}
	}

}

public class T4 {

	public static void main(String[] args) {
		// ���� loop�� �������� �׻� ����ڰ� ��û�� �� �� �ִ�.
		// ������� ��û�� �̸��� �Է��ϸ� ȸ�������� ����˴ϴ�.
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
