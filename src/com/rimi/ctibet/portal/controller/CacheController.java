package com.rimi.ctibet.portal.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.cache.CacheOperation;

@Controller("cacheController")
@RequestMapping("web/cache")
public class CacheController extends BaseController implements ServletContextAware {

	ServletContext application;

	@RequestMapping(value = "clear", produces = "text/html;charset=utf-8",method=RequestMethod.POST)
	public void clear(HttpServletRequest request, HttpServletResponse response) {
		String message = "success";
		try {
			CacheOperation.getInstance().removeAllCacheData();
		} catch (Exception e) {
			message = e.getMessage();
		}
		this.writeContent(message, response);
	}

	/********************************************
	 * Setter Getter
	 ********************************************/
	@Override
	public void setServletContext(ServletContext application) {
		this.application = application;
	}
	
	public void writeContent(String content, HttpServletResponse response) {
		String charset = "utf-8";
		String contentType = response.getContentType();
		if (contentType == null) {
			contentType = "text/plain";
			response.setContentType(contentType);
		}
		response.setCharacterEncoding(charset );
		BufferedWriter fw = null;
		try {
			fw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), charset));
			fw.write(content);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
