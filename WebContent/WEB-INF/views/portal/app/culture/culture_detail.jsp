<%@ page language="java" import="java.util.*" import="com.rimi.ctibet.domain.Content" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="author" content="quansenx">
	<meta name="keywords" content="${content.tdkTitle}" />
	<meta name="description" content="${content.tdkDescription}" />
	<title>${content.tdkTitle}</title>
	<%@include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctxPortal}modules/ion.rangeSlider/css/ion.rangeSlider.css">
	<link rel="stylesheet" href="${ctxPortal}modules/ion.rangeSlider/css/ion.rangeSlider.skinNice.css">
	<!--[if lt IE 9]>
	<script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
	<script src="${ctxPortal}modules/fix/respond.min.js"></script>
	<![endif]-->
	<script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
	<script>
	seajs.config({
		alias: {
			"jquery": "${ctxPortal}modules/jquery/jquery/1.11.1/jquery.js",
			"avalon": "${ctxPortal}modules/avalon/1.3.7/avalon.js",
			"Validform": "${ctxPortal}modules/Validform/5.3.2/Validform.min.js",
			"jplayer": "${ctxPortal}modules/jplayer/2.9.1/jquery.jplayer.js",
			"player": "${ctxPortal}assets/js/music/player.js",
			"paginator": "${ctxPortal}modules/paginator/0.5/bootstrap-paginator.js",
			"common/css": "${ctxPortal}assets/css/common.css",
			"footer/css": "${ctxPortal}assets/css/footer.css"
		}
	});
	</script>
	<style>
		p{margin: 0;}
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
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"/>
<jsp:include page="${root}/portal/headerFooterController/header?seo_index=seo_index"/>
	<div class="container">
		<div class="bd">
			<div class="clearfix">
				<div class="bd-left music-wrap">
					<div class="place">
						<ul>
							<li class="location">当前位置：</li>
							<li>
								<a href="${ctx}culture">读西藏</a>
							</li>
							<li class="slipt"></li>
							<li>
								<a href="${ctx }culture/cultural">西藏文化传播</a>
							</li>
							<li class="slipt"></li>
							<li>
								<a >${content.typeName}</a>
							</li>
							<li class="slipt"></li>
							<li class="active">${content.contentTitle}</li>
						</ul>
					</div>
				<c:if test="${'2010' eq content.contentType }">
					<div class="bd-pane music-pane clearfix">
						<div class="bd-img music-photo">
							<div class="music-mark"></div>
							<img src="${ctx}${content.title}" alt="${content.contentTitle}"></div>
						<div class="pane-desc">
							<h1 class="da-title">${content.contentTitle}</h1>
<!-- 							<p class="album">《听我的声音》</p> -->
							<h2>${content.authorCode}</h2>
							<p class="m-score" data-score="${content.others.score}" data-readonly="true"></p>
						</div>
						
						<div class="music-control" id="J_music_0">
							<div class="mc-view">
								<span class="da-view">已有<em>${content.others.view }</em>人阅读</span>
								<p class="types"><span class="heart">${content.others.favorite }</span><span class="like">${content.others.praise}</span><span class="comment num">${content.others.comment}</span></p>
								<div class="opt-btns">
									<a href="#" class="mobile">彩铃</a>
								</div>
							</div>
							<div class="share bdsharebuttonbox">
								<span>分享到：</span>
								<a href="#" class="weibo" data-cmd="tqq" title="分享到腾讯微博" >分享到腾讯微博</a>
								<a href="#" class="sina" data-cmd="tsina" title="分享到新浪微博" >分享到新浪微博</a>
								<a href="#" class="qzone" data-cmd="qzone" title="分享到QQ空间">分享到QQ空间</a>
							</div>
							<div id="music_player_0" data-url="${ctx}${content.description}" data-toggle="music"></div>
							<div class="m-control J_control">
								<a href="javascript:;" class="play">play</a>
