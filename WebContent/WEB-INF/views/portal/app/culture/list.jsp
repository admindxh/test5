<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="author" content="quansenx">
	<meta name="keywords" content=" 西藏${typeName}" />
	<meta name="description" content="读西藏${typeName}。了解西藏${typeName}，尽在天上西藏官网。" />
	<title>读西藏${typeName}_天上西藏</title>
	<%@include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css">
	<!--[if lt IE 9]>
	<script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
	<script src="${ctxPortal}modules/fix/respond.min.js"></script>
	<![endif]-->
	<script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
	<script type="text/javascript">
	// Set configuration
	seajs.config({
		alias: {
			"jquery": "${ctxPortal}modules/jquery/jquery/1.11.1/jquery.js",
			"avalon": "${ctxPortal}modules/avalon/1.3.7/avalon.js",
			"paginator": "${ctxPortal}modules/paginator/0.5/bootstrap-paginator.js",
			"common/css": "${ctxPortal}assets/css/common.css",
			"footer/css": "${ctxPortal}assets/css/footer.css"
		}
	});
	</script>
	<style>
	.hot-search{
		width: auto!important;
	}
	.place .form-control-static {
		width: 420px!important;
		text-overflow: inherit!important;
	}
	</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"/>
<jsp:include page="${root}/portal/headerFooterController/header"/>
	<div class="container">
		<!-- banner -->
		<div class="static-banner">
			<img src="${ctxPortal}static/images/banner_dxzlist.png">
		</div>
		<div class="bd">
			<form id="frm" action="${ctx }culture/list" class="form-inline"  method="post">
			<%@include file="info-search.jsp" %>
			<div class="clearfix">
				<div class="bd-left">
					<div class="bd-heading">
						<h2 class="i${type}">${typeName}</h2>
						<div class="bd-sort">
							<a id="100" href="javascript:void(0);"  onclick="debugger;order(100)" >最新发布</a>
							<a id="204" href="javascript:void(0);"  onclick="order(204)">最多收藏</a>
							<a id="202" href="javascript:void(0);"  onclick="order(202)">最多评论</a>
						</div>
					</div>
						<input type="hidden" name="type" value="${type}"/>
						<input id="oder" type="hidden" name="orderType" value="${orderType}"/>
						<input id="cpage" type="hidden" name="currentPage" value="1" />
					<div class="bd-list">
						<c:if test="${empty pager.dataList}"><div class="nodata"></div></c:if>
						<c:forEach var="el" items="${pager.dataList}">
							<div class="media" >
							<a  target="_blank" class="media-left" href="${ctx}culture/detail/${el.code}.html">
								<img alt="${el.contentTitle}" style="width: 220px;height: 140px" src="${ctx}${el.title}" class="img-rounded"></a>
							<div class="media-body">
								<h4 class="media-heading <c:if test='${el.isOfficial==0}' >best</c:if> " ><i class="best-icon"></i><a target="_blank" href="${ctx }culture/detail/${el.code}.html">${el.contentTitle}</a></h4>
								<a target="_blank" href="${ctx}culture/detail/${el.code}.html"><p class="bd-text js_sub_content">${el.contetNotHtml}</p></a>
								<p class="types"><span title="收藏" class="heart">${el.others.favorite}</span><span title="赞" class="like" style="cursor: default;">${el.others.praise }</span><span title="评论" class="comment">${el.others.comment}</span></p>
							</div>
						</div>
						</c:forEach>
					</div>
					<c:if test="${not empty pager.dataList}">
			<div  data-toggle="paging" class="paging paging-center paging-lg paging-white">
		      </div>
					</c:if>
				</div>
				<div class="bd-right">
					<div class="bdr-item">
						<div class="bd-heading">
							<h2 class="classify">读西藏分类 CLASSIFY</h2>
						</div>
						<div class="cls-wrap">
							<div class="cls-list">
								<a id="1120" href="${ctx}culture/list/1120.html" class="clsi1 left">西藏动态<span class="arrow"></span></a>
								<a id="1070" href="${ctx}culture/list/1070.html" class="clsi2 right">名人<span class="arrow"></span></a>
								<a id="1010" href="${ctx}culture/list/1010.html" class="clsi3 left">节日<span class="arrow"></span></a>
								<a id="1080" href="${ctx}culture/list/1080.html" class="clsi4 right">服饰<span class="arrow"></span></a>
								<a id="1020" href="${ctx}culture/list/1020.html" class="clsi5 left">历史<span class="arrow"></span></a>
								<a id="1090" href="${ctx}culture/list/1090.html" class="clsi6 right">艺术<span class="arrow"></span></a>
								<a id="1030" href="${ctx}culture/list/1030.html" class="clsi7 left">风俗<span class="arrow"></span></a>
								<a id="1100" href="${ctx}culture/list/1100.html" class="clsi8 right">特产<span class="arrow"></span></a>
								<a id="1040" href="${ctx}culture/list/1040.html" class="clsi9 left">宗教<span class="arrow"></span></a>
								<a id="1110" href="${ctx}culture/list/1110.html" class="clsi10 right">地理<span class="arrow"></span></a>
								<a id="1050"href="${ctx}culture/list/1050.html" class="clsi11 left">美食<span class="arrow"></span></a>
								<a href="${ctx}culture/cultural.html" class="clsi12 right">西藏文化传播<span class="arrow"></span></a>
								<a id="1060"href="${ctx}culture/list/1060.html" class="clsi13 left">动植物<span class="arrow"></span></a>
							</div>
						</div>
					</div>
					<div class="bdr-item">
						<div class="bd-heading">
							<h2 class="hot">热点频道 HOT</h2>
						</div>
						<div class="h-list" ms-important="hotView">
							<div class="media" ms-repeat="list">
								<h3 class="media-heading"><a target="_blank" ms-href="${ctx}{{el.url}}">{{el.contentTitle}}</a></h3>
								<div class="media-content" ms-if="$first">
									<a target="_blank" class="media-left"  ms-href="${ctx}{{el.url}}">
										<img height="70" width="110" ms-attr-alt="el.contentTitle" ms-src="{{el.title?'${ctx}'+el.title:'../../static/images/c1.jpg'}}" class=" img-rounded"></a>
									<div class="media-body">
									<a target="_blank"  class="media-left"  ms-href="${ctx}{{el.url}}">	<p class="bd-text js_sub_summary ">{{el.contetNotHtml|html}}</p></a>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="bdr-item">
						<div class="bd-heading">
							<h2 class="others">其他推荐 OTHERS</h2>
						</div>
						<div class="h-list" ms-important="otherView">
							<div class="media" ms-repeat="list">
								<h3 class="media-heading"><a target="_blank" ms-href="${ctx}{{el.url}}">{{el.contentTitle}}</a></h3>
								<div class="media-content" ms-if="$first">
									<a target="_blank" class="media-left"  ms-href="${ctx}{{el.url}}">
										<img ms-attr-alt="el.contentTitle" height="70" width="110" ms-src="{{el.title?'${ctx}'+el.title:'../../static/images/c1.jpg'}}" class=" img-rounded"></a>
									<div class="media-body">
										<a target="_blank" class="media-left"  ms-href="${ctx}{{el.url}}"><p class="bd-text js_sub_summary">{{el.contetNotHtml|html}}</p></a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			</form>
		</div>
	</div>
	<jsp:include page="${root}/portal/headerFooterController/footer"/>
	
	<script type="text/javascript">
		var basePath_ = "${ctx}";
		var basePathPortal = "${ctxPortal}";
		var type_ = "${type}";
		var orderType_ = "${orderType}";
		var currentPage_ = "${pager.currentPage}";
		var totalPage_ = "${pager.totalPage}";
	</script>
	<script type="text/javascript" src="${ctx}portal/assets/js/culture/list.js"></script>
	<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
	
</body>
</html>