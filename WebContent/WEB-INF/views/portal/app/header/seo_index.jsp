<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.rimi.ctibet.domain.LogUser" session="true"%>
<%@include file="/common-html/mainfrontcommon.jsp"%>
<%  LogUser lg=(LogUser)request.getSession().getAttribute("logUser");
    request.setAttribute("lgUser",lg);
    String ip = request.getHeader("X-Real-IP");
    request.setAttribute("ip",ip);
 %>
<div class="navbar cb-index-nav navbar-static-top" id="top" role="banner">
    <div class="container">
        <div class="navbar-header">
            <strong class="logo">
                <a href="${ctx}" class="navbar-brand" style="width: 200px;"><img src="${ctxPortal }assets/icon/logo2.png" width="200" height="60"></a>
                <span class="sr-only">天上西藏</span>
            </strong>
        </div>
        <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
            <ul class="nav navbar-nav navbar-left" id="topNavbar">
                <c:forEach items="${programaList}" var="program" varStatus="status">
                    <%-- <li  
                     <c:if test="${program.available==0 }">style="display:none;"</c:if>
                    ><a class="head-menu" href="${ctxIndex}/${program.programaUrl}">${program.programaName}</a></li> --%>
                   	<c:choose>
                   		<c:when test="${ip eq '125.70.10.34' && program.code eq '797a0b1e-760a-11e4-b6ce-005056a05bc9' }">
                   			<c:set var="title_display" value=""/>
                   		</c:when>
                   		<c:when test="${ip eq '183.223.252.42' && program.code eq '797a0b1e-760a-11e4-b6ce-005056a05bc9' }">
                   			<c:set var="title_display" value=""/>
                   		</c:when>
                   		<c:when test="${program.code eq '797a0b1e-760a-11e4-b6ce-005056a05bc9'}">
                   			<c:set var="title_display" value="display:none;"/>
                   		</c:when>
                   		<c:otherwise>
                   			<c:set var="title_display" value=""/>
                   		</c:otherwise>
                   	</c:choose>
                   	<li style="${title_display }"><a  class="head-menu" href="${ctxIndex}/${program.programaUrl}">${program.programaName}</a></li>
                </c:forEach>
                <li id="floating"></li>
            </ul>
            <ul class="nav navbar-nav navbar-right logged-on">
                <c:choose>
                    <c:when test="${empty lgUser}">
                        <li><a href="<%=basePath %>portal/registerController/register" target="_blank">注册</a></li>
                        <li><a href="#loginModal" data-toggle="login">登录</a></li>
                        <li><a href="#loginModal"  id="fav" class="fav" title="收藏本页"></a></li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <span class="badge" style="display: none;"></span>
                            <i class="umark"></i>
                            <img width="40px" height="40px"
                                <c:if test="${empty lgUser.pic}"><c:choose>
                                    <c:when test="${lgUser.sex eq '1'}">src="${ctxPortal}static/default/male.jpg"</c:when>
                                    <c:otherwise>src="${ctxPortal}static/default/female.jpg"</c:otherwise>
                                </c:choose></c:if>
                                <c:if test="${not empty lgUser.pic}"><c:choose>
                                    <c:when test="${lgUser.sex eq '0'&& lgUser.pic eq '/portal/static/default/male.jpg'}">src="${ctxPortal}static/default/female.jpg"</c:when>
                                    <c:otherwise>src="<%=basePath%>${lgUser.pic }"</c:otherwise>
                                </c:choose></c:if>  alt="用户头像"/>
                        </li>
                        <li><a href="javascript:void(0);" onclick="toUserCenter();" class="unameClass">${lgUser.username}</a></li>
                        <li><a href="javascript:void(0);" onclick="logout();">退出</a></li>
                        <li><a href="javascript:;" id="fav" class="fav" title="收藏本页"></a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </div>
    <div id="tags"
    <c:if test="${programNam==1||programNam==0||programNam==4}">style="display: block;"</c:if>
    <c:if test="${programNam!=1}">style="display: none;"</c:if>
    >
        <div class="tags-mark"></div>
        <div class="container">
            <ul class="list-unstyled">
                <li>
                    <c:forEach items="${destinationList}" var="des">
                        <a href="${ctxIndex}${des.linkUrl}" target="_blank">${des.destinationName}</a>
                    </c:forEach>
                </li>
            </ul>
        </div>
    </div>
</div>
<jsp:include page="${root}/portal/headerFooterController/window" flush="true"/>
<script>
    var programNam = "${programNam}";
    var logUser  = "${logUser}";
</script>
<script src="${ctx}common-js/heard.js" type="text/javascript"></script>