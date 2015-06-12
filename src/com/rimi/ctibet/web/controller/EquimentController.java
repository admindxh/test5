package com.rimi.ctibet.web.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.BeansUtil;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.ImageZipUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.CommonOrder;
import com.rimi.ctibet.domain.Equipment;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.service.ICommonOrderService;
import com.rimi.ctibet.web.service.IEquimentService;
import com.rimi.ctibet.web.service.IProgramaService;

@Controller
@RequestMapping("web/equip")
public class EquimentController  extends BaseController{
	
	@Autowired
	private  IEquimentService equimentService; 
	
	@Resource ICommonOrderService commonOrderService;
	
	@Autowired
	private IProgramaService programaService;
    
	/**
	 * 骑行装备列表
	 * @param pager
	 * @param rq
	 * @param modelMap
	 * @param name
	 * @param proType
	 * @return
	 */
	@RequestMapping("list")
	public String list(String name,String proType,Pager pager,HttpServletRequest rq,ModelMap modelMap){
		 Pager pager1  = equimentService.getEquiList(name, proType, pager);
		 modelMap.put("pager", pager1);
		 List<Programa> ptypes= programaService.getSendPrograma("superequipmenttype");
		 modelMap.put("ptypes", ptypes);
		 String proTypeText  = rq.getParameter("proTypeText");
		 if (StringUtils.isNotBlank(proTypeText)) {
			
		 }else{
			 
			 proTypeText = "全部类型";
		 }
		 modelMap.put("proTypeText", proTypeText);
		 modelMap.put("proType", proType);
		 modelMap.put("name", name);
		 
		 
		return "manage/html/ride/equiment";
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
		 List<Programa> ptypes= programaService.getSendPrograma("superequipmenttype");
		 modelMap.put("ptypes", ptypes);
		
		 
		return "manage/html/ride/equirement-creat";
	}
	
