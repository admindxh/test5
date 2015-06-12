<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>看西藏-看西藏首页显示</title>
    <%@include file="/common-html/common.jsp" %>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/travel.css" />
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置--><%--
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>看西藏</a> -</span>
        <span><a href="#" target="_self">看西藏首页显示</a></span>
    </div>

    --%><!-- 模块管理 { -->
    <div class="muduleIndex">
    	<rimiTag:perm url="web/mutiController/banner">
        <a href="${ctx}web/mutiController/banner" target="_self" class="all-inline mbgc_0">Banner管理</a>
        </rimiTag:perm>
        <rimiTag:perm url="web/mutiController/showMuti">
        <a href="${ctx}web/mutiController/showMuti" target="_self" class="all-inline mbgc_1">图说西藏显示管理</a>
        </rimiTag:perm>
        <rimiTag:perm url="video-display.html">
        <a href="video-display.html" target="_self" class="all-inline mbgc_2">视频显示管理</a>
        </rimiTag:perm>
    </div><!-- } 模块管理 -->
</div><!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/home/home.js" type="text/javascript"></script>


<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch"> <!-- IE10+ -->
<link rel="next"> <!-- Firefox -->
<link rel="prerender"> <!-- Chrome -->
</body>
</html>