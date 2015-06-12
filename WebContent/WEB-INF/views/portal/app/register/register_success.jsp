<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" /> 
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
    <meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
    <title>天上西藏-感受西藏魅力，体验西藏生活-天上西藏官网</title>
    <%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxPortal }/modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxPortal }/assets/css/register/register_success.css"/>
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <style>
	    .main{
	    	margin: 0 auto!important;
	    }
	    .footer{margin-top: 0!important;}
    </style>
    <script type="text/javascript">
       // Set configuration
        seajs.config({
            alias: {
                "jquery": "jquery/jquery/1.11.1/jquery.js",
                "avalon":"${ctxPortal}/modules/avalon/1.3.7/avalon.js",
                "footer/css":"${ctxPortal}/assets/css/footer.css"
            }
        });
    </script>
</head>
<body>
    <!-- 注册成功页面 -->
 <div style="background: url('../static/images/find_bg.jpg') no-repeat;background-size: cover;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader( src='../static/images/register_bg.jpg', sizingMethod='scale');height:800px; padding-top: 200px;">
    <div id="success-gage" class="success-main">
        <div class="header"></div>
        <div class="clearfix content">
            <div class="success-img"><img src="${ctxPortal }/assets/icon/paper_cut.png"/></div>
            <p class="reg-success">恭喜您，天上西藏会员注册成功！</p>
            <p class="return-home"><span id="second">3</span>秒后返回首页</p>

            <div class="line"></div>
            <div class="clearfix into-btn" style="position: relative;">
                <p>您也可以直接进入：</p>
                <a class="portal-home" href="<%=basePath%>" target="_blank">门户首页</a>
                <a class="personal-center" target="_blank" href="<%=basePath %>member/userinfo/getAllMyMsg">个人中心</a>
            </div>
        </div>
    </div>
  </div>
    <!-- 注册成功页面 End -->
     <jsp:include page="/WEB-INF/views/portal/app/footer/index.jsp"></jsp:include>
    
    <script>
        seajs.use('avalon', function(avalon){
            avalon.define({
                $id:"indexView"
            })
        });
        seajs.use('footer/css');
        //seajs.use('${ctxPortal}/assets/js/register/register.js');
        seajs.use('jquery', function($){
	        $(function(){
		        var i = 3
		       var stopInterval = setInterval(function(){
			        	i--;
		        		$("#second").text(i);
		        		if(i == 1){
							clearInterval(stopInterval);
		        		}
		        	},1000);    
			   setTimeout('window.location="<%=basePath%>"',4000);
	        })
       });

    </script>
</body>
</html>
