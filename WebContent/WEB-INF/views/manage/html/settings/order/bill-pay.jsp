<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>系统设置-账单管理-支付内容管理</title>
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
			<span><a>支付内容管理</a></span>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<div class="searchTools">

				<label class="va_m">关键字:</label>
				<input id="searchName" type="text" placeholder="">

				<button type="button" class="btn-search"><!--查询--></button>

			</div>
			<!-- } 搜索查询栏 -->

			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th>支付对象</th>
						<th>支付名称</th>
						<th>单笔金额</th>
						<th>支付数笔</th>
						<th>共计金额</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody ms-important="listView">
					<tr ms-repeat="data">
						<td>活动</td>
						<td style="max-width:480px;">
							<a ms-attr-href="${ctx}activity/forActivityDetail?code={{el.code}}" target="_blank">
								<span class="ellipsis maxW460">{{el.name}}</span>
							</a>
						</td>
						<td>{{el.money}}</td>
						<td>{{el.payNum}}</td>
						<td>{{el.totalMoney}}</td>
						<td>
							<%-- <a ms-href="${ctx}web/orderChannel/forOrderChannelManage?activityCode={{el.code}}" class="paySourceManage" target="_self">支付渠道管理</a> --%>
							<%-- <a ms-href="${ctx }web/OrderChannelSource/forOrderChannelSourceManage?activityCode={{el.code}}" target="_self">报名渠道管理</a> --%>
							<a ms-href="${ctx}web/order/forSingleOrderManageList?activityCode={{el.code}}" class="orderManage" target="_self">订单管理</a>
						</td>
					</tr>
				</tbody>
			</table>

			<!-- 表格分页 -->
			<div id="listPaging" class="paging"></div>
			
		</div>
		<!-- } 模块管理 -->
	</div>
	<!-- } main -->

	<!-- JS引用部分 -->
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
	
	<!-- jsp -->
	<script src="${ctxManage}resources/js/avalon.js" type="text/javascript"></script>
	<script src="${ctxManage}resources/js/bootstrap-paginator.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		var searchName = "";
		
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
			var url="${ctx}web/order/loadPayContentList";
			var params = {
					//pageSize: 2, 
					name: searchName,
					currentPage: currentPage
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
	</script>

	<script type="text/javascript">
		$(".btn-search").click(function(){
			searchName = $("#searchName").val();
			loadList(1);
		});
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
