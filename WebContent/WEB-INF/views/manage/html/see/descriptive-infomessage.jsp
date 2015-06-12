<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>看西藏-图说西藏信息管理</title>
    <%@include file="/common-html/common.jsp" %>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
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

    <script type="text/javascript">
	//单独删除目的地信息
    function deleteMuti(_code){
			   		if(_code==""){
			   		   alert("请选择要删除的图集。");
			   		return;
			   	    }
			   		$.ajax( {
			   			url : contextPath + "/web/mutiController/deleteMuti",
			   			dataType : "json",
			   			type : "post",
			   			data : {
			   				paramJson : "{'code':'" + _code + "'}"
			   			},
			   			async : false, 
			   			success : function(json) {
			   				location.href = "getMutiList";
			   			}
			   		});			   	
    }
    //批量删除目的地信息
    function batchDeleteMuti(){
 		var code = "";
 		$("input[name='code']:checked").each(function(){
 			if(code==""){
 				code = $(this).val();
 				////.log(code);
 			}else{
 				code = (code + ","+$(this).val());
 				////.log(code);
 			}
 		});
 		//删除
 		deleteMuti(code);
    }
    
    </script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location mb40">
        <label>您当前位置为:</label>
        <span><a>看西藏</a> -</span>
        <span><a href="#" target="_self">图说西藏信息管理</a></span>
    </div>

    <!-- 数据操作 -->
    <div class="searchManage">
    	<rimiTag:perm url="web/mutiController/mutiEdit">
        <a href="${ctx }web/mutiController/descriptive-add" target="_self" class="btn-anchor">新建</a>
        </rimiTag:perm>
        
        <button id="batchRemove" type="button" class="btn-sure">批量删除</button>
    </div>
  <form id="listForm" method="post" action="${ctx }web/mutiController/getMutiList" >
    <div class="searchTools">
        <label class="w-auto">关键字:</label>
        <input type="text" name="keywords" maxlength="300" value="${keywords}"  placeholder="请输入关键字搜索"/>
        <button class="btn-search va_m ml20" type="submit"></button>
        <div class="select-base float_r">
        <input name="action" type="hidden" value="${action}">
            <i class="w-140">快捷筛选</i>
            <dl style="">
                <dt name="1">按时间</dt>
                <dt name="2">按查看量</dt>
                <dt name="3">按评论量</dt>
                <dt name="4">按被赞次数</dt>
                <dt name="5">按收藏次数</dt>
            </dl>
        </div>
    </div>
    <input type="hidden" name="programaCode" value="14dba551-cb5b-4631-b5ef-b3838670b3a9" >
    <!-- 模块管理 { -->
    <div class="tableManage pos-rela">
        <!-- 数据列表 -->
        <table border="0" cellpadding="0" cellspacing="0" class="dataTable">
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="dataCheck" class="allCheck">
                    全选
                </th>
                <th>创建时间</th>
                <th>编号</th>
                <th>标题</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="muti" items="${pager.dataList}" varStatus="status">
            <tr>
                <td>
                    <input type="checkbox" name="code" value="${muti.code}" class="dataCheck">
                </td>
<!--                 HH:mm:ss -->
                <td><fmt:formatDate value="${muti.createTime}" pattern="yyyy-MM-dd "/></td>
                <td>${muti.code }</td>
                <td><a href="${ctx}${muti.hyperlink}" target="new"><span class="maxW460">${muti.name }</span></a></td>
                <td>
               		 <rimiTag:perm url="web/mutiController/mutiEdit">
                    <a href="${ctx }web/mutiController/mutiEdit?code=${muti.code}">编辑</a>
                    </rimiTag:perm>
                    <rimiTag:perm url="web/mutiController/deleteMuti">
                    <a onclick="deleteView('${muti.code}')" class="deleInfo">删除</a>
                    </rimiTag:perm>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        	</table>
        <!-- 表格分页 -->
         	    <c:if test="${empty pager.dataList}">
					<p style="color: red;text-align: center;">暂无相关搜索数据</p>
		 		</c:if>
			     <c:if test="${not empty pager.dataList}">
						<div class="paging">
    	                   <%@include file="/common-html/pager.jsp" %>
                        </div> 
				</c:if>
    		</div><!-- } 模块管理 -->
			</form>
</div><!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
<script>
    //======================================
    //				删除类别
    //======================================
    function deleteView(code){
		popupLayer(
			'',
			'<div style="width: 320px; text-align:center; margin: 0 auto;">您确定要删除？</div>',
			'<button type="button" class="btn-sure sureDele mr15">确定</button>' +
			'<button type="button" class="btn-sure noDele cancel ml15">取消</button>'
		);
		$(document).one('click', '.sureDele', function(){
			deleteMuti(code);
			closePopup();
		});
	}
    // 取消删除
    $(document).on("click", ".noDele", function() {
        var deleTr = $(".dataTable").find("tr.dele-waiting");
        // 消除删除状态
        deleTr.removeClass("dele-waiting");
    }); 

    // 批量确定删除
    $(document).on("click",".confirm",function(){
    	batchDeleteMuti();
		
        closePopup();
    });
    
    $("#batchRemove").on('click',function(){
        var trChecked = $('.dataTable').find("tr.trChecked");
        if(trChecked.length){
            popupLayer(
                    '',
                    '<div style="width: 320px; text-align:center; margin: 0 auto;">您确定要删除？</div>',
                    '<button type="button" class="btn-sure confirm mr15">确定</button>' +
                    '<button type="button" class="btn-sure cancel ml15">取消</button>'
            );
        }else{
            msgBox("请勾选需要删除的板块","info",1000);
        }
    })
    $(".select-base dl dt").on('click',function(){
			setTimeout(function(){$("#listForm").submit();},10);		
		});
</script>
<script type="text/javascript">
$(function(){
	var $select = $('.select-base'), $val = $select.find('input[name="action"]').val(),
		$text = $select.find('dt[name="' + $val + '"]').text()
		$select.find('i').text($text)
})
</script>
<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch"> <!-- IE10+ -->
<link rel="next"> <!-- Firefox -->
<link rel="prerender"> <!-- Chrome -->
</body>
</html>

