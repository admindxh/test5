package com.rimi.ctibet.portal.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.sortmsg.SendSMS;
import com.rimi.ctibet.common.util.CheckMobile;
import com.rimi.ctibet.common.util.HtmlRegexpUtil;
import com.rimi.ctibet.common.util.LoginMD5;
import com.rimi.ctibet.common.util.Security;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.XssFilter;
import com.rimi.ctibet.common.util.sendemail.SendEmail;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.LoginLog;
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.domain.MemberEmail;
import com.rimi.ctibet.domain.MemberMobile;
import com.rimi.ctibet.domain.MobileCode;
import com.rimi.ctibet.web.service.ILoginLogService;
import com.rimi.ctibet.web.service.IMemberEmailService;
import com.rimi.ctibet.web.service.IMemberMobileService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IMobileCodeService;

@Controller
@RequestMapping("/portal/clientLog/")
public class LoginController extends BaseController {
	@Resource
	private IMemberService memberService;
	@Resource
	private IMemberMobileService memberMobileService;
	@Resource
	private IMemberEmailService memberEmailService;
	@Resource
	private IMobileCodeService mobileCodeService;
	@Resource
	private ILoginLogService loginLogService;

	@RequestMapping("loginPage")
	public String loginPage(HttpServletRequest req) {
		req.getSession().removeAttribute("logUser");
		req.setAttribute("logflag", req.getParameter("logFlag"));

		String userAgent = req.getHeader("user-agent");
		boolean isMobile = CheckMobile.check(userAgent);
		if (isMobile) {
			String back_url = req.getParameter("back_url");
			req.setAttribute("back_url", back_url);
			return "portal/app/login/mobile-login";
		}
		return "portal/app/login";
	}

	/**
	 * 跳转到找回密码选择修改方式页
	 * 
	 * @return
	 */
	@RequestMapping("changeInfo")
	public String changeInfo() {
		return "portal/app/find_password";
	}

	/**
	 * 跳转到邮件发送成功页（邮箱）
	 * 
	 * @param req
	 * @param email
	 * @return
	 */
	@RequestMapping("turnToMail")
	public String turnToMail(HttpServletRequest req, String email) {
		email = XssFilter.protectAgainstXSS(email);
		req.setAttribute("email", email);
		String url = "";
		if (!StringUtils.isBlank(email) && email(email)) {
			if (email.endsWith("@qq.com")) {
				url = "http://mail.qq.com";
			} else if (email.endsWith("@163.com")) {
				url = "http://mail.163.com";
			} else if (email.endsWith("@sina.com")
					|| email.endsWith("@sina.cn")) {
				url = "https://mail.sina.com.cn";
			} else if (email.endsWith("@sohu.com")) {
				url = "http://login.mail.sohu.com";
			} else if (email.endsWith("@rimionline.com")) {
				url = "http://tel.exmail.qq.com/login";
			}
		}
		req.setAttribute("url", url);
		return "portal/app/find/mail_find_next";
	}

