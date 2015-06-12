package com.rimi.ctibet.web.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.KeyWordFilter;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.BadWords;
import com.rimi.ctibet.web.dao.BadWordsDao;
import com.rimi.ctibet.web.service.BadWordsService;
@Service("badWordsService")
public class BadWordsServiceImpl extends BaseServiceImpl<BadWords> implements BadWordsService{
  @Resource
  private BadWordsDao badWordsDao;

public BadWordsDao getBadWordsDao() {
	return badWordsDao;
}

public void setBadWordsDao(BadWordsDao badWordsDao) {
	this.badWordsDao = badWordsDao;
}

@Override
public Pager findByPager(String sql, List param, Pager pager) {
	// TODO Auto-generated method stub
	return badWordsDao.findByJDBCSql(sql, param, pager);
}

@Override
public List<BadWords> findbyPro(String pro, Object value) {
	// TODO Auto-generated method stub
	return badWordsDao.findByPro(pro, value);
}

@Override
public void initBadWords() {
	// TODO Auto-generated method stub
	List<BadWords> badword=this.findAll();
	List bad=new ArrayList();
	for(BadWords b:badword){
		bad.add(b.getBadword());
	}
	KeyWordFilter.addKeywords(bad);
}
}
