<%@ page import="com.rimi.ctibet.web.controller.OrderController"%>
<%@ page import="com.rimi.ctibet.domain.CommonOrder"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>骑行专区 - 订单管理</title>
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
			<span><a>骑行专区</a> -</span>
			<span><a>订单管理</a></span>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<div class="searchTools">

<!-- 				<label class="va_m">付款人:</label> -->
<!-- 				<input id="mobileEmail" type="text" placeholder="请输入手机号或电子邮箱"> -->
				
				<label class="va_m ml12">订单名称:</label>
				<input id="orderName" type="text" placeholder="请输入名称">
				
				<label class="ml20">创建时间:</label>
				<input id="dateSearch" type="text" placeholder="" class="w-120" name="createTime" value="${time }">

				<!--查询-->
				<button type="button" class="btn-search"></button>

				<!-- 快捷查询{ -->
				<div class="shortcutSearch">
					<div id="payStateSelect" class="select-base ml10">
						<input type="hidden" value="100" />
						<i class="w-110">全部状态</i>
						<dl>
							<dt name="100">全部状态</dt>
							<dt name="0">未付款</dt>
							<dt name="1">已付款</dt>
						</dl>
					</div>
				</div>
				<!-- }快捷查询 -->
			</div>
			<!-- } 搜索查询栏 -->

			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable" >
				<thead>
					<tr>
						<th>订单名称</th>
						<th>订单编号</th>
						<th>订单状态</th>
						<th>订单金额</th>
						<th>创建时间</th>
						<th>用户</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody ms-important="listView" id="dataTable">
					<tr ms-repeat="data">
						<td>
							<%-- 装备订单 --%>
							<a ms-if="el.equipUrl" ms-href="${ctx}{{el.equipUrl}}" target="_activityDetail">
								<span class="maxW300">{{el.orderName}}</span>
							</a>
							<%-- 普通订单 --%>
							<span class="maxW300" ms-if="!el.equipUrl">{{el.orderName}}</span>
						</td>
						<td>{{el.orderCode}}</td>
						<td>{{el.payState == 0 ? '未付款' : '已付款' }}</td>
						<td>{{el.money}}</td>
						<td>{{el.createTime|date("yyyy-MM-dd HH:mm")}}</td>
						<td>{{el.memberName}}</td>
						<td>
							<%-- 操作
								<a ms-href="${ctx}web/common-order/detail?orderCode={{el.code}}" class="detail" target="_self">详情</a>
								<a ms-if="el.payState==0" ms-href="${ctx}web/common-order/edit?orderCode={{el.code}}" class="modi" target="_self">修改</a> 
							--%>
							<a ms-if="el.payState==<%= CommonOrder.PAY_STATE_NO %>" ms-click="deleteOrder(el.code)">删除</a>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- 表格分页 -->
			<div id="listPaging" class="paging"></div>
			<!-- 表格分页 -->
			<div class="paging"></div>
		</div>
		<!-- } 模块管理 -->
	</div>
	<!-- } main -->

	<!-- JS引用部分 -->
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/bootstrap-datepicker.js" type="text/javascript"></script>
	<script src="${ctxManage}resources/js/avalon.js" type="text/javascript"></script>
	<script src="${ctxManage}resources/js/bootstrap-paginator.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	
		// 运行时间选择控件
		$("#dateSearch").datepicker();
		
		var mobileEmail = "";
		var orderName = "";
		var createTime = $("#dateSearch").val();
		var payState = 100;
		
		function servicesPage(pageId, currentPage, totalPage, call_){
			var options = {
		     	currentPage: currentPage || 1,
		     	totalPages: totalPage || 1,
		     	itemTexts: function (type, page, current) {
		            switch (type) {
		                case "first": return "首页";
		                case "prev":  return "上一页";
		                case "next":  return "下一页";
		                case "last":  return "末页";
		                case "page":  return page;
		            }
		        },
		     	onPageChanged: function(e,oldPage,newPage){
		     		call_(newPage);
		    	}
			};
			$('#'+pageId).bootstrapPaginator(options);
		}
		
		// 加载列表
		var listVM = avalon.define({ $id: 'listView', data: [] });
		function loadList(currentPage){
			var url="${ctx}web/common-order/list";
			var params = {
				//pageSize: 2, 
				currentPage : currentPage,
// 				mobileEmail : mobileEmail,
				orderTime : createTime,
				orderName : orderName,
				payState : payState
			};
			$.post(url, params, function (response) {
			  	servicesPage("listPaging", response.currentPage, response.totalPage, loadList);
			  	listVM.data = response.dataList;
				avalon.scan();
			}, "json");
		}
		// 初始化列表数据
		loadList(1);
		/* 延迟0.3秒为IE8表格添加隔行变色效果 */
		setTimeout("trAlternateColor('.dataTable')", 300);
	</script>
	
	<script type="text/javascript">
		$(".btn-search").click(function(){
// 			mobileEmail = $("#mobileEmail").val();
			orderName = $("#orderName").val();
			createTime = $("#dateSearch").val();
			loadList(1);
		});
		$("#payStateSelect dt").click(function(){
			payState = $(this).attr("name");
			loadList(1);
		});

		/**
		 * 删除订单.
		 * code 订单编号
		 */
		function deleteOrder(code){
			popupLayer('',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">是否删除？</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				var url = "${ctx}web/common-order/" + code;
				$.post(url, function(response){
				  	if(response.status){
				  		msgBox("删除成功！", "pass", 1500);
				  		loadList(1);
				  	}else{
				  		msgBox("删除失败！", "erro", 1500);
				  	}
				}, "json");
				closePopup();
			});
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
