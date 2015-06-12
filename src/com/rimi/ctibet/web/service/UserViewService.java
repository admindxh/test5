package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.UserView;
import com.rimi.ctibet.domain.View;

public interface UserViewService extends BaseService<UserView>{
   //根据属性查询相关记录
	public List<UserView> findByProperty(String pro,Object value);
	public List<UserView> findListBySql(String sql,List param);
	public List<UserView> findUserViewBySql(String sql,List param);
	public int getViewCountByType(String memCode,String type);
	public Pager findPagerBySql(String sql,List param,Pager pager);
	
    /**
     * 通过 会员code 和 景点code获取数据
     */
    public UserView getUserView(String memberCode, String viewCode, String type);
    
    /**
     * 保存
     */
    public void saveUserView(UserView userView);
    
    
    public Pager  getPager(String sql,Pager pager);
    
    public List<View> getViewXy(List param);
    public UserView getUserDes(String memberCode, String desCode, String type);
}
