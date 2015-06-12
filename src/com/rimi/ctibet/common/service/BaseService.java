package com.rimi.ctibet.common.service;

import java.util.List;

import com.rimi.ctibet.common.util.Pager;


public interface BaseService<T> {
	
	public T updateLogical2(T t) throws Exception;
	
	/**
	 * 通过id查找对象查询 avaliable=1的数据
	 * @param id
	 * @return
	 */
	public T findById(int id);
	public <X> X findById(Class<X> clazz, int id);
	
	/**
	 * 根据code查询 avaliable=1的数据
	 */
	public T findByCode(String code);
	public T findByCodeWithoutVali(String code);
	public <X> X findByCode(Class<X> clazz, String code);
	
	/**
	 * 获取所有数据查询 avaliable=1的数据
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
	
	/**
	 * 保存对象
	 * @param t
	 */
	public void save(T t);
	
	/**
	 * 逻辑删除 将数据的avaliable字段更新为0
	 * @param id	主键
	 * @param propertyName	字段名（不传默认值是 id）
	 * @return
	 */
	public int deleteLogicalById(int id);
	public int deleteLogicalById(Class<?> clazz, int id);
	
	
	
	/**
	 * 根据对象的某个属性查询
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object value);
	
	
	/**
	 * 根据对象的某个属性查询
	 * @return
	 */
	public List<T> findByPropertyOrder(String propertyName, Object value,  String orderName,  String orderType  );
	
	/**
	 * 逻辑删除 根据code将数据的avaliable字段更新为0
	 * @param code
	 * @return
	 */
	public int deleteLogicalByCode(String code);
	public int deleteLogicalByCode(Class<?> clazz, String code);
	
	/**
	 * 更新对象（逻辑删除删除老数据保存新数据）
	 * @param t
	 * @return
	 * @throws Exception 
	 */
	public T updateLogical(T t);

	/**
	 * 更新对象
	 * @return
	 */
	public void update(T entity);
	
	/**
	 * 物理删除
	 * @param t
	 */
	public void delete(T t);

	/**
	 * 物理删除 通过id
	 * @param id
	 */
	public int deleteById(int id);
	public int deleteById(Class<?> clazz, int id);
	
	/**
	 * 物理删除 通过code
	 * @param id
	 */
	public int deleteByCode(String code);
	public int deleteByCode(Class<?> clazz, String code);
	public int deleteBySql(final String sql);
	
	public int findCountBySql(String sql, Object ... params);
	public int findCountBySql(String sql);
}
