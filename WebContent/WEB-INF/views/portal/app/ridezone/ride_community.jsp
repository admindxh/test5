<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化，西藏徒步，西藏攻略、西藏交流社区、西藏问答社区 " />
	<meta name="description" content=" 天上西藏社区-天上社区，帮助您解决西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化，西藏徒步，西藏攻略上面的问题，hold住最美回忆，西藏旅游攻略尽在天下西藏。" />
	<title>天上社区-天上西藏官网</title>
    <%@include file="/common-html/commonPortal.jsp"%>
    <link rel="stylesheet" href="${ctxPortal}/assets/css/ride-zone/ride_community.css" />
    <style>

		.driving .driving-info ul li {
			margin-top: 5px;
		}
		.driving .driving-info ul {
			width: 670px;
		}
    </style>
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxManage}/resources/plugin/sea.js"></script>
</head>
<body ms-controller="view">
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
	<!-- top -->
	<jsp:include page="${root}/ride/ridePropgram"></jsp:include>
<!-- Content -->
<div class="community container">
    <div class="location">
        <img src="${ctxPortal}/assets/icon/location_red.png"/>
        <span>当前位置:</span>
        <a href="${ctx}ride" target="_self">骑行专区</a>
        <img src="${ctxPortal}/assets/icon/arrow_right.png"/>
        <a href="${ctx}ride/rideCommunity" target="_self">骑行社区</a>
    </div>
    <h2 class="ride-header">骑行社区 RIDE COMMUNITY</h2>
    <div class="ride-info">
        
        <c:forEach var="obj" items="${list }">
	        <div class="media">
	            <div class="media-left">
	                <a target="_blank" href="${ctx}${obj.url}">
	                    <img src="${ctx}${obj.img}" />
	                </a>
	            </div>
	            <div class="media-body">
	                <h4 class="media-heading">${obj.progrmaName }(<span>${obj.postNum }</span>)</h4>
	                <p>${fn:length(obj.summary)>64?fn:substring(obj.summary,0,64):obj.summary}${fn:length(obj.summary)>64?'...':''}</p>
	                <span class="view-count">${obj.checkNum }</span>
	                <span class="reviews">${obj.replyNum }</span>
	            </div>
	            <c:if test="${not empty obj.content}">
		            <div class="recent-comments">
		                <%-- <p>${obj.content }</p> --%>
		                <p><a target="_blank" href="${ctx}${obj.postUrl }">${obj.postTitle }</a></p>
		                <div class="comment-time">
		                    <img width="30" height="30" src="${ctx}${obj.replyerPic}" alt="${obj.replyerName }"/>
		                    <span>${obj.replyerName }</span>
		                    <span>${obj.replyTime }</span>
		                </div>
		            </div>
	            </c:if>
	        </div>
        </c:forEach>
        
    </div>
</div><!-- Content End -->
<!-- footer -->
<jsp:include page="${root}/portal/headerFooterController/footer"></jsp:include>
<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
<script type="text/javascript" src="${ctx }/common-js/tssq.js"></script>
<script type="text/javascript">
    function adver(href, code) {
        frontContentStatic('3452871b-7422-33b2-b6de-349202605b2a', 'content', code, 'click');
        window.open(href);
    }
</script>
</body>
</html>