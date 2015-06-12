package com.rimi.ctibet.web.dao.daoimpl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Equipment;
import com.rimi.ctibet.web.dao.IEquipmentDao;

@Repository(value="isEquipmentDao")
public class EquipmenDaoImpl extends   BaseDaoImpl<Equipment> implements IEquipmentDao {

	@Override
	public Pager getEquiList(String name, String proType,Pager pager) {
		// TODO Auto-generated method stub
		String sql   =" SELECT  eq.*,p.programaName ,IF(eq.paytype='1','本站购买','其他站点购买')  as  paytypename from equipment  eq  LEFT  join programa p on p.available=1 and p.pCode='superequipmenttype' and p.`code` = eq.proType  where eq.avaliable=1 and 1=1 ";
		List<Object>  params  = new ArrayList<Object>();
		if (StringUtils.isNotBlank(name)) {
			 sql =  sql  + " and  ( eq.name like  ?  or eq.code like ? ) ";
				 params.add("%"+name+"%");
				 params.add("%"+name+"%");
		}
		if (StringUtils.isNotBlank(proType)) {
			 sql =  sql  + " and  eq.proType =  ? ";
				 params.add(proType);
		}
		
		return super.findPagerBySQL(sql, params, pager);
	}

	
	

}
