package com.rimi.ctibet.common.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 酒店价格
 * Created by shangzf on 2015/1/16.
 */
public class HotelRatePlanHandler extends DefaultHandler {

    private List<String> list = new ArrayList<String>();

    public List<String> getList() {
        return list;
    }

    public HotelRatePlanHandler() {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        int l = attributes.getLength();
        if (l > 0 && "BaseByGuestAmt".equals(qName)) {
            for (int i = 0; i < l; i++) {
                if ("AmountBeforeTax".equals(attributes.getQName(i))) {
                    list.add(attributes.getValue(i));
                    break;
                }
            }
        }
        super.startElement(uri, localName, qName, attributes);
    }

}
