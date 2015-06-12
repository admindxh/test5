<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
	<title>游西藏-商户管理-商户信息管理-商户添加</title>
<%@include file="/common-html/commonxz.jsp" %>
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
	<style>
		/* 闭合浮动 */
		.floatfix:after {
			content: "";
			display: table;
			clear: both;
		}
	</style>
</head>

<body>
	<!-- main { -->
	<div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>游西藏</a> -</span>
			<span><a>商户管理</a> -</span>
			<span><a href="commercial-info-manage.html" target="_self">商户信息管理</a> -</span>
			<span><a href="#" target="_self">商户添加</a></span>
		</div>

		<!-- 模块管理 { -->
		<form>
			<!-- 基本信息 { -->
			<div>
				<h2 class="mt40">商户基本信息:</h2>

				<div class="filament_solid_ddd ov-au mt20">

					<div class="formLine mt10">
						<div class="w50p">
							<label>关联区域:</label>
							<label class="w-auto"><span>拉萨地区</span>、<span>林芝地区</span>
							</label>
							<button type="button" class="btn-base ml20">关联</button>
						</div>
					</div>

					<div class="formLine mt10">
						<div class="w50p">
							<label>所属目的地:</label>
							<label class="w-auto"><span>目的地1</span>、<span>目的地2</span>
							</label>
							<button type="button" class="btn-base ml20">关联</button>
						</div>
					</div>

					<div class="formLine mt10">
						<label>关联景点:</label>
						<label class="w-auto"><span>景点1</span>、<span>景点2</span>
						</label>
						<button type="button" class="btn-base ml20">关联</button>
					</div>

					<div class="formLine mt10">
						<label>团游标题:</label>
						<input type="text" class="w-260" />
					</div>

					<div class="formLine mt10">
						<label>团游图片:</label>
						<button type="button" class="btn-base btn-uploadImg">点击上传图片</button>
						<span class="txt-suggest ml20">必须是1000*200尺寸的jpg/png格式</span>
					</div>

					<div class="formLine mt10">
						<label class="pos_r_t5">团游概述:</label>
						<textarea name="" id="" cols="70" rows="5"></textarea>
					</div>

					<div class="formLine mt10 mb20">
						<label class="pos_r_t5">商户详细介绍:</label>
						<textarea name="" id="" cols="70" rows="10"></textarea>
					</div>
				</div>
			</div>
			<!-- } 基本信息 -->

			<!-- 商户预订信息 { -->
			<div>
				<h2 class="mt100">团游预订信息:</h2>

				<div class="filament_solid_ddd ov-au mt20">
					<div class="formLine mt20">
						<label>预定价格:</label>
						<input type="text" class="w-260" />
						<span>起</span>
					</div>
					<div class="formLine mb20">
						<label>预定网址:</label>
						<input type="text" class="w-260" />
					</div>
				</div>
			</div>
			<!-- } 商户预订信息 -->

			<!-- SEO信息 { -->
			<div>
				<h2 class="mt100">页面SEO信息:</h2>

				<div class="filament_solid_ddd ov-au mt20">
					<div class="formLine mt20 mb20">
						<label class="w-160">&lt;Keywords&gt;标签:</label>
						<input type="text" maxlength="" class="w-320">
					</div>
				</div>
			</div>
			<!-- } SEO信息 -->

			<div class="txt-ac mt40">
				<button class="btn-sure" type="button">添加</button>
				<button class="btn-sure ml100" type="button">重置</button>
			</div>
		</form>
		<!-- } 模块管理 -->
	</div>
	<!-- } main -->

	<!-- 利用空闲时间预加载指定页面 -->
	<link rel="prefetch">
	<!-- IE10+ -->
	<link rel="next">
	<!-- Firefox -->
	<link rel="prerender">
	<!-- Chrome -->
</body>

</html>