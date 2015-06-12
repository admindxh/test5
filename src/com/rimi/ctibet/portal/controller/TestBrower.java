package com.rimi.ctibet.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.util.CheckMobile;

@Controller
@RequestMapping("portal/testbrower")
public class TestBrower {
			
	@RequestMapping("testBrower")
	public String testBrower(HttpServletRequest request,HttpServletResponse response){
		
		
		return  "";
	}
}
