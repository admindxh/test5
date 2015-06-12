<%@ page language="java" import="java.util.*" pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String port = ":" + request.getServerPort();
    if ("80".equals(""+request.getServerPort())) {
        port = "";
    }
    String basePath = request.getScheme() + "://" + request.getServerName() + port + path + "/";
    request.setAttribute("ctx",basePath);
    request.setAttribute("ctxPortal",basePath+"portal/"); //xiangwq
    request.setAttribute("ctxApp",basePath+"portal/app/");
    request.setAttribute("root","/");
%>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏历史，西藏节日，西藏风俗，西藏特产，西藏宗教，西藏名人，西藏艺术，西藏美食，西藏地理，西藏服饰 " />
	<meta name="description" content="读西藏专栏为您提供西藏节日，西藏历史，西藏风俗，西藏特产，西藏宗教，西藏名人，西藏美食和西藏服务的具特色的名族文化知识。 " />
    <title>读西藏历史_西藏节日_西藏特产_天上西藏官网</title>
    <link rel="stylesheet" href="${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
    <script type="text/javascript" src="${ctxPortal}modules/jquery/jquery/1.11.1/jquery.js"></script>
    <script>
        seajs.config({
            alias: {
                "jquery": "${ctxPortal}modules/jquery/jquery/1.11.1/jquery.js",
                "avalon":"${ctxPortal}modules/avalon/1.3.7/avalon.js",
                "header":"${ctxPortal}assets/css/common.css",
                "content/top/css":"${ctxPortal}assets/css/content_top.css",
                "footer/css":"${ctxPortal}assets/css/footer.css"
            }
        });
    </script>
    <style>
        .written-message h5 a{
            font-weight: 700;
        }
        .festiva-info p{
            color: #898989;
        }
    </style>
</head>
<body ms-controller="indexView">
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"/>
<jsp:include page="${root}/portal/headerFooterController/header"/>
<jsp:include page="${root}/portal/app/banner/culture_banner.html"></jsp:include>
<jsp:include page="${root}portal/app/index/index-search.html"></jsp:include>
<%@include file="culture/taste.jsp" %>
<jsp:include page="${root}/portal/app/culture/culture.html?type=${type}"></jsp:include>
<jsp:include page="${root}portal/headerFooterController/footer" flush="ture"/>
<script>
    // Set configuration
    seajs.use(['footer/css','header','content/top/css']);
    seajs.use('avalon', function(avalon){
        avalon.define({
            $id:"indexView",
            showTag:true
        });
        $(function(){
            avalon.scan();
        });
    });
</script>
<script src="${ctx}/common-js/seajindex.js" type="text/javascript">
</script>
<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
</body>
</html>