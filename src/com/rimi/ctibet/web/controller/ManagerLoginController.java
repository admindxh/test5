package com.rimi.ctibet.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.VerifyCodeUtil;
import com.rimi.ctibet.domain.UserInfo;
import com.rimi.ctibet.web.controller.vo.UserVO;
import com.rimi.ctibet.web.service.IUserInfoService;

@Controller(value = "managerLogin")
@RequestMapping("/manage/html/")
public class ManagerLoginController extends BaseController {

	public static final String SESSION_USER = "session_user";
	private static final String baseUrl = "/manage/html/";
	public  static Logger LOG = Logger.getLogger(ManagerLoginController.class);
	
	@Resource
	private IUserInfoService userInfoService;
	// 用户登录检查
	// 跳转到相应页面
	@RequestMapping("login")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response, UserInfo userInfo) throws Exception {
		ModelAndView view = new ModelAndView();
		// 验证来源,可能存在bug，登陆不了 也不显示任何提示信息！
		if (isFirstRefer(request)) {
			return view;
		}
		if(StringUtils.isBlank(userInfo.getName())){
			view.addObject("notice","");
			return view;
		}
		if(StringUtils.isBlank(userInfo.getPwd())){
			view.addObject("notice", "密码不能为空");
			return view;
		}
//		if(StringUtils.isBlank(userInfo.getCode())){
//			view.addObject("notice", "验证码不能为空");
//			return view;
//		}
		
		// 验证随机码
		if (checkRandomCode(userInfo.getCode())) {
			if (StringUtils.isNotBlank(userInfo.getName())
					&& StringUtils.isNotBlank(userInfo.getPwd())) {// 验证空
				userInfo = userInfoService.checkLogin(
						userInfo.getName().trim(), userInfo.getPwd().trim());
				if (userInfo != null) {// 验证通过加装权限，重定向到登陆到主菜单
					HttpSession session = request.getSession();
					session.invalidate();
					session = request.getSession(true);
					UserVO user = userInfoService.getUserVo(userInfo.getCode());
					session.setAttribute(SESSION_USER, user);
					view.setViewName(redirect(baseUrl + "main.html"));
				} else {
					view.addObject("notice", "账号或密码错误请检查！");
				}
			} else {// 验证空失败 包错误
				view.addObject("notice", "验证码错误请检查！");
			}
		} else {// 验证随机码失败！默认返回
			view.addObject("notice", "验证码错误请检查！");
		}
		return view;
	}

	private boolean isFirstRefer(HttpServletRequest req) {
		String referer = req.getHeader("referer");
		if (StringUtils.isBlank(referer)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkRandomCode(String code) {
		String randomCoe = (String) this.session.getAttribute("randomcode");
		if(StringUtils.isBlank(code)||StringUtils.isBlank(randomCoe))
		{
			//return false;
			return true;
		}
		if (randomCoe.toLowerCase().trim().equals(code.toLowerCase().trim())) {
			return true;
		} else {
			return false;
		}
	}

	// 管理员退出
	@RequestMapping("logoff")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			session.invalidate();
		} catch (Exception e) {
		}
		ModelAndView view = new ModelAndView();
		view.setViewName(forward(baseUrl + "login.html"));
		return view;
	}

	@RequestMapping(value = "verify")
	public void verify(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("image/jpeg");
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession();
		VerifyCodeUtil vCode = new VerifyCodeUtil();
		session.setAttribute("randomcode", vCode.getRandomCode());
		try {
			ImageIO.write(vCode.getRandomImage(), "png",
					response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
