package com.rimi.ctibet.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.web.service.IContentService;

/**
 * 系统设置--运营管理--其他页面
 * 
 * @author chengsl
 */
@Controller("sysSettingOtherPageController")
@RequestMapping("/manage/html/settings")
public class OtherPageController extends BaseController {

	private static final String REGEX = ",";
	
	@Resource
	private IContentService contentService;
	/**
	 * 管理页
	 */
	@RequestMapping(value = "otherWeb")
	public ModelAndView otherWeb() {
		ModelAndView view = new ModelAndView();
		return view;
	}
	/**
	 * 异步获取管理页数据
	 * @param page
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value = "otherlist", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String otherList(Pager page, String keyword) {
		Content content = new Content();
		content.setContentTitle(keyword);
		content.setContentType("4000");
		contentService.search(page, content, "100");
		return obj2json(page);
	}
	/**
	 * 增加 表单页
	 * @return
	 */
	@RequestMapping(value = "otherWeb-add")
	public ModelAndView otherWebaddFrm() {
		ModelAndView view = new ModelAndView();
		return view;
	}
	/**
	 * 增加
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "otherWebadd")
	public ModelAndView otherWebadd(Content content) {
		ModelAndView view = new ModelAndView();
		content.setCode(CodeFactory.createOtherCode());
		content.setContentType(content.CONTENTTYPE_OTHER);
		content.setCreateuserCode(getManagerUser().getCode());
		content.setCreateTime(new Date());
		contentService.save(content);
		view.setViewName(redirect("otherWeb"));
		return view;
	}
	/**
	 *修改 表单页
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "otherWeb-edit")
	public ModelAndView otherWebEditFrm(String code) {
		ModelAndView view = new ModelAndView();
		Content content = contentService.findByCode(code);
		view.addObject("content", content);
		return view;
	}
	/**
	 * 修改
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "otherWebedit")
	public ModelAndView otherWebEdit(Content content) {
		ModelAndView view = new ModelAndView();
		content.setLastEditTime(new Date());
		content.setChangeuserCode(getManagerUser().getCode());
		contentService.updateLogical(content);
		view.setViewName(redirect("otherWeb"));
		return view;
	}
	/**
	 *  删除，支持批量删除
	 * @param codes
	 * @return 列表页
	 */
	@RequestMapping(value="otherdeletes")
	public ModelAndView otherDelete(String codes)
	{
		ModelAndView view = new ModelAndView();
		try {
			drop(codes);
			view.setViewName(redirect("otherWeb"));
		} catch (Exception e) {
			view.setViewName("other");
		}
		return view;
	}
	
	
	private void drop(String idsStr) {
		idsStr += REGEX;
		String ids[] = idsStr.split(REGEX);
		List<String> codes = new ArrayList<String>();
		for (String string : ids) {
			if (!"".equals(string)) {
				codes.add(string);
			}
		}
		contentService.deleteByCodes(codes);
	}
}
