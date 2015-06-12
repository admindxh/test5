<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE>
<html>

<head>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>游西藏-商户管理-商户汇总页显示</title>
    <%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css"/>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>

</head>

<body>
<!-- main { -->
<div class="main">
    <form id="commer-show-form" method="post" enctype="multipart/form-data">
        <input type="hidden" name="bannerProgramaCode" value="${bannerProgramaCode}">
        <input type="hidden" id="pcode" name="commerProgramaCode" value="${commerProgramaCode}">
        <!-- 页面位置-->
        <div class="location">
            <label>您当前位置为:</label>
            <span><a>游西藏</a> -</span>
            <span><a href="travel.html" target="_self">商户管理</a> -</span>
            <span><a>商户汇总页显示</a></span>
        </div>

        <!-- 查看按钮 -->
        <!--			<div class="operManage">
            <button type="button" class="lookup btn-sure">查看</button>
        </div>-->

        <!-- 模块管理 { -->
        <div class="muduleManage commercial filament_solid_ddd pos-rela mt20">
            <h2 class="lev2">Banner广告位</h2>


            <div class="posidSet-unit mt35">
                <i>图片1</i>

                <div class="left">

                    <div class="formLine">
                        <label>图片:</label>

                        <div id="pickfiles1" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                        <span class="txt-suggest ml20">推荐尺寸：565 * 255</span>
                    </div>
                    <div class="formLine">
                        <label>链接至:</label>
                        <input id="hyperlink0" name="content" type="text" maxlength="" value="${bannerList[0].content }"
                               class="w-260">
                    </div>
                </div>
                <div class="right mt10">
                    <label>缩略图:</label>
                    <img id="imgshow1"
                         <c:if test="${not empty bannerList[0].url }">src="${ctx }${bannerList[0].url }"</c:if>
                         <c:if test="${empty bannerList[0].url}">src="${ctx}//portal/static/default/square.png"</c:if>
                         title="缩略图名称" alt="请上传图片" class="style-d" style="width: 339px;height: 153px;">
                    <input type="hidden" id="url1" class="url" name="url"

                           <c:if test="${not empty bannerList[0].url }">value="${bannerList[0].url }"</c:if>
                           <c:if test="${empty bannerList[0].url}">value="/portal/static/default/square.png"</c:if>

                            />
                </div>
            </div>

            <div class="posidSet-unit">
                <i>图片2</i>

                <div class="left">

                    <div class="formLine">
                        <label>图片:</label>

                        <div id="pickfiles2" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                        <span class="txt-suggest ml20">推荐尺寸：565 * 255</span>
                    </div>
                    <div class="formLine">
                        <label>链接至:</label>
                        <input id="hyperlink1" name="content" type="text" maxlength="" value="${bannerList[1].content }"
                               class="w-260">
                    </div>
                </div>
                <div class="right mt10">
                    <label>缩略图:</label>
                    <img id="imgshow2"
                         <c:if test="${not empty bannerList[1].url }">src="${ctx }${bannerList[1].url }"</c:if>
                         <c:if test="${empty bannerList[1].url}">src="${ctx}//portal/static/default/square.png"</c:if>
                         title="缩略图名称" alt="请上传图片" class="style-d" style="width: 339px;height: 153px;">
                    <input type="hidden" id="url2" class="url" name="url"

                           <c:if test="${not empty bannerList[1].url }">value="${bannerList[1].url }"</c:if>
                           <c:if test="${empty bannerList[1].url}">value="/portal/static/default/square.png"</c:if>

                            />
                </div>
            </div>

            <div class="posidSet-unit">
                <i>图片3</i>

                <div class="left">

                    <div class="formLine">
                        <label>图片:</label>

                        <div id="pickfiles3" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                        <span class="txt-suggest ml20">推荐尺寸：565 * 255</span>
                    </div>
                    <div class="formLine">
                        <label>链接至:</label>
                        <input id="hyperlink2" name="content" type="text" maxlength="" value="${bannerList[2].content }"
                               class="w-260">
                    </div>
                </div>
                <div class="right mt10">
                    <label>缩略图:</label>
                    <img id="imgshow3"
                         <c:if test="${not empty bannerList[2].url }">src="${ctx }${bannerList[2].url }"</c:if>
                         <c:if test="${empty bannerList[2].url}">src="${ctx}//portal/static/default/square.png"</c:if>
                         title="缩略图名称" alt="请上传图片" class="style-d" style="width: 339px;height: 153px;">
                    <input type="hidden" id="url3" class="url" name="url"

                           <c:if test="${not empty bannerList[2].url }">value="${bannerList[2].url }"</c:if>
                           <c:if test="${empty bannerList[2].url}">value="/portal/static/default/square.png"</c:if>

                            />
                </div>
            </div>

        </div>
        <!-- } 模块管理 -->

        <!-- 模块管理 { -->
        <div class="muduleManage recommend filament_solid_ddd pos-rela">
            <h2 class="lev2">官方推荐</h2>
            <button type="button" onclick="showHot()" class="btn-base pos-abso" style="top:20px;right:30px;">显示最热
            </button>
            <div id="muti1" class="formLine mt35">
                <label>商户1链接:</label>
                <input id="commerLink1" name="commerLink" value="${commerList[0].url }" type="text" class="w-260">
                <label class="w-auto">或编号:</label>
                <input id="commerNum1" name="commerCode" value="${commerList[0].content }" type="text" class="w-200">
            </div>

            <div id="muti2" class="formLine">
                <label>商户2链接:</label>
                <input id="commerLink2" name="commerLink" value="${commerList[1].url }" type="text" class="w-260">
                <label class="w-auto">或编号:</label>
                <input id="commerNum2" name="commerCode" value="${commerList[1].content }" type="text" class="w-200">
            </div>

            <div id="muti3" class="formLine">
                <label>商户3链接:</label>
                <input id="commerLink3" name="commerLink" value="${commerList[2].url }" type="text" class="w-260">
                <label class="w-auto">或编号:</label>
                <input id="commerNum3" name="commerCode" value="${commerList[2].content }" type="text" class="w-200">
            </div>

            <!-- 操作 -->
            <div class="saveOper mt30">
                <button type="button" class="btn-sure mr30" onclick="preView()">预览</button>
                <button type="button" class="save btn-sure mr100">保存</button>
            </div>

        </div>
        <!-- } 模块管理 -->
    </form>
</div>

<!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
<script type="text/javascript">
    function preView() {
        $("#commer-show-form").attr("action", "preView").attr("target", "_blank").submit();

    }

    /* “Banner广告位”链接验证 */
    $(".muduleManage.commercial [id^='hyperlink']").blur(function () {
        var $this = $(this),
            thisVal = $this.val(),
            reg_thisVal = $.VLDTOR.IsWebUrl(thisVal);
        if (reg_thisVal || thisVal == "") {
            $this.next(".errMesg").remove();
        } else {
            creatErrMesg(this, "不能为中文和空格", "right");
        }
    });

    /* “官方推荐”链接验证 */
    $(document).on("blur", ".muduleManage:eq(1) input[id^='commerLink']", function () {
        var thisVal = $(this).val(),
            reg_thisVal = $.VLDTOR.IsWebUrl(thisVal);
        if(reg_thisVal || thisVal == "") {
            $(this).next(".errMesg").remove();
            if($(this).siblings('input').val() == ""){
	            $(this).nextAll('input').val(thisVal.split('code=')[1]);
            }
        } else {
            creatErrMesg(this, "不能为中文和空格", "top");
        }
    });

    /* “官方推荐”编号验证 */
    $(document).on("blur", ".muduleManage:eq(1) input[id^='commerNum']", function () {
        var thisVal = $(this).val(),
            reg_thisVal = $.VLDTOR.IsEnNum(thisVal);

        if (reg_thisVal || thisVal == "") {
            $(this).next(".errMesg").remove();
        } else {
            creatErrMesg(this, "只能为英文和数字", "top");
        }
    });

    /* 保存验证 */
    $(".save.btn-sure").click(function () {
        // “Banner广告位”遍历验证
        var bannerLink = $(".muduleManage.commercial [id^='hyperlink']"),
            bannerLink_len = bannerLink.length;

        for (var i = 0; i < bannerLink_len; i++) {
            bannerLink.eq(i).blur();
        }

        // “官方推荐”链接遍历验证
        var txt_link = $(".muduleManage:eq(1) input[id^='commerLink']"),
            txt_link_len = txt_link.length;

        for (var i = 0; i < txt_link_len; i++) {
            txt_link.eq(i).blur();
        }

        // “官方推荐”编号遍历验证
        var txt_num = $(".muduleManage:eq(1) input[id^='commerNum']"),
            txt_num_len = txt_num.length;

        for (var i = 0; i < txt_num_len; i++) {
            txt_num.eq(i).blur();
        }

        // 验证通过
        var errInfo = $(".errMesg").length == 0;
        if (errInfo) {
            msgBox("保存成功！", "pass", 2600);
            submit(1);
        }
        // 验证未通过
        else {
            msgBox("输入的内容有误，请检查！", "erro", 2000);
            return false;
        }
    });

</script>
<script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
<script type="text/javascript">

    //加载事件创建上传
    $(function () {
        for (var i = 1; i <= 4; i++) {
            cteateUploder('pickfiles' + i, 'imgshow' + i, 'url' + i);
        }
    })


</script>
<script type="text/javascript">
    function cteateUploder(pickId, preId, urlHiddenId) {
        var contextPath = "${ctx}";
        var options_ = {
            swf: contextPath + '/manage/webuploader/Uploader.swf',
            server: contextPath + '/web/imageController/uploadImage',
            runtimeOrder: "flash",
            accept: {extensions: 'jpg,jpeg,bmp,png,gif'},
            pick: {
                id: '#' + pickId,
                multiple: false
            },
            fileVal: 'file',
            auto: true,
            resize: false,
            fileSizeLimit: 400 * 1024,
        };
        addEvent(new WebUploader.Uploader(options_), preId, urlHiddenId);
    }
    function addEvent(up, preId, urlHiddenId) {
        up.on('uploadSuccess', function (file, response) {
            $.ajax({
                type: "post",
                url: contextPath + "/web/imageController/getUrl",
                dataType: "text",
                async: false,
                success: function (data) {
                    $("#" + urlHiddenId).val(data);
                    $("#" + preId).attr("src", "${ctx}" + data);//预览img

                },
                error: function () {
                    alert("数据异常");
                }
            });
        });
        up.on('error', function (type) {
            //alert("错误信息:"+type);
            if (type == "Q_EXCEED_SIZE_LIMIT" || type == "F_EXCEED_SIZE") {
            	msgBox("文件超过大小,请将上传文件大小控制在400kb以内", "erro");
            } else if (type == "Q_TYPE_DENIED") {
            	msgBox("上传格式错误，或者是文件大小必须大于0kb");
            } else {
            	msgBox("上传错误，请重试。");
            }
        });
    }
</script>
<script type="text/javascript">

    //********************************************************
    //表单顺序提交
    function submit(preview) {
        //.log(preview);
        if (preview == 1) {
            $.ajax({
                type: "post",
                url: "${ctx }web/merchant/saveContent",
                data: $("#commer-show-form").serialize(),
                dataType: "text",
                async: false,
                success: function (data) {
                    //.log(data);
                }
            });
        } else {
            $.ajax({
                type: "post",
                url: "${ctx }web/merchant/previewContent",
                data: $("#form").serialize(),
                dataType: "text",
                async: false,
                success: function (data) {
                    //.log(data);
                    window.open("${ctx }web/merchant/previewManageFront");
                }
            });

        }
    }
    //**********************************************
    function showHot() {
        //.log("in showhot");
        var _pcode = $("#pcode").val();
        $.ajax({
            type: "post",
            url: "${ctx }web/merchant/showhot",
            dataType: "json",
            data: {programaCode: _pcode},
            async: false,
            success: function (json) {
                if (json.msg == "1") {
                    alert("数据量不足!");
                } else {
                    //先把当前div的内容清空
                    for (var i = 1; i < 3; i++) {
                        $("#muti" + i).html("");
                    }
                    //再重新添加内容
                    var list = json.data;
                    for (var i = 1; i < 3; i++) {
                        var url1 = list[i].url;
                        if (!url1) {
                            url1 = "";
                        }
                        var code1 = list[i].code;
                        if (!code1) {
                            code1 = "";
                        }
                        $("#muti" + i).append(
                                "<label>商户" + i + "链接:</label>" +
                                "<input id='commerLink" + i + "' name='commerLink' value='" + url1 + "' type='text' class='w-260'>" +
                                "<label class='w-auto'>或编号:</label>" +
                                "<input id='commerNum" + i + "' name='commerCode' value='" + code1 + "' type='text' class='w-200'>"
                        );
                    }
                }
            }
        });
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

