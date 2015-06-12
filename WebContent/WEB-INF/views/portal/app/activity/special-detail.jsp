<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib   prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jstl/fmt_rt" %>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
 	String path = request.getContextPath();
	String port = ":" + request.getServerPort();
	if ("80".equals(""+request.getServerPort())) {
		port = "";
	}
	String basePath = request.getScheme() + "://" + request.getServerName() + port + path + "/";
 	request.setAttribute("ctx", basePath);
 	request.setAttribute("ctxManage", basePath + "/manage/");
 	request.setAttribute("ctxPortal", basePath + "portal/");
 %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="author" content="quansenx">
	<meta name="keywords" content="${content.tdkKeywords}"/>
	<meta name="description" content="${content.tdkDescription}"/>
	<title>${content.tdkTitle}</title>
	
  	<link rel="stylesheet" href="${ctxPortal }/modules/bootstrap/3.3.1/css/bootstrap.min.css">
  	<script id="seajsnode" src="${ctxPortal }/modules/seajs/seajs/2.2.1/sea.js"></script>
  	<script>
  	seajs.config({
  	    alias: {
  	      "jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js",
  	      "avalon":"${ctxPortal}/modules/avalon/1.3.7/avalon.js",
  	      "fancybox":"${ctxPortal}/modules/fancybox/jquery.fancybox.js",
  	      "fancybox/css":"${ctxPortal}/modules/fancybox/jquery.fancybox.css",
  	      "common/css":"${ctxPortal }/assets/css/common.css",
  	      "activity/css":"${ctxPortal }/assets/css/activity/activity.css",
  	      "footer/css":"${ctxPortal }/assets/css/footer.css",
  	      "detail/js":"${ctxPortal }/assets/js/activity/detail.js",
  	      "paginator":"${ctxPortal }/modules/paginator/0.5/bootstrap-paginator.js",
  	      "dateUtil":"${ctx }/common-js/DateUtil.js"
  	    }
  	});
    seajs.use(["jquery"], function($){
		window.$ = $;
    });
  	</script>
  <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>
  <header class="navbar navbar-dark" id="top" role="banner">
		<div class="container">
			<div class="navbar-header pull-left">
				<a  href="${ctx}home" class="navbar-brand">
					<img src="${ctxPortal }/assets/icon/logo-white.png" alt="logo"></a>
			</div>
			<div class="pull-right login-and-share bdsharebuttonbox">
				<div style="position: absolute;right:0;top: 0;">
					<a href="#" class="share weixin" data-cmd="weixin" title="分享到微信">微信</a>
					<a href="#" class="share weibo" data-cmd="tsina" title="分享到微博">微博</a>
					<a href="#" class="share qzone" data-cmd="qzone" title="分享到QQ空间">QQ空间</a>
				</div>  
			</div>
		</div>
	</header>
  <div class="ac-banner" style="background-image: url(${ctx}${content.title });filter: progid:DXImageTransform.Microsoft.AlphaImageLoader( src='${ctx}${content.title }', sizingMethod='scale');">
    <div class="container" style="height: 500px;">
    	<div class="dbanner-title" style="display: none;">
    		<h2 style="${ empty content.title ? 'color: #aaa;' : '' }">${content.contentTitle }</h2>
    	</div>
    </div>
  </div>
  <!-- 上传作品弹出层 End -->
  <div class="ac-intro">
  	<div class="container">
  		<h1>简介 <small>INTRODUCTION</small></h1>
  		<div class="intro-inner" id="share">
  			${content.summary }
  		</div>
  	</div>
  </div>
  <jsp:include page="${root}/portal/headerFooterController/footer"/>
  <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
  <script>
  
  seajs.use(['common/css', 'activity/css' ,'footer/css', 'fancybox/css']);
  seajs.use(['jquery','avalon','fancybox',  'artDialog/6.0.0/dialog', 'paginator'], function($, avalon){
		  $(function(){
			  $.ajax(
		  			{
		  			url:"${ctx}/web/frontContentStatisController/staticsSystemLog",		
		  			data: {protype:"${content.contentType}",tableName:"content",code:"${content.code}",actionType:"click"},
		  			type:"POST",
		  			success:function(data){
		  				
		  			}
		  		  }
		  	  );
		})

    avalon.define({
      $id:"view"
    })
  });//
  seajs.use("bootstrap/3.3.1/js/dropdown.js",function(){
      $('[data-toggle="dropdown"]').dropdown();
  });
  seajs.use('${ctxPortal}/assets/js/tourism/strategy_list.js');
  seajs.use('artDialog/6.0.0/dialog', function(dialog){
    var elem = document.getElementById('voteDialog')
    var d = dialog({
      fixed: true,
      content: elem,
      padding:0
    })
  })
  
  </script>
	<script>
	    window._bd_share_config = {
	        "common": {
	            //"bdText": $("#share").text(), // 分享的内容
	            "bdText" : "${activity.name}", // 分享的内容
	            "bdDesc": "分享的摘要", // 分享的摘要
	            "bdUrl": "", // 分享的Url地址
	            "bdPic": "" // 分享的图片
	        },
	        "share": {
	            "bdSize": 24,
	            "bdCustomStyle": '${ctxPortal}/assets/css/common.css'
	        }
	    };
	    with (document)0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];
  </script>
  <jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
 
</body>
</html>