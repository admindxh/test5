package com.rimi.ctibet.common.constants;

import org.apache.commons.lang.StringUtils;

/**
 * 常量帮助类
 * @author dengxh
 *
 */
public class Constants {
	/**
	 * 审核通过
	 */
	public static  final int STATUS_PASS = 1; //审核通过
	/**
	 * 审核不通过
	 */
	public static  final int STATUS_NOPASS = -1; //审核不通过
	/**
	 * 审核中
	 */
	public static  final int STATUS_CHECK = 0; //审核中
	
	
	
	public static  final int Click_Praise  = 0;//赞
	
	public static final int CLICK_VIEW  = 1;//浏览
	
	public static final int CLICK_HOT = 1;//最热门
	
	public static final int CLICK_REPLY = 2;//评论
	
	public static final int CLICK_FAVATE = 3 ;//收藏
	
	/**
	 * 目的地
	 */
	public static final String SEARCH_DES = "des";//
	
	/**
	 * 景点
	 */
	public  static final String SEARCH_VIEW= "view";
	
	/********** 消息通知 begin ************/
	public final static String Stra_New_Reply="straNewReply";  //攻略新回复提醒
	
	public final static String Post_New_Reply="postNewReply";  //帖子新回复提醒
	
	public final static String Stra_Judge_Ok="straJudgeOk";    //攻略审核通过提醒
	
	public final static String Post_Judge_Ok="postJudgeOk";    //帖子审核通过提醒
	
	public final static String Stra_Judge_Back="straJudgeBack";//攻略审核退回提醒  
	
	public final static String Post_Judge_Back="postJudgeBack";//帖子审核退回提醒
	
	public final static String Stra_Delete="straDelete";       //攻略删除提醒
	
	public final static String Post_Delete="postDelete";       //帖子删除提醒
	
	public final static String Reply_Delete="replyDelete";     //回复、评论删除提醒
	
	public final static String Post_Top="postTop";             //帖子置顶通知
	
	public final static String Reply_Judge_Ok="replyJudgeOk";  //帖子审核通过
	
	/********** 消息通知 end ************/
	
	/***点击日志类型***/
	public final static String 	ACTION_CLICK="click";//点击类型,查看类型
	public final static String  ACTION_FAVATE="favate";   //收藏数量类型
	public final static String  ACTION_PRAISE="praise";   //赞数类型
	public final static String  ACTION_REPLY="reply";//评论类型
	public final static String  ACTION_HREF="href";//外链数量（商户）
	/****/
	
	
	/**
	 * 获取中文名称 
	 */
	public  static String getTextByCode(Integer code){
		if (code!=null) {
			 if (StringUtils.isNotBlank(code.toString())) {
				 if (code==STATUS_PASS) {
					return  "已审核";
				}else if(code==STATUS_CHECK){
					
					return  "未审核";
				}else if(code==STATUS_NOPASS){
					
					return  "审核未通过";
				}else{
					return "全部状态";
					
				}
			}
		}else{
			
			return "全部状态";
		}
		return "";
	}
	/***
	 * 清除缓存时间
	 */
	public static long  intervalTime = 1000*60*10;
	
	
	
	/**
	 * 清除访问时间次数
	 */
	public static int    maxVisitCount  =  50;
	
	public static  final String  paytype_bz = "1";//本站支付
	public static  final String  paytype_wz = "2";//其他站点支付
	
	/**
	 * 装备地址
	 */
	public static  final String EQUIRMENT_URL = "ride/eqindex/detail";//装备地址
	
	
	
	
}
