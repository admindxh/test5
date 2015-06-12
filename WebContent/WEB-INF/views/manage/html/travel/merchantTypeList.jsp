<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<title>������-�̻�����-�̻�������</title>
		<%@include file="/common-html/commonxz.jsp"%>
		<script type="text/javascript" src="<%=basePath%>manage/resources/js/dataValidation.js"></script>
	</head>

	<body>
		<!-- main { -->
		<div class="main">
			<!-- ҳ��λ��-->
			<div class="location">
				<label>
					����ǰλ��Ϊ:
				</label>
				<span><a>������</a> -</span>
				<span><a>�̻�����</a> -</span>
				<span><a>�̻�������</a> </span>
			</div>

			<!-- ���ݲ��� -->
			<div class="searchManage">
				<button id="category-add" type="button" class="btn-sure">
					�½����
				</button>
				<button type="button" class="btn-sure"
					onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/merchant/deleteMerchantType','code')">
					����ɾ��
				</button>
			</div>

			<!-- ģ����� { -->

			<div class="tableManage pos-rela">
				<form method="post" action="searchMerchantType">
					<!-- ������ѯ�� { -->
					<div class="searchTools">

						<label class="va_m">
							�����:
						</label>

						<input type="text" placeholder="���������ƽ��йؼ�������" name="keyWord"
							value="${keyWord}" >
						<button type="submit" class="btn-search">
							<!--��ѯ-->
						</button>
					</div>
				</form>
				<!-- } ������ѯ�� -->
				<form method="post" action="getMerchantTypeList" id="listForm">
					<!-- �����б� -->
					<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
						<thead>
							<tr>
								<th class="w-120">
									<input type="checkbox" name="dataCheck" class="allCheck mr5">
									ȫѡ
								</th>
								<th>
									���
								</th>
								<th>
									�����
								</th>
								<th>
									����
								</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${empty pager.dataList}">
								<tr>
									<td>
										��������
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
											<a onclick="deleteM('${merchantType.code}',this)" href="#">ɾ��</a>
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</form>
				<!-- ����ҳ -->
				<div class="paging">
					<%@include file="/common-html/pager.jsp"%>
				</div>



			</div>
			<!-- } ģ����� -->
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
		 //ɾ����ȷ�Ͽ�
		function deleteM(plateCode,el){
			  popupLayer(
      				'',
      				'<div style="text-align:center"><br>�Ƿ�ɾ����</div>',
      				'<div class="formLine">' +
      				'<button type="button" class="btn-sure sureDelOtherMud mr20">ȷ��</button>' +
      				'<button type="button" class="btn-sure cancelDelOtherMud cancel ml20">ȡ��</button>' +
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
           				msgBox("ɾ���ɹ���",'pass',1000);
           				setTimeout(function(){
           					location.href="${ctx}/web/merchant/getMerchantTypeList";
           				},1200);
           			}
           			else if(data=='faied'){
           				msgBox("�������̻�����ɾ����",'pass');
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
		//				ɾ�����
		//======================================
		// ����׼��ɾ����
		$(document).on("click", ".dataTable .dele", function() {
			var $this = $(this);
			// ����ɾ��״̬
			$this.parents("tr").addClass("dele-waiting");
			// ����ȷ��ɾ����
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">ɾ���÷���Ὣɾ���������̻�<br>�Ƿ�ɾ����</div>',
				'<button type="button" class="btn-sure sureDele mr15">ȷ��</button>' +
				'<button type="button" class="btn-sure noDele cancel ml15">ȡ��</button>'
			);
		});
		
		// ȷ��ɾ�������Ӿ�������ɾ������һ�У�
		$(document).on("click", ".sureDele", function() {
			var deleTr = $(".dataTable").find("tr.dele-waiting");
			// �Ƴ�����
			deleTr.remove();
			// �رյ�����
			closePopup();
		});
		
		// ȡ��ɾ��
		$(document).on("click", ".noDele", function() {
			var deleTr = $(".dataTable").find("tr.dele-waiting");
			// ����ɾ��״̬
			deleTr.removeClass("dele-waiting");
		});
		
		//======================================
		//				�½����
		//======================================
		// �����½��򣨺�̨����ΪԪ����Ӹ��ֱ�ʶ������ΪԪ������¼��������Ա��ڹ��ܵ�ʵ�֡���
		$("#category-add").click(function() {
			popupLayer(
				"<h1>�½��̻����</h1>", 
				'<div class="formLine mt30 mb30" style="text-align:center">' +
                '<form action="saveOrUpdateMerchantType" method="post" id="merchantTypeForm" onsubmit="return false">'+
				'<label style="width:auto">�̻����:&nbsp;</label>' +
				'<input id="mType" type="text" class="w-200" name="name">&nbsp;' +
				'<span class="reqItem">*</span>' +
				'</div>',
				'<button type="button" class="btn-sure sureAdd mr15">ȷ��</button>' +
				'<button type="button" class="btn-sure noAdd cancel ml15">ȡ��</button>'+
				'</form>'
			);
		});
		
		// ȷ��������֤��ȷ����֤ͨ������¼�������̨���ܵ�ʱ�����룩
		$(document).on("click", ".sureAdd", function() {
			$("#mType").blur();
			
			var hasErr = $(".errMesg").length > 0;
			if(hasErr) {
				return;
			} else {
				
				saveMerchantType();
			}
		});
		
		// ������֤��ʾ
		$(document).on("click", "#category dt", function() {
			var $this = $(this);
			$this.parents(".select-base").next(".errMesg").text("*").removeClass("errMesg");
		});

		// �����̻����ͱ�ʧ����֤
		$(document).on('blur','#mType',function(){
			var $this = $(this),
				this_val = $this.val();
			valid_txtBox(this, strRange(this_val, 2, 30) && $.trim(this_val) != "",'�̻����ֻ��Ϊ2-30λ�ַ�','top');
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
			    		'<span class="disp-ib">��ǰ��&nbsp;${pager.currentPage }&nbsp;ҳ</span>'+
			    		'<span class="disp-ib">��&nbsp;${pager.totalPage }&nbsp;ҳ</span>'+
			    		'<span class="disp-ib">��&nbsp;${pager.totalCount }&nbsp;��</span>' +
			    		'</div>';
	$("#pageInfo").append(pageInfo);

	if(${nameEx =='1'}){
	   msgBox("�Ѿ��������ͬ���̻�����",'erro',2000);
	}
})
	</script>

		<!-- ���ÿ���ʱ��Ԥ����ָ��ҳ�� -->
		<link rel="prefetch">
		<!-- IE10+ -->
		<link rel="next">
		<!-- Firefox -->
		<link rel="prerender">
		<!-- Chrome -->
	</body>

</html>
