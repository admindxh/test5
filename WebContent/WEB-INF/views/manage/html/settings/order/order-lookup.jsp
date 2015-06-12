<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<%@ include file="/common-html/common.jsp" %>
	<c:if test="${empty listType}">
		<title>系统设置-账单管理-支付内容管理-订单管理-查看订单</title>
	</c:if>
	<c:if test="${not empty listType}">
		<title>系统设置-账单管理-所有订单管理-查看订单</title>
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
				<span><a href="${ctx}web/order/forSingleOrderManageList?activityCode=${activity.code} " target="_self">订单管理</a> -</span>
			</c:if>
			<c:if test="${not empty listType}">
				<span><a href="${ctx}/web/order/forOrderManageList" target="_self">所有订单管理</a> -</span>
			</c:if>
			<span><a>查看订单</a></span>
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
				
				<div class="formGroup">
					<div class="group_inBlock ml50">
						<label>订单金额:</label>
						<span class="disp-ib va_m">${order.money}元</span>
					</div>
				</div>
				
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
									<%--<a href="${ctx}${obj.propertyValue}" target="_blank" class="btn-base_min">查看</a>--%>
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
					<a class="btn-anchor mr50" onclick="javascript:history.back(-1);">返回</a>
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
	<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).off("click").on("click", "#lookupImg", function() {
			popupImg("<img class='imgContent_layer' src='" + $(this).attr('imgsrc') + "' />");
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
