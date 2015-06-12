<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
		String path = request.getContextPath();
String port = ":" + request.getServerPort();
if ("80".equals(""+request.getServerPort())) {
		port = "";

	}
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + port + path + "/";	   
request.setAttribute("ctx",basePath);
	    request.setAttribute("ctxManage",basePath+"/manage/");
	    request.setAttribute("ctxPortal",basePath+"/portal/"); //xiangwq 
	    request.setAttribute("ctxMRead", basePath+"manage/html/read/");//csl
	    request.setAttribute("ctxApp",basePath+"portal/app/"); //
	    request.setAttribute("root","/");
%>

<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏图片，西藏视频，西藏旅游图片，西藏旅游景点图片，西藏旅游视频，西藏景点视频，天上西藏" />
    <meta name="description" content="看西藏：汇集海量西藏旅游景点图片,由驴友摄影师摄影包括西藏图片，西藏视频，西藏旅游图片，西藏旅游景点图片，西藏旅游视频，西藏景点视频等图片视频，记录了在西藏旅游行程中的名胜景点、风土人情。" />
    <title>西藏图片视频_看西藏-天上西藏官网</title>
    <link rel="stylesheet" href="${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxPortal}assets/css/discover/picture_list.css"/>
    <script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
    <script type="text/javascript">
    seajs.config({
        alias: {
            "jquery": "jquery/jquery/1.11.1/jquery.js",
            "avalon": "avalon/1.3.7/avalon.js",
            "paginator": "paginator/0.5/bootstrap-paginator.js",
            "header/css": "${ctxPortal}/assets/css/common.css",
            "footer/css": "${ctxPortal}/assets/css/footer.css"
        }
    });
    
    </script>
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
</head>
<body >
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"/>
<jsp:include page="${root}/portal/headerFooterController/header"/>
<div class="container main" ms-controller="indexView" >
    <!-- banner -->
    <div class="banner">
        <img src="${ctxPortal}/static/images/banner_tsxz_list.png"/>
    </div><!-- banner End -->
    <div class="header"></div>
    <ul class="listing clearfix">
        <li value="3">最多收藏</li>
        <li value="2">最多评论</li>
        <li value="1" class="down">最新发布</li>
    </ul>
   <!-- content -->
    <div class="content clearfix" >
         <div id="nopicture" style="display: none;" class="nodata"></div>
        <div ms-repeat="list" class="con-info clearfix">
            <div class="sce-img">
               <a target="_blank" ms-href="${ctx}{{el.hyperlink}}"> <img ms-attr-alt="el.name" ms-src="{{el.coverUrl?'${ctx}'+el.coverUrl:'../static/images/img2.png'}}"/></a>
            </div>
            <div class="particular">
                <h5><a target="_blank" ms-href="${ctx}{{el.hyperlink}}" class="js_sub_title">{{el.name}}</a></h5>
                <p class="presentation js_sub_summary">
                {{el.summary}}
                </p>
                <div class="look-over">
                    <span class="see" title="查看数">{{el.falseViewcount}}</span><span class="star" title="收藏数">{{el.falseFavoriteNum}}</span>
                </div>
            </div>
        </div>
    </div><!-- content End -->
     <div ms-if="list.size()"  id="paging" class="paging paging-center paging-lg page">
    <!-- page -->
    </div>
</div>
<!-- footer -->
<jsp:include page="${root}portal/headerFooterController/footer" flush="ture"/> 

<script type="text/javascript">
	var basePath_ = "${ctx}";
	var basePathPortal = "${ctxPortal}";
</script>
<script type="text/javascript" src="${ctx}portal/assets/js/discover/picture_list.js"></script>

<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
</body>
</html>
