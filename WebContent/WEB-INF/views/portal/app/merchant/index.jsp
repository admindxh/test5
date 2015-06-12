<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="rimi" uri="/rimi-tags"%>
<%@ taglib prefix="udj" uri="/user-defined-jstl"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="author" content="quansenx">
		<meta name="keywords" content="西藏美食，西藏团游，西藏住宿，西藏娱乐" />
		<meta name="description" content="天上西藏商户栏目为你提供酒店住宿、餐饮美食、生活娱乐、团队旅游的信息，帮助您体验西藏风光，分享路途点滴，hold住最美回忆，西藏旅游攻略尽在天下西藏。" />
		<title>天上西藏-感受西藏魅力，体验西藏生活-天上西藏官网</title>
		<%@include file="/common-html/commonPortal.jsp"%>
		<!--[if lt IE 9]>
		<script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
		<script src="${ctxPortal}modules/fix/respond.min.js"></script>
	<![endif]-->
	</head>
	<body ms-controller="view">
		<jsp:include page="${root}/portal/headerFooterController/header"></jsp:include>
		<jsp:include page="../login/login_modal.jsp"></jsp:include>
		<div class="container">
			<div class="bd">
				<div class="xzbox clearfix">
					<div class="bd-left">
						<div class="bdh clearfix">
							<div class="menu pull-left">
								<a href="list/0.html" class="all active"><span
									class="arrow"></span>全部分类</a>
								<a href="list/${mt1.code}.html" class="mm-icon hotel"><span
									class="arrow"></span>${udj:subString(mt1.name,4)}</a>
								<a href="list/${mt2.code}.html" class="mm-icon food"><span
									class="arrow"></span>${udj:subString(mt2.name,4)}</a>
								<a href="list/${mt3.code}.html" class="mm-icon amuse"><span
									class="arrow"></span>${udj:subString(mt3.name,4)}</a>
								<a href="group" class="mm-icon group"><span
									class="arrow"></span>团游</a>
							</div>
							<div class="slides-wrap pull-right">
								<div class="search">
									<form id="searchForm" action="${ctx}search.html" target="new" class="form-inline">
										<div class="form-group">
											<div class="input-group">
												<input id="keywords" type="text" class="form-control" name="keywords"
													placeholder="请输入关键字搜索景点">
												<div class="input-group-addon btn-addon">
													<button>
														<span class="sr-only">go</span>
													</button>
												</div>
											</div>
										</div>
										<div class="form-group hot-search">
											<label for="hotSearch">
												热门搜索：
											</label>
											<p class="form-control-static hot-tags">
												<a href="#" class="active">纳木错</a>
												<a href="#">布达拉宫</a>
												<a href="#">大昭寺</a>
												<a href="#">酒店</a>
												<a href="#">美食</a>
											</p>
										</div>
									</form>
								</div>
								<div class="slides">
									<div id="slide">
										<c:forEach items="${banner}" var="b">
											<div class="slide-item">
												<c:if test="${not empty b.url}">
													<a href="${b.content}" target="_blank"><img src="${ctx}${b.url}"
															alt="焦点图" width="705" height="255" > </a>
												</c:if>
												<c:if test="${empty b.url}">
													<a href="${b.content}"><img
															src="${ctxPortal}/static/default/square.png" alt="焦点图"
															width="705" height="255"> </a>
												</c:if>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
						<!-- 住宿模块 -->
						<div class="xz-box">
							<div class="xz-heading">
								<a target="_blank" href="${ctx}tourism/merchant/list/${mt1.code}.html"><h2 class="tt-icon">
									<i class="t-icon"></i> ${mt1.name}
								</h2></a>
								<div class="index-more">
									<a target="_blank" href="${ctx}tourism/merchant/list/${mt1.code}.html">更多 MORE</a>
								</div>
							</div>
							<div class="xz-bd-wrap">
								<div class="xz-bd clearfix">
									<c:set value="${fn:length(hotels)}" var="hotelLength"/>
									<c:if test="${hotelLength==0}">
										<c:forEach begin="1" end="6">
											<div class="bd-item">
												<a target="_blank" href="${ctx}${hotel.url}">
													<img src="${ctxPortal}/static/default/square.png" width="350" height="265">
												</a>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${hotelLength<6&&hotelLength!=0}">
										<c:forEach items="${hotels}" var="hotel">
											<div class="bd-item">
												<a target="_blank" href="${ctx}${hotel.url}">
													<c:if test="${empty hotel.merchantimage}">
														<img src="${ctxPortal}/static/default/square.png" alt="${udj:subString(hotel.merchantname,5)}" width="350" height="265">
													</c:if>
													<c:if test="${not empty hotel.merchantimage}">
														<img src="${ctx}${hotel.merchantimage}" alt="${udj:subString(hotel.merchantname,5)}" width="350" height="265">
													</c:if>
												</a>
												<div class="mark"></div>
												<a target="_blank" href="${ctx}${hotel.url}" class="link-text"><p class="text">${hotel.merchantdetail}</p></a>
												<div class="bd-content">
													<p>${udj:subString(hotel.merchantname,5)}(${udj:subString(hotel.destinationname,4) })</p>
													<span class="weye">${hotel.viewcount}</span>
												</div>
											</div>
										</c:forEach>
										<c:forEach begin="1" end="${6-hotelLength}">
											<div class="bd-item">
												<a target="_blank" href="${ctx}${hotel.url}">
													<img src="${ctxPortal}/static/default/square.png" alt="暂无图片" width="350" height="265">
												</a>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${hotelLength>=6}">
										<c:forEach items="${hotels}" var="hotel">
											<div class="bd-item">
												<a target="_blank" href="${ctx}${hotel.url}">
													<c:if test="${empty hotel.merchantimage}">
														<img src="${ctxPortal}/static/default/square.png" alt="${udj:subString(hotel.merchantname,5)}" width="350" height="265">
													</c:if>
													<c:if test="${not empty hotel.merchantimage}">
														<img src="${ctx}${hotel.merchantimage}" alt="${udj:subString(hotel.merchantname,5)}" width="350" height="265">
													</c:if>
												</a>
												<div class="mark"></div>
												<a target="_blank" href="${ctx}${hotel.url}" class="link-text"><p class="text">${hotel.merchantdetail}</p></a>
												<div class="bd-content">
													<p>${udj:subString(hotel.merchantname,5)}(${udj:subString(hotel.destinationname,4) })</p>
