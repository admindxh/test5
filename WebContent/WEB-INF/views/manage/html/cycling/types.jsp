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
				<span><a>����ר��</a> -</span>
				<span><a>װ���Ƽ�</a> -</span>
				<span><a>װ�����͹���</a></span>
			</div>

			<%-- 
				<!-- ���ݲ��� -->
				<div class="searchManage">
					<button id="category-add" type="button" class="btn-sure">
						�½����
					</button>
				</div>
			--%>
			
			<!-- ģ����� { -->

			<div class="tableManage pos-rela">
				<form method="post" action="searchMerchantType">
					<!-- ������ѯ�� { -->
					<div style="height: 26px;">
					</div>
				</form>
				<!-- } ������ѯ�� -->
					<!-- �����б� -->
					<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
						<thead>
							<tr>
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
						<tbody  ms-important="infoView" id="tbodyList">
						   <tr ms-if="!data.size()">
			                	<td></td>
			                	<td>
			                		<p style="color: red;text-align: center;">���������������</p>
			                	<td>
               				</tr>
							<tr ms-repeat="data">
								<td >
								{{el.code}}
								</td>
								<td>
								{{el.programaName}}
								</td>
								<td>
									<%--
									<a   ms-click="deletetype(el.code)">ɾ��</a>
									 --%>
									<a class="update" ms-attr-name="el.programaName" ms-attr-code="el.code">�޸�</a>
								</td>
							</tr>
						</tbody>
					</table>
					<div id="infoPage" class="paging"></div>
			</div>
			<!-- } ģ����� -->
		</div>
		<!-- } main -->
