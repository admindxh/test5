package com.rimi.ctibet.common.util;

import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//获取当前连接数
public class ConnectionUtil {
	static ComboPooledDataSource ds = new ComboPooledDataSource();
    public static void showDSInfo() {
    	try{
		//System.out.println(ds.getMaxPoolSize());// 最大连接数
	    //System.out.println(ds.getMinPoolSize());// 最小连接数
	    //System.out.println(ds.getNumBusyConnections());// 正在使用连接数
	    //System.out.println(ds.getNumIdleConnections());// 空闲连接数
	    //System.out.println(ds.getNumConnections());// 总连接数
	    }catch(Exception e){e.printStackTrace();}
      }
   }
