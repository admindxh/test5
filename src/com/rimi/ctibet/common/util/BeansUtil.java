package com.rimi.ctibet.common.util;

import java.lang.reflect.Field;

/**
 * 将一个对象的属性赋值给另一个对象
 * @author dengxh
 *
 */
public class BeansUtil { 

	/**
	 *将 src 对象中的属性赋值给 des 对象
	 * @param des
	 * @param src
	 */
	public static void copyPropertysWithoutNull(Object des, Object src) {
		Class<?> clazz = des.getClass();
		Field[] srcfields = src.getClass().getDeclaredFields();
		for (Field field : srcfields) {
			if (field.getName().equals("serialVersionUID"))
				continue;
			Field f;
			try {
				f = clazz.getDeclaredField(field.getName());
				f.setAccessible(true);
				field.setAccessible(true);
				Object obj = field.get(src);
				if (obj != null)
					f.set(des, obj);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}