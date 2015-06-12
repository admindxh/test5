<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
	<title>骑行专区-骑行路线站点管理-新建站点</title>
	<%@ include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctx}manage/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctx}manage/resources/css/base.css" />
	<link rel="stylesheet" href="${ctx}manage/resources/css/travel/formWeb.css" />
	<script src="${ctx}manage/resources/plugin/respond.min.js" type="text/javascript"></script>
	
</head>

<body>
	<!-- main { -->
	<div class="main">
		<form id="saveForm" method="post" action="${ctx}web/serviceSite/save">
			<input type="hidden" name="code" value="${code}">
			<!-- 页面位置-->
			<div class="location">
				<label>您当前位置为:</label>
				<span><a>骑行专区</a> -</span>
				<span><a href="${ctx}/web/serviceSite/list">骑行路线站点管理-</a></span>
				<span><a href="${ctx}/web/serviceSite/addUI">新建站点</a></span>
			</div>

			<!-- 模块管理 { -->
			<div class="muduleManage details filament_solid_ddd">

				<!-- 基本信息 { -->
				<div class="contClassify">
					<h2 class="title">基本信息</h2>
					<div class="formLine">
						<label>站点名称:</label>
						<input type="text" maxlength="30" class="w-260" name="siteName" id="rideLineName">
						<span class="reqItem">*</span>
						<label>线路类型:</label>
						<div id="theirMudle"  class="select-base">
							<i class="w-140">请选择所属类型</i>
							<dl>
								<c:forEach var="program" items="${rideList}">
									<dt  inputValue="${program.code}" inputName="ridelineId">${program.name}</dt>
								</c:forEach>
							</dl>
							<input id="proType" type="hidden" value="" name="ridelineId"/>
						</div>
						<span class="reqItem">*</span>
					</div>
					
					<div class="formLine mt20">
						<label>站点Banner图:</label>
						<div id="bannerPickId" class="btn-base uploadImg">点击上传图片</div>
						<span class="txt-suggest ml20">推荐尺寸：150 * 150</span>
						<span class="reqItem">*</span>
					</div>
					<div class="formLine">
						<label class="pos_r_t5">Banner图预览：</label>
						<img id="bannerPre"  style="width:150px;height:150px;" src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg" title="缩略图名称" alt="请上传图片" class="va_t" style="width: 150px;height: 150px;">
						<input id="bannerHiddenId" type="hidden" name="sitImg">
					</div>
					<div class="formLine">
						<label>站点序号:</label>
						<input type="text" maxlength="30" class="w-260" name="orderSite" id="order">
						<span class="reqItem">*</span>
					</div>

				</div><!-- } 基本信息 -->
				
			<div class="contClassify">
					<h2 class="title">站点服务信息</h2>
					<div class="formLine">
						<label>服务站点名称:</label>
						<input type="text" maxlength="30" class="w-260" name="serviceName" id="serviceName">
						<span class="reqItem">*</span>
					</div>
					
					
					<div class="formLine mt20">
						<label>服务站点Banner图:</label>
						<div id="servicePickId" class="btn-base uploadImg">点击上传图片</div>
						<span class="txt-suggest ml20">推荐尺寸：120 * 76</span>
						<span class="reqItem">*</span>
					</div>
					<div class="formLine">
						<label class="pos_r_t5">Banner图预览：</label>
						<img id="servicePreId" style="width:120px;height:76px;" src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg" title="缩略图名称" alt="请上传图片" class="va_t" style="width: 120px;height: 76px;">
						<input id="serviceImgHiddenId" type="hidden" name="serviceImg">
					</div>
					
					
					<div class="formLine">
						<label>服务站点电话:</label>
						<input type="text" maxlength="30" class="w-260" name="servicePhone" id="servicePhone">
						<span class="reqItem">*</span>
					</div>
					
					<div class="formLine mt20">
						<label class="pos_r_t5">服务站点地址:</label>
						<%--<script id="summary" class="ueEditor_cont" name="summary"  style="height:500px;" type="text/plain"></script> --%>
						<textarea id="serviceAdress" name="serviceAdress" cols="70" rows="5"></textarea>
						<span class="reqItem">*</span>
					</div>
			</div>
			
			
			
			<div class="contClassify">
                <h2 class="title">商户一(宿)</h2>

                <div class="formLine mt15">
                    <label>编号:</label>
                    <input type="text"  name="urls" class="w-320  to-link">
                    <input type="hidden"  name="types"  value="merchantShu">
                </div>
            </div>

            <!-- 新闻二 { -->
            <div class="contClassify">
                <h2 class="title">商户二(宿)</h2>

                <div class="formLine mt15">
                    <label>编号:</label>
                    <input type="text"  name="urls" class="w-320 to-link">
                    <input type="hidden"  name="types"  value="merchantShu">
                </div>
            </div>
			
			<div class="contClassify">
                <h2 class="title">商户一(食)</h2>

                <div class="formLine mt15">
                    <label>编号:</label>
                    <input type="text"  name="urls" class="w-320  to-link">
                    <input type="hidden"  name="types"  value="merchantShi">
                </div>
            </div>

            <!-- 新闻二 { -->
            <div class="contClassify">
                <h2 class="title">商户二(食)</h2>

                <div class="formLine mt15">
                    <label>编号:</label>
                    <input type="text"  name="urls" class="w-320 to-link">
                    <input type="hidden"  name="types"  value="merchantShi">
                </div>
            </div>


            <!-- 新闻三 { -->
            <div class="contClassify">
                <h2 class="title">攻略一</h2>
                <div class="formLine mt15">
                    <label>编号:</label>
                     <input type="text"  name="urls" class="w-320 to-link">
                    <input type="hidden"  name="types"  value="content">
                </div>
            </div>
			
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
		    width: 1903,
		    height: 500,
		    crop: true
		};
		cteateUploder("bannerPickId", "bannerPre", "bannerHiddenId", "banner", fileSingleSizeLimit, compress);
		cteateUploder("servicePickId", "servicePreId", "serviceImgHiddenId", "banner", fileSingleSizeLimit, compress);
	</script>
	
	<script type="text/javascript">
	  $(".to-link").blur(function () {
        var this_val = $(this).val(),
                isNull = this_val == "";
        if(isNull) {
            creatErrMesg(this, "不能为空", "right");
        } else {
            removeErrMesg(this);
        }
    });
	// 装备类型验证
		$(document).on("click", "#theirMudle", function() {
			var $this = $(this),
				this_i = $this.children("i").text().substr(0,3);
			if(this_i != "请选择") {
				valid_customSelect("#theirMudle", "请选择线路类型");
				VLDRST.TheirMudle = true;
			} else {
				VLDRST.TheirMudle = false;
			}
		});
	// 价格数验证
		$("#order").blur(function() {
			var $this = $(this),
				thisVal = $this.val();
			// 执行验证并设置验证结果的状态
			if(valid_txtBox_create(this,inputRange(this, 1, 7) && $.VLDTOR.IsNum(thisVal) && thisVal != "", "只能输入1-7位的正整数","left")) {
				VLDRST.Collect_num = true;
			} else {
				VLDRST.Collect_num = false;
			}
		});
		$("#rideLineName").blur(function () {
			var $this = $(this);
			var thisVal = $this.val();
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,30), "只能为2-30个字符", "top");
		});
		
		$("#serviceName").blur(function () {
			var $this = $(this);
			var thisVal = $this.val();
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,30), "只能为2-30个字符", "right");
		});


		
		$("#servicePhone").blur(function () {
			var $this = $(this);
			var thisVal = $this.val();
			valid_txtBox(this, $.VLDTOR.IsPhone(thisVal) , "请填写电话号码或者手机号", "right");
			
		});



		
		
		$("#serviceAdress").blur(function () {
			var $this = $(this);
			var thisVal = $this.val();
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,200), "只能为2-200个字符", "right");
		});

		
	
		$(".saveBtn").click(function(){
			$("#rideLineName").blur();
			$("#serviceName").blur();
			$("#servicePhone").blur();
			$("#serviceAdress").blur();
			valid_customSelect("#theirMudle", "请选择所属类型");
			
			var hasImg = $("#bannerHiddenId").val();
			if(hasImg==''){
				creatErrMesg("#bannerPickId", "请至少上传一张图片", "top");
			} else {
				removeErrMesg("#bannerPickId");
			}
			var hasImg1 = $("#serviceImgHiddenId").val();
			if(hasImg1==''){
				creatErrMesg("#servicePickId", "请至少上传一张图片", "top");
			} else {
				removeErrMesg("#servicePickId");
			}
			$(".to-link").blur();
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
	<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
</body>