package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.MerchantType;
import com.rimi.ctibet.web.dao.IMerchantTypeDao;
import com.rimi.ctibet.web.service.IMerchantTypeService;

@Service("merchantTypeService")
@Transactional
public class MerchantTypeServiceImpl implements IMerchantTypeService{

	@Resource
	private IMerchantTypeDao merchantTypeDao;
	@Override
	public void deleteMerchantType(MerchantType merchantType) {
		merchantTypeDao.deleteByCode(merchantType.getCode());
	}

	@Override
	public List<MerchantType> getMerchantList() {
		return merchantTypeDao.findAllAvaliable();
	}

	@Override
	public void saveMerchantType(MerchantType merchantType) {
		merchantTypeDao.save(merchantType);
	}

	@Override
	public void updateMerchantType(MerchantType merchantType) {
		merchantTypeDao.updateAsHibernate(merchantType);
	}
	
	@Override
	public MerchantType findByCode(String code) {
		return merchantTypeDao.getByCode(code);
	}

	@Override
	public Pager getAllMerchnatType(Pager pager) {
		return merchantTypeDao.getAllMerchantType(pager);
	}

	@Override
	public Pager searchMerchantType(Pager pager, String keyWord) {
		return merchantTypeDao.searchMerchantType(pager,keyWord);
	}

	@Override
	public List<MerchantType> findbypro(String pro, Object value) {
		// TODO Auto-generated method stub
		return merchantTypeDao.findByProperty(pro, value);
	}
}
