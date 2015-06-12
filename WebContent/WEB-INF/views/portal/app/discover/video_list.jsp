<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏图片，西藏视频，西藏旅游图片，西藏旅游景点图片，西藏旅游视频，西藏景点视频，天上西藏" />
    <meta name="description" content="看西藏：汇集海量西藏旅游景点图片,由驴友摄影师摄影包括西藏图片，西藏视频，西藏旅游图片，西藏旅游景点图片，西藏旅游视频，西藏景点视频等图片视频，记录了在西藏旅游行程中的名胜景点、风土人情。" />
    <title>西藏图片视频_看西藏-天上西藏官网</title>
	<%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxPortal}assets/css/discover/video_list.css"/>
    <script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
    <script type="text/javascript">
    // Set configuration
    seajs.config({
        alias: {
            "jquery": "jquery/jquery/1.11.1/jquery.js",
            "avalon": "avalon/1.3.7/avalon.js",
            "paginator": "paginator/0.5/bootstrap-paginator.js",
            "header/css": "${ctxPortal}assets/css/common.css",
            "footer/css": "${ctxPortal}assets/css/footer.css"
        }
    });
    </script>
    
    <!--[if lt IE 9]>
    <script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}modules/fix/respond.min.js"></script>
    <![endif]-->
</head>

<body ms-controller="indexView">
<!-- header -->
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"/>
<jsp:include page="${root}/portal/headerFooterController/header"/>

<div class="container main">
    <!-- banner -->
    <div class="banner">
        <img src="${ctxPortal}static/images/banner_video_list.png"/>
    </div><!-- banner End -->

    <h2 class="header"><span class="sr-only">西藏视频</span></h2>
    <ul  class="listing clearfix">
        <li id="204">最多收藏</li>
        <li id="201">最多播放</li>
        <li id="100" class="down">最新发布</li>
    </ul>
    <!-- content -->
    <div class="content clearfix">
        <div ms-repeat="list" class="con-info clearfix">
            <div class="sce-img">
                <img ms-src="{{el.title}}" ms-attr-alt="el.contentTitle"/>
                <a target="_blank" ms-href="${ctx}discover/video/{{el.code}}.html"></a>
            </div>
            <div class="particular">
                <h5 style="height: 20px;"><a target="_blank" ms-href="${ctx}discover/video/{{el.code}}.html">{{el.contentTitle}}</a></h5>
                <div class="look-over">
                    <span class="see" title="查看数">{{el.others.view}}</span><span class="star" title="收藏数">{{el.others.favorite}}</span>
                </div>
            </div>
        </div>
    </div><!-- content End -->
    <!-- page -->
    <div id="paging" class="paging paging-center paging-lg page">
    </div><!-- page End -->
</div>
<!-- footer -->
<jsp:include page="${root}portal/headerFooterController/footer" flush="ture" />

<script type="text/javascript">
	var basePath_ = "${ctx}";
	var basePathPortal = "${ctxPortal}";
</script>
<script type="text/javascript" src="${ctx}portal/assets/js/discover/video_list.js"></script>

</body>
</html>