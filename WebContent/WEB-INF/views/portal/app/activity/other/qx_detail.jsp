<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common-html/commonTag.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <%-- <meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
    <meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
    <title>天上西藏-感受西藏魅力，体验西藏生活-天上西藏官网</title> --%>
    <title>西藏活动-天上西藏官网</title>
    <meta name="keywords" content="西藏活动，天上西藏活动，西藏摄影比赛，天上西藏官网活动，天上西藏，${activity.keywords }"/>
    <meta name="description" content="西藏活动为您提供最权威、最新的各种西藏相关活动，期待您的关注，了解更多西藏旅游资讯，欢迎访问天上西藏网站！"/>

    <%-- <%@ include file="/common-html/frontcommon.jsp" %> --%>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <style>
        .fancybox-title-inslid-wrap .child {
            line-height: 28px;
            margin-top: 10px;
        }
    </style>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal }/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script src="${ctxPortal }/assets/js/Alert.js"></script>
    <script>
        var _alert = new Alert();
        seajs.config({
            alias: {
                "jquery": "jquery/jquery/1.11.1/jquery.js",
                "avalon": "avalon/1.3.7/avalon.js",
                "fancybox": "fancybox/jquery.fancybox.js",
                "paginator": "paginator/0.5/bootstrap-paginator.js",
                "fancybox/css": "fancybox/jquery.fancybox.css",
                "common/css": "${ctxPortal }/assets/css/common.css",
                "activity/css": "${ctxPortal }/assets/css/activity/activity.css",
                "footer/css": "${ctxPortal }/assets/css/footer.css",
                "detail/js": "${ctxPortal }/assets/js/activity/detail.js",
                "dateUtil": "${ctx }/common-js/DateUtil.js"
            }
        });
        seajs.use(["jquery"], function ($) {
            window.$ = $;
        });
    </script>
    <style>
        input[type='radio']:focus {
            box-shadow: none;
        }
    </style>
</head>
<body>
<header class="navbar navbar-dark" id="top" role="banner">
    <div class="container">
        <div class="navbar-header pull-left">
            <a href="${ctx}home" class="navbar-brand">
                <img src="${ctxPortal }/assets/icon/logo-white.png" alt="logo"></a>
        </div>
        <div class="pull-right login-and-share bdsharebuttonbox" style="position: relative;padding-right:135px;">
            <c:choose>
                <c:when test="${empty lgUser}">
                    <a href="<%=basePath %>portal/registerController/register">注册</a>
                    <a id="login-btn" href="#loginModal" data-toggle="login">登录</a>
                </c:when>
                <c:otherwise>
                    <div class="pull-left" style="margin-top: 9px;margin-right: 5px;position: relative;"><span
                            class="badge" style="position: absolute; top: -5px; right: -15px; z-index: 1; "></span>
                        <img width="40px" height="40px" src="${ctx}${lgUser.pic}" alt="用户头像"/>
                        <i class="umark umark_b"></i></div>
                    <a target="_blank" href="${ctx}member/show/${lgUser.code}.html"
                       class="pull-left">${lgUser.username }</a>
                    <a href="javascript:void(0);" onclick="logout();" class="pull-left">退出</a>
                </c:otherwise>
            </c:choose>
            <div style="position: absolute;right:0;top: 0;">
                <a href="#" class="share weixin" data-cmd="weixin" title="分享到微信">微信</a>
                <a href="#" class="share weibo" data-cmd="tsina" title="分享到微博">微博</a>
                <a href="#" class="share qzone" data-cmd="qzone" title="分享到QQ空间">QQ空间</a>
            </div>
        </div>
    </div>
</header>

<!-- 遮挡层 -->
<div class="goto-bg">
    <div class="goto-activity">
        <div>
            <a  href="http://weibo.com/u/2854434354" target="_blank"><img src="${ctxPortal}/assets/icon/tsxz.png" alt=""/></a>
            <a href="http://www.10086.cn/xz/" target="_blank"><img src="${ctxPortal}/assets/icon/mobile1.png" alt=""/></a>
            <a href="http://10086.cn/yn/" target="_blank"><img src="${ctxPortal}/assets/icon/mobile2.png" alt=""/></a>
            <a href="http://weibo.com/u/5223358186" target="_blank"><img src="${ctxPortal}/assets/icon/juleb.png" alt=""/></a>
        </div>
    </div>
</div>
<div id="shielding-layer" class="dis-hidden"></div>


