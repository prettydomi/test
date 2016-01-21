package cn.tedu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MDUtils {
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String sha1(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("sha1");

			byte[] bs = md.digest(str.getBytes());
			String str1 = getFormattedText(bs);
			return str1;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);

		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
	
	public static void main(String[] args) {
		String str = "hello";
		String str1 = sha1(str);
		System.out.println(str1);
	}
}
