<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
request.setAttribute("programNam",100);
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="author" content="quansenx">
	<meta name="keywords" content="${content.keywords}" />
	<meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
	<title>西藏文化转播详情页面_天上西藏</title>
	<%@include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctxPortal}assets/css/common.css">
	<link rel="stylesheet" href="${ctxPortal}assets/css/footer.css">
	<!--[if lt IE 9]>
	<script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
	<script src="${ctxPortal}modules/fix/respond.min.js"></script>
	<![endif]-->
	<script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
	<style>
	    .main{
	    	margin: 0 auto!important;
	    }
	    .footer{margin-top: 0!important;}
	    .container img{
	    	max-width: 100%;
				height: auto;
	    }
    </style>

<script type="text/javascript">
seajs.config({
			alias: {
				"jquery": "jquery/jquery/1.11.1/jquery.js",
				"avalon": "avalon/1.3.7/avalon.js",
				"common/css": "${ctxPortal}assets/css/common.css",
				"footer/css": "${ctxPortal}assets/css/footer.css"
			}
		});
</script>
</head>
<body>
<jsp:include page="${root}/portal/headerFooterController/header?seo_index=seo_index"/>
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
<div class="main">
	<div class="container" style="margin-top: 60px; padding-bottom: 30px;word-break: break-all;word-wrap: break-word;min-height: 550px;">
	   ${content.content}
	</div>
</div>
<jsp:include page="/WEB-INF/views/portal/app/footer/index.jsp"></jsp:include>

</body>
</html>