<%@page import="com.rimi.ctibet.web.controller.OrderController"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>系统设置-账单管理-所有订单管理</title>
	<%@ include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>

<body>
	<!-- main { -->
	<div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>系统设置</a> -</span>
			<span><a>账单管理</a> -</span>
			<span><a>所有订单管理</a></span>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<div class="searchTools">

				<label class="va_m">付款人:</label>
				<input id="mobileEmail" type="text" placeholder="请输入手机号或电子邮箱">
				
				<label class="va_m ml12">支付名称:</label>
				<input id="activityName" type="text" placeholder="请输入名称">

				<button type="button" class="btn-search"><!--查询--></button>

				<!-- 快捷查询 { -->
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
				<!-- } 快捷查询 -->
			</div>
			<!-- } 搜索查询栏 -->

			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable" >
				<thead>
					<tr>
						<th>支付名称</th>
						<th>订单编号</th>
						<th>订单状态</th>
						<th>订单金额</th>
						<th>创建时间</th>
						<th>渠道</th>
						<th>用户</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody ms-important="listView" id="dataTable">
					
					<tr ms-repeat="data">
						<td>
							<a ms-href="${ctx}{{el.activityLinkUrl}}" target="_activityDetail">
								<span class="maxW300">{{el.activityName}}</span>
							</a>
						</td>
						<td>{{el.orderCode}}</td>
						<td>{{el.payState==0?'未付款':'已付款'}}</td>
						<td>{{el.orderMoney}}</td>
						<td>{{el.createTime|date("yyyy-MM-dd HH:mm")}}</td>
						<!-- <td>{{el.orderChannelName?el.orderChannelName:'官方网站'}}</td> -->
						<td>{{el.orderChannelSourceName}}</td>
						<td>{{el.mobile?el.mobile:el.email}}</td>
						<td>
							<a ms-href="${ctx}web/order/forOrderDetail?orderCode={{el.orderCode}}&listType=<%=OrderController.LIST_TYPE_ALL%>" class="detail" target="_self">详情</a>
							<a ms-if="el.payState==0" ms-href="${ctx}web/order/forEditOrder?orderCode={{el.orderCode}}&listType=<%=OrderController.LIST_TYPE_ALL%>" class="modi" target="_self">修改</a>
							<a ms-if="el.payState==0" ms-click="deleteOrder(el.orderCode)">删除</a>
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
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>

	<!-- jsp -->
	<script src="${ctxManage}resources/js/avalon.js" type="text/javascript"></script>
	<script src="${ctxManage}resources/js/bootstrap-paginator.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		var mobileEmail = "";
		var activityName = "";
		var payState = 100;
		
		function servicesPage(pageId, currentPage, totalPage, call_){
			var options = {
		     	currentPage: currentPage || 1,
		     	totalPages: totalPage || 1,
		     	itemTexts: function (type, page, current) {
		            switch (type) {
		                case "first":
		                    return "首页";
		                case "prev":
		                    return "上一页";
		                case "next":
		                    return "下一页";
		                case "last":
		                    return "末页";
		                case "page":
		                    return page;
		            }
		        },
		     	onPageChanged: function(e,oldPage,newPage){
		     		call_(newPage);
		    	}
			}
			$('#'+pageId).bootstrapPaginator(options);
		}
		
		//加载列表{
		var listVM = avalon.define({
			$id: 'listView',
			data: []
		});
		function loadList(currentPage){
			var thisCall = loadList;
			var url="${ctx}web/order/loadOrderList";
			var params = {
					//pageSize: 2, 
					currentPage: currentPage,
					mobileEmail: mobileEmail,
					activityName: activityName,
					payState: payState
			};
			$.post(url, params, function(response){
			  	servicesPage("listPaging", response.currentPage, response.totalPage, thisCall);
			  	listVM.data = response.dataList;
				avalon.scan();
			}, 'json');
		}
		function loadData(){
			loadList(1);
		}
		loadData();
		/* 延迟0.3秒为IE8表格添加隔行变色效果 */
		setTimeout("trAlternateColor('.dataTable')",300);
	</script>
	
	<script type="text/javascript">
		$(".btn-search").click(function(){
			mobileEmail = $("#mobileEmail").val();
			activityName = $("#activityName").val();
			loadList(1);
		});
		$("#payStateSelect dt").click(function(){
			payState = $(this).attr("name");
			loadList(1);
		});
		//删除
		function deleteOrder(code){
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">是否删除？</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				var url="${ctx}web/order/deleteOrder";
				var params = {
					orderCode: code
				};
				$.post(url, params, function(response){
				  	if(response=='success'){
				  		msgBox("删除成功！", "pass", 1500);
				  		loadList();
				  	}else{
				  		msgBox("删除失败！", "erro", 1500);
				  	}
				});
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
