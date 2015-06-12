package com.rimi.ctibet.common.util;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class DateUtil {
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String TIMESTAMP_PATTERN_2 = "yyyy-MM-dd HH:mm:ss.S";
	public static String dataFormatWhole(Date date) {
		return FORMATW.format(date);
	}

	public static String formatDate(Date date, int style) {
		if (date == null) {
			return "";
		}
		switch (style) {
		case 4:
			return FORMATS.format(date);
		case 3:
			return FORMATM.format(date);
		case 2:
			return FORMAT.format(date);
		case 5:
			return FORMATY.format(date);
		default:
			return FORMATW.format(date);
		}
	}
	
	public static Date getNowTimeDate (){
		return DateUtil.getNowTime(DateUtil.getTime(5), "yyyy-MM-dd HH:mm:ss");
	}

	public static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat FORMATW = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat FORMATM = new SimpleDateFormat("MM-dd HH:mm");
	public static final DateFormat FORMATS = new SimpleDateFormat("MM-dd");
	public static final DateFormat FORMATY = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static FilenameFilter DIR_FILE_FILTER = new FilenameFilter() {

	public boolean accept(File dir, String name) {
			if (dir.isDirectory()) {
				return true;
			} else {
				return false;
			}
		}
	};
	
	
	public static String dateToStr(Date date,String strFormat) {
	  
	   SimpleDateFormat format = new SimpleDateFormat(strFormat);
	   String str = format.format(date);
	   return str;
	} 


	public static Date strToDate(String str,String strFormat) {	  
	   SimpleDateFormat format = new SimpleDateFormat(strFormat);
	   Date date = null;
	   try {
		   date = format.parse(str);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   return date;
	}
	
	public static Date getNowTime(String strDate,String strFormat){		
		DateFormat format = new SimpleDateFormat(strFormat);
		Date date = null;
		try
		{
			date = format.parse(strDate);
		} catch(Exception e){}
		return date;
	}

	public static String getTime(int type)
	{
		String t = null;
		SimpleDateFormat format = null;
		Calendar cal = Calendar.getInstance();
		switch (type)
		{
			case 0:
				format = new SimpleDateFormat("yyyyMMddHHmmss");
				break;
			case 1:
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
			case 2:
				format = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
				break;
			case 3:
				format = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");
				break;
			case 4:
				format = new SimpleDateFormat("yyyy-MM-dd");
				break;
			case 5:
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
			case 6:
				format = new SimpleDateFormat("yyyyMMdd");
				break;
			case 7:
				format = new SimpleDateFormat("yyyy-MM");
				break;
			case 8:
				format = new SimpleDateFormat("HH:mm:ss");
				break;
			case 9:
				format = new SimpleDateFormat("yyyy");
				break;
			case 10:
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				break;
			default:
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
		}
		t = format.format(cal.getTime());
		return t;
	}
	
	public static long getLongTime() {
		return System.currentTimeMillis();
	}
	
	public static long compareDate(Date iDate,String format){
		long distime = 0;
		if (iDate != null) {
			try {
				distime = DateUtil.getLongTime() - DateFormat.getDateTimeInstance().parse(DateUtil.formatDateTime(iDate, format)).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			distime = distime/1000/60/60/24;	        
		}		
		return distime;
	}
	
	 public static boolean isDateBefore(Date date2){
	  try{
		   Date date1 = new Date();
		   DateFormat df = DateFormat.getDateTimeInstance();
		   return date1.before(df.parse(DateUtil.formatDateTime(date2, "yyyy-MM-dd HH:mm:ss"))); 
	  }catch(ParseException e){
		  //System.out.print("[isDateBefore] " + e.getMessage());
		  return false;
	  }
	 }
	 
	
	 public static boolean isDateBefore(String date1,String date2){
	  try{
		   DateFormat df = DateFormat.getDateTimeInstance();
		   return df.parse(date1).before(df.parse(date2)); 
	  }catch(ParseException e){
		  //System.out.print("[isDateBefore] " + e.getMessage());
		  return false;
	  }
	 }

	
	public static Date strToDate(String dateStr) {
		Date dateTemp = null;
		try {
			dateStr = dateStr.substring(0, 10);
			StringTokenizer token = new StringTokenizer(dateStr, "-");
			int year = Integer.parseInt(token.nextToken());
			int month = Integer.parseInt(token.nextToken()) - 1;
			int day = Integer.parseInt(token.nextToken());
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, day);
			dateTemp = cal.getTime();
		} catch (Exception ex) {
			//System.out.println(ex.getMessage());
		}
		return dateTemp;
	}
	
	public static String formatDateTime(Date date,String format) {
		SimpleDateFormat outFormat = new SimpleDateFormat(format);
		return outFormat.format(date);
	}
	
	  public static long getTwoDateDays(Date beginDate,Date endDate)
	  {
			long beginTime = beginDate.getTime();
			long endTime = endDate.getTime();
			long betweenDays = (long)((endTime - beginTime) / (1000 * 60 * 60 *24) + 0.5);
			return betweenDays;
	  }
	  /**
	   * 毫秒日期转换到指定格式
	   * @param time 日期
	   * @param format 转换格式
	   * @return
	   * @throws Exception
	   */
	public static String secondToStr(long time,String format)throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.format(time);
	}
	
	/**
	 * 将long型的时间戳转换为字符串
	 * @param timestamp
	 * @return
	 */
	public static String longToString(long timestamp, int style){
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(timestamp);
		//System.out.println(new Date(timestamp));
		//System.out.println("****************************");
		return formatDate(new Date(timestamp), style);
	}
	
	/**
	 * 将long型的时间戳转换为字符串
	 * @param timestamp
	 * @return
	 */
	public static Date longToDate(long timestamp){
		return new Date(timestamp);
	}
	
	/**
	 * 获取当前时间
	 */
	public final static Date getCurrentDate(){
	    return new Date(System.currentTimeMillis());
	}
	
}
