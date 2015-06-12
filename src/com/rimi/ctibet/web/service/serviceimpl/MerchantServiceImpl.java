
package com.rimi.ctibet.web.service.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.HTMLTagUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.MerchantManage;
import com.rimi.ctibet.domain.MerchantType;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.portal.controller.vo.HotMerchantVo;
import com.rimi.ctibet.portal.controller.vo.MerchantVo;
import com.rimi.ctibet.web.dao.IMerchantAndViewDao;
import com.rimi.ctibet.web.dao.IMerchantDao;
import com.rimi.ctibet.web.dao.IMerchantManageDao;
import com.rimi.ctibet.web.dao.IMerchantTypeDao;
import com.rimi.ctibet.web.dao.IViewDao;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IMerchantService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IUserFavoriteService;


@Service("merchantService")
@Transactional
public class MerchantServiceImpl implements IMerchantService{

	@Resource
	private IMerchantDao merchantDao;
	@Resource
	private IViewDao viewDao;
	@Resource
	private IMerchantAndViewDao merchantAndViewDao;
	@Resource
	private IMerchantTypeDao merchantTypeDao;
	@Resource
	private IMerchantManageDao merchantManageDao;
	@Resource
	private IUserFavoriteService userFavoriteService;
	@Resource
	private IPraiseService praiseService;
	@Resource
	private DestinationService destinationService;
	
	
	@Override
	public void deleteMerchant(Merchant merchant) {
		merchantDao.deleteMerchant(merchant);
		//删除关联的收藏表
		userFavoriteService.deleteByCode(merchant.getCode());
	}

	@Override
	public Merchant getMerchantByCode(String code) {
		return merchantDao.findByCode(code);
	}

	@Override
	public Pager getMerchantByMerchantType(Pager pager, String type) {
		return merchantDao.getMerchantByMerchantType(pager,type);
	}

	@Override
	public Pager getMerchantByViewId(Pager pager, String viewId) {
		return merchantDao.getMerchantByViewId(pager,viewId);
	}

	@Override
	public Pager merchantList(Pager pager) {
		pager.setDataList(merchantDao.findAll());
		return pager;
	}

	@Override
	public void saveMerchant(Merchant merchant) {
		merchantDao.save(merchant);
		//关联赞表
		Praise p = new Praise();
		p.setAvaliable(1);
		p.setCode(CodeFactory.create("praise"));
		p.setContentCode(merchant.getCode());
		p.setCreateTime(new Date());
	    praiseService.save(p); 	
	}

	@Override
	public void updateMerchant(Merchant merchant) {
       merchantDao.updateAsHibernate(merchant); 
  	}

	@Override
	public List<String> getMerchantImgListByCode(String merchantCode) {
		return merchantDao.getMerchantImgListByCode(merchantCode);
	}

	@Override
	public Pager getMerchantByAvaliable(Pager pager, String isAvailable) {
		return merchantDao.getMerchantByAvaliable(pager,isAvailable);
	}

	@Override
	public Map<String, List<Merchant>> getProtalMerchant(int num) {
		return merchantDao.getProtalMerchant(num);
	}
	
    /**
     * 通过目的地获取商户
     * @param destinationCode
     * @return Map<商户名：对应的商户列表>
     */
	@Override
	public Map<String,List<Merchant>> getMerchantByDestinationCode(String destinationCode) {
		List<View> viewList = viewDao.findViewByDestinationCode(destinationCode);
		List<Merchant> merchantList = new ArrayList<Merchant>();
		List<MerchantType> merchantTypeList = new ArrayList<MerchantType>();
		if (viewList.size() > 0) {
			for (View v : viewList) {
				List<Merchant> mList = merchantAndViewDao.getMerchantByView(v.getCode());
				for (Merchant m : mList) {
					merchantList.add(m);
				}
			}
		}
		merchantTypeList = merchantTypeDao.findAll();
		Map<String,List<Merchant>> merchantMap = new HashMap<String, List<Merchant>>();
		for(MerchantType mt : merchantTypeList){
			List<Merchant> mlist = new ArrayList<Merchant>();
			for(Merchant m : merchantList){
				if(m.getMerchantType().equals(mt.getCode())){
					mlist.add(m);
				}
				merchantMap.put(mt.getName(),mlist);
			}
		}
		return merchantMap;
	}

	@Override
	public Pager searchMerchant(Pager pager, String areaCode,
			String distintionCode, String type, String isOffice, String keyWord,String rule) {
		return merchantDao.searchMerchant(pager, areaCode, distintionCode, type, isOffice, keyWord,rule);
	}
    
