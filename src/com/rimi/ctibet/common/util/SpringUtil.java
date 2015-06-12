package com.rimi.ctibet.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware  {
	
	private static ApplicationContext appCtx; 
	
	 public static ApplicationContext getAppCtx() {
		return appCtx;
	}
	public static void setAppCtx(ApplicationContext appCtx) {
		SpringUtil.appCtx = appCtx;
	}
	public static ApplicationContext getApplicationContext(){  
	        return appCtx;  
	    }  
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		 appCtx = applicationContext; 		
	}  
}
