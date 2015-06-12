<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
	<title>游西藏-目的地管理-景点信息管理-景点添加</title>
	<%@include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
	<style>
		/* 闭合浮动 */
		.floatfix:after {
			content: "";
			display: table;
			clear: both;
		}
	</style>
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
</head>

<body>
	<!-- main { -->
	<div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>游西藏</a> -</span>
			<span><a>目的地管理</a> -</span>
			<span><a href="${ctx}web/viewController/listView" target="_self">景点信息管理</a> -</span>
			<span><a href="#" target="_self">景点添加</a></span>
		</div>

		<!-- 模块管理 { -->
		<form id="scenic-add-form" action="${ctx}web/viewController/addView" method="post">
			<input type="hidden" name="code" value="${code}">
			<!-- 基本信息 { -->
			<div>
				<h2 class="mt40 title">景点基本信息:</h2>

				<div class="filament_solid_ddd ov-au mt10 pos-rela">

					<div class="formLine mt14">
						<div>
							<label>所属目的地:</label>
							<div id="subDest" class="select-base">
								<input type="hidden" name="destinationCode">
								<i class="w-140">请选择区域</i>
								<dl>
									<dt name="">请选择区域</dt>
									<c:forEach var="obj" items="${listDestination}">
				                    	<dt name="${obj.code}">${obj.destinationName }</dt>
				                    </c:forEach>
								</dl>
							</div>
							<span class="reqItem">*</span>
						</div>

						<div class="posi_t30_r20">
							<div>
								<label>想去人数:</label>
								<input id="fakeWantCount" type="text" class="w-100 number" name="fakeWantCount" value="0"/>
								<button type="button" class="btn-base" onclick="javascript:document.getElementById('fakeWantCount').value=0;">恢复默认数</button>
								<span class="txt-suggest ml10">*此项不填将显示真实人数</span>
							</div>

							<div class="mt40">
								<label>去过人数:</label>
								<input id="fakeGoneCount" type="text" class="w-100 number" name="fakeGoneCount" value="0"/>
								<button type="button" class="btn-base" onclick="javascript:document.getElementById('fakeGoneCount').value=0;">恢复默认数</button>
								<span class="txt-suggest ml10">*此项不填将显示真实人数</span>
							</div>
						</div>
					</div>

					<div class="formLine mt20">
						<label>景点名称:</label>
						<input id="scenicName" type="text" class="w-200" name="viewName"/>
						<span class="reqItem">*</span>
					</div>
					
					<div class="formLine mt20">
						<label>景点缩略图:</label>
						<div id="imgPickId" class="btn-base btn-uploadImg">点击上传图片</div>
						<span class="txt-suggest ml20">建议是276 * 336尺寸的jpg/png格式</span>
					</div>
					<div class="formLine">
						<label style="vertical-align: top;">缩略图:</label>
						<div id="" class="disp-ib">

							<div class="mb30">
								<img id="imgPreId" width="138" height="168" src="${ctx}/manage/resources/img/ele/loadImg_default_c.jpg" />
								<input id="imgHiddenId" type="hidden" name="img" value="/portal/static/default/square.png">
								<a href="javascript:void(0);" class="cleanImg">删除</a>
							</div>
						</div>
					</div>

					<div class="formLine">
						<label>景点主图:</label>
						<!-- <button type="button" class="btn-base btn-uploadImg">点击上传图片</button> -->
						<div id="pickId" class="btn-base btn-uploadImg">点击上传图片</div>
						<span class="txt-suggest ml20">必须是1000 * 360尺寸的jpg/png格式，最多上传5张图片</span>
						<input id="hiddenId" type="hidden" name="viewImage">
					</div>

					<div class="formLine">
						<label style="vertical-align: top;">主图缩略图:</label>
						<div id="preDiv" class="disp-ib">
							<div id="firstMainImage" class="mb30">
								<img class="scenImg" src="${ctx}/manage/resources/img/ele/loadImg_default_a.jpg" width="1000" height="360" />
								<a href="javascript:void(0);" class="cleanMainImage">删除</a>
							</div>
						</div>
					</div>
					<div id="preHiddenDiv" style="display: none;"><!-- 留作复制用 -->
						<div class="mb30">
							<img width="1000" height="360" src="${ctx}/manage/resources/img/ele/loadImg_default_a.jpg" />
							<a href="javascript:void(0);" class="cleanMainImage">删除</a>
						</div>
					</div>

					<div class="formLine mt20">
						<label class="pos_r_t5">景点介绍:</label>
						<%-- <textarea name="viewIntroduce" id="scenicIntro" cols="70" rows="10" class="mb20"></textarea> --%>
						<script id="scenicIntro" class="ueEditor_cont" name="viewIntroduce"  style="height:500px;" type="text/plain"></script>
						<span class="reqItem">*</span>
					</div>
				</div>
			</div>
			<!-- } 基本信息 -->

			<!-- 景点其他信息 { -->
			<div>
				<h2 class="mt100">景点其他信息:</h2>

				<div class="filament_solid_ddd ov-au mt30">
					<div class="formLine mt20">
						<label class="w-160">景点360全景链接:</label>
						<input type="text" class="w-260 toLink" name="view_360full"/>
					</div>
					<div class="formLine mt20">
						<label class="w-160">景点高清全景链接:</label>
						<input type="text" class="w-260 toLink" name="hdFullUrl"/>
					</div>
					<div class="formLine mt20">
						<label class="w-160">景点实景视频链接:</label>
						<input type="text" class="w-260 toLink" name="realSceneVideoUrl"/>
					</div>
					<div class="formLine mt20">
						<label class="w-160">景点地址:</label>
						<input id="address" type="text" class="w-260 secAdress" name="address"/>
						<input id="addressXY" type="hidden" class="w-260" name="xy"/>
						<span id="addressXYspan" class="txt-suggest ml20"></span><!-- style="display: none;" -->
					</div>
					<div class="formLine mt20">
						<label class="pos_r_t5 w-160">导览简介:</label>
						<!-- <textarea name="guide" id="" cols="70" rows="10" class="mb20 secInfo"></textarea> -->
						<script id="shortGuide" class="ueEditor_cont" name="shortGuide"  style="height:500px;" type="text/plain"></script>
					</div>
					<div class="formLine mt20">
						<label class="pos_r_t5 w-160">导览:</label>
						<!-- <textarea name="guide" id="" cols="70" rows="10" class="mb20 secInfo"></textarea> -->
						<script id="guide" class="ueEditor_cont" name="guide"  style="height:500px;" type="text/plain"></script>
					</div>
					<div class="formLine mt20">
						<label class="pos_r_t5 w-160">交通简介:</label>
						<!-- <textarea name="traffic" id="" cols="70" rows="5" class="mb20 secInfo"></textarea> -->
						<script id="shortTraffic" class="ueEditor_cont" name="shortTraffic"  style="height:500px;" type="text/plain"></script>
					</div>
					<div class="formLine mt20">
						<label class="pos_r_t5 w-160">交通:</label>
						<!-- <textarea name="traffic" id="" cols="70" rows="5" class="mb20 secInfo"></textarea> -->
						<script id="traffic" class="ueEditor_cont" name="traffic"  style="height:500px;" type="text/plain"></script>
					</div>
					<div class="formLine mt20">
						<label class="pos_r_t5 w-160">注意事项简介:</label>
						<!-- <textarea name="notice" id="" cols="70" rows="5" class="mb20 secInfo"></textarea> -->
						<script id="shortNotice" class="ueEditor_cont" name="shortNotice"  style="height:500px;" type="text/plain"></script>
					</div>
					<div class="formLine mt20">
						<label class="pos_r_t5 w-160">注意事项:</label>
						<!-- <textarea name="notice" id="" cols="70" rows="5" class="mb20 secInfo"></textarea> -->
						<script id="notice" class="ueEditor_cont" name="notice"  style="height:500px;" type="text/plain"></script>
					</div>
					
				</div>
			</div>
			<!-- } 景点其他信息 -->

			<!-- SEO信息 { -->
			<div class="contClassify">
				<h2 class="title">页面SEO信息</h2>
				<div class="filament_solid_ddd ov-au mt30">
					<div class="formLine mt20 mb20">
						<label class="w-180 ft-w-b ml40">&lt;Title&gt;标签:</label>
						<input type="text" maxlength="" class="w-320 seoInfo" name="tdkTitle">
					</div>
					<div class="formLine mt20 mb20">
						<label class="w-180 ft-w-b ml40">&lt;Description&gt;标签:</label>
						<input type="text" maxlength="" class="w-320 seoInfo" name="tdkDescription">
					</div>
					<div class="formLine mt20 mb20">
						<label class="w-180 ft-w-b ml40">&lt;Keywords&gt;标签(关键字):</label>
						<input type="text" maxlength="" class="w-320 seoInfo" name="tdkKeywords">
						<span class="txt-suggest ml20">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
					</div>
				</div>
			</div>
			<!-- } SEO信息 -->

			<div class="txt-ac mt40">
				<button class="btn-sure saveBtn" type="button">添加</button>
				<button class="btn-sure ml100" type="button" onclick="back();">取消</button>
			</div>
		</form>
		<!-- } 模块管理 -->
	</div>
	<!-- } main -->

	<!-- JS引用部分 -->
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
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
	<!-- JSP -->
	<%-- <script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script> --%>
	<script src="${ctx}manage/webuploaderbase/webuploader.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/webuploader/checkflash.js" type="text/javascript"></script>
	<!-- 实例化编辑器 -->
    <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.all.js"></script>
	<script type="text/javascript"> 
	    var ue = UE.getEditor('scenicIntro',{maximumWords:800,autoHeightEnabled:false});
	    ue.addListener("contentChange", function(e) {
			//$("#edui1_wordcount").empty();
			var ueCont = ue.getContentTxt();
			if(ueCont == "") {
				valid_txtBox_create("#scenicIntro", false, "内容必须在2-800个字符之间", "top-right");
				//return false;
			}
			var count = ue.getContentLength(true);
			if (count > 800 || count < 2) {
				valid_txtBox_create("#scenicIntro", false, "内容必须在2-800个字符之间", "top-right");
			}else{
				$("#scenicIntro").next(".errMesg").remove();
			}
		});
	    
	    //valid_txtBox_create(this, ($.VLDTOR.IsArticle(thisVal) && inputRange(this,2,2000)) || thisVal == "",'只能为空或字母、数字、中文及常见符号，且长度在2-2000','right');
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
		
	    var ue_guide = UE.getEditor('guide',{maximumWords:2000,autoHeightEnabled:false});
	    ue_guide.addListener("contentChange", function(e) {
	    	checkLength(ue_guide, 'guide');
		});
	    
	    var ue_traffic = UE.getEditor('traffic',{maximumWords:2000,autoHeightEnabled:false});
	    ue_traffic.addListener("contentChange", function(e) {
	    	checkLength(ue_traffic, 'traffic');
		});
	    
	    var ue_notice = UE.getEditor('notice',{maximumWords:2000,autoHeightEnabled:false});
	    ue_notice.addListener("contentChange", function(e) {
	    	checkLength(ue_notice, 'notice');
		});
	    
	    
	    
	    //各种简介
	    function checkShortLength(ue, id){
			var ueCont = ue.getContentTxt();
			if(ueCont == "") {
				valid_txtBox_create("#"+id, false, "内容必须在2-300个字符之间", "top-right");
				//return false;
			}
			var count = ue.getContentLength(true);
			if (count > 300 || count < 2) {
				valid_txtBox_create("#"+id, false, "内容必须在2-300个字符之间", "top-right");
			}else{
				$("#"+id).next(".errMesg").remove();
			}
		}
		
		function checkShortContent(ue, id){
			var ueCont = ue.getContentTxt();
			var count = ue.getContentLength(true);
			if(ueCont == "") {
				valid_txtBox_create("#"+id, false, "内容必须在2-300个字符之间", "top-right");
				msgBox("填写的信息有误，请检查！", "erro", 1200);
			}
			else if (count > 300 || count < 2) {
				msgBox("内容必须在2-2000个字符之间", "erro");
				msgBox("填写的信息有误，请检查！", "erro", 1200);
			}
		}
		
		var ue_shortguide = UE.getEditor('shortGuide',{maximumWords:300,autoHeightEnabled:false});
		ue_shortguide.addListener("contentChange", function(e) {
	    	checkShortLength(ue_shortguide, 'shortGuide');
		});
	    
	    var ue_shorttraffic = UE.getEditor('shortTraffic',{maximumWords:300,autoHeightEnabled:false});
	    ue_shorttraffic.addListener("contentChange", function(e) {
	    	checkShortLength(ue_shorttraffic, 'shortTraffic');
		});
	    
	    var ue_shortnotice = UE.getEditor('shortNotice',{maximumWords:300,autoHeightEnabled:false});
	    ue_shortnotice.addListener("contentChange", function(e) {
	    	checkShortLength(ue_shortnotice, 'shortNotice');
		});
	    
	</script>
	<!-- 上传组件 -->
	<script type="text/javascript">
		var paths = new Array();
		var isUp = true;
	  	function addEvent(up){
	  		up.on( 'uploadSuccess', function(file, response) {
				//paths.push(response.filePath);
				$("#firstMainImage").remove();
				var $div = $("#preHiddenDiv").children().clone();
				$div.find("img").attr("src","${ctx}"+response.filePath);
				$div.find("img").attr("imgpath",response.filePath);
				$("#preDiv").append($div);
				this.reset();
		    });
	  		up.on( 'beforeFileQueued', function() {
	  			var len = $("#preDiv").children("div").length;
	  			if(len==5){
	  				msgBox("最多上传5张图片", "erro", 1200);
	  				this.cancelFile();
	  				this.reset();
	  			}
		    });
	  		up.on( 'uploadError', function(file, reason) {
	  			msgBox("上传格式错误", "erro", 1200);
	  		});
	  		up.on( 'error', function(type) {
				//alert("错误信息:"+type);
				isUp = false;
				this.reset();
				if(type=="Q_EXCEED_SIZE_LIMIT" || type=="F_EXCEED_SIZE"){
					msgBox("文件超过大小", "erro", 1200);
				}else if(type=="Q_TYPE_DENIED"){
					msgBox("上传格式错误", "erro", 1200);
	  			}else if(type=="Q_EXCEED_NUM_LIMIT"){
					msgBox("最多上传5张图片，请重新选择文件。", "erro", 1200);
	  			}else {
	  				msgBox("上传错误，请重试。", "erro", 1200);
	  			}
		    });
	  	}
	  	var fileSingleSizeLimit = 1 * 400 * 1024;
		var compress = {
		    width: 1000,
		    height: 360,
		    allowMagnify: true,
		    crop: true
		};
		function cteateUploder(){
	  		var contextPath = "${ctx}";
	  		var options_={
				swf :  contextPath+'/manage/webuploader/Uploader.swf',
				server : contextPath+'/web/viewController/fileUpload',
				runtimeOrder : "flash",
				accept : {extensions : 'jpg,png'},
				formData:{
					code: '${code}',
					floderName: 'viewImg'
				},
				pick : {
					id:'#pickId',
					multiple:false
				},
				fileVal : 'files',
				auto : true,
				resize: false,
				fileNumLimit: 1,
				fileSingleSizeLimit: fileSingleSizeLimit,
				compress: false
			};
			addEvent(new WebUploader.Uploader(options_));
		}
		cteateUploder();
		
		//景点缩略图上传组件
		function createImgUploader(){
	  		var contextPath = "${ctx}";
	  		var options_={
				swf :  contextPath+'/manage/webuploader/Uploader.swf',
				server : contextPath+'/web/viewController/fileUpload',
				runtimeOrder : "flash",
				accept : {extensions : 'jpg,png'},
				formData:{
					code: '${code}',
					floderName: 'img'
				},
				pick : {
					id:'#imgPickId',
					multiple:false
				},
				fileVal : 'files',
				auto : true,
				resize: false,
				fileSingleSizeLimit: fileSingleSizeLimit,
				compress: false
			};
			var up = new WebUploader.Uploader(options_);
			up.on( 'uploadSuccess', function(file, response) {
				$("#imgHiddenId").val(response.filePath);
				$("#imgPreId").attr("src",contextPath + response.filePath);
		    });
	  		up.on( 'uploadFinished', function() {
	  			this.reset();
		    });
	  		up.on( 'uploadError', function(file, reason) {
	  			msgBox("上传格式错误", "erro", 1200);
	  		});
	  		up.on( 'error', function(type) {
				//alert("错误信息:"+type);
				isUp = false;
				this.reset();
				if(type=="Q_EXCEED_SIZE_LIMIT" || type=="F_EXCEED_SIZE"){
					msgBox("文件超过大小,请将上传文件大小控制在400kb以内", "erro");
				}else if(type=="Q_TYPE_DENIED"){
					msgBox("上传格式错误", "erro", 1200);
	  			}else if(type=="Q_EXCEED_NUM_LIMIT"){
					msgBox("最多上传5张图片，请重新选择文件。", "erro", 1200);
	  			}else {
	  				msgBox("上传错误，请重试。", "erro", 1200);
	  			}
		    });
		}
		createImgUploader();
		$(".cleanImg").click(function (){
			var defaultPath = contextPath + "/manage/resources/img/ele/loadImg_default_c.jpg";
			var hiddenPath =  "/portal/static/default/square.png";
			$("#imgPreId").attr("src", defaultPath);
			$("#imgHiddenId").val(hiddenPath);
		});
		
		$(document).on("click",".cleanMainImage",function(){
			$(this).parent("div").remove();
			var len = $("#preDiv").children("div").length;
			if(len==0){
				var $div = $("#preHiddenDiv").children().clone();
				$div.attr("id","firstMainImage");
				$("#preDiv").append($div);
			}
		});

	</script>
	<script type="text/javascript">
	
		//提交并获取坐标
		function submitAndGetXy(){
			$("#addressXY").val("");
			$("#addressXYspan").text("");
			//通过地址获取百度地图坐标
			if($("#address").val()){
				new BMap.Geocoder().getPoint(
					$("#address").val(),
					function(point){
						if(point){
							$("#addressXY").val(point.lng +"," + point.lat);
							$("#addressXYspan").text("坐标:"+point.lng +"," + point.lat);
						}
						msgBox("添加成功！", "pass", 2600);
						$("#scenic-add-form").submit();
					},
					"西藏"
				);
			}else{
				msgBox("添加成功！", "pass", 2600);
				$("#scenic-add-form").submit();
			}
		}
		
		/* 所属目的地验证 */
		$(document).on("click", "#subDest dt", function() {
			var $this = $(this),
				errMesg = $this.parents(".select-base").next(".errMesg"),
				errMesg_len = errMesg.length;
			// 后面有错误信息
			if(errMesg_len > 0) {
				// 移除错误信息
				errMesg.removeClass("errMesg").text("*");
			}
		});
		
		/* 景点名称验证 */
		$("#scenicName").blur(function () {
			var $this = $(this),
				thisVal = $this.val();
	
			// 执行验证并设置验证结果的状态
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,30), "名称只能为中英文和数字，且长度在2-30", "top");
		});
		
		/* 景点介绍验证 */
		/* $("#scenicIntro").blur(function() {
			var $this = $(this),
				thisVal = $this.val();
			// 执行验证并设置验证结果的状态
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,800), "只能为中英文数字及常见符号，且长度在2-800", "right");
		}); */
	
		/* 人数验证 */
		$(".number").blur(function () {
			var $this = $(this),
					thisVal = $this.val();
			valid_txtBox_create(this, $.VLDTOR.IsNum(thisVal) && inputRange(this,1,7) ,'只能1-7位正整数','top');
		});
	
		$(".toLink").blur(function () {
			var $this = $(this),
					thisVal = $this.val(),
					isNull = $this.val()=="";
			valid_txtBox_create(this, $.VLDTOR.IsHTTP(thisVal) || isNull,'需要输入http(s)://开头的链接','right');
		});
	
		/* 景点地址验证 */
		$(".secAdress").blur(function () {
			$("#addressXY").val("");
			$("#addressXYspan").text("");
			//通过地址获取百度地图坐标
			new BMap.Geocoder().getPoint(
				$("#address").val(),
				function(point){
					$("#addressXY").val(point.lng +"," + point.lat);
					$("#addressXYspan").text("坐标:"+point.lng +"," + point.lat);
				},
				"西藏"
			);
			var $this = $(this),
					thisVal = $this.val();
			valid_txtBox_create(this, ($.VLDTOR.IsArticle(thisVal) && inputRange(this,2,50)) || thisVal == "",'只能为空或字母、数字、中文及常见符号，且长度在2-50','right');
		});
	
		/* 导览，交通，注意事项验证 */
		/* $(".secInfo").blur(function () {
			var $this = $(this),
				thisVal = $this.val();
			valid_txtBox_create(this, ($.VLDTOR.IsArticle(thisVal) && inputRange(this,2,2000)) || thisVal == "",'只能为空或字母、数字、中文及常见符号，且长度在2-2000','right');
		}); */
		/* SEO 验证 */
		$(".seoInfo").blur(function () {
			var $this = $(this),
					thisVal = $this.val();
			valid_txtBox_create(this, ($.VLDTOR.IsEnCnNum_comma(thisVal) && inputRange(this,2,20)) || thisVal == "",'只能为空或字母、数字及中文，且长度在2-20','top');
		});
		
		/* 保存验证 */
		$(".saveBtn").click(function() {
			// 所属目的地验证
			var	subDest_slt = $("#subDest"),
				subDest = subDest_slt.children("i").text(),
				subDest_val = subDest.substr(0,3);
			
			if(subDest_val == "请选择") {
				errMesg(subDest_slt, "请选择所属目的地", "right");
				VLDRST.Destination = false;
			} else {
				subDest_slt.next(".errMesg").removeClass("errMesg").text("*");
				VLDRST.Destination = true;
			}
			
			// 失焦验证
			$("#scenicName").blur();
			$("#scenicIntro").blur();
			$(".number").blur();
			$(".toLink").blur();
			$(".secAdress").blur();
			$(".secInfo").blur();
			$(".seoInfo").blur();

			var ueCont = ue.getContentTxt();
			var count = ue.getContentLength(true);
			if(ueCont == "") {
				valid_txtBox_create("#scenicIntro", false, "内容必须在2-800个字符之间", "top-right");
				msgBox("填写的信息有误，请检查！", "erro", 1200);
			}
			else if (count > 800 || count < 2) {
				msgBox("内容必须在2-800个字符之间！", "erro");
				msgBox("填写的信息有误，请检查！", "erro", 1200);
			}
			
			checkContent(ue_guide, 'guide');
			checkContent(ue_traffic, 'traffic');
			checkContent(ue_notice, 'notice');
			checkShortContent(ue_shortguide, 'shortGuide');
			checkShortContent(ue_shorttraffic, 'shortTraffic');
			checkShortContent(ue_shortnotice, 'shortNotice');
			
			// 是否上传图片
			var imgs = $("#preDiv").find("img");
			var img_arrays = new Array();
			for(var i=0;i<imgs.length;i++){
				var path = imgs.eq(i).attr("imgpath");
				if(path){
					img_arrays.push(path);
				}
			}
			$("#hiddenId").val(img_arrays.join(","));
			//alert(img_arrays.join(","));
			var hasImage = $("#hiddenId").val();
			if(hasImage==''){
				creatErrMesg("#pickId", "请至少上传一张图片", "top");
			} else {
				removeErrMesg("#pickId");
			}
			
			// 验证通过
			var errMesgLen = $('.errMesg').length == 0;
			if(errMesgLen) {
				if($("#fakeWantCount").val()==undefined || $("#fakeWantCount").val()==""){
					$("#fakeWantCount").val(0);
				}
				if($("#fakeGoneCount").val()==undefined || $("#fakeGoneCount").val()==""){
					$("#fakeGoneCount").val(0);
				}
				$.post("${ctx}web/viewController/getSameViewNum",{name:$("#scenicName").val()},function(data){
					if(data==0){
						//alert($("#addressXY").val());
						//msgBox("添加成功！", "pass", 2600);
						//$("#scenic-add-form").submit();
						submitAndGetXy();
					}else{
						msgBox("已有同名景点", "erro", 1200);
					}
				});
				
			}
			// 验证未通过
			else {
				msgBox("输入的内容有误，请检查！", "erro", 2600);
				return false;
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