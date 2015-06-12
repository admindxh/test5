<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.rimi.ctibet.domain.LogUser" session="true"%>
<%  LogUser lg=(LogUser)request.getSession().getAttribute("logUser");
    request.setAttribute("lgUser",lg);
 %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化"/>
    <meta name="description" content="天上西藏网为您提供全面靠谱的西藏旅游资讯，旅游攻略，西藏自助游，西藏自驾游，西藏骑行，全面权威的西藏文化介绍。打造权威的西藏文化旅游网。"/>
    <%@include file="/common-html/frontcommon.jsp" %>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxPortal}/assets/css/my-center/modified_data.css"/>
    <link rel="stylesheet" href="${ctxPortal}/modules/scrollbar/jquery.mCustomScrollbar.css">
    <title>【天上西藏】西藏旅游-感受西藏魅力，体验西藏生活-天上西藏官网</title>
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
    seajs.config({
        alias: {
            "jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js",
            "avalon": "${ctxPortal}/modules/avalon/1.3.7/avalon.js",
            "dateTime": "${ctxPortal}/modules/DateTimePicker/jquery.datetimepicker.js",
            "dropdown": "${ctxPortal}/modules/bootstrap/3.3.1/js/dropdown.js",
            "webuploader": "${ctxPortal}/modules/webuploader/webuploader.js",
            "upload": "${ctxPortal}/modules/webuploader/upload.js",
            "dateTime/css": "${ctxPortal}/modules/DateTimePicker/jquery.datetimepicker.css",
            "header": "${ctxPortal}/assets/css/common.css",
            "footer/css": "${ctxPortal}/assets/css/footer.css",
            "jcrop/css":"${ctxPortal}/modules/Jcrop/css/jquery.Jcrop.min.css",
        }
    });
    var contextPath = '${ctxPortal}';
   </script>
    
    
    <style type="text/css">
		.avatar { position: absolute; left: 296px; top: 46px;}
		.avatar img { width: 128px; height: 128px; display: block;}
		.pickfiles{
			display: inline-block;
			padding-top:4px;
			background-color: #068322;
		    border: 0 none;
		    border-radius: 3px;
		    color: #fff;
		    height: 30px;
		    margin-right: 10px;
		    outline: medium none;
		    width: 80px;
		    text-align: center;
		}
		.main{
	    	margin: 0 auto!important;
	    }
	    .footer{margin-top: 230px!important;}
	</style>
</head>

<body>
<!-- top -->
<jsp:include page="${root}/portal/headerFooterController/header"></jsp:include>

