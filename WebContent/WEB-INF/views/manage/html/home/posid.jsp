<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1"/>
    <title>门户首页-推荐位管理</title>
    <%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css"/>
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css"/>
    <script src="${ctxManage}/resources/plugin/respond.min.js"
            type="text/javascript"></script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>门户首页</a> -</span>
        <span><a href="#" target="_self">推荐位管理</a></span>
    </div>

    <!-- 模块管理 { -->
    <div class="muduleManage filament_solid_ddd">
        <!-- 查看按钮 -->
        <div class="searchManage">
            <button type="button" class="lookup btn-sure" onclick="javascript:window.open('${ctx}')">查看</button>
        </div>

        <!-- 推荐位选择标签 { -->
        <div class="posidTab">
            <label class="checked">推荐位一</label>
            <label>推荐位二</label>
            <label>推荐位三</label>
        </div>
        <!-- } 推荐位选择标签 -->

        <!-- 推荐位图文设置 { -->
        <div class="posidSet filament_solid_ccc">
            <!-- 推荐位一 { -->
            <div class="posidSet-1 show">
                <!-- form1 -->
                <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                      id="form1">
                    <div class="posidSet-unit">
                        <i>图片1</i>

                        <div class="left">
                            <div class="formLine">
                                <label>图片1:</label>

                                <div id="pickfiles1" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：360 * 400</span>
                            </div>
                            <div class="formLine">
                                <label>显示标题:</label>
                                <input class="showTitile showTitle" value="${imageList[0][0].name }" name="name"
                                       type="text">
                            </div>
                            <div class="formLine" style="display: none;">
                                <label>显示简介:</label>
                                <input class="showIntro" value="${imageList[0][0].summary }" name="summary" type="text">
                            </div>
                            <div class="formLine">
                                <label>链接至:</label>
                                <input class="toLink" name="hyperlink" value="${imageList[0][0].hyperlink }"
                                       type="text">
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow1" style="width:180px;height:200px;"

                            <c:if test="${empty imageList[0][0].url}">
                                 src="${ctx}//portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty imageList[0][0].url}">
                                 src="${ctx}/${imageList[0][0].url }"
                            </c:if>
                                 title="缩略图名称" alt="请上传图片" class="style-b">
                            <input type="hidden" class="url" id="url1" name="url"

                                    <c:if test="${empty imageList[0][0]}">
                                        value="/portal/static/default/square.png"
                                    </c:if>
                                    <c:if test="${not empty imageList[0][0]}">
                                        value="${imageList[0][0].url }"
                                    </c:if>

                                    />
                        </div>
                        <input type="hidden" name="mutiCode" value="${mutiList[0].code }"/>
                        <input type="hidden" name="code" value="${imageList[0][0].code }"/>
                        <input type="hidden" name="isPreview" value="1"/>
                    </div>
                </form>
                <!-- form2 --- form7 -->
                <c:forEach var="x" begin="2" end="7" step="1" varStatus="status">
                    <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                          id="form${x}">
                        <div class="posidSet-unit">
                            <i>图片${x}</i>

                            <div class="left">
                                <div class="formLine">
                                    <label>图片${x}:</label>

                                    <div id="pickfiles${x}" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                    <span class="txt-suggest ml20">
                                        <c:choose>
                                            <c:when test="${status.index == 6}">
                                                推荐尺寸：236 * 110
                                            </c:when>
                                            <c:when test="${status.index == 7}">
                                                推荐尺寸：360 * 160
                                            </c:when>
                                            <c:otherwise>
                                                推荐尺寸：226 * 220
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                                <div class="formLine">
                                    <label>显示标题:</label>
                                    <input class="showTitile showTitle" value="${imageList[0][x-1].name }" name="name"
                                           type="text">
                                </div>
                                <div class="formLine" style="display: none;">
                                    <label>显示简介:</label>
                                    <input class="showIntro" name="summary" value="${imageList[0][x-1].summary }"
                                           type="text">
                                </div>
                                <div class="formLine">
                                    <label>链接至:</label>
                                    <input class="toLink" name="hyperlink" value="${imageList[0][x-1].hyperlink }"
                                           type="text">
                                </div>
                            </div>
                            <div class="right mt30">
                                <label>缩略图:</label>
                                <img id="imgshow${x}"
                                <c:choose>
                                <c:when test="${status.index == 6}">
                                     style="width:236px; height:110px;"
                                </c:when>
                                <c:when test="${status.index == 7}">
                                     style="width:360px; height:160px;"
                                </c:when>
                                <c:otherwise>
                                     style="width:226px; height:220px;"
                                </c:otherwise>
                                </c:choose>
                                <c:if test="${empty imageList[0][x-1].url}">
                                     src="${ctx}//portal/static/default/square.png"
                                </c:if>
                                <c:if test="${not empty imageList[0][x-1].url}">
                                     src="${ctx}/${imageList[0][x-1].url }"
                                </c:if>
                                     title="缩略图名称" alt="请上传图片" class="style-b">
                                <input type="hidden" class="url" id="url${x}" name="url"

                                        <c:if test="${empty imageList[0][x-1].url}">
                                            value="/portal/static/default/square.png"
                                        </c:if>
                                        <c:if test="${not empty imageList[0][x-1].url}">
                                            value="${imageList[0][x-1].url }"
                                        </c:if>

                                        />
                            </div>
                            <input type="hidden" name="mutiCode" value="${mutiList[0].code }"/>
                            <input type="hidden" name="code" value="${imageList[0][x-1].code }"/>
                            <input type="hidden" name="isPreview" value="1"/>
                        </div>
                    </form>

                </c:forEach>
                <!-- 保存 -->
                <div class="saveOper">
                    <button type="button" class="btn-sure mr30" onclick="submit(2,1,0)">预览</button>
                    <button type="button" class="save btn-sure" onclick="submit(1,1,'d321de41-cd1e-4576-ba3a-4d1183ff714b')">保存</button>
                </div>
            </div>
            <!-- } 推荐位一 -->

            <!-- 推荐位二 { -->
            <div class="posidSet-2">
                <!-- form8 -->
                <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                      id="form8">
                    <div class="posidSet-unit">
                        <i>图片1</i>

                        <div class="left">
                            <div class="formLine">
                                <label>图片1:</label>

                                <div id="pickfiles8" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：425 * 255</span>
                            </div>
                            <div class="formLine">
                                <label>显示标题:</label>
                                <input class="showTitile showTitle" value="${imageList[1][0].name }" name="name"
                                       type="text">
                            </div>
                            <div class="formLine"  style="display: none;">
                                <label>显示简介:</label>
                                <input class="showIntro" name="summary" value="${imageList[1][0].summary }" type="text">
                            </div>
                            <div class="formLine">
                                <label>链接至:</label>
                                <input class="toLink" name="hyperlink" value="${imageList[1][0].hyperlink }"
                                       type="text">
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow8" style="width:255px;height:153px;"

                            <c:if test="${empty imageList[1][0].url}">
                                 src="${ctx}//portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty imageList[1][0].url}">
                                 src="${ctx}/${imageList[1][0].url }"
                            </c:if>

                                 title="缩略图名称" alt="请上传图片" class="style-b">
                            <input type="hidden" class="url" id="url8" name="url"

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

                <!-- form9 --- form14 -->
                <c:forEach var="x" begin="9" end="14" step="1" varStatus="status">
                    <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                          id="form${x}">
                        <div class="posidSet-unit">
                            <i>图片${x-7}</i>

                            <div class="left">
                                <div class="formLine">
                                    <label>图片${x-7}:</label>

                                    <div id="pickfiles${x}" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                    <span class="txt-suggest ml20">
                                        <c:choose>
                                            <c:when test="${status.index == 9 || status.index == 14}">
                                                推荐尺寸：350 * 270
                                            </c:when>
                                            <c:when test="${status.index == 11}">
                                                推荐尺寸：300 * 255
                                            </c:when>
                                            <c:otherwise>
                                                推荐尺寸：230 * 270
                                            </c:otherwise>
                                        </c:choose>    
                                    </span>
                                </div>
                                <div class="formLine">
                                    <label>显示标题:</label>
                                    <input class="showTitile showTitle" name="name" value="${imageList[1][x-8].name }"
                                           type="text">
                                </div>
                                <div class="formLine" style="display: none;">
                                    <label>显示简介:</label>
                                    <input class="showIntro" name="summary" value="${imageList[1][x-8].summary }"
                                           type="text">
                                </div>
                                <div class="formLine">
                                    <label>链接至:</label>
                                    <input class="toLink" name="hyperlink" value="${imageList[1][x-8].hyperlink }"
                                           type="text">
                                </div>
                            </div>
                            <div class="right mt30">
                                <label>缩略图:</label>
                                <img id="imgshow${x}"
                                <c:choose>
                                <c:when test="${status.index == 9 || status.index == 14}">
                                     style="width: 210px;height: 162px;"
                                </c:when>
                                <c:when test="${status.index == 11}">
                                     style="width:180px;height:153px;"
                                </c:when>
                                <c:otherwise>
                                     style="width:184px;height:216px;"
                                </c:otherwise>
                                </c:choose>

                                <c:if test="${empty imageList[1][x-8].url}">
                                     src="${ctx}/portal/static/default/square.png"
                                </c:if>
                                <c:if test="${not empty imageList[1][x-8].url}">
                                     src="${ctx}/${imageList[1][x-8].url }"
                                </c:if>

                                     title="缩略图名称" alt="请上传图片" class="style-b">

                                <input type="hidden" id="url${x}" class="url" name="url"
                                        <c:if test="${empty imageList[1][x-8].url}">
                                            value="/portal/static/default/square.png"
                                        </c:if>
                                        <c:if test="${not empty imageList[1][x-8].url}">
                                            value="${imageList[1][x-8].url }"
                                        </c:if>

                                        />
                            </div>
                            <input type="hidden" name="mutiCode" value="${mutiList[1].code }"/>
                            <input type="hidden" name="code" value="${imageList[1][x-8].code }"/>
                            <input type="hidden" name="isPreview" value="1"/>
                        </div>
                    </form>
                </c:forEach>
                <!-- 保存 -->
                <div class="saveOper">
                    <button type="button" class="btn-sure mr30" onclick="submit(2,8,0)">预览</button>
                    <button type="button" class="save btn-sure" onclick="submit(1,8,'4debe6ec-dbbf-45ee-86a4-fbeb3d1b28af')">保存</button>
                </div>
            </div>
            <!-- } 推荐位二 -->

            <!-- 推荐位三 { -->
            <div class="posidSet-3">
                <!-- form15-->
                <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                      id="form15">
                    <div class="posidSet-unit">
                        <i>图片1</i>

                        <div class="left">
                            <div class="formLine">
                                <label>图片1:</label>

                                <div id="pickfiles15" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：270 * 340</span>
                            </div>
                            <div class="formLine">
                                <label>显示标题:</label>
                                <input class="showTitile showTitle" name="name" value="${imageList[2][0].name }"
                                       type="text">
                            </div>
                            <div class="formLine" style="display: none;">
                                <label>显示简介:</label>
                                <input class="showIntro" name="summary" value="${imageList[2][0].summary }" type="text">
                            </div>
                            <div class="formLine">
                                <label>链接至:</label>
                                <input class="toLink" name="hyperlink" value="${imageList[2][0].hyperlink }"
                                       type="text">
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow15" style="width:135px;height:170px;"

                            <c:if test="${empty imageList[2][0].url}">
                                 src="${ctx}/portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty imageList[2][0].url}">
                                 src="${ctx}/${imageList[2][0].url }"
                            </c:if>

                                 title="缩略图名称" alt="请上传图片" class="style-b">

                            <input type="hidden" id="url15" class="url" name="url"

                                    <c:if test="${empty imageList[2][0].url}">
                                        value="/portal/static/default/square.png"
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
                <!-- form16 --- form21 -->
                <c:forEach var="x" begin="16" end="20" step="1" varStatus="status">
                    <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                          id="form${x}">
                        <div class="posidSet-unit">
                            <i>图片${x-14}</i>

                            <div class="left">
                                <div class="formLine">
                                    <label>图片${x-14}:</label>

                                    <div id="pickfiles${x}" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                    <span class="txt-suggest ml20">
                                        <c:choose>
                                            <c:when test="${status.index == 16 || status.index == 17}">
                                                推荐尺寸：270 * 340
                                            </c:when>
                                            <c:when test="${status.index == 18}">
                                                推荐尺寸：300 * 240
                                            </c:when>
                                            <c:when test="${status.index == 19}">
                                                推荐尺寸：425 * 220
                                            </c:when>
                                            <c:otherwise>
                                                推荐尺寸：300 * 150
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                                <div class="formLine">
                                    <label>显示标题:</label>
                                    <input class="showTitile showTitle" name="name" value="${imageList[2][x-15].name }"
                                           type="text">
                                </div>
                                <div class="formLine" style="display: none;">
                                    <label>显示简介:</label>
                                    <input class="showIntro" name="summary" value="${imageList[2][x-15].summary }"
                                           type="text">
                                </div>
                                <div class="formLine">
                                    <label>链接至:</label>
                                    <input class="toLink" name="hyperlink" value="${imageList[2][x-15].hyperlink }"
                                           type="text">
                                </div>
                            </div>
                            <div class="right mt30">
                                <label>缩略图:</label>
                                <img id="imgshow${x}"
                                <c:choose>
                                    <c:when test="${status.index == 16 || status.index == 17}">
                                         style="width:135px;height:170px;"
                                    </c:when>
                                    <c:when test="${status.index == 18}">
                                         style="width:180px;height:144px;"
                                    </c:when>
                                        <c:when test="${status.index == 19}">
                                             style="width:255px;height:132px;"
                                    </c:when>
                                    <c:otherwise>
                                         style="width:300px;height:150px;"
                                </c:otherwise>
                                </c:choose>
                                <c:if test="${empty imageList[2][x-15].url}">
                                     src="${ctx}//portal/static/default/square.png"
                                </c:if>
                                <c:if test="${not empty imageList[2][x-15].url}">
                                     src="${ctx}/${imageList[2][x-15].url }"
                                </c:if>
                                     title="缩略图名称" alt="请上传图片" class="style-b">
                                <input type="hidden" id="url${x}" class="url" name="url"

                                        <c:if test="${empty imageList[2][x-15].url}">
                                            value="/portal/static/default/square.png"
                                        </c:if>
                                        <c:if test="${not empty imageList[2][x-15].url}">
                                            value="${imageList[2][x-15].url }"
                                        </c:if>
                                        />
                            </div>
                            <input type="hidden" name="mutiCode" value="${mutiList[2].code }"/>
                            <input type="hidden" name="code" value="${imageList[2][x-15].code }"/>
                            <input type="hidden" name="isPreview" value="1"/>
                        </div>
                    </form>
                </c:forEach>
                <!-- 保存 -->
                <div class="saveOper">
                    <button type="button" class="btn-sure mr30" onclick="submit(2,15,0)">预览</button>
                    <button type="button" class="save btn-sure" onclick="submit(1,15,'32713644-32f0-4486-8b4b-048e43031035')">保存</button>
                </div>
            </div>
            <!-- } 推荐位三 -->
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
<script src="${ctxManage}/resources/js/dataValidation.js"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
<script type="text/javascript">
    /* 标签页切换 */
    var uploaders = new Array();
    $(".posidTab label").click(function () {


        var $this = $(this),
                thisIndex = $this.index();
        $this.addClass("checked").siblings().removeClass("checked");
        $(".posidSet > div").eq(thisIndex).addClass("show").siblings("div[class^='posidSet-']").removeClass("show");
        var upLoadDiv = $(".posidSet>div.show").find("div[id^=pickfiles]");
        for (var i = 0; i < upLoadDiv.length; i++) {
            var upLoadNode = upLoadDiv.eq(i).attr('id'),
                    upLoadNum = upLoadNode.split('pickfiles')[1];
            $("#pickfiles" + upLoadNum).html('请上传图片');
            var up = cteateUploder('pickfiles' + upLoadNum, 'imgshow' + upLoadNum, 'url' + upLoadNum);

        }
    });

    /* 标题验证 */
    $(".showTitle").blur(function () {
        var thisVal = $(this).val();
        var regTest = $.VLDTOR.IsArticle(thisVal),
                regTest2 = $.VLDTOR.IsNum(thisVal),
                regLen = inputRange(this, 2, 30);
        if (regTest2) {
            valid_txtBox_create(this, false, "不能为纯数字", "right");
            return;
        }
        valid_txtBox_create(this, regTest && regLen || thisVal == "", "只能为数字、中文或英文，且长度在2-30之间", "right");
    });
    /* 简介验证 */
    $(".showIntro").blur(function () {
        var thisVal = $(this).val();
        var regTest = $.VLDTOR.IsArticle(thisVal),
                regLen = inputRange(this, 2, 300);
        valid_txtBox_create(this, regTest && regLen || thisVal == "", "只能为数字、中文或英文，且长度在2-300之间", "right");
    });
    /* 链接验证 */
    $(".toLink").blur(function () {
        var thisVal = $(this).val();
        var regTest = $.VLDTOR.IsHTTP(thisVal);
        valid_txtBox_create(this, regTest || thisVal == "", "请输入以http(s)://开头的链接", "right");
    });
</script>
<script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
<script type="text/javascript">

    //加载事件创建上传
    $(function () {
        for (var i = 1; i <= 23; i++) {
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
            compress: null
        };
        var up = new WebUploader.Uploader(options_);
        addEvent(up, preId, urlHiddenId);
        return up;
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
    //表单顺序提交
    function submit(preview, x,muticode) {
        
//        $(".show input.showIntro").blur();
//        $(".show input.showTitle").blur();
//        $(".show input.toLink").blur();

        // 循环遍历判断表单组的填写
        var formGroup = $(".posidSet .show").find("form[id^='form']"),
                formGroup_len = formGroup.length;
        for (var i = 0; i < formGroup_len; i++) {
            var groupInput = formGroup.eq(i).find("input[type='text']"),
                    groupInput_len = groupInput.length,
                    hasVal = false;
            // 遍历出组内的文本框
            for (var j = 0; j < groupInput_len; j++) {
                var this_val = groupInput.eq(j).val();
                // 如果至少有一个含有数组的文本框
                if (this_val != "") {
                    // 做上有值组的标记
                    hasVal = true;
                }
            }
            // 如果该组有至少一个文本框填写
            if (hasVal == true) {
                // 遍历所有文本框
                for (var k = 0; k < groupInput_len; k++) {
                    var this_val = groupInput.eq(k).val();
                    // 如果含有没有值的项
                    if (this_val == "") {
                        // 创建一个错误信息
                        creatErrMesg($(groupInput.eq(k)), "已填写内容的组，内容必须填写完整", "right");
                    } else {
                        // 正常数据失焦验证
                        $(groupInput.eq(k)).blur();
                    }
                }
            }
            // 执行正常的数据失焦验证
            else {
                for (var m = 0; m < groupInput_len; m++) {
                    $(groupInput.eq(m)).blur();
                }
            }
        }

        var regSpan = $(".show span.errMesg").length > 0;
        if (regSpan) {
            msgBox("输入的内容有误，请检查！", "erro", 1000);
        } else {
            if (preview == 1) {
            	$.ajax({
    			 	async: false,
    			    type:'post',
    			    url:'${ctx}/web/imageController/deleteImageBymutiCode?muticode='+muticode,
    			    success:function(data){
    			    	for (var i = x; i <= x + 6; i++) {
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
    	                msgBox("保存成功", "pass", 2600);
    	                window.location.href = "${ctx}/web/homeController/getManageImg?programaCode=13173f79-75da-11e4-b6ce-005056a05bc9";

    				   }


    			});
                
            } else {
            	 for (var i = x; i <= x + 6; i++) {
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
        }
		


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

