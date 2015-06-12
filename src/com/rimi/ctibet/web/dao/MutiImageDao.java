package com.rimi.ctibet.web.dao;

import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.web.controller.vo.MutiImageVO;

public interface MutiImageDao extends BaseDao<MutiImage> {
   /**
    * 获取列表并分页
    * @param name
    * @param pager
    * @param model
    * @return
    */
	Pager getMutiImagePager(Pager pager,String programaCode);

	
	/**
	 * 通过活动code查询
	 * @param activityCode
	 * @return
	 */
	public MutiImage getMutiImageByActCode(String activityCode);
	
	/**
	 * 通过programaCode获取图集对象 不包含List<Image>
	 */
	public MutiImageVO getMutiImageByProgramaCode(String programaCode);

    /**
     * 通过programaCode获取图集list
     * @param programaCode
     * @return
     */
	public List<MutiImage> findMutiImageByProgramaCode(String programaCode);

    /**
     * 前台分页
     * @param pager
     * @param action
     * @return
     */
	public Pager getFrontMutiPager(Pager pager, String action);

	/**
	 * 查询指定行号的图集 
	 * @param mutiImage 限制条件
	 * @param rowNo
	 * @return
	 */
	public MutiImage findbyRowNo(MutiImage mutiImage, int rowNo);

	/**
	 * 查询 图集 的行号
	 * @param mutiImage
	 * @return
	 */
	public int findRowNo(MutiImage mutiImage);
	
	/**
	 * 查询一页数据
	 * @param pager 页面基本设置（大小，页码） 
	 * @param mutiImage 查询条件 
	 * @param orderField 排序字段 eg: "createtime desc"
	 * @return 返回一页数据   用map存储一行数据 
	 */
	public Pager search(Pager pager,MutiImage mutiImage,String orderField);

}
