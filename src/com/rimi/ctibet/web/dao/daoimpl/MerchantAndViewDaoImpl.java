package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.MerchantAndView;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.dao.IMerchantAndViewDao;
import com.rimi.ctibet.web.dao.IMerchantDao;

@Repository("merchantAndViewDao")
public class MerchantAndViewDaoImpl extends BaseDaoImpl<MerchantAndView>
		implements IMerchantAndViewDao {
	@Resource
	private IMerchantDao merchantDao;

	/**
	 * 通过VIEWCODE得到MERCHANT
	 */
	@Override
	public List<Merchant> getMerchantByView(String code) {
		List<MerchantAndView> mvList = findByProperty("viewCode", code);
		List<Merchant> merchantList = new ArrayList<Merchant>();
		if (mvList.size() > 0) {
			for (MerchantAndView mav : mvList) {
				merchantList.add(merchantDao.findByCode(mav.getMerchantCode()));
			}
		}
		return merchantList;
	}

	@Override
	public List<View> getViewsByMCode(String code) {
		// TODO Auto-generated method stub
		String sql = "select DISTINCT tview.viewName  ,tview.code  from  merchant_view mv left join  tview   on mv.viewCode  = tview.`code`  ";
		if (StringUtils.isNotBlank(code)) {
			sql += " and mv.merchantCode ='" + code
					+ "'  HAVING  tview.code is not NULL";
		}
		// sql += " group by tview.`code` ";
		return this.findListBySql(View.class, sql);
	}

	@Override
	public void deleteByMerchatCode(final String merchantCode) {
		// TODO Auto-generated method stub

		Object o = getHibernateTemplate().execute(
				new HibernateCallback<Object>() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						// TODO Auto-generated method stub
						String sql = " delete from  MerchantAndView mv where 1=1   ";
						if (StringUtils.isNotBlank(merchantCode)) {
							sql = sql + " and mv.merchantCode='" + merchantCode
									+ "' ";
						}
						Query query = session.createQuery(sql);
						return query.executeUpdate();
					}
				});
	}

}