<!-- 报名付费 -->
<!-- 报名 -->
<c:if test="${activity.isEnrollPay==1}">
    <div id="sign-up" class="sign-up clearfix dis-hidden">
        <div class="sign-left clearfix">
            <div><img src="${ctxPortal}/assets/icon/left_play.png" alt=""/></div>
        </div>
        <div class="sign-right">
            <button class="off"></button>
            <form id="enrollForm" method="post" class="form-horizontal" role="form">
                <input type="hidden" name="activityCode" value="${activity.code }">
                <input type="hidden" name="OCS" value="${OCS }">

                <div class="sign-info">
                    <!--  -->

                    <c:forEach var="obj" varStatus="sta" items="${listEnrollForm }">
                        <c:if test="${obj.isRequire==0 }">
                            <c:set var="req" value=""/>
                        </c:if>
                        <c:if test="${obj.isRequire==1 }">
                            <c:set var="req" value="*"/>
                        </c:if>
                        <c:choose>
                            <%-- 文本 isRequire  --%>
                            <c:when test="${obj.propertyType==PROPERTY_TYPE_TEXT }">
                                <div class="message clearfix">
                                    <div class="name">
                                        <span style="font-size: 14px;margin-right: 5px;color: #cf1423;">${req}</span>
                                        <label>${obj.property}</label>
                                    </div>
                                    <div class="input-box">
                                        <!-- <input class="form-control" type="text" maxlength="30"/> -->
                                        <input type="text" proType="${obj.propertyType }"
                                               prototypeName="${obj.property}" isRequire="${obj.isRequire}"
                                               class="form-control baomyz"
                                               name="listMemberEnrollDetail[${sta.index }].propertyValue">
                                        <input type="hidden" class="form-control"
                                               name="listMemberEnrollDetail[${sta.index }].enrollFormCode"
                                               value="${obj.code }">

                                        <p class="hint">2-30个字符</p>
                                    </div>
                                </div>
                            </c:when>
                            <%-- 数字 --%>
                            <c:when test="${obj.propertyType==PROPERTY_TYPE_NUMBER }">
                                <div class="message clearfix">
                                    <div class="name">
                                        <span style="font-size: 14px;margin-right: 5px;color: #cf1423;">${req}</span>
                                        <label>${obj.property}</label>
                                    </div>
                                    <div class="input-box">
                                        <!-- <input class="form-control" type="text" maxlength="30"/> -->
                                        <input type="text" proType="${obj.propertyType }" isRequire="${obj.isRequire}"
                                               prototypeName="${obj.property}" class="form-control baomyz"
                                               name="listMemberEnrollDetail[${sta.index }].propertyValue">
                                        <input type="hidden" maxlength="30" class="form-control"
                                               name="listMemberEnrollDetail[${sta.index }].enrollFormCode"
                                               value="${obj.code }">

                                        <p class="hint">最多输入30个数字</p>
                                    </div>
                                </div>
                            </c:when>
                            <%-- 下拉选框 --%>
                            <c:when test="${obj.propertyType==PROPERTY_TYPE_SELECT }">
                                <div class="message clearfix">
                                    <div class="name">
                                        <span style="font-size: 14px;margin-right: 5px;color: #cf1423;">${req}</span>
                                        <label>${obj.property}</label>
                                    </div>
                                    <div class="input-box">
                                        <div class="drop-down">
                                            <div class="dropdown">
                                                <input type="hidden" proType="${obj.propertyType }"
                                                       isRequire="${obj.isRequire}" prototypeName="${obj.property}"
                                                       class="baomyz"
                                                       name="listMemberEnrollDetail[${sta.index }].propertyValue">
                                                <button class="dropdown-toggle" type="button" id="scen-menu"
                                                        data-toggle="dropdown">
                                                    <label>请选择</label><span></span>
                                                </button>
                                                <ul id="viewDorpListOption" class="dropdown-menu" role="menu"
                                                    aria-labelledby="dropdownMenu1">
                                                    <c:forEach var="op" items="${obj.options }">
                                                        <li role='presentation'><a role='menuitem' tabindex='-1'
                                                                                   href='javascript:void(0);'
                                                                                   value='${op}'>${op}</a></li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                        <input type="hidden" class="form-control"
                                               name="listMemberEnrollDetail[${sta.index }].enrollFormCode"
                                               value="${obj.code }">
                                    </div>
                                </div>
                            </c:when>
                            <%-- 上传 --%>
                            <c:otherwise>
                                <div class="message clearfix">
                                    <div class="name">
                                        <span style="font-size: 14px;margin-right: 5px;color: #cf1423;">${req}</span>
                                        <label>${obj.property}</label>
                                    </div>
                                    <div class="input-box">
                                        <input type="hidden" class="form-control"
                                               name="listMemberEnrollDetail[${sta.index }].enrollFormCode"
                                               value="${obj.code }">
                                        <input id="enrollHiddenUrl${sta.index}" type="hidden" class="form-control "
                                               name="listMemberEnrollDetail[${sta.index }].propertyValue">

                                        <p class="hint">支持${obj.propertyType }</p>
                                        <input id="enrollHiddenFileName${sta.index}"
                                               proType="${obj.propertyType }"
                                               isRequire="${obj.isRequire}"
                                               type="text"
                                               readonly="readonly"
                                               class="form-control baomyz upload-input"
                                               name="listMemberEnrollDetail[${sta.index }].fileName"
                                               prototypeName="${obj.property}"
										><div id="enrollPickId${sta.index}" class="upbtn">上传</div>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <!--  -->
                </div>
                <button type="button" class="confirm" onclick="addEnrollForm()">提交</button>
            </form>
        </div>
    </div>
    <%-- 报名 End --%>

    <%-- 付款 --%>
    <div id="pay-money" class="sign-up pay-money clearfix dis-hidden">
        <div class="sign-left pay-left clearfix">
            <div><img src="${ctxPortal}/assets/icon/left_play.png" alt=""/></div>
        </div>
        <div class="sign-right pay-right">
            <button class="off"></button>
            <div class="sign-info pay-info">
                <div class="message">
                    <label>请选择支付方式:</label>
                </div>

                <div class="message">
                    <label>应付总额:</label>
                    <span>￥${activity.money}</span>
                </div>

                <div class="message" style="margin-top: 25px;">
                    <label>支付宝支付:</label>

                    <div class="alipay">
                        <img src="${ctxPortal}/assets/icon/alipay.png" alt=""/>
                    </div>
                </div>
            </div>
            <form id="payForm" method="post">
                <input type="hidden" name="activityCode" value="${activity.code}">
            </form>
            <button type="button" class="confirm" id="J_PayAfterEnroll">确认付款</button>
        </div>
    </div>
    <%-- 付款 End--%>
