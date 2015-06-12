<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="com.rimi.ctibet.domain.LogUser" session="true" %>
<%@page import="com.rimi.ctibet.domain.Content" %>
<%@taglib uri="/rimi-tags"  prefix="rimi"%>
<%@taglib prefix="udj" uri="/user-defined-jstl" %>
<%
    LogUser lg = (LogUser) request.getSession().getAttribute("logUser");
    request.setAttribute("lgUser", lg);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="${merchant.tdkKeywords }" />
	<meta name="description" content="${merchant.tdkDescription }" />
	<title>${merchant.tdkTitle }</title>
    <%@include file="/common-html/frontcommon.jsp" %>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <!--[if lt IE 9]>
    <script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script type="text/javascript">
        seajs.config({
            alias: {
                "jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js",
                "avalon": "${ctxPortal}/modules/avalon/1.3.7/avalon.js",
                "common/css": "${ctxPortal}/assets/css/common.css",
                "merchant/css": "${ctxPortal}/assets/css/merchant.css",
                "footer/css": "${ctxPortal}/assets/css/footer.css",
                "detail/css": "${ctxPortal}/assets/css/tourism/detail.css",
                "paginator": "${ctxPortal}/modules/paginator/0.5/bootstrap-paginator.js",
                "Validform": "${ctxPortal}/modules/Validform/5.3.2/Validform.min.js"
            }
        });
    </script>
	<style>
		.xzbox p{
			margin: 0;
		}
		.content  a, a:hover {
						color: inherit;
			}
		    .content a{
			     color: #337ab7; 
 				text-decoration: underline; 
				}
	</style>
