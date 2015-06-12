/**
 * 
 */
package com.rimi.ctibet.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author liuxz
 * @date 2014-3-5
 * @package com.rimi.medical.common.util
 * @copyright 成都睿峰科技有限公司
 * @version V1.0
 */
public class FileUtil {
	/**
	 * 上传文件到服务器
	 * @param file
	 * @param path
	 * @param fileName
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static void uploadFile(MultipartFile file,String path,String fileName) throws IllegalStateException, IOException{
        File targetFile = new File(path, fileName);    
        if(!targetFile.exists()){    
            targetFile.mkdirs();    
        }       
        file.transferTo(targetFile);   
	}
	
	
	/**
	 * 删除文件
	 * @param paramString
	 * @return
	 * @throws FileNotFoundException
	 */
	public static boolean delete(String paramString)
	    throws FileNotFoundException{
	    File localFile = new File(paramString);
	    if (!(localFile.exists()))
	      throw new FileNotFoundException(paramString);
	    return localFile.delete();
	}
	
	/**
	 * 移动文件
	 * @param oldpath
	 * @param newPath
	 * @return
	 */
	public static boolean moveFile(String oldpath, String newPath){
		try {
			File oldFile = new File(oldpath);
			File newFile = new File(newPath);
			if(!newFile.exists()){
				newFile.getParentFile().mkdirs();
			}
			return oldFile.renameTo(newFile);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
