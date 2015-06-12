package com.rimi.ctibet.portal.controller.UserCenter;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rimi.ctibet.common.exception.JsonParseException;
import com.rimi.ctibet.common.sortmsg.SendSMS;
import com.rimi.ctibet.common.util.ImageHelper;
import com.rimi.ctibet.common.util.JsonUtil;
import com.rimi.ctibet.common.util.LoginMD5;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.common.util.sendemail.SendEmail;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.domain.MemberEmail;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.domain.MemberMobile;
import com.rimi.ctibet.domain.MobileCode;
import com.rimi.ctibet.portal.controller.LoginController;
import com.rimi.ctibet.web.service.IMemberEmailService;
import com.rimi.ctibet.web.service.IMemberMobileService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IMobileCodeService;

@Controller
@RequestMapping({"/portal/userAccountSet/","/member/account"})
public class UserAccountSetController {
	@Resource
	private IMemberService memberService;
	@Resource
	private IMemberEmailService memberEmailService;
	@Resource
	private IMemberMobileService memberMobileService;
	@Resource
	private IMobileCodeService mobileCodeService;
    
	
	//只要进入这个controller就会先执行这个方法
	@ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.put("programNam","60");
	}
	@RequestMapping("userhome")
	public String userhome(HttpServletRequest req) {
		LogUser lg = (LogUser) req.getSession().getAttribute("logUser");
		if (lg == null) {
			return "redirect:/";
		}
		return "portal/app/usercenter/home";
	}

	@RequestMapping("toEditPage")
	public String toEditPage(HttpServletRequest req, String type) {
		LogUser lg = (LogUser) req.getSession().getAttribute("logUser");
		if (lg == null) {
			return "redirect:/";
		}
		List<MemberInfo> list = memberService.findbymecode(lg.getCode());
		if (list.size() <= 0) {
			return "redirect:/";
		}
		req.setAttribute("type", type);
		return "portal/app/usercenter/modified_data";
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
	 * @param password
	 * @param birthday
	 * @param province
	 * @param city
	 * @param score
	 * @return
	 * @throws JSONException
	 * @throws ParseException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest req, String sex, String pic,
			String email, String phone, String username, String password,
			String birthday, String province, String city, String score)
			throws JSONException, ParseException, UnsupportedEncodingException,
			IOException {
		LogUser lg = (LogUser) req.getSession().getAttribute("logUser");
		if (lg == null) {
			return "redirect:/";
		}
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		//String code = req.getParameter("code");
		String code = req.getParameter("memberCode");//
		req.setAttribute("sex", sex);
		req.setAttribute("email", email);
		req.setAttribute("phone", phone);
		req.setAttribute("username", username);
		req.setAttribute("birthday", birthday);
		req.setAttribute("province", province);
		req.setAttribute("city", city);
		req.setAttribute("score", score);
		// 验证电话号码是否重复
		if (StringUtils.isNotBlank(phone)) {
			List<MemberMobile> mlist = memberService.findMobileByPro("mobile",
					phone);
			if (mlist.size() > 0) {
				MemberMobile mm = mlist.get(0);
				if (mm != null) {
					if (mm.getMemberCode() != null) {
						if (!code.equals(mm.getMemberCode())) {
							if (StringUtils.isNotBlank(birthday)) {
								req.setAttribute("birthday",
										fmt.parse(birthday));
							}
							req.setAttribute("province", province);
							req.setAttribute("city", city);
							req.setAttribute("phoneExsist", 1);
							return "portal/app/usercenter/modified_data";
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
						if (StringUtils.isNotBlank(birthday)) {
							req.setAttribute("birthday", fmt.parse(birthday));
						}
						req.setAttribute("emailExsist", 1);
						req.setAttribute("province", province);
						req.setAttribute("city", city);
						return "portal/app/usercenter/modified_data";
					}
				}
			}
		}
		// 验证昵称是否重复
		// if(StringUtils.isNotBlank(username)){
		// //验证昵称是否重复
		// List<MemberInfo> elist1=memberService.findInfoByPro("name",
		// username);
		// if(elist1.size()>0){
		// MemberInfo me=elist1.get(0);
		// if(me!=null){
		// if(!code.equals(me.getMemberCode())){
		// if(StringUtils.isNotBlank(birthday)){
		// req.setAttribute("birthday", fmt.parse(birthday));
		// }
		// req.setAttribute("nameExsist", 1);
		// req.setAttribute("province", province);
		// req.setAttribute("city", city);
		// return "portal/app/usercenter/modified_data";
		// }
		// }
		// }
		// }
		Member m = memberService.findByCode(code);
		if (StringUtils.isNotBlank(password)) {
			m.setPassword(LoginMD5.compile(password));
		}
		// 实例化对象
		MemberInfo mi = new MemberInfo();
		MemberMobile mm = new MemberMobile();
		MemberEmail me = new MemberEmail();

		List<MemberInfo> milist = memberService.findbymecode(code);
		if (milist.size() > 0) {
			mi = milist.get(0);

            if (StringUtils.isNotBlank(pic)) {
                mi.setPic(pic);
            }
			mi.setSex(Integer.parseInt(sex));
			mi.setEmail(email);
			mi.setPhone(phone);
			mi.setName(username);
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
		if (StringUtils.isNotBlank(phone)) {
			List<MemberMobile> mmlist = memberService.findMobileByPro(
					"memberCode", code);
			if (mmlist.size() > 0) {
				mm = mmlist.get(0);
				mm.setIsVerified(1);
				mm.setMobile(phone);
			} else {
				MemberMobile membermo = new MemberMobile();
				membermo.setAvaliable(1);
				membermo.setCode(Uuid.uuid());
				membermo.setIsVerified(0);
				membermo.setMemberCode(code);
				membermo.setMobile(phone);
				memberMobileService.save(membermo);
			}
		}
		try {
			memberService.updateByModel(m, mi, me, mm);
			req.setAttribute("updateflag", 1);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		if (StringUtils.isNotBlank(lg.getEmail())) {
			List<LogUser> list = memberService.login(lg.getEmail(),
					lg.getPassword());
			if (list.size() > 0) {
				LogUser loguser = list.get(0);
				req.getSession().setAttribute("logUser", loguser);
			}

		}
		if (StringUtils.isNotBlank(lg.getMobile())) {
			List<LogUser> list = memberService.login(lg.getMobile(),
					lg.getPassword());
			if (list.size() > 0) {
				LogUser loguser = list.get(0);
				req.getSession().setAttribute("logUser", loguser);
			}

		}
		String path = req.getContextPath();
		String basePath = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + path + "/";
		return "redirect:" + basePath + "member/userinfo/getAllMyMsg";
	}

	public String check(HttpServletRequest req, String optType, String sex,
			String email, String phone, String username, String password,
			String birthday, String province, String city, String score) {
		req.setAttribute("sex", sex);
		req.setAttribute("email", email);
		req.setAttribute("phone", phone);
		req.setAttribute("username", username);
		req.setAttribute("birthday", birthday);
		req.setAttribute("province", province);
		req.setAttribute("city", city);
		req.setAttribute("score", score);

		if (StringUtils.isBlank(sex)) {
			// req.setAttribute("error", -1);
			return "-1";
		}
		if (StringUtils.isBlank(email)) {
			// req.setAttribute("error", -2);
			return "-2";
		}
		if (StringUtils.isBlank(phone)) {
			// req.setAttribute("error", -3);
			return "-3";
		}
		if (StringUtils.isBlank(username)) {
			// req.setAttribute("error", -4);
			return "-4";
		}
		// if(StringUtils.isBlank(password)){
		// req.setAttribute("error", -5);
		// return "manage/html/settings/member_add";
		// }
		if (StringUtils.isBlank(birthday)) {
			// req.setAttribute("error", -6);
			return "-6";
		}
		if (StringUtils.isBlank(province)) {
			// req.setAttribute("error", -7);
			return "-7";
		}
		if (StringUtils.isBlank(city)) {
			// req.setAttribute("error", -8);
			return "-8";
		}
		if (StringUtils.isBlank(score)) {
			// req.setAttribute("error", -9);
			return "-9";
		}

		return null;
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
	@RequestMapping("updatePass")
	public String updatePass(HttpServletRequest req, String oldPass,
			String newPass) throws JSONException {
		// 判断用户是否登录
		LogUser user = (LogUser) req.getSession().getAttribute("logUser");
		if (user == null) {
			return "redirect:/";
		}
		if (StringUtils.isBlank(oldPass)) {
			req.setAttribute("error", -110);
			req.setAttribute("type", "pass");
			return "portal/app/usercenter/modified_data";
		}
		if (StringUtils.isBlank(newPass)) {
			req.setAttribute("error", -111);
			req.setAttribute("type", "pass");
			return "portal/app/usercenter/modified_data";
		}
		oldPass = LoginMD5.compile(oldPass);
		if (!oldPass.equals(user.getPassword())) {
			req.setAttribute("error", -112);
			req.setAttribute("type", "pass");
			return "portal/app/usercenter/modified_data";
		}
		Member m = memberService.findByCode(user.getCode());
		m.setPassword(LoginMD5.compile(newPass));
		try {
			memberService.updateMember(m);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			return "redirect:/";
		}
		req.setAttribute("hasChanged", 1);
		String path = req.getContextPath();
		String basePath = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + path + "/";
		return "redirect:" + basePath + "portal/clientLog/loginPage?logFlag=1";

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
	@RequestMapping("updatePassAjax")
	@ResponseBody
	public String updatePassAjax(HttpServletRequest req, String oldPass,
			String newPass) throws JSONException {
		// 判断用户是否登录
		LogUser user = (LogUser) req.getSession().getAttribute("logUser");
		if (user == null) {
			return error("没有登陆");
		}
		if (StringUtils.isBlank(oldPass)) {
			req.setAttribute("error", -110);
			return error("原密码错误");
		}
		if (StringUtils.isBlank(newPass)) {
			req.setAttribute("error", -111);
			return error("新密码错误");
		}
		oldPass = LoginMD5.compile(oldPass);
		if (!oldPass.equals(user.getPassword())) {
			req.setAttribute("error", -112);
			return error("原密码错误");
		}
		Member m = memberService.findByCode(user.getCode());
		m.setPassword(LoginMD5.compile(newPass));
		try {
			memberService.updateMember(m);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			return error("修改失败");
		}
		req.setAttribute("hasChanged", 1);
		String path = req.getContextPath();
		String basePath = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + path + "/";
		return success("修改成功");

	}

	/**
	 * 找回密码发送手机验证码/或者邮箱验证
	 * 
	 * @param phone
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "sendMsg", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String sendMsg(String mobile, String email, String findType,
			HttpServletRequest req) throws JSONException {
		// 判断用户是否登录
		LogUser user = (LogUser) req.getSession().getAttribute("logUser");
		if (user == null) {
			return error("请先登录");
		}
		String memberCode = req.getParameter("memberCode");
		// 如果通过手机号找回密码
		if ("byMobile".equals(findType)) {
			if (StringUtils.isBlank(mobile)) {
				return error("手机号不能为空");
			}
			// 验证电话号码是否重复
			if (StringUtils.isNotBlank(mobile)) {
				List<MemberMobile> mlist = memberService.findMobileByPro(
						"mobile", mobile);
				if (mlist.size() > 0) {
					MemberMobile mm = mlist.get(0);
					if (mm != null) {
						if (mm.getMemberCode() != null) {
							if (!memberCode.equals(mm.getMemberCode())) {
								return error("该手机号已经被使用");
							}
						}
					}
				}
			}
			List<MemberMobile> b = memberMobileService.checkMobile(mobile);
			// if(b.size()<=0){
			// return error("找不到该用户");
			// }
			// 随机生成6位数验证码
			String code = String
					.valueOf(Math.round(Math.random() * 899999 + 100000));// 随机生成6位随机数
			String[] a = { mobile };
			String msg = "您正在进行绑定手机号操作，此次操作的验证码为( " + code
					+ " )，请注意查收。该验证码3分钟之内录入有效";
			//System.out.println(code);
			send s = new send(a, msg, "byMobile");
			Thread thread = new Thread(s);
			thread.start();

			List<MobileCode> l = mobileCodeService.findValicode(mobile);
			if (l.size() > 0) {
				MobileCode mc = l.get(0);
				mc.setValidateCode(code);
				mc.setCreateTime(new Timestamp(new Date().getTime()));
				mobileCodeService.update(mc);
			} else {
				MobileCode mc = new MobileCode();
				mc.setCreateTime(new Timestamp(new Date().getTime()));
				mc.setMobile(mobile);
				mc.setValidateCode(code);
				mobileCodeService.save(mc);
			}
			return success("验证信息发送成功");
		}
		// 通过邮箱找回密码
		if ("byEmail".equals(findType)) {
			if (StringUtils.isBlank(email)) {
				return error("邮箱不能为空");
			}
			if (!email(email)) {
				return error("邮箱格式不正确");
			}
			if (StringUtils.isNotBlank(email)) {
				List<MemberEmail> elist = memberService.findEmailByPro("email",
						email);
				if (elist.size() > 0) {
					MemberEmail me = elist.get(0);
					if (me != null) {
						if (!memberCode.equals(me.getMemberCode())) {
							return error("该邮箱已经被使用");
						}
					}
				}
			}

			StringBuffer content = new StringBuffer("");

			String code = String
					.valueOf(Math.round(Math.random() * 899999 + 100000));// 随机生成6位随机数
			content.append("您好，尊敬的用户。 你的账号<b><font color=\"#ff0000\">")
					.append(email).append("</font></b>，正在绑定新邮箱。此次验证码为")
					.append(code)
					.append("<div>&nbsp; &nbsp;请注意保管并在20分钟内进行相关操作！</div>");
			send s = new send(content.toString(), "byEmail", email);
			Thread th = new Thread(s);
			th.start();

			if (StringUtils.isNotBlank(user.getCode())) {
				List<MemberEmail> list = memberService.findEmailByPro(
						"memberCode", user.getCode());
				if (list.size() > 0) {
					MemberEmail me = list.get(0);
					me.setValidateCode(code);
					me.setEmail(email);
					me.setIsBind(0);
					me.setBindTime(new Timestamp(new Date().getTime()));
					memberEmailService.update(me);
				} else {
					MemberEmail me = new MemberEmail();
					me.setAvaliable(1);
					me.setCode(Uuid.uuid());
					me.setEmail(email);
					me.setIsBind(0);
					me.setMemberCode(user.getCode());
					me.setValidateCode(code);
					me.setBindTime(new Timestamp(new Date().getTime()));
					memberEmailService.save(me);
				}
			} else {
				MemberEmail me = new MemberEmail();
				me.setAvaliable(1);
				me.setCode(Uuid.uuid());
				me.setEmail(email);
				me.setIsBind(0);
				me.setMemberCode(user.getCode());
				me.setValidateCode(code);
				me.setBindTime(new Timestamp(new Date().getTime()));
				memberEmailService.save(me);
			}
			return success("邮件发送成功");
		}

		return error("请选择找回方式");
	}

	/**
	 * 验证验证码是否正确
	 * 
	 * @param mobile
	 * @param email
	 * @param code
	 * @param type
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "checkCode", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String checkCode(HttpServletRequest req, String mobile,
			String email, String code, String type) throws JSONException {
		LogUser user = (LogUser) req.getSession().getAttribute("logUser");
		if (user == null) {
			return error("请先登录");
		}
		if (StringUtils.isBlank(code)) {
			return error("验证码不能为空");
		}
		if ("byMobile".equals(type)) {
			if (StringUtils.isBlank(mobile)) {
				return error("手机号不能为空");
			}
			List<MobileCode> list = mobileCodeService.findValicode(mobile);
			if (list.size() > 0) {
				MobileCode mc = list.get(0);
				if (mc != null) {
					if (mc.getCreateTime() != null) {
						long time = mc.getCreateTime().getTime() + 180000;
						if (System.currentTimeMillis() > time) {
							return error("验证超时");
						}
					}

					if (mc.getValidateCode() != null) {
						if (code.equals(mc.getValidateCode())) {
							return success("验证通过");
						} else {
							return error("验证码错误");
						}
					}
				}
			}
		}
		if ("byEmail".equals(type)) {
			if (StringUtils.isBlank(email)) {
				return error("邮箱不能为空");
			}
			String Email = "";
			if (StringUtils.isNotBlank(user.getEmail())) {
				Email = user.getEmail();
			} else {
				Email = email;
			}
			List<MemberEmail> me = memberEmailService.checkEmail(Email);
			if (me.size() > 0) {
				MemberEmail m = me.get(0);
				if (m != null) {
					if (m.getBindTime() != null) {
						long time = m.getBindTime().getTime() + 1200000;
						if (System.currentTimeMillis() > time) {
							return error("验证超时");
						}
					}
					if (m.getValidateCode() != null) {
						if (code.equals(m.getValidateCode())) {
							return success("验证通过");
						} else {
							return error("验证码错误");
						}
					}
				}
			}
		}
		return error("数据异常");
	}

	/**
	 * 验证邮箱格式
	 */
	public static boolean email(String email) {
		return (email == null || "".equals(email)) ? false : email
				.matches("^([\\w_\\.-]+)@([\\w\\.-]+)\\.([a-zA-Z\\.]{2,6})$");
	}

	/**
	 * 上传头像
	 * 
	 * @param req
	 * @param file
	 * @return
	 * @throws JSONException
	 * @throws JsonParseException
	 */
	@RequestMapping(value = "uploadPic", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String uploadPic(HttpServletRequest req, MultipartFile file)
			throws JSONException, JsonParseException {
		JSONObject obj = new JSONObject();
		try {
			LogUser user = (LogUser) req.getSession().getAttribute("logUser");
			if (user == null) {
				obj.put("status", -3);
				return obj.toString();
			}
			if (file == null) {
				obj.put("status", -1);
				return obj.toString();
			}
			if (!storeFile(req, file)) {
				obj.put("status", 0);
				return obj.toString();
			}
			String pic = (String) req.getSession()
					.getAttribute("uploadPicPath");
			obj.put("status", 1);
			obj.put("url", pic);
			if (user.getCode() != null) {
				MemberInfo mi = memberService.findMemberInfo(user.getCode());
				if (mi != null) {
					mi.setPic(pic);
					memberService.updateMemberInfo(mi);
					user.setPic(pic);
				}
			}
			req.getSession().removeAttribute("uploadPicPath");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return JsonUtil.toJson(obj);
	}

	/**
	 * 裁剪图片
	 * 
	 * @param url
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	@RequestMapping(value = "cutImg", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String cutImg(HttpServletRequest req, String url, String turl,
			int x, int y, int w, int h) throws JSONException, IOException {
		try {

			String path = req.getSession().getServletContext().getRealPath("/");
			//System.out.println(path);
			//刘洪兵新增
			if(x<=0){
				x=0;
			}
			if(y<=0){
				y=0;
			}
			ImageHelper.cutImage(path + url, path + turl, x, y, w, h);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			return error("图片分辨率应该大于等于120*120");
		}
		return success("裁剪成功");
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
	private static final String ACCESSFILETYPE = ".*(jpeg|jpg|png|gif|bmp)$";

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
	 * 返回错误信息
	 * 
	 * @param msg
	 * @return
	 * @throws JSONException
	 */
	private static String error(String msg) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("status", 101);
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

	/**
	 * 多线程发送信息
	 * 
	 * @author abcd
	 * 
	 */
	class send implements Runnable {
		private String[] mobile;
		private String msg;
		private String findType;
		private String email;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				if ("byMobile".equals(findType)) {
					SendSMS.send(msg, mobile);

				}
				if ("byEmail".equals(findType)) {
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
