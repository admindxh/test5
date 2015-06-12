package com.rimi.ctibet.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.AdArea;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.service.IAdAreaService;
import com.rimi.ctibet.web.service.IFrontContentStatisService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.ImageService;

@Controller
@RequestMapping("web/adArea")
public class AdAreaController {

	@Resource
	private IAdAreaService adAreaService;
	@Resource
	private IProgramaService programaService;
	@Resource
	private ImageService imgService;
	@Resource
	private IFrontContentStatisService contentStatisService;

	@RequestMapping("adArealist")
	public ModelAndView adArealist(Pager pager, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView("manage/html/settings/AdArealist");
		List<Programa> ps = programaService.getSendPrograma("ggwlx");
		for (Programa p : ps) {
			if (p.getProgramaName().contains("天上社区首页")) {
				p.setTypeCode("bb");
			} else if (p.getProgramaName().contains("天上社区论坛版块页")) {
				p.setTypeCode("bbs");
			} else if (p.getProgramaName().contains("游西藏")) {
				p.setTypeCode("tour");
			} else if (p.getProgramaName().contains("商户首页")) {
				p.setTypeCode("merc");
			} else if (p.getProgramaName().contains("商户列表页")) {
				p.setTypeCode("merlist");
			} else if (p.getProgramaName().contains("西藏文化传播")) {
				p.setTypeCode("cult");
			}
		}
		return view.addObject("ps", ps);
	}

	@RequestMapping("gotoSaveAdArea")
	public ModelAndView gotoSaveAdArea(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView("");
		return view;
	}

	@RequestMapping("saveAdArea")
	public ModelAndView saveAdArea(HttpServletRequest request,
			HttpServletResponse response, AdArea adArea) {
		ModelAndView view = new ModelAndView();
		return view;
	}

	@RequestMapping("gotoUpdateAdArea")
	public ModelAndView gotoUpdateAdArea(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView("manage/html/settings/AdAreaEdit");
		String proCode = request.getParameter("code");
		List<Map<String, Object>> ads = adAreaService
				.getAdAreaByProCode(proCode);
		return view.addObject("ads", ads).addObject("proCode", proCode);
	}

	@RequestMapping("updateAdArea")
	public ModelAndView updateAdArea(HttpServletRequest request,
			HttpServletResponse response, AdArea ad) {
		ModelAndView view = new ModelAndView("redirect:/web/adArea/adArealist");
		String proCode = request.getParameter("proCode");
		String codes = request.getParameter("codes");//需要删除的
		String[] codeArray = codes.split(",");
		String sql = "delete from fronotcontentstatis where code = ";
		if(codeArray!=null&&codeArray.length>0)
		for (int i = 0; i < codeArray.length; i++) {
			sql += "'" + codeArray[i] + "'";
			contentStatisService.deleteBySql(sql);
			adAreaService.deleteByProperty("code", codeArray[i]);
		}

		String[] codes2 = request.getParameterValues("codes2");//没删除的
		String[] urls = request.getParameterValues("urls");
		String[] imgUrls = request.getParameterValues("imgUrls");
			for (int i = 0; i < imgUrls.length; i++) {
				if(codes2!=null&&codes2.length>i){
					AdArea adv =  adAreaService.findByCode(codes2[i]);
					if (!urls[i].startsWith("http://")) {
						urls[i] = "http://" + urls[i];
					}
					adv.setImgUrl(imgUrls[i]);
					adv.setUrl(urls[i]);
					adAreaService.updateAdArea(adv);
				}else{
					AdArea adv = new AdArea();
					adv.setCode(CodeFactory.create("Adv"));
					adv.setType(proCode);
					if (!urls[i].startsWith("http://")) {
						urls[i] = "http://" + urls[i];
					}
					adv.setImgUrl(imgUrls[i]);
					adv.setUrl(urls[i]);
					adAreaService.saveAdArea(adv);
				}
			}
		List<Map<String, Object>> ads = adAreaService
				.getAdAreaByProCode(proCode);
		view.addObject("ads", ads).addObject("proCode", proCode);

		return view;
	}

	@RequestMapping(value = "deleteAdArea", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String deleteAdArea(HttpServletRequest request,
			HttpServletResponse response) throws JSONException {
		// ModelAndView view = new
		// ModelAndView("redirect:/web/adArea/adArealist");
		String proCode = request.getParameter("proCode");
		String codes = request.getParameter("codes");
		String[] codeArray = codes.split(",");
		JSONObject obj = new JSONObject();
		try {
			adAreaService.deleteByProperty("type", proCode);
			String sql = "delete from  fronotcontentstatis where code = ";
			for (int i = 0; i < codeArray.length; i++) {
				sql += "'" + codeArray[i] + "'";
				contentStatisService.deleteBySql(sql);
			}
			obj.put("data", 1);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			obj.put("data", -1);
		}
		return obj.toString();
	}

	/**
	 * 根据模块CODE查询广告
	 * 
	 * @param proCode
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getAdverByPro", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String getAdverByPro(String proCode) throws JSONException {
		JSONObject obj = new JSONObject();
		if (StringUtils.isNotBlank(proCode)) {
			List<AdArea> list = adAreaService.findbypro(proCode);
			List li = new ArrayList();
			for (AdArea a : list) {
				Map map = new HashMap();
				map.put("code", a.getCode());
				map.put("createTime", a.getCreateTime());
				map.put("id", a.getId());
				map.put("imgUrl", a.getImgUrl());
				map.put("type", a.getType());
				map.put("url", a.getUrl());
				map.put("avaliable", a.getAvaliable());
				li.add(map);
			}
			obj.put("data1", li);
		}
		return obj.toString();
	}
}
