<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.rimi.ctibet.domain.LogUser" session="true"%>
<%@ taglib prefix="rimi" uri="/rimi-tags" %>
<%@ taglib prefix="udj" uri="/user-defined-jstl" %>
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
		<meta name="keywords"
			content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
		<meta name="description"
			content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
		<title>天上社区</title>
		<%@include file="/common-html/commonPortal.jsp"%>
		<link rel="stylesheet"
			href="${ctxPortal}assets/css/community/bbs-list.css" />
		<link rel="stylesheet" href="${ctxPortal}/modules/slick/slick.css" />
		<!--[if lt IE 9]>
    <script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}modules/fix/respond.min.js"></script>
    <![endif]-->
	</head>
	<body ms-controller="view">
		<%--<div id="header" ms-include-src="'${ctxPortal}/app/header/index.jsp'"></div>--%>
		<jsp:include page="${root}/portal/headerFooterController/header"></jsp:include>
		<jsp:include page="../login/login_modal.jsp"></jsp:include>
		<div class="main container">
			<div class="banner">
				<c:if test="${not empty plate.imageUrl}">
					<img src="${ctx}${plate.imageUrl}" width=1200 height=200 />
				</c:if>
				<%--判断是否是全站置顶区--%>
				<c:if test="${empty plate.imageUrl}">
					<img src="${ctx}${plate.imageUrl}" width=1200 height=200 />
				</c:if>
				<%--<div class="banner-info">
					<h3>
						${plate.programaSummary }
					</h3>
				</div>
			--%>
			</div>
			<!-- banner End -->
			<div class="location mt60 mb30">
				<span>当前位置:</span>
				<c:if test="${empty plate.programaName}">
					<span><a href="${ctx}/community/frontIndex">天上社区</a>>全站置顶区</span>
				</c:if>
				<c:if test="${not empty plate.programaName}">
					<span><a href="${ctx}/community/frontIndex">天上社区</a>>${plate.programaName}</span>
				</c:if>
			</div>
			<div class="adv clearfix">
				<div>
					<p class="text">
				        ${udj:subString(plate.programaSummary,65)}
					</p>
				</div>
                <c:choose>
                    <c:when test="${not empty ad}">
                        <a href="javascript:;"
                           onclick="adver('${ad.url}','${ad.code }')" target="_blank"><img
                                src="${ctx}/${ad.imgUrl}" />
                        </a>
                    </c:when>
                    <c:otherwise>
                        <img src="${ctxPortal}/static/images/bbs_list-img.jpg" />
                    </c:otherwise>
                </c:choose>
			</div>
			<!-- advertisement End -->
			<div class="sort mt60 mb30 clearfix">
				<div class="issue">
					<c:if test="${empty plateCode}">
						<a data-listRule="createTime" href="javascript:void(0)">最新发布</a>
					</c:if>
					<c:if test="${not empty plateCode}">
						<a data-listRule="createTime"
							href="${ctx}community/list/createTime/1/${plateCode}.html"
							target="_self">最新发布</a>
					</c:if>
				</div>
				<div class="reply">
					<c:if test="${empty plateCode}">
						<a data-listRule="newReply" href="javascript:void(0)">最新回复</a>
					</c:if>
					<c:if test="${not empty plateCode}">
						<a data-listRule="newReply"
							href="${ctx}community/list/newReply/1/${plateCode}.html"
							target="_self">最新回复</a>
					</c:if>
				</div>
				<div class="praise">
					<c:if test="${empty plateCode}">
						<a data-listRule="mostPraise" href="javascript:void(0)">被赞最多</a>
					</c:if>
					<c:if test="${not empty plateCode}">
						<a data-listRule="mostPraise"
							href="${ctx}community/list/mostPraise/1/${plateCode}.html"
							target="_self">被赞最多</a>
					</c:if>
				</div>
				<div class="post-btn">
					<c:if test="${empty plateCode}"></c:if>
					<c:if test="${not empty plateCode}">
						<a href="javascript:void(0);" onclick="savePost()"></a>
					</c:if>
				</div>
			</div>
			<div class="bbs-list">
				<div class="header">
					<ul class="clearfix">
						<li class="theme w-theme">
							主题
						</li>
						<li class="author w-author">
							作者
						</li>
						<li class="examine w-examine">
							查看/回复
						</li>
						<li class="list-reply w-reply">
							最后回复
						</li>
					</ul>
				</div>
				<div class="content">
					<c:forEach items="${pager.dataList}" var="post">
						<ul class="clearfix">
							<li class="w-theme theme-info">
								<span>
									<a target="_blank" href="${ctx}${post.url}">${post.ctitle}</a>
									<c:if test="${post.falsepraise>=20}"><img src="${ctx }portal/assets/icon/_zan.png"></c:if>
								</span>
								<input type="hidden" value="${post.code}" name="code" />
								<a href=""></a>
							</li>
							<li class="w-author author-info clearfix">
								<img
									<c:if test="${empty post.postuserpic}">src="${ctxPortal}/static/images/shouye_39.png"</c:if>
									<c:if test="${not empty post.postuserpic}">src="${ctx}${post.postuserpic}"</c:if>
									alt="用户头像"
									style="width: 38px; height: 38px; border-radius: 50%;" />
								<div>
									<p class="nickname">
										<c:if test="${post.usersex eq '0' }"><img src="${ctx }portal/assets/icon/female_b.png" /></c:if>
										<c:if test="${post.usersex ne '0' }"><img src="${ctx }portal/assets/icon/male_b.png" /></c:if>
										&nbsp;
										<c:if test="${post.userName!='天上社区官方'}">
											<a  target="_blank" href="${ctx}member/show/${post.usercode}.html"><span>${post.userName}</span></a>
										</c:if>
										<c:if test="${post.userName=='天上社区官方'}">
											<span>${post.userName}</span>
										</c:if>
									</p>

									<p class="time-info">
										<fmt:formatDate value="${post.ctime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
									</p>
								</div>
							</li>
							<li class="w-examine examine-info">
								<p>
									<img src="${ctxPortal}/assets/icon/eye.png" title="查看数" />
									<span>${post.falseViewcount}</span>
								</p>

								<p>
									<img src="${ctxPortal}/assets/icon/chat.png" title="回复数" />
									<span class="reply-num">${post.falsereplynum==0?post.falsereplynum:post.falsereplynum-1}</span>
								</p>
							</li>
							<li class="w-reply author-info clearfix">
								<c:if test="${not empty post.replyName}">
									<img
										<c:if test="${empty post.replyPic}"> src="${ctxPortal}/static/images/shouye_39.png" </c:if>
										<c:if test="${not empty post.replyPic}"> src="${ctx}${post.replyPic}" </c:if>
										alt="用户头像"
										style="width: 38px; height: 38px; border-radius: 50%;" />

									<div>
										<div>
											<p class="nickname">
												<span>
												<c:if test="${post.replysex eq '0' }"><img src="${ctx }portal/assets/icon/female_b.png" /></c:if>
												<c:if test="${post.replysex ne '0' }"><img src="${ctx }portal/assets/icon/male_b.png" /></c:if>
												&nbsp;${post.replyName}
												</span>
											</p>
											<p class="time-info">
												${post.replyTime}
											</p>
										</div>
									</div>
								</c:if>
								<c:if test="${empty post.replyName}">
									<p class="nickname">
										<span style="margin-left: 58px;">暂无回复</span>
									</p>
								</c:if>
							</li>
						</ul>
					</c:forEach>
				</div>
			</div>
			<!-- 分页 -->
			<div id="page"></div>
		</div>
		<jsp:include page="../footer/index.jsp" />
		<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
		<script>

		
    seajs.use(['jquery', 'paginator/0.5/bootstrap-paginator.js'], function ($) {
        var options = {
            currentPage: ${pager.currentPage},
            totalPages:${pager.totalPage},
            pageUrl: function (type, page, current) {
                var url  =  "${ctx}community/list/${orderBy}/"+page+"/${plateCode}.html";
                //return '${ctx}community/list?plateCode=${plateCode}&currentPage=' + page+"&orderBy=${orderBy}"
                return  url;
            }
        }
        $('#page').bootstrapPaginator(options)

        //点击排序变色
        $(function(){
             var listNode = $(".sort>div").not(".post-btn"),
           			aNode = listNode.children("a[data-listrule]");
             for(var i = 0; i< listNode.length; i++){
    			if(aNode.eq(i).attr("data-listRule") == "${orderBy}"){
    				listNode.eq(i).addClass("down");
    			}
             }
         })
            
    })
</script>
<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
		<script>
    //判断用户是否登录
    function savePost() {
        if (${lgUser==null}) {
            //console.log("dsafdsfsdfddsdssdfd");
            $('[data-toggle="login"]').click();
            return;
        } else {
            location.href = '<%=basePath%>community/gotoSavePost?plateCode=${plateCode1}';
        }
    }
    
    function adver(href,code){
	    frontContentStatic('3452871b-74d2-33b2-b6de-34d202605b2d','content',code,'click');
	    window.open(href);
    }
    
</script>
	</body>
</html>