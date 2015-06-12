package com.rimi.ctibet.web.dao;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.AdArea;

public interface IAdAreaDao extends BaseDao<AdArea>{
	//根据programaCode获取广告对象
	public List<Map<String,Object>> getAdAreaByProCode(String proCode);
}
