<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib   prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jstl/fmt_rt" %>   
<%
   	String path = request.getContextPath();
String port = ":" + request.getServerPort();
if ("80".equals(""+request.getServerPort())) {
	port = "";
}
	String basePath = request.getScheme() + "://" + request.getServerName() + port + path + "/";
	// String basePath = request.getScheme() + "://" + request.getServerName() + port + path + "/";
   	request.setAttribute("ctx", basePath);
   	request.setAttribute("ctxManage", basePath + "/manage/");
   	request.setAttribute("ctxPortal", basePath + "portal/"); //xiangwq
   %>
<!DOCTYPE HTML>
<html>
<head>
<title>天上西藏门户管理系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta name="viewport" content="width=device-width,initial-scale=1" />
<%--<meta http-equiv="X-UA-Compatible" content="IE=8" />--%>
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
<meta name="description" content="该管理系统用于设置和管理“天上西藏”门户网的显示内容及动态数据。" />
<link rel="shortcut icon"
	href="<%=basePath%>/manage/resources/img/other/favicon.ico" />
<link rel="bookmark"
	href="<%=basePath%>/manage/resources/img/other/favicon.ico" />
<link rel="stylesheet"
	href="<%=basePath%>/manage/resources/plugin/baseCSS-1.0.4.min.css" />
<link rel="stylesheet"
	href="<%=basePath%>/manage/resources/css/index.css" />
