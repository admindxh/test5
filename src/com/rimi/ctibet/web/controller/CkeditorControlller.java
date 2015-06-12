package com.rimi.ctibet.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.FileHelper;
import com.rimi.ctibet.common.util.Uuid;
@Controller
public class CkeditorControlller  extends BaseController{
	
	
  
    @RequestMapping(value = "system/upload.do",method=RequestMethod.POST)
    public void   ckeditorImag(String uploadContentType ,String   uploadFileName,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap ) throws IOException{  
    	List<MultipartFile> images = FileHelper.getFileSet(request,
				1024 * 1024*3, null, "upload");
    	response.setCharacterEncoding("utf-8");  
        PrintWriter out = response.getWriter();  
        File upload  = null;
        //对文件进行校验  
         String urlPr  = getURI(request);
        if(images==null || images.size()<=0){  
            out.print("<font color=\"red\" size=\"2\">*请选择上传文件</font>");
            out.close();
        }else{
		        uploadContentType =  images.get(0).getContentType();
		        uploadFileName  = images.get(0).getOriginalFilename();
		        if ((uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg"))  
		                && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".jpg")) {  
		            //IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg  
		        }else if(uploadContentType.equals("image/png") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".png")){  
		              
		        }else if(uploadContentType.equals("image/gif") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".gif")){  
		              
		        }else if(uploadContentType.equals("image/bmp") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".bmp")){  
		        }else{  
		            out.print("<font color=\"red\" size=\"2\">*文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）</font>");  
		            out.close();
		        }  
		        if(images.get(0).getSize() > 2*1024*1024){  
		            out.print("<font color=\"red\" size=\"2\">*文件大小不得大于2M</font>");  
		            out.close();
		        }else{  
			        //将文件保存到项目目录下  
			        InputStream is =images.get(0).getInputStream();
			        String uploadPath =  request.getSession().getServletContext()   
			                .getRealPath("/upload/ckUpImg");   //设置保存目录  
			        if (!new File(uploadPath).exists()) {
						 new File(uploadPath).mkdirs();
					}
			        String fileName =  Uuid.uuid();  //采用UUID的方式随机命名  
			        fileName += uploadFileName.substring(uploadFileName.length() - 4);  
			        File toFile = new File(uploadPath, fileName);  
			        OutputStream os = new FileOutputStream(toFile);     
			        byte[] buffer = new byte[1024];     
			        int length = 0;  
			        while ((length = is.read(buffer)) > 0) {     
			            os.write(buffer, 0, length);     
			        }     
			        is.close();  
			        os.close();  
			        //设置返回“图像”选项卡  
			        String callback = request.getParameter("CKEditorFuncNum");    
			        out.println("<script type=\"text/javascript\">");    
			        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" +urlPr+ "/upload/ckUpImg/" + fileName + "','')");    
			        out.println("</script>"); 
		        }
        }
    }  
}  
