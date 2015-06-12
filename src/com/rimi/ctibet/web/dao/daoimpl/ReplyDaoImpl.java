package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.Reply;
import com.rimi.ctibet.web.dao.IReplyDao;

@Repository("replyDao")
public class ReplyDaoImpl extends BaseDaoImpl<Reply> implements IReplyDao {
	
	/**
	 * 通过帖子code物理删除帖子和贴回复以及子回复的关联关系
	 * @param postCode
	 * @return
	 */
	public int deletePostByPostCode(final String postCode){
	    return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

            @Override
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                StringBuffer sql = new StringBuffer();
                sql.append("");
                sql.append(" UPDATE reply SET code='1' WHERE postCode IN ( ");
                sql.append("    SELECT contentCode FROM ( ");
                sql.append("        SELECT contentCode FROM reply WHERE postCode=:postCode ");
                sql.append("     ) x ");
                sql.append(" ) ");
                sql.append(" OR postCode =:postCode ");
                SQLQuery sqlQuery = arg0.createSQLQuery(sql.toString());
                return sqlQuery.setParameter("postCode", postCode).executeUpdate();
            }
	        
        });
	}
	
	@Override
    public void deleteReplyByPostCode(final String postCode){
	    getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                String sql = "DELETE FROM reply WHERE postCode=:postCode ";
                SQLQuery sqlQuery = arg0.createSQLQuery(sql);
                sqlQuery.setParameter("postCode", postCode);
                return sqlQuery.executeUpdate();
            }
        });
	}
	
}
