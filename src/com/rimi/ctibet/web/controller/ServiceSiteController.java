package com.rimi.ctibet.web.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.omg.IOP.CodecFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.BeansUtil;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.FileUtil;
import com.rimi.ctibet.common.util.ImageZipUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.Equipment;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.RecommonedSite;
import com.rimi.ctibet.domain.RideLine;
import com.rimi.ctibet.domain.ServiceSite;
import com.rimi.ctibet.web.service.IRecommonedSiteService;
import com.rimi.ctibet.web.service.IRideLineService;
import com.rimi.ctibet.web.service.IserviceSiteService;


@Controller
@RequestMapping("web/serviceSite")
public class ServiceSiteController extends BaseController  implements ServletContextAware{
	
	@Autowired
	private  IserviceSiteService siteService;
	
	ServletContext application;
	
	
	 final String tempPath = "/temp/";
	
	@Autowired
	private IRideLineService rideLineService;
	
	@Autowired
	private IRecommonedSiteService  recommonedSiteService;
	
	@RequestMapping("list")
	public  String  list(String siteName,String serviceName,String ridelineId,String proType,Pager pager,HttpServletRequest rq,ModelMap modelMap){
		Pager pagerObject  =  siteService.getPagerSite(siteName, serviceName, proType, pager);
		 String proTypeText = request.getParameter("proTypeText");
		 List<RideLine> rideList = rideLineService.findAll();
		 modelMap.put("rideList", rideList);
		if (StringUtils.isNotBlank(proTypeText )) {
		 }else{
			 proTypeText = "全部线路类型";
		 }
		modelMap.put("proTypeText", proTypeText);
		 modelMap.put("siteName", siteName);
		 modelMap.put("pager", pagerObject);
		 return   "manage/html/ride/servicesite";
	}
	
