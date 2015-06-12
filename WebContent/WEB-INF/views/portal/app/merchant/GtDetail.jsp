<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.rimi.ctibet.domain.LogUser" session="true" %>
<%@page import="com.rimi.ctibet.domain.Content"%>
<%@taglib uri="/rimi-tags"  prefix="rimi"%>
<%@taglib prefix="udj" uri="/user-defined-jstl" %>
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
		<meta name="keywords" content="${gtd.tdkKeywords}" />
		<meta name="description" content="${gtd.tdkDescription}" />
		<title>${gtd.tdkTitle}</title>
		<%@include file="/common-html/frontcommon.jsp"%>
		<link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
		<style>
			.content{
				word-wrap: break-word;
			}
			.content p{
				margin: 0;
			}
			.content  a, a:hover {
						color: inherit;
			}
		    .content a{
			     color: #337ab7; 
 				text-decoration: underline; 
				}
		</style>
		<!--[if lt IE 9]>
	<script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
	<script src="${ctxPortal}/modules/fix/respond.min.js"></script>
	<![endif]-->
		<script id="seajsnode"
			src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
		<script>
			seajs.config({
				alias: {
					"jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js",
					"avalon": "${ctxPortal}/modules/avalon/1.3.7/avalon.js",
					"common/css": "${ctxPortal}/assets/css/common.css",
					"merchant/css": "${ctxPortal}/assets/css/merchant.css",
					"footer/css": "${ctxPortal}/assets/css/footer.css",
					"paginator":"${ctxPortal}/modules/paginator/0.5/bootstrap-paginator.js",
					"Validform": "${ctxPortal}/modules/Validform/5.3.2/Validform.min.js",
				}
			});
		</script>
	</head>
	<body>
		<jsp:include page="${root}/portal/headerFooterController/header?seo_index=seo_index"></jsp:include>
		<jsp:include page="../login/login_modal.jsp"></jsp:include>
		<div class="container">
			<div class="bd">
				<div class="xzbox clearfix">
					<div class="bd-left">
					<div class="location" style="padding-bottom: 20px;">
						            <img src="${ctxPortal}/assets/icon/location_red.png"/>
						            <span>当前位置:</span>
						            <a href="${ctx}tourism/strage/frontIndex" target="_self">游西藏</a>
						            <img src="${ctxPortal}/assets/icon/arrow_right.png"/>
						            <a href="${ctx}tourism/merchant/index" target="_self">商户</a>
						            <img src="${ctxPortal}/assets/icon/arrow_right.png"/>
						            <span style="color:red"> ${gtd.name}</span>
			</div>
						<strong class="xz-heading hd-title" style="background: white;font-weight: normal;">
							<p>
								${gtd.name}
								<i class="ctibet"></i>
							</p>
						</strong>
						<div class="shows-wrap">
						<div class="types">
								<span class="geye" title="查看数">${gtd.viewNum}</span>
								<span class="heart" id="heart" title="收藏数">${gtd.praiseNum}</span>
								 <div style="display: inline;border: 0px;position: relative;top: 3px;left: 8px;">
										<button class='star_min <rimi:IsCollect code="${gtd.code }" memCode="${logUser.code }" >star_min_active</rimi:IsCollect>' onclick="isRecoredMerchantFavate('${gtd.code}',this,'min')"  ></button>
								</div>
							</div>
							<div class="shows clearfix">
								<div class="show-item big">
									<img src="${ctx}${gtd.img}" width="350" height="265">
								</div>
								<c:forEach items="${imgs}" var="img" varStatus="index">
									<div class="show-item" ${index.index== 0  ? 'class="active"' : ''} >
										<div class="show-mark"></div>
										<img src="${ctx}/${img.url }" width="135" height="125">
									</div>
								</c:forEach>
							</div>
						</div>
						<div class="xzbox">
							<div class="bd-heading">
								<h1 class="intro">
									简介 INTRODUCTION
								</h1>
								<div class="share bdsharebuttonbox">
									<a href="#" class="weibo" data-cmd="tqq" title="分享到腾讯微博">分享到腾讯微博</a>
									<a href="#" class="sina" data-cmd="tsina" title="分享到新浪微博">分享到新浪微博</a>
									<a href="#" class="qzone" data-cmd="qzone" title="分享到QQ空间">分享到QQ空间</a>
								</div>
							</div>
							<div class="content">
									${gtd.detail}
							</div>
							
						</div>
						<div id="hm_t_62004"></div>
						<div class="xz-box dh-main" ms-important="allActivityView">
							<div class="comments" ms-repeat="data" ms-attr-class="one:$first" data-repeat-rendered="rendered">
								<div class="media">
									<a class="media-left" href="#"> <span class="user-mark"></span>
										 <img alt="68x68" ms-attr-src="${ctx}{{el.pic}}" width="68"
                                     height="68"> </a>
									<div class="media-body">
										<h2 class="media-heading">
											 <i ms-class={{el.sex==1?"j-male":"j-female"}}></i>
											{{el.name}}
											<span class="time">{{el.createtime}}</span>
											<p style="display: inline;" class="m-score" ms-data-score="el.title==0?5:el.title" data-readonly="true"></p>
										</h2>
										<h3>
											{{el.content}}
										</h3>
									</div>
								</div>
							</div>
							<div id="allActivityPage" class="paging paging-center paging-lg paging-white">
							</div>
							<div class="comment" id="merchantReply">
								<form action="${ctx}/tourism/merchant/saveReply" id="form1" method="post">
									<input type="hidden" name="gtcode" value="${gtd.code}" />
									<input type="hidden" name="title" id="score" value="5"/>
									 <input type="hidden"  value="${token}" name="token"/>
                           			<div class="d-score">评分：<span></span></div>
									<textarea id="reply_area" onfocus="validLg()" name="content" rows="10"
                                            class="form-control" placeholder="有什么好的建议来说两句吧~"
                                            datatype="*2-200" nullmsg="请输入内容"></textarea>
                                    <span class="Validform_checktip"></span>
									<div class="text-right">
										 <c:if test="${not  empty logUser}">
                                <button style="background-color: #cf1423" class="btn-comment">发表评论</button>
                             </c:if>
                             <c:if test="${  empty logUser}">
                                <button style="background-color: #BAB3B3" class="btn-comment">发表评论</button>
                             </c:if>
									</div>
								</form>
							</div>
							<c:if test="${empty logUser}">
									<p class="log-ts pull-left">您还没有登录，请先<a href="#loginModal" data-toggle="login">登录</a>或<a href="${ctx }portal/registerController/register" target="_blank">注册</a>，再进行评论！</p>
					       </c:if>
							
							
						</div>
					</div>
					<div class="bd-right">
						<div class="ctrip">
							<div class="chb"></div>
							<div class="cbd">
								<p class="price">
									价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：
									<c:if test="${gtd.price=='0'}">
										<span>暂无价格</span>
									</c:if>
									<c:if test="${gtd.price!='0'}">
										<span>&yen;${gtd.price}<em>起</em> </span>
									</c:if>
								</p>
								<c:if test="${not empty gtd.ctripUrl}">
									<a target="_blank" href="${gtd.ctripUrl}" class="btn-yd"><span
										class="sr-only">立即预定</span> </a>
								</c:if>
								<c:if test="${empty gtd.ctripUrl}">
								</c:if>
							</div>
						</div>
						<p style="margin-top: 30px;">来源：
						         <c:if test="${empty gtd.ctripUrl}">天上西藏
								 </c:if>
								 <c:if test="${not empty gtd.ctripUrl}">携程旅行网
								 </c:if></p>
						
						
						<div class="xz-box">
							<div class="xz-heading">
								<h2 class="dest">
									<span class="sr-only">相关目的地</span>
								</h2>
							</div>
							<c:forEach items="${des}" var="destination">
								<div class="dest-content">
                                    <div class="row">
                                        <div class="col-xs-6"><span><a target="_blank" href="${ctxIndex}${destination.linkUrl}" class="label">${destination.destinationName}</a></span></div>
                                    </div>
								</div>
							</c:forEach>
						</div>
						<div class="xz-box">
							<div class="xz-heading">
								<h2 class="dspot">
									<span class="sr-only">相关景点 SPOT</span>
								</h2>
							</div>
							<div class="dspot-content">
								<ul class="list-unstyled clearfix">
									<c:forEach items="${views}" var="view">
										<li>
											<a target="_blank" href="${ctx }${view.linkUrl}"><img src="${view.img}" width="150"
													height="210" alt="${view.viewName}"> </a>
											<div class="dmark"></div>
											<div class="desc">
												<p>
													${view.viewName}
												</p>
											</div>
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="${root}/portal/headerFooterController/footer"></jsp:include>
		
		<script>
		    var basePath_ = "${ctx}";
			var basePathPortal = "${ctxPortal}";
		    
			var merchantCode2_ = "${merchant.code}";
			var merchantCode_ = "${merchantCode}";
			var merchantName_ = "${merchant.merchantName}";
			var merchantType_ = "${merchant.merchantType}";
			
			var gtdCode_ = '${gtd.code}';
			var gtdName_ = "${gtd.name}";
			
			var DETAIL_TOUR_GROUP_REPLY_ = '<%=Content.DETAIL_TOUR_GROUP_REPLY%>';
			
			var isLogin_ = ${lgUser==null};
			var lgUser__ = '${lgUser}';
		</script>
		<script type="text/javascript" src="${ctx}portal/assets/js/tourism/gt_detail.js"></script>
		
		<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
		<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
	</body>
</html>