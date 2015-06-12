<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
    <title>活动&专题-活动&专题信息管理-专题信息管理</title>
	<%@ include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/datepicker.css" />
	
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
</head>
<body>
    <!-- main { -->
    <div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>活动&专题</a> -</span>
			<span><a>活动&专题信息管理</a> -</span>
			<span><a href="#" target="_self">专题信息管理</a></span>
		</div>
		
		<!-- 数据操作 -->
		<div class="searchManage">
			<rimiTag:perm url="web/specialController/forAddSpecial">
				<a href="${ctx }web/specialController/forAddSpecial" target="_self" class="btn-anchor">新建专题</a>
			</rimiTag:perm>
			<rimiTag:perm url="web/specialController/deleteSpecial">
				<button type="button" class="btn-sure" onclick="deleteBatch()">批量删除</button>
			</rimiTag:perm>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<form action="${ctx }web/specialController/searchSpecialList" id="listForm" method="POST">
			<div class="searchTools">
				
				<label>按标题:</label>
				<input type="text" placeholder="" name="name" value="${name }">
				
				<label class="ml20">按发布时间:</label>
				<input id="dateSearch" type="text" placeholder="" class="w-120" name="time" value="${time }">
				
				<!-- 查询 -->
				<button type="button" class="btn-search" onclick="search()"><!--查询--></button>
				
				<!-- 快捷查询 { -->
				<div class="shortcutSearch">

					<!-- 快捷筛选 -->
					<div id="orderSelect" class="select-base ml-10 _orderby">
						<input type="hidden" name="orderBy" value="${orderBy }">
						<!-- <input type="hidden" name="orderBy" value="0" > -->
						<i class="w-140">快捷筛选</i>
						<dl>
							<dt name="1">按时间</dt>
							<dt name="${ORDERBY_FAVORITE }">按收藏人数</dt>
							<dt name="${ORDERBY_REPLY }">按评论人数</dt>
						</dl>
					</div>
				</div><!-- } 快捷查询 -->
			</div>
			</form>
			<!-- } 搜索查询栏 -->
			
			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th>
							<input type="checkbox" class="allCheck">
							全选
						</th>
						<th>排序</th>
						<th>创建日期</th>
						<th>名称</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<!-- <tr>
						<td>
							<input type="checkbox" name="dataCheck" class="dataCheck">
						</td>
						<td>1</td>
						<td>2014-12-02</td>
						<td>
							<a href="#">
								<span class="maxW600">西藏拉萨自驾五日游、西藏纳木错综合三日游</span>
							</a>
						</td>
						<td>
							<a>前台查看</a>
							<a href="special-creat.html" target="_self">编辑</a>
							<a>删除</a>
						</td>
					</tr> -->
					<c:forEach var="obj" varStatus="sta" items="${pager.dataList }">
						<tr>
							<td>
								<input type="checkbox" name="dataCheck" class="dataCheck" value="${obj.code }">
							</td>
							<%-- <td>${sta.index+1 }</td> --%>
							<td>${obj.sortNum }</td>
							<td><fmt:formatDate type="both" value="${obj.createTime }" pattern="yyyy-MM-dd" /></td>
							<td>
								<a target="_blank" href="${ctx}${obj.url}">
									<span class="maxW600">${obj.name }</span>
								</a>
							</td>
							<td>
								<a target="_blank" href="${ctx }${obj.url}">前台查看</a>
								<rimiTag:perm url="web/specialController/forEditSpecial">
									<a href="${ctx }web/specialController/forEditSpecial?code=${obj.code}" target="_self">编辑</a>
								</rimiTag:perm>
								<rimiTag:perm url="web/specialController/deleteSpecial">
									<a href="javascript:void(0);" onclick="deleteSpecial('${obj.code}');">删除</a>
								</rimiTag:perm>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<!-- 表格分页 -->
			<div class="paging">
				<!-- <button type="button" class="btn-base_min">首页</button>
				<button type="button" class="btn-base_min">上一页</button>
				<button type="button" class="btn-base_min">下一页</button>
				<button type="button" class="btn-base_min">末页</button> -->
				<%@ include file="/common-html/pager.jsp" %>
			</div>
				
		</div><!-- } 模块管理 -->
    </div><!-- } main -->

    <!-- JS引用部分 -->
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/bootstrap-datepicker.js" type="text/javascript"></script>
	<script src="${ctxManage}resources/plugin/respond.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
	<script type="text/javascript">
		// 运行时间选择控件
		$("#dateSearch").datepicker();
	</script>
	
	<!--  -->
	<script src="${ctx }common-js/common.js"></script>
	<script type="text/javascript">
		$(function(){
			var  pageInfo =  '<div class="paging-info">'+
			    	 '<span class="disp-ib">当前第&nbsp;${pager.currentPage }&nbsp;页</span>'+
			    	 '<span class="disp-ib">共&nbsp;${pager.totalPage }&nbsp;页</span>'+
			    	 '<span class="disp-ib">共&nbsp;${pager.totalCount }&nbsp;条</span>' +
			    	 '</div>';
			    	$("#pageInfo").append(pageInfo);
		});
		function search(){
			var form = document.getElementById("listForm");
			form.submit();
		}
		function deleteSpecial(code){
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">您确定要删除？</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				var url="${ctx }web/specialController/deleteSpecial?codes="+code;
				window.location.href=url;
				closePopup();
			});
			
		}
		function deleteBatch(){
			var url="${ctx }web/specialController/deleteSpecial";
			deleteBatchCodeOrSingle("dataCheck", url, "codes")
		}
		//orderSelect
		//初始化下拉列表
		function initDlSelect(id){
			var div = $("#"+id);
			var value = div.find("input").val();
			var dtValue = div.find("dt[name='"+value+"']").text();
			if(dtValue){
				div.find("i").text(dtValue);
			}
		}
		initDlSelect('orderSelect');
		
		$("._orderby dt").click(function(){
			$(this).parent().parent().find("input").val($(this).attr("name"));
			search();
		});
	</script>
<%-- 	
<div id="" class="select-base ml-10">
	<input type="hidden" name="orderBy" value="0" >
	<i class="w-140">快捷筛选</i>
	<dl>
		<dt name="1">按时间</dt>
		<dt name="${ORDERBY_FAVORITE }">按收藏人数</dt>
		<dt name="${ORDERBY_REPLY }">按评论人数</dt>
	</dl>
</div>
	 --%>
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>
