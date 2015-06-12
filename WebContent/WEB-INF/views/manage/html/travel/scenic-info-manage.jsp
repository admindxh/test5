<%@page import="com.rimi.ctibet.domain.View"%>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>游西藏-目的地管理-景点信息管理</title>
	<%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
    <style>
        /* 闭合浮动 */
        .floatfix:after{
            content:"";
            display:table;
            clear:both;
        }
    </style>
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>游西藏</a> -</span>
        <span><a>目的地管理</a> -</span>
        <span><a href="#" target="_self">景点信息管理</a></span>
    </div>

    <!-- 数据操作 -->
    <div class="searchManage">
    	<rimiTag:perm url="web/viewController/forAddView">
	        <a href="${ctx}web/viewController/forAddView" target="_self" class="btn-anchor">添加景点</a>
    	</rimiTag:perm>
    	<rimiTag:perm url="web/viewController/deleteViews">
        	<button type="button" class="btn-sure" onclick="deleteViews();">批量删除</button>
        </rimiTag:perm>
    </div>

	<form id="listForm" action="${ctx}web/viewController/searchView" method="post">
    <!-- 搜索 -->
    <div class="searchTools mt60">
        <input id="viewName" type="text" maxlength="30" class="w-260" name="viewName" placeholder="请输入景点名称进行检索" value="${viewName}">
        <button type="button" class="btn-search"></button>
        <div class="disp-ib ml20">
            <div class="select-base destination-select">
            	<input id="destinationCode" type="hidden" name="destinationCode" value="${destinationCode}">
                <i class="w-140">所属目的地</i>
                <dl>
                    <dt name="">所属目的地</dt>
                    <c:forEach var="obj" items="${listDestination}">
                    	<dt name="${obj.code}">${obj.destinationName }</dt>
                    </c:forEach>
                </dl>
            </div>
        </div>
		<div class="select-base orderBy-select ml10">
			<input id="orderBy" type="hidden" name="orderBy" value="${orderBy}">
			<i class="w-140">按去过人数</i>
			<dl>
				<dt name="<%=View.ORDER_REAL_GONECOUNT%>">按去过人数</dt>
				<dt name="<%=View.ORDER_REAL_WANTCOUNT%>">按想去人数</dt>
				<dt name="<%=View.ORDER_REPLYNUM%>">按评论数</dt>
			</dl>
		</div>
    </div>
    </form>

    <!-- 模块管理 { -->
    <div class="tableManage pos-rela">

        <!-- 数据列表 -->
        <table border="0" cellpadding="0" cellspacing="0" class="dataTable">
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="dataCheck" class="allCheck">全选
                </th>
                <th>景点名称</th>
                <th>所属目的地的</th>
                <th>景点介绍</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="obj" items="${pager.dataList}">
            	<tr>
	                <td>
	                    <input type="checkbox" name="dataCheck" class="dataCheck" value="${obj.code}">
	                </td>
	                <td><a href="${ctx}${obj.linkUrl}" target="_blank">${obj.viewName}</a></td>
	                <td>${obj.destinationName}</td>
	                <%-- <td>${fn:substring(obj.viewIntroduce, 0, 18)}...</td> --%>
	                <%-- <td>${(fn:length(obj.viewIntroduce)>18)?(fn:substring(obj.viewIntroduce, 0, 18)):obj.viewIntroduce }${(fn:length(obj.viewIntroduce)>18)?'...':'' }</td> --%>
	                <td>${(fn:length(obj.viewIntroduceCleanHtml)>18)?(fn:substring(obj.viewIntroduceCleanHtml, 0, 18)):obj.viewIntroduceCleanHtml }${(fn:length(obj.viewIntroduceCleanHtml)>18)?'...':'' }</td>
	                <td>
	                    <a href="${ctx}${obj.linkUrl}" target="_blank">前台查看</a>
	                    <rimiTag:perm url="web/viewController/forEditView">
		                    <a href="${ctx}web/viewController/forEditView?code=${obj.code}&wantCount=${obj.wantCount}&goneCount=${obj.goneCount}">编辑</a>
	                    </rimiTag:perm>
	                    <rimiTag:perm url="web/viewController/deleteViews">
	                    	<a href="javascript:deleteView('${obj.code}');">删除</a>
	                    </rimiTag:perm>
	                    <rimiTag:perm url="web/viewController/forViewPictureManage">
		                    <a href="${ctx}web/viewController/forViewPictureManage?destinationCode=${obj.destinationCode}&viewCode=${obj.code}" target="_self">图集管理</a>
	                    </rimiTag:perm>
	                </td>
	            </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- 表格分页 -->
        <div class="paging">
            <%@include file="/common-html/pager.jsp" %>
        </div>

    </div><!-- } 模块管理 -->
