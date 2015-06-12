package com.rimi.ctibet.portal.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.rimi.ctibet.common.exception.JsonParseException;
import com.rimi.ctibet.common.sortmsg.SendSMS;
import com.rimi.ctibet.common.util.ImageHelper;
import com.rimi.ctibet.common.util.JsonUtil;
import com.rimi.ctibet.common.util.KeyWordFilter;
import com.rimi.ctibet.common.util.LoginMD5;
import com.rimi.ctibet.common.util.MatchHolder;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.common.util.Security;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.common.util.security.RSACoder;
import com.rimi.ctibet.common.util.sendemail.SendEmail;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.LoginLog;
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.domain.MemberEmail;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.domain.MemberMobile;
import com.rimi.ctibet.domain.MobileCode;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.portal.controller.LoginController.send;
import com.rimi.ctibet.web.service.ILoginLogService;
import com.rimi.ctibet.web.service.IMemberEmailService;
import com.rimi.ctibet.web.service.IMemberMobileService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IMobileCodeService;
import com.rimi.ctibet.web.service.IProgramaService;

/**
 * app 手机用户接口
 * @author dengxh
 *
 */
@Controller
@RequestMapping("portal/appregisterController")
public class AppRegisterController {
	 
	 private static final String ACCESSFILETYPE = ".*(jpeg|jpg|png|gif|bmp)$";
	 private final String inputStr = "tianshangxizangToken";  
	
     @Resource
     private IMemberService memberService;
     @Resource
     private IMemberMobileService memberMobileService;
     @Resource
     private ILoginLogService loginLogService;
    @Resource
    private IProgramaService programaService;
    @Resource
    private IMobileCodeService mobileCodeService;
     
    @Resource
	private IMemberEmailService memberEmailService;
     
     
    @RequestMapping("sendTest")
     public String sendTest(){
    	 SendEmail.send("526249497@qq.com", "222sdfsdf");
    	 return "";
    	 
     }
     
