package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.domain.UserView;
import com.rimi.ctibet.web.dao.UserViewDao;
@Repository("userViewDao")
public class UserViewDaoImpl extends BaseDaoImpl<UserView> implements UserViewDao{

    @Override
    public UserView getUserView(String memberCode, String viewCode, String type) {
        List<Object> params = new ArrayList<Object>();
        String sql = " SELECT * FROM user_view WHERE memberCode=? AND viewCode=? AND type=? ";
        params.add(memberCode);
        params.add(viewCode);
        params.add(type);
       
        return ListUtil.returnSingle(findListBySql(UserView.class, sql, params));
    }
    @Override
    public UserView getUserDes(String memberCode, String desCode, String type) {
        List<Object> params = new ArrayList<Object>();
        String sql = " SELECT * FROM user_view WHERE memberCode=? AND areaCode=? AND type=? ";
        params.add(memberCode);
        params.add(desCode);
        params.add(type);
       
        return ListUtil.returnSingle(findListBySql(UserView.class, sql, params));
    }
    
}
