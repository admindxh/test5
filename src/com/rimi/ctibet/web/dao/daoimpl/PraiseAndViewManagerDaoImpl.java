package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.PraiseAndViewManager;
import com.rimi.ctibet.web.dao.IPraiseAndViewManagerDao;
@Repository("praiseAndViewManagerDao")
public class PraiseAndViewManagerDaoImpl extends BaseDaoImpl<PraiseAndViewManager> implements IPraiseAndViewManagerDao{

	@Override
	public void deleteRercord(PraiseAndViewManager pvm) {
		pvm.setAvaliable(0);
		super.updateAsHibernate(pvm);
	}

	@Override
	public PraiseAndViewManager getRecordByCode(String code) {
		return super.findByCode(code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isRecored(final PraiseAndViewManager pvm) {
		 boolean flag = false;

		List<PraiseAndViewManager> list = getHibernateTemplate().execute(new HibernateCallback<List<PraiseAndViewManager>>() {
			@Override
			public List<PraiseAndViewManager> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(" from PraiseAndViewManager pvm where pvm.contentCode = ? and pvm.ip = ? ").setString(0,pvm.getContentCode()).setString(1,pvm.getIp()).list();
			}
		});
		 if (list!=null&&list.size()>=1) {
		     flag  = true;	
		}
		return flag;
	}

	@Override
	public void saveRecord(PraiseAndViewManager pvm) {
       super.save(pvm); 		
	}
}
