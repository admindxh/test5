package com.rimi.ctibet.web.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.rimi.ctibet.domain.AdArea;
import com.rimi.ctibet.web.dao.IAdAreaDao;
import com.rimi.ctibet.web.service.IAdAreaService;
@Service("adAreaService")
public class AdAreaServiceImpl implements IAdAreaService{

	@Resource
	private IAdAreaDao adAreaDao; 
	@Override
	public void deleteAdArea(AdArea adArea) {
		if(adArea!=null)
			adAreaDao.delete(adArea);
		
	}

	@Override
	public AdArea findByCode(String code) {
       if(StringUtils.isNotBlank(code))
    	   return adAreaDao.findByCode(code);
		return null;
	}

	@Override
	public List<AdArea> getAdAreaList() {
		return adAreaDao.findAll();
	}

	@Override
	public void saveAdArea(AdArea adArea) {
		if(adArea!=null)
			adAreaDao.save(adArea);
		
	}

	@Override
	public void updateAdArea(AdArea adArea) {
		if(adArea!=null)
			adAreaDao.updateAsHibernate(adArea);
	}

	@Override
	public List<Map<String,Object>> getAdAreaByProCode(String proCode) {
		if(StringUtils.isNotBlank(proCode)){
			return adAreaDao.getAdAreaByProCode(proCode);
		}
		return null;
	}

	@Override
	public void deleteByProperty(String propertyName, String propertyValue) {
		adAreaDao.deleteByProperty(propertyName, propertyValue);
	}

	@Override
	public List<AdArea> findbypro(String proCode) {
		// TODO Auto-generated method stub
		List param=new ArrayList();
		 param.add(proCode);
		 String sql="SELECT * FROM  `ad_area` WHERE avaliable='1' AND type=?";
		return adAreaDao.findListBySql(AdArea.class, sql, param);
	}
}
