package com.rimi.ctibet.web.service;

import java.io.IOException;
import java.util.List;

import org.springframework.ui.ModelMap;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Destination;
/**
 * 
 * @author xiangwq
 *
 */
public interface DestinationService extends BaseService<Destination> {
    /**
     * 获得目的地列表
     * @param destinationName
     *          目的地名称
     * @param pager
     * @param model
     * @return
     */
	public Pager getDestinationPager(Pager pager,
			ModelMap model);
    /**
     * 通过目的地ID获取目的地
     * @param destinationId 目的地ID
     * @return
     */
	public Destination getDestinationById(String destinationId);
	/**
	 * 保存目的地
	 * @param destination
	 * @throws IOException 
	 */
	public void saveDestination(Destination destination);
	/**
	 * 删除选择的目的地（目的地以下有景点的不能删除）
	 * @param destinationIds
	 * @throws Exception 
	 */
	public void deleteSelect(String[] destinationIds) throws Exception;
	
	/**
	 * 得到所有目的地列表
	 */
	public List<Destination> getAllDestination();
	
	/**
	 * 通过目的地Code获取目的地
	 * @param destinationCode
	 * @return
	 */
	public Destination getDestinationByCode(String destinationCode);
	/**
	 * 保存热门景点
	 * @param dCode
	 * @param pCode
	 * @param hotviewCode
	 * @param viewCode
	 */
	public void saveHotView(String dCode, String pCode, String hotviewCode,
			String[] viewCode);
	/**
	 * 保存精彩旅程
	 * @param dCode
	 * @param pCode
	 * @param travelCode
	 * @param urls
	 * @param codes
	 */
	public void saveTravel(String dCode, String pCode, String[] contentCode,
			String[] urls, String[] codes);
	/**
	 * 保存推荐商户
	 * @param dCode
	 * @param pCode
	 * @param contentCode
	 * @param urls
	 * @param codes
	 * @param typeCode 
	 */
	public void saveRecomMerchant(String order,String dCode, String pCode,
			String[] contentCode, String[] urls, String[] codes,String type, String typeCode);
	/**
	 * 想去数和去过数的保存
	 * @param destination
	 */
	public void saveGoneWant(Destination destination);

}
