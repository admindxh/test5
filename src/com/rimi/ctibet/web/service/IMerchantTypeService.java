package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.MerchantType;


public interface IMerchantTypeService {
	
	public void saveMerchantType(MerchantType merchantType);
    public void updateMerchantType(MerchantType merchantType);
    public void deleteMerchantType(MerchantType merchantType);
    public List<MerchantType> getMerchantList();
	public MerchantType findByCode(String code);
	public Pager getAllMerchnatType(Pager pager);
	public Pager searchMerchantType(Pager pager,String keyWord);
    public List<MerchantType> findbypro(String pro,Object value);
}
