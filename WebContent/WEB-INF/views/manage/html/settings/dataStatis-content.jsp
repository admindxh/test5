<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
<%@ include file="/common-html/common.jsp" %>
	<title>系统设置-数据统计-内容统计</title>
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
			<span><a>内容统计</a></span>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<div class="searchTools">
				<form id="listForm" action="${ctx }/web/frontContentStatisController/intoFrontContentStatis" method="post">
				<label class="va_m">开始时间:</label>
				<input id="startDate" value="${start}" name="start" type="text" class="w-120">
				<label class="va_m ml10">结束时间:</label>
				<input id="endDate" value="${end}" name="end" type="text" class="w-120">
				
				<div class="select-base ml10">
					<i class="w-200">${proTypeText }</i>
						<dl>
							<dt  inputValue="" inputName="proType"  inputTextValue="全部版块" inputTextName="proTypeText">全部版块</dt>
							<c:forEach var="base" items="${baselist}">
								<dt  inputValue="${base.code}"  inputName="proType"   inputTextValue="${base.typename}" inputTextName="proTypeText">${base.typename}</dt>
							</c:forEach>
						</dl>
							<input id="proType" type="hidden" value="${protype }" name="proType"/>
							<input id="proTypeText" type="hidden" value="${proTypeText }" name="proTypeText"/>
				</div>
				
				<!-- 查询 -->
				<button type="button" onclick="javascript:$('#listForm').submit()" class="btn-search">
					<!--查询-->
				</button>
				</form>
			</div><!-- } 搜索查询栏 -->
			
			<!-- 精确搜索详情显示 { -->
			<div class="filament_solid_ddd pos-rela mt20 mb20">
				<div class="formGroup detail">
				
					<span class="dataDetail number">${start }</span>
					<label class="va_m">至</label>
					<span class="dataDetail number">${end }</span>
					<label class="va_m">内</label>
					
					<span class="dataDetail">${proTypeText }</span>
					
					<label class="va_m ml20">查看:</label>
					<span class="dataDetail number">${contentVo.clickCount}</span>
					
					<label class="va_m ml20">收藏:</label>
					<span class="dataDetail number">${contentVo.favateCount}</span>
					
					<label class="va_m ml20">被赞:</label>
					<span class="dataDetail number">${contentVo.praiseCount}</span>
					
					<label class="va_m ml20">评论:</label>
					<span class="dataDetail number">${contentVo.replyCount}</span>
					
				</div>
			</div><!-- } 精确搜索详情显示 -->

			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th class="w-180">统计开始日期</th>
						<th class="w-180">统计截止日期</th>
						<th>栏目</th>
						<th>查看</th>
						<th>收藏数</th>
						<th>被赞数</th>
						<th>评论数</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${result.dataList}" var="ct" >
					<tr>
						<td>${start }</td>
						<td>${end }</td>
						<td>${ct.proName }</td>
						<td>${ct.clickCount }</td>
						<td>${ct.favateCount }</td>
						<td>${ct.praiseCount }</td>
						<td>${ct.replyCount }</td>
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
	<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
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
