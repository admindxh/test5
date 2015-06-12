<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="udj" uri="/user-defined-jstl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <meta http-equiv="Content-Type" content="IE=edge,chrome=1"/>
    <meta name="keywords" content="西藏骑行攻略，西藏骑行注意事项，西藏骑行计划，骑行318，川藏线，川藏线骑行攻略，骑行西藏，318川藏线"/>
    <meta name="description"
          content="骑行专区：为驴友提供高质量的西藏骑行分享交流专区，包括西藏骑行攻略，西藏骑行注意事项，西藏骑行计划，西藏骑行约伴，骑行西藏，318川藏线，骑行318，川藏线骑行攻略等西藏骑行分享。"/>
    <meta name="Author" content="lh"/>
    <%@include file="/common-html/mainfrontcommon.jsp" %>
    <title>骑车去西藏_骑行专区-天上西藏官网</title>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxPortal}/assets/css/riding-area/star_service.css"/>
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
        // Set configuration
        seajs.config({
            alias: {
                "jquery": "jquery/jquery/1.11.1/jquery.js",
                "avalon": "avalon/1.3.7/avalon.js",
                "common/css": "${ctxPortal}/assets/css/common.css",
                "content/top/css": "${ctxPortal}/assets/css/content_top.css",
                "footer/css": "${ctxPortal}/assets/css/footer.css"
            }
        });
        seajs.use(["common/css", "footer/css"]);
        seajs.use('avalon', function (avalon) {
            
        })

		function errorImg (_this) {
        	_this.src = "${ctx}/portal/static/default/square.png";
        }
    </script>
</head>
<body>
<!-- 登录弹出框  -->
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
<!-- header -->
<jsp:include page="${root}/ride/ridePropgram"></jsp:include>
<div class="container">
    <!-- banner -->
    <div id="rd-banner" class="clearfix">
        <div class="rd-dh pull-left">
            <div class="rd-nav">
                <span>导航</span>
            </div>
            <ul class="rd-menu">
            <c:forEach items="${line}" var="obj" varStatus="status">
                <li class="menu-item${status.index+1} ${obj.code eq rideLine.code?' active':''}"><a href="${ctx }ride/line/list/${obj.code}.html">${obj.name}	<br/>${obj.subTitle}</a></li>
            </c:forEach>
            </ul>
        </div>
        <div class="b-ner pull-right">
            <img width="833" height="255" src="${ctx}/${rideLine.bannerImg}"/>
        </div>
    </div>
    <!-- content -->
    <div id="rd-content">
        <div class="row">
            <div class="col-xs-9">
                <h3 class="rd-views"><span class="sr-only">沿线看点</span></h3>
                <div class="ser-sta">
                	<c:forEach var="ride" items="${rlist}" varStatus="status">
                	     <c:if test="${status.last}">
                		         <div class="ser-item-last clearfix">
                	     </c:if>
                	      <c:if test="${!status.last}">
                		         <div class="ser-item1 clearfix">
                	     </c:if>
                        <div class="ser-title pull-left">
                            <h3>
                                <img src="${ctxPortal}/assets/icon/local_black.png" alt="local"/>
                                <span>第${status.index+1}站</span>
                                <span class=h-info>${ ride.serviceSite.siteName}</span>
                            </h3>
                            <div class="ser-img">
                                <img onerror="errorImg(this);" src="${ctx}/${ride.serviceSite.sitImg}" alt="${ ride.serviceSite.siteName}"/>
                            </div>
                        </div>
                        <div class="ser-cont pull-left">
                            <i></i>
                            <div class="ser-info clearfix">
                                <div class="pull-left ser-info-ad">
                                    <h4>${ride.serviceSite.serviceName }</h4>
                                    <div class="ser-top">
                                    <img style="float: right;" onerror="errorImg(this);" width="120" src="${ctx}/${ride.serviceSite.serviceImg}"/>
                                          <p>地址：<br/>${ride.serviceSite.serviceAdress }</p>
                                           <p>紧急援助电话：<br/>${ride.serviceSite.servicePhone}</p>
                                    </div>
                                    <div class="ser-bot clearfix">
                                        <div class="pull-left">
                                            <span class="i-con">宿</span>
                                            <ul>
                                            	<c:forEach var="ms" items="${ride.merchantShu}">
                                                  <li><a target="_blank" href="${ctx }${ms.url}">${ms.merchantName }</a></li>
                                            	</c:forEach>
                                            </ul>
                                            <a target="_blank" href="${ctx }tourism/merchant/merchantList/1/1/1${ride.serviceSite.siteName}/1merchantType422672563900.html" class="more-info more-left">更多</a>
                                        </div>
                                        <div class="pull-left">
                                            <span class="i-con">食</span>
                                            <ul>
                                                <c:forEach var="ms" items="${ride.merchantShi}">
                                                  <li><a target="_blank" href="${ctx }${ms.url}">${ms.merchantName }</a></li>
                                            	</c:forEach>
                                            </ul>
                                            <%-- <a href="${ctx }tourism/merchant/merchantList/1/1/merchantType422672581150" class="more-info more-center">更多</a> --%>
                                            <a target="_blank" href="${ctx }tourism/merchant/merchantList/1/1/1${ride.serviceSite.siteName}/1merchantType422672572609.html" class="more-info more-center">更多</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="pull-left ser-info-gl">
                                    <h4>推荐攻略</h4>
                                    <div class="rd-time"><span>
									<fmt:formatDate value="${ride.content[0].createTime}"  pattern="dd"/>
                                    </span>
                                    <br/><fmt:formatDate value="${ride.content[0].createTime}"  pattern="yyyy-MM"/></div>
                                    <div class="rd-title">
                                        <h5><a href="${ctxIndex }${ride.content[0].url}"  target="_blank">${ride.content[0].contentTitle }</a></h5>
                                        <span>作者：${ride.content[0].authorCode}</span>
                                    </div>
                                    <p class="gl-cont">
                                    		${udj:subString(udj:filterHtmlTags(ride.content[0].content),'120')}
                                    </p>
                                    <a target="_blank" href="${ctx }tourism/strage/intoTraval" class="more-info more-right" >更多</a>
                                </div>
                            </div>
                        </div>	
                    </div>	
                    </c:forEach>
                    <!-- 最后一个站点，类名改成 ser-item-last -->
                </div>
            </div>
            <div class="col-xs-3">
                <h3 class="rd-intro"><span class="sr-only">线路简介</span></h3>
                <p class="rd-txt">
                    	${rideLine.introduce }
                </p>
                <h3 class="rd-notice"><span class="sr-only">官方说明</span></h3>
                <p class="rd-txt">
                  ${rideLine.summary }
                </p>
                <h3 class="rd-pre"><span class="sr-only">注意事项</span></h3>
                <p class="rd-txt">
                    ${rideLine.notice}
                </p>
                <c:forEach items="${arealist}" var="ar">
                <a href="${ar.url}" target="_blank">
                <img width="227" src="${ctx}/${ar.imgurl}"/></a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<!-- footer -->
<jsp:include page="${root}/portal/headerFooterController/footer"/>
</body>
</html>