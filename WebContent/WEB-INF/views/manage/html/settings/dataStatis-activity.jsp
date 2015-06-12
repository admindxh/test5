<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
	<title>系统设置-数据统计-活动统计</title>
	<%@ include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/datepicker.css" />
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
</head>

<body>
	<!-- main { -->
	<div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>系统设置</a> -</span>
			<span><a>数据统计</a> -</span>
			<span><a>活动统计</a></span>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<div class="searchTools">
				<form id="listForm" action="${ctx }/web/activityStatisController/intoActivityStatis">
					<label class="va_m">开始时间:</label>
					<input id="startDate" type="text" class="w-120" name="start" value="${start }">
					<label class="va_m ml10">结束时间:</label>
					<input id="endDate" type="text" class="w-120"  name="end" value="${end }">
					<button type="button" onclick="javascript:$('#listForm').submit()" class="btn-search"><!--查询--></button>
				</form>
			</div>
			<!-- } 搜索查询栏 -->
			
			<!-- 精确搜索详情显示 { -->
			<div class="filament_solid_ddd pos-rela mt20 mb20">
				<div class="formGroup detail">
				
					<span class="dataDetail number">${start }</span>
					<label class="va_m">至</label>
					<span class="dataDetail number">${end }</span>
					<label class="va_m">内</label>
					
					<label class="va_m ml20">活动总点击数:</label>
					<span class="dataDetail number">
						<c:if test="${empty activetyTotalVo.checkNum }">0</c:if>
						<c:if test="${not  empty activetyTotalVo.checkNum }">${activetyTotalVo.checkNum}</c:if>
					
					</span>
					
					<label class="va_m ml20">活动总参与数:</label>
					<span class="dataDetail number">
						<c:if test="${empty activetyTotalVo.acvisitCount }">0</c:if>
						<c:if test="${not  empty activetyTotalVo.acvisitCount }">${activetyTotalVo.acvisitCount}</c:if>
					</span>
					
					<label class="va_m ml20">活动总收入金额:</label>
					<span class="dataDetail number">
						<c:if test="${empty activetyTotalVo.totalMoney }">0</c:if>
						<c:if test="${not  empty activetyTotalVo.totalMoney }">${activetyTotalVo.totalMoney}</c:if>
					
					</span>
					
				</div>
			</div><!-- } 精确搜索详情显示 -->

			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th>统计开始时间</th>
						<th>统计结束时间</th>
						<th>活动名称</th>
						<th>活动点击数</th>
						<th>参与会员数</th>
						<th>收入金额</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="ac" items="${result.dataList}" varStatus="status">
					<tr>
						<td>${start}</td>
						<td>${end }</td>
						<td><a  href="${ctx }/${ac.acurl}" target="_blank">${ac.acname}</a></td>
						<td>${ac.checkNum }</td>
						<td>${ac.acvisitCount }</td>
						<td>
						<c:if test="${empty ac.totalMoney }">0</c:if>
						<c:if test="${not  empty ac.totalMoney }">${ac.totalMoney}</c:if>
						
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<!-- 表格分页 -->
			<div class="paging">
    	<%@include file="/common-html/pager.jsp" %>
    </div>

		</div>
		<!-- } 模块管理 -->
	</div>
	<!-- } main -->

	<!-- JS引用部分 -->
	
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base-form.js"></script>
	<script src="${ctxManage}/resources/plugin/bootstrap-datepicker.js" type="text/javascript"></script>
	<script type="text/javascript">
		// 运行时间选择控件
		datePicker("startDate", "endDate");
		$(function(){
			var  pageInfo =  '<div class="paging-info">'+
					    		'<span class="disp-ib">当前第&nbsp;${result.currentPage }&nbsp;页</span>'+
					    		'<span class="disp-ib">共&nbsp;${result.totalPage }&nbsp;页</span>'+
					    		'<span class="disp-ib">共&nbsp;${result.totalCount }&nbsp;条</span>' +
					    		'</div>';
	    	$("#pageInfo").append(pageInfo);
			//
	    	
		});
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
