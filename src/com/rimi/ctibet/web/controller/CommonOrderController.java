package com.rimi.ctibet.web.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.CommonOrder;
import com.rimi.ctibet.domain.Equipment;
import com.rimi.ctibet.web.service.ICommonOrderService;
import com.rimi.ctibet.web.service.IEquimentService;

/**
 * 骑行专区 / 订单管理 / 控制器
 *
 * @author tangHQ
 * @date 2015-4-9
 */
@Controller
@RequestMapping("web/common-order")
public class CommonOrderController extends BaseController {
	
	/** 业务接口 */
	@Resource ICommonOrderService commonOrderService;
	@Resource IEquimentService equipmentServiceImpl;
	
	/** JSP路径前缀 */
	private static final String JSP_BASE_PREFIX = "manage/html/ride/";
	
	/** 订单管理主页面. */
	private static final String JSP_ORDER_MANAGE = JSP_BASE_PREFIX + "order-manage";
	
	/**
	 * 订单管理首页
	 * 
	 * @param request
	 * @return JSP页面
	 */
	@RequestMapping("page")
	public String commonOrder () { 
		request.setAttribute("time", String.format("%tF", new Date()));
		return JSP_ORDER_MANAGE;
	}
	
	/**
	 * 加载订单数据
	 * 
	 * @param pager 分页对象
	 * @return JSON数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="list", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public String loadOrders (Pager pager, CommonOrder order, String orderTime) {
		
		commonOrderService.queryCommonOrderByPager(pager, order, orderTime);
		
		List<CommonOrder> dataList = pager.getDataList();
		
		// 查询为装备订单的订单URL
		if (dataList != null && !dataList.isEmpty()) {
			for (CommonOrder o : dataList) {
				String type = o.getType(); // 订单类型
				String code = o.getGoodsCode(); // 订单编号
				if (StringUtils.isBlank(type) || !CommonOrder.TYPE_EQUIPMENT.equals(type)) continue; // 如果不是装备订单，不予处理
				if (StringUtils.isBlank(code)) continue; // 如果订单编号为空，则信息丢失或发生错误，不予处理
				Equipment equip = equipmentServiceImpl.findByCode(code); // 查询对应装备信息
				if (StringUtils.isBlank(equip.getUrl())) continue; // 如果装备信息中的装备链接为空，则发生信息丢失或错误，不予处理
				o.setEquipUrl(equip.getUrl()); // 最后一步，设置该订单链向装备的订单URL
			}
		}
		
		return obj2json(pager);
	}
	
	
	/**
	 * 删除订单
	 * 
	 * @return
	 */
	@RequestMapping(value = "{code}", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public String deleteOrder (@PathVariable("code") String orderCode) {
		
		JSONObject res = new JSONObject();
		
		if (StringUtils.isNotBlank(orderCode)) {
			res.put("status", commonOrderService.deleteLogicalByCode(orderCode) > 0);
		} else res.put("status", false);
		
		return res.toString();
	}
}
