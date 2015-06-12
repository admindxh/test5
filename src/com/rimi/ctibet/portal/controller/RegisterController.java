package com.rimi.ctibet.portal.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.sortmsg.SendSMS;
import com.rimi.ctibet.common.util.CheckMobile;
import com.rimi.ctibet.common.util.HtmlRegexpUtil;
import com.rimi.ctibet.common.util.KeyWordFilter;
import com.rimi.ctibet.common.util.LoginMD5;
import com.rimi.ctibet.common.util.MatchHolder;
import com.rimi.ctibet.common.util.Security;
import com.rimi.ctibet.common.util.XssFilter;
import com.rimi.ctibet.common.util.sendemail.SendEmail;
import com.rimi.ctibet.common.util.sendemail.ServiceException;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.domain.MemberEmail;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.domain.MobileCode;
import com.rimi.ctibet.web.service.IMemberService;

/**
 * 注册控制器
 * 
 * @author xiangwq
 * @date 2014/12/2
 */
@Controller
@RequestMapping("portal/registerController")
public class RegisterController extends BaseController {

	@Resource
	private IMemberService memberService;
	
	/**
	 * 跳转到用户手册
	 * @return
	 */
	@RequestMapping("toLicence")
	public String toLicence(){
		return "portal/app/register/licence";
	}
	
