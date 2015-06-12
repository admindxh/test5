<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1"/>
    <title>门户首页-读西藏管理</title>
    <%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css"/>
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css"/>
    <script type="text/javascript">
        //表单顺序提交
        function submit(preview, x) {
            $(".show input.showIntro").blur();
            $(".show input.showTitle").blur();
            $(".show input.toLink").blur();
            var regSpan = $(".show span.errMesg").length > 0;
            if (!regSpan) {
                if (preview == 1) {
                    for (var i = x; i <= x; i++) {
                        $.ajax({
                            type: "post",
                            url: "${ctx }web/homeController/saveManageImg",
                            data: $("#form" + i).serialize(),
                            dataType: "text",
                            async: false,
                            success: function (data) {
                                //.log(data);
                            }
                        });
                    }
                    msgBox("保存成功", "pass", 1500);
                    window.location.href = "${ctx}/web/homeController/getManageImg?programaCode=132a2285-75da-11e4-b6ce-005056a05bc9";

                } else {
                    for (var i = x; i <= x; i++) {
                        $.ajax({
                            type: "post",
                            url: "${ctx }web/homeController/previewManageImg",
                            data: $("#form" + i).serialize(),
                            dataType: "text",
                            async: false,
                            success: function (data) {
                                //.log(data);
                            }
                        });
                    }
                    var form = $('<form style="display" target="_blank" id="open" action="${ctx}web/homeController/previewManageFront" method="post"></form>');
                    $("body").append(form);
                    $("#open").submit();
                }
            } else {
                msgBox("输入的内容有误，请检查！", "erro", 1000);
                return false;
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
        <span><a>门户首页</a> -</span>
        <span><a href="#" target="_self">读西藏管理</a></span>
    </div>

    <!-- 模块管理 { -->
    <div class="muduleManage filament_solid_ddd">
        <!-- 查看按钮 -->
        <div class="searchManage">
            <button type="button" class="btn-sure" onclick="javascript:window.open('${ctx}')">查看</button>
        </div>

        <!-- 推荐位选择标签 { -->
        <div class="posidTab">
            <label class="checked">推荐位</label>
            <label>风俗</label>
            <label>历史</label>
            <label>宗教</label>
            <label>其他</label>
        </div>
        <!-- } 推荐位选择标签 -->

        <!-- 推荐位图文设置 { -->
        <div class="posidSet filament_solid_ccc">
            <!-- 推荐位 { -->
            <div class="posidSet-posid show">
                <!-- form1 -->
                <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                      id="form1">
                    <div class="posidSet-unit">
                        <i>图片1</i>

                        <div class="left">
                            <div class="formLine">
                                <label>图片1:</label>

                                <div id="pickfiles1" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：200 * 400</span>
                            </div>
                            <div class="formLine">
                                <label>显示标题:</label>
                                <input class="showTitle" name="name" value="${imageList[4][0].name }" type="text">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine">
                                <label>显示简介:</label>
                                <input class="showIntro" name="summary" value="${imageList[4][0].summary }" type="text">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine">
                                <label>链接至:</label>
                                <input class="toLink" name="hyperlink" value="${imageList[4][0].hyperlink }"
                                       type="text">
                                <span class="reqItem">*</span>
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow1" style="width:120px;height:240px;"
                            <c:if test="${empty imageList[4][0].url}">
                                 src="${ctx}//portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty imageList[4][0].url}">
                                 src="${ctx}/${imageList[4][0].url }"
                            </c:if>
                                 title="缩略图名称" alt="请上传图片" class="style-b">
                            <input type="hidden" class="url" id="url1" name="url"

                                    <c:if test="${empty imageList[4][0].url}">
                                        value="/portal/static/default/square.png"
                                    </c:if>
                                    <c:if test="${not empty imageList[4][0].url}">
                                        value="${imageList[4][0].url }"
                                    </c:if>

                                    />
                        </div>
                    </div>
                    <input type="hidden" name="mutiCode" value="${mutiList[4].code }"/>
                    <input type="hidden" name="code" value="${imageList[4][0].code }"/>
                    <input type="hidden" name="isPreview" value="1"/>
                </form>
                <!-- }form1 -->
                <!-- 保存 -->
                <div class="saveOper">
                    <button type="button" class="btn-sure mr30" onclick="submit(2,1)">预览</button>
                    <button type="button" class="save btn-sure" onclick="submit(1,1)">保存</button>
                </div>
            </div>
            <!-- } 推荐位 -->
            <!-- 风俗 { -->
            <div class="posidSet-mores">
                <!-- form2 -->
                <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                      id="form2">
                    <div class="posidSet-unit">
                        <i>图片1</i>

                        <div class="left">
                            <div class="formLine">
                                <label>图片1:</label>

                                <div id="pickfiles2" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：240 * 274</span>
                            </div>
                            <div class="formLine">
                                <label>显示标题:</label>
                                <input class="showTitle" name="name" type="text" maxlength=""
                                       value="${imageList[3][0].name }">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine">
                                <label>显示简介:</label>
                                <input class="showIntro" name="summary" type="text" maxlength=""
                                       value="${imageList[3][0].summary }">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine">
                                <label>链接至:</label>
                                <input class="toLink" name="hyperlink" type="text" maxlength=""
                                       value="${imageList[3][0].hyperlink }">
                                <span class="reqItem">*</span>
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow2" style="width:240px;height:274px;"

                            <c:if test="${empty imageList[3][0].url}">
                                 src="${ctx}portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty imageList[3][0].url}">
                                 src="${ctx}${imageList[3][0].url }"
                            </c:if>

                                 title="缩略图名称" alt="请上传图片" class="style-b">
                            <input type="hidden" class="url" id="url2" name="url"
                                    <c:if test="${empty imageList[3][0].url}">
                                        value="/portal/static/default/square.png"
                                    </c:if>
                                    <c:if test="${not empty imageList[3][0].url}">
                                        value="${imageList[3][0].url }"
                                    </c:if>
                              />
                        </div>
                        <input type="hidden" name="mutiCode" value="${mutiList[3].code }"/>
                        <input type="hidden" name="code" value="${imageList[3][0].code }"/>
                        <input type="hidden" name="isPreview" value="1"/>
                    </div>
                </form>
                <!-- }form2 -->

                <!-- 保存 -->
                <div class="saveOper">
                    <button type="button" class="btn-sure mr30" onclick="submit(2,2)">预览</button>
                    <button type="button" class="save btn-sure" onclick="submit(1,2)">保存</button>
                </div>
            </div>
            <!-- } 风俗 -->

            <!-- 历史 { -->
            <div class="posidSet-history">
                <!-- form3{ -->
                <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                      id="form3">
                    <div class="posidSet-unit">
                        <i>图片1</i>

                        <div class="left">
                            <div class="formLine">
                                <label>图片1:</label>

                                <div id="pickfiles3" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：240 * 274</span>
                            </div>
                            <div class="formLine">
                                <label>显示标题:</label>
                                <input class="showTitle" name="name" type="text" maxlength=""
                                       value="${imageList[2][0].name }">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine">
                                <label>显示简介:</label>
                                <input class="showIntro" name="summary" type="text" maxlength=""
                                       value="${imageList[2][0].summary }">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine">
                                <label>链接至:</label>
                                <input class="toLink" name="hyperlink" type="text" maxlength=""
                                       value="${imageList[2][0].hyperlink }">
                                <span class="reqItem">*</span>
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow3" style="width:240px;height:274px;"

                            <c:if test="${empty imageList[2][0].url}">
                                 src="${ctx}portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty imageList[2][0].url}">
                                 src="${ctx}/${imageList[2][0].url }"
                            </c:if>

                                 title="缩略图名称" alt="请上传图片" class="style-b">
                            <input type="hidden" class="url" id="url3" name="url"


                                    <c:if test="${empty imageList[2][0].url}">
                                        value="portal/static/default/square.png"
                                    </c:if>
                                    <c:if test="${not empty imageList[2][0].url}">
                                        value="${imageList[2][0].url }"
                                    </c:if>
                                    />
                        </div>
                        <input type="hidden" name="mutiCode" value="${mutiList[2].code }"/>
                        <input type="hidden" name="code" value="${imageList[2][0].code }"/>
                        <input type="hidden" name="isPreview" value="1"/>
                    </div>
                </form>
                <!-- }form3 -->
                <!-- 保存 -->
                <div class="saveOper">
                    <button type="button" class="btn-sure mr30" onclick="submit(3,3)">预览</button>
                    <button type="button" class="save btn-sure" onclick="submit(1,3)">保存</button>
                </div>
            </div>
            <!-- } 历史 -->
            <!-- 宗教 { -->
            <div class="posidSet-religion">
                <!-- form4{ -->
                <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                      id="form4">
                    <div class="posidSet-unit">
                        <i>图片1</i>

                        <div class="left">
                            <div class="formLine">
                                <label>图片1:</label>

                                <div id="pickfiles4" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：240 * 274</span>
                            </div>
                            <div class="formLine">
                                <label>显示标题:</label>
                                <input class="showTitle" name="name" type="text" maxlength=""
                                       value="${imageList[1][0].name }">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine">
                                <label>显示简介:</label>
                                <input class="showIntro" name="summary" type="text" maxlength=""
                                       value="${imageList[1][0].summary }">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine">
                                <label>链接至:</label>
                                <input class="toLink" name="hyperlink" type="text" maxlength=""
                                       value="${imageList[1][0].hyperlink }">
                                <span class="reqItem">*</span>
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow4" style="width:240px;height:274px;"

                            <c:if test="${empty imageList[1][0].url}">
                                 src="${ctx}//portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty imageList[1][0].url}">
                                 src="${ctx}/${imageList[1][0].url }"
                            </c:if>

                                 title="缩略图名称" alt="请上传图片" class="style-b">
                            <input type="hidden" class="url" id="url4" name="url"

                                    <c:if test="${empty imageList[1][0].url}">
                                        value="/portal/static/default/square.png"
                                    </c:if>
                                    <c:if test="${not empty imageList[1][0].url}">
                                        value="${imageList[1][0].url }"
                                    </c:if>

                                    />
                        </div>
                        <input type="hidden" name="mutiCode" value="${mutiList[1].code }"/>
                        <input type="hidden" name="code" value="${imageList[1][0].code }"/>
                        <input type="hidden" name="isPreview" value="1"/>
                    </div>
                </form>
                <!-- }form4 -->

                <!-- 保存 -->
                <div class="saveOper">
                    <button type="button" class="btn-sure mr30" onclick="submit(4,4)">预览</button>
                    <button type="button" class="save btn-sure" onclick="submit(1,4)">保存</button>
                </div>
            </div>
            <!-- } 宗教 -->
            <!-- 其他{ -->
            <div class="posidSet-other">
                <!-- form5{ -->
                <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                      id="form5">
                    <div class="posidSet-unit">
                        <i>图片1</i>
                        <div class="left">
                            <div class="formLine">
                                <label>图片1:</label>
                                <div id="pickfiles5" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：240 * 274</span>
                            </div>
                            <div class="formLine">
                                <label>显示标题:</label>
                                <input class="showTitle" name="name" type="text" maxlength=""
                                       value="${imageList[0][0].name }">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine">
                                <label>显示简介:</label>
                                <input class="showIntro" name="summary" type="text" maxlength=""
                                       value="${imageList[0][0].summary }">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine">
                                <label>链接至:</label>
                                <input class="toLink" name="hyperlink" type="text" maxlength=""
                                       value="${imageList[0][0].hyperlink }">
                                <span class="reqItem">*</span>
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow5" style="width:240px;height:274px;"

                            <c:if test="${empty imageList[0][0].url}">
                                 src="${ctx}portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty imageList[0][0].url}">
                                 src="${ctx}/${imageList[0][0].url }"
                            </c:if>
                                 title="缩略图名称" alt="请上传图片" class="style-b">
                            <input type="hidden" class="url" id="url5" name="url"

                                    <c:if test="${empty imageList[0][0].url}">
                                        value="/portal/static/default/square.png"
                                    </c:if>
                                    <c:if test="${not empty imageList[0][0].url}">
                                        value="${imageList[0][0].url }"
                                    </c:if>
                                    />
                        </div>
                        <input type="hidden" name="mutiCode" value="${mutiList[0].code }"/>
                        <input type="hidden" name="code" value="${imageList[0][0].code }"/>
                        <input type="hidden" name="isPreview" value="1"/>
                    </div>
                </form>
                <!-- }form19 --
                <!-- 保存 -->
                <div class="saveOper">
                    <button type="button" class="btn-sure mr30" onclick="submit(5,5)">预览</button>
                    <button id="save" type="button" class="save btn-sure" onclick="submit(1,5)">保存</button>
                </div>
            </div>
            <!-- } 其他 -->
        </div>
        <!-- } 推荐位图文设置 -->
    </div>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
<script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
<script type="text/javascript">
    //加载事件创建上传
    $(function () {
        for (var i = 1; i <= 5; i++) {
            cteateUploder('pickfiles' + i, 'imgshow' + i, 'url' + i);
        }
    })
</script>
<script type="text/javascript">
    /* 标签页切换 */
    $(".posidTab label").click(function () {
        var $this = $(this),
                thisIndex = $this.index();
        $this.addClass("checked").siblings().removeClass("checked");
        $(".posidSet > div").eq(thisIndex).addClass("show").siblings("div[class^='posidSet-']").removeClass("show");

        for (var i = 1; i <= 5; i++) {
            $("#pickfiles" + i).html('请上传图片');
            cteateUploder('pickfiles' + i, 'imgshow' + i, 'url' + i);
        }
    });

    /* 标题验证 */
    $(".showTitle").blur(function () {
        var thisVal = $(this).val();
        var regTest = $.VLDTOR.IsArticle(thisVal),
            regLen = inputRange(this, 2, 30);
        valid_txtBox(this, regTest && regLen, "只能为数字、中文或英文，且长度在2-30之间", "right")

    });
    /* 简介验证 */
    $(".showIntro").blur(function () {
        var thisVal = $(this).val();
        var regTest = $.VLDTOR.IsArticle(thisVal),
            regLen = inputRange(this, 2, 300);
        valid_txtBox(this, regTest && regLen, "只能为数字、中文或英文，且长度在2-300之间", "right")
    });
    /* 链接验证 */
    $(".toLink").blur(function () {
        var thisVal = $(this).val();
        var regTest = $.VLDTOR.IsHTTP(thisVal),
            regNull = $.trim(thisVal) != "";
        valid_txtBox(this, regTest && regNull, "只能以http(s)开头", "right")
    });
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
            compress: null
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
                msgBox("上传格式错误或者上传大小不能为0kb","erro");
            } else {
                msgBox("上传错误，请重试。","erro");
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
