package com.rimi.ctibet.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.FileUtil;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.RideRecommon;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IEquimentService;
import com.rimi.ctibet.web.service.IRideCommonService;
import com.rimi.ctibet.web.service.IRideLineService;

@RequestMapping("web/ridecommon")
@Controller
public class RideCommonController extends BaseController  implements ServletContextAware  {

	@Autowired
	private IRideLineService rideLineService;

	@Autowired
	private IContentService contentService;

	@Autowired
	private IEquimentService equimentService;
	
	@Autowired
	private IRideCommonService  rideCommonService;

	ServletContext application;
    
    final String tempPath = "/temp/";
	
	@RequestMapping("list")
	public String list(HttpServletRequest request, ModelMap modelMap) {
		  //查询攻略code
		  List<RideRecommon> jdgl = rideCommonService.findByProperty("contentType", "jdgl");
		  List<RideRecommon> yqlj = rideCommonService.findByProperty("contentType", "yqlj");
		  List<RideRecommon> zbtj = rideCommonService.findByProperty("contentType", "zbtj");
		  modelMap.put("jdgl", jdgl);
		  modelMap.put("yqlj", yqlj);
		  modelMap.put("zbtj", zbtj);
		  return "manage/html/ride/riderecomm";
	}

	@RequestMapping("save")
	public String save(HttpServletRequest request, ModelMap modelMap,
			String[] contentTypes, String[] ctornames, String[] urls,
			String[] imgurl) {
		rideCommonService.deleteBySql("   delete   from    riderecomm    ");
		//保存数据
		for (int i = 0; i < contentTypes.length; i++) {
			  RideRecommon recommon =  new RideRecommon();
			  recommon.setAvaliable(Integer.valueOf(1));
			  recommon.setCode(CodeFactory.createCode("qxtj"));
			  recommon.setCreateTime(new Date());
			  recommon.setContentType(contentTypes[i]);
			  recommon.setCtorname(ctornames[i]);
			  recommon.setUrl(urls[i]);
			  
			  if(StringUtil.isNotNull(imgurl[i])){
                String basePath = application.getRealPath("/");
                String newBannerPath = imgurl[i].replace(tempPath, "");
                FileUtil.moveFile(basePath + imgurl[i], basePath+newBannerPath);
                recommon.setImgurl(newBannerPath);
			  }
			  
			  rideCommonService.save(recommon);
		}
		return list(request, modelMap);
	}
    /********************************************
     * Setter Getter
     ********************************************/
    @Override
    public void setServletContext(ServletContext arg0) {
        this.application = arg0;
    }
}
