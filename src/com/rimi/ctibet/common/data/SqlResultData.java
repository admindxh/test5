package com.rimi.ctibet.common.data;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class SqlResultData {
	
	public  static final Logger logger = Logger.getLogger(SqlResultData.class);
	
	/**
	 * 将用sql查询返回的map的Key以驼峰命名
	 * @param resultMap
	 * @return
	 */
	public static Map<String, Object> changeSqlResultMap(Map<String, Object> resultMap){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(resultMap != null && resultMap.size() > 0){
			for(String key : resultMap.keySet()){
				dataMap.put(changeKeyToHump(key), resultMap.get(key));
			}
		}
		return dataMap;
	}
	
	/**
	 * 将字段名根据下划线转换为驼峰命名
	 * @param key
	 * @return
	 */
	public static String changeKeyToHump(String key){
		String humpKey = "";
		if(StringUtils.isNotBlank(key)){
		  String 	keyResult = key.toLowerCase();
			String[] names = keyResult.split("_");
			if(names != null && names.length > 0){
				humpKey += names[0];
				if(names.length > 1){
					for(int i = 1; i < names.length; i++){
						humpKey += names[i].substring(0, 1).toUpperCase() + names[i].substring(1);
					}
				}
			}
		}
		return humpKey;
	}
	
	/**
	 * 查询时，将字符串转换为in子查询字符串
	 * @param str
	 * @return
	 */
	public static String toQueryStr(String str) {
		StringBuffer buffer = new StringBuffer();
		if (StringUtils.isNotBlank(str)) {
			Pattern pattern = Pattern.compile("[\\[,\\]]+");
			String[] strs = pattern.split(str);
			for (int i = 0; i < strs.length; i++) {
				buffer.append("'").append(strs[i].trim()).append("'");
				if (i != strs.length - 1) {
					buffer.append(", ");
				}
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 把map结果集转为对象结果集
	 * @param clazz
	 * @param list
	 * @return
	 */
	public static <X> List<X> mapToList(Class<X> clazz, List<Map<String , Object>> list){
		Set<String> set = new HashSet<String>();
		List<X> returnList = new ArrayList<X>();
		if(list!=null && list.size()>0){
			long opTime = System.currentTimeMillis();
			logger.debug("-> 输出对象:" + clazz.getName());
			for (Map<String, Object> m : list) {
				X x = null;
				try {
					x = clazz.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				for (String k : m.keySet()) {
					try {
						PropertyDescriptor propertyDescriptor = new PropertyDescriptor(k, clazz);
						Method writeMethod = propertyDescriptor.getWriteMethod();
						Object val = m.get(k);
						if(val!=null){
						    if(val instanceof Number){
						        Class<?> paramClass = writeMethod.getParameterTypes()[0];
						        if(paramClass.getSuperclass()==Number.class){
						            Constructor<?> constructor = paramClass.getConstructor(String.class);
						            val = constructor.newInstance(val.toString());
						        }else if(paramClass == Integer.TYPE){
						            val = Integer.valueOf(val.toString());
                                }else if(paramClass == Long.TYPE){
                                    val = Long.valueOf(val.toString());
                                }else if(paramClass == Float.TYPE){
                                    val = new Float(val.toString());
                                }else if(paramClass == Double.TYPE){
                                    val = new Double(val.toString());
                                }else {
                                    val = val.toString();
                                }
						    }
						}
						writeMethod.invoke(x, val);
					} catch (IllegalArgumentException e) {
						logger.debug("-> " + k + "=" + m.get(k) + " 赋值出错(可能值与属性类型不匹配)");
					} catch (IntrospectionException e) {
						set.add(k);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				returnList.add(x);
			}
			if(set.size()>0){
				String info = "-> 未发现以下属性：";
				for (String s : set) {
					info += s + ",";
				}
				logger.debug(info);
			}
			logger.debug("耗时：" + (System.currentTimeMillis() - opTime) + "ms");
		}
		return returnList;
	}
	
	public static void main(String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		String s = "123";
		//System.out.println(Integer.class);
		//System.out.println(Integer.class.getSuperclass());
		//System.out.println(Integer.class.getSuperclass() == Number.class);
		//System.out.println(int.class == Integer.TYPE);
	}
	
}
