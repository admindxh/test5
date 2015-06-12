package com.rimi.ctibet.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.WebSearch;
import com.rimi.ctibet.common.util.annotation.Token;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.web.controller.vo.Culture;
import com.rimi.ctibet.web.controller.vo.InfoLink;
import com.rimi.ctibet.web.controller.vo.InfoLinkManage;
import com.rimi.ctibet.web.service.IContentService;

/**
 * 看西藏视频管理
 * 
 * @author chengsl
 * 
 */
@Controller("seeTibetVideoManageController")
@RequestMapping("/manage/html/see/")
public class SeeTibetVideoController extends BaseController {
	private static final String REGEX = ",";
	private static final String BASEURL = "/manage/html/see/";
	private static final String FILENAME = "video-infos";
    public static final	Logger LOG = Logger.getLogger(SeeTibetVideoController.class);
    
	@Resource
	private IContentService contentService;
	
	@RequestMapping("see")
	public ModelAndView see()
	{
		return new ModelAndView();
	}

	/**
	 * 视频显示管理
	 * 
	 * @return 默认
	 */
	@RequestMapping(value = "video-display")
	public ModelAndView videoDispaly(String type) {
		// 接收参数并保持
	
		ModelAndView modelAndView = new ModelAndView();
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		try {
			if (StringUtils.isBlank(type)) {
				infoLinkManage.readLinks(FILENAME);
				modelAndView.addObject("links", infoLinkManage.getInfoLinks());
			} else { // 从数据库查询
				List links = infoLinkManage.getInfoLinks();
				links.clear();
				Content content = new Content();
				content.setContentType("3000");
				Pager pager = new Pager();
				pager.setPageSize(3);
				contentService.search(pager, content, type);
				List<Content> contents = pager.getDataList();
				int i = 0;
				while (i < 3) {
					InfoLink link = new InfoLink();
					Content c;
					if (i < contents.size()) {
						c = contents.get(i);
					} else {
						c = new Content();
					}
					link.setLink(c.getUrl());
					link.setCode(c.getCode());
					i++;
					links.add(link);
				}
				modelAndView.addObject("links",links);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

	/**
	 * 视频管理页
	 * 
	 * @return 默认
	 */
	@RequestMapping(value = "videodispalysave")
	public ModelAndView videoDispalySave(String links[], String codes[]) {
		// 接收参数并保持
		ModelAndView view = new ModelAndView();
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		try {
			
				if (infoLinkManage.progLinks(links, codes)) {
					infoLinkManage.saveLinks(FILENAME);
			
			}
		} catch(Exception e) {
				e.printStackTrace();
		}
		view.setViewName(redirect(BASEURL + "video-display"));
		return view;
	}
/*	
	private List<Content> loadDispalyVideo()
	{
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		infoLinkManage.readLinks("video-infos");
		List<InfoLink> infoLinks = infoLinkManage.getInfoLinks();
		List<Content> contents = new ArrayList<Content>();
		for (InfoLink infoLink : infoLinks) {
			contents.add(contentService.findByCode(infoLink.getCode()));
		}
		return contents;
	}
	*/
	
	/**
	 * 视频管理页 加载 出来显示
	 * 
	 * @return 默认
	 */
	@RequestMapping(value = "video-infomessage")
	public ModelAndView videoMessageLoad() {
		ModelAndView modelAndView = new ModelAndView();
		return modelAndView;
	}


	/**
	 * 视频管理页
	 * 
	 * @return 默认
	 */
	@RequestMapping(value = "videolist", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String videolist(Pager pager, String keyword, String orderType) {
		if (null == orderType || "".equals(orderType)) {
			orderType = "201";
		}
		String contentType = "3010";
		Content searchContent = new Content();
		searchContent.setContentType(contentType);
		searchContent.setContentTitle(keyword);
		searchContent.setCode(keyword);
		searchContent.setAuthorCode(keyword);
		contentService.search(pager, searchContent, orderType);
		return obj2json(pager);
	}

	/**
	 * 视频添加表单页
	 * 
	 * @return 默认
	 * 
	 */
	@Token(save=true)
	@RequestMapping(value = "video-creat")
	public ModelAndView videoCreatFrm() {
		ModelAndView view = new ModelAndView();
		return view;
	}

	/**
	 * 视频添加表单页
	 * 
	 * @return 默认
	 */
	@Token(remove=true)
	@RequestMapping(value = "videocreat")
	public ModelAndView videoCreat(Content content) {
		ModelAndView view = new ModelAndView();
		content.setCreateuserCode(getManagerUser().getCode());
		content.setCode(CodeFactory.createVideoCode());
		content.setUrl("discover/video?code="
				+ content.getCode());
		//content.setTitle("");
		addOtherFeilds(content, request);
		contentService.save(content);
		addSearch(content);
		view.setViewName(redirect(BASEURL + "video-infomessage"));
		return view;
	}

	/**
	 * 删除; 可以批量删除 用springmvc分割数组的字符分割
	 * 
	 * @param codes
	 * @return
	 */

	@RequestMapping(value = "deletes")
	public ModelAndView deletes(String codes) {
		ModelAndView view = new ModelAndView();
		try {
			drop(codes);
			view.setViewName(redirect(BASEURL + "video-infomessage"));
		} catch (Exception e) {
			e.printStackTrace();
			view.setViewName("error");
		}
		return view;
	}

	private void drop(String idsStr) {
		idsStr += REGEX;
		String ids[] = idsStr.split(REGEX);
		List<String> codes = new ArrayList<String>();
		List<WebSearch> wss = new ArrayList();
		for (String string : ids) {
			if (!"".equals(string)) {
				codes.add(string);
				WebSearch webSearch = new WebSearch();
				webSearch.setCode(string);
				LuceneUtil2.delete(webSearch);
			}
		}
		contentService.deleteByCodes(codes);
	}

	private void updateSearch(Content video) {
		WebSearch webSearch = new WebSearch();
		webSearch.setCode(video.getCode());
		webSearch.setTitle(video.getContentTitle());
		webSearch.setContent(video.getContetNotHtml());
		webSearch.setUrl(video.getUrl());
		webSearch.setDate(video.getCreateTime().getTime());
		webSearch.setImageUrl(video.getTitle());
		webSearch.setType("3000");
		LuceneUtil2.update(webSearch);
	}

	private void addSearch(Content culture) {
		WebSearch webSearch = new WebSearch();
		webSearch.setCode(culture.getCode());
		webSearch.setTitle(culture.getContentTitle());
		webSearch.setContent(culture.getContetNotHtml());
		webSearch.setUrl(culture.getUrl());
		webSearch.setImageUrl(culture.getTitle());
		webSearch.setType("3000");
		LuceneUtil2.add(webSearch);
	}


	/**
	 * 视频修改表单页
	 * 
	 * @return 默认
	 */
	@Token(save=true)
	@RequestMapping(value = "video-edit")
	public ModelAndView videoEditFrm(String code) {
		ModelAndView view = new ModelAndView();
		Content content = contentService.findByCode(code);
		view.addObject("video", content);
		return view;
	}

	/**
	 * 视频修改表单页
	 * 
	 * @return 默认
	 */
	@Token(remove=true)
	@RequestMapping(value = "videoedit")
	public ModelAndView videoEdit(Content content, String dates) {
		ModelAndView view = new ModelAndView();
		content.setCreateuserCode(getManagerUser().getCode());
		content.setCreateTime(DateUtil.strToDate(dates,
				DateUtil.TIMESTAMP_PATTERN_2));
		content.setUrl("discover/video?code="
				+ content.getCode());
		addOtherFeilds(content, request);
		contentService.updateLogical(content);
		updateSearch(content);
		view.setViewName(redirect(BASEURL + "video-infomessage"));
		return view;
	}

	private void addOtherFeilds(Content content, HttpServletRequest request) {
		Map others = new HashMap();
		Culture culture = new Culture();
		others.put(culture.FEILD_VIEW,
				filter(request.getParameter(culture.FEILD_VIEW)));
		others.put(culture.FEILD_FAVORITE,
				filter(request.getParameter(culture.FEILD_FAVORITE)));
		others.put(culture.FEILD_PRAISE,
				filter(request.getParameter(culture.FEILD_PRAISE)));
		others.put(culture.FEILD_COMMENT,
				filter(request.getParameter(culture.FEILD_COMMENT)));
		others.put(culture.FEILD_RL_VIEW,
				filter(request.getParameter(culture.FEILD_RL_VIEW)));
		others.put(culture.FEILD_RL_FAVORITE,
				filter(request.getParameter(culture.FEILD_RL_FAVORITE)));
		others.put(culture.FEILD_RL_PRAISE,
				filter(request.getParameter(culture.FEILD_RL_PRAISE)));
		others.put(culture.FEILD_RL_COMMENT,
				filter(request.getParameter(culture.FEILD_RL_COMMENT)));
		content.setOthers(others);
	}

	public String filter(String s) {
		if (!StringUtil.isNotNull(s)) {
			return "0";
		}
		return s;
	}

	@RequestMapping(value = "uploadvideo", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String uploadVide(HttpServletRequest request,
			HttpServletResponse response, String dir) {
		List<String> paths = super.uploadFile(request, response, dir);
		Pager pager = new Pager();
		pager.setDataList(paths);
		return obj2json(pager);
	}

}
