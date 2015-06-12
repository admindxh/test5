<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>系统设置-评价&回复管理-待审核的评价&回复</title>
	<%@ include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <style>
        /* 闭合浮动 */
        .floatfix:after{
            content:"";
            display:table;
            clear:both;
        }
        .bor_t{
            border-top: 1px solid #ccc;
        }
		.bor_t p {
			width:90%;
		}
        .w96p{
            width: 96%;
        }
    </style>
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
        <span><a>待审核的评价&回复</a></span>
    </div>

    <!-- 数据操作 -->
    <div class="searchManage">
        <button type="button" class="btn-sure deleteBatch">批量删除</button>
    </div>

    <!-- 模块管理 { -->
    <div class="tableManage pos-rela">
        <!-- 搜索查询栏 { -->
        <div class="searchTools formLine">

            <!-- 关键字 -->
            <div class="select-base ml-20">
            	<input id="replyType" type="hidden" name="" value="${replyType }">
                <i class="w-140">按栏目筛选</i>
                <dl>
                	<dt class="replyTypeClick" name="">全部栏目</dt>
                    <c:forEach var="obj" items="${listPrograma}">
                    	<dt class="replyTypeClick" name="${obj.enName}">${obj.programaName}</dt>
                    </c:forEach>
                </dl>
            </div>
        </div><!-- } 搜索查询栏 -->

        <!-- 数据列表 -->
        <div class="formLine">
            <input type="checkbox" name="dataCheck" class="allCheck">
            <label class="w-auto">全选</label>
        </div>

        <ul id="ul" class="mt10" ms-important="listView">
            <li class="pt10 bor_t" ms-repeat="data" ms-if-loop="el.title">
                <input type="checkbox" name="checkBoxContentCode" class="dataCheck float_l" ms-attr-value="el.contentCode">
                <div class=" disp-ib w96p ml15">
                    <div>
                        <label class="w-auto">{{el.programaName}}</label>
                        <span> > </span>
                        <a ms-href="${ctx}{{el.linkUrl}}" target="_blank" class="ml10 disp-ib w210 str-clip">{{el.title|truncate(13," ...")}}</a>
                        <span class="ft-c_red ml10">待审核</span>
                        <div class="float_r">
                            <button type="button" class="btn-base_min review" ms-attr-val="el.contentCode">通过</button>
                            &nbsp;<button type="button" class="btn-base_min delete" ms-attr-val="el.contentCode">删除</button>
                        </div>
                    </div>
                    <p class="mt15 ft-c_gray f15">
                        <span>{{el.memberName}}</span>
                        <span>发表于</span>
                        <span>{{el.createTime | date("yyyy-MM-dd HH:mm")}}</span>
                    </p>
                    <p class="mt15 openInfo" style="word-wrap: break-word; line-height: 20px;">{{el.content|truncate(100," ...")}}</p>
                    <input class="openInfo" type="hidden" ms-attr-value="el.content"/>
                    <a href="javascript:void(0);" target="_self" class="float_r mt10 mb5 openInfo">展开</a>
                </div>
            </li>
        </ul>

        <!-- 表格分页 -->
        <div id="listPaging" class="paging"></div>

    </div><!-- } 模块管理 -->
</div><!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/bbs/audit-wait.js" type="text/javascript"></script>

