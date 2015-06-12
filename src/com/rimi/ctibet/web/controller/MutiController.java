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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.exception.JsonParseException;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.JsonUtil;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.common.util.WebSearch;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.web.controller.vo.InfoLink;
import com.rimi.ctibet.web.controller.vo.InfoLinkManage;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.MutiImageService;

/**
 * 图说西藏管理controller
 * 
 * @author xiangwq
 * 
 */
@Controller
@RequestMapping("web/mutiController")
public class MutiController extends BaseController {

	@Resource
	private MutiImageService mutiService;
	@Resource
	private ImageService imageService;
	@Resource
	private IContentService contentService;
	@Resource
	private IPraiseService praiseService;

	/**
	 * 获取图集并分页
	 * 
	 * @param pager
	 * @param model
	 * @return
	 */
	@RequestMapping("getMutiList")
	public String getMutiList(Pager pager, String keywords, String action,
			ModelMap model) {
		if (StringUtils.isBlank(action)) {
			action = "1";
		}
		Map map = new HashMap();
		map.put("1", "m.createTime desc");
		map.put("2", "falseViewcount desc");
		map.put("3", "falseFavoriteNum desc");
		map.put("4", "falseReplyNum desc");
		map.put("5", "falseFavoriteNum desc");
		String programaCode = "14dba551-cb5b-4631-b5ef-b3838670b3a9";
		MutiImage mutiImage = new MutiImage();
		mutiImage.setProgramaCode(programaCode);
		mutiImage.setCode(keywords);
		mutiImage.setName(keywords);
		pager = mutiService.search(pager, mutiImage, (String) map.get(action));
		model.put("pager", pager);
		model.put("keywords", keywords);
		model.put("action", action);
		
		return "manage/html/see/descriptive-infomessage";
	}

	/**
	 * 添加或者编辑图集
	 */
	@RequestMapping("mutiEdit")
	public String mutiEdit(ModelMap model, String code) {
		MutiImage muti = null;
		muti = mutiService.getMutiImageByCode(code);
		if (StringUtils.isNotBlank(code)) {
			List<Image> imageList = imageService.getImageByMutiCode(code);
			Praise praise = praiseService.getPraiseContentCode(code);
			model.put("muti", muti);
			imageList = getListimage(imageList);
			model.put("imageList", imageList);
			model.put("praise", praise);
		}
		return "manage/html/see/descriptive-edit";
	}
	
	/**
	 * 去掉重复的数据
	 * @param list
	 * @return
	 */
	public List<Image> getListimage(List<Image>  list){
		if(list!=null)
		{
			for (int i = 0; i < list.size(); i++)  //外循环是循环的次数
	        {
	            for (int j = list.size() - 1 ; j > i; j--)  //内循环是 外循环一次比较的次数
	            {
	
	                if (list.get(i).getUrl()!=null&&list.get(j).getUrl()!=null&&list.get(i).getUrl().equals(list.get(j).getUrl()))
	                {
	                	list.remove(j);
	                }
	
	            }
	        }
		}
		return list;
	}
	
	
	@RequestMapping("descriptive-add")
	public ModelAndView addfrm(ModelMap model) {
		ModelAndView view = new ModelAndView();
		view.setViewName("manage/html/see/descriptive-add");
		MutiImage muti;
		muti = new MutiImage();
		muti.setCode(CodeFactory.createMutiCode());
		view.addObject("muti", muti);
		return view;
	}

	/**
	 * 删除图集
	 * 
	 * @return
	 */
	@RequestMapping("deleteMuti")
	public ModelAndView deleteMuti(HttpServletRequest request,
			HttpServletResponse response, String paramJson) {
		// 参数MAP
		Map<String, String> maplist;
		// 返回接口MAP 初始化code 为0失败 1成功
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "0");