	/**
	 * 骑行装备添加页面
	 * @param pager
	 * @param rq
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("addUI")
	public String addUI(ModelMap modelMap,String eqIds){
		 List<RideLine> rideList = rideLineService.findAll();
		 modelMap.put("rideList", rideList);
		
		 
		return "manage/html/ride/servicesite-create";
	}
	
	
	/**
	 * 骑行装备添加页面
	 * @param pager
	 * @param rq
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("save")
	public String save(ServiceSite serviceSite,String [] urls,String[] types ,ModelMap modelMap,String eqIds,HttpServletRequest rq,Pager pager){
		
		serviceSite.setAvaliable(1);
		String code  = CodeFactory.create("zdxl");
		serviceSite.setCode(code);
		serviceSite.setCreateTime(new Date());
		if(StringUtil.isNotNull(serviceSite.getServiceImg())){
            String basePath = application.getRealPath("/");
            String newBannerPath = serviceSite.getServiceImg().replace(tempPath, "");
            FileUtil.moveFile(basePath + serviceSite.getServiceImg(), basePath+newBannerPath);
            serviceSite.setServiceImg(newBannerPath);
        }
		if(StringUtil.isNotNull(serviceSite.getSitImg())){
            String basePath = application.getRealPath("/");
            String newBannerPath = serviceSite.getSitImg().replace(tempPath, "");
            FileUtil.moveFile(basePath + serviceSite.getSitImg(), basePath+newBannerPath);
            serviceSite.setSitImg(newBannerPath);
        }
		siteService.save(serviceSite);
		
		
		for (int i = 0; i < urls.length; i++) {
			    String   codeContent= urls[i];
				if (StringUtils.isNotBlank(codeContent)) {
					  String type= types[i];
					  RecommonedSite recommonedSite =  new RecommonedSite();
					  recommonedSite.setAvaliable(1);
					  recommonedSite.setCode(CodeFactory.create("tjfw"));
					  recommonedSite.setCreatetime(new Date());
					  recommonedSite.setContentType(type);
					  recommonedSite.setSitecode(code);
					  recommonedSite.setConentCode(codeContent);
					  recommonedSiteService.save(recommonedSite);
				}
			 
		}
		
		return  list("", "", "", "", pager, rq, modelMap);
	}
	/**
	 * 骑行装备添加页面
	 * @param pager
	 * @param rq
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("updateUI")
	public String updateUI(ModelMap modelMap,String code){
		 List<RideLine> rideList = rideLineService.findAll();
		 modelMap.put("rideList", rideList);
		 if (StringUtils.isNotBlank(code)) {
			 ServiceSite serviceSte = siteService.findByCode(code);
				 if (serviceSte!=null&&StringUtils.isNotBlank(serviceSte.getRidelineId())) {
					RideLine ride = rideLineService.findByCode(serviceSte.getRidelineId());
					 if(ride!=null) {
						 modelMap.put("pname", ride.getName());
						 modelMap.put("pcode", ride.getCode());
					 }else{
						 modelMap.put("pname", "请选择线路类型");
					 }
				 }else{
					 modelMap.put("pname", "请选择装备类型");
				 }
				 modelMap.put("site", serviceSte);
		         List<RecommonedSite> rslist = recommonedSiteService.findByProperty("sitecode", serviceSte.getCode());
		         List<String> merchantShu  = new ArrayList<String>();
		         List<String> merchantShi  = new ArrayList<String>();
		         List<String> content  = new ArrayList<String>();
		         for (RecommonedSite rs : rslist) {
					 if (rs.getContentType().equals("merchantShu")) {
						 merchantShu.add(rs.getConentCode());
					}else if(rs.getContentType().equals("merchantShi")){
						merchantShi.add(rs.getConentCode());
						
					}else{
						content.add(rs.getConentCode());
					}
				 }
		         modelMap.put("merchantShi", merchantShi);
		         modelMap.put("merchantShu", merchantShu);
		         modelMap.put("content", content);
		         modelMap.put("rslist", rslist);
		 }
		 
		 
		return "manage/html/ride/servicesite-update";
	}
	
	/**
	 * 骑行装备修改页面
	 * @param pager
	 * @param rq
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("update")
	public String update(ServiceSite serviceSite,String [] urls,String[] types  ,ModelMap modelMap,String eqIds,HttpServletRequest rq,Pager pager){
		ServiceSite serviceSite1 = siteService.findByCode(serviceSite.getCode());
		//BeanUtils.copyProperties(equipment, equipment2);
		BeansUtil.copyPropertysWithoutNull(serviceSite1, serviceSite);
		if(StringUtil.isNotNull(serviceSite.getServiceImg())){
            String basePath = application.getRealPath("/");
            String newBannerPath = serviceSite.getServiceImg().replace(tempPath, "");
            FileUtil.moveFile(basePath + serviceSite.getServiceImg(), basePath+newBannerPath);
            serviceSite1.setServiceImg(newBannerPath);
        }
		if(StringUtil.isNotNull(serviceSite.getSitImg())){
            String basePath = application.getRealPath("/");
            String newBannerPath = serviceSite.getSitImg().replace(tempPath, "");
            FileUtil.moveFile(basePath + serviceSite.getSitImg(), basePath+newBannerPath);
            serviceSite1.setSitImg(newBannerPath);
        }
		siteService.update(serviceSite1);
		if (serviceSite1!=null) {
			recommonedSiteService.deleteBySql("delete  from  recommoned_site  where sitecode='"+serviceSite1.getCode()+"'");
		}
		for (int i = 0; i < urls.length; i++) {
		    String   codeContent= urls[i];
			if (StringUtils.isNotBlank(codeContent)) {
				  String type= types[i];
				  RecommonedSite recommonedSite =  new RecommonedSite();
				  recommonedSite.setAvaliable(1);
				  recommonedSite.setCode(CodeFactory.create("tjfw"));
				  recommonedSite.setCreatetime(new Date());
				  recommonedSite.setContentType(type);
				  recommonedSite.setSitecode(serviceSite1.getCode());
				  recommonedSite.setConentCode(codeContent);
				  recommonedSiteService.save(recommonedSite);
			}
	  }
		return  list("", "", "", "", pager, rq, modelMap);
	}
	
	@RequestMapping("delete")
	public String delete(String codes ,ModelMap modelMap,String eqIds,HttpServletRequest rq,Pager pager){
		if (StringUtils.isNotBlank(codes)) {
			 String code[] = codes.split(",");
			 for (String c : code) {
				siteService.deleteByCode(c);
				//删除中间表
				 if (c!=null) {
						recommonedSiteService.deleteBySql("delete  from  recommoned_site  where sitecode='"+c+"'");
					}
			 }
			 
		}
		
		return  list("", "", "", "", pager, rq, modelMap);
	}
	
	
	public IserviceSiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(IserviceSiteService siteService) {
		this.siteService = siteService;
	}
	
	@Override
    public void setServletContext(ServletContext arg0) {
        this.application = arg0;
    }
	
}
