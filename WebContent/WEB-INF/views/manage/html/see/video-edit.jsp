<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge"/>
    <title>看西藏-视频专区信息管理-视频信息修改</title>
	<%@include file="/common-html/common.jsp" %>
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
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
        <label>您当前位置为:</label>
        <span><a>看西藏</a> -</span>
        <span><a href="video-infomessage.html" target="_self">视频专区信息管理</a> -</span>
        <span><a href="#" target="_self">视频信息修改</a></span>
    </div>

    <!-- 模块管理 { -->
    <form id="dest-add" action="videoedit" method="post" >
    	<!-- 隐形数据  还需一些-->
		<input name="dates" type="hidden" value="${video.createTime }">
       	<input name="authorCode" type="hidden" value="${video.authorCode }">
       	<input name="code" type="hidden" value="${video.code }">
		<input name="comment" type="hidden" value="${video.others.comment}">
		<input name="rlcomment" type="hidden" value="${video.others.rlcomment}">
		<input name="rlpraise" type="hidden" value="${video.others.rlpraise}">
        <input name="rlview" type="hidden" value="${video.others.rlview}">
        <input name="rlfavorite" type="hidden" value="${video.others.rlfavorite}">
      	<input name="contentType" type="hidden" value="${video.contentType}">
        
        <!-- 基本信息 { -->
        <div>
            <h2 class="mt40">视频信息:</h2>
            <div class="filament_solid_ddd ov-au mt30">
                <div class="formLine mt20">
                    <label class="w-140">视频名称:</label>
                    <input name="contentTitle" id="destName" value="${video.contentTitle}" type="text" class="w-260 "/>
                </div>
				
				<div class="formLine">
                    <label class="w-140">视频:</label>
                    <div id="btnuploadfile1"  class="btn-base btn-uploadImg">点击上传视频</div>
                    <span class="txt-suggest">只能上传MP4、flv格式的视频</span>
                </div>

                <div class="prog formLine" >
                    <label class="w-140">进度:</label>
                    <div class="progress" style="width: 550px;height: 20px;display: inline-block;vertical-align: middle;">
                        <div class="progress-bar" role="progressbar" style="width: 0%;height: inherit;background-color: #3071a9;border-radius: 5px;"></div>
                    </div>
                    <label class="w-auto jdInfo">0%</label>
                </div>

                <div class="formLine">
                    <label class="w-140">图片:</label>
                    <div id="btnuploadfile"  class="btn-base btn-uploadImg">点击上传图片</div>
                    <span class="txt-suggest">推荐尺寸：250 * 200</span>
                    <input id="imgurl" type="hidden" name="title" value="${video.title}">
                    <c:if test="${empty video.title}">
                    <img id="previewimage" width="200" height="160" src="${ctxManage}/resources/img/ele/loadImg_default_c.jpg" alt="" class="ml20 va_m" />
					</c:if>
					<c:if test="${not empty video.title}">
                    <img id="previewimage" width="200" height="160" src="${video.title}" alt="" class="ml20 va_m" />
					</c:if>                
                </div>
                <div class="formLine mt10">
                    <label class="w-140 pos_r_t5">视频详情介绍:</label>
                    <textarea name="content" id="destIntro" cols="70" rows="10">${video.content}</textarea>
                </div>
                <div class="formLine mt20">
                    <label class="w-140">视频链接:</label>
                    <input id="toLink" value="${video.description}" name="description" type="text" class="w-260"/>
                </div>

                <div class="formLine mt10 mb20">
                    <label class="w-140">查看:</label>
               
                    <input name="view" value="${video.others.view}" type="text" maxlength="7" class="w-100 number"/>
                    <button onclick="setDefault(this,${video.others.rlview})" type="button" class="btn-base">恢复系统值</button>
                    <label>收藏:</label>
                    <input  name="favorite" value="${video.others.favorite}" type="text" maxlength="7" class="w-100 number"/>
                    <button  onclick="setDefault(this,${video.others.rlfavorite})"  type="button" class="btn-base">恢复系统值</button>
                    <label>赞:</label>
                    <input name="praise" value="${video.others.praise}"  type="text" maxlength="7" class="w-100 number"/>
                    <button type="button" onclick="setDefault(this,${video.others.rlpraise})" class="btn-base">恢复系统值</button>
                </div>
            </div>
        </div>
        <!-- } 基本信息 -->
		<!-- SEO信息 { -->
		<div class="contClassify">
			<h2 class="title">页面SEO信息</h2>
			<div class="filament_solid_ddd ov-au mt30">
				<div class="formLine mt20 mb20">
					<label class="w-180 ft-w-b ml40">&lt;Title&gt;标签:</label>
					<input type="text" maxlength="" class="w-320 seoInfo" name="tdkTitle" value="${video.tdkTitle }">
				</div>
				<div class="formLine mt20 mb20">
					<label class="w-180 ft-w-b ml40">&lt;Description&gt;标签:</label>
					<input type="text" maxlength="" class="w-320 seoInfo" name="tdkDescription" value="${video.tdkDescription }">
				</div>
				<div class="formLine mt20 mb20">
					<label class="w-180 ft-w-b ml40">&lt;Keywords&gt;标签(关键字):</label>
					<input type="text" maxlength="" class="w-320 seoInfo" name="tdkKeywords" value="${video.tdkKeywords }">
					<span class="txt-suggest ml20">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
				</div>
			</div>
		</div>
		<!-- } SEO信息 -->

        <div class="txt-ac mt40">
            <button id="save" class="btn-sure" type="button">保存</button>
             <button class="btn-sure ml20" type="reset" onclick="back()">取消</button>
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
<script type="text/javascript" src="${ctxManage}//webuploaderbase/webuploader.min.js"></script>
<script type="text/javascript" src="${ctxManage}//webuploader/checkflash2.js"></script>
<!-- 图片拖拽排序 -->
<script type="text/javascript">
/* 视频名称验证 */
$("#destName").blur(function () {
    var $this = $(this),
            thisVal = $this.val(),
            regTEst = $.VLDTOR.IsArticle(thisVal);
    // 执行验证并设置验证结果的状态
   valid_txtBox_create(this,regTEst && inputRange(this,2,30),"不能为空格，且长度在2-30","right");
});
/* 视频详情介绍 */
$("#destIntro").blur(function () {
    var $this = $(this),
            thisVal = $this.val(),
            regTest = $.VLDTOR.IsArticle(thisVal);
    // 执行验证并设置验证结果的状态
    valid_txtBox_create(this,regTest && inputRange(this,2,5000),"不能为空格，且长度在2-5000","right");

});

/* 视频链接验证及其触发功能 */
$("#toLink").blur(function () {
    var $this = $(this),
        thisVal = $this.val(),
        isQiNiu = thisVal.indexOf("qiniudn"),
        lastDot = thisVal.lastIndexOf("."),
        fielType = thisVal.substr(lastDot),
        toJPG = thisVal.replace(fielType, "_01.jpg"),
        regTest = $.VLDTOR.IsWebUrl(thisVal),
        notNull = $.trim(thisVal) != "";
    // 如果为七牛网的视频
    if(isQiNiu != -1) {
        // 设置图片路径和隐藏域的值
        $("#previewimage").attr("src", toJPG);
        $("#imgurl").val(toJPG);
        // 清除可能存在的错误提示
        removeErrMesg("#previewimage");
    }
    valid_txtBox_create(this,regTest && notNull,"不能为空，且不能以空格开头","right");
});

/* SEO验证 */
$(".seoInfo").blur(function () {
		var $this = $(this),
                thisVal = $this.val(),
                regTest = $.VLDTOR.IsEnCnNum_comma(thisVal);
        thisVal = thisVal.replace(/，/g,",");
		while(thisVal.indexOf(",,") != -1){
			thisVal = thisVal.replace(/,,/g,",");
		}
		thisVal = thisVal.replace(/^,/,"").replace(/,$/,"");
		$this.val(thisVal);
        var m = thisVal.match(/,/g);
        valid_txtBox_create(this, (regTest && inputRange(this, 2, 20) && (!m || m.length < 5)) || thisVal == "", "只能为空或字母、数字及中文，且长度在2-20", "top");
});
/* 数字输入框验证 */
$(".number").blur(function () {
    var $this = $(this),
            thisVal = $this.val(),
            regTest = $.VLDTOR.IsNum(thisVal);
    valid_txtBox_create(this, regTest || thisVal == "", "只能为空或正整数", "top");
    if(thisVal == ""){
		$this.val('0');    	
    }
});

/* 保存验证 */
$("#save").click(function () {
	
    $("#destName").blur();
    $("#destIntro").blur();
    $("#toLink").blur();
    $(".seoInfo").blur();
    $(".number").blur();
    // 判断图片是否上传
    var imgurl = $("#imgurl").val();
    if(imgurl == "") {
        creatErrMesg("#previewimage", "请上传图片", "right")
    } else {
        removeErrMesg("#previewimage");
    }
    // 验证通过
    var errInfo = $(".errMesg").length == 0;
    if (errInfo) {
    	ispost=true;
    	$("#dest-add").submit();
        msgBox("保存成功！", "pass", 2600);
    }
    // 验证未通过
    else {
        msgBox("输入的内容有误，请检查！", "erro", 2600);
    }
});
     function initUploader(param1, param2, pkid, posid)//多个参数可改为 定义一个对象 传入  	
		{
			function callback(file, resp)//文件 ，服务器返回的数据 	
			{
			this.reset();
			//////.log(resp.dataList);
			var filepath=resp.dataList[0];
				$("#toLink").val('${ctx}'+filepath);
			msgBox(file.name+"上传成功！", "pass", 2600);
			}
			var opt = {
				auto : true,//自动上传 ，加载完文件就上传，     
				swf : '${ctxManage}/webuploader/Uploader.swf',
				fileSingleSizeLimit:500*1024*1024,
				accept:{extensions:'mp4,flv'},
				formData : {
					dir : "upload/video",
				},//上传文件时 提交的数据     
				server : '${ctx}/manage/html/see/uploadvideo.html',
				runtimeOrder:'flash,html5',
				pick : {
					id : pkid,
					multiple : false
				},
				resize : false
			};
			uploader = new WebUploader.Uploader(opt);
			uploader.on('uploadSuccess', callback);//成功的回调函数   
			uploader.on('error', function(type) {//出错的回调函数  
				uploader.reset(); 
				if(type=="Q_EXCEED_SIZE_LIMIT" || type=="F_EXCEED_SIZE"){
					msgBox("文件超过大小", "info", 1200);
				}else if(type=="Q_TYPE_DENIED"){
					msgBox("上传格式错误", "info", 1200);
	  			}else{
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
	    $(function(){
	    	if(!checkFlash())
	    	{
	          initUploader("文件1", "用户1", "#btnuploadfile1", "#pos1");
	    	}else
	    	{
	    		$("#btnuploadfile1").on("click",function(){
	    			msgBox("请安装最新的flash插件", "erro", 2000);
	    		});	
	    	}
	    });
		function setDefault(a,number)
		{
			if($(a).prev('.errMesg')){
				$(a).prev().prev().val(number);
				setTimeout(function(){
					$(a).prev('.errMesg').remove();	
				},200);
			}
			if($(a).prev('.number')){
				$(a).prev().val(number);	
			}
		};
		  function initUploader2(param1, param2, pkid, posid)//多个参数可改为 定义一个对象 传入  	
			{
				function callback(file, resp)//文件 ，服务器返回的数据 	
				{
				this.reset();
				var filepath=resp.dataList[0];
				$("#previewimage").attr("src","../../../"+filepath+"?id="+Math.random());
				$("#imgurl").val('${ctx}'+filepath);
				 removeErrMesg("#previewimage");
				}
				var opt = {
					auto : true,//自动上传 ，加载完文件就上传，     
					swf : '${ctxManage}/webuploader/Uploader.swf',
					fileSingleSizeLimit:1*400*1024,
					accept:{extensions:'gif,jpg,jpeg,bmp,png'},
					formData : {
						type : 'image',
						dir:'upload/image'
					},
					//上传文件时 提交的数据     
					server : '${ctxMRead}uploadimage.html',
					runtimeOrder:'flash,html5',
					pick : {
						id : pkid,
						multiple : false
					},
					compress:null//{width: 800,height: 600, quality: 80,allowMagnify: true, crop: true,preserveHeaders: true,noCompressIfLarger: true, compressSize: 2*1024*1024}
				};
				uploader = new WebUploader.Uploader(opt);
				uploader.on('uploadSuccess', callback);//成功的回调函数   
				uploader.on('error', function(type) {//出错的回调函数  
					uploader.reset(); 
					if(type=="Q_EXCEED_SIZE_LIMIT" || type=="F_EXCEED_SIZE"){
						msgBox("文件超过大小,请将上传文件大小控制在400kb以内", "erro");
					}else if(type=="Q_TYPE_DENIED"){
						msgBox("上传格式错误", "info", 1200);
		  			}else{
		  				msgBox("上传错误，请重试。", "info", 1200);
		  			}
				});
			}
	$(function() {
		if (!checkFlash()) {
			initUploader2("文件1", "用户1", "#btnuploadfile", "#pos1");
		} else {
			$("#btnuploadfile").on("click", function() {
				msgBox("请安装最新的flash插件", "erro", 2000);
			});
		}
	});
</script>
<script type="text/javascript">
    function back(){
        popupLayer(
                '',
                '<div style="width: 320px; text-align:center; margin: 0 auto;">返回将不保存数据，是否返回？</div>',
                '<button type="button" class="btn-sure sure mr15">确定</button>' +
                '<button type="button" class="btn-sure cancel ml15">取消</button>'
        );
        $(document).one('click', '.sure', function(){
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