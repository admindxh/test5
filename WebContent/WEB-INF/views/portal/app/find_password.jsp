<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
    <meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
    <title>天上西藏-感受西藏魅力，体验西藏生活-天上西藏官网</title>
    <%@include file="/common-html/frontcommon.jsp" %>
    <link rel="stylesheet" href="<%=basePath %>portal/modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="<%=basePath %>portal/modules/seajs/seajs/2.2.1/sea.js"></script>
    <style>
    .main{
    	margin: 0 auto!important;
    }
    .footer{margin-top: 0!important;}
   
	  #logo{
		    padding-bottom: 40px;
		    text-align: center;
		}
	
    </style>
    <script type="text/javascript">
       // Set configuration
	    seajs.config({
	        alias: {
	            "jquery": "jquery/jquery/1.11.1/jquery.js",
	            "avalon":"avalon/1.3.7/avalon.js",
	            "footer/css":"../assets/css/footer.css"
	        }
	    });
    </script>
</head>
<body class="clearfix">
<div  style="background: url('../static/images/find_bg.jpg') no-repeat;background-size: cover;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader( src='../static/images/register_bg.jpg', sizingMethod='scale'); height:800px; padding-top: 200px;">
<div id="logo" style="position:relative;"><a href="<%=basePath %>"><h1><img src="${ctxPortal}/assets/icon/logo_link.png"/></h1></a></div>
<div class="main">
    <div class="clearfix header">
        <div id="phone" class="phone"><h2 style="font-size: 18px;">通过手机重置密码</h2></div>        <!-- 通过手机找回密码 -->
        <div id="mail" class="mail">通过邮箱重置密码</div>         <!-- 通过邮箱找回密码 -->
    </div>
    <div class="clearfix content">
        <!-- ============ 通过手机找回密码界面 ============== -->
        <form action="<%=basePath %>portal/clientLog/changePage" method="post" name="phForm">
        <input type="hidden" name="findType" value="byMobile"/>
        <div id="phone-info" class="clearfix reg-interface">
            <div class="clearfix">
                <span>手机号</span>
                <input class="form-control" placeholder="请输入您的手机号" id="mobile" name="mobile" value="${mobile }" type="text"/>
            </div>
            <div class="clearfix">
                <span>手机验证</span>
                <span class="validate-btn">获取验证码</span>
                <input class="form-control validate" id="validateCode" name="code" value="${code }" placeholder="请输入验证码" type="text"/>
            </div>
            <div id="phone-next" class="immediately">下一步</div>
        </div>
        </form>
        <!-- ============ 通过手机找回密码界面 End ============== -->
        <!-- ============ 通过邮箱找回密码界面 ============== -->
        <div id="mail-info" class="clearfix reg-interface">
            <div class="clearfix">
                <span>邮箱</span>
                <input class="form-control" placeholder="请输入您的邮箱号" value="${email }" id="Email" type="text"/>
            </div>
            <div id="mail-next" class="immediately">下一步</div>
        </div>
        <!-- ============ 通过邮箱找回密码界面 End ============== -->
    </div>
</div>
</div>
<!-- footer 
<div id="footer" ms-include-src="'footer/index.html'"></div>-->
<jsp:include page="${root}/portal/headerFooterController/footer"/>

<script>
   
    seajs.use(['footer/css','jquery']);
    seajs.use('avalon', function(avalon){
        avalon.define({
            $id:"indexView"
        })
    })

    seajs.use('../assets/css/find/find_password.css');
    seajs.use('../assets/js/find/find_password.js');
    seajs.use('jquery',function($){
          
                if(${error=='-1'}){
		          alert("手机号或验证码不能为空");
		        }
		        if(${error=='-2'}){
		          alert("未找到该手机号的验证信息");
		        }
		        if(${error== '-3'}){
		          alert("时间超过三分钟，验证已经失效");
		        }
		        if(${error == '-4'}){
		          alert("验证码错误");
		        }
		        if(${error == '-6'}){
		          alert("邮箱验证超时");
		          location.href='<%=basePath %>portal/clientLog/changeInfo';
		        }
       		$(function(){
       			$('#mail').trigger('click');
       		});
	      $("#mail-next").on('click',function(){
	        var email=$('#Email').val();
	        if(email==''){
	           alert("请输入邮箱");
	           return;
	        }
	        $.ajax({
		     			type : "post",
		     			url : "<%=basePath%>portal/clientLog/sendMsg",
		     			data : {email:email,findType:"byEmail"},
		     			dataType : "json",
		     			async : true,
		     			success : function(data) {
		     			     if(data.status=='101'){
		        			     alert(data.msg);
		        			 }else{
		        			    //location.href ="../app/find/mail_find_next.html";
		        			    location.href ='<%=basePath%>portal/clientLog/turnToMail?email='+email;
		        			 }
		     	        }
		       })
	    });
	
	    $('.validate-btn').on('click',function(){
	        var mobile=$('#mobile').val();
	        var $this = $(this), $db = $this.data('db')
	        
	        if($db){
	        	return false;
	        }
	        if(mobile==''){
	         alert("请输入手机号");
	        }
	        if(mobile!=''){
		        $.ajax({
		     			type : "post",
		     			url : "<%=basePath%>portal/clientLog/sendMsg",
		     			data : {mobile:mobile,findType:"byMobile"},
		     			dataType : "json",
		     			async : true,
		     			success : function(data) {
		        			 if(data.status=='101'){
		        			    alert(data.msg);
		        			 }else if(data.status=='1'){
		        			    alert(data.msg);
		        			    $this.data('db', true)
		        			    var count = 60;
		        			    _timer($this, count)
		        			 }
		     	        }
		       })
	       }
	     });
	     
	     function _timer(el, countdown){
	     	--countdown
	     	el.text(countdown + ' 秒后重新获取')
	     	if(countdown && countdown<60){
	     		setTimeout(function(){
	     			_timer(el, countdown)
	     		}, 1000)
	     	}
	     	if(countdown < 1){
	     		el.text('获取验证码').data('db', false)
	     	}
	     }
    })
</script>
<div>
</div>
</body>
</html>
