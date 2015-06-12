package com.rimi.ctibet.portal.controller.UserCenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Order;
import com.rimi.ctibet.domain.UserFavorite;
import com.rimi.ctibet.domain.UserView;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IActivityService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMemberEnrollDetailService;
import com.rimi.ctibet.web.service.IOrderService;
import com.rimi.ctibet.web.service.IUserFavoriteService;
import com.rimi.ctibet.web.service.UserViewService;
@Controller
@RequestMapping("/portal/userActivity/")
public class UserActivityController {
   @Resource
   private IActivityService activityService;
   @Resource
   private IMemberEnrollDetailService memberEnrollDetailService;
   @Resource
   private IUserFavoriteService userFavoriteService;
   @Resource
   private IContentService contentService;
   @Resource
   private UserViewService userViewService;
   @Resource
   private DestinationService destinationService;
   @Resource
   private IOrderService orderService;
   
 //只要进入这个controller就会先执行这个方法
	@ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.put("programNam","60");
	}
   /**
    * 获取活动列表
    * @param req
    * @param pageNum
    * @return
 * @throws JSONException 
 * @throws ParseException 
    */
   @SuppressWarnings("unchecked")
@RequestMapping(value="getActList",produces="text/html;charset=utf-8")
   public String getActList(HttpServletRequest req,@RequestParam(defaultValue="1",required=false) int pageNum,String state,String paystate) throws JSONException, ParseException{
	   LogUser log=(LogUser)req.getSession().getAttribute("logUser");
	   if(log==null){
		   return "redirect:/";
	   }
	   String memberCode="";
	   if(log!=null){
		   if(log.getCode()!=null){
			   memberCode=log.getCode();
		   }
	   }
	   String hql="select * FROM activity WHERE avaliable='1' AND code IN(SELECT code FROM `user_favorite` WHERE TYPE='"+UserFavorite.User_Fav_Activity+"' AND memberCode=?)";
	 
	   if(StringUtils.isNotBlank(state)&&StringUtils.isBlank(paystate)){
		   if("running".equals(state)){
			  hql+=" AND DATE_FORMAT(endDate,'%Y-%m-%d')>=DATE_FORMAT(NOW(),'%Y-%m-%d')";
		   }else if("end".equals(state)){
			   hql+=" AND DATE_FORMAT(endDate,'%Y-%m-%d')<=DATE_FORMAT(NOW(),'%Y-%m-%d')";
		   }
	   }else if(StringUtils.isNotBlank(paystate)&&StringUtils.isBlank(state)){
		   if("payed".equals(paystate)){
			   hql+=" AND isPay='1'";
		   }else if("unpay".equals(paystate)){
		       hql+=" AND isPay=0";
		   }
	   }else if(StringUtils.isNotBlank(state)&&StringUtils.isNotBlank(paystate)){
		   if("running".equals(state)&&"payed".equals(paystate)){
			  hql+=" AND  DATE_FORMAT(endDate,'%Y-%m-%d')>=DATE_FORMAT(NOW(),'%Y-%m-%d') and isPay='1'";
		   }else if("running".equals(state)&&"unpay".equals(paystate)){
			  hql+=" AND  DATE_FORMAT(endDate,'%Y-%m-%d')>=DATE_FORMAT(NOW(),'%Y-%m-%d') and isPay='0'";
		   }if("end".equals(state)&&"payed".equals(paystate)){
			  hql+=" AND  DATE_FORMAT(endDate,'%Y-%m-%d')<=DATE_FORMAT(NOW(),'%Y-%m-%d') and isPay='1'";
		   }else if("end".equals(state)&&"unpay".equals(paystate)){
			   hql+=" AND  DATE_FORMAT(endDate,'%Y-%m-%d')<=DATE_FORMAT(NOW(),'%Y-%m-%d') and isPay='0'";
		   }
	  }
	   hql+=" ORDER BY startDate DESC";
	   Pager pager=new Pager();
	    pager.setCurrentPage(pageNum);
	    pager.setPageSize(10);
	   List param=new ArrayList();
	    param.add(memberCode);
	   activityService.findPagerBySQL(hql, param, pager); 
	   List<Activity> list=pager.getDataList();
	   //如果状态为已上传
	   if("uploaded".equals(paystate)){
		   hql="select * FROM activity WHERE avaliable='1' AND code IN(SELECT code FROM `user_favorite` WHERE TYPE='"+UserFavorite.User_Fav_Activity+"' AND memberCode=?)";
		   if(StringUtils.isNotBlank(state)&&StringUtils.isBlank(paystate)){
			   List<Activity> relist=new ArrayList<Activity>();
			   if("running".equals(state)){
				  hql+=" AND DATE_FORMAT(endDate,'%Y-%m-%d')>=DATE_FORMAT(NOW(),'%Y-%m-%d')";
			   }else if("end".equals(state)){
				   hql+=" AND DATE_FORMAT(endDate,'%Y-%m-%d')<=DATE_FORMAT(NOW(),'%Y-%m-%d')";
			   }
			   hql+=" ORDER BY startDate DESC";
			   activityService.findPagerBySQL(hql, param, pager); 
			   List<Activity> l=pager.getDataList();
			   for(Activity al:l){
			    	 relist.add(al);
			   }
			   SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
			   String now=fmt.format(new Date());
			   for(Activity a:relist){
				   if(a.getEndDate()!=null){
					   String end=fmt.format(a.getEndDate());
					   if(fmt.parse(end).getTime()>=fmt.parse(now).getTime()){
						   a.setSummary("Run");
					   }else if(fmt.parse(end).getTime()<=fmt.parse(now).getTime()){
						   a.setSummary("End");
					   }
				   }
			   }
			   pager.setDataList(relist);
		   }else if(StringUtils.isNotBlank(paystate)&&StringUtils.isBlank(state)){
			   List<Activity> relist=new ArrayList<Activity>();
			   for(Activity a:list){
				 param.clear();
				 param.add(memberCode);
				 param.add(memberCode);
			     hql="SELECT * FROM activity WHERE avaliable='1' AND CODE IN(SELECT CODE FROM `user_favorite`  WHERE TYPE='activity' AND memberCode=?" 
                     +" AND CODE IN(SELECT activityCode FROM `activity_product` WHERE avaliable='1' AND memberCode=? AND activityCode='"+a.getCode()+"'))";
			     hql+=" ORDER BY startDate DESC";
			     activityService.findPagerBySQL(hql, param, pager); 
			     List<Activity> l=pager.getDataList();
			     for(Activity al:l){
			    	 relist.add(al);
			     }
			   }
			   SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
			   String now=fmt.format(new Date());
			   for(Activity a:relist){
				   if(a.getEndDate()!=null){
					   String end=fmt.format(a.getEndDate());
					   if(fmt.parse(end).getTime()>=fmt.parse(now).getTime()){
						   a.setSummary("Run");
					   }else if(fmt.parse(end).getTime()<=fmt.parse(now).getTime()){
						   a.setSummary("End");
					   }
				   }
			   }
			   pager.setDataList(relist);
		   }else if(StringUtils.isNotBlank(state)&&StringUtils.isNotBlank(paystate)){
			   if("running".equals(state)){
				   List<Activity> relist=new ArrayList<Activity>();
				   for(Activity a:list){
					 param.clear();
					 param.add(memberCode);
					 param.add(memberCode);
					 hql="SELECT * FROM activity WHERE avaliable='1' AND  DATE_FORMAT(endDate,'%Y-%m-%d')>=DATE_FORMAT(NOW(),'%Y-%m-%d') AND CODE IN(SELECT CODE FROM `user_favorite`  WHERE TYPE='activity' AND memberCode=?" 
	                     +" AND CODE IN(SELECT activityCode FROM `activity_product` WHERE avaliable='1' AND memberCode=? AND activityCode='"+a.getCode()+"'))";
				     hql+=" ORDER BY startDate DESC";
				     activityService.findPagerBySQL(hql, param, pager); 
				     List<Activity> l=pager.getDataList();
				     for(Activity al:l){
				    	 relist.add(al);
				     }
				   }
				   SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
				   String now=fmt.format(new Date());
				   for(Activity a:relist){
					   if(a.getEndDate()!=null){
						   String end=fmt.format(a.getEndDate());
						   if(fmt.parse(end).getTime()>=fmt.parse(now).getTime()){
							   a.setSummary("Run");
						   }else if(fmt.parse(end).getTime()<=fmt.parse(now).getTime()){
							   a.setSummary("End");
						   }
					   }
				   }
				   pager.setDataList(relist);
				  
			   }else if("end".equals(state)){
				   List<Activity> relist=new ArrayList<Activity>();
				   String  temp=hql;
				   for(Activity a:list){
					 hql=temp;
					 param.clear();
					 param.add(memberCode);
					 param.add(memberCode);
					 hql="SELECT * FROM activity WHERE avaliable='1' AND  DATE_FORMAT(endDate,'%Y-%m-%d')<=DATE_FORMAT(NOW(),'%Y-%m-%d') AND CODE IN(SELECT CODE FROM `user_favorite`  WHERE TYPE='activity' AND memberCode=?" 
	                     +" AND CODE IN(SELECT activityCode FROM `activity_product` WHERE avaliable='1' AND memberCode=? AND activityCode='"+a.getCode()+"'))";
				     hql+=" ORDER BY startDate DESC";
				     activityService.findPagerBySQL(hql, param, pager); 
				     List<Activity> l=pager.getDataList();
				     for(Activity al:l){
				    	 relist.add(al);
				     }
				   }
				   SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
				   String now=fmt.format(new Date());
				   for(Activity a:relist){
					   if(a.getEndDate()!=null){
						   String end=fmt.format(a.getEndDate());
						   if(fmt.parse(end).getTime()>=fmt.parse(now).getTime()){
							   a.setSummary("Run");
						   }else if(fmt.parse(end).getTime()<=fmt.parse(now).getTime()){
							   a.setSummary("End");
						   }
					   }
				   }
				   pager.setDataList(relist);
				  
			   }
		  }
	   }
	   
	   SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
	   String now=fmt.format(new Date());
	   for(Activity a:list){
		   if(a.getEndDate()!=null){
			   String end=fmt.format(a.getEndDate());
			   if(fmt.parse(end).getTime()>=fmt.parse(now).getTime()){
				   a.setSummary("Run");
			   }else if(fmt.parse(end).getTime()<=fmt.parse(now).getTime()){
				   a.setSummary("End");
			   }
		   }
	   }
		 //查询用户收藏数及回复评论数
	   	int favCount=userFavoriteService.favCount(memberCode);
	   	int repCount=contentService.getAllReply(memberCode);
	  //查询用户想去的景点数目
	  	 int cntWanna= userViewService.getViewCountByType(memberCode, UserView.User_View_Wanna);
	  	 //查询用户去过的景点数目
	  	 int cntGone=userViewService.getViewCountByType(memberCode,UserView.User_View_Gone);
	  	 //获取坐标
		  	List param2=new ArrayList();
			param2.add(memberCode);
			List<View> lis=userViewService.getViewXy(param2);
			for(View v:lis){
				if(v.getCode()!=null){
					String sq="SELECT * FROM user_view WHERE  viewCode=? AND memberCode=?";
					List<Object> pa=new ArrayList<Object>();
					pa.add(v.getCode());
					pa.add(memberCode);
					List<UserView> li=userViewService.findUserViewBySql(sq, pa);
					if(li.size()>0){
						UserView u=li.get(0);
						if(u!=null){
							if(u.getType()!=null){
								v.setWanaOrGne(u.getType());
							}
						}
					}
				}
			}
			Map<String,List<View>> map=new HashMap<String,List<View>>();
			for(View v:lis){
				if(v.getDestinationCode()!=null){
				  	Destination de=destinationService.findByCode(v.getDestinationCode());
				  	if(de!=null){
				  	  if(de.getDestinationName()!=null)
				  	     v.setDestinationName(de.getDestinationName());
				  	}
				}
				List temp=null;
				if(map.containsKey(v.getDestinationName())){
					temp=map.get(v.getDestinationName());
				}else{
					temp=new ArrayList();
					map.put(v.getDestinationName(), temp);
				}
			    temp.add(v);
			}
	   List<Activity> activityList=pager.getDataList();
	   for(Activity act:activityList){
		  Order order= orderService.getOrderByActCodeMemberCode(act.getCode(), memberCode);
		  if(order!=null){
			 act.setIsPay(order.getPayState());
		  }
	   }	
	   
	   req.setAttribute("locat",net.sf.json.JSONObject.fromObject(map));
	   req.setAttribute("cntWanna", cntWanna);
	   req.setAttribute("cntGone", cntGone);
	   req.setAttribute("favCount", favCount);
	   req.setAttribute("repCount", repCount);
	   req.setAttribute("pager", pager);
	   req.setAttribute("type", "acti");
	   req.setAttribute("payState", paystate);
	   req.setAttribute("state",state);
	   return "portal/app/usercenter/home";
   }
   /**
    * 获取活动列表
    * @param req
    * @param pageNum
    * @return
 * @throws JSONException 
 * @throws ParseException 
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value="getActListPage",produces="text/html;charset=utf-8")
   @ResponseBody
   public String getActListPage(HttpServletRequest req,Pager pager,String state,String paystate) throws JSONException, ParseException{
	   LogUser log=(LogUser)req.getSession().getAttribute("logUser");
	   if(log==null){
		   return "redirect:/";
	   }
	   String memberCode="";
	   if(log!=null){
		   if(log.getCode()!=null){
			   memberCode=log.getCode();
		   }
	   }
	   String hql="select * FROM activity WHERE avaliable='1' AND code IN(SELECT code FROM `user_favorite` WHERE TYPE='"+UserFavorite.User_Fav_Activity+"' AND memberCode=?)";
	 
	   if(StringUtils.isNotBlank(state)&&StringUtils.isBlank(paystate)){
		   if("running".equals(state)){
			  hql+=" AND DATE_FORMAT(endDate,'%Y-%m-%d')>=DATE_FORMAT(NOW(),'%Y-%m-%d')";
		   }else if("end".equals(state)){
			   hql+=" AND DATE_FORMAT(endDate,'%Y-%m-%d')<DATE_FORMAT(NOW(),'%Y-%m-%d')";
		   }
	   }else if(StringUtils.isNotBlank(state)){
		   if("running".equals(state)){
			  hql+=" AND  DATE_FORMAT(endDate,'%Y-%m-%d')>=DATE_FORMAT(NOW(),'%Y-%m-%d')";
		   }if("end".equals(state)){
			  hql+=" AND  DATE_FORMAT(endDate,'%Y-%m-%d')<DATE_FORMAT(NOW(),'%Y-%m-%d')";
		   }
	  }
	   hql+=" ORDER BY startDate DESC";
	  // Pager pager=new Pager();
	  //  pager.setCurrentPage(pageNum);
	    pager.setPageSize(10);
	   List param=new ArrayList();
	    param.add(memberCode);
	   activityService.findPagerBySQL(hql, param, pager); 
	   List<Activity> list=pager.getDataList();
	   //如果状态为已上传
	   if("uploaded".equals(paystate)){
		   hql="select * FROM activity WHERE avaliable='1' AND code IN(SELECT code FROM `user_favorite` WHERE TYPE='"+UserFavorite.User_Fav_Activity+"' AND memberCode=?)";
		   if(StringUtils.isNotBlank(state)&&StringUtils.isBlank(paystate)){
			   List<Activity> relist=new ArrayList<Activity>();
			   if("running".equals(state)){
				  hql+=" AND DATE_FORMAT(endDate,'%Y-%m-%d')>=DATE_FORMAT(NOW(),'%Y-%m-%d')";
			   }else if("end".equals(state)){
				   hql+=" AND DATE_FORMAT(endDate,'%Y-%m-%d')<DATE_FORMAT(NOW(),'%Y-%m-%d')";
			   }
			   hql+=" ORDER BY startDate DESC";
			   activityService.findPagerBySQL(hql, param, pager); 
			   List<Activity> l=pager.getDataList();
			   for(Activity al:l){
			    	 relist.add(al);
			   }
			   SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
			   String now=fmt.format(new Date());
			   for(Activity a:relist){
				   if(a.getEndDate()!=null){
					   String end=fmt.format(a.getEndDate());
					   if(fmt.parse(end).getTime()>=fmt.parse(now).getTime()){
						   a.setSummary("Run");
					   }else if(fmt.parse(end).getTime()<=fmt.parse(now).getTime()){
						   a.setSummary("End");
					   }
				   }
				   Order order= orderService.getOrderByActCodeMemberCode(a.getCode(), memberCode);
				   if(order!=null){
					 a.setIsPay(order.getPayState());
				   }
			   }
			   pager.setDataList(relist);
		   }else if(StringUtils.isNotBlank(paystate)&&StringUtils.isBlank(state)){
			   List<Activity> relist=new ArrayList<Activity>();
			   for(Activity a:list){
				 param.clear();
				 param.add(memberCode);
				 param.add(memberCode);
			     hql="SELECT * FROM activity WHERE avaliable='1' AND CODE IN(SELECT CODE FROM `user_favorite`  WHERE TYPE='"+UserFavorite.User_Fav_Activity+"' AND memberCode=?" 
                     +" AND CODE IN(SELECT activityCode FROM `activity_product` WHERE avaliable='1' AND memberCode=? AND activityCode='"+a.getCode()+"'))";
			     hql+=" ORDER BY startDate DESC";
			     activityService.findPagerBySQL(hql, param, pager); 
			     List<Activity> l=pager.getDataList();
			     for(Activity al:l){
			    	 relist.add(al);
			     }
			   }
			   SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
			   String now=fmt.format(new Date());
			   for(Activity a:relist){
				   if(a.getEndDate()!=null){
					   String end=fmt.format(a.getEndDate());
					   if(fmt.parse(end).getTime()>=fmt.parse(now).getTime()){
						   a.setSummary("Run");
					   }else if(fmt.parse(end).getTime()<=fmt.parse(now).getTime()){
						   a.setSummary("End");
					   }
				   }
				   Order order= orderService.getOrderByActCodeMemberCode(a.getCode(), memberCode);
				   if(order!=null){
					 a.setIsPay(order.getPayState());
				   }
			   }
			   pager.setDataList(relist);
		   }else if(StringUtils.isNotBlank(state)&&StringUtils.isNotBlank(paystate)){
			   if("running".equals(state)){
				   List<Activity> relist=new ArrayList<Activity>();
				   for(Activity a:list){
					 param.clear();
					 param.add(memberCode);
					 param.add(memberCode);
					 hql="SELECT * FROM activity WHERE avaliable='1' AND  DATE_FORMAT(endDate,'%Y-%m-%d')>=DATE_FORMAT(NOW(),'%Y-%m-%d') AND CODE IN(SELECT CODE FROM `user_favorite`  WHERE TYPE='"+UserFavorite.User_Fav_Activity+"' AND memberCode=?" 
	                     +" AND CODE IN(SELECT activityCode FROM `activity_product` WHERE avaliable='1' AND memberCode=? AND activityCode='"+a.getCode()+"'))";
				     hql+=" ORDER BY startDate DESC";
				     activityService.findPagerBySQL(hql, param, pager); 
				     List<Activity> l=pager.getDataList();
				     for(Activity al:l){
				    	 relist.add(al);
				     }
				   }
				   SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
				   String now=fmt.format(new Date());
				   for(Activity a:relist){
					   if(a.getEndDate()!=null){
						   String end=fmt.format(a.getEndDate());
						   if(fmt.parse(end).getTime()>=fmt.parse(now).getTime()){
							   a.setSummary("Run");
						   }else if(fmt.parse(end).getTime()<fmt.parse(now).getTime()){
							   a.setSummary("End");
						   }
					   }
					   Order order= orderService.getOrderByActCodeMemberCode(a.getCode(), memberCode);
					   if(order!=null){
						 a.setIsPay(order.getPayState());
					   }
				   }
				   pager.setDataList(relist);
				  
			   }else if("end".equals(state)){
				   List<Activity> relist=new ArrayList<Activity>();
				   String  temp=hql;
				   for(Activity a:list){
					 hql=temp;
					 param.clear();
					 param.add(memberCode);
					 param.add(memberCode);
					 hql="SELECT * FROM activity WHERE avaliable='1' AND  DATE_FORMAT(endDate,'%Y-%m-%d')<=DATE_FORMAT(NOW(),'%Y-%m-%d') AND CODE IN(SELECT CODE FROM `user_favorite`  WHERE TYPE='"+UserFavorite.User_Fav_Activity+"' AND memberCode=?" 
	                     +" AND CODE IN(SELECT activityCode FROM `activity_product` WHERE avaliable='1' AND memberCode=? AND activityCode='"+a.getCode()+"'))";
				     hql+=" ORDER BY startDate DESC";
				     activityService.findPagerBySQL(hql, param, pager); 
				     List<Activity> l=pager.getDataList();
				     for(Activity al:l){
				    	 relist.add(al);
				     }
				   }
				   SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
				   String now=fmt.format(new Date());
				   for(Activity a:relist){
					   if(a.getEndDate()!=null){
						   String end=fmt.format(a.getEndDate());
						   if(fmt.parse(end).getTime()>=fmt.parse(now).getTime()){
							   a.setSummary("Run");
						   }else if(fmt.parse(end).getTime()<fmt.parse(now).getTime()){
							   a.setSummary("End");
						   }
					   }
					   Order order= orderService.getOrderByActCodeMemberCode(a.getCode(), memberCode);
					   if(order!=null){
						 a.setIsPay(order.getPayState());
					   }
				   }
				   pager.setDataList(relist);
				  
			   }
		  }
	   }
	   
	   SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
	   String now=fmt.format(new Date());
	   List remoList=new ArrayList();
	   for(Activity a:list){
		   if(a.getEndDate()!=null){
			   String end=fmt.format(a.getEndDate());
			   if(fmt.parse(end).getTime()>=fmt.parse(now).getTime()){
				   a.setSummary("Run");
			   }else if(fmt.parse(end).getTime()<fmt.parse(now).getTime()){
				   a.setSummary("End");
			   }
		   }
	      Order order= orderService.getOrderByActCodeMemberCode(a.getCode(), memberCode);
		  if(order!=null){
			 a.setIsPay(order.getPayState());
		  }
		  if(StringUtils.isNotBlank(paystate)){
			   if("payed".equals(paystate)){
				  if(a.getIsPay()==0){
				    remoList.add(a);
				  }
			   }else if("unpay".equals(paystate)){
				   if(a.getIsPay()==1){
					   remoList.add(a);
				   }
			   }
		   }
	   }
	   if(StringUtils.isNotBlank(paystate)){
		   list.removeAll(remoList);
	   }
		 //查询用户收藏数及回复评论数
	   	int favCount=userFavoriteService.favCount(memberCode);
	   	int repCount=contentService.getAllReply(memberCode);
	  //查询用户想去的景点数目
	  	 int cntWanna= userViewService.getViewCountByType(memberCode, UserView.User_View_Wanna);
	  	 //查询用户去过的景点数目
	  	 int cntGone=userViewService.getViewCountByType(memberCode,UserView.User_View_Gone);
	  	 //获取坐标
		  	List param2=new ArrayList();
			param2.add(memberCode);
			List<View> lis=userViewService.getViewXy(param2);
			for(View v:lis){
				if(v.getCode()!=null){
					String sq="SELECT * FROM user_view WHERE  viewCode=? AND memberCode=?";
					List<Object> pa=new ArrayList<Object>();
					pa.add(v.getCode());
					pa.add(memberCode);
					List<UserView> li=userViewService.findUserViewBySql(sq, pa);
					if(li.size()>0){
						UserView u=li.get(0);
						if(u!=null){
							if(u.getType()!=null){
								v.setWanaOrGne(u.getType());
							}
						}
					}
				}
			}
			Map<String,List<View>> map=new HashMap<String,List<View>>();
			for(View v:lis){
				if(v.getDestinationCode()!=null){
				  	Destination de=destinationService.findByCode(v.getDestinationCode());
				  	if(de!=null){
				  	  if(de.getDestinationName()!=null)
				  	     v.setDestinationName(de.getDestinationName());
				  	}
				}
				List temp=null;
				if(map.containsKey(v.getDestinationName())){
					temp=map.get(v.getDestinationName());
				}else{
					temp=new ArrayList();
					map.put(v.getDestinationName(), temp);
				}
			    temp.add(v);
			}
			req.setAttribute("locat",net.sf.json.JSONObject.fromObject(map));
	  	req.setAttribute("cntWanna", cntWanna);
		req.setAttribute("cntGone", cntGone);
	   	req.setAttribute("favCount", favCount);
	   	req.setAttribute("repCount", repCount);
	  // req.setAttribute("pager", pager);
	   req.setAttribute("type", "acti");
	   req.setAttribute("payState", paystate);
	   req.setAttribute("state",state);
	   return new Gson().toJson(pager);
   }
    /**
     * 删除活动
     * @param req
     * @param code
     * @return
     * @throws JSONException
     * @throws ParseException 
     */
   @SuppressWarnings("unchecked")
