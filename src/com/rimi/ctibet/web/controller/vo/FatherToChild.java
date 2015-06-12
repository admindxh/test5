package com.rimi.ctibet.web.controller.vo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class FatherToChild {

	
	//父类属性值 全复制给子类，向上遍历
	public static void fatherToChild(Object father, Object child) {
		Class fm = child.getClass().getSuperclass();
		if (fm==null) {
			return  ;
		}
		if (father!=null&&fm != father.getClass()) {
			//System.out.println("非父子类关系");
			return;
		}
		try {
			Class fatherClass = father.getClass();
			List ff=new ArrayList<Field>();
			getField(ff,fatherClass);
			for (int i = 0; i < ff.size(); i++) {
				Field f =(Field) ff.get(i);// 取出每一个属性
				Class type = f.getType();
				if (checkStaticParam(f)) {
					continue;
				}
				String paramName = upperHeadChar(f.getName());
				Method fathermeMethod = fatherClass
						.getMethod("get" + paramName);
				Object obj = fathermeMethod.invoke(father);// 取出属性值
				Method childMethod = child.getClass().getMethod(
						"set" + paramName, type);
				childMethod.invoke(child, obj);
			}
		} catch (Exception e) {
			//System.out.println(e);
			//System.out.println("转换错误！！！！");
		}
	}
	public static void getField(List<Field> fields,Class  classname){
		if(classname!=Object.class)
		{
			getField(fields, classname.getSuperclass());
		}else
		{
			return;
		}
		Field[] fs=classname.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			fields.add(fs[i]);
		}
	}
	

	private static String upperHeadChar(String in) {
		String head = in.substring(0, 1);
		String out = head.toUpperCase() + in.substring(1, in.length());
		return out;
	}

	private static boolean checkStaticParam(Field field) {
		if (Modifier.isStatic(field.getModifiers())) {
			return true;
		}
		return false;
	}
	
	public static void childToFather(Object child,Object father)
	{
		try {
			Class fatherClass = father.getClass();
			List ff=new ArrayList<Field>();
			getField(ff,fatherClass);
			for (int i = 0; i < ff.size(); i++) {
				Field f =(Field) ff.get(i);// 取出每一个属性
				Class type = f.getType();
				if (checkStaticParam(f)) {
					continue;
				}
				String paramName = upperHeadChar(f.getName());
				Method fathermeMethod = fatherClass
						.getMethod("get" + paramName);
				Object obj = fathermeMethod.invoke(child);// 取出属性值
				Method childMethod = father.getClass().getMethod(
						"set" + paramName, type);
				childMethod.invoke(father, obj);
			}
		} catch (Exception e) {
			//System.out.println(e);
			//System.out.println("转换错误！！！！");
		}
	}

}
