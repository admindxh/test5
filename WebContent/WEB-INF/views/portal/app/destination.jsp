<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="udj" uri="/user-defined-jstl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"       prefix="fn" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
	<meta name="author" content="quansenx">
	<%@include file="/common-html/frontcommon.jsp" %>
	
	<meta name="keywords" content="${destination.destinationName}，${destination.destinationName}景点，${destination.destinationName}简介，${viewString}" />
<meta name="description" content="${destination.destinationName}简介，${destination.destinationName}景点介绍，让您更了解${destination.destinationName}的地域、文化、景点、历史，hold住最美回忆，西藏旅游攻略尽在天下西藏。" />
<title>${destination.destinationName}景点介绍_${destination.destinationName}风景-天上西藏官网</title>
	<link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctxPortal}/assets/css/tourism/destination.css"/>
	<link rel="stylesheet" href="${ctxPortal}/assets/css/tourism/tourist.css"/>
	<script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
	<!--[if lt IE 9]>
	<script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
	<script src="${ctxPortal}/modules/fix/respond.min.js"></script>
	<![endif]-->
	<style>
	.h2{
	font-size: 16px!important;
	width: 105px;
	margin: 0!important;
	text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;
	line-height: 30px!important;
	}
	</style>
	<script>
			// Set configuration
			seajs.config({
				alias: {
				    "jquery": "jquery/jquery/1.11.1/jquery.js",
					"avalon": "avalon/1.3.7/avalon.js",
					"bootstrap/css": "bootstrap/3.3.1/css/bootstrap.min.css",
					"header/css": "${ctxPortal}/assets/css/common.css",
					"footer/css": "${ctxPortal}/assets/css/footer.css"
				}
			});
			seajs.use(['bootstrap/css', 'header/css', 'footer/css']);
			seajs.use('avalon', function(avalon) {
				avalon.define({
					$id: "indexView"
				})
			})
		</script>
		<script>
		window._bd_share_config = {
			"common" : {
				"bdText" : "${destination.destinationName}", // 分享的内容
				"bdDesc" : "分享的摘要", // 分享的摘要
				"bdUrl": "", // 分享的Url地址
				"bdPic": "" // 分享的图片
			},
			"share" : {
				"bdSize" : 24,
				"bdCustomStyle": '${ctxPortal}/assets/css/common.css'
			}
		};
		with (document)
			0[(getElementsByTagName('head')[0] || body)
					.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
					+ ~(-new Date() / 36e5)];
	</script>
