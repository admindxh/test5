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
		<title>系统设置-角色权限管理-系统操作员管理-修改系统操作员</title>
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
				<span><a href="searchUser" target="_self">系统操作员管理</a> -</span>
				<span><a>修改系统操作员</a> </span>
			</div>

			<!-- 模块管理 { -->
			<form id="member-add-form" method="post" action="saveOrUpdate">
				<!-- 会员信息 { -->
				<div class="filament_solid_ddd pos-rela mt30">

					<div class="formLine mt20">
						<label>
							账户名:
						</label>
						<input id="account" type="text" class="w-320" name="name" 
							value="${user.name}" disabled="disabled"/>
						<input type="hidden" value="${user.code}" name="code" />
						<input type="hidden" value="${user.id}" name="id" />
						<span class="reqItem">*</span>
					</div>

					<div class="formLine">
						<label>
							选择角色:
						</label>
						<div id="chooseRole" class="select-base">
							<input id="" name="" type="hidden" value="" />
							<i class="w-320">${myRole.name}</i>
							<dl>
								<c:forEach items="${roles}" var="role">
									<dt inputValue="${role.code}" inputName="roleCode">
										${role.name}
									</dt>
								</c:forEach>
							</dl>
							<input id="proCode" type="hidden" value="${myRole.code}" name="roleCode" />
						</div>
						<span class="reqItem">*</span>
					</div>

					<!-- 按钮 -->
					<div class="ml140 mt40 mb30">
						<button class="btn-sure sureModif" type="submit">
							修改
						</button>
						<a class="btn-anchor ml20 mr40" type="button" onclick="back()">取消</a>
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