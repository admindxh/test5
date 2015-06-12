package com.rimi.ctibet.web.dao;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.View;

public interface IViewDao extends BaseDao<View> {
	
	/**
	 * 通过destinationCode来查询有效景点
	 * @param destinationCode
	 * @return
	 */
	public List<View> findViewByDestinationCode(String destinationCode);
	
	public Pager testPager(Pager pager);
	
	//public List<View> searchViewByIdName(View view);
    /**
     * 景点信息管理分页
     * @param destinationCode
     * @param viewName
     * @param pager
     * @return
     */
	public Pager getViewPager(String destinationCode, String viewName, Pager pager);
	
	/**
	 * 通过目的地code 景点code 关键字 以及指定排序方式搜索数据
	 * @param destinationCode
	 * @param viewCode
	 * @param viewName
	 * @param keyWords
	 * @param orderBy
	 * @return
	 */
	public Pager searchViewByKeyWords(Pager pager, String destinationCode, String viewCode, String viewName, String keyWords, int orderBy);
    /**
     * 按收藏数排序
     * @param i
     * @return
     */
	public List<View>  orderByFav(int i);
   
	
	public Destination getDestinationByView(String code);
	//===================made by xz===========================
	//按照想去数排序
	public List<Map<String,Object>> getViewByWann(int num,String descode);
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
