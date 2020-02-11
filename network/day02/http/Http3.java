package http;

import java.net.HttpURLConnection;
import java.net.URL;

public class Http3 {

	public static void main(String[] args) throws Exception{
		
		String urlstr = "url/test/car.jsp?";
		urlstr += "id=gdgd&lat=127.89&lng=37.2";
		
		URL url = new URL(urlstr);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setReadTimeout(5000);
		con.setRequestMethod("POST");
		con.getInputStream();
		con.disconnect();

	}

}
