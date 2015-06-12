<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jstl/fmt_rt" %>
<%
	String path = request.getContextPath();
String port = ":" + request.getServerPort();
if ("80".equals(""+request.getServerPort())) {
	port = "";
}
String basePath = request.getScheme() + "://"
		+ request.getServerName() + port + path + "/";
	request.setAttribute("ctx",basePath);
	request.setAttribute("ctxManage",basePath+"/manage/");
	request.setAttribute("ctxPortal",basePath+"/portal/"); //xiangwq
	request.setAttribute("ctxMRead", basePath+"manage/html/read/");//csl
	request.setAttribute("ctxApp",basePath+"portal/app/"); //
	request.setAttribute("root","/");
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge"/>
    <title>系统设置-会员管理-<c:if test="${flag eq 'save'}">新增会员</c:if><c:if test="${flag eq 'update'}">修改会员</c:if></title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
        <link rel="stylesheet" href="${ctxManage}/resources/plugin/datepicker.css" />
    <style type="text/css">
		.avatar { position: absolute; left: 456px; top: 46px;}
		.avatar img { width: 120px; height: 120px; display: block;}
		.pickfiles{
			display: inline-block;
			vertical-align: middle;
		}
		.webuploader-pick{
			background-color: #fbfbfb;
			border: 1px solid #9c9c9c;
			border-radius: 3px;
			text-align: center;
			font: normal 14px/32px "微软雅黑";
			color: #8c8c8c;
		}
		.webuploader-pick-hover{
		    background: none repeat scroll 0 0 #817171;
   			color: #e3e3e3;
		}
	</style>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <script type="text/javascript">
	    var contextPath='<%=basePath%>';
    </script>

</head>