<script src="${ctxManage}resources/js/avalon.js" type="text/javascript"></script>
<script src="${ctxManage}resources/js/bootstrap-paginator.js" type="text/javascript"></script>
<script type="text/javascript">

		var originalName = "";

	    function servicesPage(pageId, currentPage, totalPage, call_) {
	        var options = {
	            currentPage: currentPage || 1,
	            totalPages: totalPage || 1,
	            itemTexts: function (type, page, current) {
	                switch (type) {
	                    case "first":
	                        return "��ҳ";
	                    case "prev":
	                        return "��һҳ";
	                    case "next":
	                        return "��һҳ";
	                    case "last":
	                        return "ĩҳ";
	                    case "page":
	                        return page;
	                }
	            },
	            onPageChanged: function (e, oldPage, newPage) {
	                call_(newPage);
	            }
	        };
	        $('#' + pageId).bootstrapPaginator(options);
	    }

	    //������Ʒ�б�{
	    var infoVM = avalon.define({
	        $id: 'infoView',
	        data: [],
	        deletetype: function (code) {
	        	deletetype(code);
	        }
	    });
	    
	    $(document).on("click", ".update", function () {
	    	var name = $(this).attr("name"),
	    		code = $(this).attr("code");
	    	originalName = name;
	    	updatetype(name, code);
	    });

	    function updatetype(name,code){
	    	popupLayer(
					"<h1>�޸�װ�����</h1>", 
					'<div class="formLine mt30 mb30" style="text-align:center">' +
	                '<form action="updateetype" method="post" id="merchantTypeForm" onsubmit="return false">'+
					'<label style="width:auto">װ�����:&nbsp;</label>' +
					'<input id="code" maxlength="30" type="hidden" class="w-200" name="code" value="'+
					code+'">&nbsp;' +
					'<input id="mType" maxlength="30" type="text" class="w-200" name="name" value="'+
					name+'">&nbsp;' +
					'<span class="reqItem">*</span>' +
					'</div>',
					'<button type="button" class="btn-sure sureupdate mr15">ȷ��</button>' +
					'<button type="button" class="btn-sure noAdd cancel ml15">ȡ��</button>'+
					'</form>'
			);
      	}
     
	 	function deletetype(code) {
			//ɾ����ajax	
		  	popupLayer( '',
                '<div style="width: 320px; text-align:center; margin: 0 auto;">��ȷ��Ҫɾ����</div>',
                '<button type="button" class="btn-sure sureDele mr15">ȷ��</button>' +
                '<button type="button" class="btn-sure noDele cancel ml15">ȡ��</button>'
        	);
	        $(document).on('click', '.sureDele', function () {
	            var url = "deleteetype?code=" + code;
			  	$.ajax({
		            type: "POST",
		            url:url ,
		            dataType: "json",
		            success: function (data) {
		            	if(data.state==0){
		            		 msgBox("ɾ���ɹ���", "info", 1000);
		 	            	setTimeout(function(){
		 	            		window.location.href=location.href;
		 	            	},2000);
		            	}
		            	else{
		            		 msgBox(data.content, "erro", 1000);
		            	}
		            }
		        });
				closePopup();
	        });
		}
	    avalon.ready(function () {
	        avalon.scan();
	    });
	    function loadInfoList(currentPage) {
	        var thisCall = loadInfoList;
	        var url = "infosearch.action";
	        // {activityCode:'${activity.code }',currentPage: currentPage, pageSize: 2}
	        var params = $('#paramsfrm').serialize() + "&currentPage=" + currentPage + "&pageSize=20";
	        $.post(url, params, function (response) {
	            servicesPage("infoPage", response.currentPage, response.totalPage, thisCall);
	            infoVM.data = response.dataList;
	        }, 'json');
	    }
		//}
		$(function () {
		    loadInfoList(1);
		    /* �ӳ�0.3��ΪIE8�����Ӹ��б�ɫЧ�� */
		    setTimeout("trAlternateColor('.dataTable')",600);
		});

     $(document).on("click", "#category-add", function () {
   		popupLayer(
			"<h1>�½�װ�����</h1>", 
			'<div class="formLine mt30 mb30" style="text-align:center">' +
            '<form action="addetype" method="post" id="merchantTypeForm" onsubmit="return false;">'+
			'<label style="width:auto">װ�����:&nbsp;</label>' +
			'<input id="mType" maxlength="30" type="text" class="w-200" name="name">&nbsp;' +
			'<span class="reqItem">*</span>' +
			'</div>',
			'<button type="button" class="btn-sure sureAdd mr15">ȷ��</button>' +
			'<button type="button" class="btn-sure noAdd cancel ml15">ȡ��</button>'+
			'</form>'
   		);
    });
     
     $(document).on("click", ".sureupdate", function() {
 		$("#mType").blur();
 		var hasErr = $(".errMesg").length > 0;
 		if(hasErr) {
 			return;
 		} else {
 			if (validateName($("#mType").val())) {
 				validateTypeName("update", function () {
 	 				var $form = $('#merchantTypeForm');
 	 	 		  	var url = 'updateetype?random='+Math.random();
 	 	 		  	var params= $form.serialize();
 	 	 		  	$.ajax({
 	 	 	            type: "POST",
 	 	 	            url: url,
 	 	 	            dataType: "json",
 	 	 	            data: params,
 	 	 	            success: function (data) {
 	 	 	            	if(data.state==0){
 	 	 	            		 msgBox("�޸ĳɹ���", "info", 1000);
 	 	 	            	}
 	 	 	            	else{
 	 	 	            		 msgBox(data.content, "erro", 1000);
 	 	 	            	}
 	 	 	            	closePopup();
		  	 	            setTimeout(function(){
	 	 	 	            	window.location.href=location.href;
		  	 	            },2000);
 	 	 	            }
 	 	 	        });
 				});
 			} else msgBox("�Ѿ�����װ�����" + $("#mType").val() + "��", "info", 1000);
 		}
 	});

     
	$(document).on("click", ".sureAdd", function() {
		$("#mType").blur();
		var hasErr = $(".errMesg").length > 0;
		if(hasErr) {
			return;
		} else {
			validateTypeName("add", function () {
				var $form = $('#merchantTypeForm');
	 		  	var url = 'addetype?random=' + Math.random();
	 		  	var params= $form.serialize();
	 		  	$.ajax({
	 	            type: "POST",
	 	            url: url,
	 	            dataType: "json",
	 	            data: params,
	 	            success: function (data) {
	 	            	if(data.state==0){
	 	            		 msgBox("��ӳɹ���", "info", 1000);
	 	            	}
	 	            	else{
	 	            		 msgBox(data.content, "erro", 1000);
	 	            	}
	 	            	closePopup();
	 	            	setTimeout(function(){
	 	            		window.location.href=location.href;
	 	            	},2000);
	 	            }
	 	        });
			});
		}
	});

	/**
	 * ��֤װ�����������������ѡ�еĵ�Item���ʲ����ظ����֣�
	 *
	 * callback ��֤ͨ���Ļص�����
	 */
	function validateTypeName (execType, callback) {
		var type = $("#mType").val();
		$.post("valid", { programaName : type }, function (response) {
			if (execType == "add") {
				if (response.result == 0) {
					callback();
				} else msgBox("�Ѿ�����װ�����" + type + "��", "info", 1000);
			} else if (execType == "update") {
				if (response.result <= 1) {
					callback();
				} else msgBox("�Ѿ�����װ�����" + type + "��", "info", 1000);
			}
		}, "json");
	}
	
	function validateName (targetName) {
		var name = "";
		var result = true;
		$("#tbodyList .update").each(function () {
			name = $(this).attr("name");
			if (name != originalName) { // �ų���ǰ��
				if (targetName.replace(/\s/g, "") == name) {
					result = false;
					return;
				}
			}
		});
		return result;
	}
	
	$(document).on('blur','#mType',function(){
		var $this = $(this),
			this_val = $this.val();
		valid_txtBox(this, strRange(this_val, 2, 30) && $.trim(this_val) != "",'װ�����ֻ��Ϊ2-30λ�ַ�','top');
	});
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
