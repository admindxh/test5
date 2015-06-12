
<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
      <title>门户首页-帖子列表管理</title>
	<%@include file="/common-html/common.jsp" %>
       <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
     <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
   
	<script type="text/javascript">

		
		//显示最新
		function  showNew(){
			window.location.href="${ctx}/web/indexDzDtManagerController/showNew";
			msgBox("显示最新数据成功!","pass",1000);
		}
		//显示保存
		function  save(saveType,avaliable){
			$(".to-link").blur();
			var regSpan = $("span.errMesg").length > 0;
			if(regSpan) {
				msgBox("填写的信息有误，请检查", "erro", 1000);
			}else{
				if(saveType=="preview"){
					$("#"+"saveForm").attr("target","_blank");
					}else{
						$("#"+"saveForm").attr("target","");
						}
				$("#saveForm").attr("action","${ctx}/web/indexDzDtManagerController/save?saveType="+saveType+"&avaliable="+avaliable);
				$("#saveForm").submit();
				msgBox("保存成功!","pass",1000);
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
			<span><a href="#" target="_self">帖子列表管理</a></span>
		</div>
		
		
		
		<!-- 模块管理 { -->
		<div class="muduleManage filament_solid_ddd">
			<!-- 管理按钮 -->
			<div class="searchManage">
				<button type="button" class="btn-sure"  onclick="showNew()">显示最新</button>
			    <button type="button" class="btn-sure"   onclick="save('preview','0')">预览</button>
				<button type="button" class="btn-sure" onclick="javascript:window.open('${ctx}')">查看</button>
				
				<button type="button" class="btn-sure"  onclick="save('save','1')">保存</button>
			</div>
			<form  id="saveForm" target="_blank" method="post"  action="${ctx}/web/indexDzDtManagerController/save">
			<!-- 帖子一 { -->
			<div class="contClassify">
				<h2 class="title">帖子一</h2>

				<div class="formLine mt15">
					<label class="w_auto">链接:</label>
					<input type="text" value="${list[0].url}" name="urls" class="w-640 to-link">
					<input type="hidden" value="${list[0].code}" name="codes" class="w-640">
				</div>
			</div>

			<!-- 帖子二 { -->
			<div class="contClassify">
				<h2 class="title">帖子二</h2>

				<div class="formLine mt15">
					<label class="w_auto">链接:</label>
					<input type="text" value="${list[1].url}"  name="urls" class="w-640 to-link">
					<input type="hidden" value="${list[1].code}" name="codes" class="w-640">
				</div>
			</div>

			<!-- 帖子三 { -->
			<div class="contClassify">
				<h2 class="title">帖子三</h2>

				<div class="formLine mt15">
					<label class="w_auto">链接:</label>
					<input type="text"  value="${list[2].url}" name="urls" class="w-640 to-link">
					<input type="hidden" value="${list[2].code}" name="codes" class="w-640">
				</div>
			</div>
			<!-- 帖子四 { -->
			<div class="contClassify">
				<h2 class="title">帖子四</h2>
				<div class="formLine mt15">
					<label class="w_auto">链接:</label>
					<input type="text" value="${list[3].url}" name="urls" class="w-640 to-link">
					<input type="hidden" value="${list[3].code}" name="codes" class="w-640">
				</div>
			</div>
			<!-- 帖子五 { -->
			<div class="contClassify">
				<h2 class="title">帖子五</h2>
				<div class="formLine mt15">
					<label class="w_auto">链接:</label>
					<input type="text" value="${list[4].url}" name="urls" class="w-640 to-link">
					<input type="hidden" value="${list[4].code}" name="codes" class="w-640">
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
	<script>
	</script>
	 <script>
		$(".to-link").blur(function () {
			var $this = $(this);
			var thisVal = $this.val();
			var regTest = $.VLDTOR.IsWebUrl(thisVal);
			valid_txtBox_create(this,regTest || thisVal == "",'不能为纯空格','right');
		});
		
	</script>
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->	
</body>
</html>