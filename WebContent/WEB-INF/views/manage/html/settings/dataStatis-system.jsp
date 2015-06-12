<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8" />
	<%@include file="/common-html/common.jsp" %>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>系统设置-数据统计-系统数据统计</title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/datepicker.css" />
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>
<body>
    <!-- main { -->
    <div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
    		<span><a>系统设置</a> -</span>
    		<span><a>数据统计</a> -</span>
    		<span><a>系统数据统计</a></span>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 详情显示 { -->
			<div class="filament_solid_ddd pos-rela mt20 mb20">
				<div class="formGroup detail">
				
					<label class="va_m">总会员注册数:</label>
					<span class="dataDetail number">${memTotal }</span>

					<label class="va_m ml15">总PV值:</label>
					<span class="dataDetail number">${ pvTotal}</span>

					<label class="va_m ml15">总平均日PV值:</label>
					<span class="dataDetail number">${pvDayTotal}</span>

					<label class="va_m ml15">总评论量:</label>
					<span class="dataDetail number">${replyTotal }</span>

					<label class="va_m ml15">总攻略量:</label>
					<span class="dataDetail number">${strageTotal }</span>
				
				</div>
			</div><!-- } 详情显示 -->
			<!-- 搜索查询栏 { -->
			<div class="searchTools" style="margin-top:10px;">
				<form id="form1" method="post" action="${ctx }/web/systemController/intoSystemStatic">
					<label class="va_m">开始时间:</label>
					<input id="startDate" value="${start1 }" name="start" type="text" class="w-80">
					<label class="va_m ml10">结束时间:</label>
					<input id="endDate" type="text" value="${end1 }" name="end" class="w-80">
					<button type="button" class="btn-search" onclick="javascript:$('#form1').submit()"><!--查询--></button>
				</form>
			</div><!-- } 搜索查询栏 -->
			
			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th>开始日期</th>
						<th>结束日期</th>
						<th>注册数</th>
						<th>活跃用户</th>
						<th>平均日活跃用户</th>
						<th>PV值(访问量)</th>
						<th>平均日PV量</th>
						<th>评论量</th>
						<th>用户上传攻略量</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${start1 }</td>
						<td>${end1 }</td>
						<td>${listMemCount }</td>
						<td>${listMemActiveCount }</td>
						<td>${listMemActiveDayCount }</td>
						<td>${listPvTotal }</td>
						<td>${listPvDayTotal }</td>
						<td>${ listReplyTotal}</td>
						<td>${ listStrageTotal}</td>
					</tr>
				</tbody>
			</table>
				
		</div><!-- } 模块管理 -->
    </div><!-- } main -->

    <!-- JS引用部分 -->
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base-form.js"></script>
	<script src="${ctxManage}/resources/plugin/bootstrap-datepicker.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
	<script type="text/javascript">
		// 运行时间选择控件
		datePicker("startDate", "endDate");
	</script>
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>
