
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
String port = ":" + request.getServerPort();
if ("80".equals(""+request.getServerPort())) {
	port = "";
}
String basePath = request.getScheme() + "://"
		+ request.getServerName() + port + path + "/";
%>

<!DOCTYPE HTML>

<html>
<head>

<base href="<%=basePath%>">
<title>My JSP 'login.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>
<script>
</script>
<body>请登录
	<form action="<%=basePath%>manage/html/login.html" method="post">
		<input type="text" name="name" value="xz"> xz
		<br> <input type="text"
			name="pwd" value="xxxx"> xxxx
		<br>
		<input type="submit" name="ok">
	</form>
</body>
</html>
