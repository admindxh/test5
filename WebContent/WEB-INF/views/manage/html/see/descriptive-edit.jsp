<%@ page language="java" import="java.util.*"
         contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>

<head>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge"/>
    <title>看西藏-图说西藏信息管理-修改图说西藏</title>
    <%@include file="/common-html/common.jsp" %>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <style>
        #pickfiles .webuploader-pick {
            width: 120px;
        }

        .dataTable {
            margin: 0 auto;
            width: 50% !important;
        }

        table td, table th {
            text-align: center;
            border: 1px solid #ddd
        }
    </style>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctxManage}//webuploaderbase/webuploader.min.js"></script>
    <script type="text/javascript" src="${ctxManage}//webuploader/checkflash2.js"></script>
    <script type="text/javascript">
    var ispost=false;
		window.onbeforeunload= function(){
			 if(ispost)return;
				event.returnValue="正在编辑中";
		  }
	</script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label> <span><a>看西藏</a> -</span> <span><a
            href="${ctx }web/mutiController/getMutiList" target="_self">图说西藏信息管理</a>
				-</span> <span><a href="#" target="_self">修改图说西藏</a></span>
    </div>
    <!-- 模块管理 { -->
    <form id="dest-add">
        <input id="mutiCode" type="hidden" name="code" value="${muti.code }"/>
        <input type="hidden" name="programaCode" value="${muti.programaCode }"/>
        <input type="hidden" name="detail" value="${muti.createTime}"/>
        <!-- 基本信息 { -->
        <div>
            <h2 class="mt40">图集基本信息:</h2>

            <div class="filament_solid_ddd ov-au mt30">
                <div class="formLine mt20">
                    <label class="w-140">图集名称:</label> 
                    <input id="destName" name="name" type="text" value="${muti.name }" class="w-260"/>
                </div>

                <div class="formLine mt20">
                    <label class="w-140">缩略图:</label>
                     <div class="btn-base" id="pick10">请上传图片</div>
                    <span class="txt-suggest">推荐尺寸：161 * 220</span>
                    <input type="hidden" name="coverUrl" id="img10" value="${muti.coverUrl}"/>
                    <img id="preimg10" class="va_t ml50" width="161" height="220" src="${ctx}${muti.coverUrl}" alt="220X300"/>
                </div>

                <div class="formLine mt10">
                    <label class="w-140 pos_r_t5">图集介绍:</label>
						<textarea name="summary" id="summary" class="destIntro" cols="70"
                                  rows="10">${muti.summary }</textarea>
                </div>
                <div class="formLine mt10 mb20">
                    <label class="w-140">查看:</label> <input id="count1" name="falseViewcount" maxlength="7" type="text"
                                                            class="w-100 number" value="${empty praise.falseViewcount?praise.viewcount:praise.falseViewcount }"/>
                    <button type="button" onclick="resetP(1)" class="btn-base">恢复系统值</button>
                    <label>收藏:</label> <input id="count2" type="text"
                                              class="w-100 number" name="falseFavoriteNum"
                                              value="${empty praise.falseFavoriteNum?praise.favoriteNum:praise.falseFavoriteNum }" maxlength="7"/>
                    <button type="button" onclick="resetP(2)" class="btn-base">恢复系统值</button>
                    <label>赞:</label> <input id="count3" type="text"
                                             class="w-100 number" name="falsePraise" value="${empty praise.falsePraise ? praise.truePraise : praise.falsePraise}"
                                             maxlength="7"/>
                    <button type="button" onclick="resetP(3)" class="btn-base">恢复系统值</button>

                </div>
            </div>
        </div>
        <!-- } 基本信息 -->

        <!-- 图集图片信息 { -->
        <div>
            <h2 class="mt100">图集图片信息:</h2>

            <div class="filament_solid_ddd ov-au mt30">

                <div class="formLine">

                    <div class="tableManage mb0 mt20">
                        <table border="0" cellpadding="0" cellspacing="0" class="dataTable">
                            <thead>
                            <tr>
                                <th>图片缩略图</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody class="tableBody">
                            <c:forEach var="img" items="${imageList}">
                                <tr>
                                    <td><img class="moveImg" alt="" src="${ctx}${img.url}" width="240" height="160">
                                    </td>
                                    <td>
                                        <a class="delPic" href="javascript:void(0);">删除</a>
                                        <a class="xiugai" style="margin-left: 20px;" href="javascript:void(0);">修改</a>
                                        <input type="hidden" name="url" value="${img.url}" class="urlInfo">
                                        <input type="hidden" name="sum" value="${img.summary}" class="sumInfo">
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="formLine mt30">
                    <label class="w-140">图片:</label>

                    <div id="pickfiles"
                         style="display: inline-block;vertical-align: middle;width:120px;" class="btn-base uploadImg">上传
                    </div>
                    <span class="txt-suggest">推荐尺寸：800 * 534</span>
                    <input id="imgurl" type="hidden">
                    <img id="previewimage" width="240" height="160"
                         src="${ctxManage}resources/img/ele/loadImg_default_c.jpg" alt=""
                         class="va_m ml20"/>
                </div>

                <div class="formLine mt10">
                    <label class="w-140 pos_r_t5">图片描述:</label>
						<textarea name="imgSummary" id="imgSummary" class="picIntro"
                                  cols="70" rows="10"></textarea>
                </div>
                <div class="formLine mb20">
                    <label class="w-140"></label>
                    <button id="continueAdd" type="button" class=" btn-base">确认添加图片</button>
                </div>
            </div>
        </div>
        <!-- } 图集图片信息 -->

        <!-- SEO信息 { -->
		<div class="contClassify">
			<h2 class="title">页面SEO信息</h2>
			<div class="filament_solid_ddd ov-au mt30">
				<div class="formLine mt20 mb20">
					<label class="w-180 ft-w-b ml40">&lt;Title&gt;标签:</label>
					<input type="text" maxlength="" class="w-320 seoInfo" name="tdkTitle" value="${muti.tdkTitle }">
				</div>
				<div class="formLine mt20 mb20">
					<label class="w-180 ft-w-b ml40">&lt;Description&gt;标签:</label>
					<input type="text" maxlength="" class="w-320 seoInfo" name="tdkDescription" value="${muti.tdkDescription }">
				</div>
				<div class="formLine mt20 mb20">
					<label class="w-180 ft-w-b ml40">&lt;Keywords&gt;标签(关键字):</label>
					<input type="text" maxlength="" class="w-320 seoInfo" name="tdkKeywords" value="${muti.tdkKeywords }">
					<span class="txt-suggest ml20">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
				</div>
			</div>
		</div>
		<!-- } SEO信息 -->

        <div class="txt-ac mt40">
            <button class="btn-sure" type="button">保存</button>
            <a class="btn-anchor" href="javascript:back()">取消</a>
        </div>

    </form>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.dragsort-0.5.1.min.js"></script>
<script type="text/javascript">
    function initUploader(param1, inputid, pkid, posid)//多个参数可改为 定义一个对象 传入
    {
        function callback(file, resp)//文件 ，服务器返回的数据
        {
            uploader.reset();
            var filepath = resp.dataList[0];
            $(posid).attr("src", "../../" + filepath + "?id=" + Math.random());
            $(inputid).val(filepath);
        }

        var opt = {
            auto: true,//自动上传 ，加载完文件就上传，
            swf: contextPath + '/manage/webuploader/Uploader.swf',
            fileSingleSizeLimit: 1 * 400 * 1024,
            accept: {extensions: 'gif,jpg,jpeg,bmp,png'},
            formData: {
                dir: "upload/muti",
                username: "xml"
            },//上传文件时 提交的数据
            server: '${ctxMRead}uploadimage.html',
            runtimeOrder: 'flash,html5',
            pick: {
                id: pkid,
                multiple: false
            },
            compress: {
                quality: 80,
                allowMagnify: true,
                crop: true,
                preserveHeaders: true,
                noCompressIfLarger: true,
                compressSize: 2 * 1024 * 1024
            }

        };
        uploader = new WebUploader.Uploader(opt);
        uploader.on('uploadSuccess', callback);//成功的回调函数
        uploader.on('error', function (type) {//出错的回调函数
            uploader.reset();
            if (type == "Q_EXCEED_SIZE_LIMIT" || type == "F_EXCEED_SIZE") {
            	msgBox("文件超过大小,请将上传文件大小控制在400kb以内", "erro");
            } else if (type == "Q_TYPE_DENIED") {
                msgBox("上传格式错误", "info", 1200);
            } else {
                msgBox("上传错误，请重试。", "info", 1200);
            }
        });

		return uploader;
    }
    $(function () {
        if (!checkFlash()) {
            initUploader("文件1", "#img10", "#pick10", "#preimg10");
			initUploader("文件1", "#imgurl", "#pickfiles", "#previewimage");
        } else {
            $("#pickfiles").on("click", function () {
                msgBox("请安装最新的flash插件", "erro", 2000);
            });
            $("#pick10").on("click", function () {
                msgBox("请安装最新的flash插件", "erro", 2000);
            });
        }
    });

</script>
<script type="text/javascript">
    /* 图片拖拽 */
    $(".tableBody").dragsort({
        dragSelector: "img.moveImg",
        dragSelectorExclude: 'div#tanchu, div#mengc ,#tanchu textarea, #tanchu button'
    });

    $(document).on('click', '.xiugai', function () {
        var $this = $(this),
                trNode = $this.parents('td'),
                tempUrl = trNode.find('input.urlInfo').val(),
                urlInfo = '${ctx}' + tempUrl,
                tempSum = trNode.find('input.sumInfo').val();
        var temp = '<div id="mengc" class="pos-fix w100p h100p" style="background-color: #000000;top: 0;left: 0;opacity: .5; filter:alpha(opacity=50); z-index: 99997; cursor: default;"></div>' +
                '<div id="tanchu"  class="pos-fix pl30 pr30 pt20 pb20" style="cursor: default; z-index: 99998; background-color: #ffffff;top: 5%;left: 30%;">' +
                '<div class="formLine">' +
                '<label class="w-auto ml20">图片:</label>' +
                '<div id="pickfiles2" style="display: inline-block;vertical-align: middle;width:120px;"  class="btn-base uploadImg">上传</div>' +
                '<span class="txt-suggest ml5">推荐尺寸：800 * 534</span>' +
                '<input id="imgurl1" type="hidden" value="' + tempUrl + '" />' +
                '<img id="newImg" width="240" height="160" src="' + urlInfo + '" alt="" class="va_m" style="margin-left: 58px; display:inline-block" />' +
                '</div>' +
                '<div class="formLine" class="mt10">' +
                '<label class="w-auto pos_r_t5">图片描述:</label>' +
                '<textarea name="" id="" class="picIntroEdit " cols="70" rows="3">' + tempSum + '</textarea>' +
                '</div>' +
                '<div class="formLine txt-ac">' +
                '<button type="button" class="btn-base saveBtn">保存</button>' +
                '<button type="button" class="btn-base closeBtn" style="margin-left: 20px;">取消</button>' +
                '</div>' +
                '</div>';
        trNode.append(temp);

		setTimeout(function(){var img = $("#newImg")[0];img.src = img.src; img.style.visibility = null;},500);
        if (!checkFlash()) {
            initUploader("文件1", "#imgurl1", "#pickfiles2", "#newImg");
        } else {
            $("#pickfiles2").on("click", function () {
                msgBox("请安装最新的flash插件", "erro", 2000);
            });
        }
    });

    /* 删除图片 */
    $(document).on('click', '.delPic', function () {
        var $this = $(this),
                trNode = $this.parents('tr');
        trNode.remove();
        if (!checkFlash()) {
            $("#pickfiles").text("上传");
            setTimeout(function(){
                initUploader("文件1", "#imgurl", "#pickfiles", "#previewimage");
            },100);
        } else {
            $("#pickfiles").on("click", function () {
                msgBox("请安装最新的flash插件", "erro", 2000);
            });
        }
    });

    /* 保存按钮 */
    $(document).on('click', ".saveBtn", function () {
        var $this = $(this),
                trNode = $this.parents('tr'),
                trImg = trNode.find('.moveImg'),
                trUrl = trNode.find('input.urlInfo'),
                trSum = trNode.find('input.sumInfo'),
                inputUrl = $("#imgurl1").val(),
                trImgUrl = '${ctx}' + inputUrl,
                tempVal = $('.picIntroEdit').val();
        $(".picIntroEdit").blur();
        var errInfo = $("#tanchu .errMesg").length == 0;
        if (errInfo) {
            // Do Something
            trImg.attr('src', trImgUrl);
            trUrl.val(inputUrl);
            trSum.val(tempVal);
            $("#mengc").remove();
            $("#tanchu").remove();
            ////.log(liImg.attr('src'));
        } else {
            msgBox("图片描述修改有误，请检查!", "erro", 1000);
        }


    });

    /* 取消按钮 */
    $(document).on('click', ".closeBtn", function () {
        $("#mengc").remove();
        $("#tanchu").remove();
    });

    /* 图集名称验证 */
    $("#destName").blur(function () {
        var $this = $(this),
                thisVal = $this.val(),
                regTest = $.VLDTOR.IsArticle(thisVal);
        // 执行验证并设置验证结果的状态
        valid_txtBox_create(this, regTest && inputRange(this, 2, 30), "只能为中英文及数字，且长度在2-30", "right");
    });

    /* 图集介绍验证 */
    $(".destIntro").blur(function () {
        var $this = $(this),
                thisVal = $this.val(),
                regTest = $.VLDTOR.IsArticle(thisVal);
        // 执行验证并设置验证结果的状态
        valid_txtBox_create(this, regTest && inputRange(this, 2, 800), "只能为中英文及数字，且长度在2-800", "right");
    });

    /* 图片描述 */
    $(".picIntro").blur(function () {
        var $this = $(this),
                thisVal = $this.val(),
                regTest = $.VLDTOR.IsArticle(thisVal);
        valid_txtBox_create(this, regTest && inputRange(this, 2, 150), "只能为中英文及数字，且长度在2-150", "right");
    });

    /* 修改图片描述 */
    $(document).on('blur', '.picIntroEdit', function () {
        var $this = $(this),
                thisVal = $this.val(),
                regTest = $.VLDTOR.IsArticle(thisVal);
        valid_txtBox_create(this, regTest && inputRange(this, 2, 150), "只能为中英文及数字，且长度在2-150", "top");
    });

    /* 数字输入框验证 */
    $(".number").blur(function () {
        var $this = $(this),
                thisVal = $this.val(),
                regTest = $.VLDTOR.IsNum(thisVal);
        valid_txtBox_create(this, regTest || thisVal == "", "只能为空或正整数", "top");
        if (thisVal == "") {
            $this.val('0');
        }
    });

    
    /* SEO 验证 */
    $(".seoInfo").blur(function () {
    	var $this = $(this),
    		thisVal = $this.val();
    	valid_txtBox_create(this, ($.VLDTOR.IsEnCnNum_comma(thisVal) && inputRange(this,2,20)) || thisVal == "",'只能为空或字母、数字及中文，且长度在2-20','top');
    });

    /* 继续添加按钮 */
    $("#continueAdd").on('click', function () {
        var trNode = $(".tableBody").children('tr');
        if (trNode.length >= 50) {
            msgBox("最多只能添加50张图片", "info", 1000);
            return false;
        }
        $(".picIntro").blur();
        var imgNode = $("#previewimage").attr('src'),
                imgUrl = $("#imgurl").val();
        imgInfo = $("#imgSummary").val(),
                regTest = $.VLDTOR.IsArticle(imgInfo);
        if (imgUrl && (regTest && inputRange($("#imgSummary")[0], 2, 150))) {
            $(".tableBody").append(
                    "<tr>" +
                    "<td>" +
                    "<img class='moveImg' width='168' height='168' src='" + imgNode + "' alt='' title='拖拽图片排序' style='cursor:pointer;'/>" +
                    "</td>" +
                    "<td>" +
                    "<a href='javascript:void(0);' class='delPic'>删除</a>" +
                    "<a href='javascript:void(0);' class='xiugai' style='margin-left: 20px;'>修改</a>" +
                    "<input type='hidden' name='url' value='" + imgUrl + "' class='urlInfo'/>" +
                    "<input type='hidden' name='sum' value='" + imgInfo + "' class='sumInfo'/>" +
                    " </td></tr>"
            );
            $("#previewimage").attr('src', '${ctx}manage/resources/img/ele/loadImg_default_c.jpg');
            $("#imgSummary").val("");
            $("#imgurl").val("");
            if (!checkFlash()) {
                $("#pickfiles").text("上传");
                setTimeout(function(){
                    initUploader("文件1", "#imgurl", "#pickfiles", "#previewimage");
                },100);
            } else {
                $("#pickfiles").on("click", function () {
                    msgBox("请安装最新的flash插件", "erro", 2000);
                });
            }
        } else {
            if (imgUrl == "") {
                msgBox("请上传图片", "info", 1000);
            } else if (imgInfo == "") {
                msgBox("请输入图片描述", "info", 1000);
            }
        }
    });

    /* 保存验证 */
    $(".btn-sure").click(function () {
        $("#destName").blur();
        $(".destIntro").blur();
        $(".seoInfo").blur();
        $(".number").blur();
        var tr_len = $(".tableBody").children('tr').length > 0;
        var errMesg_len = $("span.errMesg").length > 0;
        // 验证通过
        if (errMesg_len) {
            msgBox("输入的内容有误，请检查！", "erro", 2600);
        }
        // 验证未通过
        else {
            if (tr_len) {
            	ispost=true;
                save();
                msgBox("保存成功！", "pass", 2600);
                window.location.href = "${ctx}/web/mutiController/getMutiList";
            } else {
                msgBox("请添加图片并填写图片信息", "erro", 2600);
            }
        }
    });
    //保存图集信息
    function save() {
        $.ajax({
            type: "post",
            url: "${ctx }web/mutiController/updateMuti",
            data: $("#dest-add").serialize(),
            dataType: "text",
            async: false,
            success: function (data) {
                ////.log(data);
            }
        });

    }
    function resetP(_action) {
        var _mutiCode = $("#mutiCode").val();
        $.ajax({
            type: "post",
            url: "${ctx }web/mutiController/getrealcount",
            data: {
                mutiCode: _mutiCode,
                action: _action
            },
            dataType: "text",
            async: false,
            success: function (data) {
                ////.log(data);
                $("#count" + _action).val(data);
            }
        });
    }

</script>

<script type="text/javascript">
    function back() {
        popupLayer(
                '',
                '<div style="width: 320px; text-align:center; margin: 0 auto;">返回将不保存数据，是否返回？</div>',
                '<button type="button" class="btn-sure sure mr15">确定</button>' +
                '<button type="button" class="btn-sure cancel ml15">取消</button>'
        );
        $(document).one('click', '.sure', function () {
            javascript:history.back(-1);
            closePopup();
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