	/**
	 * 手机注册
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("memberRegistByMobile")
	public String memberRegistByMobile(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException, JSONException, IOException {
		
	    String returnUrl = "portal/app/register";
	    boolean isMobile = CheckMobile.check(request.getHeader("user-agent"));
        if(isMobile)returnUrl = "portal/app/moblie-register";
	    
	    int isByMobile = 1;
		String mobile = request.getParameter("mobile");
		mobile = XssFilter.protectAgainstXSS(mobile);
		mobile  = HtmlRegexpUtil.filterHtml(mobile);
		String name = request.getParameter("name");
		name = XssFilter.protectAgainstXSS(name);
		name  = HtmlRegexpUtil.filterHtml(name);
		String password = request.getParameter("password");
		password = XssFilter.protectAgainstXSS(password);
		String validate = request.getParameter("validate");
		validate = XssFilter.protectAgainstXSS(validate);
         if(StringUtils.isBlank(mobile)){
        	 request.setAttribute("error", -1);
        	 return returnUrl;
         }
         if(StringUtils.isBlank(name)){
        	 request.setAttribute("error", -2);
        	 return returnUrl;
         }
         if(StringUtils.isBlank(password)){
        	 request.setAttribute("error", -3);
        	 return returnUrl;
         }
         if(StringUtils.isBlank(validate)){
        	 request.setAttribute("error", -4);
        	 return returnUrl;
         }
       
		// 判断手机号是否已经被注册
		if (memberService.checkMobileIsRepeat(mobile)) {
			model.put("text", "注册失败，手机已经被注册");
			request.setAttribute("error", -5);
            return returnUrl;  // 手机已经被注册
		} else {
			//验证昵称是否重复
//			 List<MemberInfo> elist=memberService.findInfoByPro("name", name);
//			 if(elist.size()>0){
//				 request.setAttribute("error", -11);
//				 return "portal/app/register";
//			 }
			if(KeyWordFilter.getInstance().isContentKeyWords(name)){
				request.setAttribute("error", -12);
	            return returnUrl; 
			}
			boolean checkCode = false;
			List<MobileCode> mobileCodeList = memberService.validateCode(
					mobile, validate);
			if (mobileCodeList != null && mobileCodeList.size() > 0) {
				MobileCode mobileCode = mobileCodeList.get(0);
				if ((new Date().getTime() - mobileCode.getCreateTime().getTime()) < Long.parseLong("180000")) { // 验证时间300000毫秒
					checkCode = true;
				} else {
					model.put("error", -6);
					request.setAttribute("mobile", mobile);
					request.setAttribute("name", name);
	                return returnUrl;  // 验证码的时间超过3分钟,已经失效
				}
			}
			if (!checkCode) {
				model.put("error", -7);
				request.setAttribute("mobile", mobile);
				request.setAttribute("name", name);
                return returnUrl; // 验证码错误
			} else {
				if (StringUtils.isNotBlank(mobile)
						&& StringUtils.isNotBlank(name)
						&& StringUtils.isNotBlank(password)
						&& StringUtils.isNotBlank(validate)) {
					String createIp = getIpAddr(request);
					Member member = memberService.saveMember(password, createIp);
					memberService.saveMemInfo(member, name);
					memberService.saveMemMobile(member, mobile, isByMobile);
					member.setStatus(1);
					memberService.updateMember(member);
					
					model.put("text", "注册成功");
					request.setAttribute("regSucc", 1);
					String logPass =  LoginMD5.compile(password);
					   List<LogUser> list=memberService.login(mobile, logPass);
					   if(list.size()>0){
						   LogUser loguser=list.get(0);
						   request.getSession().setAttribute("logUser",loguser);
					   }
					   
					if(isMobile){
					    String back_url = request.getParameter("back_url");
				        request.setAttribute("back_url", back_url);
					    model.addAttribute("login_state", "success");
					    return returnUrl;
					}
					return "portal/app/register/register_success";// 注册成功
				}
			}
		}
		request.setAttribute("regSucc", 0);
        return returnUrl;  //注册失败
	}


	/**
	 * 邮箱注册
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "memberRegistByEmail", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	ModelAndView memberRegistByEmail(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 注册
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String createIp = getIpAddr(request);
		String path = request.getContextPath();
		String resend=request.getParameter("resend");
		//验证邮箱是否重复
		if(!"1".equals(resend)){
		 List<MemberEmail> elist=memberService.findEmailByPro("email", email);
		 if(elist.size()>0){
			 resultMap.put("data", -110);
			 return jsonView(resultMap);
		 }
		 //验证昵称是否重复
//		 List<MemberInfo> elist1=memberService.findInfoByPro("name", name);
//		 if(elist1.size()>0){
//			 resultMap.put("data", -111);
//			 return jsonView(resultMap);
//		 }
		 if(KeyWordFilter.getInstance().isContentKeyWords(name)){
			    resultMap.put("data", -112);
			    return jsonView(resultMap); 
		 }
		}
		
        String basePath =getBasePath(request);
		if (memberService.processregister(email, name, password, createIp, basePath,resend)) {// 发邮箱激活
			resultMap.put("data", "发送成功");
			return jsonView(resultMap);
		} else {
			resultMap.put("data", "发送失败");
			return jsonView(resultMap);
		}
	}
	
	/****************************同步添加APP用户 start**************************************/
	/**
	 * 同步手机注册用户
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="SycMemberMobile",produces="text/html;charset=utf-8")
	@ResponseBody
	public String sycMemberMobile(HttpServletRequest request,
			HttpServletResponse response,String mobile,String name,String password) throws UnsupportedEncodingException, JSONException, IOException {
		int isByMobile = 1;
		JSONObject obj=new JSONObject();
         if(StringUtils.isBlank(mobile)){
        	 obj.put("status", -1);
        	 obj.put("msg", "手机号不能为空");
        	 return obj.toString();
         }
         if(StringUtils.isBlank(name)){
        	 obj.put("status", -1);
        	 obj.put("msg", "用户名不能为空");
        	 return obj.toString();
         }
         if(StringUtils.isBlank(password)){
        	 obj.put("status", -1);
        	 obj.put("msg", "密码不能为空");
        	 return obj.toString();
         }
         if(!MatchHolder.isEmpTelphone(mobile)){
 			obj.put("status", -1);
 			obj.put("msg", "手机号格式不正确");
 			return obj.toString(); //邮箱不符合规范
 		}	
		// 判断手机号是否已经被注册
		if (memberService.checkMobileIsRepeat(mobile)) {
			obj.put("status", -1);
       	    obj.put("msg", "该手机号已经被注册");
       	    return obj.toString();
		} else {
//			//验证昵称是否重复
//			 List<MemberInfo> elist=memberService.findInfoByPro("name", name);
//			 if(elist.size()>0){
//				 obj.put("status", -1);
//		       	 obj.put("msg", "该昵称已经被使用");
//				 return obj.toString();
//			 }
			if(KeyWordFilter.getInstance().isContentKeyWords(name)){
				obj.put("status", -1);
	       	    obj.put("msg", "昵称包含敏感词");
	       	    return obj.toString();
		    }
			if (StringUtils.isNotBlank(mobile)
					&& StringUtils.isNotBlank(name)
					&& StringUtils.isNotBlank(password)) {
				String createIp = getIpAddr(request);
				Member member = memberService.saveMember(password, createIp);
				memberService.saveMemInfo(member, name);
				memberService.saveMemMobile(member, mobile, isByMobile);
				member.setStatus(1);
				memberService.updateMember(member);
				
				obj.put("status", 1);
	        	obj.put("msg", "注册成功");
	           return obj.toString();
				
			}
			
		}
		obj.put("status", -1);
    	obj.put("msg", "注册失败");
        return obj.toString();  //注册失败
	}
	/**
	 * 同步邮箱注册用户
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping(value="SycMemberEmail",produces="text/html;charset=utf-8")
	@ResponseBody
	public String sycMemberEmail(HttpServletRequest request,
			HttpServletResponse response,String  email,String name,String password) throws JSONException {
		JSONObject obj=new JSONObject();
		if(StringUtils.isBlank(email)){
       	 obj.put("status", -1);
       	 obj.put("msg", "邮箱不能为空");
       	 return obj.toString();
        }
        if(StringUtils.isBlank(name)){
       	 obj.put("status", -1);
       	 obj.put("msg", "用户名不能为空");
       	 return obj.toString();
        }
        if(StringUtils.isBlank(password)){
       	 obj.put("status", -1);
       	 obj.put("msg", "密码不能为空");
       	 return obj.toString();
        }
        if(!MatchHolder.isEmail(email)){
			obj.put("status", -1);
			obj.put("msg", "邮箱格式不正确");
			return obj.toString(); //邮箱不符合规范
		}	
		String createIp = getIpAddr(request);
		 //验证邮箱是否重复
		 List<MemberEmail> elist=memberService.findEmailByPro("email", email);
		 if(elist.size()>0){
			 obj.put("status", -1);
	         obj.put("msg", "该邮箱已经被使用");
	         return obj.toString();
		 }
		//验证昵称是否重复
//		 List<MemberInfo> elist1=memberService.findInfoByPro("name", name);
//		 if(elist1.size()>0){
//			 obj.put("status", -1);
//	       	 obj.put("msg", "该昵称已经被使用");
//			 return obj.toString();
//		 }
		 if(KeyWordFilter.getInstance().isContentKeyWords(name)){
				obj.put("status", -1);
	       	    obj.put("msg", "昵称包含敏感词");
	       	    return obj.toString();
		 }
        String basePath =getBasePath(request);
		if (memberService.processregister(email, name, password, createIp, basePath,"")) {// 发邮箱激活
			 obj.put("status", 1);
	       	 obj.put("msg", "注册成功");
	       	 return obj.toString();
		} else {
			 obj.put("status", -1);
	       	 obj.put("msg", "注册失败");
	       	 return obj.toString();
		}
	}
	
	/****************************同步添加APP用户 end************************************/
	
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
     * 验证邮箱
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequestMapping("emailValidate")
	public String emailValidate(HttpServletRequest request,
			HttpServletResponse response, String code) {
//      String email = request.getParameter("email");//获取email
//      String validateCode = request.getParameter("validateCode");//激活码
       if(StringUtils.isBlank(code)){
    	   return "redirect:/";
       }
       String decode=Security.getInstance().decode(code);
	   String timeStr=decode.split(",")[0];
	   String email=decode.split(",")[1];
	   String validateCode=decode.split(",")[2];
	   String pass=decode.split(",")[3];
	   long time=Long.parseLong(timeStr);
	   if(System.currentTimeMillis()>time){
		   request.setAttribute("overTime", 1);
		  List<MemberEmail> list= memberService.findEmailByPro("email", email);
		  for(MemberEmail me:list){
			  if(me.getIsBind()==0){
				 // memberService.deleteByCode(MemberEmail.class,me.getCode());
				 
				  if(me.getMemberCode()!=null){
					 memberService.delPhyByMemCode(me.getMemberCode());
				  }
			  }
		  }
		   return "portal/app/register";//return error("邮箱验证超时");
	   }
	   
      try {
         memberService.processActivate(email,validateCode);//调用激活方法
          List<LogUser> list=memberService.login(email, pass);
		   if(list.size()>0){
			   LogUser loguser=list.get(0);
				request.getSession().setAttribute("logUser",loguser);
		   }
		  
		return "portal/app/register/register_success";	
	  // return "redirect:"+getBasePath(request)+"/portal/clientLog/loginPage?xparam=1";//跳转的页面待改
      } catch (ServiceException e) {
          request.setAttribute("message", e.getMessage());
          return "portal/app/register";   
      }
	}
	
