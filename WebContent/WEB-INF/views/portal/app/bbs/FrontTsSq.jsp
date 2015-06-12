<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/rimi-tags" prefix="rimi"%>
<%@taglib prefix="udj" uri="/user-defined-jstl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化，西藏徒步，西藏攻略、西藏交流社区、西藏问答社区 " />
	<meta name="description" content=" 天上西藏社区-天上社区，帮助您解决西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化，西藏徒步，西藏攻略上面的问题，hold住最美回忆，西藏旅游攻略尽在天下西藏。" />
	<title>天上社区-天上西藏官网</title>
    <%@include file="/common-html/commonPortal.jsp"%>
    <link rel="stylesheet" href="${ctxPortal}/assets/css/community/community.css" />
    <link rel="stylesheet" href="${ctxPortal}/assets/css/community/left_plate.css" />
    <link rel="stylesheet" href="${ctxPortal}/assets/css/community/lunbo.css" />
    <link rel="stylesheet" href="${ctxPortal}/assets/css/community/right_plate.css" />
    <link rel="stylesheet" href="${ctxPortal}/assets/css/community/overHeader.css" />
    <link rel="stylesheet" href="${ctxPortal}/assets/css/content_top.css" />
    <link rel="stylesheet" href="${ctxPortal}/modules/slick/slick.css" />
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxManage}/resources/plugin/sea.js"></script>
</head>
<body ms-controller="view">
<!-- Header -->
<jsp:include page="${root}/portal/headerFooterController/header"></jsp:include>
<jsp:include page="../login/login_modal.jsp"></jsp:include><!-- Header End -->
<!-- Content -->
<div class="community container">
    <div style="display: none;" class="lunb-main">
        <div class="lunb clearfix">
            <c:forEach items="${banner}" var="b">
                <div class="watch">
                    <a target="_blank" href="${b.content}"><img src="${ctx}${b.url}" style="width: 1000px; height: 365px" />
                    </a>
                    <%-- <div class="img-info" style="display: none;">
                        <h4>【徒步西藏】美丽的多庆错,说走的旅行</h4>
                        <div class="change-img">
                            <img src="${ctx}/${b.url}" />
                            <label>走在旅途上的羽毛</label>
                            <label class="ml40">
                                <img src="${ctxPortal}/assets/icon/eye_white.png" />
                                <span>19874</span>
                                <img src="${ctxPortal}/assets/icon/chat_white.png" class="ml5" />
                                <span>8214497</span>
                            </label>
                        </div>
                    </div> --%>
                </div>
            </c:forEach>
        </div>
    </div>
     <div style="margin-top: 30px;"></div>
    <jsp:include page="${root}portal/app/index/index-search.html"></jsp:include>
   
    <div class="row">
        <div class="col-xs-9">
            <!-- overHead -->
            <div class="overhead">
                <div class="header clearfix xz-heading">
                    <a target="_blank" class="title-more" href="${ctx}community/more/orderBy_createTime/isTop_1.html"><h2><span class="sr-only">全站置顶</span></h2></a>
                    <a target="_blank" class="more" href="${ctx}community/more/orderBy_createTime/isTop_1.html">更多MORE</a>
                </div>
                <div class="content">
                    <c:forEach items="${topPost}" var="post">
                        <div class="media media-mt18">
                            <div class="media-body">
                                <h3 class="media-heading">
                                    <a target="_blank" href="${ctx}${post.url}">${post.contenttitle}</a>
                                </h3>
                                <span class="ml20">
                                    <img src="${ctxPortal}/assets/icon/eye_ac.png" />
                                    <label class="data">${post.falseviewcount}</label>
                                    <img class="ml20" src="${ctxPortal}/assets/icon/chat.png"/>
                                    <label class="data">${post.falsereplynum==0?post.falsereplynum:post.falsereplynum-1}</label>
                                </span>
                                <ul class="user-info">
                                    <li>
                                        <a target="_blank" href="${ctx}member/show/${post.usercode}.html">
                                            <c:if test="${empty post.postuserpic}">
                                               		 <img src="${ctx}/portal/static/default/male.jpg" style="width: 30px; height: 30px; border-radius: 50%;" />
                                            </c:if>
                                            <c:if test="${not  empty post.postuserpic}">
                                            <img src="${ctx}/${post.postuserpic}" style="width: 30px; height: 30px; border-radius: 50%;" />
                                            
                                            </c:if>
                                        </a>
                                        <c:if test="${post.usersex eq '0' }">
                                            <img src="${ctx }portal/assets/icon/female_b.png" />
                                        </c:if>
                                        <c:if test="${post.usersex ne '0' }">
                                            <img src="${ctx }portal/assets/icon/male_b.png" />
                                        </c:if>
                                        <label>作者:</label>
                                        <c:if test="${post.postusername!='天上西藏官方'}">
                                            <a  target="_blank"
                                                    href="${ctx}member/show/${post.usercode}.html">
                                                <span class="us-name" title="${post.postusername}">${post.postusername}</span></a>
                                        </c:if>
                                        <c:if test="${post.postusername=='天上西藏官方'}">
                                            <span class="us-name">${post.postusername}</span>
                                        </c:if>
                                        <label>
                                            <fmt:formatDate value="${post.createtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                        </label>
                                    </li>
                                    <li class="ml30">
                                        <c:if test="${not empty post.replyerName}">
                                            <a  target="_blank"
                                                    href="${ctx}member/show/${post.replyUserCode}.html"><img src="${ctx}/${post.replyerPic}" style="width: 30px; height: 30px; border-radius: 50%;" /></a>
                                            <label>回复:</label>
                                            <span class="us-name"><a target="_blank" href="${ctx}member/show/${post.replyUserCode}.html" title="${post.replyerName}">${post.replyerName}</a></span>
                                            <label>${post.replyCreatetime }</label>
                                        </c:if>
                                        <c:if test="${empty post.replyerName}">
                                            暂无回复
                                        </c:if>
                                    </li>
                                </ul>
                                <label class="str50">${udj:filterHtmlTags(post.content)}</label>
                            </div>
                            <div class="media-right">
                                <div class="zan" data-code="${post.code}"
                                        <rimi:IsPraiseTag code="${post.code}"></rimi:IsPraiseTag>></div>
                                <label class="zan-lab">${post.falsepraise}</label>
                            </div>
                        </div>
                    </c:forEach>
                </div><!-- overHead content End -->
            </div><!-- overHead End -->
            <!-- 自驾骑行 -->
            <div class="driving">
                <h2 class="header"><span class="sr-only">自驾骑行</span></h2>
                <!-- content -->
                <div class="content">
                    <div class="driving-info mt20 clearfix">
                        <a target="_blank" href="${ctx }community/list/createTime/1/notice.html">
                            <img src="${ctxPortal}/assets/icon/riding_notice.png" />
                        </a>
                        <ul>
                            <c:forEach items="${drivAannouncements}" var="post">
                                <li class="clearfix">
                                    <label class="driving-left">
                                        <a target="_blank" href="${ctx}${post.url}">${post.contenttitle}</a>
                                    </label>
                                    <label class="driving-right">
                                        <fmt:formatDate value="${post.createtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                    </label>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="driving-info mt20 clearfix">
                        <a target="_blank"  href="${ctx}community/list/createTime/1/stroty.html">
                            <img src="${ctxPortal}/assets/icon/riding_story.png" />
                        </a>
                        <ul>
                            <c:forEach items="${driveStorys}" var="post">
                                <li class="clearfix">
                                    <label class="driving-left"><a target="_blank" href="${ctx}${post.url}">${post.contenttitle}</a></label>
                                    <label class="driving-right">
                                        <fmt:formatDate value="${post.createtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                    </label>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="driving-info mt20 clearfix">
                        <a target="_blank" href="${ctx}community/list/createTime/1/collect.html">
                            <img src="${ctxPortal}/assets/icon/riding_collect.png" />
                        </a>
                        <ul>
                            <c:forEach items="${driveRecruits}" var="post">
                                <li class="clearfix">
                                    <label class="driving-left">
                                        <a target="_blank" href="${ctx}${post.url}">${post.contenttitle}</a>
                                    </label>
                                    <label class="driving-right">
                                        <fmt:formatDate value="${post.createtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                    </label>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="driving-info mt20 clearfix">
                        <a target="_blank" href="${ctx}community/list/createTime/1/discuss.html">
                            <img src="${ctxPortal}/assets/icon/equip_discuss.png" />
                        </a>
                        <ul>
                            <c:forEach items="${driveEquipments}" var="post">
                                <li class="clearfix">
                                    <label class="driving-left"><a target="_blank" href="${ctx}${post.url}">${post.contenttitle}</a></label>
                                    <label class="driving-right">
                                        <fmt:formatDate value="${post.createtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                    </label>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div><!-- content End -->
            </div><!-- 自驾骑行 End -->
            <c:forEach items="${normalPost}" var="plate">
                <div class="blogs">
                    <div class="header clearfix">
                        <a target="_blank" class="t-mor" href="${ctx}community/list/createTime/1/${plate.plate.code}.html" target="_blank">
                            <div class="blogs-title clearfix">
                                <img src="${ctxPortal}/assets/icon/blogs_bg.png" />
                                <h2>${plate.plate.programaName}</h2>
                            </div>
                        </a>
                        <a target="_blank" class="mor" href="${ctx}community/list/createTime/1/${plate.plate.code}.html" target="_blank">更多MORE</a>
                    </div>
                    <!-- content -->
                    <div class="content">
                        <c:forEach items="${plate.posts}" var="post">
                            <div class="media">
                                <div class="media-body">
                                    <h3 class="media-heading">
                                        <a target="_blank" href="${ctx}${post.url}" target="_blank">${post.contenttitle}</a>
                                    </h3>
                                    <span class="ml20">
                                        <img src="${ctxPortal}/assets/icon/eye_ac.png" />
                                        <label class="data">${post.falseviewcount}</label>
                                        <img class="ml20" src="${ctxPortal}/assets/icon/chat.png" />
                                        <label class="data">${post.falsereplynum==0?post.falsereplynum:post.falsereplynum-1}</label>
                                    </span>
                                    <ul class="user-info">
                                        <li>
                                            <a  target="_blank"
                                                    href="${ctx}member/show/${post.usercode}.html">
                                                    
                                                    <c:if  test="${empty post.pic}">
															<img style="width: 30px; height: 30px; border-radius: 50%;" src="${ctx}portal/static/default/male.jpg"/>
													</c:if>
													<c:if  test="${not  empty post.pic}">
															<img src="${ctx}/${post.postuserpic}"
                                                 style="width: 30px; height: 30px; border-radius: 50%;" />
													</c:if>
                                                    
                                                 
                                                 
                                                 
                                            <c:if test="${post.usersex eq '0' }">
                                                <img src="${ctx }portal/assets/icon/female_b.png" />
                                            </c:if>
                                            <c:if test="${post.usersex ne '0' }">
                                                <img src="${ctx }portal/assets/icon/male_b.png" />
                                            </c:if></a>
                                            <label>作者:</label>
                                            <c:if test="${post.postusername!='天上西藏官方'}">
                                                <a class="author" target="_blank" href="${ctx}member/show/${post.usercode}.html">
                                                    <span class="us-name" title="${post.postusername}">${udj:subString(post.postusername,15)}</span>
                                                </a>
                                                <fmt:formatDate value="${post.createtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                            </c:if>
                                            <c:if test="${post.postusername=='天上西藏官方'}">
                                                <span class="us-name">${post.postusername}</span>
                                                <fmt:formatDate value="${post.createtime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                            </c:if>
                                        </li>
                                        <li class="ml30">
                                            <c:if test="${not empty post.replyerName}">
                                                 <a  target="_blank"
                                                    href="${ctx}member/show/${post.replyUserCode}.html"><img src="${ctx}/${post.replyerPic}" style="width: 30px; height: 30px; border-radius: 50%;" /></a>
                                                <label>回复:</label>
                                                <a target="_blank"
                                                    href="${ctx}member/show/${post.replyUserCode}.html" target="_blank"><span class="us-name" title="${post.replyCreatetime}">${post.replyCreatetime}</span></a>
                                                <label>${post.replyerName}</label>
                                            </c:if>
                                            <c:if test="${empty post.replyerName}">暂无回复</c:if>
                                        </li>
                                    </ul>
                                    <label class="str50">${udj:filterHtmlTags(post.content)}</label>
                                </div>
                                <div class="media-right">
                                    <div>
                                        <div class="zan" data-code="${post.code}"
                                                <rimi:IsPraiseTag code="${post.code}"></rimi:IsPraiseTag>></div>
                                        <label class="zan-lab">${post.falsepraise}</label>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div><!-- content End -->
                </div>
            </c:forEach>
        </div>
        <div class="col-xs-3">
            <!-- reply 最赞回复-->
           
            <!-- 回复&被赞 -->
            <div class="praised">
                <div class="header clearfix">
                    <div class="huif huif-active"></div>
                    <div class="beiz"></div>
                </div>
                <!-- content -->
                <div class="content" id="huif" sytle="display:block">
                    <c:forEach items="${mostRplys}" var="post" varStatus="index">
                        <div class="media">
                            <div class="media-left">
                                <img src="${ctxPortal}/assets/icon/num${index.count}.png" />
                            </div>
                            <div class="media-body">
                                <p>
                                    <a target="_blank" title="${empty post.contenttitle?"无标题":post.contenttitle}"
                                       href="${ctx}${post.url}">${empty post.contenttitle?"无标题":post.contenttitle}</a>
                                </p>
                            </div>
                            <div class="media-right clearfix">
                                <img src="${ctxPortal}/assets/icon/chat.png" />
                                <label>${post.falsereplynum==0?post.falsereplynum:post.falsereplynum-1}</label>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="content" id="beiz" style="display: none">
                    <c:forEach items="${mostPraise}" var="post" varStatus="index">
                        <div class="media">
                            <div class="media-left">
                                <img src="${ctxPortal}/assets/icon/num${index.count}.png" />
                            </div>
                            <div class="media-body">
                                <p>
                                    <a target="_blank" title="${empty post.contenttitle?"无标题":post.contenttitle}"
                                       href="${ctx}${post.url}">${empty post.contenttitle?"无标题":post.contenttitle}</a>
                                </p>
                            </div>
                            <div class="media-right clearfix">
                                <img src="${ctxPortal}/assets/icon/_like.png" />
                                <label>${post.falsepraise }</label>
                            </div>
                        </div>
                    </c:forEach>
                </div><!-- content End -->
            </div><!-- 回复&被赞 End -->
            <!-- 积分榜&排行榜 -->
            <div class="integral">
                <div class="header clearfix">
                    <div class="lunt lunt-active"></div>
                    <div class="post-rank"></div>
                </div>
                <!-- content -->
                <div id="lunt" class="content" style="display: block;">
                    <c:forEach items="${scoreUsers}" var="user" varStatus="index">
                        <div class="media">
                            <div class="media-left">
                                <img src="${ctxPortal}/assets/icon/num${index.count}.png" />
                            </div>
                            <div class="media-body">
                                <div class="media-heading">
                                    <img
	                                    <c:if test="${user.sex == 1}">src="${ctxPortal}/assets/icon/man_sign.png"</c:if>
	                                    <c:if test="${user.sex == 0}">src="${ctxPortal}/assets/icon/woman_sign.png"</c:if>
	                                />
                                    <label>
                                        <c:if test="${user.username!='天上社区官方'}">
                                            <a target="_blank" href="${ctx}member/show/${user.code}.html">
                                                <span>${user.username}</span>
                                            </a>
                                        </c:if>
                                        <c:if test="${user.username=='天上社区官方'}">
                                            <span>${user.username}</span>
                                        </c:if>
                                    </label>
                                </div>
                                <p>
                                    <c:if test="${empty user.score}">0积分</c:if>
                                    <c:if test="${not empty user.score}">${user.score}积分</c:if>
                                </p>
                            </div>
                            <div class="media-right">
                                <img src="${ctx}/${user.pic}" alt="${user.username}"
                                     style="width: 38px; height: 38px; border-radius: 50%;" />
                            </div>
                        </div>
                    </c:forEach>
                </div><!-- content End -->
                <!-- content -->
                <div id="post-rank" class="content" style="display: none;">
                    <c:forEach items="${postUsers}" var="user" varStatus="index">
                        <div class="media">
                            <div class="media-left">
                                <img src="${ctxPortal}/assets/icon/num${index.count}.png" />
                            </div>
                            <div class="media-body">
                                <div class="media-heading">
                                    <img
	                                    <c:if test="${user.sex == 1}">src="${ctxPortal}/assets/icon/man_sign.png"</c:if>
	                                    <c:if test="${user.sex == 0}">src="${ctxPortal}/assets/icon/woman_sign.png"</c:if>
                                    />
                                    <label>
                                        <c:if test="${user.username!='天上社区官方'}">
                                            <a target="_blank" href="${ctx}member/show/${user.code}.html">
                                                <span>
                                                    <c:if test="${fn:length(user.username)>7}">
                                                        ${fn:substring(user.username,0,7)}...
                                                    </c:if>
                                                    <c:if test="${fn:length(user.username)<=7}">
                                                        ${user.username}
                                                    </c:if>
                                                </span>
                                            </a>
                                        </c:if>
                                        <c:if test="${user.username=='天上社区官方'}">
                                            <span>${user.username}</span>
                                        </c:if>
                                    </label>
                                </div>
                                <p>
                                    <c:if test="${empty user.pcount}">发帖数:</c:if>
                                    <c:if test="${not empty user.pcount}">发帖数:${user.pcount}</c:if>
                                </p>
                            </div>
                            <div class="media-right">
                                <img
                                        <c:if test="${empty user.pic}">src="${ctxPortal}/static/images/shouye_39.png"</c:if>
                                        <c:if test="${not empty user.pic}">src="${ctx}${user.pic}"</c:if>
                                        style="width: 38px; height: 38px; border-radius: 50%;" />
                            </div>
                        </div>
                    </c:forEach>
                </div><!-- content End -->
            </div><!-- 积分榜&排行榜 End -->
            <!-- reply 最赞回复-->
            <div class="reply">
                <h2 class="header xz-heading"><span class="sr-only">最赞回复</span></h2>
                <!-- reply content -->
                <div class="content">
                    <c:forEach items="${bestPraise}" var="reply">
                        <c:if test="${not empty reply.content and not empty reply.url and not empty reply.from}">
                            <div class="media">
                                <div class="media-body">
                                    <p class="str50">${udj:filterHtmlTags(reply.content)}</p>
                                    <div class="by">
                                        <label>BY:</label>
                                        <label>${reply.name}</label>
                                    </div>
                                    <div class="from">
                                        <label>FROM:</label>
                                        <span><a target="_blank" href="${reply.url}">${reply.from}</a></span>
                                    </div>
                                </div>
                                <div class="media-right">
                                    <div class="zan" data-code="${reply.code}" im-code="${reply.imCode}"
                                            <rimi:IsPraiseTag code="${reply.code}" falsePraise="${reply.falseprise}"></rimi:IsPraiseTag>></div>
                                    <label class="zan-lab">${reply.falseprise}</label>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div><!-- reply content End -->
            </div><!-- reply End -->
            <c:forEach items="${adList}" var="ad">
                <div class="adv-img">
                    <a href="javascript:;" onclick="adver('${ad.url}','${ad.code }')"
                       target="_blank"><img src="${ctx}/${ad.imgUrl}" /> </a>
                </div>
            </c:forEach>
        </div>
    </div>
</div><!-- Content End -->
<!-- footer -->
<jsp:include page="${root}/portal/headerFooterController/footer"></jsp:include>
<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
<script type="text/javascript" src="${ctx }/common-js/tssq.js"></script>
<script type="text/javascript">
    function adver(href, code) {
        frontContentStatic('3452871b-7422-33b2-b6de-349202605b2a', 'content', code, 'click');
        window.open(href);
    }
</script>
</body>
</html>