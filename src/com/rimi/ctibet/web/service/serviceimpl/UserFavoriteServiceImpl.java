package com.rimi.ctibet.web.service.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.GroupTravel;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.UserFavorite;
import com.rimi.ctibet.web.dao.IGroupTravelDao;
import com.rimi.ctibet.web.dao.IMerchantDao;
import com.rimi.ctibet.web.dao.IPraiseDao;
import com.rimi.ctibet.web.dao.IUserFavoriteDao;
import com.rimi.ctibet.web.service.IUserFavoriteService;
@Service("userFavoriteService")
@Transactional
public class UserFavoriteServiceImpl extends BaseServiceImpl<UserFavorite> implements IUserFavoriteService{
      @Resource
      private IUserFavoriteDao userFavoriteDao;
      @Resource
      private IMerchantDao merchantDao;
      @Resource
      private IGroupTravelDao groupTravelDao;
      @Resource
      private IPraiseDao praiseDao;
      
    @Override
    public Pager findFavbyPager(String sql, List param, Pager pager) {
    	// TODO Auto-generated method stub
    	return userFavoriteDao.findListPagerBySql(Content.class, pager, sql, param);
    }
    
    @Override
    public Pager findWithPagerByMap(String hql, Map<String, Object> param,
    		Pager pager) {
    	// TODO Auto-generated method stub
    	return userFavoriteDao.findWithPagerByMap(hql, param, pager);
    }
    
    @Override
    public List<Programa> findStraType(String sql, List param) {
    	// TODO Auto-generated method stub
    	return userFavoriteDao.findListBySql(Programa.class, sql, param);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public int getFavCount(String code, String type) {
    	// TODO Auto-generated method stub
        String sql="SELECT * FROM `user_favorite` WHERE code=? AND type=?";
    	List params=new ArrayList();
    	params.add(code);
    	params.add(type);
    	List<UserFavorite> list=userFavoriteDao.findListBySql(UserFavorite.class, sql, params);
    	
    	return list.size();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean isFavAlready(String memCode, String code, String type) {
    	// TODO Auto-generated method stub
        String sql="SELECT * FROM `user_favorite` WHERE memberCode=? and code=? AND type=?";
    	List params=new ArrayList();
    	params.add(memCode);
    	params.add(code);
    	params.add(type);
    	List<UserFavorite> list=userFavoriteDao.findListBySql(UserFavorite.class, sql, params);
        
    	return list.size()>0;
    }
    
    public IUserFavoriteDao getUserFavoriteDao() {
    	return userFavoriteDao;
    }
    
    public void setUserFavoriteDao(IUserFavoriteDao userFavoriteDao) {
    	this.userFavoriteDao = userFavoriteDao;
    }
    
    @Override
    public List<UserFavorite> findListBySql(String sql, List param) {
    	// TODO Auto-generated method stub
    	return userFavoriteDao.findListBySql(UserFavorite.class, sql, param);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public int favCount(String memberCode) {
    	// TODO Auto-generated method stub
        String sql="SELECT * FROM content WHERE avaliable='1' AND CODE IN (SELECT CODE FROM `user_favorite` WHERE memberCode=?) AND contentType NOT LIKE '3___'";
        List param=new ArrayList();
        param.add(memberCode);
        List<Content> list=userFavoriteDao.findListBySql(Content.class, sql, param);
        
        String sql2="SELECT * FROM `merchant` WHERE avaliable='1' AND CODE IN (SELECT CODE FROM `user_favorite` WHERE memberCode=?)";
        List<Merchant> ml=merchantDao.findListBySql(Merchant.class, sql2, param);
        
        String sql3="SELECT * FROM `grouptravel` WHERE avaliable='1' AND CODE IN (SELECT CODE FROM `user_favorite` WHERE memberCode=?)";
    	List<GroupTravel> gl=groupTravelDao.findListBySql(GroupTravel.class, sql3, param);
    	
    	//看西藏 视频、图集
  		String hql="SELECT contentTitle AS contentTitle,code AS code,createTime AS createTime,url AS url FROM content WHERE avaliable='1' AND contentType LIKE '3___' AND code IN (SELECT code FROM user_favorite WHERE memberCode=? ) "
				+"UNION "
				+"SELECT NAME AS contentTitle,code AS code,createTime AS createTime,hyperlink AS url FROM `mutiimage` WHERE avaliable='1' and code IN(SELECT code FROM `user_favorite` WHERE memberCode=?) order by createTime desc";
  		param.clear();
  		param.add(memberCode);
  		param.add(memberCode);
  		List<Content> se= userFavoriteDao.findListBySql(Content.class, hql, param);
    	
    	return list.size()+ml.size()+gl.size()+se.size();
    }
    
    @Override
    public UserFavorite getByCode(String code, String memberCode) {
        // TODO Auto-generated method stub
        return userFavoriteDao.getByCode(code, memberCode);
    }

    @Override
    public String saveFavorite(String memberCode, String code, String type) {
        if(StringUtil.isNotNull(memberCode)){
            boolean isfav = this.isFavAlready(memberCode, code, type);
            if (isfav) {
                return "already_fav";
            }
            try {
                //更新假数据
                Praise praise = praiseDao.getPraiseContentCode(code);
                if(praise!=null){
                    praise.setFavoriteNum(praise.getFavoriteNum()+1);
                    praise.setFalseFavoriteNum(praise.getFalseFavoriteNum()+1);
                    praiseDao.update(praise);
                }else{
                    praise = new Praise();
                    praise.setAvaliable(1);
                    praise.setCode(Uuid.uuid());
                    praise.setViewCount(1);
                    praise.setFalseViewcount(1);
                    praise.setFavoriteNum(1);
                    praise.setFalseFavoriteNum(1);
                    praise.setContentCode(code);
                    praise.setCreateTime(new Date(System.currentTimeMillis()));
                    praiseDao.save(praise);
                }
                //添加真实数据
                UserFavorite userFavorite = new UserFavorite();
                userFavorite.setCode(code);
                userFavorite.setState("1");
                userFavorite.setType(type);
                userFavorite.setMemberCode(memberCode);
                this.save(userFavorite);
                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }else{
            return "need_login";
        }
    }

}
