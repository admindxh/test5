package com.rimi.ctibet.common.dao.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.data.SqlResultData;
import com.rimi.ctibet.common.util.GenericsUtils;
import com.rimi.ctibet.common.util.Pager;
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T>{
	
    	
	private JdbcTemplate jdbcTemplate;
	
	
	protected Class domainClass; 
	
	
	public Session getMySession(){
	  return 	 getHibernateTemplate().execute(new HibernateCallback<Session>(){
			   @Override
				public Session doInHibernate(Session arg0){
						return arg0;
				}
		 } );
		
	}
	
    public BaseDaoImpl() {
    	
        this.domainClass = GenericsUtils.getSuperClassGenricType(getClass());
    }
    
    @Resource(name="sessionFactory")
	public void setSessionMyFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}
	



	@Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	/**
	 * 查询所有的有效数据 
	 * xiangwq
	 * @return
	 */
	public List<T> findAllAvaliable() {
		//return (List<T>) getHibernateTemplate().loadAll(domainClass);
		String hql = "from " + domainClass.getName() +" WHERE avaliable=1";
		return getHibernateTemplate().find(hql);
	}
	/**
	 * 查询所有的数据
	 * @return
	 */
	public List<T> findAll() {
		//return (List<T>) getHibernateTemplate().loadAll(domainClass);
		String hql = "from " + domainClass.getName() +" where avaliable=1";
		return getHibernateTemplate().find(hql);
	}
	public <X> List<X> findAll(Class<X> clazz) {
		//return (List<T>) getHibernateTemplate().loadAll(domainClass);
		String hql = "from " + clazz.getName();
		return getHibernateTemplate().find(hql);
	}
	/**
	 * 查询所有并分页的数据
	 * @return
	 */
	public Pager findAll(final Pager pager){
	    return getHibernateTemplate().execute(new HibernateCallback<Pager>() {
            public Pager doInHibernate(Session arg0) throws HibernateException, SQLException {
                String hql = "from " + domainClass.getName();
                Query query = arg0.createQuery(hql);
                query.setFirstResult(pager.getStartIndex());
                query.setMaxResults(pager.getPageSize());
                List<?> dataList = query.list();
                hql = "select count(*) " + hql;
                pager.setDataList(dataList);
                pager.setTotalCount(Integer.parseInt(arg0.createQuery(hql).list().get(0).toString()));
                int i = (int) Math.ceil(new Double(pager.getTotalCount()) / pager.getPageSize());
                pager.setTotalPage(i);
                return pager;
            }
        });
	}
	public Pager findAll(final Class<?> clazz, final Pager pager){
		return getHibernateTemplate().execute(new HibernateCallback<Pager>() {
            public Pager doInHibernate(Session arg0) throws HibernateException, SQLException {
                String hql = "from " + clazz.getName();
                Query query = arg0.createQuery(hql);
                query.setFirstResult(pager.getStartIndex());
                query.setMaxResults(pager.getPageSize());
                List<?> dataList = query.list();
                hql = "select count(*) " + hql;
                pager.setDataList(dataList);
                pager.setTotalPage(0);
                pager.setTotalPage(pager.getTotalPage());
            
                pager.setTotalCount(Integer.parseInt(arg0.createQuery(hql).list().get(0).toString()));
                int i = (int) Math.ceil(new Double(pager.getTotalCount()) / pager.getPageSize());
                pager.setTotalPage(i);
                return pager;
            }
        });
	}
	
	/**
	 * 根据参数查询
	 * @return
	 */
	public List find(String hql, Object... values) {
		return getHibernateTemplate().find(hql, values);
	}
	/**
	 * 根据参数查询
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List find(String hql, Map<String, Object> Param) {

		final String fhql = hql;
		final Map<String, Object> fParam = Param;
		return (List)getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session arg0)throws HibernateException, SQLException {
				Query query = arg0.createQuery(fhql);
				Iterator<String> keyIt = fParam.keySet().iterator();
				//System.out.println("HQL语句-----------------------------》" + fhql);
				while (keyIt.hasNext()) {
					String property = keyIt.next();
					//System.out.println("属性名：" + property + "属性值"+ fParam.get(property));
					query.setParameter(property, fParam.get(property));
				}
				arg0.clear();
				return query.list();
			}

		});

		//		
		//	
		// Query query = getSession().createQuery(hql);
		// Iterator<String> keyIt = Param.keySet().iterator();
		// //System.out.println("HQL语句-----------------------------》"+hql);
		// while (keyIt.hasNext()) {
		// String property=keyIt.next();
		// //System.out.println("属性名："+property+"属性值"+Param.get(property));
		// query.setParameter(property, Param.get(property));
		// }
		// getSession().close();
		// getHibernateTemplate().clear();
		// return query.list();
	}

	/**
	 * 根据参数查询并分页
	 * @return
	 */
	public Pager findWithPagerByArray(final String hql, final Pager pager, final Object... values) {

		return getHibernateTemplate().execute(new HibernateCallback<Pager>() {
            public Pager doInHibernate(Session arg0) throws HibernateException, SQLException {
                Query query = arg0.createQuery(hql);
                query.setFirstResult(pager.getStartIndex());
                query.setMaxResults(pager.getPageSize());
                for (int i = 1; i < values.length; i++) {
                    query.setParameter(i, values[i]);
                }
                List dataList = query.list();
                String hql_ = hql;
                if (hql_.toLowerCase().trim().startsWith("from")) {
                    hql_ = "select count(*) " + hql_;
                } else {
                    hql_ = hql_ = "select count(*) from (" + hql_ + ")";
                }
                pager.setDataList(dataList);
                pager.setTotalCount(Integer.parseInt(arg0.createQuery(hql_).list().get(0).toString()));
                int i = (int) Math.ceil(new Double(pager.getTotalCount()) / pager.getPageSize());
                pager.setTotalPage(i);
                return pager;
            }
        });
	}
	/**
	 * 根据参数查询并分页
	 * @return
	 */
	public Pager findWithPagerByMap(final String hql_, final Map<String, Object> Param, final Pager pager) {
		return getHibernateTemplate().execute(new HibernateCallback<Pager>() {
            public Pager doInHibernate(Session arg0) throws HibernateException, SQLException {
                String hql = hql_;
                Query query = arg0.createQuery(hql);
                Iterator<String> keyIt = Param.keySet().iterator();
                //System.out.println("HQL--------------------------------------->"+hql);
                while (keyIt.hasNext()) {
                    String property=keyIt.next();
                    //System.out.println("属性名："+property+"属性值"+Param.get(property));
                    query.setParameter(property, Param.get(property));
                }
                query.setFirstResult(pager.getStartIndex());
                query.setMaxResults(pager.getPageSize());
                //System.out.println("--------------------------------------------------"+pager.getStartIndex()+"=="+pager.getEndIndex());
                
                List dataList = query.list();
                if (hql.toLowerCase().trim().startsWith("from")) {
                    hql = "select count(*) " + hql;
                } else {
                    hql = "select count(*)  " + hql.replaceFirst("select[\\s\\S]*from", "from") +" ";
                }
                pager.setDataList(dataList);
                pager.setTotalCount(Integer.parseInt(find(hql, Param).get(0).toString()));
                int i = (int) Math.ceil(new Double(pager.getTotalCount()) / pager.getPageSize());
                pager.setTotalPage(i);
                return pager;
            }
        });
	}
	/**
	 * Cripager查询
	 * @return
	 */
	public List<T> findByCriteria(final Criteria criteria) {
		List list = criteria.list();
		return list;
	}

	/**
	 * DetachedCriteria查询
	 * @return
	 */
	public List<T> findByCriteria(final DetachedCriteria detachedCriteria) {
		return (List<T>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						List list = criteria.list();
						return list;
					}
				});
	}
	/**
	 * 根据对象查询
	 * @return
	 */
	public List<T> findByExample(T instance) {
		List<T> results = (List<T>) getHibernateTemplate().findByExample(instance);
		return results;
	}
	/**
	 * 根据id查询
	 * @return
	 */
	@Deprecated
	public Object findById(final Class clz,final String id) {
		return getHibernateTemplate().execute(new HibernateCallback<Object>() {
            public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
                Query query= arg0.createQuery("from "+clz.getName()+" where id=:id");
                query.setParameter("id", id);
                return query.uniqueResult();
            }
        });
	}
	/**
	 * 根据id查询
	 * @return
	 */
	@Deprecated
	public T findById(String id) {
		return (T) getHibernateTemplate().get(domainClass, id);
	}
	
	/**
	 * 根据id查询 avaliable=1的数据
	 */
	public T findById(int id) {
		String hql = "from " + domainClass.getName() + " where id=? and avaliable=1";
		List<T> list = getHibernateTemplate().find(hql, id);
		return (list!=null && list.size()>0)?(list.get(0)):(null);
	}
	public <X> X findById(Class<X> clazz, int id) {
		String hql = "from " + clazz.getName() + " where id=? and avaliable=1";
		List<X> list = getHibernateTemplate().find(hql, id);
		return (list!=null && list.size()>0)?(list.get(0)):(null);
	}
	
	/**
	 * 根据code查询 avaliable=1的数据
	 */
	public T findByCode(String code){
		String hql = "from " + domainClass.getName() + " where code=? and avaliable=1";
		List<T> list = getHibernateTemplate().find(hql, code);
		return (list!=null && list.size()>0)?(list.get(0)):(null);
	}
	public T findByCodeWithoutVali(String code){
		String hql = "from " + domainClass.getName() + " where code=?";
		List<T> list = getHibernateTemplate().find(hql, code);
		return (list!=null && list.size()>0)?(list.get(0)):(null);
	}
	public <X> X findByCode(Class<X> clazz, String code){
		String hql = "from " + clazz.getName() + " where code=? and avaliable=1";
		List<X> list = getHibernateTemplate().find(hql, code);
		return (list!=null && list.size()>0)?(list.get(0)):(null);
	}
	
	
	/**
	 *根据code查询数据 
	 */ 
	public T findByCodeAsHibernate(String code){
		String hql = "from " + domainClass.getName() + " where code=? ";
		List<T> list = getHibernateTemplate().find(hql, code);
		return (list!=null && list.size()>0)?(list.get(0)):(null);
	}
	
	

	/**
	 * 根据对象的某个属性查询
	 * @return
	 */
	public List<T> findByProperty(final String propertyName, final Object value) {
		
		List<T> list = getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException,
					SQLException {
				String queryString = "from " + domainClass.getName()
				+ " as model where model.avaliable=1 and model." + propertyName + "=:"+propertyName+"";
				Query query   = session.createQuery(queryString);
				query.setParameter(propertyName, value);
				return query.list();
			}
		});
		
		return  list;
	}
	
	
	/**
	 * 根据对象的某个属性查询
	 * @return
	 */
	public List<T> findByPropertyOrder(final String propertyName, final Object value, final String orderName, final String orderType ) {
		
		List<T> list = getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException,
					SQLException {
				String queryString = "from " + domainClass.getName()
				+ " as model where model.avaliable=1 and model." + propertyName + "=:"+propertyName+"  order  by model."+orderName+"  "+orderType;
				
				Query query   = session.createQuery(queryString);
				query.setParameter(propertyName, value);
				return query.list();
			}
		});
		
		return  list;
	}
	
	
	/**
	 * 根据对象的某个属性查询
	 * @return
	 */
	public List<T> findByProWithoutAvali(final String propertyName, final Object value) {
		
		List<T> list = getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException,
					SQLException {
				String queryString = "from " + domainClass.getName()
				+ " as model where model." + propertyName + "=:"+propertyName+"";
				Query query   = session.createQuery(queryString);
				query.setParameter(propertyName, value);
				return query.list();
			}
		});
		
		return  list;
	}
	/**
	 * 根据对象的某个属性查询
	 * @return
	 */
	public void save(T entity) {
		
		 getHibernateTemplate().save(entity);
	}
	/**
	 * 根据对象更新
	 * @return
	 */
	public void updateAsHibernate(T entity) {
		getHibernateTemplate().update(entity);
	}
	public T updateLogical2(T t) throws  Exception{
		
		
			//设置修改时间
			new PropertyDescriptor("lastEditTime", t.getClass()).getWriteMethod().invoke(t, new Date(System.currentTimeMillis()));
			//获取code值
			String code = new PropertyDescriptor("code", t.getClass()).getReadMethod().invoke(t).toString();
			//删除
			this.deleteLogicalByCode(code);
			//保存
			this.save(t);
		return t;
		
	}
	/**
	 * 更新对象（逻辑删除删除老数据保存新数据）
	 * @return
	 */
	public T updateLogical(T t) {
		try {
			//设置修改时间
			new PropertyDescriptor("lastEditTime", t.getClass()).getWriteMethod().invoke(t, new Date(System.currentTimeMillis()));
			//获取code值
			String code = new PropertyDescriptor("code", t.getClass()).getReadMethod().invoke(t).toString();
			//删除
			this.deleteLogicalByCode(code);
			//保存
			this.save(t);
		} catch (Exception e) {
			e.printStackTrace();
			//t = null;
		}
		return t;
	}
	
	
	
	/**
	 * 更新对象
	 * @return
	 */
	public void update(T t) {
		getHibernateTemplate().update(t);
	}
	
	/**
	 * 根据对象的id删除对象
	 * @return
	 */
	public int delete(final String id) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                return arg0.createQuery("delete  from "+domainClass.getName() +"  where id=:id").setParameter("id", id).executeUpdate();
            }
        });
	}

	/**
	 * 根据对象的id和对象class删除对象
	 * @return
	 */
	public int delete(final Class c,final String id) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                return arg0.createQuery("delete  from "+c.getName() +"  where id=:id").setParameter("id", id).executeUpdate();
            }
        });
	}
	
	/**
	 * 物理删除 根据code
	 * @param code
	 * @return
	 */
	public int deleteByCode(final String code){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                return arg0.createQuery("delete from "+domainClass.getName() +"  where code=:code").setParameter("code", code).executeUpdate();
            }
        });
	}
	public int deleteByCode(final Class<?> clazz, final String code){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                return arg0.createQuery("delete from "+clazz.getName() +"  where code=:code").setParameter("code", code).executeUpdate();
            }
        });
	}
	
	/**
	 * 逻辑删除 将数据的avaliable字段更新为0
	 * @param id	主键
	 * @return
	 */
	public int deleteLogicalById(final int id) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                String hql = " update " + domainClass.getName() + " set avaliable=0 where id=:id and avaliable=1";
                return arg0.createQuery(hql).setParameter("id", id).executeUpdate();
            }
        });
	}	
	public int deleteLogicalById(final Class<?> clazz, final int id) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                String hql = " update " + clazz.getName() + " set avaliable=0 where id=:id and avaliable=1";
                return arg0.createQuery(hql).setParameter("id", id).executeUpdate();
            }
        });
	}	
	
	/**
	 * 逻辑删除 根据code将数据的avaliable字段更新为0
	 * @param code
	 * @return
	 */
	public int deleteLogicalByCode(final String code) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                String hql = " update " + domainClass.getName() + " set avaliable=0 where code=:code and avaliable=1";
                return arg0.createQuery(hql).setParameter("code", code).executeUpdate();
            }
        });
	}
	public int deleteLogicalByCode(final Class<?> clazz, final String code) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                String hql = " update " + clazz.getName() + " set avaliable=0 where code=:code and avaliable=1";
                return arg0.createQuery(hql).setParameter("code", code).executeUpdate();
            }
        });
	}
	public int deleteLogicalByMemberCode(final Class<?> clazz, final String code) {
		
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                String hql = " update " + clazz.getName() + " set avaliable=0 where memberCode=:code and avaliable=1";
                return arg0.createQuery(hql).setParameter("code", code).executeUpdate();
            }
        });
	}
	
	/**
	 * 保存或更新对象  
	 * @return
	 */
	@Deprecated
	public void saveOrUpdate(T entity){
		getHibernateTemplate().saveOrUpdate(entity);
	}
	/**
	 * 根据对象的实体删除对象
	 * @return
	 */
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}
	/**
	 * 通过指定字段物理删除数据
	 * @param properrt
	 * @param code
	 * @return
	 */
	public int deleteByProperty(final String properrt, final String code){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                String hql = " delete from " + domainClass.getName() + " where "+ properrt +"='" + code+"'";
                return arg0.createQuery(hql).executeUpdate();
            }
        });
	}

	/**
	 * Criteria查询并分页
	 * @return
	 */
	public Pager findPageByCriteria(final Criteria criteria, Pager pager) {
		int totalCount = getCountByCriteria(criteria);
		criteria.setProjection(null);
		criteria.setFirstResult(pager.getStartIndex());
		if (pager.getEndIndex() > 0) {
			//criteria.setMaxResults(pager.getEndIndex());
			criteria.setMaxResults(pager.getPageSize());
		}
		List dataList = criteria.list();
		for (Object object : dataList) {
			//System.out.println("object:"+object);
		}
		pager.setDataList(dataList);
		pager.setTotalCount(totalCount);
		return pager;
	}

	/**
	 * Criteria查询总条数
	 * @return
	 */
	public int getCountByCriteria(final Criteria criteria) {
		Integer count = (Integer) criteria
				.setProjection(Projections.rowCount()).uniqueResult();
		return count.intValue();
	}

	/**
	 * DetachedCriteria查询并分页
	 * @return
	 */
	public Pager findPageByCriteria(final DetachedCriteria detachedCriteria,
			final Pager pager) {
		return (Pager) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);
				int totalCount = ((Integer) criteria.setProjection(
						Projections.rowCount()).uniqueResult()).intValue();
				criteria.setProjection(null);
				List items = criteria.setFirstResult(pager.getStartIndex())
						.setMaxResults(pager.getEndIndex()).list();
				pager.setDataList(items);
				pager.setTotalCount(totalCount);
				int i = (int) Math.ceil(new Double(pager.getTotalCount()) / pager.getPageSize());
			    pager.setTotalPage(i);
				return pager;
			}
		});
	}

	/**
	 * DetachedCriteria查询总条数
	 * @return
	 */
	public int getCountByCriteria(final DetachedCriteria detachedCriteria) {
		Integer count = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						return criteria.setProjection(Projections.rowCount())
								.uniqueResult();
					}
				});
		return count.intValue();
	}
	/**
	 * 根据query查询
	 * @return
	 */
	public List findByQuery(Query query) {
		return query.list();
	}
	/**
	 * 根据query查询并带参
	 * @return
	 */
	public List findByQuery(Query query, Map<String, Object> param) {
		Iterator<String> keyIt = param.keySet().iterator();
		while (keyIt.hasNext()) {
			String property=keyIt.next();
			query.setParameter(property, param.get(property));
		}
		return query.list();
	}
	public List find(Map<String, Object> Param) {
		String hql="from "+domainClass.getName()+" where avaliable=1 ";
		Iterator<String> keyIt = Param.keySet().iterator();
		while (keyIt.hasNext()) {
			String property=keyIt.next();
			hql=hql+" and "+property+"=:"+property;
		}
		return this.find(hql, Param);
	}
	
	/**
	 * 通过SQL 查询 返回dateList = List<Map> 
	* @Title: findPagerBySQL 
	* @Description: TODO
	* @param @param sql
	* @param @param params
	* @param @param pager
	* @param @return 
	* @return Pager
	* @throws
	 */
	public Pager findPagerBySQL(final String sql, final List params,final Pager pager){
	    
	    return getHibernateTemplate().execute(new HibernateCallback<Pager>() {
            public Pager doInHibernate(Session arg0) throws HibernateException, SQLException {

                // 定义返回值

                int pageNum = pager.getCurrentPage();
                int pageRows = pager.getTotalPage();
                // 先得到符合条件的行总数(如果需要分页则要提取总行数)
                int totalRows = 0;
                if (pageNum > 0 && pageRows > 0){
                    final String sqlTextCount = "SELECT COUNT(*) TOTALNUMBER FROM (" + sql + ") t";
                    SQLQuery query = (SQLQuery)arg0.createSQLQuery(sqlTextCount);
                    if (params != null && params.size() > 0) {
                        for (int i = 0; i < params.size(); i++) {
                            query.setParameter(i, params.get(i));
                        }
                    }
                    List<Map<String, Object>> recordCount = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                    totalRows = ((BigInteger) recordCount.get(0).get("TOTALNUMBER")).intValue();
                    pager.setTotalCount(totalRows);
                    int i = (int) Math.ceil(new Double(pager.getTotalCount()) / pager.getPageSize());
                    pager.setTotalPage(i);
                }else{
                    pager.setTotalCount(0);
                }
         
                // 计算应该提取的数据的开始行号(并计算真实的当前页)
                final int firstRow = pager.getStartIndex();
                final int maxResult = pager.getPageSize();

                // 提取数据
                final String sqlText = sql;
                SQLQuery query = (SQLQuery)arg0.createSQLQuery(sqlText);
                if (firstRow > 0) {
                    query.setFirstResult(firstRow);
                }
                if (maxResult > 0) {
                    query.setMaxResults(maxResult);
                }
                // 如果有参数则设置参数
                if (params != null && params.size() > 0) {
                    for (int i = 0; i < params.size(); i++) {
                        query.setParameter(i, params.get(i));
                    }
                }
                // //System.out.print(query.toString());
                // 提取数据
                 
                List<Map<String, Object>> recordList =(List<Map<String, Object>>) query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                pager.setDataList(recordList);

                // 返回结果数据
                return pager;
            }
        });
	    
	}
	
	/**
	 * 通过SQL 查询返回List
	* @Title: findListBysql 
	* @Description: TODO
	* @param @param sql
	* @param @param params
	* @param @return 
	* @return List
	* @throws
	 */
	public List findListBysql(final String sql, final List params){
	    return getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>() {
            public List<Map<String, Object>> doInHibernate(Session arg0) throws HibernateException, SQLException {
                SQLQuery query = (SQLQuery)arg0.createSQLQuery(sql);
                if (params != null && params.size() > 0) {
                    for (int i = 0; i < params.size(); i++) {
                        query.setParameter(i, params.get(i));
                    }
                }
                List<Map<String, Object>> recordList = (List<Map<String, Object>>) query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                List<Map<String, Object>> rs = changeMapKey(recordList);
                return recordList;
            }
        });
	}
	
	/**
	 * JDBC通过结果集元数据封装List结果集
	 * 
	 * @param sql
	 * @return
	 */
	@Deprecated
	public List<Map<String, Object>> findByJDBCSql(String sql, List<Object> params) {
		List<Map<String, Object>> mp  =     new ArrayList<Map<String,Object>>();
		if (params!=null&&params.size()>=1) {
			mp = getJdbcTemplate().queryForList(sql,params.toArray());
		}else{
			mp = getJdbcTemplate().queryForList(sql);
			
		}
		//将key全部转换为小写
		List<Map<String, Object>> rs = changeMapKey(mp);
		return rs;
		/*Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCConnectionUtil.getConnention();
			pstmt = conn.prepareStatement(sql);

			// 遍历参数
			if (params != null && params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = pstmt.getMetaData();

			// 取得结果集列数
			int columnCount = rsmd.getColumnCount();

			// 构造泛型结果集
			List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
			Map<String, Object> data = null;

			// 循环结果集
			while (rs.next()) {
				data = new HashMap<String, Object>();
				// 每循环一条将列名和列值存入Map
				for (int i = 1; i <= columnCount; i++) {
					if(rs.getObject(rsmd.getColumnLabel(i)) instanceof oracle.sql.TIMESTAMP){
						data.put(SqlResultData.changeKeyToHump(rsmd.getColumnLabel(i)), rs.getTimestamp(rsmd.getColumnLabel(i)).getTime());
					}else{
						data.put(SqlResultData.changeKeyToHump(rsmd.getColumnLabel(i)), rs.getObject(rsmd.getColumnLabel(i)));
					}
				}
				// 将整条数据的Map存入到List中
				datas.add(data);
			}
			return datas;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCConnectionUtil.free(rs, pstmt, conn);
		}*/
	}
	/**
	 * 将 查询的结果map的key转换成小写，并将下划线去掉
	 * @param mp
	 * @return
	 */
	private List<Map<String, Object>> changeMapKey(
			List<Map<String, Object>> mp) {
		List<Map<String,Object>> rs = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : mp) {
			Map tmpMap=new HashMap<String, Object>();
			for (String string : map.keySet()) {
				String tmpkey=SqlResultData.changeKeyToHump(string);
				tmpMap.put(tmpkey, map.get(string));
			}	
			rs.add(tmpMap);
		}
		return rs;
	}

	/**
	 * JDBC通过结果集元数据封装List结果集
	 * 
	 * @param sql
	 * @return
	 */
	
	@Deprecated
	public Pager findByJDBCSql(String sql, List<Object> params, Pager pager) {
		//获取数据行数
    	int pageNum = pager.getCurrentPage();
		int pageRows = pager.getTotalPage();
		int totalRows = 0;
		if (pageNum > 0 && pageRows > 0){
			String sqlTextCount = "SELECT COUNT(*) TOTALNUMBER FROM (" + sql + ") _t_ ";
			if (params != null && params.size() > 0) {
				totalRows = getJdbcTemplate().queryForInt(sqlTextCount, params.toArray());
			}else{
				totalRows = getJdbcTemplate().queryForInt(sqlTextCount);
			}
			pager.setTotalCount(totalRows);
		}else{
			pager.setTotalCount(0);
		}
		
		//拼接分页sql
		StringBuffer sqlPage = new StringBuffer();
    	StringBuffer prefix = new StringBuffer(" SELECT * FROM ( ");
    	StringBuffer suffix = new StringBuffer(" ) _t_ limit ");
    	sqlPage.append(prefix).append(sql).append(suffix);
    	int firstRow = pager.getStartIndex();
		int maxResult = pager.getPageSize();
		sqlPage.append(firstRow).append(",").append(maxResult);
		
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		if(params != null && params.size() > 0){
			listMap = getJdbcTemplate().queryForList(sqlPage.toString(), params.toArray());
			String p_ = "";
    		for (Object p : params) {
				p_ += p + ", ";
			}
    		logger.debug("-> 参数：" + p_);
		}else{
			listMap = getJdbcTemplate().queryForList(sqlPage.toString());
		}
		pager.setDataList(listMap);
		pager.setTotalPage(pager.getTotalPage());
    	return pager;
		/*Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCConnectionUtil.getConnention();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int pageNum = pager.getCurrentPage();
			int pageRows = pager.getTotalPage();
			// 先得到符合条件的行总数(如果需要分页则要提取总行数)
			int totalRows = 0;
			if (pageNum > 0 && pageRows > 0) {
				final String sqlCount = "SELECT COUNT(*) TOTALNUMBER FROM (" + sql + ") as w";
				PreparedStatement totalPstmt = conn.prepareStatement(sqlCount, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				if (params != null && params.size() > 0) {
					for (int i = 0; i < params.size(); i++) {
						totalPstmt.setObject(i + 1, params.get(i));
					}
				}
				ResultSet totalRs = totalPstmt.executeQuery();
				totalRs.first();
				totalRows = totalRs.getInt(1);
				pager.setTotalCount(totalRows);
			} else {
				pager.setTotalCount(0);
			}

			// 遍历参数
			if (params != null && params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}
			}

			// 计算应该提取的数据的开始行号(并计算真实的当前页)
			final int firstRow = pager.getStartIndex();
			final int maxResult = pager.getPageSize();

			pstmt.setMaxRows(maxResult + firstRow);

			rs = pstmt.executeQuery();

			// 将游标移动到第一条记录
			rs.first();
			// 游标移动到要输出的第一条记录
			rs.relative(firstRow - 1);

			ResultSetMetaData rsmd = pstmt.getMetaData();

			// 取得结果集列数
			int columnCount = rsmd.getColumnCount();

			// 构造泛型结果集
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			Map<String, Object> data = null;

			// 循环结果集
			while (rs.next()) {
				data = new HashMap<String, Object>();
				// 每循环一条将列名和列值存入Map
				for (int i = 1; i <= columnCount; i++) {
					if(rs.getObject(rsmd.getColumnLabel(i)) instanceof oracle.sql.TIMESTAMP){
						data.put(SqlResultData.changeKeyToHump(rsmd.getColumnLabel(i)), rs.getTimestamp(rsmd.getColumnLabel(i)).getTime());
					}else{
						data.put(SqlResultData.changeKeyToHump(rsmd.getColumnLabel(i)), rs.getObject(rsmd.getColumnLabel(i)));
					}
				}
				// 将整条数据的Map存入到List中
				dataList.add(data);
			}
			pager.setDataList(dataList);
			return pager;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCConnectionUtil.free(rs, pstmt, conn);
		}*/
	}
	
    /**
     * JDBC 返回指定行数的结果集对象
     * @param clazz
     * @param sql
     * @param params
     * @return
     */
	public final <X> List<X> findListBySqlRow(Class<X> clazz, int row, String sql, List<Object> params){
        return findListBySqlRow(clazz, row, sql, params.toArray());
    }
    public final <X> List<X> findListBySqlRow(Class<X> clazz, int row, String sql, Object ... params){
    	StringBuffer sqlPage = new StringBuffer();
    	StringBuffer prefix = new StringBuffer(" SELECT * FROM ( ");
    	StringBuffer suffix = new StringBuffer(" ) _t_ limit ");
    	sqlPage.append(prefix).append(sql).append(suffix);
		sqlPage.append(row);
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		if(params != null && params.length > 0){
			listMap = getJdbcTemplate().queryForList(sqlPage.toString(), params);
			String p_ = "";
            for (Object p : params) {
                p_ += p + ", ";
            }
            logger.debug("-> 参数：" + p_);
		}else{
			listMap = getJdbcTemplate().queryForList(sqlPage.toString());
		}
		/*List<X> datelist = null;
        if(params != null && params.length > 0){
            datelist = getJdbcTemplate().query(sqlPage.toString(), new BeanPropertyRowMapper<X>(clazz), params);
            //System.out.print("-> 参数：");
            for (Object p : params) {
                //System.out.print(p + ", ");
            }
            //System.out.println();
        }else{
            datelist= getJdbcTemplate().query(sqlPage.toString(), new BeanPropertyRowMapper<X>(clazz));
        }
		return datelist;*/
		return SqlResultData.mapToList(clazz, listMap);
    }
    
    /**
     * JDBC 返回结果集对象
     * @param clazz
     * @param sql
     * @param params
     * @return
     */
    public final <X> List<X> findListBySql(Class<X> clazz, String sql, List<Object> params){
        return findListBySql(clazz, sql, params.toArray());
    }
    public final <X> List<X> findListBySql(Class<X> clazz, String sql, Object ... params){
    	List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
    	//List<X> datelist = null;
    	if(params!=null && params.length>0){
    		listMap = getJdbcTemplate().queryForList(sql, params);
    	    //datelist = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<X>(clazz), params);
    		String p_ = "";
            for (Object p : params) {
                p_ += p + ", ";
            }
            logger.debug("-> 参数：" + p_);
    	}else{
    		listMap = getJdbcTemplate().queryForList(sql);
    	    //datelist= getJdbcTemplate().query(sql, new BeanPropertyRowMapper<X>(clazz));
    	}
    	return SqlResultData.mapToList(clazz, listMap);
    	//return datelist;
    }
    
    /**
     * JDBC 返回结果集分页对象 pager
     * @param clazz
     * @param sql
     * @param params
     * @param pager
     * @return
     */
    public final <X> Pager findListPagerBySql(Class<X> clazz, Pager pager, String sql, List<Object> params){
    	return findListPagerBySql(clazz, pager, sql, params.toArray());
    }
    public final <X> Pager findListPagerBySql(Class<X> clazz, Pager pager, String sql, Object ... params){
    	//获取数据行数
    	
    	int pageNum = pager.getCurrentPage();
		int pageRows = pager.getTotalPage();
		int totalRows = 0;
		if (pageNum > 0 && pageRows > 0){
			String sqlTextCount = "SELECT COUNT(*) TOTALNUMBER FROM (" + sql + ") _t_ ";
			if (params != null && params.length > 0) {
				totalRows = getJdbcTemplate().queryForInt(sqlTextCount, params);
			}else{
				totalRows = getJdbcTemplate().queryForInt(sqlTextCount);
			}
			pager.setTotalCount(totalRows);
		}else{
			pager.setTotalCount(0);
		}
		
		//拼接分页sql
		StringBuffer sqlPage = new StringBuffer();
    	StringBuffer prefix = new StringBuffer(" SELECT * FROM ( ");
    	StringBuffer suffix = new StringBuffer(" ) _t_ limit ");
    	sqlPage.append(prefix).append(sql).append(suffix);
    	int firstRow = pager.getStartIndex();
		int maxResult = pager.getPageSize();
		sqlPage.append(firstRow).append(",").append(maxResult);
		
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		//List<X> datelist = null;
		if(params != null && params.length > 0){
			listMap = getJdbcTemplate().queryForList(sqlPage.toString(), params);
			//datelist = getJdbcTemplate().query(sqlPage.toString(), new BeanPropertyRowMapper<X>(clazz), params);
			String p_ = "";
            for (Object p : params) {
                p_ += p + ", ";
            }
            logger.debug("-> 参数：" + p_);
		}else{
			listMap = getJdbcTemplate().queryForList(sqlPage.toString());
			//datelist= getJdbcTemplate().query(sqlPage.toString(), new BeanPropertyRowMapper<X>(clazz));
		}
		pager.setDataList(SqlResultData.mapToList(clazz, listMap));
		//pager.setDataList(datelist);
		pager.setTotalPage(pager.getTotalPage());
    	return pager;
    }
	
    /**
     * JDBC 获取sql数据行数
     * @param sql
     * @param params
     * @return
     */
    public final int findCountBySql(String sql, List<Object> params){
        return findCountBySql(sql, params.toArray());
    }
    public final int findCountBySql(String sql, Object ... params){
        String sqlCount = "SELECT COUNT(*) TOTALNUMBER FROM (" + sql + ") _t_ ";
        int totalRows = 0;
        if (params != null && params.length > 0) {
            totalRows = getJdbcTemplate().queryForInt(sqlCount, params);
            String p_ = "";
            for (Object p : params) {
                p_ += p + ", ";
            }
            logger.debug("-> 参数：" + p_);
        }else{
            totalRows = getJdbcTemplate().queryForInt(sqlCount);
        }
        return totalRows;
    }
    public int findCountBySql(String sql){
    	 int totalRows = 0;
    	 totalRows =  getJdbcTemplate().queryForInt(sql);
    	 return totalRows;
    }

	@Override
	public int deleteBySql(final String sql) {
		 getHibernateTemplate().execute( new HibernateCallback() {
			  @Override
			public Object doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				// TODO Auto-generated method stub
				return  arg0.createSQLQuery(sql).executeUpdate();
			}
		});
		return 0;
	}

	

}