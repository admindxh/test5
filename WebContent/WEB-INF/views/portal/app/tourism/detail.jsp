<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.rimi.ctibet.domain.Content"%>
<%@taglib uri="/rimi-tags"  prefix="rimi"%>
<%@taglib prefix="fnt"  uri="http://java.sun.com/jstl/fmt_rt"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="author" content="quansenx">
		<meta name="keywords" content="${content.tdkKeywords }" />
		<meta name="description" content="${content.tdkDescription }" />
		<title>${not empty content.tdkTitle?content.tdkTitle: content.contentTitle}</title>
		<%@include file="/common-html/mainfrontcommon.jsp" %>
		<link rel="stylesheet"
			href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
		<!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
		<script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
		<script type="text/javascript" src="${ctx}common-js/maincommon.js"></script>
		<script>
		 seajs.config({
		        alias: {
		            "jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js",
		            "avalon": "${ctxPortal}/modules/avalon/1.3.7/avalon.js",
		            "common/css": "${ctxPortal}/assets/css/common.css",
		            "footer/css": "${ctxPortal}/assets/css/footer.css",
		            "detail/css":"${ctxPortal}/assets/css/tourism/detail.css",
					"paginator":"${ctxPortal }/modules/paginator/0.5/bootstrap-paginator.js",
					"commonjs":"${ctx}/common-js/common.js",
					"Validform": "${ctxPortal}/modules/Validform/5.3.2/Validform.min.js",
		           }
		    });
		    seajs.use(['common/css','footer/css','detail/css']);
		    seajs.use('slick/slick.css');
		window._bd_share_config = {
			"common" : {
				"bdText" : "${frontPageVo.travelTitle}", // 分享的内容
				"bdDesc" : "分享的摘要", // 分享的摘要
				"bdUrl": "${ctx}tourism/strage/detail/${frontPageVo.code }.html", // 分享的Url地址
				"searchPic":'1',
				"bdPic": "" // 分享的图片
			},
			"share" : {
				"bdSize" : 24,
				"bdCustomStyle": '${ctxPortal}/assets/css/common.css'
			}
		};
		with (document)
			0[(getElementsByTagName('head')[0] || body)
					.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
					+ ~(-new Date() / 36e5)];
	</script>
	<script type="text/javascript" src="${ctx}/portal/modules/ueditor/1.4.3/ueditor.parse.js"></script>
		<style>
			.bd-left .dh-main p{
				margin: 0;
			}
			.dh-main a, a:hover {
						color: inherit;
			}
		    .dh-main a{
			     color: #337ab7; 
 				text-decoration: underline; 
				}
			

		</style>
	</head>
	<body>
	
		<jsp:include page="${root}/portal/headerFooterController/header?seo_index=seo_index"/>

		<script
			src="http://qzonestyle.gtimg.cn/qzone/app/qzlike/qzopensl.js#jsdate=20111201"
			charset="utf-8"></script>
		
		<div class="container" style="width: 1200px;">
			<div class="bd">
			<div class="place">
				<ul>
					<li class="location">当前位置：</li>
					<li><a href="${ctx }tourism">游西藏</a></li>
					<li class="slipt"></li>
					<li><a href="${ctx }tourism/strage/intoTraval/${frontPageVo.programaCode}.html">${prName }</a>
					</li>
					<li class="slipt"></li>
						<li><font color="red">${frontPageVo.travelTitle}</font></li>
				</ul>
			</div>	
			<div class="clearfix" style="margin-top: -10px;">
				<div class="bd-left">
					<div class="dh-area">
						<h1 class="da-title">
							${frontPageVo.travelTitle }
						</h1>
						<p class="da-view">
							<c:if test="${frontPageVo.isOfficial==0}">
								<a href="${ctx }member/show/${frontPageVo.memberCode}.html" target="_blank"><img class="head-portrait"
									
									<c:if test="${empty  frontPageVo.pic }">
											src="${ctxPortal}//static/images/shouye_39.png" 
									</c:if>
									<c:if test="${not empty  frontPageVo.pic }">
											src="${ctx}/${frontPageVo.pic }" 
									</c:if>
								 />
								 </a>
							 </c:if>
							 <c:if test="${frontPageVo.isOfficial==1}">
							 <img class="head-portrait"
									
									<c:if test="${empty  frontPageVo.pic }">
											src="${ctxPortal}/static/images/shouye_39.png" 
									</c:if>
									<c:if test="${not empty  frontPageVo.pic }">
											src="${ctx}/${frontPageVo.pic }" 
									</c:if>
								 />
							 
							 </c:if>
							<span>
							<img class="sex" 
								<c:if test="${ frontPageVo.sex==1 ||empty frontPageVo.sex  }">src="${ctxPortal}/assets/icon/male_b.png"</c:if>
								<c:if test="${ frontPageVo.sex==0}">src="${ctxPortal}/assets/icon/female_b.png"</c:if>
								 />
							<c:if test="${frontPageVo.isOfficial==1}">${frontPageVo.userName}</c:if>
							<c:if test="${frontPageVo.isOfficial==0}">
								<a  href="${ctx }member/show/${frontPageVo.memberCode}.html" target="_blank">${frontPageVo.userName}</a></span>
							</c:if>
							<em>
							<fnt:formatDate value="${frontPageVo.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							
							</em>
							
							
						</p>
						<p class="types">
							<span class="eyes" title="查看数">${frontPageVo.viewCount}</span><span
								class="heart" id="favateCount" title="收藏数">${frontPageVo.faveteCount
								}</span><span id="praiseCount" class="like" title="赞数">${frontPageVo.praiseCount}</span><span
								class="comment" title="评论数">${frontPageVo.replyCount }</span>
						</p>
						<div class="share bdsharebuttonbox">
					<span>分享到：</span>
					<a href="#" class="weibo" data-cmd="tqq" title="分享到腾讯微博" >分享到腾讯微博</a>
					<a href="#" class="sina" data-cmd="tsina" title="分享到新浪微博" >分享到新浪微博</a>
					<a href="#" class="qzone" data-cmd="qzone" title="分享到QQ空间">分享到QQ空间</a>
				</div>
					</div>
					<div class="dh-main" ms-important="allActivityView">
						<div class="content" style="word-wrap:break-word ">
							${frontPageVo.travelContent}
						</div>
						<div class="group-btn">
							<a href="javascript:;" 
							<rimi:IsStraCollect code="${code }" memCode="${logUser.code }">class="star_active"</rimi:IsStraCollect>
							
							 class="fav" title="收藏"
								onclick="isRecoredFavate('${code}',this)"><span
								class="sr-only">收藏</span>
							</a>
							<a href="javascript:isRecored('${code}',this);" 
								<rimi:IsPraiseTag code="${code}">class="like  zan_active"</rimi:IsPraiseTag> 
								class="like" title="赞"
							><span class="sr-only">赞</span></a>
						</div>
					   <div id="hm_t_62004"></div>
						<div class="comments">
							<div class="media" ms-repeat="data" ms-attr-class="one:$first">
								<a class="media-left" ms-href="${ctx }member/show/{{el.replyUserCode}}.html" target="_blank"> 
								<span class="user-mark">
									<img alt="68x68" style="width:68px;height:68px;border-radius: 50px;" ms-src="${ctx}/{{el.userImgUrl==''||el.userImgUrl==null?'portal/static/images/shouye_39.png':el.userImgUrl}}">
								
								</span>
									
								</a>
								<div class="media-body">
									<h2 class="media-heading">
										<i ms-attr-class="{{  el.replYUserSex=='0'? 'j-female':'j-male'}}"></i>
										<a ms-href="${ctx }member/show/{{el.replyUserCode}}.html" target="_blank"> {{el.userName}}</a>
										<span class="time">{{el.createTime|date('yyyy-MM-dd HH:mm:ss')}}</span>
									</h2>
									<h3>
										{{el.replyContent}}
									</h3>
								</div>
							</div>
						</div>
						<div id="allActivityPage"
							class="paging paging-center paging-lg paging-white"></div>

			<c:if test="${not  empty logUser}">
						<div class="comment" id="comment">
							<form id="formReply"
								action="${ctx}/tourism/strage/saveReply"
								method="post">
								<input type="hidden" name="programaCode"  value="${frontPageVo.programaCode }" />
								<input type="hidden" name="postCode" value="${code}" />
								<div>
									<textarea id="content" name="content" rows="10" errormsg="请输入2-200位字母、数字、汉字及常用标点符号"  datatype="*2-200" class="form-control"
										placeholder="有什么好的建议来说两句吧~"></textarea>
								</div>
								<div class="text-right">
									<button class="btn-comment" >
										发表评论
									</button>
								</div>
							</form>
						</div>
						</c:if>
						
						<c:if test="${empty logUser}">
									<p class="log-ts pull-left">您还没有登录，请先<a href="#loginModal" data-toggle="login">登录</a>或<a href="${ctx }portal/registerController/register" target="_blank">注册</a>，再进行评论！</p>
					</c:if>

					</div>
				</div>
				<div class="bd-right">
					<div class="bdr-item">
						<div class="bd-heading">
							<h2 class="recom">
								相关推荐 RECOMMEND
							</h2>
						</div>
						<div class="h-list">
							<div class="media" ms-repeat="avalon.range(7)">
								<c:forEach items="${reommendList}" var="rm">

									<h3 class="media-heading" style="line-height:30px;">
										<a target="_blank" href="${ctxIndex }${rm.url}" title="${rm.travelTitle}">${rm.travelTitle}</a>
									</h3>

								</c:forEach>
								<c:if test="${  empty reommendList}">
								    <div class="nodata" style="display: block;"></div>
								
								</c:if>
							</div>
						</div>
					</div>
					<div class="bdr-item2">
						<div class="bd-heading">
							<h2 class="recom">
								相关地点 PLACE
							</h2>
						</div>
						<div class="dest-content">
						<c:if test="${not empty  des}">
							<c:forEach items="${des}" var="d">
							   <c:if test="${not empty  d.desName}">
							   <span>
								<a href="${ctxIndex }${d.desUrl}" target="_blank" class="label">${d.desName }</a>
								</span>
								</c:if>
							</c:forEach>
							</c:if>
						</div>
					</div>
					<div class="bdr-item3">
						<div class="bd-heading">
							<h2 class="recom">
								相关景点 SPOTS
							</h2>
						</div>
						<div class="content switchover">
						  <c:if test="${not empty  views}">
							<c:forEach items="${views}" var="v" varStatus="status">
							 	 <c:if test="${status.index+1<=32}">
										<c:if test="${(status.index+1)%4==1}">
											<div class="bd-img">
										</c:if>
										<div class="img-item">
											<c:if test="${not empty v.viewUrl}">
												<a href="${ctx }${v.viewUrl}" target="_blank"> <img
														src="${ctx}/${v.viewImg}" />
													<div class="img-mc"></div>
													<p>
														${v.viewName }
													</p> </a>
											 </c:if>
										</div>
										<c:if test="${(status.index+1)%4==0}">
											</div>
										</c:if>
									</c:if>
								</c:forEach>
						</c:if>
					</div>
				</div>
			</div>
			</div>
		</div>
		
		</div>
		</div>
		<div id="footer"><jsp:include page="/WEB-INF/views/portal/app/footer/index.jsp"></jsp:include></div>
		
		
		<script type="text/javascript">
			var basePath_ = "${ctx}";
			var basePathPortal = "${ctxPortal}";
			var ctxPageUtil_ = "${ctxPageUtil}";
			
			var contentCode_ = "${content.code}";
			var programaCode_ = "${content.programaCode}";
			var code_ = '${code}';
			
			var DETAIL_STRATEGY_REPLY_ = "<%=Content.DETAIL_STRATEGY_REPLY%>";
		</script>
		<script type="text/javascript" src="${ctx}portal/assets/js/tourism/detail.js"></script>
		
		
		<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
		<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
		<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
	</body>
</html>