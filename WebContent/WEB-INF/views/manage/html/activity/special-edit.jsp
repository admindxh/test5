<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
	<title>活动&专题-活动&专题信息管理-编辑专题</title>
	<%@ include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/datepicker.css" />
	<!-- 网页文本编辑器插件 -->
	<%-- <script src="${ctxManage}/resources/plugin/ckeditor/ckeditor.js" type="text/javascript"></script> --%>
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
		<form id="saveForm" method="post">
			<input type="hidden" name="code" value="${content.code }">
			<input type="hidden" name="praiseCode" value="${praise.code }">
			<!-- 页面位置-->
			<div class="location">
				<label>您当前位置为:</label>
				<span><a>活动&专题</a> -</span>
				<span><a>活动&专题信息管理</a> -</span>
				<span><a href="${ctx}web/specialController/showSpecialList" target="_self">专题信息管理</a> -</span>
				<span><a href="#" target="_self">修改专题</a></span>
			</div>

			<!-- 数据操作 -->
			<div class="searchManage">
				<button type="button" class="btn-sure preview">预览</button>
				<button type="button" class="btn-sure saveAdd">发布</button>
			</div>

			<!-- 模块管理 { -->
			<div class="muduleManage details filament_solid_ddd">
				<!-- 活动类型 { -->
				<div class="contClassify bt-n">
					<!--<h2 class="title">活动类型</h2>-->

					<div class="formLine">
						<label class="w-auto">类型:</label>
						<span class="dataVal">专题</span>
					</div>

				</div>
				<!-- } 活动类型 -->

				<!-- 基本信息 { -->
				<div class="contClassify">
					<h2 class="title">基本信息</h2>

					<div class="formLine">
						<label>专题名称:</label>
						<input type="text" maxlength="30" class="w-260" name="contentTitle" value="${content.contentTitle }">
						<span class="reqItem">*</span>

						<label>序号:</label>
						<input id="sortNum" type="text" maxlength="5" name="sortNum" value="${content.sortNum }">
						
						<label>查看量:</label>
						<input id="viewCount" type="text" name="falseViewcount" maxlength="7" value="${praise.falseViewcount }">
						<button type="button" class="btn-base" onclick="javascript:$('#viewCount').val(${not empty praise.viewCount?praise.viewCount:0});">恢复默认</button>
						
					</div>

					<div class="formLine">
						<label>缩略图:</label>
						<input id="hiddenUrlTag" type="hidden" name="tag" value="${content.tag}">
						<div id="imgFileTag" class="btn-base uploadImg">点击上传图片</div>
						<span class="txt-suggest ml20">推荐尺寸：317 * 229</span>
					</div>
					<div class="formLine">
						<label class="pos_r_t5">缩略图预览：</label>
						<img width="317" height="229" id="preImgTag" src="${ctx}${content.tag}" title="缩略图名称" alt="请上传图片" class="va_t">
					</div>

					<div class="formLine">
						<label>Banner图:</label>
						<!-- <input id="imgFile" type="file" name="files"> -->
						<input id="hiddenUrl" type="hidden" name="title" value="${content.title }">
						<!-- <button id="imgFile" type="button" class="btn-base uploadImg">点击上传图片</button> -->
						<div id="imgFile" class="btn-base uploadImg">点击上传图片</div>
						<span class="txt-suggest ml20">推荐尺寸：1903 * 500</span>
					</div>
					<div class="formLine">
						<label class="pos_r_t5">缩略图：</label>
						<img id="preImg" src="${ctx}${content.title}" title="缩略图名称"
                             alt="请上传图片" class="va_t" style="width: 1140px;height: 300px;">
					</div>

					<div class="formLine">
						<label class="pos_r_t5">活动简介:</label>
						<%-- <span class="dataVal va_t">
							<textarea id="editor1" name="summary" cols="80" rows="10" class="ckeditor" style="display:inline-block">${content.summary }</textarea>
						</span> --%>
						<script id="editor1" class="ueEditor_cont" name="summary"  style="height:500px;" type="text/plain">${content.summary }</script>
					</div>

				</div><!-- } 基本信息 -->
				
			
				<!-- SEO信息 { -->
				<div class="contClassify">
					<h2 class="title">页面SEO信息</h2>
					<div class="filament_solid_ddd ov-au mt30">
						<div class="formLine mt20 mb20">
							<label class="w-180 ft-w-b ml40">&lt;Title&gt;标签:</label>
							<input type="text" maxlength="" class="w-320 seoInfo" name="tdkTitle" value="${content.tdkTitle }">
						</div>
						<div class="formLine mt20 mb20">
							<label class="w-180 ft-w-b ml40">&lt;Description&gt;标签:</label>
							<input type="text" maxlength="" class="w-320 seoInfo" name="tdkDescription" value="${content.tdkDescription }">
						</div>
						<div class="formLine mt20 mb20">
							<label class="w-180 ft-w-b ml40">&lt;Keywords&gt;标签(关键字):</label>
							<input type="text" maxlength="" class="w-320 seoInfo" name="tdkKeywords" value="${content.tdkKeywords }">
							<span class="txt-suggest ml20">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
						</div>
					</div>
				</div>
				<!-- } SEO信息 -->
				
				<!-- 确认按钮 -->
				<%--
				<div class="formLine saveOper">
					<button type="button" class="btn-base mr30 saveAdd">发布</button>
					<button type="button" class="btn-base" onclick="back();">取消</button>
				</div>
				--%>

			</div>
			<!-- } 模块管理 -->
		</form>
	</div>
	<!-- } main -->
	
	<!-- JS引用部分 -->
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/bootstrap-datepicker.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base-form.js"></script>
	<script src="${ctxManage}/resources/js/activity/activity-creat.js" type="text/javascript"></script>
	<script src="${ctxManage}resources/plugin/respond.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>

	<!-- 实例化编辑器 -->
    <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.all.js"></script>
	<script type="text/javascript"> 
		var ue = UE.getEditor('editor1', {autoHeightEnabled: false, initialFrameHeight: 350});
	</script>
	<script type="text/javascript">
		function back(){
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">是否返回？</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				javascript:history.back(-1);
				closePopup();
			});
		}
	</script>
	<script src="${ctx}manage/webuploaderbase/webuploader.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/webuploader/checkflash.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		function save(){
			$("input[type='file']").attr('disabled',"true");
			var form = $("#saveForm");
			form.attr("target", "_self");
			form.attr("action", "${ctx }web/specialController/editSpecial");
			form.submit();
		}
		function pre(){
			//$("input[type='file']").attr('disabled',"true");
			var form = $("#saveForm");
			form.attr("target", "_special_pre");
			form.attr("action", "${ctx }web/specialController/previewSpecial");
			form.submit();
		}
		
		//上传插件
		var options_={
			swf :  '${ctx}/manage/webuploader/Uploader.swf',
			server : '${ctx}/web/specialController/fileUpload',
			runtimeOrder : "flash",
			accept : {extensions : 'jpg,jpeg,bmp,png'},
			formData:{
				code:'${content.code }',
				floderName: 'spe'
			},
			pick : {
				id:'#imgFile',
				multiple:false
			},
			fileVal : 'files',
			auto : true,
			resize: false,
			fileSingleSizeLimit: 1 * 400 *1024,
			compress: {
				width: 1920,
			    height: 420,
			    allowMagnify: true,
			    crop: true
			}
		};
		var uploader = new WebUploader.Uploader(options_);
		uploader.on( 'uploadSuccess', function(file, response) {
			$("#preImg").attr("src",contextPath + response.filePath);
			$("#hiddenUrl").val(response.filePath);
			this.reset();
	    });
		uploader.on( 'uploadError', function(file, reason) {
  			msgBox("上传格式错误", "info", 1200);
  		});
		uploader.on( 'error', function(type) {
			//alert("错误信息:"+type);
			if(type=="Q_EXCEED_SIZE_LIMIT" || type=="F_EXCEED_SIZE"){
				msgBox("文件超过大小,请将上传文件大小控制在400kb以内", "erro");
			}else if(type=="Q_TYPE_DENIED"){
				msgBox("上传格式错误", "info", 1200);
  			}else{
  				msgBox("上传错误，请重试。", "info", 1200);
  			}
	    });
	</script>
	<script type="text/javascript">
		//上传缩略图
		var options_tag_={
			swf :  '${ctx}/manage/webuploader/Uploader.swf',
			server : '${ctx}/web/specialController/fileUpload',
			runtimeOrder : "flash",
			accept : {extensions : 'jpg,jpeg,bmp,png'},
			formData:{
				code:'${content.code }',
				floderName: 'speImg'
			},
			pick : {
				id:'#imgFileTag',
				multiple:false
			},
			fileVal : 'files',
			auto : true,
			resize: false,
			fileSingleSizeLimit: (1 * 400 *1024),
			compress: {
				width: 317,
			    height: 229,
			    allowMagnify: true,
			    crop: true
			}
		};
		var uploader_tag = new WebUploader.Uploader(options_tag_);
		uploader_tag.on( 'uploadSuccess', function(file, response) {
			$("#preImgTag").attr("src",contextPath + response.filePath);
			$("#hiddenUrlTag").val(response.filePath);
			this.reset();
	    });
		uploader_tag.on( 'uploadError', function(file, reason) {
				msgBox("上传格式错误", "info", 1200);
			});
		uploader_tag.on( 'error', function(type) {
			//alert("错误信息:"+type);
			if(type=="Q_EXCEED_SIZE_LIMIT" || type=="F_EXCEED_SIZE"){
				msgBox("文件超过大小,请将上传文件大小控制在400kb以内", "erro");
			}else if(type=="Q_TYPE_DENIED"){
				msgBox("上传格式错误", "info", 1200);
				}else{
					msgBox("上传错误，请重试。", "info", 1200);
				}
	    });
	</script>
	
	<script type="text/javascript">
		 //验证
		 // 专题名称
		$("[name='contentTitle']").blur(function () {
			var this_val = $(this).val(),
				testPass = $.VLDTOR.IsArticle(this_val),
				lenRange = inputRange(this, 2, 30);
			
			valid_txtBox(this, testPass && lenRange, "只能输入中文、英文、数字常用符号且长度在2-30", "top");
		});
		
		// 序号
		$("[name='sortNum']").blur(function () {
			var this_val = $(this).val(),
				testPass = $.VLDTOR.IsNum(this_val);
			valid_txtBox_create(this, testPass || this_val == "", "只能为正整数", "top");
		});

		// 查看量
		$("#viewCount").blur(function () {
			var this_val = $(this).val(),
				testPass = $.VLDTOR.IsNum(this_val);
			valid_txtBox_create(this, testPass || this_val == "", "只能为正整数", "top");
		});

		// 富文本内容验证
		 ue.addListener( 'contentChange', function() {
			 var ueCont = this.getContentTxt();
			 if(ueCont != "") {
				 valid_richTxt(this,"#editor1",2,5000,"内容必须在2-5000个字符之间");
			 }
			 else {
				 removeErrMesg("#editor1");
			 }
		 });
		 ue.addListener( 'focus', function() {
			 var ueCont = this.getContentTxt();
			 if(ueCont != "") {
				 valid_richTxt(this,"#editor1",2,5000,"内容必须在2-5000个字符之间");
			 }
			 else {
				 removeErrMesg("#editor1");
			 }
		 });
		
		// keywords
		$("[name='keywords']").blur(function () {
			var this_val = $(this).val(),
				testPass = $.VLDTOR.IsArticle(this_val),
					lenRange = inputRange(this, 2, 20);
			valid_txtBox_create(this, testPass && lenRange || this_val == "", "只能输入2-30个字符", "top");
		});
		/* SEO 验证 */
		$(".seoInfo").blur(function () {
			var $this = $(this),
				thisVal = $this.val();
			valid_txtBox_create(this, ($.VLDTOR.IsEnCnNum_comma(thisVal) && inputRange(this,2,20)) || thisVal == "",'只能为空或字母、数字及中文，且长度在2-20','top');
		});
		// 保存验证
		function submitForm(call_){
			// 失焦验证
			$("[name='contentTitle'], [name='sortNum'], #viewCount, [name='keywords']").blur();
			/* SEO 验证 */
			$(".seoInfo").blur();
			// 是否上传banner图片
			var hasImage = $("#hiddenUrl").val();
			if(hasImage==''){
				creatErrMesg($("#imgFile").next("span"), "请上传Banner图", "right");
			} else {
				removeErrMesg($("#imgFile").next("span"));
			}

			var errLen = $(".errMesg").length > 0;
			// 不通过
			if(errLen) {
				msgBox("填写的信息有误，请检查", "erro");
				return;
			}
			// 验证通过
			else {
				var sortNum = $("#sortNum").val();
				if(sortNum==undefined || sortNum==""){
					$("#sortNum").val(0);
				}
				// 提交表单
				call_();
			}
		}
		$(".saveAdd").click(function() {
			ispost=true;
			msgBox("保存成功！", "pass");
			submitForm(save);
		});
		$(".preview").click(function() {
			ispost=true;
			submitForm(pre);
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
