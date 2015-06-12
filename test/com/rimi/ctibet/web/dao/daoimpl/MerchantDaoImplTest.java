package com.rimi.ctibet.web.dao.daoimpl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.web.dao.IMerchantDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MerchantDaoImplTest {

	@Resource IMerchantDao merchantDao;

	@Test
	public void getMerchantByMerchantTypeSql() {
		Pager pager = merchantDao.getMerchantByMerchantTypeSql(new Pager(), "1");
		for (Object merchant : pager.getDataList()) {
			//System.out.println(merchant);
		}
	}
	@Test
	public void getMerchantByProCodeMerchantTypeSql() {
		Pager pager = merchantDao.getMerchantByProCodeMerchantTypeSql(new Pager(), "test", "1");
		for (Object merchant : pager.getDataList()) {
			//System.out.println(merchant);
		}
	}
	@Test
	public void getAvailableMerchantByCode() {
		Merchant merchant = merchantDao.getAvailableMerchantByCode("1");
		//System.out.println(merchant);
	}
	@Test
	public void getMerchantByProCodeMerchantTypeSqlRow() {
		List<Merchant> list = merchantDao.getMerchantByProCodeMerchantTypeSql("test", "1", 2);
		//System.out.println(list);
	}

}
