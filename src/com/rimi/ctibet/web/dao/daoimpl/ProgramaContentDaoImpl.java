package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.ProgramaContent;
import com.rimi.ctibet.web.dao.IProgramaContentDao;

@Repository("programaContentDao")
public class ProgramaContentDaoImpl extends BaseDaoImpl<ProgramaContent>
		implements IProgramaContentDao {

	/**
	 * 通过栏目code和内容code来删除栏目和内容的映射关系
	 * 
	 * @param programaCode
	 * @param contentCode
	 * @return
	 */
	public int deleteProgramaContentByProCodeConCode(final String programaCode,
			final String contentCode) {
		final String hql = " delete from ProgramaContent where proCode=:programaCode and conCode=:contentCode ";


		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameter("programaCode",programaCode);
				query.setParameter("contentCode",contentCode);
				return query.executeUpdate();
			}
		});
//		Query query = getSession().createQuery(hql);
//		query.setParameter("programaCode", programaCode);
//		query.setParameter("contentCode", contentCode);
//		return  query.executeUpdate();
	}

}
