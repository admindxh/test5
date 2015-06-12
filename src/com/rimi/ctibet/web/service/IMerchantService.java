package com.rimi.ctibet.web.service;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.MerchantManage;
import com.rimi.ctibet.domain.MerchantType;
import com.rimi.ctibet.portal.controller.vo.HotMerchantVo;
import com.rimi.ctibet.portal.controller.vo.MerchantVo;

public interface IMerchantService {
	
	public void saveMerchant(Merchant merchant);
    public void updateMerchant(Merchant merchant);
    public void deleteMerchant(Merchant merchant);
    public Merchant getMerchantByCode(String code);
    //根据景点查询商户
    public Pager getMerchantByViewId(Pager pager,String id);
    //根据商户类别查询商户
    public Pager getMerchantByMerchantType(Pager pager,String type);
    //获取所有商户
    public Pager merchantList(Pager pager); 
    //根据状态查询商户
    public Pager getMerchantByAvaliable(Pager pager,String isAvailable);
    //返回所有图片
    public List<String> getMerchantImgListByCode(String merchantCode);
    //返回前台的商户推荐
    public Map<String,List<Merchant>> getProtalMerchant(int num);
    //商户列表的高级查询
    public Pager searchMerchant(Pager pager,String areaCode, String distintionCode,String type, String isOffice, String keyWord,String rule);
    //后台商户排序
    public Pager orderByMerchat(Pager pager,String rule);
    /**
     * 门户前台商户推荐（根据商户类型推荐，包括商户的赞和基本信息）
     * 门户推荐商户显示,num显示展示条数
     */
    public List<MerchantVo> getPortalMerchant(int num);
    
    /**
     * 通过目的地获取商户
     * @param destinationCode
     * @return Map<商户名：对应的商户列表>
     */
	public Map<String,List<Merchant>> getMerchantByDestinationCode(String destinationCode);
	
	//热门商户展示，先按照目的地，再按照景点
	public List<HotMerchantVo> getHotMerchant(int num,String distinationCode);
	
	//MerchantManage的回显
	public List<MerchantManage> getMerchantManage();
	
	//清空MerchantManage的老数据
	public void clearOldMerchantManage();
	//MerchantManage的添加
	public void saveMerchantManage(MerchantManage mm);
	//==========================================================门户=========================================================
	//门户显示商户列表
	public Pager getFrontMerchantList(Pager pager,String viewCode,String destinationCode,String keyWord,String type,String rule);
	//门户显示指定类型的商户
	public List<Map<String,Object>> getFrontMerchantByType(String typeCode,int num);
	//门户显示団游
	public List<Map<String,Object>> getFrontTravel(int num);
	//门户显示官方推荐
	public List<Map<String,Object>> getOffice(int num);
    //根据商户code获取相应的多个景点
	public List<Map<String,Object>> getViewsByMerchantCode(String merchantCode);
	//根据商户code获取评论
	public Pager getReplyInfoByMerchantCode(Pager pager,String merchantCode);
	//根据商户code获取赞数和浏览数
	public List<Map<String,Object>> getPraiseAndView(String merchantCode);
	//个人中心查询收藏分页数据
	public Pager findListByPager(String sql,List param,Pager pager);
	
	public List<HotMerchantVo> getAllHotMerchant(int num);
	
	/**
     * 通过目的地code和景点code 获取最新 官方推荐商户
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
	public List<Merchant> findbypro(String string, String name);    
	//根据属性查询 
	public List<Merchant> findbypro(String pro,Object value);
	//单独获取携程价格
	public String getCtripNow(String mName);

    /**
     * 通过商户code获取商户评分
     */
    public String getScore(String code);
}
