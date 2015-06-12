package com.rimi.ctibet.web.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.FrontContentStatis;
import com.rimi.ctibet.web.controller.vo.BaseCodeVo;
import com.rimi.ctibet.web.controller.vo.FrontContentVo;

public interface IFrontContentStatisService  extends BaseService<FrontContentStatis>{
	public List<BaseCodeVo> getBaseCodeListByPcode(final String pcode);
	
	public BaseCodeVo getBaeCodeVo(String code);
	
	/**
	 * 分页统计内容信息，等
	 * @return
	 */
	public Pager getPageFrontContentByCon(String start,String end,String proType,Pager pager);
	public FrontContentVo getAllFrontContent(String start,String end,String proType	);
	
	
	/**
	 * 商户  统计全部信息
	 * @param start
	 * @param end
	 * @param proType
	 * @return
	 */
	public FrontContentVo getAllFrontMerchant(String start,String end,String proType,String merchantName	);
	/**
	 * 查询商户分页
	 */
	public Pager getPageFrontMerchanttByCon(String start,String end,String proType,String merchantName,Pager pager);
	
	
	
	public FrontContentVo getAllFrontAderv(String start, String end,
			String proType);
	public Pager getPageFrontAdervByCon(String start, String end,
			String proType, Pager pager);
}
