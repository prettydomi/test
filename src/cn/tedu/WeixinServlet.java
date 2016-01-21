package cn.tedu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.tedu.util.WeixinUtils;

public class WeixinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TOKEN = "tmooc";

	public WeixinServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	private boolean valid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String echostr = request.getParameter("echostr");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");

		boolean b = WeixinUtils.checkSignature(signature, timestamp, nonce, TOKEN);

		PrintWriter out = response.getWriter();

		if (echostr != null) {
			out.print(echostr);
		}

		return b;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain;charset=utf-8");
		if (valid(request, response)) {
			String postData = WeixinUtils.getRowPostData(request);
			Map<String, String> data = WeixinUtils.fromXML(postData, WeixinUtils.receive);
			
			// ..
			// ...
			// ..

			PrintWriter out = response.getWriter();
			
			if (data.get(WeixinUtils.CONTENT) != null && data.get(WeixinUtils.CONTENT).length() > 0) {
				
				Map<String,String> data1 = new HashMap<String,String>();
				data1.put(WeixinUtils.FROM_USER_NAME, data.get(WeixinUtils.TO_USER_NAME));
				data1.put(WeixinUtils.TO_USER_NAME, data.get((WeixinUtils.FROM_USER_NAME)));
				data1.put(WeixinUtils.CONTENT, "hahahahaha");
				data1.put(WeixinUtils.CREATE_TIME, System.currentTimeMillis()+"");
				data1.put(WeixinUtils.MSG_TYPE, "text");
				data1.put(WeixinUtils.FUNC_FLAG, "0");
				
				out.print(WeixinUtils.toXML(data1, WeixinUtils.send));
			}
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
