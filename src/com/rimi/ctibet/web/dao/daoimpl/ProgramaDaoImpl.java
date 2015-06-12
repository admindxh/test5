package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.CastTimeStamp2DateInResultHelper;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.dao.IProgramaDao;

@Repository("programaDao")
public class ProgramaDaoImpl extends BaseDaoImpl<Programa> implements
		IProgramaDao {
	@Override
	public Programa findByCode(String code) {
		final String hsql = "from  Programa  where available=1 and code ='" + code + "'";
		
		
		return getHibernateTemplate().execute(
				new HibernateCallback<Programa>() {
					@Override
					public Programa doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Query query = arg0.createQuery(hsql);
						List<Programa> list   = query.list();
						return   list!=null&&list.size()>=1?list.get(0):null;
					}
				});
	 }

	@Override
	public Pager getProgramaOrderByRank(Pager pager) {
		Map<String, Object> byMap = new HashMap<String, Object>();
		List<Programa> merchants = new ArrayList<Programa>();
		String hql = "from Programa p order by p.rank desc";
		return findWithPagerByMap(hql, byMap, pager);
	}
	@Deprecated
	@Override
	public void deletePrograma(Programa programa) {
		programa.setAvailable(0);
		getSession(false)
				.createSQLQuery(
						"UPDATE programa_content pc SET  pc.proCode = 0 WHERE pc.proCode = ? ")
				.setString(0, programa.getCode()).executeUpdate();
		this.updateAsHibernate(programa);
	}
	
	@Override
	public List<Programa> getTopPrograma() {
		Pager pager = new Pager();
		Map<String, Object> byMap = new HashMap<String, Object>();
		String hql = "select p  from Programa p  where pCode is null";
		pager = findWithPagerByMap(hql, byMap, pager);
		List<Programa> programas = (List<Programa>) pager.getDataList();
		return programas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Programa> getSendPrograma(final String code) {
	    String hql = "from Programa where  ( available=1  ) and  pCode = '"+code+"' order by rank ";
        return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Map<String, Object>> getContentByProgramaCode(String code) {
		List<Object> list = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder("SELECT c.*,p.programaName FROM programa p,content c,programa_content pc WHERE 1 = 1");
		if (StringUtils.isNotBlank(code)){
            sql.append(" AND p.`code` = ? AND pc.proCode = ?");
            list.add(code);
            list.add(code);
		}
        sql.append(" AND c.`code` = pc.conCode");
		return findByJDBCSql(sql.toString(), list);
	}

	/**
	 * 通过父栏目CODE找到子栏目
	 */
	@Override
	public List<Programa> getProgramaByPCode(String programaCode) {
		return findByProperty("pCode", programaCode);
	}
	@Override
	public Programa updateLogical(final Programa programa)
	{
		final String sql="update programa set available=0 where code='"+programa.getCode()+"'";
		getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query=session.createSQLQuery(sql);
				query.executeUpdate();
				return null;
			}
		});
		this.save(programa);
		return programa;
	}

    @Override
    public List<Programa> getReplyManagePrograma() {
        StringBuffer sql = new StringBuffer(" SELECT * FROM programa WHERE available=1 AND programaSummary='reply_manage' ORDER BY id ");
        return findListBySql(Programa.class, sql.toString());
    }
    
    /**
     * 通过栏目类型获取评价回复栏目
     */
    public Programa getReplyManageProgramaByType(String type){
        String sql = " SELECT * FROM programa WHERE available=1 AND programaSummary='reply_manage' AND enName=? ";
        return ListUtil.returnSingle(findListBySql(Programa.class, sql, new Object[]{type}));
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Pager findByParent(final Pager pager,Programa parentPrograma,String keyword)
    {
    	final String hql="from Programa where available=1 and pCode='"+parentPrograma.getCode()+"'";
    	getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query=session.createQuery(hql);
				query.setMaxResults(pager.getPageSize());
				query.setFirstResult(pager.getStartIndex());
				String pageSql="select count(*) from ( select * "+hql+" ) t";
				pager.setTotalCount(getJdbcTemplate().queryForInt(pageSql));
				pager.setDataList(query.list());
				return null;
			}
		});
    	return pager;
    }

	@Override
	public List<Programa> findByParent(final Programa condition) {
    	return getHibernateTemplate().execute(new HibernateCallback<List<Programa>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Programa> doInHibernate(Session session) throws HibernateException, SQLException {
				
				String hql = " from Programa where available = 1 ";
				
				Map<String, Object> pros = new HashMap<String, Object>();
				
				String pcode = condition.getpCode();
				if (StringUtils.isNotBlank(pcode)) {
					hql += " and pCode = :pCode ";
					pros.put("pCode", pcode);
				}
				
				String name = condition.getProgramaName();
				if (StringUtils.isNotBlank(name)) {
					hql += " and programaName = :programaName ";
					pros.put("programaName", name);
				}
				
				Query query = session.createQuery(hql);
					  query.setProperties(pros);
				
				return query.list();
			}
		});
	}

	@Override
	public Pager findByOrder(final Pager pager, final Programa programa) {
		return getHibernateTemplate().execute(new HibernateCallback<Pager>() {
			@Override
			public Pager doInHibernate(Session session) throws HibernateException, SQLException {
				
				String hql = " from Programa where available = 1 and pCode = '" + programa.getCode() + "' ";
				
				Query query = session.createQuery(hql + " order by code ");
					  query.setMaxResults(pager.getPageSize());
					  query.setFirstResult(pager.getStartIndex());
					  
				String pageSql = "select count(*) from (select * "+ hql +" ) t";
				
				pager.setTotalCount(getJdbcTemplate().queryForInt(pageSql));
				pager.setDataList(query.list());
				
				return pager;
			}
		});
	}
}