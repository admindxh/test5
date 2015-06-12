<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
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
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge"/>
    <title>修改密码</title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>

<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
    		<span><a>系统操作员修改密码</a></span>
    </div>

    <!-- 模块管理 { -->
    <form id="member-add-form" action="newPwd" method="post">
        <!-- 会员信息 { -->
		<div class="filament_solid_ddd pos-rela mt30">
			
			<div class="formLine">
				<label>原密码:</label>
				<input id="pwd" type="password" class="w-320" name="oldPwd" />
				<span class="reqItem">*</span>
			</div>
			
			<div class="formLine">
				<label>新密码:</label>
				<input id="newPwd" type="password" class="w-320" name="newPwd"/>
				<span class="reqItem">*</span>
			</div>
			
			<div class="formLine">
				<label>确认密码:</label>
				<input id="reNewPwd" type="password" class="w-320" name="rePwd"/>
				<span class="reqItem">*</span>
			</div>
			
			<!-- 按钮 -->
			<div class="ml140 mt40 mb30">
				<button class="btn-sure sureNewPwd" type="button">确定</button>
				<a class="btn-anchor ml20 mr40" type="reset" onclick="resetUserInfo()" style="cursor: pointer;">重置</a>
			</div>

		</div><!-- } 会员信息 -->
    </form>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
<script type="text/javascript">
	//======================================
	//				数据验证
	//======================================
	
	// 密码
	$("#pwd").blur(function() {
		var thisNode = $(this)[0],
			this_val = $(this).val(),
			repwd_val = $("#repwd").val(),
			reg_val = $.VLDTOR.IsPWD(this_val),
			val_range =  inputRange(this, 6, 16);
		if(reg_val && val_range){
			$.ajax({
				url: "${ctx}/manage/html/isOldPwdRight",
				dataType:"json",
				type:"get",
				data:{"oldPwd":this_val},
				async: false,
				success: function(data) {
					if(!data){
						errMesg(thisNode, "密码不正确", "right");
					}else{
						$(thisNode).next('.reqItem').removeClass('errMesg').text('*');
					}
				}
			})
		}else{
			errMesg(this, "密码只能为6-16位的非中文字符", "right");
		}
	});
	
	// 密码
	$("#newPwd").blur(function() {
		var this_val = $(this).val(),
			repwd_val = $("#reNewPwd").val(),
			reg_val = $.VLDTOR.IsPWD(this_val),
			val_range =  inputRange(this, 6, 16);
		if(repwd_val != "") {
			modifErrMesg("#reNewPwd");
			valid_txtBox("#reNewPwd", this_val == repwd_val, "两次密码输入不一致，请检查", "right");
		}
		valid_txtBox(this, reg_val && val_range, "密码只能为6-16位的非中文字符", "right");
	});

	// 确认密码
	$("#reNewPwd").blur(function() {
		var this_val = $(this).val(),
			pwd_val = $("#newPwd").val(),
			pwd_val_len = pwd_val.length;
		if(pwd_val_len == "") {
			valid_txtBox(this, false, "请先输入密码", "right");
			return;
		}
		valid_txtBox(this, this_val == pwd_val, " 两次密码输入不一致，请检查！", "right");
	});
	
	// 保存按钮验证
	$(".sureNewPwd").click(function() {
		// 失焦验证
		$("#pwd, #newPwd, #reNewPwd").blur();

		// 是否含有错误信息
		var hasErr = $(".errMesg").length > 0;

		// 含有验证不通过的项
		if(hasErr) {
			msgBox("您填写的信息有误，请检查！", "erro");
			return;
		}
		// 验证通过提交表单
		else {
			msgBox("修改成功！", "pass");
			$("#member-add-form").submit();
		}
	}); 
	function resetUserInfo(){

		document.getElementById("member-add-form").reset();

		}
</script>
<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch">
<!-- IE10+ -->
<link rel="next">
<!-- Firefox -->
<link rel="prerender">
<!-- Chrome -->
</body>

</html>