<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib   prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jstl/fmt_rt" %>   
<%@ taglib prefix="rimiTag"  uri="/rimi-tags" %>
<%
	String path = request.getContextPath();
String port = ":" + request.getServerPort();
if ("80".equals(""+request.getServerPort())) {
	port = "";
}
String basePath = request.getScheme() + "://"
		+ request.getServerName() + port + path + "/";
	request.setAttribute("ctx", basePath);
	request.setAttribute("ctxManage", basePath + "/manage/");
	request.setAttribute("ctxPortal", basePath + "portal/"); //xiangwq
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
	<title>天上西藏门户管理系统-管理员登陆</title>
	<link rel="shortcut icon" href="${ctxManage }resources/img/other/favicon.ico" />
	<link rel="bookmark" href="${ctxManage }resources/img/other/favicon.ico" />
	<link rel="stylesheet" href="${ctxManage }resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage }resources/css/login.css" />
	<!--<script src="../resources/plugin/respond.min.js" type="text/javascript"></script>-->
	<script>
		if(self != top){
			top.location.href = self.location.href
		}
	</script>
	<!--[if ie 8]>
	<style type="text/css">
		.formLine input {padding-top: 6px;}
	</style>
	<![endif]-->
</head>
<body>
	<!-- header -->
	<div class="header">
		<div class="logo"></div>
	</div>
	
	<form  id="loginfrm"  method="post" action="${ctxManage }html/login.html">
		<!-- 登陆框 { -->
		<div class="loginBox">

			<!-- 登陆框表单元素 -->
			<div class="login-group">
				<!-- 登录信息 -->
				<div class="login-info">
					<!-- 账户 -->
					<div class="formLine account">
						<input id="adminName"  name="name" type="text" class="account" placeholder="请输入账户名" />
					</div>

					<!-- 密码 -->
					<div class="formLine passwd">
						<input id="pwd" name="pwd" type="text" class="pwd"  placeholder="请输入密码" />
					</div>
					
					<!-- 验证码 -->
					<div class="formLine validCode">
						<input id="verCode" name="code" type="text" class="txtSecuCode" placeholder="请输入验证码" />
						<span class="secuCode">
							<img id="vercodeimage" src="${ctx}manage/html/verify" alt="点击刷新验证码" title="点击刷新">
						</span>
					</div>
				</div>
					
				<div class="mesgBox">${notice}</div>
				
				<!-- 登录按钮 -->
				<div class="formLine loginButton txt-ac">
					<button id="login" name="ok" type="button" class="btn-base btn-login"></button>
				</div>

			</div>

		</div>
		<!-- } 登陆框 -->
	</form>
	
	<!-- 背景大图 -->
	<div class="login-bg">
		<img src="${ctxManage }resources/img/bg/login-bg.jpg" />
	</div>
	
	<!-- JS引用部分 -->
	<script src="${ctxManage }resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage }resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage }resources/js/login.js" type="text/javascript"></script>
	<script>
		$(function(){
			$(document).on('click','#vercodeimage',function(){
				var num = parseInt(Math.random()*100);
				$(this).attr('src','${ctx}manage/html/verify?'+num);
			});
		});
		
	</script>
	<!-- 利用空闲时间预加载指定页面 -->
	<link href="index.html" rel="prefetch">
	<!-- IE10+ -->
	<link href="index.html" rel="next">
	<!-- Firefox -->
	<link href="index.html" rel="prerender">
	<!-- Chrome -->
</body>
</html>