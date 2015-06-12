package com.rimi.ctibet.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.FileHelper;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.WebSearch;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.controller.vo.Culture;
import com.rimi.ctibet.web.controller.vo.FatherToChild;
import com.rimi.ctibet.web.controller.vo.InfoLink;
import com.rimi.ctibet.web.controller.vo.InfoLinkManage;
import com.rimi.ctibet.web.controller.vo.UserVO;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.ImageService;

/**
 * 读西藏 后台管理
 * 
 * @author chengsl
 * 
 */
@Controller
@RequestMapping("/manage/html/read/")
public class ReadTibetController extends BaseController {
	
	public static  final Logger LOG = Logger.getLogger(ReadTibetController.class);
	
	private static String baseUrl = "/manage/html/read/";
	/** 推荐位 **/
	public static final String TYPE_NAME_POS = "posid";
	/** 文化传播 **/
	public static final String TYPE_NAME_CUL = "cultural";
	/** 动态 **/
	public static final String TYPE_NAME_DYN = "dynamicList";
	/** 基本文化信息：如历史，风俗等 **/
	public static final String TYPE_NAME_IDY = "infoDisplay";
	private static final String REGEX = ",";
	private static final String MVCDEFAULTREGEX = ",";
	private static final String BANNERCODE = "61d19785-7e8c-12e4-b6ce-005056a05bc9";
	@Resource
	private IContentService contentService;
	@Resource
	private IProgramaService programaService;
	@Resource
	private ImageService imageService;

	/**
	 * 添加读西藏文化表单
	 */
	@RequestMapping("read")
	public ModelAndView read(String path) {
		ModelAndView modelAndView = new ModelAndView();
		if (StringUtils.isNotBlank(path)) {
			modelAndView.setViewName(redirect(path));
		}
		return modelAndView;
	}

	/**
	 * 添加读西藏文化表单
	 */
	@RequestMapping("info-creat")
	public ModelAndView creatInfoFrm() {
		ModelAndView modelAndView = new ModelAndView();
		String cultureTypes[][] = Culture.CULTURE_TYPES;
		modelAndView.addObject("types", cultureTypes);
		modelAndView.setViewName(baseUrl + "info-creat");
		return modelAndView;
	}

	/**
	 * 添加读西藏文化传播表单
	 * 
	 * @return
	 */
	@RequestMapping("cultural-info-creat")
	public ModelAndView creatCultureFrm() {
		ModelAndView modelAndView = new ModelAndView();
		String cultureTypes[][] = Culture.CULTURE_M_TYPES;
		modelAndView.addObject("types", cultureTypes);
		modelAndView.setViewName(baseUrl + "cultural-info-creat");
		return modelAndView;
	}

	/**
	 * 更新读西藏文化表单
	 * 
	 * @param code
	 *            文化code
	 * @return
	 */
	@RequestMapping("info-edit")
	public ModelAndView editInfoFrm(String code) {
		ModelAndView modelAndView = new ModelAndView();
		Content content = contentService.findByCode(code);
		Culture culture = new Culture();
		FatherToChild.fatherToChild(content, culture);
		// 阅读数，收藏数，赞
		modelAndView.addObject("culture", culture);
		modelAndView.setViewName(baseUrl + "info-edit");
		return modelAndView;
	}

	/**
	 * 更新读西藏文化传播表单
	 * 
	 * @param code
	 *            文化传播
	 * @return
	 */
	@RequestMapping("cultural-info-edit")
	public ModelAndView editCultureFrm(String code) {
		ModelAndView modelAndView = new ModelAndView();
		Content content = contentService.findByCode(code);
		Culture culture = new Culture();
		FatherToChild.fatherToChild(content, culture);
		// 阅读数，收藏数，赞
		modelAndView.addObject("culture", culture);
		modelAndView.setViewName(baseUrl + "cultural-info-edit");
		return modelAndView;
	}

