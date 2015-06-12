<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common-html/commonTag.jsp" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=0.8,maximum-scale=1.5"/>
    <meta name="author" content="huanl">
    <meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏"/>
    <meta name="description"
          content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！"/>
    <title>天上西藏-感受西藏魅力，体验西藏生活-天上西藏官网</title>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPortal}/assets/css/activity/qx-login.css">
</head>
<body>
    <div class="sl-header"><img src="${ctxPortal}/assets/icon/qx-login.png" alt="登陆天上西藏"/></div>
    <form id="form1" action="${ctx}portal/clientLog/login.html" method="post">
    	<input type="hidden" name="mobile" value="yes">
    	<input type="hidden" name="back_url" value="${back_url }">
	    <div class="sl-body">
	        <div class="sl-field">
	            <input type="text" name="tlogUser" placeholder="邮箱/手机号"/>
	        </div>
	        <div class="sl-field">
	            <input type="password" name="logPass" placeholder="请输入密码"/>
	        </div>
	        <div class="sl-opt">
	            <div class="now-reg">
	            	没有帐号？
	            	<a id="J_zhuce" href="javascript:void(0);">立即注册&gt;&gt;</a>
	            </div>
	        </div>
	        <button type="button" class="sl-btn">登录</button>
	    </div>
    </form>
<script src="${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js"></script>
<script type="text/javascript">
	$("#J_zhuce").click(function(){
		window.location.href="${ctx}portal/registerController/register?back_url=${back_url}";
	});
	$(".sl-btn").click(function(){
		$("#form1").submit();
	});
	window.onload=function(){
		if(${login_state eq 'success'}){
			alert("登录成功");
			if(${not empty back_url}){
        		window.location.href="${back_url}";
    		}else{
    			window.location.href="${ctx}activity";
    		}
			//window.close();
		}else if(${error=='-1'}){
		  	alert("用户名不能为空");
		}else if(${error=='-2'}){
		  	alert("密码不能为空");
		}else if(${error=='-3'}){
		  	alert("验证码不能为空");
		}else if(${error=='-4'}){
		  	alert("验证码错误");
		}else if(${error=='-5'}){
		  	alert("用户名或密码错误");
		}else if(${error=='-6'}){
		  	alert("未找到该用户");
		}else if(${error=='-7'}){
		  	alert("请先到邮箱激活账户");
		}else if(${error=='-8'}){
		  	alert("该手机账户尚未激活");
		}else if(${error=='-11'}){
		  	alert("账户已被停封，请与管理员联系");
		}
		if(${logflag=='1'}){
			alert("密码修改成功，请重新登录");
		}
	}
</script>
</body>
</html>