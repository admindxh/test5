package com.rimi.ctibet.portal.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.UserFavorite;
import com.rimi.ctibet.web.controller.vo.Culture;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IReplyService;
import com.rimi.ctibet.web.service.IUserFavoriteService;

/**
 * 看西藏-视频-前端页面
 * 
 * @author chengsl
 */
@Controller(value = "seeTibetVideoPortal")
@RequestMapping(value = "portal/app/discover")
public class SeeTibetVideoController extends BaseController {

	private static final String BASEURL = "portal/app/discover/";
	@Resource
	private IUserFavoriteService userFavoriteService;
	@Resource
	private IContentService contentService;
	@Resource
	private IProgramaService programaService;

	/**
	 * 视频列表页
	 * 
	 * @return 默认
	 */
	@RequestMapping("video_list")
	public ModelAndView listVideoPage() {
		ModelAndView view = new ModelAndView();
		view.addObject("programNam", 3);
		return view;
	}

	/**
	 * 视频列表
	 * 
	 * @param pager
	 * @param orderType
	 * @return json
	 */
	@RequestMapping(value = "list", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String list(Pager pager, String orderType) {
		Content content = new Content();
		content.setContentType("3000");
		contentService.searchOnOthers(pager, content, orderType);
		return obj2json(pager);
	}

	/**
	 * 视频详情页
	 * 
	 * @return 默认
	 */
	@RequestMapping("video")
	public ModelAndView videoPage(String code) {
		ModelAndView view = new ModelAndView();
		Content content = contentService.findByCode(code);
		// 统计次数
		addView(code, content);
		view.addObject("content", content);
		view.addObject("programNam", 3);
		view.addObject("isOpen",
				programaService.isOpenReply(Content.DETAIL_VEDIO_REPLY));
		return view;
	}

	@RequestMapping("prevideo")
	public ModelAndView preVideo(Content content) {
		ModelAndView view = new ModelAndView();

		view.addObject("content", content);
		view.setViewName(BASEURL + "video");
		view.addObject("programNam", 3);
		return view;
	}

	private void addView(String code, Content content) {
		String name = code + "view";
		// if (checkSessionOperate(session, name)) {
		//
		// } else {
		Map map = contentService.findOthers(code);
		String ps = (String) map.get(Culture.FEILD_VIEW);
		String rlps = (String) map.get(Culture.FEILD_RL_VIEW);
		try {
			ps = checkNull2zero(ps);
			rlps = checkNull2zero(rlps);
			int x = Integer.parseInt(ps) + 1;
			int y = Integer.parseInt(rlps) + 1;
			//map.clear();
			map.put(Culture.FEILD_VIEW, "" + x);
			map.put(Culture.FEILD_RL_VIEW, "" + y);
			map.put(Culture.FEILD_FAVORITE, checkNull2zero((String)map.get(Culture.FEILD_FAVORITE)));
			map.put(Culture.FEILD_PRAISE, checkNull2zero((String)map.get(Culture.FEILD_PRAISE)));
			contentService.updateOthers(code, map);
			content.setState(0);
			content.setOthers(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// }
	}

	/**
	 * 查询评论
	 * 
	 * @param code
	 * @param page
	 * @return 评论列表
	 */
	@RequestMapping(value = "comments", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String comments(String code, Pager page) {
		if (page == null) {
			page = new Pager();
			page.setPageSize(8);
		}
		page = contentService.getReplyByPostCodeState(page, code, null, 1);
		return obj2json(page);
	}

	// 插入评论
	@RequestMapping(value = "comment", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String comment(Content comment, String contentcode) {
		LogUser lu = getFrontUser();
		String msg = "";
		if (null == lu) {
			comment.setState(3);// 没有登陆
			lu = new LogUser();
		} else {
			comment.setAuthorCode(lu.getUsername());
			comment.setCreateuserCode(lu.getCode());
			comment.setCode(CodeFactory.createCultureReplyCode());
			contentService.saveReply(comment, contentcode);
			Map map = contentService.findOthers(contentcode);
			String cm = (String) map.get(Culture.FEILD_COMMENT);
			String rlcm = (String) map.get(Culture.FEILD_RL_COMMENT);
			try {
				cm = checkNull2zero(cm);
				rlcm = checkNull2zero(rlcm);

				int x = Integer.parseInt(cm) + 1;
				int y = Integer.parseInt(rlcm) + 1;
				map.clear();
				map.put(Culture.FEILD_COMMENT, "" + x);
				map.put(Culture.FEILD_RL_COMMENT, "" + y);
				contentService.updateOthers(contentcode, map);
				comment.setState(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj2json(comment);
	}

	private String checkNull2zero(String pr) {
		if (!StringUtil.isNotNull(pr) || pr.equals("null")) {
			pr = "0";
		}
		return pr;
	}

	// 点赞 ajax
	@RequestMapping(value = "praise", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String praise(String code) {
		//
		String name = code;
		if (getFrontUser() != null) {
			// name += getFrontUser().getCode();
		}
		Content content = new Content();
		if (checkSessionOperate(session, name)) {
			content.setState(4);// 已经点过赞
			content.setCode(code);
		} else {
			Map map = contentService.findOthers(code);
			String ps = (String) map.get(Culture.FEILD_PRAISE);
			String rlps = (String) map.get(Culture.FEILD_RL_PRAISE);
			try {
				ps = checkNull2zero(ps);
				rlps = checkNull2zero(rlps);
				int x = Integer.parseInt(ps) + 1;
				int y = Integer.parseInt(rlps) + 1;
				map.clear();
				map.put(Culture.FEILD_PRAISE, "" + x);
				map.put(Culture.FEILD_RL_PRAISE, "" + y);
				contentService.updateOthers(code, map);
				content.setState(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj2json(content);
	}

	// 收藏 ajax
	@RequestMapping(value = "favorite", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String favorite(String code) {
		LogUser user = getFrontUser();
		Content content = new Content();
		content.setCode(code);
		if (null == user) {
			content.setState(3);// 没有登陆
		} else {
			if (userFavoriteService.isFavAlready(user.getCode(), code,
					UserFavorite.User_Fav_See)) {
				content.setState(4);// 已经收藏
			} else {// 收藏
				UserFavorite userFavorite = new UserFavorite();
				userFavorite.setMemberCode(user.getCode());
				userFavorite.setType(UserFavorite.User_Fav_See);
				userFavorite.setCode(code);
				userFavorite.setJoinTime(new Date());
				userFavoriteService.save(userFavorite);
				content.setState(0);
				Map map = contentService.findOthers(code);
				String ft = (String) map.get(Culture.FEILD_FAVORITE);
				String rlft = (String) map.get(Culture.FEILD_RL_FAVORITE);
				try {
					ft = checkNull2zero(ft);
					rlft = checkNull2zero(rlft);
					int x = Integer.parseInt(ft) + 1;
					int y = Integer.parseInt(rlft) + 1;
					map.clear();
					map.put(Culture.FEILD_FAVORITE, "" + x);
					map.put(Culture.FEILD_RL_FAVORITE, "" + y);
					contentService.updateOthers(code, map);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return obj2json(content);
	}

	/**
	 * 查找相关信息，热门信息等，根据 传入参数决定返回值
	 * 
	 * @param page
	 * @param content
	 * @param orderField
	 * @return 内容数组
	 */
	@RequestMapping(value = "others", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String loadOthers(Pager page, Content content, String orderType) {
		if (page.getPageSize() > 11) {
			return obj2json(page);
		}
		contentService.searchOnOthers(page, content, orderType);
		return obj2json(page);
	}

	@RequestMapping(value = "checkpraise", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String checkPraise(String code) {
		String name = code;// 用户名
		Content content = new Content();
		if (checkSessionOperate2(session, name)) {
			content.setState(4);
		}
		return obj2json(content);
	}

	@RequestMapping(value = "checkfav", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String checkFav(String code) {
		LogUser user = getFrontUser();
		Content content = new Content();
		content.setCode(code);
		if (null == user) {
			content.setState(3);// 没有登陆
		} else {
			if (userFavoriteService.isFavAlready(user.getCode(), code,
					UserFavorite.User_Fav_See)) {
				content.setState(4);//
			}
		}
		return obj2json(content);
	}

}