     /**
 	 * 同步手机注册用户
 	 * 
 	 * @param request
 	 * @param response
 	 * @param model
 	 * @return
     * @throws Exception 
 	 */
 	@RequestMapping(value="SycMemberMobile",produces="text/html;charset=utf-8")
 	@ResponseBody
 	public String sycMemberMobile(HttpServletRequest request,
 			HttpServletResponse response,String mobile,String name,String password) throws Exception {
 			 		int isByMobile = 1;
	        if(StringUtils.isBlank(mobile)){
	     	   return error("手机号不能为空","");
	        }
	        if(StringUtils.isBlank(name)){
	     	   return error("用户名不能为空","");
	        }
	        if(StringUtils.isBlank(password)){
	    	   return error("密码不能为空","");
	        }
	        if(!MatchHolder.isEmpTelphone(mobile)){
			   return error("手机号格式不正确","");
	  		}	
	 		// 判断手机号是否已经被注册
	 		if (memberService.checkMobileIsRepeat(mobile)) {
	           return error("该手机号已经被注册","");
	 		} else {
	 			if(KeyWordFilter.getInstance().isContentKeyWords(name)){
	 	       	   return error("昵称包含敏感词","");
	 		    }
	 			if (StringUtils.isNotBlank(mobile)
	 					&& StringUtils.isNotBlank(name)
	 					&& StringUtils.isNotBlank(password)) {
	 				String createIp = getIpAddr(request);
	 				Member member  = null;
	 			    try {
	 			    	member = memberService.saveMember(password, createIp);
	 	 				memberService.saveMemInfo(member, name);
	 	 				memberService.saveMemMobile(member, mobile, isByMobile);
//	 	 				member.setStatus(1);
//	 	 				memberService.updateMember(member);
	 	 				
					} catch (Exception e) {
						e.printStackTrace();// TODO: handle exception
						return inserterror("手机注册失败","");
					}
	 				
	 	           return success("手机注册成功",member.getCode());
	 				
	 			}
	 			
	 		}
 		return error("注册类型有误，请检查参数type","");  //注册失败
 	}
 	
 	
 	
 	
 	
 	
 	
 	
 	 /**
 	 * 邮箱注册
 	 * 
 	 * @param request
 	 * @param response
 	 * @param model
 	 * @return
     * @throws Exception 
 	 */
 	@RequestMapping(value="SycMemberEmail",produces="text/html;charset=utf-8")
 	@ResponseBody
 	public String sycMemberEmail(HttpServletRequest request,
 			HttpServletResponse response,String email,String name,String password) throws Exception {
 			 		 if(StringUtils.isBlank(email)){
 			        	return error("邮箱不能为空","");
 			         }
 			         if(StringUtils.isBlank(name)){
 			        	return error("用户名不能为空","");
 			         }
 			         if(StringUtils.isBlank(password)){
 			        	return error("密码不能为空","");
 			         }
 			         if(!MatchHolder.isEmail(email)){
 			 			return error("邮箱格式不正确","");
 			 		}	
 			 		String createIp = getIpAddr(request);
 			 		 //验证邮箱是否重复
 			 		 List<MemberEmail> elist=memberService.findEmailByPro("email", email);
 			 		 if(elist.size()>0){
 			 			return error("该邮箱已经被使用","");
 			 		 }
 			 		 if(KeyWordFilter.getInstance().isContentKeyWords(name)){
 			 				return error("昵称包含敏感词","");
 			 		 }
 			         String basePath =getBasePath(request);
 			         Member m=new Member();
 			         String code=Uuid.uuid();
 			          m.setAvaliable(1);
 			          m.setCode(code);
 			          m.setCreateIp(createIp);
 			          m.setCreateTime(new Timestamp(new Date().getTime()));
 			          m.setMemberType(1);
 			          m.setStatus(1);
 			          m.setPassword(LoginMD5.compile(password));
 			         MemberEmail me=new MemberEmail();
 			          me.setAvaliable(1);
 			          me.setCode(Uuid.uuid());
 			          me.setEmail(email);
 			          me.setIsBind(1);
 			          me.setBindTime(new Timestamp(new Date().getTime()));
 			          me.setMemberCode(code);
 			         MemberInfo mi=new MemberInfo();
 			          mi.setAvaliable(1);
 			          mi.setCode(Uuid.uuid());
 			          mi.setMemberCode(code);
 			          mi.setName(name);
 			          mi.setSex(1);
 			          mi.setPic("/portal/static/default/male.jpg");
 			          MemberMobile mm=new MemberMobile();
 			         try {
 			        	 memberService.saveByModel(m, mi, me, mm);
 			        	 return success("邮箱注册成功",m.getCode());
 					 } catch (Exception e) {
 						e.printStackTrace();// TODO: handle exception
 						return inserterror("邮箱注册失败","");
 			        } 
 	}
 	
 	
 	
