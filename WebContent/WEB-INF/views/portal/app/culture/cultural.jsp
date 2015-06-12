<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="author" content="quansenx">
	<meta name="keywords" content=" 西藏文化传播" />
	<meta name="description" content="读西藏文化传播。了解西藏文化传播，尽在天上西藏官网。" />
	<title>读西藏文化传播_天上西藏</title>
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
		// Set configuration
		seajs.config({
			alias: {
				"jquery": "jquery/jquery/1.11.1/jquery.js",
				"avalon": "avalon/1.3.7/avalon.js",
				"jplayer": "jplayer/2.9.1/jquery.jplayer.js",
				"paginator": "paginator/0.5/bootstrap-paginator.js",
				"player": "${ctxPortal}assets/js/music/player.js",
				"common/css": "${ctxPortal}assets/css/common.css",
				"footer/css": "${ctxPortal}assets/css/footer.css"
			}
		});
	</script>
</head>
<body >
<!-- 	<div id="header" ms-include-src="'../header/index'"></div> -->
	<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"/>
<jsp:include page="${root}/portal/headerFooterController/header?seo_index=seo_index"/>
	<div class="container">
		<!-- banner -->
		<div class="static-banner">
			<img src="${ctxPortal}static/images/banner_dxzlist.png"></div>
		<div class="bd">
		<%@include file="culture-search.jsp" %>
			<div class="clearfix">
				<div class="bd-left">
					<div id ="nodata" class="nodata" style="display: none;"></div>
					<div class="bdl-item" ms-important="musicView">
						<div ms-if="list.size()"> 
						<div class="bd-heading">
							<h2 class="music">音乐 MUSIC</h2>
							<div class="bd-sort">
							<a  href="javascript:void(0);" class="active down"  ms-click="order(1,100)" >最新发布</a>
							<a  href="javascript:void(0);"   ms-click="order(1,204)">最多收藏</a>
							<a  href="javascript:void(0);"  ms-click="order(1,202)">最多评论</a>
							</div>
						</div>
						<div class="bd-list">
							<div class="bd-pane music-pane clearfix" ms-repeat="list" data-repeat-rendered="rendered">
								<div class="bd-img music-photo">
									<a ms-href="${ctx }culture/culture_detail/{{el.code}}.html"><div class="music-mark"></div></a>
									<img style="height: 110px;width: 110px" ms-src="${ctx}{{el.title}}" ms-attr-alt="el.contentTitle"></div>
								<div class="pane-desc">
									<h2 ms-class="best:!el.isOfficial"><i class="best-icon"></i><a ms-href="${ctx }culture/culture_detail/{{el.code}}.html">{{ el.contentTitle }}</a></h2>
<!-- 									<p class="album">《听我的声音》</p> -->
									<h3>{{ el.authorCode }}</h3>
									<p class="m-score" ms-attr-data-score="el.others.score" data-readonly="true"></p>
								</div>
								<div class="music-control" ms-attr-id="J_music_{{$index}}">
									<div class="opt-btns">
										<a href="javascript:void(0);" ms-click="favorite(el.code)" class="o-fav" ms-class="active:!el.sortNum" title="收藏">收藏</a>
										<a href="javascript:void(0);" ms-click="praise(el.code)" class="like" ms-class="active:!el.state" title="赞">赞</a>
										<a href="javascript:void(0);"  ms-click="share(el)" class="o-share active" title="分享">分享</a>
<!-- 										<a href="javascript:void(0);" class="mobile active" title="彩铃">彩铃</a> -->
<!-- 									data-toggle="mobile" -->
									</div>
									<div ms-attr-id="music_player_{{$index}}" ms-attr-data-url="${ctx}{{el.description}}" data-toggle="music"></div>
									<div class="m-control J_control">
										<a href="javascript:void(0);" class="play">play</a>
