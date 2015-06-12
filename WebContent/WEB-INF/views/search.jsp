<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
		String path = request.getContextPath();
	String port = ":" + request.getServerPort();
	if ("80".equals(""+request.getServerPort())) {
		port = "";
	}
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + port + path + "/";
	    request.setAttribute("ctx",basePath);
	    request.setAttribute("ctxManage",basePath+"/manage/");
	    request.setAttribute("ctxPortal",basePath+"/portal/"); //xiangwq 
	    request.setAttribute("ctxMRead", basePath+"manage/html/read/");//csl
	    request.setAttribute("ctxApp",basePath+"portal/app/"); //
	    request.setAttribute("root","/");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="keywords" content="西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化" />
<meta name="description"
	content="天上西藏网为您提供全面靠谱的西藏旅游资讯，旅游攻略，西藏自助游，西藏自驾游，西藏骑行，全面权威的西藏文化介绍。打造权威的西藏文化旅游网。" />
<title>【天上西藏】西藏旅游-感受西藏魅力，体验西藏生活-天上西藏官网</title>
<link rel="stylesheet"
	href="${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css">
<!--[if lt IE 9]>
    <script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}modules/fix/respond.min.js"></script>
  <![endif]-->
<script id="seajsnode"
	src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
<script>
	// Set configuration
	seajs.config({
		alias : {
			"jquery" : "jquery/jquery/1.11.1/jquery.js",
			"avalon" : "modules/avalon/1.3.7/avalon.js",
			"paginator": "paginator/0.5/bootstrap-paginator.js",
			"common/css" : "${ctxPortal}assets/css/common.css",
			"search/css" : "${ctxPortal}assets/css/search.css",
			"footer/css" : "${ctxPortal}assets/css/footer.css"
		}
	});
	seajs.use([ 'common/css', 'search/css', 'footer/css' ]);
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"/>
<jsp:include page="${root}/portal/headerFooterController/header"/>
	<div class="container">
		<div class="search-pane search">
			<form id="searchForm" action="search"  class="form-inline">
				<div class="form-group">
					<div class="input-group">
						<input id="cpage" type="hidden" name="currentPage" value="1">
						<input type="text" class="form-control" name="keywords" value="<c:out value="${keywords}"/>"
							placeholder="请输入关键词搜索">
						<div class="input-group-addon btn-addon">
							<button>
								<span class="sr-only">go</span>
							</button>
						</div>
					</div>
				</div>
			</form>
			<!-- 无结果显示 -->
			<c:if test="${ empty pager.dataList}">
				<div class="error-tip">
					<c:if test="${not empty keywords}">
						<p class="info">找不到和您的查询“<c:out value="${keywords}"/>”相关的内容或信息</p>
					</c:if>
					<p>建议：</p>
					<ul>
						<li >请检查输入字词有无错误。</li>
						<li>请尝试其他的查询词。</li>
						<li>请改用较常见的字词。</li>
					</ul>
				</div>
			</c:if>
		</div>
		<!-- 搜索结果 -->
		<c:if test="${not empty pager.dataList}">
			<div class="fe-list">
				<c:forEach items="${pager.dataList}" var="el">
				<div class="media">
				 <c:if test="${el.type ne 3000}">
					<a class="media-left" href="${ctx}${el.url}"target="new" > <img alt="120x120" width="120"
						height="120" src="${el.imageUrl}" ></a>
				 </c:if>
				 <c:if test="${el.type eq 3000}">
					<a class="media-left" href="${ctx}${el.url}"target="new" > <img alt="120x120" width="120"
						height="120" src="${el.imageUrl}" ></a>
				 </c:if>
					<div class="media-body">
						<h2 class="media-heading">
							<a href="${ctx}${el.url}" target="new">${el.title }</a>
						</h2>
						<p>${el.content}</p>
					</div>
				</div>
				</c:forEach>
			</div>
			<!-- 分页 -->
		<div id="paging" >
		</div>
		</c:if>
	</div>
	<script type="text/javascript">
		seajs.use(['jquery','paginator'],function($,paginator){
			function createPage(currentPage, totalPage){
				var options = {
			        currentPage:currentPage || 1,
			        totalPages: totalPage || 1,
			        onPageClicked: function(e,originalEvent,type,page){
		        		$("#cpage").val(page);
		            	$("#searchForm").submit();
		        	}
		      	};
		      	$('#paging').bootstrapPaginator(options);
			}
			$(function(){
				createPage('${pager.currentPage}', '${pager.totalPage}');
			});
		});
		
	</script>
</body>
</html>