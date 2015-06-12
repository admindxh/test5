package com.rimi.ctibet.portal.controller.UserCenter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.rimi.ctibet.domain.GroupTravel;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.domain.Reply;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IGroupTravelService;
import com.rimi.ctibet.web.service.IMerchantService;
import com.rimi.ctibet.web.service.IReplyService;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.MutiImageService;
@Controller
@RequestMapping("/portal/userReply/")
public class UserReplyController {
   @Resource
   private IReplyService replyService;
   @Resource
   private IContentService contentService;
   @Resource
   private MutiImageService mutiImageService;
   @Resource
   private IViewService viewService;
   @Resource
   private IMerchantService merchantService;
   @Resource
   private IGroupTravelService groupTravelService;
   
    //只要进入这个controller就会先执行这个方法
	@ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.put("programNam","60");
	}
   @SuppressWarnings("unchecked")
   @RequestMapping("getUserReply")
   public String getUserReply(HttpServletRequest req,@RequestParam(defaultValue="1",required=false)int pageNum,String type) throws JSONException{
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
	  if(StringUtils.isBlank(type)){
		  type="post";
	  }
	  Pager pager=new Pager();
	    pager.setCurrentPage(pageNum);
	    pager.setPageSize(6);
	   List param=new ArrayList();
	    param.add(memberCode);
	   List<Content> relist=null;
	   if("post".equals(type)){
		  sql ="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND contentType=? order by createTime desc";
		  param.add(Content.CONTENTTYPE_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  relist= buildData(list);
	   }
	   if("stra".equals(type)){
		  sql="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND contentType=? order by createTime desc"; 
		  param.add(Content.DETAIL_STRATEGY_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  relist= buildData(list);
	   }
	   if("read".equals(type)){
	      sql="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND (contentType=? or contentType=?) order by createTime desc"; 
		  param.add(Content.DETAIL_READ_TIBET_CULTURE_REPLY);
		  param.add(Content.DETAIL_READ_TIBET_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  relist= buildData(list);
	   }
	   if("look".equals(type)){
		  sql="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND (contentType=? or contentType=?) order by createTime desc"; 
		  param.add(Content.DETAIL_VEDIO_REPLY);
		  param.add(Content.DETAIL_PICTURE_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  relist= buildData(list); 
	   }
	   if("view".equals(type)){
		  sql="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND contentType=? order by createTime desc"; 
		  param.add(Content.DETAIL_VIEW_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  relist= buildData(list); 
	   }
	   if("merc".equals(type)){
		  sql="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND contentType=? order by createTime desc"; 
		  param.add(Content.DETAIL_MERCHANT_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  relist= buildData(list); 
	   }
	   if("tour".equals(type)){
		  sql="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND contentType=? order by createTime desc"; 
		  param.add(Content.DETAIL_TOUR_GROUP_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  relist=buildData(list); 
	   }
	   pager.setDataList(relist);
	   req.setAttribute("pager", pager);
	   req.setAttribute("classHolder", type);
	   return "portal/app/usercenter/comment_reply";
   }
   /**
    * 分页
    * @param req
    * @param pageNum
    * @param type
    * @return
    * @throws JSONException
    */
   @SuppressWarnings("unchecked")
@RequestMapping(value="getUserReplyPage", produces = "text/html; charset=utf-8")
   @ResponseBody
   public String getUserReplyPage(HttpServletResponse response,HttpServletRequest req,Pager pager,String type) throws JSONException{
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
	  if(StringUtils.isBlank(type)){
		  type="post";
	  }
	 // Pager pager=new Pager();
	 //   pager.setCurrentPage(pageNum);
	    pager.setPageSize(6);
	   List param=new ArrayList();
	    param.add(memberCode);
	   List<Content> relist=null;
	   if("post".equals(type)){
		  sql ="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND contentType=? order by createTime desc";
		  param.add(Content.CONTENTTYPE_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  pager.setTotalPage(pager.getTotalPage());
		  relist= buildData(list);
	   }
	   if("stra".equals(type)){
		  sql="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND contentType=? order by createTime desc"; 
		  param.add(Content.DETAIL_STRATEGY_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  relist= buildData(list);
	   }
	   if("read".equals(type)){
	      sql="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND (contentType=? or contentType=?) order by createTime desc"; 
		  param.add(Content.DETAIL_READ_TIBET_CULTURE_REPLY);
		  param.add(Content.DETAIL_READ_TIBET_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  relist= buildData(list);
	   }
	   if("look".equals(type)){
		  sql="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND (contentType=? or contentType=?) order by createTime desc"; 
		  param.add(Content.DETAIL_VEDIO_REPLY);
		  param.add(Content.DETAIL_PICTURE_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  relist= buildData(list); 
	   }
	   if("view".equals(type)){
		  sql="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND contentType=? order by createTime desc"; 
		  param.add(Content.DETAIL_VIEW_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  relist= buildData(list); 
	   }
	   if("merc".equals(type)){
		  sql="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND contentType=? order by createTime desc"; 
		  param.add(Content.DETAIL_MERCHANT_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  relist= buildData(list); 
	   }
	   if("tour".equals(type)){
		  sql="SELECT * FROM content WHERE avaliable='1' AND state='1' AND createuserCode =? AND contentType=? order by createTime desc"; 
		  param.add(Content.DETAIL_TOUR_GROUP_REPLY);
		  contentService.getMyReplyByType(sql, param, pager);
		  List<Content> list=pager.getDataList();
		  relist=buildData(list); 
	   }
	   pager.setDataList(relist);
	   //req.setAttribute("pager", pager);
	   req.setAttribute("classHolder", type);
	   return new Gson().toJson(pager);
   }
   /**
    * 处理返回数据，添加回复的上级标题
    * @param list
    * @return
    */
   public List<Content> buildData(List<Content> list){
	   for(Content c:list){
		  if(c.getCode()!=null){
			  List<Reply> li=replyService.findByProperty("contentCode", c.getCode());
			  if(li.size()>0){
				  Reply reply=li.get(0);
				  if(reply!=null&&reply.getPostCode()!=null){
					 Content con=contentService.findByCode(reply.getPostCode());
				     if(con!=null){
				    	 if(con.getContentTitle()!=null){
				    	    c.setContentTitle(con.getContentTitle());
				    	 }
				    	 if(con.getUrl()!=null){
				    		 c.setUrl(con.getUrl());
				    	 }
				     }else{
				    	 if(c.getContentType()!=null){
							 if(Content.DETAIL_VIEW_REPLY.equals(c.getContentType())){
								View view= viewService.findByCode(reply.getPostCode());
								if(view!=null){
									if(view.getViewName()!=null){
										c.setContentTitle(view.getViewName());
									}
									if(view.getLinkUrl()!=null){
										c.setUrl(view.getLinkUrl());
									}
								}
							 }
							 if(Content.DETAIL_MERCHANT_REPLY.equals(c.getContentType())){
								 List<Merchant> mer=merchantService.findbypro("code", reply.getPostCode());
								 if(mer.size()>0){
									 Merchant m=mer.get(0);
									 if(m!=null){
										 if(m.getMerchantName()!=null)
										 c.setContentTitle(m.getMerchantName());
										 if(m.getUrl()!=null)
										 c.setUrl(m.getUrl());
									 }
								 }
							 }
							 if(Content.DETAIL_TOUR_GROUP_REPLY.equals(c.getContentType())){
								 GroupTravel gt=groupTravelService.getGroupTravelByCode(reply.getPostCode());
								 if(gt!=null){
									 if(gt.getName()!=null)
									 c.setContentTitle(gt.getName());
									 if(gt.getUrl()!=null)
									 c.setUrl(gt.getUrl());
								 }
							 }
						 }
				    	MutiImage lit= mutiImageService.getMutiImageByCode(reply.getPostCode());
				    	if(lit!=null){
				    		if(lit.getName()!=null){
				    			c.setContentTitle(lit.getName());
				    		}
				    		if(lit.getCoverUrl()!=null){
				    			c.setUrl(lit.getHyperlink());
				    		}
				    	}
				     }
				 }
			  }
		  }
	  }
	  return list;
   }
   
   /**
    * 删除评论回复
    * @param req
    * @param code
    * @param type
    * @return
    * @throws JSONException
    */
   @RequestMapping(value="delReply",produces="text/html;charset=utf-8")
   @ResponseBody
   public String delReply(HttpServletRequest req,String code,String type) throws JSONException{
	  JSONObject obj=new JSONObject();
	  //判断用户是否登录
	  LogUser user=(LogUser)req.getSession().getAttribute("logUser");
	  if(user==null){
		  obj.put("delflag", -110);
		  return obj.toString();
	  }
	  String memberCode="";
	  if(user.getCode()!=null){
		  memberCode=user.getCode();
	  }
	  try {
		  contentService.deleteByCode(code);
		  List<Reply> li=replyService.findByProperty("contentCode", code);
		  for(int i=0;i<li.size();i++){
			  replyService.delete(li.get(i));
		  }
		  obj.put("delflag", 1);
	 } catch (Exception e) {
		e.printStackTrace();// TODO: handle exception
		obj.put("delflag", -1);
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
