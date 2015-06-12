<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title></title>
    <%@include file="/common-html/frontcommon.jsp" %>
    <link rel="stylesheet" href="<%=basePath %>portal/modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=basePath %>portal/assets/css/find/new_password.css"/>
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="<%=basePath %>portal/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
    // Set configuration
    seajs.config({
        alias: {
            "jquery": "jquery/jquery/1.11.1/jquery.js",
            "avalon":"avalon/1.3.7/avalon.js",
            "strength": "strength/strength.js",
            "footer/css":"../assets/css/footer.css"
        }
    });
    </script>
     <style>
	    .main{
	    	margin: 0 auto!important;
	    }
	    .footer{margin-top: 0!important;}
	    .strength-msg{
		    position: absolute!important;
		    bottom: -18px;
		    right: 36px;
		    font-size: 12px;
		    margin-top: 0 !important;
		    line-height: normal!important;
		    padding: 0 5px;
		}
		.veryweak{
		    background-color: #FF6666;
		    border-color: #F04040!important
		}
		.weak{
		    background-color: #FFCCCC;
		    border-color: #FF853C!important;
		}
		.medium{
		    background-color: #99cc66;
		    border-color: #FC0!important;
		}
		.strong{
		    background-color: #339933;
		    border-color: #8DFF1C!important;
		}
		 #logo{
		    padding-bottom: 40px;
		    text-align: center;
		}
    </style>
</head>
<body id="indexView">
<div  style="background: url('../static/images/find_bg.jpg') no-repeat;background-size: cover;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader( src='../static/images/find_bg.jpg', sizingMethod='scale'); height:800px; padding-top: 200px;">
<div id="logo"><a href="<%=basePath %>"><img src="${ctxPortal}/assets/icon/logo_link.png"/></a></div>
<div class="main">
    <div class="header">
        <p>通过邮箱重置密码</p>        <!-- 通过手机找回密码 -->
    </div>
    <form action="<%=basePath %>portal/clientLog/changPassword" method="post" name="passForm" id="passForm">
    <input type="hidden" name="findType" value="byEmail"/>
    <input type="hidden" name="code" value="${code }"/>
    <div class="clearfix content">
        <!-- ============ 通过手机找回密码界面 ============== -->
        <div id="new_password" class="clearfix reg-interface">
            <div class="clearfix" style="position: relative">
                <span>新密码</span>
                <input class="form-control" placeholder="请输入您的新密码" name="password" maxlength="16" id="password" type="password"/>
            </div>
            <div class="clearfix" style="position: relative">
                <span>确认密码</span>
                <input class="form-control" placeholder="请再次输入您的新密码" id="passconfirm"  maxlength="16" type="password"/>
            </div>
        </div>
        <!-- ============ 通过手机找回密码界面 End ============== -->
        <div class="immediately" onclick="funs();">下一步</div>
    </div>
    </form>
</div>
</div>
    <jsp:include page="${root}/portal/headerFooterController/footer"/>
</body>
<script type="text/javascript">
    seajs.use('footer/css');
    seajs.use('avalon', function(avalon){
        avalon.define({
            $id:"indexView"
        })
    })
</script>
<script type="text/javascript">
     /*function sub(){
        var pass=$("#password").val();
        var pac=$("#passconfirm").val();
        alert(pass+pac);
        if(pass==''){
          alert("密码不能为空");
          return false;
        }
         if(pass.length<6||pass.length>16){
          alert("密码为6-16位字符");
          return false;
       }
        if(pass!=pac){
           alert("两次密码不一致");
           return false;
        }
     }*/
     function funs(){
       var pass=$("#password").val();
        var pac=$("#passconfirm").val();
        if(pass==''){
          alert("密码不能为空");
          return false;
        }
        if(pass.length<6||pass.length>16){
            alert("密码为6-16位字符");
            return false;
         }
        if(pass!=pac){
            alert("两次密码不一致");
            return false;
         }
        document.getElementById("passForm").submit();
     }
    seajs.use('jquery', function(){
        $(function(){
           if(${error=='-1'}){
	          alert("没有找到该用户");
	        }
	        if(${error=='-2'}){
	          alert("验证超时");
	        }
	        if(${error== '-3'}){
	          alert("数据异常，操作失败");
	        }
	        if(${error=='-5'}){
	          alert("密码不能为空");
	        }
        })
    })
     seajs.use('strength', function(avalon){
            $(function(){
              $('#password').strength({
                callback: function(total){
                  if (!$(this).siblings('.strength-msg').length) {
                    $(this).after('<div class="strength-msg"></div>')
                  }
                  var $strength = $(this).siblings('.strength-msg')
                  switch(total){
                    case 0:
                      $strength.removeClass('weak');
                      $strength.removeClass('medium');
                      $strength.removeClass('strong');
                      $strength.addClass('weak').text('密码强度：弱')
                    case 1:
                      $strength.removeClass('weak');
                      $strength.removeClass('medium');
                      $strength.removeClass('strong');
                      $strength.addClass('weak').text('密码强度：弱')
                    break;
                    case 2:
                      $strength.removeClass('weak');
                      $strength.removeClass('medium');
                      $strength.removeClass('strong');
                      $strength.addClass('weak').text('密码强度：弱')
                    break;
                    case 3:
                      $strength.removeClass('weak');
                      $strength.removeClass('veryweak');
                      $strength.removeClass('strong');
                      $strength.addClass('medium').text('密码强度：中')
                    break;
                    case 4:
                      $strength.removeClass('weak');
                      $strength.removeClass('medium');
                      $strength.removeClass('veryweak');
                      $strength.addClass('strong').text('密码强度：强')
                    break;
                  }
                }
              });
             
            })
        });
 </script>
</html>