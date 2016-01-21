package cn.tedu.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class WeixinUtils  {
	
	public static final String TO_USER_NAME = "ToUserName";
	public static final String FROM_USER_NAME = "FromUserName";
	public static final String CREATE_TIME = "CreateTime";
	public static final String MSG_TYPE = "MsgType";
	public static final String CONTENT = "Content";
	public static final String MSG_ID = "MsgId";
	public static final String FUNC_FLAG = "FuncFlag";
	
	public static final String[] receive = new String[] { WeixinUtils.TO_USER_NAME, WeixinUtils.FROM_USER_NAME,
			WeixinUtils.CREATE_TIME, WeixinUtils.MSG_TYPE, WeixinUtils.CONTENT };
	
	public static final String[] send = new String[] { WeixinUtils.TO_USER_NAME, WeixinUtils.FROM_USER_NAME,
			WeixinUtils.CREATE_TIME, WeixinUtils.MSG_TYPE, WeixinUtils.CONTENT, WeixinUtils.FUNC_FLAG };
	
	
	public static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
		String[] tmpArr = new String[] { token, timestamp, nonce };
		Arrays.sort(tmpArr);
		String str = StringUtils.join(tmpArr, "");
		str = MDUtils.sha1(str);
		return str.equals(signature);

	}
	

	public static Map<String, String> fromXML(String xml, String[] elements) {

		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			Element root = doc.getRootElement();
			Map<String, String> data = new HashMap<String, String>();
			for (int i = 0; i < elements.length; i++) {
				String value = root.elementText(elements[i]);
				data.put(elements[i], value);
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String toXML(Map<String, String> data, String[] elements) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("xml");
		for (int i = 0; i < elements.length; i++) {
			root.addElement(elements[i]).addText(data.get(elements[i]));
		}

		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			XMLWriter writer = new XMLWriter(os);
			writer.write(doc);
			writer.flush();
			writer.close();

			return new String(os.toByteArray(), "UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static String getRowPostData(HttpServletRequest request)
			throws ServletException, IOException {
		StringBuffer postData = new java.lang.StringBuffer();
		InputStream in = request.getInputStream();
		BufferedInputStream buf = new BufferedInputStream(in);
		byte[] buffer = new byte[1024];
		int iRead;
		while ((iRead = buf.read(buffer)) != -1) {
			postData.append(new String(buffer, 0, iRead, "UTF-8"));
		}

		return postData.toString();

	}

	public static void main(String[] args) {
		
		String xml = "<xml><name>dahuang</name><age>12</age></xml>";
		String[] elements = new String[]{"name","age"};
		Map<String,String> data = fromXML(xml, elements);
		System.out.println(data.get("age"));
		System.out.println(data);
		data.put("age", "20");
		
		String xml1 = toXML(data, elements);
		System.out.println(xml1);

	}
}
