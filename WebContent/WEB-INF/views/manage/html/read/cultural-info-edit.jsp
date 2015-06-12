<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE HTML >
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>西藏文化传播信息管理-编辑西藏文化传播信息</title>
    <%@include file="/common-html/common.jsp" %>
    <script src="../../resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="../../resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="../../resources/css/base.css"/>
    <link rel="stylesheet" href="../../resources/css/travel/formWeb.css"/>
    <link rel="stylesheet" href="../../resources/plugin/datepicker.css"/>
    <style>
        .mt-40 {
            margin-top: -40px;
        }

        .floatfix:after {
            content: "";
            display: table;
            clear: both;
        }
    </style>
    <!-- 网页文本编辑器插件 -->
    <script src="../../resources/plugin/ckeditor/ckeditor.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctxManage}//webuploaderbase/webuploader.min.js"></script>
    <script type="text/javascript" src="${ctxManage}//webuploader/checkflash2.js"></script>
</head>

<body>
<!-- main { -->
<div class="main">
    <form id="creatRead" name="" method="post">
        <!-- 页面位置-->
        <div class="location">
            <label>您当前位置为:</label>
            <span><a href="cultural-trans.html" target="_self">西藏文化传播信息管理</a> -</span>
            <span><a href="#" target="_self">编辑西藏文化传播信息</a></span>
        </div>
        <!-- 隐形数据 -->
        <input name="dates" type="hidden" value="${culture.createTime }">
        <input name="comment" type="hidden" value="${culture.others.comment}">
        <input name="rlcomment" type="hidden" value="${culture.others.rlcomment}">
        <input name="rlpraise" type="hidden" value="${culture.others.rlpraise}">
        <input name="rlview" type="hidden" value="${culture.others.rlview}">
        <input name="rlfavorite" type="hidden" value="${culture.others.rlfavorite}">

        <input name="type" value="cul" type="hidden">
        <!-- 数据操作 -->
        <div class="searchManage">
            <button id="preview" type="button" class="btn-sure">预览</button>
            <button id="saveForm" type="button" class="btn-sure">保存</button>
        </div>
        <!-- 模块管理 { -->
        <div class="muduleManage details filament_solid_ddd">
            <div>
                <div class="formLine floatfix mb15">
                    <div>
                        <label>编号:</label><span class="ml10">${culture.code }</span>
                        <input type="hidden" name="code" value="${culture.code }" class="w-100">
                        <label>评分:</label>
                        <input name="score" value="${culture.others.score}" type="text" class="w-100 pingf">
                        <button class="btn-base" onclick="setDefaultScore(this,
                        <c:if test="${!empty culture.others.rlscore}">${culture.others.rlscore}</c:if>
                        <c:if test="${empty culture.others.rlscore}">0</c:if>)" type="button">恢复系统值
                        </button>
                    </div>
                    <div class="mt20">
                        <label>查看:</label>
                        <input name="view" type="text" maxlength="7" value="${culture.others.view}"
                               class="w-100 number">
                        <button class="btn-base" onclick="setDefault(this,${culture.others.rlview})" type="button">
                            恢复系统值
                        </button>

                        <label class="ml30">收藏:</label>
                        <input name="favorite" type="text" value="${culture.others.favorite}" maxlength="7"
                               class="w-100 number">
                        <button class="btn-base" onclick="setDefault(this,${culture.others.rlfavorite})" type="button">
                            恢复系统值
                        </button>

                        <label>赞:</label>
                        <input name="praise" type="text" value="${culture.others.praise}" maxlength="7"
                               class="w-100 number">
                        <button class="btn-base" onclick="setDefault(this,${culture.others.rlpraise})" type="button">
                            恢复系统值
                        </button>
                    </div>

                    <!-- 文章标题 { -->
                    <div class="mt20">
                        <label>标题:</label>
                        <input name="contentTitle" type="text" maxlength="30" value="${culture.contentTitle }"
                               class="w-260 article-title">
                        <span class="reqItem">*</span>

                        <label>作者:</label>
                        <input name="authorCode" value="${culture.authorCode }" type="text" maxlength="30"
                               class="w-200 writer">
                        <span class="reqItem">*</span>

                        <!-- 是否推荐 { -->
                        <label class="ml30">是否推荐:</label>
                        <c:if test="${culture.isOfficial=='1'}" var="rs" scope="request">
                            <input id="yes" value="0" type="radio" name="isOfficial" class="ml50"><label for="yes"
                                                                                                         class="lbl_check">是</label>
                            <input id="no" value="1" type="radio" name="isOfficial" class="ml50" checked="true"><label
                                for="no" class="lbl_check">否</label>
                        </c:if>
                        <c:if test="${culture.isOfficial!='1'}" var="rs" scope="request">
                            <input id="yes" value="0" type="radio" name="isOfficial" class="ml50" checked="true"><label
                                for="yes" class="lbl_check">是</label>
                            <input id="no" value="1" type="radio" name="isOfficial" class="ml50"><label for="no"
                                                                                                        class="lbl_check">否</label>
                        </c:if>
                    </div>

                    <!-- 所属类型 { -->
                    <div class="mt20">
                        <label>所属类型：</label>

                        <div id="includType" class="select-base">
                            <input name="contentType" type="hidden" value="${culture.contentType }">
                            <i class="w-140">音乐</i>
                            <dl>
                                <dt name="2010">音乐</dt>
                                <dt name="2020">小说</dt>
                                <dt name="2030">游戏</dt>
                                <dt name="2040">其他</dt>
                            </dl>
                        </div>

                        <div id="dropInfo" class="disp-i">
                            <label class="ml50">上传音乐文件:</label>
                            <div id="btnuploadmusic" class="btn-base uploadImg" type="button">点击上传音乐</div>
                            <span class="reqItem">*</span>
                            <span class="txt-suggest ml20">支持MP3，不超过10M</span>
                        </div>
                        <div id="dropInfo2" class="disp-n">
                            <label class="ml50">上传游戏文件:</label>
                            <div id="btnuploadgame" class="btn-base uploadImg">点击上传游戏</div>
<!--                             <span class="txt-suggest">（支持的游戏包类型）</span> -->
                        </div>
                        <input id="musicurl" name="description" type="text" value="${culture.description}" style="display:none">
                    </div>
                    <div class="prog mt20" id="jd">
<!-- style="display:none" -->
                        <label>进度:</label>
                        <div class="progress" style="width: 550px;height: 20px;display: inline-block;vertical-align: middle;">
                            <div class="progress-bar" role="progressbar" style="width: 0%;height: inherit;background-color: #3071a9;border-radius: 5px;"></div>
                        </div>
                        <label class="w-auto jdInfo">0%</label>
                    </div>

                    <!-- 图片上传 { -->
                    <div class="float_l mt20 w45p">
                        <label>缩略图</label>
                        <input id="imgurl" name="title" type="hidden" value="${culture.title}">

                        <div id="btnuploadfile" class="btn-base uploadImg" type="button">点击上传图片</div>
                        <span class="reqItem">*</span>
                        <span class="txt-suggest ml20">推荐尺寸：150 * 150，且不超过10M</span>
                    </div>

                    <!-- 图片缩略图 { -->
                    <div class="w45p mr40 mt-40 float_r">
                        <img id="previewimage" style="width:150px;height:150px;" src="../../../${culture.title}"
                             title="缩略图名称" alt="请上传图片" class="style-b">
                    </div>
                </div>
                <!-- 正文内容 { -->
                <div class="formLine ">
                    <label class="pos_r_t5">内容:</label>
                    <script id="content" class="ueEditor_cont" name="content" style="height:500px;"
                            type="text/plain">${culture.content}</script>
                    <span class="reqItem">*</span>
                </div>
            </div>
        </div>
        <!-- } 模块管理 -->

        <!-- SEO信息 { -->
		<div class="contClassify">
			<h2 class="title">页面SEO信息</h2>
			<div class="filament_solid_ddd ov-au mt30">
				<div class="formLine mt20 mb20">
					<label class="w-180 ft-w-b ml40">&lt;Title&gt;标签:</label>
					<input type="text" maxlength="" class="w-320 seoInfo" name="tdkTitle" value="${culture.tdkTitle }">
				</div>
				<div class="formLine mt20 mb20">
					<label class="w-180 ft-w-b ml40">&lt;Description&gt;标签:</label>
					<input type="text" maxlength="" class="w-320 seoInfo" name="tdkDescription" value="${culture.tdkDescription }">
				</div>
				<div class="formLine mt20 mb20">
					<label class="w-180 ft-w-b ml40">&lt;Keywords&gt;标签(关键字):</label>
					<input type="text" maxlength="" class="w-320 seoInfo" name="tdkKeywords" value="${culture.tdkKeywords }">
					<span class="txt-suggest ml20">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
				</div>
			</div>
		</div>
		<!-- } SEO信息 -->
    </form>
</div>
<!-- } main -->
<!-- JS引用部分 -->
<script src="../../resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="../../resources/js/base.js" type="text/javascript"></script>
<script src="../../resources/plugin/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="../../resources/js/base-form.js"></script>
<script src="../../resources/js/activity/activity-creat.js" type="text/javascript"></script>
<script>
    $(function () {
        var $select = $('.select-base'), $val = $select.find('input[name="contentType"]').val(),
                $text = $select.find('dt[name="' + $val + '"]').text()
        $select.find('i').text($text)
        
        
        if(!$("input[name='view']").val())$("input[name='view']").val(0);
        if(!$("input[name='favorite']").val())$("input[name='favorite']").val(0);
        if(!$("input[name='praise']").val())$("input[name='praise']").val(0);
          
    })

</script>
<script>

    function initUploader(type, filetype, filesize, url, path, pkid, posid)//多个参数可改为 定义一个对象 传入
    {
        function callback(file, resp)//文件 ，服务器返回的数据
        {
            this.reset();
            ////.log(resp.dataList);
            var filepath = resp.dataList[0];
            if (type == "image") {
                $("#previewimage").attr("src", "../../../" + filepath + "?id=" + Math.random());
                removeErrMesg("#btnuploadfile");
            } else if(type=="music") {
                msgBox(file.name + "上传成功", "pass", 1200);
                removeErrMesg("#btnuploadmusic");
            }else {
            	filepath='${ctx}'+filepath;
            }
            $(posid).val(filepath);
        }

        var opt = {
            auto: true,//自动上传 ，加载完文件就上传，
            swf: '../../webuploader/Uploader.swf',
            fileSingleSizeLimit: filesize,
            accept: {extensions: filetype},
            formData: {
                type: type,
                dir: path
            },//上传文件时 提交的数据
            server: '${ctxMRead}' + url,
            runtimeOrder: 'flash,html5',
            pick: {
                id: pkid,
                multiple: false
            },
            compress: {
                width: 800,
                height: 800,
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
        uploader.on('uploadProgress',function(file,percentage) {
            console.log(parseInt(percentage * 100));
            var $li = $(".prog"),
                    $jdInfo = $li.find('.jdInfo'),
                    $percent = $li.find('.progress .progress-bar');

            $li.css('display', 'block');
            $percent.css( 'width', parseInt(percentage * 100) + '%' );
            $jdInfo.text(parseInt(percentage * 100) + '%');
        });
    }
    $(function () {
        if (!checkFlash()) {
            initUploader("image", "gif,jpg,jpeg,bmp,png", 1 * 400 * 1024, "uploadimage.html", "upload/image", "#btnuploadfile", "#imgurl");
            initUploader("music", "mp3", 10 * 1024 * 1024, "uploadmusic.html", "upload/music", "#btnuploadmusic", "#musicurl");
            initUploader("game", "apk", 10*1024 * 1024 * 1024, "uploadmusic.html", "upload/game", "#btnuploadgame", "#musicurl");
        } else {
            $("#btnuploadmusic").on("click", function () {
                msgBox("请安装最新的flash插件", "erro", 2000);
            });
            $("#btnuploadfile").on("click", function () {
                msgBox("请安装最新的flash插件", "erro", 2000);
            });
            $("#btnuploadgame").on("click", function () {
                msgBox("请安装最新的flash插件", "erro", 2000);
            });
        }
    });

    function setDefault(a, number) {
        if ($(a).prev('.errMesg')) {
            $(a).prev().prev().val(number);
            setTimeout(function () {
                $(a).prev('.errMesg').remove();
            }, 200);
        }
        if ($(a).prev('.number')) {
            $(a).prev().val(number);
        }
    }

    function setDefaultScore(a, score, number) {
        number = number || 1;
        var x = score / number;
        if(x>10){
        	x=10;
     	}
        
        var rs=(""+x).substr(0,3);
        if ($(a).prev('.errMesg')) {
            $(a).prev().prev().val(rs);
            setTimeout(function () {
                $(a).prev('.errMesg').remove();
            }, 200);
        }
        if ($(a).prev('.pingf')) {
            $(a).prev().val(rs);
        }
    }
    ;
</script>
<script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.all.js"></script>
<!-- 实例化编辑器 -->
<script type="text/javascript">
    var ue = UE.getEditor('content',{
        autoHeightEnabled:false,
        initialFrameHeight: 350,
        maximumWords:20000
    });
</script>
<script src="../../resources/plugin/jquery.rimi.validation.js"></script>
<script src="../../resources/js/dataValidation.js"></script>
<script src="../../resources/plugin/jquery.rimi.validation.js"></script>
<script src="../../resources/js/dataValidation.js"></script>
<script>
    /**** 查看、收藏和暂的“清零”操作 ****/
    $("[name='view'], [name='favorite'], [name='praise']").focus(function() {
        var thisVal = $(this).val();
        if(thisVal == "0") {
            $(this).val("");
        }
    });
    /**** 富文本内容验证 ****/
    ue.addListener('contentChange', function () {
        valid_richTxt(this, "#content", 2, 20000, "内容必须在2-2w个字符之间");
    });
    ue.addListener('focus', function () {
        valid_richTxt(this, "#content", 2, 20000, "内容必须在2-2w个字符之间");
    });

    /**** 常规数据验证 ****/
    $(".writer").blur(function () {
        var $this = $(this);
        var thisVal = $this.val();
        var regTest = $.VLDTOR.IsArticle(thisVal);
        valid_txtBox_create(this, regTest && inputRange(this, 2, 30), "字符长度在2-30，且开头不能为空格", 'top');
    });

    $(".article-title").blur(function () {
        var $this = $(this);
        var thisVal = $this.val();
        var regTest = $.VLDTOR.IsArticle(thisVal);
        valid_txtBox_create(this, regTest && inputRange(this, 2, 30), "字符长度在2-30，且开头不能为空格", 'top');
    });

    $(".seoInfo").blur(function () {
        var $this = $(this),
                thisVal = $this.val(),
                regTest = $.VLDTOR.IsEnCnNum_comma(thisVal);
        valid_txtBox_create(this, (regTest && inputRange(this, 2, 20)) || thisVal == "", "只能为空或字母、数字及中文，且长度在2-20", 'top')
    });

    $(".number").blur(function () {
        var $this = $(this);
        var thisVal = $this.val();
        var regTest = $.VLDTOR.IsNum(thisVal);
        valid_txtBox_create(this, regTest || thisVal == "", "只能为空或正整数", 'top');
        if (thisVal == "") {
            $this.val('0');
        }
    });

    $(".pingf").blur(function () {
        var $this = $(this),
                thisVal = $this.val(),
                regTest = $.VLDTOR.IsGrade(thisVal);
        valid_txtBox_create(this, regTest || thisVal == "", "只能为空或0-10之间的值，且只能保留一位小数", 'top');
    });
    //
    function checkMusic() {
        // 当选择音乐的时候进行验证
        var includVal = $("#includType").children("i").text();
        if (includVal == "音乐") {
            // 是否上传音乐
            var uploadMusic = $("#musicurl").val();
            if (uploadMusic == "") {
                creatErrMesg("#btnuploadmusic", "请上传音乐", "right");
            } else {
                removeErrMesg("#btnuploadmusic");
            }
        }

    }

    $("#saveForm").click(function () {
        $(".article-title").blur();
        $(".writer").blur();
        $(".number").blur();
        $(".pingf").blur();
        $(".seoInfo").blur();
        checkMusic();
        var ueCont = ue.getContentTxt(),
                upLoadImg = $("#imgurl");
        if (ueCont == "") {
            valid_txtBox_create("#content", false, "内容必须在2-2w个字符之间", "top-right");
        }
        // 图片上传验证
        if (upLoadImg.val() == "") {
            creatErrMesg("#btnuploadfile", '请上传图片', 'top');
        } else {
            removeErrMesg("#btnuploadfile");
        }
        var regTestNum = $(".errMesg").length > 0;
        if (regTestNum) {
            msgBox("您输入的信息有误，请检查！", "erro", 1000);
        } else {
            $("#creatRead").attr('target', '_self');
            $("#creatRead").attr('action', 'cultureupdate.html');
            ////.log($("#creatRead").attr('action'));
            $('#creatRead').submit();
        }
    })
    $("#preview").on('click', function () {                //预览按钮
        $(".article-title").blur();
        $(".writer").blur();
        $(".number").blur();
        $(".pingf").blur();
        $(".seoInfo").blur();
        checkMusic();
        var ueCont = ue.getContentTxt(),
                upLoadImg = $("#imgurl");
        if (ueCont == "") {
            valid_txtBox_create("#content", false, "内容必须在2-2w个字符之间", "top-right");
        }
        // 图片上传验证
        if (upLoadImg.val() == "") {
            creatErrMesg("#btnuploadfile", '请上传图片', 'top');
        } else {
            removeErrMesg("#btnuploadfile");
        }
        var regTestNum = $(".errMesg").length > 0;
        if (regTestNum) {
            msgBox("您输入的信息有误，请检查！", "erro", 1000);
        } else {
            $("#creatRead").attr('target', '_blank');
            $("#creatRead").attr('action', '${ctx}portal/app/culture/preview.html');
            $('#creatRead').submit();
        }
    });
    $(document).on('click', '.select-base', function () {
        check(this);
    });
    function check(select) {
        var $this = $(select),
                thisTxt = $(select).find('input:hidden').val();
  
        if (thisTxt =="2010") {
            $("#dropInfo").css("display", "inline-block");
            if (!checkFlash()) {
                initUploader("music", "mp3", 10 * 1024 * 1024, "uploadmusic.html", "upload/music", "#btnuploadmusic", "#musicurl");
            } else {
                $("#btnuploadmusic").on("click", function () {
                    msgBox("请安装最新的flash插件", "erro", 2000);
                });
            }
        } else {
        	   removeErrMesg($("#btnuploadmusic").next(".txt-suggest"));
            $("#dropInfo").css("display", "none");
        }
        if (thisTxt == "2030") {
            $("#dropInfo2, #musicurl").css("display", "inline-block");
            if (!checkFlash()) {
                initUploader("game", "apk", 10*1024 * 1024 * 1024, "uploadmusic.html", "upload/game", "#btnuploadgame", "#musicurl");
            } else {
                $("#btnuploadgame").on("click", function () {
                    msgBox("请安装最新的flash插件", "erro", 2000);
                });
            }
        } else {
            $("#dropInfo2, #musicurl").css("display", "none");
        }

        $("#jd").show();
     

        
    }
    // 选择其它项时，消除音乐选择的错误提示
    $(document).on("click", "#includType dt", function () {
        var this_val = $("#includType").children("i").text();
        if (this_val != "音乐") {
            $("#btnuploadmusic").next(".errMesg").remove();
        }
    });
    $(function () {
        check($(".select-base"));
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