<!-- 										<a href="javascript:void(0);" class="pause">pause</a> -->
										<a href="javascript:void(0);" class="stop">stop</a>
									</div>
									<div class="m-progress">
										<input ms-attr-data-mid="music_player_{{$index}}" class="J_irs" type="hidden">
										<!-- <div class="move-wrap" data-range_min="0" data-range_max="100" data-cur_min="0"  data-cur_max="100">
											<div class="mp-btn-move"></div>
										</div>
										<div class="m-progress-move"></div> -->
									</div>
									<div class="timer">
										<span class="jp-current-time"></span>
										/
										<span class="jp-duration"></span>
									</div>
									<div class="volume"></div>
								</div>
							</div>
						</div>
						<div id="musicPage"><!-- 分页占位 --></div>
					</div>
					</div>
					<!-- 小说 -->
					<div class="bdl-item" ms-important="storyView" >
					<div ms-if="list.size()"> 
						<div class="bd-heading">
							<h2 class="novel">小说 NOVEL</h2>
							<div class="bd-sort">
							<a  href="javascript:void(0);"  class="active down" ms-click="order(2,100)" >最新发布</a>
							<a  href="javascript:void(0);"   ms-click="order(2,204)">最多收藏</a>
							<a  href="javascript:void(0);"  ms-click="order(2,202)">最多评论</a>
							</div>
						</div>
						<div class="bd-list-pane">
							<div class="media"  ms-repeat="list" data-repeat-rendered="rendered">
								<div class="media-content">
									<a target="_blank" class="media-left" ms-href="${ctx}{{el.url}}">
										<img  style="height: 150px;width: 150px"   ms-attr-src="${ctx}{{el.title}}" class="img-responsive"></a>
									<div class="media-body">
										<div class="pull-left">
											<div class="n-text">
												<h2 class="media-heading" ms-class="best:!el.isOfficial">
												<i class="best-icon"></i>
												<a target="_blank" ms-href="${ctx }culture/culture_detail/{{el.code}}.html">{{ el.contentTitle }}</a>
												</h2>
												<p class="text-muted">{{ el.authorCode }}</p>
											</div>
											<p class="m-score" ms-attr-data-score="el.others.score" data-readonly="true"></p>
										</div>
										<a target="_blank" class="media-left" ms-href="${ctx}{{el.url}}"><div class="pull-right">
											{{el.contetNotHtml|html}}
										</div></a>
									</div>
								</div>
							</div>
							<div id="storyPage"><!-- 分页占位 --></div>
						</div>
						</div>
					</div>
					<!-- 游戏 -->
					<div class="bdl-item" ms-important="gameView" >
						<div ms-if="list.size()"> 
						<div class="bd-heading">
							<h2 class="game">游戏 GAME</h2>
							<div class="bd-sort">
								<a  href="javascript:void(0);"  class="active down" ms-click="order(3,100)" >最新发布</a>
							<a  href="javascript:void(0);"   ms-click="order(3,204)">最多收藏</a>
							<a  href="javascript:void(0);"  ms-click="order(3,202)">最多评论</a>
							</div>
						</div>
						<div class="bd-list-pane">
								<div class="media" ms-repeat="list" data-repeat-rendered="rendered">
									<div class="media-content">
										<a target="_blank"  class="media-left" ms-href="${ctx}{{el.url}}">
											<img  style="height: 150px;width: 150px"  ms-attr-alt="el.contentTitle" ms-attr-src="${ctx}{{el.title}}" class="img-responsive"></a>
										<div class="media-body">
											<div class="pull-left">
												<div class="n-text">
													<h2 class="media-heading" ms-class="best:!el.isOfficial">
													<i class="best-icon"></i>
														<a target="_blank" ms-href="${ctx }culture/culture_detail/{{el.code}}.html">{{ el.contentTitle }}</a>
													</h2>
													<p class="text-muted">{{ el.authorCode }}</p>
												</div>
												<p class="m-score" ms-attr-data-score="el.others.score" data-readonly="true"></p>
											</div>
										<a target="_blank" class="media-left" ms-href="${ctx}{{el.url}}"><div class="pull-right">
											{{el.contetNotHtml|html}}
										</div></a>
										</div>
										<a target="_blank" ms-if="el.description" ms-href="el.description" class="download"></a>
									</div>
								</div>
							<!-- 分页 -->
							<div  id="gamePage"><!-- 分页占位 --></div>
						</div>
						</div>
					</div>
					<div class="bdl-item" ms-important="otherlist" >
					<div ms-if="list.size()"> 
						<div class="bd-heading">
							<h2 class="other">其他 OTHER</h2>
							<div class="bd-sort">
								<a  href="javascript:void(0);"  class="active down"  ms-click="order(4,100)" >最新发布</a>
							<a  href="javascript:void(0);"   ms-click="order(4,204)">最多收藏</a>
							<a  href="javascript:void(0);"  ms-click="order(4,202)">最多评论</a>
							</div>
						</div>
						<div class="bd-list-pane">
							<!-- ajax 异步取的数据 -->
								<div class="media" ms-repeat="list" data-repeat-rendered="rendered">
									<div class="media-content">
										<a target="_blank" class="media-left" ms-href="${ctx}{{el.url}}">
											<img  style="height: 150px;width: 150px" ms-attr-alt="el.contentTitle" ms-attr-src="${ctx}{{el.title}}" class="img-responsive"></a>
										<div class="media-body">
											<div class="pull-left">
												<div class="n-text">
													<h2 class="media-heading" ms-class="best:!el.isOfficial">
													<i class="best-icon"></i>
														<a target="_blank" ms-href="${ctx }culture/culture_detail/{{el.code}}.html">{{ el.contentTitle }}</a>
													</h2>
												<p class="text-muted">{{ el.authorCode }}</p>
												</div>
												<p class="m-score" ms-attr-data-score="el.others.score" data-readonly="true"></p>
											</div>
											<a target="_blank" class="media-left" ms-href="${ctx}{{el.url}}"><div class="pull-right">
											{{el.contetNotHtml|html}}
										</div></a>
										</div>
									</div>
								</div>
							<!-- 分页 -->
							<div id="otherPage"><!-- 分页占位 --></div>
						</div>
						</div> 
					</div>
				</div>
				<!--  -->
				<div class="bd-right">
					<div class="bdr-item">
						<div class="bd-heading">
							<h2 class="classify">读西藏分类 CLASSIFY</h2>
						</div>
						<div class="cls-wrap">
							<div class="cls-list">
								<a id="1120" href="list?type=1120" class="clsi1 left">西藏动态<span class="arrow"></span></a>
								<a id="1070" href="list?type=1070" class="clsi2 right">名人<span class="arrow"></span></a>
								<a id="1010" href="list?type=1010" class="clsi3 left">节日<span class="arrow"></span></a>
								<a id="1080" href="list?type=1080" class="clsi4 right">服饰<span class="arrow"></span></a>
								<a id="1020" href="list?type=1020" class="clsi5 left">历史<span class="arrow"></span></a>
								<a id="1090" href="list?type=1090" class="clsi6 right">艺术<span class="arrow"></span></a>
								<a id="1030" href="list?type=1030" class="clsi7 left">风俗<span class="arrow"></span></a>
								<a id="1100" href="list?type=1100" class="clsi8 right">特产<span class="arrow"></span></a>
								<a id="1030" href="list?type=1040" class="clsi9 left">宗教<span class="arrow"></span></a>
								<a id="1110" href="list?type=1110" class="clsi10 right">地理<span class="arrow"></span></a>
								<a id="1050"href="list?type=1050" class="clsi11 left">美食<span class="arrow"></span></a>
								<a href="cultural" class="clsi12 active right">西藏文化传播<span class="arrow"></span></a>
								<a id="1060"href="list?type=1060" class="clsi13 left">动植物<span class="arrow"></span></a>
							</div>
						</div>
					</div>
					<div class="bdr-item">
						<div class="bd-heading">
							<h2 class="hot">热点频道 HOT</h2>
						</div>
						<div class="h-list" ms-important="hotView" >
							<div ms-if="!list.size()" class="nodata"></div>
							<div class="media" ms-repeat="list">
								<h3 class="media-heading" ms-if="!$first" style="width: 230px;">
									<a target="_blank" ms-href="${ctx}{{el.url}}">【{{el.contentType=='2010'?'音乐':el.contentType=='2020'?'小说':el.contentType=='2030'?'游戏':'其他'}}】{{el.contentTitle}}</a>
								</h3>
								<div class="media-content" ms-if="$first">
									<a class="media-left" href="#">
										<img alt="70x70" width="70" height="70" ms-src="${ctx}{{el.title}}" class=" img-rounded"></a>
									<div class="media-body">
										<h3 class="media-heading"  style="width: 165px;">
											<a target="_blank" ms-href="${ctx}{{el.url}}">【{{el.contentType=='2010'?'音乐':el.contentType=='2020'?'小说':el.contentType=='2030'?'游戏':'其他'}}】{{el.contentTitle}}</a>
										</h3>
										<p class="name">{{el.authorCode}}</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="bdr-item"  ms-important="otherView">
						<div class="bd-heading">
							<h2 class="others">其他推荐 OTHERS</h2>
						</div>
						<div class="h-list">
							<div ms-if="!list.size()" class="nodata"></div>
							<div class="media" ms-repeat="list">
								<h3 class="media-heading" ms-if="!$first"  style="width: 230px;">
									<a target="_blank" ms-href="${ctx}{{el.url}}">{{el.contentTitle}}</a>
								</h3>
								<div class="media-content" ms-if="$first">
									<a target="_blank" class="media-left" ms-href="${ctx}{{el.url}}">
										<img alt="70x70" width="70" height="70" ms-src="${ctx}{{el.title}}" class=" img-rounded"></a>
									<div class="media-body">
										<h3 class="media-heading"  style="width: 165px;">
											<a target="_blank" ms-href="${ctx}{{el.url}}">{{el.contentTitle}}</a>
										</h3>
										<a target="_blank" class="media-left"  ms-href="${ctx}{{el.url}}">	<p style='width: 180px;' class="bd-text js_sub_summary" >{{el.contetNotHtml}}</p></a>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="adpositionid">
						<c:forEach items="${adList}" var="ad">
						  <a href="javascript:void(0);" onclick="adver('${ad.url}','${ad.code }')" target="_blank"><img src="${ctx}${ad.imgUrl}" /></a>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="cbt-dialog" id="mobileDialog">
    <div class="cd-header mdia">
      <a href="javascript:void(0);" class="cd-close">x</a>
    </div>
    <div class="dialog-body">
      <div class="clearfix">
        <div class="m-detail">
        	<h5>彩铃详情</h5>
        	<h5>歌曲：献上最真挚的信仰</h5>
        	<h5>歌手：陈铫</h5>
        </div>
        <div class="subscibe pull-left">
        	<h5>手机号：<input type="text" class="form-control"></h5>
        	<div class="send-pane">
        		<button class="btn-send">发送验证码</button>
        	</div>
        </div>
      </div>
    </div>
  </div>
  <!-- 分享 -->
  <div class="sdmark"></div>
  <div class="cbt-dialog" id="shareDialog">
    <div class="cd-header sdia">
      <a href="javascript:;" class="cd-close">x</a>
    </div>
    <div class="dialog-body">
      <div class="clearfix">
        <div class="m-detail" ms-controller="share">
        	<h5>分享详情</h5>
        	<h5>歌曲：{{ info.contentTitle }}</h5>
        	<h5>歌手：{{ info.authorCode }}</h5>
        </div>
        <div class="subscibe pull-left">
        	<div class="share bdsharebuttonbox">
        		<span>分享到：</span>
        		<a href="#" class="weibo" data-cmd="tqq" title="分享到腾讯微博">分享到腾讯微博</a>
        		<a href="#" class="sina" data-cmd="tsina" title="分享到新浪微博">分享到新浪微博</a>
        		<a href="#" class="qzone" data-cmd="qzone" title="分享到QQ空间">分享到QQ空间</a>
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
	</script>
	<script type="text/javascript" src="${ctx}portal/assets/js/culture/cultural.js"></script>
	<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
	<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>

</body>
</html>