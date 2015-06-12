package com.rimi.ctibet.web.service.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Equipment;
import com.rimi.ctibet.web.dao.IEquipmentDao;
import com.rimi.ctibet.web.service.IEquimentService;

@Transactional
@Service
public class EquipmentServiceImpl extends BaseServiceImpl<Equipment> implements IEquimentService {
	
	@Autowired
	private IEquipmentDao equipmentDao;
	
	@Override
	public Pager getEquiList(String name, String proType, Pager pager) {
		// TODO Auto-generated method stub
		return  equipmentDao.getEquiList(name, proType, pager);
	}

	public IEquipmentDao getEquipmentDao() {
		return equipmentDao;
	}

	public void setEquipmentDao(IEquipmentDao equipmentDao) {
		this.equipmentDao = equipmentDao;
	}
	

}
