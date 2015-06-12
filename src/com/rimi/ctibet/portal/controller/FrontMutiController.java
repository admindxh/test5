package com.rimi.ctibet.portal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.cache.CacheOperation;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.UserFavorite;
import com.rimi.ctibet.web.controller.vo.InfoLink;
import com.rimi.ctibet.web.controller.vo.InfoLinkManage;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IPostService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IUserFavoriteService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.MutiImageService;

/**
 * 前台看西藏
 * 
 * @author xiangwq
 * 
 */
@Controller
@RequestMapping("portal/frontMutiController")
public class FrontMutiController extends BaseController {
	@Resource
	private MutiImageService mutiService;
	@Resource
	private ImageService imageService;
	@Resource
	private IContentService contentService;
	@Resource
	private IPraiseService praiseService;
	@Resource
	private IUserFavoriteService userFavoriteService;
	@Resource
	private IPostService postService;

	
	public static void main(String[] args) {
	}
	/**
	 * 跳转到看西藏主页
	 */
	@RequestMapping("toHome")
	public ModelAndView getMutiDetail(ModelMap model) {
		ModelAndView view = (ModelAndView) CacheOperation.getInstance().getCacheData(this, "getMutiDetailHanlder",null , Constants.intervalTime, Constants.maxVisitCount);
		return view;
	}
	public ModelAndView getMutiDetailHanlder(){
		
		ModelAndView view = new ModelAndView("portal/app/discover/index");
		view.addObject("programNam", 3);
		// *******************************banner *****************************
		ModelMap model = new ModelMap();
		loadBanner(model);
		// ************************图说西藏显示************************************
		loadImageTibet(model);
		// *************************最热查看*************************************
		loadHotImages(model);
		// **************************视频接口***********************************
		// 最热视频
		loadHotVideo(model);
		// 视频列表
		List<Content> videoList = loadDispalyVideo();
		view.addObject("videoList", videoList);
		
		view.addObject("banner",model.get("banner"));
		view.addObject("show",model.get("show"));
		view.addObject("hotMutiList",model.get("hotMutiList"));
		view.addObject("hotVideoList",model.get("hotVideoList"));
		
		// ******************************************************************
		return view;
	}

	private void loadHotVideo(ModelMap model) {
		Content videoContent = new Content();
		Pager videoPager = new Pager();
		videoContent.setContentType("3000");
		videoPager.setPageSize(4);
		videoPager = contentService.searchOnOthers(videoPager, videoContent,
				"201");
		model.put("hotVideoList", videoPager.getDataList());
	}

	private void loadHotImages(ModelMap model) {
		String programaCode = "14dba551-cb5b-4631-b5ef-b3838670b3a9";
		Pager pager = new Pager();
		pager.setPageSize(4);
		MutiImage mutiImage = new MutiImage();
		Map map = new HashMap();
		map.put("2", "falseViewcount desc");
		mutiImage.setProgramaCode("14dba551-cb5b-4631-b5ef-b3838670b3a9");
		pager = mutiService.search(pager, mutiImage, (String) map.get("2"));
		//pager = mutiService.findMutiPagerOnImages(pager, programaCode);
		List<MutiImage> mutiList = pager.getDataList();
		model.put("hotMutiList", mutiList);
	}

