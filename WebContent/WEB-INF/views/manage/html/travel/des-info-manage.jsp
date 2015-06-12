<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
		<title>游西藏-目的地管理-目的地信息管理</title>
<%@include file="/common-html/common.jsp"%>
		<link rel="stylesheet"
			href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
		<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
		<link rel="stylesheet"
			href="${ctxManage}/resources/css/travel/formWeb.css" />
			<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
			type="text/javascript"></script>
		<style>
/* 闭合浮动 */
.floatfix:after {
	content: "";
	display: table;
	clear: both;
}
</style>
		<script type="text/javascript">
    	//单独删除目的地信息
       function deleteDes(_code){
    	   popupLayer(
     				'',
     				'<div style="text-align:center">是否删除该目的地</div>',
     				'<div class="formLine">' +
     				'<button type="button" class="btn-sure sureDelOtherMud mr20">确定</button>' +
     				'<button type="button" class="btn-sure cancelDelOtherMud cancel ml20">取消</button>' +
     				'</div>'
     	  );
     	  $(document).one("click",".sureDelOtherMud",function(){
			   		$.ajax( {
			   			url :"${ctx}/web/destinationController/deleteDestination",
			   			dataType : "json",
			   			type : "post",
			   			data : {"code" :  _code},
			   			async : false, 
			   			success : function(data) {
				   			if(data=='error')
				   			{
				   				msgBox("您所删除的目的地中已包含景点数据，无法删除!","pass",1000);
							return  ;
					   			}
			   				location.href="${ctx}/web/destinationController/getDestinationPager";
		       			},
		       			error:function() {}
		       		});
		      	  closePopup();
		          	});
				}
       //批量删除目的地信息
       function batchDeleteDestination(){
    		var code = "";
			if($("input[name='dataCheck']:checked").length<=0)
			{
    		msgBox("请选择需要删除的数据!","error",1000);
				return ;
			}
    		$("input[name='dataCheck']:checked").each(function(){
    			if(code==""){
    				code = $(this).val();
    			}else{
    				code = (code + ","+$(this).val());
    			}
    		});
    		//删除
    		deleteDes(code);
       }
    
    </script>
	</head>
	<body>
		<!-- main { -->
		<div class="main">
			<!-- 页面位置-->
			<div class="location mb40">
				<label>
					您当前位置为:
				</label>
				<span><a>游西藏</a> -</span>
				<span><a>目的地管理</a> -</span>
				<span><a href="#" target="_self">目的地信息管理</a>
				</span>
			</div>

			<!-- 数据操作 -->
			<div class="searchManage">
				<rimiTag:perm url="web/destinationController/forDestinationAdd">
					<a href="${ctx}web/destinationController/destinationEdit"
						target="_self" class="btn-anchor">添加目的地</a>
				</rimiTag:perm>
				<rimiTag:perm url="web/destinationController/deleteDestination">
					<a  href="javascript:void(0);" class="btn-anchor"
						onclick="batchDeleteDestination()">
						批量删除
					</a>
				</rimiTag:perm>
			</div>

			<!-- 模块管理 { -->
			<div class="tableManage pos-rela mt60">
				<form id="listForm" method="post"
					action="${ctx}/web/destinationController/getDestinationPager">
					<!-- 数据列表 -->
					<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
						<thead>
							<tr>
								<th>
									<input type="checkbox" class="allCheck">
									全选
								</th>
								<th>
									目的地名称
								</th>
								<th>
									目的地简介
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="destination" items="${pager.dataList}"
								varStatus="status">
								<tr>
									<td>
										<input type="checkbox" name="dataCheck" class="dataCheck"
											value="${destination.code}">
									</td>
									<td>
									       <c:set var="testStr" value="${destination.destinationName}" />
							              <c:choose>
							                   <c:when test="${fn:length(testStr) > 30}">
							                      <c:out value="${fn:substring(testStr, 0, 30)}" /> ...
							                   </c:when>
							                   <c:otherwise>
							                      <c:out value="${testStr}" />
							                   </c:otherwise>
							              </c:choose>
									  </td>
									  <td>
									     <c:set var="testStr" value=" ${destination.destinationSummary }" />
							              <c:choose>
							                   <c:when test="${fn:length(testStr) > 30}">
							                      <c:out value="${fn:substring(testStr, 0, 30)}" /> ...
							                   </c:when>
							                   <c:otherwise>
							                      <c:out value="${testStr}" />
							                   </c:otherwise>
							              </c:choose> 
									  </td>
									<td>
										<a
											href="${ctxIndex}${destination.linkUrl}"
											target="_blank">前台查看</a>
										<rimiTag:perm url="web/destinationController/destinationEdit">
											<a
												href="${ctx}web/destinationController/destinationEdit?destinationCode=${destination.code}">编辑</a>
										</rimiTag:perm>
										<rimiTag:perm
											url="web/destinationController/deleteDestination">
											<a onclick="deleteDes('${destination.code}')">删除</a>
										</rimiTag:perm>
										<rimiTag:perm url="web/destinationController/imageManaege">
											<a
												href="${ctx }web/destinationController/imageManaege?option=1&&destinationCode=${destination.code}">图集管理</a>
										</rimiTag:perm>
									</td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
					<%-- <c:if test="${empty pager.dataList}">
					<p style="color: red;text-align: center;">暂无相关搜索数据</p>
		 		</c:if>
			     <c:if test="${not empty pager.dataList}">
						<jsp:include page="/common-html/pager.jsp"></jsp:include>
				</c:if> --%>
					<div class="paging">
						<%@include file="/common-html/pager.jsp"%>
					</div>
				</form>


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
		<script type="text/javascript" src="${ctx}/common-js/common.js"></script>

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

		<!-- 利用空闲时间预加载指定页面 -->
		<link rel="prefetch">
		<!-- IE10+ -->
		<link rel="next">
		<!-- Firefox -->
		<link rel="prerender">
		<!-- Chrome -->
	</body>
</html>
