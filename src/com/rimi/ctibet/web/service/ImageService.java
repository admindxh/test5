package com.rimi.ctibet.web.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.ImagePager;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Image;

/**
 * 
 * @author xiangwq
 * @date 2014/11/21
 */
public interface ImageService extends BaseService<Image> {
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @return
	 */
	public List<String> uploadImg(List<MultipartFile> images,
			String path,Timestamp tsp);
	/**
	 * 保存图片
	 * @param image
	 */
    public void saveImage(Image image);
    /**
     * 删除图片
     * @param destinationIds
     */
    public void deleteSelect(String[] codes);
    /**
     * 通过图集Code获取图片列表
     * @param MutiImageId
     * @return
     */
    public List<Image> getImageByMutiImageCode(String MutiImageCode);
 
    
    /**
     * 获取图片列表并分页
     * @param pager
     * @param model
     * @param pageCount
     * @return
     */
    public ImagePager getImagePager(ImagePager pager,int pageSize,String MutiImageCode); 
    
    /**
	 * 传入首页管理的栏目code，找到对应图集code,返回图像
	 */  
    public Map<String,List<Image>> getHomeImg();

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
	
	public List<String> outPutFile(List<MultipartFile> images, String dir);
	/**
	 * 获取游西藏首页的banner,推荐位
	 * @return
	 */
	public Map<String, List<Image>> getTravelHomeImg();
	
	/**
	 * 通过图集code获取图片
	 * @param mutiCode
	 */
	public List<Image> getImageByMutiCode(String mutiCode);
	
	/**
	 * 通过图集code获取图片 并初始化指定数量的image
	 */
	public List<Image> getImageByMutiCodeImageNum(String mutiCode, int imageNum, String name, String summary);
	
	/**
	 * 删除Image
	 * @param listImage
	 */
	public void updateImageList(List<Image> listImage);
	/**
	 * 图片分页
	 */
	public Pager findImagePager(String code, Pager pager);
   /**
    * 通过url查询图片
    * @param url
    * @return
    */
	public List<Image> findImageByUrl(String url);
	/**
	 * 用户图集分页
	 * @param mutiCode
	 * @param pager
	 * @return
	 */
	public Pager findUserImgPager(String destinationCode,String mutiCode, Pager pager);


	/**
     * 通过 code和类型获取景点图集
     * @param pager
     * @param viewCode
     * @param type
     * @return
     */
    public Pager getViewAtlasByCodeType(Pager pager, String viewCode, String type);
    
    /**
     * 通过攻略code逻辑删除数据
     * @param mutiCode
     */
    public void deleteAtlasByMutiCode(String mutiCode);
    
    /**
     * 删除多个图片
     * @param codes
     */
    public void deleteImages(String basePath, String[] codes);
    
    /**
     * 通过 code和类型获取目的地图集
     * @param pager
     * @param viewCode
     * @param type
     * @return
     */
    public Pager getDestinationCodeAtlasByCodeType(Pager pager, String destinationCode, String type);
    
    /**
     * 通过url删除图集
     * @param url
     */
    public void deleteAtlasByUrl(String url);
    /**
     * 通过url删除图集
     * @param url
     */
    public void deleteAtlasByUrls(String basePath, String[] urls);
    
}