	/**
	 * 添加读西藏-文化信息
	 * 
	 * @param culture
	 *            文化内容
	 * @param request
	 * @return
	 */
	@RequestMapping("infocreat")
	public ModelAndView add(Content culture, HttpServletRequest request) {
		LOG.info("infocreate");
		ModelAndView modelAndView = new ModelAndView();
		try {
			// 包装信息
			tarContent(culture, request);
			extracted(culture);
			otherFeilds(culture, request);
			culture.setAuthorCode("天上西藏");
			culture.setUrl("culture/detail/"
					+ culture.getCode()+".html");
			LOG.info(culture);
			check(culture);
			addSearch(culture);
			contentService.save(culture);
			modelAndView.setViewName(redirect("infoManage"));
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}

	private void check(Content content) {
		if (StringUtils.isBlank(content.getTitle())
				|| "portal/static/default/square.png"
						.equals(content.getTitle())) {
			String url = getContentImgSrc(content.getContent());
			if (StringUtils.isBlank(url)) {
				url = "portal/static/default/square.png";
			} else {
				try {
					url=url.replaceAll(request.getContextPath(), "");
				} catch (Exception e) {
					LOG.error(e);
				}
			}
			content.setTitle(url);
		}
	}

	// 包装信息
	private void tarContent(Content culture, HttpServletRequest request) {
		UserVO user = (UserVO) request.getSession().getAttribute(
				UserInfoController.SESSION_USER);
		try {
			culture.setCreateuserCode(user.getCode());
		} catch (Exception e) {
		}
	}

	private void extracted(Content culture) {
		culture.setCreateTime(new Date());
		culture.setCode(CodeFactory.createCultureCode());
	}

	// 添加读西藏 文化传播
	@RequestMapping("culturecreat")
	public ModelAndView culturecreate(Content culture,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			// 包装信息
			tarContent(culture, request);
			otherFeilds(culture, request);
			culture.getOthers().put(Culture.FEILD_SCORE,"0.0");
			extracted(culture);
			culture.setUrl("culture/culture_detail/"
					+ culture.getCode()+".html");
			check(culture);
			LOG.info(culture);
			addSearch(culture);
			contentService.save(culture);
			modelAndView.setViewName(redirect("cultural-trans"));
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}

	// 更新 读西藏信息
	@RequestMapping("infoupdate")
	public ModelAndView update(Culture culture, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			// 包装信息
			tarContent(culture, request);
			otherFeilds(culture, request);
			culture.setLastEditTime(new Date());
			culture.setUrl("culture/detail/"
					+ culture.getCode()+".html");
			culture.setCreateTime(DateUtil.strToDate(culture.getDates(),
					DateUtil.TIMESTAMP_PATTERN_2));
			Content content = new Content();
			FatherToChild.childToFather(culture, content);
			check(content);
			// 更新搜索
			updateSearch(content);
			contentService.updateLogical(content);
			modelAndView.setViewName(redirect("infoManage"));
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}

	private void updateSearch(Content culture) {
		/*WebSearch webSearch = new WebSearch();
		webSearch.setCode(culture.getCode());
		webSearch.setTitle(culture.getContentTitle());
		webSearch.setContent(culture.getContetNotHtml());
		webSearch.setUrl(culture.getUrl());
		webSearch.setDate(culture.getCreateTime().getTime());
		webSearch.setImageUrl(culture.getTitle());
		LuceneUtil2.update(webSearch);*/
	}

	private void addSearch(Content culture) {
		/*WebSearch webSearch = new WebSearch();
		webSearch.setCode(culture.getCode());
		webSearch.setTitle(culture.getContentTitle());
		webSearch.setContent(culture.getContetNotHtml());
		webSearch.setUrl(culture.getUrl());
		webSearch.setImageUrl(culture.getTitle());
		LuceneUtil2.add(webSearch);*/
	}

	private void otherFeilds(Content culture, HttpServletRequest request) {
		Map others = new HashMap();
		others.put(Culture.FEILD_VIEW,
				filter(request.getParameter(Culture.FEILD_VIEW)));
		others.put(Culture.FEILD_FAVORITE,
				filter(request.getParameter(Culture.FEILD_FAVORITE)));
		others.put(Culture.FEILD_PRAISE,
				filter(request.getParameter(Culture.FEILD_PRAISE)));
		others.put(Culture.FEILD_COMMENT,
				filter(request.getParameter(Culture.FEILD_COMMENT)));
		others.put(Culture.FEILD_RL_VIEW,
				filter(request.getParameter(Culture.FEILD_RL_VIEW)));
		others.put(Culture.FEILD_RL_FAVORITE,
				filter(request.getParameter(Culture.FEILD_RL_FAVORITE)));
		others.put(Culture.FEILD_RL_PRAISE,
				filter(request.getParameter(Culture.FEILD_RL_PRAISE)));
		others.put(Culture.FEILD_RL_COMMENT,
				filter(request.getParameter(Culture.FEILD_RL_COMMENT)));
		culture.setOthers(others);
	}

	private String filter(String s) {
		if (!StringUtil.isNotNull(s)) {
			return "0";
		}
		return s;
	}

	// 更新读西藏 文化传播
	@RequestMapping("cultureupdate")
	public ModelAndView cultureupdate(Culture culture,
			HttpServletRequest request) {
		otherFeilds(culture, request);
		culture.getOthers().put(Culture.FEILD_SCORE,
				request.getParameter(Culture.FEILD_SCORE));
		ModelAndView modelAndView = new ModelAndView();
		try {
			LOG.info(culture);
			culture.setLastEditTime(new Date());
			tarContent(culture, request);
			culture.setCreateTime(DateUtil.strToDate(culture.getDates(),
					DateUtil.TIMESTAMP_PATTERN_2));
			culture.setUrl("culture/culture_detail/"
					+ culture.getCode()+".html");
			Content content = new Content();
			FatherToChild.childToFather(culture, content);
			check(content);
			updateSearch(content);
			contentService.updateLogical(content);
			modelAndView.setViewName(redirect("cultural-trans"));
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}

	// 分类检索 、按字段排序、并分页
	@RequestMapping("infoManage")
	public ModelAndView search(Pager pager, String orderType, String contentType) {
		LOG.info(orderType);
		LOG.info(contentType);
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (null == orderType || "".equals(orderType)) {
				orderType = "201";
				contentType = "1000";
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName(baseUrl + "error");
		}
		return modelAndView;
	}

	// 分类检索 、按字段排序、并分页
	@RequestMapping(value = "infosearch", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String search1(Pager pager, String keyword, String orderType,
			String contentType) {
		LOG.info(orderType);
		LOG.info(contentType);
		try {
			if (null == orderType || "".equals(orderType)) {
				orderType = "201";
				contentType = "1000";
			}
			Content searchContent = new Content();
			searchContent.setContentType(contentType);
			searchContent.setContentTitle(keyword);
			// searchContent.setContent(keyword);
			searchContent.setCode(keyword);
			contentService.search(pager, searchContent, orderType);
			List<Content> contents = pager.getDataList();
			List cultures = new ArrayList<Culture>();
			for (Content content : contents) {
				Culture culture = new Culture();
				FatherToChild.fatherToChild(content, culture);
				cultures.add(culture);
			}
			pager.setDataList(cultures);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj2json(pager);
	}

	// 分类检索 、按字段排序、并分页
	@RequestMapping("cultural-trans")
	public ModelAndView searchCulture(Pager pager, String keyword,
			String orderType, String contentType) {
		LOG.info(orderType);
		LOG.info(contentType);
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (null == orderType || "".equals(orderType)) {
				orderType = "100";
				contentType = "2000";
			}
			modelAndView.setViewName(baseUrl + "cultural-trans");
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName(baseUrl + "error");
		}
		return modelAndView;
	}

	@RequestMapping("infodelete")
	public ModelAndView delete(String codes, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			drop(codes);
			modelAndView.setViewName(redirect("infoManage"));
		} catch (Exception e) {
			LOG.error(e);
			modelAndView.setViewName(baseUrl + "error");
		}
		return modelAndView;
	}

	@RequestMapping("culturedelete")
	public ModelAndView cultureDelete(String codes) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			drop(codes);
			modelAndView.setViewName(redirect("cultural-trans"));
		} catch (Exception e) {
			LOG.error(e);
			modelAndView.setViewName(baseUrl + "error");
		}
		return modelAndView;
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

	@RequestMapping("banner")
	public ModelAndView banner(String path) {
		ModelAndView modelAndView = new ModelAndView();
		Programa programa = null;
		try {
			if (StringUtil.isNotNull(path)) {
				programa = new Programa();
				programa.setEnName("读西藏banner位");
				programa.setCode(BANNERCODE);
				programa.setImageUrl(path);
				programa.setLastEditTime(new Date());
				UserVO user = (UserVO) session
						.getAttribute(UserInfoController.SESSION_USER);
				programa.setCreateUserCode(user.getCode());
				// programa.setProgramaSummary("最后修改人：" + user.getName());
				programaService.updatePrograma(programa);
				modelAndView.addObject("rs", "ok");
			} else {
				programa = programaService.getProgramaByCode(BANNERCODE);
			}
			modelAndView.addObject("programa", programa);
			modelAndView.setViewName(baseUrl + "banner");
		} catch (Exception e) {
			LOG.error(e);
			modelAndView.setViewName(baseUrl + "error");
		}
		return modelAndView;
	}

	// 上传图片
	@RequestMapping(value = "uploadimage", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public String uploadImage(HttpServletRequest request,
			HttpServletResponse response, String dir) {
		List<String> paths = super.uploadFile(request, response, dir);
		Pager pager = new Pager();
		pager.setDataList(paths);
		return obj2json(pager);
	}

	// 上传图片
	@RequestMapping(value = "uploadmusic", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public String uploadMusic(HttpServletRequest request,
			HttpServletResponse response, String dir) {
		List<String> paths = super.uploadFile(request, response, dir);
		Pager pager = new Pager();
		pager.setDataList(paths);
		return obj2json(pager);
	}

	@RequestMapping("cultural")
	public ModelAndView cultural(String[] links, String codes[]) {
		ModelAndView modelAndView = new ModelAndView();
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		try {
			infoLinkManage.readLinks("cultural");
			modelAndView.addObject("links", infoLinkManage.getInfoLinks());
			modelAndView.setViewName(baseUrl + "cultural");
		} catch (Exception e) {
			LOG.error(e);
			modelAndView.setViewName(baseUrl + "error");
		}
		return modelAndView;
	}

	@RequestMapping("dynamicList")
	public ModelAndView dynamicList(String links[], String[] codes) {
		String type = "dynamicList";
		ModelAndView modelAndView = new ModelAndView();
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		try {
			infoLinkManage.readLinks(type);
			modelAndView.addObject("links", infoLinkManage.getInfoLinks());
			modelAndView.setViewName(baseUrl + type);
		} catch (Exception e) {
			LOG.error(e);
			modelAndView.setViewName(baseUrl + "error");
		}
		return modelAndView;
	}

	@RequestMapping("infoDisplay")
	public ModelAndView infoDisplay(String links[], String[] codes) {
		String type = "infoDisplay";
		ModelAndView modelAndView = new ModelAndView();
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		try {
			infoLinkManage.readLinks(type);
			modelAndView.addObject("links", infoLinkManage.getInfoLinks());
			modelAndView.setViewName(baseUrl + type);
		} catch (Exception e) {
			LOG.error(e);
			modelAndView.setViewName(baseUrl + "error");
		}
		return modelAndView;
	}

	// 推荐位
	@RequestMapping("posid")
	public ModelAndView posid(String links, String codes) {
		String type = "posid";
		ModelAndView modelAndView = new ModelAndView();
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		try {
			infoLinkManage.readLinks(type);
			modelAndView.addObject("links", infoLinkManage.getInfoLinks());
			modelAndView.setViewName(baseUrl + type);
		} catch (Exception e) {
			LOG.error(e);
			modelAndView.setViewName(baseUrl + "error");
		}
		return modelAndView;
	}

	// 管理类型保存
	@RequestMapping("managesave")
	public ModelAndView manageSave(String t, String[] links, String[] codes) {
		ModelAndView modelAndView = new ModelAndView();
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		try {
			if (infoLinkManage.progLinks(links, codes)) {
				infoLinkManage.saveLinks(t);
			}
			modelAndView.setViewName(redirect(t));
		} catch (Exception e) {
			LOG.error(e);
			modelAndView.setViewName(baseUrl + "error");
		}
		return modelAndView;
	}

	// 恢复默认
	@RequestMapping("managedefault")
	public ModelAndView manageDefault(String t, String[] links, String[] codes,
			String orderType) {
		ModelAndView modelAndView = new ModelAndView();
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		List<Content> contents = null;
		LOG.info(t);
		try {
			// 查出相应类型最新 放入相应文件中 TODO
			if (TYPE_NAME_DYN.equals(t)) {
				contents = loadLastContent(
						Culture.getTypeId(Culture.TYPE_DYNAMIC), 7);
			} else if (TYPE_NAME_POS.equals(t)) {
				contents = loadLastContent("1000", 4);
			} else if (TYPE_NAME_CUL.equals(t)) {
				// 文化传播
				contents = loadCulture(orderType);
			} else if (TYPE_NAME_IDY.equals(t)) {
				contents = loadInfoCul(orderType);
			}
			if (contents.size() > 0) {
				List<InfoLink> infoLinks = new ArrayList<InfoLink>();
				for (Content content : contents) {
					InfoLink infoLink = new InfoLink();
					infoLink.setCode(content.getCode());
					if (Integer.parseInt(content.getContentType()) / 1000 == 1) {
						infoLink.setLink(!StringUtil.isNotNull(content.getUrl()) ? "culture/detail/"
								+ content.getCode()+".html"
								: content.getUrl());
					} else {
						infoLink.setLink(!StringUtil.isNotNull(content.getUrl()) ? "culture/culture_detail/"
								+ content.getCode()+".html"
								: content.getUrl());
					}
					infoLinks.add(infoLink);
				}
				infoLinkManage.setInfoLinks(infoLinks);
			}
			infoLinkManage.saveLinks(t);
			// 跳转到相应的页面
			modelAndView.setViewName(redirect(t));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			modelAndView.setViewName(baseUrl + "error");
		}
		return modelAndView;
	}

	private List loadInfoCul(String orderType) {
		List<Content> contents = new ArrayList<Content>();
		initInfoContents(contents, Culture.TYPE_HOLIDAY, 1, orderType);
		initInfoContents(contents, Culture.TYPE_HISTORY, 1, orderType);
		initInfoContents(contents, Culture.TYPE_CUSTOM, 1, orderType);
		initInfoContents(contents, Culture.TYPE_CELEBRITY, 4, orderType);
		initInfoContents(contents, Culture.TYPE_RELIGION, 4, orderType);
		initInfoContents(contents, Culture.TYPE_FOODS, 4, orderType);
		initInfoContents(contents, Culture.TYPE_ART, 4, orderType);
		initInfoContents(contents, Culture.TYPE_SPECIALTY, 7, orderType);
		initInfoContents(contents, Culture.TYPE_BIONT, 7, orderType);
		initInfoContents(contents, Culture.TYPE_COSTUME, 2, orderType);
		initInfoContents(contents, Culture.TYPE_GEOGRAHY, 2, orderType);
		return contents;
	}

	private void initContents(List<Content> contents, String type, int number,
			String ordeType) {
		List rs = loadLastContent(type, number, ordeType);
		contents.addAll(rs);
	}

	private void initCulContents(List<Content> contents, int type, int number,
			String orderType) {
		initContents(contents, Culture.getMTypeId(type), number, orderType);
	}

	private void initInfoContents(List<Content> contents, int type, int number,
			String orderType) {
		initContents(contents, Culture.getTypeId(type), number, orderType);
	}

	private List<Content> loadCulture(String orderType) {
		List<Content> contents = new ArrayList<Content>();
		initCulContents(contents, Culture.TYPE_M_MUSIC, 6, orderType);
		initCulContents(contents, Culture.TYPE_M_STORY, 6, orderType);
		initCulContents(contents, Culture.TYPE_M_GAME, 6, orderType);
		return contents;
	}

	private List loadLastContent(String type, int size) {
		String orderType = "100";
		return loadLastContent(type, size, orderType);
	}

	private List loadLastContent(String type, int size, String orderType) {
		if (StringUtils.isBlank(orderType)) {
			orderType = "100";
		}
		Pager pager = new Pager();
		pager.setPageSize(size);
		Content content = new Content();
		content.setContentType(type);
		contentService.search(pager, content, orderType);
		List<Content> contents = pager.getDataList();
		int i = 0;
		int m = contents.size();
		while (i < size - m) {
			Content tmpcontent = new Content();
			tmpcontent.setCode("NotFound");
			tmpcontent.setContentType(type);
			contents.add(tmpcontent);
			i++;
		}
		return contents;
	}
}
