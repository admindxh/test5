<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.rimi.ctibet.domain.LogUser" session="true"%>
<%@ page import="com.rimi.ctibet.domain.CommonOrder" session="true"%>
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
		<meta name="keywords"
			content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
		<meta name="description"
			content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
		<%@include file="/common-html/frontcommon.jsp"%>
		<title>个人中心_天上西藏</title>
		<link rel="stylesheet"
			href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
		<link rel="stylesheet"
			href="${ctxPortal}/assets/css/my-center/home.css" />
		<!--[if lt IE 9]>
	<script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
	<script src="${ctxPortal}modules/fix/respond.min.js"></script>
	<![endif]-->
		<script id="seajsnode"
			src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
		<script>
		// Set configuration
		seajs.config({
			alias: {
				"jquery": "jquery/jquery/1.11.1/jquery.js",
				"avalon": "avalon/1.3.7/avalon.js",
				"map": "${ctxPortal}/assets/js/map.js",
				"paginator":"${ctxPortal }/modules/paginator/0.5/bootstrap-paginator.js",
				"common/css": "${ctxPortal}/assets/css/common.css",
				"footer/css": "${ctxPortal}/assets/css/footer.css"
			}
		});
		seajs.use(['jquery','common/css','footer/css']);

	</script>
	<style>
	.tabs-content, .filtrate{
		display:block;
	}
	</style>
	</head>
	<body>
		<jsp:include page="${root}/portal/headerFooterController/header"></jsp:include>
		<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
		<div class="container" ms-controller="view">
			<div class="bd">
				<div class="xz-box clearfix">
					<div class="box-left">
						<div class="user-info-b">
							<div class="uib-img clearfix">
								<div class="user-opt">
									<a
										href="<%=basePath%>member/account/toEditPage?type=data"
										class="modify">修改资料 </a>
									<a
										href="<%=basePath%>member/account/toEditPage?type=pic"
										class="modify-head">修改头像 </a>
									<a
										href="<%=basePath%>member/account/toEditPage?type=pass"
										class="modify-pass">修改密码 </a>
								</div>
								<div class="uib-head">
									<span class="uib-mark"></span>
									<img alt="120x120" <c:if test="${empty lgUser.pic }">
							                   <c:choose>
							                     <c:when test="${lgUser.sex eq '1'}">src="${ctxPortal}/static/default/male.jpg"</c:when>
							                     <c:otherwise>src="${ctxPortal}/static/default/female.jpg"</c:otherwise> 
							                   </c:choose>
							               </c:if>
                                          <c:if test="${not empty lgUser.pic }">
										    <c:choose>
							                     <c:when test="${lgUser.sex eq '0'&& lgUser.pic eq '/portal/static/default/male.jpg'}">src="${ctxPortal}/static/default/female.jpg"</c:when>
							                     <c:otherwise>src="<%=basePath%>${lgUser.pic }"</c:otherwise> 
							                   </c:choose>
										 </c:if>
										width="120" height="120">
								</div>
							</div>
							<div class="uinfo">
								<div class="uf-item">
									<!-- <a href="<%=basePath%>member/account/toEditPage">${lgUser.username } <i class="j-male"></i></a> -->
									<span class="red">${lgUser.username }<c:if
											test="${lgUser.sex eq '1'}">
											<i class="j-male"></i>
										</c:if>
										<c:if test="${lgUser.sex eq '0'}">
											<i class="j-female"></i>
										</c:if>
									</span>
									<input type="hidden" value="${lgUser.code }" id="memberCode" />
								</div>
								<div class="uf-item">
									积分
									<span class="yellow">${lgUser.score }<c:if
											test="${empty lgUser.score}">0</c:if>
									</span>
								</div>
								<div class="uf-item">
									朝圣者
									<span class="red">LV0</span>
								</div>
								<div class="uf-item clearfix">
									<div>
										收藏
										<span class="green"> <a
											href="<%=basePath%>portal/userFavorite/getUserFav"
											style="color: green;">${favCount} </a> </span>
									</div>
									<div>
										评论&回复
										<span class="green"> <a
											href="<%=basePath%>portal/userReply/getUserReply"
											style="color: green;">${repCount} </a> </span>
									</div>
								</div>
							</div>
						</div>
                    </div>
					<div class="box-right">
						<!-- 地图 -->
						<div class="map-wrap">
							<div id="map" style="height: 100%; width: 100%;"></div>
							<a href="<%=basePath%>member/userView/initial"
								class="footprint">足迹</a>
							<div class="go-wrap">
								<span class="been">去过：${cntGone }</span>
								<span>想去：${cntWanna }</span>
							</div>
						</div>
					</div>
				</div>
				<input type="hidden" id="mtype" value="${type }" />
				<div class="xz-box xz-box-list" id="nodata">
					<div class="J-tabs">
						<a name="mess" style="cursor: pointer;"
							<c:if test="${type eq 'mess'}">class="active"</c:if>>主页
							<c:if test="${unReadCount!='0'}">
								<span class="badge">${unReadCount }</span>
							</c:if>  
						</a>
						<a name="stra" <c:if test="${type eq 'stra'}">class="active"</c:if> style="cursor: pointer;">我的攻略</a>
						<a name="post" <c:if test="${type eq 'post'}">class="active"</c:if> style="cursor: pointer;">我的帖子 </a>
						<a name="acti" <c:if test="${type eq 'acti'}">class="active"</c:if> style="cursor: pointer;">我的活动</a>
						<!-- 订单管理 -->
						<a name="order" <c:if test="${type eq 'order'}">class="active"</c:if> style="cursor: pointer;">我的订单</a>
					</div>

					<!-- 主页 -->
					<div class="tabs-content list-unstyled athome dis-show"
						ms-if="type=='mess'">
						<table class="table">
							<tbody>
								<!--已读请添加 markread 样式 -->
								<tr ms-repeat="list" class="markread">
									<td>
										<div ms-if="el.type=='straNewReply'">
											有人新评论了您的攻略在
											<a ms-if="el.url" ms-href="<%=basePath%>{{el.url }}"
												class="green">{{el.contentTitle }}</a>
											<a ms-if="!el.url" ms-href="javascript:void(0);"
												title="内容可能已被删除" class="green">{{el.contentTitle }}</a>
											快去看看吧！
										</div>
										<div ms-if="el.type=='postNewReply'">
											有人新回复了您的帖子
											<a ms-if="el.url" ms-href="<%=basePath%>{{el.url }}"
												class="green">{{el.contentTitle }}</a>
											<a ms-if="!el.url" ms-href="javascript:void(0);"
												title="内容可能已被删除" class="green">{{el.contentTitle }}</a>
											快去看看吧！
										</div>
										<div ms-if="el.type=='straJudgeOk'">
											您发布的攻略
											<a ms-if="el.url" ms-href="<%=basePath%>/{{el.url }}"
												class="green">{{el.contentTitle }}</a>
											<a ms-if="!el.url" ms-href="javascript:void(0);"
												title="内容可能已被删除" class="green">{{el.contentTitle }}</a>
											已经通过了系统审核，奖励您积分{{el.score }}(每天最多20分)，感谢您，您的攻略将指导更多的驴友出行！
										</div>
										<div ms-if="el.type=='postJudgeOk'">
											您发布的帖子
											<a ms-if="el.url" ms-href="<%=basePath%>{{el.url }}"
												class="green">{{el.contentTitle }}</a>
											<a ms-if="!el.url" ms-href="javascript:void(0);"
												title="内容可能已被删除" class="green">{{el.contentTitle }}</a>
											已经通过了系统审核，奖励您积分{{el.score }}(每天最多20分)，感谢您,您的帖子将指导更多的驴友出行！
										</div>
										<div ms-if="el.type=='replyJudgeOk'">
											您的评论回复
											<a ms-if="el.url" ms-href="<%=basePath%>{{el.url }}"
												class="green">{{el.contentTitle }}</a>
											<a ms-if="!el.url" ms-href="javascript:void(0);"
												title="内容可能已被删除" class="green">{{el.contentTitle }}</a>
											已经通过了系统审核，奖励您积分{{el.score }}(每天最多20分)，感谢您,你的评论/回复将指导更多的驴友出行！
										</div>
										<div ms-if="el.type=='straJudgeBack'">
											遗憾地通知您，您发布的攻略
											
											<a ms-if="el.url"  ms-href="<%=basePath%>tourism/upload/edit?sourceHref=<%=basePath%>portal/userStrategy/getMyStrat&code={{el.contentCode}}"
												class="green">{{el.contentTitle }}</a>
											<a ms-if="!el.url" ms-href="javascript:void(0);"
												title="内容可能已被删除" class="green">{{el.contentTitle }}</a>
											并未通过系统的审核，您可以在您的攻略列表中修改该攻略，进行再次提交！如果您有任何反馈，可以
											<a
												href="<%=basePath%>portal/app/setting/other.html?code=OTH419760232787"
												class="green">联系我们</a>
										</div>
										<div ms-if="el.type=='postJudgeBack'">
											遗憾地通知您，您发布的帖子
											<a ms-if="el.url" ms-href="<%=basePath%>community/gotoUpdatePostUc?code={{el.contentCode }}"
												class="green">{{el.contentTitle }}</a>
											<a ms-if="!el.url" ms-href="javascript:void(0);"
												title="内容可能已被删除" class="green">{{el.contentTitle }}</a>
											并未通过系统的审核，您可以在您的帖子列表中修改该帖子，进行再次提交！如果您有任何反馈，可以
											<a
												href="<%=basePath%>portal/app/setting/other.html?code=OTH419760232787"
												class="green">联系我们</a>
										</div>
										<div ms-if="el.type=='straDelete'">
											遗憾地通知您，由于您发布的攻略
											<a ms-if="el.url" ms-href="<%=basePath%>{{el.url }}"
												class="green">{{el.contentTitle }}</a>
											<a ms-if="!el.url" ms-href="javascript:void(0);"
												title="内容可能已被删除" class="green">{{el.contentTitle }}</a>
											内容违反了国家法律法规或者本站内容发布标准，管理员已将其删除！如果您有任何反馈，可以
											<a
												href="<%=basePath%>portal/app/setting/other.html?code=OTH419760232787"
												class="green">联系我们</a>
										</div>
										<div ms-if="el.type=='postDelete'">
											遗憾地通知您，由于您发布的帖子
											<a ms-if="el.url" ms-href="<%=basePath%>{{el.url }}"
												class="green">{{el.contentTitle }}</a>
											<a ms-if="!el.url" ms-href="javascript:void(0);"
												title="内容可能已被删除" class="green">{{el.contentTitle }}</a>
											内容违反了国家法律法规或者本站内容发布标准，管理员已将其删除！如果您有任何反馈，可以
											<a
												href="<%=basePath%>portal/app/setting/other.html?code=OTH419760232787"
												class="green">联系我们</a>
										</div>
										<div ms-if="el.type=='replyDelete'">
											遗憾地通知您，由于您对
											<a ms-if="el.url" ms-href="<%=basePath%>{{el.url }}"
												class="green">{{el.contentTitle }}</a>
											<a ms-if="!el.url" ms-href="javascript:void(0);"
												title="内容可能已被删除" class="green">{{el.contentTitle }}</a>
											的评论/回复违反了国家法律法规或者本站内容发布标准，管理员已将其删除！如果您有任何反馈，可以
											<a
												href="<%=basePath%>portal/app/setting/other.html?code=OTH419760232787"
												class="green">联系我们</a>
										</div>
										<div ms-if="el.type=='postTop'">
											恭喜您，您发布的帖子
											<a ms-if="el.url" ms-href="<%=basePath%>{{el.url }}"
												class="green">{{el.contentTitle }}</a>
											<a ms-if="!el.url" ms-href="javascript:void(0);"
												title="内容可能已被删除" class="green">{{el.contentTitle }}</a>
											已被管理员执行了置顶操作，感谢您为其他社区驴友提供的精彩内容！
										</div>
									</td>
									<td class="text-center" width="200">
										{{el.createDate |date('yyyy-MM-dd HH:mm:ss')}}
									</td>
									<td class="text-center" width="30">
										<span ms-click="delMess(el.code);" class="h-trash"></span>
									</td>
								</tr>
							</tbody>
						</table>
					</div>


					<!-- 我的攻略 -->
					<div class="filtrate clearfix my-strategy" ms-if="type=='stra'">
						<form action="<%=basePath%>portal/userStrategy/getMyStrat"
							method="post" name="straForm">
							<div class="drop-down">
								<label>
									筛选:
								</label>
								<div class="dropdown glclass">
									<button class="dropdown-toggle" type="button"
										data-toggle="dropdown">
										<label>
											<c:if test="${not empty proName}">${proName}</c:if>
											<c:if test="${empty proName}">攻略类型</c:if>
										</label><span></span>
									</button>
									<input type="hidden" name="straType" id="straType"
										value="${proCode }" />
									<ul class="dropdown-menu" id="type" name="stra" role="menu"aria-labelledby="dropdownMenu1">
										<li role="presentation">
											<a data-value="" role="menuitem" tabindex="-1"
												href="javascript:void(0);">请选择</a>
										</li>
										<li role="presentation" ms-repeat="Stype">
											<a ms-value="{{el.code}}" role="menuitem" tabindex="-1"
												href="javascript:void(0);">{{el.programaName}}</a>
										</li>
									</ul>
								</div>

								<div class="dropdown current-state">
									<button class="dropdown-toggle" type="button"
										data-toggle="dropdown">
										<label>
											<c:if test="${not empty stateShow}">${stateShow}</c:if>
											<c:if test="${empty stateShow}">审核状态</c:if>
										</label><span></span>
									</button>
									<input type="hidden" name="straState" id="straState"
										value="${judgeState }" />
									<ul class="dropdown-menu" id="state" role="menu" name="stra"
										aria-labelledby="dropdownMenu1">
										<li role="presentation">
											<a role="menuitem" ms-value="" tabindex="-1"
												href="javascript:void(0);">请选择</a>
										</li>
										<li role="presentation">
											<a role="menuitem" ms-value="0" tabindex="-1"
												href="javascript:void(0);">待 审 核</a>
										</li>
										<li role="presentation">
											<a role="menuitem" ms-value="1" tabindex="-1"
												href="javascript:void(0);">审核通过</a>
										</li>
										<li role="presentation">
											<a role="menuitem" ms-value="-1" tabindex="-1"
												href="javascript:void(0);">审核失败</a>
										</li>
									</ul>
								</div>
							</div>
						</form>
						<button
							onclick="javascript:location.href='<%=basePath%>tourism/upload/intoStragePage?sourceHref=<%=basePath%>portal/userStrategy/getMyStrat'">
							上传攻略
						</button>
					</div>

					<div class="tabs-content list-unstyled mygl my-strategy"
						ms-if="type=='stra'">
						<table class="table">
							<thead>
								<tr>
									<th>
										<div>
											攻略标题
										</div>
									</th>
									<th>
										<div>
											类型
										</div>
									</th>
									<th>
										<div>
											当前状态
										</div>
									</th>
									<th>
										<div>
											操作
										</div>
									</th>
								</tr>
							</thead>
							<tbody>
								<!--已读请添加 markread 样式 -->

								<tr ms-repeat="listS">
									<td>
										<a ms-if="el.url && el.state=='1'" ms-href="<%=basePath%>{{el.url }}">{{el.contentTitle}}</a>
										<a ms-if="el.url && (el.state=='-1'||el.state=='0')" ms-href="<%=basePath%>tourism/upload/edit?sourceHref=<%=basePath%>portal/userStrategy/getMyStrat&code={{el.code}}">{{el.contentTitle}}</a>
										<a ms-if="!el.url" ms-href="javascript:void(0);"
											title="内容可能已被删除">{{el.contentTitle}}</a> 
									</td>
									<td class="text-center" width="160">
										{{el.programaName }}
									</td>
									<td class="text-center" width="160" ms-if="el.state=='0'">
										待审核
									</td>
									<td class="text-center" width="160" ms-if="el.state=='1'">
										审核通过
									</td>
									<td class="text-center" width="160" ms-if="el.state=='-1'">
										审核失败
									</td>

									<td class="text-center" width="160">
										<!-- <span class="h-edit" ></span> -->
										<span class="h-trash" ms-click="delStra(el.code);"></span>
									</td>
								</tr>

							</tbody>
						</table>
					</div>


					<!-- 我的帖子 -->
					<div class="filtrate clearfix my-post" ms-if="type=='post'">
						<div class="drop-down">
							<label>
								筛选:
							</label>
							<div class="dropdown glclass">
								<button class="dropdown-toggle" type="button"
									data-toggle="dropdown">
									<label>
										<c:if test="${not empty proTypeShow}">${proTypeShow}</c:if>
										<c:if test="${empty proTypeShow}">所属板块</c:if>
									</label><span></span>
								</button>
								<input type="hidden" name="postType" id="postType" value="${postCode }" />
								<ul class="dropdown-menu" role="menu" name="post"
									aria-labelledby="dropdownMenu1">
									<li role="presentation">
										<a data-value="" role="menuitem" tabindex="-1"
											href="javascript:void(0);">所属模块</a>
									</li>
									
									<li role="presentation" ms-repeat="Ptype">
										<a ms-value="{{el.code }}" role="menuitem" tabindex="-1"
											href="javascript:void(0);">{{el.programaName }}</a>
									</li>

									
								</ul>
							</div>

							<div class="dropdown current-state">
								<button class="dropdown-toggle" type="button"
									data-toggle="dropdown">
									<label>
										<c:if test="${not empty proStateShow}">${proStateShow}</c:if>
										<c:if test="${empty proStateShow}">审核状态</c:if>
									</label><span></span>
								</button>
								<input type="hidden" name="postState" id="postState" value="${postState }" />
								<ul class="dropdown-menu" role="menu" name="post"
									aria-labelledby="dropdownMenu1">
									<li role="presentation">
										<a role="menuitem" ms-value="" tabindex="-1"
											href="javascript:void(0);">请 选 择</a>
									</li>
									<li role="presentation">
										<a role="menuitem" ms-value="0" tabindex="-1"
											href="javascript:void(0);">待 审 核</a>
									</li>
									<li role="presentation">
										<a role="menuitem" ms-value="1" tabindex="-1"
											href="javascript:void(0);">审核通过</a>
									</li>
									<li role="presentation">
										<a role="menuitem" ms-value="-1" tabindex="-1"
											href="javascript:void(0);">审核失败</a>
									</li>
								</ul>
							</div>
						</div>
						<button
							onclick="javascript:location.href='<%=basePath%>community/gotoSavePostUc'">
							发布帖子
						</button>
					</div>

					<div class="tabs-content list-unstyled mygl my-post"
						ms-if="type=='post'">
						<table class="table">
							<thead>
								<tr>
									<th>
										<div>
											帖子标题
										</div>
									</th>
									<th>
										<div>
											所属板块
										</div>
									</th>
									<th>
										<div>
											当前状态
										</div>
									</th>
									<th>
										<div>
											操作
										</div>
									</th>
								</tr>
							</thead>
							<tbody>
								<!--已读请添加 markread 样式 -->

								<tr ms-repeat="listP">
									<td>
										<a ms-if="el.state=='1' && el.url" ms-href="<%=basePath%>{{el.url }}">{{el.contentTitle}}</a>
										<a ms-if="(el.state=='0'||el.state=='-1') && el.url" ms-href="<%=basePath%>community/gotoUpdatePostUc?code={{el.code }}">{{el.contentTitle}}</a>
                                        <a ms-if="!el.url" ms-href="javascript:void(0);" title="内容可能已被删除">{{el.contentTitle}}</a>
									</td>
									<td class="text-center" width="160">
										{{el.programaName }}
									</td>
									<td class="text-center" width="160" ms-if="el.state =='0'">待审核</td>
									<td class="text-center" width="160" ms-if="el.state =='1'">审核通过</td>
									<td class="text-center" width="160" ms-if="el.state =='-1'">审核失败</td>
								
									<td class="text-center" width="160">
										<!--<span class="h-edit" ></span>-->
										<span class="h-trash" ms-click="delPost(el.code)"></span>
									</td>
								</tr>

							</tbody>
						</table>
					</div>


					<!-- 我的活动 -->
					<div class="filtrate clearfix my-activity" ms-if="type=='acti'">
						<div class="drop-down">
							<label>
								筛选:
							</label>
							<div class="dropdown glclass">
								<button class="dropdown-toggle" type="button"
									data-toggle="dropdown">
									<label>
										<c:if test="${not empty state}">
											<c:if test="${state eq 'running'}">进 行 中</c:if>
											<c:if test="${state eq 'end'}">已 结 束</c:if>
										</c:if>
										<c:if test="${empty state}">活动状态</c:if>
									</label><span></span>
								</button>
								<input type="hidden" name="state" id="astate" value="${state }" />
								<ul class="dropdown-menu" role="menu" name="acti"
									aria-labelledby="dropdownMenu1">
									<li role="presentation">
										<a role="menuitem" value="" tabindex="-1"
											href="javascript:void(0);">请 选 择</a>
									</li>
									<li role="presentation">
										<a role="menuitem" value="running" tabindex="-1"
											href="javascript:void(0);">进 行 中</a>
									</li>
									<li role="presentation">
										<a role="menuitem" value="end" tabindex="-1"
											href="javascript:void(0);">已 结 束</a>
									</li>
								</ul>
							</div>

							<div class="dropdown current-state">
								<button class="dropdown-toggle" type="button"
									data-toggle="dropdown">
									<label>
										<c:if test="${not empty payState}">
											<c:if test="${payState eq 'payed'}">已 付 款</c:if>
											<c:if test="${payState eq 'unpay'}">待 付 款</c:if>
											<c:if test="${payState eq 'uploaded'}">已上传</c:if>
										</c:if>
										<c:if test="${empty payState}">参与状态</c:if>
									</label><span></span>
								</button>
								<input type="hidden" name="paystate" id="paystate" value="${payState }" />
								<ul class="dropdown-menu" role="menu" name="acti"
									aria-labelledby="dropdownMenu1">
									<li role="presentation">
										<a role="menuitem" value="" tabindex="-1"
											href="javascript:void(0);">请 选 择</a>
									</li>
									<li role="presentation">
										<a role="menuitem" value="payed" tabindex="-1"
											href="javascript:void(0);">已 付 款</a>
									</li>
									<li role="presentation">
										<a role="menuitem" value="unpay" tabindex="-1"
											href="javascript:void(0);">待 付 款</a>
									</li>
									<li role="presentation">
										<a role="menuitem" value="uploaded" tabindex="-1"
											href="javascript:void(0);">已 上 传</a>
									</li>

								</ul>
							</div>
						</div>
						<!-- <button onclick="javascript:location.href='<%=basePath%>tourism/upload/intoStragePage'">上传攻略</button> -->
					</div>

					<div class="tabs-content list-unstyled mygl my-activity"
						ms-if="type=='acti'">
						<table class="table">
							<thead>
								<tr>
									<th>
										<div>
											活动名称
										</div>
									</th>
									<th>
										<div>
											活动状态
										</div>
									</th>
									<th>
										<div>
											参与状态
										</div>
									</th>
									<th>
										<div>
											操作
										</div>
									</th>
								</tr>
							</thead>
							<tbody>

								<tr ms-repeat="listA">
									<td>
										<a ms-if="el.linkUrl" ms-href="<%=basePath%>{{el.linkUrl }}"
											target="_blank">{{el.name }}</a>
										<a ms-if="!el.linkUrl" ms-href="javascript:void(0);"
											title="内容可能已被删除" target="_blank">{{el.name }}</a>
									</td>
									<td class="text-center" width="160" ms-if="el.summary=='Run'">
										进行中
									</td>
									<td class="text-center" width="160" ms-if="el.summary=='End'">
										已结束
									</td>
									<td class="text-center" width="160" ms-if="el.isPay=='1'">
										已付款
									</td>
									<td class="text-center" width="160" ms-if="el.isPay=='0'">
										未付款
									</td>

									<!-- 已付款 请给第一个 span 添加 paid 样式,给第二个 span 添加 see-about 样式 -->
									<td class="text-center" width="180">
										<span class="h-edit paid"
											ms-if="el.isEnrollPay == '1'&&(el.isPay == '0'||el.summary=='Run')"
											ms-click="pay(el.summary,el.code)" title="付款"
											style="margin-right: 15px;"></span>
										<span
											ms-click="linkTo(el.linkUrl)"
											class="h-trash see-about" title="查看"></span>
										<span class="h-trash" ms-click="delActi(el.code)" title="删除"
											style="margin-left: 15px;"></span>
									</td>
								</tr>

							</tbody>
						</table>
					</div>
					
					
					<!-- 我的订单S -->
					<div class="filtrate clearfix my-activity" ms-if="type=='order'">
					
						<!-- 筛选条件 -->
						<div class="drop-down">
							<label>筛选:</label>
							<div class="dropdown current-state">
								<button class="dropdown-toggle" type="button" data-toggle="dropdown">
									<label>
										<c:if test="${payState == 0 || payState == 1}">
											<c:if test="${payState == 0}">未支付</c:if>
											<c:if test="${payState == 1}">已支付</c:if>
										</c:if>
										<c:if test="${empty payState}">请 选 择</c:if>
									</label><span></span>
								</button>
								<input type="hidden" name="paystate" id="order-paystate" value="${payState }" />
								<ul class="dropdown-menu" name="order" role="menu" aria-labelledby="dropdownMenu1">
									<li role="presentation">
										<a role="menuitem" value="2" tabindex="-1" href="javascript:void(0);">请 选 择</a>
									</li>
									<li role="presentation">
										<a role="menuitem" value="1" tabindex="-1" href="javascript:void(0);">已支付</a>
									</li>
									<li role="presentation">
										<a role="menuitem" value="0" tabindex="-1" href="javascript:void(0);">未支付</a>
									</li>
								</ul>
							</div>
						</div>
					</div>

					<!-- 订单数据 -->
					<div class="tabs-content list-unstyled mygl my-activity" ms-if="type=='order'">
						<table class="table">
							<thead>
								<tr>
									<th><div>订单名称</div></th>
									<th><div>创建时间</div></th>
									<th><div>订单状态</div></th>
									<th><div>操作</div></th>
								</tr>
							</thead>
							<tbody>
								<tr ms-repeat="orderList">
									<td>
										<span ms-if="el.successUrl">{{el.orderName }}</span>
										<span ms-if="!el.successUrl" title="内容可能已被删除" >{{el.orderName }}</span>
									</td>
									<td class="text-center" width="160">{{el.createTime|date('yyyy-MM-dd hh:mm:ss')}}</td>
									<td class="text-center" width="160" ms-if="el.payState==<%=CommonOrder.PAY_STATE_DONE%>">已支付</td>
									<td class="text-center" width="160" ms-if="el.payState==<%=CommonOrder.PAY_STATE_NO%>">未支付</td>

									<!-- 已付款 请给第一个 span 添加 paid 样式,给第二个 span 添加 see-about 样式 -->
									<td class="text-center" width="180" align="center">
										<span ms-if="el.payState==<%=CommonOrder.PAY_STATE_NO%>" ms-click="toPay(el.code)" class="h-edit paid" title="去支付"></span>
									</td>
								</tr>
							</tbody>
						</table>
						<form action="${ctx}alipay/commonPay" method="post">
							<input type="hidden" name="commonOrderCode" id="commonOrderCode">
						</form>
					</div>
					<!-- 我的订单 E -->

				</div>
				
				<!-- 木有数据的时候加上这个空标签 -->
				
				<!-- end -->
				<div class="xz-box">
					<!-- 分页 -->
					<div id="paging"></div>
				</div>
			</div>
		</div>
		<!--<div id="footer" ms-include-src="'../footer/index.html'"></div>
