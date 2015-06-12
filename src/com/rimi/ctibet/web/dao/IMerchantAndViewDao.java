package com.rimi.ctibet.web.dao;

import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.MerchantAndView;
import com.rimi.ctibet.domain.View;

public interface IMerchantAndViewDao extends BaseDao<MerchantAndView> {
 /**
  * 通过Viewcode获取merchant
  * @param code
  * @return
  */
  public List<Merchant> getMerchantByView(String code);
  
  /**
   * 商户的所有景点
   * @return
   */
  public List<View> getViewsByMCode(String  code);
  
  
  public void deleteByMerchatCode(String merchantCode);
  

}
