package com.rimi.ctibet.portal.interceptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.util.Security;
import com.rimi.ctibet.common.util.SpringUtil;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.TextSearchFile;
import com.rimi.ctibet.common.util.annotation.Token;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.ImageService;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class LoginInterceptor implements HandlerInterceptor{
    private IMemberService memberService;
    public  static  Logger LOGGER = Logger.getLogger(LoginInterceptor.class);
    
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void postHandle1(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object arg2) throws Exception {
		//判断内网还是用户级别
		//LOGGER.error(" error 性能测试----"+getIpAddr(req));
		//LOGGER.info("  info 性能测试----"+getIpAddr(req));
		//start  一个页面只能提交一次验证评论
		String url = req.getRequestURL().toString();
		if(url!=null&&!(url.contains("videocreat")||url.contains("videoedit"))){
		  if (arg2 instanceof HandlerMethod) {
		        HandlerMethod handlerMethod = (HandlerMethod) arg2;
		        Method method = handlerMethod.getMethod();
		        Token annotation = method.getAnnotation(Token.class);
		        if (annotation != null) {
		            boolean needSaveSession = annotation.save();
		            if (needSaveSession) {
		            	req.getSession(true).setAttribute("token", UUID.randomUUID().toString());
		            }
		            boolean needRemoveSession = annotation.remove();
		            if (needRemoveSession) {
		                if (isRepeatSubmit(req)) {
		                    return false;
		                }
		                req.getSession(true).removeAttribute("token");
		            }
		        }
		      
		   } 
		}
		//end  一个页面只能提交一次验证评论
		//判断合并的资源进行response 提高效率
		/*if (req!=null&&req.getQueryString()!=null&&req.getQueryString().indexOf("?")==0) {
			System.out.println(req.getQueryString());
			//获取所有的请求css 或者 js  以 ,分割的数组
			String cssOrjs = req.getQueryString().substring(1);
			String fileType  ="";
			if (cssOrjs.contains("css")) {
				fileType=".css";
			}else if(cssOrjs.contains("js")){
				fileType = ".js";
			}try{
				mergeResource(req, res, fileType, cssOrjs);
				return false;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		
		String referer=req.getHeader("referer");
		if(StringUtils.isBlank(referer)){
		//	return false;
		}
		if(url.contains("validateCode")||url.contains("WEB-INF")||url.contains("uploadPic")||url.contains("logout")||url.contains("registerController")||url.contains("sendMsg")){
			return true;
		}
        LogUser mem=(LogUser)req.getSession().getAttribute("logUser");
        Cookie[] cookies=req.getCookies();
        String token="";
        if(mem==null&&cookies!=null){
        	for(int i=0;i<cookies.length;i++){
        		if(cookies[i].getName().equals("token")){
        			token+=cookies[i].getValue();
        			break;
        		}
        	}
        	if(StringUtils.isNotBlank(token)){
        		Security secur=Security.getInstance();
        		String decode=secur.decode(token);
        		String timeStr=decode.split(",")[0]; 
        		long time=Long.parseLong(timeStr);
        		if(System.currentTimeMillis()<time){
        			String username=decode.split(",")[1];
        			String password=decode.split(",")[2];
        			req.setAttribute("loginUser", username);
        			req.setAttribute("loginPass", password);
        			if(!login(req)){
        				this.removeCookie(res);
        				return true;
        			}
        		}
        	}
        }
		
		return true;
	}

	public boolean login(HttpServletRequest req){
		String username=(String)req.getAttribute("loginUser");
		String password=(String)req.getAttribute("loginPass");
		memberService = (IMemberService) SpringUtil.getAppCtx().getBean("memberService");
		
		if(StringUtils.isBlank(username)){
			return false;
		}
		if(StringUtils.isBlank(password)){
			return false;
		}
		List<LogUser> list=memberService.login(username, password);
		if(list.size()<=0){
			return false;
		}
		LogUser loguser=list.get(0);
	   if(loguser!=null){
		   if(loguser.getIsBind()!=null){
			   if("0".equals(loguser.getIsBind())){
				   return false;
			   }
		   }
	   }
		req.getSession().setAttribute("logUser",loguser);
		////System.out.println(req.getSession().getAttribute("logUser"));
		return true;
	}
    
	public void removeCookie(HttpServletResponse rep){
		Cookie cookie=new Cookie("token",null);
		cookie.setMaxAge(0);
		rep.addCookie(cookie);
	}
	
	public IMemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
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
                       mergeNr.append(str); //这里之前我没加\\n丫的我知道错了,居然都被注释了.哈哈!
                       if (fileType.contains("js")) {
                    	   mergeNr.append("\\n");
					  }
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
            response.sendRedirect(request.getContextPath()+"/filecompress/"+startTimeWriter+fileType);
           //request.getRequestDispatcher(request.getContextPath()+"/filecompress/"+startTimeWriter+fileType).forward(request, response);
           reader.close();
           writer.close();
           //tempFile.delete();
          /// tempFileWriter.delete();
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
    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(true).getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        //考虑ajax 请求
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }
    public  String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	
}
