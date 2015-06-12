package com.rimi.ctibet.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

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


/**
 * 审核通过的回复
 * @author dengxh
 *
 */
@Controller
@RequestMapping("web/passReplyController")
public class PassReplyController extends BaseController{

	@Resource
	private IPostService postService;
	@Resource
	private IPlateService plateService;
	
	/**
	 * 发布回复（待定）
	 * @param request
	 * @param response
	 * @param post
	 * @return
	 */
	@RequestMapping("savePost")
	public @ResponseBody String savePost(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Content post){
		String ProgramaCode = request.getParameter("programaCode");
		post.setCode(Uuid.uuid());
		post.setProgramaCode("e152eee3-ddaf-4f7d-8141-060c121f5995");
		post.setAvaliable(1); 
		post.setContentType(Content.CONTENTTYPE_REPLY);
		post.setState(0);
		post.setCreateTime(new Date());
		post.setCreateuserCode("1");
		postService.savePost(post,null);
		return "success";
	}
	
	/**
	 * 删除回复
	 * @param request
	 * @param response
	 * @return
	 */
	
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
	
	@RequestMapping("passcheckedPostList")
	public ModelAndView UncheckedPostList(Pager pager,HttpServletRequest request,HttpServletResponse response){
		//System.out.println("PostController.UncheckedPostList()");
		ModelAndView view = new ModelAndView("dxhpostTest");
		view.addObject("list", postService.UncheckedPostList(pager,1,Content.CONTENTTYPE_POST));
		Pager list= postService.UncheckedPostList(pager,1,Content.CONTENTTYPE_POST);
		/*for (Map<String, Object> map : list) {
			//System.out.println(map.get("contenttitle"));
			//System.out.println(map.get("name"));
			//System.out.println("===================================================");
		}
		*/
		return view;
	}

	/**
	 * 查询回复
	 * @param pager
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("searchPost")
	public ModelAndView searchPost(Pager pager,HttpServletRequest request,HttpServletResponse response){
		
		List<Programa> programaList = plateService.getTopPlate();
		ModelAndView view = new ModelAndView("dxhreplyTest");
//		int state = Integer.valueOf(request.getParameter("state"));
		int state = 1;
		String contentType = Content.CONTENTTYPE_REPLY;
		String programaCode = request.getParameter("proCode");
		//System.out.println("-----------------------------------------------"+programaCode);
		String keyword = request.getParameter("keyword");
		Pager result = postService.searchPost(state, contentType, programaCode, keyword,pager);
		
//		for (Map<String, Object> map : result) {
//			//System.out.println(map.get("contenttitle"));
//			//System.out.println(map.get("name"));
//			//System.out.println("===================================================");
//		}
//		
		view.addObject("programsList", programaList);
		view.addObject("result", result);
		return view;
	}
}

