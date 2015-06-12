package com.rimi.ctibet.web.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.index.PositionBasedTermVectorMapper.TVPositionInfo;
import org.springframework.stereotype.Service;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.UserView;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.dao.IViewDao;
import com.rimi.ctibet.web.dao.UserViewDao;
import com.rimi.ctibet.web.service.UserViewService;
@Service("userViewService")
public class UserViewServiceImpl extends BaseServiceImpl<UserView> implements UserViewService{
    @Resource
    private UserViewDao userViewDao;
    @Resource
    private IViewDao viewDao;

    public UserViewDao getUserViewDao() {
        return userViewDao;
    }

    public void setUserViewDao(UserViewDao userViewDao) {
        this.userViewDao = userViewDao;
    }

    @Override
    public List<UserView> findByProperty(String pro, Object value) {
        // TODO Auto-generated method stub
        return userViewDao.findByProperty(pro, value);
    }

    @Override
    public List<UserView> findListBySql(String sql, List param) {
        // TODO Auto-generated method stub
        return userViewDao.findListBysql(sql, param);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getViewCountByType(String memCode, String type) {
        // TODO Auto-generated method stub
        String sql = "SELECT * FROM `user_view` WHERE avaliable='1' AND memberCode=? AND type=? AND viewCode IN (SELECT CODE FROM tview WHERE avaliable='1')";
        List param = new ArrayList();
        param.add(memCode);
        param.add(type);
        List<UserView> list = userViewDao.findListBysql(sql, param);
        return list.size();
    }

    @Override
    public Pager findPagerBySql(String sql, List param, Pager pager) {
        // TODO Auto-generated method stub
        return userViewDao.findListPagerBySql(View.class, pager, sql, param);
    }

    @Override
    public UserView getUserView(String memberCode, String viewCode, String type){
        return userViewDao.getUserView(memberCode, viewCode, type);
    }

    @Override
    public void saveUserView(UserView userView) {
        this.save(userView);
        String viewCode = userView.getViewCode();
        View view = viewDao.findByCode(viewCode);
        if(userView.getType().equals(UserView.User_View_Wanna)){
            view.setWantCount(view.getWantCount()+1);
            view.setFakeWantCount(view.getFakeWantCount()+1);
        }
        if(userView.getType().equals(UserView.User_View_Gone)){
            view.setGoneCount(view.getGoneCount()+1);
            view.setFakeGoneCount(view.getFakeGoneCount()+1);
        }
        viewDao.update(view);
    }

	@Override
	public Pager getPager(String sql, Pager pager) {
		// TODO Auto-generated method stub
		return    userViewDao.findListPagerBySql(View.class, pager, sql);
	}

	@Override
	public List<View> getViewXy(List param) {
		// TODO Auto-generated method stub
		String sql ="SELECT id, avaliable, code, viewName, destinationCode, goneCount, wantCount, viewImage, view_360full, viewIntroduce, ticketPrice, ticketUrl, isHaveGateTicket, createTime, lastEditTime, keyword, fakeGoneCount, fakeWantCount, checkNum, fakeCheckNum, replyNum, likeNum, fakeLikeNum, guide, traffic, notice, address, hdFullUrl, realSceneVideoUrl, linkUrl,xy FROM `tview` WHERE avaliable='1' ";
 	    sql = sql  + "AND CODE IN(SELECT viewCode FROM `user_view` WHERE avaliable='1'  AND memberCode=?)";
		List<View> list= userViewDao.findListBySql(View.class, sql, param);
//		for(View v:list){
//			if(v.getXy()!=null){
//				String[] location=v.getXy().split(",");
//				if(location.length>0){
//					v.setX(location[0]);
//					v.setY(location[1]);
//				}
//			}
//			
//		}
		return list;
	}
	 public UserView getUserDes(String memberCode, String desCode, String type){
		 
		return  this.userViewDao.getUserDes(memberCode, desCode, type);
	 }

	@Override
	public List<UserView> findUserViewBySql(String sql, List param) {
		// TODO Auto-generated method stub
		return userViewDao.findListBySql(UserView.class, sql, param);
	}
}
