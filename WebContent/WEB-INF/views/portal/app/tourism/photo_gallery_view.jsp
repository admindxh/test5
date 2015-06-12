<%@page import="com.rimi.ctibet.domain.Image"%>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/commonTag.jsp" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏目的地，西藏景点，西藏游记攻略，西藏路线，西藏自驾游攻略，西藏美食攻略，西藏骑行攻略"/>
    <meta name="description" content="游西藏：西藏目的地，西藏景点，西藏机票，西藏游记攻略，西藏路线，西藏自驾游攻略，西藏美食攻略，西藏骑行攻略等旅游攻略。"/>
    <title>西藏旅游攻略，自助游，自驾游,西藏骑行攻略_游西藏-天上西藏官网</title>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxPortal}/assets/css/tourism/photo_gallery.css"/>
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script type="text/javascript">
	    seajs.config({
	        alias: {
	            "jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js",
	            "avalon":"${ctxPortal}/modules/avalon/1.3.7/avalon.js",
	            "paginator":"${ctxPortal}/modules/paginator/0.5/bootstrap-paginator.js",
	            "fancybox":"${ctxPortal}/modules/fancybox/jquery.fancybox.js",
	            "fancybox/css":"${ctxPortal}/modules/fancybox/jquery.fancybox.css",
	            "common/css":"${ctxPortal}/assets/css/common.css",
	            "footer/css":"${ctxPortal}/assets/css/footer.css"
	        }
	    });
    </script>
</head>
<body>
    <!-- header -->
    <jsp:include page="${root}/portal/headerFooterController/header"/>
    <jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>

    <div class="container photo-gallery">

        <!-- location -->
        <div class="location">
            <img src="${ctxPortal}/assets/icon/location_red.png"/>
            <span>当前位置:</span>
            <a href="${ctx}tourism" target="_self">游西藏</a>
            <img src="${ctxPortal}/assets/icon/arrow_right.png"/>
            <a href="${ctxno}${destination.linkUrl}" target="_self">${destination.destinationName }</a>
            <img src="${ctxPortal}/assets/icon/arrow_right.png"/>
            <span style="color:red;" class="active">${view.viewName }</span>
        </div><!-- location End -->

        <!-- content -->
        <div class="content">
            <div class="header"></div>
        </div><!-- content End -->

        <!-- 官方图集 & 用户上传 -->
        <div class="photo-class">
            <span id="official" class="active">官方图集</span><span id="user-upload">用户上传</span>
        </div><!-- 官方图集 & 用户上传 End -->

        <!-- 官方列表 -->
        <div id="official-list" class="photo-list dis-show" ms-important="offUploadView">
        	<div class="clearfix">
	            <div class="img-list" ms-repeat="data" ms-click="showGallery(el, $index)">
                    <img class="picture" ms-attr-src="el.url"/>
                    <img class="ctibet" src="${ctxPortal}/assets/icon/official.png"/>
	            </div>
	        </div><!-- 官方列表 End -->
	        <div class="nodata" ms-visible="!data.size()"></div>
	        <div id="offUploadPaging" ms-visible="data.size()" class="paging paging-center paging-lg page"></div>
		</div>

        <div id="userUpload-list" class="photo-list " ms-important="userUploadView">
			<div class="clearfix">
	            <div class="img-list" ms-repeat="data" ms-click="showGallery(el, $index)">
	                <img class="picture" ms-attr-src="el.url" ms-attr-alt="el.name"/>
	                <div class="picture-bg"></div>
	                <div class="picture-info">
	                    <div class="from"><span>From</span> <span>{{el.name}}</span></div>
	                    <div class="by"><span>By</span> <span>{{el.createUserName}}</span></div>
	                </div>
	            </div>
	        </div>
	        <div class="nodata" ms-visible="!data.size()"></div>
	        <div id="userUploadPaging" ms-visible="data.size()" class="paging paging-center paging-lg page"></div>
		</div>
        <!-- page -->

        <!-- page End -->
    </div>

    <!-- footer -->
    <jsp:include page="${root}/portal/headerFooterController/footer"/>

	<script type="text/javascript">
		var basePath_ = "${ctx}";
		var basePathPortal = "${ctxPortal}";
		
		var viewCode_ = "${viewCode }";
		var ATLAS_USER_ = "<%=Image.ATLAS_USER%>";
		var ATLAS_OFFICAL_ = "<%=Image.ATLAS_OFFICAL%>";
		seajs.use(['common/css','footer/css', 'fancybox/css']);
	</script>
	<script type="text/javascript" src="${ctx}portal/assets/js/tourism/photo_gallery_view.js"></script>

</body>
</html>