@RequestMapping("deleteAct")
   public String deleteAct(HttpServletRequest req,String code) throws JSONException, ParseException{
	   //判断用户是否登录
	   LogUser loguser=(LogUser)req.getSession().getAttribute("logUser");
	   if(loguser==null){
		   return "redirect:/";
	   }
	   if(StringUtils.isBlank(code)){
		   return getActList(req,1,"","");
	   }
	   try {
		   //memberEnrollDetailService.deleteMemberEnrollDetailByActCodeMemberCode(code, loguser.getMemberCode());
		   String memberCode="";
		  	  if(loguser.getCode()!=null){
		  		  memberCode=loguser.getCode();
		  	  }
		  	  String sql="SELECT * FROM `user_favorite` WHERE memberCode=? AND code=? AND type=?";
	          List param=new ArrayList();
	           param.add(memberCode);
	           param.add(code);
	           param.add(UserFavorite.User_Fav_Activity);
	         List<UserFavorite> list=userFavoriteService.findListBySql(sql, param);
	         for(int i=0;i<list.size();i++){
	        	 try {
	        		 userFavoriteService.delete(list.get(i));
				} catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
					req.setAttribute("delflag", 0);
				}
	         }
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			return getActList(req,1,"","");
		}
	    
	   
	   return getActList(req,1,"","");
   }
   
   /**
    * 返回错误信息
    * @param msg
    * @return
    * @throws JSONException
    */
   private static String error(String msg) throws JSONException{
	   JSONObject obj=new JSONObject();
	   obj.put("status", 101);
	   obj.put("msg", msg);
	   return obj.toString();
   }
   
   /**
    * 返回成功信息
    * @param msg
    * @return
    * @throws JSONException
    */
   private static String success(String msg) throws JSONException{
	   JSONObject obj=new JSONObject();
	   obj.put("status", 1);
	   obj.put("msg", msg);
	   return obj.toString();
   }
public IActivityService getActivityService() {
	return activityService;
}
public void setActivityService(IActivityService activityService) {
	this.activityService = activityService;
}
}
