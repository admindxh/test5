package com.rimi.ctibet.portal.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.cache.CacheOperation;
import com.rimi.ctibet.domain.AdArea;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.UserFavorite;
import com.rimi.ctibet.web.controller.vo.Culture;
import com.rimi.ctibet.web.controller.vo.FatherToChild;
import com.rimi.ctibet.web.controller.vo.InfoLink;
import com.rimi.ctibet.web.controller.vo.InfoLinkManage;
import com.rimi.ctibet.web.service.IAdAreaService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IUserFavoriteService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.MutiImageService;

/**
 * 
 * 门户读西藏
 * 
 * @author chengsl
 */
@Controller
public class ReadTibetCultureController extends BaseController {
	public static final String TYPE_NAME_POS = "posid";// 推荐位
	public static final String TYPE_NAME_CUL = "cultural";// 文化传播
	public static final String TYPE_NAME_DYN = "dynamicList";// 动态
	public static final String TYPE_NAME_IDY = "infoDisplay";// 信息
	public static final String TYPE_NAME_PRE = "pre";// 预览临时存放！
	public static Map size=new HashedMap();
	static{
		size.put(TYPE_NAME_POS, 4);
		size.put(TYPE_NAME_CUL,18 );
		size.put(TYPE_NAME_DYN, 7);
		size.put(TYPE_NAME_IDY, 37);
		size.put(TYPE_NAME_PRE, 18);
	}
	//
	private static String baseUrl = "portal/app/";
	//
	private static final String BANNERCODE = "61d19785-7e8c-12e4-b6ce-005056a05bc9";
	@Resource
	private MutiImageService mutiImageService;
	@Resource
	private ImageService imageService;
	@Resource
	private IProgramaService programaService;
	@Resource
	private IContentService contentService;
	@Resource
	private IUserFavoriteService userFavoriteService;
	@Resource
	private IAdAreaService adAreaService;
	private Programa loadBanner() {
		// code
		return programaService.getProgramaByCode(BANNERCODE);
	};
	// 加载 栏目 栏目信息
	// 推荐位、动态、信息、文化传播
	private List<Content> loadContent(String type) {
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		infoLinkManage.readLinks(type);
		List<InfoLink> links = infoLinkManage.getInfoLinks();
		initLinks(links, (Integer)size.get(type));
		if(TYPE_NAME_CUL.equals(type)){
			return loadDataCul(links,type);
		}else
		{
			return loadData(links);
		}
	}
	
	private void initLinks(List<InfoLink> links, int x) {
		if(links.size()<x){
			int i=0;
			while(i<x){
				links.add(new InfoLink());
				i++;
			}
		}
	}
	private List<Content> loadDataCul(List<InfoLink> links,String type) {
		List<Content> contents = new ArrayList<Content>();
		int i=0;
		for (InfoLink infoLink : links) {
			Content content = contentService.findByCode(infoLink.getCode());
			try {
				if (1 == Integer.parseInt(content.getContentType()) / 1000) {
					content.setSummary("detail");
				} else {
					content.setSummary("culture_detail");
				}
			} catch (Exception e) {
			content =new Content();
			content.setContentTitle("暂无数据");
			content.setContent("暂无数据");
			content.setContetNotHtml("暂无数据");
			content.setAuthorCode("暂无数据");
			content.setUrl("portal/static/default/square.png");
			if(i<6)
			{	
				content.setTitle("portal/static/default/circle.png");
			}else if(i<6*2)
			{
				content.setTitle("portal/static/default/rec.png");
			}else if(i<6*3){
				content.setTitle("portal/static/default/square.png");
				}
			}
			contents.add(content);
			i++;
		}
		return contents;
	}
	
	
	
	private List<Content> loadData(List<InfoLink> links) {
		////System.out.println("links size" + links.size());
		List<Content> contents = new ArrayList<Content>();
		for (InfoLink infoLink : links) {
			Content content = contentService.findByCode(infoLink.getCode());
			try {
				if (1 == Integer.parseInt(content.getContentType()) / 1000) {
					content.setSummary("detail");
				} else {
					content.setSummary("culture_detail");
				}
			} catch (Exception e) {
			content =new Content();
			content.setContentTitle("暂无数据");
			content.setContent("暂无数据");
			content.setContetNotHtml("暂无数据");
			content.setUrl("portal/static/default/square.png");
			content.setTitle("portal/static/default/square.png");
			}
			contents.add(content);
		}
		return contents;
	}

