<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jstl/fmt_rt" %>   
<%
	//String path = request.getContextPath();
	String path = request.getContextPath().trim().equals("/")?request.getContextPath():request.getContextPath()+"/";
	String port = ":" + request.getServerPort();
	if ("80".equals(""+request.getServerPort())) {
		port = "";
	}
	String basePath = request.getScheme() + "://"+ request.getServerName() + port + path;
	request.setAttribute("ctx",basePath);
	request.setAttribute("ctxManage",basePath+"/manage/");
	request.setAttribute("ctxPortal",basePath+"portal/");
	
	String pathno = request.getContextPath().trim().equals("/")?"":request.getContextPath();
	String basePathno = request.getScheme() + "://"+ request.getServerName() + port + pathno;
	request.setAttribute("ctxno",basePathno);
%>