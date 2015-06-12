<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>骑行专区-骑行路线管理</title>
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
			<span><a>骑行专区</a> -</span>
			<span><a href="#" target="_self">星级服务</a> -</span>
			<span><a>骑行路线管理</a></span>
		</div>

		<div class="searchManage">
			<a href="${ctx }web/rideLine/forAddRideLine" target="_self" class="btn-anchor">新建路线</a>
			<a href="javascript:void(0);" class="btn-anchor batchdelete">批量删除</a>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<div class="searchTools">

				
				<label class="va_m ml12">名称:</label>
				<input id="rideLineName" type="text" placeholder="请输入名称">

				<button type="button" class="btn-search"><!--查询--></button>

			</div>
			<!-- } 搜索查询栏 -->

			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable" >
				<thead>
					<tr>
						<th>
		                    <input type="checkbox" name="dataCheck" class="allCheck">全选
		                </th>
						<th>名称</th>
						<th>副标题</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody ms-important="listView" id="dataTable">
					
					<tr ms-repeat="data">
						<td>
		                    <input type="checkbox" name="dataCheck" class="dataCheck" ms-attr-value="el.code">
		                </td>
						<td>
							<a ms-attr-href="${ctx}{{el.link}}" target="_ridelineDetail">
								<span class="maxW300">{{el.name}}</span>
							</a>
						</td>
		                <td>{{el.subTitle}}</td>
						<td>{{el.createTime|date("yyyy-MM-dd HH:mm")}}</td>
						<td>
							<a ms-attr-href="${ctx}web/rideLine/forEditRideLine?code={{el.code}}" target="_self">修改</a>
							<a ms-click="deleteData(el.code)">删除</a>
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

	<!-- jsp -->
	<script src="${ctxManage}resources/js/avalon.js" type="text/javascript"></script>
	<script src="${ctxManage}resources/js/bootstrap-paginator.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		var rideLineName = "";
		
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
			var url="${ctx}web/rideLine/loadRideLineList";
			var params = {
					//pageSize: 2, 
					currentPage: currentPage,
					name: rideLineName
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
			rideLineName = $("#rideLineName").val();
			loadList(1);
		});
		
		$(".batchdelete").click(function(){
			var checkBoxs = $(".dataCheck:checked");
			if(checkBoxs.length>0){
				var array = new Array();
				for(var i = 0; i < checkBoxs.length; i++){
					array.push(checkBoxs.eq(i).val());
				}
				deleteData(array.join(","));
			}else{
				msgBox("请选择要删除的数据！", "erro", 1500);
			}
		});
		
		//删除
		function deleteData(codes){
			//alert(codes);
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">是否删除？</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				var url="${ctx}web/rideLine/deleteRideLine";
				var params = {
					codes: codes
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