	/**
	 * 骑行装备修改页面
	 * @param pager
	 * @param rq
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("updateUI")
	public String updateUI(ModelMap modelMap,String ecode){
		
		 List<Programa> ptypes= programaService.getSendPrograma("superequipmenttype");
		
		 modelMap.put("ptypes", ptypes);
		 if (StringUtils.isNotBlank(ecode)) {
		 Equipment equipment = equimentService.findByCode(ecode);
			 if (equipment!=null&&StringUtils.isNotBlank(equipment.getProType())) {
				 Programa programa = programaService.getProgramaByCode(equipment.getProType());
				 if (programa!=null) {
					 modelMap.put("pname", programa.getProgramaName());
					 modelMap.put("pcode", programa.getCode());
				}else{
					 modelMap.put("pname", "请选择装备类型");
					
				}
			 }else{
				 
				 modelMap.put("pname", "请选择装备类型");
			 }
			 modelMap.put("equipment", equipment);
		}
		 
		 
		return "manage/html/ride/equirement-update";
	}
	@RequestMapping("delete")
	public String delete(String codes ,ModelMap modelMap,String eqIds,HttpServletRequest rq,Pager pager){
		if (StringUtils.isNotBlank(codes)) {
			 String code[] = codes.split(",");
			 for (String c : code) {
				equimentService.deleteByCode(c);
			}
		}
		
		return list("", "", pager, rq, modelMap);
	}
	
	/**
	 * 验证装备是否可删除（可被删除的前提为该装备未被购买）
	 * 
	 * @param code 装备编号
	 * @return "true" / "false"
	 * @author tangHQ
	 * @date 2015-4-15
	 */
	@RequestMapping(value = "validDel", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public String validDel (String codes) {
		
		List<CommonOrder> orders = commonOrderService.queryOrdersByGoodsCode(codes);
		return String.valueOf(orders == null || orders.isEmpty());
	}
	
	
	/**
	 * 骑行装备添加页面
	 * @param pager
	 * @param rq
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("save")
	public String save(Equipment equipment ,ModelMap modelMap,String eqIds,HttpServletRequest rq,Pager pager){
		
		try {
			//System.out.println("imageName------------>"+image.getName());
			File oldFile  = new  File(getUrlFileImg(equipment.getSumaryimg(),request));
			String fileName1 = oldFile.getName();
			String  prexis = fileName1.substring(fileName1.lastIndexOf(".")+1);
			Timestamp tsp = new Timestamp(new Date().getTime());
			int x=(int)(Math.random()*100);
			String s =  "/upload" + "/img/equirement/" + tsp.getTime()+""+"/"+x+"."+prexis;//s 表示访问路劲
			String imgPath = request.getSession().getServletContext().getRealPath("")+ s;  //存入图片的路径
			File newFile  =  new File(imgPath);
			File filedir = new File(request.getSession().getServletContext().getRealPath("")+"/upload" + "/img/equirement/" + tsp.getTime());
			if (!filedir.exists()) {
				 filedir.mkdirs();
				if (!newFile.exists()) {
					newFile.createNewFile();
				}
			}
			//图片压缩
			ImageZipUtil.zipImageFile(oldFile, newFile, 400, 300, 1f);
			equipment.setSmallimg(s);
			equipment.setAvaliable(1);
			String createCode = CodeFactory.createCode("equiment");
			equipment.setUrl(super.getUrlHtml(Constants.EQUIRMENT_URL, createCode));
			equipment.setCreatetime(new Date());
			equipment.setCode(createCode);
			equimentService.save(equipment);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list("", "", pager, rq, modelMap);
	}
	/**
	 * 骑行装备修改页面
	 * @param pager
	 * @param rq
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("update")
	public String update(Equipment equipment ,ModelMap modelMap,String eqIds,HttpServletRequest rq,Pager pager){
		
		try {
			//System.out.println("imageName------------>"+image.getName());
			File oldFile  = new  File(getUrlFileImg(equipment.getSumaryimg(),request));
			String fileName1 = oldFile.getName();
			String  prexis = fileName1.substring(fileName1.lastIndexOf(".")+1);
			Timestamp tsp = new Timestamp(new Date().getTime());
			int x=(int)(Math.random()*100);
			String s =  "/upload" + "/img/equirement/" + tsp.getTime()+""+"/"+x+"."+prexis;//s 表示访问路劲
			String imgPath = request.getSession().getServletContext().getRealPath("")+ s;  //存入图片的路径
			File newFile  =  new File(imgPath);
			File filedir = new File(request.getSession().getServletContext().getRealPath("")+"/upload" + "/img/equirement/" + tsp.getTime());
			if (!filedir.exists()) {
				 filedir.mkdirs();
				if (!newFile.exists()) {
					newFile.createNewFile();
				}
			}
			//图片压缩
			ImageZipUtil.zipImageFile(oldFile, newFile, 400, 300, 1f);
			equipment.setSmallimg(s);
			Equipment equipment2 = equimentService.findByCode(equipment.getCode());
			//BeanUtils.copyProperties(equipment, equipment2);
			BeansUtil.copyPropertysWithoutNull(equipment2, equipment);
			equipment2.setUrl(super.getUrlHtml(Constants.EQUIRMENT_URL, equipment2.getCode()));
			equimentService.update(equipment2);
			//equimentService.save(equipment);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list("", "", pager, rq, modelMap);
	}
	/**
	 * 获取url  图片路径
	 * @param url
	 * @param request
	 * @return
	 */
	public String getUrlFileImg(String url,HttpServletRequest request){
		String  tomcatPath =  request.getSession().getServletContext().getRealPath("");
		
		String imgPath =  "";
		if (StringUtils.isNotBlank(url)&&url.indexOf("upload")!=-1) {
			   String urls[]= url.split("upload");
			   imgPath = tomcatPath+"/upload"+urls[1];
		} 
		return  imgPath;
	}
	
	
}
