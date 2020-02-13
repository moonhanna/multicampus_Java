package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

class LoginThread extends Thread{

	String id;
	String pwd;
	String result;
	
	public LoginThread() {
		
	}

	public LoginThread(String id, String pwd) {
		this.id = id;
		this.pwd = pwd;
	}

	@Override
	public void run() {
		
		String urlstr = "url/httptest/logintest.jsp";
		urlstr += "?id="+id+"&pwd="+pwd;
		URL url = null;
		HttpURLConnection con = null; //HttpURLConnection클래스의 메소드를 쓰기 위해, URLConnection을 쓰면 함수가 별로 없음
		InputStream in = null;
		InputStreamReader is = null;
		BufferedReader br = null;
		
		try {
			url = new URL(urlstr);
			con = (HttpURLConnection)url.openConnection();
			con.setReadTimeout(5000);
			con.setRequestMethod("POST");
			in = con.getInputStream(); //전송된 것 받음, 바이트단위 
			is = new InputStreamReader(in); //InputStreamReader는 한글이 내려왔을 때 처리가능
			br = new BufferedReader(is); //readLine을 쓰기 위해
			if(con.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				String temp = "";
				while((temp = br.readLine()) != null)
				{
					result = temp.trim(); //양 옆의 공백제거
				}
			}
			else
			{
				result = "0";
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(br != null)
			{
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}

public class Login {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		LoginThread lt = null;
		
		while(true)
		{
			System.out.println("Input id,pwd");
			String id = sc.nextLine();
			if(id.equals("q"))
			{
				break;
			}
			String pwd = sc.nextLine();
			lt = new LoginThread(id,pwd);
			lt.start();
			try {
				lt.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(lt.result.equals("1")) {
				System.out.println("로그인 성공");
			}
			else
			{
				System.out.println("로그인 실패");
			}
		}
		
		System.out.println("End!");
		
		sc.close();

	}

}
