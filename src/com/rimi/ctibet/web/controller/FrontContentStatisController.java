
package com.rimi.ctibet.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.FrontContentStatis;
import com.rimi.ctibet.domain.MerchantType;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.controller.vo.BaseCodeVo;
import com.rimi.ctibet.web.controller.vo.FrontContentVo;
import com.rimi.ctibet.web.service.IFrontContentStatisService;
import com.rimi.ctibet.web.service.IMerchantTypeService;
import com.rimi.ctibet.web.service.IProgramaService;

@Controller
@RequestMapping(value="web/frontContentStatisController")
public class FrontContentStatisController {
	
	
	@Resource(name="frontContentStatisService")
	private IFrontContentStatisService contentStatisService;

	@Resource
	private IMerchantTypeService merchantTypeService;
	@Resource
	private IProgramaService programaService;
	
	
	/**
	 * 统计后台 相应的日志 
	 * @param protype 
	 * @param tableName
	 * @param code
	 * @param actionType
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("staticsSystemLog")
	@ResponseBody
	public  String  staticsSystemLog(String protype,String tableName,String code,String actionType,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		
	      FrontContentStatis  contentStatis  = new FrontContentStatis();
		  contentStatis.setActiontype(actionType);
		  contentStatis.setProgtype(protype);
		  contentStatis.setCode(code);
		  contentStatis.setTablename(tableName);
		  contentStatis.setCtime(new Date());
		  contentStatisService.save(contentStatis);
		 return "";
	}
	/**
	 * 内容统计
	 * @param start
	 * @param end
	 * @param protype
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("intoFrontContentStatis")
	public String intoFrontContentStatis(Pager pager,String start,String end,String protype,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		//目的地维护
		SimpleDateFormat dateFormat  =  new SimpleDateFormat("yyyy-MM-dd");
		String proType  = request.getParameter("proType");
		String menu   = request.getParameter("menu");
		if (StringUtils.isNotBlank(menu)) {
			start  =  dateFormat.format(new Date());
			end    =  dateFormat.format(new Date());
		}
		List<BaseCodeVo>  baselist  = contentStatisService.getBaseCodeListByPcode("1");
		FrontContentVo contentVo =  contentStatisService.getAllFrontContent(start, end, proType);
		pager.setPageSize(20);
		Pager result  =  contentStatisService.getPageFrontContentByCon(start, end, proType, pager);
		contentVo.setReplyCount(getReplyCount(result));
		
		String proTypeText  =request.getParameter("proTypeText");
		if (!StringUtils.isNotBlank(proTypeText)) {
			proTypeText= "全部栏目";
		}
		modelMap.addAttribute("start", start);
		modelMap.addAttribute("end", end);
		modelMap.addAttribute("proTypeText", proTypeText);
		modelMap.addAttribute("protype", proType);
		modelMap.addAttribute("result", result);
		modelMap.addAttribute("contentVo", contentVo);
		modelMap.addAttribute("baselist", baselist);
		return "manage//html//settings//dataStatis-content" ;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 商户统计
	 * @param start
	 * @param end
	 * @param protype
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("intoFrontMerchantStatis")
	public String intoFrontMerchantStatis(String merchantName,Pager pager,String start,String end,String protype,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		//目的地维护
		SimpleDateFormat dateFormat  =  new SimpleDateFormat("yyyy-MM-dd");
		String proType  = request.getParameter("proType");
		String menu   = request.getParameter("menu");
		if (StringUtils.isNotBlank(menu)) {
			start  =  dateFormat.format(new Date());
			end    =  dateFormat.format(new Date());
		}
		List<MerchantType>  baselist  = merchantTypeService.getMerchantList();
		FrontContentVo contentVo =  contentStatisService.getAllFrontMerchant(start, end, proType, merchantName);
		
		Pager result  =  contentStatisService.getPageFrontMerchanttByCon(start, end, proType, merchantName, pager);
		
		
		
		
		String proTypeText  =request.getParameter("proTypeText");
		if (!StringUtils.isNotBlank(proTypeText)) {
			proTypeText= "全部类型";
			proType= "";
		}
		
		
		modelMap.addAttribute("start", start);
		modelMap.addAttribute("end", end);
		modelMap.put("proType", proType);
		modelMap.addAttribute("proTypeText", proTypeText);
		modelMap.addAttribute("result", result);
		contentVo.setReplyCount(getReplyCount(pager));
		
		contentVo.setReplyCount(getReplyCount(pager));
		modelMap.addAttribute("contentVo", contentVo);
		modelMap.addAttribute("baselist", baselist);
		modelMap.addAttribute("merchantName", merchantName);
		return "manage//html//settings//dataStatis-sell" ;
	}
	
	
	public int getReplyCount(Pager  pager){
		
		 List dataList = pager.getDataList();
		 int rc  =   0 ;
		 if (dataList!=null) {
			 for (Object object : dataList) {
				 FrontContentVo m  = (FrontContentVo) object;
				rc +=  m.getReplyCount();
			 }
			
		}
		  
		
		return  rc;
	}
	
	
	
	
	/**
	 * 广告统计
	 * @param start
	 * @param end
	 * @param protype
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("intoFrontAderStatis")
	public String intoFrontAderStatis(Pager pager,String start,String end,String protype,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		//目的地维护
		SimpleDateFormat dateFormat  =  new SimpleDateFormat("yyyy-MM-dd");
		String proType  = request.getParameter("proType");
		String menu   = request.getParameter("menu");
		if (StringUtils.isNotBlank(menu)) {
			start  =  dateFormat.format(new Date());
			end    =  dateFormat.format(new Date());
		}
		List<Programa>  baselist  = programaService.getSendPrograma("ggwlx");
		FrontContentVo contentVo =  contentStatisService.getAllFrontAderv(start, end, proType);
		
		Pager result  =  contentStatisService.getPageFrontAdervByCon(start, end, proType, pager);
		contentVo.setClickCount(getAderCount(pager));
		String proTypeText  =  request.getParameter("proTypeText");
		if (!StringUtils.isNotBlank(proTypeText)) {
			proTypeText = "全部页面";
		}
		modelMap.addAttribute("start", start);
		modelMap.addAttribute("end", end);
		modelMap.addAttribute("proTypeText", proTypeText);
		modelMap.addAttribute("proType", proType);
		modelMap.addAttribute("result", result);
		modelMap.addAttribute("contentVo", contentVo);
		modelMap.addAttribute("baselist", baselist);
		return "manage//html//settings//dataStatis-adver" ;
	}
	public int  getAderCount(Pager pager){
		
		int count  = 0 ;
		 List dataList = pager.getDataList();
		 if (dataList!=null) {
			 for (Object object : dataList) {
				 FrontContentVo  ft  = (FrontContentVo) object;
				 count+=ft.getClickCount();
			}
		}
		return count;
	}
	
	
	
	
	
	public IFrontContentStatisService getContentStatisService() {
		return contentStatisService;
	}

	public void setContentStatisService(
			IFrontContentStatisService contentStatisService) {
		this.contentStatisService = contentStatisService;
	}
	
	
	
	
	
	
}