 	/**
     * 前台用户登录
     * @param logUser
     * @param logPass
     * @param verifyCode
     * @param req
     * @param res
     * @return
     * @throws JSONException 
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="loginAjax",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String loginAjax(String tlogUser,String logPass,HttpServletRequest req,HttpServletResponse res,HttpSession session) throws JSONException, UnsupportedEncodingException, IOException{
 	   if(StringUtils.isBlank(tlogUser)){
 		  return error("用户名不能为空","");
 	   }
 	   if(StringUtils.isBlank(logPass)){
 		  return error("密码不能为空","");
 	   }
 	  
 	   //String password = new String(new BASE64Decoder().decodeBuffer(logPass),"utf-8");
 	   logPass =  LoginMD5.compile(logPass);
 	   List<LogUser> list=memberService.login(tlogUser, logPass);
 	   if(list.size()<=0){
 		  return error("用户名或密码错误","");
 	   }
 	  LogUser loguser=list.get(list.size()-1);
 	   if(loguser!=null){
 		   if(loguser.getEmail()==null&&loguser.getMobile()==null){
 			   return  error("未找到该用户","");
 		   }
 		   //System.out.println(loguser.getStatus());
 		   if(loguser.getStatus()==0){
 			   return error("账户已被停封，请与管理员联系","");
 		   }
 		   if(loguser.getIsBind()!=null&&loguser.getEmail()!=null&&loguser.getMobile()==null){
 			   if(loguser.getIsBind()==0){
 				  
 				   return error("请先到邮箱激活账户","");
 			   }
 		   }
 		   if(loguser.getIsVerified()!=null&&loguser.getEmail()==null&&loguser.getMobile()!=null){
 			   if(loguser.getIsVerified()==0){
 				 
 				   return  error("该账户尚未激活","");
 			   }
 		   }
 		   
 		   if(loguser.getIsVerified()!=null&&loguser.getEmail()!=null&&loguser.getMobile()!=null&&loguser.getIsBind()!=null){
 			   if(loguser.getIsVerified()==0&&!tlogUser.contains("@")){
 				   return  error("该账户尚未激活","");
 			   }
 			   if(loguser.getIsBind()==0&&tlogUser.contains("@")){
 				   return error("请先到邮箱激活账户","");
 			   }
 		   }
 	   }
 	  
 	   LoginLog ll=new LoginLog();
 	   ll.setLogDate(new Date());
 	   ll.setMemberCode(loguser.getCode());
 	   loginLogService.save(ll);
 	   req.getSession().setAttribute("logUser",loguser);
 	   return success("手机端登录成功",loguser.getCode());
    }
 	
 	
   /**
    * 用户个人信息
    * @param userid
    * @param req
    * @param res
    * @param session
    * @return
    * @throws JSONException
    * @throws UnsupportedEncodingException
    * @throws IOException
    */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="userinfo",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String userinfo(String userid,HttpServletRequest req,HttpServletResponse res,HttpSession session) throws JSONException, UnsupportedEncodingException, IOException{
    	//判断用户是否登录
		/*
		 *  LogUser user = (LogUser) req.getSession().getAttribute("logUser");
			if (user == null) {
				return error("没有登陆","");
		}
		*/
       if(StringUtils.isBlank(userid)){
 		  return error("用户id不能为空","");
 	   }
 	   //String password = new String(new BASE64Decoder().decodeBuffer(logPass),"utf-8");
 	   List<LogUser> list=memberService.findLogUserByCode(userid);
 	   if(list.size()<=0){
 		  return error("当前用户不存在","");
 	   }
 	   LogUser loguser=list.get(0);
 	   if(loguser!=null){
 		   if(loguser.getEmail()==null&&loguser.getMobile()==null){
 			   return  error("未找到该用户","");
 		   }
 		   //System.out.println(loguser.getStatus());
 		   if(loguser.getStatus()==0){
 			   return error("账户已被停封，请与管理员联系","");
 		   }
 		   if(loguser.getIsBind()!=null&&loguser.getEmail()!=null&&loguser.getMobile()==null){
 			   if(loguser.getIsBind()==0){
 				   return error("请先到邮箱激活账户","");
 			   }
 		   }
 		   if(loguser.getIsVerified()!=null&&loguser.getEmail()==null&&loguser.getMobile()!=null){
 			   if(loguser.getIsVerified()==0){
 				   return  error("该账户尚未激活","");
 			   }
 		   }
 	   }
 	  
 	   LoginLog ll=new LoginLog();
 	   ll.setLogDate(new Date());
 	   ll.setMemberCode(loguser.getCode());
 	   loginLogService.save(ll);
 	   req.getSession().setAttribute("logUser",loguser);
 	   return   userInfoJson(loguser);
    }
    
