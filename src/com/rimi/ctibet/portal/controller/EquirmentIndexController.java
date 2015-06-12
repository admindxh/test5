package com.rimi.ctibet.portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Equipment;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.portal.controller.vo.EquireMentVo;
import com.rimi.ctibet.portal.controller.vo.PostVo;
import com.rimi.ctibet.web.service.IAdAreaService;
import com.rimi.ctibet.web.service.IEquimentService;
import com.rimi.ctibet.web.service.IPostService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.ImageService;

@RequestMapping("ride/eqindex")
@Controller
public class EquirmentIndexController  extends BaseController{
    
	
	private String parentType = "superequipmenttype";// 装备类型的父类code 固定值
	
	@Autowired
	private IEquimentService equimentService;
	
	@Autowired
	private ImageService imageService;
	
	
	@Autowired
	private IPostService postService;
	
	@Autowired
	private IProgramaService programaService;
	
	@Autowired
	private IAdAreaService areaService;
	
	
	@ModelAttribute()
    public void initMethod(Model model){
        model.addAttribute("programNam","4");
    }
	
	
	/**
	 * 所有类型装备
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,ModelMap modelMap,String type,Pager pager){
		//查询banner 图片
		List<Image> bannerImageList = new ArrayList<Image>();
		bannerImageList = imageService.getImageByMutiCode("74ffb15c-0192-41ed-91f5-9ac29a651ee4");
		if (bannerImageList!=null) {
			for (Image image : bannerImageList) {
				Equipment eq = equimentService.findByCode(image.getName());
				if (eq!=null) {
					image.setName(eq.getUrl());   
				}
			}
		}
		//发布时间
		List<PostVo> createTime = postService.getPostByPrograma(Content.RIDE_ZB, Content.ORDER_CREATETIME_DESC, 4);
		//最新回复
		List<PostVo> replyTime = postService.getPostByPrograma(Content.RIDE_ZB, Content.ORDER_REPLYNUM_DESC, 4);
		//被赞最多
		List<PostVo> praiseTime = postService.getPostByPrograma(Content.RIDE_ZB, Content.ORDER_PRAISE_DESC, 4);
		modelMap.put("blist", bannerImageList);
		modelMap.put("createTime", createTime);
		modelMap.put("replyTime", replyTime);
		modelMap.put("praiseTime", praiseTime);
		//查询所有类型的装备
		Programa parentPrograma = new Programa();
		parentPrograma.setCode(parentType);
		pager.setPageSize(5);
		Pager   pagerEq = programaService.findByParent(pager, parentPrograma, "");
		List<Programa> list = pagerEq.getDataList();
		modelMap.put("list", list);//装备类型banner
		List<EquireMentVo> equireMentVos = new ArrayList<EquireMentVo>();
		if (list!=null) {
			 for (Programa programa : list) {
				//封装装备 vo
				 Pager p =  new Pager();
				 p.setPageSize(6);
				 Pager equiList = equimentService.getEquiList("", programa.getCode(), p);
				 EquireMentVo equireMentVo = new EquireMentVo();
				 equireMentVo.setPrograma(programa);
				 equireMentVo.setEqlist(equiList.getDataList());
				 equireMentVos.add(equireMentVo);
			}
		}
		modelMap.put("equireMentVos", equireMentVos);
		//查询广告位
		List<Map<String, Object>>  arealist = areaService.getAdAreaByProCode("e43gh3hy32-75e6-11e4-b6ce-005a56a05bc9");
		modelMap.put("arealist", arealist);
		return  "portal/app/riding-area/rec_equiment";
	}
	
	/**
	 * 更多
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("more")
	public String more(HttpServletRequest request,ModelMap modelMap,String type,Pager pager){
		//查询banner 图片
		modelMap.put("type", type);	
		Programa programa = programaService.getProgramaByCode(type);
		if (programa!=null) {
			modelMap.put("p", programa);
		}
		List<Image> bannerImageList = new ArrayList<Image>();
		bannerImageList = imageService.getImageByMutiCode("74ffb15c-0192-41ed-91f5-9ac29a651ee4");
		if (bannerImageList!=null) {
			for (Image image : bannerImageList) {
				Equipment eq = equimentService.findByCode(image.getName());
				if (eq!=null) {
					image.setName(eq.getUrl());   
				}
			}
		}
		//发布时间
		List<PostVo> createTime = postService.getPostByPrograma(Content.RIDE_ZB, Content.ORDER_CREATETIME_DESC, 4);
		//最新回复
		List<PostVo> replyTime = postService.getPostByPrograma(Content.RIDE_ZB, Content.ORDER_REPLYNUM_DESC, 4);
		//被赞最多
		List<PostVo> praiseTime = postService.getPostByPrograma(Content.RIDE_ZB, Content.ORDER_PRAISE_DESC, 4);
		modelMap.put("blist", bannerImageList);
		modelMap.put("createTime", createTime);
		modelMap.put("replyTime", replyTime);
		modelMap.put("praiseTime", praiseTime);
		
		//查询广告位
		List<Map<String, Object>>  arealist = areaService.getAdAreaByProCode("e43gh3hy32-75e6-11e4-b6ce-005a56a05bc9");
		modelMap.put("arealist", arealist);
		
		
		//查询所有类型的装备
		Programa parentPrograma = new Programa();
		parentPrograma.setCode(parentType);
		pager.setPageSize(5);
		Pager   pagerEq = programaService.findByParent(pager, parentPrograma, "");
		List<Programa> list = pagerEq.getDataList();
		modelMap.put("list", list);//装备类型banner
		
		return  "portal/app/riding-area/rec_equimentmore";
	}
	
	
	/**
	 * 详情 
	 */
	@RequestMapping("detail")
	public String detail(Model model, String code){
	    Equipment equipment = equimentService.findByCode(code);
	    if(equipment!=null){
	        Programa programa = programaService.getProgramaByCode(equipment.getProType());
	        model.addAttribute("pro", programa);
	        model.addAttribute("equipment", equipment);
	    }
		return "portal/app/ridezone/goods_detail";
	}
	/**
	 * 跳转到创建订单
	 */
	@RequestMapping("create-order")
	public String createOrder(Model model, String code, int num){
	    Equipment equipment = equimentService.findByCode(code);
	    model.addAttribute("equipment", equipment);
	    model.addAttribute("num", num);
	    return "portal/app/ridezone/goods_buy";
	}
	/**
	 * 更新装备数量
     */
    @RequestMapping(value="updateEquipmentNum", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String updateEquipmentNum(String code, int num){
        try {
            Equipment equipment = equimentService.findByCode(code);
            if(equipment!=null){
                equipment.setCount(equipment.getCount()-num);
            }
            equimentService.update(equipment);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
	
	
	/**
	 * 分页 装备类型
	 * @param pager
	 * @param typ
	 * @return
	 */
	@RequestMapping(value="getEqList", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getEqList(Pager pager,String type){
		Pager pager2 = equimentService.getEquiList("", type, pager);
		return new Gson().toJson(pager2);
	}
	
	
}
