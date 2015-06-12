<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/commonTag.jsp" %>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
    <title>首届西藏国际微电影节微视大赛</title>
    <link rel="stylesheet" href="<%=basePath%>wsds/css/reset.css"/>
    <link rel="stylesheet" href="<%=basePath%>wsds/css/video.css"/>
<link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
-->
<style>
		.fancybox-title-inslid-wrap .child{
			line-height: 28px;
			margin-top: 10px;
		}
	</style>
	<script id="seajsnode" src="${ctxPortal }/modules/seajs/seajs/2.2.1/sea.js"></script>
  <script src="${ctxPortal }/assets/js/Alert.js"></script>
  <script>
  var _alert = new Alert();
  seajs.config({
	    alias: {
	      "jquery": "jquery/jquery/1.11.1/jquery.js",
	      "avalon":"avalon/1.3.7/avalon.js",
	      "fancybox":"fancybox/jquery.fancybox.js",
	      "paginator":"paginator/0.5/bootstrap-paginator.js",
	      "fancybox/css":"fancybox/jquery.fancybox.css",
	      "common/css":"${ctxPortal }/assets/css/common.css",
	      "activity/css":"${ctxPortal }/assets/css/activity/activity.css",
	      "footer/css":"${ctxPortal }/assets/css/footer.css",
	      "detail/js":"${ctxPortal }/assets/js/activity/detail.js",
	      "dateUtil":"${ctx }/common-js/DateUtil.js"
	    }
  });
  seajs.use(["jquery"], function($){
		window.$ = $;
  });
  </script>
  <style>
  input[type='radio']:focus{
  	box-shadow:none;
  }
  a:HOVER{
  	text-decoration: none;
  	color:rgb(119, 114, 114);
  }
  .unit{
  	width:315px;
  }
  </style>
</head>	

<body>
 <header class="navbar navbar-dark" id="top" role="banner">
		<div class="container">
			<div class="navbar-header pull-left">
				<a href="${ctx}home" class="navbar-brand">
					<img src="${ctxPortal }/assets/icon/logo-white.png" alt="logo"></a>
			</div>
			<div class="pull-right login-and-share bdsharebuttonbox" style="position: relative;padding-right:135px;">
				<%-- <a href="${ctx}/portal/registerController/register">注册</a>
				<a href="#loginModal" data-toggle="login">登录</a> --%>
               <c:choose>
                  <c:when test="${empty lgUser}">
                    <a href="<%=basePath %>portal/registerController/register">注册</a>
                    <a id="login-btn" href="#loginModal" data-toggle="login">登录</a>
                  </c:when>
                  <c:otherwise>
                  	<div class="pull-left" style="margin-top: 9px;margin-right: 5px;position: relative;"><span class="badge" style="position: absolute; top: -5px; right: -15px; z-index: 1; "></span>
                    <img width="40px" height="40px" src="${ctx}${lgUser.pic}" alt="用户头像"/>
                    <i class="umark umark_b"></i></div>
                	<%-- <a href="javascript:void(0);" onclick="toUserCenter();" class="pull-left">
                       <c:choose>
							<c:when test="${fn:length(lgUser.username)>5 }">${fn:substring(lgUser.username,0,5) }...</c:when>
                           <c:otherwise>${lgUser.username}</c:otherwise>
                       </c:choose>
                    </a> --%>
                    <a target="_blank" href="${ctx}member/show/${lgUser.code}.html" class="pull-left">${lgUser.username }</a>
              		<a href="javascript:void(0);" onclick="logout();" class="pull-left">退出</a>
                  </c:otherwise>
               </c:choose>
					<div style="position: absolute;right:0;top: 0;">
					<a href="#" class="share weixin" data-cmd="weixin" title="分享到微信">微信</a>
					<a href="#" class="share weibo" data-cmd="tsina" title="分享到微博">微博</a>
					<a href="#" class="share qzone" data-cmd="qzone" title="分享到QQ空间">QQ空间</a>
					</div>
			</div>
		</div>
	</header>
		<!--banner-->
