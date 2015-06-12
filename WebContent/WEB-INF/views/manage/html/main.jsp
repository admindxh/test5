<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<%
	String path = request.getContextPath();
	String port = ":" + request.getServerPort();
	if ("80".equals("" + request.getServerPort())) {
		port = "";
	}
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + port + path + "/";
	request.setAttribute("ctx", basePath);
	request.setAttribute("ctxManage", basePath + "/manage/");
	request.setAttribute("ctxPortal", basePath + "portal/"); //xiangwq
%>
<%
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
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
				<a href="${ctx }/manage/html/admin-home" target="ifr_main"
					title="打开后台管理系统首页" class="toAdminHome">
					<div class="logo"></div>
					<div class="logo-title"></div> </a>
				<!-- 修改密码 & 退出系统 -->
				<div class="baseOperate">
					<a href="gotoNewPwd" target="ifr_main"> <i class="repwd"></i> <span>修改密码</span>
					</a>
					<a target="_self" class="adminExit"> <i class="exit"></i> <span>退出系统</span>
					</a>
				</div>

			</div>
			<div class="header-bottom">
				<!-- 管理员信息 -->
				<div class="adminInfo">
					<p>
						欢迎您：
						<span class="adminName"><c:out value="${userInfo.name}"></c:out>
						</span>
					</p>
					<p>
						所属用户组：
						<span class="operGroup"><c:out
								value="${userInfo.role.name}"></c:out> </span>
					</p>
				</div>
				<%-- 管理首页 --%>
				<div class="adminHome">
					
						<a href="${ctx}/manage/html/admin-home" target="ifr_main"> <!--管理首页-->
						</a>
					
				</div>
				<%-- 主导航 --%>
				<div class="main_nav">

					<rimiTag:perm url="web/index/intoPage?path=manage/html/home/home">
						<a href="${ctx}/web/index/intoPage?path=manage/html/home/home" target="ifr_main">门户首页</a>
					</rimiTag:perm>
					<rimiTag:perm
						url="web/index/intoPage?path=manage/html/travel/travel">
						<a href="${ctx}/web/index/intoPage?path=manage/html/travel/travel" target="ifr_main">游西藏</a>
					</rimiTag:perm>
					<rimiTag:perm url="read/read.html">
						<a href="read/read.html" target="ifr_main">读西藏</a>
					</rimiTag:perm>
					<rimiTag:perm url="see/see.html">
						<a href="see/see.html" target="ifr_main">看西藏</a>
					</rimiTag:perm>
					<rimiTag:perm
						url="web/activityBannerManageController/forActivityManage">
						<a href="${ctx }web/activityBannerManageController/forActivityManage" target="ifr_main">活动&专题</a>
					</rimiTag:perm>
					<rimiTag:perm url="web/index/intoPage?path=manage/html/bbs/bbs">
						<a href="${ctx}/web/index/intoPage?path=manage/html/bbs/bbs" target="ifr_main">天上社区</a>
					</rimiTag:perm>
					<rimiTag:perm url="web/ride/lists">
						<%-- <a data-index="6" href="${ctx}web/homeController/cycle" target="ifr_main">骑行专区</a> --%>
						<a href="${ctx}web/index/intoPage?path=manage/html/ride/ride" target="ifr_main">骑行专区</a>
					</rimiTag:perm>
					<rimiTag:perm url="web/member/list">
						<a href="${ctx}/web/index/intoPage?path=manage/html/settings/settings" target="ifr_main">系统设置</a>
					</rimiTag:perm>
				</div>
			</div>
		</div>
		<!-- } header -->

		<!-- main { -->
		<div class="main">
			<!-- main_left { -->
			<div class="main_left">

				<!-- 导航游标 -->
				<div class="navNonius"></div>
				<rimiTag:perm url="web/index/intoPage?path=manage/html/home/home">
					<!-- 门户首页 { -->
					<div class="minor_nav">
						<ul>
							<rimiTag:perm url="home/home.html">
								<li>
									<a href="${ctx}/web/index/intoPage?path=manage/html/home/home"
										target="ifr_main">门户首页显示</a>
								</li>
							</rimiTag:perm>
							<rimiTag:perm
								url="web/homeController/getManageImg?programaCode=e43cb722-75d6-11e4-b6ce-005056a05bc9">
								<li>
									<a
										href="${ctx }/web/homeController/getManageImg?programaCode=e43cb722-75d6-11e4-b6ce-005056a05bc9"
										target="ifr_main">Banner管理</a>
								</li>
							</rimiTag:perm>
							<rimiTag:perm
								url="web/homeController/getManageImg?programaCode=13173f79-75da-11e4-b6ce-005056a05bc9">
								<li>
									<a
										href="${ctx }/web/homeController/getManageImg?programaCode=13173f79-75da-11e4-b6ce-005056a05bc9"
										target="ifr_main">推荐位管理</a>
								</li>
							</rimiTag:perm>
							<rimiTag:perm url="web/indexXzDtManagerController/saveUI">
								<li>
									<a href="${ctx }/web/indexXzDtManagerController/saveUI"
										target="ifr_main">西藏动态列表管理</a>
								</li>
							</rimiTag:perm>
							<rimiTag:perm url="web/indexDzDtManagerController/saveUI">
								<li>
									<a href="${ctx }/web/indexDzDtManagerController/saveUI"
										target="ifr_main">帖子列表管理</a>
								</li>
							</rimiTag:perm>
							<rimiTag:perm url="web/indexGlDtManagerController/saveUI">
								<li>
									<a href="${ctx }/web/indexGlDtManagerController/saveUI"
										target="ifr_main">游西藏攻略管理</a>
								</li>
							</rimiTag:perm>
							<rimiTag:perm
								url="web/homeController/getManageImg?programaCode=1320eb90-75da-11e4-b6ce-005056a05bc9">
								<li>
									<a
										href="${ctx }/web/homeController/getManageImg?programaCode=1320eb90-75da-11e4-b6ce-005056a05bc9"
										target="ifr_main">景点管理</a>
								</li>
							</rimiTag:perm>
							<rimiTag:perm
								url="web/homeController/getManageImg?programaCode=1323f0e2-75da-11e4-b6ce-005056a05bc9">
								<li>
									<a
										href="${ctx }/web/homeController/getManageImg?programaCode=1323f0e2-75da-11e4-b6ce-005056a05bc9"
										target="ifr_main">图说西藏管理</a>
								</li>
							</rimiTag:perm>
							<rimiTag:perm url="web/indexSpDtManagerController/saveUI">
								<li>
									<a href="${ctx }/web/indexSpDtManagerController/saveUI"
										target="ifr_main">视频专区管理</a>
								</li>
							</rimiTag:perm>
							<rimiTag:perm
								url="web/homeController/getManageImg?programaCode=132a2285-75da-11e4-b6ce-005056a05bc9">
								<li>
									<a
										href="${ctx }/web/homeController/getManageImg?programaCode=132a2285-75da-11e4-b6ce-005056a05bc9"
										target="ifr_main">读西藏管理</a>
								</li>
							</rimiTag:perm>
							<rimiTag:perm url="web/indexWhMgController/saveUI">
								<li>
									<a href="${ctx }/web/indexWhMgController/saveUI"
										target="ifr_main">西藏文化传播管理</a>
								</li>
							</rimiTag:perm>
							<rimiTag:perm
								url="web/homeController/getManageImg?programaCode=13334a3a-75da-11e4-b6ce-005056a05bc9">
								<li>
									<a
										href="${ctx }/web/homeController/getManageImg?programaCode=13334a3a-75da-11e4-b6ce-005056a05bc9"
										target="ifr_main">浮窗管理</a>
								</li>
							</rimiTag:perm>
						</ul>
					</div>
				</rimiTag:perm>
				<!-- } 门户首页 -->
				<rimiTag:perm
					url="web/index/intoPage?path=manage/html/travel/travel">
					<!-- 游西藏 { -->
					<div class="minor_nav disp-n">
						<ul>
							<rimiTag:perm
								url="web/index/intoPage?path=manage/html/travel/travel">
								<li>
									<a
										href="${ctx}/web/index/intoPage?path=manage/html/travel/travel"
										target="ifr_main">游西藏首页显示</a>
									<i class="down"></i>
									<div>
										<rimiTag:perm
											url="web/travelController/getManageImg?programaCode=1256se5-qe2c-52e4-a6ce-11505ca05dc9">
											<a
												href="${ctx }/web/travelController/getManageImg?programaCode=1256se5-qe2c-52e4-a6ce-11505ca05dc9"
												target="ifr_main">Banner管理</a>
										</rimiTag:perm>
										<rimiTag:perm
											url="web/travelController/getManageImg?programaCode=bc22ed19-b2bc-42cd-a8c9-beb96e25ed89">
											<a
												href="${ctx }/web/travelController/getManageImg?programaCode=bc22ed19-b2bc-42cd-a8c9-beb96e25ed89"
												target="ifr_main">推荐位管理</a>
										</rimiTag:perm>
										<rimiTag:perm
											url="web/travelController/getManageImg?programaCode=25b327a5-7e8c-12e4-b6ce-005056b896a3">
											<a
												href="${ctx }/web/travelController/getManageImg?programaCode=25b327a5-7e8c-12e4-b6ce-005056b896a3"
												target="ifr_main">热门景点管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="web/readTibetZhMgController/list">
											<a href="${ctx }/web/readTibetZhMgController/list"
												target="ifr_main">综合攻略管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="web/readTibetZjyMgController/list">
											<a href="${ctx }/web/readTibetZjyMgController/list"
												target="ifr_main">自驾游攻略管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="web/readTibetQxMgController/list">
											<a href="${ctx }/web/readTibetQxMgController/list"
												target="ifr_main">骑行攻略管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="web/merchant/gotoMerchantManage">
											<a href="${ctx}/web/merchant/gotoMerchantManage"
												target="ifr_main">商户管理</a>
										</rimiTag:perm>
									</div>
								</li>
							</rimiTag:perm>
							<rimiTag:perm url="default/strategy/manage">
								<li>
									<a target="ifr_main">攻略管理</a>
									<i class="right"></i>
									<div class="disp-n">
										<rimiTag:perm url="web/readTibetSgCheckMgController/list">
											<a
												href="${ctx }/web/readTibetSgCheckMgController/list?menu=1"
												target="ifr_main">待审核的攻略</a>
										</rimiTag:perm>
										<rimiTag:perm url="web/readTibetSgPassMgController/list">
											<a href="${ctx }/web/readTibetSgPassMgController/list?menu=1"
												target="ifr_main">已审核的攻略</a>
										</rimiTag:perm>
									</div>
								</li>
							</rimiTag:perm>
							<rimiTag:perm url="default/destination/manage">
								<li>
									<a target="ifr_main">目的地管理</a>
									<i class="right"></i>
									<div class="disp-n">
										<rimiTag:perm
											url="web/destinationController/getDestinationPager">
											<a
												href="${ctx }/web/destinationController/getDestinationPager"
												target="ifr_main">目的地信息管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="web/viewController/listView">
											<a href="${ctx}/web/viewController/listView"
												target="ifr_main">景点信息管理</a>
										</rimiTag:perm>
									</div>
								</li>
							</rimiTag:perm>
							<rimiTag:perm url="default/merchant/manage">
								<li>
									<a href="javascript:;" target="ifr_main">商户管理</a>
									<i class="right"></i>
									<div class="disp-n">
										<rimiTag:perm url="web/merchant/banner">
											<a href="${ctx}/web/merchant/banner" target="ifr_main">商户汇总页显示</a>
										</rimiTag:perm>
										<rimiTag:perm url="web/merchant/getMerchantTypeList">
											<a href="${ctx}/web/merchant/getMerchantTypeList"
												target="ifr_main">商户类别管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="web/merchant/merchantList">
											<a href="${ctx}/web/merchant/merchantList" target="ifr_main">商户信息管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="web/groupTravel/searchGroupTravel">
											<a href="${ctx}/web/groupTravel/searchGroupTravel"
												target="ifr_main">团游信息管理</a>
										</rimiTag:perm>
									</div>
								</li>
							</rimiTag:perm>
						</ul>
					</div>
				</rimiTag:perm>
				<!-- } 游西藏 -->
				<rimiTag:perm url="read/read.html">
					<!-- 读西藏 { -->
					<div class="minor_nav disp-n">
						<ul>
							<rimiTag:perm url="read/read.html">
								<li>
									<a href="read/read.html" target="ifr_main">读西藏首页显示</a>
									<i class="down"></i>
									<div>
										<rimiTag:perm url="read/banner.html">
											<a href="read/banner.html" target="ifr_main">Banner管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="read/posid.html">
											<a href="read/posid.html" target="ifr_main">推荐位管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="read/dynamicList.html">
											<a href="read/dynamicList.html" target="ifr_main">西藏动态显示管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="read/infoDisplay.html">
											<a href="read/infoDisplay.html" target="ifr_main">读西藏信息显示管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="read/cultural.html">
											<a href="read/cultural.html" target="ifr_main">西藏文化传播显示管理</a>
										</rimiTag:perm>
									</div>
								</li>
							</rimiTag:perm>
							<rimiTag:perm url="read/infoManage.html">
								<li>
									<a href="read/infoManage.html" target="ifr_main">读西藏信息管理</a>
								</li>
							</rimiTag:perm>


							<rimiTag:perm url="read/cultural-trans.html">
								<li>
									<a href="read/cultural-trans.html" target="ifr_main">西藏文化传播信息管理</a>
								</li>
							</rimiTag:perm>

						</ul>
					</div>
				</rimiTag:perm>
				<!-- } 读西藏 -->
				<!-- 看西藏 { -->
				<rimiTag:perm url="see/see.html">
					<div class="minor_nav disp-n">
						<ul>
							<rimiTag:perm url="see/see.html">
								<li>
									<a href="see/see.html" target="ifr_main">看西藏首页显示</a>
									<i class="down"></i>
									<div>
										<rimiTag:perm url="web/mutiController/banner">
											<a href="${ctx }web/mutiController/banner" target="ifr_main">Banner管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="web/mutiController/showMuti">
											<a href="${ctx }/web/mutiController/showMuti"
												target="ifr_main">图说西藏显示管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="see/video-display.html">
											<a href="see/video-display.html" target="ifr_main">视频显示管理</a>
										</rimiTag:perm>
									</div>
								</li>
							</rimiTag:perm>
							<rimiTag:perm url="web/mutiController/getMutiList">
								<li>
									<a href="${ctx }/web/mutiController/getMutiList"
										target="ifr_main">图说西藏信息管理</a>
								</li>
							</rimiTag:perm>

							<rimiTag:perm url="see/video-infomessage.html">
								<li>
									<a href="see/video-infomessage.html" target="ifr_main">视频专区信息管理</a>
								</li>
							</rimiTag:perm>

						</ul>
					</div>
				</rimiTag:perm>
				<!-- } 看西藏 -->
				<rimiTag:perm
					url="web/activityBannerManageController/forActivityManage">
					<!-- 活动&专题 { -->
					<div class="minor_nav disp-n">
						<ul>
							<rimiTag:perm
								url="web/activityBannerManageController/forActivityManage">
								<li>
									<a
										href="${ctx }web/activityBannerManageController/forActivityManage"
										target="ifr_main">活动&专题首页显示</a>
									<i class="down"></i>

									<div>
										<rimiTag:perm
											url="web/activityBannerManageController/forActivityBannerManage">
											<a
												href="${ctx }web/activityBannerManageController/forActivityBannerManage"
												target="ifr_main">Banner管理</a>
										</rimiTag:perm>
										<rimiTag:perm
											url="web/activityBannerManageController/forSpecailShowManage">
											<a
												href="${ctx }web/activityBannerManageController/forSpecailShowManage"
												target="ifr_main">专题显示管理</a>
										</rimiTag:perm>
									</div>
								</li>
								</rimiTag:perm>
								<rimiTag:perm url="web/activityController/showList">
								<li>
									<a>活动&专题信息管理</a>
									<i class="right"></i>
									<div class="disp-n">
										<rimiTag:perm url="web/activityController/showList">
											<a href="${ctx }web/activityController/showList"
												target="ifr_main">活动信息管理</a>
										</rimiTag:perm>
										<rimiTag:perm url="web/specialController/showSpecialList">
											<a href="${ctx }web/specialController/showSpecialList"
												target="ifr_main">专题信息管理</a>
										</rimiTag:perm>
									</div>
								</li>
							</rimiTag:perm>
						</ul>
					</div>
				</rimiTag:perm>
				<!-- } 活动&专题 -->
				<rimiTag:perm url="web/index/intoPage?path=manage/html/bbs/bbs">
					<!-- 天上社区 { -->
					<div class="minor_nav disp-n">
						<ul>
							<rimiTag:perm url="web/tssq/index">
								<li>
									<a href="${ctx}/web/tssq/index" target="ifr_main">天上社区首页显示</a>

									<i class="down"></i>
									<div>
										<%--<rimiTag:perm url="web/tssq/banner">
											<a href="${ctx}/web/tssq/banner" target="ifr_main">Banner管理</a>
										</rimiTag:perm>--%>
										<rimiTag:perm url="web/bbsReplyControoler/saveUI">
											<a href="${ctx }/web/bbsReplyControoler/saveUI"
												target="ifr_main">最赞回复显示管理</a>
										</rimiTag:perm>
									</div>
								</li>
									<rimiTag:perm url="web/tssq/plateList">
								<li>
										<a href="${ctx}/web/tssq/plateList" target="ifr_main">论坛版块管理</a>
										<i class="right"></i>
										<div>
											<a href="${ctx}/web/tssq/plateList" target="ifr_main">版块列表页</a>
										</div>
								</li>
								</rimiTag:perm>
							<li>
								<rimiTag:perm url="default/post$reply/manage">
									<a href="javascript:;" target="ifr_main">帖子&回复管理</a>
								</rimiTag:perm>
								<i class="right"></i>
								<div class="disp-n">
									<rimiTag:perm url="web/tssq/uncheckedPostList">
										<a href="${ctx}/web/tssq/uncheckedPostList" target="ifr_main">待审核的帖子&回复</a>
									</rimiTag:perm>
									<rimiTag:perm url="web/passPostController/searchPost">
										<a href="${ctx}/web/passPostController/searchPost"
											target="ifr_main">已审核的帖子&回复</a>
									</rimiTag:perm>
								</div>
							</li>
				</rimiTag:perm>
				</ul>
			</div>
			</rimiTag:perm>
			<!-- } 天上社区 -->
				<rimiTag:perm url="web/ride/lists">
				<!-- 骑行专区 { -->
				<div class="minor_nav disp-n">
					<ul>
						<rimiTag:perm url="web/ridecd/list">
							<li>
								<a href="${ctx}web/index/intoPage?path=manage/html/ride/ride" target="ifr_main">骑行专区首页</a>
								<i class="down"></i>
								<div>
								    <rimiTag:perm url="web/homeController/getManageImg?programaCode=er5tyq3h632-75e6-11e4-byce-005ajya05bc9">
									  <a href="${ctx }/web/homeController/getManageImg?programaCode=er5tyq3h632-75e6-11e4-byce-005ajya05bc9" target="ifr_main">banner管理</a>
									</rimiTag:perm>
									<rimiTag:perm url="web/ridecommon/list">
										<a href="${ctx }/web/ridecommon/list" target="ifr_main">骑行首页相关推荐</a>
									</rimiTag:perm>
								</div>
							</li>
						</rimiTag:perm>
						<rimiTag:perm url="web/ride/zb">
							<li>
								<a target="ifr_main">装备推荐</a>
								<i class="right"></i>
								<div class="disp-n">
									<rimiTag:perm url="web/homeController/getManageImg?programaCode=er5gh3hl32-75e6-11e4-byce-005a56a05bc9">
											<a
											href="${ctx }/web/homeController/getManageImg?programaCode=er5gh3hl32-75e6-11e4-byce-005a56a05bc9"
											target="ifr_main">骑行装备首页</a>
									</rimiTag:perm>
									<rimiTag:perm url="cycling/types">
										<a href="cycling/types" target="ifr_main">装备类型管理</a>
									</rimiTag:perm>
									<rimiTag:perm url="web/equip/list">
										<a href="${ctx}web/equip/list" target="ifr_main">装备管理</a>
									</rimiTag:perm>
								</div>
							</li>
						</rimiTag:perm>
						<rimiTag:perm url="web/service/listsite">
							<li>
								<a target="ifr_main">星级服务</a>
								<i class="right"></i>
								<div class="disp-n">
								<rimiTag:perm url="web/rideLine/forRideLineList">
									<a href="${ctx}web/rideLine/forRideLineList" target="ifr_main">骑行路线管理</a>
									</rimiTag:perm>
									<rimiTag:perm url="web/serviceSite/list">
										<a href="${ctx}web/serviceSite/list" target="ifr_main">骑行路线站点管理</a>
									</rimiTag:perm>
								</div>
							</li>
						</rimiTag:perm>
						<rimiTag:perm url="web/common-order/page">
								<li>
									<a href="${ctx}web/common-order/page" target="ifr_main">订单管理</a>
								</li>
						</rimiTag:perm>
					</ul>
				</div>
				</rimiTag:perm>
			
			<!-- } 骑行专区 -->
		<rimiTag:perm url="web/member/list">
			<!-- 系统设置 { -->
				<div class="minor_nav disp-n">
					<ul>
						<rimiTag:perm url="web/member/listmanager">
							<li>
								<a href="${ctx }web/member/list" target="ifr_main">会员管理</a>
							</li>
						</rimiTag:perm>
						<rimiTag:perm url="default/shujutongji">
							<li>
								<a>数据统计</a>
								<i class="right"></i>
								<div class="disp-n">
									<rimiTag:perm
										url="web/systemController/intoSystemStatic?menu=1">
										<a href="${ctx}/web/systemController/intoSystemStatic?menu=1"
											target="ifr_main">系统数据统计</a>
									</rimiTag:perm>
									<rimiTag:perm
										url="web/activityStatisController/intoActivityStatis?menu=1">
										<a
											href="${ctx }/web/activityStatisController/intoActivityStatis?menu=1"
											target="ifr_main">活动统计</a>
									</rimiTag:perm>
									<rimiTag:perm
										url="web/desAndViewStatisController/intoDesAndViewStatis?menu=1">
										<a
											href="${ctx}/web/desAndViewStatisController/intoDesAndViewStatis?menu=1"
											target="ifr_main">目的地&景点统计</a>
									</rimiTag:perm>
									<rimiTag:perm
										url="web/frontContentStatisController/intoFrontAderStatis?menu=1">
										<a
											href="${ctx }/web/frontContentStatisController/intoFrontAderStatis?menu=1"
											target="ifr_main">广告统计</a>
									</rimiTag:perm>
									<rimiTag:perm
										url="web/frontContentStatisController/intoFrontContentStatis?menu=1">
										<a
											href="${ctx }/web/frontContentStatisController/intoFrontContentStatis?menu=1"
											target="ifr_main">内容统计</a>
									</rimiTag:perm>
									<rimiTag:perm
										url="web/frontContentStatisController/intoFrontMerchantStatis?menu=1">
										<a
											href="${ctx }/web/frontContentStatisController/intoFrontMerchantStatis?menu=1"
											target="ifr_main">销售数据统计（外链）</a>
									</rimiTag:perm>
								</div>
							</li>
						</rimiTag:perm>
						<rimiTag:perm url="web/badwords/list">
							<li>
								<a href="${ctx }web/badwords/list" target="ifr_main">敏感词管理</a>
							</li>
						</rimiTag:perm>
						<rimiTag:perm url="web/role/roleList">
							<li>
								<a>角色权限管理</a>
								<i class="right"></i>
								<div class="disp-n">
									<a href="${ctx}/web/role/roleList" target="ifr_main">角色管理</a>
									<a href="${ctx}/manage/html/searchUser" target="ifr_main">系统操作员管理</a>
								</div>
							</li>
						</rimiTag:perm>
						<rimiTag:perm url="web/scoreManagerController/getListScoreManager">
							<li>
								<a href="${ctx }/web/scoreManagerController/getListScoreManager"
									target="ifr_main">积分管理</a>
							</li>
						</rimiTag:perm>
						<rimiTag:perm url="settings/otherWeb.html">
							<li>
								<a href="javascript:;" target="ifr_main">运营管理</a>
								<i class="right"></i>
								<div class="disp-n">
									<a href="settings/otherWeb.html" target="ifr_main">其他页面管理</a>

									<a href="${ctx}/web/adArea/adArealist" target="ifr_main">广告位管理</a>
									<a href="${ctx}/web/surguestion/list" target="ifr_main">建议与意见管理</a>
								</div>
							</li>
						</rimiTag:perm>
						<rimiTag:perm
							url="web/replyManageController/forReplyManageSettingList">
							<li>
								<a>评价&amp;回复管理</a>
								<i class="right"></i>
								<div class="disp-n">
									<a
										href="${ctx}web/replyManageController/forReplyManageSettingList"
										target="ifr_main">评价&amp;回复设置</a>
									<a
										href="${ctx}web/replyManageController/forReviewReplyManageList"
										target="ifr_main">待审核的评价&amp;回复</a>
									<a
										href="${ctx}web/replyManageController/forAlreadyReviewReplyManage"
										target="ifr_main">已审核的评价&amp;回复</a>
								</div>
							</li>
						</rimiTag:perm>
						<rimiTag:perm url="web/order/forPayContentList">
							<li>
								<a>账单管理</a>
								<i class="right"></i>
								<div class="disp-n">
									<a href="${ctx}web/order/forPayContentList" target="ifr_main">支付内容管理</a>
									<a href="${ctx}web/order/forOrderManageList" target="ifr_main">所有订单管理</a>
								</div>
							</li>
						</rimiTag:perm>
						<rimiTag:perm url="web/data">
						<li>
								<a>数据管理</a>
								<i class="right"></i>
								<div class="disp-n">
								  <rimiTag:perm url="web/cacheManagerController/intoCacheList">
									<a href="${ctx}web/cacheManagerController/intoCacheList" target="ifr_main">缓存管理</a>
								</rimiTag:perm>
								 <rimiTag:perm url="ctrip/intCtripPage">
									<a href="${ctx}ctrip/intCtripPage" target="ifr_main" >携程酒店同步</a>
									</rimiTag:perm>
								</div>
						</li>
						</rimiTag:perm>
					</ul>
				</div>
			<!-- } 系统设置 -->
			</rimiTag:perm>
		</div>
		<!-- } main_left -->

		<!-- main_right { -->
		<div class="main_right">
			<iframe id="ifr_main" name="ifr_main" marginheight="0"
				marginwidth="0" frameborder="0" scrolling="auto" class="ifr_main"
				src="admin-home.html"></iframe>
		</div>
		<!-- } main_right-->
		</div>
		<!-- } main -->

		<!-- footer { -->
		<div class="footer">
            &copy; Company 2014 – Copyright on 中国移动通信集团西藏有限公司&nbsp;藏ICP备09000139号
		</div>
		<!-- } footer -->

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
		<script src="../resources/plugin/jquery-1.11.1.min.js"
			type="text/javascript"></script>
		<script src="../resources/plugin/baseCSS-1.0.4.js"
			type="text/javascript"></script>
		<script src="../resources/js/index.js" type="text/javascript"></script>
	
		<!-- 利用空闲时间预加载指定页面 -->
		<link rel="prefetch">
		<!-- IE10+ -->
		<link rel="next">
		<!-- Firefox -->
		<link rel="prerender">
		<!-- Chrome -->
		<!--
	-- 例子：
	-- <link rel="prefetch" href="http://www.baidu.com">
	-- <link rel="prefetch" href="userManage/userInfoManage.html">
	-->
	</body>
</html>