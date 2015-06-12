<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.rimi.ctibet.domain.Content"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String port = ":" + request.getServerPort();
	if ("80".equals(""+request.getServerPort())) {
		port = "";

	}
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + port + path + "/";
	request.setAttribute("ctx", basePath);
	request.setAttribute("ctxManage", basePath + "/manage/");
	request.setAttribute("ctxPortal", basePath + "/portal/"); //xiangwq 
	request.setAttribute("ctxMRead", basePath + "manage/html/read/");//csl
	request.setAttribute("ctxApp", basePath + "portal/app/"); //
	request.setAttribute("root", "/");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="author" content="quansenx">
<meta name="keywords" content="${muti.tdkKeywords}" />
<meta name="description" content="${muti.tdkDescription}" />
<title>${muti.tdkTitle}</title>
<link rel="stylesheet"
	href="${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css">
<!--[if lt IE 9]>
	<script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
	<script src="${ctxPortal}modules/fix/respond.min.js"></script>
	<![endif]-->
<script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
<script type="text/javascript">
	seajs.config({
		alias: {
			"jquery": "jquery/jquery/1.11.1/jquery.js",
			"avalon": "avalon/1.3.7/avalon.js",
			"validform": "Validform/5.3.2/Validform.min.js",
			"paginator": "paginator/0.5/bootstrap-paginator.js",
			"slick": "slick/slick.js",
			"common/css": "${ctxPortal}assets/css/common.css",
			"footer/css": "${ctxPortal}assets/css/footer.css"
		}
	});
	</script>
	<style type="text/css">
		.content  a, a:hover {
						color: inherit;
			}
		    .content a{
			     color: #337ab7; 
 				text-decoration: underline; 
		    }
	
	</style>
