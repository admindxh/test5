package com.rimi.ctibet.web.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CastTimeStamp2DateInResultHelper;
import com.rimi.ctibet.common.util.FileHelper;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.service.IProgramaService;

/**
 * 栏目管理相关接口
 * 
 * @author xiaozhen
 * @date 2014--11
 * @package com.rimi.medical.web.programa.controller
 * @copyright 成都睿峰科技有限公司
 * @version V1.0
 */

@Controller
@RequestMapping("/manage/html")
public class ProgramaController extends BaseController {

	@Resource
	private IProgramaService programaService;

	/**
	 * 新增或修改栏目
	 * 
	 * @param request
	 * @param response
	 * @param programa
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveOrUpdatePrograma")
	public @ResponseBody
	String saveOrUpdateProgramam(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute Programa programa)
			throws Exception {
		// 获取上传图片集合
		List<MultipartFile> programaImgs = FileHelper.getFileSet(request,
				1024 * 1024 * 20, null, "programaImgs");
		String message = "";
		String path = request.getSession().getServletContext().getRealPath("/")
				+ "/WebRoot" + ReadSettingProperties.getValue("upload")
				+ "/img" + "/programaImg" + "/";
		if (StringUtils.isBlank(programa.getProgramaName())) {
			message = "必要参数为空";
			return message;
		}
		// =============================修改栏目============================
		if (StringUtils.isNotBlank(programa.getCode())) {
			Programa programa2 = programaService.getProgramaByCode(programa
					.getCode());
			programa.setId(programa2.getId());
			// 将老图片全部删除
			if (StringUtils.isNotBlank(programa2.getImageUrl())) {
				String[] oldImgs = programa2.getImageUrl().split(",");
				FileHelper.deleteFile(oldImgs, path);
			}
			// 将新图片全部保存
			programa.setImageUrl("");
			for (MultipartFile programaImg : programaImgs) {
				String imgPath = path + programa.getCode();
				FileHelper.uploadFile(programaImg, imgPath);
				programa.setImageUrl(programa.getImageUrl()
						+ programa.getCode() + "/"
						+ programaImg.getOriginalFilename() + ",");
			}
			programa.setLastEditTime(new Date());
			programaService.updatePrograma(programa);
			message = "修改成功";
			return message;
		}
		// ====================新增栏目========================
		String programaCode = Uuid.uuid();
		programa.setImageUrl("");
		// 判断是否存在文件夹
		String imgPath = path + programaCode;
		File file = new File(imgPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		for (MultipartFile programaImg : programaImgs) {
			FileHelper.uploadFile(programaImg, imgPath);
			programa.setImageUrl(programa.getImageUrl() + programaCode + "/"
					+ programaImg.getOriginalFilename() + ",");
		}
		programa.setCode(programaCode);
		if (StringUtils.isNotBlank(programa.getImageUrl()))
			programa.setCreateTime(new Date());
		programaService.savePrograma(programa);
		message = "保存成功";
		return message;
	}

	/**
	 * 新增
	 * 
	 */
	@RequestMapping("save")
	public String saveProgramam(HttpServletRequest request,
			HttpServletResponse response,  Programa programa)
			throws Exception {
		String programaCode = Uuid.uuid();
		programa.setCode(programaCode);
		programa.setCreateTime(new Date());
		programa.setLastEditTime(new Date());
		programa.setAvailable(1);
		//System.out.println(programa);
		// 判断是否存在文件夹
		programaService.savePrograma(programa);
		return "forward:programa";
	}
	/**
	 * 删除栏目
	
	 */
	@RequestMapping("delete")
	public 
	String delete(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute Programa programa) {
		String message = "";
		if (StringUtils.isBlank(programa.getCode())) {
			message = "必要参数为空";
			return message;
		}
		programaService.delete(programa);
		return "forward:programa";
	}

	/**
	 * 删除栏目
	 * 
	 * @param request
	 * @param response
	 * @param programa
	 * @return
	 */
	@RequestMapping("deletePrograma")
	public @ResponseBody
	String deletePrograma(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute Programa programa) {
		String message = "";
		String path = request.getSession().getServletContext().getRealPath("/")
				+ "/WebRoot" + ReadSettingProperties.getValue("upload")
				+ "/img" + "/programaImg" + "/";
		if (StringUtils.isBlank(programa.getCode())) {
			message = "必要参数为空";
			return message;
		}
		Programa programa2 = programaService.getProgramaByCode(programa
				.getCode());
		// 将老图片全部删除
		if (StringUtils.isNotBlank(programa2.getImageUrl())) {
			String[] oldImgs = programa2.getImageUrl().split(",");
			FileHelper.deleteFile(oldImgs, path);
		}
		programa2.setImageUrl("");
		programaService.deletePrograma(programa2);
		message = "删除成功";
		return message;
	}

	/**
	 * 按照栏目code获取栏目
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getProgramaByCode")
	public ModelAndView getProgramaByCode(HttpServletRequest request,
			HttpServletResponse response) {
		String programaCode = request.getParameter("programaCode");
		ModelAndView view = new ModelAndView("programa/programaManager");
		String message = "";
		if (StringUtils.isBlank(programaCode)) {
			message = "必要参数为空";
			view.addObject(message);
			return view;
		}
		Programa programa = programaService.getProgramaByCode(programaCode);
		view.addObject(programa);
		return view;
	}

	/**
	 * 按照rank获取栏目（降序排列）
	 * 
	 * @param pager
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getProgramaOrderByRank")
	public @ResponseBody
	Object getProgramaOrderByRank(Pager pager, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView("programa/programaManager");
		pager = programaService.getProgramaOrderByRank(pager);
		view.addObject(pager);
		return view;
	}

	/**
	 * 
	 * @param pager
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("programa")
	public ModelAndView getPrograma(Pager pager, HttpServletRequest request,
			HttpServletResponse response) {
		pager.setPageSize(1000);
		ModelAndView view = new ModelAndView("/test/programamanage");
		pager = programaService.getProgramaOrderByRank(pager);
		view.addObject(pager);
		return view;
	}

	/**
	 * 获取顶层的programa
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getTopPrograma")
	public @ResponseBody
	Map getTopPrograma(HttpServletRequest request, HttpServletResponse response) {
		List<Programa> topPrograma = programaService.getTopPrograma();
		Map<String, Object> topProgramaMap = new HashMap<String, Object>();
		topProgramaMap.put("topPrograma", topPrograma);
		return topProgramaMap;
	}

	/**
	 * 根据顶层programa code 获取子 programa
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getSendPrograma")
	public @ResponseBody
	Map getSendPrograma(HttpServletRequest request, HttpServletResponse response) {
		String pCode = request.getParameter("pCode");
		List<Programa> sendPrograma = programaService.getSendPrograma(pCode);
		Map<String, Object> sendProgramaMap = new HashMap<String, Object>();
		sendProgramaMap.put("sendPrograma", sendPrograma);
		return sendProgramaMap;
	}

	@RequestMapping("getContentByProgramaCode")
	public @ResponseBody
	Map getContentByProgramaCode(HttpServletRequest request,
			HttpServletResponse response) {
		String programaCode = request.getParameter("programaCode");
		if (StringUtils.isBlank(programaCode)) {
			return null;
		}
		Map<String, Object> contentMap = new HashMap<String, Object>();
		List<Map<String, Object>> contentList = programaService
				.getContentByProgramaCode(programaCode);
		contentMap.put("contentList",
				CastTimeStamp2DateInResultHelper.newResulr(contentList));
		return contentMap;
	}
}
