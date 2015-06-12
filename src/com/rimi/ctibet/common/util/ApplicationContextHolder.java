package com.rimi.ctibet.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHolder implements ApplicationContextAware{
	 private static ApplicationContext applicationContext;
	 
	 private static Log log = LogFactory.getLog(ApplicationContextHolder.class);

	 public  void setApplicationContext(ApplicationContext applicationContext) {
	  
	   this.applicationContext = applicationContext;
	  
	 }
	 
	 public static ApplicationContext getApplicationContext() {
	   return applicationContext;
	 }

	 public static Object getBean(String name) {
	  return getApplicationContext().getBean(name);
	 }
}