</head>
<body>
<jsp:include page="${root}/portal/headerFooterController/header?seo_index=seo_index"></jsp:include>
<jsp:include page="../login/login_modal.jsp"></jsp:include>
<div class="container">
    <div class="bd">
        <div class="xzbox clearfix">
        	
            <div class="bd-left">
            <div class="location" style="padding-bottom: 20px;">
						            <img src="${ctxPortal}/assets/icon/location_red.png"/>
						            <span>当前位置:</span>
						            <a href="${ctx}tourism/strage/frontIndex" target="_self">游西藏</a>
						            <img src="${ctxPortal}/assets/icon/arrow_right.png"/>
						            <a href="${ctx}tourism/merchant/index" target="_self">商户</a>
						            <img src="${ctxPortal}/assets/icon/arrow_right.png"/>
						            <span style="color:red"> ${merchant.merchantName}</span>
			</div>
                <strong class="xz-heading hd-title" style="background: white;font-weight: normal;">
                    <p>
                        ${merchant.merchantName}
                        <c:if test="${merchant.isRecommend==1}">
                            <i class="ctibet"></i>
                        </c:if>
                    </p>
                </strong>
                <div class="shows-wrap">
                    <div class="types">
                        <span class="geye" title="查看数">${praiseAndView.falseviewcount}</span>
                        <span class="heart" id="heart" title="收藏数">${praiseAndView.falsefavoritenum}</span>
                                       <%--<div class="loving" style="display: inline;border: 0px;">--%>
										<%--<button style=" width: 30px;height: 30px;outline: none;display: inline;border: 0px;" onclick="isRecoredMerchantFavate('${merchant.code}',this)" <rimi:IsCollect code="${merchant.code}" memCode="${logUser.code }">class="star_active"</rimi:IsCollect>></button>--%>
										<%--<label>${praiseAndView.falsefavoritenum}</label>--%>
								<%--</div>--%>
                        <div style="display: inline;border: 0px;position: relative;top: 3px;left: 8px;">
                            <button class='star_min <rimi:IsCollect code="${merchant.code }" memCode="${logUser.code }" >star_min_active</rimi:IsCollect>' onclick="isRecoredMerchantFavatexz('${merchant.code}',this,'min')"  ></button>
                        </div>
                    </div>
                    <div class="shows clearfix">
                        <div class="show-item big">
                            <c:if test="${empty merchant.merchantImage}">
                                <img src="${ctxPortal}/static/default/square.png"
                                     width="350" height="265">
                            </c:if>
                            <c:if test="${not empty merchant.merchantImage}">
                                <img src="${ctx}/${merchant.merchantImage}" width="350"
                                     height="265">
                            </c:if>
                        </div>
                        <c:forEach items="${imgs}" var="img" varStatus="index">
                            <div class="show-item ${index.index== 0?'active': ''}">
                                <div class="show-mark"></div>

                                <c:if test="${empty img.url}">
                                    <img src="${ctxPortal}/static/default/square.png"
                                         width="350" height="265">
                                </c:if>
                                <c:if test="${not empty img.url}">
                                    <img src="${ctx}/${img.url }" width="135" height="125">
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="xzbox">
                    <div class="bd-heading">
                        <h1 class="intro">
                            简介 INTRODUCTION
                        </h1>

                        <div class="share bdsharebuttonbox">
                            <a href="#" class="weibo" data-cmd="tqq" title="分享到腾讯微博">分享到腾讯微博</a>
                            <a href="#" class="sina" data-cmd="tsina" title="分享到新浪微博">分享到新浪微博</a>
                            <a href="#" class="qzone" data-cmd="qzone" title="分享到QQ空间">分享到QQ空间</a>
                        </div>
                    </div>
                    <div class="content">
                        <p>
                            ${merchant.merchantDetail}
                        </p>
                    </div>
                </div>
                <div id="hm_t_62004"></div>
                <%--						<c:if test="${not empty {{el.name}}}">--%>
                <div class="xz-box dh-main" ms-important="allActivityView">
                    <div class="comments" ms-repeat="data" ms-class="one:$first"
                         style="padding-bottom: 0;" data-repeat-rendered="rendered">
                        <div class="media">
                            <a target="_blank" class="media-left" ms-href="<%=basePath %>member/show/{{el.createuserCode}}.html"> <span class="user-mark"></span>
                                <img alt="68x68" ms-attr-src="${ctx}{{el.pic}}" width="68"
                                     height="68"> </a>

                            <div class="media-body">
                                <h2 class="media-heading">
                                    <i ms-class={{el.sex==1?"j-male":"j-female"}}></i>
                                    {{el.name}}
                                    <span class="time">{{el.createTime | date('yyyy-MM-dd HH:mm:ss')}}</span>
                                    <p style="display: inline;" class="m-score" ms-data-score="el.title==0?5:el.title" data-readonly="true"></p>
                                </h2>

                                <h3>
                                    {{el.content}}
                                </h3>
                            </div>
                        </div>
                    </div>
                    <div id="allActivityPage"></div>
                    <%--						</c:if>--%>
                   
                    <div class="comment" id="merchantReply">
                        <form action="${ctx}/tourism/merchant/saveReply" id="form1" method="post">
                        <input type="hidden"  value="${token}" name="token"/>
                            <input type="hidden" name="merchantCode" value="${merchant.code}"/>
                            <input type="hidden" name="merchantType" value="${merchant.merchantType}"/>
                            <input type="hidden" name="title" id="score" value="5"/>
                           	<div class="d-score">评分：<span></span></div>
                           	
							<textarea id="reply_area"  onfocus="validLg()" name="content" rows="10"
	                            class="form-control" placeholder="有什么好的建议来说两句吧~"
	                            datatype="*2-200" nullmsg="请输入内容"></textarea>
                            <span class="Validform_checktip"></span>
                            <div class="text-right">
                             <c:if test="${not  empty logUser}">
                                <button style="background-color: #cf1423" class="btn-comment">发表评论</button>
                             </c:if>
                             <c:if test="${  empty logUser}">
                                <button style="background-color: #BAB3B3" class="btn-comment">发表评论</button>
                             </c:if>
                            </div>
                        </form>
                    </div>
                   
							<c:if test="${empty logUser}">
									<p class="log-ts pull-left">您还没有登录，请先<a href="#loginModal" data-toggle="login">登录</a>或<a href="${ctx }portal/registerController/register" target="_blank">注册</a>，再进行评论！</p>
					       </c:if>
                    
                </div>
            </div>
            <div class="bd-right">
                <div class="ctrip">
                    <div class="shchb"></div>
                    <div class="cbd">
                        <p class="price">
                           	 价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：
                            <c:if test="${merchant.price=='0'}">
                                <span>暂无价格</span>
                            </c:if>
                              <c:if test="${empty merchant.price}">
                                <span>暂无价格</span>
                            </c:if>
                            <c:if test="${merchant.price!='0'&& not empty merchant.price}">
                                <span>&yen;${merchant.price}<em>起</em> </span>
                            </c:if>
                        </p>
                        <c:if test="${empty merchant.activityUrl }">
	                        <c:if test="${not empty merchant.ctripUrl}">
	                            <a href="javascript:void(0)" onclick="onclikHref('${merchant.ctripUrl}')" class="btn-yd">
	                            	<span class="sr-only">立即预定</span>
	                            </a>
	                        </c:if>
                        </c:if>
                        <c:if test="${not empty merchant.activityUrl }">
                            <a href="javascript:void(0)" onclick="onclikHref('${merchant.activityUrl}')" class="btn-yd">
                            	<span class="sr-only">立即预定</span>
                            </a>
                        </c:if>
                    </div>
                </div>
                <div class="air-addr">
                    地址：
                    <p>
                        ${destination.destinationName}
                        <c:if test="${not empty merchant.merchantSummary}">
                        -${merchant.merchantSummary}
                        </c:if>
                    </p>
                </div>
                <p style="margin-top: 10px;">来源：
						         <c:if test="${empty merchant.ctripUrl}">天上西藏
								 </c:if>
								 <c:if test="${not empty merchant.ctripUrl}">携程旅行网
								 </c:if></p>
                
                
                    <div class="xz-box">
	                    <div class="xz-heading">
	                        <h2 class="dest">
	                            <span class="sr-only">相关目的地</span>
	                        </h2>
	                    </div>
	                    <div class="dest-content">
	                        <span><a target="_blank" href="${ctx}${destination.linkUrl}" class="label">${destination.destinationName}</a></span>
	                    </div>
	                </div>
                <div class="xz-box" style="height: 370px; overflow: hidden;">
                    <div class="xz-heading">
                        <h2 class="dspot">
                            <span class="sr-only">相关景点 SPOT</span>
                        </h2>
                    </div>
                    <div class="content switchover" style="overflow: visible;">
                        <c:if test="${not empty views}">
                            <c:forEach items="${views}" var="view" varStatus="status">
                                <c:if test="${(status.index+1)%4==1}">
                                    <div class="bd-img">
                                </c:if>
                                <div class="img-item dspot-content2">
                                    <a  target="_blank" href="${ctx}${view.linkurl}"><img
                                            src="${ctx}${view.img}" width="150" height="210"
                                            alt="${view.viewname}"> </a>

                                    <div class="dmark"></div>
                                    <div class="desc">
                                        <p>
                                                ${view.viewname}
                                        </p>
                                    </div>
                                </div>
                                <c:if test="${(status.index+1)%4==0}">
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty views}">
                            <div class='nodata'></div>
                        </c:if>
                    </div>
                </div>
        </div>
    </div>
</div>
    </div>
</div>
<jsp:include page="${root}/portal/headerFooterController/footer"></jsp:include>

<script>
    var basePath_ = "${ctx}";
	var basePathPortal = "${ctxPortal}";
    
	var merchantCode2_ = "${merchant.code}";
	var merchantCode_ = "${merchantCode}";
	var merchantName_ = "${merchant.merchantName}";
	var merchantType_ = "${merchant.merchantType}";
	
	var DETAIL_MERCHANT_REPLY_ = '<%=Content.DETAIL_MERCHANT_REPLY%>';
	
	var isLogin_ = ${lgUser==null};
</script>
<script type="text/javascript" src="${ctx}portal/assets/js/tourism/merchant_detail.js"></script>

<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>


</body>
</html>