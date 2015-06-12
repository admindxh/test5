package com.rimi.ctibet.init;

import java.io.File;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

/**
 * 启动服务器时清空上传文件临时目录
 * 
 * 需要清空的目录配置到 /ctibet/resources/temppath.properties文件中
 * 
 * @author yeyu
 *
 */
public class RemoveUploadTempFloder extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final	Logger LOGGER = Logger.getLogger(RemoveUploadTempFloder.class);
	
    public RemoveUploadTempFloder() {}

    @Override
	public void init(ServletConfig config) throws ServletException {
    	LOGGER.info("开始清空上传temp目录");
    	long op = System.currentTimeMillis();
    	
    	try {
    		
			Properties properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream("temppath.properties"));
			Set<Object> keySet = properties.keySet();
			String base = config.getServletContext().getRealPath("/");
			for (Object key : keySet) {
				String property = properties.getProperty(key.toString());
				if(property!=null && !property.equals("")){
					deleteFile(new File(base + property));
				}
			}
			
		} catch (Exception e) {
			LOGGER.info("清空出错");
		}
    	
    	LOGGER.info("清空上传temp目录结束 耗时：" + (System.currentTimeMillis()-op) + "ms");
	}
    
    private void deleteFile(File file){
    	if(file.exists()){
	    	File[] listFiles = file.listFiles();
	    	if(listFiles != null && listFiles.length > 0){
		    	for (File f : listFiles) {
					if(f.isDirectory()){
						//deleteFile(f);
						deleteFloder(f);
					}
					if(f.isFile() && f.exists()){
						if(f.delete()){
							LOGGER.debug("删除->" + f.getAbsolutePath() + "成功");
						}else{
							LOGGER.debug("删除->" + f.getAbsolutePath() + "失败");
						}
						//logger.info("->" + f.getAbsolutePath());
					}
				}
	    	}
    	}else{
    		LOGGER.info("->" + file.getAbsolutePath() + " 无此目录");
    	}
    }
    
    private void deleteFloder(File file){
    	deleteFile(file);
    	file.delete();
    }
    
    public static void main(String[] args) {
    	//deleteFile(new File("D:/Bray/projects/ctibet/ctibet/WebRoot/upload/img/activity/temp"));
    	try {
    		// "temppath.properties"
			Properties properties = new Properties();
			properties.load(RemoveUploadTempFloder.class.getClassLoader().getResourceAsStream("temppath.properties"));
			Set<Object> keySet = properties.keySet();
			for (Object key : keySet) {
				//System.out.println(properties.getProperty(key.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
}
