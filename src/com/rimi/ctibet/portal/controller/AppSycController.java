package com.rimi.ctibet.portal.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.rimi.ctibet.common.exception.JsonParseException;
import com.rimi.ctibet.common.util.ImageHelper;
import com.rimi.ctibet.common.util.JsonUtil;
import com.rimi.ctibet.common.util.KeyWordFilter;
import com.rimi.ctibet.common.util.LoginMD5;
import com.rimi.ctibet.common.util.MatchHolder;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.common.util.security.Coder;
import com.rimi.ctibet.common.util.security.RSACoder;
import com.rimi.ctibet.common.util.security.Security;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.LoginLog;
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.domain.MemberEmail;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.domain.MemberMobile;
import com.rimi.ctibet.web.service.ILoginLogService;
import com.rimi.ctibet.web.service.IMemberMobileService;
import com.rimi.ctibet.web.service.IMemberService;

@Controller
@RequestMapping("/portal/appSycController/")
public class AppSycController {
	 private static final String ACCESSFILETYPE = ".*(jpeg|jpg|png|gif|bmp)$";
	 private final String inputStr = "tianshangxizangToken";  
	
     @Resource
     private IMemberService memberService;
     @Resource
     private IMemberMobileService memberMobileService;
     @Resource
     private ILoginLogService loginLogService;
    
