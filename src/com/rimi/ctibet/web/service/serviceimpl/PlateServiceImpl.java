package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.dao.IPlateDao;
import com.rimi.ctibet.web.dao.IPostDao;
import com.rimi.ctibet.web.service.IPlateService;

@Transactional
@Service("plateService")
public class PlateServiceImpl implements IPlateService{

	
	@Resource
	private IPlateDao plateDao;
	@Resource
	private IPostDao postDao;
	@Override
	public void deletePlate(Programa programa) {
		programa.setAvailable(0);
		plateDao.updateAsHibernate(programa);
		//删除版块下面关联的帖子
		postDao.deleteByProperty("programaCode", programa.getCode());
	}

	@Override
	public Programa findByCode(String code) {
		return plateDao.findByCode(code);
	}

	@Override
	public List<Map<String, Object>> getContentByProgramaCode(String code) {
		return plateDao.getContentByProgramaCode(code);
	}

	@Override
	public List<Programa> getSendPrograma(String code) {
		return plateDao.getSendPrograma();
	}

	@Override
	public List<Programa> getTopPlate() {
		return plateDao.getTopPlate();
	}

	@Override
	public void savePlate(Programa programa) {
		programa.setpCode("0c60136a-91f8-48b6-bd3b-1bee73f0114f");
       plateDao.save(programa);
	}

	@Override
	public void updatePlate(Programa programa) {
      plateDao.updateAsHibernate(programa);		
	}

	@Override
	public List<Map<Programa, List<Content>>> getProtalPlate() {
		return plateDao.getProtalPlate();
	}

	@Override
	public Pager plateList(Pager pager) {
		return plateDao.plateList(pager);
	}

	@Override
	public List<Map<String,Object>> getFrontTsSqShow(String plateCode,int isTop) {
		if(StringUtils.isNotBlank(plateCode))
			plateDao.getFrontTsSqShow(plateCode, isTop);
		return null;
	}

	@Override
	public List<Programa> getProList() {
		// TODO Auto-generated method stub
		return plateDao.getProList();
	}

	@Override
	public String postCountByIsTop() {
		return plateDao.postCountByIsTop();
	}
}