		if (paramJson == null) {
			resultMap.put("data", "参数错误！");
			return jsonView(resultMap);
		}
		try {
			maplist = JsonUtil.json2Map(paramJson);
		} catch (JsonParseException e) {
			e.printStackTrace();
			resultMap.put("data", "参数错误！");
			return jsonView(resultMap);
		}
		// 从页面获取的要删除的id
		String code = maplist.get("code");
		if (StringUtils.isBlank(code)) {
			resultMap.put("data", "参数错误！");
			return jsonView(resultMap);
		}
		try {
			String[] codes = code.split(",");
			mutiService.deleteSelect(codes);//图集，评论在下满删除
			deleteSearch(codes);//删除搜索所有库
			resultMap.put("msg", "1");
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			resultMap.put("data", e.getMessage());
		}
		return jsonView(resultMap);
	}

	private void deleteSearch(String[] codes) {
		List<WebSearch> webSearchs = new ArrayList<WebSearch>();
		for (int i = 0; i < codes.length; i++) {
			String string = codes[i];
			WebSearch webSearch = new WebSearch();
			webSearch.setCode(string);
			LuceneUtil2.delete(webSearch);
		}
	}

	private void updateSearch(MutiImage muti, Image image) {
		WebSearch webSearch = new WebSearch();
		webSearch.setCode(muti.getCode());
		webSearch.setTitle(muti.getName());
		webSearch.setContent(muti.getSummary());
		webSearch.setUrl(muti.getHyperlink());
		webSearch.setDate(muti.getCreateTime().getTime());
		webSearch.setImageUrl(image.getUrl());
		LuceneUtil2.update(webSearch);
	}

	private void addSearch(MutiImage muti, Image image) {
		WebSearch webSearch = new WebSearch();
		webSearch.setCode(muti.getCode());
		webSearch.setTitle(muti.getName());
		webSearch.setContent(muti.getSummary());
		webSearch.setUrl(muti.getHyperlink());
		webSearch.setImageUrl(image.getUrl());
		LuceneUtil2.add(webSearch);
	}

	public void saveImg(String summary, String url, String mutiCode) {
		Image img = new Image();
		img.setSummary(summary);
		img.setUrl(url);
		img.setMutiCode(mutiCode);
		img.setCreateUserCode(getManagerUser().getCode());
		imageService.saveImage(img);
	}

	/**
	 * 保存图集
	 * 
	 * @return
	 */
	@RequestMapping("saveMuti")
	public @ResponseBody
	String saveMuti(MutiImage muti, String[] url, String[] sum) {
		muti.setCreateUserCode(getManagerUser().getCode());
		muti.setCreateTime(new Timestamp(new Date().getTime()));
		Image image = new Image();
		if(url!=null)
		{
			image.setUrl(url[0]);
		}
		int i = 0;
		for (String string : sum) {
			saveImg(sum[i], url[i], muti.getCode());
			i++;
		}
		muti.setHyperlink("discover/picture/"
				+ muti.getCode()+".html");
		Praise p = new Praise();
		p.setAvaliable(1);
		p.setCode(Uuid.uuid());
		p.setContentCode(muti.getCode()); // praise的contentcode保存为muticode
		p.setCreateTime(new Timestamp(new Date().getTime()));
		p = setNum(p);
		praiseService.save(p);
		mutiService.saveMuti(muti);
		addSearch(muti, image);
		return "success";
	}

	public Praise setNum(Praise p) {
		p.setFalseFavoriteNum(0);
		p.setFalsePraise(0);
		p.setFalseReplyNum(0);
		p.setFalseViewcount(0);
		p.setFavoriteNum(0);
		return p;
	}

	@RequestMapping("updateMuti")
	public @ResponseBody
	String updateMuti(MutiImage muti, String[] url, String[] sum, Praise praise) {
		muti.setCreateUserCode(getManagerUser().getCode());
		muti.setCreateTime(new Timestamp(DateUtil.strToDate(muti.getDetail(),
				DateUtil.TIMESTAMP_PATTERN_2).getTime()));
		muti.setHyperlink("discover/picture/"
				+ muti.getCode()+".html");
		Praise rlPraise = praiseService.getPraiseContentCode(muti.getCode());
		
		Integer viewCountTrue = rlPraise.getViewCount();
		Integer praiseTrue = rlPraise.getTruePraise();
		Integer favoriteNumTrue = rlPraise.getFavoriteNum();
		
		Integer viewCountFake = praise.getFalseViewcount();
		Integer praiseFake = praise.getFalsePraise();
		Integer favoriteNumFake = praise.getFalseFavoriteNum();
		
		if(viewCountTrue == viewCountFake){
			rlPraise.setFalseViewcount(null);
			rlPraise.setViewCount(viewCountTrue);
		}else {
			rlPraise.setFalseViewcount(viewCountFake);
		}
		
		if(praiseTrue == praiseFake){
			rlPraise.setFalsePraise(null);
			rlPraise.setTruePraise(praiseTrue);
		}else {
			rlPraise.setFalsePraise(praiseFake);
		}
		
		if(favoriteNumTrue == favoriteNumFake){
			rlPraise.setFalseFavoriteNum(null);
			rlPraise.setFavoriteNum(favoriteNumTrue);
		}else {
			rlPraise.setFalseFavoriteNum(favoriteNumFake);
		}
		praiseService.update(rlPraise);
		mutiService.deleteSelect(new String[] { muti.getCode() });
		int i = 0;
		Image image = new Image();
		if (url != null) {
			image.setUrl(url[0]);
		}
		for (String string : sum) {
			saveImg(sum[i], url[i], muti.getCode());
			i++;
		}
		mutiService.saveMuti(muti);
		updateSearch(muti, image);
		return "success";
	}

	/**
	 * 通过url获取图片
	 */
	@RequestMapping("getImgByUrl")
	public @ResponseBody
	ModelAndView getImgByUrl(String url) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Image> imgList = imageService.findImageByUrl(url);
		Image img = null;
		if (imgList.size() > 0) {
			img = imgList.get(0);
		}
		resultMap.put("data", img);
		return jsonView(resultMap);
	}

	/**
	 * 跳转至看西藏banner管理
	 */
	@RequestMapping("banner")
	public String banner(ModelMap model) {
		// 栏目号为78abc9785-123c-1224-bab-0211056a05bc9 的content表中放入
		List<Content> clist = contentService
				.findContentByProgrmaCode("78abc9785-123c-1224-bab-0211056a05bc9");
		model.put("programaCode", "78abc9785-123c-1224-bab-0211056a05bc9");
		model.put("contentList", clist);
		return "manage/html/see/banner";
	}

	/**
	 * 保存从前台管理页面过来的banner
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping("saveContent")
	public @ResponseBody
	String saveContent(String[] url, String[] contentTitle, String programaCode) {
		List<Content> clist = new ArrayList<Content>();
		for (int i = 0; i < 4; i++) {
			Content c = new Content();
			// url 为图片路径
			// content保存为连接
			// contentTitle保存为编号
			int x = checkCode(contentTitle[i]);
			if (x == 0) {
				return "error";
			} else if (x == 1) {
				c.setContent("discover/picture/"
						+ contentTitle[i]+".html");
			} else if (x == 2) {
				c.setContent("discover/video/"
						+ contentTitle[i]+".html");
			}
			c.setProgramaCode(programaCode);
			c.setUrl(url[i]);
			c.setContentTitle(contentTitle[i]);
			c.setContentType("图说西藏banner");
			clist.add(c);
		}
		contentService.updateSeeBanner(clist, programaCode);
		return "success";
	}

	private int checkCode(String code) {
		if (StringUtils.isNotBlank(code) || code.length() > 3) {
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

	/**
	 * 跳转到图说西藏显示管理
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("showMuti")
	public String showMuti(ModelMap model) {
		List<Content> clist = contentService
				.findContentByProgrmaCode("345287ab-742a-33b2-b6ce-349202605b2a");
		model.put("programaCode", "345287ab-742a-33b2-b6ce-349202605b2a");
		model.put("contentList", clist);
		return "manage/html/see/descriptive-text";
	}

	/**
	 * 保存图说西藏显示
	 * 
	 * @param url
	 * @param content
	 * @param programaCode
	 * @return
	 */
	@RequestMapping("saveShowMuti")
	public @ResponseBody
	String saveShowMuti(String[] url, String[] content, String programaCode) {
		List<Content> clist = new ArrayList<Content>();
		int i= 0;
		for (String s:content) {
			Content c = new Content();
			// url 保存为连接 
			//content保存为编号
			c.setProgramaCode(programaCode);
			c.setUrl(url[i]);
			c.setContent(s);
			c.setContentType("图说西藏显示");
			clist.add(c);
			i++;
		}
		contentService.updateSeeBanner(clist, programaCode);
		return "success";
	}

	/**
	 * 显示图说西藏最新
	 * 
	 * @return
	 */
	@RequestMapping("shownew")
	public @ResponseBody
	ModelAndView shownew(String programaCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<MutiImage> mutiList = mutiService
				.getMutiImageByProgramaCode(programaCode);
		resultMap.put("data", mutiList);
		return jsonView(resultMap);
	}

	/**
	 * 显示图说西藏最热
	 * 
	 * @return
	 */
	@RequestMapping("showhot")
	public @ResponseBody
	ModelAndView showhot(String programaCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<MutiImage> mutiList = mutiService
				.getMutiImageByProgramaCode(programaCode);
		List<Praise> plist = new ArrayList<Praise>();
		for (MutiImage muti : mutiList) {
			plist.add(praiseService.getPraiseContentCode(muti.getCode()));
		}
		// 获得排序后的praiseList
		plist = praiseService.orderByViewCount(plist);
		// 取前三个出来,并查询出mutiimage
		List<MutiImage> mlist = new ArrayList<MutiImage>();
		if (plist.size() >= 3) {
			for (int i = 0; i < 3; i++) {
				MutiImage muti = mutiService.getMutiImageByCode(plist.get(i)
						.getContentCode());
				mlist.add(muti);
			}
		} else {
			resultMap.put("msg", "1"); // 数据量不足
		}
		// 最终返回排序好的mutilist ,最大到小
		resultMap.put("data", mlist);
		return jsonView(resultMap);
	}

	/**
	 * 获得真实数据
	 * 
	 * @param mutiCode
	 * @param action
	 * @return
	 */
	@RequestMapping("getrealcount")
	public @ResponseBody
	String getrealcount(String mutiCode, String action) {
		Praise p = praiseService.getPraiseContentCode(mutiCode);
		// action 1 查看 2收藏 3点赞
		if ("1".equals(action)) {
			return String.valueOf(p.getViewCount());
		}
		if ("2".equals(action)) {
			return String.valueOf(p.getFavoriteNum());
		} else {
			return String.valueOf(p.getTruePraise());
		}
	}

	/**
	 * 预览banner
	 * 
	 * @return
	 */
	@RequestMapping("previewContent")
	public String previewContent(String[] url, ModelMap model) {
		// *******************************banner *****************************
		List<Map<String, Object>> bannerList = new ArrayList<Map<String, Object>>();
		List<Content> bannerConList = contentService
				.findContentByProgrmaCode("78abc9785-123c-1224-bab-0211056a05bc9");
		for (int i = 0; i < bannerConList.size(); i++) {
			// content的url 保存为图片路径 content保存为连接 contentTitle保存为编号
			Map<String, Object> map = new HashMap<String, Object>();
			MutiImage muti = mutiService.getMutiImageByCode(bannerConList
					.get(i).getContentTitle());
			Praise praise = null;
			// String imgUrl = bannerConList.get(i).getUrl();
			String hyperlink = bannerConList.get(i).getContent();
			if (muti != null) {
				praise = praiseService.getPraiseContentCode(muti.getCode());
			}
			// 把查出的字段放入bannaerMap里
			map.put("muti", muti);
			map.put("imgUrl", url[i]);
			map.put("hyperlink", hyperlink);
			map.put("praise", praise);
			// 把bannerMap放入bannerList里
			bannerList.add(map);
		}
		// 把bannerlist保存到model并传入首页
		model.put("banner", bannerList);
		// ************************图说西藏显示************************************
		List<Map<String, Object>> showList = new ArrayList<Map<String, Object>>();
		List<Content> showConList = contentService
				.findContentByProgrmaCode("345287ab-742a-33b2-b6ce-349202605b2a");
		for (Content c : showConList) {
			// url 保存为连接 content保存为编号
			Map<String, Object> map = new HashMap<String, Object>();
			MutiImage muti = mutiService.getMutiImageByCode(c.getContent());
			List<Image> imglist = new ArrayList<Image>();
			Praise praise = null;
			// 把找到图集里第一张图片当做封面
			if (muti != null) {
				imglist = imageService.getImageByMutiCode(muti.getCode());
				praise = praiseService.getPraiseContentCode(muti.getCode());
			}
			String imgUrl = null;
			if (imglist.size() > 0) {
				imgUrl = imglist.get(0).getUrl();
			}
			String hyperlink = c.getUrl();
			// 把查出的字段放入showMap里
			map.put("praise", praise);
			map.put("muti", muti);
			map.put("imgUrl", imgUrl);
			map.put("hyperlink", hyperlink);
			// 把bannerMap放入bannerList里
			showList.add(map);
		}
		model.put("show", showList);
		// *************************最热查看*************************************
		List<MutiImage> mutiList = mutiService
				.getMutiImageByProgramaCode("14dba551-cb5b-4631-b5ef-b3838670b3a9");
		List<Praise> plist = new ArrayList<Praise>();
		for (MutiImage muti : mutiList) {
			plist.add(praiseService.getPraiseContentCode(muti.getCode()));
		}
		// 获得排序后的praiseList
		plist = praiseService.orderByViewCount(plist);
		// 取前4个出来,并查询出mutiimage
		List<MutiImage> mlist = new ArrayList<MutiImage>();
		if (plist.size() >= 4) {
			for (int i = 0; i < 4; i++) {
				MutiImage muti = mutiService.getMutiImageByCode(plist.get(i)
						.getContentCode());
				mlist.add(muti);
			}
		}
		// 最终返回排序好的mutilist ,从大到小
		model.put("hotMutiList", mlist);
		model.put("hotPraiseList", plist);
		// **************************视频接口***********************************
		// 最热视频
		Content videoContent = new Content();
		Pager videoPager = new Pager();
		videoContent.setContentType("3000");
		videoPager = contentService.searchOnOthers(videoPager, videoContent,
				"203");
		videoPager.setPageSize(4);
		model.put("hotVideoList", videoPager.getDataList());
		// 视频列表
		List<Content> videoList = loadDispalyVideo();
		model.put("videoList", videoList);
		model.addAttribute("programNam", 3);
		// ******************************************************************
		return "portal/app/discover/index";
	}

	private List<Content> loadDispalyVideo() {
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		infoLinkManage.readLinks("video-infos");
		List<InfoLink> infoLinks = infoLinkManage.getInfoLinks();
		List<Content> contents = new ArrayList<Content>();
		for (InfoLink infoLink : infoLinks) {
			contents.add(contentService.findByCode(infoLink.getCode()));
		}
		return contents;
	}

	private void loadpreBanner(ModelMap model) {
		List<Map<String, Object>> bannerList = new ArrayList<Map<String, Object>>();
		List<Content> bannerConList = contentService
				.findContentByProgrmaCode("78abc9785-123c-1224-bab-0211056a05bc9");
		for (int i = 0; i < bannerConList.size(); i++) {
			// content的url 保存为图片路径 content保存为连接 contentTitle保存为编号
			Map<String, Object> map = new HashMap<String, Object>();
			MutiImage muti = mutiService.getMutiImageByCode(bannerConList
					.get(i).getContentTitle());
			Praise praise = null;
			String imgUrl = bannerConList.get(i).getUrl();
			String hyperlink = bannerConList.get(i).getContent();
			if (muti != null) {
				praise = praiseService.getPraiseContentCode(muti.getCode());
			}
			// 把查出的字段放入bannaerMap里
			map.put("muti", muti);
			map.put("imgUrl", imgUrl);
			map.put("hyperlink", hyperlink);
			map.put("praise", praise);
			// 把bannerMap放入bannerList里
			bannerList.add(map);
		}
		// 把bannerlist保存到model并传入首页
		model.put("banner", bannerList);
	}
	@RequestMapping(value = "default", produces = "text/html; charset=utf-8")
	public String loadDefault(String type)
	{
		if(StringUtils.isBlank(type))
		{
			type="1";
		}
		Map map = new HashMap();
		map.put("1", "m.createTime desc");
		map.put("2", "viewcount desc");
		map.put("3", "favoriteNum desc");
		map.put("4", "replyNum desc");
		map.put("5", "favoriteNum desc");
		Pager pager = new Pager();
		pager.setPageSize(4);
		pager=mutiService.search(pager, new MutiImage(), (String)map.get(type));
		return obj2json(pager);
	}

}
