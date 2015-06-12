<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>门户首页</title>
	<%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/home.css" />
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>
<body>
    <!-- main { -->
    <div class="main">
    	<!-- 页面位置-->
    	<div class="location">
    		<label>您当前位置为:</label>
    		<span><a>门户首页</a> -</span>
    		<span><a>门户首页显示</a></span>
    	</div>
    	
    	<!-- 模块管理 { -->
    	<div class="muduleIndex">
    		<a href="${ctx }/web/homeController/getManageImg?programaCode=e43cb722-75d6-11e4-b6ce-005056a05bc9" target="_self" class="all-inline mbgc_0">Banner管理</a>
    		<a href="${ctx }/web/homeController/getManageImg?programaCode=13173f79-75da-11e4-b6ce-005056a05bc9" target="_self" class="most-inline mbgc_1">推荐位管理</a>
    		<a href="${ctx }/web/indexXzDtManagerController/saveUI" target="_self" class="less-inline mbgc_2">西藏动态列表管理</a>
    		<a href="${ctx }/web/indexDzDtManagerController/saveUI" target="_self" class="most-inline mbgc_3">帖子列表管理</a>
    		<div class="less-inline">
				<a href="${ctx }/web/indexGlDtManagerController/saveUI" target="_self" class="less-inline-halves mbgc_4 mb12">游西藏攻略管理</a>
				<a href="${ctx }/web/homeController/getManageImg?programaCode=1320eb90-75da-11e4-b6ce-005056a05bc9" target="_self" class="less-inline-halves mbgc_5">景点管理</a>
   			</div>
   			<a href="${ctx }/web/homeController/getManageImg?programaCode=1323f0e2-75da-11e4-b6ce-005056a05bc9" target="_self" class="most-inline mbgc_6">图说西藏管理</a>
   			<a href="${ctx }/web/indexSpDtManagerController/saveUI" target="_self" class="less-inline mbgc_7">视频专区管理</a>
   			<a href="${ctx }/web/homeController/getManageImg?programaCode=132a2285-75da-11e4-b6ce-005056a05bc9" target="_self" class="all-inline mbgc_8">读西藏管理</a>
   			<a href="${ctx }/web/indexWhMgController/saveUI" target="_self" class="all-inline mbgc_9">西藏文化传播管理</a>
   			<a href="${ctx }/web/homeController/getManageImg?programaCode=13334a3a-75da-11e4-b6ce-005056a05bc9" target="_self" class="float-block mbgc_8">
   				<span>浮<br>窗<br>管<br>理</span>
   			</a>
    	</div><!-- } 模块管理 -->
    </div><!-- } main -->

    <!-- JS引用部分 -->
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/home/home.js" type="text/javascript"></script>
    <script type="text/javascript">
		// 设置“浮窗管理”的位置
		setpos_floatBlock();
		$(window).resize(function() {
			setpos_floatBlock();
		});
		function setpos_floatBlock() {
			var allInline = $(".all-inline:eq(0)"),
				leftGap = allInline.offset().left,
				allInline_W = allInline.width(),
				result_position = leftGap + allInline_W;
			$(".float-block ").css("left", (result_position + 12) + "px");
		}
	</script>
	
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>