<!-- content -->
<div class="container main" ms-controller="indexView">
    <div class="header">
        <ul class="clearfix">
            <li class="active">修改资料</li>
            <li id="changeP">修改头像</li>
            <li>修改密码</li>
        </ul>
        <div><img src="${ctxPortal}/assets/icon/return.png"/><a href="<%=basePath %>member/userinfo/getAllMyMsg" target="_self">返回个人中心</a></div>
    </div>

    <div class="row dis-show">
    <form action="<%=basePath %>member/account/update" method="post" name="userForm" id="userForm">
        <input type="hidden" id="imgUrl" value="${lgUser.pic }" name="pic"/>
        <input type="hidden" name="code" id="memberCode" value="${lgUser.code }"/>
        <input type="hidden" name="memberCode" id="memberCode" value="${lgUser.code }"/>
        <!-- 修改资料（邮箱） -->
        <div class="modified-phone dis-show">
            <div class="phone-item">
                <span>昵称:</span>
                <input type="text" name="username" id="username" value="${lgUser.username }" class="form-control"/>
            </div>
      
              <c:if test="${not empty lgUser.mobile&&(empty lgUser.email||lgUser.isBind eq '0')}">
	            <div class="phone-item">
	                <span>手机:</span>
	                <label>${lgUser.mobile }</label>
	                <input type="hidden" value="${lgUser.mobile }" id="uMobile"/>
	            </div>
	            <div class="phone-item">
	                <span>邮箱:</span>
	                <input type="text" class="form-control" name="email" id="email" value="" placeholder="请输入邮箱"/>
	                <button type="button" id="mailVali">发送验证码</button>
	            </div>
	            <div class="phone-item">
	                <span>输入<br/>验证码:</span>
	                <input type="text" class="form-control" name="code" id="codeE" placeholder="请输入验证码"/>
	                <button type="button" id="checkE">验证</button>
	                <div>
	                    <span class="warning">*</span>
	                    <p>绑定您的邮箱，将双重保障您的账户安全，<br/>同时邮箱也可以作为用户名登</p>
	                </div>
	            </div>
	          </c:if>
	            
              <c:if test="${not empty lgUser.email&&empty lgUser.mobile}">
	            <div class="phone-item">
	                <span>邮箱:</span>
	                <label>${lgUser.email }</label>
	                <input type="hidden" value="${lgUser.email }" id="uEmail"/>
	            </div>
	            <div class="phone-item">
	                <span>手机:</span>
	                <input type="text" name="phone" class="form-control" id="mobile" value="" placeholder="请输入手机号"/>
	                <button type="button" id="phoneVali">发送验证码</button>
	            </div>
	            <div class="phone-item">
	                <span>输入<br/>验证码:</span>
	                <input type="text" class="form-control" name="code" id="codeM" placeholder="请输入验证码"/>
	                <button type="button" id="checkM">验证</button>
	                <div>
	                    <span class="warning">*</span>
	                    <p>绑定您的手机，将双重保障您的账户安全，<br/>同时手机号也可以作为用户名登</p>
	                </div>
	            </div>
	          </c:if>
	           <c:if test="${not empty lgUser.email&&not empty lgUser.mobile}">
	             <div class="phone-item">
	                <span>手机:</span>
	                <label>${lgUser.mobile }</label>
	                <input type="hidden" value="${lgUser.mobile }" id="uMobile"/>
	            </div>
	            <c:if test="${lgUser.isBind eq '1'}">
	             <div class="phone-item">
	                <span>邮箱:</span>
	                <label>${lgUser.email }</label>
	                <input type="hidden" value="${lgUser.email }" id="uEmail"/>
	             </div>
	            </c:if>
              </c:if>
              
             <input type="hidden" id="hiddenEmail" value="${lgUser.email }"/> 
             <input type="hidden" id="hiddenMobile" value="${lgUser.mobile }"/>
            <input type="hidden" id="isChk" value="0"/>
            <div class="phone-item">
                <span>性别:</span>
                <input id="man" name="sex" value="1" type="radio" checked="checked"/><label for="man">男</label>
                <input id="woman" name="sex" value="0" type="radio" <c:if test="${lgUser.sex eq '0'}">checked="checked"</c:if>/><label for="woman">女</label>
            </div>
            <div class="phone-item">
                <span>生日:</span>
                <input id="datetimepicker" value="<fmt:formatDate value="${lgUser.birthday }" pattern="yyyy-MM-dd"/>" name="birthday" class="form-control" type="text">
            </div>
            <div class="phone-item">
                <span>所在地:</span>

                <div class="dropdown">
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu" data-toggle="dropdown"><span class="text"> {{ citiesText }} </span><span class="caret"></span>
                    </button>
                    <input type="hidden" name="province" ms-duplex="citiesText" id="province"/>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" style="max-height: 200px;overflow: hidden;overflow-y: auto;">
                    	<li role="presentation" ms-repeat="cities" ms-click="changeCities($key)"><a role="menuitem" tabindex="-1" href="javascript:;">{{ $key }}</a></li>
                    </ul>
                </div>
                <div class="dropdown">
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown"><span class="text">{{ cityText }}</span><span class="caret"></span>
                    </button>
                    <input type="hidden" id="city" name="city" ms-duplex="cityText"/>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" style="max-height: 200px;overflow: hidden;overflow-y: auto;">
                    	<li role="presentation" ms-repeat="citys" ms-click="changeCity(el)"><a role="menuitem" tabindex="-1" href="javascript:;">{{ el }}</a></li>
                    </ul>
                </div>
            </div>
            <button type="button" class="finish" id="finalSub">完成</button>
        </div>
     </form>

        <!-- 头像 -->
        <div class="head-portrait">
            <div>
                <img width="120" height="120" <c:if test="${not empty lgUser.pic}">src="${ctx }${lgUser.pic }"</c:if>
                                  <c:choose>
				                     <c:when test="${lgUser.sex eq '1'}">src="${ctxPortal}/static/default/male.jpg"</c:when>
				                     <c:otherwise>src="${ctxPortal}/static/default/female.jpg"</c:otherwise> 
				                   </c:choose> 
                                  id="userPic" />
            </div>
            <button id="change-picture">更换头像</button>
        </div>
    </div>
    <!-- 修改头像 -->
    <div class="modification-picture">
       <div class="see-about">
            <div class="upload-img clearfix">
                <div class="pull-left">
                    <h5>原始图片</h5>
                    <!-- default/male.jpg -->
                    <div class="jcrop-holder-wrap">
                    <img id="target" <c:if test="${not empty lgUser.pic}">src="${ctx }${lgUser.pic }"</c:if> <c:if test="${empty lgUser.pic}">src="${ctxPortal}static/default/male.jpg"</c:if>/></div>
                </div>
                <div id="preview-pane" class="pull-left">
                    <h5>头像浏览</h5>
                    <div class="preview-container">
                        <img <c:if test="${not empty lgUser.pic}">src="${ctx }${lgUser.pic }"</c:if> <c:if test="${empty lgUser.pic}">src="${ctxPortal}static/default/male.jpg"</c:if> class="jcrop-preview" alt="Preview">
                    </div><span style="color: gray;">建议上传尺寸600*800的图片</span>
                </div>
            </div>
            <div id="btnuploadfile" class="pickfiles">选择文件</div>
            <button id="cutImg" type="button">保存</button>
            <input type="hidden" id="x" name="x" />
            <input type="hidden" id="y" name="y" />
            <input type="hidden" id="w" name="w" />
            <input type="hidden" id="h" name="h" />
        </div>
        <div class="hint" style="width: 316px">
            Hi，${lgUser.username }，告诉你一个秘密，数据显示，自己设置的头像总是比默认头像好看8.6倍！
        </div>
    </div>
    <!-- 修改密码 -->
    <div class="change-password" style="height: 300px;">
    <form action="<%=basePath %>member/account/updatePass" method="post" name="passForm">
        <div class="phone-item">
            <span>旧密码:</span>
            <input type="password" id="oldpwd" name="oldPass" maxlength="16" class="form-control"/>
        </div>
        <div class="phone-item">
            <span>新密码:</span>
            <input type="password" id="pwd" name="newPass" maxlength="16" class="form-control"/>
        </div>
        <div class="phone-item">
            <span>确认<br/>新密码:</span>
            <input type="password" id="pwdConfirm" maxlength="16" class="form-control"/>
        </div>
        <button id="changePass" type="button" class="finish">完成</button>
      </form>
    </div>
