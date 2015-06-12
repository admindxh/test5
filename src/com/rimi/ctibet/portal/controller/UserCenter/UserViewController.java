package com.rimi.ctibet.portal.controller.UserCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
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
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.UserView;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.UserViewService;

@Controller
@RequestMapping({"/portal/userView/","/member/userView"})
public class UserViewController {
    @Resource
    private UserViewService userViewService;
    @Resource
    private IContentService contentService;
    @Resource
    private DestinationService destinationService;
    
  //只要进入这个controller就会先执行这个方法
	@ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.put("programNam","60");
	}
    /**
     * 初始化用户足迹
     * @param req
     * @return
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("initial")
    public String initial(HttpServletRequest req,@RequestParam(defaultValue="1",required=false)int pageNum) throws JSONException{
    	 //判断用户是否登录
	  	  LogUser user=(LogUser)req.getSession().getAttribute("logUser");
	  	  if(user==null){
	  		  return "redirect:/";
	  	  }
	  	  String memberCode="";
	  	  String sql="";
	  	  if(user.getCode()!=null){
	  		  memberCode=user.getCode();
	  	  }
	  	  List param=new ArrayList();
	  	    param.add(memberCode);
	  	  //查询用户去过的目的地
	  	 sql="SELECT code,destinationName FROM `destination` WHERE CODE IN(SELECT areaCode FROM `user_view` WHERE avaliable='1' AND memberCode=?  and user_view.type='gone' )  AND avaliable='1' GROUP BY destinationName";
	  	 List destGone=userViewService.findListBySql(sql, param);
	  	 sql="SELECT code,destinationName FROM `destination` WHERE CODE IN(SELECT areaCode FROM `user_view` WHERE avaliable='1' AND memberCode=? and user_view.type='wanna')  AND avaliable='1' GROUP BY destinationName";
	  	 List destWant=userViewService.findListBySql(sql, param);
	  	 //查询用户想去的景点数目
	  	 int cntWanna= userViewService.getViewCountByType(memberCode, UserView.User_View_Wanna);
	  	 //查询用户去过的景点数目
	  	 int cntGone=userViewService.getViewCountByType(memberCode,UserView.User_View_Gone);
	  	 //查询用户想去的和去过的所有景点
	  	 sql="SELECT * FROM `tview` WHERE avaliable='1' AND CODE IN(SELECT viewCode FROM `user_view` WHERE avaliable='1' AND memberCode=?)";
	  	 Pager pager=new Pager();
	  	 pager.setCurrentPage(pageNum);
	  	// userViewService.findPagerBySql(sql, param, pager);
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
		List<View> list=userViewService.getViewXy(param2);
		for(View v:list){
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
    	  return "portal/app/usercenter/my_trucks";
    }
    
    
    
    /**
	 * 攻略 全部活动列表
	 * portal/activityController/getActivityIndexActivityList
     * @throws JSONException 
	 */
	@RequestMapping(value="getPageView", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getPageView(Pager pager,HttpServletRequest request,HttpServletResponse response) throws JSONException{
		 //判断用户是否登录
	  	  LogUser user=(LogUser)request.getSession().getAttribute("logUser");
	  	  if(user==null){
	  		  return "redirect:/";
	  	  }
	  	  String memberCode="";
	  	  String sql="";
	  	  if(user.getCode()!=null){
	  		  memberCode=user.getCode();
	  	  }
	  	    List param=new ArrayList();
  	        param.add(memberCode);
    	   sql ="SELECT id, avaliable, img,code, viewName, destinationCode,fakeGoneCount as goneCount,fakeWantCount as wantCount, viewImage, view_360full, viewIntroduce, ticketPrice, ticketUrl, isHaveGateTicket, createTime, lastEditTime, keyword, fakeGoneCount, fakeWantCount, checkNum, fakeCheckNum, replyNum, likeNum, fakeLikeNum, guide, traffic, notice, address, hdFullUrl, realSceneVideoUrl, linkUrl FROM `tview` WHERE avaliable='1'";
    	   sql = sql  + "AND CODE IN(SELECT viewCode FROM `user_view` WHERE avaliable='1'  AND memberCode= '"+memberCode+"'" ;
    	   String type =  request.getParameter("type");
    	   if (StringUtils.isNotBlank(type)) {
			   sql  =  sql  +" and    user_view.type='"+type+"'" ;
		   }
    	   sql = sql  +")";
    	   
    	   //pager.setPageSize(1);
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
    @RequestMapping("getViewByType")
    public String getViewByType(HttpServletRequest req,@RequestParam(defaultValue="1",required=false)int pageNum,String type,String proCode) throws JSONException{
    	//判断用户是否登录
	  	  LogUser user=(LogUser)req.getSession().getAttribute("logUser");
	  	  if(user==null){
	  		  return "redirect:/";
	  	  }
	  	  String memberCode="";
	  	  String sql="";
	  	  if(user.getCode()!=null){
	  		  memberCode=user.getCode();
	  	  }
	  	 
	  	Pager pager=new Pager();
	  	  pager.setCurrentPage(pageNum);
	  	List param=new ArrayList();
  	      param.add(memberCode);
	  	  if("wanna".equals(type)){
	  		  param.add(UserView.User_View_Wanna);
	  		  param.add(proCode);
	  		  sql="SELECT * FROM `tview` WHERE avaliable='1' AND CODE IN(SELECT viewCode FROM `user_view` WHERE avaliable='1' AND memberCode=? and type=?) AND destinationCode=?";
	  		  userViewService.findPagerBySql(sql, param, pager);
	  	  }
	  	  if("gone".equals(type)){
	  		  param.add(UserView.User_View_Gone);
	  		  param.add(proCode);
	  		  sql="SELECT * FROM `tview` WHERE avaliable='1' AND CODE IN(SELECT viewCode FROM `user_view` WHERE avaliable='1' AND memberCode=? and type=?)  AND destinationCode=?";
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
					String sq="SELECT * FROM user_view WHERE  viewCode=? AND memberCode=?";
					List<Object> pa=new ArrayList<Object>();
					pa.add(v.getCode());
					pa.add(memberCode);
					List<UserView> li=userViewService.findListBySql(sq, pa);
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
    	return "portal/app/usercenter/my_trucks";
    }
    
    /**
     * 获取景点坐标
     * @param memberCode
     * @return
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="getViewXy",produces="text/html;charset=utf-8")
    @ResponseBody
    public String getViewXy(String memberCode) throws JSONException{
    	JSONObject obj=new JSONObject();
    	if(StringUtils.isNotBlank(memberCode)){
    		List param=new ArrayList();
    		param.add(memberCode);
    		List<View> list=userViewService.getViewXy(param);
    		Map<String,List<View>> map=new HashMap<String,List<View>>();
    		for(View v:list){
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
    		obj.put("data",JSONArray.fromObject(map));
    		
    	}
    	
    	return obj.toString();
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
}
