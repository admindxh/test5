<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<title>游西藏-商户管理-商户类别管理</title>
		<%@include file="/common-html/commonxz.jsp"%>
		<script type="text/javascript" src="<%=basePath%>manage/resources/js/dataValidation.js"></script>
	</head>

	<body>
		<!-- main { -->
		<div class="main">
			<!-- 页面位置-->
			<div class="location">
				<label>
					您当前位置为:
				</label>
				<span><a>游西藏</a> -</span>
				<span><a>商户管理</a> -</span>
				<span><a>商户类别管理</a> </span>
			</div>

			<!-- 数据操作 -->
			<div class="searchManage">
				<button id="category-add" type="button" class="btn-sure">
					新建类别
				</button>
				<button type="button" class="btn-sure"
					onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/merchant/deleteMerchantType','code')">
					批量删除
				</button>
			</div>

			<!-- 模块管理 { -->

			<div class="tableManage pos-rela">
				<form method="post" action="searchMerchantType">
					<!-- 搜索查询栏 { -->
					<div class="searchTools">

						<label class="va_m">
							类别名:
						</label>

						<input type="text" placeholder="请输入名称进行关键字搜索" name="keyWord"
							value="${keyWord}" >
						<button type="submit" class="btn-search">
							<!--查询-->
						</button>
					</div>
				</form>
				<!-- } 搜索查询栏 -->
				<form method="post" action="getMerchantTypeList" id="listForm">
					<!-- 数据列表 -->
					<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
						<thead>
							<tr>
								<th class="w-120">
									<input type="checkbox" name="dataCheck" class="allCheck mr5">
									全选
								</th>
								<th>
									编号
								</th>
								<th>
									类别名
								</th>
								<th>
									操作
								</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${empty pager.dataList}">
								<tr>
									<td>
										暂无数据
									</td>
								</tr>
							</c:if>
							<c:if test="${not empty pager.dataList}">
								<c:forEach items="${pager.dataList}" var="merchantType"
									varStatus="count">
									<tr>
										<td>
											<input type="checkbox" class="dataCheck" name="dataCheck"
												value="${merchantType.code}">
										</td>
										<td>
											${count.index+1}
										</td>
										<td>
											${merchantType.name }
										</td>
										<td>
											<a onclick="deleteM('${merchantType.code}',this)" href="#">删除</a>
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</form>
				<!-- 表格分页 -->
				<div class="paging">
					<%@include file="/common-html/pager.jsp"%>
				</div>



			</div>
			<!-- } 模块管理 -->
		</div>
		<!-- } main -->

