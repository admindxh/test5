<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
    <title>活动&专题-活动&专题信息管理-活动信息管理</title>
    <%@ include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctx }manage/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctx }manage/resources/css/base.css" />
    
    <script src="${ctx }manage/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
</head>
<body>
    <!-- main { -->
    <div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a href="activity.html" target="_self">活动&专题</a> -</span>
			<span><a>活动&专题信息管理</a> -</span>
			<span><a href="#" target="_self">活动信息管理</a></span>
		</div>
		
		<!-- 数据操作 -->
		<div class="searchManage">
			<!-- <a href="activity-creat.html" target="_self" class="btn-anchor">新建活动</a> -->
			<rimiTag:perm url="web/activityController/forAddActivity">
				<a href="${ctx }web/activityController/forAddActivity" target="_self" class="btn-anchor">新建活动</a>
			</rimiTag:perm>
			<rimiTag:perm url="web/activityController/deleteActivity">
				<button type="button" class="btn-sure" onclick="deleteBatch()">批量删除</button>
			</rimiTag:perm>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<form id="listForm" action="${ctx }web/activityController/searchList" method="POST">
			<div class="searchTools">
				
				<!-- 按标题 -->
				<label>按标题:</label>
				<input type="text" name="name" value="${name}">
				
				<!-- 查询 -->
				<button type="button" class="btn-search" onclick="search()"><!--查询--></button>
				<!-- 快捷查询 { -->
				<div class="shortcutSearch">
					<!-- 包含模块 -->
					<div class="select-base ml-20 _block">
						<input type="hidden" name="block" value="${block }">
						<i class="w-110">包含模块</i>
						<dl>
							<dt name="0">全部模块</dt>
							<dt name="1">上传</dt>
							<dt name="2">投票</dt>
							<dt name="3">报名</dt>
							<dt name="4">报名付费</dt>
						</dl>
					</div>

					<!-- 进行状态 -->
					<div class="select-base ml-10 _isEnd">
						<input type="hidden" name="isEnd" value="${isEnd }">
						<i class="w-110">全部状态</i>
						<dl>
							<dt name="1">全部状态</dt>
							<dt name="4">未开始</dt>
							<dt name="2">进行中</dt>
							<dt name="3">已结束</dt>
						</dl>
					</div>

					<!-- 快捷筛选 -->
					<div class="select-base ml-10 _orderby">
						<input type="hidden" name="orderby" value="${orderby }">
						<i class="w-140">快捷筛选</i>
						<dl>
							<dt name="1">按时间</dt>
							<dt name="2">按报名人数</dt>
							<dt name="3">按上传作品数</dt>
						</dl>
					</div>
				</div><!-- } 快捷查询 -->
			</div>
			</form><!-- } 搜索查询栏 -->
			
			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th>
							<input id="CheckAll" type="checkbox" name="" class="allCheck">
							全选
						</th>
						<th>序号</th>
						<th>创建日期</th>
						<th>名称</th>
						<th>官网是否显示</th>
						<th>进行状态</th>
						<th>模块功能</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<!-- 列表{ -->
					<c:forEach var="obj" items="${pager.dataList }">
						<tr>
							<td>
								<input type="checkbox" name="dataCheck" class="dataCheck" value="${obj.code }">
							</td>
							<td>${obj.sortNum }</td>
							<td><fmt:formatDate type="both" value="${obj.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>
								<a href="${ctx }${obj.linkUrl}" target="_blank">
									<span class="maxW460">${obj.name }</span>
								</a>
							</td>
							<td>${obj.description eq '1'?'是':'否' }</td>
							<td>${obj.activityState }</td>
							<td>${obj.blocks }</td>
							<td>
								<c:if test="${obj.isEnrollPay==1 }">
									<a href="${ctx }web/OrderChannelSource/forOrderChannelSourceManage?activityCode=${obj.code}">报名渠道管理</a>
								</c:if>
								<rimiTag:perm url="web/activityController/forActivityManage">
									<a href="${ctx }web/activityController/forActivityManage?activityCode=${obj.code}" target="_self">活动管理</a>
								</rimiTag:perm>
								<a href="${ctx }${obj.linkUrl}" target="_blank">前台查看</a>
								<rimiTag:perm url="web/activityController/forEditActivity">
									<a href="${ctx }web/activityController/forEditActivity?code=${obj.code}">编辑</a>
								</rimiTag:perm>
								<rimiTag:perm url="web/activityController/deleteActivity">
									<c:if test="${obj.code ne 'ACT4222013853974417' && obj.code ne 'ACT4238208750543147'}">
										<a href="javascript:void(0);" onclick="deleteAct('${obj.code }')">删除</a>
									</c:if>
								</rimiTag:perm>
							</td>
						</tr>
					</c:forEach>
				<!-- 列表} -->
				</tbody>
			</table>

			<!-- 表格分页 -->
			<div class="paging">
				<!-- button type="button" class="btn-base_min">首页</button>
				<button type="button" class="btn-base_min">上一页</button>
				<button type="button" class="btn-base_min">下一页</button>
				<button type="button" class="btn-base_min">末页</button> -->
				<%@include file="/common-html/pager.jsp" %>
			</div>
				
		</div><!-- } 模块管理 -->
    </div><!-- } main -->

    <!-- JS引用部分 -->
    <script src="${ctx }manage/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctx }manage/resources/js/base.js" type="text/javascript"></script>
    <script src="${ctx }manage/resources/js/base-tabel.js" type="text/javascript"></script>
    <script src="${ctxManage}resources/plugin/respond.min.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
    
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
		$(function(){
			var size = ${fn:length(pager.dataList)};
			if(size==0){
				msgBox("暂无数据", "info", 1200);
			}
		})
		function search(){
			var form = document.getElementById("listForm");
			form.submit();
		}
		function deleteBatch(){
			/* popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">您确定要删除选中的活动？</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				var url="${ctx }web/activityController/deleteActivity";
				deleteBatchCodeOrSingle("dataCheck", url, "codes");
				closePopup();
			}); */
			var url="${ctx }web/activityController/deleteActivity";
			deleteBatchCodeOrSingle("dataCheck", url, "codes");
		}
		function deleteAct(code){
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">您确定要删除该项活动？</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				var url = "${ctx }web/activityController/deleteActivity?codes="+code;
				window.location.href=url;
				closePopup();
			});
		}
		
		$("#CheckAll").click(function(){
		     $("input[name='dataCheck']").attr("checked",$(this).attr("checked"));
		});
		
		
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
	<script type="text/javascript">
		$("._block dt").click(function(){
			$(this).parent().parent().find("input").val($(this).attr("name"));
			search();
		});
		$("._isEnd dt").click(function(){
			$(this).parent().parent().find("input").val($(this).attr("name"));
			search();
		});
		$("._orderby dt").click(function(){
			$(this).parent().parent().find("input").val($(this).attr("name"));
			search();
		});
	</script>
	
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>
