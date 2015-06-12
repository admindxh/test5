package com.rimi.ctibet.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.rimi.ctibet.domain.BadWords;
import com.rimi.ctibet.web.service.BadWordsService;
@Component
public class BadWordsInit implements InitializingBean{
    public static  Logger LOGER=LoggerFactory.getLogger(BadWords.class);
    @Resource
    private BadWordsService badWordsService;
	@Override
	public void afterPropertiesSet() throws Exception {
	  SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  try {
	      badWordsService.initBadWords();
	      
	      LOGER.info("Init BadWords Success~.........！Time:"+fmt.format(new Date())+"~~~");
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			LOGER.info("Init BadWords Error~.........！Time:"+fmt.format(new Date())+"~~~");
		}
	}

}
