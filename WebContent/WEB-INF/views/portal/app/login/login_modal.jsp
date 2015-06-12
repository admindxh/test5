<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
	String port = ":" + request.getServerPort();
	if ("80".equals(""+request.getServerPort())) {
		port = "";
	}
	String basePath = request.getScheme() + "://" + request.getServerName() + port + path + "/";
	 request.setAttribute("ctx",basePath);
	 request.setAttribute("ctxPortal",basePath+"portal/"); 	//xiangwq
%>
<div id="loginModal">
	<div class="login-header">
		<a href="#" class="login-close">关闭</a>
	</div>
	<div class="login-body clearfix">
		<div class="login-form">
			<div class="login-field">
				<input type="text" class="login-control" name="tlogUser" id="tlogUser" style="height: 31px;" placeholder="邮箱/手机号"></div>
			<div class="login-field">
				<input type="password" class="login-control" style="height: 31px;" name="logPass" id="logPass" placeholder="请输入密码"></div>
			<div class="login-field">
				<label for="remember" class="login-label"><input type="checkbox" name="remember" id="remember" value="0">记住我</label>
				<a href="<%=basePath %>portal/clientLog/changeInfo" class="forget">忘记密码？</a>
			</div>
			<button class="login-btn">登录</button>
		</div>
		<div class="login-text">
			<p>在天上西藏，你可以<strong class="high">结识朋友</strong></p>
			<p>分享<strong class="green">游记&攻略</strong></p>
			<p>获得权威的<strong class="blue">官方指南</strong></p>
			<p>领略最全面的<strong class="yellow">西藏文化</strong></p>
			<div class="login-reg">还没有注册？<a href="<%=basePath %>portal/registerController/register" class="high">立即注册&gt;</a></div>
		</div>
	</div>
</div>
<script>
	var  contentPath ='${ctx}';
    seajs.config({
        alias: {
            "holder": "${ctxPortal}modules/placeholder/jquery.placeholder.js"
        }
    });
</script>
<script type="text/javascript" src="${ctx}common-js/seajlogin_model.js"></script>