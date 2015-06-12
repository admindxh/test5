<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE HTML>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="author" content="quansenx">
	<meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
	<meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
	<title>登录</title>
	 <%@include file="/common-html/frontcommon.jsp" %>
	<link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctxPortal}/assets/css/login/login.css"></head>
    <!--[if lt IE 9]>
    	<script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    	<script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
	    seajs.config({
		    alias: {
		      "jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery",
     		  "footer/css": "${ctxPortal}/assets/css/footer.css"
	   		 }
	  	});
	  	seajs.use('footer/css');
	  	seajs.use('jquery',function($){window.$ = $;});
    </script>
	<style>
	  #logo{
		    padding-top: 120px;
		    text-align: center;
		}
	</style>
<body>
<form id="log_form" name="log_form" action="<%=basePath %>/portal/clientLog/login.html" method="post">
	<div class="full-bg" style="background-image: url(../static/images/full_bg.jpg);filter: progid:DXImageTransform.Microsoft.AlphaImageLoader( src='../static/images/full_bg.jpg', sizingMethod='scale');">
		<div id="logo"><a href="<%=basePath %>"><h1><img src="${ctxPortal}/assets/icon/logo_link.png"/></h1></a></div>
		<div class="single-login">
			<div class="sl-header">
				<h2 style="display: none;">登录天上西藏</h2>
			</div>
			<div class="sl-body">
				<div class="sl-field">
					<input type="text" name="tlogUser" placeholder="邮箱/手机号">
				</div>
				<div class="sl-field">
					<input type="password" name="logPass" placeholder="请输入密码">
				</div>
				<%-- <div class="sl-field clearfix">
					<input type="text"  name="verifyCode" class="v-input" placeholder="请输入验证码">
					<img src="<%=basePath %>validateCode.html" alt="验证码" class="v-img" id="veriImg" onclick="change(this);" title="看不清楚请点我更换哟~" >
				</div> --%>
				<div class="sl-opt">
					<label for="remember" class="login-label">
						<input type="checkbox" name="remember" value="0" onchange="rememberMe(this)" id="remember">记住我</label>
					<a href="<%=basePath %>portal/clientLog/changeInfo" class="forget" target="_blank">忘记密码？</a>
					<div class="now-reg">
						没有帐号？<a href="<%=basePath %>portal/registerController/register" target="_blank">立即注册&gt;&gt;</a>
					</div>
				</div>
				<button class="sl-btn">登录</button>
			</div>
		</div>
	</div>
	<jsp:include page="${root}/portal/headerFooterController/footer"/>
</form>
	<!-- <div ms-controller="view" ms-include-src="'views/portal/footer/index.html'"></div> -->
	
  <script>
   //更换验证码
    function change(obj){
       var url = '<%=basePath %>validateCode.html?' + new Date().getTime();
	   obj.src=url;
     }
    
    function rememberMe(obj){
       if(obj.checked){
         obj.value="1";
       }else{
         obj.value="0";
       }
    }
    window.onload=function(){
      if(${error=='-1'}){
        alert("用户名不能为空");
      }
      if(${error=='-2'}){
        alert("密码不能为空");
      }
      if(${error=='-3'}){
        alert("验证码不能为空");
      }
      if(${error=='-4'}){
        alert("验证码错误");
      }
      if(${error=='-5'}){
        alert("用户名或密码错误");
      }
      if(${error=='-6'}){
        alert("未找到该用户");
      }
      if(${error=='-7'}){
        alert("请先到邮箱激活账户");
      }
      if(${error=='-8'}){
        alert("该手机账户尚未激活");
      }
      if(${error=='-11'}){
        alert("账户已被停封，请与管理员联系");
      }
      if(${logflag=='1'}){
        alert("密码修改成功，请重新登录");
      }
    }
  </script>
</body>
</html>