	private void loadImageTibet(ModelMap model) {
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
		List<Content> showConList = contentService
				.findContentByProgrmaCode("345287ab-742a-33b2-b6ce-349202605b2a");
		for (Content c : showConList) {
			// url 保存为连接
			// content保存为编号
			Map<String, Object> map = new HashMap<String, Object>();
			MutiImage muti = mutiService.getMutiImageByCode(c.getContent());
			List<Image> imglist = new ArrayList<Image>();
			Praise praise = null;
			String imgUrl = null;
			if (muti != null) {
				imglist = imageService.getImageByMutiCode(muti.getCode());
				imgUrl = muti.getCoverUrl();
				praise = praiseService.getPraiseContentCode(muti.getCode());
			}
			int x = checkCode(c.getContent());
			if (x == 0) {
				muti = ceateNotData(map);
				imgUrl="portal/static/default/square.png";
			} else if (x == 1) {
				map.put("hyperlink", "discover/picture/"
						+ c.getContent()+".html");
				
			} else if (x == 2) {
				map.put("hyperlink",
						"discover/video/" + c.getContent()+".html");
				imgUrl="portal/static/default/square.png";
			}
			map.put("praise", praise);
			map.put("muti", muti);
			map.put("imgUrl", imgUrl);
			showList.add(map);
		}
		model.put("show", showList);
	}
	private void loadBanner(ModelMap model) {
		List<Map<String, Object>> bannerList = new ArrayList<Map<String, Object>>();
		// banner位
		List<Content> bannerConList = contentService
				.findContentByProgrmaCode("78abc9785-123c-1224-bab-0211056a05bc9");
		for (int i = 0; i < bannerConList.size(); i++) {
			// content的url 保存为图片路径
			// content保存为连接
			// contentTitle保存为编号
			Praise praise = null;
			MutiImage muti = null;
			Map<String, Object> map = new HashMap<String, Object>();
			String imgUrl = null;
			if (checkCode(bannerConList.get(i).getContentTitle()) == 1) {//图集
					muti = mutiService.getMutiImageByCode(bannerConList
						.get(i).getContentTitle());
				if (muti != null) {
					praise = praiseService.getPraiseContentCode(muti.getCode());
					imgUrl = bannerConList.get(i).getUrl();
					map.put("imgUrl", imgUrl);
				}else
				{
					muti = ceateNotData(map);
				}
			} else if (2 == checkCode(bannerConList.get(i).getContentTitle())) {
				Content content=contentService.findByCode(bannerConList.get(i).getContentTitle());
				if(content==null)
				{
					muti = ceateNotData(map);
				}else
				{
				Map tmpmap = contentService.findOthers(bannerConList.get(i).getContentTitle());
				praise = new Praise();
				praise.setFalseViewcount(Integer.valueOf((String)tmpmap.get("view")));
				praise.setFalseFavoriteNum(Integer.valueOf((String)tmpmap.get("favorite")));
				muti  = new MutiImage();
				muti.setName(content.getContentTitle());
				muti.setSummary(content.getContetNotHtml());
				imgUrl = bannerConList.get(i).getUrl();
				map.put("imgUrl", imgUrl);
				}
			} else {
				muti = ceateNotData(map);
			}
			map.put("muti", muti);
			String hyperlink = bannerConList.get(i).getContent();
			map.put("hyperlink", hyperlink);
			map.put("praise", praise);
			// 把bannerMap放入bannerList里
			bannerList.add(map);
		}
		// 把bannerlist保存到model并传入首页
		model.put("banner", bannerList);
	}

	private MutiImage ceateNotData(Map<String, Object> map) {
		MutiImage muti;
		muti = new MutiImage();
		muti.setCoverUrl("portal/static/default/square.png");
		muti.setName("暂无数据");
		muti.setSummary("暂无数据");
		map.put("hyperlink", "portal/static/default/square.png");
		String imgUrl="portal/static/default/square.png";
		map.put("imgUrl", imgUrl);
		return muti;
	}

	private List<Content> loadDispalyVideo() {
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		infoLinkManage.readLinks("video-infos");
		List<InfoLink> infoLinks = infoLinkManage.getInfoLinks();
		List<Content> contents = new ArrayList<Content>();
		for (InfoLink infoLink : infoLinks) {
			Content content=contentService.findByCode(infoLink.getCode());
			if(content==null||StringUtils.isBlank(content.getCode())){
				content= new Content();
				content.setTitle("404");
				content.setUrl("404");
			}
			contents.add(content);
		}
		return contents;
	}