<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
    		<span><a>系统设置</a> -</span>
    		<span><a href="${ctx }web/member/list" target="_self">会员管理</a> -</span>
    		<c:if test="${flag eq 'save'}"><span><a>新增会员</a></span></c:if><c:if test="${flag eq 'update'}"><span><a>修改会员</a></span></c:if>
    </div>

    <!-- 模块管理 { -->
    <form id="member-add-form" method="post"  <c:if test="${flag eq 'save'}">action="${ctx}/web/member/save"</c:if> 
		                       <c:if test="${flag eq 'update'}">action="${ctx}/web/member/update"</c:if>  enctype="multipart/form-data" autocomplete="off">
        <!-- 会员信息 { -->
		<div id="member-add" class="filament_solid_ddd pos-rela mt30">
            <input type="hidden" name="code" value="${code }"/>
			<div class="formLine mt20">
				<label>性别:</label>
				<input id="male" name="sex" type="radio" value="1" class="ml10" checked /><label for="male" class="w-auto pointer ml10">男</label>
				<input id="female" name="sex" type="radio" value="0" <c:if test="${sex eq '0'}">checked="checked"</c:if>  class="ml25" /><label for="female" class="w-auto pointer ml10">女</label>
			</div>

			<div class="formLine mt30 mb40">
				<label>头像:</label>
				<div id="btnuploadfile" class="pickfiles imgNode">请上传图片</div>
                <span class="txt-suggest ml5">推荐尺寸：240 * 240</span>
            </div>
			<div class="avatar">
				<img src="<%=basePath %>${pic }" width="240" height="240" id="previewimage" alt="用户头像" />
				<input type="hidden" class="url" name="pic" value="${pic }" id="hiddenimage" />
			</div>
			
			<div class="formLine">
				<label>邮箱:</label>
				<input id="email" type="text" name="email" maxlength="100" <c:if test="${flag eq 'update'}">onblur="checkUniqueM(this,'email','${code }');"</c:if><c:if test="${flag eq 'save'}">onblur="checkUnique(this,'email');"</c:if> value="${email }" class="w-320" />
			</div>
			
			<div class="formLine">
				<label>手机:</label>
				<input id="mobile" name="phone" maxlength="11" <c:if test="${flag eq 'update'}">onblur="checkUniqueM(this,'phone','${code }');"</c:if> <c:if test="${flag eq 'save'}">onblur="checkUnique(this,'phone');"</c:if> onkeyup="value=value.replace(/[^\d]/g,'')" value="${phone }" type="text" class="w-320" />
			</div>
			
			<div class="formLine">
				<label>昵称:</label>
				<input id="niceName" name="username" value="${username }"  maxlength="10" type="text" class="w-320" />
				<span class="reqItem">*</span>
			</div>
			
			<div class="formLine">
				<label>密码:</label>
				<input id="pwd" type="text" name="password" class="w-320" onfocus="this.type='password'" autocomplete="off" />
				<c:if test="${flag eq 'save'}"><span class="reqItem">*</span></c:if>
			</div>
			
			<div class="formLine">
				<label>确认密码:</label>
				<input id="repwd" type="text" class="w-320" onfocus="this.type='password'" autocomplete="off" />
				<c:if test="${flag eq 'save'}"><span class="reqItem">*</span></c:if>
			</div>
			
			<div class="formLine">
				<label>生日:</label>
				<input id="birthday" name="birthday" value="<c:if test="${not empty birthday }"><fmt:formatDate value="${birthday }" pattern="yyyy-MM-dd"/></c:if>" type="text" class="w-320" />
			</div>
			
			<div class="formLine">
				<label>所在地:</label>
				<div id="provinceDiv" class="select-base">
					<input id="provinceVal" name="province" type="hidden" value="<c:if test="${not empty province}">${province}</c:if>" />
					<i class="w-140" id="chkPro"><c:if test="${flag eq 'save'}">请选择所在省份</c:if><c:if test="${not empty province}">${province}</c:if></i>
					<dl id="province" >
						<dt name="" onclick="set_city(this);">请选择所在省份</dt>
						<dt onclick="set_city(this);" name="台湾">台湾</dt>
						<dt onclick="set_city(this);" name="北京">北京</dt>
						<dt onclick="set_city(this);" name="上海">上海</dt>
						<dt onclick="set_city(this);" name="天津">天津</dt>
						<dt onclick="set_city(this);" name="重庆">重庆</dt>
						<dt onclick="set_city(this);" name="河北省">河北省</dt>
						<dt onclick="set_city(this);" name="山西省">山西省</dt>
						<dt onclick="set_city(this);" name="辽宁省">辽宁省</dt>
						<dt onclick="set_city(this);" name="吉林省">吉林省</dt>
						<dt onclick="set_city(this);" name="黑龙江省">黑龙江省</dt>
						<dt onclick="set_city(this);" name="江苏省">江苏省</dt>
						<dt onclick="set_city(this);" name="浙江省">浙江省</dt>
						<dt onclick="set_city(this);" name="安徽省">安徽省</dt>
						<dt onclick="set_city(this);" name="福建省">福建省</dt>
						<dt onclick="set_city(this);" name="江西省">江西省</dt>
						<dt onclick="set_city(this);" name="山东省">山东省</dt>
						<dt onclick="set_city(this);" name="河南省">河南省</dt>
						<dt onclick="set_city(this);" name="湖北省">湖北省</dt>
						<dt onclick="set_city(this);" name="湖南省">湖南省</dt>
						<dt onclick="set_city(this);" name="广东省">广东省</dt>
						<dt onclick="set_city(this);" name="海南省">海南省</dt>
						<dt onclick="set_city(this);" name="四川省">四川省</dt>
						<dt onclick="set_city(this);" name="贵州省">贵州省</dt>
						<dt onclick="set_city(this);" name="云南省">云南省</dt>
						<dt onclick="set_city(this);" name="陕西省">陕西省</dt>
						<dt onclick="set_city(this);" name="甘肃省">甘肃省</dt>
						<dt onclick="set_city(this);" name="青海省">青海省</dt>
						<dt onclick="set_city(this);" name="内蒙古">内蒙古</dt>
						<dt onclick="set_city(this);" name="广西">广西</dt>
						<dt onclick="set_city(this);" name="西藏">西藏</dt>
						<dt onclick="set_city(this);" name="宁夏">宁夏</dt>
						<dt onclick="set_city(this);" name="新疆">新疆</dt>
						<dt onclick="set_city(this);" name="香港">香港</dt>
						<dt onclick="set_city(this);" name="澳门">澳门</dt>
					</dl>
				</div>
				<div id="city" class="select-base ml5">
					<input id="cityVal" name="city" type="hidden" value="<c:if test="${not empty city}">${city}</c:if>" />
					<i class="w-140" id="chkCity"><c:if test="${flag eq 'save'}">请选择所在城市</c:if><c:if test="${not empty city}">${city}</c:if></i>
					<dl>
						<dt id="cityDt" name="">请选择所在城市</dt>
					</dl>
				</div>
				<!-- <span class="reqItem">*</span>-->
			</div>
			
			<div class="formLine">
				<label>积分:</label>
				<input id="score" name="score" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="7" value="${score }" type="text" class="w-320" />
			</div>
			
			<!-- 按钮 -->
			<div class="ml140 mt40 mb30">
				<a class="btn-sure sureAdd" href="javascript:void(0)">保 存</a>
				<a class="btn-sure ml20 mr40" onclick="javascript:back()" type="button">取 消</a>
			</div>

		</div><!-- } 会员信息 -->
    </form>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-form.js"></script>
<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
<script src="${ctxManage}webuploader/webuploader.js"></script>
 <script src="${ctxManage}/webuploader/upload.js" type="text/javascript"></script>
 <script type="text/javascript" src="${ctxManage}webuploader/checkflash.js"></script>
<!--[if ie]>
<script type="text/javascript">
	$(function() {
		// 修改text的密码框为password型
		$("#pwd,#repwd").remove();
		$(".formLine").eq(5).children("label").after('&nbsp;<input id="pwd" type="password" name="password" class="w-320" autocomplete="off" />');
		$(".formLine").eq(6).children("label").after('&nbsp;<input id="repwd" type="password" class="w-320" autocomplete="off" />');
	});
</script>
<![endif]-->
<script type="text/javascript">
		// 运行时间选择控件
		datePicker("birthday");
	</script>
	<script type="text/javascript">
		function setUserPic(){
			var hiddenVal = $("#hiddenimage").val();
			var isSet = hiddenVal=="" || hiddenVal=="/portal/static/default/male.jpg" || hiddenVal=="/portal/static/default/female.jpg"
			
			if(isSet){
				var sex = $("input[name='sex']:checked").val();
				var pic = "";
				if(sex==1){
					pic = "/portal/static/default/male.jpg";
				}else{
					pic = "/portal/static/default/female.jpg";
				}
				$("#previewimage").attr("src", '${ctx}' + pic);
				$("#hiddenimage").val(pic);
			}
		}
		$("input[name='sex']").click(function(){
			setUserPic();
		});
		$(setUserPic());
	</script>
	<script type="text/javascript">
		$(function(){
		  if(${error=='-1'}){
		     msgBox("请选择性别！", "erro", 2600);
		  }
		  if(${error=='-2'}){
		     msgBox("请填写手机号或邮箱！", "erro", 2600);
		  }
		 /* if(${error=='-3'}){
		     msgBox("手机号码不能为空！", "erro", 2600);
		  }*/
		  if(${error=='-4'}){
		     msgBox("昵称不能为空！", "erro", 2600);
		  }
		  if(${error=='-5'}){
		     msgBox("密码不能为空！", "erro", 2600);
		  }
		 /* if(${error=='-6'}){
		     msgBox("生日不能为空！", "erro", 2600);
		  }
		  if(${error=='-7'}){
		     msgBox("请选择所在省！", "erro", 2600);
		  }
		  if(${error=='-8'}){
		     msgBox("请选择所在市！", "erro", 2600);
		  }
		  if(${error=='-9'}){
		     msgBox("积分不能为空！", "erro", 2600);
		  }*/
		  if(${phoneExsist=='1'}){
		     msgBox("电话号码不能重复！", "erro", 2600);
		  }
		  if(${emailExsist=='1'}){
		     msgBox("邮箱不能重复！", "erro", 2600);
		  }
		   if(${nameExsist=='1'}){
		     msgBox("昵称不能重复！", "erro", 2600);
		  }
		   if(${fileError=='1'}){
		     msgBox("文件上传错误！", "erro", 2600);
		  }
		  /**** 数据验证 ****/
	
			// 邮箱
			$("#email").blur(function() {
				var this_val = $(this).val(),
					reg_val = $.VLDTOR.IsEmail(this_val);
				modifErrMesg("#email,#mobile");
				// 验证信息方法
				valid_txtBox(this, reg_val || this_val == "", "邮箱格式不正确，请检查", "right");
			});
			
			// 手机
			$("#mobile").blur(function() {
				var this_val = $(this).val(),
					reg_val = $.VLDTOR.IsMobile(this_val);
				modifErrMesg("#email,#mobile");
				// 验证信息方法
				valid_txtBox(this, reg_val || this_val == "", "格式只能为11位的手机号码", "right");
			});
			
			// 昵称
			$("#niceName").blur(function() {
				var this_val = $(this).val(),
					reg_val = $.VLDTOR.IsEnCnNum(this_val),
					val_range =  inputRange(this, 1, 10);
				// 验证信息方法
				valid_txtBox(this, reg_val && val_range, "只能为1-10位的中、英文和数字", "right");
			});
			
			// 密码
			$("#pwd").blur(function() {
				var this_val = $(this).val(),
					repwd_val = $("#repwd").val(),
					reg_val = $.VLDTOR.IsPWD(this_val),
					val_range =  inputRange(this, 6, 16);
				if(repwd_val != "") {
				   if(${flag=='save'}){
					modifErrMesg("#repwd");
				   }else if(${flag=='update'}){
				     removeErrMesg("#repwd");
				   }
					valid_txtBox("#repwd", this_val == repwd_val, "两次密码输入不一致，请检查", "right");
				}
				valid_txtBox(this, reg_val && val_range, "密码只能为6-16位的非中文字符", "right");
			});
			
			// 确认密码
			$("#repwd").blur(function() {
				var this_val = $(this).val(),
					pwd_val = $("#pwd").val(),
					pwd_val_len = pwd_val.length;
				if(pwd_val_len == "") {
					valid_txtBox(this, false, "请先输入密码", "right");
					return;
				}if(this_val!=''&&pwd_val!=''){
				  valid_txtBox(this, this_val == pwd_val, "两次密码输入不一致，请检查", "right");
				}
			});
			
			// 生日
			$("#birthday").blur(function() {
				var this_val = $(this).val(),
					reg_val = $.VLDTOR.IsDate(this_val);
				// 验证信息方法
				valid_txtBox_create(this, reg_val || this_val == "", "日期格式不正确（建议使用日期控件进行选择）", "right");
			});
			
			
			// 积分
			$("#score").blur(function() {
				var this_val = $(this).val(),
					reg_val = $.VLDTOR.IsNum(this_val);
				// 验证信息方法
				valid_txtBox_create(this, reg_val || this_val == "", "只能为正整数（选填）", "right");
			});
			/**** 新增按钮验证 ****/
			$(".sureAdd").click(function() {
			   // 获取邮箱和手机的输入情况（必填其中一项）
				var email_val = $("#email").val(),
					mobile_val = $("#mobile").val();
				if(email_val == "" && mobile_val == "") {
		           // valid_txtBox("#email", false, "邮箱和手机至少需要填写一项", "right");
					//valid_txtBox("#mobile", false, "邮箱和手机至少需要填写一项", "right");
					 msgBox("邮箱和手机至少需要填写一项", "erro", 2600);
					 return;
				}
				// 邮箱
				if(email_val!='') {
					var reg_val = $.VLDTOR.IsEmail(email_val);
					// 验证信息方法
					//valid_txtBox_create("#email", reg_val, "邮箱格式不正确，请检查", "right");
					if(!reg_val){
					  msgBox("邮箱格式不正确", "erro", 2600);
					 return;
					}
				}
				
				// 手机
				if(mobile_val!='') {
					var reg_val = $.VLDTOR.IsMobile(mobile_val);
					// 验证信息方法
					//valid_txtBox_create("#mobile", reg_val, "格式只能为11位的手机号码", "right");
					if(!reg_val){
					  msgBox("手机号格式不正确", "erro", 2600);
					  return;
					}
				}
				var pwd=$("#pwd").val(),
				    repwd=$("#repwd").val();
				if(pwd==''){
				  valid_txtBox("#pwd", false, "请输入密码", "right");
				}if(repwd==''){
				  valid_txtBox("#repwd", false, "请输入确认密码", "right");
				}
				// 失焦验证
				$("#email, #mobile, #niceName, #pwd, #repwd, #birthday, #score").blur();
				
				
				
				if(pwd!=''){
				    var reg_val = $.VLDTOR.IsPWD(pwd),
					    val_range =  inRange(pwd, 6, 16);
				   if(!(reg_val && val_range)){
					 valid_txtBox_create("#pwd", reg_val && val_range, "密码只能为6-16位的非中文字符", "right");
					 return;
					}
				    if(repwd==''){
				       msgBox("请填写确认密码", "erro", 2600);
					   return;
				    }
				     valid_txtBox_create("#repwd", repwd == pwd, "两次密码输入不一致，请检查", "right");
				}
				// 是否含有错误信息
				var hasErr = $(".errMesg").length > 0;
				
				// 含有验证不通过的项
				if(hasErr) {
					msgBox("您填写的信息有误，请检查！", "erro");
					return;
				}
				// 验证通过提交表单
				else {
					//msgBox("新建成功！", "pass");
					$("#member-add-form").submit();
				}
			});
		})
		/*function checkPass(obj){
		   var pwd=obj.value;
		   if(pwd.length<6||pwd.length>16){
	          msgBox("密码为6-16位字符", "erro", 2600);
	          $("#pwd").focus();
	          return false;
	       }
		}
		function checkMobile(){
		    var name=$('#niceName').val();
		    var sMobile = $('#mobile').val();
		    var email=$('#email').val();
		    var pwd=$('#pwd').val();
		    var repwd=$('#repwd').val();
		    if(sMobile==''&&email==''){
		        msgBox("请填写手机号或邮箱", "erro", 2600);
		        $("#email").focus();
		        return false;
		    }
		    //var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
		    if(email!=''){
		        var reg_val = $.VLDTOR.IsEmail(email);
			    if(!reg_val){
			       msgBox("请输入正确的邮箱", "erro", 2600);
			        $("#email").focus();
			        return false;
			    }
		    }
		    if(sMobile!=''){
			   var reg_val = $.VLDTOR.IsMobile(sMobile);
			   if(!reg_val){
			      msgBox("请填写正确格式的手机号", "erro", 2600);
			      $('#mobile').focus();
				  return false;
			   }
		    }
		    if(name==''){
		       msgBox("请填写昵称", "erro", 2600);
		        $("#niceName").focus();
		        return false;
		    }
		    //var namereg=/^[A-Za-z0-9\u4e00-\u9fa5]+$/;
		    if(name!=''){
		       var reg_val = $.VLDTOR.IsEnCnNum(name),
			       val_range = inRange(name, 1, 10);
			   if(!reg_val||!val_range){
			       msgBox("昵称为1-10位中文、数字、字母", "erro", 2600);
			        $("#niceName").focus();
			        return false;
			    }
		    }
		    if(${flag=='save'}){
			    if(pwd==''){
			        msgBox("请填写密码", "erro", 2600);
			        $('#pwd').focus();
			        return false;
			    }
			    if(repwd==''){
			        msgBox("请填写确认密码", "erro", 2600);
			        $('#repwd').focus();
			        return false;
			    }
		    }
		    if(pwd!=''){
		      if(pwd.length<6||pwd.length>16){
		          msgBox("密码为6-16位字符", "erro", 2600);
		          $("#pwd").focus();
		          return false;
		      }
		      if(repwd==''){
		        msgBox("请填写确认密码", "erro", 2600);
		        $("#repwd").focus();
		        return false;
		      }
		      if(pwd!=repwd){
		        msgBox("两次录入的密码不一致", "erro", 2600);
		        $("#repwd").focus();
		        return false;
		      }
		    }
		    $('#member-add-form').submit();
		   
		} */
		function inRange(obj,min,max){
		   if(obj.length<min||obj.length>max){
		      return false
		   }
		   return true;
		}
		function checkUnique(obj,type){
		   var val=obj.value;
		   if(type=='email'){
		      if(val!=''){
			       $.ajax({
		     			type : "post",
		     			url : "<%=basePath%>/web/member/checkUnique",
		     			data : {email:val},
		     			dataType : "json",
		     			async : true,
		     			success : function(data) {
		     			     if(data.data=='1'){
		        			      msgBox("该邮箱已经被使用", "erro", 2600);
		        			 }
		     	        }
				     })
			  }
		   }
		   if(type=='phone'){
		     if(val!=''){
		       $.ajax({
		     			type : "post",
		     			url : "<%=basePath%>/web/member/checkUnique",
		     			data : {phone:val},
		     			dataType : "json",
		     			async : true,
		     			success : function(data) {
		     			     if(data.data=='1'){
		        			      msgBox("该手机号已经被使用", "erro", 2600);
		        			 }
		     	        }
			     })
			  }
		   }
		   if(type=='name'){
		     if(val!=''){
		       $.ajax({
		     			type : "post",
		     			url : "<%=basePath%>/web/member/checkUnique",
		     			data : {name:val},
		     			dataType : "json",
		     			async : true,
		     			success : function(data) {
		     			     if(data.data=='1'){
		        			      msgBox("该昵称已经被使用", "erro", 2600);
		        			 }
		     	        }
			     })
			  }
		   }
		}
	   function checkUniqueM(obj,type,code){
		   var val=obj.value;
		   if(type=='email'){
		      if(val!=''){
			       $.ajax({
		     			type : "post",
		     			url : "<%=basePath%>/web/member/checkUniqueM",
		     			data : {email:val,code:code},
		     			dataType : "json",
		     			async : true,
		     			success : function(data) {
		     			     if(data.data=='1'){
		        			      msgBox("该邮箱已经被使用", "erro", 2600);
		        			 }
		     	        }
				     })
			  }
		   }
		   if(type=='phone'){
		     if(val!=''){
		       $.ajax({
		     			type : "post",
		     			url : "<%=basePath%>/web/member/checkUniqueM",
		     			data : {phone:val,code:code},
		     			dataType : "json",
		     			async : true,
		     			success : function(data) {
		     			     if(data.data=='1'){
		        			      msgBox("该手机号已经被使用", "erro", 2600);
		        			 }
		     	        }
			     })
			  }
		   }
		   if(type=='name'){
		     if(val!=''){
		       $.ajax({
		     			type : "post",
		     			url : "<%=basePath%>/web/member/checkUniqueM",
		     			data : {name:val,code:code},
		     			dataType : "json",
		     			async : true,
		     			success : function(data) {
		     			     if(data.data=='1'){
		        			      msgBox("该昵称已经被使用", "erro", 2600);
		        			 }
		     	        }
			     })
			  }
		   }
		}
		/******************省市联动*********************/
		cities = new Object();
			cities['台湾']=new Array('台北','台南','其他');
			cities['北京']=new Array('北京');
			cities['上海']=new Array('上海');
			cities['天津']=new Array('天津');
			cities['重庆']=new Array('重庆');
			cities['河北省']=new Array('石家庄', '张家口', '承德', '秦皇岛', '唐山', '廊坊', '保定', '沧州', '衡水', '邢台', '邯郸');
			cities['山西省']=new Array('太原', '大同', '朔州', '阳泉', '长治', '晋城', '忻州', '吕梁', '晋中', '临汾', '运城');
			cities['辽宁省']=new Array('沈阳', '朝阳', '阜新', '铁岭', '抚顺', '本溪', '辽阳', '鞍山', '丹东', '大连', '营口', '盘锦', '锦州', '葫芦岛');
			cities['吉林省']=new Array('长春', '白城', '松原', '吉林', '四平', '辽源', '通化', '白山', '延边');
			cities['黑龙江省']=new Array('哈尔滨', '齐齐哈尔', '黑河', '大庆', '伊春', '鹤岗', '佳木斯', '双鸭山', '七台河', '鸡西', '牡丹江', '绥化', '大兴安');
			cities['江苏省']=new Array('南京', '徐州', '连云港', '宿迁', '淮阴', '盐城', '扬州', '泰州', '南通', '镇江', '常州', '无锡', '苏州');
			cities['浙江省']=new Array('杭州', '湖州', '嘉兴', '舟山', '宁波', '绍兴', '金华', '台州', '温州', '丽水');
			cities['安徽省']=new Array('合肥', '宿州', '淮北', '阜阳', '蚌埠', '淮南', '滁州', '马鞍山', '芜湖', '铜陵', '安庆', '黄山', '六安', '巢湖', '池州', '宣城');
			cities['福建省']=new Array('福州', '南平', '三明', '莆田', '泉州', '厦门', '漳州', '龙岩', '宁德');
			cities['江西省']=new Array('南昌', '九江', '景德镇', '鹰潭', '新余', '萍乡', '赣州', '上饶', '抚州', '宜春', '吉安');
			cities['山东省']=new Array('济南', '聊城', '德州', '东营', '淄博', '潍坊', '烟台', '威海', '青岛', '日照', '临沂', '枣庄', '济宁', '泰安', '莱芜', '滨州', '菏泽');
			cities['河南省']=new Array('郑州', '三门峡', '洛阳', '焦作', '新乡', '鹤壁', '安阳', '濮阳', '开封', '商丘', '许昌', '漯河', '平顶山', '南阳', '信阳', '周口', '驻马店');
			cities['湖北省']=new Array('武汉', '十堰', '襄攀', '荆门', '孝感', '黄冈', '鄂州', '黄石', '咸宁', '荆州', '宜昌', '恩施', '襄樊');
			cities['湖南省']=new Array('长沙', '张家界', '常德', '益阳', '岳阳', '株洲', '湘潭', '衡阳', '郴州', '永州', '邵阳', '怀化', '娄底', '湘西');
			cities['广东省']=new Array('广州', '清远', '韶关', '河源', '梅州', '潮州', '汕头', '揭阳', '汕尾', '惠州', '东莞', '深圳', '珠海', '江门', '佛山', '肇庆', '云浮', '阳江', '茂名', '湛江');
			cities['海南省']=new Array('海口', '三亚');
			cities['四川省']=new Array('成都', '广元', '绵阳', '德阳', '南充', '广安', '遂宁', '内江', '乐山', '自贡', '泸州', '宜宾', '攀枝花', '巴中', '达川', '资阳', '眉山', '雅安', '阿坝', '甘孜', '凉山');
			cities['贵州省']=new Array('贵阳', '六盘水', '遵义', '毕节', '铜仁', '安顺', '黔东南', '黔南', '黔西南');
			cities['云南省']=new Array('昆明', '曲靖', '玉溪', '丽江', '昭通', '思茅', '临沧', '保山', '德宏', '怒江', '迪庆', '大理', '楚雄', '红河', '文山', '西双版纳');
			cities['陕西省']=new Array('西安', '延安', '铜川', '渭南', '咸阳', '宝鸡', '汉中', '榆林', '商洛', '安康');
			cities['甘肃省']=new Array('兰州', '嘉峪关', '金昌', '白银', '天水', '酒泉', '张掖', '武威', '庆阳', '平凉', '定西', '陇南', '临夏', '甘南');
			cities['青海省']=new Array('西宁', '海东', '西宁', '海北', '海南', '黄南', '果洛', '玉树', '海西');
			cities['内蒙古']=new Array('呼和浩特', '包头', '乌海', '赤峰', '呼伦贝尔盟', '兴安盟', '哲里木盟', '锡林郭勒盟', '乌兰察布盟', '鄂尔多斯', '巴彦淖尔盟', '阿拉善盟');
			cities['广西']=new Array('南宁', '桂林', '柳州', '梧州', '贵港', '玉林', '钦州', '北海', '防城港', '南宁', '百色', '河池', '柳州', '贺州');
			cities['西藏']=new Array('拉萨', '那曲', '昌都', '林芝', '山南', '日喀则', '阿里');
			cities['宁夏']=new Array('银川', '石嘴山', '吴忠', '固原');
			cities['新疆']=new Array('乌鲁木齐', '克拉玛依', '喀什', '阿克苏', '和田', '吐鲁番', '哈密', '博尔塔拉', '昌吉', '巴音郭楞', '伊犁', '塔城', '阿勒泰');
			cities['香港']=new Array('香港');
			cities['澳门']=new Array('澳门'); 
	    function set_city(province){
	        var mainCity='';
	        $("#cityDt").siblings('dt').remove();
			var pv, cv;
			var i, ii;
			pv=province.getAttribute("name");
			cv=city.value;
			city.length=1;
			if(pv==''){
			   $("#chkCity").text("请选择所在城市").css('color','gray');
			   return;
			 }
			if(typeof(cities[pv])=='undefined') return;
			$('#provinceVal').val(pv);
			for(i=0; i<cities[pv].length; i++){
				ii = i+1;
				//city.options[ii] = new Option();
				//city.options[ii].text = cities[pv][i];
				//city.options[ii].value = cities[pv][i];
				mainCity=cities[pv][0];
				$("#cityDt").after('<dt onclick="changeCity(this)" name="'+cities[pv][i]+'">'+cities[pv][i]+'</dt>');
			}
		    $("#chkCity").text(mainCity).css('color','gray');
		   $("#cityVal").val(mainCity);
		}
		function changeCity(obj){
		   var cityval=obj.getAttribute("name");
		   $("#cityVal").val(cityval);
		}
		/*
		function initUploader(param1, param2, pkid, posid)//多个参数可改为 定义一个对象 传入  	
		{
			function callback(file, resp)//文件 ，服务器返回的数据 	
			{
			//.log(resp.dataList);
			/*var filepath=resp.dataList[0];
			$("#previewimage").attr("src","../../../"+filepath+"?id="+Math.random());
			$("#imgurl").val(filepath);
			}
			var opt = {
				auto : true,//自动上传 ，加载完文件就上传，     
				swf : '${ctxManage}/webuploader/Uploader.swf',
				fileSingleSizeLimit:2*1024*1024,
				accept:{extensions:'gif,jpg,jpeg,bmp,png'},
				formData : {
					dir : "upload/user",
					username : param2
				},//上传文件时 提交的数据     
				server : '${ctxMRead}uploadimage.html',
				runtimeOrder:'flash,html5',
				pick : {
					id : pkid,
					multiple : false
				},
				resize : false
			};
			uploader = new WebUploader.Uploader(opt);
			uploader.on('uploadSuccess', callback);//成功的回调函数   
			uploader.on('error', function(str) {//出错的回调函数  
				uploader.reset();   
				alert("错误!\n错误码:" + str);
			});
		}
		initUploader("文件1", "用户1", "#btnuploadfile", "#pos1");
		*/
	  </script>

	<script type="text/javascript">
		function back(){
			popupLayer(
					'',
					'<div style="width: 320px; text-align:center; margin: 0 auto;">返回将不保存数据，是否返回？</div>',
					'<button type="button" class="btn-sure sure mr15">确定</button>' +
					'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				location.href='${ctx }web/member/list';
				closePopup();
			});
		}
	</script>

<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch">
<!-- IE10+ -->
<link rel="next">
<!-- Firefox -->
<link rel="prerender">
<!-- Chrome -->
</body>

</html>