     public boolean decodeByPrivateKey(String token) throws Exception {  
        try{     
         
         //解密参数
         byte[] requestParamDecoder = Security.getInstance().decode(token) ;
         
         byte[] decodedData = RSACoder.decryptByPrivateKey(requestParamDecoder,ReadSettingProperties.getValue("rsa.properties", "privateKey"));  
         String outputStr = new String(decodedData);  
         return inputStr.equals(outputStr);
        }catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			return false;
		}
     }
     
     @RequestMapping(value="encodeByPublicKey",produces="text/html;charset=utf-8")
     @ResponseBody
     public String encodeByPublicKey() throws JSONException, Exception{
    	 try{ 
             byte[] data = inputStr.getBytes();  
             byte[] encodedData = RSACoder.encryptByPublicKey(data, ReadSettingProperties.getValue("rsa.properties", "publicKey"));  
             String requestParam =Security.getInstance().encode(encodedData); //加密参数
             return success(requestParam);
    	 }catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			return error("加密失败");
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
     @RequestMapping(value="login",produces="text/html;charset=UTF-8")
     @ResponseBody
     public String login(String tlogUser,String logPass,HttpServletRequest req,HttpServletResponse res,HttpSession session) throws JSONException, UnsupportedEncodingException, IOException{
  	   if(StringUtils.isBlank(tlogUser)){
  		  return error("用户名不能为空");
  	   }
  	   if(StringUtils.isBlank(logPass)){
  		  return error("密码不能为空");
  	   }
  	  
  	   //String password = new String(new BASE64Decoder().decodeBuffer(logPass),"utf-8");
  	   logPass =  LoginMD5.compile(logPass);
  	   List<LogUser> list=memberService.login(tlogUser, logPass);
  	   if(list.size()<=0){
  		  return error("用户名或密码错误");
  	   }
  	   LogUser loguser=list.get(list.size()-1);
  	   if(loguser!=null){
  		   if(loguser.getEmail()==null&&loguser.getMobile()==null){
  			   return  error("未找到该用户");
  		   }
  		   //System.out.println(loguser.getStatus());
  		   if(loguser.getStatus()==0){
  			   return error("账户已被停封，请与管理员联系");
  		   }
  		   if(loguser.getIsBind()!=null&&loguser.getEmail()!=null&&loguser.getMobile()==null){
  			   if(loguser.getIsBind()==0){
  				  
  				   return error("请先到邮箱激活账户");
  			   }
  		   }
  		   if(loguser.getIsVerified()!=null&&loguser.getEmail()==null&&loguser.getMobile()!=null){
  			   if(loguser.getIsVerified()==0){
  				 
  				   return  error("该账户尚未激活");
  			   }
  		   }
  		   
  		   if(loguser.getIsVerified()!=null&&loguser.getEmail()!=null&&loguser.getMobile()!=null&&loguser.getIsBind()!=null){
  			   if(loguser.getIsVerified()==0&&!tlogUser.contains("@")){
  				   return  error("该账户尚未激活");
  			   }
  			   if(loguser.getIsBind()==0&&tlogUser.contains("@")){
  				   return error("请先到邮箱激活账户");
  			   }
  		   }
  	   }
  	   //用户勾选了记住我
  	   String rem=req.getParameter("remember");
  	   if ("1".equals(rem)) {
  			Security security = Security.getInstance();
  			Long time = System.currentTimeMillis() + 2592000000L;// 一个月后过期
  			StringBuffer tokenStr = new StringBuffer("");
  			Cookie token = new Cookie("token", security.encodeStr(tokenStr.append(
  					time).append(",").append(tlogUser).append(",").append(
  					logPass).toString()));
  			token.setMaxAge(2592000);// 一个月后失效
  			token.setPath("/");
  			res.addCookie(token);
  		} else {
  			Cookie token = new Cookie("token", null);// 删除cookie
  			token.setMaxAge(0);
  			res.addCookie(token);
  		}
  	   //向登录日志加入数据
  	   LoginLog ll=new LoginLog();
  	   ll.setLogDate(new Date());
  	   ll.setMemberCode(loguser.getCode());
  	   loginLogService.save(ll);
  	   req.getSession().setAttribute("logUser",loguser);
  	   return success("登录成功");
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
 	@RequestMapping(value="registByApp",produces="text/html;charset=utf-8")
 	@ResponseBody
 	public String registByApp(HttpServletRequest request,
 			HttpServletResponse response,String email,String mobile,String type,String name,String password,String token) throws Exception {
 		 //验证rsa
		if(!decodeByPrivateKey(token)){
	 		 return error("验证未通过");
	 	 }
 	 if(StringUtils.isBlank(type)){
 		 return error("type不能为空");
 	 }
 	 if("byMobile".equals(type)){
	 		int isByMobile = 1;
	        if(StringUtils.isBlank(mobile)){
	     	   return error("手机号不能为空");
	        }
	        if(StringUtils.isBlank(name)){
	     	   return error("用户名不能为空");
	        }
	        if(StringUtils.isBlank(password)){
	    	   return error("密码不能为空");
	        }
	        if(!MatchHolder.isEmpTelphone(mobile)){
			   return error("手机号格式不正确");
	  		}	
	 		// 判断手机号是否已经被注册
	 		if (memberService.checkMobileIsRepeat(mobile)) {
	           return error("该手机号已经被注册");
	        	   
	 		} else {
	
	 			if(KeyWordFilter.getInstance().isContentKeyWords(name)){
	 	       	   return error("昵称包含敏感词");
	 		    }
	 			if (StringUtils.isNotBlank(mobile)
	 					&& StringUtils.isNotBlank(name)
	 					&& StringUtils.isNotBlank(password)) {
	 				String createIp = getIpAddr(request);
	 			    try {
	 			    	Member member = memberService.saveMember(password, createIp);
	 	 				memberService.saveMemInfo(member, name);
	 	 				memberService.saveMemMobile(member, mobile, isByMobile);
//	 	 				member.setStatus(1);
//	 	 				memberService.updateMember(member);
	 	 				
					} catch (Exception e) {
						e.printStackTrace();// TODO: handle exception
						return inserterror("注册失败");
					}
	 				
	 	           return success("注册成功");
	 				
	 			}
	 			
	 		}
 	  }
 	  if("byEmail".equals(type)){
	 		 if(StringUtils.isBlank(email)){
	        	return error("邮箱不能为空");
	         }
	         if(StringUtils.isBlank(name)){
	        	return error("用户名不能为空");
	         }
	         if(StringUtils.isBlank(password)){
	        	return error("密码不能为空");
	         }
	         if(!MatchHolder.isEmail(email)){
	 			return error("邮箱格式不正确");
	 		}	
	 		String createIp = getIpAddr(request);
	 		 //验证邮箱是否重复
	 		 List<MemberEmail> elist=memberService.findEmailByPro("email", email);
	 		 if(elist.size()>0){
	 			return error("该邮箱已经被使用");
	 		 }
	 		 if(KeyWordFilter.getInstance().isContentKeyWords(name)){
	 				return error("昵称包含敏感词");
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
	        	 return success("注册成功");
			 } catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
				return inserterror("注册失败");
			 } 
 	  }
 		return error("注册类型有误，请检查参数type");  //注册失败
 	}
 	/**
	 * 修改
	 * 
	 * @param req
	 * @param sex
	 * @param file
	 * @param email
	 * @param phone
	 * @param username
	 * @param code
	 * @param birthday
	 * @param province
	 * @param city
	 * @param score
	 * @return
 	 * @throws Exception 
	 */
	@RequestMapping(value="update",produces="text/html;charset=utf-8")
	@ResponseBody
	public String update(HttpServletRequest req, String sex,
			String email, String mobile, String name, String code,
			String birthday, String province, String city,String token)
			throws Exception {
		// 判断用户是否登录
 		LogUser user = (LogUser) req.getSession().getAttribute("logUser");
		if (user == null) {
			return error("请先登录");
		}
        //验证rsa		
		if(!decodeByPrivateKey(token)){
	 		 return error("验证未通过");
	 	 }
		if(user!=null){
			code=user.getCode();
		}
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		// 验证电话号码是否重复
		if (StringUtils.isNotBlank(mobile)) {
			List<MemberMobile> mlist = memberService.findMobileByPro("mobile",
					mobile);
			if (mlist.size() > 0) {
				MemberMobile mm = mlist.get(0);
				if (mm != null) {
					if (mm.getMemberCode() != null) {
						if (!code.equals(mm.getMemberCode())) {
							return error("手机号不能重复");
						}
					}
				}
			}
		}
		// 验证邮箱是否重复
		if (StringUtils.isNotBlank(email)) {
			List<MemberEmail> elist = memberService.findEmailByPro("email",
					email);
			if (elist.size() > 0) {
				MemberEmail me = elist.get(0);
				if (me != null) {
					if (!code.equals(me.getMemberCode())) {
						return error("邮箱不能重复");
					}
				}
			}
		}
		
		Member m = memberService.findByCode(code);
		
		// 实例化对象
		MemberInfo mi = new MemberInfo();
		MemberMobile mm = new MemberMobile();
		MemberEmail me = new MemberEmail();

		List<MemberInfo> milist = memberService.findbymecode(code);
		if (milist.size() > 0) {
			mi = milist.get(0);
			if(StringUtils.isNotBlank(sex))
			mi.setSex(Integer.parseInt(sex));
			mi.setEmail(email);
			mi.setPhone(mobile);
			mi.setName(name);
			if (StringUtils.isNotBlank(birthday))
				mi.setBirthday(fmt.parse(birthday));
			mi.setProvince(province);
			mi.setCity(city);
			// mi.setScore(Integer.parseInt(score));
		}
		if (StringUtils.isNotBlank(email)) {
			List<MemberEmail> melist = memberService.findEmailByPro(
					"memberCode", code);
			if (melist.size() > 0) {
				me = melist.get(0);
				me.setIsBind(1);
				me.setEmail(email);
			}
		}
		if (StringUtils.isNotBlank(mobile)) {
			List<MemberMobile> mmlist = memberService.findMobileByPro(
					"memberCode", code);
			if (mmlist.size() > 0) {
				mm = mmlist.get(0);
				mm.setIsVerified(1);
				mm.setMobile(mobile);
			} else {
				MemberMobile membermo = new MemberMobile();
				membermo.setAvaliable(1);
				membermo.setCode(Uuid.uuid());
				membermo.setIsVerified(0);
				membermo.setMemberCode(code);
				membermo.setMobile(mobile);
				memberMobileService.save(membermo);
			}
		}
		try {
			memberService.updateByModel(m, mi, me, mm);
			return success("修改成功");
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			return inserterror("修改失败");
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
 	 * 修改密码
 	 * 
 	 * @param req
 	 * @param oldPass
 	 * @param newPass
 	 * @return
     * @throws Exception 
 	 */
 	@RequestMapping(value="updatePass",produces="text/html;charset=utf-8")
 	@ResponseBody
 	public String updatePass(HttpServletRequest req, String oldPassword,
 			String newPassword,String token) throws Exception {
 		// 判断用户是否登录
 		LogUser user = (LogUser) req.getSession().getAttribute("logUser");
		if (user == null) {
			return error("请先登录");
		}
		 //验证rsa
		if(!decodeByPrivateKey(token)){
	 		 return error("验证未通过");
	 	 }
 		if (StringUtils.isBlank(oldPassword)) {
 			return error("原密码不能为空");
 		}
 		if (StringUtils.isBlank(newPassword)) {
 			return error("新密码不能为空");
 		}
 		oldPassword = LoginMD5.compile(oldPassword);
 		if (!oldPassword.equals(user.getPassword())) {
 			return error("原密码错误");
 		}
 		Member m = memberService.findByCode(user.getCode());
 		m.setPassword(LoginMD5.compile(newPassword));
 		try {
 			memberService.updateMember(m);
 		} catch (Exception e) {
 			e.printStackTrace();// TODO: handle exception
 			return inserterror("修改失败");
 		}
 		
 		return success("修改成功");

 	}
 	
 	/**
	 * 上传头像
	 * 
	 * @param req
	 * @param file
	 * @return
	 * @throws JsonParseException
 	 * @throws Exception 
	 */
	@RequestMapping(value = "uploadPic", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String uploadPic(HttpServletRequest req, MultipartFile picFile,String token)
			throws JsonParseException, Exception {
		JSONObject obj = new JSONObject();
		try {
			LogUser user = (LogUser) req.getSession().getAttribute("logUser");
			if (user == null) {
				return error("请先登录");
			}
			 //验证rsa
			if(!decodeByPrivateKey(token)){
		 		 return error("验证未通过");
		 	 }
			if (picFile == null) {
				return error("头像不能为空");
			}
			if (!storeFile(req, picFile)) {
				return error("上传失败");
			}
			String pic = (String) req.getSession()
					.getAttribute("uploadPicPath");
			obj.put("status", 1);
			obj.put("pic", pic);
			if (user.getCode() != null) {
				MemberInfo mi = memberService.findMemberInfo(user.getCode());
				if (mi != null) {
					mi.setPic(pic);
					try {
						memberService.updateMemberInfo(mi);
					} catch (Exception e) {
						e.printStackTrace();// TODO: handle exception
						return inserterror("上传失败");
					}
				}
			}
			req.getSession().removeAttribute("uploadPicPath");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return JsonUtil.toJson(obj);
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
	 * 上传文件
	 * 
	 * @param picFile
	 * @param gradutePic
	 * @param student
	 * @return
	 */
	

	private boolean storeFile(HttpServletRequest req, MultipartFile imgFile) {
		try {
			if (!imgFile.isEmpty()) {
				if (!checkFileType(imgFile, ACCESSFILETYPE)) {
					//System.out.println("File Type Not Allow!");
					return false;
				}
				String rpath = req.getSession().getServletContext()
						.getRealPath("/");
				String fileName = System.currentTimeMillis() + "."
						+ getFileExt(imgFile.getOriginalFilename());
				String path = rpath + getStorePath() + fileName;
				String repath = getStorePath() + fileName;
				checkFolder(rpath + getStorePath());
				writeByte(path, imgFile.getBytes());
				req.getSession().setAttribute("uploadPicPath", repath);
				ImageHelper.reduceImg(path, path, 69, 48);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			//System.out.println("Student Pic or GradutePic Upload Faild!");
			return false;
		}

		return true;
	}

	/**
	 * 上传
	 * 
	 * @param fileName
	 * @param b
	 * @return
	 */
	public static boolean writeByte(String fileName, byte[] b) {
		try {
			BufferedOutputStream fos = new BufferedOutputStream(
					new FileOutputStream(fileName));
			fos.write(b);
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public String getFileExt(String fileName) {
		if (fileName != null) {
			String fileExt = "";
			fileName = fileName.toLowerCase();
			int index = fileName.lastIndexOf(".");
			fileExt = fileName.substring(index + 1, fileName.length());
			return fileExt;
		} else {
			return "";
		}
	}

	/**
	 * 判断目录是否存在，如果不存在则建立
	 * 
	 * @param fileRealPath
	 * @return
	 */
	public boolean checkFolder(String fileRealPath) {
		boolean tt = false;
		try {
			File f = new File(fileRealPath);
			if (f.exists()) {
				tt = true;
			} else {
				tt = f.mkdirs();
			}
		} catch (Exception ex) {
			//System.out.println("[checkFolder] error:" + ex.getMessage());
		}
		return tt;
	}

	/**
	 * 检测文件类型
	 * 
	 * @param fileResource
	 * @param ACCESS_FILE_TYPE
	 * @return
	 */
	public boolean checkFileType(MultipartFile fileResource,
			String ACCESS_FILE_TYPE) {
		if (fileResource == null || fileResource.isEmpty()) {
			return true;
		}

		if (getFileExt(fileResource.getOriginalFilename()).toLowerCase()
				.matches(ACCESS_FILE_TYPE)) {
			return true;
		}

		return false;
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
	private static String error(String msg) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("status", -1);
		obj.put("msg", msg);
		return obj.toString();
	}
	/**
	 * 返回错误信息
	 * 
	 * @param msg
	 * @return
	 * @throws JSONException
	 */
	private static String inserterror(String msg) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("status", 0);
		obj.put("msg", msg);
		return obj.toString();
	}
	/**
	 * 返回成功信息
	 * 
	 * @param msg
	 * @return
	 * @throws JSONException
	 */
	private static String success(String msg) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("status", 1);
		obj.put("msg", msg);
		return obj.toString();
	}
	
	
}
