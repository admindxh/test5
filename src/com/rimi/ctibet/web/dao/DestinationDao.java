package com.rimi.ctibet.web.dao;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.View;
/**
 * 
 * @author xiangwq
 *
 */
public interface DestinationDao extends BaseDao<Destination>{
	
	
    /**
     * 获取目的地列表并分页
     * @param destinationName  目的地名称
     * @param pager
     * @param model
     * @return
     */
	public Pager getDestinationPager(Pager pager,
			ModelMap model);
    /**
     * 通过目的地ID获取目的地信息
     * @param destinationId  目的地ID
     * @return
     */
	public Destination getDestinationById(String destinationId);
	/**
	 * 通过目的地ID查找景点列表
	 * @param destinationId
	 * @return
	 */
	public List<View> getViewsByDesCode(String destinationCode);
	/**
	 * 获取所有的目的地
	 * @return
	 */
	public List<Destination> getALLDestination();

}
