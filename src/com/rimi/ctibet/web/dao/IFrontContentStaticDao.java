package com.rimi.ctibet.web.dao;

import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.FrontContentStatis;
import com.rimi.ctibet.web.controller.vo.BaseCodeVo;
import com.rimi.ctibet.web.controller.vo.FrontContentVo;

public interface IFrontContentStaticDao  extends BaseDao<FrontContentStatis>{
	/**
	 * 查询类型
	 * @param pcode
	 * @return
	 */
	public List<BaseCodeVo> getBaseCodeListByPcode(String pcode);
	
	/**
	 * 统计全部信息
	 * @param start
	 * @param end
	 * @param proType
	 * @return
	 */
	public FrontContentVo getAllFrontContent(String start,String end,String proType	);
	
	/**
	 * 分页统计内容信息，等
	 * @return
	 */
	public Pager getPageFrontContentByCon(String start,String end,String proType,Pager pager);
	
	/**
	 * 商户全部信息统计
	 * @param start
	 * @param end
	 * @param proType
	 * @return
	 */
	public FrontContentVo getAllFrontMerchant(String start,String end,String proType,String merchantName	);
	
	
	
	/**
	 * 分页统计商户信息，等
	 * @return
	 */
	public Pager getPageFrontMerchanttByCon(String start,String end,String proType,String merchantName,Pager pager);
	
	
	
	/**
	 * 广告统计全部信息
	 * @param start
	 * @param end
	 * @param proType
	 * @return
	 */
	public FrontContentVo getAllFrontAderv(String start,String end,String proType	);
	
	/**
	 * 广告统计全部信息 分页
	 * @return
	 */
	public Pager getPageFrontAdervByCon(String start,String end,String proType,Pager pager);
	
	
}
