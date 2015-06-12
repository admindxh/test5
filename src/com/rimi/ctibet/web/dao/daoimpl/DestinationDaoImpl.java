package com.rimi.ctibet.web.dao.daoimpl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.dao.DestinationDao;

@Repository("destinationDao")
public class DestinationDaoImpl extends BaseDaoImpl<Destination> implements
		DestinationDao {

	/**
	 * 得到目的地列表并分页
	 */
	@Override
	public Pager getDestinationPager(Pager pager,
			ModelMap model) {
//		List params = new ArrayList();
//		// merchant_type 1为酒店 2为餐饮 3为娱乐 4为购物
//		StringBuffer sql = new StringBuffer("select * from destinationQuery d");
//		if (StringUtils.isNotBlank(destinationName)) {
//			sql.append(" and d.destinationName like :destinationName ");
//			params.add(destinationName);
//		}
//		sql.append(" group by d.destinationName order by d.createTime desc");
//		//System.out.println(sql);
//		return findByJDBCSql(sql.toString(), params, pager);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("FROM Destination d WHERE d.avaliable=1 ORDER BY d.createTime desc");
		return findWithPagerByMap(hql.toString(),paraMap , pager);
	}

	/**
	 * 通过目的地ID获取目的地信息
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Destination getDestinationById(String destinationId) {
		return findById(destinationId);
	}

	/**
	 * 通过目的地ID查找景点的列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<View> getViewsByDesCode(String destinationCode) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String hql = "FROM View v WHERE v.destinationCode=:destinationCode and v.avaliable=1";
		paraMap.put("destinationCode", destinationCode);
		return (List<View>)find(hql, paraMap);
	}
    /**
     * 获取所有的目的地地点
     */
	@Override
	public List<Destination> getALLDestination() {
		return findAllAvaliable();
		//String sql = " SELECT * FROM destination ";
		//return findListBySql(Destination.class, sql);
	}
}
