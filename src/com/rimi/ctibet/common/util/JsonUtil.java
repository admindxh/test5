package com.rimi.ctibet.common.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rimi.ctibet.common.exception.JsonParseException;

/** 
 * Java对象和JSON字符串相互转化工具类  
 */  
public final class JsonUtil {  
      
    private JsonUtil(){}  
      
    /** 
     * 对象转换成json字符串 
     * @param obj  
     * @return  
     */  
    public static String toJson(Object obj) throws JsonParseException{  
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();  
        return gson.toJson(obj);  
    }  
  
    /** 
     * json字符串转成对象 
     * @param paramJson   
     * @param type 
     * @return  
     */  
    public static <T> T json2Object(String paramJson, Type type) throws JsonParseException{  
    	T t=null;
		try {
			Gson gson = new Gson();   
        	t=gson.fromJson(paramJson, type);
		} catch (Exception e) {
			throw new JsonParseException("json parse error,parameter is not conform to the standard of json");
		}
        return t; 
    }  
  
    /** 
     * json字符串转成对象 
     * @param paramJson   
     * @param type  
     * @return  
     */  
    public static <T> T json2Object(String paramJson, Class<T> type) throws JsonParseException{  
    	T t=null;
		try {
			Gson gson = new Gson();   
        	t=gson.fromJson(paramJson, type);
		} catch (Exception e) {
			throw new JsonParseException("json parse error,parameter is not conform to the standard of json");
		}
        return t;  
    }  
    
    /** 
     * json字符串转成Map
     * @param paramJson   
     * @return  
     */ 
	public static Map<String, String> json2Map(String paramJson) throws JsonParseException{
		Map<String, String> jmap=null;
		try {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();  
	    jmap = gson.fromJson(paramJson,  
                new TypeToken<Map<String, String>>() {  
                }.getType());  
		} catch (Exception e) {
			throw new JsonParseException("json parse error,parameter is not conform to the standard of json");
		}
	    return jmap;
	}
	
	 /** 
     * json字符串转成List<Map<String,String>>
     * @param paramJson   
     * @return  
     */ 
	public static List<Map<String,String>> json2List(String paramJson) throws JsonParseException{
		List<Map<String,String>> list=null;
		try {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();  
        list = gson.fromJson(paramJson,  
                new TypeToken<List<Map<String, String>>>() {  
                }.getType());  
		} catch (Exception e) {
			throw new JsonParseException("json parse error,parameter is not conform to the standard of json");
		}
	    return list;
	}
	
//	public static void main(String[] args) {
//		List<TDoctor> tList = new ArrayList<TDoctor>();
//		TDoctor t1 = new TDoctor();
//		t1.setDisplayName("zhangsan");
//		tList.add(t1);
//		TDoctor t2 = new TDoctor();
//		t2.setDisplayName("lisi");
//		tList.add(t2);
//		try {
//			//System.out.println(toJson(tList));
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("wangba", "hey");
//		map.put("wangwu", "hello");
//		try {
//			//System.out.println(toJson(map));
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}  