<%--		<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"--%>
<%--			type="text/javascript"></script>--%>
<%--		<script src="${ctx}/common-js/common.js" type="text/javascript"></script>--%>
<%--		<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"--%>
<%--			type="text/javascript"></script>--%>
<%--		<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>--%>
<%--		<script src="${ctxManage}/resources/js/base-tabel.js"--%>
<%--			type="text/javascript"></script>--%>
<%--		<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"--%>
<%--			type="text/javascript"></script>--%>
<%--		<script src="${ctxManage}/resources/js/dataValidation.js"--%>
<%--			type="text/javascript"></script>--%>
		<script type="text/javascript">
		 //删除的确认框
		function deleteM(plateCode,el){
			  popupLayer(
      				'',
      				'<div style="text-align:center"><br>是否删除？</div>',
      				'<div class="formLine">' +
      				'<button type="button" class="btn-sure sureDelOtherMud mr20">确定</button>' +
      				'<button type="button" class="btn-sure cancelDelOtherMud cancel ml20">取消</button>' +
      				'</div>'
      	  );
      	  $(document).off("click",".sureDelOtherMud").on("click",".sureDelOtherMud",function(){
               var $tr = $(el).parents('tr');
               $.ajax( {
       			url : "${ctx}/web/merchant/ajaxDeleteMerchantType",
       			dataType : "json",
       			type:"post",
       			data : {
       				"code" : plateCode
       			},
       			async : false,
       			success : function(data) {
           			//.log("sdsddsds");
           			if(data=='success'){
           				msgBox("删除成功！",'pass',1000);
           				setTimeout(function(){
           					location.href="${ctx}/web/merchant/getMerchantTypeList";
           				},1200);
           			}
           			else if(data=='faied'){
           				msgBox("旗下有商户不能删除！",'pass');
<%--           				location.href="${ctx}/web/merchant/getMerchantTypeList";--%>
               		}
       			},
       			error : function(data) {
           			//.log("sdsddsds");
       				location.href="${ctx}/web/merchant/getMerchantTypeList";
       			}
       		});
      	  closePopup();
          	});
		}
		//======================================
		//				删除类别
		//======================================
		// 弹出准备删除框
		$(document).on("click", ".dataTable .dele", function() {
			var $this = $(this);
			// 设置删除状态
			$this.parents("tr").addClass("dele-waiting");
			// 弹出确认删除框
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">删除该分类会将删除其下面商户<br>是否删除？</div>',
				'<button type="button" class="btn-sure sureDele mr15">确定</button>' +
				'<button type="button" class="btn-sure noDele cancel ml15">取消</button>'
			);
		});
		
		// 确定删除（从视觉界面上删除了这一行）
		$(document).on("click", ".sureDele", function() {
			var deleTr = $(".dataTable").find("tr.dele-waiting");
			// 移除该行
			deleTr.remove();
			// 关闭弹出框
			closePopup();
		});
		
		// 取消删除
		$(document).on("click", ".noDele", function() {
			var deleTr = $(".dataTable").find("tr.dele-waiting");
			// 消除删除状态
			deleTr.removeClass("dele-waiting");
		});
		
		//======================================
		//				新建类别
		//======================================
		// 弹出新建框（后台可以为元素添加各种标识符，和为元素添加事件触发，以便于功能的实现。）
		$("#category-add").click(function() {
			popupLayer(
				"<h1>新建商户类别</h1>", 
				'<div class="formLine mt30 mb30" style="text-align:center">' +
                '<form action="saveOrUpdateMerchantType" method="post" id="merchantTypeForm" onsubmit="return false">'+
				'<label style="width:auto">商户类别:&nbsp;</label>' +
				'<input id="mType" type="text" class="w-200" name="name">&nbsp;' +
				'<span class="reqItem">*</span>' +
				'</div>',
				'<button type="button" class="btn-sure sureAdd mr15">确定</button>' +
				'<button type="button" class="btn-sure noAdd cancel ml15">取消</button>'+
				'</form>'
			);
		});
		
		// 确定创建验证（确定验证通过后的事件需做后台功能的时候添入）
		$(document).on("click", ".sureAdd", function() {
			$("#mType").blur();
			
			var hasErr = $(".errMesg").length > 0;
			if(hasErr) {
				return;
			} else {
				
				saveMerchantType();
			}
		});
		
		// 消除验证提示
		$(document).on("click", "#category dt", function() {
			var $this = $(this);
			$this.parents(".select-base").next(".errMesg").text("*").removeClass("errMesg");
		});

		// 新增商户类型别失焦验证
		$(document).on('blur','#mType',function(){
			var $this = $(this),
				this_val = $this.val();
			valid_txtBox(this, strRange(this_val, 2, 30) && $.trim(this_val) != "",'商户类别只能为2-30位字符','top');
		});
		
function saveMerchantType(){
	if($('span.errMesg').length == 0){
		var $form = $('#merchantTypeForm');
	  	var url = '${ctx}web/merchant/saveOrUpdateMerchantType?random='+Math.random();
	  	$.post(url, $form.serialize(), function(data){
	  		closePopup();
	  		window.location.href="${ctx}web/merchant/getMerchantTypeList";
		 });
	  
	}
}

$(function(){
	var  pageInfo =  '<div class="paging-info">'+
			    		'<span class="disp-ib">当前第&nbsp;${pager.currentPage }&nbsp;页</span>'+
			    		'<span class="disp-ib">共&nbsp;${pager.totalPage }&nbsp;页</span>'+
			    		'<span class="disp-ib">共&nbsp;${pager.totalCount }&nbsp;条</span>' +
			    		'</div>';
	$("#pageInfo").append(pageInfo);

	if(${nameEx =='1'}){
	   msgBox("已经添加了相同的商户类型",'erro',2000);
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
