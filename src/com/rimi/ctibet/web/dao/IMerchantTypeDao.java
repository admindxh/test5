package com.rimi.ctibet.web.dao;

import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.MerchantType;

public interface IMerchantTypeDao extends BaseDao<MerchantType>{
	
	//按照rank取出商户类型
	public List<MerchantType> getMerchantTypeOederByRank();

	public MerchantType getByCode(String code);
	
	public Pager getAllMerchantType(Pager pager);
	
	public Pager searchMerchantType(Pager pager,String keyWord);

	public List<MerchantType> findAllAvaliable();

}
