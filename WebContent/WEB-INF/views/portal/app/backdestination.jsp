<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common-html/common.jsp" %>

<!DOCTYPE HTML>
<html lang="en">

<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
	<meta name="author" content="quansenx">
	<meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
	<meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
	<title>天上西藏-感受西藏魅力，体验西藏生活-天上西藏官网</title>
	<link rel="stylesheet" href="${ctxPortal}/assets/css/tourism/destination.css"/>
	<script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
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
							<div class="header clearfix">
								<div class="title"></div>
								<div class="shar">
									<a class="weibo" href="#"></a>
									<a class="sina" href="#"></a>
									<a class="qzone" href="#"></a>
								</div>
							</div>
							<div class="content">
								<p>
									${destination.destinationIntroduce }
								</p>
							</div>
						</div>
					</div>
					<div class="col-xs-3">
						<div class="air-ticket">
							<div class="air-header">
								<img src="${ctxPortal}/assets/icon/aircraft.png" alt="" />
								<h5>机票搜索<br/>TICKET SEARCH</h5>
								<a href="#"><span>更多 MORE</span><img src="${ctxPortal}/assets/icon/more_white.png" alt=""/></a>
							</div>
							<div class="colour-bar"></div>
							<div class="air-content">
								<div>
									<label>出发日期</label>
									<input type="text" value="2014-11-20" />
								</div>
								<div>
									<label>出发城市</label>
									<input type="text" value="成都市" />
								</div>
								<div>
									<label>到达城市</label>
									<input type="text" value="拉萨市" />
								</div>
								<div>
									<label>特　　价</label><span class="bargain-price">650</span><span class="price-unit">元起</span>
								</div>
								<button></button>
							</div>
						</div>
					</div>
				</div>
                <!-- tourist -->
	 	        <jsp:include page="${root}portal/distination/tourist" flush="ture"/> 
                <!-- 精彩旅程 -->
				<div class="row">
					<div class="col-xs-9">
						<div class="journey">
							<div class="header">
								<div class="left"></div>
								<div class="right"><a href="${ctx }tourism/strage /intoTraval">更多MORE</a></div>
							</div>
							<div class="journey-img clearfix">
								<div class="img-left float-left">
								<!-- <img src="${ctxPortal}/static/images/destination_tour_img.png" alt=""/> -->
								    <a href="${ctx }/${travelMap.travelUrlList[0]}">
								    <img  style="width:518px;height:256px;" src="${ctx}${travelMap.travelList[0].url}" alt=""/>
								    </a>
									<div class="info">
										<p>${travelMap.travelList[0].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png" alt=""/><span>${travelMap.travelPraiseList[0].falseViewcount }</span>
											<img src="${ctxPortal}/assets/icon/star_white.png" alt=""/><span>${travelMap.travelPraiseList[0].falseFavoriteNum }</span>
										</div>
									</div>
								</div>
								<div class="img-right float-right">
								    <a href="${ctx }/${travelMap.travelUrlList[1]}">
									<img style="width:342px;height:256px;" src="${ctx}${travelMap.travelList[1].url}" alt=""/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[1].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png" alt=""/><span>${travelMap.travelPraiseList[1].falseViewcount }</span>
											<img src="${ctxPortal}/assets/icon/star_white.png" alt=""/><span>${travelMap.travelPraiseList[1].falseFavoriteNum }</span>
										</div>
									</div>
								</div>
							</div>
							<div class="journey-img mt10 clearfix">
								<div class=" img-right float-left">
								    <a href="${ctx }/${travelMap.travelUrlList[2]}">
									<img style="width:342px;height:256px;" src="${ctx}${travelMap.travelList[2].url}" alt=""/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[2].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png" alt=""/><span>${travelMap.travelPraiseList[2].falseViewcount }</span>
											<img src="${ctxPortal}/assets/icon/star_white.png" alt=""/><span>${travelMap.travelPraiseList[2].falseFavoriteNum }</span>
										</div>
									</div>
								</div>
								<div class="img-left float-right">
								    <a href="${ctx }/${travelMap.travelUrlList[3]}">
									<img  style="width:518px;height:256px;" src="${ctx}${travelMap.travelList[3].url}" alt=""/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[3].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png" alt=""/><span>${travelMap.travelPraiseList[3].falseViewcount }</span>
											<img src="${ctxPortal}/assets/icon/star_white.png" alt=""/><span>${travelMap.travelPraiseList[3].falseFavoriteNum }</span>
										</div>
									</div>
								</div>
							</div>
							<div class="journey-img mt10 clearfix">
								<div class="img-left float-left">
								    <a href="${ctx }/${travelMap.travelUrlList[4]}">
									<img style="width:518px;height:256px;" src="${ctx}${travelMap.travelList[4].url}" alt=""/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[4].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png" alt=""/><span>${travelMap.travelPraiseList[4].falseViewcount }</span>
											<img src="${ctxPortal}/assets/icon/star_white.png" alt=""/><span>${travelMap.travelPraiseList[4].falseFavoriteNum }</span>
										</div>
									</div>
								</div>
								<div class="img-right float-right">
								    <a href="${ctx }/${travelMap.travelUrlList[5]}">
									<img style="width:342px;height:256px;" src="${ctx}${travelMap.travelList[5].url}" alt=""/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[5].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png" alt=""/><span>${travelMap.travelPraiseList[5].falseViewcount }</span>
											<img src="${ctxPortal}/assets/icon/star_white.png" alt=""/><span>${travelMap.travelPraiseList[5].falseFavoriteNum }</span>
										</div>
									</div>
								</div>
							</div>
							<div class="journey-img mt10 clearfix">
								<div class=" img-right float-left">
								    <a href="${ctx }/${travelMap.travelUrlList[6]}">
									<img style="width:342px;height:256px;" src="${ctx}${travelMap.travelList[6].url}" alt=""/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[6].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png" alt=""/><span>${travelMap.travelPraiseList[6].falseViewcount }</span>
											<img src="${ctxPortal}/assets/icon/star_white.png" alt=""/><span>${travelMap.travelPraiseList[6].falseFavoriteNum }</span>
										</div>
									</div>
								</div>
								<div class="img-left float-right">
								    <a href="${ctx }/${travelMap.travelUrlList[7]}">
									<img style="width:518px;height:256px;" src="${ctx}${travelMap.travelList[7].url}"  alt=""/>
									</a>
									<div class="info">
										<p>${travelMap.travelList[7].contentTitle }</p>
										<div>
											<img src="${ctxPortal}/assets/icon/eye_white.png" alt=""/><span>${travelMap.travelPraiseList[7].falseViewcount }</span>
											<img src="${ctxPortal}/assets/icon/star_white.png" alt=""/><span>${travelMap.travelPraiseList[7].falseFavoriteNum }</span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-3">
						<div class="hotel">
							<div class="header">
								<div class="left">${merchantTwoMap.merchantType }</div>
								<div class="right"><a href="${ctx }/tourism/merchant/merchantList">更多MORE</a></div>
							</div>
							<div class="hotel-info">
								<h5>${merchantTwoMap.merchantList[0].merchantName }</h5>
								<div class="media message">
									<a class="media-left" href="${ctx }${merchantTwoMap.merchantUrlList[0] }">
										<img style="width:68px;height:68px;" src="${ctx }${merchantTwoMap.merchantList[0].merchantImage}" alt="">
										<c:if test="${merchantTwoMap.merchantList[0].isRecommend eq 1}">
										<img class="ctibet" src="${ctxPortal}/assets/icon/ctibet_yxz.png" alt=""/>
										</c:if>										    
									</a>
									<div class="media-body">
										<p>${merchantTwoMap.merchantList[0].merchantSummary }</p>
									</div>
									<div class="loving">
										<img src="${ctxPortal}/assets/icon/star.png" alt=""/>
										<label>${merchantTwoMap.merchantPraiseList[0].falsePraise}</label>
									</div>
								</div>
								<div class="line"></div>
							</div>
							<div class="hotel-info mt18">
								<h5>${merchantTwoMap.merchantList[1].merchantName }</h5>
								<div class="media message">
									<a class="media-left" href="${ctx }${merchantTwoMap.merchantUrlList[1] }">
										<img style="width:68px;height:68px;" src="${ctx }${merchantTwoMap.merchantList[1].merchantImage}" alt="">
										<c:if test="${merchantTwoMap.merchantList[1].isRecommend eq 1}">
										<img class="ctibet" src="${ctxPortal}/assets/icon/ctibet_yxz.png" alt=""/>
										</c:if>										
									</a>
									<div class="media-body">
										<p>${merchantTwoMap.merchantList[1].merchantSummary }</p>
									</div>
									<div class="loving">
										<img src="${ctxPortal}/assets/icon/star.png" alt=""/>
										<label>${merchantTwoMap.merchantPraiseList[1].falsePraise}</label>
									</div>
								</div>
								<div class="line"></div>
							</div>
							<div class="hotel-info mt18">
								<h5>${merchantTwoMap.merchantList[2].merchantName }</h5>
								<div class="media message">
									<a class="media-left" href="${ctx }${merchantTwoMap.merchantUrlList[2] }">
										<img style="width:68px;height:68px;" src="${ctx }${merchantTwoMap.merchantList[2].merchantImage}" alt="">
										<c:if test="${merchantTwoMap.merchantList[2].isRecommend eq 1}">
										<img class="ctibet" src="${ctxPortal}/assets/icon/ctibet_yxz.png" alt=""/>
										</c:if>										
									</a>
									<div class="media-body">
										<p>${merchantTwoMap.merchantList[2].merchantSummary }</p>
									</div>
									<div class="loving">
										<img src="${ctxPortal}/assets/icon/star.png" alt=""/>
										<label>${merchantTwoMap.merchantPraiseList[2].falsePraise}</label>
									</div>
								</div>
								<div class="line"></div>
							</div>
							<div class="hotel-info mt18">
								<h5>${merchantTwoMap.merchantList[3].merchantName }</h5>
								<div class="media message">
									<a class="media-left" href="${ctx }${merchantTwoMap.merchantUrlList[3] }">
										<img style="width:68px;height:68px;" src="${ctx }${merchantTwoMap.merchantList[3].merchantImage}" alt="">
										<c:if test="${merchantTwoMap.merchantList[3].isRecommend eq 1}">
										<img class="ctibet" src="${ctxPortal}/assets/icon/ctibet_yxz.png" alt=""/>
										</c:if>											
									</a>
									<div class="media-body">
										<p>${merchantTwoMap.merchantList[3].merchantSummary }</p>
									</div>
									<div class="loving">
										<img src="${ctxPortal}/assets/icon/star.png" alt=""/>
										<label>${merchantTwoMap.merchantPraiseList[2].falsePraise}</label>
									</div>
								</div>
								<div class="line"></div>
							</div>
						</div>
						<div class="hotel food">
							<div class="header">
								<div class="left">${merchantThreeMap.merchantType }</div>
								<div class="right"><a href="${ctx }/tourism/merchant/merchantList">更多MORE</a></div>
							</div>
							<div class="hotel-info">
								<h5>${merchantThreeMap.merchantList[0].merchantName }</h5>
								<div class="media message">
									<a class="media-left" href="${ctx }${merchantThreeMap.merchantUrlList[0] }">
										<img style="width:68px;height:68px;" src="${ctx }${merchantThreeMap.merchantList[0].merchantImage}" alt="">
										<c:if test="${merchantThreeMap.merchantList[0].isRecommend eq 1}">
										<img class="ctibet" src="${ctxPortal}/assets/icon/ctibet_yxz.png" alt=""/>
										</c:if>	
									</a>
									<div class="media-body">
										<p>${merchantThreeMap.merchantList[0].merchantSummary}</p>
									</div>
									<div class="loving">
										<img src="${ctxPortal}/assets/icon/star.png" alt=""/>
										<label>${merchantThreeMap.merchantPraiseList[0].falsePraise}</label>
									</div>
								</div>
								<div class="line"></div>
							</div>
							<div class="hotel-info mt18">
								<h5>${merchantThreeMap.merchantList[1].merchantName }</h5>
								<div class="media message">
									<a class="media-left" href="${ctx }${merchantThreeMap.merchantUrlList[1] }">
									<img style="width:68px;height:68px;" src="${ctx }${merchantThreeMap.merchantList[1].merchantImage}" alt="">
									<c:if test="${merchantThreeMap.merchantList[1].isRecommend eq 1}">
										<img class="ctibet" src="${ctxPortal}/assets/icon/ctibet_yxz.png" alt=""/>
										</c:if>
									</a>
									<div class="media-body">
										<p>${merchantThreeMap.merchantList[1].merchantSummary}</p>
									</div>
									<div class="loving">
										<img src="${ctxPortal}/assets/icon/star.png" alt=""/>
										<label>${merchantThreeMap.merchantPraiseList[1].falsePraise}</label>
									</div>
								</div>
								<div class="line"></div>
							</div>
							<div class="hotel-info mt18">
								<h5>${merchantThreeMap.merchantList[2].merchantName }</h5>
								<div class="media message">
									<a class="media-left" href="${ctx }${merchantThreeMap.merchantUrlList[2] }">
										<img style="width:68px;height:68px;" src="${ctx }${merchantThreeMap.merchantList[2].merchantImage}" alt="">
										<c:if test="${merchantThreeMap.merchantList[2].isRecommend eq 1}">
										<img class="ctibet" src="${ctxPortal}/assets/icon/ctibet_yxz.png" alt=""/>
										</c:if>
									</a>
									<div class="media-body">
										<p>${merchantThreeMap.merchantList[2].merchantSummary}</p>
									</div>
									<div class="loving">
										<img src="${ctxPortal}/assets/icon/star.png" alt=""/>
										<label>${merchantThreeMap.merchantPraiseList[2].falsePraise}</label>
									</div>
								</div>
								<div class="line"></div>
							</div>
							<div class="hotel-info mt18">
								<h5>${merchantThreeMap.merchantList[3].merchantName }</h5>
								<div class="media message">
									<a class="media-left" href="${ctx }${merchantThreeMap.merchantUrlList[3] }">
										<img style="width:68px;height:68px;" src="${ctx }${merchantThreeMap.merchantList[3].merchantImage}" alt="">
										<c:if test="${merchantThreeMap.merchantList[3].isRecommend eq 1}">
										<img class="ctibet" src="${ctxPortal}/assets/icon/ctibet_yxz.png" alt=""/>
										</c:if>
									</a>
									<div class="media-body">
										<p>${merchantThreeMap.merchantList[3].merchantSummary}</p>
									</div>
									<div class="loving">
										<img src="${ctxPortal}/assets/icon/star.png" alt=""/>
										<label>${merchantThreeMap.merchantPraiseList[3].falsePraise}</label>
									</div>
								</div>
								<div class="line"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- footer -->
		<jsp:include page="${root}portal/headerFooterController/footer" flush="ture"/> 
		<script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
		<script>
			// Set configuration
			seajs.config({
				alias: {
					"jquery": "jquery/1.11.1/jquery.js",
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
		<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
	</body>

</html>