</head>
	<body ms-controller="indexView">
		<!-- header -->
        <jsp:include page="${root}portal/headerFooterController/header" flush="ture"/> 
		<!-- banner -->
	 	<jsp:include page="${root}portal/distination/desbanner" flush="ture"/> 
		<!-- content -->
		<div id="content">
			<div class="container">
				<div class="row">
					<div class="col-xs-9">
						<div class="introduction">
							<div class="location" style="padding-bottom: 20px;">
						            <img src="${ctxPortal}/assets/icon/location_red.png"/>
						            <span>当前位置:</span>
						            <a href="${ctx}tourism/strage/frontIndex" target="_self">游西藏</a>
						            <img src="${ctxPortal}/assets/icon/arrow_right.png"/>
						            <span  style="color:red;">${destination.destinationName }</span>
						            </div>
							<div class="header clearfix">
								<div class="title"><h2 class="sr-only">${destination.destinationName}简介</h2></div>
								<div class="share bdsharebuttonbox">
									<span>分享到：</span>
									<a href="#" class="weibo" data-cmd="tqq" title="分享到腾讯微博" >分享到腾讯微博</a>
									<a href="#" class="sina" data-cmd="tsina" title="分享到新浪微博" >分享到新浪微博</a>
									<a href="#" class="qzone" data-cmd="qzone" title="分享到QQ空间">分享到QQ空间</a>
							</div>
							</div>
							<div class="content">
								<p>
									${destination.destinationIntroduce }
								</p>
							</div>
						</div>

						<%-- tourist left --%>
						<div class="tourist-left">
							<div class="header">
								<a href="${ctx }tourism/view/forView/${destination.code }.html" target="_blank"><div class="left"><h2 class="sr-only">${destination.destinationName}热门景点</h2></div></a>
								<div class="right"><a href="${ctx }tourism/view/forView/${destination.code }.html" target="_blank">更多MORE</a></div>
							</div>
							<div class="content">
								<div class="clearfix content-top">
									<div>
									
									
										<a href="${ctx }${views[0].linkUrl}" target="_blank"><img 
										
										<c:if test="${not  empty  views[0].img }">src="${ctx}/${views[0].img}"</c:if>
									    <c:if test="${empty  views[0].img }">src="${ctx}/portal/static/default/square.png"</c:if>
										
										 alt="${views[0].viewName}"/></a>
										<div class="img-info">
											<div class="scene-name">${views[0].viewName}</div>
											<div class="scene-go">
												<p>去过：<span>${views[0].fakeGoneCount}</span></p>
												<p>想去：<span>${views[0].fakeWantCount}</span></p>
											</div>
										</div>
									</div>
									<div>
										<a href="${ctx }${views[1].linkUrl}" target="_blank"><img 
										
										<c:if test="${not  empty  views[1].img }">src="${ctx}/${views[1].img}"</c:if>
									    <c:if test="${empty  views[1].img }">src="${ctx}/portal/static/default/square.png"</c:if>
										
										alt="${views[1].viewName}"/></a>
										<div class="img-info img-info-top-2">
											<div class="scene-name">${views[1].viewName}</div>
											<div class="scene-go">
												<p>去过：<span>${views[1].fakeGoneCount}</span></p>
												<p>想去：<span>${views[1].fakeWantCount}</span></p>
											</div>
										</div>
									</div>
									<div>
										<a href="${ctx }${views[2].linkUrl}" target="_blank"><img 
										
										<c:if test="${not  empty  views[2].img }">src="${ctx}/${views[2].img}"</c:if>
									    <c:if test="${empty  views[2].img }">src="${ctx}/portal/static/default/square.png"</c:if>
										
										
										 alt="${views[2].viewName}"/></a>
										<div class="img-info img-info-top-3">
											<div class="scene-name">${views[2].viewName}</div>
											<div class="scene-go">
												<p>去过：<span>${views[2].fakeGoneCount}</span></p>
												<p>想去：<span>${views[2].fakeWantCount}</span></p>
											</div>
										</div>
									</div>
									<div>
										<a href="${ctx }${views[3].linkUrl}" target="_blank"><img 
										
										
										<c:if test="${not  empty  views[3].img }">src="${ctx}/${views[3].img}"</c:if>
									    <c:if test="${empty  views[3].img }">src="${ctx}/portal/static/default/square.png"</c:if>
										
										 alt="${views[3].viewName}"/></a>
										<div class="img-info img-info-top-4">
											<div class="scene-name">${views[3].viewName}</div>
											<div class="scene-go">
												<p>去过：<span>${views[3].fakeGoneCount}</span></p>
												<p>想去：<span>${views[3].fakeWantCount}</span></p>
											</div>
										</div>
									</div>
									<div>
										<a href="${ctx }${views[4].linkUrl}" target="_blank"><img 
										<c:if test="${not  empty  views[4].img }">src="${ctx}/${views[4].img}"</c:if>
									    <c:if test="${empty  views[4].img }">src="${ctx}/portal/static/default/square.png"</c:if>
										
										 alt="${views[4].viewName}"/></a>
										<div class="img-info img-info-top-5">
											<div class="scene-name">${views[4].viewName}</div>
											<div class="scene-go">
												<p>去过：<span>${views[4].fakeGoneCount}</span></p>
												<p>想去：<span>${views[4].fakeWantCount}</span></p>
											</div>
										</div>
									</div>
								</div>
								<div class="clearfix content-top content-footer">
									<div>
										<a href="${ctx }${views[5].linkUrl}" target="_blank"><img 
										
										<c:if test="${not  empty  views[5].img }">src="${ctx}/${views[5].img}"</c:if>
									    <c:if test="${empty  views[5].img }">src="${ctx}/portal/static/default/square.png"</c:if>
										
										 alt="${views[5].viewName}"/></a>
										<div class="img-info img-info-bottom-1">
											<div class="scene-name">${views[5].viewName}</div>
											<div class="scene-go">
												<p>去过：<span>${views[5].fakeGoneCount}</span></p>
												<p>想去：<span>${views[5].fakeWantCount}</span></p>
											</div>
										</div>
									</div>
									<div>
										<a href="${ctx }${views[6].linkUrl}" target="_blank"><img 
										
										
										<c:if test="${not  empty  views[6].img }">src="${ctx}/${views[6].img}"</c:if>
									    <c:if test="${empty  views[6].img }">src="${ctx}/portal/static/default/square.png"</c:if>
										
										 alt="${views[6].viewName}"/></a>
										<div class="img-info img-info-bottom-2">
											<div class="scene-name">${views[6].viewName}</div>
											<div class="scene-go">
												<p>去过：<span>${views[6].fakeGoneCount}</span></p>
												<p>想去：<span>${views[6].fakeWantCount}</span></p>
											</div>
										</div>
									</div>
									<div>
										<a href="${ctx }${views[7].linkUrl}" target="_blank"> <img 
										
										<c:if test="${not  empty  views[7].img }">src="${ctx}/${views[7].img}"</c:if>
									    <c:if test="${empty  views[7].img }">src="${ctx}/portal/static/default/square.png"</c:if>
										
										 alt="${views[7].viewName}"/></a>
										<div class="img-info img-info-bottom-3">
											<div class="scene-name">${views[7].viewName}</div>
											<div class="scene-go">
												<p>去过：<span>${views[7].fakeGoneCount}</span></p>
												<p>想去：<span>${views[7].fakeWantCount}</span></p>
											</div>
										</div>
									</div>
									<div>
										<a href="${ctx }${views[8].linkUrl}" target="_blank"><img  
										
										
										<c:if test="${not  empty  views[8].img }">src="${ctx}/${views[8].img}"</c:if>
									    <c:if test="${empty  views[8].img }">src="${ctx}/portal/static/default/square.png"</c:if>
										
										 alt="${views[8].viewName}"/></a>
										<div class="img-info img-info-bottom-4">
											<div class="scene-name">${views[8].viewName}</div>
											<div class="scene-go">
												<p>去过：<span>${views[8].fakeGoneCount}</span></p>
												<p>想去：<span>${views[8].fakeWantCount}</span></p>
											</div>
										</div>
									</div>
									<div>
										<a href="${ctx }${views[9].linkUrl}" target="_blank"><img 
										<c:if test="${not  empty  views[9].img }">src="${ctx}/${views[9].img}"</c:if>
									    <c:if test="${empty  views[9].img }">src="${ctx}/portal/static/default/square.png"</c:if>
										
										 alt="${views[9].viewName}"/></a>
										<div class="img-info img-info-bottom-5">
											<div class="scene-name">${views[9].viewName}</div>
											<div class="scene-go">
												<p>去过：<span>${views[9].fakeGoneCount}</span></p>
												<p>想去：<span>${views[9].fakeWantCount}</span></p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<%-- tourist left --%>

						<%-- 精彩旅程 --%>
						<div class="journey">
							<div class="header">
								<a  target="_blank"  href="${ctx }tourism/strage/intoTravaldes/${destination.code}.html"><div class="left"><h2 class="sr-only">${destination.destinationName}精彩旅程</h2></div></a>
								<div class="right"><a target="_blank" href="${ctx }tourism/strage/intoTravaldes/${destination.code}.html">更多MORE</a></div>
							</div>
							<div class="journey-img clearfix">
								<div class="img-left float-left">
									<a href="${ctxIndex }${travelMap.travelUrlList[0]}" target="_blank">
										<img   
											<c:if test="${empty  travelMap.travelList[0].url}">src="${ctx }/portal/static/default/square.png"</c:if>
											<c:if test="${not  empty  travelMap.travelList[0].url}">src="${travelMap.travelList[0].url}" </c:if>
										   alt="${travelMap.travelList[0].contentTitle }"/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[0].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png"  title="查看数"/><span>${travelMap.travelPraiseList[0].falseViewcount }</span>
										</div>
									</div>
								</div>
								<div class="img-right float-right">
									<a href="${ctxIndex }${travelMap.travelUrlList[1]}" target="_blank">
										<img 
										
										<c:if test="${empty  travelMap.travelList[1].url}">src="${ctx }/portal/static/default/square.png"</c:if>
											<c:if test="${not  empty  travelMap.travelList[1].url}">src="${travelMap.travelList[1].url}" </c:if>
										
										 alt="${travelMap.travelList[1].contentTitle }"/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[1].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png"   title="查看数"/><span>${travelMap.travelPraiseList[1].falseViewcount }</span>
										</div>
									</div>
								</div>
							</div>
							<div class="journey-img mt10 clearfix">
								<div class=" img-right float-left">
									<a href="${ctxIndex }${travelMap.travelUrlList[2]}" target="_blank">
										<img    
										
											<c:if test="${empty  travelMap.travelList[2].url}">src="${ctx }/portal/static/default/square.png"</c:if>
											<c:if test="${not  empty  travelMap.travelList[2].url}">src="${travelMap.travelList[2].url}" </c:if>
										
										 alt="${travelMap.travelList[2].contentTitle }"/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[2].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png"   title="查看数"/><span>${travelMap.travelPraiseList[2].falseViewcount }</span>
										</div>
									</div>
								</div>
								<div class="img-left float-right">
									<a href="${ctxIndex }${travelMap.travelUrlList[3]}" target="_blank">
										<img 
										
										<c:if test="${empty  travelMap.travelList[3].url}">src="${ctx }/portal/static/default/square.png"</c:if>
											<c:if test="${not  empty  travelMap.travelList[3].url}">src="${travelMap.travelList[3].url}" </c:if>
										
										 alt="${travelMap.travelList[3].contentTitle }"/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[3].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png"   title="查看数"/><span>${travelMap.travelPraiseList[3].falseViewcount }</span>
										</div>
									</div>
								</div>
							</div>
							<div class="journey-img mt10 clearfix">
								<div class="img-left float-left">
									<a href="${ctxIndex }${travelMap.travelUrlList[4]}" target="_blank">
										<img  
											<c:if test="${empty  travelMap.travelList[4].url}">src="${ctx }/portal/static/default/square.png"</c:if>
											<c:if test="${not  empty  travelMap.travelList[4].url}">src="${travelMap.travelList[4].url}" </c:if>
										 alt="${travelMap.travelList[4].contentTitle }"/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[4].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png"   title="查看数"/><span>${travelMap.travelPraiseList[4].falseViewcount }</span>
										</div>
									</div>
								</div>
								<div class="img-right float-right">
									<a href="${ctxIndex }${travelMap.travelUrlList[5]}" target="_blank">
										<img 
											<c:if test="${empty  travelMap.travelList[5].url}">src="${ctx }/portal/static/default/square.png"</c:if>
											<c:if test="${not  empty  travelMap.travelList[5].url}">src="${travelMap.travelList[5].url}" </c:if>
										 alt="${travelMap.travelList[5].contentTitle }"/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[5].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png"   title="查看数"/><span>${travelMap.travelPraiseList[5].falseViewcount }</span>
										</div>
									</div>
								</div>
							</div>
							<div class="journey-img mt10 clearfix">
								<div class=" img-right float-left">
									<a href="${ctxIndex }${travelMap.travelUrlList[6]}" target="_blank">
										<img
										
										<c:if test="${empty  travelMap.travelList[6].url}">src="${ctx }/portal/static/default/square.png"</c:if>
											<c:if test="${not  empty  travelMap.travelList[6].url}">src="${travelMap.travelList[6].url}" </c:if>
										
										 alt="${travelMap.travelList[6].contentTitle }"/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[6].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png"   title="查看数"/><span>${travelMap.travelPraiseList[6].falseViewcount }</span>
										</div>
									</div>
								</div>
								<div class="img-left float-right">
									<a href="${ctxIndex }${travelMap.travelUrlList[7]}" target="_blank">
										<img 
										
										    <c:if test="${empty  travelMap.travelList[7].url}">src="${ctx }/portal/static/default/square.png"</c:if>
											<c:if test="${not  empty  travelMap.travelList[7].url}">src="${travelMap.travelList[7].url}" </c:if>
										
										 alt="${travelMap.travelList[7].contentTitle }"/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[7].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png"   title="查看数"/><span>${travelMap.travelPraiseList[7].falseViewcount }</span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<%-- 精彩旅程 End --%>
					</div>
					<div class="col-xs-3">
						<%-- 机票 --%>
						<c:if test="${not empty destination.jp}">
						<div class="air-ticket">
		                <div class="air-header">
		                    <img src="${ctxPortal}/assets/icon/aircraft.png"/>
		                    <h2 style="display: inline-block;vertical-align: middle;font-size: 14px;color: #fff;">机票搜索<br/>TICKET SEARCH</h2>
		                    <a href="http://flights.ctrip.com/"
		                       target="_blank"><span>更多</span><img src="${ctxPortal}/assets/icon/more_white.png"
		                       /></a>
		                </div>
                <div class="colour-bar"></div>
		                <form target="_blank" id="form1" method="post" action="${ctx}portal/airPlane/OrderAirPlane">
			                <div class="air-content">
			                    <div id="start">
			                        <label>出发日期</label><input type="text" name="date" value="${ct }" readonly="readonly"/>
			                    </div>
			                    <div style="position: relative;">
			                        <label>出发城市</label><input type="text" name="start" value="" id="J_CityFloat" readonly="readonly"  />
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
			                    <div>
	                      <div style="position: relative;">
                         <label>到达城市</label><input type="text" name="" value="" id="e_CityFloat" autocomplete="off" readonly="readonly" />
                           <div class="float">
                              <div class="link-city" id="destcity">
                                  <input type="hidden" name="dest">
                                  <a href="javascript:;" data-code="拉萨">拉萨贡嘎机场</a>
                                  <a href="javascript:;" data-code="林芝">林芝米林机场</a>
                                  <a href="javascript:;" data-code="昌都">昌都邦达机场</a>
                              </div>
                          </div>
                            </div>
			                    </div>
			                    <div>
			                        
			                    </div>
			                    <button type="button" onclick="javascript:$('#form1').submit()"></button>
			                    <br/>
			                </div>
		                </form>
           				 </div>
						</c:if>
						<%-- 机票 End --%>
					<c:if test="${not empty  merchantOneMap.merchantType}">
						<%-- tourist right --%>
						<div class="tourist-right">
							<div class="header">
								<a href="${ctx}${merchantOneMap.merchantTypeUrl}" target="_blank"><div class="left"><h2 class="h2">${merchantOneMap.merchantType }</h2></div></a>
								<div class="right"><a href="${ctx }${merchantOneMap.merchantTypeUrl}" target="_blank">更多MORE</a></div>
							</div>
							<c:if test="${m1=='true'}">
									 <img style="margin-top: 15px;" src="${ctxPortal}/static/images/no-recommendation.png"/>
							</c:if>
							<c:if test="${m1!='true'}">
							    <c:forEach items="${merchantOneMap.merchantList}"  var="m" varStatus="status">
										<div class="content">
											<h3 style="font-size:14px;"><a href="${ctx }${merchantOneMap.merchantUrlList[status.index] }" target="_blank">${m.merchantName }</a></h3>
											<div class="media message">
												<a class="media-left" href="${ctx }${merchantOneMap.merchantUrlList[status.index] }" target="_blank">
													<div>
														<img src="${ctx }/${m.merchantImage}" style="width:67px;height:67px;" alt="${m.merchantName }"/>
													<c:if test="${m.isRecommend eq 1}">
														<img class="ctibet" src="${ctxPortal}assets/icon/ctibet_yxz.png"/>
													</c:if>
													</div>
												</a>
												<div class="media-body">
													<div class="mer-info">
														<span>价格:${empty m.price || "0" eq m.price ? "暂无" : m.price }</span>
														<p mcode="${m.code}" class="mer-score" data-score="5" data-readonly="true"></p>
													</div>
												</div>
											</div>
											<div class="line"></div>
									</div>			
							</c:forEach>
							</c:if>
						</div>
						</c:if>
						<%-- tourist right --%>
				<c:if test="${not empty merchantTwoMap.merchantList}">
						<%-- 精彩旅程 right --%>
						<div class="hotel">
							<div class="header">
								<a href="${ctx }${merchantTwoMap.merchantTypeUrl}" target="_blank"><div class="left">${merchantTwoMap.merchantType }</div></a>
								<div class="right"><a href="${ctx }${merchantTwoMap.merchantTypeUrl}" target="_blank">更多MORE</a></div>
							</div>
							<c:if test="${m2=='true'}">
									 <img style="margin-top: 15px;" src="${ctxPortal}/static/images/no-recommendation.png"/>
							</c:if>
							<c:if test="${m2!='true'}">
									<c:forEach items="${merchantTwoMap.merchantList}"  var="m" varStatus="status">
										<div class="hotel-info">
											<h5><a href="${ctx }${merchantTwoMap.merchantUrlList[status.index] }" target="_blank">${m.merchantName }</a></h5>
											<div class="media message">
												<a class="media-left" href="${ctx }${merchantTwoMap.merchantUrlList[status.index] }" target="_blank">
													<div>
														<img src="${ctx }/${m.merchantImage}" style="width:67px;height:67px;" alt="${m.merchantName }"/>
													<c:if test="${m.isRecommend eq 1}">
														<img class="ctibet" src="${ctxPortal}assets/icon/ctibet_yxz.png"/>
													</c:if>
													</div>
												</a>
												<div class="media-body">
													<div class="mer-info">
														<span>价格:${empty m.price || "0" eq m.price ? "暂无" : m.price }</span>
														<p mcode="${m.code}" class="mer-score" data-score="5" data-readonly="true"></p>
													</div>
												</div>
											</div>
											<div class="line"></div>
									</div>
								</c:forEach>
							</c:if>
							</div>
							</c:if>
					<c:if test="${not  empty merchantThreeMap.merchantType}">
						<div class="hotel food">
							<div class="header">
								<a href="${ctx }${merchantThreeMap.merchantTypeUrl}"  target="_blank"><div class="left">${merchantThreeMap.merchantType }</div></a>
								<div class="right"><a href="${ctx }${merchantThreeMap.merchantTypeUrl}" target="_blank">更多MORE</a></div>
							</div>
							<c:if test="${m3=='true'}">
									<img style="margin-top: 15px;" src="${ctxPortal}/static/images/no-recommendation.png"/>
							</c:if>
							<c:if test="${m3!='true'}">
									<c:forEach items="${merchantThreeMap.merchantList}"  var="m" varStatus="status">
										<div class="hotel-info">
											<h5><a href="${ctx }${merchantThreeMap.merchantUrlList[status.index] }" target="_blank">${m.merchantName }</a></h5>
											<div class="media message">
												<a class="media-left" href="${ctx }${merchantThreeMap.merchantUrlList[status.index] }" target="_blank">
													<div>
														<img src="${ctx }/${m.merchantImage}"  style="width:67px;height:67px;" alt="${m.merchantName }"/>
													<c:if test="${m.isRecommend eq 1}">
														<img class="ctibet" src="${ctxPortal}assets/icon/ctibet_yxz.png"/>
													</c:if>
													</div>
												</a>
												<div class="media-body">
													<div class="mer-info">
														<span>价格:${empty m.price || "0" eq m.price ? "暂无" : m.price }</span>
														<p mcode="${m.code}" class="mer-score" data-score="5" data-readonly="true"></p>
													</div>
												</div>
											</div>
											<div class="line"></div>
									</div>		
							</c:forEach>
							</c:if>	
						</div>
						</c:if>
						<%-- 精彩旅程 right End --%>
					</div>
				</div>
			</div>
		</div>
		<!-- footer -->
		<jsp:include page="${root}portal/headerFooterController/footer" flush="ture"/>
		<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
		<script type="text/javascript">
			var basePath_ = "${ctx}";
			var basePathPortal = "${ctxPortal}";
		</script>
		<script type="text/javascript" src="${ctx}portal/assets/js/tourism/destination.js"></script>
		<script type="text/javascript" src="${ctx}portal/assets/js/tourism/tourism.js"></script>
	</body>
</html>