	/**
	 * 前台用户登录
	 * 
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
	@RequestMapping(value = "login", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	public String login(String tlogUser, String logPass, String verifyCode,
			HttpServletRequest req, HttpServletResponse res, HttpSession session)
			throws JSONException, UnsupportedEncodingException, IOException {

		String returnUrl = "portal/app/login";
		String mobile = req.getParameter("mobile");
		boolean isMobile = StringUtil.isNotNull(mobile) && mobile.equals("yes");
		if (isMobile) {
			returnUrl = "portal/app/login/mobile-login";
		}
		if (StringUtils.isBlank(tlogUser)) {
			req.setAttribute("error", -1);
			req.setAttribute("tlogUser", tlogUser);
			return returnUrl;// error("用户名不能为空");
		}
		if (StringUtils.isBlank(logPass)) {
			req.setAttribute("error", -2);
			req.setAttribute("tlogUser", tlogUser);
			return returnUrl;// return error("密码不能为空");
		}
		// if(StringUtils.isBlank(verifyCode)){
		// req.setAttribute("error", -3);
		// req.setAttribute("tlogUser", tlogUser);
		// return returnUrl;//return error("验证码不能为空");
		// }
		// if(!checkVrify(verifyCode, session)){
		// req.setAttribute("error", -4);
		// req.setAttribute("tlogUser", tlogUser);
		// return returnUrl;//return error("验证码错误");
		// }
		// String password = new String(new
		// BASE64Decoder().decodeBuffer(logPass),"utf-8");
		logPass = LoginMD5.compile(logPass);
		List<LogUser> list = memberService.login(tlogUser, logPass);
		if (list.size() <= 0) {
			req.setAttribute("error", -5);
			req.setAttribute("tlogUser", tlogUser);
			return returnUrl;// return error("用户名或密码错误");
		}
		LogUser loguser = list.get(0);
		if (loguser != null) {
			if (loguser.getEmail() == null && loguser.getMobile() == null) {
				// if("0".equals(loguser.getIsBind())){
				req.setAttribute("error", -6);
				req.setAttribute("tlogUser", tlogUser);
				return returnUrl;// return error("未找到该用户");
				// }
			}
			if (loguser.getStatus() == 0) {
				req.setAttribute("error", -11);
				req.setAttribute("tlogUser", tlogUser);
				return returnUrl;// return error("未找到该用户");
				// return error("账户已被停封，请与管理员联系");
			}
			if (loguser.getIsBind() != null && loguser.getEmail() != null
					&& loguser.getMobile() == null) {
				if (loguser.getIsBind() == 0) {
					req.setAttribute("error", -7);
					req.setAttribute("tlogUser", tlogUser);
					return returnUrl;// return error("请先到邮箱激活账户");
				}
			}
			if (loguser.getIsVerified() != null && loguser.getEmail() == null
					&& loguser.getMobile() != null) {
				if (loguser.getIsVerified() == 0) {
					req.setAttribute("error", -8);
					req.setAttribute("tlogUser", tlogUser);
					return returnUrl;// return error("该账户尚未激活");
				}
			}
			if (loguser.getIsVerified() != null && loguser.getEmail() != null
					&& loguser.getMobile() != null
					&& loguser.getIsBind() != null) {
				if (loguser.getIsVerified() == 0 && !tlogUser.contains("@")) {
					req.setAttribute("error", -8);
					req.setAttribute("tlogUser", tlogUser);
					return returnUrl;// return error("该账户尚未激活");
				}
				if (loguser.getIsBind() == 0 && tlogUser.contains("@")) {
					req.setAttribute("error", -7);
					req.setAttribute("tlogUser", tlogUser);
					return returnUrl;// return error("请先到邮箱激活账户");
				}
			}
		}

		// 用户勾选了记住我
		String rem = req.getParameter("remember");
		if ("1".equals(rem)) {
			Security security = Security.getInstance();
			Long time = System.currentTimeMillis() + 2592000000L;// 一个月后过期
			StringBuffer tokenStr = new StringBuffer("");
			Cookie token = new Cookie("token", security.encode(tokenStr.append(
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
		// 向登录日志加入数据
		LoginLog ll = new LoginLog();
		ll.setLogDate(new Date());
		ll.setMemberCode(loguser.getCode());
		loginLogService.save(ll);
		req.getSession().setAttribute("logUser", loguser);
		if (isMobile) {
			String back_url = req.getParameter("back_url");
			req.setAttribute("back_url", back_url);
			req.setAttribute("login_state", "success");
			return returnUrl;
		}
		return "redirect:/";
	}

	/**
	 * 前台用户登录
	 * 
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
	@RequestMapping(value = "loginAjax", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String loginAjax(String tlogUser, String logPass,
			HttpServletRequest req, HttpServletResponse res, HttpSession session)
			throws JSONException, UnsupportedEncodingException, IOException {
		if (StringUtils.isBlank(tlogUser)) {
			return error("用户名不能为空");
		}
		if (StringUtils.isBlank(logPass)) {
			return error("密码不能为空");
		}

		// String password = new String(new
		// BASE64Decoder().decodeBuffer(logPass),"utf-8");
		logPass = LoginMD5.compile(logPass);
		List<LogUser> list = memberService.login(tlogUser, logPass);
		if (list.size() <= 0) {
			return error("用户名或密码错误");
		}
		LogUser loguser = list.get(0);
		if (loguser != null) {
			if (!logPass.equals(loguser.getPassword())
					&& StringUtils.isNotBlank(loguser.getPassword())) {
				return error("密码错误！");
			}
			if (StringUtils.isBlank(loguser.getPassword())) {
				return error("为保证数据安全请重置密码！");
			}

			if (loguser.getEmail() == null && loguser.getMobile() == null) {
				// if("0".equals(loguser.getIsBind())){

				return error("未找到该用户");
				// }
			}
			// System.out.println(loguser.getStatus());
			if (loguser.getStatus() == 0) {
				return error("账户已被停封，请与管理员联系");
			}
			if (loguser.getIsBind() != null && loguser.getEmail() != null
					&& loguser.getMobile() == null) {
				if (loguser.getIsBind() == 0) {

					return error("请先到邮箱激活账户");
				}
			}
			if (loguser.getIsVerified() != null && loguser.getEmail() == null
					&& loguser.getMobile() != null) {
				if (loguser.getIsVerified() == 0) {

					return error("该账户尚未激活");
				}
			}

			if (loguser.getIsVerified() != null && loguser.getEmail() != null
					&& loguser.getMobile() != null
					&& loguser.getIsBind() != null) {
				if (loguser.getIsVerified() == 0 && !tlogUser.contains("@")) {
					return error("该账户尚未激活");
				}
				if (loguser.getIsBind() == 0 && tlogUser.contains("@")) {
					return error("请先到邮箱激活账户");
				}
			}
		}
		// 用户勾选了记住我
		String rem = req.getParameter("remember");
		if ("1".equals(rem)) {
			Security security = Security.getInstance();
			Long time = System.currentTimeMillis() + 2592000000L;// 一个月后过期
			StringBuffer tokenStr = new StringBuffer("");
			Cookie token = new Cookie("token", security.encode(tokenStr.append(
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
		// 向登录日志加入数据
		LoginLog ll = new LoginLog();
		ll.setLogDate(new Date());
		ll.setMemberCode(loguser.getCode());
		loginLogService.save(ll);
		req.getSession().setAttribute("logUser", loguser);
		return success("登录成功");
	}

	/**
	 * 退出
	 * 
	 * @param session
	 * @param res
	 * @throws JSONException
	 */
	@RequestMapping(value = "logout", produces = "text/html;charset=UTF-8")
	public String logout(HttpSession session, HttpServletResponse res)
			throws JSONException {
		try {
			LogUser loguser = (LogUser) session.getAttribute("logUser");
			if (loguser != null) {
				session.removeAttribute("logUser");
			}
			Cookie token = new Cookie("token", null);
			token.setMaxAge(0);
			token.setPath("/");
			res.addCookie(token);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			return error("System Error~");
		}

		return "redirect:/";
	}

