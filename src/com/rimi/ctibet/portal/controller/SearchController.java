package com.rimi.ctibet.portal.controller;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.WebSearch;

@Controller("searchController")
public class SearchController extends BaseController {
	@RequestMapping(value="search",produces = "text/html; charset=utf-8")
	public ModelAndView search(Pager pager, WebSearch webSearch) {
		ModelAndView view = new ModelAndView();
		if (StringUtils.isNotBlank(webSearch.getKeywords())) {
			LuceneUtil2 luceneUtil = new LuceneUtil2();
			pager = luceneUtil.search(pager, webSearch);
			view.addObject(pager);
		}
		view.addObject("keywords", webSearch.getKeywords());
		return view;
	}
	
	@RequestMapping(value="portal/app/index/index-search")
	public ModelAndView indexSearch()
	{
		return new ModelAndView();
	}

}
