package com.rimi.ctibet.web.dao.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.MobileCode;
import com.rimi.ctibet.web.dao.IMobileCodeDao;
/**
 * 
 * @author xiangwq
 *
 */
@Repository("mobileCodeDao")
public class MobileCodeDaoImpl extends BaseDaoImpl<MobileCode> implements IMobileCodeDao {
	
	/**
	 * 匹配手机号和验证码返回一个List
	 */
    @SuppressWarnings("unchecked")
	@Override
    public List<MobileCode> findByMobileAndValidate(String mobile,
    		String validate) {
    	Map<String, Object> paraMap = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("FROM MobileCode m where m.mobile=:mobile and m.validateCode=:validate");
		paraMap.put("mobile", mobile);
		paraMap.put("validate", validate);
		return find(hql.toString(), paraMap);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<MobileCode> findByMobile(String mobile) {
    	Map<String, Object> paraMap = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("FROM MobileCode m where m.mobile=:mobile");
		paraMap.put("mobile", mobile);
		return find(hql.toString(), paraMap);
    }
}
