package com.rimi.ctibet.portal.controller.UserCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.MerchantType;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.UserFavorite;
import com.rimi.ctibet.web.service.IGroupTravelService;
import com.rimi.ctibet.web.service.IMerchantService;
import com.rimi.ctibet.web.service.IMerchantTypeService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IUserFavoriteService;
import com.rimi.ctibet.web.service.MutiImageService;
@Controller
@RequestMapping("/portal/userFavorite/")
public class UserFavoriteController extends BaseController{
    @Resource
    private IUserFavoriteService userFavoriteService;
    @Resource
    private IPraiseService praiseService;
    @Resource
    private IProgramaService programaService;
    @Resource
    private IMerchantTypeService merchantTypeService;
    @Resource
    private IMerchantService merchantService;
    @Resource
    private MutiImageService mutiImageService;
    @Resource
    private IGroupTravelService groupTravelService;
    
  //只要进入这个controller就会先执行这个方法
	@ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.put("programNam","60");
	}
    
    @SuppressWarnings("unchecked")
	@RequestMapping("getUserFav")
    public String getUserFav(HttpServletRequest req,@RequestParam(defaultValue="1",required=false) int pageNum,String type) throws JSONException{
    	  //判断用户是否登录
	  	  LogUser user=(LogUser)req.getSession().getAttribute("logUser");
	  	  if(user==null){
	  		  return "redirect:/";
	  	  }
	  	  String memberCode="";
	  	  String hql="";
	  	  if(user.getCode()!=null){
	  		  memberCode=user.getCode();
	  	  }
	  	  if(StringUtils.isBlank(type)){
	  		  type="post";
	  	  }
	  	  Pager pager=new Pager();
	  	    pager.setCurrentPage(pageNum);
	  	    pager.setPageSize(10);
	  	  List param=new ArrayList();
    	   
    	   
	  	  if("post".equals(type)){
		  		hql="SELECT c.*,f.joinTime FROM content c,user_favorite f  WHERE c.code=f.code AND avaliable='1' AND contentType=? AND c.code IN (SELECT code FROM user_favorite WHERE memberCode=? AND TYPE=?) GROUP BY c.code  order by createTime desc"; 
		  		param.add(Content.CONTENTTYPE_POST);
		  		param.add(memberCode);
		  		param.add(UserFavorite.User_Fav_Post);
		  	 userFavoriteService.findFavbyPager(hql, param, pager);
	  	  }
	  	if("stra".equals(type)){
	  		hql="SELECT c.*,f.joinTime FROM content c,user_favorite f WHERE c.code=f.code AND avaliable='1' AND contentType=? AND c.code IN (SELECT code FROM user_favorite WHERE memberCode=? AND TYPE=?) GROUP BY c.code  order by createTime desc"; 
	  		param.add(Content.CONTENTTYPE_STRATEGY);
	  		param.add(memberCode);
	  		param.add(UserFavorite.User_Fav_Stra);
	  		userFavoriteService.findFavbyPager(hql, param, pager);
  	    }
	  	if("read".equals(type)){
	  		hql="SELECT c.*,f.joinTime FROM content c,user_favorite f WHERE c.code=f.code AND avaliable='1' AND (contentType like '1___' or contentType like '2___')AND c.code IN (SELECT code FROM user_favorite WHERE memberCode=? AND TYPE=?) GROUP BY c.code  order by createTime desc";
	  		//param.put("contentType", Content.CONTENTTYPE_STRATEGY);
	  		param.add(memberCode);
	  		param.add(UserFavorite.User_Fav_Read);
	  		userFavoriteService.findFavbyPager(hql, param, pager);
	  	}
	  	if("look".equals(type)){
	  		//看西藏 视频、图集
	  		hql="SELECT * from( SELECT c.contentTitle AS contentTitle,c.code AS code,createTime AS createTime,c.url AS url,joinTime FROM content c,user_favorite f WHERE c.code=f.code AND c.avaliable='1' AND c.contentType LIKE '3___' AND c.code IN (SELECT code FROM user_favorite WHERE memberCode=? AND TYPE=?) "
					+"UNION "
					+"SELECT NAME AS contentTitle,m.code AS code,createTime AS createTime,hyperlink AS url,joinTime FROM `mutiimage` m,user_favorite f WHERE m.code=f.code AND m.avaliable='1' and m.code IN(SELECT code FROM `user_favorite` WHERE memberCode=? AND TYPE=?))t GROUP BY code  order by createTime desc";
	  		param.add(memberCode);
	  		param.add(UserFavorite.User_Fav_See);
	  		param.add(memberCode);
	  		param.add(UserFavorite.User_Fav_Image);
	  		userFavoriteService.findFavbyPager(hql, param, pager);
	  	}
	  	if("merc".equals(type)){
	  		hql="SELECT c.*,f.joinTime FROM merchant c,user_favorite f WHERE c.code=f.code AND avaliable='1' AND c.code IN(SELECT code FROM user_favorite WHERE TYPE=? AND memberCode=?) GROUP BY c.code  order by createTime desc";
	  		param.add(UserFavorite.User_Fav_Merc);
	  		param.add(memberCode);
	  		//userFavoriteService.findFavbyPager(hql, param, pager);
	  		merchantService.findListByPager(hql, param, pager);
	  	}
	  	
	  	if("tour".equals(type)){
	  		//团游
	  		hql="SELECT c.*,f.joinTime FROM `grouptravel` c,user_favorite f WHERE c.code=f.code AND c.code IN(SELECT code FROM `user_favorite` WHERE memberCode=? AND TYPE=?) GROUP BY c.code  order by createTime desc";
	  		param.add(memberCode);
	  		param.add(UserFavorite.User_Fav_Group);
	  		groupTravelService.findListByPager(hql, param, pager);
	  	}
	  	req.setAttribute("pager", pager);
	  	req.setAttribute("classHolder", type);
    	return "portal/app/usercenter/my_favorite";
    }
    
    /**
     * 分页
     * @param req
     * @param pager
     * @param type
     * @return
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="getUserFavPage",produces="text/html;charset=utf-8")
    @ResponseBody
    public String getUserFavPage(HttpServletRequest req,Pager pager,String type) throws JSONException{
    	  //判断用户是否登录
	  	  LogUser user=(LogUser)req.getSession().getAttribute("logUser");
	  	  if(user==null){
	  		  return "redirect:/";
	  	  }
	  	  String memberCode="";
	  	  String hql="";
	  	  if(user.getCode()!=null){
	  		  memberCode=user.getCode();
	  	  }
	  	  if(StringUtils.isBlank(type)){
	  		  type="post";
	  	  }
	  	 // Pager pager=new Pager();
	  	 //   pager.setCurrentPage(pageNum);
	  	    pager.setPageSize(10);
	  	  List param=new ArrayList();
    	   
    	   
	  	  if("post".equals(type)){
		  		hql="SELECT c.*,f.joinTime FROM content c,user_favorite f  WHERE c.code=f.code AND avaliable='1' AND contentType=? AND c.code IN (SELECT code FROM user_favorite WHERE memberCode=? AND TYPE=?) GROUP BY c.code  order by createTime desc"; 
		  		param.add(Content.CONTENTTYPE_POST);
		  		param.add(memberCode);
		  		param.add(UserFavorite.User_Fav_Post);
		  	 userFavoriteService.findFavbyPager(hql, param, pager);
	  	  }
	  	if("stra".equals(type)){
	  		hql="SELECT c.*,f.joinTime FROM content c,user_favorite f WHERE c.code=f.code AND avaliable='1' AND contentType=? AND c.code IN (SELECT code FROM user_favorite WHERE memberCode=? AND TYPE=?) GROUP BY c.code  order by createTime desc"; 
	  		param.add(Content.CONTENTTYPE_STRATEGY);
	  		param.add(memberCode);
	  		param.add(UserFavorite.User_Fav_Stra);
	  		userFavoriteService.findFavbyPager(hql, param, pager);
  	    }
	  	if("read".equals(type)){
	  		hql="SELECT c.*,f.joinTime FROM content c,user_favorite f WHERE c.code=f.code AND avaliable='1' AND (contentType like '1___' or contentType like '2___')AND c.code IN (SELECT code FROM user_favorite WHERE memberCode=? AND TYPE=?) GROUP BY c.code  order by createTime desc";
	  		//param.put("contentType", Content.CONTENTTYPE_STRATEGY);
	  		param.add(memberCode);
	  		param.add(UserFavorite.User_Fav_Read);
	  		userFavoriteService.findFavbyPager(hql, param, pager);
	  	}
	  	if("look".equals(type)){
	  		//看西藏 视频、图集
	  		hql="SELECT * from( SELECT c.contentTitle AS contentTitle,c.code AS code,createTime AS createTime,c.url AS url,joinTime FROM content c,user_favorite f WHERE c.code=f.code AND c.avaliable='1' AND c.contentType LIKE '3___' AND c.code IN (SELECT code FROM user_favorite WHERE memberCode=? AND TYPE=?) "
					+"UNION "
					+"SELECT NAME AS contentTitle,m.code AS code,createTime AS createTime,hyperlink AS url,joinTime FROM `mutiimage` m,user_favorite f WHERE m.code=f.code AND m.avaliable='1' and m.code IN(SELECT code FROM `user_favorite` WHERE memberCode=? AND TYPE=?))t GROUP BY code  order by createTime desc";
	  		param.add(memberCode);
	  		param.add(UserFavorite.User_Fav_See);
	  		param.add(memberCode);
	  		param.add(UserFavorite.User_Fav_Image);
	  		userFavoriteService.findFavbyPager(hql, param, pager);
	  	}
	  	if("merc".equals(type)){
	  		hql="SELECT c.*,f.joinTime FROM merchant c,user_favorite f WHERE c.code=f.code AND avaliable='1' AND c.code IN(SELECT code FROM user_favorite WHERE TYPE=? AND memberCode=?) GROUP BY c.code  order by createTime desc";
	  		param.add(UserFavorite.User_Fav_Merc);
	  		param.add(memberCode);
	  		//userFavoriteService.findFavbyPager(hql, param, pager);
	  		merchantService.findListByPager(hql, param, pager);
	  	}
	  	
	  	if("tour".equals(type)){
	  		//团游
	  		hql="SELECT c.*,f.joinTime FROM `grouptravel` c,user_favorite f WHERE c.code=f.code AND c.code IN(SELECT code FROM `user_favorite` WHERE memberCode=? AND TYPE=?) GROUP BY c.code  order by createTime desc";
	  		param.add(memberCode);
	  		param.add(UserFavorite.User_Fav_Group);
	  		groupTravelService.findListByPager(hql, param, pager);
	  	}
	  	//req.setAttribute("pager", pager);
	  	req.setAttribute("classHolder", type);
    	return new Gson().toJson(pager);
    }
    
    
    /**
     * 取消收藏
     * @param req
     * @param code
     * @return
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="delFav",produces="text/html;charset=utf-8")
	@ResponseBody
    public String delFav(HttpServletRequest req,String code,String type) throws JSONException{
    	 JSONObject obj=new JSONObject();
		 //判断用户是否登录
	  	  LogUser user=(LogUser)req.getSession().getAttribute("logUser");
	  	  if(user==null){
	  		  obj.put("delflag", -110);
	  		  return obj.toString();
	  	  }
	  	  if(StringUtils.isBlank(code)){
	  		  obj.put("delflag", -1);
	  		  return obj.toString();
	  	  }
	  	  String memberCode="";
	  	  if(user.getCode()!=null){
	  		  memberCode=user.getCode();
	  	  }
	  	  String sql="SELECT * FROM `user_favorite` WHERE memberCode=? AND code=? AND type=?";
          List param=new ArrayList();
           param.add(memberCode);
           param.add(code);
           param.add(type);
         List<UserFavorite> list=userFavoriteService.findListBySql(sql, param);
         for(int i=0;i<list.size();i++){
        	 try {
        		 userFavoriteService.delete(list.get(i));
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
				 obj.put("delflag", -1);
		  		 return obj.toString();
			}
         }
         //攻略中间表收藏数目-1
        Praise pri= praiseService.getPraiseContentCode(code);
        if(pri!=null){
        	if(pri.getFalseFavoriteNum()!=null){
              pri.setFavoriteNum(pri.getFalseFavoriteNum()-1);
        	}
        	if(pri.getFavoriteNum()<0){
        		pri.setFavoriteNum(0);
        	}
          praiseService.update(pri);
        }
        req.setAttribute("delflag",1);
        if("stratege".equals(type)){
        	type="stra";
        }else if("readTibet".equals(type)){
        	type="read";
        }else if("merchant".equals(type)){
        	type="merc";
        }else if("tourGroup".equals(type)){
        	type="tour";
        }else if("seeTibet".equals(type)){
        	type="look";
        }
        obj.put("delflag", 1);
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

    
    
    
    /*************************************************************  END  ******************************************************************/
    /**
     * 获取用户的所有攻略
     * @param req
     * @param pageNum
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="getFavStrate",produces="text/html;charset=utf-8")
    public String getFavStrate(HttpServletRequest req,@RequestParam(defaultValue="1",required=false) int pageNum){
    	//判断是否登录
    	/*************/
    	//查找攻略类型
    	String sql="SELECT * FROM programa WHERE pCode=(SELECT code FROM programa WHERE programaName=?)";
    	List para=new ArrayList();
    	 para.add("攻略类型");
    	
		List<Programa> listType=userFavoriteService.findStraType(sql, para);
    	req.setAttribute("listType", listType);
    	
    	//查询用户的所有攻略
    	Pager pager=new Pager();
    	 pager.setCurrentPage(pageNum);
    	 pager.setPageSize(50);
    	//List param=new ArrayList();
    	//param.add(1);
    	Map param=new HashMap();
    	  param.put("memberCode","1");
    	String hql="FROM Content WHERE avaliable='1' AND contentType='strategy' AND code IN (SELECT code FROM UserFavorite WHERE memberCode=:memberCode AND TYPE='strategy')";
    	userFavoriteService.findWithPagerByMap(hql, param, pager);
    	List<Content> list=pager.getDataList();
    	for(Content con:list){
    		if(con.getProgramaCode()!=null){
    		 Programa pro=programaService.getProgramaByCode(con.getProgramaCode());
    		   if(pro!=null){
	    			if(pro.getProgramaName()!=null){
	    				con.setContentType(pro.getProgramaName());
	    			}
    		   }
    		}
    	}
    	return "";
    }
	
    /**
     * 分类查询用户收藏的攻略
     * @param req
     * @param code 攻略分类ID
     * @param pageNum
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="getFavStraByIden",produces="text/html;charset=utf-8")
	public String getFavStraByIden(HttpServletRequest req,String code,@RequestParam(defaultValue="1",required=false) int pageNum){
    	
    	Pager pager=new Pager();
	   	 pager.setCurrentPage(pageNum);
	   	 pager.setPageSize(50);
	   	
	   	Map param=new HashMap();
	   	  param.put("programaCode", code);
	   	  param.put("memberCode","1");
	   	  
	   	String hql="FROM Content WHERE avaliable='1' AND contentType='strategy' AND programaCode=:programaCode AND code IN (SELECT code FROM UserFavorite WHERE memberCode=:memberCode AND TYPE='strategy')";
    	
	   	userFavoriteService.findWithPagerByMap(hql, param, pager);
	   	return "";
    }
    
    /**
     * 查询用户收藏的全部商户
     * @param req
     * @param code
     * @param pageNum
     * @return
     */
    @RequestMapping(value="getFavMerchant",produces="text/html;charset=utf-8")
    public String getFavMerchant(HttpServletRequest req,@RequestParam(defaultValue="1",required=false) int pageNum){
    	//查询所有商户分类
    	List<MerchantType> merTypeList=merchantTypeService.getMerchantList();
    	req.setAttribute("merType", merTypeList);
    	//查询用户收藏的商户
    	Pager pager=new Pager();
	   	 pager.setCurrentPage(pageNum);
	   	 pager.setPageSize(50);
	   	
	   	Map param=new HashMap();
	   	  param.put("memberCode","1");
	   	String hql="FROM Merchant WHERE avaliable='1' AND code IN(SELECT code FROM UserFavorite WHERE memberCode=:memberCode AND TYPE='merchant') ";
    	userFavoriteService.findWithPagerByMap(hql, param, pager);
	   	return "";
    }
    
    /**
     * 根据类型查询用户所收藏的商户
     * @param req
     * @param code
     * @param pageNum
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("getFavMerByIden")
    public String getFavMerByIden(HttpServletRequest req,String code,@RequestParam(defaultValue="1",required=false) int pageNum){
    	Pager pager=new Pager();
	   	 pager.setCurrentPage(pageNum);
	   	 pager.setPageSize(50);
	   	
	   	Map param=new HashMap();
	   	  param.put("memberCode","1");
	   	  param.put("merchantType", code);
	   	String hql="FROM Merchant WHERE avaliable='1' AND merchantType=:merchantType  AND code IN(SELECT code FROM UserFavorite WHERE memberCode=:memberCode AND TYPE='merchant') ";
    	
		userFavoriteService.findWithPagerByMap(hql, param, pager);
	   	return "";
    }
    
    /**
     * 查询用户收藏的团游
     * @param req
     * @param code
     * @param pageNum
     * @return
     */
    @ RequestMapping("getFavTuany")
    public String getFavTuany(HttpServletRequest req,String code,@RequestParam(defaultValue="1",required=false) int pageNum){
    	
    	return "";
    }
    
    /**
     * 添加收藏 通过code 和 type
     * /portal/userFavorite/saveFavorite
     */
    @RequestMapping("saveFavorite")
    @ResponseBody
    public String saveFavorite(String code, String type){
        LogUser user = getFrontUser();
        if(user!=null){
            return userFavoriteService.saveFavorite(user.getCode(), code, type);
        }else{
            return "need_login";
        }
    }

    /**
     * 查询是否已收藏
     * /portal/userFavorite/checkIsFavorite
     */
    @RequestMapping("checkIsFavorite")
    @ResponseBody
    public String checkIsFavorite(String code){
        LogUser user = getFrontUser();
        if(user!=null){
            UserFavorite userFavorite = userFavoriteService.getByCode(code, user.getCode());
            if(userFavorite!=null){
                return "success";
            }
        }
        return "error";
    }
    
    
    
}
