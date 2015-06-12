<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <%@include file="/common-html/common.jsp" %>
    <title>游西藏-游西藏首页显示-自驾游攻略管理</title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css"/>
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css"/>
    <script>
        // 检索内容是否填写
        var thisWebHasVal = false;

        function submit(preview) {
            // 获取文本框数量
            var toLink = $("[name='hyperlink']"),
                toLink_len = toLink.length;
            for(var i = 0; i < toLink_len; i++) {
                var hasVal = toLink.eq(i).val().length > 0;
                var thumbnail = toLink.eq(i).parents('.posidSet-unit').find('input[id^=hiddenId]').val();
                if(hasVal && thumbnail != "portal/static/default/square.png") {
                    thisWebHasVal = true;
                    break;
                }
            }
            //保存
            var linkInput = $(".posidSet-unit .formLine input[type='text']"),
                linkInput_len = linkInput.length,
                    picInput = $(".posidSet-unit .perc40 input[type='hidden']");

            // 如果有文本框含有值
            if(thisWebHasVal) {
                // 遍历验证
                for (var i = 0; i < linkInput_len; i++) {
                    linkInput.eq(i).blur();
                    var picBtn = picInput.eq(i).parents('.posidSet-unit').find('.btn-uploadImg')[0];
                    if((linkInput.eq(i).val() != "") && (picInput.eq(i).val() == "/portal/static/default/square.png")){
                        creatErrMesg(picBtn,'请上传图片','top');
                    }else{
                        removeErrMesg(picBtn);
                    }
                }
            } else {
                msgBox("至少需要输入一项内容,并上传图片！", "erro", 2600);
                return false;
            }

            var spanReg = $("span.errMesg").length == 0;


            // 全部通过
            if (spanReg) {

                //$("input[name='isPreview']").val(preview);
                //保存
                if (preview == 1) {
                    for (var i = 1; i <= 4; i++) {
                        $.ajax({
                            type: "post",
                            url: "${ctx }web/readTibetZjyMgController/saveManageImg",
                            data: $("#form" + i).serialize(),
                            dataType: "text",
                            async: false,
                            success: function (data) {
                                //.log(data);
                            }
                        });
                    }
                    window.location.href = "${ctx }web/readTibetZjyMgController/list";
                    msgBox("保存成功！", "pass", 1600);
                } else {
                    for (var i = 1; i <= 4; i++) {
                        $.ajax({
                            type: "post",
                            url: "${ctx }web/readTibetZjyMgController/previewManageImg",
                            data: $("#form" + i).serialize(),
                            dataType: "text",
                            async: false,
                            success: function (data) {
                                //.log(data);
                            }
                        });
                    }
                    var form = $('<form style="display" target="_blank" id="open" action="${ctx}web/readTibetZjyMgController/previewManageFront" method="post"></form>');
                    $("body").append(form);
                    $("#open").submit();
                }
            }
            // 有至少一个未通过
            else {
                msgBox("输入的内容有误，请检查！", "erro", 2600);
                return false;
            }


        }

    </script>
