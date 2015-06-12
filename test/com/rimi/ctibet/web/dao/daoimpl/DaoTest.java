package com.rimi.ctibet.web.dao.daoimpl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.dao.IContentDao;
import com.rimi.ctibet.web.dao.IMerchantDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DaoTest {

	@Resource IMerchantDao merchantDao;
	@Resource IContentDao contentDao;

	@Test
	public void test() {
		String sql = " SELECT * FROM ctibet.programa where concat(programaName, programaSummary) like ? ";
		List<Programa> list = merchantDao.findListBySql(Programa.class, sql, new Object[]{"%前台%"});
		for (Programa programa : list) {
			//System.out.println(programa);
		}
	}
	@Test
	public void test2() {
		contentDao.updateState("1", 0);
	}

}
