<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
		<title>游西藏-商户管理-商户汇总页显示</title>
		<link rel="stylesheet"
			href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
		<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
		<script src="${ctxManage}/resources/plugin/respond.min.js"
			type="text/javascript"></script>
		<link rel="stylesheet"
			href="${ctxManage}/resources/css/home/imgDescr.css" />
		<link rel="stylesheet"
			href="${ctxManage}/resources/css/travel/formWeb.css" />
		<link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
		<script type="text/javascript">
	 //===============================================================
	 //                            上传插件
	 //===============================================================
	  $(function() {
    
    var imgId;
	$(".imgNode").click(
		function(){
			var temp = $(this).parent().parent().parent().find("img").attr("id");
			//.log(temp);
			imgId = temp;
		}
	);
    	// 初始化Web Uploader
    	var uploader = WebUploader.create( {
    		
    		// 自动上传。
    		auto : true,

    		// swf文件路径
    		swf :  contextPath+'/manage/webuploader/Uploader.swf',

    		// 文件接收服务端。
    		server : contextPath+'/web/imageController/uploadImage',

    		// 选择文件的按钮。可选。
    		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
    		pick : '.pickfiles',

    		// 验证文件总数量, 超出则不允许加入队列x
    		fileNumLimit : 10,
    		fileSizeLimit : 1 * 1024 * 1024,
    		// 设置文件上传域的name
    		fileVal : 'file',

    		// 只允许选择文件，可选。
    		accept : {
    			title : 'Images',
    			extensions : 'gif,jpg,jpeg,bmp,png',
    			mimeTypes : 'image/*'
    		},
    		runtimeOrder : "flash"
    	});

    	uploader.on('beforeFileQueued', function(file) {
    		uploader.reset();
    	})
        
		uploader.on('uploadSuccess', function(file) {		
			$.ajax( {
				type:"post",
				url : contextPath+"/web/imageController/getUrl",
				dataType : "text",
				async : false,
				success : function(data) {
				//.log("uploadSuccess"+data);
                $('#'+imgId).attr("src","${ctx}"+data);
                $("#"+imgId).next().val(data);				 
			},
			error : function() {
				alert("数据异常");
			}
			});
		})
    });
   //********************************************************

	    
	</script>
	</head>

	<body>
		<!-- main { -->
		<div class="main">
			<form id="commer-show-form" method="post"
				enctype="multipart/form-data">
				<input type="hidden" name="programaCode" value="${programaCode}">
				<!-- 页面位置-->
				<div class="location">
					<label>
						您当前位置为:llllllllllllllllllllllll
					</label>
					<span><a>游西藏</a> -</span>
					<span><a href="travel.html" target="_self">商户管理</a> -</span>
					<span><a>商户汇总页显示</a>
					</span>
				</div>

				<!-- 查看按钮 -->
				<!--			<div class="operManage">
				<button type="button" class="lookup btn-sure">查看</button>
			</div>-->

				<!-- 模块管理 { -->
				<div class="muduleManage filament_solid_ddd pos-rela">
					<h2 class="lev2">
						Banner广告位
					</h2>


					<div class="posidSet-unit mt35">
						<i>图片1</i>
						<div class="left">
							<div class="formLine">
								<label>
									图片:
								</label>
								<div id="pickfiles1" class="btn-uploadImg pickfiles imgNode">
									请上传图片
								</div>
								<span class="txt-suggest ml20">推荐尺寸：800 * 360</span>
							</div>
							<div class="formLine">
								<label>
									链接至:
								</label>
								<input id="hyperlink1" name="content" type="text" maxlength=""
									value="${bannerList[0].content }" class="w-260">
							</div>
						</div>
						<div class="right mt10">
							<label>
								缩略图:
							</label>
							<img id="imgshow1"
								<c:if test="${not empty bannerList[0].url }">src="${ctx }${bannerList[0].url }"</c:if>
								<c:if test="${empty bannerList[0].url}">src="${ctxManage}/resources/img/ele/loadImg_default_d.jpg"</c:if>
								title="缩略图名称" alt="请上传图片" class="style-d">
							<input type="hidden" class="url" name="url"
								value="${bannerList[0].url}" />
						</div>
					</div>


					<div class="posidSet-unit">
						<i>图片2</i>
						<div class="left">

							<div class="formLine">
								<label>
									图片:
								</label>
								<div id="pickfiles2" class="btn-uploadImg pickfiles imgNode">
									请上传图片
								</div>
								<span class="txt-suggest ml20">推荐尺寸：800 * 360</span>
							</div>
							<div class="formLine">
								<label>
									链接至:
								</label>
								<input id="hyperlink2" name="content" type="text" maxlength=""
									value="${bannerList[1].content }" class="w-260">
							</div>
						</div>
						<div class="right mt10">
							<label>
								缩略图:
							</label>
							<img id="imgshow2"
								<c:if test="${not empty bannerList[1].url }">src="${ctx }${bannerList[1].url }"</c:if>
								<c:if test="${empty bannerList[1].url}">src="${ctxManage}/resources/img/ele/loadImg_default_d.jpg"</c:if>
								title="缩略图名称" alt="请上传图片" class="style-d">
							<input type="hidden" class="url" name="url"
								value="${bannerList[1].url}" />
						</div>
					</div>


					<div class="posidSet-unit">
						<i>图片3</i>
						<div class="left">

							<div class="formLine">
								<label>
									图片:
								</label>
								<div id="pickfiles3" class="btn-uploadImg pickfiles imgNode">
									请上传图片
								</div>
								<span class="txt-suggest ml20">推荐尺寸：800 * 360</span>
							</div>
							<div class="formLine">
								<label>
									链接至:
								</label>
								<input id="hyperlink3" name="content" type="text" maxlength=""
									value="${bannerList[2].content }" class="w-260">
							</div>
						</div>
						<div class="right mt10">
							<label>
								缩略图:
							</label>
							<img id="imgshow3"
								<c:if test="${not empty bannerList[2].url }">src="${ctx }${bannerList[2].url }"</c:if>
								<c:if test="${empty bannerList[2].url}">src="${ctxManage}/resources/img/ele/loadImg_default_d.jpg"</c:if>
								title="缩略图名称" alt="请上传图片" class="style-d">
							<input type="hidden" class="url" name="url"
								value="${bannerList[2].url}" />
						</div>
					</div>

					<!--<div class="posidSet-unit">
					<i>图片4</i>
					<div class="left">

						<div class="formLine">
							<label>图片:</label>
							<button type="button" class="btn-base uploadImg">点击上传图片</button>
						</div>
						<div class="formLine">
							<label>链接至:</label>
							<input id="" name="" type="text" maxlength="" value="" class="w-260">
							<span class="reqItem">*</span>
						</div>
					</div>
					<div class="right mt10">
						<label>缩略图:</label>
						<img src="${ctxManage}/resources/img/ele/loadImg_default_d.jpg" title="缩略图名称" alt="请上传图片" class="style-d">
					</div>
				</div>
-->
				</div>
				<!-- } 模块管理 -->

				<!-- 模块管理 { -->
				<div class="muduleManage filament_solid_ddd pos-rela">
					<h2 class="lev2">
						官方推荐
					</h2>
					<button type="button" onclick="showHot()" class="btn-base pos-abso"
						style="top: 20px; right: 30px;">
						显示最热
					</button>
					<div class="formLine mt35">
						<label>
							商户1链接:
						</label>
						<input id="commerLink1" name="commerLink"
							value="${commerList[0].hyperlink }" type="text" class="w-260">
						<label class="w-auto">
							或编号:
						</label>
						<input id="commerNum1" name="commerCode"
							value="${commerList[0].content }" type="text" class="w-200">
					</div>

					<div class="formLine">
						<label>
							商户2链接:
						</label>
						<input id="commerLink2" name="commerLink"
							value="${commerList[1].hyperlink }" type="text" class="w-260">
						<label class="w-auto">
							或编号:
						</label>
						<input id="commerNum2" name="commerCode"
							value="${commerList[1].content }" type="text" class="w-200">
					</div>

					<div class="formLine">
						<label>
							商户3链接:
						</label>
						<input id="commerLink3" name="commerLink"
							value="${commerList[2].hyperlink }" type="text" class="w-260">
						<label class="w-auto">
							或编号:
						</label>
						<input id="commerNum3" name="commerCode"
							value="${commerList[2].content }" type="text" class="w-200">
					</div>

					<!-- 操作 -->
					<div class="saveOper mt30">
						<button type="button" class="btn-sure mr30">
							预览
						</button>
						<button type="button" class="save btn-sure mr100">
							保存
						</button>
					</div>

				</div>
				<!-- } 模块管理 -->
			</form>
		</div>

		<!-- } main -->

		<!-- JS引用部分 -->
		<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
		<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/dataValidation.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/webuploader/webuploader.js"
			type="text/javascript"></script>
		<script type="text/javascript">
		
		/* “Banner广告位”链接验证 */
		$(document).on("blur", ".muduleManage:first input[type='text']", function() {
			var $this = $(this),
				thisVal = $this.val();

			// 执行验证并设置验证结果的状态
			if ($.VLDTOR.IsWebUrl(thisVal) || thisVal == "") {
				$this.next(".errMesg").remove();
				VLDRST.ToLink = true;
			} else {
				if($this.next(".errMesg").length != 0) {
					$this.next(".errMesg").remove();
				}
				creatErrMesg(this, "只能为以“http、https、ftp”开头的网址格式", "right");
				VLDRST.ToLink = false;
			}
		});
		
		/* “官方推荐”链接验证 */
		$(document).on("blur", ".muduleManage:eq(1) input[id^='commerLink']", function() {
			var $this = $(this),
				thisVal = $this.val(),
				nextTxt = $this.nextAll("input"),
				nextTxt_val = nextTxt.val();

			// 验证通过(全部)
			if ($.VLDTOR.IsWebUrl(thisVal) || thisVal == "") {
				if($.VLDTOR.IsEnNum(nextTxt_val) || nextTxt_val == "") {
					$this.nextAll(".errMesg").remove();
				}
				VLDRST.CommerLink = true;
			} 
			// 验证不通过
			else {
				if($this.nextAll(".errMesg").length != 0 && $.trim(nextTxt_val) != "") {
					$this.nextAll(".errMesg").remove();
				}
				if($this.nextAll(".errMesg").length > 0) {
					$this.nextAll(".errMesg").text("只能为以“http、https、ftp”开头的网址格式");
				} else {
					creatErrMesg($this.nextAll("input"), "只能为以“http、https、ftp”开头的网址格式", "right");
				}
				VLDRST.CommerLink = false;
			}
		});
		
		/* “官方推荐”编号验证 */
		$(document).on("blur", ".muduleManage:eq(1) input[id^='commerNum']", function() {
			var $this = $(this),
				thisVal = $this.val(),
				prevTxt = $this.prevAll("input"),
				prevTxt_val = prevTxt.val();

			// 验证通过
			if ($.VLDTOR.IsEnNum(thisVal) || thisVal == "") {
				if($.VLDTOR.IsEnNum(prevTxt_val) || prevTxt_val == "") {
					$this.next(".errMesg").remove();
				}
				VLDRST.LinkNum = true;
			} 
			// 验证不通过
			else {
				if($this.next(".errMesg").length != 0 && $.trim(prevTxt_val) != "") {
					$this.next(".errMesg").remove();
				}
				if($this.next(".errMesg").length > 0) {
					$this.next(".errMesg").text("编号只能为英文和数字");
				} else {
					creatErrMesg(this, "编号只能为英文和数字", "right");
				}
				VLDRST.LinkNum = false;
			}
		});
		
		/* 保存验证 */
		$(".save.btn-sure").click(function() {
			var bannerLink = $(".muduleManage:first input[type='text']"),
				bannerLink_len = bannerLink.length,
				commerLink = $(".muduleManage:eq(1) input[id^='commerLink']"),
				commerLink_len = commerLink.length,
				commerNum = $(".muduleManage:eq(1) input[id^='commerNum']"),
				commerNum_len = commerNum.length,
				isPass = true;
			
			// “Banner广告位”遍历验证
			for(var i = 0; i < bannerLink_len; i++) {
				bannerLink.eq(i).blur();
				if(!VLDRST.ToLink) {
					isPass = false;
				}
			}
			
			// “官方推荐”链接遍历验证
			for(var i = 0; i < commerLink_len; i++) {
				commerLink.eq(i).blur();
				if(!VLDRST.CommerLink) {
					isPass = false;
				}
			}
			
			// “官方推荐”编号遍历验证
			for(var i = 0; i < commerLink_len; i++) {
				commerNum.eq(i).blur();
				if(!VLDRST.LinkNum) {
					isPass = false;
				}
			}
			
			// 验证通过
			if (isPass) {
				msgBox("保存成功！", "pass", 2600);
				$("#commer-show-form").submit();
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

