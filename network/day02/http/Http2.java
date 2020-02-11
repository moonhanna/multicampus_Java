package http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Http2 {

	public static void main(String[] args) throws Exception {

		String urlstr = "url/test/tomcat.zip";
		URL url = new URL(urlstr);
		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is, 100000);
		
		FileOutputStream os = null;
		BufferedOutputStream bos = null;
		
		os = new FileOutputStream("tomcat.zip");
		bos = new BufferedOutputStream(os);
		
		int data = 0;
		
		while((data = bis.read()) != -1) //EOF
		{
			bos.write(data);
			System.out.println(data);
		}
		bos.close();
		bis.close();
		System.out.println("End");
		
	}

}