</c:if>

<div class="ac-notice" style="${activity.isEnroll==1?'':'display: none;'}">
    <div class="goto-xz clearfix">
        <div>
            <button type="button" class="J_pay"></button>
        </div>
    </div>
</div>

<div class="registration goto-regis" style="${activity.isEnroll==1?'':'display: none;'} background-color: #f1f1f1;">
    <div class="container">
        <h1>最新报名
            <small>REGISTRATION</small>
        </h1>
        <div class="enroll-inner">
            <c:if test="${empty listMemberEnrollDetailVO}">
                <div style="height: 210px;" class="nodata"></div>
            </c:if>
            <c:forEach var="obj" items="${listMemberEnrollDetailVO }">
                <div class="eli-item one" style="margin-left: 50px;">
                    <%-- <div class="eli-img">
                        <a href="${ctx}member/show/${obj.memberCode}.html">
                            <img style="width: 159px; height: 159px; border-radius: 50%;" src="${ctx}${obj.pic}" alt="">
                        </a>
                    </div> --%>
                    <c:if test="${obj.sex==0 }">
                        <c:set var="enrollsex" value="female"/>
                    </c:if>
                    <c:if test="${obj.sex==1 }">
                        <c:set var="enrollsex" value="male"/>
                    </c:if>
                    <h2 class="eli-name"><i class="${enrollsex }-md"></i><a target="_blank" href="${ctx}member/show/${obj.memberCode}.html">${obj.memberName}</a></h2>
                    <div class="eli-date">报名时间：<fmt:formatDate value="${obj.enrollTime }" type="both" pattern="yyyy-MM-dd"/></div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<div class="goto-bm">
    <img alt="" onclick="javascript:window.open('http://shang.qq.com/wpa/qunwpa?idkey=0b432a3aa63cfc1ba5a7c9c88b4d66f3e32399812689b17ff038574e74eae578')" src="${ctx}portal/static/images/qqzx.png" style="margin-top: 81px;cursor: pointer;">
    <button type="button" class="J_pay"></button>
</div>

<div id="goto-footer">
<jsp:include page="${root}/portal/headerFooterController/footer"/>
</div>

<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>

<!-- jsp -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctx}manage/webuploaderbase/webuploader.min.js" type="text/javascript"></script>
<%-- <script src="${ctxManage}/webuploader/checkflash.js" type="text/javascript"></script> --%>

  <script type="text/javascript">
  		var basePath_ = "${ctx}";
  		var basePathPortal = "${ctxPortal}";
  		
  		var activityCode_ = "${activity.code }";
  		var isEnd_ = "${isEnd}";
  		var OCS_ = "${OCS }";
  		
  		<%-- var share_activity_summary = "${activity.summary }"; --%>
		var share_activity_name = "${activity.name}";
		var share_activity_img = "${ctx}${activity.img}";
		
		var PROPERTY_TYPE_NUMBER = "${PROPERTY_TYPE_NUMBER}";
		var PROPERTY_TYPE_TEXT = "${PROPERTY_TYPE_TEXT}";
		var PROPERTY_TYPE_SELECT = "${PROPERTY_TYPE_SELECT}";
		
		var listEnrollForm_ = ${empty listEnrollFormJson?'[]':listEnrollFormJson};
  </script>

<script type="text/javascript" src="${ctx}portal/assets/js/activity/qx_detail.js"></script>

<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>


</body>
</html>