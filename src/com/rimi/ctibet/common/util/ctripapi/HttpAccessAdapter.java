package com.rimi.ctibet.common.util.ctripapi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.GZIPInputStream;


public class HttpAccessAdapter {
	private static ArrayList<HttpRequestProperty> staticHttpRequestProperty = LoadStaticHttpRequestProperties();

	public static String sendRequestToUrl(String requestContent,
			String urlString, String paraName) {
		InputStream errorStream = null;
		HttpURLConnection httpCon = null;
		try {
			URL url = new URL(urlString);
			String content = xmlToString(requestContent);
			String soapMessage = addSoapShell(content);
			httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setRequestMethod("POST");
			httpCon.setDoOutput(true);
			httpCon.setDoInput(true);

			for (int i = 0; i < staticHttpRequestProperty.size(); i++) {
				httpCon
						.setRequestProperty(staticHttpRequestProperty.get(i)
								.getName(), staticHttpRequestProperty.get(i)
								.getValue());
			}

			httpCon.setRequestProperty("Content-Length", String
					.valueOf(soapMessage.length()));

			OutputStream outputStream = httpCon.getOutputStream();
			outputStream.write(soapMessage.getBytes("UTF-8"));
			outputStream.close();

			InputStream inputStream = httpCon.getInputStream();
			// String encoding = httpCon.getRequestProperty("Accept-Encoding");
			BufferedReader br = null;
			// httpCon.getResponseMessage();
			if (httpCon.getRequestProperty("Accept-Encoding") != null) {
				try {
					GZIPInputStream gzipStream = new GZIPInputStream(
							inputStream);
					br = new BufferedReader(new InputStreamReader(gzipStream,
							"UTF-8"));
				} catch (IOException e) {
					br = new BufferedReader(new InputStreamReader(inputStream,
							"UTF-8"));
				}
			} else {
				br = new BufferedReader(new InputStreamReader(inputStream,
						"UTF-8"));
			}
			StringBuffer result = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			inputStream.close();
			br.close();
			return stringToXML(removeSoapShell(result.toString()));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			errorStream = httpCon.getErrorStream();
			if (errorStream != null) {
				String errorMessage = null;
				String line = null;
				BufferedReader br = new BufferedReader(new InputStreamReader(
						errorStream));
				try {
					while ((line = br.readLine()) != null) {
						errorMessage += line;
					}
					br.close();
					return errorMessage;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			try {
				errorStream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (SdkSystemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpCon != null) {
				httpCon.disconnect();
			}
		}

		return "";
	}

	// 锟斤拷Soap锟斤拷锟斤拷锟接碉拷锟斤拷锟斤拷锟斤拷锟斤拷
	private static String addSoapShell(
			/* String parameterName, */String patameterValue) throws Exception {
		BufferedReader bufferedReader = null;
		try {
			InputStream in = HttpAccessAdapter.class
					.getResourceAsStream("RequestSOAPTemplate.xml");
			bufferedReader = new BufferedReader(new InputStreamReader(in));
			String text = bufferedReader.readLine();
			StringBuilder soapShellStringBuilder = new StringBuilder();
			while (text != null) {
				soapShellStringBuilder.append(text);
				text = bufferedReader.readLine();
			}
			String result = soapShellStringBuilder.toString();
			return result.replaceAll("string", patameterValue);
		} catch (FileNotFoundException e) {
			throw new SdkSystemException("锟睫凤拷锟揭碉拷锟斤拷锟斤拷模锟斤拷锟侥硷拷");
		} catch (IOException e) {
			throw new SdkSystemException("锟睫凤拷锟斤拷取锟斤拷锟斤拷模锟斤拷锟侥硷拷");
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
				}
			}
		}
	}

	// 删锟斤拷Soap锟斤拷锟斤拷锟较�
	private static String removeSoapShell(String source) {
		String result = "";
		int indexElementBegin = source.indexOf("<RequestResult>");
		int indexElementEnd = source.indexOf("</RequestResult>");
		if (indexElementBegin > 0 && indexElementEnd > 0) {
			result = source.substring(indexElementBegin
					+ "<RequestResult>".length(), indexElementEnd);
		}
		return result;
	}

	// 锟斤拷xml锟侥碉拷转锟斤拷为锟缴达拷锟斤拷锟斤拷址锟�
	private static String xmlToString(String source) {
		String result = source.replaceAll("<", "&lt;");
		result = result.replaceAll(">", "&gt;");
		return result;
	}

	// 锟斤拷锟斤拷锟截碉拷锟街凤拷转锟斤拷为锟缴凤拷锟斤拷锟叫伙拷XML锟侥憋拷
	private static String stringToXML(String source) {
		String result = source.replaceAll("&lt;", "<");
		result = result.replaceAll("&gt;", ">");
		return result;
	}

	// 锟斤拷锟截撅拷态锟斤拷息
	private static ArrayList<HttpRequestProperty> LoadStaticHttpRequestProperties() {
		ArrayList<HttpRequestProperty> staticHttpRequestProperty = new ArrayList<HttpRequestProperty>();
		//staticHttpRequestProperty.add(new HttpRequestProperty("Host",
//				"crmint.dev.sh.ctriptravel.com"));
		staticHttpRequestProperty.add(new HttpRequestProperty("Content-Type",
				"text/xml; charset=UTF-8"));
		staticHttpRequestProperty.add(new HttpRequestProperty("SOAPAction",
				"http://ctrip.com/Request"));
		staticHttpRequestProperty.add(new HttpRequestProperty(
				"Accept-Encoding", "gzip, deflate"));

		return staticHttpRequestProperty;
	}
	
	//==============================================项目查询方法====================================================
    //响应酒店列表 
	public static String respForHotelList(String cityCode) {

		//String request = "<Request><Header AllianceID=\"1\" SID=\"1\" TimeStamp=\"1352265056449\" Signature=\"E7C13030A4763015A3F7BA5C32613FCF\" RequestType=\"OTA_Ping\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\"/><HotelRequest><RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><ns:OTA_PingRQ><ns:EchoData>锟斤拷通锟斤拷锟斤拷</ns:EchoData></ns:OTA_PingRQ></RequestBody></HotelRequest></Request>";
		String request = createRequestXml(cityCode);
		String url = "http://openapi.ctrip.com/Hotel/OTA_HotelSearch.asmx?wsdl";
		// "http://crmint.dev.sh.ctriptravel.com/Hotel/OTA_Ping.asmx?wsdl";
		String paraName = "requestXML";
		String response = HttpAccessAdapter.sendRequestToUrl(request, url,
				paraName);
		return response;
	}
    //响应酒店详细
	public static String respForHotelDetail(String cityCode) {

		//String request = "<Request><Header AllianceID=\"1\" SID=\"1\" TimeStamp=\"1352265056449\" Signature=\"E7C13030A4763015A3F7BA5C32613FCF\" RequestType=\"OTA_Ping\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\"/><HotelRequest><RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><ns:OTA_PingRQ><ns:EchoData>锟斤拷通锟斤拷锟斤拷</ns:EchoData></ns:OTA_PingRQ></RequestBody></HotelRequest></Request>";
		String request = createRequestDetailXml(cityCode);
	    String url = "http://openapi.ctrip.com/Hotel/OTA_HotelDescriptiveInfo.asmx?wsdl";
		// "http://crmint.dev.sh.ctriptravel.com/Hotel/OTA_Ping.asmx?wsdl";
		String paraName = "requestXML";

		String response = HttpAccessAdapter.sendRequestToUrl(request, url,
				paraName);
		return response;
	}
	/**
	 * 获取酒店价格
	 * @param hotelCode 酒店编号
	 * @return stirng
	 */
	public static String respForHotelRatePlan(String hotelCode) {
		String request = creatRequestHotelRatePlan(hotelCode);
		String url = "http://openapi.ctrip.com/Hotel/OTA_HotelRatePlan.asmx?wsdl";
		String paraName = "requestXML";
		return sendRequestToUrl(request, url, paraName);
	}
	//测试酒店查询生成的xml,必要的查询条件xml格式。
	private static String createRequestXml(String cityCode) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("<Request><Header AllianceID=\"");
			sb.append(ConfigData.AllianceId);
			sb.append("\" SID=\"");
			sb.append(ConfigData.sid);
			sb.append("\" TimeStamp=\"");
			long timestamp = SignatureUtils.getTimeStamp();
			sb.append(timestamp);
			sb.append("\" Signature=\"");
	
			String signature = SignatureUtils.calculationSignature(timestamp
					+ "", ConfigData.AllianceId, ConfigData.secretKey, ConfigData.sid, ConfigData.otaHotelSearch);
			sb.append(signature);
			sb.append("\" RequestType=\"");
			sb.append(ConfigData.otaHotelSearch);
			sb.append("\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\"/>" +
		    "<HotelRequest><RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" +
			"<ns:OTA_HotelSearchRQ Version=\"0.0\" PrimaryLangID=\"zh\" xsi:schemaLocation=\"http://www.opentravel.org/OTA/2003/05 OTA_HotelSearchRQ.xsd\" xmlns=\"http://www.opentravel.org/OTA/2003/05\">" +
			"<ns:Criteria>"+
		    "<ns:Criterion>"+
		    "<ns:HotelRef HotelCityCode=\""+cityCode+"\" />"+
		    "<ns:Award Provider=\"HotelStarRate\" Rating=\"2\"/>"+
		    "</ns:Criterion>"+
		    "</ns:Criteria>"+
			"</ns:OTA_HotelSearchRQ>" +
			"</RequestBody>" +
			"</HotelRequest>" +
			"</Request>");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return sb.toString();
	}
	//根据酒店的code查询出酒店的详细信息
	private static String createRequestDetailXml(String hotelCode){
		StringBuffer sb = new StringBuffer();
		try{
			sb.append("<Request><Header AllianceID=\"");
			sb.append(ConfigData.AllianceId);
			sb.append("\" SID=\"");
			sb.append(ConfigData.sid);
			sb.append("\" TimeStamp=\"");
			long timestamp = SignatureUtils.getTimeStamp();
			sb.append(timestamp);
			sb.append("\" Signature=\"");
			String signature = SignatureUtils.calculationSignature(timestamp
					+ "", ConfigData.AllianceId, ConfigData.secretKey, ConfigData.sid, ConfigData.otaHotelDescriptiveInfo);
			sb.append(signature);
			sb.append("\" RequestType=\"");
			//查询酒店详细信息
			sb.append(ConfigData.otaHotelDescriptiveInfo);
			sb.append("\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\"/>" +
		    "<OTA_HotelDescriptiveInfoRQ Version=\"1.0\" xsi:schemaLocation=\"http://www.opentravel.org/OTA/2003/05 OTA_HotelDescriptiveInfoRQ.xsd\" xmlns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
			"<HotelDescriptiveInfos><HotelDescriptiveInfo HotelCode=\""+hotelCode+"\">" +
			"<HotelInfo SendData=\"true\"/><FacilityInfo SendGuestRooms=\"true\"/><AreaInfo SendAttractions=\"true\" SendRecreations=\"true\"/><MultimediaObjects SendData=\"true\"/></HotelDescriptiveInfo><HotelDescriptiveInfo HotelCode=\""+hotelCode+"\">"+
		    "<HotelInfo SendData=\"true\"/><FacilityInfo SendGuestRooms=\"true\"/><AreaInfo SendAttractions=\"true\" SendRecreations=\"true\"/><MultimediaObjects SendData=\"true\"/></HotelDescriptiveInfo><HotelDescriptiveInfo HotelCode=\""+hotelCode+"\">"+
		    "<HotelInfo SendData=\"true\"/><FacilityInfo SendGuestRooms=\"true\"/><AreaInfo SendAttractions=\"true\" SendRecreations=\"true\"/><MultimediaObjects SendData=\"true\"/></HotelDescriptiveInfo></HotelDescriptiveInfos></OTA_HotelDescriptiveInfoRQ></Request>");
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	
	
	/**
	 * 根据酒店编号获取酒店价格
	 * @param hotelCode 酒店编号 String
	 * @return string
	 */
	private static String creatRequestHotelRatePlan(String hotelCode) {
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Calendar calendar = Calendar.getInstance();
		String st = simpleDateFormat.format(calendar.getTime());
		String stf = st.substring(0,st.length()-2) + ":" + st.substring(st.length()-2);
		String end = dateFormat.format(calendar.getTime());
		int i = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, i - 2);
		String start = dateFormat.format(calendar.getTime());
		long timestamp = SignatureUtils.getTimeStamp();
		String signature = null;
		try {
			signature = SignatureUtils.calculationSignature(timestamp + "", ConfigData.AllianceId, ConfigData.secretKey, ConfigData.sid, ConfigData.otaHotelRatePlan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		builder.append("<Request><Header AllianceID=\"")
				.append(ConfigData.AllianceId)
				.append("\" SID=\"")
				.append(ConfigData.sid)
				.append("\" TimeStamp=\"")
				.append(timestamp)
				.append("\" Signature=\"")
				.append(signature)
				.append("\" RequestType=\"")
				.append(ConfigData.otaHotelRatePlan)
				.append("\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\"/><HotelRequest><RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><ns:OTA_HotelRatePlanRQ TimeStamp=\"")
				.append(stf)
				.append("\" Version=\"1.0\"><ns:RatePlans><ns:RatePlan><ns:DateRange Start=\"")
				.append(start)
				.append("\" End=\"")
				.append(end)
				.append("\"/><ns:RatePlanCandidates><ns:RatePlanCandidate AvailRatesOnlyInd=\"true\" ><ns:HotelRefs><ns:HotelRef HotelCode=\"")
				.append(hotelCode)
				.append("\"/></ns:HotelRefs></ns:RatePlanCandidate></ns:RatePlanCandidates></ns:RatePlan></ns:RatePlans></ns:OTA_HotelRatePlanRQ></RequestBody></HotelRequest></Request>");
		return builder.toString();
	}
	/**
	 * 根据城市编号获取団游信息
	 * @param cityCode 城市编号 String
	 * @return string
	 */
	public static String creatRequestGroupTravelByCityCode(String hotelCode) {
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Calendar calendar = Calendar.getInstance();
		String st = simpleDateFormat.format(calendar.getTime());
		String stf = st.substring(0,st.length()-2) + ":" + st.substring(st.length()-2);
		String end = dateFormat.format(calendar.getTime());
		int i = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, i - 2);
		String start = dateFormat.format(calendar.getTime());
		long timestamp = SignatureUtils.getTimeStamp();
		String signature = null;
		try {
			signature = SignatureUtils.calculationSignature(timestamp + "", ConfigData.AllianceId, ConfigData.secretKey, ConfigData.sid, ConfigData.otaHotelRatePlan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		builder.append("<Request><Header AllianceID=\"")
				.append(ConfigData.AllianceId)
				.append("\" SID=\"")
				.append(ConfigData.sid)
				.append("\" TimeStamp=\"")
				.append(timestamp)
				.append("\" Signature=\"")
				.append(signature)
				.append("\" RequestType=\"")
				.append("Product_Get")
				.append("\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\"/><SearchProductRQ><RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><ns:OTA_HotelRatePlanRQ TimeStamp=\"")
				.append(stf)
				.append("\" Version=\"1.0\"><ns:RatePlans><ns:RatePlan><ns:DateRange Start=\"")
				.append(start)
				.append("\" End=\"")
				.append(end)
				.append("\"/><ns:RatePlanCandidates><ns:RatePlanCandidate AvailRatesOnlyInd=\"true\" ><ns:HotelRefs><ns:HotelRef HotelCode=\"")
				.append(hotelCode)
				.append("\"/></ns:HotelRefs></ns:RatePlanCandidate></ns:RatePlanCandidates></ns:RatePlan></ns:RatePlans></ns:OTA_HotelRatePlanRQ></RequestBody></HotelRequest></Request>");
		return builder.toString();
	}
//	<Request>
//	<Header AllianceID="24507" SID="460204" TimeStamp="1421532195" Signature="D8D98974741CC9304F757255F70B0616" RequestType="Product_Get" AsyncRequest="false" Timeout="0" MessagePriority="3" />
//	<SearchProductRQ>
//	  <SearchCondition> 
//	   <CityInfo>
//	     <CityID>14</CityID>
//	     <CityName> </CityName>
//	   </CityInfo>
//	<ItemTypeList> 
//	<ItemType>1</ItemType> 
//	</ItemTypeList> 
//	</SearchCondition> 
//	<DisplaySettings>
//	     <PageSettings>
//	      <PageSize>1000</PageSize>
//	      <CurrentPageIndex>1</CurrentPageIndex>
//	    </PageSettings>
//	    <SortType>0</SortType>
//	</DisplaySettings>
//	</SearchProductRQ>
//
//	</Request>
}
