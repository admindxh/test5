
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>西藏动态列表管理</title>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<%@include file="/common-html/common.jsp" %>
	<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
</head>
<body>
 <!-- main { -->
    <div class="main">
			<!-- 页面位置-->
			<div class="location">
				<label>您当前位置为:</label>
				<span><a href="home.html" target="_self">门户首页</a> -</span>
				<span><a href="#" target="_self">西藏动态列表管理</a></span>
			</div>
			
			<!-- 模块管理 { -->
			<div class="tableManage">
			
				<!-- 管理按钮 -->
				<div class="manageBtn">
					<button type="button" class="btn-sure">新建</button>
					<button type="button" class="btn-sure" onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/indexdynamicdatamgController/delete','contentCode')">删除</button>
				</div>
				
				<!-- 数据列表 -->
				
				
        <form id="listForm" method="post" action="${ctx}/web/indexdynamicdatamgController/list?proCode=13198b8d-75da-11e4-b6ce-005056a05bc9"></form>
		
		<!-- 数据列表 -->
				<table border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<th>
								<input type="checkbox" name="dataCheckAll" >
								全选
							</th>
							<th>排序</th>
							<th>创建日期</th>
							<th>标题</th>
							<th>内容</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
		 			<c:forEach var="dynamicDataMg" items="${pager.dataList}" varStatus="status">
						<tr>
							<td>
								<input type="checkbox" name="dataCheck" value="${dynamicDataMg.code}">
							</td>
							<td>${status.index+1}</td>
							<td><fmt:formatDate value="${dynamicDataMg.createTime}"  pattern="yyyy-MM-dd" /></td>
							<td>
								<a href="#" title="${dynamicDataMg.contentTitle}">
									<span class="maxW340">${dynamicDataMg.contentTitle}</span>
								</a>
							</td>
							<td>
								<a href="#" title="${dynamicDataMg.content}">
									<span class="maxW800">${dynamicDataMg.content}</span>
								</a>
							</td>
							<td>
								<a>取消首页显示</a>
								<a>编辑</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
		<%@include file="/common-html/pager.jsp" %>
			</div><!-- } 模块管理 -->
    </div><!-- } main -->

    <!-- JS引用部分 -->
    
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
	
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->



</body>
</html>