</div>

<!-- footer -->
<jsp:include page="${root}/portal/headerFooterController/footer"/>
<script type="text/javascript">
  seajs.use(['header', 'footer/css', 'dateTime/css','jcrop/css']);
    seajs.use(['jquery', 'avalon', 'dateTime'], function ($, avalon) {
        var cityModel;
        avalon.ready(function(){
            cityModel= avalon.define({
                $id: "indexView",
                citys: [],
                cities:{},
                citiesText:'${lgUser.province}',
                cityText:'${lgUser.city }',
                changeCities: function(val){
                    cityModel.citiesText = val
                    cityModel.cityText = cities[val][0]
                    cityModel.citys = cities[val]
                    $("#mobile").focus().blur();
                    $("#codeM").focus().blur();
                },
                changeCity: function(val){
                    cityModel.cityText = val
                    $("#mobile").focus().blur();
                    $("#codeM").focus().blur();
                }
            })
            avalon.scan()
            cityModel.cities = cities
            cityModel.citys = cities[cityModel.citiesText]
            window.cityModel = cityModel
        })
    });
    seajs.use('dropdown');

    seajs.use(['jquery','webuploader'], function ($) {
        $(".main .header ul li").on('click', function () {
            var $this = $(this),
                    thisText = $this.text();
            $this.addClass('active').nextAll('li').removeClass('active');
            $this.prevAll('li').removeClass('active');
            if(thisText == "修改资料"){
                $('.main>div').not(".main div.row").removeClass('dis-show');
                $(".main .row").addClass('dis-show');
            }
            if(thisText == "修改头像"){
            	if(!uploader){
            		__initUploader()
            	}
            	//uploader.addButton('#btnuploadfile');
                $(".main>div").not(".main .modification-picture").removeClass('dis-show');
                $(".main .modification-picture").addClass('dis-show');
                
            }
            if(thisText == "修改密码"){
                $('.main>div').not(".main div.change-password").removeClass('dis-show');
                $('.main .change-password').addClass('dis-show');
            }
        });

        $("#change-picture").on('click',function(){
            if(!uploader){
            	__initUploader()
            }
            var liNode = $(".main .header ul li").eq(1);
            $(".main>div").not(".main .modification-picture").removeClass('dis-show');
            $(".main .modification-picture").addClass('dis-show');
            liNode.addClass('active').nextAll('li').removeClass('active');
            liNode.prevAll('li').removeClass('active');
            
        });

        // 下拉菜单
        /*$(document).on('click','.dropdown-menu li',function(){
            var $this = $(this),
                    aText = $this.children('a').text(),
                    btnText = $this.parent('.dropdown-menu').siblings('[data-toggle="dropdown"]').children('.text');
            btnText.text(aText);
        });*/
        var uploader;
        function __initUploader(){
        	uploader = WebUploader.create({
			    // 选完文件后，是否自动上传。
			    auto: true,
			    // swf文件路径
			    swf: contextPath +'/modules/webuploader/Uploader.swf',
			    // 文件接收服务端。
			    server: contextPath + '/userAccountSet/uploadPic',
			    // 选择文件的按钮。可选。
			    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
			    pick: '#btnuploadfile',
			    // 设置文件上传域的name
			    fileVal:'file',
			    // 验证文件总数量, 超出则不允许加入队列x
				fileNumLimit : 10,
				fileSizeLimit : 0.8 * 1024 * 1024,
			    // 只允许选择图片文件。
			    accept: {
			        title: 'Images',
			        extensions: 'gif,jpg,jpeg,bmp,png',
			        mimeTypes: 'image/*'
			    }
			});
            uploader.on('uploadAccept', function (object, ret) {
                if (ret.map.status != 1) {
                    return false
                }
                var imgUrl = contentPath + ret.map.url
                $('#target').removeAttr('style').attr('src', imgUrl).hide();
                $('.preview-container img').removeAttr('style').attr('src', imgUrl)
                $('#imgUrl').val(ret.map.url);
                
                /*if(jcrop_api){
                    jcrop_api.setImage(imgUrl)
                    jcrop_api.setSelect([0, 0, 120, 120]);
                }*/
               setTimeout(function(){
                    initJcrop()
                },100)
            });
	
			uploader.on('uploadFinished', function(file){
				 
			})
			uploader.on('error', function(handler) {
				if (handler == "Q_EXCEED_NUM_LIMIT") {
					alert("超出最大张数");
				}
				if (handler == "F_DUPLICATE") {
					alert("文件重复");
				}
		
				if (handler == "Q_EXCEED_SIZE_LIMIT") {
					alert("文件大小超出限制");
				}
				if(handler=="Q_TYPE_DENIED"){
	  			    alert("上传格式错误");
				}
		   });
        }
        $(function(){
           
          if(${type=='pic'}){
          	__initUploader()
             var liNode = $(".main .header ul li").eq(1);
             $(".main>div").not(".main .modification-picture").removeClass('dis-show');
             $(".main .modification-picture").addClass('dis-show');
             liNode.addClass('active').nextAll('li').removeClass('active');
             liNode.prevAll('li').removeClass('active');
          }
          if(${type=='data'}){
             $('.main>div').not(".main div.row").removeClass('dis-show');
                $(".main .row").addClass('dis-show');
          }
          if(${type=='pass'}){
             var liNode = $(".main .header ul li").eq(2);
             $(".main>div").not(".main .modification-picture").removeClass('dis-show');
             $(".main .change-password").addClass('dis-show');
             liNode.addClass('active').nextAll('li').removeClass('active');
             liNode.prevAll('li').removeClass('active');
          }
		  if(${error=='-1'}){
		     alert("请选择性别！");
		  }
		  if(${error=='-2'}){
		    alert("邮箱不能为空！");
		  }
		  if(${error=='-3'}){
		     alert("手机号码不能为空！");
		  }
		  if(${error=='-4'}){
		     alert("昵称不能为空！");
		  }
		  if(${error=='-5'}){
		     alert("密码不能为空！");
		  }
		  if(${error=='-6'}){
		     alert("生日不能为空！");
		  }
		  if(${error=='-7'}){
		     alert("请选择所在省！");
		  }
		  if(${error=='-8'}){
		     alert("请选择所在市！");
		  }
		  if(${error=='-9'}){
		     alert("积分不能为空！");
		  }
		  if(${error=='-110'}){
		     alert("旧密码不能为空！");
		  }
		  if(${error=='-111'}){
		     alert("新密码不能为空！");
		  }
		  if(${error=='-112'}){
		     alert("原密码错误！");
		  }
		  if(${phoneExsist=='1'}){
		     alert("电话号码不能重复！");
		  }
		  if(${emailExsist=='1'}){
		     alert("邮箱不能重复！");
		  }
		  if(${nameExsist=='1'}){
		     alert("昵称不能重复");
		  }
		   if(${fileError=='1'}){
		     alert("文件上传错误！");
		  }
		  if(${hasChanged=='1'}){
		    alert("密码修改成功，请重新登录");
		    location.href='<%=basePath%>portal/clientLog/loginPage';
		  }
		
		//提交
		$('#changePass').on('click',function(){
		   var oldpwd=$('#oldpwd').val();
		   var pwd=$('#pwd').val();
		   var pwdconfirm=$('#pwdConfirm').val();
		  
		   if(oldpwd==''){
		      alert("旧密码不能为空");
		      return ;
		   }
		   if(oldpwd.length<6||oldpwd.length>16){
		      alert("密码长度为6-16位字符");
		      return;
		   }
		   if(pwd==''){
		     alert("新密码不能为空");
		     return;
		   }
		   if(pwd.length<6||pwd.length>16){
		      alert("密码长度为6-16位字符");
		      return;
		   }
		   if(pwdconfirm==''){
		     alert("确认密码不能为空");
		     return;
		   }
		   if(pwd!=pwdconfirm){
		     alert("两次录入的密码不一致");
		     return;
		   }
		   
		   document.forms.passForm.submit();
		})
		//发送验证码
		$("#mailVali").on('click',function(){
	        var email=$('#email').val();
	        var memberCode=$('#memberCode').val();
	        var uEmail=$('#uEmail').val();
	        if(email==''){
	           alert("请输入邮箱");
	           return;
	        }
	        var reg = /^([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	        if(!reg.test(email)){
	           alert("邮箱格式不正确");
	           return;
	        }
	        if(email==uEmail){
	          alert("该邮箱与当前绑定的邮箱一致，请更换");
	          return;
	        }
	        $.ajax({
	     			type : "post",
	     			url : "<%=basePath%>member/account/sendMsg",
	     			data : {email:email,findType:"byEmail",memberCode:memberCode},
	     			dataType : "json",
	     			async : true,
	     			success : function(data) {
	     			     if(data.status=='101'){
	        			     alert(data.msg);
	        			 }else{
	        			    alert(data.msg);
	        			 }
	     	        }
		       })
	    });

	    $('#phoneVali').on('click',function(){
	        var mobile=$('#mobile').val();
	        var memberCode=$('#memberCode').val();
	        var uMobile=$('#uMobile').val();
	        if(mobile==''){
	          alert("请输入手机号");
	          return;
	        }
	        var reg = /^(13|15|18)\d{1}[-]?\d{4}[-]?\d{4}$/;
	       
	        if(!reg.test(mobile)){
	           alert("手机号格式不正确");
	           return;
	        }
	        if(mobile==uMobile){
	          alert("该手机号与当前绑定的手机号一致，请更换");
	          return;
	        }
	        if(mobile!=''){
		        $.ajax({
		     			type : "post",
		     			url : "<%=basePath%>member/account/sendMsg",
		     			data : {mobile:mobile,findType:"byMobile",memberCode:memberCode},
		     			dataType : "json",
		     			async : true,
		     			success : function(data) {
		        			 if(data.status=='101'){
		        			    alert(data.msg);
		        			 }else if(data.status=='1'){
		        			    alert(data.msg);
		        			 }
		     	        }
		       })
	       }
	     });
	     //验证
	     $('#checkM').on('click',function(){
	        var mobile=$('#mobile').val();
	        var code=$('#codeM').val();
	        var hidM=$('#hiddenMobile').val();
	        if(mobile==''){
	          alert("请输入手机号");
	          return;
	        }
	       if(mobile==hidM){
	          alert("当前手机号未发生改变");
	          return;
	        }
	        if(code==''){
	          alert("请输入验证码");
	          return;
	        }
	        if(mobile!=''){
		        $.ajax({
		     			type : "post",
		     			url : "<%=basePath%>member/account/checkCode",
		     			data : {mobile:mobile,type:"byMobile",code:code},
		     			dataType : "json",
		     			async : true,
		     			success : function(data) {
		        			 if(data.status=='101'){
		        			    $('#isChk').val('0');
		        			    alert(data.msg);
		        			 }else if(data.status=='1'){
		        			    alert(data.msg);
		        			    $('#isChk').val('1');
		        			 }
		     	        }
		       })
	       }
	     });
	     $('#checkE').on('click',function(){
	        var email=$('#email').val();
	        var code=$('#codeE').val();
	        var hidE=$('#hiddenEmail').val();
	        if(email==''){
	          alert("请输入邮箱");
	          return;
	        }
	       if(hidE==email){
	          alert("当前邮箱未发生改变");
	          return;
	        }
	        if(code==''){
	          alert("请输入验证码");
	          return;
	        }
	        if(email!=''){
		        $.ajax({
		     			type : "post",
		     			url : "<%=basePath%>member/account/checkCode",
		     			data : {email:email,type:"byEmail",code:code},
		     			dataType : "json",
		     			async : true,
		     			success : function(data) {
		        			 if(data.status=='101'){
		        			    alert(data.msg);
		        			    $('#isChk').val('0');
		        			 }else if(data.status=='1'){
		        			    alert(data.msg);
		        			     $('#isChk').val('1');
		        			 }
		     	        }
		       })
	       }
	     });

	     $("#cutImg").on('click',function(){
	        var x=$('#x').val();
            var y=$('#y').val();
            var w= $('#w').val();
            var h=$('#h').val();
            var url=$("#imgUrl").val();
             var turl=$("#imgUrl").val();
           if(url!=''){
             $.ajax({
	     			type : "post",
	     			url : "<%=basePath%>member/account/cutImg",
	     			data : {x:x,y:y,w:w,h:h,url:url,turl:turl},
	     			dataType : "json",
	     			async : true,
	     			success : function(data) {
	        			 if(data.status=='101'){
	        			    alert(data.msg);
	        			 }else if(data.status=='1'){
	        			    $('#userPic').attr('src',contentPath+url+"?"+ (new Date() -0));
	        			    alert("头像修改成功", function(){
                                //location.href='<%=basePath %>member/account/toEditPage?type=data';
                                location.href='<%=basePath %>member/userinfo/getAllMyMsg';
                            });
	        			 }
	     	        }
		       })
            }
	     })
	     $('#finalSub').on('click',function(){
	        var ischk=$('#isChk').val();
	        var email=$('#email').val();
		    var mobile=$('#mobile').val();
		    var seEmail=$('#hiddenEmail').val();
		    var seMobile=$('#hiddenMobile').val();
		    var name=$('#username').val();
		    if(seMobile==''){
			    if(mobile!=''&&ischk=='0'){
			       alert("请先进行手机验证");
			       return;
			    }
		    }
		    if(seEmail==''){
			    if(email!=''&&ischk=='0'){
			       alert("请先进行邮箱验证");
			       return;
			    }
	        }
	        var reg = /^([\u4e00-\u9fa5]|[a-zA-Z0-9])+$/;
	        if(!(reg.test(name)&&inRange(name,1,10))){
	           alert("昵称为1-10位中文、英文、数字");
	           return;
	        } 
	        document.forms.userForm.submit();
	     })
	     function inRange(obj,min,max){
		   if(obj.length<min||obj.length>max){
		      return false
		   }
		   return true;
		}
	   })
	});
    seajs.use('dateTime', function () {
        $('#datetimepicker').datetimepicker({
            lang: "ch",
            timepicker: false,	
            format: 'Y-m-d',
            formatDate: 'Y-m-d',
			maxDate: 0
        })
    });
    window.jcrop_api = null;
    // 头像裁剪
    seajs.use([
        'jquery',
        'Jcrop/js/jquery.Jcrop.js'
    ], function($) {
        $(function() {
            var boundx, boundy, xsize, ysize, $pimg;
            function initJcrop() {
                var $preview = $('#preview-pane'),
                    $pcnt = $('#preview-pane .preview-container');
                $pimg = $('#preview-pane .preview-container img');

                xsize = $pcnt.width(),
                    ysize = $pcnt.height();
                if (jcrop_api) {
                    jcrop_api.destroy()
                }
                $("#target").Jcrop({
                    onChange: updatePreview,
                    minSize: [120, 120],
                    maxSize: [360, 360],
                    aspectRatio: xsize / ysize,
                    onSelect: function(c){
                        $('.jcrop-holder-wrap').animate({scrollTop: c.y, scrollLeft: c.x})
                    }
                }, function() {
                    // Use the API to get the real image size
                    var bounds = this.getBounds();
                    boundx = bounds[0];
                    boundy = bounds[1];
                    // Store the API in the jcrop_api variable
                    window.jcrop_api = jcrop_api = this;
                    var w = 360,
                        h = 360,
                        x = (boundx - w) / 2,
                        y = (boundy - h) / 2,
                        ox = x + w,
                        oy = y + h;
                    jcrop_api.setSelect([0, 0, ox, oy]);
                });
            }
            window.initJcrop = initJcrop
            initJcrop()

            function updatePreview(c) {
                updateCoords(c)
                if (parseInt(c.w) > 0) {
                    var rx = xsize / c.w;
                    var ry = ysize / c.h;
                    $pimg.css({
                        width: Math.round(rx * boundx) + 'px',
                        height: Math.round(ry * boundy) + 'px',
                        marginLeft: '-' + Math.round(rx * c.x) + 'px',
                        marginTop: '-' + Math.round(ry * c.y) + 'px'
                    });
                }
            };

            function updateCoords(c) {
                $('#x').val(c.x);
                $('#y').val(c.y);
                $('#w').val(c.w);
                $('#h').val(c.h);
            }
        });
    });

    /******************省市联动*********************/
	var cities = {"北京":["北京"],"上海":["上海"],"天津":["天津"],"重庆":["重庆"],"河北省":["石家庄","张家口","承德","秦皇岛","唐山","廊坊","保定","沧州","衡水","邢台","邯郸"],"山西省":["太原","大同","朔州","阳泉","长治","晋城","忻州","吕梁","晋中","临汾","运城"],"辽宁省":["沈阳","朝阳","阜新","铁岭","抚顺","本溪","辽阳","鞍山","丹东","大连","营口","盘锦","锦州","葫芦岛"],"吉林省":["长春","白城","松原","吉林","四平","辽源","通化","白山","延边"],"黑龙江省":["哈尔滨","齐齐哈尔","黑河","大庆","伊春","鹤岗","佳木斯","双鸭山","七台河","鸡西","牡丹江","绥化","大兴安"],"江苏省":["南京","徐州","连云港","宿迁","淮阴","盐城","扬州","泰州","南通","镇江","常州","无锡","苏州"],"浙江省":["杭州","湖州","嘉兴","舟山","宁波","绍兴","金华","台州","温州","丽水"],"安徽省":["合肥","宿州","淮北","阜阳","蚌埠","淮南","滁州","马鞍山","芜湖","铜陵","安庆","黄山","六安","巢湖","池州","宣城"],"福建省":["福州","南平","三明","莆田","泉州","厦门","漳州","龙岩","宁德"],"江西省":["南昌","九江","景德镇","鹰潭","新余","萍乡","赣州","上饶","抚州","宜春","吉安"],"山东省":["济南","聊城","德州","东营","淄博","潍坊","烟台","威海","青岛","日照","临沂","枣庄","济宁","泰安","莱芜","滨州","菏泽"],"河南省":["郑州","三门峡","洛阳","焦作","新乡","鹤壁","安阳","濮阳","开封","商丘","许昌","漯河","平顶山","南阳","信阳","周口","驻马店"],"湖北省":["武汉","十堰","襄攀","荆门","孝感","黄冈","鄂州","黄石","咸宁","荆州","宜昌","恩施","襄樊"],"湖南省":["长沙","张家界","常德","益阳","岳阳","株洲","湘潭","衡阳","郴州","永州","邵阳","怀化","娄底","湘西"],"广东省":["广州","清远","韶关","河源","梅州","潮州","汕头","揭阳","汕尾","惠州","东莞","深圳","珠海","江门","佛山","肇庆","云浮","阳江","茂名","湛江"],"海南省":["海口","三亚"],"四川省":["成都","广元","绵阳","德阳","南充","广安","遂宁","内江","乐山","自贡","泸州","宜宾","攀枝花","巴中","达川","资阳","眉山","雅安","阿坝","甘孜","凉山"],"贵州省":["贵阳","六盘水","遵义","毕节","铜仁","安顺","黔东南","黔南","黔西南"],"云南省":["昆明","曲靖","玉溪","丽江","昭通","思茅","临沧","保山","德宏","怒江","迪庆","大理","楚雄","红河","文山","西双版纳"],"陕西省":["西安","延安","铜川","渭南","咸阳","宝鸡","汉中","榆林","商洛","安康"],"甘肃省":["兰州","嘉峪关","金昌","白银","天水","酒泉","张掖","武威","庆阳","平凉","定西","陇南","临夏","甘南"],"青海省":["西宁","海东","西宁","海北","海南","黄南","果洛","玉树","海西"],"内蒙古":["呼和浩特","包头","乌海","赤峰","呼伦贝尔盟","兴安盟","哲里木盟","锡林郭勒盟","乌兰察布盟","鄂尔多斯","巴彦淖尔盟","阿拉善盟"],"广西":["南宁","桂林","柳州","梧州","贵港","玉林","钦州","北海","防城港","南宁","百色","河池","柳州","贺州"],"西藏":["拉萨","那曲","昌都","林芝","山南","日喀则","阿里"],"宁夏":["银川","石嘴山","吴忠","固原"],"新疆":["乌鲁木齐","克拉玛依","喀什","阿克苏","和田","吐鲁番","哈密","博尔塔拉","昌吉","巴音郭楞","伊犁","塔城","阿勒泰"],"香港":["香港"],"澳门":["澳门"], "台湾":["台北","台南","其他"]};
</script>

</body>
</html>