package com.rimi.ctibet.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.WebSearch;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.UserInfo;
import com.rimi.ctibet.web.controller.vo.FireAuthority;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.ILoginLogService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IPageViewService;
import com.rimi.ctibet.web.service.IPostService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IUserInfoService;

/**
 * 后台主页
 * 
 * @author chengsl
 */
@Controller
@RequestMapping("/manage/html/")
public class MainController extends BaseController {
	private static final String BASEURL = "/manage/html/";
	public static  Logger LOG = Logger.getLogger(MainController.class);
	@Resource
	private IUserInfoService userInfoService;
	@Resource
	private IProgramaService programaService;
	@Resource
	private IContentService contentService;

	@Resource
	private IPageViewService pageViewService;

	@Resource
	private ILoginLogService loginLogService;

	@Resource
	private IMemberService memberService;

	@Resource
	private IPostService postService;

	// 首页 显示登录框页面
	@RequestMapping("index")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView(BASEURL + "index");
		return modelAndView;
	}

	// 概览页面
	@RequestMapping("admin-home")
	public ModelAndView adminhome() {
		ModelAndView modelAndView = new ModelAndView(BASEURL + "admin-home");

		// 待审核评论数量 {
		// web/replyManageController/forReviewReplyManageList?replyType=
		int viewCount = contentService.findReplyNumByContentTypeState(
				Content.DETAIL_VIEW_REPLY, Content.REVIEW_STATE_WAIT); // 景点详情页待审核评论数
		int merchantCount = contentService.findReplyNumByContentTypeState(
				Content.DETAIL_MERCHANT_REPLY, Content.REVIEW_STATE_WAIT); // 商户详情页待审核评论数
		int strategyCount = contentService.findReplyNumByContentTypeState(
				Content.DETAIL_STRATEGY_REPLY, Content.REVIEW_STATE_WAIT); // 攻略详情页待审核评论数
		int readTibetCount = contentService.findReplyNumByContentTypeState(
				Content.DETAIL_READ_TIBET_REPLY, Content.REVIEW_STATE_WAIT); // 读西藏详情页待审核评论数
		int readTibetCultureCount = contentService
				.findReplyNumByContentTypeState(
						Content.DETAIL_READ_TIBET_CULTURE_REPLY,
						Content.REVIEW_STATE_WAIT); // 读西藏文化详情页待审核评论数
		int allReplyCount = contentService.findReplyNumByContentTypeState(null,
				Content.REVIEW_STATE_WAIT); // 总的数量

		// 待审核帖子的评论和回复
		int uncheckedpostandreplynum = postService
				.getUncheckedPostAndReplyNum();

		modelAndView.addObject("uncheckedpostandreplynum",
				uncheckedpostandreplynum);
		modelAndView.addObject("viewCount", viewCount);
		modelAndView.addObject("merchantCount", merchantCount);
		modelAndView.addObject("strategyCount", strategyCount);
		modelAndView.addObject("readTibetCount", readTibetCount
				+ readTibetCultureCount);
		modelAndView.addObject("allReplyCount", allReplyCount);

		// 报表展示数据查询
		// 得到当前日期的前面7天
		Date currentDate = new Date();
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance();// 得到日期

		String stdate = "[";// 当前日期格式字符串
		String pv = "[";// pv 值
		String acnumber = "[";// 活跃数
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");

		for (int i = -6; i <= 0; i++) {
			Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DAY_OF_MONTH, i);
			dBefore = calendar.getTime();
			stdate = stdate + "'" + (calendar.get(Calendar.MONTH) + 1) + "/"
					+ simpleDateFormat.format(dBefore) + "'";
			// 获取当前日期
			Integer pvTotal = pageViewService.getMemCountByTime(dBefore,
					dBefore);
			Integer listMemActiveCount = loginLogService.getActUserNum(dBefore,
					dBefore); // 活跃数量
			pv = pv + pvTotal;
			acnumber = acnumber + listMemActiveCount;
			if (i != 0) {
				stdate = stdate + ",";
				pv = pv + ",";
				acnumber = acnumber + ",";
			}
		}
		stdate = stdate + "]";
		pv = pv + "]";
		acnumber = acnumber + "]";
		// 统计及总的注册数量
		// 总的注册数量
		Integer memTotal = memberService.getMemCountByTime(null, null);
		modelAndView.addObject("memTotal", memTotal);
		// 统计评论量
		Integer replyTotal = contentService.getReplyCount(null, null);

		modelAndView.addObject("replyTotal", replyTotal);
		// System.out.println(stdate+"-----"+pv+"---"+acnumber);
		modelAndView.addObject("stdate", stdate);
		modelAndView.addObject("pv", pv);
		modelAndView.addObject("acnumber", acnumber);
		return modelAndView;
	}

	// 登录后跳到的主页
	@RequestMapping("main")
	@FireAuthority(value = 0)
	public ModelAndView main(HttpServletRequest request) {
		LOG.info("进入主页");
		ModelAndView modelAndView = new ModelAndView(BASEURL + "main");
		// 后台导航栏 code 6fda225d-764f-42d6-9dfa-0b8f0ba5ca70
		String topCode = "6fda225d-764f-42d6-9dfa-0b8f0ba5ca70";
		List<Programa> topProgramas = programaService.getSendPrograma(topCode);
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session
				.getAttribute(UserInfoController.SESSION_USER);
		/*
		 * for (Programa programa : topProgramas) { log.info(programa); }
		 */
		modelAndView.addObject("topProgramas", topProgramas);
		modelAndView.addObject("userInfo", userInfo);
		return modelAndView;
	}

	// 建设中
	@RequestMapping("other")
	public String other(HttpServletRequest request) {
		return BASEURL + "other/building";
	}

	/**
	 * 根据path跳转
	 */
	@RequestMapping("finder")
	public ModelAndView read(String path) {
		ModelAndView modelAndView = new ModelAndView();
		if (StringUtils.isNotBlank(path)) {
			modelAndView.setViewName(redirect(path));
		}
		return modelAndView;
	}

	@RequestMapping("getSessionId")
	@ResponseBody
	public String getSessionId(HttpServletRequest request) {
		return request.getSession().getId();
	}

	public static void main(String[] args) {
		Date currentDate = new Date();
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance();// 得到日期

		String stdate = "[";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");

		for (int i = -6; i <= 0; i++) {
			Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DAY_OF_MONTH, i);
			dBefore = calendar.getTime();
			stdate = stdate + "'" + (calendar.get(Calendar.MONTH) + 1) + "/"
					+ simpleDateFormat.format(dBefore) + "'";
			if (i != 0) {
				stdate = stdate + ",";
			}
		}
		stdate = stdate + "]";
		// System.out.println(stdate);
	}

	// 删除搜索信息
	@RequestMapping(value = "deletelucene", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String delete(String code) {
		boolean isTrue = false;
		if(StringUtils.isBlank(code)){
			return "code为空，删除失败了.....";
		}
		try {
			WebSearch webSearch = new WebSearch();
			webSearch.setCode(code);
			isTrue = LuceneUtil2.delete(webSearch);
		} catch (Exception e) {
			e.printStackTrace();
			isTrue = false;
		}
		if (isTrue) {
			return "删除" + code + "成功";
		} else {
			return "删除 失败了.....";
		}
	}
}
