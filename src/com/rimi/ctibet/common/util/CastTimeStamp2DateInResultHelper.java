package com.rimi.ctibet.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * @author xiaozhen
 *将结果集中与日期相关的时间戳改变成string类型。
 */
public class CastTimeStamp2DateInResultHelper {

	public static List<Map<String,Object>> newResulr(List<Map<String,Object>> result){
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                
		  try {
			for (Map<String, Object> map : result) {
				Set ketSet =map.keySet();
				Iterator it = ketSet.iterator();
				while(it.hasNext()){
					String keyName = it.next().toString();
				    if("createtime".equals(keyName)){
				    	Date date = sdf.parse(map.get("createtime").toString());
				    	map.put("createtime",date.toLocaleString());
				    }
				    if("lastedittime".equals(keyName)){
				    	Date date = sdf.parse(map.get("lastedittime").toString());
				    	map.put("lastedittime",date.toLocaleString());
				    }
				    if("verfifytime".equals(keyName)){
				    	Date date = sdf.parse(map.get("verfifytime").toString());
				    	map.put("verfifytime",date.toLocaleString());
				    }
				    if("bindtime".equals(keyName)){
				    	Date date = sdf.parse(map.get("bindtime").toString());
				    	map.put("bindtime",date.toLocaleString());
				    }
				 }
			   }
			 } catch (ParseException e) {
				 e.printStackTrace();
			 }    
		return result;
	}
}
