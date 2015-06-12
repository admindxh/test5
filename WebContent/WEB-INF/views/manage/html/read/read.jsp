<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>读西藏首页</title>
    <script src="../../resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="../../resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="../../resources/css/base.css" />
    <link rel="stylesheet" href="../../resources/css/read/read.css" />
    <script src="../../resources/plugin/respond.min.js" type="text/javascript"></script>
</head>
<body>
    <!-- main { -->
    <div class="main">
    	<!-- 页面位置-->
    	<%--<div class="location">
    		<label>您当前位置为:</label>
			<span><a>读西藏</a> -</span>
    		<span><a href="#" target="_self">读西藏首页显示</a></span>
    	</div>
    	
    	--%><!-- 模块管理 { -->
    	<div class="muduleIndex">
    	<rimiTag:perm url="read/banner.html">
    		<a href="../read/banner.html" target="_self" class="all-inline mbgc_1">Banner管理</a>
    		</rimiTag:perm>
    		<rimiTag:perm url="read/posid.html">
    		<a href="../read/posid.html" target="_self" class="most-inline mbgc_5">推荐位管理</a>
    		</rimiTag:perm>
    		<rimiTag:perm url="read/infoDisplay.html">
    		<a href="../read/infoDisplay.html" target="_self" class="most-inline mbgc_9">读西藏信息显示管理</a>
    		</rimiTag:perm>
    		<div class="less-inline merchant">
    		<rimiTag:perm url="read/dynamicList.html">
				<a href="../read/dynamicList.html" target="_self" class="all-inline merchant mbgc_6">
  					<span>西<br>藏<br>动<br>态<br>显<br>示<br>管<br>理</span>
  				</a>
  				</rimiTag:perm>
   			</div>
   			<rimiTag:perm url="read/cultural.html">
			<a href="../read/cultural.html" target="_self" class="all-inline mbgc_2">西藏文化传播显示管理</a>
			</rimiTag:perm>
    	</div><!-- } 模块管理 -->
    </div><!-- } main -->

    <!-- JS引用部分 -->
    <script src="../../resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="../../resources/js/base.js" type="text/javascript"></script>
	
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>