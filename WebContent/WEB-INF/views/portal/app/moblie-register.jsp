<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=0.8,maximum-scale=1.5"/>
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏"/>
    <meta name="description"
          content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！"/>
    <title>天上西藏-感受西藏魅力，体验西藏生活-天上西藏官网</title>
    <%@include file="/common-html/frontcommon.jsp" %>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxPortal}/assets/css/register/moblie-register.css"/>
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
        seajs.config({
            alias: {
                "jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery",
                "strength": "strength/strength.js"
            }
        });
    </script>
    <script type="text/javascript">
        seajs.use(['jquery'], function ($) {
        	//alert("${back_url}");
            $(function () {
            	if(${login_state eq 'success'}){
            		alert("注册成功");
            		if(${not empty back_url}){
	            		window.location.href="${back_url}";
            		}else{
            			window.location.href="${ctx}activity";
            		}
            		return;
            	}
                if (${error=='-1'}) {
                    alert("电话号码不能为空");
                }
                if (${error=='-2'}) {
                    alert("昵称不能为空");
                }
                if (${error=='-3'}) {
                    alert("密码不能为空");
                }
                if (${error=='-4'}) {
                    alert("验证码不能为空");
                }
                if (${error=='-5'}) {
                    alert("该手机号已经被注册");
                }
                if (${error=='-6'}) {
                    alert("验证码已经失效");
                }
                if (${error=='-7'}) {
                    alert("验证码错误");
                }
                //if(${error=='-11'}){
                //   alert("该昵称已经被使用");
                //}
                if (${error=='-12'}) {
                    alert("昵称包含敏感词");
                }
                if (${regSucc=='1'}) {
                    $('#reg-page').hide();
                    $("#success-gage").show();
                }
                if (${regSucc=='0'}) {
                    alert("数据异常，注册失败");
                }
                if (${overTime=='1'}) {
                    alert("验证超时");
                }
                $('.validate-btn').on('click', function () {
                    var mobile = $('#mobile').val();
                    var $this = $(this), $db = $this.data('db');

                    if ($db) {
                        return false;
                    }
                    if (mobile == '') {
                        alert("请输入手机号");
                        return;
                    }
                    $.ajax({
                        url: "sendValidate",
                        dataType: "json",
                        type: "post",
                        data: {mobile: mobile},
                        async: true,
                        success: function (data) {
                            alert(data.msg);
                            if (data.data != -1) {
                                $this.data('db', true)
                                var count = 60;
                                _timer($this, count)
                            }
                        }
                    });
                })
            })
        })
        //判断手机是否重复
        function isRepeat() {
        	var mobile = $("#mobile").val();
        	 if ($db) {
                 return false;
             }
             if (mobile == '') {
                 alert("请输入手机号");
                 return;
             }
            if (mobile != '') {
                $.ajax({
                    url: "isMobileRepeat",
                    dataType: "json",
                    type: "post",
                    async: true,
                    data: {mobile: mobile},
                    success: function (data) {
                        if (data.data != '1')
                            alert(data.msg);
                    }
                });
            }
        }
        //判断邮箱是否重复
        function isEmailRepeat(email) {
            if (email != '') {
                $.ajax({
                    url: "isEmailRepeat",
                    dataType: "json",
                    type: "post",
                    data: {email: email},
                    success: function (data) {
                        if (data.data == '1')
                            alert("邮箱已经被注册");
                    }
                });
            }
        }
        //判断昵称是否重复
        function isNameRepeat(name) {
            if (name != '') {
                $.ajax({
                    url: "isNikeRepeat",
                    dataType: "json",
                    type: "post",
                    data: {name: name},
                    async: false,
                    success: function (data) {
                        if (data.data == '1')
                            alert("该昵称已经被使用");
                    }
                });
            }
        }
        //发短信验证手机号
        function validat() {
            var mobile = $('#mobile').val();
            var $this = $(this), $db = $this.data('db')

            if ($db) {
                return false;
            }
            if (mobile == '') {
                alert("请输入手机号");
                return;
            }
            $.ajax({
                url: "sendValidate",
                dataType: "json",
                type: "post",
                data: {mobile: mobile},
                async: true,
                success: function (data) {
                    alert(data.msg);
                    $this.data('db', true)
                    var count = 60;
                    _timer($this, count)
                }
            });
        }
        function _timer(el, countdown) {
            --countdown
            el.text(countdown + ' 秒后重新获取')
            if (countdown && countdown < 60) {
                setTimeout(function () {
                    _timer(el, countdown)
                }, 1000)
            }
            if (countdown < 1) {
                el.text('获取验证码').data('db', false)
            }
        }
        //提交表单
        function submi(obj) {

            if (obj == 'res') {
                $('#resend').val('1');
            } else {
                $('#resend').val('0');
            }
            var type = $('#type').val();

            if (type == 'byPhone') {
                var mobile = $('#mobile').val();
                var vali = $('#vali').val();
                var name = $('#name').val();
                var password = $('#password').val();
                var confirmpwd = $('#confirmpwd').val();

                if (mobile == '') {
                    alert("请输入手机号码");
                    return;
                }
                var mreg = /^(13|15|18)\d{1}[-]?\d{4}[-]?\d{4}$/;
                if (!mreg.test(mobile)) {
                    alert("手机号格式不正确");
                    return;
                }
                if (vali == '') {
                    alert("请输入验证码");
                    return;
                }
                if (name == '') {
                    alert("请输入昵称");
                    return;
                }
                var reg = /^([\u4e00-\u9fa5]|[a-zA-Z0-9])+$/;
                if (!reg.test(name)) {
                    alert("昵称为中文、数字、字母");
                    return;
                }
                if (name.length < 1 || name.length > 10) {
                    alert("昵称为1-10位字符");
                    return;
                }
                if (password == '') {
                    alert("请输入密码");
                    return;
                }
                if (password.length < 6 || password.length > 16) {
                    alert("密码为6-16位字符");
                    return;
                }
                if (confirmpwd == '') {
                    alert("确认密码不能为空");
                    return;
                }
                if (password != confirmpwd) {
                    alert("两次录入的密码不一致");
                    return;
                }

                document.forms.mobileRegister.submit();
                // $("#mobileRegister").submit();
            }
            if (type == 'byMail') {
                var email = $('#email').val();
                var name = $('#ename').val();
                var password = $('#epwd').val();
                var confirmpwd = $('#econfirmpwd').val();
                if (email == '') {
                    alert("请输入邮箱");
                    return;
                }
                var thisRegExp = /^([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
                if (!thisRegExp.test(email)) {
                    alert("邮箱格式不正确");
                    return;
                }
                if (name == '') {
                    alert("请输入昵称");
                    return;
                }
                var reg = /^([\u4e00-\u9fa5]|[a-zA-Z0-9])+$/;
                if (!reg.test(name)) {
                    alert("昵称为中文、数字、字母");
                    return;
                }
                if (name.length < 1 || name.length > 10) {
                    alert("昵称为1-10位字符");
                    return;
                }
                if (password == '') {
                    alert("请输入密码");
                    return;
                }
                if (password.length < 6 || password.length > 16) {
                    alert("密码为6-16位字符");
                    return;
                }
                if (password != confirmpwd) {
                    alert("两次录入的密码不一致");
                    return;
                }
                if (!$('#chkBox').prop('checked')) {
                    alert("您未同意用户协议");
                    return;
                }
                sendEmail($("#email").val(), $("#ename").val(), $("#epwd").val());
            }
        }
        //发送邮件
        function sendEmail(_email, _name, _password) {

            $.ajax({
                url: "memberRegistByEmail",
                dataType: "json",
                type: "post",
                data: {email: _email, name: _name, password: _password, resend: $('#resend').val()},
                async: false,
                success: function (data) {
                    if (data.data == "发送成功") {
                        $("#reg-page").css({display: "none"});
                        $("#mail-send").css({display: "block"});
                        $("#linkEmail").html(_email);
                    }
                    if (data.data == '-110') {
                        alert("该邮箱已经被使用");
                    }
                    if (data.data == '-112') {
                        alert("昵称包含敏感词");
                    }
                }
            });
        }
        function chenckPwd(obj) {
            if (obj == 'phone') {
                var p = $('#password').val();
                var c = $('#confirmpwd').val();
                if(c!=""){
	                if (p != c) {
	                    alert("两次录入的密码不一致");
	                }
                }
            }
            if (obj == 'email') {
                var p = $('#epwd').val();
                var c = $('#econfirmpwd').val();
                if(c!=""){
	                if (p != c) {
	                    alert("两次录入的密码不一致");
	                }
                }
            }
        }
        //跳转至相应邮箱
        function goEmail(email) {
            if (email.indexOf("@qq.com") > 0) {
                url = "http://mail.qq.com";
            } else if (email.indexOf("@163.com") > 0) {
                url = "http://mail.163.com";
            } else if (email.indexOf("@sina.com") > 0 || email.indexOf("@sina.cn") > 0) {
                url = "https://mail.sina.com.cn";
            } else if (email.indexOf("@sohu.com") > 0) {
                url = "http://login.mail.sohu.com";
            } else if (email.indexOf("@rimionline.com") > 0) {
                url = "http://tel.exmail.qq.com/login";
            } else {
            	var domain = email.substr(email.indexOf('@') + 1);
            	var url = 'http://mail.' + domain + '/';
            }
            window.open(url);
        }
    </script>
    <style>
        .footer {
            margin-top: 0 !important;
        }
    </style>
</head>
<body>
<div>
    <div id="reg-page" class="main">
        <div class="clearfix header">手机注册</div>
        <div class="clearfix content">
            <input type="hidden" name="type" value="byPhone" id="type"/>
            <form name="mobileRegister" action="memberRegistByMobile" method="post">
            	<input type="hidden" name="back_url" value="${back_url}">
                <div class="reg-interface">
                    <div class="form-group">
                        <label>手机号</label>
                        <input class="form-control" placeholder="请输入您的手机号" maxlength="11" value="${mobile }" type="text"
                               name="mobile" id="mobile" onblur="isRepeat()"/>
                    </div>
                    <div class="form-group clearfix">
                        <label>手机验证</label>
                        <input class="form-control validate" placeholder="请输入验证码" maxlength="6" type="text"
                               name="validate" id="vali"/>
                        <div class="validate-btn">获取验证码</div>
                    </div>
                    <div class="form-group">
                        <label>昵称</label>
                        <input class="form-control" placeholder="${nameHolder }" maxlength="10" value="${nameHolder }"
                               id="name" type="text" name="name"/>
                    </div>
                    <div class="form-group reg-psw">
                        <label>密码</label>
                        <input class="form-control" placeholder="请输入您的密码" type="password" maxlength="16" id="password"
                               name="password"/>
                    </div>
                    <div class="form-group">
                        <label>确认密码</label>
                        <input class="form-control" placeholder="请再次输入您的密码" type="password" maxlength="16"
                               id="confirmpwd" name="confirmpwd" onblur="chenckPwd('phone')"/>
                    </div>
                </div>
            </form>
            <div class="reg-affirm clearfix">
                <div id="register" class="immediately" onclick="submi()">立即注册</div>
                <a href="<%=basePath %>portal/clientLog/loginPage" class="possess" target="_blank">已有账号登录</a>
                <span id="user-agreement" class="agreement">注册视为同意<a
                        href="<%=basePath %>portal/registerController/toLicence">《天上西藏用户协议》</a></span>
            </div>
        </div>
    </div>
</div>
<script>
    seajs.use('strength', function (avalon) {
        $(function () {
        	$('#mail').trigger('click');
            $(document).on('focus', '#password,#epwd', function(){
            	initStrength(this)
            })
        	function initStrength(el){
        		$(el).strength({
                    callback: function (total) {
                        if (!$(this).siblings('.strength-msg').length) {
                            $(this).after('<div class="strength-msg"></div>')
                        }
                        var $strength = $(this).siblings('.strength-msg')
                        switch (total) {
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
                                $strength.removeClass('veryweak');
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
        	}
        })
    });
</script>
<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
</body>
</html>