	@Override
	public List<HotMerchantVo> getAllHotMerchant(int num) {
		return merchantDao.getAllHotMerchant(num);
	}

	@Override
	public List<HotMerchantVo> getHotMerchant(int num,String destinationCode) {
		if(num!=0)
		   return merchantDao.getHotMerchant(num,destinationCode);
		return null;
	}

	@Override
	public List<MerchantVo> getPortalMerchant(int num) {
		return merchantDao.getPortalMerchant(num);
	}

	@Override
	public void saveMerchantManage(MerchantManage mm) {
		if(mm!=null)
			merchantManageDao.saveMerchantManage(mm);
	}

	@Override
	public List<MerchantManage> getMerchantManage() {
		return merchantManageDao.getMerchantManage();
	}

	@Override
	public void clearOldMerchantManage() {
       merchantManageDao.clearOldMerchantManage(); 		
	}

	@Override
	public Pager getFrontMerchantList(Pager pager,String viewCode,
			String destinationCode, String keyWord, String type,String rule) {
       pager.setPageSize(10);		
       return merchantDao.getFrontMerchantList(pager,viewCode,destinationCode,keyWord,type,rule);
	}

	@Override
	public List<Map<String,Object>> getFrontMerchantByType(String typeCode,int num) {
		if(StringUtils.isNotBlank(typeCode)){
			List<Map<String,Object>> result = merchantDao.getFrontMerchantByType(typeCode,num);
			for (Map<String, Object> map : result) {
				map.put("merchantdetail", HTMLTagUtil.delHTMLTag(map.get("merchantdetail").toString()));
				map.put("merchantdetail", map.get("merchantdetail").toString().replace("&nbsp;", ""));
			}
		   return result;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getFrontTravel(int num) {
		return merchantDao.getFrontTravel(num);
	}

	@Override
	public List<Map<String, Object>> getOffice(int num) {
		return merchantDao.getOffice(num);
	}

	@Override
	public List<Map<String,Object>> getViewsByMerchantCode(String merchantCode) {
		if(StringUtils.isNotBlank(merchantCode))
			return merchantDao.getViewsByMerchantCode(merchantCode);
		return null;
	}

	@Override
	public Pager getReplyInfoByMerchantCode(Pager pager,
			String merchantCode) {
		if(StringUtils.isNotBlank(merchantCode))
			return merchantDao.getReplyInfoByMerchantCode(pager,merchantCode);
		return null;
	}

	@Override
	public List<Map<String, Object>> getPraiseAndView(String merchantCode) {
		if(StringUtils.isNotBlank(merchantCode))
				return merchantDao.getPraiseAndView(merchantCode);
		return null;
	}

	@Override
	public Pager findListByPager(String sql, List param, Pager pager) {
		// TODO Auto-generated method stub
		return merchantDao.findListPagerBySql(Merchant.class, pager, sql, param);
	}

    @Override
    public List<HotMerchantVo> getHotMerchantByView(String viewCode, int row) {
        return merchantDao.getHotMerchantByView(viewCode, row);
    }

	@Override
	public Pager orderByMerchat(Pager pager, String rule) {
		 
		return merchantDao.orderByMerchat(pager,rule);
	}

	@Override
	public List<Map<String, Object>> getMUrlAndCodeByMType(String mTypeCode,
			int num,String desCode) {
		if(StringUtils.isBlank(mTypeCode))
		  return null;
		return merchantDao.getMUrlAndCodeByMType(mTypeCode,num,desCode);
	}

	@Override
	public Pager getOfficeList(Pager pager) {
		return merchantDao.getOfficeList(pager);
	}

	@Override
	public String getCtripPrice(String mName) {
     	if(StringUtils.isBlank(mName))
     		return "0";
		return merchantDao.getCtripPrice(mName);
	}

	@Override
	public List<Map<String, Object>> getTop3Mt() {
        return merchantDao.getTop3Mt();
	}

    @Override
    public List<HotMerchantVo> getHotMerchantBydestinationCode(String destinationCode, int row) {
        return merchantDao.getHotMerchantBydestinationCode(destinationCode, row);
    }

	@Override
	public List<Merchant> findbypro(String string, String name) {
		// TODO Auto-generated method stub
		return merchantDao.findByProperty(string, name);
	}

	@Override
	public List<Merchant> findbypro(String pro, Object value) {
		return merchantDao.findByProperty(pro, value);
	}

	@Override
	public String getCtripNow(String mName) {
		if(StringUtils.isBlank(mName)){
			return "0";
		}
		return merchantDao.getCtripNow(mName);
	}

    @Override
    public String getScore(String code) {
        return StringUtil.computScore(merchantDao.getScore(code));
    }

}
