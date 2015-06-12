<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String port = ":" + request.getServerPort();
if ("80".equals(""+request.getServerPort())) {
	port = "";
}
String basePath = request.getScheme() + "://"
		+ request.getServerName() + port + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>My JSP '首页西藏动态列表管理.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.min.js"></script>
  </head>
  
  <body>
   		<form id="listForm" method="post"  action="<%=basePath%>/web/dynamicdatamgController/list?proCode=13198b8d-75da-11e4-b6ce-005056a05bc9" ></form>
		
		<c:forEach var="obj" items="${pager.dataList }">
			${obj }<br>
		</c:forEach>
		
		<%@include file="/common-html/pager.jsp" %>		
   
   
  </body>
</html>
