package com.rimi.ctibet.web.dao.daoimpl;

import org.springframework.beans.BeanUtils;

import com.rimi.ctibet.common.util.BeansUtil;

public class BeansUtilTest {
		public static void main(String[] args) {
			StudentTst  studentTst  = new StudentTst();
			studentTst.setName("test");
			studentTst.setSex("女");
			StudentTst studentTst2  =  new StudentTst();
			studentTst2.setName("test2");
			//BeansUtil.copyPropertysWithoutNull(studentTst2, studentTst);
			BeanUtils.copyProperties(studentTst, studentTst2);
			//System.out.println(studentTst2.getName()+"---"+studentTst2.getSex());
			
			
		}
}
