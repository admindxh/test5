package com.rimi.ctibet.portal.controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.util.ValidateCodeCreator;

@Controller
public class ValidateCodeController {
	
	 
	@RequestMapping("validateCode")
	public String execute( HttpServletRequest request, HttpServletResponse response){
		ValidateCodeCreator creator = ValidateCodeCreator.instance();
		request.getSession().setAttribute("validateCode", creator.getString());//将验证码放入session
		ByteArrayInputStream image = creator.getImage();
		byte[] b = new byte[image.available()];
		try {
			image.read(b);
			OutputStream ops = response.getOutputStream();
			
			ops.write(b);
			ops.close();
			image.close();
			b = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
