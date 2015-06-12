<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>游西藏首页</title>
		<%@include file="/common-html/common.jsp"%>
		<link rel="stylesheet"
			href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
		<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
		<link rel="stylesheet"
			href="${ctxManage}/resources/css/travel/travel.css" />
		<script src="${ctxManage}/resources/plugin/respond.min.js"
			type="text/javascript"></script>
	</head>
	<body>
		<!-- main { -->
		<div class="main">
			<!-- 页面位置-->
			<%--<div class="location">
				<label>
					您当前位置为:
				</label>
				<span><a>游西藏</a> -</span>
				<span><a>游西藏首页显示</a>
				</span>
			</div>

			--%><!-- 模块管理 { -->
			<div class="muduleIndex">
				<rimiTag:perm
					url="web/travelController/getManageImg?programaCode=1256se5-qe2c-52e4-a6ce-11505ca05dc9">
					<a
						href="${ctx}/web/travelController/getManageImg?programaCode=1256se5-qe2c-52e4-a6ce-11505ca05dc9"
						target="_self" class="all-inline mbgc_2">Banner管理</a>
				</rimiTag:perm>
				<rimiTag:perm
					url="web/travelController/getManageImg?programaCode=bc22ed19-b2bc-42cd-a8c9-beb96e25ed89">
					<a
						href="${ctx }/web/travelController/getManageImg?programaCode=bc22ed19-b2bc-42cd-a8c9-beb96e25ed89"
						target="_self" class="most-inline mbgc_4">推荐位管理</a>
				</rimiTag:perm>
				<rimiTag:perm
					url="web/travelController/getManageImg?programaCode=25b327a5-7e8c-12e4-b6ce-005056b896a3">
					<a
						href="${ctx }/web/travelController/getManageImg?programaCode=25b327a5-7e8c-12e4-b6ce-005056b896a3"
						target="_self" class="most-inline mbgc_6">热门景点管理</a>
				</rimiTag:perm>
				<rimiTag:perm url="web/readTibetZhMgController/list">
					<a href="${ctx }/web/readTibetZhMgController/list" target="_self"
						class="most-inline mbgc_8">综合攻略管理</a>
				</rimiTag:perm>
				<rimiTag:perm url="web/readTibetZjyMgController/list">
					<a href="${ctx }/web/readTibetZjyMgController/list" target="_self"
						class="most-inline mbgc_0">自驾游攻略管理</a>
				</rimiTag:perm>
				<rimiTag:perm url="web/readTibetQxMgController/list">
					<a href="${ctx }/web/readTibetQxMgController/list" target="_self"
						class="most-inline mbgc_1">骑行攻略管理</a>
				</rimiTag:perm>
				<div class="less-inline merchant">
					<rimiTag:perm url="web/merchant/gotoMerchantManage">
						<a href="${ctx}/web/merchant/gotoMerchantManage" target="_self"
							class="all-inline merchant mbgc_3"> <span>商<br>户<br>管<br>理</span>
						</a>
					</rimiTag:perm>
				</div>
			</div>
			<!-- } 模块管理 -->
		</div>
		<!-- } main -->

		<!-- JS引用部分 -->
		<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>

		<!-- 利用空闲时间预加载指定页面 -->
		<link rel="prefetch">
		<!-- IE10+ -->
		<link rel="next">
		<!-- Firefox -->
		<link rel="prerender">
		<!-- Chrome -->
	</body>
</html>