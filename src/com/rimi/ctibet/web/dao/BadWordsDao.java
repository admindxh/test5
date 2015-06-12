package com.rimi.ctibet.web.dao;

import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.BadWords;

public interface BadWordsDao extends BaseDao<BadWords>{
	public List<BadWords> findByPro(String pro, Object value);
}
