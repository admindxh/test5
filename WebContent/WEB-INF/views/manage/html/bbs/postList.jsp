<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>�Ż���ҳ-�����б����</title>
</head>
<body>
<%@include file="/common-html/common.jsp" %>
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
    <!-- main { -->
    <div class="main">
		<!-- ҳ��λ��-->
		<div class="location">
			<label>����ǰλ��Ϊ:</label>
			<span><a>�Ż���ҳ</a> -</span>
			<span><a href="#" target="_self">�����б����</a></span>
		</div>

		<!-- ģ����� { -->
		<div class="muduleManage filament_solid_ddd">

			<!-- ����ť -->
			<div class="searchManage">
				<button type="button" class="btn-sure">��ʾ����</button>
				<button type="button" class="btn-sure">Ԥ��</button>
				<button type="button" class="btn-sure">����</button>
				<button type="button" class="btn-sure">�鿴</button>
			</div>

			<div class="contClassify">
				<h2 class="title">����һ</h2>

				<div class="formLine mt15">
					<label class="w_auto">����:</label>
					<input type="text" class="w-640">
				</div>
			</div>

			<div class="contClassify">
				<h2 class="title">���Ӷ�</h2>

				<div class="formLine mt15">
					<label class="w_auto">����:</label>
					<input type="text" class="w-640">
				</div>
			</div>

			<div class="contClassify">
				<h2 class="title">������</h2>

				<div class="formLine mt15">
					<label class="w_auto">����:</label>
					<input type="text" class="w-640">
				</div>
			</div>

			<div class="contClassify">
				<h2 class="title">������</h2>

				<div class="formLine mt15">
					<label class="w_auto">����:</label>
					<input type="text" class="w-640">
				</div>
			</div>

			<div class="contClassify">
				<h2 class="title">������</h2>

				<div class="formLine mt15">
					<label class="w_auto">����:</label>
					<input type="text" class="w-640">
				</div>
			</div>

		</div><!-- } ģ����� -->
    </div><!-- } main -->
    <!-- ���ÿ���ʱ��Ԥ����ָ��ҳ�� -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>

