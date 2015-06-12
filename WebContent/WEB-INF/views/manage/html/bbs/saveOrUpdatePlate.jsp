<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>天上社区-论坛版块管理-板块列表页-新建论坛版块</title>
    <style>
        .mt-40 {
            margin-top: -40px;
        }

        .floatfix:after {
            content: "";
            display: table;
            clear: both;
        }

        #editor1 {

        }
    </style>
</head>

<body>
<%@include file="/common-html/commonxz.jsp" %>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
        type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-tabel.js"
        type="text/javascript"></script>
<link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css"/>
<script src="${ctxManage}/webuploader/webuploader.js"
        type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"
        type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js"
        type="text/javascript"></script>
<!-- main { -->
<div class="main">
    <form id="creatRead" name="" action="saveOrUpdatePlate" method="post">
        <!-- 页面位置-->
        <div class="location">
            <label>
                您当前位置为:
            </label>
            <span><a>天上社区</a> -</span>
            <span><a>论坛版块管理</a> -</span>
            <span><a href="forum-list.html" target="_self">板块列表页</a> -</span>
            <span><a href="#" target="_self">新建论坛版块</a> </span>
        </div>

        <!-- 数据操作 -->
        <div class="searchManage">
            <button id="preview" class="btn-sure" type="button" onclick="preViewPlate()">
                预览
            </button>
            <button id="saveForm" type="button" class="btn-sure">
                保存
            </button>
        </div>

        <!-- 模块管理 { -->
        <div class="muduleManage details filament_solid_ddd">
            <!-- 版块名称  { -->
            <div class="formLine floatfix">
                <label>
                    版块名称:
                </label>
                <input type="hidden" maxlength="30" class="w-260" name="code"
                       value="${plate.code}">
                <input id="programaName" type="text" maxlength="30" class="w-260"
                       name="programaName" value="${plate.programaName}"/>
                <span class="reqItem">*</span>

                <!-- 图片上传 { -->
                <div class="mt20 floatfix">
                    <label>
                        背景图:
                    </label>

                    <div id="pickfiles${x}" class="pickfiles imgNode">
                        请上传图片
                    </div>
                    <span class="txt-suggest ml20">推荐上传大小1000 * 200</span>
                </div>

                <!-- 图片缩略图 { -->
                <div>
                    <c:if test="${empty plate.imageUrl}">
                        <img id="imgshow1"
                             src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg"
                             title="图片名称" alt="banner图片" class="forumPlateImg">
                    </c:if>
                    <c:if test="${not empty plate.imageUrl}">
                        <img  id="imgshow1"
                             src="${ctx}${plate.imageUrl}" title="图片名称" alt="banner图片" class="forumPlateImg">
                    </c:if>
                    <input type="hidden" class="url" name="url" value="${plate.imageUrl}"/>
                </div>

                <!-- 正文内容 { -->
                <div class="mt30">
                    <label class="disp-ib pos_r_t5">
                        版块介绍:
                    </label>
                    <textarea id="editor1" name="programaSummary" cols="71" rows="10"
                              class="ckeditor pl5 disp-ib"
                              style="display: inline-block">${plate.programaSummary}</textarea>
                </div>

            </div>
        </div>
        <!-- } 模块管理 -->

        <!-- SEO信息 { -->
        <div class="contClassify">
            <h2 class="title">
                SEO信息
            </h2>

            <div class="formLine">
                <label class="w-auto ft-w-b ml40" style="margin-left: 40px;">&lt;Keywords&gt;标签(关键字):</label>
                <input id="keywords" type="text" maxlength="" class="w-320" name="remark"
                       value="${plate.remark}">
                <span class="txt-suggest ml20">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
            </div>
        </div>
        <!-- } SEO信息 -->
    </form>
</div>
<!-- } main -->
<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch">
<!-- IE10+ -->
<link rel="next">
<!-- Firefox -->
<link rel="prerender">
<!-- Chrome -->
<script>
    /* 版块名称验证 */
    $("#programaName").blur(function () {
        var $this = $(this);
        var thisVal = $this.val();
        valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this, 2, 30), "内容长度只能在2-30个字符，且开头不能为空格", "right");
    });

    /* 版块介绍验证 */
    $("#editor1").blur(function () {
        var $this = $(this);
        var thisVal = $this.val(),
                regTest = $.VLDTOR.IsArticle(thisVal);
        valid_txtBox_create(this, regTest && inputRange(this, 2, 300), "内容长度只能在2-300个字符，且开头不能为空格", "top");
    });

    /* SEO验证 */
    $("#keywords").blur(function () {
        var $this = $(this),
                thisVal = $this.val();
        valid_txtBox_create(this, ($.VLDTOR.IsEnCnNum_comma(thisVal) && inputRange(this, 2, 20)) || thisVal == "", "只能为空或中英文及数字，且长度在2-20", "bottom");
    });

    /* 图片验证 */
    $(function () {
        uploader.on('fileQueued', function (file) {
            removeErrMesg($("#pickfiles").next(".txt-suggest"));
        })
    })
    $("#saveForm").click(function () {
        $("#js_name").blur();
        $("#editor1").blur();
        $("#keywords").blur();

        // 获取图片是否已经上传
        var imgVal = $("#imgshow1").next().val();
        if (imgVal == "") {
            valid_txtBox_create($("#pickfiles").next(".txt-suggest"), false, "请上传图片", "right");
        } else {
            removeErrMesg($("#pickfiles").next(".txt-suggest"));
        }

        var errInfo = $(".errMesg").length == 0;
        if (errInfo) {
            $("#creatRead").submit();
        } else {
            msgBox("输入信息有误，请检查", "erro", 1000);
            return false;
        }
    });

    function preViewPlate() {
        $("#creatRead").attr("action", "pewViewPlate").attr("target", "_blank").submit();
        $("#creatRead").attr("action", "saveOrUpdatePlate").attr("target", "_self");
    }

    // safari兼容性处理
    /*safari_set();

    function safari_set() {
        if(navigator.userAgent.indexOf("Mac") > 0) {
            $("#editor1").css({
                "position": "relative"
            });
        }
    }*/
</script>
</body>
</html>
