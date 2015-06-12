/*package com.rimi.web.pay.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.rimi.common.service.BaseManager;
import com.rimi.common.tools.Constants;
import com.rimi.common.util.Common;
import com.rimi.common.util.StringUtil;
import com.rimi.web.order.model.Order;
import com.rimi.web.order.model.OrderedLesson;
import com.rimi.web.order.service.OrderManager;
import com.rimi.web.order.service.OrderedLessonManager;
import com.rimi.web.resource.model.Course;
import com.rimi.web.resource.service.CourseManager;
import com.rimi.web.resource.service.ResourceManager;
import com.rimi.web.rimipay.model.UserLessonOrder;
import com.rimi.web.rimipay.service.UserLessonOrderManager;
import com.rimi.web.rimipay.service.UserMoneyManager;
import com.rimi.web.statistics.model.CourseStatistics;
import com.rimi.web.statistics.service.CourseStatisticsManager;
import com.rimi.web.user.model.ClientUser;
import com.rimi.web.user.service.ClientUserManager;
import com.tenpay.util.TenpayUtil;
*//**
 * 
 * @ClassName: AlipayAction
 * @Description:支付宝支付接口
 * @author 
 * @date 2013-11-21 下午03:15:19
 * 
 *//*

public class AlipayController  {
	
	@Autowired
	private OrderedLessonManager orderedLessonManager;
	@Autowired
	private ResourceManager resourceManager;
	@Autowired
	private CourseStatisticsManager courseStatisticsManager;
	@Autowired
	private ClientUserManager clientUserManager;
	@Autowired
	private CourseManager courseManager;
	@Autowired
	private OrderManager orderManager;
	@Autowired
	private UserMoneyManager userMoneyManager;
	@Autowired
	private UserLessonOrderManager userLessonOrderManager;
	
	private Logger log = LoggerFactory.getLogger(AlipayController.class);
    
	// //////////////////////////////////请求参数//////////////////////////////////////

	// 支付类型
	private static final String payment_type = "1";
	// 必填，不能修改
	// 服务器异步通知页面路径
	//private static final  String notify_url = "http://e.rimiedu.com/alipay/notify.html";
	// 需http://格式的完整路径，不能加?id=123这类自定义参数

	// 页面跳转同步通知页面路径
	//private static final  String return_url = "http://e.rimiedu.com/return_url.jsp";

	// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

	*//**
	 * @throws UnsupportedEncodingException
	 * 
	 * @Title: pay
	 * @Description: 支付的时候请求该URL
	 * @param @return
	 * @return String
	 * @throws
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping("pay")
	public String pay(HttpServletRequest request, HttpServletResponse response,String cid, String broadCastURL, HttpSession session)  {
		String needUseGlod = request.getParameter("defaultJinbiNumber");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(StringUtil.isEmpty(cid)){
			return "redirect:index.html";
		}
		ClientUser clientUser =(ClientUser)session.getAttribute("clientUser");
		if(clientUser==null){
			return "redirect:index.html";
		}
		Course course = courseManager.get(cid);
		if(course==null && StringUtil.isEmpty(broadCastURL)){			
			return "redirect:index.html";
		}
		
		// 商品展示地址
		String courseUrl = Constants.getBasePath(request)+"courseIntro/execute.html?cid="+course.getId();
		if (StringUtil.isNotBlank(broadCastURL)){
			courseUrl = broadCastURL;	//如果是直播付费，完成之后跳往直播页面。
		}
		//String show_url = Constants.getBasePath(request) + "course/"+course.getId()+".html";
		// 需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html
		
		Map map = new HashMap();
		map.put("resourceId", course.getId());
		map.put("userId", clientUser.getId());
		map.put("state", "ok");
		map.put("paystate", "payed");
		
		List<Order> li = orderManager.listByMap(map);
		
		if(li.size()>0){
			return "redirect:"+courseUrl;
		}
		
		//金币流程
		int useGlod = 0;
		if("on".equals(needUseGlod)){
			if(userMoneyManager.buyCourse(course,clientUser.getId())){//直接金币购买成功
				if (StringUtil.isNotBlank(broadCastURL)){
					return "redirect:" + broadCastURL;
				}
				return "redirect:"+ Constants.getBasePath(request)+"courseIntro/execute.html?cid="+course.getId();
			}else{
				useGlod = userMoneyManager.getGlodByUid(clientUser.getId());
			}
		}
		
		// 卖家支付宝帐户
		String seller_email = "e-learning@rimionline.com";
		// 必填

		// 商户订单号
		String out_trade_no = order();
		// 商户网站订单系统中唯一订单号，必填
		
		// 订单名称
		String subject = course.getName().trim();
		// 必填

		// 付款金额
		String total_fee = Double.toString(Constants.sub(course.getPrice(),useGlod));//减去金币
		// 必填

		// 订单描述

		String body =course.getDesce().trim();

		// 防钓鱼时间戳
		String anti_phishing_key = "";
		// 若要使用请调用类文件submit中的query_timestamp函数

		// 客户端的IP地址
		String exter_invoke_ip = "";
		// 非局域网的外网IP地址，如：221.0.0.1

		// ////////////////////////////////////////////////////////////////////////////////

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", Constants.getBasePath(request)+"alipay/notify.html");
//		sParaTemp.put("return_url", Constants.getBasePath(request)+"return_url.jsp");
		sParaTemp.put("return_url", Constants.getBasePath(request)+"alipay/myreturnback.html");
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", courseUrl);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
		//记录订单
		Order order = new Order();
		Date date= new Date();
		order.setId(Common.getUUID());
		order.setCreatTime(date);
		order.setIp(Common.getIp(request));
		order.setOrderId(out_trade_no);
		order.setPrice(course.getPrice());
		order.setResourceId(course.getId());
		order.setPaystate("unpay");
		order.setResourceName(course.getName());
		order.setState("ok");
		order.setUserId(clientUser.getId());
		order.setUsername(clientUser.getNikename());
		order.setStyle("course");
		order.setPayStyle("alipay");
		int i=orderManager.save(order,useGlod);
		
		if(i==1){
			// 建立请求
			request.getSession().removeAttribute("broadCastURL");	
			if(StringUtil.isNotBlank(broadCastURL)){
				request.getSession().setAttribute("broadCastURL", broadCastURL);
			}
			
			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
			log.debug("跳转到支付宝页面:"+sHtmlText);
			if (sHtmlText != null && !"".equals(sHtmlText.trim())) {
				return render(sHtmlText, "text/html;charset=utf-8",response);
			}
		}
		
		return null;
	}
	
	//异步通知
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/notify",produces="text/html;charset=utf-8")
	@ResponseBody
	public String mynotify(HttpServletRequest request,HttpServletResponse response, BaseManager<Order, String> orderManager) throws UnsupportedEncodingException {
		
		log.debug("进入alipay/notify.html");
		
		// 获取支付宝POST过来反馈信息
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		log.debug("params参数:"+params);
		
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no")
				.getBytes("ISO-8859-1"), "UTF-8");

		// 支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes(
				"ISO-8859-1"), "UTF-8");

		// 交易状态
		String trade_status = new String(request.getParameter("trade_status")
				.getBytes("ISO-8859-1"), "UTF-8");

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		log.debug("验证之前:"+out_trade_no);
		if (AlipayNotify.verify(params)) {// 验证成功
			try{
				log.debug("进入验证成功");
				// ////////////////////////////////////////////////////////////////////////////////////////
				// 请在这里加上商户的业务逻辑程序代码
	
				// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				if (trade_status.equals("TRADE_FINISHED")||trade_status.equals("TRADE_SUCCESS")) {
					log.debug("进入验证成功:"+trade_status);
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
				
					// 注意：TRADE_FINISHED
					// 该种交易状态只在两种情况下出现
					// 1、开通了普通即时到账，买家付款成功后。
					// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
					OrderedLesson orderedLesson =new OrderedLesson();
					Map map = new HashMap();
					map.put("state", "ok");
					map.put("orderId", out_trade_no);
					List<Order> lis = orderManager.listByMap(map);
					Order order = null;
					if(lis.size()>0){
						order =lis.get(0);
						if("payed".equals(order.getPaystate())){
							log.debug("已经支付");
							return "redirect:"+Constants.getBasePath(request)+"courseIntro/execute.html?cid="+order.getResourceId();
						}
					}else{
						log.debug("没有该订单");
						return "redirect:index.html";
					}
					
					log.debug("金币生效");
					//金币订单生效判断
					UserLessonOrder ulo = userLessonOrderManager.getByOrderId(out_trade_no);
					if(ulo != null){
						if(!"unpay".equals(ulo.getState())){
							return "redirect:index.html";
						}else{
							if(!userLessonOrderManager.buyCourse(ulo)){
								return "redirect:index.html";
							}
						}
					}
					log.debug("金币结束");
					
					orderedLesson.setId(Common.getUUID());
					orderedLesson.setUserId(order.getUserId());
					orderedLesson.setResourceId(order.getResourceId());
					orderedLesson.setState("ok");
					orderedLesson.setStyle("course");
					int i=orderedLessonManager.save(orderedLesson);
					
					log.debug("保存已购买课程数据:"+ (i==1));
					
					if(i==1){
						order.setPaystate("payed");
						Date date =new Date();
						Calendar cal = Calendar.getInstance();
						order.setPayTime(date);
						order.setPayStyle("alipay");
						if(cal.get(Calendar.MONTH) + 1>=10){
							order.setPayMonth(Integer.toString(cal.get(Calendar.YEAR))+"-"+Integer.toString(cal.get(Calendar.MONTH) + 1));
							}else{
							order.setPayMonth(Integer.toString(cal.get(Calendar.YEAR))+"-"+"0"+Integer.toString(cal.get(Calendar.MONTH) + 1));	
							}
						int n =orderManager.update(order);
						
						if(n==1){
							Map map1 = new HashMap();
							map1.put("lessonId", order.getResourceId());
							map1.put("state", "ok");
							List<CourseStatistics> li =courseStatisticsManager.listByMap(map1);
							if(li.size()>0){
								CourseStatistics courseStatistics = li.get(0);
								courseStatistics.setBuyCount(courseStatistics.getBuyCount()+1);
								courseStatisticsManager.update(courseStatistics);
							}else{
								CourseStatistics courseStatistics = new CourseStatistics();
								courseStatistics.setBuyCount(1);
								courseStatistics.setId(Common.getUUID());
								courseStatistics.setLessonId(order.getResourceId());
								courseStatistics.setName(order.getResourceName());
								courseStatisticsManager.save(courseStatistics);
							}
							Course course=courseManager.get(order.getResourceId());
							if(course.getConvertScore()>0){
								ClientUser user =clientUserManager.get(order.getUserId());
								user.setScore(course.getConvertScore()+user.getScore());
								clientUserManager.update(user);
							}
							
							
						}
						log.debug("信息保存结束");
					}
				
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序

				// 注意：TRADE_SUCCESS
				// 该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
				}
			}catch (Exception e) {
				log.error("支付包异步通知失败",e);
				return render("fail", "text/html",response);
			}
			// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

			// 请不要修改或删除
			return render("success", "text/html",response);

			// ////////////////////////////////////////////////////////////////////////////////////////
		} else {// 验证失败
			return render("fail", "text/html",response);
		}
	}
	
	//同步通知
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/myreturnback",produces="text/html;charset=utf-8")
	public String myreturnback(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		log.debug("进入alipay/myreturnback.html");
		// 获取支付宝GET过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no")
				.getBytes("ISO-8859-1"), "UTF-8");

		// 支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes(
				"ISO-8859-1"), "UTF-8");

		// 交易状态
		String trade_status = new String(request.getParameter("trade_status")
				.getBytes("ISO-8859-1"), "UTF-8");

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		String broadCastURL = (String) request.getSession().getAttribute("broadCastURL");
		request.getSession().removeAttribute("broadCastURL");
		log.debug("验证是否成功？："+verify_result);
		Order order=null;
		if (verify_result) {// 验证成功
			// ////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码
			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if (trade_status.equals("TRADE_FINISHED")
					|| trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序
				Map map = new HashMap();
				map.put("state", "ok");
				map.put("orderId", out_trade_no);
				//map.put("paystate", "payed");
				List<Order> lis = orderManager.listByMap(map);
				if(lis.size()>0){
					order=lis.get(0);
					if("payed".equals(order.getPaystate())){//已经处理过了
						
						if(StringUtil.isNotBlank(broadCastURL)){
							return "redirect:"+broadCastURL;
						}
						return "redirect:"+Constants.getBasePath(request)+"courseIntro/execute.html?cid="+order.getResourceId();
					}else{
						log.debug("金币生效Begin:");
						OrderedLesson orderedLesson =new OrderedLesson();
						//金币订单生效判断
						UserLessonOrder ulo = userLessonOrderManager.getByOrderId(out_trade_no);
						if(ulo != null){
							if(!"unpay".equals(ulo.getState())){
								return "redirect:index.html";
							}else{
								if(!userLessonOrderManager.buyCourse(ulo)){
									return "redirect:index.html";
								}
							}
						}
						
						log.debug("金币生效End:");
						
						orderedLesson.setId(Common.getUUID());
						orderedLesson.setUserId(order.getUserId());
						orderedLesson.setResourceId(order.getResourceId());
						orderedLesson.setState("ok");
						orderedLesson.setStyle("course");
						int i=orderedLessonManager.save(orderedLesson);
						log.debug("保存已购买课程数据:"+ (i==1));
						if(i==1){
							order.setPaystate("payed");
							Date date = new Date();
							Calendar cal = Calendar.getInstance();
							order.setPayTime(date);
							order.setPayStyle("alipay"); 
							if(cal.get(Calendar.MONTH) + 1>=10){
							order.setPayMonth(Integer.toString(cal.get(Calendar.YEAR))+"-"+Integer.toString(cal.get(Calendar.MONTH) + 1));
							}else{
							order.setPayMonth(Integer.toString(cal.get(Calendar.YEAR))+"-"+"0"+Integer.toString(cal.get(Calendar.MONTH) + 1));	
							}
							int n = orderManager.update(order);
							if(n==1){
								Map map1 = new HashMap();
								map1.put("lessonId", order.getResourceId());
								map1.put("state", "ok");
								List<CourseStatistics> li =courseStatisticsManager.listByMap(map1);
								if(li.size()>0){
									CourseStatistics courseStatistics = li.get(0);
									courseStatistics.setBuyCount(courseStatistics.getBuyCount()+1);
									courseStatisticsManager.update(courseStatistics);
								}else{
									CourseStatistics courseStatistics = new CourseStatistics();
									courseStatistics.setBuyCount(1);
									courseStatistics.setId(Common.getUUID());
									courseStatistics.setLessonId(order.getResourceId());
									courseStatistics.setName(order.getResourceName());
									courseStatisticsManager.save(courseStatistics);
								}
								Course course = courseManager.get(order.getResourceId());
								if(course.getConvertScore()>0){
									ClientUser user =clientUserManager.get(order.getUserId());
									user.setScore(course.getConvertScore()+user.getScore());
									clientUserManager.update(user);
								}
								log.debug("信息保存结束");
							}
						}
					}
				}else{
					if(StringUtil.isNotBlank(broadCastURL)){
						return "redirect:"+broadCastURL;
					}
					
					return "redirect:"+Constants.getBasePath(request)+"courseIntro/execute.html?cid="+order.getResourceId(); 
				}
				
			}
			log.debug("验证成功");
			// 该页面可做页面美工编辑
			//return render("验证成功<br />", "text/html",response);
			if(StringUtil.isNotBlank(broadCastURL)){
				return "redirect:"+broadCastURL;
			}
			
			return "redirect:"+Constants.getBasePath(request)+"courseIntro/execute.html?cid="+order.getResourceId(); 
			// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

			// ////////////////////////////////////////////////////////////////////////////////////////
		} else {
			log.debug("验证失败");
			//// 该页面可做页面美工编辑
			return render("购买失败", "text/html",response);
		}
	
	}

	private String order() {
		// 当前时间 yyyyMMddHHmmss
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		// 订单号，此处用时间加随机数生成，商户根据自己情况调整，只要保持全局唯一就行
		String out_trade_no = strTime + strRandom;
		return out_trade_no;

	}
	
	
	protected String render(String text, String contentType,HttpServletResponse response) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
		}
		return null;
	}

	public OrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(OrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public OrderedLessonManager getOrderedLessonManager() {
		return orderedLessonManager;
	}

	public void setOrderedLessonManager(OrderedLessonManager orderedLessonManager) {
		this.orderedLessonManager = orderedLessonManager;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	public void setResourceManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	public CourseManager getCourseManager() {
		return courseManager;
	}

	public void setCourseManager(CourseManager courseManager) {
		this.courseManager = courseManager;
	}

	public CourseStatisticsManager getCourseStatisticsManager() {
		return courseStatisticsManager;
	}

	public void setCourseStatisticsManager(
			CourseStatisticsManager courseStatisticsManager) {
		this.courseStatisticsManager = courseStatisticsManager;
	}

	public ClientUserManager getClientUserManager() {
		return clientUserManager;
	}
	public void setClientUserManager(ClientUserManager clientUserManager) {
		this.clientUserManager = clientUserManager;
	}

	public UserMoneyManager getUserMoneyManager() {
		return userMoneyManager;
	}

	public void setUserMoneyManager(UserMoneyManager userMoneyManager) {
		this.userMoneyManager = userMoneyManager;
	}

	public UserLessonOrderManager getUserLessonOrderManager() {
		return userLessonOrderManager;
	}

	public void setUserLessonOrderManager(
			UserLessonOrderManager userLessonOrderManager) {
		this.userLessonOrderManager = userLessonOrderManager;
	}

}
*/