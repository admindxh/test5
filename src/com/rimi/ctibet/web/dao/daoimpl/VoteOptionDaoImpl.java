package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.VoteOption;
import com.rimi.ctibet.web.dao.IVoteOptionDao;

@Repository("voteOptionDao")
public class VoteOptionDaoImpl extends BaseDaoImpl<VoteOption> implements IVoteOptionDao {
	
	/**
	 * 通过活动code获取选项
	 * @param activityCode
	 * @return
	 */
	public List<VoteOption> getVoteOptionsByActivityCode(String activityCode){
	    List<Object> params = new ArrayList<Object>();
		String sql = " SELECT * FROM vote_option WHERE activityCode=? ";
		params.add(activityCode);
		return findListBySql(VoteOption.class, sql, params);
	}
	
	/**
	 * 投票
	 */
	public void updateVoteOptionCountByCode(final String code){
		HibernateCallback<Object> hibernateCallback = new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(" UPDATE vote_option SET counts = counts + 1,fakeCounts = fakeCounts + 1 WHERE code=:code ");
				sqlQuery.setParameter("code", code);
				sqlQuery.setParameter("code", code);
				sqlQuery.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(hibernateCallback);
	}
	
	/**
	 * 通过选项code 更新伪投票数
	 * @param colums
	 * @param value
	 */
	public void updateFakeCountsByCode(final String code, final int fakeCounts){
		HibernateCallback<Object> hibernateCallback = new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = " UPDATE vote_option SET fakeCounts=:fakeCounts WHERE code=:code ";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.setParameter("code", code);
				sqlQuery.setParameter("fakeCounts", fakeCounts);
				sqlQuery.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(hibernateCallback);
	}
	/**
	 * 通过选项code获取选项
	 */
	public VoteOption getVoteOptionByCode(String code){
	    List<Object> params = new ArrayList<Object>();
		String sql = " SELECT * FROM vote_option WHERE code=? ";
		params.add(code);
		List<VoteOption> list = findListBySql(VoteOption.class, sql, params);
		return (list!=null && list.size()>0)?(list.get(0)):(null);
	}
}