</div><!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>

<!-- JSP -->
<script src="${ctx }common-js/common.js"></script>
<script type="text/javascript">
	$(function(){
		var size = ${fn:length(pager.dataList)};
		if(size==0){
			msgBox("暂无数据", "info", 1200);
		}
	})
	$(function(){
		var  pageInfo =  '<div class="paging-info">'+
		    	 '<span class="disp-ib">当前第&nbsp;${pager.currentPage }&nbsp;页</span>'+
		    	 '<span class="disp-ib">共&nbsp;${pager.totalPage }&nbsp;页</span>'+
		    	 '<span class="disp-ib">共&nbsp;${pager.totalCount }&nbsp;条</span>' +
		    	 '</div>';
		    	$("#pageInfo").append(pageInfo);
	});
	$(function(){
		var divs = $(".select-base");
		for(var i=0; i < divs.length; i++){
			var div = divs.eq(i);
			var value = div.find("input").val();
			var dtValue = div.find("dt[name='"+value+"']").text();
			if(dtValue){
				div.find("i").text(dtValue);
			}
		}
	});
	$(".destination-select dt").click(function(){
		var val = $(this).attr("name");
		$("#destinationCode").val(val);
		$("#listForm").submit();
	});
	$(".orderBy-select dt").click(function(){
		var val = $(this).attr("name");
		$("#orderBy").val(val);
		$("#listForm").submit();
	});
	$(".btn-search").click(function(){
		$("#listForm").submit();
	});
	
	//删除
	function deleteView(code){
		$.post("${ctx}web/viewController/checkViewDelete",{viewCode:code},function(data){
			var msg = "";
			if(data==0){
				msg = "您确定要删除？";
				popupLayer(
					'',
					'<div style="width: 320px; text-align:center; margin: 0 auto;">' + msg + '</div>',
					'<button type="button" class="btn-sure sure mr15">确定</button>' +
					'<button type="button" class="btn-sure cancel ml15">取消</button>'
				);
				$(document).one('click', '.sure', function(){
					var url = "${ctx}web/viewController/deleteViews?codes="+code;
					window.location.href=url;
					closePopup();
				});
			}else{
				msg = "该景点下包含商户、团游、攻略信息，无法删除";
				msgBox(msg, "erro", 2000);
			}
		});
	}
	//批量删除
	function deleteViews(){
		var msg = "";
		var codes = $("input[name='dataCheck']:checked");
		if(codes==undefined||$(codes).size()<=0){
			msgBox("请选择你要删除的数据！", "pass", 2600);
			return; 
		}
		for(var i = 0; i < codes.length; i++){
			var flag = true;
			var url = "${ctx}web/viewController/checkViewDelete";
			var params = {viewCode: codes.eq(i).val()};
			$.ajax({
				url: url,
				type: 'post',
				data: params,
				async: false,
				success: function(data){
					if(data==0){
						msg = "您确定要删除？";
					}else{
						msg = "所选景点中有景点包含商户、团游、攻略信息，无法删除";
						msgBox(msg, "erro", 2600);
						flag = false;
					}
				}
			})//done()
			if(!flag)break;
		}
		//alert(msg);
		if(flag){
			var url = "${ctx}web/viewController/deleteViews";
			deleteBatchCodeOrSingle("dataCheck", url, "codes", msg);
		}
	}
	
</script>

<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch"> <!-- IE10+ -->
<link rel="next"> <!-- Firefox -->
<link rel="prerender"> <!-- Chrome -->
</body>
</html>