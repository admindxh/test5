package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.BadWords;

public interface BadWordsService extends BaseService<BadWords>{
    public Pager findByPager(String sql,List param,Pager pager);
    public List<BadWords> findbyPro(String pro,Object value);
    public void initBadWords();
}