<%--													<p class="text">${udj:subString(hotel.merchantdetail,15)}</p>--%>
													<span class="weye">${hotel.viewcount}</span>
												</div>
											</div>
										</c:forEach>
									</c:if>
								</div>
							</div>
						</div>
						<!-- 结束 -->
						<!-- 住宿模块 -->
						<div class="xz-box">
							<div class="xz-heading">
								<a target="_blank" href="${ctx}tourism/merchant/list/${mt2.code}.html"><h2 class="tt-icon">
									<i class="t-icon"></i> ${mt2.name}
								</h2></a>
								<div class="index-more">
									<a target="_blank" href="${ctx}tourism/merchant/list/${mt2.code}.html">更多 MORE</a>
								</div>
							</div>
							<div class="xz-bd-wrap">
								<div class="xz-bd clearfix">
									<c:set value="${fn:length(foods)}" var="foodsLength"/>
									<c:if test="${foodsLength==0}">
										<c:forEach begin="1" end="6">
											<div class="bd-item">
												<a target="_blank" href="${ctx}${hotel.url}">
													<img src="${ctxPortal}/static/default/square.png" width="350" height="265">
												</a>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${foodsLength<6&&foodsLength!=0}" >
										<c:forEach items="${foods}" var="food">
											<div class="bd-item">
												<a target="_blank" href="${ctx}${food.url}">
													<c:if test="${empty food.merchantimage}">
														<img src="${ctxPortal}/static/default/square.png" alt="${udj:subString(food.merchantname,5)}" width="350" height="265">
													</c:if> <c:if test="${not empty food.merchantimage}">
														<img src="${ctx}${food.merchantimage}" alt="${udj:subString(food.merchantname,5)}" width="350" height="265">
													</c:if>
												</a>
												<div class="mark"></div>
												<a target="_blank" href="${ctx}${food.url}" class="link-text"><p class="text">${food.merchantdetail}</p></a>
												<div class="bd-content">
													<p>${udj:subString(food.merchantname,5)}(${udj:subString(food.destinationname,4) })</p>
													<span class="weye">${food.viewcount}</span>
												</div>
											</div>
										</c:forEach>
										<c:forEach begin="1" end="${6-foodsLength}">
											<div class="bd-item">
												<a target="_blank" href="${ctx}${food.url}">
													<img src="${ctxPortal}/static/default/square.png" alt="暂无图片" width="350" height="265">
												</a>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${foodsLength>=6}">
										<c:forEach items="${foods}" var="food">
											<div class="bd-item">
												<a target="_blank" href="${ctx}${food.url}">
													<c:if test="${empty food.merchantimage}">
														<img src="${ctxPortal}/static/default/square.png" alt="${udj:subString(food.merchantname,5)}" width="350" height="265">
													</c:if> <c:if test="${not empty food.merchantimage}">
														<img src="${ctx}${food.merchantimage}" alt="${udj:subString(food.merchantname,5)}" width="350" height="265">
													</c:if>
												</a>
												<div class="mark"></div>
													<a target="_blank" href="${ctx}${food.url}" class="link-text"><p class="text">${food.merchantdetail}</p></a>
												<div class="bd-content">
													<p>${udj:subString(food.merchantname,5)}(${udj:subString(food.destinationname,4) })</p>
													<span class="weye">${food.viewcount}</span>
												</div>
											</div>
										</c:forEach>
									</c:if>
								</div>
							</div>
						</div>
						<!-- 结束 -->
						<!-- 娱乐模块 -->
						<div class="xz-box">
							<div class="xz-heading">
								<a target="_blank" href="${ctx}tourism/merchant/list/${mt3.code}.html"><h2 class="tt-icon">
									<i class="t-icon"></i> ${mt3.name}
								</h2></a>
								<div class="index-more">
									<a target="_blank" href="${ctx}tourism/merchant/list/${mt3.code}.html">更多 MORE</a>
								</div>
							</div>
							<div class="xz-bd-wrap">
								<div class="xz-bd clearfix">
									<c:set value="${fn:length(plays)}" var="playsLength"/>
									<c:if test="${playsLength==0}">
										<c:forEach begin="1" end="6">
											<div class="bd-item">
												<a target="_blank" href="${ctx}${hotel.url}">
													<img src="${ctxPortal}/static/default/square.png" alt="暂无图片" width="350" height="265">
												</a>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${playsLength<6&&playsLength!=0}">
										<c:forEach items="${plays}" var="play">
											<div class="bd-item">
												<a target="_blank" href="${ctx}${play.url}">
													<c:if test="${empty play.merchantimage}">
														<img src="${ctxPortal}/static/default/square.png" alt="${udj:subString(play.merchantname,5)}" width="350" height="265">
													</c:if>
													<c:if test="${not empty play.merchantimage}">
														<img src="${ctx}${play.merchantimage}" alt="${udj:subString(play.merchantname,5)}" width="350" height="265">
													</c:if>
												</a>
												<div class="mark"></div>
													<a target="_blank" href="${ctx}${play.url}" class="link-text"><p class="text">${play.merchantdetail}</p></a>
												<div class="bd-content">
													<p>${udj:subString(play.merchantname,5)}(${udj:subString(play.destinationname,4) })</p>
													<span class="weye">${play.viewcount}</span>
												</div>
											</div>
										</c:forEach>
										<c:forEach begin="1" end="${6-playsLength}">
											<div class="bd-item">
												<a target="_blank" href="${ctx}${play.url}">
													<img src="${ctxPortal}/static/default/square.png" alt="暂无图片" width="350" height="265">
												</a>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${playsLength>=6}">
										<c:forEach items="${plays}" var="play">
											<div class="bd-item">
												<a target="_blank" href="${ctx}${play.url}">
													<c:if test="${empty play.merchantimage}">
														<img src="${ctxPortal}/static/default/square.png" alt="${udj:subString(play.merchantname,5)}" width="350" height="265">
													</c:if>
													<c:if test="${not empty play.merchantimage}">
														<img src="${ctx}${play.merchantimage}" alt="${udj:subString(play.merchantname,5)}" width="350" height="265">
													</c:if>
												</a>
												<div class="mark"></div>
													<a target="_blank" href="${ctx}${play.url}" class="link-text"><p class="text">${play.merchantdetail}</p></a>
												<div class="bd-content">
													<p>${udj:subString(play.merchantname,5)}(${udj:subString(play.destinationname,4) })</p>
													<span class="weye">${play.viewcount}</span>
												</div>
											</div>
										</c:forEach>
									</c:if>
								</div>
							</div>
						</div>
						<!-- 结束 -->
						<!-- 団游模块 -->
						<div class="xz-box">
							<div class="xz-heading">
								<a target="_blank" href="${ctx}tourism/merchant/group"><h2 class="tt-icon">
									<i class="t-icon"></i> 团游
								</h2></a>
								<div class="index-more">
									<a target="_blank" href="${ctx}tourism/merchant/group">更多 MORE</a>
								</div>
							</div>
							<div class="xz-bd-wrap">
								<div class="xz-bd clearfix">
									<c:set value="${fn:length(groupTravel)}" var="groupTravelLength"/>
									<c:if test="${groupTravelLength==0}">
										<c:forEach begin="1" end="6">
											<div class="bd-item">
												<a  target="_blank" href="${ctxIndex}${hotel.url}">
													<img src="${ctxPortal}/static/default/square.png" alt="暂无图片" width="350" height="265">
												</a>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${groupTravelLength<6&&groupTravelLength!=0}">
										<c:forEach items="${groupTravel}" var="gtt">
											<div class="bd-item">
												<a target="_blank" href="${ctxIndex}/${gtt.url}">
												 	<c:if test="${empty gtt.img}">
														<img src="${ctxPortal}/static/default/square.png" alt="${udj:subString(gtt.name,10)}" width="350" height="265">
													</c:if> 
													<c:if test="${not empty gtt.img}">
														<img src="${ctx}${gtt.img}" alt="${udj:subString(gtt.name,10)}" width="350" height="265">
													</c:if>
												 </a>
												<div class="mark"></div>
												<a target="_blank" href="${ctxIndex}/${gtt.url}" class="link-text"><p class="text">${udj:filterHtmlTags(gtt.detail)}</p></a>
												<div class="bd-content">
													<p>${udj:subString(gtt.name,10)}</p>
													<span class="weye">${gtt.viewcount}</span>
												</div>
											</div>
										</c:forEach>
										<c:forEach begin="1" end="${6-groupTravelLength}">
											<div class="bd-item">
												<a target="_blank" href="${ctxIndex}${gtt.url}">
													<img src="${ctxPortal}/static/default/square.png" alt="暂无图片" width="350" height="265" >
												 </a>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${groupTravelLength>=6}">
										<c:forEach items="${groupTravel}" var="gtt">
											<div class="bd-item">
												<a target="_blank" href="${ctx}${gtt.url}">
												 	<c:if test="${empty gtt.img}">
														<img src="${ctxPortal}/static/default/square.png" alt="暂无图片" width="350" height="265">
													</c:if> 
													<c:if test="${not empty gtt.img}">
														<img src="${ctx}${gtt.img}" alt="${gtt.name}" width="350" height="265">
													</c:if>
												 </a>
												<div class="mark"></div>
													<a target="_blank" href="${ctx}${gtt.url}" class="link-text"><p>${udj:filterHtmlTags(gtt.detail)}</p></a>
												<div class="bd-content">
													<p>${gtt.name}</p>
													<span class="weye">${gtt.viewcount}</span>
												</div>
											</div>
										</c:forEach>
									</c:if>
								</div>
							</div>
						</div>
						<!-- 结束 -->
					</div>
					<div class="bd-right">
						<div class="xz-heading">
							<h2 class="spot">
								<span class="sr-only">官方推荐</span>
							</h2>
							<div class="index-more">
								<a target="_blank" href="${ctx}tourism/merchant/list/iof/office.html">更多 MORE</a>
							</div>
						</div>
						<c:forEach items="${offices}" var="office">
							<div class="item">
								<a target="_blank" href="${ctx}${office.url}">
									<c:if test="${empty office.merchantimage}">
										<img src="${ctxPortal}/static/default/square.png" width="310" height="230" alt="暂无图片">
									</c:if>
									<c:if test="${not empty office.merchantimage}">
										<img src="${ctx}${office.merchantimage}" width="310" height="230" alt="${office.merchantname}" >
									</c:if>
								</a>
								<span class="ctibet"></span>
								<div class="mark"></div>
								<div class="bd-content">
									<p class="title">
										${office.merchantname}
									</p>
									<span class="weye">${office.viewcount}</span>
								</div>
							</div>
						</c:forEach>
						<%--广告--%>
						<div class="bd-ad-wrap">
							<c:forEach items="${adList}" var="ad">
								<div class="bd-ad">
									<a href="javascript:;"
										onclick="adver('${ad.url}','${ad.code }')" target="_blank"><img
											src="${ctx}${ad.imgUrl}" />
									</a>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="${root}/portal/headerFooterController/footer"></jsp:include>
		
		<script type="text/javascript">
			seajs.config({
				alias: {
					"jquery": "jquery/jquery/1.11.1/jquery.js",
					"avalon": "avalon/1.3.7/avalon.js",
					"common/css": "${ctxPortal}/assets/css/common.css",
					"merchant/css": "${ctxPortal}/assets/css/merchant.css",
					"footer/css": "${ctxPortal}/assets/css/footer.css"
				}
			});
		</script>
		<script type="text/javascript" src="${ctx}portal/assets/js/tourism/merchant_index.js"></script>
		
		<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
		<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
		
	</body>
</html>