<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<!DOCTYPE html>
<html>
<head>
     <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>星级服务-骑行线路管理</title>
	<%@include file="/common-html/common.jsp" %>
     <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
     <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <script type="text/javascript">
    $(function(){
		var  pageInfo =  '<div class="paging-info">'+
				    		'<span class="disp-ib">当前第&nbsp;${pager.currentPage }&nbsp;页</span>'+
				    		'<span class="disp-ib">共&nbsp;${pager.totalPage }&nbsp;页</span>'+
				    		'<span class="disp-ib">共&nbsp;${pager.totalCount }&nbsp;条</span>' +
				    		'</div>';
    	$("#pageInfo").append(pageInfo);
	});
    </script>
    
    
</head>
<body>
		<!-- main { -->
    <div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>骑行专区</a> -</span>
			<span><a href="#" target="_self">星级服务</a> -</span>
			<span><a href="#" target="_self">骑行线路管理</a></span>
		</div>
		
		<!-- 数据操作 -->
		<div class="searchManage">
			<a href="javascript:void(0)" target="_self" class="btn-anchor" onclick="addUI('${ctx}/web/serviceSite/addUI')">新建线路</a>
			<button type="button" class="btn-sure" onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/serviceSite/delete','codes')">批量删除</button>
		</div>
	
		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<form id="listForm" method="post" action="${ctx }/web/serviceSite/list">
					<div class="searchTools">
						<!-- 名称/编号 -->
						<input type="text" value="${siteName}" name="siteName" id="keyWord" placeholder="站点名称/服务站点名称/编号" />
						<!-- 装备类型 -->
						<div class="select-base ml-10">
							<i class="w-120">${proTypeText }</i>
							<dl>
								<dt  inputValue="" inputName="proType" inputTextValue="全部路线类型" inputTextName="proTypeText">全部路线类型</dt>
								<c:forEach var="ride" items="${rideList}">
									<dt  inputValue="${ride.code}"  inputName="proType"   inputTextValue="${ride.name}" inputTextName="proTypeText">${ride.name}</dt>
								</c:forEach>
							</dl>
							<input id="proType" type="hidden" value="${proType }" name="proType"/>
							<input id="proTypeText" type="hidden" value="${proTypeText }" name="proTypeText"/>
						</div>
						 
						
						<!-- 查询 -->
						<button type="button" class="btn-search"  onclick="javascript:$('#listForm').submit()"><!--查询--></button>
						 <!-- 快捷筛选 -->
	                 
					</div><!-- } 搜索查询栏 -->
			</form>
			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th>
							<input type="checkbox" name="dataCheck" class="allCheck">
							全选
						</th>
						<th>排序</th>
						<th>编号</th>
						<th>创建日期</th>
						<th>站点名称</th>
						<th>服务站点名称</th>
						<th>服务站点电话</th>
						<th>服务站点地址</th>
						<th>线路名称</th>
						<%--<th>攻略内容</th>
						--%><th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="obj" items="${pager.dataList}" varStatus="status">
						<tr>
							<td>
								<input type="checkbox" name="dataCheck" class="dataCheck" value="${obj.code}">
							</td>
							<td>${status.index+1}</td>
							<td>${obj.code}</td>
							<td>
							  <fmt:formatDate  pattern="yyyy-MM-dd HH-mm-ss" value="${obj.createTime}"></fmt:formatDate>
							</td>
							<td>${fn:length(obj.sitename)>10?fn:substring(obj.sitename,0,10):obj.sitename}${fn:length(obj.sitename)>10?'...':''}</td>
							<td>${fn:length(obj.servicename)>10?fn:substring(obj.servicename,0,10):obj.servicename}${fn:length(obj.servicename)>10?'...':''}</td>
							<td>${obj.servicephone}</td>
							<td>${fn:length(obj.serviceadress)>10?fn:substring(obj.serviceadress,0,10):obj.serviceadress}${fn:length(obj.serviceadress)>10?'...':''}</td>
							<td>${obj.name }</td>
							<td>
								<a  href="javascript:void(0)"   onclick="deletebySingle('${obj.code}','${ctx}/web/serviceSite/delete','codes')">删除</a>
								<a  href="javascript:void(0)"   onclick="update('${ctx}/web/serviceSite/updateUI','code=${obj.code}')">修改</a>
							</td>
							
						</tr>
					</c:forEach>
					 <c:if test="${empty pager.dataList}">
		            	<tr>
			            	        <td colspan="11" style="text-align: center;color:red;">
										     暂无相关站点
								    </td>      	
		            	</tr>
           			 </c:if>
					</tbody>
				</table>
				<div class="paging">
					<%@include file="/common-html/pager.jsp" %>
				</div>
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