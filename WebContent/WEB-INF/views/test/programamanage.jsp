<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'login.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="http://www.jeasyui.com/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="http://www.jeasyui.com/easyui/themes/color.css">
<link rel="stylesheet" type="text/css"
	href="http://www.jeasyui.com/easyui/demo/demo.css">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.6.min.js"></script>
<script type="text/javascript"
	src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		//
		//
	});
</script>
</head>
<body>
	<!-- 	<c:out value="${pager.dataList}"></c:out> -->
	<form id="listForm" method="post"
		action="<%=basePath%>/manage/html/programa"></form>

	<table id="tt" class="easyui-datagrid" style="100%;height:auto;">
		<thead>
			<tr>
				<th field="name1">id</th>
				<th field="name2">code</th>
				<th field="name3" >available</th>
				<th field="name4">programaName</th>
				<th field="name5">programaSummary</th>
				<th field="name6">enName</th>
				<th field="name7">imageUrl</th>
				<th field="name8">programaUrl</th>
				<th field="name9">pCode</th>
				<th field="name10">createUserCode</th>
				<th field="name11">createTime</th>
				<th field="name12">lastEditTime</th>
				<th field="name13">remark</th>
				<th field="name14">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="programa" items="${pager.dataList}">
				<tr>
					<td>${programa.id}</td>
					<td>${programa.code}</td>
					<td>${programa.available}</td>
					<td>${programa.programaName}</td>
					<td>${programa.programaSummary}</td>
					<td>${programa.enName}</td>
					<td>${programa.imageUrl}</td>
					<td>${programa.programaUrl}</td>
					<td>${programa.pCode}</td>
					<td>${programa.createUserCode}</td>
					<td>${programa.createTime}</td>
					<td>${programa.lastEditTime}</td>
					<td>${programa.remark}</td>
					<td><a href="<%=basePath%>/manage/html/delete?code=${programa.code}">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@include file="/common-html/pager.jsp"%>
	<form action="<%=basePath%>/manage/html/save" method="post">

		<table style="margin-top: 40px">
			<tr>
				<td>添加栏目：</td>
				<td><input type="text" name="programaName" placeholder="programaName" /></td>
				<td><input type="text" name="programaSummary" placeholder="programaSummary" /></td>
				<td><input type="text" name="enName" placeholder="enName" /></td>
				<td><input type="text" name="imageUrl" placeholder="imageUrl" /></td>
				<td><input type="text" name="programaUrl" placeholder="programaUrl" /></td>
				<td><input type="text" name="pCode"placeholder="pCode"  /></td>
				<td><input type="text" name="createUserCode" placeholder="createUserCode" /></td>
				<td><input type="text" name="remark" placeholder="remark" /></td>
				<td><input type="submit"  /></td>
			</tr>
		</table>
	</form>
</body>
</html>
