<%@ page language="java" import="java.util.*"
	import="com.rimi.ctibet.domain.Content" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="author" content="quansenx">
<meta name="keywords" content="${content.tdkKeywords}" />
<meta name="description" content="${content.tdkDescription}" />
<title>${content.tdkTitle }</title>
<%@include file="/common-html/common.jsp"%>
<link rel="stylesheet"
	href="${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css">
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
			"Validform": "${ctxPortal}modules/Validform/5.3.2/Validform.min.js",
			"common/css": "${ctxPortal}assets/css/common.css",
			"paginator": "${ctxPortal}modules/paginator/0.5/bootstrap-paginator.js",
			"footer/css": "${ctxPortal}assets/css/footer.css"
		}
	});
	</script>
	<style>
		.content p{margin: 0;}
		.content  a, a:hover {
						color: inherit;
			}
		    .content a{
			     color: #337ab7; 
 				text-decoration: underline; 
				}
	</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp" />
	<jsp:include page="${root}/portal/headerFooterController/header?seo_index=seo_index"/>
	<div class="container">
		<div class="bd">
			<div class="clearfix">
				<div class="bd-left">
					<div class="place">
						<ul>
							<li class="location">当前位置：</li>
							<li><a href="${ctx}culture">读西藏</a></li>
							<li class="slipt"></li>
							<li><a href="${ctx }culture/list/${content.contentType}.html">${content.typeName}</a>
							</li>
							<li class="slipt"></li>
							<li class="active">${content.contentTitle}</li>
						</ul>
					</div>
					<div class="dh-area">
						<h1 class="da-title">${content.contentTitle}</h1>
						<p class="da-view">
							<em>天上西藏官方</em>出品 <span>已有<em>${content.others.view}</em>人阅读
							</span>
						</p>
						<p class="types">
							<span class="heart" title="收藏数">${content.others.favorite }</span><span
								class="like" style="cursor: default;" title="赞数">${content.others.praise }</span><span
								class="comment num" title="评论数">${content.others.comment}</span>
						</p>
						<div class="share bdsharebuttonbox">
							<span>分享到：</span> <a href="#" class="weibo" data-cmd="tqq"
								title="分享到腾讯微博">分享到腾讯微博</a> <a href="#" class="sina"
								data-cmd="tsina" title="分享到新浪微博">分享到新浪微博</a> <a href="#"
								class="qzone" data-cmd="qzone" title="分享到QQ空间">分享到QQ空间</a>
						</div>
					</div>
					<div class="dh-main">
						<div class="content">${content.content}</div>
						<div class="group-btn">
							<a style="cursor: pointer;"  id="fav1"
								onclick="favorite('${content.code}',this)" class="fav" title="收藏"><span
								class="sr-only">收藏</span></a>
							<a id="praise"  style="cursor: pointer;"
								class="like"  onclick="praise('${content.code}', this)" title="赞"><span
								class="sr-only">赞</span></a>
						</div>
						<div id="replyText"  ms-controller="view">
							<div class="comments">
								<div class="media" ms-repeat="list">
									<div class="media-left">
										<a style="display: block; width: 68px;" ms-href="${ctx}member/show/{{el.memberCode}}.html">
											<span class="user-mark"></span> <img alt="68x68" height="68" width="68" ms-attr-src="{{'${ctx}'+el.memberPic}}">
										</a>
									</div>
									<div class="media-body">
										<h2 class="media-heading">
										<i ms-attr-class="{{ el.memberSex!=0? 'j-male':'j-female'}}"></i>
											<a ms-href="${ctx}member/show/{{el.memberCode}}.html">
												{{el.memberName}}</a>
											<span class="time">{{ el.createTime | date('yyyy/MM/dd HH:mm')}}</span>
										</h2>
										<h3>{{el.content}}</h3>
									</div>
								</div>
							</div>
							<div id="hm_t_62004"></div>
							<div  style="height: 10px;"></div>
							<div ms-if="list.size()" id="commentPage">
								<!-- 			  	 分页 -->
							</div>
							
							
							<c:if test="${not  empty logUser}">
							<div class="comment" <c:if test="${isOpen!=1}">style="display:none;"</c:if> >
								<form id="commentfrm" action="${ctx}portal/app/culture/comment">
									<input type="hidden" name="contentcode" value="${content.code}">
									<input type="hidden" name="contentType"
										value="read_tibet_reply">
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
				<div class="bd-right">
					<div class="bdr-item">
						<div class="bd-heading">
							<h2 class="recom">相关推荐 RECOMMEND</h2>
						</div>
						<div class="h-list" ms-controller="otherView">
						<div ms-if="!list.size()"><div class="nodata"></div></div>
						<c:if test="${not empty content.code}">
							<div ms-if="list.size()==1"><div class="nodata"></div></div>
						</c:if>
							<div class="media" ms-repeat="list">
								<h3  ms-if="el.code!='${content.code}'"  class="media-heading">
									<a ms-href="${ctx}{{el.url}}">{{el.contentTitle}}</a>
								</h3>
							</div>
							<!-- 百度推荐 -->
							<div id="hm_t_57469"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="${root}/portal/headerFooterController/footer" />
	
	<script type="text/javascript">
		var basePath_ = "${ctx}";
		var basePathPortal = "${ctxPortal}";
		
		var contentCode_ = "${content.code}";
		var contentTitle_ = "${content.contentTitle}";
		var contentType_ = "${content.contentType}";
	</script>
	<script type="text/javascript" src="${ctx }/portal/assets/js/culture/detail.js"></script>
	<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
	<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
	<script type="text/javascript">
	window.onload =function(){
		frontContentStatic('${content.contentType}','content','${content.code}','click');
	}
	</script>
</body>
</html>