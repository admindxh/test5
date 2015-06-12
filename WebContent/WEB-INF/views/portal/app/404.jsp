<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<% 
request.setAttribute("programNam", 100);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
    <meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
    <title>天上西藏-感受西藏魅力，体验西藏生活-天上西藏官网</title>
    <%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPortal}/assets/css/404.css"/>
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
                "common/css": "${ctxPortal}/assets/css/common.css",
                "footer/css": "${ctxPortal}/assets/css/footer.css"
            }
        });
    </script>
</head>
<body>
<!-- header -->
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"/>
<jsp:include page="${root}portal/headerFooterController/header" flush="ture"/>
<!-- content -->
<div class="main">
    <div class="container">
        <div class="lose404">
            <img src="${ctxPortal}/assets/icon/404.png"/>
        </div>
    </div>
</div>
<!-- footer -->
<jsp:include page="${root}portal/headerFooterController/footer" flush="ture"/>

<script>
    seajs.use(['common/css', 'footer/css']);
    seajs.use(['avalon'], function(avalon) {
        avalon.define({
            $id: "view"
        })
    });
</script>
</body>
</html>