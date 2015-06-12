package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.MerchantType;
import com.rimi.ctibet.web.dao.IMerchantTypeDao;

@Repository("merchantTypeDao")
public class MerchantTypeDaoImpl extends BaseDaoImpl<MerchantType> implements IMerchantTypeDao{

	@Override
	public  List<MerchantType> getMerchantTypeOederByRank() {
		// TODO Auto-generated method stub
		final String hql = "from MerchantType mt order by mt.rank";
		 List<MerchantType>  list  = 	getHibernateTemplate().execute(new HibernateCallback< List<MerchantType>>() {
			@Override
			public  List<MerchantType> doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				// TODO Auto-generated method stub
				Query query  = arg0.createQuery(hql);
				return   query.list();
			}
		});
		return list;
	}
	
	@Override
	public MerchantType getByCode(String code) {
		String hql = "FROM MerchantType m WHERE m.code=:code";
	    Map<String, Object> para = new HashMap<String, Object>();
	    para.put("code", code);
	    List<MerchantType> list = find(hql, para);
	    if(list.size()>0){
	    	return list.get(0);
	    }
	    return null;
	}
	
	public static void main(String[] args) {
		MerchantTypeDaoImpl t = new MerchantTypeDaoImpl();
		List<MerchantType> merchantTypes = t.getMerchantTypeOederByRank();
                   //System.out.println(merchantTypes); 
		//		for (MerchantType merchantType : merchantTypes) {
//			//System.out.println(merchantType.getName());
//		}
	}

	@Override
	public Pager getAllMerchantType(Pager pager) {
		
		return findAll(pager);
	}

	@Override
	public Pager searchMerchantType(Pager pager, String keyWord) {
		String sql ="SELECT mt.* FROM merchant_type mt  ";
		List<Object> params = new ArrayList<Object>();
		if(StringUtils.isNotBlank(keyWord)){
		   params.add("%"+keyWord+"%");
		   sql += " WHERE mt.`name` LIKE ? ";
		}
		sql += " order by mt.createTime desc";
		return findListPagerBySql(MerchantType.class, pager, sql, params);
	}
}