	/**
	 * 退出
	 * 
	 * @param session
	 * @param res
	 * @throws JSONException
	 */
	@RequestMapping(value = "logoutAjax", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String logoutAjax(HttpSession session, HttpServletResponse res)
			throws JSONException {
		try {
			LogUser loguser = (LogUser) session.getAttribute("logUser");
			if (loguser != null) {
				session.removeAttribute("logUser");
			}
			Cookie token = new Cookie("token", null);
			token.setMaxAge(0);
			token.setPath("/");
			res.addCookie(token);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			return error("System Error~");
		}
		return success("退出成功");
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
		// 如果通过手机号找回密码
		if ("byMobile".equals(findType)) {
			if (StringUtils.isBlank(mobile)) {
				return error("手机号不能为空");
			}
			List<MemberMobile> b = memberMobileService.checkMobile(mobile);
			if (b.size() <= 0) {
				return error("找不到该用户");
			}
			// 随机生成6位数验证码
			String code = String.valueOf(Math
					.round(Math.random() * 899999 + 100000));// 随机生成6位随机数
			String[] a = { mobile };
			String msg = "您申请了找回密码，此次操作的验证码为( " + code
					+ " )，请注意查收。该验证码3分钟之内录入有效";
			// System.out.println(code);
			send s = new send(a, msg, "byMobile");
			Thread thread = new Thread(s);
			thread.start();
			// MobileCode mobileCode=new MobileCode();
			List<MobileCode> list = mobileCodeService.findValicode(a[0]);
			if (list.size() > 0) {
				MobileCode mobileCode = list.get(0);
				mobileCode.setMobile(a[0]);
				mobileCode.setValidateCode(code);
				mobileCode.setCreateTime(new Timestamp(new Date().getTime()));
				try {
					mobileCodeService.update(mobileCode);
				} catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
					return error("数据异常");
				}
			}
			req.setAttribute("hiddenCode", "mobile");
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
			List<MemberEmail> list = memberEmailService.checkEmail(email);
			if (list.size() <= 0) {
				return error("找不到该用户");
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
			return success("邮件发送成功");
		}

		return error("请选择找回方式");
	}

	/**
	 * 获取路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}

	@RequestMapping("checkEmail")
	public String checkEmail(HttpServletRequest request, String email, String id) {

		request.setAttribute("email", deEmail(email));
		request.setAttribute("id", id);

		String url = "";
		if (!StringUtils.isBlank(email) && email(email)) {
			if (email.endsWith("@qq.com")) {
				url = "http://mail.qq.com";
			} else if (email.endsWith("@163.com")) {
				url = "http://mail.163.com";
			} else if (email.endsWith("@sina.com")
					|| email.endsWith("@sina.cn")) {
				url = "https://mail.sina.com.cn";
			} else if (email.endsWith("@sohu.com")) {
				url = "http://login.mail.sohu.com";
			}
		}
		request.setAttribute("url", url);
		return "";
	}

	/**
	 * 验证邮箱格式
	 */
	public static boolean email(String email) {
		return (email == null || "".equals(email)) ? false : email
				.matches("^([\\w_\\.-]+)@([\\w\\.-]+)\\.([a-zA-Z\\.]{2,6})$");
	}

	/**
	 * *号处理邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static String deEmail(String email) {
		String s = email;
		if (s == null || "".equals(s))
			return s;
		if (s.indexOf("@") == -1)
			return "";
		String q = s.substring(0, s.indexOf("@"));
		String h = s.substring(s.indexOf("@"));
		int length = q.length();
		if (length <= 2) {
			q = "**";
		} else if (length < 4 || length > 2) {
			q = q.substring(0, 1) + "**"
					+ q.substring(q.length() - 1, q.length());
		} else {
			q = q.substring(0, 3) + "**"
					+ q.substring(q.length() - 1, q.length());
		}
		return q + h;
	}

	/**
	 * 
	 * 跳转到修改密码页
	 * 
	 * @param req
	 * @param code
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping("changePage")
	public String changePage(HttpServletRequest req, String code,
			String findType, String mobile) throws JSONException {

		if (StringUtils.isNotBlank(code)) {
			code = XssFilter.protectAgainstXSS(code);
			code = HtmlRegexpUtil.filterHtml(code);

		}
		if (StringUtils.isNotBlank(findType)) {
			findType = XssFilter.protectAgainstXSS(findType);
			findType = HtmlRegexpUtil.filterHtml(findType);
		}
		if (StringUtils.isNotBlank(mobile)) {

			mobile = XssFilter.protectAgainstXSS(mobile);
			mobile = HtmlRegexpUtil.filterHtml(mobile);
		}
		if ("byEmail".equals(findType)) {
			if (StringUtils.isBlank(code)) {
				// 参数code为空返回首页
				return "redirect:/";
			}
			String decode = Security.getInstance().decode(code);
			String timeStr = decode.split(",")[0];
			long time = Long.parseLong(timeStr);
			if (System.currentTimeMillis() > time) {
				req.setAttribute("error", -6);
				return "portal/app/find_password";// return error("邮箱验证超时");
			}
			req.setAttribute("code", code);
			return "portal/app/find/new_password_mail";
		}
		if ("byMobile".equals(findType)) {
			if (StringUtils.isBlank(mobile)) {
				req.setAttribute("error", -1);
				req.setAttribute("code", code);
				return "portal/app/find_password";// return error("手机号不能为空");
			}
			if (StringUtils.isBlank(code)) {
				req.setAttribute("error", -1);
				req.setAttribute("mobile", mobile);
				return "portal/app/find_password";// return error("验证码不能为空");
			}
			List<MobileCode> list = mobileCodeService.findValicode(mobile);
			if (list.size() <= 0) {
				req.setAttribute("error", -2);
				req.setAttribute("mobile", mobile);
				req.setAttribute("code", code);
				return "portal/app/find_password";// return
													// error("未找到该手机号的验证信息");
			}
			MobileCode mc = list.get(0);
			if (mc.getCreateTime() != null) {
				if (System.currentTimeMillis() > mc.getCreateTime().getTime() + 180000) {
					req.setAttribute("error", -3);
					req.setAttribute("mobile", mobile);
					req.setAttribute("code", code);
					return "portal/app/find_password";// error("时间超过三分钟，验证已经失效");
				}
			}
			if (mc != null && mc.getValidateCode() != null) {
				if (!code.equals(mc.getValidateCode())) {
					req.setAttribute("error", -4);
					req.setAttribute("mobile", mobile);

					return "portal/app/find_password";// return error("验证码错误");
				}
			}
			// 如果验证通过则通过手机号查询用户信息
			List<MemberMobile> b = memberMobileService.checkMobile(mobile);
			if (b.size() > 0) {
				MemberMobile mm = b.get(0);
				String c = Security.getInstance().encode(
						(System.currentTimeMillis() + 180000)
								+ ","
								+ LoginMD5.compile(mm.getMobile()
										+ mm.getMemberCode()
										+ mm.getIsVerified()) + ","
								+ mm.getMemberCode());
				req.setAttribute("code", c);
			}
			return "portal/app/find/new_password_phone";
		}
		return "redirect:/";
	}

	/**
	 * 修改密码
	 * 
	 * @param code
	 * @param password
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "changPassword", produces = "text/html;charset=utf-8")
	public String changPassword(HttpServletRequest req, String code,
			String password) throws JSONException {
		String findType = req.getParameter("findType");
		if (StringUtils.isBlank(password)) {
			if ("byEmail".equals(findType)) {
				req.setAttribute("code", code);
				req.setAttribute("findType", findType);
				req.setAttribute("error", -5);
				return "portal/app/find/new_password_mail";
			}
			if ("byMobile".equals(findType)) {
				req.setAttribute("code", code);
				req.setAttribute("findType", findType);
				req.setAttribute("error", -5);
				return "portal/app/find/new_password_phone";
			}
		}
		if (StringUtils.isBlank(code)) {
			// 返回系统首页
			return "redirect:/";
		}
		String decode = Security.getInstance().decode(code);
		String memberCode = decode.split(",")[2];
		String timeStr = decode.split(",")[0];
		if (StringUtils.isNotBlank(timeStr)) {
			long time = Long.parseLong(timeStr);
			if (System.currentTimeMillis() < time) {
				Member m = memberService.findByCode(memberCode);
				if (m == null) {
					if ("byEmail".equals(findType)) {
						req.setAttribute("code", code);
						req.setAttribute("findType", findType);
						req.setAttribute("error", -1);
						return "portal/app/find/new_password_mail";
					}
					if ("byMobile".equals(findType)) {
						req.setAttribute("code", code);
						req.setAttribute("findType", findType);
						req.setAttribute("error", -1);
						return "portal/app/find/new_password_phone";
					}
					// return error("没有找到该用户");
				}
				m.setPassword(LoginMD5.compile(password));
				try {
					memberService.updateMember(m);
				} catch (Exception e) {
					e.printStackTrace();// TODO: handle exception
					if ("byEmail".equals(findType)) {
						req.setAttribute("code", code);
						req.setAttribute("findType", findType);
						req.setAttribute("error", -3);
						return "portal/app/find/new_password_mail";
					}
					if ("byMobile".equals(findType)) {
						req.setAttribute("code", code);
						req.setAttribute("findType", findType);
						req.setAttribute("error", -3);
						return "portal/app/find/new_password_phone";
					}
					// return error("修改密码失败");
				}
			} else {
				if ("byEmail".equals(findType)) {
					req.setAttribute("code", code);
					req.setAttribute("findType", findType);
					req.setAttribute("error", -2);
					return "portal/app/find/new_password_mail";
				}
				if ("byMobile".equals(findType)) {
					req.setAttribute("code", code);
					req.setAttribute("findType", findType);
					req.setAttribute("error", -2);
					return "portal/app/find/new_password_phone";
				}
				// return error("邮箱验证超时");
			}
		}

		return "portal/app/find/find_success";
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

	/**
	 * 判断验证码是否正确
	 * 
	 * @param verifyCode
	 * @param session
	 * @return
	 */
	public boolean checkVrify(String verifyCode, HttpSession session) {
		if (StringUtils.isBlank(verifyCode)
				|| !verifyCode.equalsIgnoreCase((String) session
						.getAttribute("validateCode"))) {
			session.removeAttribute("validateCode");
			return false;
		}
		return true;
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
	 * 检查登录
	 */
	@RequestMapping(value = "checkLogin", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String checkLogin() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", getFrontUser());
		if (getFrontUser() != null) {
			map.put("msg", "success");
		} else {
			map.put("msg", "needlogin");
		}
		return obj2json(map);
	}

	public IMemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}
}