-->
		<jsp:include page="${root}/portal/headerFooterController/footer" />

		<script>
	/**
	  **分页
	  */
		seajs.use(['jquery','avalon','paginator'], function($,avalon) {
		   var _model = avalon.define({
	    		$id: 'view',
	    		list:[],
	    		listP:[],
	    		listS:[],
	    		listA:[],
	    		Stype:[],
	    		Ptype:[],
	    		orderList : [],
	    		type:'mess',
	    		delMess: function(code){
	    			delMess(code);
	    		},
	    		delStra: function(code){
	    			delStra(code);
	    		},
	    		delPost: function(code){
	    			delPost(code);
	    		},
	    		delActi: function(code){
	    			delActi(code);
	    		},
	    		pay: function(summ,code){
	    			pay(summ,code);
	    		},
	    		linkTo:function(url){
	    		   linkTo(url);
	    		},
	    		toPay : function (orderCode) {
					var $input = $("#commonOrderCode");
					var $form = $input.parent();
						$input.val(orderCode);
						$form.submit();
				}
	    	});
            avalon.scan();
		   function loadData(type, currentPage, code, state){
		   		_model.type = type;
		        var url='';
		        var data={};
		        if(type=='mess'){
		           url='member/userinfo/getAllMyMsgPage';
		           data={"currentPage": currentPage};
		        }else if(type=='stra'){
		           url='portal/userStrategy/getMyStratPage';
		           data={"currentPage": currentPage,"proCode":code,"judgeState":state};
		        }else if(type=='post'){
		           url='portal/userPost/getPostListPage';
		           data={"currentPage": currentPage,"proCode":code,"judgeState":state};
		        }else if(type=='acti'){
		           url='portal/userActivity/getActListPage';
		           data={"currentPage": currentPage,"paystate":code,"state":state};
		        } 
		        // 我的订单
		        else if ( type == 'order' ) {
		           url = "portal/user-order/list";
		           data = {"currentPage" : currentPage, "pageSize" : 10, "payState" : code};
		        }
		        //点击返回时，样式变化
		        $("a").removeClass("active");
		        $("a[name=" + _model.type + "]").addClass("active");
		        
		        currentPage = currentPage || 1;
		        
		    	$.ajax({
		    		url: "${ctx}"+url,
		    		data:data,
		    		type:'post',
		    		dataType: 'json',
		    		success: function(data){
		    			if(type=='mess'){
		 		            //点击了主页,取消显示 提示信息
		    				$('.badge').remove();
		 		        }
		    		   	if(data.dataList.length){
			    			createPage(data.currentPage, data.totalPage, type,code,state);
			    			$(".nodata").remove();
		    			}else{
		    			    $(".nodata").remove();
		    			    $('#nodata').append('<div class="nodata"></div>');
		    				$('#paging').empty();
		    			}
		    			if(type=='mess'){
		    			  _model.list = data.dataList;
		    			}else if(type=='stra'){
		    			  _model.listS=data.dataList;
		    			}else if(type=='post'){
		    			  _model.listP=data.dataList;
		    			}else if(type=='acti'){
		    			  _model.listA=data.dataList;
		    			} 
		    			// 我的订单
		    			else if(type=='order'){
		    			  _model.orderList = data.dataList;
		    			}
		    		}
		    	});
	    	}
	        var type=$('#mtype').val();
	    	loadData(type);
		
			$(document).on('click', '.cancel', function() {
				var $this = $(this);
				//Do Something
				$this.parent().remove(); //删除当前行
			});

			$(".navigation ul li").on('click', function() {
				$(this).addClass('active').nextAll('li').removeClass('active');
				$(this).prevAll('li').removeClass('active');
			});
			
			function createPage(currentPage, totalPage, type,code,state){
		       	if(totalPage>=1){
			       	var options = {
				     	currentPage: currentPage || 1,
				     	totalPages: totalPage || 1,
				     	//removeClass:'paging-white',
				     	onPageChanged: function(e, oldPage, newPage){
				     		loadData(type, newPage,code,state);
				     	}
					};
				}
				//分页按钮
				$('#paging').bootstrapPaginator(options);
	      }
		

		$(".J-tabs a").on('click',function(){
// 			alert('ad');
			var $this = $(this);
			var type=$this.attr('name');
			$('#mtype').val(type);
			if(type=='stra'){
			  $.ajax({
		    		url: "${ctx}portal/userStrategy/straType",
		    		dataType: 'json',
		    		success: function(data){
		    		   _model.Stype=data.straType;
		    		}
		    	});
			}
			if(type=='post'){
			    $.ajax({
		    		url: "${ctx}portal/userPost/getProType",
		    		dataType: 'json',
		    		success: function(data){
		    		    _model.Ptype=data.postType;
		    		}
		    	});
			}
			$this.addClass('active').siblings().removeClass('active');
		    loadData($this.attr('name'));
		});
		$(function(){
			if(${type eq 'mess'}){
				$('.athome').addClass('dis-show');
			}else if(${type eq 'stra'}){
				$('.my-strategy').addClass('dis-show');
			}else if(${type eq 'post'}){
				$('.my-post').addClass('dis-show');
			}else if(${type eq 'acti'}){
				$('.my-activity').addClass('dis-show');
			}
			if(${delflag=='0'}){
			  alert("删除失败");
			}
			if(${delflag=='1'}){
			  alert("删除成功");
			  if(${type=='mess'}){
			    location.href='<%=basePath%>member/userinfo/getAllMyMsg';
			  }
			  if(${type=='stra'}){
			     location.href='<%=basePath%>portal/userStrategy/getMyStrat';
			  }
			  if(${type=='post'}){
			    location.href='<%=basePath%>portal/userPost/getPostList';
			  }
			  if(${type=='acti'}){
			     location.href='<%=basePath%>portal/userActivity/getActList';
			  }
			}
           
// 			$(document).on('click', '.dropdown-menu li a', function(){
// 			   $(this).parent().parent().siblings('input[type=hidden]').val($(this).attr('value'));
// 			   var typ = $('#mtype').val();
// 			   if(typ=='stra'){
// 				    var procode=$('#straType').val();
// 				    var state=$('#straState').val();
				    
// 					location.href='<%=basePath%>portal/userStrategy/getMyStrat?proCode='+procode+'&judgeState='+state;
// 					loadData(typ,1,procode,state);
// 				}
// 				if(typ=='post'){
// 				   var procode=$('#postType').val();
// 				   var state=$('#postState').val();
// 				   location.href='<%=basePath%>portal/userPost/getPostList?proCode='+procode+'&judgeState='+state;
// 				   loadData(typ,1,procode,state);
// 				}
// 				if(typ=='acti'){
// 				   var paystate=$('#paystate').val();
// 				   var state=$('#astate').val();
// 				  location.href='<%=basePath%>portal/userActivity/getActList?paystate='+paystate+'&state='+state;
// 				  loadData(typ,1,paystate,state);
// 				}
// 			});

			$(document).on('click', '.dropdown-menu li a', function(){
			   	$(this).parent().parent().siblings('input[type=hidden]').val($(this).attr('value'));
				var type = $(this).parent().parent().eq(0).attr("name");
				if(type=='stra'){
					var procode=$('#straType').val();
					var state=$('#straState').val();
					loadData(type,1,procode,state);
				}
				else if(type=='post'){
					var procode=$('#postType').val();
					var state=$('#postState').val();
					loadData(type,1,procode,state);
				}
				else if(type=='acti'){
					var paystate=$('#paystate').val();
					var state=$('#astate').val();
					loadData(type,1,paystate,state);
				}
				// 我的订单
				else if (type == "order") {
					var payState = $("#order-paystate").val();
					loadData(type, 1, payState);
				} 
			});
		});
	})
	 //删除消息
       function delMess(obj){
			 var _alert = new Alert();
	          _alert.show("确定删除当前记录？",function(){
		         location.href='<%=basePath%>member/userinfo/delMsg?msgCode='+obj;
	         });
       }
       //阅读信息
       function readMess(code){
          if(code!=''){
              $.ajax({
	     			type : "post",
	     			url : "<%=basePath%>member/userinfo/readMsg",
	     			data : {msgCode:code},
	     			dataType : "json",
	     			async : true,
	     			success : function(data) {
	     			    
	     	        }
	       })
          }
       }
       //删除攻略
       function delStra(obj){
    	   var _alert = new Alert();
          _alert.show("确定删除当前记录？",function(){
        	  location.href='<%=basePath%>portal/userStrategy/deleteMyStra?code='+obj;
              });
       }
       //删除帖子
       function delPost(obj){
    	   var _alert = new Alert();
           _alert.show("确定删除当前记录？",function(){
        	   location.href='<%=basePath%>portal/userPost/deletePost?id='+obj;
               });
       }
       //删除活动
       function delActi(obj){
    	   var _alert = new Alert();
           _alert.show("确定删除当前记录？",function(){
        	   location.href='<%=basePath%>portal/userActivity/deleteAct?code='+obj;
               });
       }
       //支付
	  	function pay(obj,code){
	  	    if(code==''){
	  	      alert("Code不能为空");
	  	      return;
	  	    }
	  		if(obj=='End'){
	  			alert("活动已结束");
	  			return;
	  		}else{
		  		var url = "${ctx}portal/order/checkPay";
		  		$.post(url, {activityCode: code}, function(response){
		  			if(response=='need_login'){
		  				//alert("请先登录");
		  				// 打开 登录框
						$('[data-toggle="login"]').click();
						return;
		  			}else if(response=='need_enroll'){
		  				alert("请先报名");
		  				return;
		  			}else if(response=='already_pay'){
		  				alert("已经支付");
		  				return;
		  			}else if(response=='to_pay'){
		  				location.href="${ctx}/alipay/pay?activityCode="+code;
		  		  		//$("#payForm").submit();
		  			}
		  		});
	  		}
	  	}
       	function linkTo(url){
       		 location.href='<%=basePath%>'+url;
       	}
	seajs.use(["jquery", "bootstrap/3.3.1/js/dropdown.js"],function($){
		$('[data-toggle="dropdown"]').dropdown();
		$(function(){
			$(".J-tabs a[name='${type}']").click();
			});
	});
	seajs.use('${ctxPortal}/assets/js/tourism/strategy_list.js');  //  下拉菜单的效果
</script>
		<!-- 调用地图 -->
		<script>
  seajs.use('map', function(myMap){
  	var locat = ${locat};
  	// 初始化景点地图
  	/* 初始化景点地图
  	 * @param  {[string]}   city   在地图中高亮显示该目的地区域。比如：拉萨市
   	 * @param  {[array object]}   spots    景点数据，包含想去和去过的数据
     * @param  {Function} callback 点击地图回调函数
  	*/
  	myMap.init('西藏', locat, mapClick);
  	// 响应地图点击事件
  	function mapClick(param){
  		//console.log(param)
  	}
  })
</script>
	</body>
</html>