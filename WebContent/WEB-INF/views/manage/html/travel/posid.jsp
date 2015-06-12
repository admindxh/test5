<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1"/>
    <%@include file="/common-html/common.jsp" %>
    <title>游西藏-游西藏首页显示-推荐位管理</title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css"/>
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css"/>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <script type="text/javascript">

        //表单顺序提交
        function submit(ident,preview, x) {
            // 让全部输入框执行一次失焦验证事件
            $(".show input[id^='name']").blur();
            $(".show input[id^='summary']").	blur();
            $(".show input[id^='hyperlink']").blur();

            // 检索内容是否填写
            var thisWebHasVal = false;

            // 遍历出所有的表单内容组
            var formGroup = $(ident).parents("[class^='posidSet-']").find(".posidSet-unit"),
                formGroup_len = formGroup.length;
            for(var i = 0; i < formGroup_len; i++) {
                var formGroup_ele = formGroup.eq(i).find("input[type='text']"),
                    formGroup_ele_len = formGroup_ele.length;
                for(var j = 0; j < formGroup_ele_len; j++) {
                    var eleHasVal = formGroup_ele.eq(j).val().length > 0;
                    if(eleHasVal) {
                        thisWebHasVal = true;
                        formGroup_ele.eq(j).parents(".posidSet-unit").addClass("validThis");
                    } else {
                        formGroup_ele.eq(j).parents(".posidSet-unit").removeClass("validThis");
                    }
                }
            }
            // 如果含有已输入值的组
            if(thisWebHasVal) {
                var txtNoVal = false,
                    noValEle = $(".validThis").find("input[type='text']"),
                    noValEle_len = noValEle.length;
                for(var k = 0; k < noValEle_len; k++) {
                    var thisTxtHasVal =  noValEle.eq(k).val().length == 0;
                    if(thisTxtHasVal) {
                        txtNoVal = true;
                        msgBox("已输入内容的组必须填写完整！", "erro", 2600);
                        return;
                    }
                }
            } else {
                msgBox("至少需要输入一组完整添加项！", "erro", 2600);
                return false;
            }

            var hasErro = $(".show .errMesg").length > 0;

            if (hasErro) {
                msgBox("输入的内容有误，请检查！", "erro", 2600);
                return false;
            } else {

                // 提交表单
                if (preview == 1) {
                    for (var i = x; i <= x + 5; i++) {
                        $.ajax({
                            type: "post",
                            url: "${ctx }web/travelController/saveManageImg",
                            data: $("#form" + i).serialize(),
                            dataType: "text",
                            async: false,
                            success: function (data) {
                                //.log(data);
                            }
                        });
                    }
                    msgBox("保存成功！", "pass", 2600);
                    window.location.href="${ctx}/web/travelController/getManageImg?programaCode=bc22ed19-b2bc-42cd-a8c9-beb96e25ed89";
                } else {
                    for (var i = x; i <= x + 5; i++) {
                        $.ajax({
                            type: "post",
                            url: "${ctx }web/travelController/previewManageImg",
                            data: $("#form" + i).serialize(),
                            dataType: "text",
                            async: false,
                            success: function (data) {
                                //.log(data);
                            }
                        });
                    }
                    var form = $('<form style="display" target="_blank" id="open" action="${ctx}web/travelController/previewManageFront" method="post"></form>');
                    $("body").append(form);
                    $("#open").submit();
                }
            }

        }

        function toIndex() {
            window.open("${ctx}tourism/strage/frontIndex");
        }
    </script>
