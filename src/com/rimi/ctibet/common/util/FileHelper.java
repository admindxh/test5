package com.rimi.ctibet.common.util;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rimi.ctibet.domain.Merchant;

public class FileHelper {
	/** 
	 * @author xiaozhen
	 *  
	 * @create 2014-11-19 
	 * @description 上传帮助类 
	 *  
	 */ 
	    /** 
	     * @descrption 根据HttpServletRequest对象获取MultipartFile集合 
	     * @author xz 
	     * @create 2014-11-19
	     * @param request 
	     * @param maxLength 
	     *            <strong>文件</strong>最大限制 
	     * @param allowExtName 
	     *            不允许上传的<strong>文件</strong>扩展名 
	     * @return MultipartFile集合 
	     */  
	    public static List<MultipartFile> getFileSet(HttpServletRequest request,  
	            long maxLength, String[] allowExtName,String filesName) {  
	        MultipartHttpServletRequest multipartRequest = null;  
	        try {  
	            multipartRequest = (MultipartHttpServletRequest) request;  
	        } catch (Exception e) {  
	            return new LinkedList<MultipartFile>();  
	        }  
	  
	        List<MultipartFile> files = new LinkedList<MultipartFile>();  
	        files = multipartRequest.getFiles(filesName);  
	        // 移除不符合条件的  
	        for (int i = 0; i < files.size(); i++) {  
	            if (!validateFile(files.get(i), maxLength, allowExtName)) {  
	                files.remove(files.get(i));  
	                if (files.size() == 0) {  
	                    return files;  
	                }  
	            }  
	        }  
	        return files;  
	    }  
	    /** 
	     * @descrption 保存<strong>文件</strong> 
	     * @author xz 
	     * @create 2014-11-19
	     * @param file 
	     *            MultipartFile对象 
	     * @param path 
	     *            保存路径，如“D:\\File\\” 
	     * @return 保存的全路径 如“D:\\File\\2345678.txt” 
	     * @throws Exception 
	     *             <strong>文件</strong>保存失败 
	     */  
	    public static void uploadFile(MultipartFile file, String path)  
	            throws Exception {  
	    	FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, file.getOriginalFilename()));
	   
	    }  
	    
	    public static void deleteFile(String[] filePaths,String path){
	    	  for(int j=0;j<filePaths.length;j++){
	            	String oldImg = filePaths[j];
	            	if (StringUtils.isNotBlank(oldImg)) {
						File oldFile = new File(path+oldImg);
						try {
							oldFile.delete();
						} catch (Exception e) {
							e.printStackTrace();
						}
                   }
	              }
	    }
	  
	    /** 
	     * @descrption 验证<strong>文件</strong>格式，这里主要验证后缀名 
	     * @author xz 
	     * @create 2012-11-19下午4:08:12 
	     * @param file 
	     *            MultipartFile对象 
	     * @param maxLength 
	     *            <strong>文件</strong>最大限制 
	     * @param allowExtName 
	     *            不允许上传的<strong>文件</strong>扩展名 
	     * @return <strong>文件</strong>格式是否合法 
	     */  
	    private static boolean validateFile(MultipartFile file, long maxLength,  
	            String[] allowExtName) {  
	        if (file.getSize() < 0 || file.getSize() > maxLength)  
	            return false;  
	        String filename = file.getOriginalFilename();  
	  
	        // 处理不选择<strong>文件</strong>点击上传时，也会有MultipartFile对象，在此进行过滤  
	        if (filename == "") {  
	            return false;  
	        }  
	        String extName = filename.substring(filename.lastIndexOf("."))  
	                .toLowerCase();  
	        if (allowExtName == null || allowExtName.length == 0  
	                || Arrays.binarySearch(allowExtName, extName) != -1) {  
	            return true;  
	        } else {  
	            return false;  
	        }  
	    }  
	
}
