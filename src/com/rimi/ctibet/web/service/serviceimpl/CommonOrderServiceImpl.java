package com.rimi.ctibet.web.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.CommonOrder;
import com.rimi.ctibet.web.dao.ICommonOrderDao;
import com.rimi.ctibet.web.service.ICommonOrderService;

/**
 * 骑行专区 / 订单管理
 *
 * @author tangHQ
 * @date 2015-4-9
 */
@Service("commonOrderService")
public class CommonOrderServiceImpl extends BaseServiceImpl<CommonOrder> implements ICommonOrderService {

    @Resource
    ICommonOrderDao commonOrderDao;

    @Override
    public Pager queryCommonOrderByPager (Pager pager, CommonOrder order, String orderTime) {
    	return commonOrderDao.queryCommonOrderByPager(pager, order, orderTime);
    }
    
    @Override
	public List<CommonOrder> queryOrdersByGoodsCode(String goodsCode) {

    	if (StringUtils.isBlank(goodsCode)) return null;
    	
    	List<CommonOrder> orders = new ArrayList<CommonOrder>();

    	if (goodsCode.contains(",")) {
    		for (String code : goodsCode.split(",")) {
    			List<CommonOrder> os = commonOrderDao.findByProperty("goodsCode", code);
    			if (os != null && !os.isEmpty()) {
    				orders.addAll(os);
    			}
    		} return orders;
    	} else return commonOrderDao.findByProperty("goodsCode", goodsCode);
	}
    
    /*---------------------Getter/Setter----------------------*/

	public ICommonOrderDao getCommonOrderDao() {
		return commonOrderDao;
	}

	public void setCommonOrderDao(ICommonOrderDao commonOrderDao) {
		this.commonOrderDao = commonOrderDao;
	}
}
