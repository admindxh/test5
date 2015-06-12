<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
	<title>系统设置-数据统计-广告统计</title>
	<%@ include file="/common-html/common.jsp" %>
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/datepicker.css" />
	<script type="text/javascript">
	$(function(){
		var  pageInfo =  '<div class="paging-info">'+
				    		'<span class="disp-ib">当前第&nbsp;${result.currentPage }&nbsp;页</span>'+
				    		'<span class="disp-ib">共&nbsp;${result.totalPage }&nbsp;页</span>'+
				    		'<span class="disp-ib">共&nbsp;${result.totalCount }&nbsp;条</span>' +
				    		'</div>';
    	$("#pageInfo").append(pageInfo);
	});	
	
	</script>
</head>
<body>
	<!-- main { -->
	<div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>系统设置</a> -</span>
			<span><a>数据统计</a> -</span>
			<span><a>广告统计</a></span>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<div class="searchTools">
				<form action="${ctx }/web/frontContentStatisController/intoFrontAderStatis" id="listForm" method="post">
				<label class="va_m">开始时间:</label>
				<input id="startDate" name="start" value="${start }" type="text" class="w-120">
				<label class="va_m ml10">结束时间:</label>
				<input id="endDate" name="end" value="${end }"  type="text" class="w-120">
				<div class="select-base ml10">
					<input type="hidden" value="0" />
					<i class="w-160">${proTypeText}</i>
					<dl> 
							<dt  inputValue=""  inputName="destinationCode"   inputTextValue="全部页面" inputTextName="proTypeText">全部页面</dt>
								<c:forEach var="bs" items="${baselist}">
									<dt  inputValue="${bs.code}" inputName="proType"  inputTextValue="${bs.programaName}" inputTextName="proTypeText">${bs.programaName}</dt>
								</c:forEach>
							</dl>
							<input id="proType" type="hidden" value="${proType}" name="proType"/>
							<input id="proTypeText" type="hidden" value="${proTypeText }" name="proTypeText"/>
				</div>

				<!-- 查询 -->
				<button type="button" class="btn-search" onclick="javascript:$('#listForm').submit()">
					<!--查询-->
				</button>
				</form>
			</div><!-- } 搜索查询栏 -->
			
			<!-- 精确搜索详情显示 { -->
			<div class="filament_solid_ddd pos-rela mt20 mb20">
				<div class="formGroup detail">
					<span class="dataDetail number ml60">${start }</span>
					<label class="va_m">至</label>
					<span class="dataDetail number">${end }</span>
					<label class="va_m">内</label>
					<span class="dataDetail">${proTypeText}</span>
					<label class="va_m ml20">点击数:</label>
					<span class="dataDetail number">${contentVo.clickCount}</span>
				</div>
			</div><!-- } 精确搜索详情显示 -->
			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th>所属页面</th>
						<th>广告位</th>
						<th>点击数</th>
					</tr>
				</thead>
				<tbody>
				  <c:forEach items="${result.dataList}" var="result">
						<tr>
							<td>
								<a href="${ctx}/${result.url}" target="_blank">
									<span class="maxW420">${result.proName }</span>
								</a>
							</td>
							<td>
								<img src="${ctx}/${result.imgUrl}" style="width: 36px;height: 36px;" class="thumbnail" alt="广告位缩略图" title="点击查看原图">
							</td>
							<td>${result.clickCount }</td>
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
	<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
	<script src="${ctxManage}/resources/plugin/bootstrap-datepicker.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		// 运行时间选择控件
		datePicker("startDate", "endDate");
		
		// 查看缩略图
		$(document).on("click", ".tableManage .thumbnail", function() {
			var thisPath = $(this).attr("src");
			popupImg('<img   src=' + thisPath + '>');
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
