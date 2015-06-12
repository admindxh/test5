package com.rimi.ctibet.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Equipment;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.RideLine;
import com.rimi.ctibet.portal.controller.vo.PostVo;
import com.rimi.ctibet.web.service.IEquimentService;
import com.rimi.ctibet.web.service.IPostService;
import com.rimi.ctibet.web.service.IRideLineService;
import com.rimi.ctibet.web.service.ImageService;

@RequestMapping("ride")
public class RideIndexController {
	
	@Autowired
	IPostService postService;
	@Autowired
	private ImageService imageService;

	@Autowired
	private IEquimentService equimentService;
	
	@Autowired
	private IRideLineService rideLineService;
	
	
	@RequestMapping("rideIndex")
	public String rideIndex(HttpServletRequest request, ModelMap modelMap) {
		//查询论坛信息
		List<PostVo> ridegg = postService.getPostByPrograma(
				Content.RIDE_GG, Content.ORDER_CREATETIME_DESC, 5);
		List<PostVo> ridegs = postService.getPostByPrograma(
				Content.RIDE_GS, Content.ORDER_CREATETIME_DESC, 5);
		List<PostVo> ridezj = postService.getPostByPrograma(
				Content.RIDE_ZJ, Content.ORDER_CREATETIME_DESC, 5);
		List<PostVo> ridezb = postService.getPostByPrograma(
				Content.RIDE_ZB, Content.ORDER_CREATETIME_DESC, 5);
		modelMap.put("ridegg", ridegg);
		modelMap.put("ridegs", ridegs);
		modelMap.put("ridezj", ridezj);
		modelMap.put("ridezb", ridezb);
		//查询banner 信息切换
		List<Image> list = imageService.getImageByMutiImageCode("74ffb15c-0192-41ed-91f5-9ac29a651ee4");
		for (Image image : list) {
			  //查询url
			 Equipment equipment = equimentService.findByCode(image.getName());
			 if (equipment!=null) {
				image.setHyperlink(equipment.getUrl());
			}
		}
		modelMap.put("blist", list);
		Pager pager  = new Pager();
		pager.setPageSize(3);
		Pager findAll = rideLineService.searchRideLineByName(pager, "");
		//查询线路
		 List<RideLine> dataList = findAll.getDataList();
		 if (dataList!=null&&dataList.size()>=1) {
			modelMap.put("lineType", dataList.get(0).getCode());
		 }
		 return "";
	}
}
