<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/mainfrontcommon.jsp" %>
<div class="container">
	<div class="xz-box">
		<div class="xz-heading">
			<a href="${ctx }culture/cultural" target="_blank"><h2 class="index-culture"><span class="sr-only">西藏文化传播</span></h2></a>
			<div class="clt-other">
				<a href="javascript:;" class="active">音乐</a>
				<a href="javascript:;">小说</a>
				<a href="javascript:;">游戏</a>
				<a href="${ctx }culture/cultural" target="_blank">更多</a>
			</div>
		</div>
		<div class="music clearfix">
            <c:forEach items="${list1}" var="list">
                <div class="music-bd">
                    <a href="${ctx }${list.url}" class="music-photo" target="_blank">
                        <img src="${ctx}${list.title}" alt="${fn:substring(list.authorCode,0,3)}">
                        <div class="mp-mark"></div>
                    </a>
                    <h4><p class="music-name"><a href="${ctx }${list.url}" target="_blank">${list.contentTitle}</a></p></h4>
                    <p class="music-singer" title="${list.authorCode }">
                        ${fn:substring(list.authorCode,0,3)}<c:if test="${fn:length(list.authorCode)>3}">...</c:if></p>
                    <p class="m-score" data-score="${list.others.score}" data-readonly="true"></p>
                </div>
            </c:forEach>
		</div>
		<div class="music clearfix" style="display: none;">
            <c:forEach items="${list2}" var="list">
                <div class="music-bd">
                    <a href="${ctx }${list.url}" class="music-photo" target="_blank">
                        <img src="${ctx}${list.title}" alt="${fn:substring(list.authorCode,0,3)}">
                    </a>
                    <h4><p class="music-name"><a href="${ctx }${list.url}" target="_blank">${list.contentTitle}</a></p></h4>
                    <p class="music-singer" title="${list.authorCode }">
                        ${fn:substring(list.authorCode,0,3)}<c:if test="${fn:length(list.authorCode)>3}">...</c:if></p>
                    <p class="m-score" data-score="${list.others.score}" data-readonly="true"></p>
                </div>
            </c:forEach>
        </div>
		<div class="music clearfix"  style="display: none;">
            <c:forEach items="${list3}" var="list">
                <div class="music-bd">
                    <a href="${ctx }${list.url}" class="music-photo" target="_blank"><img src="${ctx}${list.title}" alt="${list.contentTitle}"></a>
                    <h4><p class="music-name"><a href="${ctx }${list.url}" target="_blank">${list.contentTitle}</a></p></h4>
                    <p class="music-singer" title="${list.authorCode }">
                        ${fn:substring(list.authorCode,0,3)}<c:if test="${fn:length(list.authorCode)>3}">...</c:if></p>
                    <p class="m-score" data-score="${list.others.score}" data-readonly="true"></p>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}common-js/seajculture.js"></script>