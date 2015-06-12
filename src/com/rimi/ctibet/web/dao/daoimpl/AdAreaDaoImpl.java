package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.AdArea;
import com.rimi.ctibet.web.dao.IAdAreaDao;
@Repository("adAreaDao")
public class AdAreaDaoImpl extends BaseDaoImpl<AdArea> implements IAdAreaDao {

	@Override
	public List<Map<String,Object>> getAdAreaByProCode(String proCode) {
		/*String sql = "SELECT a.* " +
				"FROM ad_area a " +
				"WHERE a.type = ? ";*/
		String sql = "SELECT a.* FROM ad_area a WHERE a.type = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(proCode);
		return findByJDBCSql(sql, params);
	}
}
