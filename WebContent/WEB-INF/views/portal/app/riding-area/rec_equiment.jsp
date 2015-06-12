<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="udj" uri="/user-defined-jstl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <meta http-equiv="Content-Type" content="IE=edge,chrome=1"/>
    <meta name="keywords" content="西藏骑行攻略，西藏骑行注意事项，西藏骑行计划，骑行318，川藏线，川藏线骑行攻略，骑行西藏，318川藏线"/>
    <meta name="description"
          content="骑行专区：为驴友提供高质量的西藏骑行分享交流专区，包括西藏骑行攻略，西藏骑行注意事项，西藏骑行计划，西藏骑行约伴，骑行西藏，318川藏线，骑行318，川藏线骑行攻略等西藏骑行分享。"/>
    <meta name="Author" content="Mr.Piz"/>
       <%@include file="/common-html/mainfrontcommon.jsp" %>
    <title>骑车去西藏_骑行专区-天上西藏官网</title>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxPortal}/assets/css/riding-area/rec_equipment.css"/>
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
        seajs.use(["common/css", "slick/slick.css", "footer/css"]);
        seajs.use('avalon', function (avalon) {
            avalon.define({
                $id: "recView",
                showTag: true
            })
        })
        
        
        
    </script>
</head>
<body ms-controller="recView">
<!-- 登录弹出框  -->
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
<!-- header -->
<jsp:include page="${root}/ride/ridePropgram"></jsp:include>
<div class="container">
    <!-- banner -->
    <div id="rec-banner" class="clearfix">
        <div class="pull-left class-menu">
            <a href="${ctx}ride/eqindex/list" ><div class="rec-class">分类</div></a>
            <ul class="rec-list">
                <c:forEach items="${list}" var="p" varStatus="status">
                    <li style="text-overflow:ecllipsis;overflow: hidden;white-space: nowrap;" class="list-item${status.index+1 }      "  onclick="javascript:window.location.href='${ctx }ride/eqindex/more/${p.code}.html'">
                    	${p.programaName}
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="pull-right rec-slick">
            <div class="slick">
                	<c:forEach items="${blist}" var="b" varStatus="">
                			<div><a href="${ctx }${b.name}" target="_blank"><img width="700" height="305" src="${ctx}${b.url}" alt="banner"/></a></div>
                	</c:forEach>
            </div>
        </div>
    </div>
    <!-- content -->
    <div id="rec-content">
        <div class="row">
        	<div class="rec-left">
        	<c:forEach items="${equireMentVos}" var="em" varStatus="">
        		<div class="shop-item">
	                <div class="rec-title clearfix">
	                    <h3><span><a href="${ctx }ride/eqindex/more/${em.programa.code}.html" target="_blank">${em.programa.programaName }</a></span></h3>
	                    <%-- <a href="${ctx }ride/eqindex/more?type=${em.programa.code}">更多 MORE</a> --%>
	                </div>
	                <div class="rec-info clearfix">
		                 <c:forEach items="${em.eqlist}" var="e">
		                 	<div class="commodity">
		                        <div class="commodity-img">
		                            <a target="_blank" href="${ctx }${e.url}"><img src="${ctx}${e.sumaryimg}" alt="${e.name }"/></a>
		                        </div>
		                        <div class="commodity-info">
		                            <p><a href="${ctx }${e.url}" target="_blank">${e.name }<br/>${e.summary }</a></p>
		                            <span class="price">${e.price}元</span>
		                        </div>
	                    	</div>
		                 </c:forEach>
		                 <c:if test="${empty em.eqlist}">
		                 	<div class="nodata"></div>
		                 </c:if>
	                </div>
           	 	</div>
        	  </c:forEach>
        	</div>
            <div class="rec-right">
                <div class="advertising">
                   <c:forEach items="${arealist}" var="ad">
                      <div><a target="_blank" href="${ad.url}"><img src="${ctx}/${ad.imgurl}" alt="广告"/></a></div>
                   </c:forEach>
                </div>
                <div class="sidebar">
                    <div class="rec-title clearfix">
                        <h3 class="rec_new"  onclick="javascript:window.open('${ctx }community/listzb/createTime/discuss.html')" style="cursor: pointer;"><span class="sr-only">最新发布</span></h3>
                        <%-- <a href="${ctx }community/listzb?orderBy=newReply&plateCode=2334fb28-be86-4ac5-a251-6eb069ca7b2f">更多 MORE</a> --%>
                        <a href="javascript:;" onclick="window.open('${ctx }community/listzb/createTime/discuss.html');">更多 MORE</a>
                    </div>
                    <ul>
                      <c:forEach items="${createTime}" var="c">
                        <li><span>●</span><a href="${ctx }${c.url}" target="_blank">${c.title}</a></li>
                      </c:forEach>
                    </ul>
                </div>
                <div class="sidebar">
                    <div class="rec-title clearfix">
                        <h3 class="rec_reply"  onclick="javascript:window.open('${ctx }community/listzb/newReply/discuss.html')" style="cursor: pointer;"><span class="sr-only">最新回复</span></h3>
                        <%-- <a href="${ctx }community/listzb?orderBy=createTime&plateCode=2334fb28-be86-4ac5-a251-6eb069ca7b2f">更多 MORE</a> --%>
                        <a href="javascript:;" onclick="window.open('${ctx }community/listzb/newReply/discuss.html');">更多 MORE</a>
                    </div>
                    <ul>
                        <c:forEach items="${replyTime}" var="c">
                        <li><span>●</span><a href="${ctx }${c.url}" target="_blank">${c.title}</a></li>
                      </c:forEach>
                    </ul>
                </div>
                <div class="sidebar">
                    <div class="rec-title clearfix">
                        <h3 class="rec_fav"  onclick="javascript:window.open('${ctx }community/listzb/mostPraise/discuss.html')" style="cursor: pointer;"><span class="sr-only">被赞最多</span></h3>
                        <a href="javascript:;" onclick="window.open('${ctx }community/listzb/mostPraise/discuss.html');">更多 MORE</a>
                    </div>
                    <ul>
                       <c:forEach items="${praiseTime}" var="c">
                          <li><span>●</span><a target="_blank" href="${ctx }${c.url}">${c.title}</a></li>
                      </c:forEach>
                    </ul>
                </div>
            </div>
            
            
        </div>
    </div>
</div>
<!-- footer -->
<!-- footer -->
<jsp:include page="${root}/portal/headerFooterController/footer"/>
<script>
    seajs.use(['jquery', 'slick/slick.js'], function ($) {
        $(function(){
            $('.slick').slick({
                autoplay: true,
                dots: true,
                arrows: false
            });
            $('.advertising').slick({
                autoplay: true,
                dots: true,
                arrows: true
            })
        });
    });
</script>
</body>
</html>