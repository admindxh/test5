<%@ page language="java"  import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="udj" uri="/user-defined-jstl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="baidu-site-verification" content="XZCjPlgCRD" />
    <meta name="keywords" content="西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化"/>
    <meta name="description" content="天上西藏网为您提供全面靠谱的西藏旅游资讯，旅游攻略，西藏自助游，西藏自驾游，西藏骑行，全面权威的西藏文化介绍。打造权威的西藏文化旅游网。"/>
	<title>【天上西藏】西藏旅游-感受西藏魅力，体验西藏生活-天上西藏官网</title>
	<meta name="keywords" content="西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化，西藏徒步，西藏攻略，西藏音乐，西藏视频"/>
	<meta name="description" content="天上西藏网为您提供全面靠谱的西藏旅游资讯，包括旅游攻略，西藏自助游，西藏自驾游，西藏骑行，西藏徒步信息，全面权威的西藏文化介绍。打造权威的西藏文化旅游网。"/>
	<title>【天上西藏】西藏旅游-感受西藏魅力，体验西藏生活-天上西藏官网</title>
    <%@include file="/common-html/mainfrontcommon.jsp" %>
	<link rel="stylesheet" href="${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css" />
	<style>
		.overflow{height:400px; overflow:hidden;}
		.gallery-box .slick-list{height:400px; overflow:hidden;}
		.info>div.js_info>p{height: 35px !important;}
		.gl-info .jiange{margin-top: 10px !important;}
		.right-content .video-top p a{overflow: hidden;display: inline-block;text-overflow: ellipsis;white-space: nowrap;width: 100%;}
		.video-conter-all>p{height: 16px !important;}
		.right-content .video-top p{margin-top: 5px!important;}
		.right-content .video-conter{margin-bottom: 7px!important;}
		.xbo-bd h2,.media-heading{white-space: nowrap;overflow: hidden;text-overflow: ellipsis;}
		.media-heading{width:410px;}
		.xb-one a:hover, .read-bd a:hover{text-decoration: none!important;}
	</style>
	<!--[if lt IE 9]>
    <script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}modules/fix/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${ctx}common-js/maincommon.js"></script>
	<script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
        //Set configuration
        seajs.config({
            alias: {
                "jquery": "${ctxPortal}modules/jquery/jquery/1.11.1/jquery.js",
                "avalon":"${ctxPortal}modules/avalon/1.3.7/avalon.js",
                "bootstrap/css":"${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css",
                "common/css":"${ctxPortal}assets/css/common.css",
                "content/top/css":"${ctxPortal}assets/css/content_top.css",
                "youxz/css":"${ctxPortal}assets/css/youxz.css",
                "tusxz/css":"${ctxPortal}assets/css/tusxz.css",
                "duxz/css":"${ctxPortal}assets/css/duxz.css",
                "index_culture/css":"${ctxPortal}assets/css/index_culture.css",
                "footer/css":"${ctxPortal}assets/css/footer.css"
            }
        });
        seajs.use(['common/css', 'content/top/css', 'youxz/css','tusxz/css','duxz/css','index_culture/css','footer/css']);
    </script>
</head>
<body>
    <jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
	<!-- top -->
	<jsp:include page="/WEB-INF/views/portal/app/header/index.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/portal/app/banner/index.jsp"></jsp:include>
	<jsp:include page="${root}portal/app/index/index-search.html"></jsp:include>
	<jsp:include page="/WEB-INF/views/portal/app/index/content_top.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/portal/app/index/yxz.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/portal/app/index/tsxz.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/portal/app/index/duxz.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/portal/app/index/culture.jsp"></jsp:include>
    <!-- footer -->
	<jsp:include page="${root}/portal/headerFooterController/footer"/>
    <script type="text/javascript" src="${ctx}common-js/seajindex.js"></script>
    <jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
    <jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
</body>
</html>