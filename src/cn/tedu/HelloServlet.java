package cn.tedu;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
	
	@Override
	public void init() throws ServletException {
		System.out.println("");
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("");
	}
	
	@Override
	public void destroy() {
		System.out.println("");
	}
}
