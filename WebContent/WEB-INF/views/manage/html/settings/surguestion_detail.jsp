<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common-html/common.jsp" %>

<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>系统设置-运营管理-建议与意见-详情</title>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>

<body>
	<!-- main { -->
	<div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>系统设置</a> -</span>
			<span><a>运营管理</a> -</span>
			<span><a href="${ctx}/web/surguestion/list" target="_self">建议与意见管理</a> -</span>
			<span><a>详情</a></span>
		</div>

		<!-- 模块管理 { -->
		<div class="muduleManage filament_solid_ddd mt20">

			<div class="formLine mt12">
				<label>提交时间:</label>
				<span class="dataDetail"><fmt:formatDate value="${sur.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></span>

			</div>

			<div class="formLine">
				<label>提交人:</label>
				<span class="dataDetail"><c:if test="${empty sur.username}">游客</c:if>${sur.username}</span>
			</div>

			<div class="formLine nowrap ov-au">
				<label>内容:</label>
				<span class="dataDetail article">${sur.content }</span>
			</div>
		</div>


		<!-- 操作按钮 -->
		<div class="formLine">
			<div class="saveOper">
				<a href="${ctx }web/surguestion/list" class="btn-anchor mr50">返回</a>
			</div>
		</div>
		<!-- } 模块管理 -->
	</div>
	<!-- } main -->

	<!-- JS引用部分 -->
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>

	<!-- 利用空闲时间预加载指定页面 -->
	<link rel="prefetch">
	<!-- IE10+ -->
	<link rel="next">
	<!-- Firefox -->
	<link rel="prerender">
	<!-- Chrome -->
</body>

</html>
