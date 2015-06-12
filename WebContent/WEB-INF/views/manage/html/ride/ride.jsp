<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<%@include file="/common-html/common.jsp" %>
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>骑行专区</title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/travel.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <%--<div class="location">
        <label>您当前位置为:</label>
        <span><a>骑行专区</a> -</span>
        <span><a>骑行专区</a></span>
    </div>
    --%><!-- 模块管理 { -->
    <div class="muduleIndex">
    	<rimiTag:perm url="web/homeController/getManageImg?programaCode=er5tyq3h632-75e6-11e4-byce-005ajya05bc9">
        <a href="${ctx}/web/homeController/getManageImg?programaCode=er5tyq3h632-75e6-11e4-byce-005ajya05bc9" target="_self" class="all-inline mbgc_5">banner管理</a>
        </rimiTag:perm>
        <rimiTag:perm url="web/ridecommon/list">
        <a href="${ctx }/web/ridecommon/list" target="_self" class="less-inline mbgc_7">骑行首页相关推荐</a>
        </rimiTag:perm>
    </div><!-- } 模块管理 -->

</div><!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>


<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch"> <!-- IE10+ -->
<link rel="next"> <!-- Firefox -->
<link rel="prerender"> <!-- Chrome -->
</body>
</html>