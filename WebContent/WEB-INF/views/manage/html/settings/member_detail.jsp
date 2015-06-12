<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!doctype html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge"/>
    <title>系统设置-会员管理-新建会员</title>
	<%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
        <link rel="stylesheet" href="${ctxManage}/resources/plugin/datepicker.css" />
    <style type="text/css">
		.avatar { position: absolute; left: 296px; top: 46px;}
		.avatar img { width: 128px; height: 128px; display: block;}
		.pickfiles{
			display: inline-block;
			vertical-align: middle;
		}
		.webuploader-pick{
			background-color: #fbfbfb;
			border: 1px solid #9c9c9c;
			border-radius: 3px;
			text-align: center;
			font: normal 14px/32px "微软雅黑";
			color: #8c8c8c;
		}
		.webuploader-pick-hover{
		    background: none repeat scroll 0 0 #817171;
   			color: #e3e3e3;
		}
	</style>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>

<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
    		<span><a>系统设置</a> -</span>
    		<span><a href="${ctx }web/member/list" target="_self">会员管理</a> -</span>
    		<span><a>会员详情</a></span>
    </div>

    <!-- 模块管理 { -->
    <form id="member-add-form" method="post"  <c:if test="${flag eq 'save'}">action="${ctx}/web/member/save"</c:if> 
		                       <c:if test="${flag eq 'update'}">action="${ctx}/web/member/update"</c:if>  enctype="multipart/form-data">
        <!-- 会员信息 { -->
		<div class="filament_solid_ddd pos-rela mt30">

			<div class="formLine mt20">
				<label>性别:</label>
				<input id="male" disabled="disabled"  name="sex" type="radio" value="1" class="ml10" checked /><label for="male" class="w-auto pointer ml10">男</label>
				<input id="female" disabled="disabled"  name="sex" type="radio" value="0" <c:if test="${sex eq '0'}">checked="checked"</c:if>  class="ml25" /><label for="female" class="w-auto pointer ml10">女</label>
			</div>
            <div class="formLine mt30 mb40">
				<label>头像:</label>
				
			</div>
			<div class="avatar">
				<img src="<%=basePath %>${pic }" id="previewimage" alt="用户头像" />	
			</div>
			<div class="formLine">
				<label>邮箱:</label>
				<input id="email" disabled="disabled"  type="text" name="email" value="${email }" class="w-320" />
			</div>
			
			<div class="formLine">
				<label>手机:</label>
				<input id="mobile" disabled="disabled"  name="phone" onkeyup="value=value.replace(/[^\d]/g,'')" value="${phone }" type="text" class="w-320" />
			</div>
			
			<div class="formLine">
				<label>昵称:</label>
				<input id="niceName" disabled="disabled"  name="username" value="${username }"  type="text" class="w-320" />
				<span class="reqItem">*</span>
			</div>
			<div class="formLine">
				<label>生日:</label>
				<input id="birthday" disabled="disabled"  name="birthday" value="<fmt:formatDate value="${birthday }" pattern="yyyy-MM-dd"/>" type="text" class="w-320" />
			</div>
			
			<div class="formLine">
				<label>所在地:</label>
				<div class="select-base">
					<input id="provinceVal" name="province" type="hidden" value="" />
					<i class="w-140" id="chkPro">${province}</i>
					
				</div>
				<div id="city" class="select-base ml5">
					<input id="cityVal" name="city" type="hidden" value="" />
					<i class="w-140" id="chkCity">${city}</i>
					
				</div>
			</div>
			
			<div class="formLine">
				<label>积分:</label>
				<input id="score" disabled="disabled" name="score" onkeyup="value=value.replace(/[^\d]/g,'')" value="${score }" type="text" class="w-320" />
			</div>
			
			<!-- 按钮 -->
			<div class="ml140 mt40 mb30">
				<a class="btn-anchor ml20 mr40" onclick="javascript:location.href='${ctx}/web/member/list'" type="button">返 回</a>
			</div>

		</div><!-- } 会员信息 -->
    </form>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-form.js"></script>
<script type="text/javascript" src="${ctx}/common-js/common.js"></script>

<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch">
<!-- IE10+ -->
<link rel="next">
<!-- Firefox -->
<link rel="prerender">
<!-- Chrome -->
</body>

</html>