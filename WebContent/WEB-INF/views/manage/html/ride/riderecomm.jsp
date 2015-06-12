<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
	<title>骑行专区-骑行首页相关推荐</title>
	<%@ include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctx}manage/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctx}manage/resources/css/base.css" />
	<link rel="stylesheet" href="${ctx}manage/resources/css/travel/formWeb.css" />
	<script src="${ctx}manage/resources/plugin/respond.min.js" type="text/javascript"></script>
	 <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css"/>
	  <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css"/>
</head>

<body>
	<!-- main { -->
	<div class="main">
		<form id="saveForm" method="post" action="${ctx}web/ridecommon/save">
			<input type="hidden" name="code" value="${code}">
			<!-- 页面位置-->
			<div class="location">
				<label>您当前位置为:</label>
				<span><a>骑行专区</a> -</span>
				<span><a>骑行专区首页</a> -</span>
				<span><a href="${ctx}/web/ridecommon/list">骑行首页相关推荐</a></span>
			</div>

			<!-- 模块管理 { -->
			<div class="muduleManage details filament_solid_ddd">

				<!-- 基本信息 { -->
				<div class="contClassify">
					<h2 class="title">本期经典攻略</h2>
					<div class="formLine">
						<label>经典攻略编号:</label>
						<input type="hidden"  value="jdgl" name="contentTypes"/>
						<input type="hidden"  value="" name="urls"/>
						<input type="hidden"  value="" name="imgurl"/>
						<input type="text" maxlength="30" value="${jdgl[0].ctorname }" class="w-260" name="ctornames" id="rideLineName">
						<span class="reqItem">*</span>
					</div>
				</div><!-- } 基本信息 -->
				
			<div class="contClassify">
					<h2 class="title">友情链接</h2>
					<div class="formLine">
						
						<input type="hidden"  value="yqlj" name="contentTypes"/>
						<input type="hidden"  value="" name="imgurl"/>
						<label>友情链接名称:</label>
						<input type="text" maxlength="30" class="w-260 ctornames"  value="${yqlj[0].ctorname}"  name="ctornames" >
						<span class="reqItem"></span>
						<label>友情链接地址:</label>
						<input type="text" maxlength="30" class="w-260 urls" value="${yqlj[0].url}" name="urls" >
					    <span class="reqItem"></span>
						
					</div>
					<div class="formLine">
					<input type="hidden"  value="yqlj" name="contentTypes"/>
						<input type="hidden"  value="" name="imgurl"/>
						<label>友情链接名称:</label>
						<input type="text" maxlength="30" class="w-260 ctornames" value="${yqlj[1].ctorname}" name="ctornames" >
						<span class="reqItem"></span>
						<label>友情链接地址:</label>
						<input type="text" maxlength="30" class="w-260 urls" value="${yqlj[1].url}" name="urls" >
						<span class="reqItem"></span>
						
						
						
					</div>
					<div class="formLine">
					<input type="hidden"  value="yqlj" name="contentTypes"/>
						<input type="hidden"  value="" name="imgurl"/>
						<label>友情链接名称:</label>
						<input type="text" maxlength="30" class="w-260 ctornames" value="${yqlj[2].ctorname}" name="ctornames" >
						<span class="reqItem"></span>
						<label>友情链接地址:</label>
						<input type="text" maxlength="30" class="w-260 urls" value="${yqlj[2].url}" name="urls" >
						<span class="reqItem"></span>
						
					</div>
					<div class="formLine">
					     <input type="hidden"  value="yqlj" name="contentTypes"/>
						<input type="hidden"  value="" name="imgurl"/>
						<label>友情链接名称:</label>
						<input type="text" maxlength="30" class="w-260 ctornames" value="${yqlj[3].ctorname}" name="ctornames" >
						<span class="reqItem"></span>
						<label>友情链接地址:</label>
						<input type="text" maxlength="30" class="w-260 urls" value="${yqlj[3].url}" name="urls" >
						<span class="reqItem"></span>
					</div>
			</div>
			
			
			
			<div class="contClassify">
                <h2 class="title">装备推荐</h2>

                <c:forEach var="x" begin="1" end="4" step="1">
                     <input type="hidden"  value="zbtj" name="contentTypes"/>
                     <input type="hidden"  value="" name="urls"/>
                    <div class="posidSet-unit">
                        <i>图片${x}</i>
                        <div class="left perc60">
                            <div class="formLine">
                                <label>图片${x}:</label>
                                <div id="pickfiles${x}" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：135 * 135</span>
                            </div>
                            <div class="formLine">
                                <label>装备编号:</label>
                                <input class="showTitle" name="ctornames" value="${zbtj[x-1].ctorname}" type="text">
                            </div>
                        </div>
                        <div class="right mt10 perc40">
                            <label>缩略图:</label>
                            <img id="imgshow${x}"
                            <c:if test="${empty  zbtj[x-1].imgurl}">
                                 src="${ctx}portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty zbtj[x-1].imgurl}">
                                 src="${ctx}${zbtj[x-1].imgurl}"
                            </c:if>
                                 title="缩略图名称" alt="请上传图片" class="style-d" style="width:135px;height:135px;">
                            <input type="hidden"  id="hidden${x}" class="url" name="imgurl"

                                    <c:if test="${empty zbtj[x-1].imgurl}">
                                        value="/portal/static/default/square.png"
                                    </c:if>
                                    <c:if test="${not empty zbtj[x-1].imgurl}">
                                        value="${zbtj[x-1].imgurl}"
                                    </c:if>


                                    />
                        </div>
                       
                       
                       
                    </div>
            	</c:forEach>
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
					code: 'EQ',
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
		for(var i = 1;i<=4;i++){
		      cteateUploder("pickfiles"+i, "imgshow"+i, "hidden"+i, "banner", fileSingleSizeLimit, compress);
	     }
	</script>
	
	<script type="text/javascript">
	  $(".ctornames").blur(function () {
		 	 var $this = $(this);
			var thisVal = $this.val();
		  if(!thisVal){
			  removeErrMesg(".ctornames");
			return ;
		  }
		  valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,10), "名称只能为中英文和数字，且长度在2-10", "top");
       });
	  $(".urls").blur(function () {
		    var $this = $(this);
			var thisVal = $this.val();
			if(!thisVal){
				removeErrMesg(".urls");
				return ;
			}
		  valid_txtBox(this, $.VLDTOR.IsHTTP(thisVal), "地址只能是以http或者https开头", "top");
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
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,30), "经典攻略编号不能为空,且长度在2-30", "right");
		});
		
		$("#serviceName").blur(function () {
			var $this = $(this);
			var thisVal = $this.val();
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,30), "名称只能为中英文和数字，且长度在2-30", "right");
		});


		
		$("#servicePhone").blur(function () {
			var $this = $(this);
			var thisVal = $this.val();
			valid_txtBox(this, $.VLDTOR.IsPhone(thisVal) , "请填写电话号码,或者手机号", "right");
		});



		
		
		$("#serviceAdress").blur(function () {
			var $this = $(this);
			var thisVal = $this.val();
			valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,2000), "名称只能为中英文和数字，且长度在2-2000", "right");
		});

		
	
		$(".saveBtn").click(function(){
			$("#rideLineName").blur();
			$(".ctornames").blur();
			$(".urls").blur();

			
			
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