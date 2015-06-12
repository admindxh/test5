package com.rimi.ctibet.common.util;

import com.rimi.ctibet.common.util.ctripapi.HttpAccessAdapter;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.web.dao.daoimpl.HotelDescriptiveInfoHandler;
import com.rimi.ctibet.web.service.IMerchantService;
import org.dom4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

@Controller
// @Component
@RequestMapping("ctrip")
public class CtripUtil {

	// @Resource
	// private static IMerchantService merchantService = new
	// MerchantServiceImpl();

	// hotelId是该 酒店所在携程网的id，然后根据携程网跳转规则拼接url
	public static String getCtripUrp(String hotelId) {
		return "http://u.ctrip.com/union/CtripRedirect.aspx?" + "TypeID=60&"
				+ "CheckInDate=2013-01-10&" + "CheckOutDate=2013-01-11&"
				+ "sid=460204&" + "allianceid=24507&" + "HotelID=" + hotelId;
	}

	// 获取指定城市的 酒店id与url,cityCode是携程网的城市code
	public static List<Map<String, Object>> getCtripInfoByCityCode(
			String cityCode) throws Exception {
		IMerchantService merchantService = (IMerchantService) SpringUtil
				.getAppCtx().getBean("merchantService");
		// List<Map<String,Object>> hotelInfos = new
		// ArrayList<Map<String,Object>>();
		String xmlList = HttpAccessAdapter.respForHotelList(cityCode);
		Document documentList;
		try {
			documentList = DocumentHelper.parseText(xmlList);
			Element rootList = documentList.getRootElement();
			Element HotelResponse = rootList.element("HotelResponse");
			Element OTA_HotelSearchRS = HotelResponse
					.element("OTA_HotelSearchRS");
			Element Properties = OTA_HotelSearchRS.element("Properties");
			List<Element> property = Properties.elements("Property");
			int i = 1;
			for (Element element : property) {
				List<Attribute> attr = element.attributes();

				// 封装单个酒店的信息
				Map<String, String> hotelInfo = new HashMap<String, String>();

				for (Attribute attribute : attr) {
					if ("HotelName".equals(attribute.getName())) {
						hotelInfo.put(attribute.getName(), attribute.getText());
					} else if ("HotelId".equals(attribute.getName())) {
						hotelInfo.put(attribute.getName(), attribute.getText());
						hotelInfo.put("HotelUrl",
								"http://u.ctrip.com/union/CtripRedirect.aspx?"
										+ "TypeID=60&CheckInDate=2013-01-10&"
										+ "CheckOutDate=2013-01-11&"
										+ "sid=460204&" + "allianceid=24507&"
										+ "HotelID=" + attribute.getText());
					} else if ("HotelCode".equals(attribute.getName())) {
						hotelInfo.put(attribute.getName(), attribute.getText());
						// ==============进行酒店的静态信息查询====================
						String xmlDetail = HttpAccessAdapter
								.respForHotelDetail(attribute.getText());

						// sax解析
						InputStream xmlInputStream = new ByteArrayInputStream(
								xmlDetail.getBytes("UTF-8"));
						SAXParserFactory factory = SAXParserFactory
								.newInstance();
						SAXParser saxParser = factory.newSAXParser();
						HotelDescriptiveInfoHandler handler = new HotelDescriptiveInfoHandler();
						saxParser.parse(xmlInputStream, handler);
						hotelInfo.put("desc", handler.getDes());

						/* 获取价格 */
						String hotelRatePlan = HttpAccessAdapter
								.respForHotelRatePlan(attribute.getText());
						ByteArrayInputStream inputStream = new ByteArrayInputStream(
								hotelRatePlan.getBytes("UTF-8"));
						HotelRatePlanHandler ratePlanHandler = new HotelRatePlanHandler();
						SAXParserFactory factory1 = SAXParserFactory
								.newInstance();
						SAXParser saxParser1 = factory1.newSAXParser();
						saxParser1.parse(inputStream, ratePlanHandler);
						List<String> ratePlan = ratePlanHandler.getList();
						if (ratePlan.size() > 0) {
							Collections.sort(ratePlan,
									new Comparator<String>() {
										public int compare(String arg0,
												String arg1) {
											Double aDouble = Double
													.parseDouble(arg0);
											Double bDouble = Double
													.parseDouble(arg1);
											return aDouble.compareTo(bDouble);
										}
									});
							hotelInfo.put("ratePlan", ratePlan.get(0));/* 最低价格 */
						} else {
							hotelInfo.put("ratePlan", "0");/* 最低价格 */
						}

					}
				}
				i++;

				Merchant isMExist = merchantService.getMerchantByCode(hotelInfo
						.get("HotelCode"));
				if (isMExist == null) {
					isMExist = new Merchant();
					isMExist.setMerchantName(hotelInfo.get("HotelName"));
					isMExist.setAvaliable(2);
					isMExist.setCode(hotelInfo.get("HotelId"));
					isMExist.setMerchantDetail(hotelInfo.get("desc"));
					isMExist.setCtripUrl(hotelInfo.get("HotelUrl"));
					isMExist.setPrice(hotelInfo.get("ratePlan"));
					isMExist.setCreateTime(new Date());
					merchantService.saveMerchant(isMExist);
				} else {
					isMExist.setMerchantName(hotelInfo.get("HotelName"));
					isMExist.setCode(hotelInfo.get("HotelId"));
					isMExist.setMerchantDetail(hotelInfo.get("desc"));
					isMExist.setCtripUrl(hotelInfo.get("HotelUrl"));
					isMExist.setPrice(hotelInfo.get("ratePlan"));
					isMExist.setLastEditTime(new Date());
					merchantService.updateMerchant(isMExist);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("test")
	public @ResponseBody boolean main2() throws Exception {
       try{
		getCtripInfoByCityCode("41");
		getCtripInfoByCityCode("108");
		getCtripInfoByCityCode("575");
		return true;
		}catch(Exception e){
			return false;
		}
	
	}
	
	/**
	 * 携程数据同步页面
	 * @throws Exception
	 */
	@RequestMapping("intCtripPage")
	public String intCtripPage() throws Exception {
		
		return  "/manage/html/settings/ctriputil";
	}

	public static void main(String[] args) {
		String hotelRatePlan = HttpAccessAdapter
				.respForHotelRatePlan("1305474");
		ByteArrayInputStream inputStream;
		try {
			inputStream = new ByteArrayInputStream(hotelRatePlan
					.getBytes("UTF-8"));
			HotelRatePlanHandler ratePlanHandler = new HotelRatePlanHandler();
			SAXParserFactory factory1 = SAXParserFactory.newInstance();
			SAXParser saxParser1 = factory1.newSAXParser();
			saxParser1.parse(inputStream, ratePlanHandler);
			List<String> ratePlan = ratePlanHandler.getList();
			if (ratePlan.size() > 0) {
				Collections.sort(ratePlan, new Comparator<String>() {
					public int compare(String arg0, String arg1) {
						Double aDouble = Double.parseDouble(arg0);
						Double bDouble = Double.parseDouble(arg1);
						return aDouble.compareTo(bDouble);
					}
				});
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
