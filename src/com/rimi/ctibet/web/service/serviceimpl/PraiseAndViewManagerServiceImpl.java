package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.PraiseAndViewManager;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.dao.IPraiseAndViewManagerDao;
import com.rimi.ctibet.web.service.IPraiseAndViewManagerService;
import com.rimi.ctibet.web.service.IProgramaService;
@Service("praiseAndViewManagerService")
@Transactional
public class PraiseAndViewManagerServiceImpl implements IPraiseAndViewManagerService{

	@Resource
	private IPraiseAndViewManagerDao praiseAndViewDao;

	@Override
	public void deleteRercord(PraiseAndViewManager pvm) {
		if(pvm!=null)
		     praiseAndViewDao.delete(pvm);
	}

	@Override
	public PraiseAndViewManager getRecordByCode(String code) {
		if(StringUtils.isNotBlank(code))
		   return praiseAndViewDao.getRecordByCode(code);
		return null;
	}

	@Override
	public boolean isRecored(String ip, String contentCode) {
		PraiseAndViewManager pvm =new PraiseAndViewManager();
		pvm.setContentCode(contentCode);
		pvm.setIp(ip);
	    return	praiseAndViewDao.isRecored(pvm);
	
	}

	@Override
	public void saveRecord(String ip, String contentCode) {
		PraiseAndViewManager pvm =new PraiseAndViewManager();
		pvm.setContentCode(contentCode);
		pvm.setIp(ip);
		pvm.setCode(Uuid.uuid());
		praiseAndViewDao.save(pvm);
	}

   
}
