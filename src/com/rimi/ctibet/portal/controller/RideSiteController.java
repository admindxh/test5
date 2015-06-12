package com.rimi.ctibet.portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.RecommonedSite;
import com.rimi.ctibet.domain.RideLine;
import com.rimi.ctibet.domain.ServiceSite;
import com.rimi.ctibet.portal.controller.vo.RideLineVo;
import com.rimi.ctibet.web.service.IAdAreaService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMerchantService;
import com.rimi.ctibet.web.service.IRecommonedSiteService;
import com.rimi.ctibet.web.service.IRideLineService;
import com.rimi.ctibet.web.service.IserviceSiteService;

@Controller
@RequestMapping("ride/line")
public class RideSiteController  extends BaseController{
	
	@Autowired
	private IRideLineService rideLineService;
	
	@Autowired
	private IserviceSiteService siteService;
	
	@Autowired
	private IRecommonedSiteService recommonedSiteService;
	
	@Autowired
	private IContentService contentService;
	
	@Autowired
	private IMerchantService merchantService;
	
	@Autowired
	private IAdAreaService areaService;
	
	@RequestMapping("list")
	public  String list(Pager pager,HttpServletRequest request,String code,ModelMap model){
		model.put("programNam", 4);
		//获取第一条数据线路
		//获取第一条数据线路
		pager.setPageSize(3);
		Pager findAll = rideLineService.searchRideLineByName(pager, "");
		List<RideLine> line = findAll.getDataList();
		model.put("line", line);
		if (line!=null&&line.size()>=1) {
			if (!StringUtils.isNotBlank(code)) {
				 code  = line.get(0).getCode();
			}
		}
		//查询线路信息
		if (StringUtils.isNotBlank(code)) {
			RideLine rideLine = rideLineService.findByCode(code);
			model.put("rideLine", rideLine);
		}
		//查询站点信息
		List<RideLineVo> rlist = new  ArrayList<RideLineVo>();//站点详情
		  List<ServiceSite> serviceSite = siteService.findByPropertyOrder("ridelineId", code,"orderSite","asc");
		 
			if (serviceSite!=null) {
				for (ServiceSite serviceSite2 : serviceSite) {
                       RideLineVo lineVo = new RideLineVo();
                       //站点信息
                       lineVo.setServiceSite(serviceSite2);
                          List<RecommonedSite> rslist = recommonedSiteService.findByProperty("sitecode", serviceSite2.getCode());
                          //宿
	      		         List<Merchant> merchantShu  = new ArrayList<Merchant>();
	      		         //食
	      		         List<Merchant> merchantShi  = new ArrayList<Merchant>();
	      		         //攻略
	      		         List<Content> content  = new ArrayList<Content>();
	      		         for (RecommonedSite rs : rslist) {
	      					 if (rs.getContentType().equals("merchantShu")) {
	      						 //merchantShu.add(rs.getConentCode());
	      						 Merchant merchant  = merchantService.getMerchantByCode(rs.getConentCode());
	      						 if (merchant!=null) {
									  merchantShu.add(merchant);
								}
	      					 }else if(rs.getContentType().equals("merchantShi")){
	      						Merchant merchant  = merchantService.getMerchantByCode(rs.getConentCode());
	      						 if (merchant!=null) {
									  merchantShi.add(merchant);
								}
	      					}else{
	      						Content c  = contentService.findByCode(rs.getConentCode());
	      						content.add(c);
	      					}
	      				 }
	      		       lineVo.setContent(content);
	      		       lineVo.setMerchantShi(merchantShi);
	      		       lineVo.setMerchantShu(merchantShu);
	      		       rlist.add(lineVo);
				}
			}
			model.put("rlist", rlist);
			List<Map<String, Object>>  arealist = areaService.getAdAreaByProCode("e43gh3h732-75d6-11e4-b6ce-005a56a05bc9");
		    model.put("arealist", arealist);
			return "portal/app/riding-area/star_service";
	}
	
	
}
