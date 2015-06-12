<%@page import="com.rimi.ctibet.domain.UserFavorite"%>
<%@page import="com.rimi.ctibet.domain.UserView"%>
<%@page import="com.rimi.ctibet.domain.Content"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/commonTag.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="author" content="quansenx">
	<meta name="keywords" content="${view.tdkKeywords }" />
	<meta name="description" content="${view.tdkDescription }" />
	<title>${view.tdkTitle }</title>
	<%-- <%@include file="/common-html/frontcommon.jsp" %> --%>
	<link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
	<!--[if lt IE 9]>
	<script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
	<script src="${ctxPortal}/modules/fix/respond.min.js"></script>
	<![endif]-->
	<script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
	<script src="${ctxPortal }/assets/js/Alert.js"></script>
	<script>
		var _alert = new Alert();
		seajs.config({
			alias: {
				"jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js",
				"avalon": "${ctxPortal}/modules/avalon/1.3.7/avalon.js",
				"Validform": "${ctxPortal}/modules/Validform/5.3.2/Validform.min.js",
				"common/css": "${ctxPortal}/assets/css/common.css",
				"scenic/css": "${ctxPortal}/assets/css/tourism/scenic_detail.css",
				"footer/css": "${ctxPortal}/assets/css/footer.css",
		        "paginator":"${ctxPortal}/modules/paginator/0.5/bootstrap-paginator.js"
			}
		});
		seajs.use(["jquery"], function($){
			window.$ = $;
		});
	</script>
	<style>
		.content p{
			margin: 0;
		}
		.sd-content p{
			margin: 0;
		}
	</style>
