package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.StrategyView;
import com.rimi.ctibet.web.dao.IStrategyViewDao;

@Repository("strategyViewDao")
public class StrategyViewDaoImpl extends BaseDaoImpl<StrategyView> implements IStrategyViewDao {
	
	/**
	 * 通过内容code删除
	 * @param contentode
	 */
	public int deleteByContentCode(final String contentCode){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                return arg0.createQuery("delete from "+domainClass.getName() +"  where contentCode=:contentCode").setParameter("contentCode", contentCode).executeUpdate();
            }
        });
	}
	
	/**
	 * 通过内容code查询攻略目的地code（景点为null的数据就是目的地code）
	 * @param contentCode
	 * @return
	 */
	public List<StrategyView> findDestinationCodeByContentCode(final String contentCode){
		return getHibernateTemplate().execute(new HibernateCallback<List<StrategyView>>() {
            public List<StrategyView> doInHibernate(Session arg0) throws HibernateException, SQLException {
                Criteria criteria = arg0.createCriteria(StrategyView.class);
                criteria.add(Restrictions.eq("contentCode", contentCode));
                criteria.add(Restrictions.or(
                        Restrictions.isNull("viewCode"), 
                        Restrictions.eq("viewCode","")
                ));
                return findByCriteria(criteria);
            }
        });
		
	}
	
	/**
	 * 通过内容code查询攻略景点code（目的地和景点不为null的数据就是景点code）
	 * @param contentCode
	 * @return
	 */
	public List<StrategyView> findViewCodeByContentCode(final String contentCode){
		return getHibernateTemplate().execute(new HibernateCallback<List<StrategyView>>() {
            public List<StrategyView> doInHibernate(Session arg0) throws HibernateException, SQLException {
                Criteria criteria = arg0.createCriteria(StrategyView.class);
                criteria.add(Restrictions.eq("contentCode", contentCode));
                criteria.add(Restrictions.not(Restrictions.eq("viewCode", "")));
                criteria.add(Restrictions.and(
                        Restrictions.isNotNull("destinationCode"), 
                        Restrictions.isNotNull("viewCode")
                ));
                
                return findByCriteria(criteria);
            }
        });
	}
	
}
