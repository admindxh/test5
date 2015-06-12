<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String port = ":" + request.getServerPort();
    if ("80".equals(""+request.getServerPort())) {
        port = "";
    }
    String basePath = request.getScheme() + "://" + request.getServerName() + port + path + "/";
    request.setAttribute("ctx", basePath);
    request.setAttribute("ctxManage", basePath + "/manage/");
    request.setAttribute("ctxPortal", basePath + "/portal/"); //xiangwq
    request.setAttribute("ctxMRead", basePath + "manage/html/read/");//csl
    request.setAttribute("ctxApp", basePath + "portal/app/");
    request.setAttribute("root", "/");
%>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
   	<meta name="keywords" content="看西藏、西藏视频、图说西藏、西藏文化、西藏特色、西藏历史、西藏节日、西藏风俗、西藏风景" />
	<meta name="description" content="看西藏专栏为您提供西藏旅游攻略、骑行攻略、徒步攻略和西藏景点介绍的视频和图片。看西藏，尽在天上西藏官网。" />
	<title>看西藏_西藏视频_图说西藏_天上西藏</title>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
        seajs.config({
            alias: {
                "jquery": "jquery/jquery/1.11.1/jquery.js",
                "avalon": "avalon/1.3.7/avalon.js",
                "common/css": "${ctxPortal}assets/css/common.css",
                "discover/css": "${ctxPortal}assets/css/discover.css",
                "footer/css": "${ctxPortal}assets/css/footer.css"
            }
        });
        var  ctxPortal = "${ctxPortal}";
    </script>
    <style>
        .index-more{width: 90px !important;}
    </style>
