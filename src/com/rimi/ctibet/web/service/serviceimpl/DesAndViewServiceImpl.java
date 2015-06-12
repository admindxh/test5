package com.rimi.ctibet.web.service.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.DesAndViewStatis;
import com.rimi.ctibet.web.controller.vo.DesAndViewStatisVo;
import com.rimi.ctibet.web.dao.IDesAndViewStatisDao;
import com.rimi.ctibet.web.service.IDesAndViewService;

@Transactional
@Service("desAndViewService")
public class DesAndViewServiceImpl extends BaseServiceImpl<DesAndViewStatis> implements IDesAndViewService {
	
	@Resource
	private IDesAndViewStatisDao  desAndViewStatisDao;
	
	@Override
	public DesAndViewStatisVo getDesViewVo(String start, String end,
			String desCode, String viewCode) {
		// TODO Auto-generated method stub
		return desAndViewStatisDao.getDesViewVo(start, end, desCode, viewCode);
	}

    public IDesAndViewStatisDao getDesAndViewStatisDao() {
        return desAndViewStatisDao;
    }

    public void setDesAndViewStatisDao(IDesAndViewStatisDao desAndViewStatisDao) {
        this.desAndViewStatisDao = desAndViewStatisDao;
    }
	
}
