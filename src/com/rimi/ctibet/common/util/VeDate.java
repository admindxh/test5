package com.rimi.ctibet.common.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.RandomStringUtils;

public class VeDate {
	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 返回日期的星期
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeekXq(String sdate) {
		int w = getWeekNum(sdate);
		String s = "";
		switch (w) {
		case 0:
			s = "星期天";
			break;
		case 1:
			s = "星期一";
			break;
		case 2:
			s = "星期二";
			break;
		case 3:
			s = "星期三";
			break;
		case 4:
			s = "星期四";
			break;
		case 5:
			s = "星期五";
			break;
		case 6:
			s = "星期六";
			break;

		}
		return s;
	}

	/**
	 * 返回数字星期 星期天是0 星期一是1
	 * 
	 * @param sdate
	 * @return
	 */
	public static int getWeekNum(String sdate) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(strToDate(sdate));
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	public static String getStringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static String getNextDay(String nowdate, String delay) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String mdate = "";
		Date d = null;
		if (nowdate == null || "".equals(nowdate)) {
			d = new Date();
		} else {
			d = strToDate(nowdate);
		}

		long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
		d.setTime(myTime * 1000);
		mdate = format.format(d);
		return mdate;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static String getNextDay(String nowdate, int delay) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String mdate = "";
		Date d = strToDate(nowdate);
		long myTime = (d.getTime() / 1000) + delay * 24 * 60 * 60;
		d.setTime(myTime * 1000);
		mdate = format.format(d);
		return mdate;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 指定多少秒前或后的时间
	 * 
	 * @param sjzl
	 * @return
	 */
	public static String getNextDayTime(int sjzl) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date();
		long myTime = (currentTime.getTime() / 1000) + sjzl;
		currentTime.setTime(myTime * 1000);
		return format.format(currentTime);
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * 
	 * @return String
	 */
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}

	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	 * 
	 * @param sformat
	 *            yyyyMMddHHmmss
	 * @return
	 */
	public static String getUserDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	 * 
	 * @param sformat
	 *            yyyyMMddHHmmss
	 * @return
	 */
	public static String getUserDate(String sformat,String date) {
		Date currentTime = null;
		if(date.length()<11)
		   currentTime = strToDate(date);
		else
			currentTime = strToDateLong(date);
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	
	/**
	 * 转为中文格式显示
	 */
	public static String getStrDateFormat(String strDate) {
		Date date = strToDateLong(strDate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		String dateString = formatter.format(date);
		return dateString;
	}
	
	
	
	public static int dateDiff(String sDate1, String sDate2) { // sDate1和sDate2是2002-12-18格式
		String[] aDate;
		Integer iDays;
		aDate = sDate1.split("-");
		Integer jsrq = Integer.valueOf(aDate[0]) * 31 * 24 + Integer.valueOf(aDate[1]) * 31 + Integer.valueOf(aDate[2]); // 转换为12-18-2002格式
		aDate = sDate2.split("-");
		Integer ksrq = Integer.valueOf(aDate[0]) * 31 * 24 + Integer.valueOf(aDate[1]) * 31 + Integer.valueOf(aDate[2]);
		iDays = jsrq - ksrq; // 把相差天数
		return iDays;
	}

	/**
	 * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
	 * 
	 * 表示是取几位随机数，可以自己定
	 */

	private static List<String> randList = new ArrayList<String>();

	/**
	 * 保证一次产生10000个随机数内无重复 平均一秒钟能产生250个随机数
	 */
	public static String getNo(int k) {
		if (randList.size() > 10000) {
			randList.clear();
		}
		String rno = getNoNo(k);
		while (randList.contains(rno)) {

			rno = getNoNo(k);

		}
		randList.add(rno);
		return rno;
	}
	
	public static String getNo(int k,List<String> slist) {
		if (slist.size() > 10000) {
			slist.clear();
		}
		String rno = getNoNo(k);
		while (slist.contains(rno)) {
			rno = getNoNo(k);
		}
		slist.add(rno);
		return rno;
	}

	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}

	private static String getNoNo(int k) {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return getUserDate("yyMMddHHmmss") + RandomStringUtils.randomNumeric(k);
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 得到二个日期间的间隔分钟
	 */
	public static String getTwoMil(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static String getPreTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param dat
	 * @return
	 */
	public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
		String str = dat.substring(0, 7);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
			str += "-31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "-30";
		} else {
			if (isLeapYear(dat)) {
				str += "-29";
			} else {
				str += "-28";
			}
		}
		return str;
	}

	/**
	 * 判断是否润年
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {

		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthBegin(String dat) {
		String str = dat.substring(0, 8);
		return str + "01";
	}

	/**
	 * 时间前推或后推秒钟,其中JJ表示秒钟.
	 */
	public static String getPreTimesec(String sj1, int jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + jj;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}
	
	public static String changeDate(String date){
		String month = date.substring(4,6);
		String year = date.substring(2,4);
		String day = date.substring(6,8);
		if("01".equals(month)) return day+"JAN"+year;
		if("02".equals(month)) return day+"FEB"+year;
		if("03".equals(month)) return day+"MAR"+year;
		if("04".equals(month)) return day+"APR"+year;
		if("05".equals(month)) return day+"MAY"+year;
		if("06".equals(month)) return day+"JUN"+year;
		if("07".equals(month)) return day+"JUL"+year;
		if("08".equals(month)) return day+"AUG"+year;
		if("09".equals(month)) return day+"SEP"+year;
		if("10".equals(month)) return day+"OCT"+year;
		if("11".equals(month)) return day+"NOV"+year;
		if("12".equals(month)) return day+"DEC"+year;
		return date;
	}
	
	public static String revertDate(String date){
		String day = date.substring(0,2);
		String month = date.substring(2,5);
		String year = date.substring(5,7);
		if("JAN".equals(month)) return "20"+year+"-01-"+day;
		if("FEB".equals(month)) return "20"+year+"-02-"+day;
		if("MAR".equals(month)) return "20"+year+"-03-"+day;
		if("APR".equals(month)) return "20"+year+"-04-"+day;
		if("MAY".equals(month)) return "20"+year+"-05-"+day;
		if("JUN".equals(month)) return "20"+year+"-06-"+day;
		if("JUL".equals(month)) return "20"+year+"-07-"+day;
		if("AUG".equals(month)) return "20"+year+"-08-"+day;
		if("SEP".equals(month)) return "20"+year+"-09-"+day;
		if("OCT".equals(month)) return "20"+year+"-10-"+day;
		if("NOV".equals(month)) return "20"+year+"-11-"+day;
		if("DEC".equals(month)) return "20"+year+"-12-"+day;
		return date;
	}
	
	public static String changeUSADate(String sdate){
		String[] sd = sdate.split(" ");
		String srq = sd[0];
		String[] newrq = srq.split("/");
		return "20" + newrq[2] +"-"+ (newrq[0].length()==1 ? ("0" + newrq[0]) : newrq[0])
		       +"-" + (newrq[1].length()==1 ? ("0" + newrq[1]) : newrq[1]) +" " + sd[1];
	}
	
	/**
	 * 将日期转换为黑屏格式
	 * */
	public static String dateShiftCommand(String strDate) throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(strDate);
		SimpleDateFormat commandFormat = new SimpleDateFormat("ddMMMyy",new Locale("US"));
		return commandFormat.format(date);
	}
	
	/**
	 * 将黑屏格式日期还原
	 * */
	public static String commandDateShift(String strDate) throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMMyy",new Locale("US"));//
		Date date = dateFormat.parse(strDate);
		SimpleDateFormat commandFormat = new SimpleDateFormat("yyyy-MM-dd");
		return commandFormat.format(date);
	}
	
	public static void main(String srgs[]){
		String sdate = "04AUG13";
		try{
			//System.out.println(commandDateShift(sdate));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	**针对婴儿转换出生年月
	 */
	public static String revertDateForInf(String date){
		String month = date.substring(0,3);
		String year = date.substring(3,5);
		if("JAN".equals(month)) return "20"+year+"年1月";
		if("FEB".equals(month)) return "20"+year+"年2月";
		if("MAR".equals(month)) return "20"+year+"年3月";
		if("APR".equals(month)) return "20"+year+"年4月";
		if("MAY".equals(month)) return "20"+year+"年5月";
		if("JUN".equals(month)) return "20"+year+"年6月";
		if("JUL".equals(month)) return "20"+year+"年7月";
		if("AUG".equals(month)) return "20"+year+"年8月";
		if("SEP".equals(month)) return "20"+year+"年9月";
		if("OCT".equals(month)) return "20"+year+"年10月";
		if("NOV".equals(month)) return "20"+year+"年11月";
		if("DEC".equals(month)) return "20"+year+"年12月";
		return date;
	}
	
	/**
	 *  把字符串转化为日期format : yyyy-MM-dd 或者  yyyy-MM-dd HH:mm:ss
	 */
	public static Date getDateByString(String date,String format) throws Exception{
		SimpleDateFormat format1 = new SimpleDateFormat(format);
		return format1.parse(date);
	}
	
	/**
	 * 比较两个时间的大小  
	 * @param DATE1 第一个时间
	 * @param DATE2 第二个时间
	 * @param format 比较的时间格式  yyyy-MM-dd HH:mm:ss 或者 yyyy-MM-dd
	 * @return 1 表示第一个时间大于第二个时间  -1 与之相反  0则表示时间相同
	 */
	public static int compareDate(String DATE1, String DATE2,String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
	
	/**
	 * 判断是否在有效期
	 * @param DATE1  起飞时间
	 * @param DATE2 开始时间
	 * @param DATE3 结束时间
	 * @param format
	 * @return
	 */
	public static int validDate(String DATE1, String DATE2,String DATE3,String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            Date dt3 = df.parse(DATE3);
            if (dt1.getTime() >= dt2.getTime() && dt1.getTime() <= dt3.getTime()) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
	
	public static String toDateString(Date date){
		  SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd");
		  if (date==null) {
			return null ;
		}
		return dateFormat.format(date);
		
		
	}
	
}
