package com.rimi.ctibet.common.util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * 本类为hibernate分页提供辅助
 * 
 * @author 鬼卒
 * 
 */
public class HibernatePageUtil {
	private Integer countRow;// 总行数
	private String entityName;// 实体名称
	private Session session;// 数据库会话
	private Integer num;// 每页显示的数量

	/**
	 * 初始化操作
	 * 
	 * @param session
	 *            数据库会话
	 * @param num
	 *            每页显示条数
	 */
	public HibernatePageUtil(Session session, Integer num) {
		this.session = session;
		this.num = num;
	}
	/**
	 * 初始化操作
	 * 
	 * @param entityName
	 *            实体名称
	 * @param session
	 *            数据库会话
	 * @param num
	 *            每页显示条数
	 */
	public HibernatePageUtil(String entityName, Session session, Integer num) {
		this.entityName = entityName;
		this.session = session;
		this.num = num;
	}

	/**
	 * 获取总行数
	 * 
	 * @return 总行数
	 */
	public Integer getCountRow() {
		Transaction transaction = session.beginTransaction();
		String hql = "select count(*) from " + entityName;
		Query query = session.createQuery(hql);
		countRow = Integer.parseInt(query.uniqueResult().toString());
		transaction.commit();
		return countRow;
	}

	/**
	 * 获取分页总数
	 * 
	 * @return 分页总数
	 */
	public Integer getConurPage() {
		Integer countRow = getCountRow();
		if (countRow % num == 0) {
			return countRow / num;
		}
		return (countRow / num) + 1;
	}

	/**
	 * 获取指定页数据
	 * 
	 * @param index
	 *            页数 从1开始
	 * @return 指定页结果
	 */
	public List<?> getResult(Integer index) {
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from " + entityName);
		query.setFirstResult((index - 1) * num);
		query.setMaxResults(num);
		List<?> list = query.list();
		transaction.commit();
		return list;
	}

	/**
	 * 获取分页总行数
	 * 
	 * @param hql
	 *            hql语句
	 * @param param
	 *            参数（可选）
	 * @return 总行数
	 */
	public Integer getCountRow(String sql, Object... param) {
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql);
		for (int i = 1; i < param.length; i++) {
			query.setParameter(i, param[i]);
		}
		countRow = Integer.parseInt(query.uniqueResult().toString());
		transaction.commit();
		return countRow;
	}

	/**
	 * 获取指定条件查询可分页数
	 * 
	 * @param hql
	 *            hql语句
	 * @param param
	 *            参数（可选）
	 * @return 分页总数
	 */
	public Integer getSpecificConurPage(String sql, Object... param) {
		getCountRow(sql,param);
		if (countRow % num == 0&&countRow!=0) {
			return countRow / num;
		}
		return (countRow / num) + 1;
	}

	/**
	 * 获取指定页数据
	 * 
	 * @param index
	 *            index 页数 从1开始
	 * @param hql
	 *            hql语句
	 * @param param
	 *            参数（可选）
	 * @return 指定页结果
	 */
	public List<?> getResult(Class entity,Integer index, String hql, Object... param) {
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(entity);
		for (int i = 0; i < param.length; i++) {
			query.setParameter(i, param[i]);
		}
		query.setFirstResult((index - 1) * num);
		query.setMaxResults(num);
		List<?> list = query.list();
		transaction.commit();
		return list;
	}
}
