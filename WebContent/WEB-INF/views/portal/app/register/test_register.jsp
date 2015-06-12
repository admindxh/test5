<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>My JSP 'test_register.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

        <script type="text/javascript" src="<%=path%>/manage/resources/plugin/jquery-1.11.1.min.js"></script>    
		<script type="text/javascript">
  function isRepeat(mobile){
     alert(mobile);
     $.ajax({
		url : "isMobileRepeat",
		dataType : "json",
		type : "post",
		data : { mobile:mobile },
		async : false,
		success : function(data) {
			alert(data.msg);
		}
	});
  }
  
  function validate(mobile){
     alert(mobile);
     $.ajax({
		url : "sendValidate",
		dataType : "json",
		type : "post",
		data : { mobile:mobile },
		async : false,
		success : function(data) {
			alert(data.msg);
		}
	});
  }
  
  </script>
	</head>

	<body>
		<form action="memberRegistByEmail?action=register" method="post">
			Email:
			<input type="text" id="email" name="email" value="">
			<br>
			Name:
			<input type="text" id="name" name="name" value="">
			<br>
			password:
			<input type="text" id="password" name="password" value="">
			<br>
			<input type="submit" value="提交">
		</form>
		<br />
		
		<div id="phone-info" class="reg-interface">
		
			<!-- 手机注册表单 -->
					<button onclick="validate(document.getElementById('mobile').value)">获取手机验证码</button>
			<form id="mobileRegister" action="memberRegistByMobile" method="post">
				<div class="clearfix">
					<span>手机号</span>
					<input class="form-control" placeholder="请输入您的手机号" type="text"
						name="mobile" id="mobile" onblur="isRepeat(this.value)" />
					<span id="hint"></span>
				</div>
				<div class="clearfix">
					<input class="form-control validate" placeholder="请输入验证码" type="text" name="validate" />
				</div>
				<div class="clearfix">
					<span>昵称</span>
					<input class="form-control" placeholder="朝圣者尼玛扎西" type="text"
						name="name" />
				</div>
				<div class="clearfix">
					<span>密码</span>
					<input class="form-control" placeholder="请输入您的密码" type="password"
						name="password" />
				</div>
				<input type="submit" value="提交">
			</form>
		</div>
	</body>
</html>
