<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
	String path = request.getContextPath();
String port = ":" + request.getServerPort();
if ("80".equals(""+request.getServerPort())) {
	port = "";
}
String basePath = request.getScheme() + "://"
		+ request.getServerName() + port + path + "/";
	request.setAttribute("ctx", basePath);
	request.setAttribute("ctxManage", basePath + "/manage/");
	request.setAttribute("ctxPortal", basePath + "portal/"); //xiangwq
%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>ϵͳ����-��ɫȨ�޹���-��ɫ����</title>
		<link rel="stylesheet"
			href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
		<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
		<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/plugin/respond.min.js"
			type="text/javascript"></script>
		<script src="${ctx}/common-js/common.js" type="text/javascript"></script>
	</head>

	<body>
		<!-- main { -->
		<div class="main">
			<!-- ҳ��λ��-->
			<div class="location">
				<label>
					����ǰλ��Ϊ:
				</label>
				<span><a>ϵͳ����</a> -</span>
				<span><a>��ɫȨ�޹���</a> -</span>
				<span><a>��ɫ����</a> </span>
			</div>

			<!-- ���ݲ��� -->
			<div class="searchManage">
				<a href="${ctx}/web/role/gotoSaveOrUpdateRole" class="btn-anchor" target="_self">�½���ɫ</a>
				<button type="button" class="btn-sure" onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/role/deleteRole','code')">
					����ɾ��
				</button>
			</div>

			<!-- ģ����� { -->
			<div class="tableManage pos-rela">
				<!-- ������ѯ�� { -->
				<div class="searchTools">
					<form method="post" action="roleList" id="listForm">
                        <label class="va_m">
                            ��ɫ��:
                        </label>
                        <input id="roleName" type="text" placeholder="" name="keyWord" value="${keyWord}">

                        <button type="submit" class="btn-search"><!--��ѯ--></button>
                    </form>
				</div>
				<!-- } ������ѯ�� -->

				<!-- �����б� -->
				<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
					<thead>
						<tr>
							<th class="w-80">
								<input type="checkbox"  class="allCheck mr5">
								ȫѡ
							</th>
							<th class="w-80">
								���
							</th>
							<th>
								��ɫ��
							</th>
							<th>
								Ȩ��
							</th>
							<th>
								����
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${roles.dataList}" var="role" varStatus="count">
							<tr>
								<td>
									<input type="checkbox" name="dataCheck" class="dataCheck" value="${role.code}">
								</td>
								<td>
									${role.code}
								</td>
								<td>
									<span class="ellipsis maxW180">${role.rolename}</span>
								</td>
								<td >
									<span class="ellipsis maxW1100" style="width: 1000px">${role.resouce}</span>
								</td>
								<td>
									<a class="edit" href="gotoSaveOrUpdateRole?code=${role.code}">�༭</a>
									<a class="shanc" roleCode="${role.code}">ɾ��</a>
								</td>
							</tr>
						</c:forEach>
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

		<!-- JS���ò��� -->
		<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/base-tabel.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/dataValidation.js"
			type="text/javascript"></script>
		<script type="text/javascript">
		//======================================
		//				ɾ�����
		//======================================
		var $this = "";
		// ����׼��ɾ����
		$(document).on("click", ".dataTable .shanc", function() {
			$this = $(this);
			// ����ɾ��״̬
			$this.parents("tr").addClass("dele-waiting");
			// ����ȷ��ɾ����
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">��ȷ��Ҫɾ����ѡ���ݣ�</div>',
				'<button type="button" class="btn-sure sureDele mr15">ȷ��</button>' +
				'<button type="button" class="btn-sure noDele cancel ml15">ȡ��</button>'
			);
		});
		
		// ȷ��ɾ�������Ӿ�������ɾ������һ�У�
		$(document).on("click",".sureDele", function() {
			var deleTr = $(".dataTable").find("tr.dele-waiting");
			var roleCode  = $this.attr("roleCode") ;
			console.log(roleCode+"ddddd");
			$.ajax({
				type:'post',//��ѡget
				url:'${ctx}/web/role/deleteRole',//�����ǽ������ݵ�PHP����
				data:'code='+roleCode,//����PHP�����ݣ����������&����
				dataType:'text',//���������ص��������� ��ѡXML ,Json jsonp script html text��
				success:function(msg){
						// �Ƴ�����
						deleTr.remove();
						// �رյ�����
						closePopup();
				}
			})
		});
		
		// ȡ��ɾ��
		$(document).on("click", ".noDele", function() {
			var deleTr = $(".dataTable").find("tr.dele-waiting");
			// ����ɾ��״̬
			deleTr.removeClass("dele-waiting");
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
