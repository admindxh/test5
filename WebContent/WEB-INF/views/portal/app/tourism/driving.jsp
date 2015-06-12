<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common-html/mainfrontcommon.jsp" %>
<%@taglib prefix="rimi" uri="/rimi-tags" %>
<%@taglib prefix="udj" uri="/user-defined-jstl" %>
<script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
<div class="container driving">
    <div class="row">
        <div class="col-xs-9 driving-left">
            <div class="header xz-heading">
                 <a  target="_blank" href="${ctx }tourism/strage/intoTraval/c1d87c3d-792e-11e4-b6ce-005056a05bc9.html" target="_blank"><h2 class="left"><span class="sr-only">自驾游攻略</span></h2></a>
                <div class="right">
                    <a target="_blank" href="${ctx }tourism/strage/intoTraval/c1d87c3d-792e-11e4-b6ce-005056a05bc9.html" target="_blank">更多MORE</a>
                </div>
            </div>
            <div class="driving-left-img-top clearfix">
                <div class="img-top-left">
                    <a href="${zjyContent[0].visitUrl}" target="_blank"><img src="${ctxIndex}${zjyContent[0].imgrurl}" alt="${zjyContent[0].title}"/></a>
                    <div class="info">
                        <p><a href="${zjyContent[0].visitUrl}" target="_blank">${zjyContent[0].title}</a></p>
                         <div>
                            <img src="${ctxPortal}assets/icon/eye_white.png" title="查看数"/><span>${zjyContent[0].viewcount}</span>
                            <%-- <img src="${ctxPortal}/assets/icon/star_white.png" title="收藏数"/><span>${zjyContent[0].favotecount}</span> --%>
                        </div>
                    </div>
                </div>
                <div class="img-top-right">
                    <a href="${zjyContent[1].visitUrl}" target="_blank"><img src="${ctxIndex}${zjyContent[1].imgrurl}" alt="${zjyContent[1].title}"/></a>
                    <div class="info">
                        <p><a href="${zjyContent[1].visitUrl}" target="_blank">${zjyContent[1].title}</a></p>
                        <div>
                            <img src="${ctxPortal}assets/icon/eye_white.png" title="查看数"/><span>${zjyContent[1].viewcount}</span>
                            <%-- <img src="${ctxPortal}/assets/icon/star_white.png" title="收藏数"/><span>${zjyContent[1].favotecount}</span> --%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="driving-left-img-top driving-left-img-footer clearfix">
                <div class=" img-top-right img-footer-left">
                    <a href="${zjyContent[2].visitUrl}" target="_blank"><img src="${ctxIndex}${zjyContent[2].imgrurl}" alt="${zjyContent[2].title}"/></a>
                    <div class="info">
                        <p><a href="${zjyContent[2].visitUrl}" target="_blank">${zjyContent[2].title}</a></p>
                        <div>
                            <img src="${ctxPortal}assets/icon/eye_white.png" title="查看数"/><span>${zjyContent[2].viewcount}</span>
                            <%-- <img src="${ctxPortal}/assets/icon/star_white.png" title="收藏数"/><span>${zjyContent[2].favotecount}</span> --%>
                        </div>
                    </div>
                </div>
                <div class="img-top-left img-footer-right">
                    <a href="${zjyContent[3].visitUrl}" target="_blank"><img src="${ctxIndex}${zjyContent[3].imgrurl}" alt="${zjyContent[3].title}"/></a>
                    <div class="info">
                        <p><a href="${zjyContent[3].visitUrl}" target="_blank">${zjyContent[3].title}</a></p>
                        <div>
                            <img src="${ctxPortal}assets/icon/eye_white.png" title="查看数"/><span>${zjyContent[3].viewcount}</span>
                            <%-- <img src="${ctxPortal}/assets/icon/star_white.png" title="收藏数"/><span>${zjyContent[3].favotecount}</span> --%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-3 driving-right">
            <div class="header header-right xz-heading">
                <a href="${ctx }${merchantList[2].mUrl}" target="_blank"><h2 class="left"><p><c:if test="${empty  merchantList[2].mType}">暂无</c:if>
                    <c:if test="${not empty  merchantList[2].mType}">${merchantList[2].mType }</c:if></p></h2></a>
                <div class="right"><a href="${ctx }${merchantList[2].mUrl}" target="_blank">更多MORE</a></div>
            </div>
            <c:if test="${empty  merchantList[2].merchantList}">
                <img src="${ctxPortal}static/images/no-recommendation.png" alt="${m.merchantName}"/>
            </c:if>
            <c:if test="${not  empty  merchantList[2].merchantList}">
                <c:forEach items="${merchantList[2].merchantList}"  var="m" varStatus="status">
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
<script>seajs.use('${ctxPortal}assets/css/tourism/driving.css');</script>