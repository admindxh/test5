<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>系统设置-账单管理-支付内容管理-支付渠道管理</title>
	<%@ include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>

<body>
	<!-- main { -->
	<div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>活动&专题</a> -</span>
			<span><a>活动&专题信息管理</a> -</span>
			<span><a href="${ctx }web/activityController/showList" target="_self">活动信息管理</a> -</span>
			<span><a>报名渠道管理</a></span>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela mt20">
			<div class="formLine">
				<label>活动名称:</label>
				<span class="dataVal">${activity.name }</span>
				<a href="${ctx}web/order/forOrderManageList" class="btn-base_min ml12" target="_self">所有订单</a>
			</div>

			<div class="formLine">
				<label>应付金额:</label>
				<span class="dataVal">${activity.money }元</span>
			</div>

			<div class="contClassify pos-rela mt12" ms-important="listView">
				<h2 class="title">其他报名渠道</h2>

				<button type="button" class="btn-sure posi_t10_r0" onclick="add();">新增</button>

				<div class="formLine paySourceLine mt12" ms-repeat="data">
					<div class="paySourceName disp-ib">
						<label>报名渠道名称:</label>
						<span class="dataVal channelName">{{el.name}}</span>
					</div>

					<label class="w-auto">报名链接:</label>
					<span class="dataVal maxW600 maxInfo">
						<a ms-href="${ctx}{{el.payLink}}" target="_blank">${ctx}{{el.payLink}}</a>
						<%-- <input class="w-260" type="text" ms-attr-value="${ctx}{{el.payLink}}"> --%>
					</span>

					<div class="operateAre disp-ib float_r">
						<button type="button" class="btn-base" ms-click="edit(el.code, el.name)">编辑</button>&nbsp;
						<button type="button" class="btn-base" ms-click="deleteChannnel(el.code)">删除</button>&nbsp;
						<a ms-href="${ctx}web/order/forSingleOrderManageList?activityCode={{el.activityCode}}&orderChannelSourceCode={{el.code}}" class="btn-base" target="_self">相关订单</a>&nbsp;
						<a ms-href="${ctx}web/activityController/forActivityManage?activityCode={{el.activityCode}}&orderChannelSourceCode={{el.code}}" class="btn-base" target="_self">相关报名表</a>&nbsp;
					</div>
				</div>

			</div>

		</div>
		<!-- } 模块管理 -->
	</div>
	<!-- } main -->

	<!-- JS引用部分 -->
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
	
	<!-- jsp -->
	<script src="${ctxManage}resources/js/avalon.js" type="text/javascript"></script>
	<script type="text/javascript">
		//加载列表{
		var listVM = avalon.define({
			$id: 'listView',
			data: []
		});
		function loadList(){
			var thisCall = loadList;
			var url="${ctx}web/OrderChannelSource/loadOrderChannelSourcelList";
			var params = {
					activityCode: '${activity.code}',
			};
			$.post(url, params, function(response){
			  	listVM.data = response;
				avalon.scan();
			}, 'json');
		}
		function loadData(){
			loadList();
		}
		loadData();
	</script>
	
	<script type="text/javascript">
		function add(){
			popupLayer(
				'新增渠道',
				'<div class="formLine">' +
					'<label class="w-auto">渠道名称：</label><input id="channelName" type="text" maxlength="30" class="w-260">&nbsp;<span class="reqItem">*</span>' +
				'</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).off('click','.sure').on('click', '.sure', function(){
				var url="${ctx}web/OrderChannelSource/addOrderChannelSource";
				var params = {
					activityCode: '${activity.code}',
					name: $("#channelName").val()
				};
				$("#channelName").blur();
				// 含有错误信息数量
				if ($("span.errMesg").length==0 && checkChannelName($("#channelName").val())) {
					$.post(url, params, function(response){
					  	if(response=='success'){
					  		msgBox("添加成功！", "pass", 1200);
					  		loadList();
					  	}else{
					  		msgBox("添加失败！", "erro", 1200);
					  	}
					});
				}else{
					return false;
				}
				closePopup();
			});
		}
		function edit(code, name){
			popupLayer(
					'编辑渠道',
					'<div class="formLine">' +
						'<label class="w-auto">渠道名称：</label><input id="channelName" value="'+name+'" type="text" maxlength="30" class="w-260">&nbsp;<span class="reqItem">*</span>' +
					'</div>',
					'<button type="button" class="btn-sure sure mr15">确定</button>' +
					'<button type="button" class="btn-sure cancel ml15">取消</button>'
				);
				$(document).off('click','.sure').on('click', '.sure', function(){
					var url="${ctx}web/OrderChannelSource/editOrderChannelSource";
					var params = {
						code: code,
						name: $("#channelName").val()
					};
					$("#channelName").blur();
					if ($("span.errMesg").length==0 && checkChannelName($("#channelName").val())) {
						$.post(url, params, function(response){
						  	if(response=='success'){
						  		msgBox("编辑成功！", "pass", 1200);
						  		loadList();
						  	}else{
						  		msgBox("编辑失败！", "erro", 1200);
						  	}
						});
					}else{
						return false;
					}
					closePopup();
				});
		}
		function deleteChannnel(code){
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">是否删除？</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).off('click','.sure').on('click', '.sure', function(){
				var url="${ctx}web/OrderChannelSource/deleteOrderChannelSource";
				var params = {code: code};
				$.post(url, params, function(response){
				  	if(response=='success'){
				  		msgBox("删除成功！", "pass", 1200);
				  		loadList();
				  	}else{
				  		msgBox("删除失败！", "erro", 1200);
				  	}
				});
				closePopup();
			});
		}
		
		
		function checkChannelName(name){
			for(var i = 0; i < $(".channelName").length; i++){
				var val = $(".channelName").eq(i).text();
				if(val==name){
					msgBox("渠道名不能重复", "erro", 1200);
					return false;
				}
			}
			return true;
		}
		
		$(document).on("blur","#channelName",function(){
			var this_val = $(this).val(),
			testPass = $.VLDTOR.IsArticle(this_val),
			lenRange = inputRange(this, 2, 30);
			valid_txtBox(this, testPass && lenRange, "只能是长度为2-30个字符", "top");
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
