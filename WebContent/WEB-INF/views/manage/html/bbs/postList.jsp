<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>门户首页-帖子列表管理</title>
</head>
<body>
<%@include file="/common-html/common.jsp" %>
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
    <!-- main { -->
    <div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>门户首页</a> -</span>
			<span><a href="#" target="_self">帖子列表管理</a></span>
		</div>

		<!-- 模块管理 { -->
		<div class="muduleManage filament_solid_ddd">

			<!-- 管理按钮 -->
			<div class="searchManage">
				<button type="button" class="btn-sure">显示最新</button>
				<button type="button" class="btn-sure">预览</button>
				<button type="button" class="btn-sure">保存</button>
				<button type="button" class="btn-sure">查看</button>
			</div>

			<div class="contClassify">
				<h2 class="title">帖子一</h2>

				<div class="formLine mt15">
					<label class="w_auto">链接:</label>
					<input type="text" class="w-640">
				</div>
			</div>

			<div class="contClassify">
				<h2 class="title">帖子二</h2>

				<div class="formLine mt15">
					<label class="w_auto">链接:</label>
					<input type="text" class="w-640">
				</div>
			</div>

			<div class="contClassify">
				<h2 class="title">帖子三</h2>

				<div class="formLine mt15">
					<label class="w_auto">链接:</label>
					<input type="text" class="w-640">
				</div>
			</div>

			<div class="contClassify">
				<h2 class="title">帖子四</h2>

				<div class="formLine mt15">
					<label class="w_auto">链接:</label>
					<input type="text" class="w-640">
				</div>
			</div>

			<div class="contClassify">
				<h2 class="title">帖子五</h2>

				<div class="formLine mt15">
					<label class="w_auto">链接:</label>
					<input type="text" class="w-640">
				</div>
			</div>

		</div><!-- } 模块管理 -->
    </div><!-- } main -->
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>

