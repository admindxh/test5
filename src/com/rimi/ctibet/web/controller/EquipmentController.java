package com.rimi.ctibet.web.controller;

import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.service.IProgramaService;

@Controller("equipmentManage")
@RequestMapping("/manage/html/")
public class EquipmentController extends BaseController {
	@Resource
	private IProgramaService programaService;
	private String parentType = "superequipmenttype";// 装备类型的父类code 固定值
	public static Logger LOG = Logger.getLogger(EquimentController.class);
	private String baseUrl = "/manage/html/";

	// ajax提交添加信息
	@RequestMapping(value = "cycling/addetype", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public String createEquipmentType(String name) {
		Content content = new Content();
		if (StringUtils.isNotBlank(name)) {
			Programa programa = new Programa();
			programa.setCode(CodeFactory.createEquipmentTypeCode());
			programa.setCreateTime(new Date());
			programa.setProgramaName(name);
			programa.setpCode(parentType);
			programaService.savePrograma(programa);
			content.setState(0);
		} else {
			content.setState(1);
			content.setContent("错误，类型名字为空！");
		}
		return obj2json(content);
	}
	
	/**
	 * 验证装备类别是否重复<br>
	 * 添加于2015/4/14，验证输入装备类别是否重复
	 * 
	 * @param name 装备类别名称
	 * @return 验证结果
	 * @author tangHQ
	 * 
	 */
	@RequestMapping(value = "cycling/valid", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public String checkRepeat (Programa programa) {
		JSONObject res = new JSONObject();
		programa.setpCode(parentType);
		res.put("result", programaService.validateNameIsRepeat(programa));
		return res.toString();
	}
	
	
	// ajax提交添加信息
	@RequestMapping(value = "cycling/updateetype", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public String updateetype(String name,String code) {
		Programa programa = programaService.getProgramaByCode(code);
		if (programa!=null) {
			 programa.setProgramaName(name);
		}
		programaService.updatePrograma(programa);
		Content content = new Content();
		if (!StringUtils.isNotBlank(name)) {
			content.setState(1);
			content.setContent("错误，类型名字为空！");
			
		}
		return obj2json(content);
	}

	@RequestMapping(value = "cycling/deleteetype", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public String deleteEquipmentType(String code) {
		Content content = new Content();
		if (StringUtils.isNotBlank(code)) {
			Programa programa = new Programa();
			try {
				programa.setCode(code);
				programaService.delete(programa);
				content.setState(0);
			} catch (Exception e) {
				LOG.error(e.toString());
				content.setState(1);
				content.setContent("错误,删除失败！");
			}
		} else {
			content.setState(2);
			content.setContent("错误，类型号为空！");
		}
		return obj2json(content);
	}

	@RequestMapping("cycling/types")
	public ModelAndView types() {
		ModelAndView modelAndView = new ModelAndView();
		return modelAndView;
	}

	// 分类检索
	@RequestMapping(value = "cycling/infosearch", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String search(Pager pager, String keyword, String orderType, String contentType) {
		Programa parentPrograma = new Programa();
		parentPrograma.setCode(parentType);
		// 未排序
//		pager = programaService.findByParent(pager, parentPrograma, keyword);
		// id排序
		programaService.findByOrder(pager, parentPrograma);
		return obj2json(pager);
	}
}
