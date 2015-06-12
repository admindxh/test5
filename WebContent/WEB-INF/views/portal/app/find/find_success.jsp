<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
	String port = ":" + request.getServerPort();
	if ("80".equals(""+request.getServerPort())) {
		port = "";

	}
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + port + path + "/";
%>

<!DOCTYPE>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title></title>
    <link rel="stylesheet" href="<%=basePath %>portal/modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=basePath %>portal/assets/css/find/find_success.css"/>
        <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
     <style>
	    .main{
	    	margin: 0 auto!important;
	    }
	    .footer{margin-top: 0!important;}
    </style>
</head>
<body id="indexView">
<div  style="background: url('../static/images/find_bg.jpg') no-repeat;background-size: cover;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader( src='../static/images/find_bg.jpg', sizingMethod='scale'); height:800px; padding-top: 200px;">
<div class="main">
    <div class="header"></div>
    <div class="clearfix content">
        <!-- ============ 密码找回成功 ============== -->
        <div class="page-cut"><img src="<%=basePath %>portal/assets/icon/paper_cut.png"/></div>
        <p class="reg-success">恭喜您，新密码已修改成功！</p>
        <p class="return-home"><span id="string">3</span>秒后返回首页</p>
        <!-- ============ 密码找回成功 End ============== -->
    </div>
   </div>
 </div>
 <jsp:include page="${root}/portal/headerFooterController/footer"/>
</body>
<script id="seajsnode" src="<%=basePath %>portal/modules/seajs/seajs/2.2.1/sea.js"></script>
<script>
    // Set configuration
    seajs.config({
        alias: {
            "jquery": "jquery/jquery/1.11.1/jquery.js",
            "avalon":"avalon/1.3.7/avalon.js",
            "footer/css":"../assets/css/footer.css"
        }
    });
    seajs.use('footer/css');
    seajs.use('avalon', function(avalon){
        avalon.define({
            $id:"indexView"
        })
    })
    //刘洪兵新增
    var time=3;
    seajs.use('jquery', function(){
        $(function(){
            $("#string").text(time);
            setInterval(function(){
				$("#string").text(time);
				if(time==0){
					location.href="<%=basePath%>";
				}
                time--;
            },1000);
        })
    })
    function time(){
        alert('asd');
    }
</script>
</html>
