<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<%@ include file="/common-html/common.jsp" %>
	<c:if test="${empty listType}">
		<title>系统设置-账单管理-支付内容管理-订单管理-编辑订单</title>
	</c:if>
	<c:if test="${not empty listType}">
		<title>系统设置-账单管理-所有订单管理-编辑订单</title>
	</c:if>
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
			<span><a>系统设置</a> -</span>
			<span><a>账单管理</a> -</span>
			<c:if test="${empty listType}">
				<span><a href="${ctx}/web/order/forPayContentList" target="_self">支付内容管理</a> -</span>
				<span><a href="${ctx}web/order/forSingleOrderManageList?activityCode=${activity.code}" target="_self">订单管理</a> -</span>
			</c:if>
			<c:if test="${not empty listType}">
				<span><a href="${ctx}/web/order/forOrderManageList" target="_self">所有订单管理</a> -</span>
			</c:if>
			<span><a>编辑订单</a></span>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage" style="margin-top: 20px;">
			<!-- 订单信息 { -->
			<div class="contClassify pos-rela mt12">
				<h2 class="title">订单信息</h2>
				
				<div class="formGroup mt12">
					<div class="group_inBlock ml50">
						<label>支付名称:</label>
						<span class="dataDetail">
							<a href="${ctx}${activity.linkUrl}" target="_blank">${activity.name}</a>
						</span>
					</div>
					
					<div class="group_inBlock">
						<label>订单编号:</label>
						<span class="dataDetail">${order.code}</span>
					</div>
					
					<div class="group_inBlock">
						<label>渠道来源:</label>
						<%-- <span class="dataDetail"><c:out value="${(order.orderChannelCode==null||order.orderChannelCode=='')?'官方网站':orderChannel.name}"/></span> --%>
						<span class="dataDetail"><c:out value="${(order.orderChannelSourceCode eq '-1')?'官方网站':orderChannelSource.name}"/></span>
					</div>
				</div>
				
				<div class="formGroup">
					<div class="group_inBlock ml50">
						<label>订单状态:</label>
						<span class="dataDetail"><c:out value="${order.payState==0?'待付款':'已付款'}"/></span>
					</div>
					
					<div class="group_inBlock">
						<label>创建时间:</label>
						<span class="dataDetail"><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm"/></span>
					</div>
					
					<div class="group_inBlock">
						<label>成交时间:</label>
						<span class="dataDetail"><fmt:formatDate value="${order.dealTime}" pattern="yyyy-MM-dd HH:mm"/></span>
					</div>
				</div>
				
				<form id="saveForm" method="POST">
					<input type="hidden" name="code" value="${order.code }">
					<input type="hidden" name="listType" value="${listType}">
					<div class="formGroup">
						<div class="group_inBlock ml50">
							<label>订单金额:</label>
							<input type="text" class="base-inputTxt" name="money" value="${order.money}">
							<span class="disp-ib va_m">元</span>
						</div>
					</div>
				</form>
				
			</div><!-- } 订单信息 -->
			
			<%-- <c:if test="${order.orderChannelCode==null||order.orderChannelCode==''}"> --%>
			<!-- 会员信息 { -->
			<div class="contClassify pos-rela mt12">
				<h2 class="title">会员信息</h2>
				
				<div class="formGroup mt12">
					<div class="group_inBlock ml50">
						<label>手机号:</label>
						<span class="dataDetail">${memberVO.mobile}</span>
					</div>

					<div class="group_inBlock">
						<label>邮箱:</label>
						<span class="dataDetail">${memberVO.email}</span>
					</div>

					<div class="group_inBlock">
						<label>昵称:</label>
						<span class="dataDetail">${memberVO.name}</span>
					</div>

					<div class="group_inBlock w-auto">
						<label>性别:</label>
						<span class="dataDetail"><c:out value="${memberVO.sex==0?'女':'男'}"/></span>
					</div>
					
				</div>
				
				<div class="formGroup">
					<div class="group_inBlock ml50">
						<label>支付宝:</label>
						<span class="dataDetail">${order.alipayEmail }</span>
					</div>
				</div>
			</div><!-- } 会员信息 -->
			
			<!-- 报名信息 { -->
			<div class="contClassify pos-rela mt12">
				<h2 class="title">报名信息</h2>
				
				<!-- <div class="formGroup mt12">
					<div class="group_inBlock ml50 w-auto">
						<label>真实姓名:</label>
						<span class="dataDetail">汉密尔顿</span>
					</div>
				</div>
				
				<div class="formGroup">
					<div class="group_inBlock ml50 w-auto">
						<label>身份证正面图:</label>
						<span class="dataDetail">gjeioroitrehjitrhsdkhihr-24564564576531454562.jpg</span>
						<a href="#" class="btn-base_min">查看</a>
					</div>
				</div>
				
				<div class="formGroup">
					<div class="group_inBlock ml50 w-auto">
						<label>资产证明:</label>
						<span class="dataDetail">《我的资产证明》.doc</span>
						<a href="#" class="btn-base_min">下载</a>
					</div>
				</div> -->
				
				<c:forEach var="obj" items="${listMemberEnrollDetailVO}">
					<div class="formGroup">
						<div class="group_inBlock ml50 w-auto">
							<c:choose>
								<c:when test="${obj.propertyType!=PROPERTY_TYPE_IMAGE && obj.propertyType!=PROPERTY_TYPE_DOC}">
									<label>${obj.property }:</label>
									<span class="dataDetail">${obj.propertyValue }</span>
								</c:when>
								<c:when test="${obj.propertyType==PROPERTY_TYPE_IMAGE}">
									<label>${obj.property }:</label>
									<span class="dataDetail">${obj.fileName }</span>
									<%-- <a href="${ctx}${obj.propertyValue}" target="_blank" class="btn-base_min">查看</a> --%>
									<a id="lookupImg" imgsrc="${ctx}${obj.propertyValue}" class="btn-base_min">查看</a>
								</c:when>
								<c:when test="${obj.propertyType==PROPERTY_TYPE_DOC}">
									<label>${obj.property }:</label>
									<span class="dataDetail">${obj.fileName }</span>
									<a href="${ctx}${obj.propertyValue}" class="btn-base_min">下载</a>
								</c:when>
							</c:choose>
						</div>
					</div>
				</c:forEach>
				
			</div><!-- } 报名信息 -->
			<%-- </c:if> --%>
			
			<!-- 操作按钮 -->
			<div class="formLine">
				<div class="saveOper">
					<button id="saveOrder" type="button" class="btn-sure mr50">保存</button>
					<button type="button" class="btn-sure mr50" onclick="back();">取消</button>
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
		<script type="text/javascript">
		$(document).off("click").on("click", "#lookupImg", function() {
			popupImg("<img class='imgContent_layer' src='" + $(this).attr('imgsrc') + "' />");
		});
	</script>
	<script type="text/javascript">
		function back(){
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">是否返回？</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				javascript:history.back(-1);
				closePopup();
			});
		}
	</script>
	
	<script type="text/javascript">
	
		$("[name='money']").blur(function(){
			var this_val = $(this).val();
			var testPass = $.VLDTOR.IsPrice(this_val) && inputRange(this, 1, 7);
			valid_txtBox_create($(this).next("span"), testPass, "只能为1-7位正数", "right");
			$(this).next("span").next(".errMesg").css("top", "-7px");
		});	
	
		$("#saveOrder").click(function(){
			var this_val = $("[name='money']").val();
			var testPass = $.VLDTOR.IsPrice(this_val) && inputRange($("[name='money']"), 1, 7);
			valid_txtBox_create($("[name='money']").next("span"), testPass, "只能为1-7位正数", "right");
			$("[name='money']").next("span").next(".errMesg").css("top", "-7px");
			if(testPass){
				$("#saveForm").attr("action", "${ctx}web/order/editOrder");
				$("#saveForm").submit();
			}
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
