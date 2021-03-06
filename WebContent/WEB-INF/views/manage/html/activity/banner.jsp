<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <!-- <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=8, chrome=1" /> -->
	<meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>活动&专题-活动&专题首页显示-Banner管理</title>
	<%@ include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css" />
    <script src="${ctxManage}resources/plugin/respond.min.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<%-- <script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script> --%>
	<script src="${ctx}manage/webuploaderbase/webuploader.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}manage/webuploader/checkflash.js"></script>
	<script type="text/javascript">
		//var contextPath = "${ctx}";
		
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
	  		var options_={
				//swf :  contextPath+'/manage/webuploader/Uploader.swf',
				swf :  contextPath+'/manage/webuploaderbase/Uploader.swf',
				server : contextPath+'/web/activityBannerManageController/fileUpload',
				runtimeOrder : "flash",
				accept : {extensions : 'jpg,jpeg,bmp,png',mimeTypes : 'image/*',title : 'Images'},
				formData:{
					floderName: floderName
				},
				pick : {
					id:'#'+pickId,
					multiple:false
				},
				fileVal : 'file',
				auto : true,
				resize: false,
				fileSingleSizeLimit: fileSingleSizeLimit,
				compress: false
			};
			addEvent(new WebUploader.Uploader(options_),preId,urlHiddenId);
		}
		$(function (){
			var size = ${fn:length(listImage)};
			for (var i = 0; i < size; i++) {
				var fileSingleSizeLimit = 1 * 400 * 1024;
				var compress = {
				    width: 1000,
				    height: 360,
				    allowMagnify: true,
				    crop: true
				};
				cteateUploder("pickId"+i, "preId"+i, "urlHiddenId"+i, "banner", fileSingleSizeLimit, compress);
			}
		})
	</script>
	
</head>
<body>
<!-- main { -->
<div class="main">
    <form id="form" method="post">
    	<input type="hidden" name="previewType" value="${previewType }">
        <!-- 页面位置-->
        <div class="location">
            <label>您当前位置为:</label>
            <span><a>活动&专题</a> -</span>
            <span><a href="${ctx}web/activityBannerManageController/forActivityManage" target="_self">活动&专题首页显示</a> -</span>
            <span><a href="#" target="_self">Banner管理</a></span>
        </div>

        <!-- 模块管理 { -->
        <div class="muduleManage filament_solid_ddd">
        
        	<c:forEach var="obj" varStatus="sta" items="${listImage }">
        		<div class="posidSet-unit">
	                <i>图片${sta.index+1 }</i>
	                <div class="left perc60">
	
	                    <div class="formLine">
	                        <label>图片:</label>
	                        <%-- <button id="pickId${sta.index}" type="button" class="btn-base uploadImg">点击上传图片</button> --%>
	                        <div id="pickId${sta.index}" class="btn-base uploadImg">点击上传图片</div>
	                        <span class="txt-suggest ml20">推荐尺寸：1000 * 360</span>
	                        <input id="urlHiddenId${sta.index}" type="hidden" name="listImage[${sta.index}].url" value="${not empty obj.url?obj.url:'/portal/static/default/square.png'}">
	                        <input name="listImage[${sta.index}].id" type="hidden" value="${obj.id }">
	                        <input name="listImage[${sta.index}].code" type="hidden" value="${obj.code }">
	                        <input name="listImage[${sta.index}].mutiCode" type="hidden" value="${obj.mutiCode }">
	                        <input name="listImage[${sta.index}].name" type="hidden" value="${obj.name }">
	                        <input name="listImage[${sta.index}].summary" type="hidden" value="${obj.summary }">
	                    </div>
	                    <div class="formLine">
	                        <label>链接至:</label>
	                        <input name="listImage[${sta.index}].hyperlink" type="text" value="${obj.hyperlink }">
	                        <!-- <span class="reqItem">*</span> -->
	                    </div>
	                </div>
	                <div class="right mt10 perc40">
	                    <label>缩略图:</label>
	                    <c:if test="${obj.url==''||obj.url==null}">
		                    <img id="preId${sta.index}" src="${ctxManage}/resources/img/ele/loadImg_default_d.jpg"
                                 title="缩略图名称" alt="请上传图片" class="act-banner"
                                 style="width: 450px;height: 162px;">
	                    </c:if>
	                    <c:if test="${obj.url!=''&&obj.url!=null}">
		                    <img id="preId${sta.index}" src="${ctx}${obj.url}"
                                 title="缩略图名称" alt="请上传图片" class="act-banner"
                                 style="width: 450px;height: 162px;">
	                    </c:if>
	                </div>
	            </div>
        	</c:forEach>

            <!-- 保存 -->
            <div class="saveOper mt20">
                <button type="button" class="save btn-sure mr30 preview">预览</button>
                <button type="button" class="save btn-sure mr40">保存</button>
            </div>
        </div><!-- } 模块管理 -->
    </form>
</div><!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>

<!-- jsp -->
<script type="text/javascript">
	
	function save(){
		$("#form").attr("target","_self");
		$("#form").attr("action","${ctx}web/activityBannerManageController/editActivityBanner");
		$("#form").submit();
	}
	function preview(){
		$("#form").attr("target","_activityIndexPreview");
		$("#form").attr("action","${ctx}web/activityBannerManageController/forPreView");
		$("#form").submit();
	}
</script>
<script type="text/javascript">
	// 超链接验证
	$("[name$='hyperlink']").blur(function() {
		/* var this_val = $(this).val(),
			isNull = $.trim(this_val) == "",
			testPass = $.VLDTOR.IsHTTP(this_val);
		// 验证信息方法
		valid_txtBox(this, !testPass && !isNull, "需要填写http://开头的链接", "bottom") */
		var this_val = $(this).val(),
		isNull = $.trim(this_val) == "",
		testPass = $.VLDTOR.IsHTTP(this_val);
		// 验证不通过
		if(!testPass && !isNull) {
			creatErrMesg(this, "需要输入http(s)://开头的链接", "bottom");
		}
		// 验证通过
		else {
			// 清除错误提示
			removeErrMesg(this);
		}
	});
	
	// 提交保存验证
	function submitForm(call_){
		// 失焦验证
		$("[name$='hyperlink']").blur();
		
		// 检查错误项
		var erro = $("span.errMesg").length > 0;
		
		// 含有错误
		if(erro) {
			// 消息盒子
			msgBox("填写的信息有误，请检查","erro");
			return;
		} 
		// 通过验证
		else {
			// 提交表单
			call_();
		}
	}
	$(".save.mr40").click(function() {
		msgBox("保存成功！","pass");
		submitForm(save);
		//save();
	});
	$(".preview").click(function() {
		submitForm(preview);
		//preview();
	});
</script>

<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch"> <!-- IE10+ -->
<link rel="next"> <!-- Firefox -->
<link rel="prerender"> <!-- Chrome -->
</body>
</html>