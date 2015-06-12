package com.rimi.ctibet.web.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.util.annotation.Token;
import com.rimi.ctibet.domain.Access;
import com.rimi.ctibet.web.controller.UserInfoController;
import com.rimi.ctibet.web.controller.vo.FireAuthority;
import com.rimi.ctibet.web.controller.vo.UserVO;

public class UserInterceptor implements HandlerInterceptor {
	public static final  Logger LOG = Logger.getLogger(UserInfoController.class);
	private static final String[] IGNORE_URI = { 
		    "web/rideLine/fileUpload",
			"upload",
			"uploadPic",
			"/manage/html/index",
			"/manage/html/login", "/manage/html/checkLogin",
			"/manage/html/login.html",
			"/tourism/strage/frontIndex",
			"/tourism/strage/frontIndex",
			"/web/frontIndexController/frontIndex",
			"/web/replyManageController/isOpen", 
			"manage/html/verify",
			"/web/replyManageController/isOpen", 
			"web/fronttTourismController",
			"tourism/strage",
			"community/frontIndex", 
			"web/frontTsSqController/frontIndex",
			"web/frontTsSqController",
			"community",
			"web/readTibetSgPassMgController/getViewByDescode",
			"web/praiseController", "web/member/checkIslogin",
			"web/adArea/getAdverByPro",
			"web/destinationController/getDestinationDetail",
			"tourism/des/detail",
			"web/destinationController/goneandwant",
			"web/imageController/uploadImage", "web/imageController/getUrl",
			"web/surguestion/save", "web/praiseController/updateViewCount",
			"web/frontContentStatisController/staticsSystemLog",
			"web/activityBannerManageController/fileUpload",
			"web/activityController/fileUpload",
			"web/specialController/fileUpload",
			"web/viewController/fileUpload", "web/viewController/uploadImages",
			"web/destinationController/uploadImages",
			"web/groupTravel/groupTravelDetail",
			"web/frontIndexController/window",
			"web/imageController/deleteImageBymutiCode",
			"web/cache/clear",
			"web/fronttTourismController/frontIndex",
			"portal/airPlane/OrderAirPlane",
			"manage/html/deletelucene"
	};

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}
	
	 private boolean isRepeatSubmit(HttpServletRequest request) {
	        String serverToken = (String) request.getSession(true).getAttribute("token");
	        if (serverToken == null) {
	            return true;
	        }
	        String clinetToken = request.getParameter("token");
	        if (clinetToken == null) {
	            return true;
	        }
	        //考虑ajax 请求
	        if (!serverToken.equals(clinetToken)) {
	            return true;
	        }
	        return false;
	    }
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		 if (handler instanceof HandlerMethod) {
		        HandlerMethod handlerMethod = (HandlerMethod) handler;
		        Method method = handlerMethod.getMethod();
		        Token annotation = method.getAnnotation(Token.class);
		        if (annotation != null) {
		            boolean needSaveSession = annotation.save();
		            if (needSaveSession) {
		            	request.getSession(true).setAttribute("token", UUID.randomUUID().toString());
		            }
		            boolean needRemoveSession = annotation.remove();
		            if (needRemoveSession) {
		                if (isRepeatSubmit(request)) {
		                    return false;
		                }
		                request.getSession(true).removeAttribute("token");
		            }
		        }
		      
		   } 
		
		
		
		
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";
		String loginPath = basePath + "manage/html/login.html";
		String url = request.getRequestURL().toString();
		LOG.info(url);
		// 不拦截的url
		if (url.contains("portal/headerFooterController/footer")) {
			//System.out.println("-----------");
		}
		for (String s : IGNORE_URI) {
			if (url.contains(s)) {
				return true;
			}
		}
		UserVO user = null;
		try {
			HttpSession session = request.getSession();
			user = (UserVO) session
					.getAttribute(UserInfoController.SESSION_USER);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		LOG.info(user);
		if (null != user && null != user.getCode()
				&& !"".equals(user.getCode())) {// 登陆与否校验
			// 登陆了
			// 1.检查非法提交
			if (isFirstRequest(request)) {
				//
				if (!url.contains("manage/html/main")) {
					response.sendRedirect(loginPath);
				}
			}
			// 权限校验
			// doSth(response, handler, user);
			return true;
		} else {
			LOG.error("没有登陆");
			response.sendRedirect(loginPath);
			//return false;
			return false;
		}
	}
	public void removeCookie(HttpServletResponse rep){
		Cookie cookie=new Cookie("token",null);
		cookie.setMaxAge(0);
		rep.addCookie(cookie);
	}
	private boolean doSth(HttpServletResponse response, Object handler,
			UserVO user) throws IOException {
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			FireAuthority fireAuthority = ((HandlerMethod) handler)
					.getMethodAnnotation(FireAuthority.class);
			// 没有声明需要权限,或者声明不验证权限
			if (fireAuthority == null) {
				return true;
			} else {
				int accessid = fireAuthority.value();
				LOG.info("资源id号：" + accessid);
				// 检查权限
				List<Access> accesses = user.getRole().getAccesses();
				// 如果验证成功返回true（这里直接写false来模拟验证失败的处理）
				if (checkAccess(accessid, accesses)) {
					return true;
				} else// 如果验证失败
				{
					// TODO 返回权限不够信息
					// 返回到登录界面
					LOG.error("权限不够");
					response.sendRedirect("index");
					return false;
				}
			}
		} else {
			return false;
		}
	}

	private boolean checkAccess(int id, List<Access> accesses) {
		// TODO
		boolean hasAccess = false;
		for (Access access : accesses) {
			if (id == Integer.parseInt(access.getCode())) {
				hasAccess = true;
				break;
			}
		}
		return hasAccess;
	}

	private boolean isFirstRequest(HttpServletRequest req) {
		String referer = req.getHeader("referer");
		if (StringUtils.isBlank(referer)) {
			return true;
		} else {
			return false;
		}
	}

}
