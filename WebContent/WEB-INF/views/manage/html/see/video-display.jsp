<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>看西藏-看西藏首页显示-视频显示管理</title>
<%@include file="/common-html/common.jsp" %>
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>看西藏</a> -</span>
        <span><a href="see.html" target="_self">看西藏首页显示</a> -</span>
        <span><a href="#" target="_self">视频显示管理</a></span>
    </div>

    <!-- 模块管理 { -->
    <div class="muduleManage filament_solid_ddd">
		<form id="datafrm"  method="post">
        <!-- 管理按钮 -->
        <div class="searchManage">
            <button onclick="sub(100)"  type="button" class="btn-sure">显示最新</button>
            <button onclick="sub(201)" type="button" class="btn-sure">显示最热</button>
            <a  href="${ctx}portal/frontMutiController/toHome" target="n" class="btn-anchor">查看</a>
        </div>

        <!-- 高清视频1 { -->
        <div class="contClassify">
            <h2 class="title">高清视频1</h2>

            <div class="formLine mt15">
                <label>链接:</label>
                <input value="${links[0].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[0].code }"name="codes" type="text" class="w-200 number">
            </div>
        </div>

        <!-- 高清视频2 { -->
        <div class="contClassify">
            <h2 class="title">高清视频2</h2>
            <div class="formLine mt15">
                <label>链接:</label>
                <input value="${links[1].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[1].code }"name="codes" type="text" class="w-200 number">
            </div>
        </div>
        <!-- 高清视频3 { -->
        <div class="contClassify">
            <h2 class="title">高清视频3</h2>

            <div class="formLine mt15">
                <label>链接:</label>
               <input value="${links[2].link}" name="links" type="text" class="w-320 toLink">
                <label class="w-auto">或编号:</label>
                <input value="${links[2].code }"name="codes" type="text" class="w-200 number">
            </div>
        </div>

        <!-- 保存 -->
        <div class="formLine">
            <div class="saveOper">
                <button id="preview" type="button" class="btn-sure">预览</button>
                <button id="save" type="button" class="save btn-sure ml20">保存</button>
            </div>
        </div>
	</form>
    </div>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
<script type="text/javascript">
    // 保存验证提交
    $("#save").click(function() {
        // 让全部输入框执行一次失焦验证事件
        $(".toLink").blur();
        $(".number").blur();
        var regSpan_len = $("span.errMesg").length > 0;
        // 通过验证
        if(regSpan_len) {
            msgBox("输入的内容有误，请检查！", "erro", 2600);
        } else {
            // Do Something
             $("#datafrm").attr('action','videodispalysave.html');
           $("#datafrm").submit();
            msgBox("保存成功！", "pass", 2600);
        }
    });
    // 保存验证提交
    $("#preview").click(function() {
        // 让全部输入框执行一次失焦验证事件
        $(".toLink").blur();
        $(".number").blur();
        var regSpan_len = $("span.errMesg").length > 0;
        // 通过验证
        if(regSpan_len) {
            msgBox("输入的内容有误，请检查！", "erro", 2600);
        } else {
          // Do Something
            $("#datafrm").attr('target','_blank');
            $("#datafrm").attr('action','${ctx}portal/frontMutiController/predisvideo');
            $("#datafrm").submit();
        //	   msgBox("保存成功！", "pass", 2600);
        }
    }); 
    function sub(t)
    {
    	if(t==1000){
    		//查看
    	}else
    	{
    	  $("#datafrm").attr('action','video-display.html?type='+t);
    	  $("#datafrm").attr("target","_self");
    	  $("#datafrm").submit();
    	}
    }
    $(".toLink").blur(function () {
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
<link rel="prefetch">
<!-- IE10+ -->
<link rel="next">
<!-- Firefox -->
<link rel="prerender">
<!-- Chrome -->
</body>
</html>