<!-- 								<a href="javascript:;" class="pause">pause</a> -->
								<a href="javascript:;" class="stop">stop</a>
							</div>
							<div class="m-progress">
							<input data-mid="music_player_0" class="J_irs" type="hidden">
								<!-- <div class="move-wrap">
									<div class="mp-btn-move"></div>
								</div>
								<div class="m-progress-move"></div> -->
							</div>
							<div class="timer">
								<span class="jp-current-time">00:00</span>
								/
								<span class="jp-duration">00:00</span>
							</div>
							<div class="volume"></div>
						</div>
					</div>
					</c:if>
					<c:if test="${'2010' ne content.contentType }">
					<div class="bd-pane music-pane clearfix" style="position: relative;">
						<div class="bd-img music-photo">
							<img src="${ctx}${content.title}" alt="${content.contentTitle}"></div>
						<div class="pane-desc">
							<h1 class="da-title">${content.contentTitle}</h1>
							<!-- 							<p class="album">《听我的声音》</p> -->
							<h2>${content.authorCode}</h2>
							<p style="left: -8px;" class="m-score" data-score="${content.others.score}" data-readonly="true"></p>
							<div class="mc-view">
								<span class="da-view">已有<em>${content.others.view }</em>人阅读</span>
								<p class="types"><span class="heart">${content.others.favorite }</span><span class="like">${content.others.praise}</span><span class="comment">${content.others.comment}</span></p>
							</div>
						</div>
						<div class="share bdsharebuttonbox" style="right: 30px; top: 10px;">
							<span>分享到：</span>
							<a href="#" class="weibo" data-cmd="tqq" title="分享到腾讯微博" >分享到腾讯微博</a>
							<a href="#" class="sina" data-cmd="tsina" title="分享到新浪微博" >分享到新浪微博</a>
							<a href="#" class="qzone" data-cmd="qzone" title="分享到QQ空间">分享到QQ空间</a>
						</div>
						<c:if test="${2030 eq content.contentType }">
							<c:if test="${not empty content.description }">
							<a href="${ content.description}"  class="download"></a></c:if>
							</c:if>
					</div>
					</c:if>
					<div class="dh-main">
						<div class="content">
							<h2>介绍</h2>
							${content.content}
						</div>
						<div class="group-btn">
									<a id="fav1"  style="cursor: pointer;" onclick="favorite('${content.code}',this)" class="fav" title="收藏"><span class="sr-only">收藏</span></a>
							<a id="praise"  style="cursor: pointer;" class="like" onclick="praise('${content.code}',this)" title="赞"><span class="sr-only">赞</span></a>
							<div class="d-score">评分：
							</div>
						</div>
<!-- 						评论 -->
						<div id="replyText" ms-controller="view">
						<div class="comments"  >
						
							<div class="media" ms-repeat="list">
								<div class="media-left">
									<a target="_blank"  style="display: block; width: 68px;"  ms-href="${ctx}member/show/{{el.memberCode}}.html">
										<span class="user-mark"></span>
									 <img alt="68x68" height="68" width="68" ms-attr-src="{{'${ctx}'+el.memberPic}}"></a>
								</div>
								<div class="media-body">
									<h2 class="media-heading">
									<i ms-attr-class="{{ el.memberSex!=0? 'j-male':'j-female'}}"></i><a  target="_blank" ms-href="${ctx}member/show/{{el.memberCode}}.html">	{{el.memberName}}</a> <span class="time">{{ el.createTime | date('yyyy/MM/dd HH:mm')}}</span>
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
			      		<div class="comment"  <c:if test="${isOpen!=1}">style="display:none;"</c:if> >
							<form id="commentfrm" action="${ctx}portal/app/culture/comment" >
							<input type="hidden" name="contentcode" value="${content.code}">
							<input type="hidden" name="contentType" value="read_tibet_culture_reply">
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
<!-- 						评论结束 -->
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
								<h3 ms-if="el.code!='${content.code}'" class="media-heading"><a target="_blank" ms-href="${ctx}{{el.url}}">{{el.contentTitle}}</a></h3>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="${root}/portal/headerFooterController/footer"/>
	
	<script type="text/javascript">
		var basePath_ = "${ctx}";
		var basePathPortal = "${ctxPortal}";
		
		var contentCode_ = "${content.code}";
		var contentTitle_ = "${content.contentTitle}";
		var contentType_ = "${content.contentType}";
	</script>
	<script type="text/javascript" src="${ctx }/portal/assets/js/culture/culture_detail.js"></script>
	
	<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
	<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
</body>
</html>