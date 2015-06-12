<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<!DOCTYPE html>
<html>
<head>
     <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>骑行专区-骑行装备</title>
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
	    
	    /**
	     * 单个删除
	     */
	    function validateDel (validUrl, delUrl, param, value) {
	    	var validParams = { codes : value };
	    	$.post(validUrl, validParams, function (res) {
	    		if (res == "true") {
	    			deletebySingle(value, delUrl, param);
	    		} else msgBox("该商品已有交易记录，无法删除!", "info", 2200);
	    	});
	    }
	    
	    /**
	     * 批量删除
	     */
	    function validateDelByBatch (validUrl, delUrl, attrName, param) {
	    	var validParams = { codes : getAllChecked(attrName) };
	    	if (validParams.codes != "") {
	    		$.post(validUrl, validParams, function (res) {
		    		if (res == "true") {
		    			deleteBatchCodeOrSingle(attrName, delUrl, param);
		    		} else msgBox("商品中存在有交易记录的装备，无法删除!", "info", 2200);
		    	});
	    	}
	    }
	    
	    /**
	     * 获取复选框中选中的值
	     */
	    function getAllChecked (attrName) {
	    	if (!attrName) return "";
	    	var codes = [];
	    	$(":input[name='" + attrName + "']:checked").each(function () {
	    		if (this.value != "on" && this.value != "") {
		    		codes.push(this.value);
	    		}
	    	});
	    	return codes.length > 0 ? codes.join(",") : "";
	    }
    </script>
</head>
<body>
		<!-- main { -->
    <div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a href="#" target="_self">骑行专区</a> -</span>
			<span><a>装备推荐</a> -</span>
			<span><a href="#" target="_self">装备管理</a></span>
		</div>
		
		<!-- 数据操作 -->
		<div class="searchManage">
			<a href="javascript:void(0)" target="_self" class="btn-anchor" onclick="addUI('${ctx}/web/equip/addUI')">新建装备</a>
<%-- 			<button type="button" class="btn-sure" onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/equip/delete','codes')">批量删除</button> --%>
			<button type="button" class="btn-sure" onclick="validateDelByBatch('${ctx}/web/equip/validDel', '${ctx}/web/equip/delete', 'dataCheck', 'codes')">批量删除</button>
		</div>
	
		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<form id="listForm" method="post" action="${ctx }/web/equip/list">
					<div class="searchTools">
						<!-- 名称/编号 -->
						<input type="text" value="${name}" name="name" id="keyWord" placeholder="装备名称/编号" />
						<!-- 装备类型 -->
						<div class="select-base ml-10">
							<i class="w-120">${proTypeText }</i>
							<dl>
								<dt  inputValue="" inputName="proType" inputTextValue="全部类型" inputTextName="proTypeText">全部类型</dt>
								<c:forEach var="program" items="${ptypes}">
									<dt  inputValue="${program.code}"  inputName="proType"   inputTextValue="${program.programaName}" inputTextName="proTypeText">${program.programaName}</dt>
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
						<th>名称</th>
						<th>类型</th>
						<th>数量</th>
						<th>价格</th>
						<th>支付类型</th>
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
							  <fmt:formatDate  pattern="yyyy-MM-dd HH-mm-ss" value="${obj.createtime}"></fmt:formatDate>
							</td>
							<td>${obj.name }</td>
							<td>${obj.programaName }</td>
							<td>${obj.count }</td>
							<td>${obj.price }</td>
							<td>${obj.paytypename }</td>
							<td>
<%-- 								<a href="javascript:void(0)" onclick="deletebySingle('${obj.code}','${ctx}/web/equip/delete','codes')">删除</a> --%>
								<a href="javascript:void(0)" onclick="validateDel('${ctx}/web/equip/validDel', '${ctx}/web/equip/delete', 'codes', '${obj.code}')">删除</a>
								<a href="javascript:void(0)" onclick="update('${ctx}/web/equip/updateUI','ecode=${obj.code}')">修改</a>
							</td>
							
						</tr>
					</c:forEach>
					 <c:if test="${empty pager.dataList}">
		            	<tr>
	            	        <td colspan="11" style="text-align: center;color:red;">
								     暂无相关装备
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