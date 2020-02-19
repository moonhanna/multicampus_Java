package tcpip2;

import java.io.Serializable;
import java.util.ArrayList;

public class Msg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String msg;
	String ip;
	ArrayList<String> ips;

	public Msg() {
	}

	public Msg(String id, String msg, String ip) {
		this.id = id;
		this.msg = msg;
		this.ip = ip;
	}

	public Msg(String id, String msg, String ip, ArrayList<String> ips) {
		this.id = id;
		this.msg = msg;
		this.ip = ip;
		this.ips = ips;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public ArrayList<String> getIps() {
		return ips;
	}

	public void setIps(ArrayList<String> ips) {
		this.ips = ips;
	}
	
	
	
	
}
