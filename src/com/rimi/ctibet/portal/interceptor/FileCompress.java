package com.rimi.ctibet.portal.interceptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.rimi.ctibet.common.util.TextSearchFile;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class FileCompress extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public FileCompress() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		  doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//判断合并的资源进行response 提高效率
		if (request!=null&&request.getQueryString()!=null&&request.getQueryString().indexOf("?")==0) {
			//获取所有的请求css 或者 js  以 ,分割的数组
			String cssOrjs = request.getQueryString().substring(1);
			String fileType  ="";
			if (cssOrjs.contains("css")) {
				fileType=".css";
			}else if(cssOrjs.contains("js")){
				fileType = ".js";
			}
			mergeResource(request, response, fileType, cssOrjs);
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	/**
	 * 合并资源js,css 合并请求，同时压缩文件在一起提高性能
	 * @param request
	 * @param response
	 * @param fileType 文件类型后缀  css or  js  ...
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void mergeResource(HttpServletRequest request, HttpServletResponse response,String fileType,String fileAllString) throws IOException, ServletException
   {
		   Long startTime = System.currentTimeMillis();
		   String tempFilePath =  getWebrootPath(request)+"filecompress/"+startTime+fileType; //先合并再压缩,所以这个是合并时的临时文件名
		   Long startTimeWriter = System.currentTimeMillis()+2;
		   String tempFilePathWriter =  getWebrootPath(request)+"filecompress/"+startTimeWriter+fileType; //先合并再压缩,所以这个是合并时的临时文件名
		  
		   int linebreakpos = -1; 
           StringBuffer mergeNr = new StringBuffer();
           String  alljs =fileAllString   ;
           StringTokenizer st = new StringTokenizer(alljs, ",");
           File tempFile = new File(tempFilePath);
           
           if (!tempFile.exists()) {
			   tempFile.createNewFile();
		   }
           
           while (st.hasMoreTokens()) {
               String jsName = st.nextToken();
               //查找文件
               File folder = new File(getWebrootPath(request)+"/portal/");// 默认目录
               File[] result = TextSearchFile.searchFile(folder, jsName);// 调用方法获得文件数组
               if (result.length<=0) {
				  continue ;
			   }
               File tempJsFile = result[0];
               if (tempJsFile.exists()) {
                   List<String> stList = FileUtils.readLines(tempJsFile, "UTF-8");
                   for (String str : stList){
                       mergeNr.append(str).append("\\n"); //这里之前我没加\\n丫的我知道错了,居然都被注释了.哈哈!
                   }
               }
           }
           FileUtils.writeStringToFile(tempFile, mergeNr.toString(), "UTF-8",
                   false);

           Reader reader = new InputStreamReader(new FileInputStream(tempFile),
                   "UTF-8");
           File tempFileWriter = new File(tempFilePathWriter);
           Writer writer = new OutputStreamWriter(new FileOutputStream(tempFileWriter),
           "UTF-8");
           //这个是YUI Compressor的压缩方法.
           if (".js".equals(fileType)) {
        	   JavaScriptCompressor compressor = new JavaScriptCompressor(reader,
                       new ErrorReporter() {

                           public void warning(String message, String sourceName,
                                   int line, String lineSource, int lineOffset) {
                               if (line < 0) {
                               } else {
                               }
                           }

                           public void error(String message, String sourceName,
                                   int line, String lineSource, int lineOffset) {
                               if (line < 0) {
                               } else {
                               }
                           }

                           public EvaluatorException runtimeError(String message,
                                   String sourceName, int line, String lineSource,
                                   int lineOffset) {
                               error(message, sourceName, line, lineSource,
                                       lineOffset);
                               return new EvaluatorException(message);
                           }
                       });
             compressor.compress(writer, -1, true, false, false, false);
		   }else if(".css".equals(fileType)){
			 CssCompressor csscompressor = new CssCompressor(reader);
			 csscompressor.compress(writer, linebreakpos);
		   }
          request.getRequestDispatcher(request.getContextPath()+"/filecompress/"+startTimeWriter+fileType).forward(request, response);
           reader.close();
           writer.close();
           tempFile.delete();
           tempFileWriter.delete();
	}
	/**
	 * 获取服务器项目绝对路径
	 * @param request
	 * @return
	 */
    private final static String getWebrootPath(HttpServletRequest request) {
	       String root  = request.getSession().getServletContext().getRealPath("/");
	       return root;
    }
}