</head>
<body>
	<!-- header { -->
	<div class="header">
		<div class="header-top">
			<!-- logo -->
			<a href="admin-home.html" target="ifr_main" title="打开后台管理系统首页"
				class="toAdminHome">
				<div class="logo"></div>
				<div class="logo-title"></div>
			</a>
			<!-- 修改密码 & 退出系统 -->
			<div class="baseOperate">
				<a href="" target="_blank"> <i class="repwd"></i> <span>修改密码</span>
				</a> <a target="_self" class="adminExit"> <i class="exit"></i> <span>退出系统</span>
				</a>
			</div>

		</div>
		<div class="header-bottom">
			<!-- 管理员信息 -->
			<div class="adminInfo">
				<p>
					欢迎您：<span class="adminName"><c:out value="${userInfo.name}"></c:out></span>
				</p>
				<p>
					所属操作组：<span class="operGroup"><c:out value="${userInfo.role.name}"></c:out></span>
				</p>
			</div>
			<!-- main_nav -->
<!-- 			<div class="main_nav"> -->
<!-- 				<c:forEach var="programas" items="${topProgramas}"> -->
					<!-- 		href="${programas.programaUrl}"	 -->
<!-- 					<a target="ifr_main"><c:out value="${programas.programaName}"></c:out></a> -->
<!-- 				</c:forEach> -->
<!-- 			</div> -->
			<!-- main_nav -->
			<div class="main_nav">
				<a href="home/home.html" target="ifr_main">门户首页</a>
				<a href="travel/travel.html" target="ifr_main">游西藏</a>
				<a href="other/building.html" target="ifr_main">读西藏</a>
				<a href="see/see.html" target="ifr_main">看西藏</a>
				<a href="${ctx }web/activityController/showList" target="ifr_main">活动&专题</a>
				<a href="other/building.html" target="ifr_main">天上社区</a>
				<a href="other/building.html" target="ifr_main">骑行专区</a>
				<a href="other/building.html" target="ifr_main">系统设置</a>
			</div>
		</div>
    </div><!-- } header -->

    <!-- main { -->
    <div class="main">
        <!-- main_left { -->
        <div class="main_left">
        	
        	<!-- 导航游标 -->
        	<div class="navNonius"></div>
        	
			<!-- 门户首页 { -->
        	<div class="minor_nav">
        		<ul>
					<li>
						<a href="home/home.html" target="ifr_main">门户首页显示</a>
					</li>
					<li>
						<a href="${ctx }/web/homeController/getManageImg?programaCode=e43cb722-75d6-11e4-b6ce-005056a05bc9" target="ifr_main">Banner管理</a>
					</li>
					<li>
						<a href="${ctx }/web/homeController/getManageImg?programaCode=13173f79-75da-11e4-b6ce-005056a05bc9" target="ifr_main">推荐位管理</a>
					</li>
					<li>
						<a href="${ctx }/web/indexXzDtManagerController/saveUI" target="ifr_main">西藏动态列表管理</a>
					</li>
					<li>
						<a href="${ctx }/web/indexDzDtManagerController/saveUI" target="ifr_main">帖子列表管理</a>
					</li>
					<li>
						<a href="${ctx }/web/indexGlDtManagerController/saveUI" target="ifr_main">游西藏攻略管理</a>
					</li>
					<li>
						<a href="${ctx }/web/homeController/getManageImg?programaCode=1320eb90-75da-11e4-b6ce-005056a05bc9" target="ifr_main">景点管理</a>
					</li>
					<li>
						<a href="${ctx }/web/homeController/getManageImg?programaCode=1323f0e2-75da-11e4-b6ce-005056a05bc9" target="ifr_main">图说西藏管理</a>
					</li>
					<li>
						<a href="${ctx }/web/indexSpDtManagerController/saveUI" target="ifr_main">视频专区管理</a>
					</li>
					<li>
						<a href="${ctx }/web/homeController/getManageImg?programaCode=132a2285-75da-11e4-b6ce-005056a05bc9" target="ifr_main">读西藏管理</a>
					</li>
					<li>
						<a href="${ctx }/web/indexWhMgController/saveUI" target="ifr_main">西藏文化传播管理</a>
					</li>
					<li>
       					<a href="${ctx }/web/homeController/getManageImg?programaCode=13334a3a-75da-11e4-b6ce-005056a05bc9" target="ifr_main">浮窗管理</a>
					</li>
        		</ul>
        	</div><!-- } 门户首页 -->
        	
			<!-- 游西藏 { -->
        	<div class="minor_nav disp-n">
        		<ul>
					<li>
						<a href="travel/travel.html" target="ifr_main">游西藏首页显示</a>
						<i class="down"></i>
						<div>
							<a href="other/building.html" target="ifr_main">Banner管理</a>
							<a href="other/building.html" target="ifr_main">推荐位管理</a>
							<a href="other/building.html" target="ifr_main">热门景点管理</a>
							<a href="${ctx }/web/readTibetZhMgController/list" target="ifr_main">综合攻略管理</a>
							<a href="${ctx }/web/readTibetZjyMgController/list" target="ifr_main">自驾游攻略管理</a>
							<a href="${ctx }/web/readTibetQxMgController/list" target="ifr_main">骑行攻略管理</a>
							<a href="${ctx}/web/merchant/merchantList" target="ifr_main">商户管理</a>
						</div>
					</li>
					<li>
						<a target="ifr_main">攻略管理</a>
						<i class="right"></i>
						<div class="disp-n">
							<a href="${ctx }/web/readTibetSgCheckMgController/list" target="ifr_main">待审核的攻略</a>
							<a href="${ctx }/web/readTibetSgPassMgController/list" target="ifr_main">已审核的攻略</a>
						</div>
					</li>
					<li>
						<a target="ifr_main">目的地管理</a>
						<i class="right"></i>
						<div class="disp-n">
							<a href="travel/destination.html" target="ifr_main">目的地首页显示</a>
							<a href="other/building.html" target="ifr_main">目的地信息管理</a>
							<a href="other/building.html" target="ifr_main">景区列表页</a>
							<a href="${ctx}web/viewController/getViewPager" target="ifr_main">景点信息管理</a>
						</div>
					</li>
					<li>
						<a href="other/building.html" target="ifr_main">商户管理</a>
						<i class="right"></i>
						<div class="disp-n">
							<a href="${ctx}/web/merchant/merchantList" target="ifr_main">商户汇总页显示</a>
							<a href="other/building.html" target="ifr_main">商户类别管理</a>
							<a href="other/building.html" target="ifr_main">商户信息管理</a>
							<a href="other/building.html" target="ifr_main">团游信息管理</a>
							<a href="${ctx}/web/merchant/merchantList" target="ifr_main">商户列表页</a>
						</div>
					</li>
        		</ul>
        	</div><!-- } 游西藏 -->
        	
        	<!-- 读西藏 { -->
        	<div class="minor_nav disp-n">
        		<ul>
					<li>
						<a href="other/building.html" target="ifr_main">读西藏首页显示</a>
						<i class="down"></i>
						<div>
							<a href="read/banner.html" target="ifr_main">Banner管理</a>
							<a href="read/posid.html" target="ifr_main">推荐位管理</a>
							<a href="read/dynamicList.html" target="ifr_main">西藏动态显示管理</a>
							<a href="read/infoDisplay.html" target="ifr_main">信息显示管理</a>
							<a href="read/cultural.html" target="ifr_main">西藏文化传播显示管理</a>
						</div>
					</li>
					<li>
						<a href="read/infoManage.html" target="ifr_main">读西藏信息管理</a>
						<i class="right"></i>
						<div class="disp-n">
							<a href="read/info-creat.html" target="ifr_main">新建读西藏信息</a>
						</div>
					</li>
					<li>
						<a href="read/cultural-trans.html" target="ifr_main">西藏文化传播信息管理</a>
						<i class="right"></i>
						<div class="disp-n">
							<a href="read/cultural-info-creat.html" target="ifr_main">新建西藏文化传播</a>
						</div>
					</li>
        		</ul>
        	</div><!-- } 读西藏 -->
        	
        	<!-- 看西藏 { -->
        	<div class="minor_nav disp-n">
        		<ul>
					<li>
						<a href="see/see.html" target="ifr_main">看西藏首页显示</a>
						<i class="down"></i>
						<div>
							<a href="other/building.html" target="ifr_main">Banner管理</a>
							<a href="other/building.html" target="ifr_main">图说西藏显示管理</a>
							<a href="other/building.html" target="ifr_main">视频显示管理</a>
						</div>
					</li>
					<li>
						<a href="other/building.html" target="ifr_main">图说西藏信息管理</a>
						<i class="right"></i>
						<div class="disp-n">
							<a href="other/building.html" target="ifr_main">图说西藏列表页</a>
							<a href="other/building.html" target="ifr_main">图说信息添加</a>
							<a href="other/building.html" target="ifr_main">图片信息修改</a>
						</div>
					</li>
					<li>
						<a href="other/building.html" target="ifr_main">视频专区信息管理</a>
						<i class="right"></i>
						<div class="disp-n">
							<a href="other/building.html" target="ifr_main">视频信息添加</a>
							<a href="other/building.html" target="ifr_main">视频信息修改</a>
						</div>
					</li>
        		</ul>
        	</div><!-- } 看西藏 -->
        	
        	<!-- 活动&专题 { -->
        	<div class="minor_nav disp-n">
        		<ul>
					<li>
						<a href="${ctx }web/activityBannerManageController/forActivityManage" target="ifr_main">活动&专题首页显示</a>
						<i class="down"></i>
						<div>
							<a href="${ctx }web/activityBannerManageController/forActivityBannerManage" target="ifr_main">Banner管理</a>
							<a href="${ctx }web/activityBannerManageController/forSpecailShowManage" target="ifr_main">专题显示管理</a>
						</div>
					</li>
					<li>
						<a>活动&专题信息管理</a>
						<i class="right"></i>
						<div class="disp-n">
							<a href="${ctx }web/activityController/showList" target="ifr_main">活动信息管理</a>
							<a href="${ctx }web/specialController/showSpecialList" target="ifr_main">专题信息管理</a>
						</div>
					</li>
        		</ul>
        	</div><!-- } 活动&专题 -->
        	
        	<!-- 天上社区 { -->
        	<div class="minor_nav disp-n">
        		<ul>
					<li>
						<a href="${ctx}/web/tssq/index" target="ifr_main">天上社区首页显示</a>
						<i class="down"></i>
						<div>
							<a href="other/building.html" target="ifr_main">Banner管理</a>
							<a href="${ctx }/web/bbsReplyControoler/saveUI" target="ifr_main">最赞回复显示管理</a>
						</div>
					</li>
					<li>
						<a href="${ctx}/web/tssq/plateList" target="ifr_main">论坛版块管理</a>
						<i class="right"></i>
						<div>
							<a href="#">板块列表页</a>
						</div>
					</li>
					<li>
						<a href="other/building.html" target="ifr_main">帖子&回复管理</a>
						<i class="right"></i>
						<div class="disp-n">
							<a href="${ctx}/web/tssq/uncheckedPostList" target="ifr_main">待审核的帖子&回复</a>
							<a href="${ctx}/web/passPostController/searchPost" target="ifr_main">已审核的帖子&回复</a>
						</div>
					</li>
        		</ul>
        	</div><!-- } 天上社区 -->
        	
        	<!-- 骑行专区 { -->
        	<div class="minor_nav disp-n">
        		<ul>
					<li>
						<a href="other/building.html" target="ifr_main">骑行专区首页显示</a>
					</li>
        		</ul>
        	</div><!-- } 骑行专区 -->
        	
        	<!-- 系统设置 { -->
        	<div class="minor_nav disp-n">
        		<ul>
					<li>
						<a href="other/building.html" target="ifr_main">会员管理</a>
					</li>
					<li>
						<a href="other/building.html" target="ifr_main">数据统计</a>
						<i class="right"></i>
						<div class="disp-n">
							<a href="other/building.html" target="ifr_main">系统数据统计</a>
							<a href="other/building.html" target="ifr_main">运营数据统计</a>
							<a href="other/building.html" target="ifr_main">销售数据统计（外链）</a>
						</div>
					</li>
					<li>
						<a href="other/building.html" target="ifr_main">敏感词管理</a>
					</li>
					<li>
						<a href="other/building.html" target="ifr_main">角色权限管理</a>
						<i class="right"></i>
						<div class="disp-n">
							<a href="other/building.html" target="ifr_main">角色管理</a>
							<a href="other/building.html" target="ifr_main">系统操作员管理</a>
						</div>
					</li>
					<li>
						<a href="other/building.html" target="ifr_main">个人账户管理</a>
					</li>
					<li>
						<a href="other/building.html" target="ifr_main">积分管理</a>
					</li>
					<li>
						<a href="other/building.html" target="ifr_main">运营管理</a>
						<i class="right"></i>
						<div class="disp-n">
							<a href="other/building.html" target="ifr_main">其他页面管理</a>
							<a href="other/building.html" target="ifr_main">评价&回复管理</a>
						</div>
					</li>
        		</ul>
        	</div><!-- } 系统设置 -->
        	
        </div><!-- } main_left -->

        <!-- main_right { -->
        <div class="main_right">
			<iframe id="ifr_main" name="ifr_main" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" class="ifr_main" src="admin-home.html"></iframe>
        </div><!-- } main_right-->
    </div><!-- } main -->

    <!-- footer { -->
    <div class="footer">
		 © copyright:2014 由成都睿峰科技有限公司研发
    </div><!-- } footer -->
	
	<!-- 弹出框层 -->
	<div class="popupLayer">
		<div class="maskLayer">
			<div class="popupBox">
				<div class="popupBox-content">
					<!--<span class="icon-info2"></span>确定退出，返回到登录界面！-->
				</div>
				<div class="popupBox-button">
					<!--<a class="mr30">确 定</a>
					<a>取 消</a>-->
				</div>
			</div>
		</div>
	</div>

    <!-- JS引用部分 -->
    <script src="../resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="../resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="../resources/js/index.js" type="text/javascript"></script>
	
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
	<!--
	-- 例子：
	-- <link rel="prefetch" href="http://www.baidu.com">
	-- <link rel="prefetch" href="userManage/userInfoManage.html">
	-->
</body>
</html>
