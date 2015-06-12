/**
 * Alipay.com Inc.
 * Copyright (c) 2005-2006 All Rights Reserved.
 */
package com.alipay.client.util;

import java.io.InputStream;
import java.util.List;

import org.nuxeo.common.xmap.XMap;

/**
 * XMap工具类，用来解析xml文件 
 * @author stone.zhangjl
 * @version $Id: XMapUtil.java, v 0.1 2008-8-21 上午10:11:28 stone.zhangjl Exp $
 */
public class XMapUtil {
    private static final XMap XMAP;

    static {
        XMAP = new XMap();
    }

    /**
     * 注册Object。
     * 
     * @param clazz
     */
    public static void register(Class<?> clazz) {
        if (clazz != null) {
            XMAP.register(clazz);
        }
    }

    /**
     * 解析xml到Object
     * @param is
     * @return
     * @throws Exception
     */
    public static Object load(InputStream is) throws Exception {
        Object obj = null;
        try {
            obj = XMAP.load(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return obj;
    }

    /**
     * Object到XML。
     * 
     * @param obj
     * @param encoding
     * @param outputsFields
     * @return
     * @throws PaygwException 
     */
    public static String asXml(Object obj, String encoding, List<String> outputsFields)
                                                                                       throws Exception {

        return XMAP.asXmlString(obj, encoding, outputsFields);
    }
}