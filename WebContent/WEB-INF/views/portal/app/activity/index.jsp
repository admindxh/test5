<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="author" content="quansenx" />
    <title>西藏活动_西藏骑行活动_西藏-天上西藏官网</title>
	<meta name="keywords" content="西藏活动，天上西藏活动，西藏摄影比赛，天上西藏官网活动，天上西藏"/>
	<meta name="description" content="西藏活动为您组织真实可靠的西藏旅行活动、关注西藏活动专栏，获取最新的西藏活动信息，天上西藏打造权威的西藏旅行网站，欢迎访问天上西藏网站！"/>
    <%@ include file="/common-html/mainfrontcommon.jsp" %>
    <link rel="stylesheet" href="${ctxPortal }modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <!--[if lt IE 9]>
    <script src="${ctxPortal }modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal }modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
        // Set configuration
        seajs.config({
            alias: {
                "jquery": "jquery/jquery/1.11.1/jquery.js",
                "avalon": "avalon/1.3.7/avalon.js",
                "common/css": "${ctxPortal }/assets/css/common.css",
                "activity/css": "${ctxPortal }/assets/css/activity/activity.css",
                "footer/css": "${ctxPortal }/assets/css/footer.css",
                "paginator":"paginator/0.5/bootstrap-paginator.js"
            }
        });
    </script>
</head>
<body>
<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
<jsp:include page="${root}/portal/headerFooterController/header"/>
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
<div class="container">
    <!-- slide -->
    <div class="ac-slide-wrap">
        <div class="ac-slide">
            <c:forEach var="obj" varStatus="sta" items="${listBannerImage }">
                <div class="acs-item">
                    <a target="_blank" href="${not empty obj.hyperlink?obj.hyperlink:'javascript:void(-1);'}"><img src="${ctx}${obj.url}"></a>
                </div>
            </c:forEach>
            <!-- control -->
            <div class="acs-mark"></div>
            <div class="acs-thumb">
                <c:forEach var="obj" varStatus="sta" items="${listBannerImage }">
                    <c:if test="${sta.index==0 }">
                        <c:set value="active" var="active" />
                    </c:if>
                    <c:if test="${sta.index!=0 }">
                        <c:set value="" var="active" />
                    </c:if>
                    <div class="thumb-item ${active }">
                        <a href="${obj.hyperlink }" target="_blank" class="thumb-img">
                            <span class="thumb-mark"></span>
                            <img src="${ctx}${obj.url}"></a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <!-- slide end -->
    <div class="bd">
        <div class="clearfix">
            <!-- 全部活动 -->
            <div class="full-event pull-left" ms-important="allActivityView">
                <h1 class="ac-title">全部活动</h1>
                <div class="fe-list">
                    <div class="media" ms-repeat="data" ms-attr-class="one:$first">
                        <a target="_blank" class="media-left" ms-href="${ctx}{{el.linkUrl}}">
                            <i ms-attr-class="{{ el.activityState=='进行中'?'underway':'finished'}}"></i>
                            <img style="width: 140px; height: 101px;" ms-attr-alt="el.name" ms-src="${ctx}{{el.img}}"></a>
                        <div class="media-body">
                            <h2 class="media-heading"><a target="_blank" ms-href="${ctx}{{el.linkUrl}}" style="color: #333;">{{el.name}}</a></h2>
                            <p>{{el.summary | clearHTML | html}}</p>
                        </div>
                        <div class="media-right">
                            <a target="_blank" ms-href="${ctx}{{el.linkUrl}}" class="btn">{{el.activityState=='进行中'?'立即参加':'查看活动'}}</a>
                            <p><span class="look">{{el.fakeCheckNum}}</span></p>
                        </div>
                    </div>
                    <div class="nodata" ms-visible="!data.size()"></div>
                </div>
                <div id="allActivityPage" class="paging paging-center paging-lg paging-white"></div><!-- 分页按钮 -->
            </div>
            <div class="join pull-right">
                <h2 class="join-title">最新参与</h2>
                <div class="user-list">
                    <c:if test="${empty listMemberEnrollDetailVO}">
                        <div class="nodata"></div>
                    </c:if>
                    <c:forEach var="obj" items="${listMemberEnrollDetailVO }">
                        <div class="media">
                            <a target="_blank" class="media-left" href="${ctx}member/show/${obj.memberCode}.html">
                                <span class="user-mark"></span>
                                <img alt="${obj.activityName}" width="68" height="67" src="${ctx}${obj.pic}">
                            </a>
                            <div class="media-body">
                                <c:if test="${obj.sex==0 }">
                                    <c:set var="sex_" value="j-female" />
                                </c:if>
                                <c:if test="${obj.sex==1 }">
                                    <c:set var="sex_" value="j-male" />
                                </c:if>
                                <h2 class="media-heading"><i class="${sex_}"></i>&nbsp;<a target="_blank" href="${ctx}member/show/${obj.memberCode}.html">${obj.memberName }</a></h2>
                                <p><fmt:formatDate value="${obj.enrollTime }" type="both" pattern="yyyy-MM-dd" /> 参加了</p>
                                <h3>${obj.activityName}</h3>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="ac-topic">
            <div class="xz-box">
                <div class="xz-heading">
                    <a href="${ctx}activity/more" target="_blank"><h2 class="topic-bg">
                        <span class="sr-only">专题TOPIC</span>
                    </h2></a>
                    <div class="index-more">
                        <a href="${ctx}activity/more" target="_blank">更多 MORE</a>
                    </div>
                </div>
                <div class="tp-gallery-wrap">
                    <a href="#" class="prev" id="J_prev"></a>
                    <a href="#" class="next" id="J_next"></a>
                    <div class="tp-gallery">
                        <c:forEach var="obj" varStatus="sta" items="${listSpecailShowImage }">
                            <div class="tpg-img"><a target="_blank" href="${not empty obj.hyperlink?obj.hyperlink:'javascript:;' }"><img src="${ctx}${obj.url}" width="300" height="180"></a></div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="${root}/portal/headerFooterController/footer"/>
<script type="text/javascript" src="${ctx }/common-js/activity.js"></script>
</body>
</html>