</head>
<body >
	<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp" />
	<jsp:include page="${root}/portal/headerFooterController/header?seo_index=seo_index"/>
	<div class="container">
		<div class="bd">
			<div class="place">
				<ul>
					<li class="location">当前位置：</li>
					<li>
						<a href="${ctx}discover">看西藏</a>
					</li>
					<li class="slipt"></li>
					<li>
						<a href="${ctx}discover/picturelist">图说西藏</a>
					</li>
					<li class="slipt"></li>
					<li class="active">${muti.name}</li>
				</ul>
			</div>
			<div class="dh-area dh-area-l">
				<h1 class="da-title">${muti.name}</h1>
				<p class="da-view">
					已有<em>${praise.falseViewcount }</em>人阅读
				</p>
				<p class="types">
					<span class="heart" title="收藏数">${praise.falseFavoriteNum }</span><span
						class="like" style="cursor: default;" title="赞数">${praise.falsePraise }</span><span class="comment num" title="评论数">${praise.falseReplyNum}</span>
				</p>
				<div class="share bdsharebuttonbox">
					<span>分享到：</span> <a href="#" class="weibo" data-cmd="tqq"
						title="分享到腾讯微博">分享到腾讯微博</a> <a href="#" class="sina"
						data-cmd="tsina" title="分享到新浪微博">分享到新浪微博</a> <a href="#"
						class="qzone" data-cmd="qzone" title="分享到QQ空间">分享到QQ空间</a>
				</div>
			</div>
			<div class="dh-main dh-main-l">
				<div class="content">
					<h2>简介</h2>
					<p>${muti.summary}</p>
				</div>
				<div class="picture-wrap">
					<div class="picture">
						<a href="javascript:;" id="J_prevArrow" <c:if test="${not empty premuti}"> data-href="${ctx }${premuti.hyperlink }"</c:if> title="上一张"
								class="prev-page"></a>
								<a href="javascript:;" id="J_nextArrow" <c:if test="${not empty nextmuti}">data-href="${ctx }${nextmuti.hyperlink }"</c:if>title="下一张"
								class="next-page"></a>
						<c:if test="${not empty premuti}">
							<a  target="_blank" href="${ctx }${premuti.hyperlink }" class="J_prev"><span
								class="j_mark"></span><span>${premuti.name }</span><img
								src="${ctx}${premuti.coverUrl}" width="150" height="110"></a>
						</c:if>
						<c:if test="${not empty nextmuti}">
							<a target="_blank" href="${ctx }${nextmuti.hyperlink }" class="J_next"><span
								class="j_mark"></span><span>${nextmuti.name }</span><img
								src="${ctx}${nextmuti.coverUrl}" width="150" height="110"></a>
						</c:if>
						<div class="picture-inner">
							<c:forEach var="img" items="${imgList}" varStatus="status">
								<div class="pi-item">
									<div class="pi-item-pic"><img src="${ctx}${img.url}" ></div>
									<div class="pi-mark"></div>
									<div class="pi-content clearfix">
										<div class="pi-page">
											<span>${status.count }</span>/${fn:length(imgList)}
										</div>
										<div class="pi-desc">${img.summary }</div>
										<div class="pi-control group-btn">
											<a style="cursor: pointer;" onclick="favorite('${img.code}','${muti.code }',this)"
												class="fav fav1" title="收藏"><span class="sr-only">收藏</span></a> <a
												href="javascript:void(0);" onclick="praise('${img.code}','${muti.code }',this)"
												class="like like1" title="赞"><span class="sr-only">赞</span></a>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
						<div class="picture-thumb">
							<a href="javascript:;" class="thumb-prev"></a> <a
								href="javascript:;" class="thumb-next"></a>
							<div class="thumbs clearfix">
								<c:forEach items="${imgList }" var="img" varStatus="status">
									<c:if test="${status.count eq 1}">
										<div class="thumb slick-current">
											<div class="slick-mark"></div>
											<img src="${ctx}${img.url}" width="120" height="90">
										</div>
									</c:if>
									<c:if test="${status.count ne 1}">
										<div class="thumb">
											<div class="slick-mark"></div>
											<img src="${ctx}${img.url}" width="120" height="90">
										</div>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<div id="replyText" <c:if test="${isOpen!=1}">style="display:none;"</c:if>  ms-controller="commentView">
					<div class="comments" >
						<div class="media" ms-repeat="list">
							<div class="media-left">
								<a target="_blank" style="display: block; width: 68px;" ms-href="${ctx}member/show/{{el.memberCode}}.html">
									<span class="user-mark"></span> <img height="68" width="68" ms-attr-src="{{'${ctx}'+el.memberPic}}">
								</a>
							</div>
							<div class="media-body">
								<h2 class="media-heading">
								<i ms-attr-class="{{ el.memberSex!=0? 'j-male':'j-female'}}"></i>
									<a  target="_blank" ms-href="${ctx}member/show/{{el.memberCode}}.html">
										{{el.memberName}}</a>
									<span class="time">{{ el.createTime | date('yyyy/MM/dd HH:mm')}}</span>
								</h2>
								<h3 style="max-width: 915px;">{{el.content}}</h3>
							</div>
						</div>
					</div>
					<div ms-if="list.size()"  id="commentPage">
						<!-- 			  	 分页 -->
					</div>
					
					<c:if test="${not  empty logUser}">
					<div class="comment"  <c:if test="${isOpen!=1}">style="display:none;"</c:if>  >
						<form id="commentfrm" action="${ctx}portal/app/discover/commentpic">
							<input type="hidden" name="contentcode" value="${muti.code}">
							<input type="hidden" name="contentType"
								value="<%=Content.DETAIL_PICTURE_REPLY%>">
							<textarea name="content" rows="10" class="form-control"
								placeholder="有什么好的建议来说两句吧~" dataType="*2-200" nullmsg="请输入评论"></textarea>
							<span class="Validform_checktip"></span>
							<div class="text-right">
								<button class="btn-comment">发表评论</button>
							</div>
						</form>
					</div>
						</c:if>
							<c:if test="${empty logUser}">
									<p class="log-ts pull-left">您还没有登录，请先<a href="#loginModal" data-toggle="login">登录</a>或<a href="${ctx }portal/registerController/register" target="_blank">注册</a>，再进行评论！</p>
					       </c:if>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="${root}portal/headerFooterController/footer" flush="ture" />
	
	<script type="text/javascript">
		var basePath_ = "${ctx}";
		var basePathPortal = "${ctxPortal}";
		
		var mutiCode_ = "${muti.code}";
		var mutiName_ = "${muti.name}";
	</script>
	<script type="text/javascript" src="${ctx}portal/assets/js/discover/picture.js"></script>
	
	<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
	<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
</body>
</html>
