package com.rimi.ctibet.web.dao.daoimpl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.web.dao.IContentDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ContentDaoImplTest {

	@Resource IContentDao contentDao;
	
	@Test
	public void searchStrategyByContentMember() {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("", );	//
		paramMap.put("state", 1);	//	审核状态
		paramMap.put("proCode", "2");	//	板块
		paramMap.put("destinationCode", "3");	// 目的地
		paramMap.put("viewCode", "4");	//	景点
		paramMap.put("isOfficial", 5);	//	官方标记
		paramMap.put("keyWord", "6");	// 关键字[会员名、手机、邮箱、攻略标题]
		paramMap.put("avaliable", 0);	//	有效性
		Pager pager = contentDao.searchStrategyByContentMember(new Pager(), paramMap,"");
		for (Object obj : pager.getDataList()) {
			//System.out.println(obj);
		}
	}

}
