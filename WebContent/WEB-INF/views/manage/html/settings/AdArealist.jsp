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
	<title>系统设置-运营管理-广告位管理</title>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>

<body>
	<!-- main { -->
	<div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>系统设置</a> -</span>
			<span><a>运营管理</a> -</span>
			<span><a>广告位管理</a> </span>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage mt-25">

			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th class="w-50p">广告位页面</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ps}" var="p">
					<tr>
						<td>
						  <a target="_blank" <c:if test="${p.typeCode eq 'bb'}">href="<%=basePath %>community/frontIndex"</c:if>
						    <c:if test="${p.typeCode eq 'bbs'}">href="<%=basePath %>community/getPostListByPlate?plateCode=e152eee3-ddaf-4f7d-8141-060c121f5995"</c:if>
						    <c:if test="${p.typeCode eq 'tour'}">href="<%=basePath %>tourism/strage/intoTraval/c1d87c3d-792e-11e4-b6ce-005056a05bc9.html"</c:if>
						    <c:if test="${p.typeCode eq 'merc'}">href="<%=basePath %>tourism/merchant/merchantIndex"</c:if>
						    <c:if test="${p.typeCode eq 'merlist'}">href="<%=basePath %>tourism/merchant/merchantIndex"</c:if>
						    <c:if test="${p.typeCode eq 'cult'}">href="<%=basePath %>portal/app/culture/cultural.html"</c:if>> ${p.programaName }</a>
						</td>
						<td>
							<a href="gotoUpdateAdArea?code=${p.code}" class="edit">编辑</a>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>

			<!-- 表格分页 -->
			<c:if test="${not empty ps}">
			<div class="paging">
    	       <%@include file="/common-html/pager.jsp" %>
            </div>
           </c:if>
           <c:if test="${empty ps}">
			<div style="text-align: center;font-size: large;font-weight: bold;margin-top: 10px">
    	        <span>暂无数据</span>
            </div>
          </c:if>
			
		</div>
		<!-- } 模块管理 -->
	</div>
	<!-- } main -->

	<!-- JS引用部分 -->
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>

	<!-- 利用空闲时间预加载指定页面 -->
	<link rel="prefetch">
	<!-- IE10+ -->
	<link rel="next">
	<!-- Firefox -->
	<link rel="prerender">
	<!-- Chrome -->
</body>

</html>