</head>
<body>
<!-- header -->
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"/>
<jsp:include page="${root}portal/headerFooterController/header" flush="ture"/>
<div class="container">
    <div class="bd">
        <div class="xz-box clearfix">
            <div class="bd-left">
                <div class="search">
                	<!-- 百度站内搜索 -->
                	<jsp:include page="/common-html/search-in-baidu.jsp"></jsp:include>
                </div>
                <div class="bdh clearfix">
                    <div class="menu pull-left">
                        <a target="_blank" href="${ctx}discover/picturelist" class="ipic"><span class="sr-only">图说西藏</span></a>
                        <a target="_blank" href="${ctx}discover/videolist" class="ivideo"><span class="sr-only">高清视频</span></a>
                    </div>
                    <div class="slides-wrap pull-right"  ms-controller="view">
                        <div class="slides">
                            <div id="slide">
                                <c:forEach items="${banner}" var="banner" >
                                    <div class="slide-item">
                                        <a target="_blank"  href="${ctx}${banner.hyperlink}"><img src="${ctx}${banner.imgUrl}" alt="${banner.muti.name }" width="695" height="290"></a>
                                        <div class="item-inner">
                                            <p class="title"><a target="_blank" href="${ctx}${banner.hyperlinkl}" class="title">${banner.muti.name }</a></p>
                                            <p class="types">
                                                <span class="geye" title="查看数">${banner.praise.falseViewcount }</span>
                                                <%-- <span class="heart">${banner.praise.falseFavoriteNum }</span> --%>
                                            </p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="xz-box">
                    <div class="xz-heading">
                        <a target="_blank" href="${ctx}discover/picturelist"><h2 class="tus">
                       <span class="sr-only">图说西藏</span>
                        </h2></a>
                        <div class="index-more">
                            <a href="${ctx}discover/picturelist" target="_blank">更多 MORE</a>
                        </div>
                    </div>
                    <div class="xz-bd-wrap">
                        <div class="xz-bd clearfix">
                            <div class="xb-item big">
                                <a target="_blank" href="${ctx}${show[0].hyperlink}"><img src="${ctx}${show[0].imgUrl}" alt="${show[0].muti.name }" width="220" height="300"></a>
                                <div class="item-content">
                                    <div class="ic-inner">
                                        <div class="title">
                                            <a target="_blank" href="${ctx}${show[0].hyperlink}" class="js_sub_title" style="float: none;">
                                                ${show[0].muti.name }
                                            </a>
                                        </div>
                                        <div class="ic-text js_sub_summary">${show[0].muti.summary }</div>
                                    </div>
                                    <p class="types">
                                        <span class="heart" title="收藏数">${show[0].praise.falseFavoriteNum }</span>
                                    </p>
                                </div>
                            </div>
                            <div class="xb-item">
                                <a target="_blank" href="${ctx}${show[1].hyperlink}">
                                    <img src="${ctx}${show[1].imgUrl}" alt="${show[1].muti.name }" width="106" height="145"></a>
                                <div class="item-content">
                                    <div class="ic-inner">
                                        <div class="title">
                                            <a target="_blank" href="${ctx}${show[1].hyperlink}" class="js_sub_title"  style="float: none;" >
                                                ${show[1].muti.name }
                                            </a>
                                        </div>
                                        <div class="ic-text js_sub_summary2">${show[1].muti.summary }</div>
                                    </div>
                                    <p class="types">
                                        <span class="heart" title="收藏数">${show[1].praise.falseFavoriteNum }</span>
                                    </p>
                                </div>
                            </div>
                            <div class="xb-item">
                                 <a target="_blank" href="${ctx}${show[2].hyperlink}">
                                    <img src="${ctx}${show[2].imgUrl}" alt="${show[2].muti.name }" width="106" height="145"></a>
                                <div class="item-content">
                                    <div class="ic-inner">
                                        <div class="title">
                                            <a target="_blank" href="${ctx}${show[2].hyperlink}" class=" js_sub_title" style="float: none;">
                                                ${show[2].muti.name }
                                            </a>
                                        </div>
                                        <div class="ic-text js_sub_summary2">${show[2].muti.summary }</div>
                                    </div>
                                    <p class="types">
                                        <span class="heart" title="收藏数">${show[2].praise.falseFavoriteNum }</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="xz-box">
                    <div class="xz-heading">
                        <a target="_blank" href="${ctx}discover/videolist"><h2 class="ihv">
                          <span class="sr-only">西藏视频</span>
                        </h2></a> 
                        <div class="index-more">
                            <a target="_blank" href="${ctx}discover/videolist">更多 MORE</a>
                        </div>
                    </div>
                    <div class="xz-bd-wrap">
                        <div class="xz-bd xz-bd-video clearfix">
                            <c:if test="${empty videoList}"><div class="nodata"></div></c:if>
                            <c:forEach items="${videoList}" var="video">
                                <div class="item-video">
                                    <a target="_blank" href="${ctx }${video.url}"><img src="${video.title}" alt="${video.contentTitle }" width="280" height="180"><i class="play"></i></a>
                                    <div class="video-inner">
                                        <p class="title">${video.contentTitle }</p>
                                        <p class="types">
                                            <span class="geye"  title="查看数">${video.others.view }</span>
                                            <span class="heart" title="收藏数">${video.others.favorite }</span>
                                        </p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div class="bd-right">
                <div class="w-box" id="indexweather" style="text-align: center;overflow: hidden;"></div>
                <div class="bdl-box">
                    <div class="iv-box">
                        <div class="xz-heading">
                           <a target="_blank" href="${ctx}discover/picturelist"> <h2 class="iview">
                                 <span class="sr-only">最热图说</span>
                            </h2></a>
                        </div>
                        <div class="h-list">
                            <c:if test="${ empty hotMutiList}"><div class="nodata"></div></c:if>
                            <c:forEach items="${hotMutiList}" var="muti" varStatus="index">
                                <div class="media" >
                                    <div class="media-content">
                                        <a target="_blank" class="media-left" href="${ctx}${muti.hyperlink }">
                                            <img alt="${muti.name }" src="${ctx}${muti.coverUrl }" class="img-rounded" width="100" height="100">
                                            <span class="num num${index.count}"></span>
                                        </a>
                                        <div class="media-body">
                                            <h2 class="media-heading" style="font-size: 14px;"><a target="_blank" href="${ctx}${muti.hyperlink}">${muti.name }</a></h2>
                                            <p class="media-heading js_sub_summary3" style="font-size: 12px;">${muti.summary }</p>
                                            <div class="types">
                                                <span class="geye"  title="查看数">${muti.falseViewcount }</span>
                                                <span class="heart" title="收藏数">${muti.falseFavoriteNum }</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="xz-box">
                        <div class="xz-heading">
                              <a target="_blank" href="${ctx}discover/videolist"><h2 class="ivideo">
                                <span class="sr-only">最热视频</span>
                            </h2></a>
                        </div>
                        <div class="h-list video-list">
                            <c:if test="${ empty hotVideoList}"><div class="nodata"></div></c:if>
                            <c:forEach var="video" items="${hotVideoList}" varStatus="status">
                                <div class="media" >
                                    <div class="media-content">
                                        <a target="_blank" class="media-left" href="${ctx}${video.url}">
                                            <img alt="${video.contentTitle }" src="${video.title}" class="img-rounded" width="135" height="90">
                                            <span class="num num${status.count }"></span>
                                            <i class="play-min"></i>
                                        </a>
                                        <div class="media-body">
                                             <h2 class="media-heading" style="font-size: 14px;"><a target="_blank" href="${ctx}${video.url}">${video.contentTitle }</a></h2>
                                            <p class="media-heading js_sub_summary4" style="font-size: 8px;color: #898989;">${video.content }</p>
                                            <div class="types">
                                                <span class="geye"  title="查看数">${video.others.view }</span>
                                                <span class="heart" title="收藏数">${video.others.favorite }</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="${root}portal/headerFooterController/footer" flush="ture"/>
<script src="${ctx }/common-js/seajindex.js" type="text/javascript"></script>
<script type="text/javascript">
    seajs.use('jquery', function(){
        /**
         * 显示天气预报
         * @param divId
         * @param areaname 地区名称 （比如lasa,chengdu,）
         * @return
         */
        function  showCrWeather(divId,areaname){
            var  weather= '<iframe src="http://cache.xixik.com.cn/11/'+areaname+'/" width="255" height="20" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>';
            $("#"+divId).html(weather);
        }
        function   lucene(){
            $("#submit1").submit();
        }
        $(function(){
            showCrWeather("indexweather","lasa");
        });
    })
</script>
<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
</body>
</html>