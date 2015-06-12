package com.rimi.ctibet.web.dao.daoimpl;


import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.web.dao.IMemberInfoDao;
@Repository("memberInfoDao")
public class MemberInfoDaoImpl extends BaseDaoImpl<MemberInfo> implements IMemberInfoDao {

    @Override
    public void updateMemberInfoScore(final String memberCode, final int scroe) {
        getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                String sql = " UPDATE member_info SET score= ifnull(score,0)+ " + scroe + " WHERE avaliable=1 AND memberCode='" + memberCode + "' ";
                return arg0.createSQLQuery(sql).executeUpdate();
            }
        });
        
    }

}
