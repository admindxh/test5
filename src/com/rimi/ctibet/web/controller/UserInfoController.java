package com.rimi.ctibet.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Role;
import com.rimi.ctibet.domain.UserInfo;
import com.rimi.ctibet.web.controller.vo.UserVO;
import com.rimi.ctibet.web.service.IRoleService;
import com.rimi.ctibet.web.service.IUserInfoService;


@Controller
@RequestMapping("/manage/html/")
public class UserInfoController {
	 public static final String SESSION_USER="session_user";
	 private static final String BASEURL="/manage/html/";
	 public static final Logger LOG = Logger.getLogger(UserInfoController.class);
	 
	 @Resource
	 private IUserInfoService userInfoService;
	 @Resource
	 private IRoleService roleService;
     //修改和增加用户	
	 @RequestMapping("saveOrUpdateUserInfo")
	 public @ResponseBody String saveOrUpateUserInfo(HttpServletRequest request,HttpServletResponse response,@ModelAttribute UserInfo userInfo) throws Exception{
		 //=====================修改文件信息====================
		 if(StringUtils.isNotBlank(userInfo.getCode())){
			userInfoService.updateUserInfo(userInfo);
	        return "修改成功";
		 }
		userInfoService.saveUserInfo(userInfo);	 
		 return "保存成功";
	 }
	 //根据code删除用户
	 @RequestMapping("deleteUserInfo")
	 public @ResponseBody String deleteUserInfo(HttpServletRequest request,HttpServletResponse response,@ModelAttribute UserInfo userInfo){
		 if(StringUtils.isNotBlank(userInfo.getCode())&&StringUtils.isNotBlank(userInfo.getId().toString())){
			 userInfoService.deleteUserInfo(userInfo);
			 return "删除成功";
		 }
		 return "缺少必要参数";
	 }
	 //根据code查询用户
	 @RequestMapping("getUserInfoByCode")
	 public @ResponseBody Map getUserInfoByCode(HttpServletRequest request,HttpServletResponse response,@ModelAttribute UserInfo userInfo){
		 Map<String,Object> userInfoMap = new HashMap<String,Object>();
		 if(StringUtils.isNotBlank(userInfo.getCode())&&StringUtils.isNotBlank(userInfo.getId().toString())){
			 userInfo = userInfoService.getByCode(userInfo.getCode());
			 userInfoMap.put(UserInfo.USERINFO, userInfo);
			 return userInfoMap;
		 }
		 userInfoMap.put(UserInfo.USERINFO, "缺少必要参数");
		 return userInfoMap;
	 }
	 //==================================================================================================================
     //高级查询
     @RequestMapping("searchUser")
     public ModelAndView searchUser(Pager pager,HttpServletRequest request,HttpServletResponse response){
    	 ModelAndView view = new ModelAndView("manage/html/settings/systemAdminList");
         String roleCode = request.getParameter("roleCode");
         Role searchRole = roleService.getRoleBuCode(roleCode);
         String keyWord = request.getParameter("keyWord");
         Pager pager2 = userInfoService.searchUsers(pager,roleCode,keyWord);
         view.addObject("pager",pager2);
         List<Role> roles = roleService.getRoles();
    	 view.addObject("roleCode", roleCode).addObject("roles",roles).addObject("keyWord", keyWord).addObject("searchRole", searchRole);
         return view;
     }
     //跳转到编辑和修改页面
     @RequestMapping("gotoSaveOrUpdate")
     public ModelAndView gotoSaveOrUpdate(HttpServletRequest request,HttpServletResponse response){
    	 String code = request.getParameter("code");
    	 if(StringUtils.isNotBlank(code)){
    		 ModelAndView view = new ModelAndView("manage/html/settings/systemAdminEdit");
    		 UserInfo user = userInfoService.getByCode(code);
    		 List<Role> roles = roleService.getRoles();
    		 Role myRole = roleService.getRoleBuCode(user.getRoleCode());
    		 view.addObject("roles",roles);
    		 view.addObject("myRole", myRole);
    		 return view.addObject("user",user);
    	 }
    	 ModelAndView view = new ModelAndView("manage/html/settings/systemAdminAdd");
    	 List<Role> roles = roleService.getRoles();
		 view.addObject("roles",roles);
    	 return view;
     }
     //保存和修改
     @RequestMapping("saveOrUpdate")
     public ModelAndView saveOrUpdate(HttpServletRequest request,HttpServletResponse response,UserInfo user){
    	 ModelAndView view = new ModelAndView("redirect:/manage/html//searchUser");
    	 if(StringUtils.isBlank(user.getCode())){
	    	 user.setCode(CodeFactory.create("sys"));
             if(!userInfoService.isRepeat(user.getName()))
	    	    userInfoService.saveUserInfo(user);
	    }else{
	    	
	    	UserInfo oldUser = userInfoService.getByCode(user.getCode());
	    	oldUser.setRoleCode(user.getRoleCode());
    	     userInfoService.updateUserInfo(oldUser);}
    	 return view;
     }
     //判断用户名是否重复
     @RequestMapping("isRepeat")
     public @ResponseBody boolean isRepeat(String name){
    	
    	  return userInfoService.isRepeat(name);
     }
     //删除
     @RequestMapping("deleteSys")
     public ModelAndView delete(HttpServletRequest request,HttpServletResponse response){
    	 ModelAndView view = new ModelAndView("redirect:/manage/html/searchUser");
    	 String code = request.getParameter("code");
    	 String[] codes = code.split(",");
    	 for (String scode : codes) {
    		 UserInfo user = userInfoService.getByCode(scode);
    		 userInfoService.deleteUserInfo(user);
		 }
    	 return view;
     }
     //单个删除
     @RequestMapping("deleteSingle")
     public @ResponseBody String deleteSingle(HttpServletRequest request,HttpServletResponse response){
    	 String code = request.getParameter("code");
    	 String[] codes = code.split(",");
    	 for (String scode : codes) {
    		 UserInfo user = userInfoService.getByCode(scode);
    		 userInfoService.deleteUserInfo(user);
		 }
    	 return "success";
     }
     //密码初始化
     @RequestMapping("initPwd")
     public @ResponseBody boolean initPwd(HttpServletRequest request,HttpServletResponse response){
         boolean flag = true;
    	 String code = request.getParameter("code");
    	 UserInfo user = userInfoService.getByCode(code);
    	 user.setPwd("888888");userInfoService.updateUserInfo(user);
    	 return flag;
     }
     //跳入管理员换密码
     @RequestMapping("gotoNewPwd")
     public ModelAndView gotoNewPwd(HttpServletRequest request,HttpServletResponse response){
    	 ModelAndView view = new ModelAndView("manage/html/settings/systemAdminNewPwd");
    	 return view;
     }
     //检验原密码是否正确
     @RequestMapping("isOldPwdRight")
     public @ResponseBody boolean isOldPwdRight(String oldPwd,HttpServletRequest request,HttpServletResponse response){
    	 boolean flag = true;
    	 UserVO logAdmin = (UserVO)request.getSession().getAttribute(SESSION_USER);
    	 if(!oldPwd.equals(logAdmin.getPwd()))
    		 flag = false;
    	 return flag;
     }
     //管理员换密码
     @RequestMapping("newPwd")
     public ModelAndView newPwd(HttpServletRequest request,HttpServletResponse response){
    	 ModelAndView view = new ModelAndView("redirect:/manage/html//searchUser");
    	 String oldPwd = request.getParameter("oldPwd");
    	 String newPwd = request.getParameter("newPwd");
    	 String rePwd = request .getParameter("rePwd");
    	 if(!newPwd.equals(rePwd)){
    		 return view;
    	 }
    	 UserVO logAdmin = (UserVO)request.getSession().getAttribute(SESSION_USER);
    	 UserInfo admin = new UserInfo();
    	 admin.setId(logAdmin.getId());
    	 admin.setCode(logAdmin.getCode());
    	 admin.setCreateTime(logAdmin.getCreateTime());
    	 admin.setLastEditTime(new Date());
    	 admin.setPwd(logAdmin.getPwd());
    	 admin.setRoleCode(logAdmin.getRoleCode());
    	 admin.setName(logAdmin.getName());
    	 userInfoService.changePwd(admin, newPwd);
    	 request.getSession().setAttribute(SESSION_USER,null);
    	 return view;
     }
     //==================================================================================================================
     //用户登录检查
     @RequestMapping("checkLogin")
     public @ResponseBody Map checkLogin(HttpServletRequest request,HttpServletResponse response,@ModelAttribute UserInfo userInfo) throws Exception{
    	 Map<String,Object> userInfoMap = new HashMap<String,Object>();
    	 if(StringUtils.isNotBlank(userInfo.getName())&&StringUtils.isNotBlank(userInfo.getPwd())){
    		userInfo = userInfoService.checkLogin(userInfo.getName(), userInfo.getPwd());
    		if(userInfo!=null){
    			userInfoMap.put(UserInfo.USERINFO, userInfo);
    			return userInfoMap;
    		}else{
    			userInfoMap.put(UserInfo.USERINFO, "用户名或密码错误");
    			return userInfoMap;
    		}
    	}
 		userInfoMap.put(UserInfo.USERINFO, "缺少必要参数");
		return userInfoMap;
     }
     //用户权限查看，根据code
     @RequestMapping("accessList")
     public @ResponseBody Map accessList(HttpServletRequest request,HttpServletResponse response,@ModelAttribute UserInfo userInfo){
    	 Map<String,Object> accessMap = new HashMap<String,Object>();
    	 List<String> access = userInfoService.accessList(userInfo.getCode());
         accessMap.put("accessList",access);
         return accessMap;
     }
}
