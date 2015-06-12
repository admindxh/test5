<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
		<title>天上社区-论坛版块管理-版块列表页</title>
		<style>
/* 闭合浮动 */
.floatfix:after {
	content: "";
	display: table;
	clear: both;
}
</style>
	</head>
	<body>
		<%@include file="/common-html/commonxz.jsp"%>
		<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/base-tabel.js"
			type="text/javascript"></script>
		<!-- main { -->
		<div class="main">
			<!-- 页面位置-->
			<div class="location mb40">
				<label>
					您当前位置为:
				</label>
				<span> <a>天上社区</a> - <a>论坛版块管理</a> - <a href="#"
					target="_self">版块列表页</a> </span>
			</div>

			<!-- 数据操作 -->
			<div class="searchManage">
				<a href="gotoSaveOrUpdatePlate" target="_self" class="btn-anchor">新建版块</a>
				<button type="button" class="btn-sure"
					onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/tssq/deletePlates','code')">
					批量删除
				</button>
			</div>

			<!-- 模块管理 { -->
			<div class="tableManage pos-rela mt60">
				<form method="post" action="plateList" id="listForm">
					<!-- 数据列表 -->
					<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
						<thead>
							<tr>
								<th>
									<input type="checkbox" name="dataCheck" class="allCheck">
									全选
								</th>
								<th>
									版块名称
								</th>
								<th>
									帖子数
								</th>
								<th>
									前台查看
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pager.dataList}" var="plate">
								<tr>
									<td>
										<input type="checkbox" name="dataCheck" class="dataCheck"
											value="${plate.code}">
									</td>
									<td>
										<a href="${ctx}${plate.programaurl}" target="_blank">${plate.programaname}</a>
									</td>
									<td>
										<c:if test="${plate.programaname != '全站置顶区'}">${plate.postnum}</c:if>
										<c:if test="${plate.programaname == '全站置顶区'}">${topNum}</c:if>
									</td>
									<td>
										<a href="${ctx}${plate.programaurl}" target="_blank"><span class="maxW600">${plate.programaurl}</span></a>
									</td>
									<td>
										<a href="gotoSaveOrUpdatePlate?code=${plate.code}"
											target="_self">编辑</a>
										<a href="#" onclick="deletePlate1('${plate.code}',this)">删除</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<c:if test="${empty pager.dataList}">
                                                           暂无数据					
					</c:if>
					<!-- 表格分页 -->
					<div class="paging">
						<%@include file="/common-html/pager.jsp"%>
					</div>
				</form>

			</div>
			<!-- } 模块管理 -->
		</div>
		<!-- } main -->

		<!-- 利用空闲时间预加载指定页面 -->
		<link rel="prefetch">
		<!-- IE10+ -->
		<link rel="next">
		<!-- Firefox -->
		<link rel="prerender">
		<!-- Chrome -->
		<script>
		function deletePlate1(plateCode,el){
			  popupLayer(
        				'',
        				'<div style="text-align:center">删除该版块将删除其下面的全部帖子信息，是否删除？</div>',
        				'<div class="formLine">' +
        				'<button type="button" class="btn-sure sureDelOtherMud mr20">确定</button>' +
        				'<button type="button" class="btn-sure cancelDelOtherMud cancel ml20">取消</button>' +
        				'</div>'
        	  );
        	  $(document).one("click",".sureDelOtherMud",function(){
                 var $tr = $(el).parents('tr');
                 $.ajax( {
         			url : contextPath + "/web/tssq/deletePlate",
         			dataType : "json",
         			type:"post",
         			data : {
         				"code" : plateCode
         			},
         			async : false,
         			success : function(data) {
         				$tr.remove();
         			}
         		});
        	  closePopup();
            	});
		}
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
	</body>
</html>
