<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
	<title>骑行专区-骑行路线管理-编辑路线</title>
	<%@ include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctx}manage/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctx}manage/resources/css/base.css" />
	<link rel="stylesheet" href="${ctx}manage/resources/css/travel/formWeb.css" />
	<script src="${ctx}manage/resources/plugin/respond.min.js" type="text/javascript"></script>
	
</head>

<body>
	<!-- main { -->
	<div class="main">
		<form id="saveForm" method="post" action="${ctx}web/rideLine/editRideLine">
			<input type="hidden" name="code" value="${rideLine.code}">
			<!-- 页面位置-->
			<div class="location">
				<label>您当前位置为:</label>
				<span><a>骑行专区</a> -</span>
				<span><a href="${ctx}web/rideLine/forRideLineList">骑行路线管理</a></span>
				<span><a>编辑路线</a></span>
			</div>

			<!-- 模块管理 { -->
			<div class="muduleManage details filament_solid_ddd">

				<!-- 基本信息 { -->
				<div class="contClassify">
					<h2 class="title">基本信息</h2>

					<div class="formLine">
						<label>名称:</label>
						<input type="text" maxlength="30" class="w-260" name="name" value="${rideLine.name}" id="rideLineName">
						<span class="reqItem">*</span>
					</div>
					<div class="formLine">
						<label>副标题:</label>
						<input type="text" maxlength="30" class="w-260" name="subTitle" id="subTitle" value="${rideLine.subTitle }">
						<span class="reqItem">*</span>
					</div>
					<div class="formLine">
						<label>报名地址:</label>
						<input type="text" maxlength="200" class="w-260" name="achref" value="${rideLine.achref}" id="achref">
						<span class="reqItem"></span>
					</div>
					<div class="formLine mt20">
						<label>Banner图:</label>
						<div id="bannerPickId" class="btn-base uploadImg">点击上传图片</div>
						<span class="txt-suggest ml20">推荐尺寸：833 * 255</span>
						<span class="reqItem">*</span>
					</div>
					<div class="formLine">
						<label class="pos_r_t5">Banner图预览：</label>
						<img id="bannerPreId" src="${ctx}${rideLine.bannerImg}" title="缩略图名称" alt="请上传图片" class="va_t" style="width: 1140px;height: 300px;">
						<input id="bannerHiddenId" type="hidden" name="bannerImg" value="${rideLine.bannerImg }">
					</div>
					
					<div class="formLine mt20">
						<label class="pos_r_t5">线路简介:</label>
						<%--<script id="introduce" class="ueEditor_cont" name="introduce" style="height:500px;" type="text/plain">${rideLine.introduce}</script> --%>
						<textarea id="introduce" name="introduce" cols="70" rows="10">${rideLine.introduce}</textarea>
						<span class="reqItem">*</span>
					</div>
					
					<div class="formLine mt20">
						<label class="pos_r_t5">官方说明:</label>
						<%--<script id="summary" class="ueEditor_cont" name="summary"  style="height:500px;" type="text/plain">${rideLine.summary}</script> --%>
						<textarea id="summary" name="summary" cols="70" rows="10">${rideLine.summary}</textarea>
						<span class="reqItem">*</span>
					</div>
					
					<div class="formLine mt20">
						<label class="pos_r_t5">注意事项:</label>
						<%--<script id="notice" class="ueEditor_cont" name="notice"  style="height:500px;" type="text/plain">${rideLine.notice}</script> --%>
						<textarea id="notice" name="notice" cols="70" rows="10">${rideLine.notice}</textarea>
						<span class="reqItem">*</span>
					</div>

				</div><!-- } 基本信息 -->
				
			
				<!-- 确认按钮 -->
				<div class="formLine">
					<div class="saveOper">
						<button type="button" class="btn-base mr30 saveBtn">发布</button>
					</div>
				</div>

			</div>
			<!-- } 模块管理 -->
		</form>
	</div>
	<!-- } main -->
	
	<!-- js -->
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
	<!-- jsp -->
	<script src="${ctx}manage/webuploaderbase/webuploader.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/webuploader/checkflash.js" type="text/javascript"></script>
	<%--
	<!-- 实例化编辑器 -->
    <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.all.js"></script>
	<script type="text/javascript">
		function checkLength(ue, id){
			var ueCont = ue.getContentTxt();
			if(ueCont == "") {
				valid_txtBox_create("#"+id, false, "内容必须在2-2000个字符之间", "top-right");
				//return false;
			}
			var count = ue.getContentLength(true);
			if (count > 2000 || count < 2) {
				valid_txtBox_create("#"+id, false, "内容必须在2-2000个字符之间", "top-right");
			}else{
				$("#"+id).next(".errMesg").remove();
			}
		}
		
		function checkContent(ue, id){
			var ueCont = ue.getContentTxt();
			var count = ue.getContentLength(true);
			if(ueCont == "") {
				valid_txtBox_create("#"+id, false, "内容必须在2-2000个字符之间", "top-right");
				msgBox("填写的信息有误，请检查！", "erro", 1200);
			}
			else if (count > 2000 || count < 2) {
				msgBox("内容必须在2-2000个字符之间", "erro");
				msgBox("填写的信息有误，请检查！", "erro", 1200);
			}
		}
		var introduceUE = UE.getEditor('introduce',{maximumWords:2000,autoHeightEnabled:false});
		introduceUE.addListener("contentChange", function(e) {
	    	checkLength(introduceUE, 'introduce');
		});
		var summaryUE = UE.getEditor('summary',{maximumWords:2000,autoHeightEnabled:false});
		summaryUE.addListener("contentChange", function(e) {
	    	checkLength(summaryUE, 'summary');
		});
		var noticeUE = UE.getEditor('notice',{maximumWords:2000,autoHeightEnabled:false});
		noticeUE.addListener("contentChange", function(e) {
	    	checkLength(noticeUE, 'notice');
		});
	</script>
	--%>
	
	<script type="text/javascript">
		function addEvent(up, preId, urlHiddenId){
	  		up.on( 'uploadSuccess', function(file, response) {
				$("#"+urlHiddenId).val(response.filePath);
				$("#"+preId).attr("src", "${ctx}"+response.filePath);//预览img
				this.reset();
		    });
	  		up.on( 'uploadError', function(file, reason) {
	  			msgBox("上传格式错误", "info", 1200);
	  		});
	  		up.on( 'error', function(type) {
				//alert("错误信息:"+type);
				if(type=="Q_EXCEED_SIZE_LIMIT" || type=="F_EXCEED_SIZE"){
					msgBox("文件超过大小,请将上传文件大小控制在400kb以内", "erro");
				}else if(type=="Q_TYPE_DENIED"){
					msgBox("上传格式错误", "info", 1200);
	  			}else{
	  				msgBox("上传错误，请重试。", "info", 1200);
	  			}
		    });
	  	}
		function cteateUploder(pickId, preId, urlHiddenId, floderName, fileSingleSizeLimit, compress){
	  		var contextPath = "${ctx}";
	  		var options_={
				swf :  contextPath+'/manage/webuploader/Uploader.swf',
				server : contextPath+'/web/rideLine/fileUpload',
				runtimeOrder : "flash",
				accept : {extensions : 'jpg,jpeg,bmp,png'},
				formData:{
					code: '${rideLine.code}',
					floderName: floderName
				},
				pick : {
					id:'#'+pickId,
					multiple:false
				},
				fileVal : 'files',
				auto : true,
				resize: false,
				fileSingleSizeLimit: fileSingleSizeLimit,
				compress: false
			};
			addEvent(new WebUploader.Uploader(options_),preId,urlHiddenId);
		}
		var fileSingleSizeLimit =  1* 400 * 1024;
		var compress = {
			width: 833,
		    height: 255,
		    crop: true
		};
		cteateUploder("bannerPickId", "bannerPreId", "bannerHiddenId", "banner", fileSingleSizeLimit, compress);
	</script>
	
	<script type="text/javascript">
		
		$("#rideLineName").blur(function () {
			var $this = $(this);
			var thisVal = $this.val();
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,30), "内容必须在2-30个字符之间", "right");
		});
		$("#subTitle").blur(function () {
			var $this = $(this);
			var thisVal = $this.val();
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,30), "内容必须在2-30个字符之间", "right");
		});
		 /* 链接验证 */
	    $("#achref").blur(function () {
	        var thisVal = $(this).val();
	        var regTest = $.VLDTOR.IsHTTP(thisVal);
	        valid_txtBox_create(this, regTest || thisVal == "", "请输入以http(s)://开头的链接", "right");
	    });
		$("#introduce").blur(function () {
			var $this = $(this);
			var thisVal = $this.val();
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,2000), "内容必须在2-2000个字符之间", "right");
		});
		$("#summary").blur(function () {
			var $this = $(this);
			var thisVal = $this.val();
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,2000), "内容必须在2-2000个字符之间", "right");
		});
		$("#notice").blur(function () {
			var $this = $(this);
			var thisVal = $this.val();
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,2000), "内容必须在2-2000个字符之间", "right");
		});
	
		$(".saveBtn").click(function(){
			$("#rideLineName").blur();
			$("#subTitle").blur();
			$("#achref").blur();
			$("#introduce").blur();
			$("#summary").blur();
			$("#notice").blur();
			
			var hasImg = $("#bannerHiddenId").val();
			if(hasImg==''){
				creatErrMesg("#bannerPickId", "请至少上传一张图片", "top");
			} else {
				removeErrMesg("#bannerPickId");
			}
			
			/* 
			//富文本验证
			checkContent(introduceUE, 'introduce');
			checkContent(summaryUE, 'summary');
			checkContent(noticeUE, 'notice');
			*/
			
			// 验证通过
			var errMesgLen = $('.errMesg').length == 0;
			if(errMesgLen) {
				msgBox("添加成功！", "pass", 2600);
				$("#saveForm").submit();
			}
			// 验证未通过
			else {
				msgBox("输入的内容有误，请检查！", "erro", 2600);
				return false;
			}
			
		});
	</script>
	
</body>