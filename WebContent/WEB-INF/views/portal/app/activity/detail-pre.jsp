<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
		.fancybox-title-inslid-wrap .child{
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
	      "avalon":"avalon/1.3.7/avalon.js",
	      "fancybox":"fancybox/jquery.fancybox.js",
	      "paginator":"paginator/0.5/bootstrap-paginator.js",
	      "fancybox/css":"fancybox/jquery.fancybox.css",
	      "common/css":"${ctxPortal }/assets/css/common.css",
	      "activity/css":"${ctxPortal }/assets/css/activity/activity.css",
	      "footer/css":"${ctxPortal }/assets/css/footer.css",
	      "detail/js":"${ctxPortal }/assets/js/activity/detail.js",
	      "dateUtil":"${ctx }/common-js/DateUtil.js"
	    }
  });
  seajs.use(["jquery"], function($){
		window.$ = $;
  });
  </script>
  <style>
  input[type='radio']:focus{
  	box-shadow:none;
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
				<%-- <a href="${ctx}/portal/registerController/register">注册</a>
				<a href="#loginModal" data-toggle="login">登录</a> --%>
               <c:choose>
                  <c:when test="${empty lgUser}">
                    <a href="<%=basePath %>portal/registerController/register">注册</a>
                    <a id="login-btn" href="#loginModal" data-toggle="login">登录</a>
                  </c:when>
                  <c:otherwise>
                  	<div class="pull-left" style="margin-top: 9px;margin-right: 5px;position: relative;"><span class="badge" style="position: absolute; top: -5px; right: -15px; z-index: 1; "></span>
                    <img width="40px" height="40px" src="${ctx}${lgUser.pic}" alt="用户头像"/>
                    <i class="umark umark_b"></i></div>
                	<%-- <a href="javascript:void(0);" onclick="toUserCenter();" class="pull-left">
                       <c:choose>
							<c:when test="${fn:length(lgUser.username)>5 }">${fn:substring(lgUser.username,0,5) }...</c:when>
                           <c:otherwise>${lgUser.username}</c:otherwise>
                       </c:choose>
                    </a> --%>
                    <a href="${ctx}member/show/${lgUser.code}.html" class="pull-left">${lgUser.username }</a>
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
  <div class="ac-banner" style="background-image: url(${ctx}${activity.bannerImg });">
    <div class="container" style="height: 500px;">
    	<div class="dbanner-title" style="display: none;">
    		<h1 style="${ empty activity.bannerImg ? 'color: #aaa;' : '' }">${activity.name }</h1>
    	</div>
      <div class="btns">
      	<c:if test="${activity.isEnroll==1 }"><a id="apply-btn" href="#loginModal" class="btn-link2">我要报名</a></c:if>
      	<c:if test="${activity.isUpload==1 }"><a id="production-btn" href="#loginModal" class="btn-link2">上传作品</a></c:if>
      </div>
    </div>
  </div>
  <!-- 遮挡层 -->
  <div id="shielding-layer" class="dis-hidden"></div>
  <!-- 报名弹出层 -->
  <c:if test="${activity.isEnroll==1&&activity.isEnrollPay==0}">
  <div class="apply-bg">
	  <div id="apply" class="dis-hidden">
	    <div class="clearfix apply-header">
	      <button id="apply-close">close</button>
	    </div>
	    <div class="apply-body">
	      <form id="enrollForm" method="post" class="form-horizontal" role="form">
	      	<input type="hidden" name="activityCode" value="${activity.code }">
	        <!-- 报名 -->
	        <c:forEach var="obj" varStatus="sta" items="${listEnrollForm }">
	        	<c:if test="${obj.isRequire==0 }">
		        	<c:set var="req" value="" />
	        	</c:if>
	        	<c:if test="${obj.isRequire==1 }">
		        	<c:set var="req" value="*" />
	        	</c:if>
	        	<c:choose>
	        		<%-- 文本 isRequire  --%>
	        		<c:when test="${obj.propertyType==PROPERTY_TYPE_TEXT }">
		        		<div class="clearfix form-group">
				          <label class="ac-name"><span style="font-size: 14px;margin-right: 5px;color: #cf1423;">${req}</span>${obj.property }</label>
				          <div class="input-box">
				            <input type="text" proType="${obj.propertyType }" prototypeName="${obj.property}" isRequire="${obj.isRequire}" class="form-control baomyz" name="listMemberEnrollDetail[${sta.index }].propertyValue">
				            <input type="hidden" class="form-control" name="listMemberEnrollDetail[${sta.index }].enrollFormCode" value="${obj.code }">
				            <span class="hint">2-30个字符</span>
				          </div>
				        </div>
	        		</c:when>
	        		<%-- 数字 --%>
	        		<c:when test="${obj.propertyType==PROPERTY_TYPE_NUMBER }">
	        			<div class="clearfix form-group">
				          <label class="ac-name"><span style="font-size: 14px;margin-right: 5px;color: #cf1423;">${req}</span>${obj.property }</label>
				          <div class="input-box">
				            <input type="text"  proType="${obj.propertyType }" prototypeName="${obj.property}" isRequire="${obj.isRequire}" class="form-control baomyz" name="listMemberEnrollDetail[${sta.index }].propertyValue">
				            <input type="hidden" maxlength="30" class="form-control" name="listMemberEnrollDetail[${sta.index }].enrollFormCode" value="${obj.code }">
				            <span class="help-block">最多输入30个数字</span>
				          </div>
				        </div>
	        		</c:when>
	        		<%-- 下拉选框 --%>
	        		<c:when test="${obj.propertyType==PROPERTY_TYPE_SELECT }">
	        			<div class="clearfix form-group">
				          <label class="ac-name"><span style="font-size: 14px;margin-right: 5px;color: #cf1423;">${req}</span>${obj.property }</label>
				          <div class="input-box">
				          		<div class="drop-down">
					                <div class="dropdown">
				                    	<input type="hidden" class="baomyz" prototypeName="${obj.property}"  proType="${obj.propertyType }"  isRequire="${obj.isRequire}" name="listMemberEnrollDetail[${sta.index }].propertyValue">
					                	<button class="dropdown-toggle" type="button" id="scen-menu" data-toggle="dropdown">
				                        <label>请选择</label><span></span></button>
					                    <ul id="viewDorpListOption" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
						                    <c:forEach var="op" items="${obj.options }">
						                        <li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value='${op}'>${op}</a></li>
						            		</c:forEach>
					                    </ul>
					                </div>
				                </div>
				            <%-- <input type="text" class="form-control" name="listMemberEnrollDetail[${sta.index }].propertyValue"> --%>
				            <input type="hidden" class="form-control" name="listMemberEnrollDetail[${sta.index }].enrollFormCode" value="${obj.code }">
				          </div>
				        </div>
	        		</c:when>
	        		<%-- 上传 --%>
	        		<c:otherwise>
		        		<div class="clearfix form-group">
				          <label class="ac-name"><span style="font-size: 14px;margin-right: 5px;color: #cf1423;">${req}</span>${obj.property }</label>
				          <div class="input-group input-box">
				          	<!-- 需要传回后台的值{ -->
					            <input type="hidden" class="form-control" name="listMemberEnrollDetail[${sta.index }].enrollFormCode" value="${obj.code }">
					            <input id="enrollHiddenUrl${sta.index}" type="hidden" class="form-control" name="listMemberEnrollDetail[${sta.index }].propertyValue">
				            <!-- } -->
				            <input id="enrollHiddenFileName${sta.index}"
				            	type="text"
				            	readonly="readonly"
				            	class="form-control baomyz fliesc"
				            	name="listMemberEnrollDetail[${sta.index }].fileName"
				            	proType="${obj.propertyType }"
				            	isRequire="${obj.isRequire}"
				            	prototypeName="${obj.property}"
				            ><div id="enrollPickId${sta.index}">上传</div>
				            <span class="help-block">${obj.propertyType }</span>
				          </div>
				        </div>
	        		</c:otherwise>
	        	</c:choose>
	        </c:forEach>
	        <button type="button" class="ac-submit" onclick="addEnrollForm()">提交</button>
	      </form>
	    </div>
	  </div>
  </div>
  </c:if>
  <!-- 报名弹出层 End -->


<!-- 报名付费 -->
<!-- 报名 -->
<c:if test="${activity.isEnrollPay==1}">
<div id="sign-up" class="sign-up clearfix dis-hidden">
    <div class="sign-left clearfix">
        <div><img src="${ctxPortal}/assets/icon/left_play.png"/></div>
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
			        	<c:set var="req" value="" />
		        	</c:if>
		        	<c:if test="${obj.isRequire==1 }">
			        	<c:set var="req" value="*" />
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
				                    <input type="text" proType="${obj.propertyType }" prototypeName="${obj.property}" isRequire="${obj.isRequire}" class="form-control baomyz" name="listMemberEnrollDetail[${sta.index }].propertyValue">
				                    <input type="hidden" class="form-control" name="listMemberEnrollDetail[${sta.index }].enrollFormCode" value="${obj.code }">
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
				                    <input type="text" proType="${obj.propertyType }" isRequire="${obj.isRequire}" prototypeName="${obj.property}" class="form-control baomyz" name="listMemberEnrollDetail[${sta.index }].propertyValue">
				                    <input type="hidden" maxlength="30" class="form-control" name="listMemberEnrollDetail[${sta.index }].enrollFormCode" value="${obj.code }">
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
				                    		<input type="hidden" proType="${obj.propertyType }" isRequire="${obj.isRequire}" prototypeName="${obj.property}" class="baomyz" name="listMemberEnrollDetail[${sta.index }].propertyValue" >
						                	<button class="dropdown-toggle" type="button" id="scen-menu" data-toggle="dropdown">
					                        	<label>请选择</label><span></span>
					                        </button>
						                    <ul id="viewDorpListOption" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
							                    <c:forEach var="op" items="${obj.options }">
							                        <li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value='${op}'>${op}</a></li>
							            		</c:forEach>
						                    </ul>
						                </div>
					                </div>
				                    <input type="hidden" class="form-control" name="listMemberEnrollDetail[${sta.index }].enrollFormCode" value="${obj.code }">
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
				                	<input type="hidden" class="form-control" name="listMemberEnrollDetail[${sta.index }].enrollFormCode" value="${obj.code }">
						            <input id="enrollHiddenUrl${sta.index}" type="hidden" class="form-control " name="listMemberEnrollDetail[${sta.index }].propertyValue">
				                    <p class="hint">支持${obj.propertyType }</p>
				                    <input id="enrollHiddenFileName${sta.index}"
				                    	proType="${obj.propertyType }"
				                    	isRequire="${obj.isRequire}"
				                    	type="text"
				                    	readonly="readonly"
				                    	class="form-control baomyz upload-input"
				                    	name="listMemberEnrollDetail[${sta.index }].fileName"
				                    	prototypeName="${obj.property}"
				                    ><div id="enrollPickId${sta.index}" class="upbtn">上传</div><%-- <button type="button" id="enrollPickId${sta.index}">上传</button> --%>
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
</div><%-- 报名 End --%>

<%-- 付款 --%>
<div id="pay-money" class="sign-up pay-money clearfix dis-hidden">
    <div class="sign-left pay-left clearfix">
        <div><img src="${ctxPortal}/assets/icon/left_play.png"/></div>
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
                    <img src="${ctxPortal}/assets/icon/alipay.png"/>
                </div>
            </div>
        </div>
        <form id="payForm" method="post">
        	<input type="hidden" name="activityCode" value="${activity.code}">
        </form>
        <button type="button" class="confirm" id="J_PayAfterEnroll">确认付款</button>
    </div>
</div><%-- 付款 End--%>
</c:if>
<!-- 报名付费 End -->


  <!-- 上传作品弹出层 -->
  <div id="production" class="dis-hidden">
    <div class="clearfix apply-header">
      <button id="production-close">close</button>
    </div>
    <div class="apply-body">
      <form id="activityProductForm" method="post" class="form-horizontal" role="form">
      	<input type="hidden" name="activityCode" value="${activity.code }">
        <div class="clearfix form-group">
          <label class="ac-name">作品名称</label>
          <div class="input-box">
            <input type="text" class="form-control upload-reg" name="name">
            <span class="help-block">2-30个字符</span>
          </div>
        </div>
        <div class="clearfix form-group">
          <label>作品</label>
          <div class="input-group input-box input-filebox">
            <input id="productFileName"
            	readonly="readonly"
            	type="text"
            	class="form-control upload-reg fliesc"
            	name="fileName"
            ><div id="productPickId">上传</div><!-- <button id="productPickId" type="button">上传</button> -->
			<input id="productUrl" type="hidden" name="url">
            <span class="help-block">支持jpg,jpeg,bmp,png</span>
          </div>
        </div>
        <button type="button" class="ac-submit" onclick="addActivityProduct()">提交</button>
      </form>
    </div>
  </div>
  <!-- 上传作品弹出层 End -->
  <div class="ac-intro">
  	<div class="container">
  		<h1>简介 <small>INTRODUCTION</small></h1>
  		<div class="intro-inner" id="share">
  			${activity.summary }
  		</div>
  	</div>
  </div>

  <!-- 投票 -->
  <c:if test="${activity.isVote==1 }">
  <div class="ac-vote">
		<div class="container">
			<h1>参与投票 <small>VOTE</small></h1>
			<div class="vote-inner">
				<div class="vote-choose">
					<h2>${activity.voteName }</h2>
					<c:forEach var="obj" items="${listVoteOption }">
						<div class="radio">
							<label>
								<input type="radio" name="choice" value="${obj.code }">${obj.options }
							</label>
						</div>
					</c:forEach>
			          <div class="vi-btns">
			            <button type="button" class="vi-btn" onclick="vote(this)">投票</button><a href="javascript:;" class="vi-btn" data-toggle="vote">查看结果</a>
			          </div>
				</div>
				<div class="vote-intro">
					  <h2>投票简介</h2>
			          <div class="vi-inner">
			            ${activity.voteSummary }
			          </div>
				</div>
			</div>
		</div>
  </div>
  </c:if>

  <div class="ac-notice" style="${activity.isEnroll==1?'':'display: none;'}">
    <div class="container">
      <h1>报名须知 <small>NOTICE</small></h1>
      <c:if test="${empty enrollNotice.name1 && empty enrollNotice.summary1 && empty enrollNotice.name2 && empty enrollNotice.summary2 && empty enrollNotice.name3 && empty enrollNotice.summary3}">
      	<div class="nodata"></div>
      </c:if>
      <c:if test="${not empty enrollNotice.name1 || not empty enrollNotice.summary1 || not empty enrollNotice.name2 || not empty enrollNotice.summary2 || not empty enrollNotice.name3 || not empty enrollNotice.summary3}">
	      <div class="acn-list">
	        <div class="al-item">
	          <h2>${enrollNotice.name1 }</h2>
	          ${enrollNotice.summary1 }
	        </div>
	         <div class="al-item">
	          <h2>${enrollNotice.name2 }</h2>
	          ${enrollNotice.summary2 }
	        </div>
	        <div class="al-item">
	          <h2>${enrollNotice.name3 }</h2>
	          ${enrollNotice.summary3 }
	        </div>
	      </div>
      </c:if>
      <c:if test="${activity.isEnrollPay==1 }">
	      <div class="ac-buy">
	        <a href="#loginModal" id="J_NowPay" class="purchase">立即购买</a>
	      </div>
      </c:if>
    </div>
  </div>

  <!-- 作品 -->
  <c:if test="${activity.isUpload==1 }">
  <div class="ac-entries" ms-important="actProductView">
    <div class="container">
      <h1>参赛作品展示 <small>ENTRIES</small></h1>
      <div class="entries-inner clearfix">

        <div class="ei-item" ms-repeat="data" ms-class="one:$first" ms-class-1="odd:$index%2==0">
          <div class="eii-title eii-new">
            <i></i>
            <h2>{{el.name}}</h2>
          </div>
          <div class="ei-img" ms-click="showBox(el, $index)">
            <a href="javascript:;"><img ms-src="${ctx}{{el.url}}" ms-attr-alt="el.activityName"></a>
          </div>
          <div class="participant">
            <div class="p-head">
              <a ms-attr-href="${ctx}member/show/{{el.memberCode}}.html">
                <img ms-if="el.sex==0" ms-src="${ctx}{{el.pic?el.pic:'/portal/static/default/female.jpg'}}"  width="67" height="67">
                <img ms-if="el.sex==1" ms-src="${ctx}{{el.pic?el.pic:'/portal/static/default/male.jpg'}}"  width="67" height="67">
                <div class="ph-mark"></div>
              </a>
            </div>
            <div class="p-info" style="margin-left: 13px;">
              <h3 ms-attr-class="{{ el.sex==1?'p-name male':'p-name female'}}"><a ms-attr-href="${ctx}member/show/{{el.memberCode}}.html">{{el.memberName}}</a></h3>
              <p>上传时间：{{el.createTime | date('yyyy-MM-dd')}}</p>
            </div>
          </div>
        </div>
		
		<div class="nodata" ms-visible="!data.size()"></div>
		
      </div>
      <div id="actProductPage" class="paging" ms-visible="data.size()"></div>
    </div>
  </div>
  </c:if>

  <div class="registration" style="${activity.isEnroll==1?'':'display: none;'}">
    <div class="container">
      <h1>最新报名 <small>REGISTRATION</small></h1>
      <div class="enroll-inner">
      	<c:if test="${empty listMemberEnrollDetailVO}">
      		<div style="height: 210px;" class="nodata"></div>
      	</c:if>
        <c:forEach var="obj" items="${listMemberEnrollDetailVO }">
	        <div class="eli-item one">
		          <div class="eli-img">
			            <a href="${ctx}member/show/${obj.memberCode}.html">
						  <img style="width: 159px; height: 159px;" src="${ctx}${obj.pic}">
			              <span class="eli-img-mark"></span>
			            </a>
		          </div>
		          <c:if test="${obj.sex==0 }">
				  	<c:set var="enrollsex" value="female" />
				  </c:if>
				  <c:if test="${obj.sex==1 }">
				  	<c:set var="enrollsex" value="male" />
				  </c:if>
		          <h2 class="eli-name"><i class="${enrollsex }-md"></i><a href="${ctx}member/show/${obj.memberCode}.html">${obj.memberName}</a></h2>
		          <div class="eli-date">报名时间：<fmt:formatDate value="${obj.enrollTime }" type="both" pattern="yyyy-MM-dd" /></div>
	        </div>
        </c:forEach>
      </div>
    </div>
  </div>
  <!-- 服务站展示 -->
  <div class="ac-entries" ms-important="entriseView" style="${not empty activity.otherBlock?'':'display: none;'}">
    <div class="container">
      <h1>${activity.otherBlock } <small>ENTRIES</small></h1>
      <div class="entries-inner inner-circle clearfix">

        <div class="ei-item" ms-repeat="data" ms-class="one:$first">
          <div class="ei-img">
            <a ms-attr-href="el.hyperlink" target="_blank"><img ms-src="${ctx}{{el.url}}" ms-attr-alt="el.name"></a>
          </div>
           <div class="eii-title">
            <h2><a ms-attr-href="el.hyperlink" target="_blank">{{ el.name }}</a></h2>
          </div>
        </div>

      </div>
	    <div class="nodata" ms-visible="!data.size()"></div>
      <div id="entriesPage" class="paging" ms-visible="data.size()"><!-- 分页按钮 --></div>
    </div>
  </div>

  <div class="cbt-dialog" id="voteDialog">
    <div class="cd-header">
      查看投票结果
      <a href="javascript:;" class="cd-close">x</a>
    </div>
    <div class="dialog-body">
      <div class="clearfix">

        <div class="vote-title">
          <div class="cd-logo"></div>
          <p class="vote-q">${activity.voteName }</p>
          <p class="text-muted">共 <strong>${allFakeVoteNum }</strong> 人参与投票</p>
        </div>

        <div class="vq-list">
          <c:forEach var="obj" items="${listVoteOptionVO }">
          	   <div class="vql-item">
	            <p>${obj.options }</p>
	            <div class="clearfix">
	              <div class="vp">
	                <div class="percentage blue" style="width:${obj.percent}%;">${obj.percent}%</div>
	              </div>
	              <span>${obj.fakeCounts }票</span>
	            </div>
	          </div>
          </c:forEach>
        </div>
      </div>
    </div>
  </div>
  <jsp:include page="${root}/portal/headerFooterController/footer"/>
  
  <jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
  <script>
  function logout(){
	var confirm=window.confirm("是否退出");
	if(confirm){
	  window.location.href='<%=basePath %>portal/clientLog/logout';
	  }
  }
  seajs.use(['common/css', 'activity/css' ,'footer/css', 'fancybox/css']);
  seajs.use(['jquery', 'avalon','fancybox',  'artDialog/6.0.0/dialog', 'paginator'], function($, avalon){
    avalon.define({
      $id:"view"
    })
  });//
  
  /* seajs.use(['jquery', 'jquery.alert/main.js'], function($){
  	$(function(){
  		$.alert('我是信息提示内容', function(){
	  		$.alert('少年郎，你点击了确定。')
	  	})
  	})
  }) */
  seajs.use(['jquery' ,"bootstrap/3.3.1/js/dropdown.js"],function($){
      $('[data-toggle="dropdown"]').dropdown();
  });
  seajs.use('${ctxPortal}/assets/js/tourism/strategy_list.js');
  seajs.use(['jquery', 'artDialog/6.0.0/dialog'], function($, dialog){
    var elem = document.getElementById('voteDialog')
    var d = dialog({
      fixed: true,
      content: elem,
      padding:0
    })
    // 关闭 投票结果
    $('.cd-close').on('click', function(event) {
      event.preventDefault()
      d.close()
    })
    // 显示 投票结果
    $('[data-toggle="vote"]').on('click',function(event) {
      event.preventDefault()
      d.showModal()
    })
  })

  	//分页
	seajs.use(["avalon", "jquery", "paginator", "dateUtil"], function(avalon, $){
		//分页
		function servicesPage(pageId, currentPage, totalPage, call_){
			var options = {
		     	currentPage: currentPage || 1,
		     	totalPages: totalPage || 1,
		     	removeClass:'paging-white',
		     	onPageChanged: function(e,oldPage,newPage){
		     		call_(newPage);
		    	}
			}
			//分页按钮
			$('#'+pageId).bootstrapPaginator(options);
		}
		// { 存放其他模块数据
		var entriesVM = avalon.define({
			$id:'entriseView',
			data:[],
			$cacheData:{}
		});
		//获取其他模块数据
		function getOtherBlockList(currentPage){
			var thisCall = getOtherBlockList;
			//判断缓存没有就请求服务器
			if(entriesVM.$cacheData[currentPage]){
				entriesVM.data = entriesVM.$cacheData[currentPage];
			}else{
				$.post('${ctx}portal/activityController/showOtherBlockList', {activityCode:'${activity.code }',currentPage: currentPage, pageSize: 4}, function(response){
				  	servicesPage("entriesPage", response.currentPage, response.totalPage, thisCall);
				  	entriesVM.data = entriesVM.$cacheData[currentPage] = response.dataList;
				}, 'json');
			}
		}
		// }
		var _actProductCode;
		var actProductVM = avalon.define({
			$id:"actProductView",
			data:[],
			$index:0,
			$currentPage: 1,
			$totalPage: 1,
			showBox: function(vmodel, index){
				actProductVM.$index = index
				var option = {};
				option.href = '${ctx}' + vmodel.url;

				option.title = vmodel.name
				option.memberName = "BY " + vmodel.memberName
				option.code = _actProductCode = vmodel.code;
				_showBox(option);
			},
			$cacheData:{},
			$like: {}
		});
		window.actProductVM = actProductVM
		// 绑定点赞事件
		$(document).on('click', '.fancybox-like', function(){
			var $this = $(this)
			$.post('${ctx}portal/activityProductController/clickLike', {code:_actProductCode}, function(response){
			  	if(response=="success"){
			  		$this.addClass('like-active')
			  		actProductVM.$like[_actProductCode] = _actProductCode;
			  		alert("点赞成功");
			  	}
			  	if(response=="already"){
			  		$this.addClass('like-active')
			  		actProductVM.$like[_actProductCode] = _actProductCode;
			  		alert("已赞");
			  	}
			});
		});
		$(document).on('click', '.fa-prev', function(){
			var c_index = actProductVM.$index;
			var c_page = actProductVM.$currentPage;
			if(c_index==0){
				if(c_page!=1){
					getActProductList(c_page-1, "prev");
				}
			}else{
				$('#actProductPage').bootstrapPaginator("show", c_page);
				actProductVM.$index = c_index-1;
				var _data = actProductVM.data[c_index-1]
				var option = {};
				option.href = '${ctx}' + _data.url;
				option.title = _data.name
				option.memberName = "BY " + _data.memberName
				option.code = _actProductCode = _data.code;
				_showBox(option);
			}
		})
		$(document).on('click', '.fa-next', function(){
			var c_index = actProductVM.$index;
			var c_page = actProductVM.$currentPage;
			var t_page = actProductVM.$totalPage;
			if(c_index==actProductVM.data.length-1){
				if(c_page < t_page){
					getActProductList(c_page+1, "next");
				}
			}else{
				$('#actProductPage').bootstrapPaginator("show", c_page);
				actProductVM.$index = c_index+1;
				var _data = actProductVM.data[c_index+1]
				var option = {};
				option.href = '${ctx}' + _data.url;
				option.title = _data.name;
				option.memberName = "BY " + _data.memberName
				option.code = _actProductCode = _data.code;
				_showBox(option);
			}
		})
		function _showBox(option){
			var data = [];
			$.fancybox(option, {
		        padding:5,
		        openEffect: 'elastic',
		        openSpeed: 150,
		        closeEffect: 'elastic',
		        closeSpeed: 150,
		        prevEffect : 'fade',
		        nextEffect : 'fade',
		        modal:false,
		        arrows:false,
		        minWidth: 500,
		        minHeight: 500,
		        helpers: {
					title: {
						type: 'inslid'
					}
		        },
		        afterShow: function(e){
		        		$.fancybox.reposition();
			        	$.fancybox.update();
						$('.fancybox-title.fancybox-title-inslid-wrap').append('<span style="margin-top: 0;" class="child">' + option.memberName + '</span>')
			        	if(actProductVM.$like[_actProductCode]){
							$(".fancybox-like").addClass('like-active')
						}
		        }
			});
		}
		window.$ = $
		//获取作品数据
		function getActProductList(currentPage, type){
			var thisCall = getActProductList;
			if(actProductVM.$cacheData[currentPage]){
				actProductVM.data = actProductVM.$cacheData[currentPage];
				actProductVM.$currentPage = currentPage;
				if(type == 'next'){
			  		actProductVM.$index = -1
			  		$('.fa-next').trigger('click')
			  	}
			  	if(type == 'prev'){
			  		actProductVM.$index = actProductVM.data.length
			  		$('.fa-prev').trigger('click')
			  	}
			}else{
				$.post('${ctx}portal/activityProductController/showActivityDetailActivityProductList', {activityCode:'${activity.code }',currentPage: currentPage, pageSize: 4}, function(response){
					actProductVM.$currentPage = response.currentPage
					actProductVM.$totalPage = response.totalPage
				  	servicesPage("actProductPage", response.currentPage, response.totalPage, thisCall);
				  	actProductVM.data = actProductVM.$cacheData[currentPage] = response.dataList;

				  	if(type == 'next'){
				  		actProductVM.$index = -1
				  		$('.fa-next').trigger('click')
				  	}
				  	if(type == 'prev'){
				  		actProductVM.$index = actProductVM.data.length
				  		$('.fa-prev').trigger('click')
				  	}
				}, 'json');
			}
		}

		//进入页面初始列表
		function servicesLoader(){
			getOtherBlockList(1);
			getActProductList(1);
		}
		servicesLoader();
	});//

  </script>

  <!-- jsp -->
  <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
  <script src="${ctx}manage/webuploaderbase/webuploader.min.js" type="text/javascript"></script>
  
  <c:if test="${empty pcOrmb}">
     <%-- <script src="${ctxManage}/webuploader/checkflash.js" type="text/javascript"></script> --%>
  </c:if>
  
  
  <script type="text/javascript">
	function addEvent(up, urlHiddenId, fileNameHiddenId){
  		up.on( 'uploadSuccess', function(file, response) {
			$("#"+fileNameHiddenId).siblings(".file-ok").remove();
			$("#"+urlHiddenId).val(response.filePath);
			$("#"+fileNameHiddenId).val(file.name);

			var name = file.name.replace("."+file.ext,"");
			if(name.length>5){
				name = name.substr(0,5)+"...";
			}
			file.name = name+file.ext;
			var o = "";
			o += "<div class='file-ok'>"
	        o += "	<img src='${ctxPortal }/assets/icon/ac_success.png' alt=''/>"
	        o += "	<span class='ac-success'>已成功</span><br />"
	        o += "	<span class='ac-document'>"+file.name+"</span>"
	        o += "</div>";
	        $("#"+urlHiddenId).parent().append(o);
			this.reset();
	    });
  		up.on( 'uploadError', function(file, reason) {
  			//$.alert("上传格式错误");
  			_alert.show("上传格式错误");
  		});
  		up.on( 'error', function(type) {
			//alert("错误信息:"+type);
			if(type=="Q_EXCEED_SIZE_LIMIT" || type=="F_EXCEED_SIZE"){
				_alert.show("文件超过大小");
			}else if(type=="Q_TYPE_DENIED"){
				_alert.show("上传格式错误");
  			}else if(type=="Q_EXCEED_NUM_LIMIT"){
  				_alert.show("最多上传5张图片，请重新选择文件。");
  			}else{
	  			_alert.show("上传错误，请重试。");
  			}
	    });
  	}
  	function cteateUploder(pickId, fileExt, urlHiddenId, fileNameHiddenId, floderName, fileSingleSizeLimit, compress){
  		var contextPath = "${ctx}";
  		var options_={
			swf :  contextPath+'/manage/webuploader/Uploader.swf',
			server : contextPath+'/portal/activityController/fileUpload',
			runtimeOrder : "flash",
			accept : {extensions : fileExt},
			formData:{
				activityCode: '${activity.code }',
				floderName: floderName
			},
			pick : {
				id:'#'+pickId,
				multiple:false
			},
			fileVal : 'files',
			auto : true,
			resize: false,
			fileSingleSizeLimit: fileSingleSizeLimit,
			compress: compress
		};
  		var uploader = new WebUploader.Uploader(options_);
		addEvent(uploader,urlHiddenId,fileNameHiddenId);
		return uploader;
	}

  	//初始化上传组件
  	var fileSingleSizeLimit = 2 * 1024 * 1024;
	var banner_compress = {
	    width: 1140,
	    height: 420,
	    crop: true
	};
	var other_compress = {
	    width: 188,
	    height: 188,
	    crop: true
	};

	var uploaderArray = new Array();
	function deleteUpload(){
		for(var i=0;i<uploaderArray.length;i++){
			uploaderArray[i].destroy();
		}
		uploaderArray = new Array();
	}
  	function initUpload(){
  		//报名表单
	    var text="${PROPERTY_TYPE_TEXT}";
	    var number="${PROPERTY_TYPE_NUMBER}";
	    var select="${PROPERTY_TYPE_SELECT}";
	    var listEnrollForm = ${empty listEnrollFormJson?'[]':listEnrollFormJson};
	  	for (var i = 0; i < listEnrollForm.length; i++) {
			var obj = listEnrollForm[i];
			if(obj.propertyType!=text && obj.propertyType!=number && obj.propertyType!=select){
				var enrollHiddenUrl="enrollHiddenUrl"+i;
				var enrollHiddenFileName="enrollHiddenFileName"+i;
				var up = cteateUploder("enrollPickId"+i, obj.propertyType.split(".").join(""), enrollHiddenUrl, enrollHiddenFileName, 'enrollform', fileSingleSizeLimit, false);
				uploaderArray.push(up);
			}
		}
  	}
  	window.onload=function(){
  		//initUpload();
  	}
  </script>
  <script type="text/javascript">
  var actUploder;
  seajs.use(['jquery'], function($){
	  $(function(){
	  	//打开报名窗
	  	$("#apply-btn").on('click',function(){
	  		if('${isEnd}'=='yes'){
	  			_alert.show("活动已结束");
	  		}else{
		  		var height = $("#sign-up").height();
		  		$(".sign-left").height(height);
	
		  		var url="${ctx}portal/memberEnrollDetailController/checkEnroll";
		  		var data={activityCode:"${activity.code}"};
		  		$.post(url,data,function(response){
		  			if(response==""){
		  				$("#shielding-layer").addClass('dis-show').removeClass('dis-hidden');
		  				$("#apply").addClass('dis-show').removeClass('dis-hidden');
		  				$("#sign-up").addClass('dis-show').removeClass('dis-hidden');
		  			  	initUpload();
		  			}else{
		  				if(response=='请先登录'){
							$('[data-toggle="login"]').trigger('click');
//                            $('#login-btn').click();
		  				}else{
			  				_alert.show(response);
		  				}
		  				// 打开 登录框
		  			}
		  		});
	  		}
		});
	  	
	  	//支付
		function pay(){
	  		if('${isEnd}'=='yes'){
	  			_alert.show("活动已结束");
	  		}else{
		  		var url = "${ctx}portal/order/checkPay";
		  		$.post(url, {activityCode: '${activity.code}', OCS:'${OCS }'}, function(response){
		  			if(response=='need_login'){
		  			///	alert("请先登录");
		  				// 打开 登录框
						$('[data-toggle="login"]').trigger('click');
		  			}else if(response=='need_enroll'){
		  				//alert("请先报名");
		  				var height = $("#sign-up").height();
			  			$(".sign-left").height(height);
		  				$("#shielding-layer").addClass('dis-show').removeClass('dis-hidden');
		  				$("#apply").addClass('dis-show').removeClass('dis-hidden');
		  				$("#sign-up").addClass('dis-show').removeClass('dis-hidden');
		  			  	initUpload();
		  			  	$(window).scrollTop(0);
		  			}else if(response=='already_pay'){
		  				_alert.show("已经支付");
		  			}else if(response=='error'){
		  				_alert.show("生成订单发生错误");
		  			}else if(response=='to_pay'){
		  				$("#payForm").attr("action", "${ctx}/alipay/pay");
		  		  		$("#payForm").submit();
		  			}
		  		});
	  		}
	  	}
	  	
	  	//报名后支付
	  	$('#J_PayAfterEnroll').on('click', function(){
	  		pay();
	  	})
	  	//立即购买
	  	$('#J_NowPay').on('click', function(){
	  		pay();
	  	})
	  	//关闭
		$("#apply-close").on('click',function(){
		  	deleteUpload();
		  	$("#shielding-layer").addClass('dis-hidden').removeClass('dis-show');
		  	$("#apply").addClass('dis-hidden').removeClass('dis-show');
		});
	  	//打开上传窗
		$("#production-btn").on('click',function(){
			if('${isEnd}'=='yes'){
	  			_alert.show("活动已结束");
	  		}else{
		  		var url="${ctx}portal/activityProductController/checkActtivityProduct";
		  		var data={activityCode:"${activity.code}"};
		  		$.post(url,data,function(response){
		  			if(response==""){
		  			  	$("#shielding-layer").addClass('dis-show').removeClass('dis-hidden');
		  			  	$("#production").addClass('dis-show').removeClass('dis-hidden');
		  				//初始化上传作品上传组件
		  			  	actUploder = cteateUploder("productPickId", "jpg,jpeg,bmp,png", "productUrl", "productFileName", 'product', fileSingleSizeLimit, false);
		  			}else{
		  				if(response=='请先登录'){
							$('[data-toggle="login"]').trigger('click');
		  				}else{
			  				_alert.show(response);
		  				}
		  			}
		  		});
	  		}
		});
	  	//关闭
		$("#production-close").on('click',function(){
			actUploder.destroy();
		  	$("#shielding-layer").addClass('dis-hidden').removeClass('dis-show');
		  	$("#production").addClass('dis-hidden').removeClass('dis-show');
		})
	  })
  })



	/* 报名付款 */
    /* $("#sign-up .confirm").on('click',function(){
        $("#sign-up").addClass('dis-hidden').removeClass('dis-show');
        $("#pay-money").addClass('dis-show').removeClass('dis-hidden');
    }); */
    $(document).on('click','.off',function(){
        var thisparent = $(this).parents('.sign-up');
        thisparent.addClass('dis-hidden').removeClass('dis-show');
        $("#shielding-layer").addClass('dis-hidden').removeClass('dis-show');
    });

	//报名提交
	function addEnrollForm(){
		$("#apply input[name='files']").attr('disabled',"true");
		var $form = $('#enrollForm');
		var url = '${ctx}portal/memberEnrollDetailController/addMemberEnrollDetail';
		//alert($form.serialize());
		if(yanz()){
			$.post(url, $form.serialize(), function(data){
				if(data=='success'){
			      	$("#apply-close").click();
			      	$("#sign-up").addClass('dis-hidden').removeClass('dis-show');
			        $("#pay-money").addClass('dis-show').removeClass('dis-hidden');
			      	_alert.show("保存成功");
			    }else if(data=='error'){
			      	$("#apply input[name='files']").removeAttr("disabled");
			      	_alert.show("保存失败");
			    }else if(data=='need_login'){
			      	$("#apply input[name='files']").removeAttr("disabled");
			      	_alert.show("请先登录");
			    }
			});
		}
  	}

	//作品提交
	function addActivityProduct(){
		$("#production input[name='files']").attr('disabled',"true");
      	var $form = $('#activityProductForm');
      	var url = '${ctx}portal/activityProductController/addActivityProduct';
      	if(upLoadReg()){
      		$.post(url, $form.serialize(), function(data){
    	        if(data=='success'){
    	          	$("#production-close").click();
    				$("#production input[name='files']").removeAttr("disabled");
    				var upLoadInput = $("#production input.upload-reg");

    				//清空表单
    				$(".apply-body .file-ok").remove();
    				upLoadInput.eq(0).val("");
    				upLoadInput.eq(1).val("");
    				$("#productUrl").val("");

    	          	_alert.show("上传成功，请等待管理员审核");
    	          	location.reload();
    	      	}else if(data=='error'){
    	          	$("#production input[name='files']").removeAttr("disabled");
    	          	_alert.show("保存失败");
    	        }
          	});
      	}
	}
	//投票
	function vote(btn){
		if('${isEnd}'=='yes'){
  			_alert.show("活动已结束");
  		}else{
			var voteOptionCode = $("input[name='choice']:checked").val();
			if(voteOptionCode!=undefined && voteOptionCode!=""){
		        var url = '${ctx}portal/voteOptionController/vote';
		        var param = {code: voteOptionCode, activityCode: '${activity.code }'};
		        var i = $.post(url, param, function(data){
		            if(data=='success'){
		            	$(btn).text("已投票");
		    			$(btn).attr('disabled',"true");
		    			location.reload();
		            }else if(data=='error'){
		            	_alert.show("投票失败");
		            }else if(data=='already'){
		            	_alert.show("你已经投过票了");
		            }
		        });
			}
  		}
	}

	//报名验证必填
	function yanz(){
		var temp = false;
		var msg = "";
		var inputNode = $("input.baomyz");
		var regName = /^(\S|\s){2,30}$/,
				regNum = /^[0-9]{1,30}$/;
		for(var i=0;i<inputNode.length;i++){
			var val = inputNode.eq(i).val();
			var prototypeName = inputNode.eq(i).attr("prototypeName");
			inputNode.eq(i).val($.trim(val))
			if(inputNode.eq(i).attr("isRequire") == 1){
				if(inputNode.eq(i).attr("proType")=="${PROPERTY_TYPE_TEXT}"){
					if(!regName.test(val) || $.trim(val) == ""){
						temp = true;
						msg = prototypeName + ",需要输入2-30个字符";
						break;
					}
				}else if(inputNode.eq(i).attr("proType")=="${PROPERTY_TYPE_NUMBER}"){
					if(!regNum.test(val)){
						temp = true;
						msg = prototypeName + ",最多输入30个数字";
						break;
					}
					
				}else{
					if(val==""){
						temp = true;
						msg = prototypeName + ",为必填项";
						break;
					}
				}
			}
		}

	
		
		if(temp){
			_alert.show(msg);
			return false;
		}
		return true;
	}
	//上传验证必填
	function upLoadReg(){
		var upLoadInput = $("#production input.upload-reg");
		var regName = /^(\S|\s){2,30}$/;
		upLoadInput.eq(0).val($.trim(upLoadInput.eq(0).val()));
		if(!regName.test(upLoadInput.eq(0).val()) || $.trim(upLoadInput.eq(0).val()) == ""){
			_alert.show("名称需要输入2-30个字符");
			return false;
		}else if(upLoadInput.eq(1).val() == ""){
			_alert.show("请上传图片");
			return false;
		}
		return true;
	}

	$.ajax({
		type : "post",
		url : "${ctx}member/userinfo/getUnReadCount",
		dataType : "json",
		async : true,
		success : function(data) {
		    if(data.status=='1'){
		      if(data.msg=='0'){
		          	$('.badge').remove();
		      }else{
		        	$('.badge').text(data.msg);
		      }
		    }
       }
	 })

  </script>
  <script type="text/javascript">
  	/* //支付
  	function pay(){
  		if('${isEnd}'=='yes'){
  			_alert.show("活动已结束");
  		}else{
	  		var url = "${ctx}portal/order/checkPay";
	  		$.post(url, {activityCode: '${activity.code}', OCS:'${OCS }'}, function(response){
	  			if(response=='need_login'){
	  			///	alert("请先登录");
	  				// 打开 登录框
					$('[data-toggle="login"]').trigger('click');
	  			}else if(response=='need_enroll'){
	  				//alert("请先报名");
	  				var height = $("#sign-up").height();
		  			$(".sign-left").height(height);
	  				$("#shielding-layer").addClass('dis-show').removeClass('dis-hidden');
	  				$("#apply").addClass('dis-show').removeClass('dis-hidden');
	  				$("#sign-up").addClass('dis-show').removeClass('dis-hidden');
	  			  	initUpload();
	  			  	$(window).scrollTop(0);
	  			}else if(response=='already_pay'){
	  				_alert.show("已经支付");
	  			}else if(response=='error'){
	  				_alert.show("生成订单发生错误");
	  			}else if(response=='to_pay'){
	  				$("#payForm").attr("action", "${ctx}/alipay/pay");
	  		  		$("#payForm").submit();
	  			}
	  		});
  		}
  	} */
  </script>
  <script>
		window._bd_share_config = {
			"common" : {
				<%--"bdText" : "${activity.summary }", // 分享的内容--%>
				<%--"bdText" : $("#share").text(), // 分享的内容--%>
				"bdText" : "${activity.name}", // 分享的内容
				"bdDesc" : "", // 分享的摘要
				'bdComment':'骑行吧，去西藏！史上最有保障的川滇骑行，即可报名啦！',
				"bdUrl": "", // 分享的Url地址
				"bdPic": "${ctx}${activity.img}" // 分享的图片
			},
			"share" : {
				"bdSize" : 24,
				"bdCustomStyle": '${ctxPortal}/assets/css/common.css'
			}
		};
		with (document)0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];
	</script>
</body>
</html>