</head>
<body>
    <jsp:include page="${root}/portal/headerFooterController/header?seo_index=seo_index"/>
	<div class="container">
		<!-- banner -->
		<div class="banner">
			<div id="J_Slick">
				<c:forEach var="obj" items="${view.viewImageArray}">
					<div class="banner-item"><img src="${ctx}${obj}" width="1000" height="360"></div>
				</c:forEach>
			</div>
			<div class="banner-bottom">
				<div class="title">
                    <h1 style="display: none;">${view.viewName }</h1>
                </div>
				<div class="bb-btns" style="margin-bottom: -13px;margin-left: 133px;">
					<!-- clickGoneOrWant -->
					<button id="goneBtn" style="width:74px;height:34px;line-height: 29px;min-width: 74px;border-radius:5px;" type="button" class="btn-in" onclick="clickGoneOrWant('<%=UserView.User_View_Gone%>',this);">去过<label>${view.fakeGoneCount }</label></button>
					<button id="wantBtn" style="width:74px;height:34px;line-height: 29px;min-width: 74px;border-radius:5px;"  type="button" class="btn-in" onclick="clickGoneOrWant('<%=UserView.User_View_Wanna%>',this);">想去<label>${view.fakeWantCount }</label></button>
				</div>
				<div class="feature">
					<c:if test="${haveViewAtlas eq 'yes'}">
						<a href="${ctx}tourism/view/forPhotoGalleryView?viewCode=${view.code}&destinationCode=${view.destinationCode}" class="images">图片集</a>
					</c:if>
					<c:if test="${view.view_360full!='' && view.view_360full!=null}">
						<a target="_blank" href="${view.view_360full }" class="panorama">360全景</a>
					</c:if>
					<c:if test="${view.hdFullUrl!='' && view.hdFullUrl!=null}">
						<a target="_blank" href="${view.hdFullUrl }" class="high-panorama">高清全景</a>
					</c:if>
					<c:if test="${view.realSceneVideoUrl!='' && view.realSceneVideoUrl!=null}">
						<a target="_blank" href="${view.realSceneVideoUrl }" class="panorama-video">实景视频</a>
					</c:if>
				</div>
			</div>
			<c:if test="${fn:length(view.viewImageArray)>1}">
				<a href="javascript:;" class="arrow arrow-prev"></a>
				<a href="javascript:;" class="arrow arrow-next"></a>
			</c:if>
		</div>
		<!-- end -->
		<div class="bd">
			<div class="clearfix">
				<div class="bd-left">
				
					<div class="location">
			            <img src="${ctxPortal}/assets/icon/location_red.png"/>
			            <span>当前位置:</span>
			            <a href="${ctx}tourism/strage/frontIndex" target="_self">游西藏</a>
			            <img src="${ctxPortal}/assets/icon/arrow_right.png"/>
			            <a href="${ctxno}${destination.linkUrl}" target="_self">${destination.destinationName }</a>
			            <img src="${ctxPortal}/assets/icon/arrow_right.png"/>
			            <span style="color:red;">${view.viewName }</span>
			        </div>
					<div class="bd-heading">
						<h2 class="intro">简介 INTRODUCTION</h2>
						<div class="share bdsharebuttonbox">
							<a href="#" class="weibo" data-cmd="tqq" title="分享到腾讯微博">腾讯微博</a>
							<a href="#" class="sina" data-cmd="tsina" title="分享到新浪微博">新浪微博</a>
							<a href="#" class="qzone" data-cmd="qzone" title="分享到QQ空间">QQ空间</a>
						</div>
					</div>
					<div class="bd-list">
						<div class="content" id="share" style="word-wrap: break-word;">
							${view.viewIntroduce }
						</div>
						<div class="sd-content">
							<div class="sd-tabs">
								<a href="javascript:void(0);" class="active">导览</a>
								<a href="javascript:void(0);">交通</a>
								<a href="javascript:void(0);">注意事项</a>
							</div>
							<div class="sd-tab-pane"  style="word-wrap: break-word;">
								<c:if test="${empty view.shortGuide}">
									<div class="nodata"></div>
								</c:if>
								<c:if test="${not empty view.shortGuide}">
									<div class="short-info">
										${view.shortGuide}
										<div class="text-right"><button class="more-info btn btn-link" onclick="moreInfo(this)">更多</button></div>
									</div>
									<div class="long-info">${view.guide}</div>
								</c:if>
							</div>
							<div class="sd-tab-pane" style="display: none; word-wrap: break-word;">
								<c:if test="${empty view.shortTraffic}">
									<div class="nodata"></div>
								</c:if>
								<c:if test="${not empty view.shortTraffic}">
									<div class="short-info">
										${view.shortTraffic}
										<div class="text-right"><button class="more-info btn btn-link" onclick="moreInfo(this)">更多</button></div>
									</div>
									<div class="long-info">${view.traffic}</div>
								</c:if>
							</div>
							<div class="sd-tab-pane" style="display: none; word-wrap: break-word;">
								<c:if test="${empty view.shortNotice}">
									<div class="nodata"></div>
								</c:if>
								<c:if test="${not empty view.shortNotice}">
									<div class="short-info">
										${view.shortNotice}
										<div class="text-right"><button class="more-info btn btn-link" onclick="moreInfo(this)">更多</button></div>
									</div>
									<div class="long-info">${view.notice}</div>
								</c:if>
							</div>
						</div>
					</div>
					<div class="xz-box">
						<div class="xz-heading">
							<a target="_blank" href="${ctx}tourism/strage/intoTraval?des=${view.destinationCode}&view=${view.code}"><h2 class="journey">
								<span class="sr-only">精彩旅程 JOURNEY</span>
							</h2></a>
							<div class="index-more">
								<a target="_blank" href="${ctx}tourism/strage/intoTraval?des=${view.destinationCode}&view=${view.code}">更多 MORE</a>
							</div>
						</div>
						<div class="journey-content clearfix">
							<c:forEach var="obj" items="${listTravalFrontPageVo}">
								<div class="jc-item">
									<c:set var="travelImg" value="${obj.travelImgUrl}"/>
									<c:if test="${empty obj.travelImgUrl}"><c:set var="travelImg" value="${ctx}portal/static/default/square.png"/></c:if>
									<a target="_blank" href="${ctxno}${obj.url}"><img class="img-rounded" src="${travelImg}" alt="${obj.travelTitle }" width="355" height="255"></a>
									<div class="desc">
										<p class="title"><a  target="_blank" style="color: white;" href="${ctxno}${obj.url}">${obj.travelTitle }</a></p>
										<span class="eye">${obj.viewCount }</span>
									</div>
									<div class="mark"></div>
								</div>
							</c:forEach>
							<!-- 没有更多攻略 -->
							<c:if test="${fn:length(listTravalFrontPageVo)<4}">
								<div class="jc-item">
									<a href="javascript:uploadStrage();"><img class="img-rounded" src="${ctxPortal}/static/default/ngl.jpg" width="355" height="255"></a>
								</div>
							</c:if>
							
						</div>
					</div>
					
					<%-- listView中包含这个景点本身 --%>
					<c:if test="${fn:length(listView)>1}">
					<div class="xz-box" >
						<div class="xz-heading">
							<h2 class="spot">
								<span class="sr-only">相关景点 SPOT</span>
							</h2>
						</div>
						<div class="spot-content clearfix">
							<c:forEach var="obj" items="${listView}">
								<c:if test="${obj.code ne view.code}">
									<a href="${ctx}${obj.linkUrl}" class="addr">${obj.viewName}</a>
								</c:if>
							</c:forEach>
						</div>
					</div>
					</c:if>
					
					<div class="xz-box dh-main" ms-important="replyView">
					
						<div class="xz-heading">
							<h2 class="comment_icon">
								<span class="sr-only">用户评论 COMMENT</span>
							</h2>
						</div>
						<div class="comments" >
							
							<div class="media" ms-repeat="data">
								<a target="_blank" class="media-left" ms-attr-href="${ctx}member/show/{{el.memberCode}}.html">
									<span class="user-mark"></span>
									<img ms-if="el.memberSex==0" alt="68x68" width="68" height="67" ms-src="${ctx}{{el.memberPic!=''&&el.memberPic?el.memberPic:'/portal/static/default/female.jpg'}}">
									<img ms-if="el.memberSex==1" alt="68x68" width="68" height="67" ms-src="${ctx}{{el.memberPic!=''&&el.memberPic?el.memberPic:'/portal/static/default/male.jpg'}}">
								</a>
								<div class="media-body">
									<h2 class="media-heading">
										<i ms-attr-class="{{ el.memberSex==1?'j-male':'j-female'}}"></i>{{el.memberName}}<span class="time">{{el.createTime|date("yyyy/MM/dd HH:mm")}}</span>
									</h2>
									<h3>{{el.content}}</h3>
								</div>
							</div>
							
							<div class="nodata" ms-visible="!data.size()"></div>
							
						</div>
						<div id="replyPaging" class="paging paging-center paging-lg paging-white" ms-visible="data.size()"></div>
						<%-- <c:if test="${isOpen==1}"> --%>
						<c:if test="${not  empty logUser}">
							<form action="${ctx}tourism/view/reply" id="commentForm">
								<div id="replyText" class="comment">
									<input type="hidden" name="code" value="${view.code }">
									<textarea id="replyContent" name="replyContent" rows="10" class="form-control" placeholder="有什么好的建议来说两句吧~" dataType="*2-200"  nullmsg="请输入评论"></textarea>
									<span class="Validform_checktip"></span>
									<div class="text-right">
										<button id="sendReply" class="btn-comment">发表评论</button>
									</div>
								</div>
							</form>
							</c:if>
							<c:if test="${ empty logUser}">
							   <p class="log-ts pull-left">您还没有登录，请先<a href="#loginModal" data-toggle="login">登录</a>或<a href="${ctx }portal/registerController/register" target="_blank">注册</a>，再进行评论！</p>
							</c:if>
						<%-- </c:if> --%>
					</div>
				</div>
				<div class="bd-right">
					<div class="air-addr" style="${empty view.address?'display:none;':''}">
						地址：
						<p>${view.address }</p>
					</div>
					<div class="air-ticket">
						<div class="air-header">
							<img src="${ctxPortal}/assets/icon/aircraft.png">
							<h5>机票搜索<br>TICKET SEARCH</h5>
								<a target="_blank" href="http://flights.ctrip.com/">
								<span>更多 </span>
								<img src="${ctxPortal}/assets/icon/more_white.png"></a>
						</div>
						<div class="colour-bar"></div>
						
						<form id="jipiao_form" target="_blank" method="post" action="${ctx}/portal/airPlane/OrderAirPlane">
						<div class="air-content">
							<div id="start">
								<label>出发日期</label>
								<input type="text" name="date" value="${currentDate}" readonly="readonly"></div>
							<div>
								<!-- <label>出发城市</label>
								<input type="text" value="成都市"></div> -->
								<label>出发城市</label>
								<input type="text" name="start" id="J_CityFloat" readonly="readonly" >
	                            <div class="float">
	                                <div class="hot-city">
	                                    <label>热门</label>
	                                </div>
	                                <div class="link-city" id="startcity">
	                                    <input type="hidden" name="start">
	                                    <a href="javascript:;" data-code="BJS">北京</a>
	                                    <a href="javascript:;" data-code="SHA">上海</a>
	                                    <a href="javascript:;" data-code="SZX">深圳</a>
	                                    <a href="javascript:;" data-code="CAN">广州</a>
	                                    <a href="javascript:;" data-code="TAO">青岛</a>
	                                    <a href="javascript:;" data-code="CTU">成都</a>
	                                    <a href="javascript:;" data-code="HGH">杭州</a>
	                                    <a href="javascript:;" data-code="WUH">武汉</a>
	                                    <a href="javascript:;" data-code="TSN">天津</a>
	                                    <a href="javascript:;" data-code="DLC">大连</a>
	                                    <a href="javascript:;" data-code="XMN">厦门</a>
	                                    <a href="javascript:;" data-code="CKG">重庆</a>
	                                </div>
	                            </div>
                            </div>
							  <div style="position: relative;">
                           <label>到达城市</label>
                           <input type="text" name="" value="" id="e_CityFloat" autocomplete="off" readonly="readonly" />
                           <div class="float">
                              <div class="link-city" id="destcity">
                                  <input type="hidden" name="dest">
                                  <a href="javascript:;" data-code="拉萨">拉萨贡嘎机场</a>
                                  <a href="javascript:;" data-code="林芝">林芝米林机场</a>
                                  <a href="javascript:;" data-code="昌都">昌都邦达机场</a>
                              </div>
                          </div>
                            </div>
							<!-- <div>
								<label>特　　价</label>
								<span class="bargain-price">650</span>
								<span class="price-unit">元起</span>
							</div> -->
							<!-- <button type="button" onclick="javascript:window.open('');"></button> -->
							<button type="button" id="J_jipiao"></button>
						</div>
					</div>
					</form>
					
					<div class="xz-box">
						<div class="xz-heading">
							<a target="_blank" href="${ctx}tourism/merchant/list?destinationCode=${view.destinationCode}&viewCode=${view.code}"><h2 class="mert">
								<span class="sr-only">推荐商户</span>
							</h2></a>
							<div class="index-more">
								<a target="_blank" href="${ctx}tourism/merchant/list?destinationCode=${view.destinationCode}&viewCode=${view.code}">更多 MORE</a>
							</div>
						</div>
						<div class="h-list">
							<c:if test="${empty listHotMerchantVo}">
								<div class="nodata"></div>
							</c:if>
							<c:forEach var="obj" items="${listHotMerchantVo}">
								<div class="media">
									<h2 class="media-heading"><a target="_blank" href="${ctx}${obj.url}">${obj.merchantName}</a></h2>
									<div class="media-content" style="position: relative;">
										<a  target="_blank" class="media-left" href="${ctx}${obj.url}">
											<img alt="${obj.merchantName}" src="${ctx}${obj.merchantImage}" class="img-responsive img-rounded">
											<span class="ctibet"></span>
										</a>
										<div class="media-body">
											<div class="bd-text">
												<span>价格：${empty obj.price || "0" eq obj.price ? "暂无" : obj.price }</span>
												<p mcode="${obj.code}" class="mer-score" data-score="5" data-readonly="true"></p>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
							
						</div>
					</div>
					<div class="xz-box">
						<div class="xz-heading">
							<a target="_blank" href="${ctx}tourism/merchant/group?programNam=1&viewCode=${view.code}&destinationCode=${view.destinationCode}"><h2 class="ty">
								<span class="sr-only">团游</span>
							</h2></a>
							<div class="index-more">
								<a target="_blank" href="${ctx}tourism/merchant/group?programNam=1&viewCode=${view.code}&destinationCode=${view.destinationCode}">更多 MORE</a>
							</div>
						</div>
						<div class="h-list">
							<c:if test="${empty listGroupTravelVO}">
								<div class="nodata"></div>
							</c:if>
							<c:forEach var="obj" items="${listGroupTravelVO}">
								<div class="media">
									<h2 class="media-heading"><a target="_blank" href="${ctx}${obj.url}">${obj.name}</a></h2>
									<div class="media-content" style="position: relative;">
										<a target="_blank" class="media-left" href="${ctx}${obj.url}">
											<img alt="${obj.name}" src="${ctx}${obj.img}" class="img-responsive img-rounded">
											<span class="ctibet"></span>
										</a>
										<div class="media-body">
											<div class="bd-text">
												<span>价格：${empty obj.price || "0" eq obj.price ? "暂无" : obj.price }</span>
												<p gcode="${obj.code}" class="gt-score" data-score="5" data-readonly="true"></p>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
							
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="${root}/portal/headerFooterController/footer"/>
	<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
	
	<script type="text/javascript">
		var basePath_ = "${ctx}";
		var basePathPortal = "${ctxPortal}";
		
		var viewCode_ = "${view.code}";
		var viewName_ = "${view.viewName}";
		
		var DETAIL_VIEW_REPLY_ = "<%=Content.DETAIL_VIEW_REPLY%>";
		var User_View_Gone_ = "<%=UserView.User_View_Gone%>";
		var User_View_Wanna_ = "<%=UserView.User_View_Wanna%>";
		var User_Fav_Merc_ = "<%=UserFavorite.User_Fav_Merc%>";
		var User_Fav_Group_ = "<%=UserFavorite.User_Fav_Group%>";
		
		seajs.use(['common/css', 'scenic/css', 'slick/slick.css', 'footer/css']);
		seajs.use([basePathPortal+'/assets/css/tourism/content_header.css', 'datepicker/datepicker.css']);
	</script>
	<script type="text/javascript" src="${ctx}portal/assets/js/tourism/scenic_detail.js"></script>
	<script type="text/javascript" src="${ctx}portal/assets/js/tourism/tourism.js"></script>
	<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
	
</body>
</html>