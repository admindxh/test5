package com.rimi.ctibet.web.dao;


import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.ImagePager;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Image;

public interface ImageDao extends BaseDao<Image> {
  
	
	/**
	 * 通过图集code获取图片
	 * @param mutiImageCode
	 * @return
	 */
	List<Image> findImageByMutiImageCode(String mutiImageCode);

	/**
	 * 获取图片并分页
	 * @param pager
	 * @param model
	 * @param pageSize
	 * @param mutiImageCode
	 * @return
	 */
	ImagePager getImagePager(ImagePager pager, int pageSize,
			String mutiImageCode);
     
	
	/**
	 * 通过活动code查询对应的其他模块信息
	 * @param ActivityCode
	 * @return
	 */
	public List<Image> getActivityOtherBlockByActivityCode(String ActivityCode);
	/**
	 * 通过活动code查询对应的其他模块信息
	 * @param ActivityCode
	 * @return
	 */
	public Pager getActivityOtherBlockByActivityCode(Pager pager, String ActivityCode);
	
	/**
	 * 通过图集code删除图片
	 * @param mutiCode
	 */
	public void deleteImageByMutiCode(String mutiCode);
	
	/**
	 * 通过图集code获取图片
	 * @param mutiCode
	 */
	public List<Image> getImageByMutiCode(String mutiCode);
   /**
    * 通过url获取图片
    * @param url
    * @return
    */
	List<Image> findImageByUrl(String url);
	/**
	 * 获得用户图集分页
	 * @param destinationCode
	 * @param mutiCode
	 * @param pager
	 * @return
	 */
	public Pager findUserImgPager(String destinationCode,String mutiCode,Pager pager);

	public Pager findImagePager(String code, Pager pager);
	
	/**
	 * 通过 code和类型获取景点图集
	 * @param pager
	 * @param viewCode
	 * @param type
	 * @return
	 */
	public Pager getViewAtlasByCodeType(Pager pager, String viewCode, String type);
	/**
	 * 通过 code和类型获取目的地图集
	 * @param pager
	 * @param viewCode
	 * @param type
	 * @return
	 */
	public Pager getDestinationCodeAtlasByCodeType(Pager pager, String destinationCode, String type);
	
	/**
	 * 通过攻略code逻辑删除数据
	 * @param mutiCode
	 */
	public void deleteAtlasByMutiCode(String mutiCode);
	
	/**
	 * 通过url删除图集
	 * @param url
	 */
	public void deleteAtlasByUrl(String url);
	
}
