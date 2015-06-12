<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common-html/mainfrontcommon.jsp" %>
<%@taglib uri="/rimi-tags"  prefix="rimi"%>
<%@taglib prefix="udj" uri="/user-defined-jstl" %>
<script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
<div class="container guide">
    <div class="row">
        <div class="col-xs-9 guide-left">
            <div class="header xz-heading">
              <a href="${ctx }tourism/strage/intoTraval/c1d94bbe-792e-11e4-b6ce-005056a05bc9.html" target="_blank">  <h2 class="left"><span class="sr-only">综合攻略</span></h2></a>
                <div class="right">
                    <a href="${ctx }tourism/strage/intoTraval/c1d94bbe-792e-11e4-b6ce-005056a05bc9.html" target="_blank">更多MORE</a>
                </div>
            </div>
            <div class="guide-left-img-top clearfix">
                <div class="img-top-left">
                    <a href="${zhContent[0].visitUrl}" target="_blank"><img src="${ctxIndex}${zhContent[0].imgrurl}" alt="${zhContent[0].title}"/></a>
                    <div class="info">
                        <p><a href="${zhContent[0].visitUrl}" target="_blank">${zhContent[0].title}</a></p>
                        <div>
                            <img src="${ctxPortal}assets/icon/eye_white.png" title="查看数"/><span>${zhContent[0].viewcount}</span>
                            <%-- <img src="${ctxPortal}/assets/icon/star_white.png" title="收藏数"/><span>${zhContent[0].favotecount}</span> --%>
                        </div>
                    </div>
                </div>
                <div class="img-top-right">
                    <a href="${zhContent[1].visitUrl}" target="_blank"><img src="${ctxIndex}${zhContent[1].imgrurl}" alt="${zhContent[1].title}"/></a>
                    <div class="info">
                        <p><a href="${zhContent[1].visitUrl}" target="_blank">${zhContent[1].title}</a></p>
                        <div>
                            <img src="${ctxPortal}assets/icon/eye_white.png" title="查看数"/><span>${zhContent[1].viewcount}</span>
                            <%-- <img src="${ctxPortal}/assets/icon/star_white.png" title="收藏数"/><span>${zhContent[1].favotecount}</span> --%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="guide-left-img-top guide-left-img-footer clearfix">
                <div class=" img-top-right img-footer-left">
                    <a href="${zhContent[2].visitUrl}" target="_blank"><img src="${ctxIndex}${zhContent[2].imgrurl}" alt="${zhContent[2].title}"/></a>
                    <div class="info">
                        <p><a href="${zhContent[2].visitUrl}" target="_blank">${zhContent[2].title}</a></p>
                        <div>
                            <img src="${ctxPortal}assets/icon/eye_white.png" title="查看数"/><span>${zhContent[2].viewcount}</span>
                            <%-- <img src="${ctxPortal}/assets/icon/star_white.png" title="收藏数"/><span>${zhContent[2].favotecount}</span> --%>
                        </div>
                    </div>
                </div>
                <div class="img-top-left img-footer-right">
                    <a href="${zhContent[3].visitUrl}" target="_blank"><img src="${ctxIndex}${zhContent[3].imgrurl}" alt="${zhContent[3].title}"/></a>
                    <div class="info">
                        <p><a href="${zhContent[3].visitUrl}" target="_blank">${zhContent[3].title}</a></p>
                        <div>
                            <img src="${ctxPortal}assets/icon/eye_white.png" title="查看数"/><span>${zhContent[3].viewcount}</span>
                            <%-- <img src="${ctxPortal}/assets/icon/star_white.png" title="收藏数"/><span>${zhContent[3].favotecount}</span> --%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-3 guide-right">
            <div class="header header-right xz-heading">
                <a href="${ctx }${merchantList[1].mUrl}" target="_blank"><h2 class="left"><p><c:if test="${empty  merchantList[1].mType}">暂无</c:if>
                    <c:if test="${not empty  merchantList[1].mType}">${merchantList[1].mType }</c:if></p></h2></a>
                <div class="right"><a href="${ctx }${merchantList[1].mUrl}" target="_blank">更多MORE</a></div>
            </div>
            <c:if test="${empty  merchantList[1].merchantList}">
                <img src="${ctxPortal}static/images/no-recommendation.png"/>
            </c:if>
            <c:if test="${not  empty  merchantList[1].merchantList}">
                <c:forEach items="${merchantList[1].merchantList}"  var="m" varStatus="status">
                    <div class="content">
                        <h3 class="h3-fz"><a href="${ctx}${m.url}" target="_blank">${m.merchantName}</a></h3>
                        <div class="media message">
                            <a class="media-left" href="${ctx}${m.url}" target="_blank">
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
<script>seajs.use('${ctxPortal}assets/css/tourism/guide.css');</script>