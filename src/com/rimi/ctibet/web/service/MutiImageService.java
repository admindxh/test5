package com.rimi.ctibet.web.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.web.controller.vo.MutiImageVO;

/**
 * 
 * @author xiangwq
 * @date 2014/11/21
 */
public interface MutiImageService {
	/**
	 * 保存图集
	 * @param muti
	 */
	public void saveMuti(MutiImage muti);
     /**
      * 保存图集
      * @param muti
      */
	public void saveMutiImage(HttpServletRequest request,HttpServletResponse response,MutiImage muti);
	/**
	 * 删除选择的图集
	 * @param mutiIds
	 */
	public void deleteSelect(String[] mutiIds);
	/**
	 * 获取图集列表并分页
	 * @param name
	 * @param pager
	 * @param model
	 * @return
	 */
	public Pager getMutiImagePager(Pager pager,String programaCode);
	/**
	 * 通过栏目code获取图集
	 * @param programaCode
	 * @return
	 */
	public List<MutiImage> getMutiImageByProgramaCode(String programaCode);
	
	/**
	 * 传入programaCode
	 */
	public MutiImage getMutiImageByCode(String code);
	
	/**
	 * 通过 programaCode获取图集信息MutiImageVO （包含 MutiImage， List<Image>）
	 */
	public MutiImageVO getMutiImageVO(String programaCode);
	/**
	 * 前段的图集分页
	 * @param pager
	 * @param action
	 * @return
	 */
	public Pager getFrontMutiPager(Pager pager, String action);
	//个人中心收藏的图集
	public Pager findListByPager(String sql,List param,Pager pager);
	public Pager findMutiPagerOnImages(Pager pager, String programaCode);
	public Map preAndNextMuti(MutiImage mutiImage);
	public Pager search(Pager pager, MutiImage mutiImage, String orderField);
	
}
