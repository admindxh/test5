<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
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
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>系统设置-角色权限管理-角色管理</title>
		<link rel="stylesheet"
			href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
		<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
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
				<span><a>角色管理</a> </span>
			</div>

			<!-- 数据操作 -->
			<div class="searchManage">
				<a href="${ctx}/web/role/gotoSaveOrUpdateRole" class="btn-anchor" target="_self">新建角色</a>
				<button type="button" class="btn-sure" onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/role/deleteRole','code')">
					批量删除
				</button>
			</div>

			<!-- 模块管理 { -->
			<div class="tableManage pos-rela">
				<!-- 搜索查询栏 { -->
				<div class="searchTools">
					<form method="post" action="roleList" id="listForm">
                        <label class="va_m">
                            角色名:
                        </label>
                        <input id="roleName" type="text" placeholder="" name="keyWord" value="${keyWord}">

                        <button type="submit" class="btn-search"><!--查询--></button>
                    </form>
				</div>
				<!-- } 搜索查询栏 -->

				<!-- 数据列表 -->
				<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
					<thead>
						<tr>
							<th class="w-80">
								<input type="checkbox"  class="allCheck mr5">
								全选
							</th>
							<th class="w-80">
								编号
							</th>
							<th>
								角色名
							</th>
							<th>
								权限
							</th>
							<th>
								操作
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${roles.dataList}" var="role" varStatus="count">
							<tr>
								<td>
									<input type="checkbox" name="dataCheck" class="dataCheck" value="${role.code}">
								</td>
								<td>
									${role.code}
								</td>
								<td>
									<span class="ellipsis maxW180">${role.rolename}</span>
								</td>
								<td >
									<span class="ellipsis maxW1100" style="width: 1000px">${role.resouce}</span>
								</td>
								<td>
									<a class="edit" href="gotoSaveOrUpdateRole?code=${role.code}">编辑</a>
									<a class="shanc" roleCode="${role.code}">删除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</form>
				<!-- 表格分页 -->
				<div class="paging">
					<%@include file="/common-html/pager.jsp"%>
				</div>

			</div>
			<!-- } 模块管理 -->
		</div>
		<!-- } main -->

		<!-- JS引用部分 -->
		<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/base-tabel.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/dataValidation.js"
			type="text/javascript"></script>
		<script type="text/javascript">
		//======================================
		//				删除类别
		//======================================
		var $this = "";
		// 弹出准备删除框
		$(document).on("click", ".dataTable .shanc", function() {
			$this = $(this);
			// 设置删除状态
			$this.parents("tr").addClass("dele-waiting");
			// 弹出确认删除框
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">您确定要删除所选数据？</div>',
				'<button type="button" class="btn-sure sureDele mr15">确定</button>' +
				'<button type="button" class="btn-sure noDele cancel ml15">取消</button>'
			);
		});
		
		// 确定删除（从视觉界面上删除了这一行）
		$(document).on("click",".sureDele", function() {
			var deleTr = $(".dataTable").find("tr.dele-waiting");
			var roleCode  = $this.attr("roleCode") ;
			console.log(roleCode+"ddddd");
			$.ajax({
				type:'post',//可选get
				url:'${ctx}/web/role/deleteRole',//这里是接收数据的PHP程序
				data:'code='+roleCode,//传给PHP的数据，多个参数用&连接
				dataType:'text',//服务器返回的数据类型 可选XML ,Json jsonp script html text等
				success:function(msg){
						// 移除该行
						deleTr.remove();
						// 关闭弹出框
						closePopup();
				}
			})
		});
		
		// 取消删除
		$(document).on("click", ".noDele", function() {
			var deleTr = $(".dataTable").find("tr.dele-waiting");
			// 消除删除状态
			deleTr.removeClass("dele-waiting");
		});
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
