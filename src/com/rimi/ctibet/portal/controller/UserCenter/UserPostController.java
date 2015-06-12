package com.rimi.ctibet.portal.controller.UserCenter;

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
import org.hibernate.annotations.Parameter;
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
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IUserFavoriteService;
import com.rimi.ctibet.web.service.UserViewService;
@Controller
@RequestMapping("/portal/userPost/")
public class UserPostController {
    @Resource
    private IPostService postService;
    @Resource
    private IUserFavoriteService userFavoriteService;
    @Resource
    private IProgramaService programaService;
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
     * 获取所有帖子(支持按状态查询)
     * @param req
     * @param pageNum
     * @return
     * @throws JSONException 
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="getPostList",produces="text/html;charset=UTF-8")
	public String getPostList(HttpServletRequest req,@RequestParam(required=false,defaultValue="1")int pageNum,String proCode,String judgeState) throws JSONException{
    	//判断用户是否登录
    	LogUser loguser=(LogUser)req.getSession().getAttribute("logUser");
    	if(loguser==null){
    		return "redirect:/";
    	}
    	String memberCode="";
    	if(loguser!=null&&loguser.getCode()!=null){
    		memberCode=loguser.getCode();
    	}
	    Pager pager=new Pager();
	     pager.setPageSize(10);
	     pager.setCurrentPage(pageNum);
	    if(StringUtils.isBlank(proCode)&&StringUtils.isBlank(judgeState)){
	    	postService.getMemberPostByState(memberCode, "", "", pager);
	    }else if(StringUtils.isNotBlank(proCode)&&StringUtils.isBlank(judgeState)){
	    	postService.getMemberPostByState(memberCode, "", proCode, pager);
	    }else if(StringUtils.isBlank(proCode)&&StringUtils.isNotBlank(judgeState)){
	    	postService.getMemberPostByState(memberCode, judgeState,"", pager);
	    }else if(StringUtils.isNotBlank(proCode)&&StringUtils.isNotBlank(judgeState)){
	    	postService.getMemberPostByState(memberCode, judgeState,proCode, pager);
	    }else{
	    	postService.getMemberPostByState(memberCode, "", "", pager);
	    } 
	    List<Content> list=pager.getDataList();
	    req.setAttribute("pager", pager);
	    //返回用户帖子的所有分类
	    List param=new ArrayList();
	     // param.add(memberCode);
	    String sql="SELECT code,programaName FROM programa WHERE code IN(SELECT programaCode FROM `content` WHERE avaliable='1' AND contentType='post')";
		List<Programa> proList=programaService.findProByMemberCode(sql, param);
		//返回查询条件
		if(StringUtils.isNotBlank(proCode)){
			String proShow="";
			for(Programa p:proList){
				if(proCode.equals(p.getCode())){
					proShow=p.getProgramaName();
					break;
				}
			}
			req.setAttribute("proTypeShow", proShow);
		}
		if(StringUtils.isNotBlank(judgeState)){
			String proShow="";
			if("0".equals(judgeState)){
				proShow="待 审 核";
			}else if("1".equals(judgeState)){
				proShow="审核通过";
			}else if("-1".equals(judgeState)){
				proShow="审核失败";
			}
			req.setAttribute("proStateShow", proShow);
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
		req.setAttribute("locat",net.sf.json.JSONObject.fromObject(map));
	  	req.setAttribute("cntWanna", cntWanna);
		req.setAttribute("cntGone", cntGone);
    	req.setAttribute("favCount", favCount);
    	req.setAttribute("repCount", repCount);
		req.setAttribute("proList", proList);
		req.setAttribute("type", "post");
		req.setAttribute("postState", judgeState);
		req.setAttribute("postType", proCode);
	    return "portal/app/usercenter/home";
	}
    /**
     * 获取所有帖子(支持按状态查询)
     * @param req
     * @param pageNum
     * @return
     * @throws JSONException 
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="getPostListPage",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getPostListPage(HttpServletRequest req,Pager pager,String proCode,String judgeState) throws JSONException{
    	//判断用户是否登录
    	LogUser loguser=(LogUser)req.getSession().getAttribute("logUser");
    	if(loguser==null){
    		return "redirect:/";
    	}
    	String memberCode="";
    	if(loguser!=null&&loguser.getCode()!=null){
    		memberCode=loguser.getCode();
    	}
	    //Pager pager=new Pager();
        // pager.setCurrentPage(pageNum);
	     pager.setPageSize(10);
	    if(StringUtils.isBlank(proCode)&&StringUtils.isBlank(judgeState)){
	    	postService.getMemberPostByState(memberCode, "", "", pager);
	    }else if(StringUtils.isNotBlank(proCode)&&StringUtils.isBlank(judgeState)){
	    	postService.getMemberPostByState(memberCode, "", proCode, pager);
	    }else if(StringUtils.isBlank(proCode)&&StringUtils.isNotBlank(judgeState)){
	    	postService.getMemberPostByState(memberCode, judgeState,"", pager);
	    }else if(StringUtils.isNotBlank(proCode)&&StringUtils.isNotBlank(judgeState)){
	    	postService.getMemberPostByState(memberCode, judgeState,proCode, pager);
	    }else{
	    	postService.getMemberPostByState(memberCode, "", "", pager);
	    } 
	    List<Content> list=pager.getDataList();
	    //req.setAttribute("pager", pager);
	    //返回用户帖子的所有分类
	    List param=new ArrayList();
	     // param.add(memberCode);
	    String sql="SELECT code,programaName FROM programa WHERE code IN(SELECT programaCode FROM `content` WHERE avaliable='1' AND contentType='post')";
		List<Programa> proList=programaService.findProByMemberCode(sql, param);
		//返回查询条件
		if(StringUtils.isNotBlank(proCode)){
			String proShow="";
			for(Programa p:proList){
				if(proCode.equals(p.getCode())){
					proShow=p.getProgramaName();
					break;
				}
			}
			req.setAttribute("proTypeShow", proShow);
		}
		if(StringUtils.isNotBlank(judgeState)){
			String proShow="";
			if("0".equals(judgeState)){
				proShow="待 审 核";
			}else if("1".equals(judgeState)){
				proShow="审核通过";
			}else if("-1".equals(judgeState)){
				proShow="审核失败";
			}
			req.setAttribute("proStateShow", proShow);
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
		req.setAttribute("locat",net.sf.json.JSONObject.fromObject(map));
	  	req.setAttribute("cntWanna", cntWanna);
		req.setAttribute("cntGone", cntGone);
    	req.setAttribute("favCount", favCount);
    	req.setAttribute("repCount", repCount);
		req.setAttribute("proList", proList);
		req.setAttribute("type", "post");
		req.setAttribute("postState", judgeState);
		req.setAttribute("postType", proCode);
	    return new Gson().toJson(pager);
	}
	@RequestMapping(value="getProType",produces="text/html;charset=UTF-8")
	@ResponseBody
    public String getProType() throws JSONException{
		JSONObject obj=new JSONObject();
    	 List param=new ArrayList();
	     // param.add(memberCode);
	    String sql="SELECT code,programaName FROM programa WHERE code IN(SELECT programaCode FROM `content` WHERE avaliable='1' AND contentType='post')";
		List<Programa> proList=programaService.findProByMemberCode(sql, param);
		List relist=new ArrayList();
		for(Programa p:proList){
			Map map=new HashMap();
			map.put("programaName", p.getProgramaName());
			map.put("code", p.getCode());
			relist.add(map);
		}
		obj.put("postType",relist);
		return obj.toString();
    }

    /**
     * 获取用户发布的帖子
     * @param req
     * @param pageNum
     * @return
     *
    @SuppressWarnings("unchecked")
	@RequestMapping(value="getPubPost",produces="text/html;charset=UTF-8")
    public String getPubPost(HttpServletRequest req,@RequestParam(required=false,defaultValue="1")int pageNum){
    	String hql="from Content where createuserCode=:createuserCode and contentType='post' order by lastEditTime desc";
    	Pager pager=new Pager();
    	 pager.setPageSize(50);
    	 pager.setCurrentPage(pageNum);
    	 Map param=new HashMap();
     	   param.put("createuserCode", "===用户ID");
    	
    	postService.findWithPagerByMap(hql, param, pager);
    	req.setAttribute("pager", pager);
    	return "";
    }
    
    /**
     * 获取用户回复的帖子
     * @param req
     * @param pageNum
     * @return
     *
    @SuppressWarnings("unchecked")
	@RequestMapping(value="getReplyPost",produces="text/html;charset=UTF-8")
    public String getReplyPost(HttpServletRequest req,@RequestParam(required=false,defaultValue="1")int pageNum){
    	String hql="from Content where createuserCode=:createuserCode and contentType='reply' order by lastEditTime desc";
    	Pager pager=new Pager();
    	 pager.setPageSize(50);
    	 pager.setPageSize(pageNum);
    	 Map param=new HashMap();
   	       param.put("createuserCode", "1");
    	postService.findWithPagerByMap(hql, param, pager);
    	req.setAttribute("pager", pager);
    	return "";
    }
    
    
    /**
     * 获取用户收藏的帖子
     * @param req
     * @param pageNum
     * @return
     *
    @SuppressWarnings("unchecked")
    @RequestMapping(value="getFavPost",produces="text/html;charset=utf-8")
	public String getFavPost(HttpServletRequest req,@RequestParam(required=false,defaultValue="1")int pageNum){
    	String hql="select * FROM Content WHERE avaliable='1' AND contentType='post' AND createuserCode IN (SELECT memberCode FROM user_favorite WHERE memberCode=? AND TYPE='post') order by lastEditTime desc";
    	Pager pager=new Pager();
    	 pager.setPageSize(50);
    	 pager.setCurrentPage(pageNum);
    	List param=new ArrayList();
    	 param.add(1);
    	userFavoriteService.findFavbyPager(hql, param, pager);
    	req.setAttribute("pager", pager);
    	return "";
    }
    
    /**
     * 删除帖子
     * @param req
     * @param id
     * @return
     * @throws JSONException 
     */
    @RequestMapping("deletePost")
    public String deletePost(HttpServletRequest req,String id) throws JSONException{
    	if(StringUtils.isNotBlank(id)){
    		Content content=postService.getPostByCode(id);
    		try {
    			postService.deletePost(content);
    			 req.setAttribute("delflag",1);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
				 req.setAttribute("delflag",0);
			}
    	}
    	req.setAttribute("type", "post");
    	return getPostList(req,1,"","");
    }
    
    
    /**
     * 跳转到修改页
     * @param req
     * @param id
     * @return
     */
    public String editPost(HttpServletRequest req,String id){
    	if(StringUtils.isNotBlank(id)){
    		Content content=postService.getPostByCode(id);
    		req.setAttribute("content", content);
    	}
    	return "";
    }
    
    public String updatePost(HttpServletRequest req,String id,String programCode,String title,String content) throws JSONException{
    	LogUser log=(LogUser) req.getSession().getAttribute("LogUser");
    	if(log==null){
    		return error("请先登录");
    	}
    	if(StringUtils.isNotBlank(id)){
    		Content c=postService.getPostByCode(id);
    		c.setAuthorCode("-----作者");
    		c.setChangeuserCode("-----修改人id");
    		c.setProgramaCode(programCode);
    		c.setContent(content);
    		c.setContentTitle(title);
    		c.setLastEditTime(new Date());
    	  postService.updatePost(c);
    	}
    	
    	return "";
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