</head>
<body>
<!-- main { -->
<div class="main">

    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>游西藏</a> -</span>
        <span><a href="${ctx }tourism/strage/frontIndex" target="_self">游西藏首页显示</a> -</span>
        <span><a>推荐位管理</a></span>
    </div>

    <div class="operManage">
        <button class="btn-sure" onclick="toIndex()" type="button">查看</button>
    </div>

    <!-- 模块管理 { -->
    <div class="muduleManage filament_solid_ddd">

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
                <form action="${ctx }web/travelController/saveManageImg" method="post" enctype="multipart/form-data"
                      id="form1">
                    <div class="posidSet-unit">
                        <i>图片1</i>

                        <div class="left">
                            <div class="formLine">
                                <label>图片1:</label>

                                <div id="pickfiles1" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：400 * 300</span>
                            </div>
                            <div class="formLine">
                                <label>显示标题:</label>
                                <input id="name1" name="name" value="${imageList[2][0].name }" type="text">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine" style="display: none;">
                                <label>显示简介:</label>
                                <input id="summary1" name="summary" value="${imageList[2][0].summary }" type="text">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine">
                                <label>链接至:</label>
                                <input id="hyperlink1" name="hyperlink" value="${imageList[2][0].hyperlink }"
                                       type="text">
                                <span class="reqItem">*</span>
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow1" style="width:200px;height:150px;"
                            <c:if test="${empty imageList[2][0].url}">
                                 src="${ctx}//portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty imageList[2][0].url}">
                                 src="${ctx}/${imageList[2][0].url }"
                            </c:if>

                                 title="缩略图名称" alt="请上传图片" class="style-b">
                            <input type="hidden" class="url" id="url1" name="url"

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
                <!-- form2 --- form6 -->
                <c:forEach var="x" begin="2" end="6" step="1" varStatus="status">
                    <form action="${ctx }web/travelController/saveManageImg" method="post" enctype="multipart/form-data"
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
                                                推荐尺寸：210 * 160
                                            </c:when>
                                            <c:otherwise>
                                                推荐尺寸：200 * 320
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                                <div class="formLine">
                                    <label>显示标题:</label>
                                    <input id="name${x}" name="name" type="text" value="${imageList[2][x-1].name}">
                                    <span class="reqItem">*</span>
                                </div>
                                <div class="formLine">
                                    <label>链接至:</label>
                                    <input id="hyperlink${x}" name="hyperlink" type="text"
                                           value="${imageList[2][x-1].hyperlink}">
                                    <span class="reqItem">*</span>
                                </div>
                            </div>
                            <div class="right mt30">
                                <label>缩略图:</label>
                                <img id="imgshow${x}"
                                <c:choose>
                                    <c:when test="${status.index == 6}">
                                         推荐尺寸：210 * 160
                                         style="width:210px;height:160px;"
                                    </c:when>
                                    <c:otherwise>
                                         推荐尺寸：200 * 320
                                         style="width:120px;height:192px;"
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${empty imageList[2][x-1].url}">
                                     src="${ctx}//portal/static/default/square.png"
                                </c:if>
                                <c:if test="${not empty imageList[2][x-1].url}">
                                     src="${ctx}/${imageList[2][x-1].url }"
                                </c:if>


                                     title="缩略图名称" alt="请上传图片" class="style-b">
                                <input type="hidden" id="url${x }" class="url" name="url"
                                        <c:if test="${empty imageList[2][x-1].url}">
                                            value="portal/static/default/square.png"
                                        </c:if>
                                        <c:if test="${not empty imageList[2][x-1].url}">
                                            value="${imageList[2][x-1].url }"
                                        </c:if>


                                        />
                            </div>
                            <input type="hidden" name="mutiCode" value="${mutiList[2].code }"/>
                            <input type="hidden" name="code" value="${imageList[2][x-1].code }"/>
                            <input type="hidden" name="isPreview" value="1"/>
                        </div>
                    </form>

                </c:forEach>
                <!-- 保存 -->
                <div class="saveOper">
                    <button type="button" class="btn-sure mr30" onclick="submit(this,2,1)">预览</button>
                    <button type="button" class="save btn-sure" onclick="submit(this,1,1)">保存</button>
                </div>
            </div>
            <!-- } 推荐位一 -->

            <!-- 推荐位二 { -->
            <div class="posidSet-2">
                <!-- form7 -->
                <form action="${ctx }web/travelController/saveManageImg" method="post" enctype="multipart/form-data"
                      id="form7">
                    <div class="posidSet-unit">
                        <i>图片1</i>

                        <div class="left">
                            <div class="formLine">
                                <label>图片1:</label>

                                <div id="pickfiles7" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：260 * 160</span>
                            </div>
                            <div class="formLine">
                                <label>显示标题:</label>
                                <input id="name7" name="name" value="${imageList[1][0].name }" type="text">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine" style="display: none;">
                                <label>显示简介:</label>
                                <input id="summary7" name="summary" value="${imageList[1][0].summary }" type="text">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine">
                                <label>链接至:</label>
                                <input id="hyperlink7" name="hyperlink" value="${imageList[1][0].hyperlink }"
                                       type="text">
                                <span class="reqItem">*</span>
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow7" style="width:260px;height:160px;"

                            <c:if test="${empty imageList[1][0].url}">
                                 src="${ctx}//portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty imageList[1][0].url}">
                                 src="${ctx}/${imageList[1][0].url }"
                            </c:if>

                                 title="缩略图名称" alt="请上传图片" class="style-b">
                            <input type="hidden" id="url7" class="url" name="url"

                                    <c:if test="${empty imageList[1][0].url}">
                                        value="portal/static/default/square.png"
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

                <!-- form8 --- form12 -->
                <c:forEach var="x" begin="8" end="10" step="1" varStatus="status">
                    <form action="${ctx }web/travelController/saveManageImg" method="post" enctype="multipart/form-data"
                          id="form${x}">
                        <div class="posidSet-unit">
                            <i>图片${x-6}</i>

                            <div class="left">
                                <div class="formLine">
                                    <label>图片${x-7}:</label>

                                    <div id="pickfiles${x}" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                    <span class="txt-suggest ml20">
                                        <c:choose>
                                            <c:when test="${status.index == 8}">
                                                推荐尺寸：200 * 320
                                            </c:when>
                                            <c:when test="${status.index == 9}">
                                                推荐尺寸：300 * 320
                                            </c:when>
                                            <c:otherwise>
                                                推荐尺寸：460 * 330
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                                <div class="formLine">
                                    <label>显示标题:</label>
                                    <input id="name${x}" name="name" value="${imageList[1][x-7].name }" type="text">
                                    <span class="reqItem">*</span>
                                </div>
                                <div class="formLine">
                                    <label>链接至:</label>
                                    <input id="hyperlink${x}" value="${imageList[1][x-7].hyperlink }" name="hyperlink"
                                           type="text">
                                    <span class="reqItem">*</span>
                                </div>
                            </div>
                            <div class="right mt30">
                                <label>缩略图:</label>
                                <img id="imgshow${x}"
                                <c:choose>
                                    <c:when test="${status.index == 8}">
                                         推荐尺寸：200 * 320
                                        style="width:120px;height:192px;"
                                    </c:when>
                                    <c:when test="${status.index == 9}">
                                         推荐尺寸：300 * 320
                                        style="width:150px;height:160px;"
                                    </c:when>
                                    <c:otherwise>
                                         推荐尺寸：460 * 330
                                        style="width:230px;height:165px;"
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${empty imageList[1][x-7].url}">
                                     src="${ctx}//portal/static/default/square.png"
                                </c:if>
                                <c:if test="${not empty imageList[1][x-7].url}">
                                     src="${ctx}/${imageList[1][x-7].url }"
                                </c:if>
                                     title="缩略图名称" alt="请上传图片" class="style-b">
                                <input type="hidden" id="url${x}" class="url" name="url"


                                        <c:if test="${empty imageList[1][x-7].url}">
                                            value="portal/static/default/square.png"
                                        </c:if>
                                        <c:if test="${not empty imageList[1][x-7].url}">
                                            value="${imageList[1][x-7].url }"
                                        </c:if>

                                        />
                            </div>
                            <input type="hidden" name="mutiCode" value="${mutiList[1].code }"/>
                            <input type="hidden" name="code" value="${imageList[1][x-7].code }"/>
                            <input type="hidden" name="isPreview" value="1"/>
                        </div>
                    </form>
                </c:forEach>
                <!-- 保存 -->
                <div class="saveOper">
                    <button type="button" class="btn-sure mr30" onclick="submit(this,2,7)">预览</button>
                    <button type="button" class="save btn-sure" onclick="submit(this,1,7)">保存</button>
                </div>
            </div>
            <!-- } 推荐位二 -->

            <!-- 推荐位三 { -->
            <div class="posidSet-3">
                <!-- form13-->
                <form action="${ctx }web/travelController/saveManageImg" method="post" enctype="multipart/form-data"
                      id="form13">
                    <div class="posidSet-unit">
                        <i>图片1</i>

                        <div class="left">
                            <div class="formLine">
                                <label>图片1:</label>

                                <div id="pickfiles13" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：380 * 160</span>
                            </div>
                            <div class="formLine">
                                <label>显示标题:</label>
                                <input id="name13" name="name" value="${imageList[0][0].name }" type="text">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine"  style="display: none;">
                                <label>显示简介:</label>
                                <input id="summary13" name="summary" value="${imageList[0][0].summary }" type="text">
                                <span class="reqItem">*</span>
                            </div>
                            <div class="formLine">
                                <label>链接至:</label>
                                <input id="hyperlink13" name="hyperlink" value="${imageList[0][0].hyperlink }"
                                       type="text">
                                <span class="reqItem">*</span>
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow13" style="width:380px;height:160px;"

                            <c:if test="${empty imageList[0][0].url}">
                                 src="${ctx}//portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty imageList[0][0].url}">
                                 src="${ctx}/${imageList[0][0].url }"
                            </c:if>


                                 title="缩略图名称" alt="请上传图片" class="style-b yxzPosit1">
                            <input type="hidden" id="url13" class="url" name="url"

                                    <c:if test="${empty imageList[0][0].url}">
                                        value="portal/static/default/square.png"
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
                <!-- form14 --- form18 -->
                <c:forEach var="x" begin="14" end="18" step="1" varStatus="status">
                    <form action="${ctx }web/travelController/saveManageImg" method="post" enctype="multipart/form-data"
                          id="form${x}">
                        <div class="posidSet-unit">
                            <i>图片${x-12}</i>

                            <div class="left">
                                <div class="formLine">
                                    <label>图片${x-12}:</label>

                                    <div id="pickfiles${x}" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                    <span class="txt-suggest ml20">
                                        <c:choose>
                                            <c:when test="${status.index == 17}">
                                                推荐尺寸：380 * 160
                                            </c:when>
                                            <c:otherwise>
                                                推荐尺寸：165 * 160
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                                <div class="formLine">
                                    <label>显示标题:</label>
                                    <input id="name${x}" name="name" value="${imageList[0][x-13].name }" type="text">
                                    <span class="reqItem">*</span>
                                </div>
                                <div class="formLine">
                                    <label>链接至:</label>
                                    <input id="hyperlink${x}" name="hyperlink" value="${imageList[0][x-13].hyperlink }"
                                           type="text">
                                    <span class="reqItem">*</span>
                                </div>
                            </div>
                            <div class="right mt30">
                                <label>缩略图:</label>
                                <img id="imgshow${x}"
                                <c:choose>
                                    <c:when test="${status.index == 17}">
                                        style="width:380px;height:160px;" class="style-b yxzPosit1"
                                    </c:when>
                                    <c:otherwise>
                                        style="width:165px;height:160px;" class="style-b"
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${empty imageList[0][x-13].url}">
                                     src="${ctx}//portal/static/default/square.png"
                                </c:if>
                                <c:if test="${not empty imageList[0][x-13].url}">
                                     src="${ctx}/${imageList[0][x-13].url }"
                                </c:if>
                                     title="缩略图名称" alt="请上传图片">
                                <input type="hidden" id="url${x}" class="url" name="url"

                                        <c:if test="${empty imageList[0][x-13].url}">
                                            value="portal/static/default/square.png"
                                        </c:if>
                                        <c:if test="${not empty imageList[0][x-13].url}">
                                            value="${imageList[0][x-13].url }"
                                        </c:if>

                                        />
                            </div>
                            <input type="hidden" name="mutiCode" value="${mutiList[0].code }"/>
                            <input type="hidden" name="code" value="${imageList[0][x-13].code }"/>
                            <input type="hidden" name="isPreview" value="1"/>
                        </div>
                    </form>
                </c:forEach>
                <!-- 保存 -->
                <div class="saveOper">
                    <button type="button" class="btn-sure mr30" onclick="submit(this,2,13)">预览</button>
                    <button type="button" class="save btn-sure" onclick="submit(this,1,13)">保存</button>
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
<script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
<script type="text/javascript">
    /* 标签页切换 */
    $(".posidTab label").click(function () {
               var $this = $(this),
                thisIndex = $this.index();
        $this.addClass("checked").siblings().removeClass("checked");
        $(".posidSet > div[class^='posidSet-']").eq(thisIndex).addClass("show").siblings("div[class^='posidSet-']").removeClass("show");
        var upLoadDiv = $(".posidSet>div.show").find("div[id^=pickfiles]");
        for (var i = 0; i < upLoadDiv.length; i++) {
            var upLoadNode = upLoadDiv.eq(i).attr('id'),
                    upLoadNum = upLoadNode.split('pickfiles')[1];
            $("#pickfiles" + upLoadNum).html('请上传图片');
            var up = cteateUploder('pickfiles' + upLoadNum, 'imgshow' + upLoadNum, 'url' + upLoadNum);

        }

    });
    // 显示标题
    $("input[id^='name']").blur(function () {
        var thisVal = $(this).val(),
            regTest = $.VLDTOR.IsArticle(thisVal),
            regNum = $.VLDTOR.IsNum(thisVal),
            regLen = inputRange(this, 2, 30);
        if(regNum) {
            valid_txtBox(this, false, "标题不能为纯数字，且长度在2-30之间", "right");
        } else {
            valid_txtBox(this, regTest && regLen || thisVal == "", "只能为常用字符及文字，且长度在2-30之间", "right");
        }
    });

    // 显示简介blur验证
    $("input[id^='summary']").blur(function () {
        var thisVal = $(this).val(),
            regTest = $.VLDTOR.IsArticle(thisVal),
            regLen = inputRange(this, 2, 300);
        valid_txtBox(this, regTest && regLen || thisVal == "", "只能为常用字符及文字，且长度在2-300之间", "right");
    });

    // 链接blur验证
    $("input[id^='hyperlink']").blur(function () {
        var thisVal = $(this).val(),
            regTest = $.VLDTOR.IsHTTP(thisVal),
            regNull = thisVal == "";
        valid_txtBox(this, regTest || regNull, "只能以http(s)开头", "right");
    });

</script>

<script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
<script type="text/javascript">

    //加载事件创建上传
    $(function () {
        for (var i = 1; i <= 20; i++) {
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

