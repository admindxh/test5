package com.rimi.ctibet.portal.controller;


import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.ImageService;

/**
 * 目的地管理
 * @author xiangwq
 * @date 2014/11/19
 */
@Controller("portal_destination")
@RequestMapping({"portal/destination","tourism/atlas"})
public class DestinationController extends BaseController  implements ServletContextAware{
    
    public static final	Logger LOGGER = Logger.getLogger(DestinationController.class);
    ServletContext application;
    
	@Resource private DestinationService destinationService;
	@Resource private IViewService viewService;
	@Resource private ImageService imageService;
	
	
	@ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.put("programNam","1");
	}
	
	
   /**
     * 跳转到 目的地图集列表
     * portal/destination/forPhotoGallery?destinationCode=dest421048545686
     */
    @RequestMapping("forPhotoGallery")
    public String forPhotoGallery(Model model, String destinationCode){
        Destination destination = destinationService.findByCode(destinationCode);
        if(destination!=null){
            model.addAttribute("destinationName", destination.getDestinationName());
            model.addAttribute("destinationCode", destination.getCode());
            model.addAttribute("destination", destination);
        }
        return "portal/app/tourism/photo_gallery";
    }
    /**
     * 获取目的地图集
     * portal/destination/loadDestinationAtlas
     */
    @RequestMapping(value="loadDestinationAtlas", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String loadDestinationAtlas(Pager pager, String destinationCode, String type){
        pager = imageService.getDestinationCodeAtlasByCodeType(pager, destinationCode, type);
        return obj2json(pager);
    }
        
    /********************************************
     * Setter Getter
     ********************************************/
    @Override
    public void setServletContext(ServletContext application) {
        this.application = application;
    }
}






