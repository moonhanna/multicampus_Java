package workshop;

import java.net.HttpURLConnection;
import java.net.URL;

class Dron extends Thread{
	
	String id;
	double lat;
	double lng;
	double height;
	
	public Dron() {
		
	}
	
	public Dron(String id, double lat, double lng, double height) {
		this.id = id;
		this.lat = lat;
		this.lng = lng;
		this.height = height;
	}

	@Override
	public void run() {
		
		String urlstr = "url/test/drone.jsp?";
		urlstr += "id="+id+"&lat="+lat+"&lng="+lng+"&height="+height;
		
		//URL url = null;
		HttpURLConnection con = null;
		
		try {
			URL url = new URL(urlstr);
			con = (HttpURLConnection)url.openConnection();
			con.setReadTimeout(5000);
			con.setRequestMethod("POST");
			con.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}
	}
	
}

class Car extends Thread{
	
	String id;
	String category;
	double lat;
	double lng;
	
	public Car() {
	
	}
	
	public Car(String id, String category, double lat, double lng) {
		super();
		this.id = id;
		this.category = category;
		this.lat = lat;
		this.lng = lng;
	}

	@Override
	public void run() {

		String urlstr = "url/test/car.jsp?";
		urlstr += "id="+id+"&category="+category+"&lat="+lat+"&lng="+lng;
		
		//URL url = null;
		HttpURLConnection con = null;
		
		try {
			URL url = new URL(urlstr);
			con = (HttpURLConnection)url.openConnection();
			con.setReadTimeout(5000);
			con.setRequestMethod("POST");
			con.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}
	}
	
}

class Apt extends Thread{
	
	String id;
	String name;
	int level;	
	
	public Apt() {
		
	}

	public Apt(String id, String name, int level) {
		this.id = id;
		this.name = name;
		this.level = level;
	}

	@Override
	public void run() {
		
		String urlstr = "url/test/apt.jsp?";
		urlstr += "id="+id+"&name="+name+"&level="+level;
		
		//URL url = null;
		HttpURLConnection con = null;
		
		try {
			URL url = new URL(urlstr);
			con = (HttpURLConnection)url.openConnection();
			con.setReadTimeout(5000);
			con.setRequestMethod("POST");
			con.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}
		
	}
	
}

public class Workshop {

	public static void main(String[] args) {
		
		String id = "02";
		double lat;
		double lng;
		
		// Dron
		double height;
		
		// Car
		String[] category = { "a", "BMW", "VOLVO", "b", "d" };

		// Apt
		String[] name = {"h","w","c","b"};
		int level;	

		while(true)
		{
			
			// Dron
			lat = Math.random()*100 +1;
			lng = Math.random()*100 +1;
			height = Math.random()*100 +1;
			
			Thread dron = new Dron(id, lat, lng, height);
			dron.run();
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			// Car
			lat = Math.random()*100 +1;
			lng = Math.random()*100 +1;
			String cat = category[(int)Math.random()*5];
			
			Thread car = new Car(id, cat, lat, lng);
			car.run();
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			// Apt
			String na = name[(int)(Math.random()*4)+1];
			level = (int)Math.random()*50+1;
			
			Thread apt = new Apt(id, na, level);
			apt.run();
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}

	}

}
