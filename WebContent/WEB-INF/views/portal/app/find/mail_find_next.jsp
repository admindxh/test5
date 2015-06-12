<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE>
<html>
<head lang="en">
    <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title></title>
    <%@include file="/common-html/frontcommon.jsp" %>
    <link rel="stylesheet" href="<%=basePath %>portal/modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=basePath %>portal/assets/css/find/mail_find_next.css"/>
        <!--[if lt IE 9]>
    <script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}modules/fix/respond.min.js"></script>
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
<body id="indexView">
 <div  style="background: url('../static/images/find_bg.jpg') no-repeat;background-size: cover;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader( src='../static/images/register_bg.jpg', sizingMethod='scale'); height:800px; padding-top: 200px;">   
    <div id="logo"><a href="<%=basePath %>"><img src="${ctxPortal}/assets/icon/logo_link.png"/></a></div>
    <div class="main">
        <div class="header">
            <p>通过邮箱找回密码</p>
        </div>
        <div class="clearfix content">
            <div class="exact">
                <img src="<%=basePath %>portal/assets/icon/exactness.png"/>
            </div>
            <input type="hidden" id="email" value="${email }"/>
            <p>“验证邮件已发送到<span>${email }</span>,<br/>您需要点击邮件中的确认链接来完成验证！”</p>
            <div class="go-mail"><a href="${empty url?'javascript:void(0);':url }" target="_blank">立即进入邮箱</a></div>
            <div class="line"></div>
            <div class="help-info">
                <h5>您没有收到确认邮件，怎么办？</h5>
                <p>1.看看是否在您邮箱的回收站中、垃圾邮件中</p>
                <p>2.确认没有收到，点此<a href="javascript:void(0);" id="sendAgain">重发一封</a></p>
            </div>
        </div>
    </div>
  </div>
   <jsp:include page="${root}/portal/headerFooterController/footer"/>
</body>

<script>
   
    seajs.use('footer/css');
    seajs.use('avalon', function(avalon){
        avalon.define({
            $id:"indexView"
        })
    })
</script>
<script type="text/javascript">
	define(function(require){
	var $ = require("jquery");
   $(function(){
	   $('#sendAgain').on('click',function(){//console.log(1);
	         var em=$('#email').val();
	         if(em==''){
	           alert("获取邮箱失败，请重新访问此页面");
	         }
	         $.ajax({
	     			type : "post",
	     			url : "<%=basePath %>portal/clientLog/sendMsg",
	     			data : {email:em,findType:"byEmail"},
	     			dataType : "json",
	     			async : true,
	     			success : function(data) {
	     			     //if(data.status=='101'){
	        			     alert(data.msg);
	        			 //}else {
	        			    //location.href ="../app/find/mail_find_next.html";
	        			    //location.href ='/ctibet/portal/clientLog/turnToMail?email='+email;
	        			 //}
	     	        }
	       })
	  })
	 })
	})
</script>
</html>
