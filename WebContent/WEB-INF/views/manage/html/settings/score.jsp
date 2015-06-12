<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
<%@ include file="/common-html/common.jsp" %>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>系统设置-积分管理</title>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>

<body>
	<!-- main { -->
	<div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>系统设置</a> -</span>
			<span><a>积分管理</a> </span>
		</div>
		<!-- 数据操作 -->
		<div class="searchManage">
			<button id="saveScoreManag" type="button" class="btn-sure" onclick="saveScore()" >保存</button>
		</div>
		<!-- 模块管理 { -->
		<div class="tableManage pos-rela mt-60">
		 <form action="${ctx }/web/scoreManagerController/updateScore" id="form1" method="post">
			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th class="w-50p">积分规则</th>
						<th>积分</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="sm">
						<tr>
							<td>${sm.scorename }</td>
							<td>
								<input name="ids" type="hidden" value="${sm.id}" />
								<input name="scorecount" type="text" class="w-120 number" value="${sm.scorecount }" maxlength="7"/>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</form>
		</div>
		<!-- } 模块管理 -->
	</div>
	<!-- } main -->

	<!-- JS引用部分 -->
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
	<script>
		$(function(){
			$('[name="scorecount"]').blur(function(){
				var $this = $(this),
					thisVal = $this.val(),
					regtest = $.VLDTOR.IsNum(thisVal);
				valid_txtBox_create(this,regtest,"只能为正整数",'right');
			});
		});

		function saveScore(){
			$('[name="scorecount"]').blur();
			var errorSpan = $('span.errMesg').length > 0;
			if(errorSpan){
				msgBox('输入的内容有误，请检查！','erro',1600);
			}else{
				$("#form1").submit();
				msgBox("保存成功!","pass",1000);
			}
			
		}
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