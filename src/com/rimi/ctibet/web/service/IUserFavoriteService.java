package com.rimi.ctibet.web.service;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.UserFavorite;

public interface IUserFavoriteService extends BaseService<UserFavorite>{
   public List<UserFavorite> findListBySql(String sql,List param);
   public Pager findFavbyPager(String sql,List param,Pager pager);
   public Pager findWithPagerByMap(String hql, Map<String, Object> param, Pager pager);
   //查询所有攻略类型
   public List<Programa> findStraType(String sql, List param);
   //查询收藏数目
   public int getFavCount(String code,String type);
   //判断是否已经收藏
   public boolean isFavAlready(String memCode,String code,String type);
   //查询用户的所有收藏数目
   public int favCount(String memberCode);
   
   /**
    * 通过code查询
    * @param code
    * @return
    */
   public UserFavorite getByCode(String code, String memberCode);
   
   /**
    * 保存收藏数据
    */
   public String saveFavorite(String memberCode, String code, String type);
   
}
