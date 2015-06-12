<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/commonTag.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="author" content="quansenx">
	<meta name="keywords" content="${activity.tdkKeywords }"/>
	<meta name="description" content="${activity.tdkDescription }"/>
	<title>${activity.tdkTitle }</title>
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
                    <a target="_blank" href="${ctx}member/show/${lgUser.code}.html" class="pull-left">${lgUser.username }</a>
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
    <!-- jsp -->
  <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
  <script src="${ctx}manage/webuploaderbase/webuploader.min.js" type="text/javascript"></script>
  
  <script type="text/javascript" src="${ctx}portal/assets/js/activity/activity_detail.js"></script>
  
</body>
</html>