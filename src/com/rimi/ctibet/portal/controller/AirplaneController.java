package com.rimi.ctibet.portal.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("portal/airPlane")
public class AirplaneController {
   @RequestMapping("OrderAirPlane")
   public  ModelAndView orderAirPlane(HttpServletRequest request,HttpServletResponse response) throws Exception{
       String start = request.getParameter("start");
	   String dest = request.getParameter("dest");
	   String date = request.getParameter("date");
	   if(!start.equals(",")&&start!=null&&dest!=null&!dest.equals("")){
		   String[] aa = start.split(",");
		   String airPlaneUrl  ="http://u.ctrip.com/union/CtripRedirect.aspx?" +
		   		"TypeID=20&" +
		   		"FlightWay=1&" +
		   		"StartCity="+transCity(aa[0]) +"&"+
		   		"DestCity="+transCity(dest) +"&"+
		   		"DepartDate="+date+"&" +
		   		"ReturnDate=yyyy-mm-dd&" +
		   		"sid=460204&" +
		   		"allianceid=24507";
		   ModelAndView loginView = new ModelAndView(new RedirectView(airPlaneUrl));
	      return loginView;
	   }
	   return new ModelAndView(new RedirectView("http://flights.ctrip.com/"));
   }
   public String transCity(String s) throws UnsupportedEncodingException {
	String r= URLEncoder.encode(s,"UTF-8");
	String rr =  URLEncoder.encode(r,"UTF-8");
	//System.out.println(rr);
	return rr;
}
}
