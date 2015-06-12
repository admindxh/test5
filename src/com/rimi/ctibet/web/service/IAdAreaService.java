package com.rimi.ctibet.web.service;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.domain.AdArea;

public interface IAdAreaService {
	
	public void saveAdArea(AdArea adArea);
    public void updateAdArea(AdArea adArea);
    public void deleteAdArea(AdArea adArea);
    public List<AdArea> getAdAreaList();
	public AdArea findByCode(String code);
	//根据programaCode获取广告对象
	public List<Map<String,Object>> getAdAreaByProCode(String proCode);
	//根据属性删除
	public void deleteByProperty(String propertyName,String propertyValue);
	public List<AdArea> findbypro(String proCode);
    
}
