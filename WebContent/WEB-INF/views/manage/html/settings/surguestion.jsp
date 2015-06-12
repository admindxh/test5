<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>系统设置-运营管理-建议与意见</title>
    <%@include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<!-- JS引用部分 -->
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
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
			<span><a href="${ctx}/web/surguestion/list">建议与意见管理</a> </span>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage">
            <form id="listForm" method="post" action="${ctx }web/surguestion/list"></form>
			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable mt20">
				<thead>
					<tr>
						<th>提交时间</th>
						<th>提交人</th>
						<th>提交内容</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				  <c:forEach items="${pager.dataList}" var="sur">
					<tr>
						<td><fmt:formatDate value="${sur.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
						   <c:choose>
			                   <c:when test="${sur.username!='游客'}">
			                       <a target="_blank" href="${ctx}/portal/othershome/othersStra?memberCode=${sur.memberCode }">${sur.username }</a>
			                   </c:when>
			                   <c:otherwise>
			                      ${sur.username }
			                   </c:otherwise>
			               </c:choose>
						  </td>
						<td>
							<a href="${ctx}/web/surguestion/detail?code=${sur.code }">
								<span class="maxW800">
								  <c:set var="testStr" value="${sur.content }" />
					              <c:choose>
					                   <c:when test="${fn:length(testStr) > 30}">
					                      <c:out value="${fn:substring(testStr, 0, 30)}" /> ...
					                   </c:when>
					                   <c:otherwise>
					                      <c:out value="${testStr}" />
					                   </c:otherwise>
					              </c:choose>
								</span>
							</a>
						</td>
						<td>
							<a onclick="deletebySingle('${sur.code}','${ctx}/web/surguestion/delete','code')">删除</a>
						</td>
					</tr>
				  </c:forEach>
				</tbody>
			</table>

		  <!-- 表格分页 -->
		  <c:if test="${not empty pager.dataList}">
			<div class="paging">
    	       <%@include file="/common-html/pager.jsp" %>
            </div>
          </c:if>
           <c:if test="${empty pager.dataList}">
			<div style="text-align: center;font-size: large;font-weight: bold;margin-top: 10px">
    	        <span>暂无数据</span>
            </div>
          </c:if>
			
		</div>
		<!-- } 模块管理 -->
	</div>
	<!-- } main -->


	<script type="text/javascript" src="${ctxManage}/resources/js/base-tabel.js"></script>
    <script type="text/javascript">
      $(function(){
        if(${delflag=='1'}){
            msgBox("删除成功！", "pass", 2600);
        }
         if(${delflag=='0'}){
            msgBox("删除失败！", "erro", 2600);
        }
         if(${delflag=='-1'}){
            msgBox("记录ID为空！", "erro", 2600);
        }
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
