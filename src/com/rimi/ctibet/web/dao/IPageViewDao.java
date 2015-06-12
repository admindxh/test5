package com.rimi.ctibet.web.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.PageView;

/**
 * 页面访问量统计 pv
 * @author dengxh
 *
 */
public  interface IPageViewDao  extends BaseDao<PageView>{
	/**
	 *通过时间查询 返回数量 
	 */
	public  Integer getMemCountByTime(Date start,Date end);
	
	
	
}
