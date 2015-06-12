package com.rimi.ctibet.common.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;

import com.rimi.ctibet.common.util.Pager;

public interface BaseDao<T> {
	/**
	 * 查询所有avaliable=1的数据
	 * @return
	 */
	public List<T> findAll();
	public <X> List<X> findAll(Class<X> clazz);
	/**
	 * 查询所有并分页avaliable=1的数据
	 * @return
	 */
	public Pager findAll(Pager pager);
	public Pager findAll(Class<?> clazz, Pager pager);
	public List<T> findAllAvaliable();
	/**
	 * 根据参数查询
	 * @return
	 */
	public List find(String hql, Object... values);
	/**
	 * 根据参数查询
	 * @return
	 */
	public List find(String hql, Map<String, Object> Param);
	/**
	 * 根据参数查询
	 * @return
	 */
	public List find(Map<String, Object> Param);
	/**
	 * 根据参数查询并分页
	 * @return
	 */
	public Pager findWithPagerByArray(String hql, Pager pager, Object... values);
	/**
	 * 根据参数查询并分页
	 * @return
	 */
	public Pager findWithPagerByMap(String hql, Map<String, Object> Param, Pager pager);
	/**
	 * Criteria查询
	 * @return
	 */
	public List<T> findByCriteria(final Criteria criteria);
	/**
	 * DetachedCriteria查询
	 * @return
	 */
	public List<T> findByCriteria(final DetachedCriteria detachedCriteria);
	/**
	 * 根据对象查询
	 * @return
	 */
	public List<T> findByExample(T instance) ;
	/**
	 * 根据id查询
	 * @return
	 */
	@Deprecated
	public T findById(String id);
	
	/**
	 * 根据id查询 avaliable=1的数据
	 */
	public T findById(int id);
	public <X> X findById(Class<X> clazz, int id);

	/**
	 * 根据code查询 avaliable=1的数据
	 */
	public T findByCode(String code);
	public T findByCodeWithoutVali(String code);
	public T findByCodeAsHibernate(String code);
	public <X> X findByCode(Class<X> clazz, String code);

	/**
	 * 根据对象的某个属性查询
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object value);
	
	
	
	/**
	 * 根据对象的某个属性查询 排序
	 * @return
	 */
	public List<T> findByPropertyOrder(String propertyName, Object value,String orderName,String orderType);
	
	public List<T> findByProWithoutAvali(final String propertyName, final Object value);
	/**
	 * 根据对象的某个属性查询
	 * @return
	 */
	public void save(T entity);

	/**
	 * 更新对象（逻辑删除删除老数据保存新数据）
	 * @return
	 */
	public T updateLogical(T entity);
	
	
	public T updateLogical2(T t) throws Exception;
	
	
	/**
	 * 更新对象
	 * @return
	 */
	public void update(T entity);
	
	/**
	 * 根据对象的id删除对象
	 * @return
	 */
	public int delete(String id) ;

	/**
	 * 根据对象的id和对象class删除对象
	 * @return
	 */
	public int delete(Class c,String id);
	
	/**
	 * 物理删除 根据code
	 * @param code
	 * @return
	 */
	public int deleteByCode(String code);
	public int deleteByCode(Class<?> clazz, String code);
	
	/**
	 * 逻辑删除 将数据的avaliable字段更新为0
	 * @param id	主键
	 * @return
	 */
	public int deleteLogicalById(int id);
	public int deleteLogicalById(Class<?> clazz, int id);
	
	/**
	 * 逻辑删除 根据code将数据的avaliable字段更新为0
	 * @param code
	 * @return
	 */
	public int deleteLogicalByCode(String code);
	public int deleteLogicalByCode(Class<?> clazz, String code);
	
	/**
	 * 保存或更新对象  
	 * @return
	 */
	@Deprecated
	public void saveOrUpdate(T entity);
	/**
	 * 根据对象的实体删除对象
	 * @return
	 */
	public void delete(T entity);
	
	/**
	 * 通过指定字段物理删除数据
	 * @param properrt
	 * @param code
	 * @return
	 */
	public int deleteByProperty(String properrt, String code);

	/**
	 * Criteria查询并分页
	 * @return
	 */
	public Pager findPageByCriteria(final Criteria criteria, Pager pager);

	/**
	 * Criteria查询总条数
	 * @return
	 */
	public int getCountByCriteria(final Criteria criteria);

	/**
	 * DetachedCriteria查询并分页
	 * @return
	 */
	public Pager findPageByCriteria(final DetachedCriteria detachedCriteria,
			final Pager pager);

	/**
	 * DetachedCriteria查询总条数
	 * @return
	 */
	public int getCountByCriteria(final DetachedCriteria detachedCriteria);
	/**
	 * 根据query查询
	 * @return
	 */
	public List findByQuery(Query query);
	/**
	 * 根据query查询并带参
	 * @return
	 */
	public List findByQuery(Query query, Map<String, Object> param);
	
	/**
	 * SQL查询分页
	* @Title: findPagerBySQL 
	* @Description: TODO
	* @param @param sql
	* @param @param params
	* @param @param pager
	* @param @return 
	* @return Pager
	* @throws
	 */
	public Pager findPagerBySQL(String sql, final List params,Pager pager);
	
	
	/**
	 * sql查询返回list
	* @Title: findListBysql 
	* @Description: TODO
	* @param @param sql
	* @param @param params
	* @param @return 
	* @return List
	* @throws
	 */
	public List findListBysql(String sql, final List params);
	
	/**
	 * JDBC通过结果集元数据封装List结果集
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findByJDBCSql(String sql, List<Object> params);
	
	/**
	 * JDBC通过结果集元数据封装Pager结果集
	 * @param sql
	 * @param params
	 * @param pager
	 * @return
	 */
	public Pager findByJDBCSql(String sql, List<Object> params, Pager pager);
	
	/**
	 * 按照hibernate方式更新
	 */
    public void updateAsHibernate(T entity);
    
    /**
     * JDBC 返回指定行数的结果集对象
     * @param clazz
     * @param sql
     * @param params
     * @return
     */
    public <X> List<X> findListBySqlRow(Class<X> clazz, int row, String sql, Object ... params);
    public <X> List<X> findListBySqlRow(Class<X> clazz, int row, String sql, List<Object> params);
    /**
     * JDBC 返回结果集对象
     * @param clazz
     * @param sql
     * @param params
     * @return
     */
    public <X> List<X> findListBySql(Class<X> clazz, String sql, Object ... params);
    public <X> List<X> findListBySql(Class<X> clazz, String sql, List<Object> params);
    /**
     * JDBC 返回结果集分页对象 pager
     * @param clazz
     * @param pager
     * @param sql
     * @param params
     * @return
     */
    public <X> Pager findListPagerBySql(Class<X> clazz, Pager pager, String sql, Object ... params);
    public <X> Pager findListPagerBySql(Class<X> clazz, Pager pager, String sql, List<Object> params);
    /**
     * JDBC 获取sql数据行数
     * @param sql
     * @param params
     * @return
     */
    public int findCountBySql(String sql, Object ... params);
    
    public int findCountBySql(String sql, List<Object> params);
    public int findCountBySql(String sql);
    public int deleteLogicalByMemberCode(Class<?> clazz, String code);
    
    public int deleteBySql(String sql);
    
}
