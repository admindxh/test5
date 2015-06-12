package com.rimi.ctibet.web.dao;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Equipment;
import com.rimi.ctibet.domain.Praise;

/**
 * 赞 dao
 * @author dengxh
 *
 */
public interface IEquipmentDao  extends BaseDao<Equipment>{
	
	
	public Pager getEquiList(String name,String proType,Pager pager);
	
	
	
}