</head>
<body>
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>自驾游攻略管理</a> -</span>
        <span><a href="#" target="_self">自驾游攻略管理</a></span>
    </div>
    <!-- 模块管理 { -->
    <div class="muduleManage filament_solid_ddd">
        <!-- 查看按钮 -->
        <div class="searchManage" style="display: none;">
            <button type="button" class="lookup btn-sure"
                    onclick="javascript:window.open('${ctx}//tourism/strage/frontIndex')">查看
            </button>
        </div>

        <!-- 景点设置 { -->
        <div class="show">
            <!-- form1{ -->
            <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                  id="form1">
                <div class="posidSet-unit">
                    <i>图片1</i>

                    <div class="left perc60">
                        <div class="formLine">
                            <label>图片1:</label>

                            <div id="pic1" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                            <span class="txt-suggest ml20">推荐尺寸：420 * 230</span>
                        </div>
                        <div class="formLine">
                            <label>链接至:</label>
                            <input name="hyperlink" value="${imageList[0][0].hyperlink }" type="text">
                            <span class="reqItem">*</span>
                        </div>
                    </div>
                    <div class="right mt10 perc40">
                        <label>缩略图:</label>


                        <img id="pre1"
                             <c:if test="${empty   imageList[0][0].url}">src="${ctx}//portal/static/default/square.png" </c:if>
                             <c:if test="${not  empty  imageList[0][0].url }">src="${ctx}${imageList[0][0].url}" </c:if>
                             title="缩略图名称" alt="请上传图片" class="style-d" style="width: 210px;height: 115px;">

                        <input type="hidden" class="url" id="hiddenId1" name="url"

                               <c:if test="${empty   imageList[0][0].url}">value="/portal/static/default/square.png" </c:if>
                               <c:if test="${not  empty  imageList[0][0].url }">value="${imageList[0][0].url}" </c:if>

                                />


                    </div>
                    <input type="hidden" name="mutiCode" id="muti" value="${mutiList[0].code }"/>
                    <input type="hidden" name="code" value="${imageList[0][0].code }"/>
                    <input type="hidden" name="isPreview" value="1"/>
                </div>
            </form>
            <!-- }form1 -->


            <!-- form2{ -->
            <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                  id="form2">
                <div class="posidSet-unit">
                    <i>图片2</i>

                    <div class="left perc60">
                        <div class="formLine">
                            <label>图片1:</label>

                            <div id="pic2" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                            <span class="txt-suggest ml20">推荐尺寸：300 * 230</span>
                        </div>
                        <div class="formLine">
                            <label>链接至:</label>
                            <input name="hyperlink" value="${imageList[0][1].hyperlink }" type="text">
                            <span class="reqItem">*</span>
                        </div>
                    </div>
                    <div class="right mt10 perc40">
                        <label>缩略图:</label>
                        <img id="pre2"
                             <c:if test="${empty imageList[0][1].url }">src="${ctx}//portal/static/default/square.png" </c:if>
                             <c:if test="${not  empty imageList[0][1].url }">src="${ctx}${imageList[0][1].url}" </c:if>
                             title="缩略图名称" alt="请上传图片" class="style-d" style="width: 180px;height: 138px;">
                        <input type="hidden" class="url" name="url" id="hiddenId2"

                               <c:if test="${empty imageList[0][1].url }">value="/portal/static/default/square.png" </c:if>
                               <c:if test="${not  empty imageList[0][1].url }">value="${imageList[0][1].url}" </c:if>


                                />
                    </div>
                </div>
                <input type="hidden" name="mutiCode" id="muti" value="${mutiList[0].code }"/>
                <input type="hidden" name="code" value="${imageList[0][1].code }"/>
                <input type="hidden" name="isPreview" value="1"/>
            </form>
            <!-- }form2 -->

            <!-- form3{ -->
            <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                  id="form3">
                <div class="posidSet-unit">
                    <i>图片3</i>

                    <div class="left perc60">
                        <div class="formLine">
                            <label>图片3:</label>

                            <div id="pic3" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                            <span class="txt-suggest ml20">推荐尺寸：300 * 230</span>
                        </div>
                        <div class="formLine">
                            <label>链接至:</label>
                            <input name="hyperlink" value="${imageList[0][2].hyperlink }" type="text">
                            <span class="reqItem">*</span>
                        </div>
                    </div>
                    <div class="right mt10 perc40">
                        <label>缩略图:</label>
                        <img id="pre3"
                             <c:if test="${empty imageList[0][2].url }">src="${ctx}//portal/static/default/square.png" </c:if>
                             <c:if test="${ not  empty imageList[0][2].url }">src="${ctx}${imageList[0][2].url}" </c:if>
                             title="缩略图名称" alt="请上传图片" class="style-d" style="width: 180px;height: 138px;">
                        <input type="hidden" class="url" name="url" id="hiddenId3"


                               <c:if test="${empty imageList[0][2].url }">value="/portal/static/default/square.png" </c:if>
                               <c:if test="${ not  empty imageList[0][2].url }">value="${imageList[0][2].url}" </c:if>

                                />
                    </div>
                </div>
                <input type="hidden" name="mutiCode" id="muti" value="${mutiList[0].code }"/>
                <input type="hidden" name="code" value="${imageList[0][2].code }"/>
                <input type="hidden" name="isPreview" value="1"/>
            </form>
            <!-- }form1 -->


            <!-- form4{ -->
            <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                  id="form4">
                <div class="posidSet-unit">
                    <i>图片4</i>

                    <div class="left perc60">
                        <div class="formLine">
                            <label>图片3:</label>

                            <div id="pic4" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                            <span class="txt-suggest ml20">推荐尺寸：420 * 230</span>
                        </div>
                        <div class="formLine">
                            <label>链接至:</label>
                            <input name="hyperlink" value="${imageList[0][3].hyperlink }" type="text">
                            <span class="reqItem">*</span>
                        </div>
                    </div>
                    <div class="right mt10 perc40">
                        <label>缩略图:</label>
                        <img id="pre4"
                             <c:if test="${ empty imageList[0][3].url }">src="${ctx}//portal/static/default/square.png" </c:if>
                             <c:if test="${ not  empty imageList[0][3].url }">src="${ctx}${imageList[0][3].url}" </c:if>
                             title="缩略图名称" alt="请上传图片" class="style-d" style="width: 210px;height: 115px;">
                        <input type="hidden" class="url" name="url" id="hiddenId4"


                               <c:if test="${ empty imageList[0][3].url }">value="/portal/static/default/square.png" </c:if>
                               <c:if test="${ not  empty imageList[0][3].url }">value="${imageList[0][3].url}" </c:if>

                                />
                    </div>
                </div>
                <input type="hidden" name="mutiCode" id="muti" value="${mutiList[0].code }"/>
                <input type="hidden" name="code" value="${imageList[0][3].code }"/>
                <input type="hidden" name="isPreview" value="1"/>
            </form>

            <!-- 保存 -->
            <div class="saveOper mt30">
                <button type="button" class="btn-sure mr30" onclick="submit(0)">预览</button>
                <button type="button" class="save btn-sure" onclick="submit(1)">保存</button>
            </div>
        </div>
        <!-- } 景点设置 -->

    </div>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>

<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>


<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common-js/common.js"></script>

<script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
<script type="text/javascript">

    //加载事件创建上传
    $(function () {
        for (var i = 1; i <= 4; i++) {
            cteateUploder('pic' + i, 'pre' + i, 'hiddenId' + i);
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
            fileSizeLimit: 400 * 1024
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
                    var uploadBtn = $("#"+preId).parents('.posidSet-unit').find('.btn-uploadImg ')[0];
                    removeErrMesg(uploadBtn);
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
<script type="text/javascript">
    // 链接验证
    $(document).on("blur", ".posidSet-unit .formLine input[type='text']", function () {
        var $this = $(this),
                thisVal = $this.val(),
                regUrl = $.VLDTOR.IsHTTP(thisVal),
                regNull = $.trim(thisVal) != "";
        // 执行验证并设置验证结果的状态
        valid_txtBox(this, regUrl || regNull, '请输入以http(s)://开头的链接', 'right');
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
