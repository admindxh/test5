package com.rimi.ctibet.common.util.cache;
/**
 * @author liyang
 * @version 1.0
 * 创建时间：Apr 1, 2010 10:51:59 AM
 * 类说明：
 * 存放缓存数据的Bean
 */
public class CacheData {
	
	 private Object data;  
	 private long time;
	 private int count;
	    
	 public CacheData(){
	        
	 }
	    
	    public CacheData(Object data, long time, int count){
	        this.data = data;
	        this.time = time;
	        this.count = count;
	    }
	    
	    public CacheData(Object data){
	        this.data = data;
	        this.time = System.currentTimeMillis();
	        this.count = 1;
	    }
	    
	    public void addCount(){
	        count++;
	    }
	    
	    public int getCount(){
	        return count;
	    }
	    public void setCount(int count){
	        this.count = count;
	    }
	    public Object getData(){
	        return data;
	    }
	    public void setData(Object data){
	        this.data = data;
	    }
	    public long getTime(){
	        return time;
	    }
	    public void setTime(long time){
	        this.time = time;
	    }


}