	@RequestMapping("culture")
	public ModelAndView cultural(String path,HttpServletRequest request,HttpServletResponse response,ModelMap  modelMap) {
		ModelAndView modelAndView = (ModelAndView) CacheOperation.getInstance().getCacheData(this, "culturalHandler", new Object[]{path==null?"":path}, Constants.intervalTime, Constants.maxVisitCount);
		modelAndView.setViewName("portal//app//culture");
		return modelAndView;
	}
	
	public ModelAndView culturalHandler(String path){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("programNam", 2);
		List posids = loadContent(TYPE_NAME_POS);
		List infos = loadContent(TYPE_NAME_IDY);
		List dyns = loadContent(TYPE_NAME_DYN);
		List culs = loadContent(TYPE_NAME_CUL);
		modelAndView.addObject(TYPE_NAME_POS, posids);
		modelAndView.addObject(TYPE_NAME_IDY, infos);
		modelAndView.addObject(TYPE_NAME_DYN, dyns);
		modelAndView.addObject(TYPE_NAME_CUL, culs);
		modelAndView.addObject("path", path);
		modelAndView.setViewName(baseUrl + "culture");
		return modelAndView;
	}

	// 读西藏 首页 下面 音乐 小说，其他，游戏 数据
	@RequestMapping(value = "culture/culture")
	public ModelAndView culture(String type) {
		ModelAndView modelAndView = new ModelAndView();
		List culs = null;
		if (StringUtil.isNotNull(type)) {
			culs = loadContent(type);
		} else {
			culs = loadContent(TYPE_NAME_CUL);
		}
		if (culs.size() != 0) {
			addData(modelAndView, culs.subList(0, 6), "list1");
			addData(modelAndView, culs.subList(6, 12), "list2");
			addData(modelAndView, culs.subList(12, 18), "list3");
		}
		modelAndView.addObject("programNam", 2);
		return modelAndView;
	}

	private void addData(ModelAndView modelAndView, List ls, String rsName) {
		Pager page = new Pager();
		page.setDataList(ls);
		modelAndView.addObject(rsName, page);
	}

