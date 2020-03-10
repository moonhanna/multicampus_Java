package com.sds;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import client.Client;
import msg.Msg;

/**
 * Servlet implementation class WebClientServlet
 */
@WebServlet({ "/WebClientServlet", "/webclient" })
public class WebClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Client client;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebClientServlet() {

    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("hi servlet");
		
		String ip = request.getParameter("ip");
		String speed = request.getParameter("speed");
		
		System.out.println(ip + " " + speed);
		
		Msg msg = new Msg("Admin", speed, ip);

		try {
			client = new Client("ip", 7777, msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

}
