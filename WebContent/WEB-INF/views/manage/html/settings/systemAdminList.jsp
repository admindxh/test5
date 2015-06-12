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
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>系统设置-角色权限管理-系统操作员管理</title>
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
				<span><a>系统操作员管理</a> </span>
			</div>

			<!-- 数据操作 -->
			<div class="searchManage">
				<a href="gotoSaveOrUpdate" class="btn-anchor w-146">新建系统操作员</a>
				<button type="button" class="btn-sure"
					onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/manage/html/deleteSys','code')">
					批量删除
				</button>
			</div>

			<!-- 模块管理 { -->
			<div class="tableManage pos-rela">
				<!-- 搜索查询栏 { -->
				<div class="searchTools">
					<form method="post" action="searchUser" id="listForm">
						<label class="va_m">
							账号:
						</label>
						<input id="roleName" type="text" placeholder="" name="keyWord"
							value="${keyWord}" />

						<button type="submit" class="btn-search">
							<!--查询-->
						</button>

						<div class="shortcutSearch">
							<div class="select-base ml10">
								<c:if test="${empty searchRole.name }">
									<i class="w-260">全部角色</i>
								</c:if>
								<c:if test="${not empty searchRole.name }">
									<i class="w-260">${searchRole.name}</i>
								</c:if>
								<dl>
									<dt inputValue="" inputName="roleCode">
										全部角色
									</dt>
									<c:forEach items="${roles}" var="role">
										<dt inputValue="${role.code}" inputName="roleCode">
											${role.name}
										</dt>
									</c:forEach>
								</dl>
								<input id="proCode" type="hidden" value="${roleCode}" name="roleCode" />
							</div>
						</div>
					</form>
				</div>
				<!-- } 搜索查询栏 -->
				<!-- 数据列表 -->
				<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
					<thead>
						<tr>
							<th class="w-80">
								<input type="checkbox" class="allCheck mr5">
								全选
							</th>
							<th>
								编号
							</th>
							<th>
								账号
							</th>
							<th>
								角色
							</th>
							<th>
								操作
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pager.dataList}" var="user" varStatus="count">
							<tr>
								<td>
									<input type="checkbox" name="dataCheck" class="dataCheck"
										value="${user.code}" />
								</td>
								<td>
									${count.index+1}
								</td>
								<td>
									<span class="ellipsis maxW300">${user.name}</span>
								</td>
								<td>
									<span class="ellipsis maxW420">${user.rolename}</span>
								</td>
								<td>
								<c:if test="${user.name == 'xz'}">超级管理员不可编辑 </c:if>
								<c:if test="${user.name != 'xz'}">
									<a href="gotoSaveOrUpdate?code=${user.code}" class="edit">编辑</a>
									<a  href="#"  onclick="deleteS('${user.code}')">删除</a></c:if>
									<a class="repwd" id="resetPwd" usercode="${user.code}">密码初始化</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

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

		function deleteS(plateCode){
			  popupLayer(
    				'',
    				'<div style="text-align:center">您确定要删除所选数据？</div>',
    				'<div class="formLine">' +
    				'<button type="button" class="btn-sure sureDelOtherMud mr20">确定</button>' +
    				'<button type="button" class="btn-sure cancelDelOtherMud cancel ml20">取消</button>' +
    				'</div>'
    	  );
    	  $(document).one("click",".sureDelOtherMud",function(){
             $.ajax( {
     			url : "${ctx}/manage/html/deleteSingle",
     			dataType : "json",
     			type:"post",
     			data : {
     				"code" : plateCode
     			},
     			async : false,
     			success : function(data) {
     				location.href="${ctx}/manage/html/searchUser";
     			},
     			error : function(data) {
     				location.href="${ctx}/manage/html/searchUser";
     			}
     		});
    	  closePopup();
        	});
		}



		$(document).on('click','#resetPwd',function(){
			//.log($(this).attr('usercode'));
			var thisCode = $(this).attr('usercode');
			$.ajax({
				url: "${ctx}/manage/html/initPwd",
				dataType:"json",
				type:"get",
				data:{"code":thisCode},
				async: false,
				success: function(data) {
					//.log(data);
					if(data){
						msgBox("密码初始化成功!","pass",2000);
					}
				}
			})
			//.log(222);
		});
		
	</script>
<script>
$(function(){
	var  pageInfo =  '<div class="paging-info">'+
			    		'<span class="disp-ib">当前第&nbsp;${pager.currentPage }&nbsp;页</span>'+
			    		'<span class="disp-ib">共&nbsp;${pager.totalPage }&nbsp;页</span>'+
			    		'<span class="disp-ib">共&nbsp;${pager.totalCount }&nbsp;条</span>' +
			    		'</div>';
	$("#pageInfo").append(pageInfo);
	//
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
