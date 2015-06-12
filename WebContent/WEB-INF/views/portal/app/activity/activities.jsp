<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/commonTag.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <!-- <meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
    <meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
    <title>活动列表</title> -->
    <title>西藏活动-天上西藏官网</title>
	<meta name="keywords" content="西藏活动，天上西藏活动，西藏摄影比赛，天上西藏官网活动，天上西藏"/>
	<meta name="description" content="西藏活动为您提供最权威、最新的各种西藏相关活动，期待您的关注，了解更多西藏旅游资讯，欢迎访问天上西藏网站！"/>
    
	<%-- <%@ include file="/common-html/frontcommon.jsp" %> --%>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
    seajs.config({
        alias: {
			"jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js",
			"avalon": "${ctxPortal}/modules/avalon/1.3.7/avalon.js",
			"paginator":"${ctxPortal}/modules/paginator/0.5/bootstrap-paginator.js",
            "activities/css":"${ctxPortal}/assets/css/activity/activities.css",
            "common/css": "${ctxPortal}/assets/css/common.css",
            "footer/css": "${ctxPortal}/assets/css/footer.css",
            "activities/js":"${ctxPortal}/modules/assets/js/activity/activities.js",
			"commonjs":"${ctx}common-js/common.js",
		    "dateUtil":"${ctx }/common-js/DateUtil.js"
        }
    });
    </script>
</head>
<body>
<jsp:include page="${root}/portal/headerFooterController/header"/>
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
<div class="container activities">
    <div class="activities-banner"><img src="${ctx}portal\static\images\banner_activities.png"/></div>
    <div class="clearfix activities-list">
        <div class="list-btn">
            <span id="activity" class="activity-inactive"></span><span id="subject" class="subject-active"></span>
        </div>
        <div class="sort">
            <label>排序方式：</label>
            <span id="latest-release" class="active">最新发布</span>
            <span id="hot">按热度</span>
        </div>
    </div>
    <!-- 活动列表 -->
    <!-- ${ctx}portal/activityController/forActivityDetail?code={{el.code}} -->
    <div id="activity-content" class="activity-none" ms-important="allActivityView">
    
        <div class="clearfix act-content">
            <div ms-repeat="data" ms-class="one:$first">
                <a target="_blank" ms-attr-href="${ctx}{{el.linkUrl}}"><img width="317" height="229" ms-src="${ctx}{{el.img}}" ms-attr-alt="el.name"/></a>
                <div class="clearfix info">
                    <p><a target="_blank" style="color: #333" ms-href="${ctx}{{el.linkUrl}}"><span>{{el.name}}</span></a><span class="activity-time">截止时间：{{ el.endDate|date("yyyy/MM/dd") }}</span></p>
                    <div>{{el.fakeCheckNum}}</div>
                    <img ms-if="el.activityState=='进行中'" class="active-state" src="${ctxPortal}/assets/icon/underway.png"/>
                    <img ms-if="el.activityState=='已结束'" class="active-state" src="${ctxPortal}/assets/icon/finish.png"/>
                </div>
            </div>
        </div>
        
        <!-- 分页 -->
    	<div id="allActivityPage" class="paging paging-center paging-lg page"><!-- 分页按钮 --></div>
    </div>
    
    <!-- 专题列表 -->
    <!-- ${ctx}portal/specialController/forSpecialDetail?code={{el.code}}-->
    <div id="subject-content" class="activity-block" ms-important="allSpecialView">
    
        <div class="clearfix act-content" >
            <div ms-repeat="data" ms-class="one:$first">
                <a target="_blank" ms-attr-href="${ctx}{{el.url}}"><img width="317" height="229" ms-src="${ctx}{{ el.tag }}" ms-attr-alt="el.name"/></a>
                <div class="clearfix info">
                    <p><a target="_blank" style="color: #333" ms-href="${ctx}{{el.url}}"><span>{{ el.name }}</span></a><span>{{el.summary|clearHTML|html|truncate(15,'...')}}</span></p>
                    <div>{{ el.falseViewcount }}</div>
                </div>
            </div>
        </div>
        
    	<!-- 分页 -->
    	<div id="allSpecialPage" class="paging paging-center paging-lg page"><!-- 分页按钮 --></div>
    </div>
    
</div>
<jsp:include page="${root}/portal/headerFooterController/footer"/>

<script type="text/javascript">
	var basePath_ = "${ctx}";
	var ORDERBY_DATE_ = ${ORDERBY_DATE};
	var ORDERBY_CHECKNUM_ = ${ORDERBY_CHECKNUM};
</script>
<script type="text/javascript" src="${ctx}portal/assets/js/activity/activities_js.js"></script>

</body>
</html>