<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>系统设置-敏感词管理</title>
    <%@include file="/common-html/common.jsp" %>
	  <!-- JS引用部分 -->
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
</head>

<body>
	<!-- main { -->
	<div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>系统设置</a> -</span>
			<span><a>敏感词管理</a> </span>
		</div>

		<!-- 数据操作 -->
		<div class="searchManage">
			<button id="batchDele" type="button" onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/badwords/delete','codes')" class="btn-sure">批量删除</button>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<div class="searchTools">
               <form name="addB" method="post" action="${ctx}/web/badwords/save">
				<label class="va_m">敏感词:</label>
				<input type="text" name="badword" id="badword" maxlength="30" placeholder="请输入要添加的敏感词">

				<button type="button" onclick="saveB();" class="btn-base ml10">添加</button>
				
				<span class="txt-suggest ml10">提示：每次可以添加一个敏感词</span>
               </form>
			</div>
			<form id="listForm" method="post" action="${ctx }web/badwords/list"></form>
			<!-- } 搜索查询栏 -->

			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th class="w-120">
							<input type="checkbox" id="allChk" name="dataCheck" class="allCheck mr5">全选
						</th>
						<th>编号</th>
						<th>敏感词</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				  <c:forEach var="s" items="${pager.dataList}" varStatus="i">
					<tr>
						<td>
							<input type="checkbox" name="dataCheck" onchange="cancelAll(this);" value="${s.code }" class="dataCheck">
						</td>
						<td>${i.count }</td>
						<td>${s.badword }</td>
						<td>
							<a class="dele" onclick="deletebySingle('${s.code}','${ctx}/web/badwords/deleteSingle','code')">删除</a>
						</td>
					</tr>
				  </c:forEach>
				</tbody>
			</table>
          <div style="float: left;color: gray;margin-top: 15px;">当前第${pager.currentPage }页  共${pager.totalPage }页 共${pager.totalCount }条</div>
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

	<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
	<script type="text/javascript">

		/* 表格全选按钮功能 */
		$(document).on("click", "input[type='checkbox'].allCheck", function() {
			//dataAllCheck(".allCheck");
			 var chk=document.getElementsByName("dataCheck");
			if($("input[type='checkbox'].allCheck").prop("checked")){
		     for(var i=0;i<chk.length;i++){
		        chk[i].checked=true;
		     }
		    }else{
		       for(var i=0;i<chk.length;i++){
		        chk[i].checked=false;
		     }
		    }
		});
		/**
		 * 功能：数据全选复选框（动态生成的table）
		 * 参数：1.表单元素标识符
		 **/
		function dataAllCheck(checkbox) {
			$("input[type='checkbox'].dataCheck").prop("checked", $(checkbox).prop("checked"));
			// 若存在class标识符则移除，否则就添加该class标识符
			if ($("input[type='checkbox'].dataCheck").parents("tr").attr("class") == "trChecked") {
				$("input[type='checkbox'].dataCheck").parents("tr").removeClass("trChecked");
			} else {
				$("input[type='checkbox'].dataCheck").parents("tr").addClass("trChecked");
			}
		}
	   function	cancelAll(){
	     var chk=$(".dataCheck");
	     
	     for(var i=0;i<chk.length;i++){
	        if(!chk[i].checked){
	            $("#allChk").prop("checked",false);//.attr("checked","false");
	            break;
	        }else{
	          $("input[type='checkbox'].allCheck").prop("checked","true");
	        }
	     }
	   }
		function saveB(){
		  var word=$('#badword').val();
		  if(word==''){
		     msgBox("请输入敏感词！", "erro", 2600);
		     return;
		  }
		  if(word.length<2||word.length>30){
		     msgBox("敏感词为2-30位字符！", "erro", 2600);
		     return;
		  }
		  document.forms.addB.submit();
		}
		
		$(function(){
		  if(${error=='1'}){
		     msgBox("敏感词不能为空！", "erro", 2600);
		  }
		  if(${error=='2'}){
		     msgBox("已添加过相同敏感词！", "erro", 2600);
		  }
		  if(${saveflag=='1'}){
		    msgBox("保存成功！", "pass", 2600);
		  }
		  if(${saveflag=='0'}){
		    msgBox("保存失败！", "erro", 2600);
		  }
		  if(${noCode=='1'}){
		    msgBox("code不能为空！", "erro", 2600);
		  }
		  if(${delflag=='1'}){
		    msgBox("删除成功！", "pass", 2600);
		  }
		  if(${delflag=='0'}){
		    msgBox("删除失败！", "erro", 2600);
		  }
		})
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
