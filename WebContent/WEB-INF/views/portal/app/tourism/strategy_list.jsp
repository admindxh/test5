<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/rimi-tags"  prefix="rimi"%>
<%@taglib prefix="udj" uri="/user-defined-jstl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"     prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="author" content="quansenx">
		<%@include file="/common-html/frontcommon.jsp" %>
		<c:if test="${programCode=='c1d94bbe-792e-11e4-b6ce-005056a05bc9'}">
			<meta name="keywords" content="西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化，西藏徒步，西藏攻略" />
			<meta name="description" content=" 西藏旅行综合攻略为您提供最权威的西藏旅行信息，包括：文化、交通、风景、地域、节日等，欢迎访问天下西藏官网。" />
			<title>西藏综合旅行攻略-天上西藏官网</title>
		</c:if>
		<c:if test="${programCode=='c1d87c3d-792e-11e4-b6ce-005056a05bc9'}">
			<meta name="keywords" content="西藏自驾游、西藏自驾游攻略" />
			<meta name="description" content="西藏自驾游：西藏自驾游_西南自驾游攻略_天下西藏，汇聚西藏的旅游景点风景景致，分享路途点滴，hold住最美回忆，西藏旅游攻略尽在天下西藏。" />
			<title>天上西藏-感受西藏魅力，体验西藏生活-天上西藏官网</title>
		</c:if>
		<c:if test="${programCode=='c1d8e87d-792e-11e4-b6ce-005056a05bc9'}">
			<meta name="keywords" content="骑行西藏，骑行西藏攻略 " />
			<meta name="description" content="骑行西藏攻略，帮助您体验西藏风光，分享路途点滴，hold住最美回忆，西藏旅游攻略尽在天下西藏。" />
			<title>天上西藏-感受西藏魅力，体验西藏生活-天上西藏官网</title>
		</c:if>
		<c:if test="${empty  programCode}">
			<meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
			<meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
			<title>天上西藏-感受西藏魅力，体验西藏生活-天上西藏官网</title>
		</c:if>
		<link rel="stylesheet" href="${ctxPortal}/assets/css/tourism/strategy_list.css" />
		<link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
		<!--[if lt IE 9]>
	    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
	    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
	    <![endif]-->
	    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
	    <script type="text/javascript">
	    seajs.config({
			alias: {
				"jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js",
				"avalon": "${ctxPortal}/modules/avalon/1.3.7/avalon.js",
				"map": "${ctxPortal}/assets/js/map.js",
				"bootstrap/css": "${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css",
				"header/css": "${ctxPortal}/assets/css/common.css",
				"footer/css": "${ctxPortal}/assets/css/footer.css",
				"commonjs":"${ctx}common-js/common.js",
				"paginator":"${ctxPortal }/modules/paginator/0.5/bootstrap-paginator.js",
			   }
		});
	    </script>
	  <style>
		  #strategy .media-heading a:hover{text-decoration: none;}
	  </style>
	</head>
	<body>
		<!-- header -->
		<jsp:include page="/WEB-INF/views/portal/app/header/index.jsp"></jsp:include>
		<div class="container main">
			<!-- banner -->
			<div class="banner">
				<!-- 地图 -->
				<div class="map-wrap">
					<!-- 热门景点列表 -->
					<ul id="hotspots" class="list-unstyled" ms-important="hotspots">
						<li ms-repeat="spots"><a target="_blank" href="#" ms-repeat-item="el">{{ item.value }}</a></li>
					</ul>
					<div id="map" style="height: 300px;"></div>
				</div>
			</div>
			<!-- banner End -->
			<!-- search
			<form id="searchForm" action="${ctx}tourism/strage/intoTraval" method="post">   -->
				
			<div class="search clearfix">
			
				<div class="region">
					<div  class="drop-down">
						<label>地区:</label>
						<div class="dropdown" id="destinationDropList">
						<input name="des" id="des" value="${des}" type="hidden"/>
							<button class="dropdown-toggle" type="button" id="region-menu" data-toggle="dropdown">
								<label>${desText}</label><span></span>
							</button>
							<ul id="destion" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
									<li role="presentation"  ><a role="menuitem" tabindex="-1" value="" href="#">全部目的地</a></li>
								<c:forEach var="obj" varStatus="sta" items="${destinationList }">
                        			<li role="presentation"  ><a role="menuitem" tabindex="${sta.index }" value="${obj.code }" href="#">${obj.destinationName }</a></li>
                        	   </c:forEach>
							</ul>
						</div>
					</div>
				</div>
				<div class="region scen">
					<div class="drop-down" >
						<label>景点:</label>
						<div class="dropdown" id="diviews">
						    <input name="view" id="view1" value="${view}" type="hidden"/>
							<button class="dropdown-toggle" type="button" id="scen-menu" data-toggle="dropdown">
								<label>${viewText}</label><span></span>
								</button>
								<ul id="view"  class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
										   <li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value=''>全部景点</a></li>
											<c:forEach var="obj" varStatus="sta" items="${viewList }">
			                        			<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value='${obj.code }'>${obj.viewName }</a></li>
			                        	   </c:forEach>	
								</ul>
						</div>
					</div>
				</div>
				<div class="region scen" style="">
					<div class="drop-down" id="glss">
						<label>攻略所属:</label>
						<div class="dropdown">
								<input name=programCode value="${programCode }" id="programCode" type="hidden"/>
							<button class="dropdown-toggle" type="button" id="scen-menu" data-toggle="dropdown" style="width: 112px;">
								<label style="width: 80px;">${glText }</label><span></span>
								</button>
							    <ul id="type" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        			<li role="presentation"><a role="menuitem" tabindex="-1" value="" href="#">所有</a></li>
								<c:forEach var="obj" varStatus="sta" items="${programaList1 }">
                        			<li role="presentation"><a role="menuitem" tabindex="${sta.index }" value="${obj.code }" href="#">${obj.programaName }</a></li>
                        	   </c:forEach>
							</ul>
						</div>
					</div>
				</div>
				<div class="region scen" style="">
					<div class="drop-down">
						<label>攻略类型:</label>
						<div class="dropdown" id="gllx">
								<input name="isOfficial" id="isOfficial" value="${isOfficial }" type="hidden"/>
							<button class="dropdown-toggle" type="button" id="scen-menu" data-toggle="dropdown" style="width: 122px;">
								<label style="width: 90px;">${gllxText }</label><span></span>
								</button>
							    <ul id="type" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        			<li role="presentation"><a role="menuitem" tabindex="0" value="0" href="#">用户上传</a></li>
                        			<li role="presentation"><a role="menuitem" tabindex="1" value="1" href="#">官方发布</a></li>
							    </ul>
						</div>
					</div>
				</div>
				<div class="fuzzy-search">
					<input type="text" name="keyword" id="keyword" value="${keyword }" style="width:160px;" placeholder="请输入关键字" /><button  id="searchstrety"><img src="${ ctxPortal}/assets/icon/shape.png" /></button>
				</div>
				<div class="hot-link" style="display: none;">
					<label>热门景点:</label>
					<c:forEach items="${viewByGone}" var="view">
						<c:if test="${not  empty view.viewcode }">
							<div>
								<a title="${view.viewname }" target="_self" href="${ctx}tourism/strage/intoTravalviews/${view.viewcode}.html">${view.viewname }</a>
							</div>
						</c:if>
					</c:forEach>
					<c:if test="${empty  viewByGone}">暂无热门景点</c:if>
				</div>
			</div>
			<input type="hidden" name="order" id="orderId" value="${order }"/>
			 <!-- </form>   --> 
			<!-- search End -->
			<!-- content -->
			<div class="row" style="width: 1200px;">
				<div class="col-xs-9" ms-important="allActivityView">
					<ul class="listing clearfix">
						<li 
							<c:if test="${order ==1  }">class="down" </c:if>
						>
						<a href="javascript:void(0);" id="zx">
							最新
						</a>
						</li>
						<li
							<c:if test="${order ==2  }">class="down" </c:if>
						>
						<a href="javascript:void(0);"  id="sc">
							最多收藏
						</a>
						</li>
						<li 
							<c:if test="${order ==3  }">class="down" </c:if>
						>
						<a href="javascript:void(0);" id="pl">
							最多评论
						</a>
						
						</li>
					</ul>
					<div class="strategy"  id="strategy">
						<div class="media" ms-repeat="data" ms-attr-class="one:$first">
							<div class="media-left">
								<div class="strategy-img" >
									<a  target="_blank" ms-href="${ctxIndex}{{el.url}}">
										<img ms-attr-alt="el.travelTitle" ms-src="{{el.travelImgUrl==''||el.travelImgUrl==null?'${ctx }/portal/static/default/square.png':el.travelImgUrl}}" style="width: 201px;height: 121px;"  />
										<img ms-if="el.isOfficial==1" class="ctibet" src="${ctxPortal}assets/icon/ctibet_yxz.png" />
									</a>
								</div>
							</div>
							<div class="media-body clearfix">
								<h4 class="media-heading"
                                    style="width: 450px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                    <a ms-href="${ctxIndex}{{el.url}}" target="_blank">{{el.travelTitle}}</a>
                                </h4>
								<div class="post-message">
									<img style="width: 18px;height: 18px;"
										 ms-src="${ctx}/{{el.pic==null||el.pic==''?'portal/static/default/male.jpg':el.pic}}"/>
									<label>作者：</label><span class="writer">
									{{el.isOfficial==1?el.userName:''}}
									<a ms-if="el.isOfficial!=1"
									   ms-href="${ctxIndex }/member/show/{{el.memberCode}}.html"
									   target="_blank">{{el.userName}}</a>
									</span>
									<label class="ml10">发布时间：</label><span>{{el.createTime|date('yyyy-MM-dd')}}</span>
								</div>
								<p class="strategy-info js_sub_info" style="word-break:break-all;">{{el.travelContent|html}}</p>
								<ul class="comment clearfix">
									<li>
										<img src="${ctxPortal}/assets/icon/eye_ac.png" title="查看数" /><span>{{el.viewCount==null?0:el.viewCount}}</span>
									</li>
									<li>
										<img src="${ctxPortal}/assets/icon/fav_b.png" title="收藏数" /><span>{{el.faveteCount==null?0:el.faveteCount}}</span>
									</li>
									<li>
										<img src="${ctxPortal}/assets/icon/_like.png" title="赞数" /><span>{{el.praiseCount==null?0:el.praiseCount}}</span>
									</li>
									<li>
										<img src="${ctxPortal}/assets/icon/chat.png" title="回复数" /><span>{{el.replyCount==null?0:el.replyCount}}</span>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<!-- page -->
					<div id="allActivityPage" class="paging paging-center paging-lg page"></div>
					<!-- page End -->
				</div>
				<div class="col-xs-3">
					<div class="uploading">
						<img src="${ctxPortal}/static/images/uploading_bg.png" />
						<div></div>
						<button type="button" onclick="uploadStrage()">上传攻略</button>
					</div>
					<c:if test="${not  empty hotMct}">
					<div class="hot"><!-- 商户 -->
						<div class="header"></div>
						<div class="hot-content">
							<!-- rank 1 -->
							<c:forEach var="hot" items="${hotMct}" varStatus="status">
								<div class="media">
								<h5><a href="${ctx }${hot.merchant.url}" target="_blank" >${hot.merchant.merchantName}</a></h5>
								<div class="media-left">
									<div class="hot-img">
										<a href="${ctx }${hot.merchant.url}" target="_blank">
											<img
													<c:if test="${empty  hot.merchant.merchantImage}">
														src="${ctx }portal/static/default/square.png"
													</c:if>
													<c:if test="${not empty  hot.merchant.merchantImage}">
														src="${ctx }/${hot.merchant.merchantImage}"
													</c:if>
													alt="${hot.merchant.merchantName}"/>
										</a>
										<img class="rank-num" src="${ctxPortal}/assets/icon/num${status.index+1}.png" />
									</div>
								</div>
								<div class="media-body">
									<div class="mer-info">
										<span>价格:${empty hot.merchant.price || "0" eq hot.merchant.price ? "暂无" : hot.merchant.price }</span>
										<p mcode="${hot.merchant.code}" class="mer-score" data-score="5" data-readonly="true"></p>
									</div>
								</div>
							</div>
							</c:forEach>
						</div>
					</div>
					</c:if>
					<c:forEach items="${adlist}" var="ad">
						<div class="advertisement">
							<a href="javascript:;" onclick="adver('${ad.url}','${ad.code }')" target="_blank">
								<img style="width:250px;height:185px;" src="${ctx}/${ad.imgUrl}" />
							</a>
						</div>
					</c:forEach>
				</div>
			</div>
			<!-- content End -->
		</div>
		<!-- footer -->
		<jsp:include page="/WEB-INF/views/portal/app/footer/index.jsp"></jsp:include>
		<%-- <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script> --%>
		<script type="text/javascript">
			var basePath_ = "${ctx}";
			var basePathPortal = "${ctxPortal}";
			var isOfficial_ = "${isOfficial}";
			var programCode_ = "${programCode }";
			var des_ = '${des}';
			var view_ = '${view}';
			var order = ${order};
			var keyword_ = '${keyword}';
		</script>
		<script type="text/javascript" src="${ctx}portal/assets/js/tourism/strategy_list_js.js"></script>
		<script type="text/javascript" src="${ctx}portal/assets/js/tourism/tourism.js"></script>
		<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
		<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
		<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
	</body>
</html>