	/**
	 * 是否邮箱重复
	 * @throws JSONException 
	 */
	@RequestMapping(value="isEmailRepeat",produces="text/html;charset=utf-8")
	@ResponseBody
	public String isEmailRepeat(String email) throws JSONException{
		JSONObject obj=new JSONObject();
		//验证邮箱是否重复
		 List<MemberEmail> elist=memberService.findEmailByPro("email", email);
		 if(elist.size()>0){
			 obj.put("data", 1);
		 }else{
			 obj.put("data", 0);
		 }
		 return obj.toString();
	}

	/**
	 * 是否昵称重复
	 * @throws JSONException 
	 */
	@RequestMapping(value="isNikeRepeat",produces="text/html;charset=utf-8")
	@ResponseBody
	public String isNikeRepeat(String name) throws JSONException{
		JSONObject obj=new JSONObject();
		//验证昵称是否重复
		 List<MemberInfo> elist=memberService.findInfoByPro("name", name);
		 if(elist.size()>0){
			 obj.put("data", 1);
		 }else{
			 obj.put("data", 0);
		 }
		 return obj.toString();
	}
	/**
	 * 验证填写的手机号是否已经被注册
	 */
	@RequestMapping("isMobileRepeat")
	public @ResponseBody
	ModelAndView isMobileIsRepeat(String mobile) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", 0);
		resultMap.put("data", 0);
		if (StringUtils.isNotBlank(mobile)) {
			if (!MatchHolder.isEmpTelphone(mobile)) {
				resultMap.put("msg", "手机号码不符合格式");
				return jsonView(resultMap); // 手机号码不符合格式
			}
			if (memberService.checkMobileIsRepeat(mobile)) {
				resultMap.put("msg", "您输入的手机号已经被注册");
				return jsonView(resultMap);
			} else {
				
				resultMap.put("data", 1);
				return jsonView(resultMap);
			}
		} else {
			resultMap.put("msg", "不能输入为空");
			return jsonView(resultMap);
		}
	}

	/**
	 * 验证手机号，并给手机发短信
	 * 
	 * @param mobile您好，尊敬的天上西藏客户
	 * @return
	 */
	@RequestMapping(value="sendValidate",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ModelAndView sendValidate(String mobile) {
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("msg", 0);
		try {
			if (StringUtils.isBlank(mobile)) {
				resultMap.put("msg", "发送失败，电话号码为空");
				return jsonView(resultMap); // 传入电话号码为空
			}
			if (!MatchHolder.isEmpTelphone(mobile)) {
				resultMap.put("msg", "手机号码不符合格式");
				return jsonView(resultMap);  // 手机号码不符合格式
			}
			List<MobileCode> mobileCodeList = memberService.validateCode(mobile);
			boolean checkCode = mobileCodeList != null && mobileCodeList.size() > 0 ? true : false;
			// 判断手机号是否已经被注册
			if (memberService.checkMobileIsRepeat(mobile)) {
				resultMap.put("msg", "短信发送失败，手机已经被注册");
				resultMap.put("data", "-1");
	            return jsonView(resultMap);  // 手机已经被注册
			} 
			
			String code = String.valueOf(Math.round(Math.random() * 899999 + 100000));// 随机生成6位随机数
			String[] phone = { mobile };
			//boolean b = SendSMS.send("你好,你的手机验证码是(" + code + ")请注意查收", phone);
			String msg="尊敬的用户您好,您的手机验证码是(" + code + ")，请注意查收";
			send s=new send(phone,msg,"byMobile");
			 Thread thread=new Thread(s);
			 thread.start();
			MobileCode mobileCode = new MobileCode();
			mobileCode.setMobile(mobile);
			mobileCode.setValidateCode(code);
			mobileCode.setCreateTime(new Timestamp(new Date().getTime()));
			if (checkCode == true) { // 如果验证码已存在就更新验证码,否则就新增
				mobileCode.setId(mobileCodeList.get(0).getId());
				memberService.updateMobileCode(mobileCode);
			} else {
				memberService.saveMobileCode(mobileCode);
			}
			resultMap.put("msg", "消息发送成功");
			return jsonView(resultMap); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultMap.put("msg", "消息发送失败");
		return jsonView(resultMap); 
	}
 
	/**
	 * 验证验证码是否正确
	 * @return
	 */
	@RequestMapping("checkValidate")
	public String checkValidate(String mobile,String validate){
		if(StringUtils.isBlank(validate)||StringUtils.isBlank(mobile)){
			return "false"; //传入参数为空
		}
		if (!MatchHolder.isEmpTelphone(mobile)) {
			return "false"; //手机号码不符合格式
		}
		List<MobileCode> mobileCodeList = memberService.validateCode(mobile, validate);
		boolean checkCode = false;
		if (mobileCodeList != null && mobileCodeList.size() > 0) {
			MobileCode mobileCode = mobileCodeList.get(0);
			if ((new Date().getTime() - mobileCode.getCreateTime().getTime()) < Long.parseLong("300000")) {
				checkCode = true;
			} else {
				
				return "false"; //验证码已经失效
			}
		}
		if (!checkCode) {
			return "false"; //验证码错误
		} else {
			return "success";  //验证码正确
		}
		
	}
	
	
	
	@RequestMapping("register")
	public String test(ModelMap model,HttpServletRequest req) {
		int num=memberService.memberNum();
		Random rand = new Random();
		int randNum = rand.nextInt(100);
		req.setAttribute("nameHolder", "朝圣者"+randNum+num);
		boolean isMobile = CheckMobile.check(req.getHeader("user-agent"));
	    if(isMobile){
	        String back_url = request.getParameter("back_url");
            request.setAttribute("back_url", back_url);
	        return "portal/app/moblie-register";
	    }
		return "portal/app/register";
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
}
