package com.rimi.ctibet.portal.controller.UserCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.XssFilter;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.domain.UserView;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IActivityService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IPostService;
import com.rimi.ctibet.web.service.IUserFavoriteService;
import com.rimi.ctibet.web.service.UserViewService;

@Controller("othersPHome")
@RequestMapping({"/portal/othershome/","/member/othershow"})
public class OthersHomeController {
	@Resource
    private IPostService postService;
	@Resource
	private IContentService contentService;
	@Resource
	private IActivityService activityService;
	@Resource
	private IMemberService memberService;
	@Resource
	private IUserFavoriteService userFavoriteService;
	@Resource
	private UserViewService userViewService;
	 @Resource
	 private DestinationService destinationService;
	 
	//只要进入这个controller就会先执行这个方法
		@ModelAttribute
		public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
			modelMap.put("programNam","60");
		}
	/**
	 * 其他用户的攻略
	 * @param req
	 * @param pageNum
	 * @param memberCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("othersStra")
	public String othersStra(HttpServletRequest req,@RequestParam(required=false,defaultValue="1")int pageNum,String memberCode){
		if(StringUtils.isBlank(memberCode)){
			return "redirect:/";
		}
		//判断用户是否登录
	  	LogUser user=(LogUser)req.getSession().getAttribute("logUser");
	  	if(user!=null){
	  		if(StringUtils.isNotBlank(user.getCode())){
	  			if(memberCode.equals(user.getCode())){
	  				String path = req.getContextPath();
	  				String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/";
	  			   return "redirect:"+basePath+"member/userinfo/getAllMyMsg";
	  			}
	  		}
	  	}
		Pager pager=new Pager();
		pager.setCurrentPage(pageNum);
		pager.setPageSize(1);
		contentService.getMemberStraByState(memberCode, "1", "", pager);
		//查询用户资料
		MemberInfo mi= memberService.findMemberInfo(memberCode);
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
    	req.setAttribute("favCount",favCount);
    	req.setAttribute("repCount", repCount);
		req.setAttribute("mem", mi);
		req.setAttribute("pager", pager);
		req.setAttribute("type", "stra");
		return "portal/app/usercenter/others_home";
	}
	/**
	 * 其他用户的攻略
	 * @param req
	 * @param pageNum
	 * @param memberCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="othersStraPage",produces="text/html;charset=utf-8")
	@ResponseBody
	public String othersStraPage(HttpServletRequest req,Pager pager,String memberCode){
		if(StringUtils.isBlank(memberCode)){
			return "redirect:/";
		}
		//判断用户是否登录
	  	LogUser user=(LogUser)req.getSession().getAttribute("logUser");
	  	if(user!=null){
	  		if(StringUtils.isNotBlank(user.getCode())){
	  			if(memberCode.equals(user.getCode())){
	  				String path = req.getContextPath();
	  				String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path+"/";
	  			   return "redirect:"+basePath+"member/userinfo/getAllMyMsg";
	  			}
	  		}
	  	}
		//Pager pager=new Pager();
		//pager.setCurrentPage(pageNum);
		pager.setPageSize(10);
		contentService.getMemberStraByState(memberCode, "1", "", pager);
		//查询用户资料
		MemberInfo mi= memberService.findMemberInfo(memberCode);
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
    	req.setAttribute("favCount",favCount);
    	req.setAttribute("repCount", repCount);
		req.setAttribute("mem", mi);
		req.setAttribute("pager", pager);
		req.setAttribute("type", "stra");
		return new Gson().toJson(pager);
	}
	/**
	 * 其他用户帖子
	 * @param req
	 * @param pageNum
	 * @param memberCode
	 * @return
	 */
	@RequestMapping("othersPost")
	public String othersPost(HttpServletRequest req,@RequestParam(required=false,defaultValue="1")int pageNum,String memberCode){
		if(StringUtils.isBlank(memberCode)){
			return "redirect:/";
		}
		Pager pager=new Pager();
		pager.setCurrentPage(pageNum);
		pager.setPageSize(10);
		postService.getMemberPostByState(memberCode, "1", "", pager);
		//查询用户资料
		MemberInfo mi= memberService.findMemberInfo(memberCode);
		//查询用户收藏数及回复评论数
    	int favCount=userFavoriteService.favCount(memberCode);
    	int repCount=contentService.getAllReply(memberCode);
    	//查询用户想去的景点数目
	  	 int cntWanna= userViewService.getViewCountByType(memberCode, UserView.User_View_Wanna);
	  	 //查询用户去过的景点数目
	  	 int cntGone=userViewService.getViewCountByType(memberCode,UserView.User_View_Gone);
	  	req.setAttribute("cntWanna", cntWanna);
		req.setAttribute("cntGone", cntGone);
    	req.setAttribute("favCount",favCount);
    	req.setAttribute("repCount", repCount);
		req.setAttribute("mem", mi);
		req.setAttribute("pager", pager);
		req.setAttribute("type", "post");
		return "portal/app/usercenter/others_home";
	}
	/**
	 * 其他用户帖子
	 * @param req
	 * @param pageNum
	 * @param memberCode
	 * @return
	 */
	@RequestMapping(value="othersPostPage",produces="text/html;charset=utf-8")
	@ResponseBody
	public String othersPostPage(HttpServletRequest req,Pager pager,String memberCode){
		if(StringUtils.isBlank(memberCode)){
			return "redirect:/";
		}
		//Pager pager=new Pager();
		//pager.setCurrentPage(pageNum);
		pager.setPageSize(10);
		postService.getMemberPostByState(memberCode, "1", "", pager);
		//查询用户资料
		MemberInfo mi= memberService.findMemberInfo(memberCode);
		//查询用户收藏数及回复评论数
    	int favCount=userFavoriteService.favCount(memberCode);
    	int repCount=contentService.getAllReply(memberCode);
    	//查询用户想去的景点数目
	  	 int cntWanna= userViewService.getViewCountByType(memberCode, UserView.User_View_Wanna);
	  	 //查询用户去过的景点数目
	  	 int cntGone=userViewService.getViewCountByType(memberCode,UserView.User_View_Gone);
	  	req.setAttribute("cntWanna", cntWanna);
		req.setAttribute("cntGone", cntGone);
    	req.setAttribute("favCount",favCount);
    	req.setAttribute("repCount", repCount);
		req.setAttribute("mem", mi);
		req.setAttribute("pager", pager);
		req.setAttribute("type", "post");
		return new Gson().toJson(pager);
	}
	/**
	 * 其他用户活动
	 * @param req
	 * @param pageNum
	 * @param memberCode
	 * @return
	 */
	@RequestMapping("othersActi")
	public String othersActi(HttpServletRequest req,@RequestParam(required=false,defaultValue="1")int pageNum,String memberCode){
		if(StringUtils.isBlank(memberCode)){
			return "redirect:/";
		}
		Pager pager=new Pager();
		pager.setCurrentPage(pageNum);
		pager.setPageSize(10);
		List param=new ArrayList();
	    param.add(memberCode);
	    String hql="select * FROM activity WHERE avaliable='1' AND code IN(SELECT activityCode FROM (SELECT memberCode, activityCode FROM activity_product GROUP BY  activityCode, memberCode"
            +" UNION SELECT memberCode, activityCode FROM member_enroll_detail GROUP BY activityCode,memberCode) t  WHERE memberCode=?) ORDER BY startDate DESC";
	     activityService.findPagerBySQL(hql, param, pager); 
	   //查询用户资料
		MemberInfo mi= memberService.findMemberInfo(memberCode);
		//查询用户收藏数及回复评论数
    	int favCount=userFavoriteService.favCount(memberCode);
    	int repCount=contentService.getAllReply(memberCode);
    	//查询用户想去的景点数目
	  	 int cntWanna= userViewService.getViewCountByType(memberCode, UserView.User_View_Wanna);
	  	 //查询用户去过的景点数目
	  	 int cntGone=userViewService.getViewCountByType(memberCode,UserView.User_View_Gone);
	  	req.setAttribute("cntWanna", cntWanna);
		req.setAttribute("cntGone", cntGone);
    	req.setAttribute("favCount",favCount);
    	req.setAttribute("repCount", repCount);
		req.setAttribute("mem", mi);
		req.setAttribute("pager", pager);
		req.setAttribute("type", "acti");
		return "portal/app/usercenter/others_home";
	}
	@RequestMapping(value="othersActiPage",produces="text/html;charset=utf-8")
	@ResponseBody
	public String othersActiPage(HttpServletRequest req,Pager pager,String memberCode){
		if(StringUtils.isBlank(memberCode)){
			return "redirect:/";
		}
		//Pager pager=new Pager();
		//pager.setCurrentPage(pageNum);
		pager.setPageSize(10);
		List param=new ArrayList();
	    param.add(memberCode);
	    String hql="select * FROM activity WHERE avaliable='1' AND code IN(SELECT activityCode FROM (SELECT memberCode, activityCode FROM activity_product GROUP BY  activityCode, memberCode"
            +" UNION SELECT memberCode, activityCode FROM member_enroll_detail GROUP BY activityCode,memberCode) t  WHERE memberCode=?) ORDER BY startDate DESC";
	     activityService.findPagerBySQL(hql, param, pager); 
	   //查询用户资料
		MemberInfo mi= memberService.findMemberInfo(memberCode);
		//查询用户收藏数及回复评论数
    	int favCount=userFavoriteService.favCount(memberCode);
    	int repCount=contentService.getAllReply(memberCode);
    	//查询用户想去的景点数目
	  	 int cntWanna= userViewService.getViewCountByType(memberCode, UserView.User_View_Wanna);
	  	 //查询用户去过的景点数目
	  	 int cntGone=userViewService.getViewCountByType(memberCode,UserView.User_View_Gone);
	  	req.setAttribute("cntWanna", cntWanna);
		req.setAttribute("cntGone", cntGone);
    	req.setAttribute("favCount",favCount);
    	req.setAttribute("repCount", repCount);
		req.setAttribute("mem", mi);
		req.setAttribute("pager", pager);
		req.setAttribute("type", "acti");
		return new Gson().toJson(pager);
	}
	
	/**
	 * 其他用户的详细足迹
	 * @param req
	 * @param pageNum
	 * @param memberCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("othersTrack")
	public String othersTrack(HttpServletRequest req,@RequestParam(required=false,defaultValue="1")int pageNum,String memberCode){
		if(StringUtils.isBlank(memberCode)){
			 return "redirect:/";
		 }
		 memberCode = XssFilter.protectAgainstXSS(memberCode);
		 String sql="";
		 List param=new ArrayList();
	  	    param.add(memberCode);
		 //查询用户去过的目的地
	  	 sql="SELECT code,destinationName FROM `destination` WHERE CODE IN(SELECT areaCode FROM `user_view` WHERE avaliable='1' AND memberCode=?  and user_view.type='gone' ) AND avaliable='1' GROUP BY destinationName";
	  	 List destGone=userViewService.findListBySql(sql, param);
	  	 sql="SELECT code,destinationName FROM `destination` WHERE CODE IN(SELECT areaCode FROM `user_view` WHERE avaliable='1' AND memberCode=? and user_view.type='wanna') AND avaliable='1' GROUP BY destinationName";
	  	 List destWant=userViewService.findListBySql(sql, param);
	  	 //查询用户想去的景点数目
	  	 int cntWanna= userViewService.getViewCountByType(memberCode, UserView.User_View_Wanna);
	  	 //查询用户去过的景点数目
	  	 int cntGone=userViewService.getViewCountByType(memberCode,UserView.User_View_Gone);
	  	 //查询用户想去的和去过的所有景点
	  	 sql="SELECT * FROM `tview` WHERE avaliable='1' AND CODE IN(SELECT viewCode FROM `user_view` WHERE avaliable='1' AND memberCode=?)";
	  	 Pager pager=new Pager();
	  	 pager.setCurrentPage(pageNum);
	  	 pager.setPageSize(10);
	  	//userViewService.findPagerBySql(sql, param, pager);
	  	 String type  = "";
	  	 type = req.getParameter("type");
	  	 if (!StringUtils.isNotBlank(type)) {
			  type= "gone"; 
		}
	  	//获取用户发布的攻略数
	  	 int cntStra=contentService.getMemberStraNum(memberCode);
	  	 //获取坐标
	  	List param2=new ArrayList();
		param2.add(memberCode);
		List<View> lis=userViewService.getViewXy(param2);
		Map<String,List<View>> map=new HashMap<String,List<View>>();
		for(View v:lis){
			if(v.getDestinationCode()!=null){
			  	Destination de=destinationService.findByCode(v.getDestinationCode());
			  	v.setDestinationName(de.getDestinationName());
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
	  	 
	  	  req.setAttribute("cntStra", cntStra);
	  	  req.setAttribute("type",type);
	  	  req.setAttribute("destGone", destGone);
	  	  req.setAttribute("destWant", destWant);
	  	  req.setAttribute("cntWanna", cntWanna) ;
	  	  req.setAttribute("cntGone", cntGone) ;
	  	  req.setAttribute("memberCode", memberCode);
		return "portal/app/usercenter/others_trucks";
	}
	
	/**
	 * 其他用户发布的攻略
	 * @param pager
	 * @param request
	 * @param response
	 * @param memberCode
	 * @return
	 */
	@RequestMapping(value="othersStrat", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String othersStra(Pager pager,HttpServletRequest request,HttpServletResponse response,String memberCode){
		String sql="";
	  	  List param=new ArrayList();
	        param.add(memberCode);
  	   sql ="SELECT id, avaliable,img, code, viewName, destinationCode, goneCount, wantCount, viewImage, view_360full, viewIntroduce, ticketPrice, ticketUrl, isHaveGateTicket, createTime, lastEditTime, keyword, fakeGoneCount, fakeWantCount, checkNum, fakeCheckNum, replyNum, likeNum, fakeLikeNum, guide, traffic, notice, address, hdFullUrl, realSceneVideoUrl, linkUrl FROM `tview` WHERE avaliable='1'";
  	   sql = sql  + "AND CODE IN(SELECT viewCode FROM `user_view` WHERE avaliable='1'  AND memberCode= '"+memberCode+"'" ;
  	  String type =  request.getParameter("type");
  	   if (StringUtils.isNotBlank(type)) {
			   sql  =  sql  +" and    user_view.type='"+type+"'" ;
		   }
  	   sql = sql  +")";
  	   
  	   pager  =  	 userViewService.getPager(sql, pager);
		   pager.setTotalPage(pager.getTotalPage());
		  return new Gson().toJson(pager);
	}
	
	 /**
     * 通过类型查询相关的景点
     * @param req
     * @param pageNum
     * @return
     * @throws JSONException 
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("getViewByType")
    public String getViewByType(HttpServletRequest req,@RequestParam(defaultValue="1",required=false)int pageNum,String type,String memberCode,String proCode) throws JSONException{
    	if(StringUtils.isBlank(memberCode)){
    		return "redirect:/";
    	}
	  	String sql="";
	  	Pager pager=new Pager();
	  	  pager.setCurrentPage(pageNum);
	  	List param=new ArrayList();
  	      param.add(memberCode);
	  	  if("wanna".equals(type)){
	  		  param.add(UserView.User_View_Wanna);
	  		  sql="SELECT * FROM `tview` WHERE avaliable='1' AND CODE IN(SELECT viewCode FROM `user_view` WHERE avaliable='1' AND memberCode=? and type=?)";
	  		  userViewService.findPagerBySql(sql, param, pager);
	  	  }
	  	  if("gone".equals(type)){
	  		  param.add(UserView.User_View_Gone);
	  		  sql="SELECT * FROM `tview` WHERE avaliable='1' AND CODE IN(SELECT viewCode FROM `user_view` WHERE avaliable='1' AND memberCode=? and type=?)";
	  		  userViewService.findPagerBySql(sql, param, pager);
	  	  }
	  	  param.clear();
	  	  param.add(memberCode);
	  	 //查询用户去过的目的地
		  	 sql="SELECT code,destinationName FROM `destination` WHERE CODE IN(SELECT areaCode FROM `user_view` WHERE avaliable='1' AND memberCode=?  and user_view.type='gone' )";
		  	 List destGone=userViewService.findListBySql(sql, param);
		  	 sql="SELECT code,destinationName FROM `destination` WHERE CODE IN(SELECT areaCode FROM `user_view` WHERE avaliable='1' AND memberCode=? and user_view.type='wanna')";
		  	 List destWant=userViewService.findListBySql(sql, param);
		  	 //查询用户想去的景点数目
		  	 int cntWanna= userViewService.getViewCountByType(memberCode, UserView.User_View_Wanna);
		  	 //查询用户去过的景点数目
		  	 int cntGone=userViewService.getViewCountByType(memberCode,UserView.User_View_Gone);
		  	 //查询用户想去的和去过的所有景点
		  	 sql="SELECT * FROM `tview` WHERE avaliable='1' AND CODE IN(SELECT viewCode FROM `user_view` WHERE avaliable='1' AND memberCode=?)";
		  	
		  	 if (!StringUtils.isNotBlank(type)) {
				  type= "gone"; 
			}
		  	 //获取用户发布的攻略数
		  	 int cntStra=contentService.getMemberStraNum(memberCode);
		  	 
		  	  //获取坐标
		  	List param2=new ArrayList();
			param2.add(memberCode);
			List<View> list=userViewService.getViewXy(param2);
			for(View v:list){
				if(v.getCode()!=null){
					List<UserView> li=userViewService.findByProperty("viewCode", v.getCode());
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
			for(View v:list){
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
			
		  	 
		  	  req.setAttribute("memberCode", memberCode);
		  	  req.setAttribute("cntStra", cntStra);
		  	  req.setAttribute("type",type);
		  	  req.setAttribute("destGone", destGone);
		  	  req.setAttribute("destWant", destWant);
		  	  req.setAttribute("cntWanna", cntWanna) ;
		  	  req.setAttribute("cntGone", cntGone) ;
	  	  req.setAttribute("pager", pager);
    	return "portal/app/usercenter/others_trucks";
    }
}
