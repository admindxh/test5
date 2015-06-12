<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>系统设置-账单管理-支付内容管理-单个订单管理</title>
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
			<span><a href="${ctx}/web/order/forPayContentList">支付内容管理</a> -</span>
			<span><a>单个订单管理</a></span>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<div class="searchTools">

				<label class="va_m">付款人:</label>
				<input id="mobileEmail" type="text" placeholder="请输入手机号或电子邮箱">
				
				<button type="button" class="btn-search"><!--查询--></button>

				<button type="button" class="btn-base ml10" onclick="exportExcel();">导出 Excel</button>
				
				<!-- 快捷查询 { -->
				<div class="shortcutSearch">
					
					<div id="channelSourceSelect" class="select-base">
						<input type="hidden" id="orderChannelSourceCode" value="${orderChannelSourceCode }">
						<i class="w-160">全部渠道</i>
						<dl>
							<dt name="">全部渠道</dt>
							<dt name="-1">官方网站</dt>
							<c:forEach var="obj" items="${listOrderChannelSource}">
								<dt name="${obj.code}">${obj.name}</dt>
							</c:forEach>
						</dl>
					</div>
					
					<%--
						<!-- 支付链接 --> 
						<div id="channelSelect" class="select-base">
						<input type="hidden" value="${channelCode}" />
						<i class="w-160">全部渠道</i>
						<dl>
							<dt name="">全部渠道</dt>
							<dt name="-1">官方网站</dt>
							<c:forEach var="obj" items="${listOrderChannel}">
								<dt name="${obj.code}">${obj.name}</dt>
							</c:forEach>
						</dl>
					</div> --%>
					
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
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
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
				<tbody ms-important="listView">
					
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
							<a ms-href="${ctx}web/order/forOrderDetail?orderCode={{el.orderCode}}" class="detail" target="_self">详情</a>
							<a ms-if="el.payState==0" ms-href="${ctx}web/order/forEditOrder?orderCode={{el.orderCode}}" class="modi" target="_self">修改</a>
							<a ms-if="el.payState==0" ms-click="deleteOrder(el.orderCode)">删除</a>
						</td>
					</tr>
					
				</tbody>
			</table>

			<!-- 表格分页 -->
			<div id="listPaging" class="paging"></div>
			
			<form id="exportExcel1" action="${ctx}web/order/exportOrderExcel" method="post">
				<input type="hidden" id="mobileEmail_id" name="mobileEmail">
				<input type="hidden" id="channelCode_id" name="channelCode">
				<input type="hidden" id="orderChannelSourceCode_id" name="orderChannelSourceCode">
				<input type="hidden" id="payState_id" name="payState">
				<input type="hidden" name="activityCode" value="${activityCode}">
			</form>
			
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
		var channelCode = "${channelCode}";
		var orderChannelSourceCode = "${orderChannelSourceCode}";
		var payState = 100;
		
		function initSelect(){
			var divs = $(".select-base");
			for(var i=0; i < divs.length; i++){
				var div = divs.eq(i);
				var value = div.find("input").val();
				var dtValue = div.find("dt[name='"+value+"']").text();
				if(dtValue){
					div.find("i").text(dtValue);
				}
				////.log(dtValue);
			}
			loadList(1);
		}
		
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
					activityCode: "${activityCode}",
					mobileEmail: mobileEmail,
					channelCode: channelCode,
					orderChannelSourceCode: orderChannelSourceCode,
					payState: payState
			};
			$.post(url, params, function(response){
			  	servicesPage("listPaging", response.currentPage, response.totalPage, thisCall);
			  	listVM.data = response.dataList;
				avalon.scan();
			}, 'json');
		}
		function loadData(){
			//loadList(1);
			initSelect();
		}
		loadData();
		
	</script>
	
	<script type="text/javascript">
		$(".btn-search").click(function(){
			mobileEmail = $("#mobileEmail").val();
			loadList(1);
		});
		$("#channelSelect dt").click(function(){
			channelCode = $(this).attr("name");
			loadList(1);
		});
		$("#channelSourceSelect dt").click(function(){
			orderChannelSourceCode = $(this).attr("name");
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
		
		function exportExcel(){
			mobileEmail = $("#mobileEmail").val();
			loadList(1);
			$("#mobileEmail_id").val(mobileEmail);
			$("#channelCode_id").val(channelCode);
			$("#orderChannelSourceCode_id").val(orderChannelSourceCode);
			$("#payState_id").val(payState);
			$("#exportExcel1").submit();
		}
		/* 延迟0.3秒为IE8表格添加隔行变色效果 */
		setTimeout("trAlternateColor('.dataTable')",300);
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
