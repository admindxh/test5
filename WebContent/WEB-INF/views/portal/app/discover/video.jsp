<%@ page language="java" import="java.util.*" import="com.rimi.ctibet.domain.Content" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="author" content="quansenx">
	<meta name="keywords" content="${content.tdkKeywords}" />
	<meta name="description" content="${content.tdkDescription}" />
	<title>${content.tdkTitle}</title>
	<%@include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctxPortal}modules/scrollbar/jquery.mCustomScrollbar.css">
	<!--[if lt IE 9]>
	<script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
	<script src="${ctxPortal}modules/fix/respond.min.js"></script>
	<![endif]-->
	<script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
	<script src="${ctxPortal}modules/ckplayer/ckplayer.js"></script>
	<script type="text/javascript">
	// Set configuration
	seajs.config({
		alias: {
			"jquery": "jquery/jquery/1.11.1/jquery.js",
			"avalon": "avalon/1.3.7/avalon.js",
			"validform": "Validform/5.3.2/Validform.min.js",
			"paginator": "paginator/0.5/bootstrap-paginator.js",
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
<body ms-controller="view">
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"/>
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
						<a href="video_list">高清视频</a>
					</li>
					<li class="slipt"></li>
					<li class="active">${content.contentTitle}</li>
				</ul>
			</div>
			<div class="dh-area dh-area-l">
				<h1 class="da-title">${content.contentTitle }</h1>
				<p class="da-view"><em>天上西藏官方</em>出品 <span>已有<em>${content.others.view}</em>人播放</span></p>
				<p class="types"><span class="heart" title="收藏数">${content.others.favorite}</span><span class="like" style="cursor: default;" title="赞数">${content.others.praise }</span><span class="comment num" title="评论数">${content.others.comment}</span></p>
				<div class="share bdsharebuttonbox">
				<span>分享到：</span>
					<a href="#" class="weibo" data-cmd="tqq" title="分享到腾讯微博" >分享到腾讯微博</a>
					<a href="#" class="sina" data-cmd="tsina" title="分享到新浪微博" >分享到新浪微博</a>
					<a href="#" class="qzone" data-cmd="qzone" title="分享到QQ空间">分享到QQ空间</a>
				</div>
			</div>
			<div class="video-wrap clearfix">
				<div id="video" class="J_video"></div>
				<div class="video-sidebar">
					<div class="sb-title">相关推荐（{{ list.size()+1 }}）</div>
					<div id="playlist">
						<div class="media active">
							<div class="media-left">
								<a target="_blank" href="${ctx}${content.url}"><img alt="${content.contentTitle}" src="${content.title}" width="150" height="100"></a>
							</div>
							<div class="media-body">
								<p class="video-name">${content.contentTitle}</p>
								<p><span>播放：${ content.others.view }</span><span>收藏：${ content.others.favorite }</span></p>
							</div>
						</div>
						<div class="media" ms-repeat="list" ms-hover="hover"  ms-if-loop="el.code!='${content.code}'">
							<div  class="media-left">
								<a target="_blank" ms-href="${ctx}{{el.url}}"><img ms-attr-alt="el.contentTitle" ms-src="el.title" width="150" height="100"></a>
							</div>
							<div class="media-body">
								<p class="video-name">{{ el.contentTitle }}</p>
								<p><span>播放：{{ el.others.view }}</span><span>收藏：{{ el.others.favorite }}</span></p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="dh-main">
				<div class="content">
					<h2>简介</h2>
					<p>${content.content}</p>
				</div>
				<div class="group-btn">
				<a   style="cursor: pointer;" onclick="favorite('${content.code}',this)" id="fav1" class="fav" title="收藏"><span class="sr-only">收藏</span></a>
				<a  style="cursor: pointer;" class="like" onclick="praise('${content.code}',this)" id="praise" title="赞"><span class="sr-only">赞</span></a>
				</div>
				  <div id="replyText" <c:if test="${isOpen!=1}">style="display:none;"</c:if>  ms-important="commentView">
					<div class="comments">
							<div class="media" ms-repeat="list">
								<div class="media-left" >
								<a target="_blank" style="display: block; width: 68px;" ms-href="${ctx}member/show/{{el.memberCode}}.html">
									<span class="user-mark"></span>
									<img height="68" width="68" ms-attr-src="{{'${ctx}'+el.memberPic}}"></a>
								</div>
								<div class="media-body">
									<h2 class="media-heading">
									<i ms-attr-class="{{ el.memberSex!=0? 'j-male':'j-female'}}"></i>
									<a target="_blank" ms-href="${ctx}member/show/{{el.memberCode}}.html">	{{el.memberName}}</a> <span class="time">{{ el.createTime | date('yyyy/MM/dd HH:mm')}}</span>
									</h2>
									<h3>{{el.content}}</h3>
								</div>
							</div>
						</div>
					<div ms-if="list.size()" id="commentPage">
<!-- 			  	 分页 -->
			      		</div>
			      		<c:if test="${not  empty logUser}">
						<div class="comment"  <c:if test="${isOpen!=1}">style="display:none;"</c:if> >
							<form id="commentfrm" action="${ctx}/portal/app/discover/comment" >
							<input type="hidden" name="contentcode" value="${content.code}">
							<input type="hidden" name="contentType" value="<%=Content.DETAIL_VEDIO_REPLY%>">
							<textarea  name="content" rows="10" class="form-control" placeholder="有什么好的建议来说两句吧~" dataType="*2-200"  nullmsg="请输入评论"></textarea>
							<span class="Validform_checktip"></span>
							<div class="text-right">
								<button  class="btn-comment">发表评论</button>
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
	<jsp:include page="${root}/portal/headerFooterController/footer"/>
	
	<script type="text/javascript">
		var basePath_ = "${ctx}";
		var basePathPortal = "${ctxPortal}";
		
		var contentTitle_ = "${content.contentTitle}";
		var contentType_ = "${content.contentType}";
		var contentCode_ = "${content.code}";
		var description_ = "${content.description}";
	</script>
	<script type="text/javascript" src="${ctx}portal/assets/js/discover/video.js"></script>
	<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
	<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
	
</body>
</html>