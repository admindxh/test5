<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jstl/fmt_rt" %>   
<%
	String path = request.getContextPath();
	String port = ":" + request.getServerPort();
	if ("80".equals(""+request.getServerPort())) {
		port = "";
	}
	String basePath = request.getScheme() + "://" + request.getServerName() + port + path + "/";
    request.setAttribute("ctx",basePath);
    request.setAttribute("ctxPortal",basePath+"/portal/");
    request.setAttribute("root","/");
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏"/>
    <meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！"/>
    <title>支付</title>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPortal}/assets/css/activity/pay_for_success.css"/>
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
        // Set configuration
        seajs.config({
            alias: {
                "jquery": "jquery/jquery/1.11.1/jquery.js",
                "avalon": "avalon/1.3.7/avalon.js",
                "common/css": "${ctxPortal }/assets/css/common.css",
                "footer/css": "${ctxPortal }/assets/css/footer.css"
            }
        });
        seajs.use(['common/css', 'footer/css']);
    </script>
</head>
<body>
<!-- header -->
<jsp:include page="${root}/portal/headerFooterController/header"/>
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
<!-- content -->
<div class="content">
    <div class="container">
        <div class="pay-success">
        	<c:if test="${type eq 'success'}">
	            <img src="${ctxPortal}/assets/icon/Pay_for_success.png" />
        	</c:if>
        	<c:if test="${type eq 'failure'}">
	            <img src="${ctxPortal}/assets/icon/Pay_for_failure.png" />
        	</c:if>
            <a target="_blank" class="return_activity" href="${ctx }portal/activityController/forActivityDetail?code=${activityCode }"><img src="${ctxPortal}/assets/icon/return_activity.png"></a>
        </div>
    </div>
</div>
<!-- footer -->
<jsp:include page="${root}/portal/headerFooterController/footer"/>
</body>
</html>