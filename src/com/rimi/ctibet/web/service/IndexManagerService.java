package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.IndexManager;
import com.rimi.ctibet.portal.controller.vo.BbsIndexManagerP;

public interface IndexManagerService   extends BaseService<IndexManager>{
	
	/**
	 * 栏目code 删除 ，同时创建对应的去新的数据 4条
	 * @param proramCode
	 */
	public void initIndexManager(String proramCode,String contentType,Integer row);
	
	public void initIndexManagerbyScore(String proramCode,String contentType,Integer row);
	
	public void initIndexManagerbyPraise(String proramCode, String contentType,
			Integer row);
	
	/**
	 * 更新栏目地址
	 * @param indexManager
	 */
    public void updateIndexManager(IndexManager indexManager);

    /**
     * 获取对应的动态首页数据
     * @param proramCode
     * @return
     */
    public List<IndexManager> getListIndexManager(String proramCode,String contentType);
    
    /**
     * 获取内容
     * @param programcode
     * @return
     */
    public List<Content>  getListContent(String programcode,String contentType);
    
    
    public void deleteProgram(String pro,Integer avlable) ;
    
    public List<BbsIndexManagerP> getListPraiseContent(String programcode,String contentType);
    
    public  List<Object[]> getOrderManager(String proramCode, final String contentType,
			final Integer row);
    public List<Content> getContentListByNumber(final int row,final String order, final String contentType,String desCode);
    
    /**
     * 获取指定数量的最赞回复
     * @param row
     * @return
     */
    public List<IndexManager> getMostPraise(int row);
    
}
