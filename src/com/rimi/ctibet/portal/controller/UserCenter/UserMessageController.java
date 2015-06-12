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
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.domain.Reply;
import com.rimi.ctibet.domain.SysMessage;
import com.rimi.ctibet.domain.UserSysMessage;
import com.rimi.ctibet.domain.UserView;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IReplyService;
import com.rimi.ctibet.web.service.ISysMessageService;
import com.rimi.ctibet.web.service.IUserFavoriteService;
import com.rimi.ctibet.web.service.IUserSysMessageService;
import com.rimi.ctibet.web.service.MutiImageService;
import com.rimi.ctibet.web.service.UserViewService;
@Controller
@RequestMapping({"/portal/userMessage/","member/userinfo"})
public class UserMessageController {
    @Resource
    private ISysMessageService sysMessageService;
    @Resource
    private IUserSysMessageService userSysMessageService;
    @Resource
    private IContentService contentService;
    @Resource
    private IUserFavoriteService userFavoriteService;	
    @Resource
    private IMemberService memberService;
    @Resource
    private UserViewService userViewService;
    @Resource
    private DestinationService destinationService;
    @Resource
    private IReplyService replyService;
    @Resource
    private MutiImageService mutiImageService;
	//只要进入这个controller就会先执行这个方法
	@ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.put("programNam","60");
	}
    /**
     * 获取用户所有信息
     * @param req
     * @param pageNum
     * @return
     * @throws JSONException 
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("getAllMyMsg")
    public String getAllMyMsg(HttpServletRequest req,@RequestParam(defaultValue="1",required=false)int pageNum) throws JSONException{
    	 //判断用户是否登录
	  	  LogUser user=(LogUser)req.getSession().getAttribute("logUser");
	  	  if(user==null){
	  		  return "redirect:/";
	  	  }
	  	 String memberCode="";
	  	 if(user.getCode()!=null){
	  		  memberCode=user.getCode();
	  	  }
    
    	
    	Pager pager =new Pager();
    	 pager.setCurrentPage(pageNum);
    	 pager.setPageSize(10);
    	List<Object> param=new ArrayList<Object>();
    	param.add(memberCode);
    	String sql="select * FROM sysmessage WHERE avaliable='1' AND (msgTo=? or msgTo='all') order by createDate desc";
    	sysMessageService.findByJdbcPager(sql, param, pager);
    	
    	//遍历记录判断是否已读
    	List<SysMessage> list=pager.getDataList();
    	for(SysMessage sm:list){
    		if(sm.getContentCode()!=null){
    			Content con=contentService.findByCodeWithoutVali(sm.getContentCode());
    			if(con!=null){
    				sm.setContentCode(con.getCode());
    				if(con.getContentTitle()!=null){
    					sm.setContentTitle(con.getContentTitle());
    					sm.setUrl(con.getUrl());
    				}
    				if(con.getContentType().contains("reply")){
    					List<Reply> rl= replyService.findByProperty("contentCode", con.getCode());
    					if(rl.size()>0){
    						Reply rep=rl.get(0);
    						if(rep!=null){
    							if(rep.getPostCode()!=null){
    								Content co=contentService.findByCodeWithoutVali(rep.getPostCode());
    								if(co!=null){
    									if(co.getContentTitle()!=null){
    										sm.setContentTitle(co.getContentTitle());
    										sm.setUrl(co.getUrl());
    									}
    								}else{
    							    	MutiImage lit= mutiImageService.getMutiImageByCode(rep.getPostCode());
    							    	if(lit!=null){
    							    		if(lit.getName()!=null){
    							    			sm.setContentTitle(lit.getName());
    							    		}
    							    		if(lit.getCoverUrl()!=null){
    							    			sm.setUrl(lit.getHyperlink());
    							    		}
    							    	}
    							     }
    							}
    						}
    					}
    				}
    			}
    		}
    		
    		if(sm.getCode()!=null&&sm.getType()!=null){
    			if(userSysMessageService.isReadOrNot(memberCode, sm.getCode(), sm.getType())){
                   //sm.setState("read");    	
                }else{
    			//向中间表插入数据
        		UserSysMessage usm=new UserSysMessage();
    	      	  usm.setMemberCode(user.getCode());
    	      	  usm.setMsgCode(sm.getCode());
    	      	  usm.setState("ok");
    	      	  usm.setType(sm.getType());
    	      	userSysMessageService.save(usm);
    		   }
    		}
    	}
    	
    	//更新Session
    	if(StringUtils.isNotBlank(user.getEmail())){
			 List<LogUser> list2=memberService.login(user.getEmail(), user.getPassword());
			   if(list2.size()>0){
				   LogUser loguser=list2.get(0);
				   req.getSession().setAttribute("logUser",loguser);
			   }
			  
		}
		if(StringUtils.isNotBlank(user.getMobile())){
			List<LogUser> list3=memberService.login(user.getMobile(),user.getPassword());
			   if(list3.size()>0){
				   LogUser loguser=list3.get(0);
				req.getSession().setAttribute("logUser",loguser);
			   }
			  
		}
    	
    	int unreadCount=sysMessageService.unReadCount(memberCode);
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
				List<UserView> li=userViewService.findUserViewBySql(sq, pa);//.findByProperty("viewCode", v.getCode());
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
			  	Destination de=destinationService.findByCodeWithoutVali(v.getDestinationCode());
			  	if(de!=null){
			  	  if(de.getDestinationName()!=null)
			  	     v.setDestinationName(de.getDestinationName());
			  	}else{
			  		continue;
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
		if (map!=null&&map.size()>=1) {
			
			req.setAttribute("locat",net.sf.json.JSONObject.fromObject(map));
		}
	  	req.setAttribute("cntWanna", cntWanna);
		req.setAttribute("cntGone", cntGone);
    	req.setAttribute("favCount", favCount);
    	req.setAttribute("repCount", repCount);
    	req.setAttribute("unReadCount", unreadCount);
    	req.setAttribute("pager",pager);
    	req.setAttribute("type", "mess");
    	return "portal/app/usercenter/home";
    }
    @SuppressWarnings("unchecked")
	@RequestMapping(value="getAllMyMsgPage",produces="text/html;charset=utf-8")
	@ResponseBody
    public String getAllMyMsgPage(HttpServletRequest req,Pager pager) throws JSONException{
    	 //判断用户是否登录
	  	  LogUser user=(LogUser)req.getSession().getAttribute("logUser");
	  	  if(user==null){
	  		  return "redirect:/";
	  	  }
	  	 String memberCode="";
	  	 if(user.getCode()!=null){
	  		  memberCode=user.getCode();
	  	  }
    
    	
    	//Pager pager =new Pager();
    	// pager.setCurrentPage(pageNum);
    	 pager.setPageSize(10);
    	List<Object> param=new ArrayList<Object>();
    	param.add(memberCode);
    	String sql="select * FROM sysmessage WHERE avaliable='1' AND (msgTo=? or msgTo='all') order by createDate desc";
    	sysMessageService.findByJdbcPager(sql, param, pager);
    	
    	//遍历记录判断是否已读
    	List<SysMessage> list=pager.getDataList();
    	for(SysMessage sm:list){
    		if(sm.getContentCode()!=null){
    			Content con=contentService.findByCodeWithoutVali(sm.getContentCode());
    			if(con!=null){
    				if(con.getContentTitle()!=null){
    					sm.setContentTitle(con.getContentTitle());
    					sm.setUrl(con.getUrl());
    				}
    				if(con.getContentType().contains("reply")){
    					List<Reply> rl= replyService.findByProperty("contentCode", con.getCode());
    					if(rl.size()>0){
    						Reply rep=rl.get(0);
    						if(rep!=null){
    							if(rep.getPostCode()!=null){
    								Content co=contentService.findByCodeWithoutVali(rep.getPostCode());
    								if(co!=null){
    									if(co.getContentTitle()!=null){
    										sm.setContentTitle(co.getContentTitle());
    										sm.setUrl(co.getUrl());
    									}
    								}else{
    							    	MutiImage lit= mutiImageService.getMutiImageByCode(rep.getPostCode());
    							    	if(lit!=null){
    							    		if(lit.getName()!=null){
    							    			sm.setContentTitle(lit.getName());
    							    		}
    							    		if(lit.getCoverUrl()!=null){
    							    			sm.setUrl(lit.getHyperlink());
    							    		}
    							    	}
    							     }
    							}
    						}
    					}
    				}
    			}
    		}
    		
    		if(sm.getCode()!=null&&sm.getType()!=null){
    			if(userSysMessageService.isReadOrNot(memberCode, sm.getCode(), sm.getType())){
                   //sm.setState("read");    	
                }else{
    			//向中间表插入数据
        		UserSysMessage usm=new UserSysMessage();
    	      	  usm.setMemberCode(user.getCode());
    	      	  usm.setMsgCode(sm.getCode());
    	      	  usm.setState("ok");
    	      	  usm.setType(sm.getType());
    	      	userSysMessageService.save(usm);
    		   }
    		}
    	}
    	
    	//更新Session
    	if(StringUtils.isNotBlank(user.getEmail())){
			 List<LogUser> list2=memberService.login(user.getEmail(), user.getPassword());
			   if(list2.size()>0){
				   LogUser loguser=list2.get(0);
				   req.getSession().setAttribute("logUser",loguser);
			   }
			  
		}
		if(StringUtils.isNotBlank(user.getMobile())){
			List<LogUser> list3=memberService.login(user.getMobile(),user.getPassword());
			   if(list3.size()>0){
				   LogUser loguser=list3.get(0);
				req.getSession().setAttribute("logUser",loguser);
			   }
			  
		}
    	
    	int unreadCount=sysMessageService.unReadCount(memberCode);
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
			  	Destination de=destinationService.findByCodeWithoutVali(v.getDestinationCode());
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
    	req.setAttribute("unReadCount", unreadCount);
    	//req.setAttribute("pager",pager);
    	req.setAttribute("type", "mess");
    	return new Gson().toJson(pager);
    }
    /**
     * 获取未读信息数目
     * @param req
     * @return
     * @throws JSONException
     */
    @RequestMapping(value="getUnReadCount",produces="text/html;charset=utf-8")
    @ResponseBody
    public String getUnReadCount(HttpServletRequest req) throws JSONException{
    	//判断用户是否登录
	  	  LogUser user=(LogUser)req.getSession().getAttribute("logUser");
	  	  if(user==null){
	  		  return "redirect:/";
	  	  }
    	int unreadCount=sysMessageService.unReadCount(user.getCode());
    	return success(String.valueOf(unreadCount));
    }
    
    /**
     * 根据分类查询用户消息
     * @param req
     * @param type
     * @param pageNum
     * @return
     * @throws JSONException 
     *
    @RequestMapping("getMyMsgByType")
    public String getMyMsgByType(HttpServletRequest req,String type,@RequestParam(defaultValue="1",required=false)int pageNum) throws JSONException{
    	if(StringUtils.isBlank(type)){
    		return error("请选择消息类型");
    	}
    	//判断用户是否登录
    	Pager pager =new Pager();
	   	 pager.setCurrentPage(pageNum);
	   	 pager.setPageSize(50);
	   	Map param=new HashMap();
	   	 param.put("msgTo", "1");
	   	 param.put("type", type);
	   	String hql="FROM SysMessage WHERE avaliable='1' AND msgTo=:msgTo AND type=:type order by createDate desc";
	   	sysMessageService.findWithPagerByMap(hql, param, pager);
	  //遍历记录判断是否已读
    	List<SysMessage> list=pager.getDataList();
    	for(SysMessage sm:list){
    		if(sm.getCode()!=null&&sm.getType()!=null){
    			if(userSysMessageService.isReadOrNot("", sm.getCode(), sm.getType())){
                   sm.setState("read");    				
    			}
    		}
    	}
    	return "";
    }
    
    
    /**
     * 用户点击阅读向中间表添加信息
     * @param req
     * @param msgCode
     * @return
     * @throws JSONException 
     */
    @RequestMapping(value="readMsg",produces="text/html;charset=utf-8")
    public String readMsg(HttpServletRequest req,String msgCode) throws JSONException{
    	LogUser lg=(LogUser)req.getSession().getAttribute("logUser");
    	if(lg==null){
    		return "redirect:/";
    	}
    	if(StringUtils.isBlank(msgCode)){
    		return error("未选择相关记录");
    	}
    	//判断用户是否登录
    	
    	SysMessage sms=sysMessageService.findByCode(msgCode);
    	if(sms==null){
    		return error("未找到相关消息");
    	}
    	if(!userSysMessageService.isReadOrNot(lg.getCode(), msgCode, sms.getType())){
	    	UserSysMessage usm=new UserSysMessage();
	    	  usm.setMemberCode(lg.getCode());
	    	  usm.setMsgCode(msgCode);
	    	  usm.setState("ok");
	    	  usm.setType(sms.getType());
	    	userSysMessageService.save(usm);
	    	return success("操作成功");
    	}
    	return error("");
    }
    
    /**
     * 删除消息
     * @param req
     * @param msgCode
     * @return
     * @throws JSONException
     */
    @RequestMapping("delMsg")
    public String delMsg(HttpServletRequest req,String msgCode) throws JSONException{
    	LogUser loguser=(LogUser)req.getSession().getAttribute("logUser");
    	if(loguser==null){
    		return getAllMyMsg(req,1);
    	}
    	if(StringUtils.isBlank(msgCode)){
    		return getAllMyMsg(req,1);
    	}
    	SysMessage sms=sysMessageService.findByCode(msgCode);
    	if(sms==null){
    		return getAllMyMsg(req,1);
    	}
        sms.setAvaliable(0);
        try {
        	sysMessageService.update(sms);
        	 req.setAttribute("delflag",1);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			 req.setAttribute("delflag",0);
		}
		
    	return getAllMyMsg(req,1);
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
	public ISysMessageService getSysMessageService() {
		return sysMessageService;
	}

	public void setSysMessageService(ISysMessageService sysMessageService) {
		this.sysMessageService = sysMessageService;
	}
	
	/**
	 * 读取消息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("readMessageInfo")
	public String readMessageInfo(HttpServletRequest req,@RequestParam(defaultValue="1",required=false)int pageNum){
		//判断用户是否登录
	  	  LogUser user=(LogUser)req.getSession().getAttribute("logUser");
	  	  if(user==null){
	  		  return "redirect:/";
	  	  }
	  	 String memberCode="";
	  	 if(user.getCode()!=null){
	  		  memberCode=user.getCode();
	  	  }
		Pager pager =new Pager();
		pager.setCurrentPage(pageNum);
	   	pager.setPageSize(10);
	   	List<Object> param=new ArrayList<Object>();
	   	param.add(memberCode);
	   	String sql="select * FROM sysmessage WHERE avaliable='1' AND (msgTo=? or msgTo='all') order by createDate desc";
	   	sysMessageService.findByJdbcPager(sql, param, pager);
	   	//遍历记录判断是否已读
	   	List<SysMessage> list=pager.getDataList();
	   	for(SysMessage sm:list){
	   		if(sm.getContentCode()!=null){
	   			Content con=contentService.findByCodeWithoutVali(sm.getContentCode());
	   			if(con!=null){
	   				sm.setContentCode(con.getCode());
	   				if(con.getContentTitle()!=null){
	   					sm.setContentTitle(con.getContentTitle());
	   					sm.setUrl(con.getUrl());
	   				}
	   				if(con.getContentType().contains("reply")){
	   					List<Reply> rl= replyService.findByProperty("contentCode", con.getCode());
	   					if(rl.size()>0){
	   						Reply rep=rl.get(0);
	   						if(rep!=null){
	   							if(rep.getPostCode()!=null){
	   								Content co=contentService.findByCodeWithoutVali(rep.getPostCode());
	   								if(co!=null){
	   									if(co.getContentTitle()!=null){
	   										sm.setContentTitle(co.getContentTitle());
	   										sm.setUrl(co.getUrl());
	   									}
	   								}else{
	   							    	MutiImage lit= mutiImageService.getMutiImageByCode(rep.getPostCode());
	   							    	if(lit!=null){
	   							    		if(lit.getName()!=null){
	   							    			sm.setContentTitle(lit.getName());
	   							    		}
	   							    		if(lit.getCoverUrl()!=null){
	   							    			sm.setUrl(lit.getHyperlink());
	   							    		}
	   							    	}
	   							     }
	   							}
	   						}
	   					}
	   				}
	   			}
   		}
   		
   		if(sm.getCode()!=null&&sm.getType()!=null){
   			if(userSysMessageService.isReadOrNot(memberCode, sm.getCode(), sm.getType())){
                  //sm.setState("read");    	
               }else{
   			  //向中间表插入数据
       		  UserSysMessage usm=new UserSysMessage();
   	      	  usm.setMemberCode(user.getCode());
   	      	  usm.setMsgCode(sm.getCode());
   	      	  usm.setState("ok");
   	      	  usm.setType(sm.getType());
   	      	  userSysMessageService.save(usm);
   		     }
   		  }
   	   }
	   return "";
	}
}
