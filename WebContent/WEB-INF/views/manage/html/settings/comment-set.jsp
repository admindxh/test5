<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>系统设置-评价&回复管理-评价&回复设置</title>
	<%@ include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>

<body>
	<!-- main { -->
	<div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>系统设置</a> -</span>
			<span><a>评价&回复管理</a> -</span>
			<span><a>评价&回复设置</a> </span>
		</div>

		<!-- 数据操作 -->
		<div class="searchManage">
			<button id="saveSet" type="button" class="btn-sure" onclick="save()">保存</button>
		</div>
		<!-- 模块管理 { -->
		<form id="saveForm" method="post">
		<div class="tableManage pos-rela mt-60">

			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th class="w-50p">栏目</th>
						<th>是否开启评论</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="obj" varStatus="sta" items="${listPrograma}">
						<tr>
							<td>${obj.programaName }</td>
							<td>
								<input name="listPrograma[${sta.index}].code" type="hidden" value="${obj.code}">
								<div class="select-base">
									<input name="listPrograma[${sta.index}].remark" type="hidden" value="${obj.remark}">
									<i class="w-100">是</i>
									<dl>
										<dt name="yes">是</dt>
										<dt name="no">否</dt>
									</dl>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<!-- 表格分页 -->
			<!-- <div class="paging">
				<button type="button" class="btn-base_min">首页</button>
				<button type="button" class="btn-base_min">上一页</button>
				<button type="button" class="btn-base_min">下一页</button>
				<button type="button" class="btn-base_min">末页</button>
			</div> -->
			
		</div>
		</form>
		<!-- } 模块管理 -->
	</div>
	<!-- } main -->

	<!-- JS引用部分 -->
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>

	<!-- jsp -->
	<script type="text/javascript">
	
		function save(){
			$("#saveForm").attr("action","${ctx}web/replyManageController/updateReplyManageSetting");
			msgBox("保存成功","pass");
			$("#saveForm").submit();
		}
	
		//初始化下拉框
		function initSelect(){
			var divs = $(".select-base");
			for(var i=0; i < divs.length; i++){
				var div = divs.eq(i);
				var value = div.find("input").val();
				var dtValue = div.find("dt[name='"+value+"']").text();
				if(dtValue){
					div.find("i").text(dtValue);
				}
			}
		}
		initSelect();
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
