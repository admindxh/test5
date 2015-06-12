package com.rimi.ctibet.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;



/**
 * 
 * @ClassName: Security
 * @Description: 编码解码请求参数
 * @author bcl
 */
public class Security {
	
	private static String base64hash = "T62tz1XHCUjk8NBveQaInA3GMswumo7gc~9VZRdqhbKyiOFlJS-xPfWE04rLY5Dp";
	//"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-/";
	
	private Security(){}
	
	private static class TEMP{
		public static final Security INSTANCE = new Security();
	}
	
	public static Security getInstance(){
		return TEMP.INSTANCE;
	}
	
	private void checkSecurity(){
		if("".equals(base64hash)||base64hash==null||base64hash.length()!=64){
			throw new RuntimeException(Security.class+"was initialize failed!");
		}
	}
	
	/**
	 * 
	 * @Title: encode
	 * @Description: 编码
	 * @param @param src
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String encode(String src){
		
		checkSecurity();
		
		StringBuilder result = new StringBuilder();
		byte[] bytes = src.getBytes();
		int length = bytes.length;
		int mod = 0;
		byte prev = 0;
		for(int i=0;i<length;i++){
			mod = i%3;
			if(mod==0){
				result.append(base64hash.charAt((bytes[i] >> 2) & 0x3F));
			}else if(mod==1){
				result.append(base64hash.charAt((prev << 4 | bytes[i] >> 4  &0x0F )& 0x3F));
			}else{
				result.append(base64hash.charAt((bytes[i] >> 6 & 0x03 | prev << 2) & 0x3F));
				result.append(base64hash.charAt(bytes[i] & 0x3F));
			}
			prev = bytes[i];
		}
		
		if(mod==0){
			result.append(base64hash.charAt(prev << 4 & 0x3C));
			result.append("==");
		}else if(mod==1){
			result.append(base64hash.charAt(prev << 2 & 0x3F));
			result.append("=");
		}
		
		return result.toString();
	}
	
	/**
	 * 
	 * @Title: decode
	 * @Description: 解码
	 * @param @param src
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String decode(String src){
		
		if(StringUtils.isBlank(src)){
			return "";
		}
		checkSecurity();
		
		byte temp = 0;
		String result = "";
		for(int i=0;i<src.length();i++){
			temp = (byte) base64hash.indexOf(src.charAt(i));
			if(temp==-1){
				result+="000000";
			}else{
				String t = Integer.toBinaryString(temp);
				if(t.length()==7){
					t = t.substring(1);
				}else if(t.length()==8){
					t = t.substring(2);
				}
				
				while(t.length()<6){
					t = "0"+t;
				}
				result+=t;
			}
		}
		while(result.endsWith("00000000")){
			result = result.substring(0,result.length()-8);
		}
		
		byte[] bytes = new byte[result.length()/8];
		for(int i=0;i<bytes.length;i++){
			bytes[i]= Integer.valueOf(result.substring(i*8,(i+1)*8),2).byteValue();
		}
		return new String(bytes);
	}
	
	public void setBase64hash(String base64hash) {
		Security.base64hash = base64hash;
	}
	
	/**
	 * 
	 * @Title: randomTable
	 * @Description: 生成随机对照表
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String randomTable(){
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-/";
		List<Character> list = new ArrayList<Character>();
		for(int i=0;i<base.length();i++){
			list.add(base.charAt(i));
		}
		
		Collections.shuffle(list);
		base = "";
		for(Character ch:list){
			base += ch;
		}
		
		return base;
	}
	
	public static void main(String[] args) {
        String code = "ACT4187011957003781,ORCHNL4198245875122661";
        Security security = new Security();
        String encode = security.encode(code);
        //System.out.println(encode);
    }
	
}