    public String userInfoJson(LogUser loguser) throws JSONException{
    	JSONObject obj = new JSONObject();
    	obj.put("status", 1);
    	obj.put("msg", "查询个人信息成功");
    	obj.put("userid", loguser.getCode());
    	obj.put("phone", loguser.getPhone());
    	obj.put("name", loguser.getUsername());
    	obj.put("email", loguser.getEmail());
    	String basePath = "";
    	Programa programa = programaService.getProgramaByCode("61r19a85-7e8c-12e4-b6ce-003056a05bc9");
    	if (programa!=null) {
			 basePath = programa.getProgramaUrl();
		}
    	obj.put("pic",basePath+"/"+loguser.getPic());
    	Integer sex  = loguser.getSex();
    	String sexName  = "暂无";
    	if (sex!=null) {
		   if (sex==1) {
			sexName = "男";
		   }else if(sex==0){
			   sexName  = "女";
		   }  	 
		}
    	obj.put("sex", sexName) ;
    	obj.put("mbstatus", loguser.getStatus());
    	return  obj.toString();
    }
    
    
    
    
    /**
	 * 修改密码
	 * 
	 * @param req
	 * @param oldPass
	 * @param newPass
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value="updatePassAjax",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updatePassAjax(HttpServletRequest req, String oldPass,
			String newPass,String userid) throws JSONException {
		//判断用户是否登录
		/*if (user == null) {
			return error("没有登陆","");
		}*/
		 if(StringUtils.isBlank(userid)){
	 		  return error("用户id不能为空","");
	 	 }
		if (StringUtils.isBlank(oldPass)) {
			req.setAttribute("error", -110);
			return error("原密码错误","");
		}
		if (StringUtils.isBlank(newPass)) {
			req.setAttribute("error", -111);
			return error("新密码错误","");
		}
		 List<LogUser> list=memberService.findLogUserByCode(userid);
	 	   if(list.size()<=0){
	 		  return error("当前用户不存在","");
	 	   }
	 	   LogUser loguser=list.get(0);
	 	   if(loguser!=null){
	 		   if(loguser.getEmail()==null&&loguser.getMobile()==null){
	 			   return  error("未找到该用户","");
	 		   }
	 		   //System.out.println(loguser.getStatus());
	 		   if(loguser.getStatus()==0){
	 			   return error("账户已被停封，请与管理员联系","");
	 		   }
	 		   if(loguser.getIsBind()!=null&&loguser.getEmail()!=null&&loguser.getMobile()==null){
	 			   if(loguser.getIsBind()==0){
	 				   return error("请先到邮箱激活账户","");
	 			   }
	 		   }
	 		   if(loguser.getIsVerified()!=null&&loguser.getEmail()==null&&loguser.getMobile()!=null){
	 			   if(loguser.getIsVerified()==0){
	 				   return  error("该账户尚未激活","");
	 			   }
	 		   }
	 	   }
		oldPass = LoginMD5.compile(oldPass);
		if (!oldPass.equals(loguser.getPassword())) {
			req.setAttribute("error", -112);
			return error("原密码错误","");
		}
		Member m = memberService.findByCode(loguser.getCode());
		m.setPassword(LoginMD5.compile(newPass));
		try {
			memberService.updateMember(m);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			return error("修改失败","");
		}
		return success("修改成功","");

	}
    
    
	 /**
	 * 重置密码
	 * 
	 * @param req
	 * @param oldPass
	 * @param newPass
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value="resetPassAjax",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String resetPassAjax(HttpServletRequest req,
			String newPass,String userid) throws JSONException {
		//判断用户是否登录
		/*if (user == null) {
			return error("没有登陆","");
		}*/
		 if(StringUtils.isBlank(userid)){
	 		  return error("用户id不能为空","");
	 	 }
		if (StringUtils.isBlank(newPass)) {
			req.setAttribute("error", -111);
			return error("新密码错误","");
		}
		 List<LogUser> list=memberService.findLogUserByCode(userid);
	 	   if(list.size()<=0){
	 		  return error("当前用户不存在","");
	 	   }
	 	   LogUser loguser=list.get(0);
	 	   if(loguser!=null){
	 		   if(loguser.getEmail()==null&&loguser.getMobile()==null){
	 			   return  error("未找到该用户","");
	 		   }
	 		   //System.out.println(loguser.getStatus());
	 		   if(loguser.getStatus()==0){
	 			   return error("账户已被停封，请与管理员联系","");
	 		   }
	 		   if(loguser.getIsBind()!=null&&loguser.getEmail()!=null&&loguser.getMobile()==null){
	 			   if(loguser.getIsBind()==0){
	 				   return error("请先到邮箱激活账户","");
	 			   }
	 		   }
	 		   if(loguser.getIsVerified()!=null&&loguser.getEmail()==null&&loguser.getMobile()!=null){
	 			   if(loguser.getIsVerified()==0){
	 				   return  error("该账户尚未激活","");
	 			   }
	 		   }
	 	   }
		Member m = memberService.findByCode(loguser.getCode());
		m.setPassword(LoginMD5.compile(newPass));
		try {
			memberService.updateMember(m);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			return error("重置密码失败","");
		}
		return success("重置密码成功","");

	}
	
	
	
	/**
	 * 获取 手机 验证码
	 * 
	 * @param req
	 * @param type 注册 0 或者修改密码  1 
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value="getPhoneCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getPhoneCode(HttpServletRequest req,
			String mobile,String type) throws JSONException {
		  if(StringUtils.isBlank(mobile)){
			   return error("手机号不能为空","");
		  }
		  if (StringUtils.isNotBlank(type)&&!type.equals("0")) {
			  List<MemberMobile> b= memberMobileService.checkMobile(mobile);
			  if(b.size()<=0){
				  return error("找不到该用户","");
			  }
		  }
		  if (type==null||(!type.equals("0")&&!type.equals("1"))) {
			  return error("tyep类型必须是0或者1(0表示注册账号,1表示修改密码)","");
		  }
		 //随机生成6位数验证码
		 String code = String.valueOf(Math.round(Math.random() * 899999 + 100000));// 随机生成6位随机数
         String[] a={mobile};
         String msg="您申请了找回密码，此次操作的验证码为( "+code+" )，请注意查收。";
         if (StringUtils.isNotBlank(type)&&type.equals("0")) {
        	 msg="您申请了注册账号，此次操作的验证码为( "+code+" )，请注意查收。";
		 }
		 send s=new send(a,msg,"byMobile");
		 Thread thread=new Thread(s);
		 thread.start();
		 //MobileCode mobileCode=new MobileCode();
		 List<MobileCode> list=mobileCodeService.findValicode(a[0]);
		 if(list.size()>0){
			 MobileCode mobileCode=list.get(0);
			  mobileCode.setMobile(a[0]);
			  mobileCode.setValidateCode(code);
			  mobileCode.setCreateTime(new Timestamp(new Date().getTime()));
			 try {
				 mobileCodeService.update(mobileCode);
			 } catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
				return error("数据异常","");
			 }
		 }
		JSONObject obj = new JSONObject();
		obj.put("status", 1);
		obj.put("mobile", mobile);//手机号
		obj.put("validity_time", 1000*60*3);//3分钟有效期
		obj.put("msg", "验证信息发送成功");
		obj.put("code",code);//手机验证码
		return obj.toString();
	}
	
	/**
	 * 手机修改密码
	 * 
	 * @param req
	 * @param oldPass
	 * @param newPass
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value="mdPassByPhone",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String mdPassByPhone(HttpServletRequest req,
			String mobile,String newpass) throws JSONException {
		 if(StringUtils.isBlank(mobile)){
			   return error("手机号不能为空","");
		   }
		 if(StringUtils.isBlank(newpass)){
			   return error("新密码不能为空","");
		   }
		  List<MemberMobile> b= memberMobileService.checkMobile(mobile);
		  
		  if(b.size()<=0){
			  return error("找不到该用户","");
		  }
		 String memberCode  = b.get(0).getMemberCode();
		 Member member = memberService.findByCode(memberCode);
		 if(member==null){
			  return error("找不到该用户","");
		  }
		 member.setPassword(LoginMD5.compile(newpass));
		 try {
			   memberService.updateMember(member);
			} catch (Exception e) {
				 JSONObject obj = new JSONObject();
				 obj.put("status", -1);
				 obj.put("msg", "找回密码失败!");
		  }
		 JSONObject obj = new JSONObject();
		 obj.put("status", 1);
		 obj.put("msg", "找回密码成功!");
		return obj.toString();
	}
	
	/**
	 * 发送邮箱信息找回密码
	 * @param req
	 * @param email
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping(value="getEmailMsg",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String  getEmailMsg(HttpServletRequest req,String email) throws JSONException{
		if (StringUtils.isBlank(email)) {
			return error("邮箱不能为空","");
		}
		if (!email(email)) {
			return error("邮箱格式不正确","");
		}
		List<MemberEmail> list = memberEmailService.checkEmail(email);
		if (list.size() <= 0) {
			return error("找不到该用户","");
		}
		MemberEmail me = list.get(0);
		StringBuffer content = new StringBuffer("");
		String code = Security.getInstance().encode(
				(System.currentTimeMillis() + 1200000)
				+ ","
				+ LoginMD5.compile(me.getEmail()
						+ me.getMemberCode() + me.getIsBind())
				+ "," + me.getMemberCode());
		content
				.append("您好，尊敬的用户。 你的账号<b><font color=\"#ff0000\">")
				.append(email)
				.append("</font></b>，申请密码找回。请在20分钟内找回密码，<a href=\"")
				.append(getBasePath(req))
				.append("/portal/clientLog/changePage.html?code=")
				.append(code)
				.append(
						"&findType=byEmail\">点击找回</a><div>&nbsp; &nbsp;请注意保管！</div>");
		send s = new send(content.toString(), "byEmail", email);
		Thread th = new Thread(s);
		th.start();
		req.setAttribute("email", email);
		req.setAttribute("hiddencode", "email");
		return success("邮件发送成功","");
		
		
	}
	
	
	
	/**
	 * 验证邮箱格式
	 */
	public static boolean email(String email) {
		return (email == null || "".equals(email)) ? false : email
				.matches("^([\\w_\\.-]+)@([\\w\\.-]+)\\.([a-zA-Z\\.]{2,6})$");
	}
	
	
    
	/**
	    * 多线程发送信息
	    * @author abcd
	    *
	    */
	   class send implements Runnable{
	        private String[] mobile;
	        private String msg;
	        private String findType;
			private String email;

			
	        @Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					if("byMobile".equals(findType)){
					  SendSMS.send(msg, mobile);
					  
					}
					if("byEmail".equals(findType)){
					   SendEmail.send(email, msg);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			public send(String[] mobile, String msg, String findType) {
				super();
				this.mobile = mobile;
				this.msg = msg;
				this.findType = findType;
			}
	        

			public send(String msg, String findType, String email) {
				super();
				this.msg = msg;
				this.findType = findType;
				this.email = email;
			}


			public String[] getMobile() {
				return mobile;
			}

	        public void setMobile(String[] mobile) {
				this.mobile = mobile;
			}

	        public String getMsg() {
				return msg;
			}
			public void setMsg(String msg) {
				this.msg = msg;
			}
			public String getFindType() {
				return findType;
			}

			public void setFindType(String findType) {
				this.findType = findType;
			}  
			public String getEmail() {
				return email;
			}

			public void setEmail(String email) {
				this.email = email;
			}
	    }
 	
 	/**
     * 获取路径
     * @param request
     * @return
     */
    public static String getBasePath(HttpServletRequest request){
 		String path = request.getContextPath();
 		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 		return basePath;
 	}
     
     
	
	/**
	 * 根据日期归类图像存放路径
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getStorePath() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String date = fmt.format(new Date());
		return "/upload/img/userpic/" + date + "/";
	}

	

	
	/**
	 * 获取客户端的IP
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
 	/**
	 * 返回错误信息
	 * 
	 * @param msg
	 * @return
	 * @throws JSONException
	 */
	private static String error(String msg,String userid) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("status", -1);
		obj.put("msg", msg);
		obj.put("userid", userid);
		return obj.toString();
	}
	/**
	 * 返回错误信息
	 * 
	 * @param msg
	 * @return
	 * @throws JSONException
	 */
	private static String inserterror(String msg,String userid) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("status", -1);
		obj.put("msg", msg);
		obj.put("userid", userid);
		return obj.toString();
	}
	/**
	 * 返回成功信息
	 * 
	 * @param msg
	 * @return
	 * @throws JSONException
	 */
	private static String success(String msg,String userid) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("status", 1);
		obj.put("msg", msg);
		obj.put("userid", userid);
		return obj.toString();
	}
     
	public static void main(String[] args) {
		String    logPass =  LoginMD5.compile("123");
	}
	
     
     

}
