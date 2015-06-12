package com.rimi.ctibet.portal.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.web.controller.vo.Culture;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IPostService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IUserFavoriteService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.MutiImageService;

@Controller("picture")
@RequestMapping("portal/app/discover/")
public class SeeTibetPictureConntroller extends BaseController {
	private static final String BASEURL = "portal/app/discover/";
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
	@Resource
	private IProgramaService programaService;

	@RequestMapping("picture")
	public ModelAndView pictureDetail(String code) {
		ModelMap model = new ModelMap();
		ModelAndView view = new ModelAndView();
		MutiImage muti = mutiService.getMutiImageByCode(code);
		MutiImage premuti = null;
		MutiImage nextmuti = null;
		Map map = mutiService.preAndNextMuti(muti);
		List<Image> imgList = imageService.getImageByMutiCode(code);
		Praise praise = praiseService.getPraiseContentCode(code);
		praise.setFalseViewcount(praise.getFalseViewcount()+1);
		praise.setViewCount(praise.getViewCount()+1);
		praiseService.update(praise);
		view.addObject("muti", muti);
		imgList  = getListimage(imgList);
		view.addObject("imgList", imgList);
		view.addObject("praise", praise);
		view.addObject("premuti", map.get("pre"));
		view.addObject("nextmuti", map.get("next"));
		view.addObject("programNam", 3);
		view.addObject("isOpen", programaService.isOpenReply(Content.DETAIL_PICTURE_REPLY));
		return view;
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
	
	/**
	 * 提交评论
	 * @param mutiCode
	 */
	
	@RequestMapping(value = "commentpic", produces = "text/html; charset=utf-8")
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
			Praise praise=praiseService.getPraiseContentCode(contentcode);
			praise.setFalseReplyNum(praise.getFalseReplyNum()+1);
			praise.setReplyNum(praise.getReplyNum()+1);
			praiseService.update(praise);
			contentService.saveReply(comment, contentcode);
		}
		return obj2json(comment);
	}
	/**
	 * 查询评论
	 * @param code
	 * @param page
	 * @return 评论列表
	 */
	  //查询评论自己调用视频 模块的 
//	@RequestMapping(value = "comments", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String comments(String code, Pager page) {
		if (page == null) {
			page = new Pager();
			page.setPageSize(8);
		}
		page = contentService.getReplyByPostCodeState(page, code, null, 1);
		return obj2json(page);
	}
	

}
