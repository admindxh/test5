package com.rimi.ctibet.common.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

/**
 * 
 * @author yuezx
 * @date 2014-3-25
 * @package com.rimi.medical.api.common.util
 * @copyright 成都睿峰科技有限公司
 * @version V1.0
 */
public class StringUtil {
	/**
	 * 上传随即字符串
	 * 
	 * @todo TODO
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * list转String ","号分隔
	 * 
	 * @param list
	 * @return
	 */
	public static String listToString(List<String> list) {
		StringBuffer sbf = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			sbf.append(list.get(i) + ",");
		}
		return sbf.substring(0, sbf.length() - 1);
	}
	
	public static String removeSpace(String s){
		String ns = s.replace(" ", "");
	   return ns;	
	}
	/**
	 * 消除字符串中的空白字符
	 */
	@Test 
	public void dd(){
		//System.out.println("ddd  ss ".replace(" ", ""));
	  //System.out.println(StringUtil.removeSpace("   da d "));	
	}
	
	/**
	 * 字符串不等于null和空字符串返回true
	 */
	public static boolean isNotNull(String s){
		if(s!=null && !s.equals("")){
			return true;
		}
		return false;
	}
	public static boolean isNotNull(String ... s){
	    if(s!=null && s.length>0){
	        for (String string : s) {
	            if(string==null || string.equals("")){
	                return false;
	            }
            }
	    }
	    return true;
	}
	
	/**
	 * 过滤字符串中的中文逗号和空格以及空值，返回用“,”重新拼接好的字符串
	 * @param val
	 * @return
	 */
	public static String join(String val){
		String res = "";
		if(val!=null && !val.equals("")){
			String[] split = val.trim().replace("，",",").split(",");
			List<String> list = new ArrayList<String>();
			for (String s : split) {
				String trim = s.trim();
				if(trim!=null && !trim.equals("")){
					list.add(trim);
				}
			}
			if(list!=null && list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					String s = list.get(i);
					res += s;
					if(i<list.size()-1){
						res += ",";
					}
				}
			}
		}
		return res;
	}
	
	/**
	 * 清除HTML标签
	 * @param str
	 * @return
	 */
	public final static String cleanHTML(String str){
	    if(str==null || str.equals("")){
	        return "";
	    }
        return str.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", "");
	}
	
	/**
	 * 计算分数
	 * @param sc
	 * @return
	 */
	public final static String computScore(float sc){
	    String score = "";
	    try {
            if(sc>0){
                String format = new DecimalFormat("0.0").format(sc);
                
                int first = Integer.valueOf(format.split("\\.")[0]);
                int secend =Integer.valueOf(format.split("\\.")[1]);
                if(secend==0){
                }else if(secend<5){
                    secend = 5;
                }else if(secend>5){
                    secend = 0;
                    first += 1;
                }
                
                score = first + "." + secend;
            }else{
                score = "5.0";
            }
        } catch (Exception e1) {
            score = "5.0";
            e1.printStackTrace();
        }
        return score;
	}
	
	
	public static void main(String[] args) {
    }
	
}
