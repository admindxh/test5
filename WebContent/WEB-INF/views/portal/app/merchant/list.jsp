<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.rimi.ctibet.domain.LogUser" session="true"%>
<%@page import="com.rimi.ctibet.domain.Content"%>
<%@taglib uri="/rimi-tags" prefix="rimi"%>
<%@taglib prefix="udj" uri="/user-defined-jstl"%>
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
		<title>商户列表页_天上西藏</title>
		<%@include file="/common-html/commonPortal.jsp"%>

		<!--[if lt IE 9]>
		<script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
		<script src="${ctxPortal}/modules/fix/respond.min.js"></script>
	<![endif]-->

	</head>
	<body>
		<jsp:include page="${root}/portal/headerFooterController/header"></jsp:include>
		<jsp:include page="../login/login_modal.jsp"></jsp:include>
		<div class="container">
			<div class="static-banner">
				<!-- 地图 -->
				<div class="map-wrap">
					<!-- 热门景点列表 -->
					<ul id="hotspots" class="list-unstyled" ms-important="hotspots">
						<li ms-repeat="spots">
							<a href="#" ms-repeat-item="el">{{ item.value }}</a>
						</li>
					</ul>
					<div id="map" style="height: 300px;"></div>
				</div>
			</div>
			<div class="bd">
				<div class="form-fields xz-box">
					<div class="search">
						<form action="${ctx}tourism/merchant/merchantList" class="form-inline" method="post" id="searchfrm">
							<input type="hidden" value="<c:out value='${type}'/>" name="type" />
							<div class="form-group" style="width: 236px;">
								<label>
									地区：	
								</label>

								<div class="dropdown">
									<button class="btn dropdown-toggle" type="button"
										data-toggle="dropdown" aria-expanded="true" id="region-menu">
										<span class="text"><c:out value='${destinationName}'/></span>
										<span class="caret"></span>
									</button>
									<input type="hidden" name="destinationCode"
										id="destinationCode" value="<c:out value='${destinationCode}'/>" />
									<ul class="dropdown-menu" role="menu"
										aria-labelledby="dropdownMenu1"
										myMethod="ajaxGetViewByDescode">
										<li role="presentation" data-name="destinationCode"
											data-code="" onclick="ajaxGetViewByDescode(this)"
											myMethod="ajaxGetViewByDescode">
											<a href="javascript:;" role="menuitem" tabindex="-1">全部地区</a>
										</li>
										<c:forEach items="${destinations}" var="destination">
											<li role="presentation" data-name="destinationCode"
												data-code="${destination.code}"
												onclick="ajaxGetViewByDescode(this)"
												myMethod="ajaxGetViewByDescode">
												<a href="javascript:;" role="menuitem" tabindex="-1"><c:out value='${destination.destinationName}'/></a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
							<div class="form-group" style="width: 236px;">
								<label>
									景点：
								</label>

								<div class="dropdown">
									<button class="btn dropdown-toggle" type="button"
										data-toggle="dropdown" aria-expanded="true">
										<span class="text" id="viewName"><c:out value='${viewName}'/></span>
										<span class="caret"></span>
									</button>
									<%--		隐藏高级查询返回的参数	--%>
									<input type="hidden" name="viewCode" value="<c:out value='${viewCode}'/>"
										id="viewCode" />
									<ul class="dropdown-menu" role="menu"
										aria-labelledby="dropdownMenu1" id="appendView">
										<%--<input type="hidden" name="viewCode" value="${viewCode}" id="viewCode" />--%>
										<%--<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" id="appendView">--%>
										<li role="presentation" data-name="viewCode" data-code="">
											<a href="javascript:;" role="menuitem" tabindex="-1">全部景点</a>
										</li>
										<c:forEach items="${views}" var="view">
											<li role="presentation" data-name="viewCode"
												data-code="${view.code}">
												<a href="javascript:;" role="menuitem" tabindex="-1">${view.viewName}</a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
							<div class="form-group">
								<div class="input-group">
									<input type="text" class="form-control" name="keyWord"
										id="keyWord" value="<c:out value='${keyWord}'/>" placeholder="关键字搜索">
									<div class="input-group-addon btn-addon">
										<button type="submit">
											<span class="sr-only">查询</span>
										</button>
									</div>
								</div>
							</div>
							<div class="form-group hot-search" style="width: 273px;">
								<label for="hotSearch">
									热门景点:&nbsp;
								</label>
								<p class="form-control-static hot-tags">
									   <c:forEach items="${viewByGone}" var="view">
											<c:if test="${not  empty view.viewcode }">
													<a title="${view.viewname }" target="_self" href="${ctx}tourism/merchant/merchantList/${view.viewcode}.html">
														${udj:subString(view.viewname,3)}
													</a>
											</c:if>
										</c:forEach>
								      <c:if test="${empty  viewByGone}">暂无热门景点</c:if>
								</p>
							</div>
						</form>
					</div>
				</div>
				<div class="clearfix">
					<div class="bd-left">
						<div class="xz-box">
							<div class="bd-heading">
								<div class="bd-sort">
									<a
										href="${ctx}tourism/mlist/2${viewCode}/2${destinationCode}/2${keyWord}/2${type}/2favorite.html"
										<c:if test="${rule=='favorite' }">class="active down"</c:if>
										id="favorite">最热门</a>
									<a
										href="${ctx}tourism/mlist/2${viewCode}/2${destinationCode}/2${keyWord}/2${type}/2collect.html"
										<c:if test="${rule=='collect' }">class="active down"</c:if>
										id="collect">最多收藏</a>
								</div>
							</div>
							<div class="bd-list mbd-list" ms-important="allActivityView">

								<div class="media" ms-repeat="data" ms-attr-class="one:$first">
									<a target="_blank" class="media-left" ms-href="${ctx}{{el.url}}"> <span
										ms-class="ctibet:el.isRecommend"></span>
											<img ms-attr-alt="el.merchantName"
											ms-if="el.merchantImage" ms-src="${ctx}{{el.merchantImage}}"
											width="160" height="120" class="img-rounded">
											<img ms-if="!el.merchantImage"
											src="${ctxPortal}/static/default/square.png" width="160"
											height="120" class="img-rounded"> </a>
									<div class="media-body">
										<!-- .ihotel 住宿，igroup 团游， iyl 娱乐，ifood 美食 -->
										<h4 class="media-heading">
											<a target="_blank" ms-href="${ctx}{{el.url}}">
												<span class="ihotel" ms-if="el.type == 'merchantType422672563900'">
                                                   {{el.merchantName}}-------({{el.destinationName}})
                                                </span>
												<span class="ifood" ms-if="el.type == 'merchantType422672572609'">
                                                    {{el.merchantName}}-------({{el.destinationName}})
                                                </span>
												<span class="iyl" ms-if="el.type == 'merchantType422672581150'">
                                                    {{el.merchantName}}-------({{el.destinationName}})
                                                </span>
                                                 <span  ms-if="el.type != 'merchantType422672581150'&&el.type!='merchantType422672572609'&&el.type!='merchantType422672563900'">
                                                    {{el.merchantName}}-------({{el.destinationName}})
                                                </span>
                                            </a>
										</h4>

										<p class="local">
											{{el.merchantSummary}}
										</p>

										<p class="bd-text">
											{{el.merchantDetail}}
										</p>
										<p class="types" style="position: relative;">
											<span style="position: absolute;top: 0;left: 0;padding: 0;margin: 0;color: #898989;">来源：{{el.ctripUrl==''||el.ctripUrl==null?'天上西藏':'携程网'}}</span>
											<span class="heart">{{el.falsefavoritenum}}</span>
										</p>

										<div class="reserve">
											<div class="price">
												<span>{{el.price==null||el.price==""||el.price=='0'?"暂无价格":("&yen;"+el.price+"起")}}</span>
											</div>
											<div class="btn-reserve">
												<a href="javascript:void(0)"
													ms-click="onclickHref(el,'<c:out value='${type}'/>')">{{el.ctripUrl==null||el.ctripUrl==""?"查看详情":"立即预订"}}</a>
											</div>

										</div>
									</div>
								</div>
								<div id="allActivityPage"
									class="paging paging-center paging-lg paging-white"></div>
							</div>
						</div>
					</div>
					<div class="bd-right">
						<div class="xz-box">
							<div class="menu m-menu pull-left">
								<c:if test="${type=='0'}">
									<a
										href="${ctx}tourism/merchant/merchantList/1${viewCode}/1${destinationCode}/1${keyWord}/10.html"
										class="all active"><span class="arrow"></span>全部分类</a>
								</c:if>
								<c:if test="${type!='0'}">
									<a
										href="${ctx}tourism/merchant/merchantList/1${viewCode}/1${destinationCode}/1${keyWord}/10.html"
										class="all"><span class="arrow"></span>全部分类</a>
								</c:if>
								<c:if test="${type==mt1.code}">
									<a
										href="${ctx}tourism/merchant/merchantList/1${viewCode}/1${destinationCode}/1${keyWord}/1${mt1.code}.html"
										class="hotel m-menu2 active"><span class="arrow"></span>${udj:subString(mt1.name,4)}</a>
								</c:if>
								<c:if test="${type!=mt1.code}">
									<a
										href="${ctx}tourism/merchant/merchantList/1${viewCode}/1${destinationCode}/1${keyWord}/1${mt1.code}.html"
										class="hotel m-menu2"><span class="arrow"></span>${udj:subString(mt1.name,4)}</a>
								</c:if>
								<c:if test="${type==mt2.code}">
									<a
										href="${ctx}tourism/merchant/merchantList/1${viewCode}/1${destinationCode}/1${keyWord}/1${mt2.code}.html"
										class="food m-menu2 active"><span class="arrow" id=""></span>${udj:subString(mt2.name,4)}</a>
								</c:if>
								<c:if test="${type!=mt2.code}">
									<a
										href="${ctx}tourism/merchant/merchantList/1${viewCode}/1${destinationCode}/1${keyWord}/1${mt2.code}.html"
										class="food m-menu2"><span class="arrow" id=""></span>${udj:subString(mt2.name,4)}</a>
								</c:if>
								<c:if test="${type==mt3.code}">
									<a
										href="${ctx}tourism/merchant/merchantList/1${viewCode}/1${destinationCode}/1${keyWord}/1${mt3.code}.html"
										class="amuse m-menu2 active"><span class="arrow" id=""></span>${udj:subString(mt3.name,4)}</a>
								</c:if>
								<c:if test="${type!=mt3.code}">
									<a
										href="${ctx}tourism/merchant/merchantList/1${viewCode}/1${destinationCode}/1${keyWord}/1${mt3.code}.html"
										class="amuse m-menu2"><span class="arrow" id=""></span>${udj:subString(mt3.name,4)}</a>
								</c:if>
								<c:if test="${type=='1'}">
									<a
										href="${ctx}tourism/merchant/merchantList/1${viewCode}/1${destinationCode}/1${keyWord}/11.html"
										class="group m-menu2 active"><span class="arrow" id=""></span>团游</a>
								</c:if>
								<c:if test="${type!='1'}">
									<a
										href="${ctx}tourism/merchant/merchantList/1${viewCode}/1${destinationCode}/1${keyWord}/11.html"
										class="group m-menu2"><span class="arrow" id=""></span>团游</a>
								</c:if>
							</div>
						</div>
						<div class="bd-ad-wrap">
							<c:forEach items="${adList}" var="ad">
								<div class="bd-ad">
									<a href="javascript:;"
										onclick="adver('${ad.url}','${ad.code }')" target="_blank"><img
											src="${ctx}/${ad.imgUrl}" /> </a>
								</div>
							</c:forEach>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="${root}/portal/headerFooterController/footer"></jsp:include>
		<script>
		    seajs.config({
		        alias: {
		            "jquery": "jquery/jquery/1.11.1/jquery.js",
		            "avalon": "avalon/1.3.7/avalon.js",
		            "map": "${ctxPortal}/assets/js/map.js",
		            "common/css": "${ctxPortal}/assets/css/common.css",
		            "footer/css": "${ctxPortal}/assets/css/footer.css",
		            "paginator": "paginator/0.5/bootstrap-paginator.js",
		            "merchant/css": "${ctxPortal}/assets/css/merchant.css"
		        }
		    });
		    seajs.use(['common/css', 'footer/css', 'merchant/css', 'slick/slick.css']);
		    
		    var basePath_ = "${ctx}";
			var basePathPortal = "${ctxPortal}";
		    
			var destinationName_ = '${destinationName}';
			var type_ = ${type };
			var keyWord_ = '${keyWord}';
			var destinationCode_ = '${destinationCode}';
			var viewCode_ = '${viewCode}';
			var rule_ = '${rule}';
			
		</script>
		<script type="text/javascript" src="${ctx}portal/assets/js/tourism/merchant_list.js"></script>
		<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
		<%-- <jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include> --%>
		<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
		
	</body>
</html>