<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1"/>
    <title>门户首页-图说西藏管理</title>
    <%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css"/>
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css"/>
    <script type="text/javascript">
        //表单顺序提交
        function submit(preview) {
            $(".showTitle").blur();
            $(".toLink").blur();
            var selSpan_len = $("span.errMesg").length;
            if (selSpan_len) {
                msgBox('填写信息有误，请检查!', "erro", 1000);
            } else {
                if (preview == 1) {
                	$.ajax({
        			 	async: true,
        			    type:'post',
        			    url:'${ctx}/web/imageController/deleteImageBymutiCode?muticode='+'789g65c5-99d8-4a37-9d13-414b80qe36g6',
        			    success:function(data){
        			    	 for (var i = 1; i <= 6; i++) {
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
        	                    msgBox('保存成功', 'pass', 1500);
        			    }
                	});
                	
                   
                } else {
                    for (var i = 1; i <= 6; i++) {
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
</head>

<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>门户首页</a> -</span>
        <span><a href="#" target="_self">图说西藏管理</a></span>
    </div>

    <!-- 模块管理 { -->
    <div class="muduleManage filament_solid_ddd">
        <!-- 查看按钮 -->
        <div class="searchManage">
            <button type="button" class="lookup btn-sure" onclick="javascript:window.open('${ctx}')">查看</button>
        </div>
        <!-- 景点设置 { -->
        <div class="show">
            <!-- form1 - form 6{ -->
            <c:forEach var="x" begin="1" end="6" step="1">
                <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data"
                      id="form${x}">
                     <input type="hidden"  value="tsxzindex" name="tsxzindex"/>
                    <div class="posidSet-unit">
                        <i>图片${x}</i>

                        <div class="left perc60">
                            <div class="formLine">
                                <label>图片${x}:</label>

                                <div id="pickfiles${x}" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：740 * 407</span>
                            </div>
                            <div class="formLine">
                                <label>显示标题:</label>
                                <input class="showTitle" name="name" value="${imageList[0][x-1].name }" type="text">
                            </div>
                            <div class="formLine">
                                <label>链接至:</label>
                                <input class="toLink" name="hyperlink" value="${imageList[0][x-1].hyperlink }"
                                       type="text">
                            </div>
                        </div>
                        <div class="right mt10 perc40">
                            <label>缩略图:</label>
                            <img id="imgshow${x}"
                            <c:if test="${empty imageList[0][x-1].url}">
                                 src="${ctx}//portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty imageList[0][x-1].url}">
                                 src="${ctx}/${imageList[0][x-1].url }"
                            </c:if>
                                 title="缩略图名称" alt="请上传图片" class="style-d" style="width:370px;height:203px;">
                            <input type="hidden" class="url" name="url"

                                    <c:if test="${empty imageList[0][x-1].url}">
                                        value="/portal/static/default/square.png"
                                    </c:if>
                                    <c:if test="${not empty imageList[0][x-1].url}">
                                        value="${imageList[0][x-1].url }"
                                    </c:if>


                                    />
                        </div>
                        <input type="hidden" name="mutiCode" id="muti" value="${mutiList[0].code }"/>
                        <input type="hidden" name="code" value="${imageList[0][x-1].code }"/>
                        <input type="hidden" name="isPreview" value="1"/>
                    </div>
                </form>
            </c:forEach>
            <!-- }form1-form6 -->

            <!-- 保存 -->
            <div class="saveOper">
                <button type="button" class="btn-sure mr30" onclick="submit(0)">预览</button>
                <button id="save" type="button" class="save btn-sure" onclick="submit(1)">保存</button>
            </div>
        </div>
        <!-- } 景点设置 -->

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
<script src="${ctxManage}/webuploader/upload.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
<script>
    /* 显示标题验证 */
    $(".showTitle").blur(function () {
//        var this_val = $(this).val();
//        var regTest = $.VLDTOR.IsArticle(this_val),
//                regLen = inputRange(this, 2, 30);
//        valid_txtBox(this, regTest && regLen, "只能为中英文和数字，且长度在2-30", "right");
        var this_val = $(this).val(),
            isNull = this_val == "",
            reg_test = $.VLDTOR.IsArticle(this_val);
        if(isNull || reg_test) {
            removeErrMesg(this);
        } else {
            creatErrMesg(this, "只能为常用字符及标点符", "right");
        }
    });
    /* 链接验证 */
    $(".toLink").blur(function () {
//        var this_val = $(this).val();
//        var regTest = $.VLDTOR.IsWebUrl(this_val),
//                regNull = $.trim(this_val) != "";
//        valid_txtBox(this, regTest && regNull, "不能为空、空格或中文", "right");
        var this_val = $(this).val(),
            isNull = this_val == "",
            reg_test = $.VLDTOR.IsHTTP(this_val);
        if(isNull || reg_test) {
            removeErrMesg(this);
        } else {
            creatErrMesg(this, "只能以http(s)开头", "right");
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
