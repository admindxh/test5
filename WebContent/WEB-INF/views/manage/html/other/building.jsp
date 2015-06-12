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
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>页面建设中</title>
	<style type="text/css">
		body, html { background-color: #fff;}
		.building { width: auto; height: auto; font:bold 36px/36px "微软雅黑"; color: #d5d5d5; position: absolute; left: 50%; top: 50%; margin-left: -140px; margin-top: -18px;}
	</style>
</head>
<body>
	<div class="building">页面尚在建设中···</div>
</body>
</html>
