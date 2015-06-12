package com.rimi.ctibet.common.util;

import java.math.BigDecimal;
import java.security.*;

public class LoginMD5 {
	private final static String[] HEXDIGITS = { "1", "3", "5", "7", "9", "q",
			"w", "r", "t", "y", "u", "i", "o", "p", "a", "s" };//把原来的字符替换为新的字符，提高加密难度

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return HEXDIGITS[d1] + HEXDIGITS[d2];
	}

	public static String compile(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin+"^$![];'./0");//在原密码后面追加一串数据，防止被破解
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
			resultString+=resultString.charAt(7);//在加密后的32为密码后面，追加一个数据，提高加密难度
		} catch (Exception ex) {
		}
		return resultString;
	}
	
	public static String oldCompile(String origin) {
		String resultString = null;
		try {
			resultString = origin;
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}
	
	public static void main(String[] args) {
		String a=  compile("123");
		System.out.println(compile("12345678"));
		BigDecimal b = new BigDecimal("8900");
		b =  b.divide( new BigDecimal("10000"));
		System.out.println(b);
	}
}