<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    System.out.print(basePath);
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.min.js"></script>
	</head>
	<body>
		<form id="listForm" method="post" action="<%=basePath%>/web/viewController/testPager"></form>
		
		<c:forEach var="obj" items="${pager.dataList }">
			${obj }<br>
		</c:forEach>
		
		<%@include file="/common-html/pager.jsp" %>
	</body>
</html>