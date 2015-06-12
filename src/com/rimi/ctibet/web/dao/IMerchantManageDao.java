package com.rimi.ctibet.web.dao;

import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.MerchantManage;

public interface IMerchantManageDao extends BaseDao<MerchantManage>{

	//MerchantManage的回显
	public List<MerchantManage> getMerchantManage();
	//MerchantManage的保存
	public void saveMerchantManage(MerchantManage mm);
	//清空MerchantManage的老数据
	public void clearOldMerchantManage();
}
