package com.rimi.ctibet.portal.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.service.IPlateService;
import com.rimi.ctibet.web.service.IPostService;

@Controller
@RequestMapping("portal/postController")
public class PostController extends BaseController{

	@Resource
	private IPostService postService;
	@Resource
	private IPlateService plateService;
	
	@RequestMapping("savePost")
	public @ResponseBody String savePost(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Content post){
		String ProgramaCode = request.getParameter("programaCode");
		post.setCode(Uuid.uuid());
		post.setProgramaCode("e152eee3-ddaf-4f7d-8141-060c121f5995");
		post.setAvaliable(1);
		post.setContentType(Content.CONTENTTYPE_POST);
		post.setState(0);
		post.setCreateTime(new Date());
		post.setCreateuserCode("1");
		postService.savePost(post,null);
		return "保存成功";
	}
	@RequestMapping("deletePost")
	public @ResponseBody String deletePost(HttpServletRequest request,HttpServletResponse response){
		//System.out.println("PostController.deletePost()");
		String code = request.getParameter("code");
		String[] codes = code.split(",");
		for(int i=0;i<codes.length;i++)
		{
			//System.out.println("--------------------------------------------------"+codes[i]);
			Content post = postService.getPostByCode(codes[i]);
			//System.out.println(post);
			postService.deletePost(post);
		}
		return "success;";
	}
	
	@RequestMapping("UncheckedPostList")
	public ModelAndView uncheckedPostList(Pager pager,HttpServletRequest request,HttpServletResponse response){
		//System.out.println("PostController.UncheckedPostList()");
		ModelAndView view = new ModelAndView("xzTest");
		view.addObject("list", postService.UncheckedPostList(pager,0,Content.CONTENTTYPE_POST));
		Pager pager1= postService.UncheckedPostList(pager,0,Content.CONTENTTYPE_POST);
		return view.addObject("pager",pager1);
	}
	@RequestMapping("searchPost")
	public ModelAndView searchPost(Pager pager,HttpServletRequest request,HttpServletResponse response){
		
		List<Programa> programaList = plateService.getTopPlate();
		ModelAndView view = new ModelAndView("xzTest");
		int state = 0;
		String contentType = Content.CONTENTTYPE_POST;
		String programaCode = request.getParameter("proCode");
		//System.out.println("-----------------------------------------------"+programaCode);
		String keyword = request.getParameter("keyword");
		Pager pager1 = postService.searchPost(state, contentType, programaCode, keyword,pager);
		view.addObject("programsList", programaList);
		view.addObject("Pager", pager1);
		return view;
	}
}

