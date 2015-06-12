package com.rimi.ctibet.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.View;

public interface IViewService extends BaseService<View> {
	
	public Pager getAll(Pager pager);
	//public List<View> search(View view);
	
	/**
	 * 保存 View
	 * @param view
	 * @param basePath
	 * @param viewImgs
	 * @param view360Full
	 */
	public boolean saveView(View view, String basePath, MultipartFile[] viewImgs, MultipartFile view360Full);
	
	/**
	 * 通过destinationCode来查询有效景点
	 * @param destinationCode
	 * @return
	 */
	public List<View> findViewByDestinationCode(String destinationCode);
	
	public Pager testPager(Pager pager);
	
	/**
	 * 分页获取景点
	 * @param destinationCode
	 * @param viewName
	 * @param pager
	 * @return
	 */
	public Pager getViewPager(String destinationCode, String viewName,
			Pager pager);
	/**
	 * 删除选中的景点
	 * @param codes
	 */
	public void deleteSelect(String[] codes);
	/**
	 * 保存景点
	 * @param view
	 */
	public void saveView(View view);
	
	/**
	 * 通过目的地code 景点code 关键字 以及指定排序方式搜索数据
	 * @param destinationCode
	 * @param viewCode
	 * @param keyWords
	 * @param orderBy
	 * @return
	 */
	public Pager searchViewByKeyWords(Pager pager, String destinationCode, String viewCode, String viewName, String keyWords, int orderBy);
	
	/**
	 * 点赞
	 */
	public void updateLikeNumByCode(String code);
	/**
	 * 取消点赞
	 */
	public void updateUnLikeNumByCode(String code);
	
	/**
	 * 查看数+1
	 */
	public void updateCheckNumByCode(String code);
     /**
      * 按收藏数排序
      * @param i
      * @return
      */
	public List<View>  orderByFav(int i);

	public Destination getDestinationByView(String code);
	
	/**
	 * 删除景点
	 */
	public void deleteView(String[] codes);
	//=========================made by xz=============================
	//按照想去数排序:Map中的key：viewcode,areacode
	public List<Map<String,Object>> getViewByWann(int numk,String descode);
//按去过数查询排序
	
	public List<Map<String, Object>> getViewByGone(int num,String descode) ;
	//判断是否是第一次进入热门景点页面
	public boolean isFirstInHotView();
	//获取热门景点数据,共有10个
	public List<Map<String,Object>> getHotView();
	
	/**
     * 通过景点查询 景点关联的 团游 商户 攻略数量
     */
    public int getViewRelationContent(String viewCode);
    
    /**
     * 通过景点名字来查询同名景点的数量
     * @param name
     * @return
     */
    public int getSameViewNum(String name);
	
}
