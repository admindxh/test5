<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
	String path = request.getContextPath();
String port = ":" + request.getServerPort();
if ("80".equals(""+request.getServerPort())) {
	port = "";
}
String basePath = request.getScheme() + "://"
		+ request.getServerName() + port + path + "/";
	request.setAttribute("ctx", basePath);
	request.setAttribute("ctxManage", basePath + "/manage/");
	request.setAttribute("ctxPortal", basePath + "portal/"); //xiangwq
%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>系统设置-角色权限管理-系统操作员管理-新建系统操作员</title>
		<link rel="stylesheet"
			href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
		<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
		<link rel="stylesheet"
			href="${ctxManage}/resources/css/travel/formWeb.css" />
		<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/plugin/respond.min.js"
			type="text/javascript"></script>
		<script src="${ctx}/common-js/common.js" type="text/javascript"></script>
		<style>
			.reqItem{
				white-space: nowrap;
			}
		</style>
	</head>

	<body>
		<!-- main { -->
		<div class="main">
			<!-- 页面位置-->
			<div class="location">
				<label>
					您当前位置为:
				</label>
				<span><a>系统设置</a> -</span>
				<span><a>角色权限管理</a> -</span>
				<span><a href="${ctx}/manage/html/searchUser" target="_self">系统操作员管理</a> -</span>
				<span><a>新建系统操作员</a> </span>
			</div>

			<!-- 模块管理 { -->
			<form id="member-add-form" method="post" action="saveOrUpdate">
				<!-- 管理员信息 -->
				<div id="admin-add" class="filament_solid_ddd pos-rela mt30">

					<div class="formLine mt20">
						<label>
							账户名:
						</label>
						<input id="account" type="text" class="w-320" name="name"
							value="${user.name}" />
						<span class="reqItem">*</span>
					</div>

					<div class="formLine">
						<label>
							密码:
						</label>
						<input id="pwd" type="password" class="w-320" name="pwd"
							value="${user.pwd}" />
						<span class="reqItem">*</span>
					</div>

					<div class="formLine">
						<label>
							确认密码:
						</label>
						<input id="repwd" type="password" class="w-320" />
						<span class="reqItem">*</span>
					</div>

					<div class="formLine">
						<label>
							选择角色:
						</label>
						<div id="chooseRole" class="select-base">
							<input id="" name="" type="hidden" value="" />
							<i class="w-320">请选择角色</i>
							<dl>
								<c:forEach items="${roles}" var="role">
									<dt inputValue="${role.code}" inputName="roleCode">
										${role.name}
									</dt>
								</c:forEach>
							</dl>
							<input id="proCode" type="hidden" value="" name="roleCode" />
						</div>
						<span class="reqItem">*</span>
					</div>

					<!-- 按钮 -->
					<div class="ml140 mt40 mb30">
						<button class="btn-sure sureAdd" type="button">
							创建
						</button>
						<a class="btn-anchor ml20 mr40" href="javascript:back()">取消</a>
					</div>

				</div>
				<!-- } 会员信息 -->
			</form>
			<!-- } 模块管理 -->
		</div>
		<!-- } main -->

		<!-- JS引用部分 -->

		<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
		<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/dataValidation.js"
			type="text/javascript"></script>
<script type="text/javascript">
		//======================================
		//				数据验证
		//======================================			

		// 账户名
		$("#account").blur(function () {
			var this_val = $(this).val(),
				thisNode = $(this)[0],
				reg_val = $.VLDTOR.IsEnCnNum(this_val),
				val_range = inputRange(this, 2, 30);
			// 验证信息方法
			if(valid_txtBox(this, reg_val && val_range, "只能为2-30位的中、英文和数字", "right")){
				$.ajax({
					url: "${ctx}/manage/html/isRepeat",
					dataType:"json",
					type:"get",
					data:{"name":this_val},
					async: false,
					success: function(data) {
						if(data){
							$(thisNode).next('.reqItem').text('账号重复！');
						}else{
							$(thisNode).next('.reqItem').removeClass('errMesg').text('*');
						}
					}
				})
			}
		});

		// 密码
		$("#pwd").blur(function() {
			var this_val = $(this).val(),
				repwd_val = $("#repwd").val(),
				reg_val = $.VLDTOR.IsPWD(this_val),
				val_range =  inputRange(this, 6, 16);
			if(repwd_val != "") {
				modifErrMesg("#repwd");
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
			}
			valid_txtBox(this, this_val == pwd_val, "两次密码输入不一致，请检查", "right");
		});
		
		// 选择消除“选择角色”错误提示
		$("#chooseRole dt").click(function() {
			$(this).parents("#chooseRole").next().removeClass("errMesg").text("*");
		});
		
		// 创建按钮验证
		$(".sureAdd").click(function() {
			// 失焦验证
			$("#account, #pwd, #repwd").blur();
			
			// 选择验证
			valid_customSelect("#chooseRole", "请选择一个角色", "right");
			
			// 是否含有错误信息
			var hasErr = $(".errMesg").length > 0;

			// 含有验证不通过的项
			if(hasErr) {
				msgBox("您填写的信息有误，请检查！", "erro");
				return;
			}
			// 验证通过提交表单
			else {
				msgBox("新建成功！", "pass");
				$("#member-add-form").submit();
			}
		}); 
		  // 批量确定删除
	    $(document).on("click",".confirm",function(){
	        var deleTr = $(".dataTable").find("tr.trChecked");
	        deleTr.remove();
	        closePopup();
	    });

	    $("#batchRemove").on('click',function(){
	        var trChecked = $('.dataTable').find("tr.trChecked");
	        if(trChecked.length){
	            popupLayer(
	                    '',
	                    '<div style="width: 320px; text-align:center; margin: 0 auto;">删除选中管理员<br>是否删除？</div>',
	                    '<button type="button" class="btn-sure confirm mr15">确定<tton>' +
	                    '<button type="button" class="btn-sure cancel ml15">取消<tton>'
	            );
	        }else{
	            msgBox("请勾选需要删除的板块","info",1000);
	        }
	    })
			
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
					javascript:history.back(-1);
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