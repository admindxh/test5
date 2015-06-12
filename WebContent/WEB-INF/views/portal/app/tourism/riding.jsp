<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common-html/mainfrontcommon.jsp" %>
<%@taglib uri="/rimi-tags" prefix="rimi" %>
<%@taglib prefix="udj" uri="/user-defined-jstl" %>
<script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
<div class="container riding">
    <div class="row">
        <div class="col-xs-9 riding-left">
            <div class="header xz-heading">
                 <a href="${ctx }tourism/strage/intoTraval/c1d8e87d-792e-11e4-b6ce-005056a05bc9.html" target="_blank"><h2 class="left"><span class="sr-only">骑行攻略</span></h2></a>
                <div class="right">
                    <a href="${ctx }tourism/strage/intoTraval/c1d8e87d-792e-11e4-b6ce-005056a05bc9.html" target="_blank">更多MORE</a>
                </div>
            </div>
            <div class="riding-left-img-top clearfix">
                <div class="img-top-left">
                    <a href="${qxContent[0].visitUrl}" target="_blank"><img src="${ctxIndex}${qxContent[0].imgrurl}" alt="${qxContent[0].title}"/></a>
                    <div class="info">
                        <p><a href="${qxContent[0].visitUrl}" target="_blank">${qxContent[0].title}</a></p>
                        <div>
                            <img src="${ctxPortal}assets/icon/eye_white.png" title="查看数"/><span>${qxContent[0].viewcount}</span>
                            <%-- <img src="${ctxPortal}/assets/icon/star_white.png" alt=""/><span>${qxContent[0].favotecount}</span> --%>
                        </div>
                    </div>
                </div>
                <div class="img-top-right">
                    <a href="${qxContent[1].visitUrl}" target="_blank"><img src="${ctxIndex}${qxContent[1].imgrurl}" alt="${qxContent[1].title}"/></a>
                    <div class="info">
                        <p><a href="${qxContent[1].visitUrl}" target="_blank">${qxContent[1].title}</a></p>
                        <div>
                            <img src="${ctxPortal}assets/icon/eye_white.png" title="查看数"/><span>${qxContent[1].viewcount}</span>
                            <%-- <img src="${ctxPortal}/assets/icon/star_white.png" alt=""/><span>${qxContent[1].favotecount}</span> --%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="riding-left-img-top riding-left-img-footer clearfix">
                <div class=" img-top-right img-footer-left">
                    <a href="${qxContent[2].visitUrl}" target="_blank"><img src="${ctxIndex}${qxContent[2].imgrurl}" alt="${qxContent[2].title}"/></a>
                    <div class="info">
                        <p><a href="${qxContent[2].visitUrl}" target="_blank">${qxContent[2].title}</a></p>
                        <div>
                            <img src="${ctxPortal}assets/icon/eye_white.png" title="查看数"/><span>${qxContent[2].viewcount}</span>
                            <%-- <img src="${ctxPortal}/assets/icon/star_white.png" alt=""/><span>${qxContent[2].favotecount}</span> --%>
                        </div>
                    </div>
                </div>
                <div class="img-top-left img-footer-right">
                    <a href="${qxContent[3].visitUrl}" target="_blank"><img src="${ctxIndex}${qxContent[3].imgrurl}" alt="${qxContent[3].title}"/></a>
                    <div class="info">
                        <p><a href="${qxContent[3].visitUrl}" target="_blank">${qxContent[3].title}</a></p>
                        <div>
                            <img src="${ctxPortal}assets/icon/eye_white.png" title="查看数"/><span>${qxContent[3].viewcount}</span>
                            <%-- <img src="${ctxPortal}/assets/icon/star_white.png" alt=""/><span>${qxContent[3].favotecount}</span> --%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-3 riding-right">
            <div class="header header-right xz-heading">
                <a href="${ctx }tourism/merchant/getGroupTravelList" target="_blank"><h2 class="left"><p>团游出行</p></h2></a>
                <div class="right"><a href="${ctx }tourism/merchant/getGroupTravelList" target="_blank">更多MORE</a></div>
            </div>
            <c:if test="${empty  merchantList[3].merchantList}">
                <img src="${ctxPortal}static/images/no-recommendation.png"/>
            </c:if>
            <c:if test="${not  empty  merchantList[3].merchantList}">
                <c:forEach items="${merchantList[3].merchantList}"  var="m" varStatus="status">
                    <div class="content">
                        <h3 class="h3-fz"><a href="${ctx }${m.url}" target="_blank">${m.merchantName}</a></h3>
                        <div class="media message">
                            <a class="media-left" href="${ctx }${m.url}" target="_blank">
                                <div>
                                    <img src="${ ctxIndex}${m.merchantImage}"/>
                                    <c:if test="${m.isRecommend==1}"><img class="ctibet" src="${ctxPortal}assets/icon/ctibet_yxz.png"/></c:if>
                                </div>
                            </a>
                            <div class="media-body">
                                <div class="mer-info">
                                    <span>价格：${empty m.price || "0" eq m.price ? "暂无" : m.price }</span>
                                    <p gcode="${m.code}" class="gt-score" data-score="5" data-readonly="true"></p>
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
<script>seajs.use('${ctxPortal}assets/css/tourism/riding.css');</script>