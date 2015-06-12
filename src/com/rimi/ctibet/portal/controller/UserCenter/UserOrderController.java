package com.rimi.ctibet.portal.controller.UserCenter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.CommonOrder;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.web.service.ICommonOrderService;

/**
 * 个人中心 / 我的订单
 *
 * @author tangHQ
 * @date 2015-4-10
 */
@Controller
@RequestMapping({"/portal/user-order/", "/member/user-order"})
public class UserOrderController {
    
	/** 订单业务接口. */
    @Resource ICommonOrderService commonOrderService;

    /** Session中的用户令牌. */
    public static final String TOKEN_LOGIN_USER = "logUser";
    
    /**
     * 初始化Controller时调用
     * 
     * @param request
     * @param modelMap
     */
	@ModelAttribute
	public void initParameters (HttpServletRequest request, ModelMap modelMap){
		modelMap.put("programNam", "60");
	}
	
	/**
	 * 获取用户订单列表
	 * 
	 * @param req
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public String orderList (HttpServletRequest req, Pager pager) {
		/*
		 *  判断用户是否登陆，只有当用户登陆成功才会执行回调函数
		 */
		return checkUser(req, pager, new LoginCallback() {
			@Override
			public String aleralyLogin(HttpServletRequest req, Pager pager) {
				
				// 会员编号
				String memberCode = req.getAttribute("memberCode").toString();
				
				// 支付状态
				String payState = req.getParameter("payState");
				
				// 查询条件
				CommonOrder condition = new CommonOrder();
							condition.setMemberCode(memberCode);
							condition.setPayState(StringUtils.isNotEmpty(payState) ? Integer.valueOf(payState) : CommonOrder.PAY_STATE_ALL);
				
				// 查询数据
				commonOrderService.queryCommonOrderByPager(pager, condition, null);
				
				// 返回JSON
				return new Gson().toJson(pager);
			}
		});
	}
	
	/**
	 * 验证用户是否登陆
	 * 
	 * @param request
	 * @param checkLogin
	 * @return
	 */
	private String checkUser (HttpServletRequest request, Pager pager, LoginCallback checkLogin) {
		LogUser user = (LogUser) request.getSession().getAttribute(TOKEN_LOGIN_USER);
		if (user == null) {
			return "redirect:/";
		} else {
			request.setAttribute("memberCode", user.getMemberCode() == null ? "" : user.getMemberCode());
			return pager == null ? checkLogin.aleralyLogin(request) : checkLogin.aleralyLogin(request, pager);
		}
	}
	
	/**
	 * 验证用户是否登陆抽象类.
	 *
	 * @author tangHQ
	 * @date 2015-4-10
	 */
	abstract class LoginCallback {
		
		/**
		 * 普通回调.
		 * 
		 * @param req HttpServletRequest
		 * @return JSON字符串
		 */
		String aleralyLogin (HttpServletRequest req) { return null; }
		
		/**
		 * 分页回调.
		 * 
		 * @param req HttpServletRequest
		 * @param pager 分页对象
		 * @return JSON字符串
		 */
		String aleralyLogin (HttpServletRequest req, Pager pager) { return null; }
	}
}
