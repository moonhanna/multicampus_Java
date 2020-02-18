package tcpip2;

import java.io.Serializable;
import java.util.ArrayList;

public class Msg implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String msg;
	String ip;
	ArrayList<String> iplist = new ArrayList<String>();

	public Msg() {
		super();
	}

	public Msg(String id, String msg, String ip) {
		super();
		this.id = id;
		this.msg = msg;
		this.ip = ip;
	}

	public Msg(String id, String msg, String ip, ArrayList<String> iplist) {
		super();
		this.id = id;
		this.msg = msg;
		this.ip = ip;
		this.iplist = iplist;
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

	public ArrayList<String> getIplist() {
		return iplist;
	}

	public void setIplist(ArrayList<String> iplist) {
		this.iplist = iplist;
	}



}
