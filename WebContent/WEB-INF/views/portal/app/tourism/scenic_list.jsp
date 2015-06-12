<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/commonTag.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏热门景,西藏景点,西藏旅游景点，布达拉宫，色季拉山，羊卓雍错" />
	<meta name="description" content=" 西藏热门景点介绍，为您提供西藏热门景点信息包括交通，文化和地区特色等，欢迎访问天下西藏官网。" />
	<title>西藏热门景点_天上西藏官网</title>
    <link rel="stylesheet" href="${ctxPortal }/modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxPortal}/assets/css/tourism/scenic_list.css"/>
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
    seajs.config({
        alias: {
            "jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js",
			"avalon": "${ctxPortal}/modules/avalon/1.3.7/avalon.js",
			"map": "${ctxPortal}/assets/js/map.js",
            "bootstrap/css": "${ctxPortal}/bootstrap/3.3.1/css/bootstrap.min.css",
            "header/css": "${ctxPortal}/assets/css/common.css",
            "footer/css": "${ctxPortal}/assets/css/footer.css",
			"paginator":"${ctxPortal}/modules/paginator/0.5/bootstrap-paginator.js"
        }
    });
    seajs.use(['header/css', 'footer/css']);
    </script>
</head>

<body ms-controller="indexView">
<!-- header -->
<jsp:include page="${root}/portal/headerFooterController/header"/>
<div class="container main">
    <!-- banner -->
    <div class="banner">
    	<!-- 地图 -->
		<div class="map-wrap">
			<!-- 热门景点列表 -->
			<ul id="hotspots" class="list-unstyled" ms-important="hotspots">
				<li ms-repeat="spots"><a target="_blank" href="#" ms-repeat-item="el">{{ item.value }}</a></li>
			</ul>
			<div id="map" style="height: 300px;"></div>
		</div>
    </div><!-- banner End -->
    <!-- search -->
    <div class="search clearfix">
        <div class="region">
            <div class="drop-down">
                <label>地区:</label>
                <div id="destinationDropList" class="dropdown">
                    <input type="hidden" id="destinationCode" name="destinationCode" value="${destinationCode }">
                	<button class="dropdown-toggle" type="button" id="region-menu" data-toggle="dropdown"><label>相关地区</label><span></span></button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                    	<li role='presentation'><a target="_blank" role='menuitem' tabindex='-1' href='javascript:void(0);' value=''>全部地区</a></li>
                        <c:forEach var="obj" varStatus="sta" items="${listDestination }">
                        	<li role="presentation"><a target="_blank" role="menuitem" tabindex="${sta.index }" value="${obj.code }" href="javascript:void(0);">${obj.destinationName }</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div class="region scen">
            <div class="drop-down">
                <label>景点:</label>
                <div id="viewDropList" class="dropdown">
                  	<input type="hidden" id="viewCode" name="viewCode" value="">
                	<button class="dropdown-toggle" type="button" id="scen-menu" data-toggle="dropdown"><label>相关景点</label><span></span></button>
                    <ul id="viewDorpListOption" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <li role='presentation'><a target="_blank" role='menuitem' tabindex='-1' href='javascript:void(0);' value=''>全部景点</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="fuzzy-search">
            <input type="text" id="keyWords" name="keyWords" placeholder="请输入关键字搜索景点" /><button type="button" id="search"><img src="${ctxPortal}/assets/icon/shape.png" /></button>
        </div>
        <div class="hot-link">
            <label>热门搜索:</label>
            <c:forEach var="obj" varStatus="sta" items="${listHotView}">
            	<c:set var="fir" value="" />
            	<c:if test="${sta.index==0}">
	            	<c:set var="fir" value="hot-link-first" />
            	</c:if>
            	<%-- <c:set var="viewName_" value="${obj.viewName}${obj.viewName}${obj.viewName}"/> --%>
            	<div><a target="_blank" title="${obj.viewName}" class="${fir}" href="${ctx}${obj.linkUrl}">${obj.viewName}</a></div>
            	<%-- <a title="${obj.viewName}" class="${fir}" href="${ctx}tourism/view/forViewDetail?code=${obj.linkUrl}">${fn:length(obj.viewName)>4?fn:substring(obj.viewName,0,4):obj.viewName}${fn:length(obj.viewName)>4?'···':''}</a> --%>
            </c:forEach>
        </div>
    </div><!-- search End -->
    <ul class="listing clearfix">
        <li class="down" value="${ORDER_WANTCOUNT}">最多想去</li>
        <li value="${ORDER_GONECOUNT}">最多去过</li>
        <li value="${ORDER_CHECKNUM}">最多查看</li>
        <li value="${ORDER_REPLYNUM}">最多评论</li>
    </ul>
    
    <!-- content -->
    <div class="content clearfix" ms-important="viewView">
        <div ms-repeat="data" class="con-info clearfix">
            <div class="sce-img">
                <a target="_blank" ms-href="${ctx}{{el.linkUrl}}"><img style="width: 181px; height: 255px;" ms-src="${ctx}{{el.img}}" ms-attr-alt="el.viewName"/></a>
            </div>
            <div class="particular">
                <h5><a target="_blank" style="color: #333;" ms-href="${ctx}{{el.linkUrl}}">{{el.viewName}}</a></h5>
                <p>
                    <span class="span-color">想去</span>
                    <span>{{el.fakeWantCount}}</span>
                </p>
                <p>
                    <span class="span-color">去过</span>
                    <span>{{el.fakeGoneCount}}</span>
                </p>
                <p class="presentation"><a target="_blank" style="color: #333;" ms-href="${ctx}{{el.linkUrl}}">{{el.address|clearHTML|html}}</a></p>
            </div>
        </div>
        <div class="nodata" id="J_nodata" style="display: none;"></div>
    </div>
    <!-- content End -->
    
    <!-- page -->
    <div id="viewPaging" class="paging paging-center paging-lg page"><!-- 分页按钮 --></div>
    <!-- page End -->
</div>
<!-- footer -->
<jsp:include page="${root}/portal/headerFooterController/footer"/>

<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>

<script type="text/javascript">
	var basePath_ = "${ctx}";
	var basePathPortal = "${ctxPortal}";
	
	var ORDER_WANTCOUNT_ = "${ORDER_WANTCOUNT}";
	var destinationCode_ = "${destinationCode}";
	
	seajs.use(basePathPortal+'/assets/js/tourism/strategy_list.js');
</script>
<script type="text/javascript" src="${ctx}portal/assets/js/tourism/scenic_list.js"></script>

</body>
</html>