<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>读西藏首页-西藏文化传播显示管理</title>
<%@include file="/common-html/common.jsp" %>
<script src="../../resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="../../resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="../../resources/css/base.css" />
    <link rel="stylesheet" href="../../resources/css/travel/formWeb.css" />
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <form id="creatRead" name="" method="post">
 	<input type="hidden" name="t" value="cultural">
 	 <input type="hidden" name="orderType" value="205">
    <div class="location">
        <label>您当前位置为:</label>
       	<span><a>读西藏</a> -</span>
		<span><a href="../read/read.html" target="_self">读西藏首页显示</a> -</span>
        <span><a href="#" target="_self">西藏文化传播显示管理</a></span>
    </div>

    <!-- 模块管理 { -->
    <div class="muduleManage filament_solid_ddd">
        <!-- 管理按钮 -->
        <div class="searchManage">
         	 <button id="preview"  type="button" class="btn-sure">预览</button>
         	 <button id="default"  type="button" class="btn-sure">显示评分最高</button>
             <button id="saveForm" type="button" class="btn-sure">保存</button>
        </div>
        <!-- 音乐 { -->
        <div class="contClassify">
            <h2 class="title">音乐</h2>

            <div class="formLine mt15">
                <label class="w_auto">链接1:</label>
                <input value="${links[0].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[0].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接2:</label>
                <input value="${links[1].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[1].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接3:</label>
                <input value="${links[2].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[2].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接4:</label>
             <input value="${links[3].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[3].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接5:</label>
                 <input value="${links[4].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[4].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接6:</label>
               <input value="${links[5].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[5].code}" name="codes" type="text" class="w-200 number">
            </div>
        </div>

        <!-- 小说 { -->
        <div class="contClassify">
            <h2 class="title">小说</h2>

            <div class="formLine mt15">
                <label class="w_auto">链接1:</label>
               <input value="${links[6].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[6].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接2:</label>
              <input value="${links[7].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[7].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接3:</label>
                 <input value="${links[8].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[8].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接4:</label>
               <input value="${links[9].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[9].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接5:</label>
                 <input value="${links[10].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[10].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接6:</label>
                 <input value="${links[11].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[11].code}" name="codes" type="text" class="w-200 number">
            </div>
        </div>

        <!-- 游戏 { -->
        <div class="contClassify">
            <h2 class="title">游戏</h2>

            <div class="formLine mt15">
                <label class="w_auto">链接1:</label>
         <input value="${links[12].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[12].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接2:</label>
               <input value="${links[13].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[13].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接3:</label>
                <input value="${links[14].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[14].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接4:</label>
               <input value="${links[15].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[15].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接5:</label>
             <input value="${links[16].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[16].code}" name="codes" type="text" class="w-200 number">
            </div>
            <div class="formLine mt15">
                <label class="w_auto">链接6:</label>
                <input value="${links[17].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[17].code}" name="codes" type="text" class="w-200 number">
            </div>
        </div>
    </div><!-- } 模块管理 -->
    </form>
</div><!-- } main -->

<!-- JS引用部分 -->
<script src="../../resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="../../resources/js/base.js" type="text/javascript"></script>
<script src="../../resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="../../resources/js/dataValidation.js" type="text/javascript"></script>
	<script>
    $("#saveForm").on('click',function(){
       	$(".toLink").blur();
		$(".number").blur();
		var regSpan = $("span.errMesg").length > 0;
		if(regSpan){
			msgBox("填写的信息有误，请检查","erro",1000);
		}else{
       $("#creatRead").attr('target','_self');
        $("#creatRead").attr('action','managesave.html');
        $("#creatRead").submit();
        msgBox("保存成功","pass",1500);
    }
    });
    
    $("#preview").on('click',function(){
       	$(".toLink").blur();
		$(".number").blur();
		var regSpan = $("span.errMesg").length > 0;
		if(regSpan){
			msgBox("填写的信息有误，请检查","erro",1000);
		}else{
			$("#creatRead").attr('target','_blank');
        $("#creatRead").attr('action','${ctx}portal/app/precultrue.html');
        $("#creatRead").submit();
		}
    });
       $("#default").on('click',function(){
    	 $("#creatRead").attr('target','_self');
        $("#creatRead").attr('action','managedefault.html');
        $("#creatRead").submit();
    });
       $("#forword").on('click',function(){
    	 $("#creatRead").attr('target','_blank');
        $("#creatRead").attr('action','${ctx}portal/app/culture.html');
        $("#creatRead").submit();
    });
       $(".toLink").blur(function () {
    	   if($(this).val()){
				var thisVal = $(this).val(),
						nextVal = $(this).siblings('.number').val(),
						nextValLen = nextVal.length > 0;
				var regTest = $.VLDTOR.IsWebUrl(thisVal),
						regNull = $.trim(thisVal) != "",
						thisValClip = thisVal.split('code=')[1];
				if(nextValLen){
					valid_txtBox_create(this,regTest,'不能为中文或空格','top');
					if(regTest && regNull){
					//$(this).siblings('.number').val(thisValClip);
					}
				}else{
					valid_txtBox_create(this,regTest && regNull,'不能为中文或空格,且与编号必填一项','top');
					if(regTest && regNull){
						$(this).siblings('.number').next('span.errMesg').remove();
						//$(this).siblings('.number').val(thisValClip);
					}
				}
    	   }
		});
		$(".number").blur(function () {
			var thisVal = $(this).val(),
					prevVal = $(this).siblings('.toLink').val(),
					preValLen = prevVal.length > 0;
			var regTest = $.VLDTOR.IsEnNum(thisVal),
					regNull = $.trim(thisVal) != "";
			if(preValLen){
				valid_txtBox_create(this,(regTest && inputRange(this,1,30)) || thisVal == "", '只能为空或数字及字母，且长度在1-30','top');
			}else{
				valid_txtBox_create(this,regTest && inputRange(this,1,30) && regNull,'只能为1-30位的数字及字母,且与链接必填一项','top');
				if(regTest && regNull){
					$(this).siblings('.toLink').next('span.errMesg').remove();
				}
			}
		});   
</script>
<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch"> <!-- IE10+ -->
<link rel="next"> <!-- Firefox -->
<link rel="prerender"> <!-- Chrome -->
</body>
</html>