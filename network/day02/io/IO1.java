package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IO1 {

	public static void main(String[] args) {

		FileReader fr = null;
		BufferedReader br = null;

		FileWriter fw = null;
		BufferedWriter bw = null;

		try {
			fr = new FileReader("test.txt");
			fw = new FileWriter("copy.txt");
			br = new BufferedReader(fr);
			bw = new BufferedWriter(fw, 5); // 사이즈 단위는 바이트
//			int data = 0;
			int sum = 0;
			String str = "";
//			while ((data = br.read()) != -1) {
//				sum++;
//				System.out.println((char) data);
//			}
			while ((str = br.readLine()) != null) { // 한줄 한번에 읽기
				sum++;
				System.out.println(str);
				bw.write(str);
				bw.newLine();
			}
			System.out.println("sum : " + sum);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (bw != null) {
					bw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
