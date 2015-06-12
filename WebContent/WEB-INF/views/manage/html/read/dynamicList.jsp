<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE HTML >
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>读西藏首页-西藏动态显示管理</title>
    <%@include file="/common-html/common.jsp" %>
    <script src="../../resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="../../resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="../../resources/css/base.css"/>
    <link rel="stylesheet" href="../../resources/css/travel/formWeb.css"/>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <form id="creatRead" name="" method="post">
        <input type="hidden" name="t" value="dynamicList">

        <div class="location">
            <label>您当前位置为:</label>
            <span><a>读西藏</a> -</span>
            <span><a href="../read/read.html" target="_self">读西藏首页显示</a> -</span>
            <span><a href="#" target="_self">西藏动态显示管理</a></span>
        </div>

        <!-- 模块管理 { -->
        <div class="muduleManage filament_solid_ddd">

            <!-- 管理按钮 -->
            <div class="searchManage">
                <button id="preview" type="button" class="btn-sure">预览</button>
                <button id="default" type="button" class="btn-sure">显示最新</button>
                <button id="saveForm" type="button" class="btn-sure">保存</button>
                <button id="forword" type="button" class="btn-sure">查看</button>
            </div>
            <!-- 新闻一 { -->
            <div class="contClassify">
                <h2 class="title">新闻一</h2>

                <div class="formLine mt15">
                    <label class="w_auto">链接:</label>
                    <input value="${links[0].link}" name="links" type="text" class="w-320 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[0].code}" name="codes" type="text" class="w-200 number">
                </div>
            </div>

            <!-- 新闻二 { -->
            <div class="contClassify">
                <h2 class="title">新闻二</h2>

                <div class="formLine mt15">
                    <label class="w_auto">链接:</label>
                    <input value="${links[1].link}" name="links" type="text" class="w-320 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[1].code}" name="codes" type="text" class="w-200 number">
                </div>
            </div>

            <!-- 新闻三 { -->
            <div class="contClassify">
                <h2 class="title">新闻三</h2>

                <div class="formLine mt15">
                    <label class="w_auto">链接:</label>
                    <input value="${links[2].link}" name="links" type="text" class="w-320 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[2].code}" name="codes" type="text" class="w-200 number">
                </div>
            </div>

            <!-- 新闻四 { -->
            <div class="contClassify">
                <h2 class="title">新闻四</h2>

                <div class="formLine mt15">
                    <label class="w_auto">链接:</label>
                    <input value="${links[3].link}" name="links" type="text" class="w-320 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[3].code}" name="codes" type="text" class="w-200 number">
                </div>
            </div>

            <!-- 新闻五 { -->
            <div class="contClassify">
                <h2 class="title">新闻五</h2>

                <div class="formLine mt15">
                    <label class="w_auto">链接:</label>
                    <input value="${links[4].link}" name="links" type="text" class="w-320 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[4].code}" name="codes" type="text" class="w-200 number">
                </div>
            </div>

            <!-- 新闻六 { -->
            <div class="contClassify">
                <h2 class="title">新闻六</h2>

                <div class="formLine mt15">
                    <label class="w_auto">链接:</label>
                    <input value="${links[5].link}" name="links" type="text" class="w-320 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[5].code}" name="codes" type="text" class="w-200 number">
                </div>
            </div>

            <!-- 新闻七 { -->
            <div class="contClassify">
                <h2 class="title">新闻七</h2>

                <div class="formLine mt15">
                    <label class="w_auto">链接:</label>
                    <input value="${links[6].link}" name="links" type="text" class="w-320 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[6].code}" name="codes" type="text" class="w-200 number">
                </div>
            </div>
        </div>
        <!-- } 模块管理 -->
    </form>
</div>
<!-- } main -->
<!-- JS引用部分 -->
<script src="../../resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="../../resources/js/base.js" type="text/javascript"></script>
<script src="../../resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
<script>
    /* 保存按钮 */
    $("#saveForm").on('click', function () {
        $(".toLink").blur();
        $(".number").blur();
        var regSpan = $("span.errMesg").length > 0;
        if (regSpan) {
            msgBox("填写的信息有误，请检查", "erro", 1000);
        } else {
            $("#creatRead").attr('target', '_self');
            $("#creatRead").attr('action', 'managesave.html');
            $("#creatRead").submit();
            msgBox("保存成功", "pass", 1500);
        }
    });
    /* 预览按钮 */
    $("#preview").on('click', function () {
        $(".toLink").blur();
        $(".number").blur();
        var regSpan = $("span.errMesg").length > 0;
        if (regSpan) {
            msgBox("填写的信息有误，请检查", "erro", 1000);
        } else {
            $("#creatRead").attr('target', '_blank');
            $("#creatRead").attr('action', '${ctx}portal/app/previewculture.html');
            $("#creatRead").submit();
        }
    });
    /* 显示最新按钮 */
    $("#default").on('click', function () {
        $("#creatRead").attr('target', '_self');
        $("#creatRead").attr('action', 'managedefault.html');
        $("#creatRead").submit();
    });
    /* 查看按钮 */
    $("#forword").on('click', function () {
        $("#creatRead").attr('target', '_blank');
        $("#creatRead").attr('action', '${ctx}portal/app/culture.html');
        $("#creatRead").submit();
    });
    /* 链接验证 */
    $(".toLink").blur(function () {
    	if($(this).val()){
	        var thisVal = $(this).val(),
	            nextVal = $(this).siblings('.number').val();
	        nextValLen = nextVal.length > 0;
	        var regTest = $.VLDTOR.IsWebUrl(thisVal),
	                regNull = $.trim(thisVal) != "",
	                thisValClip = thisVal.split('code=')[1];
			//$(this).siblings('.number').val(thisValClip);
	        var this_val = $(this).val(),
	            isNull = this_val == "",
	            reg_test = $.VLDTOR.IsWebUrl(this_val);
	        if(isNull || reg_test) {
	            removeErrMesg(this);
	        } else {
	            creatErrMesg(this, "不能为中文或空格", "top");
	        }
    	}
    });
    /* 编号验证 */
    $(".number").blur(function () {
//        var thisVal = $(this).val();
//        var prevVal = ($(this).siblings('.toLink').val() + "");
//        var preValLen = prevVal.length > 0;
//        var regTest = $.VLDTOR.IsEnNum(thisVal),
//                regNull = $.trim(thisVal) != "";
//        if (preValLen) {
//            valid_txtBox_create(this, (regTest && inputRange(this, 1, 30)) || thisVal == "", '只能为空或数字及字母，且长度在1-30', 'top');
//        } else {
//            valid_txtBox_create(this, regTest && inputRange(this, 1, 30) && regNull, '只能为1-30位的数字及字母,且与链接必填一项', 'top');
//            if (regTest && regNull) {
//                $(this).siblings('.toLink').next('span.errMesg').remove();
//            }
//        }
        var this_val = $(this).val(),
            isNull = this_val == "",
            reg_test = $.VLDTOR.IsEnNum(this_val);
        if(isNull || reg_test) {
            removeErrMesg(this);
        } else {
            creatErrMesg(this, "只能为1-30位的数字及字母", "top");
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