<div class="banner" style="margin-top: -23px;"><img style="max-width: 100%" src="<%=basePath%>wsds/img/banner.png"></div>
<!--视频列表-->
<div class="video-cont">
    <div class="video-h1">优秀参赛作品</div>
    <div class="mainer clearfix">
        <div class="unit">
            <a href="<%=basePath%>wsds/m-byydxz.html">
                <div class="unit-img"><img src="<%=basePath%>wsds/img/p1.jpg"></div>
                <h2>不一样的西藏</h2>

                <p>主演：费雁鹏</p>

                <p>导演：郭梁</p></a>
        </div>
        <div class="unit">
            <a href="<%=basePath%>wsds/m-lzdd.html">
                <div class="unit-img"><img src="<%=basePath%>wsds/img/p2.jpg"></div>
                <h2>林芝的冬</h2>

                <p>导演：房曦桐</p>

                <p>导演：房曦桐</p></a>
        </div>
        <div class="unit">
            <a href="<%=basePath%>wsds/m-ld.html">
                <div class="unit-img"><img src="<%=basePath%>wsds/img/p3.jpg"></div>
                <h2>龙达</h2>

                <p>主演：普布次仁 焦恩佳 文辰扬</p>

                <p>导演：孔君</p></a>
        </div>
        <div class="unit">
            <a href="<%=basePath%>wsds/m-lu.html">
                <div class="unit-img"><img src="<%=basePath%>wsds/img/p4.jpg"></div>
                <h2>路</h2>

                <p>主演：拉巴次仁 扎西次旦</p>

                <p>导演：刘士杨</p></a>
        </div>
        <div class="unit">
            <a href="<%=basePath%>wsds/m-sybzxls.html">
                <div class="unit-img"><img src="<%=basePath%>wsds/img/p5.jpg"></div>
                <h2>山月不知心里事</h2>

                <p>主演：索兰玉珍 多布杰</p>

                <p>导演：何思雨</p></a>
        </div>
        <div class="unit">
            <a href="<%=basePath%>wsds/m-syc.html">
               <div class="unit-img"><img src="<%=basePath%>wsds/img/p6.jpg"></div>
                <h2>酥油茶</h2>

                <p>主演：桑杰 央珍 张明月</p>

                <p>导演：刘士杨</p></a>
        </div>
        <div class="unit">
            <a href="<%=basePath%>wsds/m-tysqddf.html">
                <div class="unit-img"><img src="<%=basePath%>wsds/img/p7.jpg"></div>
                <h2>太阳升起的地方</h2>

                <p>主演：王联杨 韩秉铎</p>

                <p>导演：张力升</p></a>
        </div>
        <div class="unit">
            <a href="<%=basePath%>wsds/m-zx.html">
                <div class="unit-img"><img src="<%=basePath%>wsds/img/p8.jpg"></div>
                <h2>扎西</h2>

                <p>主演：罗海斌 于帅 杜卉 张苏 陈大爷 路飞</p>

                <p>导演：赵思淇 </p></a>
        </div>
        <div class="unit">
            <a href="<%=basePath%>wsds/m-zb.html">
                <div class="unit-img"><img src="<%=basePath%>wsds/img/p9.jpg"></div>
                <h2>卓布</h2>

                <p>主演：刘志豪 姚玮勇</p>

                <p>导演：张力升</p></a>
        </div>
    </div>
</div>
<script type="text/javascript">
  		var basePath_ = "${ctx}";
  		var basePathPortal = "${ctxPortal}";
  		
  		var activityCode_ = "${activity.code }";
  		var isEnd_ = "${isEnd}";
  		var OCS_ = "${OCS }";
  		
  		<%-- var share_activity_summary = "${activity.summary }"; --%>
		var share_activity_name = "${activity.name}";
		var share_activity_img = "${ctx}${activity.img}";
		
		var PROPERTY_TYPE_NUMBER = "${PROPERTY_TYPE_NUMBER}";
		var PROPERTY_TYPE_TEXT = "${PROPERTY_TYPE_TEXT}";
		var PROPERTY_TYPE_SELECT = "${PROPERTY_TYPE_SELECT}";
		
		var listEnrollForm_ = ${empty listEnrollFormJson?'[]':listEnrollFormJson};
  </script>
   <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
  <jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
  <script type="text/javascript" src="${ctx}portal/assets/js/activity/wsds1.js"></script>
</body>
</html>
