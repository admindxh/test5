package com.rimi.ctibet.common.util;

import java.util.Date;
import java.util.Random;

public class CodeFactory {
    

	public static String createMutiCode()
	{
		Date date = new Date();
		long time =date.getTime();
		//Random random = new Random();
		//int xxxx=random.nextInt(1000);
		String code="MUT"+(time+"").substring(1);
		return code;
	}
	
	public static String createCultureCode()
	{
		Date date = new Date();
		long time =date.getTime();
		//Random random = new Random();
		//int xxxx=random.nextInt(1000);
		String code="CUL"+(time+"").substring(1);
		return code;
	}
	public static String createEquipmentTypeCode()
	{
		Date date = new Date();
		long time =date.getTime();
		//Random random = new Random();
		//int xxxx=random.nextInt(1000);
		String code="EQT"+(time+"").substring(1);
		return code;
	}
	public static String createOtherCode()
	{
		Date date = new Date();
		long time =date.getTime();
		String code="OTH"+(time+"").substring(1);
		return code;
	}
	public static String createVideoCode()
	{
		Date date = new Date();
		long time =date.getTime();
		String code="VID"+(time+"").substring(1);
		return code;
	}
	public static String createCultureReplyCode()
	{
		Date date = new Date();
		long time =date.getTime();
		String code="CRL"+(time+"").substring(1);
		return code;
	}
	public static String createVideoReplyCode()
	{
		Date date = new Date();
		long time =date.getTime();
		String code="VRL"+(time+"").substring(1);
		return code;
	}
	public static String createMutiReplyCode()
	{
		Date date = new Date();
		long time =date.getTime();
		String code="MRL"+(time+"").substring(1);
		return code;
	}
	public static String create(String  modelName)
	{
		Date date = new Date();
		long time =date.getTime();
		String code=  modelName+""+(time+"").substring(1);
		return code;
	}
	
	/**
	 * 生成编号  
	 * @param modelName 菜单名称
	 * @return
	 */
	public static String createCode(String name)
	{
		Date date = new Date();
		long time =date.getTime();
		time = System.currentTimeMillis();
		StringBuffer xxxx=new StringBuffer(new Random().nextInt(10000)+"");
		while(xxxx.length()<4){
			xxxx.insert(0, "0");
		}
		//System.out.println(xxxx);
		String code=name+(time+"").substring(1) + xxxx;
		return code;
	}
	
	public static void main( String[] m)
	{
		Integer c =Integer.valueOf(126);
		Integer d =Integer.valueOf(126);
		String createActCode = createCode("ACT");
		//System.out.println(createActCode);
		//System.out.println(createActCode.length());
		
	}
}