<!-- jsp -->
<script src="${ctxManage}resources/js/avalon.js" type="text/javascript"></script>
<script src="${ctxManage}resources/js/bootstrap-paginator.js" type="text/javascript"></script>
<script type="text/javascript">

	var contentCode = "";
	var contentType="";
	var c_page=1;
	
	$(function(){
		var divs = $(".select-base");
		for(var i=0; i < divs.length; i++){
			var div = divs.eq(i);
			var value = div.find("input").val();
			var dtValue = div.find("dt[name='"+value+"']").text();
			if(dtValue){
				div.find("i").text(dtValue);
				contentType = value;
			}
		}
		loadData(1);
	});
	
	//click
	$(".replyTypeClick").click(function(){
		contentType = $(this).attr("name");
		loadList(1);
	});
	/* $(".allCheck").click(function(){
	     $("input[name='dataCheck']").attr("checked",$(this).attr("checked"));
	}); */

	function servicesPage(pageId, currentPage, totalPage, call_){
		var options = {
	     	currentPage: currentPage || 1,
	     	totalPages: totalPage || 1,
	     	itemTexts: function (type, page, current) {
	            switch (type) {
	                case "first":
	                    return "首页";
	                case "prev":
	                    return "上一页";
	                case "next":
	                    return "下一页";
	                case "last":
	                    return "末页";
	                case "page":
	                    return page;
	            }
	        },
	     	onPageChanged: function(e,oldPage,newPage){
	     		call_(newPage);
	    	}
		}
		$('#'+pageId).bootstrapPaginator(options);
	}
	
	//加载列表{
	var listVM = avalon.define({
		$id: 'listView',
		data: []
	});
	function loadList(currentPage){
		avalon.clearHTML($("#ul"));
		var thisCall = loadList;
		var url="${ctx}web/replyManageController/loadReplyManageList";
		var params = {
				activityCode:'${activity.code }',
				currentPage: currentPage, 
				//pageSize: 5, 
				contentType: contentType,
				state: 0
		};
		$.post(url, params, function(response){
			c_page = response.currentPage;
		  	servicesPage("listPaging", response.currentPage, response.totalPage, thisCall);
		  	listVM.data = response.dataList;
			avalon.scan();
		}, 'json');
	}
	function loadData(){
		loadList(1);
	}
	
	// 展开
	$(document).on('click',"a.openInfo",function(){
		var inputTemp = $(this).siblings('input.openInfo').val(),
			pTemp = $(this).siblings('p.openInfo').text();
		$(this).siblings('p.openInfo').text(inputTemp);
		$(this).siblings('input.openInfo').val(pTemp);
		$(this).text('收起').addClass('closeInfo').removeClass('openInfo');
	});
	// 收起
	$(document).on('click','a.closeInfo',function(){
		var inputTemp = $(this).siblings('input.openInfo').val(),
			pTemp = $(this).siblings('p.openInfo').text();
		$(this).siblings('p.openInfo').text(inputTemp);
		$(this).siblings('input.openInfo').val(pTemp);	
		$(this).text('展开').addClass('openInfo').removeClass('closeInfo');
	});
	
	//审核
	$(document).on("click", ".review", function() {
		contentCode = $(this).attr("val");
		popupLayer(
			'',
			'<div style="width: 320px; text-align:center; margin: 0 auto;">是否通过审核？</div>',
			'<button type="button" class="btn-sure sureReview mr15">确定</button>' +
			'<button type="button" class="btn-sure noDele cancel ml15">取消</button>'
		);
	});
	$(document).on("click", ".sureReview", function() {
		var url="${ctx}web/replyManageController/reviewReply";
		var params = {contentCode:contentCode};
		$.post(url, params, function(response){
		  	if(response=="success"){
		  		msgBox("审核成功", "pass", 2000);
		  		loadList(c_page);
		  	}else{
		  		msgBox("审核失败", "erro", 2000);
		  	}
		});
		// 关闭弹出框
		closePopup();
	});//
	
	
	
	
	//删除
	$(document).on("click", ".delete", function() {
		contentCode = $(this).attr("val");
		popupLayer(
			'',
			'<div style="width: 320px; text-align:center; margin: 0 auto;">是否删除？</div>',
			'<button type="button" class="btn-sure sureDele mr15">确定</button>' +
			'<button type="button" class="btn-sure noDele cancel ml15">取消</button>'
		);
	});
	//批量删除{
	$(document).on("click", ".deleteBatch", function() {
		var checkBox = $("input[name='checkBoxContentCode']:checked");
		if(checkBox!=undefined&&checkBox.length>0){
			var array = new Array();
			for(var i=0;i<checkBox.length;i++){
				array.push(checkBox.eq(i).val());
			}
			contentCode = array.join(",");
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">是否删除？</div>',
				'<button type="button" class="btn-sure sureDele mr15">确定</button>' +
				'<button type="button" class="btn-sure noDele cancel ml15">取消</button>'
			);
		}else{
			msgBox("请选择要删除的数据", "info", 2000);
		}
	});
	//删除确定按钮
	$(document).on("click", ".sureDele", function() {
		var url="${ctx}web/replyManageController/deleteReply";
		var params = {contentCodes: contentCode};
		$.post(url, params, function(response){
		  	if(response=="success"){
		  		//loadList(c_page);
		  		loadList(1);
		  		msgBox("删除成功", "pass", 2000);
                $(".allCheck").attr('checked',false);
            }else{
		  		msgBox("删除失败", "erro", 2000);
		  	}
		});
		// 关闭弹出框
		closePopup();
	});//
	
	
	
</script>

<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch"> <!-- IE10+ -->
<link rel="next"> <!-- Firefox -->
<link rel="prerender"> <!-- Chrome -->
</body>
</html>
