package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.domain.Access;
import com.rimi.ctibet.web.dao.IAccessDao;
import com.rimi.ctibet.web.service.IAccessService;
@Transactional
@Service("accessService")
public class AccessServiceImpl implements IAccessService{
    @Resource
	private IAccessDao accessDao;
	@Override
	public void deleteAccess(Access access) {
		accessDao.deleteAccess(access);
		
	}

	@Override
	public Access getAccessByCode(String code) {
		return accessDao.findByCode(code);
	}

	@Override
	public void saveAccess(Access access) {
		accessDao.save(access);
		
	}

	@Override
	public void updateAccess(Access access) {
       accessDao.update(access);	
	}

	@Override
	public List<Access> accessList() {
		return accessDao.accessList();
	}

	@Override
	public List<Access> getTopAccess() {
		return accessDao.getTopAccess();
	}

	@Override
	public List<Access> getSendAccess(String pCode) {
		return accessDao.getSendAccess(pCode);
	}

	@Override
	public Map<String,List<Access>> getAllAccess() {
		return  accessDao.getAllAccess();
	}

}
