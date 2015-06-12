<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="udj" uri="/user-defined-jstl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="author" content="quansenx">
    <title> 游西藏_西藏旅游攻略-天上西藏官网</title>
	<meta name="keywords" content=”西藏骑行攻略，西藏旅游攻略，西藏自驾游攻略，西藏景点"/>
	<meta name="description" content="游西藏专栏为您提供西藏旅游攻略、骑行攻略、徒步攻略和西藏景点介绍，游西藏，关注天上西藏官网。"/>

    <%@include file="/common-html/mainfrontcommon.jsp" %>
    <link rel="stylesheet" href="${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPortal}assets/css/common.css">
    <script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <style>
        .overflow{height:430px; overflow:hidden;}
        .container{width: 1000px !important;padding: 0 !important;}
        .circulation .img-box{height: auto;}
        .container .col-xs-9.tourist-left,
        .container .col-xs-3.tourist-right{margin-top: 0 !important;}
        #top{margin-bottom: 0;}
        .h3-fz{font-size: 14px;}
    </style>
    <script type="text/javascript" src="${ctx}common-js/maincommon.js"></script>
    <script>
        // Set configuration
        seajs.config({
            alias: {
                "jquery": "jquery/jquery/1.11.1/jquery.js",
                "avalon":"avalon/1.3.7/avalon.js",
                "content/top/css":"${ctxPortal}assets/css/content_top.css",
                "footer/css":"${ctxPortal}assets/css/footer.css"
            }
        });
        seajs.use(['content/top/css','footer/css']);
        var basePath_ = "${ctx}";
    </script>
</head>
<body>
    <!-- top -->
    <jsp:include page="/WEB-INF/views/portal/app/header/index.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/views/portal/app/banner/yxz_banner.jsp"></jsp:include>
    <jsp:include page="${root}portal/app/index/index-search.html"></jsp:include>
    <jsp:include page="/WEB-INF/views/portal/app/tourism/content_header.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/views/portal/app/tourism/tourist.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/views/portal/app/tourism/guide.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/views/portal/app/tourism/driving.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/views/portal/app/tourism/riding.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/views/portal/app/footer/index.jsp"></jsp:include>
    <script type="text/javascript" src="${ctx}/common-js/seajindex.js"></script>
	<script type="text/javascript" src="${ctx}portal/assets/js/tourism/tourism.js"></script>
</body>
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
</html>