	/**
	 * 跳转到图说西藏的列表页
	 * 
	 * @return
	 */
	@RequestMapping("toList")
	public ModelAndView toList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("portal/app/discover/picture_list");
		modelAndView.addObject("programNam", 3);
		return modelAndView;
	}

	/**
	 * ajax刷新分页
	 * 
	 * @param action
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value = "list", produces = "text/html; charset=utf-8")
	public @ResponseBody
	String getAjaxPager(String action, Pager pager,HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		Map map = new HashMap();
		map.put("1", "m.createTime desc");
		map.put("2", "falseReplyNum desc");
		map.put("3", "falseFavoriteNum desc");
		if (StringUtils.isBlank(action)) {
			action = "1";

		}
		MutiImage mutiImage = new MutiImage();
		mutiImage.setProgramaCode("14dba551-cb5b-4631-b5ef-b3838670b3a9");
		pager = mutiService.search(pager, mutiImage, (String) map.get(action));
		return obj2json(pager);
	}

	/**
	 * 跳转到图集详情页
	 * 
	 * @param muticode
	 * @param model
	 * @return
	 */
	@RequestMapping("getMutiDetail")
	public String getMutiDetail(String muticode, ModelMap model) {
		MutiImage muti = mutiService.getMutiImageByCode(muticode);
		MutiImage premuti = null;
		MutiImage nextmuti = null;
		Map map = mutiService.preAndNextMuti(muti);
		List<Image> imgList = imageService.getImageByMutiCode(muticode);
		Praise praise = praiseService.getPraiseContentCode(muticode);
		model.put("muti", muti);
		model.put("imgList", imgList);
		model.put("praise", praise);
		model.put("premuti", map.get("pre"));
		model.put("nextmuti", map.get("next"));
		// *******************评论********************
		Pager pager = new Pager();
		pager.setPageSize(8);
		pager = postService.getMutiPost(pager, muticode);
		List<Content> postList = postService.findPostByMutiCode(muticode);
		model.put("commentCount", postList.size());
		model.put("pager", pager);
		model.addAttribute("programNam", 3);
		return "portal/app/discover/picture";
	}

	// 收藏 ajax
	@RequestMapping(value = "favorite", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String favorite(String imgCode, String mutiCode) {
		LogUser user = getFrontUser();
		Content content = new Content();
		content.setCode(mutiCode);
		if (null == user) {
			content.setState(3);// 没有登陆
		} else {
			if (userFavoriteService.isFavAlready(user.getCode(), mutiCode,
					UserFavorite.User_Fav_Image)) {
				content.setState(4);// 已经收藏
			} else {// 收藏
				UserFavorite userFavorite = new UserFavorite();
				userFavorite.setMemberCode(user.getCode());
				userFavorite.setType(UserFavorite.User_Fav_Image);
				userFavorite.setCode(mutiCode);
				userFavorite.setJoinTime(new Date());
				userFavoriteService.save(userFavorite);
				Praise p = praiseService.getPraiseContentCode(mutiCode);
				p.setFalseFavoriteNum(p.getFalseFavoriteNum() + 1);
				p.setFavoriteNum(p.getFavoriteNum() + 1);
				p.setLastEditTime(new Date());
				praiseService.update(p);
				content.setState(0);
			}
		}
		return obj2json(content);
	}

	@RequestMapping(value = "praise", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String praise(String imgCode, String mutiCode) {
		//
		String name = mutiCode;// 用户名
		if (getFrontUser() != null) {
		//	name += getFrontUser().getCode();
		}
		Content content = new Content();
		if (checkSessionOperate(session, name)) {
			content.setState(4);// 已经点过赞
			content.setCode(mutiCode);
		} else {
			Praise p2 = praiseService.getPraiseContentCode(mutiCode);
			p2.setTruePraise(p2.getTruePraise() + 1);
			p2.setFalsePraise(p2.getFalsePraise() + 1);
			p2.setLastEditTime(new Date());
			praiseService.update(p2);
			content.setState(0);
		}
		return obj2json(content);
	}

	/**
	 * 图集详情页 评论分页
	 * 
	 * @param currentPage
	 * @param mutiCode
	 * @return
	 */
	@RequestMapping("getAjaxCommentPager")
	public @ResponseBody
	String getAjaxCommentPager(String currentPage, String mutiCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Pager pager = new Pager();
		pager.setCurrentPage(Integer.parseInt(currentPage));
		pager.setPageSize(8);
		pager = postService.getMutiPost(pager, mutiCode);
		resultMap.put("data", pager);
		//System.out.println("请求完毕");
		return new Gson().toJson(pager);
	}

	@RequestMapping("prebanner")
	public String preBanner(String[] url, String[] content,
			String[] contentTitle, ModelMap model) {
		List bannerList = new ArrayList();
		int i = 0;
		for (String string : contentTitle) {
			Map<String, Object> map = new HashMap<String, Object>();
			MutiImage muti = mutiService.getMutiImageByCode(contentTitle[i]);
			map.put("muti", muti);
			map.put("imgUrl", url[i]);

			int x = checkCode(contentTitle[i]);
			if (x == 0) {
				return "error";
			} else if (x == 1) {
				map.put("hyperlink", "discover/picture/"
						+ contentTitle[i]+".html");
			} else if (x == 2) {
				map.put("hyperlink", "discover/video/"
						+ contentTitle[i]+".html");
			}
			i++;
			bannerList.add(map);
		}
		// 把bannerlist保存到model并传入首页
		model.put("banner", bannerList);
		// ************************图说西藏显示**********************************
		loadImageTibet(model);
		// *************************最热查看************************************
		loadHotImages(model);
		// **************************视频接口***********************************
		// 最热视频
		loadHotVideo(model);
		// 视频列表
		List<Content> videoList = loadDispalyVideo();
		model.put("videoList", videoList);
		model.addAttribute("programNam", 3);
		// ******************************************************************
		return "portal/app/discover/index";
	}

	private int checkCode(String code) {
		if (StringUtils.isNotBlank(code) && code.length() > 3) {
			String precode = code.substring(0, 3);
			if (!"MUT".equals(precode) && !"VID".equals(precode)) {
				return 0;
			} else {
				if ("MUT".equals(precode)) {
					return 1;
				} else {
					return 2;
				}
			}
		} else {
			return 0;
		}
	}

	@RequestMapping("predistext")
	public String preDisText(String[] url, String[] content,
			String[] contentTitle, ModelMap model) {
		loadBanner(model);
		// ************************图说西藏显示**********************************
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
		List<Content> showConList = contentService
				.findContentByProgrmaCode("345287ab-742a-33b2-b6ce-349202605b2a");
		int i = 0;
		for (String c : content) {
			// url 保存为连接 content保存为编号
			Map<String, Object> map = new HashMap<String, Object>();
			MutiImage muti = mutiService.getMutiImageByCode(c);
			List<Image> imglist = new ArrayList<Image>();
			if (muti != null) {
				imglist = imageService.getImageByMutiCode(muti.getCode());
			}
			String imgUrl = null;
			if (imglist.size() > 0) {
				imgUrl = imglist.get(0).getUrl();
			}
			int x = checkCode(content[i]);
			if (x == 0) {
				return "error";
			} else if (x == 1) {
				map.put("hyperlink", "discover/picture/"
						+ content[i]+".html");
			} else if (x == 2) {
				map.put("hyperlink", "discover/video/"
						+ content[i]+".html");
			}
			;
			// 把查出的字段放入showMap里
			map.put("muti", muti);
			map.put("imgUrl", imgUrl);
			showList.add(map);
			i++;
		}
		model.put("show", showList);
		// *************************最热查看************************************
		loadHotImages(model);
		// **************************视频接口***********************************
		// 最热视频
		loadHotVideo(model);
		// 视频列表
		List<Content> videoList = loadDispalyVideo();
		model.put("videoList", videoList);
		// ******************************************************************
		return "portal/app/discover/index";
	}

	@RequestMapping("predisvideo")
	public String preDisText(String[] links, String[] codes, ModelMap model) {
		loadBanner(model);
		// ************************图说西藏显示**********************************
		loadImageTibet(model);
		// *************************最热查看************************************
		loadHotImages(model);
		// **************************视频接口***********************************
		// 最热视频
		loadHotVideo(model);
		// 视频列表
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		List<Content> videoList = new ArrayList<Content>();
		try {
			if (infoLinkManage.progLinks(links, codes)) {
				for (InfoLink infoLink : infoLinkManage.getInfoLinks()) {
					videoList
							.add(contentService.findByCode(infoLink.getCode()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("videoList", videoList);
		return "portal/app/discover/index";
	}

	@RequestMapping("")
	@ResponseBody
	public String search(Pager pager, MutiImage mutiImage, String orderField) {
		mutiImage.setProgramaCode("");
		pager = mutiService.search(pager, mutiImage, orderField);

		return obj2json(pager);
	}

}
