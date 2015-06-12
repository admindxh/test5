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
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.UserView;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IPostService;
import com.rimi.ctibet.web.service.IUserFavoriteService;
import com.rimi.ctibet.web.service.UserViewService;
@Controller
@RequestMapping({"/portal/userStrategy/","/member/userStrategy"})
public class UserStrategyController {
    @Resource
    private IPostService postService;
    @Resource
    private IUserFavoriteService userFavoriteService;
    @Resource
    private IContentService contentService;
    @Resource
    private UserViewService userViewService;
    @Resource
    private DestinationService destinationService;
    @Resource
    private IMemberService memberService;
  //只要进入这个controller就会先执行这个方法
	@ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.put("programNam","60");
	}
    /**
     * 查询用户添加的攻略
     * @param req
     * @param pageNum
     * @return
     * @throws JSONException 
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("getMyStrat")
    public String getMyStrat(HttpServletRequest req,@RequestParam(defaultValue="1",required=false) int pageNum,String proCode,String judgeState) throws JSONException{
    	//判断是否登录
    	LogUser loguser=(LogUser)req.getSession().getAttribute("logUser");
    	if(loguser==null){
    		return "redirect:/";
    	}
    	//查找攻略类型
    	String sql="SELECT * FROM programa WHERE pCode=(SELECT code FROM programa WHERE programaName=?)";
    	List para=new ArrayList();
    	 para.add("攻略类型");
    	
		List<Programa> listType=userFavoriteService.findStraType(sql, para);
    	req.setAttribute("straType", listType);
    	//返回查询条件
    	if(StringUtils.isNotBlank(proCode)){
    		String proName="";
	    	for(Programa p:listType){
	    		if(proCode.equals(p.getCode())){
	    			proName=p.getProgramaName();
	    			break;
	    		}
	    	}
	    	req.setAttribute("proName", proName);
    	}
    	if(StringUtils.isNotBlank(judgeState)){
    		String stateShow="";
    		if("0".equals(judgeState)){
    			stateShow="待 审 核";
    		}else if("1".equals(judgeState)){
    			stateShow="审核通过";
    		}else if("-1".equals(judgeState)){
    			stateShow="审核失败";
    		}
    		req.setAttribute("stateShow", stateShow);
    	}
    	
    	String memberCode="";
    	if(loguser!=null&&loguser.getCode()!=null){
    		memberCode=loguser.getCode();
    	}
    	//String hql="FROM Content WHERE avaliable='1' AND contentType='strategy' and createuserCode=:createuserCode";
    	Pager pager=new Pager();
    	 pager.setCurrentPage(pageNum);
    	 pager.setPageSize(10);
    	if(StringUtils.isBlank(proCode)&&StringUtils.isBlank(judgeState)){
 	    	contentService.getMemberStraByState(memberCode, "", "", pager);
 	    }else if(StringUtils.isNotBlank(proCode)&&StringUtils.isBlank(judgeState)){
 	    	contentService.getMemberStraByState(memberCode, "", proCode, pager);
 	    }else if(StringUtils.isBlank(proCode)&&StringUtils.isNotBlank(judgeState)){
            contentService.getMemberStraByState(memberCode, judgeState, "", pager);
 	    }else if(StringUtils.isNotBlank(proCode)&&StringUtils.isNotBlank(judgeState)){
 	    	contentService.getMemberStraByState(memberCode, judgeState, proCode, pager);
 	    }else{
 	    	contentService.getMemberStraByState(memberCode, "", "", pager);
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
		//更新Session
    	if(StringUtils.isNotBlank(loguser.getEmail())){
			 List<LogUser> list2=memberService.login(loguser.getEmail(), loguser.getPassword());
			   if(list2.size()>0){
				   LogUser logusers=list2.get(0);
				   req.getSession().setAttribute("logUser",logusers);
			   }
			  
		}
		if(StringUtils.isNotBlank(loguser.getMobile())){
			List<LogUser> list3=memberService.login(loguser.getMobile(),loguser.getPassword());
			   if(list3.size()>0){
				   LogUser logusers=list3.get(0);
				req.getSession().setAttribute("logUser",logusers);
			   }
			  
		} 
	  	req.setAttribute("cntWanna", cntWanna);
		req.setAttribute("cntGone", cntGone);
    	req.setAttribute("favCount", favCount);
    	req.setAttribute("repCount", repCount);
    	req.setAttribute("pager", pager);
    	req.setAttribute("type", "stra");
    	req.setAttribute("proCode", proCode);
    	req.setAttribute("judgeState", judgeState);
    	return "portal/app/usercenter/home";
    }
    /**
     * 查询用户添加的攻略
     * @param req
     * @param pageNum
     * @return
     * @throws JSONException 
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="getMyStratPage",produces="text/html;charset=UTF-8")
	@ResponseBody
    public String getMyStratPage(HttpServletRequest req,Pager pager,String proCode,String judgeState) throws JSONException{
    	//判断是否登录
    	LogUser loguser=(LogUser)req.getSession().getAttribute("logUser");
    	if(loguser==null){
    		return "redirect:/";
    	}
    	//查找攻略类型
    	String sql="SELECT * FROM programa WHERE pCode=(SELECT code FROM programa WHERE programaName=?)";
    	List para=new ArrayList();
    	 para.add("攻略类型");
    	
		List<Programa> listType=userFavoriteService.findStraType(sql, para);
    	req.setAttribute("straType", listType);
    	//返回查询条件
    	if(StringUtils.isNotBlank(proCode)){
    		String proName="";
	    	for(Programa p:listType){
	    		if(proCode.equals(p.getCode())){
	    			proName=p.getProgramaName();
	    			break;
	    		}
	    	}
	    	req.setAttribute("proName", proName);
    	}
    	if(StringUtils.isNotBlank(judgeState)){
    		String stateShow="";
    		if("0".equals(judgeState)){
    			stateShow="待 审 核";
    		}else if("1".equals(judgeState)){
    			stateShow="审核通过";
    		}else if("-1".equals(judgeState)){
    			stateShow="审核失败";
    		}
    		req.setAttribute("stateShow", stateShow);
    	}
    	
    	String memberCode="";
    	if(loguser!=null&&loguser.getCode()!=null){
    		memberCode=loguser.getCode();
    	}
    	//String hql="FROM Content WHERE avaliable='1' AND contentType='strategy' and createuserCode=:createuserCode";
    	//Pager pager=new Pager();
    	// pager.setCurrentPage(pageNum);
    	 pager.setPageSize(10);
    	if(StringUtils.isBlank(proCode)&&StringUtils.isBlank(judgeState)){
 	    	contentService.getMemberStraByState(memberCode, "", "", pager);
 	    }else if(StringUtils.isNotBlank(proCode)&&StringUtils.isBlank(judgeState)){
 	    	contentService.getMemberStraByState(memberCode, "", proCode, pager);
 	    }else if(StringUtils.isBlank(proCode)&&StringUtils.isNotBlank(judgeState)){
            contentService.getMemberStraByState(memberCode, judgeState, "", pager);
 	    }else if(StringUtils.isNotBlank(proCode)&&StringUtils.isNotBlank(judgeState)){
 	    	contentService.getMemberStraByState(memberCode, judgeState, proCode, pager);
 	    }else{
 	    	contentService.getMemberStraByState(memberCode, "", "", pager);
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
		//更新Session
    	if(StringUtils.isNotBlank(loguser.getEmail())){
			 List<LogUser> list2=memberService.login(loguser.getEmail(), loguser.getPassword());
			   if(list2.size()>0){
				   LogUser logusers=list2.get(0);
				   req.getSession().setAttribute("logUser",logusers);
			   }
			  
		}
		if(StringUtils.isNotBlank(loguser.getMobile())){
			List<LogUser> list3=memberService.login(loguser.getMobile(),loguser.getPassword());
			   if(list3.size()>0){
				   LogUser logusers=list3.get(0);
				req.getSession().setAttribute("logUser",logusers);
			   }
			  
		} 
	  	req.setAttribute("cntWanna", cntWanna);
		req.setAttribute("cntGone", cntGone);
    	req.setAttribute("favCount", favCount);
    	req.setAttribute("repCount", repCount);
    	//req.setAttribute("pager", pager);
    	req.setAttribute("type", "stra");
    	req.setAttribute("proCode", proCode);
    	req.setAttribute("judgeState", judgeState);
    	return new Gson().toJson(pager);
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value="straType",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String straType() throws JSONException{
    	JSONObject obj=new JSONObject();
    	//查找攻略类型
    	String sql="SELECT code,programaName FROM programa WHERE pCode=(SELECT code FROM programa WHERE programaName=?)";
    	List para=new ArrayList();
    	 para.add("攻略类型");
    	
		List<Programa> listType=userFavoriteService.findStraType(sql, para);
		List relist=new ArrayList();
		for(Programa p:listType){
			Map map=new HashMap();
			map.put("programaName", p.getProgramaName());
			map.put("code", p.getCode());
			relist.add(map);
		}
		obj.put("straType", relist);
		return obj.toString();
    }
    
    /**
     * 分类查询用户攻略
     * @param req
     * @param code 攻略分类ID
     * @param pageNum
     * @return
     *
    @SuppressWarnings("unchecked")
    @RequestMapping(value="getFavStraByIden",produces="text/html;charset=utf-8")
	public String getMyStraByIden(HttpServletRequest req,String code,@RequestParam(defaultValue="1",required=false) int pageNum){
    	
    	Pager pager=new Pager();
	   	 pager.setCurrentPage(pageNum);
	   	 pager.setPageSize(50);
	   	
	   	Map param=new HashMap();
	   	  param.put("programaCode", code);
	   	  param.put("createuserCode","1");
	   	  
	   	String hql="FROM Content WHERE avaliable='1' AND contentType='strategy' AND programaCode=:programaCode AND createuserCode=:createuserCode";
    	
	   	postService.findWithPagerByMap(hql, param, pager);
	   	return "";
    }*/
    
   
    /**
     * 删除攻略
     * @param req
     * @param code
     * @return
     * @throws JSONException 
     */
   @RequestMapping(value="deleteMyStra",produces="text/html;charset=utf-8")
   public String deleteMyStra(HttpServletRequest req,String code) throws JSONException{
	   if(StringUtils.isNotBlank(code)){
		   Content con=postService.getPostByCode(code);
		    con.setAvaliable(0);
		  try {
			  postService.updatePost(con);
			  req.setAttribute("delflag",1);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
				req.setAttribute("delflag",0);
			}
		   
	   }
	   return getMyStrat(req,1,"","");
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
