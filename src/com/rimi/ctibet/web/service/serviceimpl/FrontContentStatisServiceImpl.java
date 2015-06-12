package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.FrontContentStatis;
import com.rimi.ctibet.web.controller.vo.BaseCodeVo;
import com.rimi.ctibet.web.controller.vo.FrontContentVo;
import com.rimi.ctibet.web.dao.IFrontContentStaticDao;
import com.rimi.ctibet.web.service.IFrontContentStatisService;

@Transactional
@Service("frontContentStatisService")
public class FrontContentStatisServiceImpl extends BaseServiceImpl<FrontContentStatis> implements IFrontContentStatisService {
		
	@Resource(name="frontContentStatisDao")
	private IFrontContentStaticDao frontContentStatisDao;
	
	
	public List<BaseCodeVo> getBaseCodeListByPcode(final String pcode){
		
		return frontContentStatisDao.getBaseCodeListByPcode(pcode);
	}

	public IFrontContentStaticDao getFrontContentStatisDao() {
		return frontContentStatisDao;
	}

	public void setFrontContentStatisDao(
			IFrontContentStaticDao frontContentStatisDao) {
		this.frontContentStatisDao = frontContentStatisDao;
	}

	@Override
	public FrontContentVo getAllFrontContent(String start, String end,
			String proType) {
		// TODO Auto-generated method stub
		return frontContentStatisDao.getAllFrontContent(start, end, proType);
	}

	@Override
	public Pager getPageFrontContentByCon(String start, String end,
			String proType, Pager pager) {
		// TODO Auto-generated method stub
		return  frontContentStatisDao.getPageFrontContentByCon(start, end, proType, pager);
	}

	@Override
	public BaseCodeVo getBaeCodeVo(String code) {
		// TODO Auto-generated method stub
		 String sql  =  "select code as code, typename from basecode where code='"+code+"'";
		 List<BaseCodeVo> list  =  frontContentStatisDao.findListBySql(BaseCodeVo.class, sql);
		 return   list!=null&&list.size()>=1?list.get(0):null;
	}

	@Override
	public FrontContentVo getAllFrontMerchant(String start, String end,
			String proType, String merchantName) {
		// TODO Auto-generated method stub
		return frontContentStatisDao.getAllFrontMerchant(start, end, proType,merchantName);
	}

	@Override
	public Pager getPageFrontMerchanttByCon(String start, String end,
			String proType, String merchantName, Pager pager) {
		// TODO Auto-generated method stub
		return frontContentStatisDao.getPageFrontMerchanttByCon(start, end, proType, merchantName, pager);
	}
	@Override
	public FrontContentVo getAllFrontAderv(String start, String end,
			String proType) {
		
			return frontContentStatisDao.getAllFrontAderv(start, end, proType);
	}
	@Override
	public Pager getPageFrontAdervByCon(String start, String end,
			String proType, Pager pager) {
		return frontContentStatisDao.getPageFrontAdervByCon(start, end, proType, pager);
	}
	
	
}
