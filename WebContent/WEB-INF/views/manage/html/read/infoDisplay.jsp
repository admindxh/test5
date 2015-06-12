<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE HTML >
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>读西藏首页-信息显示管理</title>
    <%@include file="/common-html/common.jsp" %>
    <script src="../../resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="../../resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="../../resources/css/base.css"/>
    <link rel="stylesheet" href="../../resources/css/travel/formWeb.css"/>
    <style>
        .w50p {
            width: 50%;
        }
    </style>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <form id="creatRead" name="" method="post">
        <input type="hidden" name="t" value="infoDisplay">
        <input type="hidden" name="orderType" value="203">

        <div class="location">
            <label>您当前位置为:</label>
            <span><a>读西藏</a> -</span>
            <span><a href="../read/read.html" target="_self">读西藏首页显示</a> -</span>
            <span><a href="#" target="_self">读西藏信息显示管理</a></span>
        </div>
        <!-- 模块管理 { -->
        <div class="muduleManage readInfoDisp filament_solid_ddd ov-au">
            <!-- 管理按钮 -->
            <div class="searchManage">
                <button id="preview" type="button" class="btn-sure">预览</button>
                <button id="default" type="button" class="btn-sure">显示最热</button>
                <button id="saveForm" type="button" class="btn-sure">保存</button>
                <button id="forword" type="button" class="btn-sure">查看</button>
            </div>

            <!-- 节日一 { -->
            <div class="contClassify">
                <h2 class="title">节日</h2>

                <div class="formLine mt15">
                    <label class="w-auto">链接:</label>
                    <input value="${links[0].link}" name="links" type="text" class="w-320 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[0].code}" name="codes" type="text" class="w-220 number">
                </div>
            </div>

            <!-- 历史 { -->
            <div class="contClassify">
                <h2 class="title">历史</h2>

                <div class="formLine mt15">
                    <label class="w-auto">链接:</label>
                    <input value="${links[1].link}" name="links" type="text" class="w-320 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[1].code}" name="codes" type="text" class="w-220 number">
                </div>
            </div>

            <!-- 风俗 { -->
            <div class="contClassify">
                <h2 class="title">风俗</h2>

                <div class="formLine mt15">
                    <label class="w-auto">链接:</label>
                    <input value="${links[2].link}" name="links" type="text" class="w-320 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[2].code}" name="codes" type="text" class="w-220 number">
                </div>
            </div>

            <!-- 名人 { -->
            <div class="contClassify float_l w50p">
                <h2 class="title">名人</h2>

                <div class="formLine">
                    <label style="width: 42px;">链接1:</label>
                    <input value="${links[3].link}" name="links" type="text" class="w-260 toLink">
                    <label style="width: 49px;">或编号:</label>
                    <input value="${links[3].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label style="width: 42px;">链接2:</label>
                    <input value="${links[4].link}" name="links" type="text" class="w-260 toLink">
                    <label style="width: 49px;">或编号:</label>
                    <input value="${links[4].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label style="width: 42px;">链接3:</label>
                    <input value="${links[5].link}" name="links" type="text" class="w-260 toLink">
                    <label style="width: 49px;">或编号:</label>
                    <input value="${links[5].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label style="width: 42px;">链接4:</label>
                    <input value="${links[6].link}" name="links" type="text" class="w-260 toLink">
                    <label style="width: 49px;">或编号:</label>
                    <input value="${links[6].code}" name="codes" type="text" class="w-200 number">
                </div>
            </div>

            <!-- 宗教 { -->
            <div class="contClassify float_l w50p">
                <h2 class="title">宗教</h2>

                <div class="formLine">
                    <label class="w-auto">链接1:</label>
                    <input value="${links[7].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[7].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接2:</label>
                    <input value="${links[8].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[8].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接3:</label>
                    <input value="${links[9].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[9].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接4:</label>
                    <input value="${links[10].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[10].code}" name="codes" type="text" class="w-200 number">
                </div>
            </div>

            <!-- 美食 { -->
            <div class="contClassify float_l w50p mt5">
                <h2 class="title">美食</h2>

                <div class="formLine">
                    <label class="w-auto">链接1:</label>
                    <input value="${links[11].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[11].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接2:</label>
                    <input value="${links[12].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[12].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接3:</label>
                    <input value="${links[13].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[13].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接4:</label>
                    <input value="${links[14].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[14].code}" name="codes" type="text" class="w-200 number">
                </div>
            </div>

            <!-- 艺术 { -->
            <div class="contClassify float_l w50p mt5">
                <h2 class="title">艺术</h2>

                <div class="formLine">
                    <label class="w-auto">链接1:</label>
                    <input value="${links[15].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[15].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接2:</label>
                    <input value="${links[16].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[16].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接3:</label>
                    <input value="${links[17].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[17].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接4:</label>
                    <input value="${links[18].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[18].code}" name="codes" type="text" class="w-200 number">
                </div>
            </div>

            <!-- 特产 { -->
            <div class="contClassify float_l w50p mt5">
                <h2 class="title">特产</h2>

                <div class="formLine">
                    <label class="w-auto">链接1:</label>
                    <input value="${links[19].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[19].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接2:</label>
                    <input value="${links[20].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[20].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接3:</label>
                    <input value="${links[21].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[21].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接4:</label>
                    <input value="${links[22].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[22].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接5:</label>
                    <input value="${links[23].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[23].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接6:</label>
                    <input value="${links[24].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[24].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接7:</label>
                    <input value="${links[25].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[25].code}" name="codes" type="text" class="w-200 number">
                </div>

            </div>

            <!-- 动植物 { -->
            <div class="contClassify float_l w50p mt5">
                <h2 class="title">动植物</h2>

                <div class="formLine">
                    <label class="w-auto">链接1:</label>
                    <input value="${links[26].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[26].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接2:</label>
                    <input value="${links[27].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[27].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接3:</label>
                    <input value="${links[28].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[28].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接4:</label>
                    <input value="${links[29].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[29].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接5:</label>
                    <input value="${links[30].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[30].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接6:</label>
                    <input value="${links[31].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[31].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接7:</label>
                    <input value="${links[32].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[32].code}" name="codes" type="text" class="w-200 number">
                </div>
            </div>

            <!-- 地理 { -->
            <div class="contClassify float_l w50p mt5">
                <h2 class="title">服饰</h2>

                <div class="formLine">
                    <label class="w-auto">链接1:</label>
                    <input value="${links[33].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[33].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接2:</label>
                    <input value="${links[34].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[34].code}" name="codes" type="text" class="w-200 number">
                </div>
            </div>
            <!-- 服饰 { -->
            <div class="contClassify float_l w50p mt5">
                <h2 class="title">地理</h2>

                <div class="formLine">
                    <label class="w-auto">链接1:</label>
                    <input value="${links[35].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[35].code}" name="codes" type="text" class="w-200 number">
                </div>
                <div class="formLine">
                    <label class="w-auto">链接2:</label>
                    <input value="${links[36].link}" name="links" type="text" class="w-260 toLink">
                    <label class="w-auto">或编号:</label>
                    <input value="${links[36].code}" name="codes" type="text" class="w-200 number">
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
<script src="../../resources/js/dataValidation.js" type="text/javascript"></script>
<script>
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
    $("#default").on('click', function () {

        $("#creatRead").attr('target', '_self');
        $("#creatRead").attr('action', 'managedefault.html');
        $("#creatRead").submit();
    });
    $("#forword").on('click', function () {
        $("#creatRead").attr('target', '_blank');
        $("#creatRead").attr('action', '${ctx}portal/app/culture.html');
        $("#creatRead").submit();
    });

    $(".toLink").blur(function () {
    	if($(this).val()){
    		var thisVal = $(this).val(),
	            numNode = $(this).siblings('.number');
		    var regTest = $.VLDTOR.IsWebUrl(thisVal),
		            thisValClip = thisVal.split('code=')[1];
		    if (valid_txtBox_create(this, regTest, '不能为中文或空格,且与编号必填一项', 'top')) {
		        if (thisValClip == undefined || thisValClip == "") {
		            numNode.next('span.errMesg').remove();
		        } else {
		            numNode.val(thisValClip);
		            numNode.next('span.errMesg').remove();
		        }
		    }
    	}
    });

    $(".number").blur(function () {
        var thisVal = $(this).val(),
                linkNode = $(this).siblings('.toLink');
        var regTest = $.VLDTOR.IsEnNum(thisVal);
        if (valid_txtBox_create(this, (regTest && inputRange(this, 1, 30)) || thisVal == "", '只能为空或1-30位的数字及字母,且与链接必填一项', 'top')) {
            linkNode.next('span.errMesg').remove();
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