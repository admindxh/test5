<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/mainfrontcommon.jsp" %>
<%@taglib prefix="rimi" uri="/rimi-tags" %>
<%@taglib prefix="udj" uri="/user-defined-jstl" %>
<style>
	.loving hover:HOVER{
	  cursor: pointer;
	}
</style>
<div class="container tourist">
    <div class="row">
        <div class="col-xs-9 tourist-left">
            <div class="header xz-heading">
               <a href="${ctx }tourism/view/forView" target="_blank"> <h2 class="left"><span class="sr-only">热门景点</span></h2></a>
                <div class="right"><a href="${ctx }tourism/view/forView" target="_blank">更多MORE</a></div>
            </div>
            <div class="content">
                <div class="clearfix content-top">
                    <div>
                        <a href="${ctx }${views[0].linkUrl}" target="_blank"><img
                                <c:if test="${not  empty  views[0].img }">src="${ctxIndex}${views[0].img}"</c:if>
                                <c:if test="${empty  views[0].img }">src="${ctx}portal/static/default/square.png"</c:if>
                                alt="${views[0].viewName}"/></a>
                        <div class="img-info">
                            <div class="scene-name"><a href="${ctx }${views[0].linkUrl}" target="_blank">${views[0].viewName}</a></div>
                            <div class="scene-go">
                                <p>去过：<span>${views[0].fakeGoneCount}</span></p>
                                <p>想去：<span>${views[0].fakeWantCount}</span></p>
                            </div>
                        </div>
                    </div>
                    <div>
                        <a href="${ctx }${views[1].linkUrl}" target="_blank"><img
                                <c:if test="${not  empty  views[1].img }">src="${ctxIndex}${views[1].img}"</c:if>
                                <c:if test="${empty  views[1].img }">src="${ctx}portal/static/default/square.png"</c:if>
                                alt="${views[1].viewName}"/></a>
                        <div class="img-info img-info-top-2">
                            <div class="scene-name"><a href="${ctx }${views[1].linkUrl}" target="_blank">${views[1].viewName}</a></div>
                            <div class="scene-go">
                                <p>去过：<span>${views[1].fakeGoneCount}</span></p>
                                <p>想去：<span>${views[1].fakeWantCount}</span></p>
                            </div>
                        </div>
                    </div>
                    <div>
                        <a href="${ctx }${views[2].linkUrl}" target="_blank"><img
                                <c:if test="${not  empty  views[2].img }">src="${ctxIndex}${views[2].img}"</c:if>
                                <c:if test="${empty  views[2].img }">src="${ctx}portal/static/default/square.png"</c:if>
                                alt="${views[2].viewName}"/></a>
                        <div class="img-info img-info-top-3">
                            <div class="scene-name"><a href="${ctx }${views[2].linkUrl}" target="_blank">${views[2].viewName}</a></div>
                            <div class="scene-go">
                                <p>去过：<span>${views[2].fakeGoneCount}</span></p>
                                <p>想去：<span>${views[2].fakeWantCount}</span></p>
                            </div>
                        </div>
                    </div>
                    <div>
                        <a href="${ctx }${views[3].linkUrl}" target="_blank"><img
                                <c:if test="${not  empty  views[3].img }">src="${ctxIndex}${views[3].img}"</c:if>
                                <c:if test="${empty  views[3].img }">src="${ctx}portal/static/default/square.png"</c:if>
                                alt="${views[3].viewName}"/></a>
                        <div class="img-info img-info-top-4">
                            <div class="scene-name"><a href="${ctx }${views[3].linkUrl}" target="_blank">${views[3].viewName}</a></div>
                            <div class="scene-go">
                                <p>去过：<span>${views[3].fakeGoneCount}</span></p>
                                <p>想去：<span>${views[3].fakeWantCount}</span></p>
                            </div>
                        </div>
                    </div>
                    <div>
                        <a href="${ctx }${views[4].linkUrl}" target="_blank"><img
                                <c:if test="${not  empty  views[4].img }">src="${ctxIdex}${views[4].img}"</c:if>
                                <c:if test="${empty  views[4].img }">src="${ctx}portal/static/default/square.png"</c:if>
                                alt="${views[4].viewName}"/></a>
                        <div class="img-info img-info-top-5">
                            <div class="scene-name"><a href="${ctx }${views[4].linkUrl}" target="_blank">${views[4].viewName}</a></div>
                            <div class="scene-go">
                                <p>去过：<span>${views[4].fakeGoneCount}</span></p>
                                <p>想去：<span>${views[4].fakeWantCount}</span></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearfix content-top content-footer">
                    <div>
                        <a href="${ctx }${views[5].linkUrl}" target="_blank"><img
                                <c:if test="${not  empty  views[5].img }">src="${ctxIndex}${views[5].img}"</c:if>
                                <c:if test="${empty  views[5].img }">src="${ctx}portal/static/default/square.png"</c:if>
                                alt="${views[5].viewName}"/></a>
                        <div class="img-info img-info-bottom-1">
                            <div class="scene-name"><a href="${ctx }${views[5].linkUrl}" target="_blank">${views[5].viewName}</a></div>
                            <div class="scene-go">
                                <p>去过：<span>${views[5].fakeGoneCount}</span></p>
                                <p>想去：<span>${views[5].fakeWantCount}</span></p>
                            </div>
                        </div>
                    </div>
                    <div>
                        <a href="${ctx }${views[6].linkUrl}" target="_blank"><img
                                <c:if test="${not  empty  views[6].img }">src="${ctxIndex}${views[6].img}"</c:if>
                                <c:if test="${empty  views[6].img }">src="${ctx}portal/static/default/square.png"</c:if>
                                alt="${views[6].viewName}"/></a>
                        <div class="img-info img-info-bottom-2">
                            <div class="scene-name"><a href="${ctx }${views[6].linkUrl}" target="_blank">${views[6].viewName}</a></div>
                            <div class="scene-go">
                                <p>去过：<span>${views[6].fakeGoneCount}</span></p>
                                <p>想去：<span>${views[6].fakeWantCount}</span></p>
                            </div>
                        </div>
                    </div>
                    <div>
                        <a href="${ctx }${views[7].linkUrl}" target="_blank"> <img
                                <c:if test="${not  empty  views[7].img }">src="${ctxIndex}${views[7].img}"</c:if>
                                <c:if test="${empty  views[7].img }">src="${ctx}portal/static/default/square.png"</c:if>
                                alt="${views[7].viewName}"/></a>
                        <div class="img-info img-info-bottom-3">
                            <div class="scene-name"><a href="${ctx }${views[7].linkUrl}" target="_blank">${views[7].viewName}</a></div>
                            <div class="scene-go">
                                <p>去过：<span>${views[7].fakeGoneCount}</span></p>
                                <p>想去：<span>${views[7].fakeWantCount}</span></p>
                            </div>
                        </div>
                    </div>
                    <div>
                        <a href="${ctx }${views[8].linkUrl}" target="_blank"><img
                                <c:if test="${not  empty  views[8].img }">src="${ctxIndex}${views[8].img}"</c:if>
                                <c:if test="${empty  views[8].img }">src="${ctx}portal/static/default/square.png"</c:if>
                                alt="${views[8].viewName}"/></a>
                        <div class="img-info img-info-bottom-4">
                            <div class="scene-name"><a href="${ctx }${views[8].linkUrl}" target="_blank">${views[8].viewName}</a></div>
                            <div class="scene-go">
                                <p>去过：<span>${views[8].fakeGoneCount}</span></p>
                                <p>想去：<span>${views[8].fakeWantCount}</span></p>
                            </div>
                        </div>
                    </div>
                    <div>
                        <a href="${ctx }${views[9].linkUrl}" target="_blank"><img
                                <c:if test="${not  empty  views[9].img }">src="${ctxIndex}${views[9].img}"</c:if>
                                <c:if test="${empty  views[9].img }">src="${ctx}portal/static/default/square.png"</c:if>
                                alt="${views[9].viewName}"/></a>
                        <div class="img-info img-info-bottom-5">
                            <div class="scene-name"><a href="${ctx }${views[9].linkUrl}" target="_blank">${views[9].viewName}</a></div>
                            <div class="scene-go">
                                <p>去过：<span>${views[9].fakeGoneCount}</span></p>
                                <p>想去：<span>${views[9].fakeWantCount}</span></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-3 tourist-right">
            <div class="header xz-heading">
                <a href="${ctx}${merchantList[0].mUrl}" target="_blank"><h2 class="left"><p>
                    <c:if test="${empty  merchantList[0].mType}">暂无</c:if>
                    <c:if test="${not empty  merchantList[0].mType}">${merchantList[0].mType}</c:if>
                </p></h2></a>
                <div class="right"><a href="${ctx}${merchantList[0].mUrl}" target="_blank">更多MORE</a></div>
            </div>
            <c:if test="${empty  merchantList[0].merchantList}">
                <img src="${ctxPortal}static/images/no-recommendation.png"/>
            </c:if>
            <c:if test="${not  empty  merchantList[0].merchantList}">
                <c:forEach items="${merchantList[0].merchantList}"  var="m" varStatus="status">
                    <div class="content">
                        <h3 class="h3-fz"><a href="${ctx }${m.url}" target="_blank">${m.merchantName}</a></h3>
                        <div class="media message">
                            <a class="media-left" href="${ctx }${m.url}" target="_blank">
                                <div>
                                    <img src="${ ctxIndex}${m.merchantImage}" alt="${m.merchantName}"/>
                                    <c:if test="${m.isRecommend==1}"><img class="ctibet" src="${ctxPortal}assets/icon/ctibet_yxz.png"/></c:if>
                                </div>
                            </a>
                            <div class="media-body">
                                <div class="mer-info">
                                    <span>价格：${empty m.price || "0" eq m.price ? "暂无" : m.price }</span>
									<p mcode="${m.code}" class="mer-score" data-score="5" data-readonly="true"></p>
                                </div>
                            </div>
                        </div>
                        <div class="line"></div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</div>
<script>seajs.use('${ctxPortal}assets/css/tourism/tourist.css');</script>