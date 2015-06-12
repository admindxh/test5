package com.rimi.ctibet.web.dao;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.MerchantManage;
import com.rimi.ctibet.portal.controller.vo.HotMerchantVo;
import com.rimi.ctibet.portal.controller.vo.MerchantVo;

public interface IMerchantDao extends BaseDao<Merchant>{

	//根据商户code获取商户信息
	public Merchant findByCode(String code);
	//删除商户，将商户available改为0
	public void deleteMerchant(Merchant merchant);
	//根据景点查询商户
	public Pager getMerchantByViewId(Pager pager,String id);
	//根据商户类别查询商户
	public Pager getMerchantByMerchantType(Pager pager,String type);
	//根据状态获取商户
	public Pager getMerchantByAvaliable(Pager pager, String isAvailable);
	//获取所有的图片
	public List<String> getMerchantImgListByCode(String merchantCode);
	
	/** 通过商户类型获取商户 有效信息 */
	public Pager getMerchantByMerchantTypeSql(Pager pager,String type);
	
	/** 通过栏目内容中间表栏目code和商户类型获取有效商户 */
	public Pager getMerchantByProCodeMerchantTypeSql(Pager pager, String proCode, String type);
	/** 通过栏目内容中间表栏目code和商户类型获取指定行数的有效商户 */
	public List<Merchant> getMerchantByProCodeMerchantTypeSql(String proCode, String type, int row);
	
	/** 通过商户code 查询有效商户 */
	public Merchant getAvailableMerchantByCode(String code);
	
	/**给前台展示商户推荐
	 */
	public Map<String,List<Merchant>> getProtalMerchant(int num);
	
	/**高级查询商户信息
	 * 
	 */
	public Pager searchMerchant(Pager pager,String areaCode,String distintionCode,String type,String isdOffice,String keyWord,String rule);
    
	/**
     *后台商户排序 
     */
	public Pager orderByMerchat(Pager pager,String rule);
	
   /**
    * 门户前台商户推荐（根据商户类型推荐，包括商户的赞和基本信息）
    * 	//门户推荐商户显示,num显示展示条数
    */
	public List<MerchantVo> getPortalMerchant(int num);
	
	//热门商户展示，先按照目的地，再按照景点
	public List<HotMerchantVo> getHotMerchant(int num,String destinationCode );
	
	//回显MerchantManage
	public List<MerchantManage> getMerchantManage();
	//============================================================================================================
	//门户显示商户列表
	public Pager getFrontMerchantList(Pager pager,String viewCode,String destinationCode,String keyWord,String type,String rule);
	//门户根据类型显示商户
	public List<Map<String,Object>> getFrontMerchantByType(String typeCode,int num);
	//门户団游显示
	public List<Map<String,Object>> getFrontTravel(int num);
	//门户显示官方推荐
	public List<Map<String,Object>> getOffice(int num);
	 //根据商户code获取相应的多个景点
	public List<Map<String,Object>> getViewsByMerchantCode(String merchantCode);
	//根据商户code获取评论
	public Pager getReplyInfoByMerchantCode(Pager pager,String merchantCode);
	//根据商户code获取赞数和浏览数
	public List<Map<String,Object>> getPraiseAndView(String merchantCode);
	public List<HotMerchantVo> getAllHotMerchant(int num);
	
	/**
	 * 通过景点code 获取最新 官方推荐商户
	 */
	public List<HotMerchantVo> getHotMerchantByView(String viewCode, int row);
	/**
	 * 通过目的地code 获取最新 官方推荐商户
	 */
	public List<HotMerchantVo> getHotMerchantBydestinationCode(String destinationCode, int row);
	
	//dxh需要的根据商户类型与数量返回商户的code和url
    public List<Map<String,Object>> getMUrlAndCodeByMType(String mTypeCode,int num,String desCode);
    //获取官方推荐列表
    public Pager getOfficeList(Pager pager);
  //根据商户名称获取其携程的价格
    public String getCtripPrice(String mName);
  //根据时间排序商户分类
	public List<Map<String,Object>> getTop3Mt();
	//单独获取携程价格
	public String getCtripNow(String mCode);
	
	/**
	 * 通过商户code获取商户评分
	 */
	public float getScore(String code);
	
	
}
