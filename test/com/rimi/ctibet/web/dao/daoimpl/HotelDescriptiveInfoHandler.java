package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
//需根据文档格式写逻辑
public class HotelDescriptiveInfoHandler extends DefaultHandler {

	private String lcName;// 标记解析 到那个元素了
	private String des = "";
	private List<String> imgs = new ArrayList<String>();
	
	
	public String  getDes(){
		return des;
	}
	public List<String> getImgs(){
		return imgs;
	}

	@Override
	public void startDocument() throws SAXException {
		//System.out.println("开始解析文档");
		//初始化对象 ;准备一行一行的读xml
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		//System.out.println("文档解析完成！！！");
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.lcName=qName;
//		//System.out.println("开始解析元素" + qName);
		if (attributes.getLength() > 0) {
//			//System.out.println("开始解析元素属性");
			int i = 0;
			while (i < attributes.getLength()) {
				if("HotelName".equals(attributes.getLocalName(i))){}
//				//System.out.println("属性：" + attributes.getLocalName(i) + ":"
//						+ attributes.getValue(i));
				i++;
			}
//			//System.out.println("解析元素属性完成");
		}
		super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
//		//System.out.println("解析元素" + qName + ":" + localName + "完成！");
		super.endElement(uri, localName, qName);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String data = new String(ch, start, length);
		//System.out.println("元素 "+lcName+" 的值:"+data);
		//解析其值 保存到对象 中
		if("Description".equals(lcName)){
			des += data;
		}
		if("URL".equals(lcName)&&data.indexOf(".jpg")>0){
			imgs.add(data);
		}
	}

	@Override
	public void warning(SAXParseException e) throws SAXException {
		//System.out.println("警告");
		e.printStackTrace();
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		System.err.println("出错了!!!! ");
		e.printStackTrace();
		super.error(e);
	}
}
