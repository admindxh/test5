package com.rimi.ctibet.common.service.impl;

import java.beans.PropertyDescriptor;
import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.GenericsUtils;
import com.rimi.ctibet.common.util.Pager;

public class BaseServiceImpl<T> implements BaseService<T> {

	//protected abstract BaseDao<T> getDao();
	
	public T updateLogical2(T t) throws Exception{
		return getDao().updateLogical2(t);
		
		
	}
	protected Class<T> domainClass;
	
	
	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		domainClass= GenericsUtils.getSuperClassGenricType(getClass());
	}
	@SuppressWarnings("unchecked")
	private BaseDao<T> getDao(){
		StringBuilder beanDao = new StringBuilder(domainClass.getSimpleName()).append("Dao");
		//beanDao.setCharAt(0, Character.toLowerCase(beanDao.charAt(0)));
		try {
			return (BaseDao<T>) new PropertyDescriptor(beanDao.toString(), this.getClass()).getReadMethod().invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("-> " + this.getClass().getName() + " 需要 " + beanDao + "属性的get方法.");
		}
		return null;
	}
	
	/**
	 * 根据对象的某个属性查询
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object value){
		return   getDao().findByProperty(propertyName, value);
	}
	
	/**
	 * 根据对象的某个属性查询
	 * @return
	 */
	public List<T> findByPropertyOrder(String propertyName, Object value,  String orderName,  String orderType  ){
		
		return   getDao().findByPropertyOrder(propertyName, value,orderName,orderType); 
		
	}
	
	
	/**
	 * 通过id查找对象查询 avaliable=1的数据
	 * @param id
	 * @return
	 */
	public T findById(int id) {
		return getDao().findById(id);
	}
	public <X> X findById(Class<X> clazz, int id){
		return getDao().findById(clazz, id);
	}
	
	/**
	 * 根据code查询 avaliable=1的数据
	 */
	public T findByCode(String code){
		return getDao().findByCode(code);
	}
	public T findByCodeWithoutVali(String code){
		return getDao().findByCodeWithoutVali(code);
	}
	public <X> X findByCode(Class<X> clazz, String code){
		return getDao().findByCode(clazz, code);
	}
	
	/**
	 * 获取所有数据 查询 avaliable=1的数据
	 * @return
	 */
	public List<T> findAll(){
		return getDao().findAll();
	}
	public <X> List<X> findAll(Class<X> clazz){
		return getDao().findAll(clazz);
	}
	/**
	 * 查询所有并分页avaliable=1的数据
	 * @return
	 */
	public Pager findAll(Pager pager){
		return getDao().findAll(pager);
	}
	public Pager findAll(Class<?> clazz, Pager pager){
		return getDao().findAll(clazz, pager);
	}

	/**
	 * 保存对象
	 * @param t
	 */
	public void save(T t) {
		getDao().save(t);
	}
	/**
	 * 逻辑删除 将数据的avaliable字段更新为0
	 * @param id	主键
	 * @return
	 */
	public int deleteLogicalById(int id) {
		return getDao().deleteLogicalById(id);
	}
	public int deleteLogicalById(Class<?> clazz, int id) {
		return getDao().deleteLogicalById(clazz, id);
	}
	
	/**
	 * 逻辑删除 根据code将数据的avaliable字段更新为0
	 * @param code
	 * @return
	 */
	public int deleteLogicalByCode(String code){
		return getDao().deleteLogicalByCode(code);
	}
	public int deleteLogicalByCode(Class<?> clazz, String code){
		return getDao().deleteLogicalByCode(clazz, code);
	}

	/**
	 * 更新对象（逻辑删除删除老数据保存新数据）
	 * @param t
 	 * @return
	 */
	public T updateLogical(T t) {
		return getDao().updateLogical(t);
	}
	
	/**
	 * 更新对象
	 * @return
	 */
	public void update(T entity){
		getDao().update(entity);
	}
	
	/**
	 * 物理删除
	 * @param t
	 */
	public void delete(T t) {
		getDao().delete(t);
	}
	
	/**
	 * 物理删除 通过id
	 * @param id
	 */
	public int deleteById(int id) {
		return getDao().delete(id+"");
	}
	public int deleteById(Class<?> clazz, int id) {
		return getDao().delete(clazz, id+"");
	}
	
	/**
	 * 物理删除 通过code
	 * @param id
	 */
	public int deleteByCode(String code) {
		return getDao().deleteByCode(code);
	}
	public int deleteByCode(Class<?> clazz, String code) {
		return getDao().deleteByCode(clazz, code);
	}
	public int deleteBySql(final String sql){
		
	return 	getDao().deleteBySql(sql);
		
	}
	public int findCountBySql(String sql, Object ... params){
		return getDao().findCountBySql(sql, params);
		
	}
	public int findCountBySql(String sql){
   	 return getDao().findCountBySql(sql);
   }
}