	@RequestMapping(value = "banner/culture_banner")
	public ModelAndView bannber(String path) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(baseUrl + "banner/culture_banner");
		Programa programa = loadBanner();
		if (StringUtil.isNotNull(path)) {
			programa.setImageUrl(path);
		}
		modelAndView.addObject("banner", programa);
		return modelAndView;
	}

	// 音乐，游戏，其他，小说列表页
	@RequestMapping(value = "culture/cultural")
	public ModelAndView cultural() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(baseUrl + "culture/cultural");
		modelAndView.addObject("programNam", 2);
		List<AdArea> ad=adAreaService.findbypro("78abc9785-123c-1s24-bab-0211056a05bcs");
		modelAndView.addObject("adList", ad);
		return modelAndView;
	}
	/**
	 * 同步加载数据 ，返回页面
	 * @param pager
	 * @param type
	 * @param keywords
	 * @param orderType
	 * @return
	 */
	@RequestMapping(value = "culture/list")
	public ModelAndView list(Pager pager, String type, String keywords,String orderType) {
		Map map = new HashedMap();
		map.put("1010", "节日 FESTIVA");
		map.put("1020", "历史 HISTORY ");
		map.put("1030", "风俗 CUSTOMS");
		map.put("1040", "宗教 RELIGION");
		map.put("1050", "美食 FOOD");
		map.put("1060", "动植物 ANIMALS AND PLANTS");
		map.put("1070", "名人 CELEBRITY");
		map.put("1080", "服饰 DRESS");
		map.put("1090", "艺术 ART");
		map.put("1100", "特产 SPECIALTIES");
		map.put("1110", "地理 GEOGRAPHY");
		map.put("1120", "西藏动态 NEWS");
		Map tmap =new HashedMap();
		tmap.put("1010", "节日");
		tmap.put("1020", "历史");
		tmap.put("1030", "风俗");
		tmap.put("1040", "宗教");
		tmap.put("1050", "美食");
		tmap.put("1060", "动植物");
		tmap.put("1070", "名人");
		tmap.put("1080", "服饰");
		tmap.put("1090", "艺术");
		tmap.put("1100", "特产");
		tmap.put("1110", "地理");
		tmap.put("1120", "西藏动态");
		ModelAndView modelAndView = new ModelAndView();
		if (!StringUtil.isNotNull(type)||type.length()>5) {
			type = Culture.getTypeId(Culture.TYPE_DYNAMIC);
		}
		if (null == pager) {
			pager = new Pager();
		}
		if (null == orderType) {
			orderType = "100";
		}
		if(keywords!=null&&keywords.length()>15)
		{
			keywords="";
		}
		pager.setPageSize(8);
		Content content = new Content();
		content.setContentType(type);
		content.setContentTitle(keywords);
		content.setContent(keywords);
		contentService.searchOnOthers(pager, content, orderType);
		if(pager.getDataList()!=null&&pager.getDataList().size()>0){
			for(Content c:(List<Content>)pager.getDataList()){
				Pager page=new Pager();
				page.setPageSize(1);
				page = contentService.getReplyByPostCodeState(page, c.getCode(), null, 1);
				c.getOthers().remove(Culture.FEILD_COMMENT);
				c.getOthers().put(Culture.FEILD_COMMENT, page.getTotalCount());
			}
		}
		modelAndView.setViewName(baseUrl + "culture/list");
		modelAndView.addObject(pager);
		modelAndView.addObject("type", type);
		modelAndView.addObject("typeName", map.get(type));
		modelAndView.addObject("ttypeName", tmap.get(type));
		modelAndView.addObject("orderType", orderType);
		modelAndView.addObject("keywords", keywords);
		modelAndView.addObject("programNam", 2);
		return modelAndView;
	}
	/**
	 *  异步加载 数据，返回json
	 * @param pager
	 * @param type
	 * @param keywords
	 * @param orderType
	 * @return
	 */
	@RequestMapping(value = "culture/list2", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String listJson(Pager pager, String type,String keywords, String orderType) {
		if (!StringUtil.isNotNull(type)) {
			type = Culture.getTypeId(Culture.TYPE_DYNAMIC);
		}
		if (null == pager) {
			pager = new Pager();
			pager.setPageSize(8);
		}
		if (null == orderType) {
			orderType = "100";
		}
		Content content = new Content();
		content.setContentType(type);
		content.setContent(keywords);
		content.setContentTitle(keywords);
		contentService.searchOnOthers(pager, content, orderType);
		if(Culture.getMTypeId(Culture.TYPE_M_MUSIC).equals(content.getContentType()))
		{
			for (Content c : (List<Content>)pager.getDataList()) {
					c.setState(checkSessionOperate2(session, c.getCode())?1:0);
					LogUser user = getFrontUser();
				if (user != null) {
					c.setSortNum(userFavoriteService.isFavAlready(
							user.getCode(), c.getCode(), UserFavorite.User_Fav_Read)?1:0);
				}else{
					c.setSortNum(0);
				}
			}
		}
		for ( Content c : (List<Content>)pager.getDataList()) {
            computeScore(c);
        }
		return obj2json(pager);
	}
	
	public void computeScore(Content content){
	    String sc = (String) content.getOthers().get(Culture.FEILD_RL_SCORE);
        if(StringUtil.isNotNull(sc) && sc.indexOf(",")!=-1){
            try {
                String[] array = sc.split(",");
                float total_sc = new Float(array[0]);
                float total_people = new Float(array[1]);
                String format = new DecimalFormat("0.0").format(total_sc /total_people);
                
                int first = Integer.valueOf(format.split("\\.")[0]);
                int secend = Integer.valueOf(format.split("\\.")[1]);
                if(secend==0){
                }else if(secend<5){
                    secend = 5;
                }else if(secend>5){
                    secend = 0;
                    first += 1;
                }
                
                content.getOthers().put(Culture.FEILD_SCORE, first + "." + secend);
            } catch (NumberFormatException e) {
                content.getOthers().put(Culture.FEILD_SCORE, "5.0");
                e.printStackTrace();
            }
        }else{
            content.getOthers().put(Culture.FEILD_SCORE, "5.0");
        }
	}
	
	@RequestMapping(value = "culture/detail")
	public ModelAndView detail(String code) {
		ModelAndView modelAndView = new ModelAndView();
		if (StringUtils.isNotBlank(code)) {
			Content content = contentService.findByCode(code);
			Culture culture = new Culture();
			if (content==null) {
			 	modelAndView.setViewName(baseUrl+"/404");
			 	return modelAndView;
			}
			modelAndView.addObject("content", culture);
			addView(code, content);
			FatherToChild.fatherToChild(content, culture);
			modelAndView.setViewName(baseUrl+"culture/detail");
		} else {
			modelAndView.setViewName("error");
		}
		modelAndView.addObject("programNam", 2);
		modelAndView.addObject("isOpen", programaService.isOpenReply(Content.DETAIL_READ_TIBET_REPLY));
		return modelAndView;
	}

	@RequestMapping(value = "culture/preview")
	public ModelAndView previewinfo(Content content,String type,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Culture culture = new Culture();
		otherFeilds(content,request);
		FatherToChild.fatherToChild(content, culture);
		modelAndView.addObject("content", culture);
		if ("info".equals(type)) {
			modelAndView.setViewName(baseUrl + "culture/detail");
		} else {
			modelAndView.setViewName(baseUrl + "culture/culture_detail");
		}
		modelAndView.addObject("programNam", 2);
		return modelAndView;
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
	
	@RequestMapping(value = "culture/culture_detail")
	public ModelAndView cultureDetail(String code) {
		ModelAndView modelAndView = new ModelAndView();
		if (StringUtils.isNotBlank(code)) {
			Content content = contentService.findByCode(code);
			Culture culture = new Culture();

			addView(code, content);
			
			computeScore(content);
            FatherToChild.fatherToChild(content, culture);
			modelAndView.addObject("content", culture);
			modelAndView.setViewName(baseUrl+"culture/culture_detail");
		} else {
			modelAndView.setViewName("error");
		}
		modelAndView.addObject("programNam", 2);
		modelAndView.addObject("isOpen", programaService.isOpenReply(Content.DETAIL_READ_TIBET_CULTURE_REPLY));
		return modelAndView;
	}

	private void addView(String code, Content content) {
		String name = code + "view";
//		if (checkSessionOperate(session, name)) {
//
//		} else {
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
	//	}
	}

	// 查询评论
	@RequestMapping(value = "culture/comments", produces = "text/html; charset=utf-8")
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
	@RequestMapping(value = "culture/comment", produces = "text/html; charset=utf-8")
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

	// 预览文化 动态 推荐位
	@RequestMapping("previewculture")
	public ModelAndView preview(String t, String[] links, String codes[]) {

		ModelAndView modelAndView = new ModelAndView();
		List posids = loadContent(TYPE_NAME_POS);
		List infos = loadContent(TYPE_NAME_IDY);
		List dyns = loadContent(TYPE_NAME_DYN);
		modelAndView.addObject(TYPE_NAME_POS, posids);
		modelAndView.addObject(TYPE_NAME_IDY, infos);
		modelAndView.addObject(TYPE_NAME_DYN, dyns);
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		infoLinkManage.progLinks(links, codes);
		modelAndView.addObject(t, loadData(infoLinkManage.getInfoLinks()));
		modelAndView.setViewName(baseUrl + "culture");
		modelAndView.addObject("programNam", 2);
		return modelAndView;
	}

	// 预览文化传播
	@RequestMapping("precultrue")
	public ModelAndView previewculture(String t, String links, String codes) {
		ModelAndView modelAndView = new ModelAndView();
		List posids = loadContent(TYPE_NAME_POS);
		List infos = loadContent(TYPE_NAME_IDY);
		List dyns = loadContent(TYPE_NAME_DYN);
		modelAndView.addObject(TYPE_NAME_POS, posids);
		modelAndView.addObject(TYPE_NAME_IDY, infos);
		modelAndView.addObject(TYPE_NAME_DYN, dyns);
		modelAndView.addObject("type", "pre");//文化传播
		InfoLinkManage infoLinkManage = new InfoLinkManage();
		infoLinkManage.progLinks(links, codes);
		infoLinkManage.saveLinks("pre");//预览文化传播信息 临时保存起来！
		//modelAndView.addObject(t, loadData(infoLinkManage.getInfoLinks()));
		modelAndView.addObject("programNam", 2);
		modelAndView.setViewName(baseUrl + "culture");
		return modelAndView;
	}
	@RequestMapping(value = "culture/checkpraise", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String checkPraise(String code) {
		String name = code;// 用户名
		Content content = new Content();
		if (checkSessionOperate2(session, name)) {
			content.setState(4);
		}
		return obj2json(content);
	}
	// 点赞 ajax
	@RequestMapping(value = "culture/praise", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String praise(String code) {
		//
		String name = code;// 用户名
		if (getFrontUser() != null) {
		//	name += getFrontUser().getCode();
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
	
	@RequestMapping(value = "culture/checkfav", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String checkFav(String code) {
		LogUser user = getFrontUser();
		Content content = new Content();
		content.setCode(code);
		if (null == user) {
			content.setState(3);// 没有登陆
		} else {
			if (userFavoriteService.isFavAlready(user.getCode(), code,
					UserFavorite.User_Fav_Read)) {
				content.setState(4);//
			}
		}
		return obj2json(content);
	}
	
	
	// 收藏 ajax
	@RequestMapping(value = "culture/favorite", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String favorite(String code) {
		LogUser user = getFrontUser();
		Content content = new Content();
		content.setCode(code);
		if (null == user) {
			content.setState(3);// 没有登陆
		} else {
			if (userFavoriteService.isFavAlready(user.getCode(), code,
					UserFavorite.User_Fav_Read)) {
				content.setState(4);// 已经收藏
			} else {// 收藏
				UserFavorite userFavorite = new UserFavorite();
				userFavorite.setMemberCode(user.getCode());
				userFavorite.setType(UserFavorite.User_Fav_Read);
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

	private String checkNull2zero(String pr) {
		if (!StringUtil.isNotNull(pr) || pr.equals("null")) {
			pr = "0";
		}
		return pr;
	}

	/**
	 * 查找相关信息，热门信息等，根据 传入参数决定返回值
	 * 
	 * @param page
	 * @param content
	 * @param orderField
	 * @return 内容数组
	 */
	@RequestMapping(value = "culture/others", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String loadOthers(Pager page, Content content, String orderType) {
		if (page.getPageSize() > 11) {
			return obj2json(page);
		}
		contentService.searchOnOthers(page, content, orderType);
		return obj2json(page);
	}
	/**
	 * 查询不包含 特定类型 的信息
	 */
	@RequestMapping(value = "culture/othercontent", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String loadOthersContent(Pager page, Content content, String orderType) {
		if (page.getPageSize() > 18) {
			return obj2json(page);
		}
		contentService.findOtherContent(page, content, content.getContentType(), orderType);
		return obj2json(page);
	}
	
	// 评分 ajax
	@RequestMapping(value = "culture/grade", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String gradeMusic(String code,String score) {
		//
		String name = code+"grade";// 用户名
		if (getFrontUser() != null) {
		//	name += getFrontUser().getCode();
		}
		Content content = new Content();
		if (checkSessionOperate(session, name)) {
			content.setState(4);
			content.setCode(code);
		} else {
			Map map = contentService.findOthers(code);
			String rlps = (String) map.get(Culture.FEILD_RL_SCORE);
			String ps=(String)map.get(Culture.FEILD_SCORE);
			try {
				rlps = checkNull2zero(rlps);
				ps=checkNull2zero(ps);
				map.clear();
				map.put(Culture.FEILD_RL_SCORE, grade(rlps, score));
				map.put(Culture.FEILD_SCORE,grade(ps,(String)map.get(Culture.FEILD_RL_SCORE),score));
				contentService.updateOthers(code, map);
				content.setState(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj2json(content);
	}
	
	private String grade(String ps, String rlps, String score) {
			try {
				int countPeople=Integer.valueOf(rlps.split(",")[1]);
				double countScore=(Double.valueOf(ps)*(countPeople-1)+Double.parseDouble(score));
				double rsScore=countScore/countPeople;
				if(rsScore>10){
					rsScore=10;
				}
				score=new DecimalFormat().format(rsScore);
				score=score.substring(0,Math.min(score.length(), 3));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return score;
	}

/**打分：总分加入分数，总人数加1
 * （分数格式 /d,/d） 逗号前是总分数,后面是总人数
 * @param oldScore 之前的分数 ,
 * @param score  当前给的分数 如 9，
 * @return 新的分数 （分数格式 /d,/d）
 */
	private String grade(String oldScore,String score)
	{ 
		try {
			if(StringUtils.isBlank(oldScore)||oldScore.indexOf(",")==-1)
			{
				oldScore=score+","+"1";
			}
			String countScore=oldScore.split(",")[0];
			String countPeople=oldScore.split(",")[1];
			double ncountScore = Double.parseDouble(countScore)+Double.parseDouble(score);
			int ncountPeople = Integer.parseInt(countPeople)+1;
			oldScore=ncountScore+","+ncountPeople;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oldScore;
	}
	
}
