<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>游西藏攻略管理</title>
	<%@include file="/common-html/common.jsp" %>
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
     <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
	<script type="text/javascript">

		
		//显示最新
		function  showNew(){
			window.location.href="${ctx}/web/indexGlDtManagerController/showNew";
			msgBox("显示最新数据成功!","pass",1000);
		}
		//显示保存
		function  save1(saveType,avaliable){
			$(".to-link").blur();
			$(".number").blur();
			var regSpan = $("span.errMesg").length > 0;
			if(regSpan){
				msgBox("填写的信息有误，请检查","erro",1000);
			}else{
					if(saveType=="preview"){
						$("#"+"saveForm").attr("target","_blank");
					}else
					      {
							$("#"+"saveForm").attr("target","");
							msgBox("保存成功!","pass",1000);
					}
					$("#saveForm").attr("action","${ctx}/web/indexGlDtManagerController/save?saveType="+saveType+"&avaliable="+avaliable);
					$("#saveForm").submit();
					
			   }
		}
	
	
	</script>
</head>
<body>
	<!-- main { -->
    <div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a href="home.html" target="_self">门户首页</a> -</span>
			<span><a href="#" target="_self">游西藏攻略管理</a></span>
		</div>
		
		
		
		<!-- 模块管理 { -->
		<div class="muduleManage filament_solid_ddd">

			<!-- 管理按钮 -->
			<div class="searchManage">
				<button type="button" class="btn-sure"  onclick="showNew()">显示最新</button>
			    <button type="button" class="btn-sure"   onclick="javascript:save1('preview','0')">预览</button>
				<button type="button" class="btn-sure"  onclick="javascript:window.open('${ctx}')">查看</button>
				<button type="button" class="btn-sure"  onclick="javascript:save1('save','1')">保存</button>
			</div>
			<form  id="saveForm"  target="_blank" method="post" action="${ctx}/web/indexGlDtManagerController/save">
			<!-- 新闻一 { -->
			<div class="contClassify">
				<h2 class="title">攻略一</h2>

				<div class="formLine mt15">
					<label>链接:</label>
					<input type="text" value="${list[0].url}" name="urls" class="w-320 to-link">
					<label class="w-auto">或编号:</label>
					<input type="text" value="${list[0].number}" name="numbers" class="w-200 number">
					<input type="hidden" value="${list[0].code}" name="codes" class="w-320">
				</div>
			</div>

			<!-- 攻略二 { -->
			<div class="contClassify">
				<h2 class="title">攻略二</h2>

				<div class="formLine mt15">
					<label>链接:</label>
					<input type="text" value="${list[1].url}"  name="urls" class="w-320 to-link">
					<label class="w-auto">或编号:</label>
					<input type="text" value="${list[1].number}" name="numbers" class="w-200 number">
					<input type="hidden" value="${list[1].code}" name="codes" class="w-320">
				</div>
			</div>

			<!-- 攻略三 { -->
			<div class="contClassify">
				<h2 class="title">攻略三</h2>

				<div class="formLine mt15">
					<label>链接:</label>
					<input type="text"  value="${list[2].url}" name="urls" class="w-320 to-link">
					<label class="w-auto">或编号:</label>
					<input type="text" value="${list[2].number}" name="numbers" class="w-200 number">
					<input type="hidden" value="${list[2].code}" name="codes" class="w-320">
				</div>
			</div>
			<!-- 攻略四 { -->
			<div class="contClassify">
				<h2 class="title">攻略四</h2>
				
				<div class="formLine mt15">
					<label>链接:</label>
					<input type="text" value="${list[3].url}" name="urls" class="w-320 to-link">
					<label class="w-auto">或编号:</label>
					<input type="text" value="${list[3].number}" name="numbers" class="w-200 number">
					<input type="hidden" value="${list[3].code}" name="codes" class="w-320">
				</div>
				</div>
			</form>
		</div><!-- } 模块管理 -->
    </div><!-- } main -->

    <!-- JS引用部分 -->
    
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/dataValidation.js"></script>
	<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
    <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
	<script>
		$(".to-link").blur(function () {
//			var thisVal = $(this).val(),
//					nextVal = $(this).siblings('.number').val(),
//					nextValLen = nextVal.length > 0;
//			var regTest = $.VLDTOR.IsWebUrl(thisVal),
//					regNull = $.trim(thisVal) != "",
//					thisValClip = thisVal.split('code=')[1];
//			if(nextValLen){
//				valid_txtBox_create(this,regTest,'不能为中文或空格','top');
//				if(regTest && regNull){
//					if(thisValClip){
//						$(this).siblings('.number').val(thisValClip);
//					}
//				}
//			}else{
//				valid_txtBox_create(this,regTest && regNull,'不能为中文或空格,且与编号必填一项','top');
//				if(regTest && regNull){
//					$(this).siblings('.number').next('span.errMesg').remove();
//					if(thisValClip){
//						$(this).siblings('.number').val(thisValClip);
//					}
//				}
//			}
			var thisVal = $(this).val(),
				reg_test = $.VLDTOR.IsWebUrl(thisVal);
			valid_txtBox_create(this, reg_test || thisVal == "",'不能为纯空格','top');
		});
		$(".number").blur(function () {
//			var thisVal = $(this).val(),
//					prevVal = $(this).siblings('.to-link').val(),
//					preValLen = prevVal.length > 0;
//			var regTest = $.VLDTOR.IsEnNum(thisVal),
//					regNull = $.trim(thisVal) != "";
//			if(preValLen){
//				valid_txtBox_create(this,regTest || thisVal == "",'只能为数字、英文或空','top');
//			}else{
//				valid_txtBox_create(this,regTest && regNull,'只能为数字、英文,且与连接必填一项','top');
//				if(regTest && regNull){
//					$(this).siblings('.to-link').next('span.errMesg').remove();
//				}
//			}
			var thisVal = $(this).val(),
				reg_test = $.VLDTOR.IsEnNum(thisVal);
			valid_txtBox_create(this, reg_test || thisVal == "",'只能为数字、英文或空','top');
		});
	
	</script>
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->	
</body>
</html>