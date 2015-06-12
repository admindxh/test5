<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
	<%@include file="/common-html/common.jsp" %>
    <title>游西藏-游西藏首页显示-Banner管理</title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/banner.css" />
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />

</head>
<body>
    <!-- main { -->
    <div class="main">
    	<!-- 页面位置-->
    	<div class="location">
    		<label>您当前位置为:</label>
			<span><a>游西藏</a> -</span>
			<span><a href="travel.html" target="_self">游西藏首页显示</a> -</span>
    		<span><a>Banner管理</a></span>
    	</div>
    	
    	<div class="operManage">
    		<button class="btn-sure" onclick="toIndex()" type="button">查看</button>
    	</div>
    	
    	<!-- 模块管理 { -->
    	<div class="muduleManage filament_solid_ddd">
    	<form  action="${ctx}web/travelController/previewBanner" method="post" enctype="multipart/form-data" id="form">
    		<!-- 上传图片 { -->
    		<div class="mud-upload">
    			<label class="lbl-base">上传图片:</label>
    			<div id="pickfiles" class="btn-uploadImg pickfiles imgNode">请上传图片</div> 
    			<span class="txt-suggest ml120">推荐尺寸：1900 * 360</span>
    		</div><!-- } 上传图片 -->
    		
    		<!-- 缩略图 { -->
    		<div class="mud-thumbnail">
    			<h3>缩略图:</h3>
    			<img id="imgshow"
    			<c:if test="${empty imageList[0][0].url}">
    				 src="${ctx}//portal/static/default/square.png"
    			</c:if>
    			<c:if test="${not empty imageList[0][0].url}">
    				src="${ctx}/${imageList[0][0].url }"
    			</c:if>	
    			
    			
    			 title="图片名称" alt="banner图片" style="width: 1260px;height: 252px;">
				<input type="hidden" id="url" class="url" name="url" 
				
				<c:if test="${empty imageList[0][0].url}">
    				 value="/portal/static/default/square.png"
    			</c:if>
    			<c:if test="${not empty imageList[0][0].url}">
    				value="${imageList[0][0].url }"
    			</c:if>
				
				
				 />    		
    		</div><!-- } 缩略图 -->
    		<input type="hidden" name="mutiCode"  value="${mutiList[0].code }"/>
    		<input type="hidden" name="code" value="${imageList[0][0].code }"/>
    		<input type="hidden" name="isPreview" value="1"/>
    		<!-- 按钮 { -->
    		<div class="mud-button">
    			<button type="button" class="btn-sure mr30" onclick="preview()">预览</button>
    			<button type="button" class="btn-sure" onclick="submit1()">确定</button>
    		</div><!-- } 按钮 -->
    		</form>
    	</div><!-- } 模块管理 -->
    </div><!-- } main -->

    <!-- JS引用部分 -->
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
        <script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
    <script src="${ctxManage}/webuploader/upload.js" type="text/javascript"></script>
	<script type="text/javascript">
		function preview(){
			var hasImage = $("#url").val();
			if(!hasImage){
				msgBox('请上传图片','erro');
				return  false;
			}
			$.ajax( {
				type : "post",
				url : "${ctx }web/travelController/previewManageImg",
				data : $("#form").serialize(),
				dataType : "text",
				async : false,
				success : function(data) {
					//.log(data);
				}
			});
			var  form  = $('<form style="display" target="_blank" id="open" action="${ctx}web/travelController/previewManageFront" method="post"></form>');
			$("body").append(form);
			$("#open").submit();
		}

		function submit1(){
			var hasImage = $("#url").val();
			if(!hasImage){
				msgBox('请上传图片','erro');
				return  false;
			}

			$.ajax({
			 	async: true,
			    type:'post',
			    url:'${ctx}/web/imageController/deleteImageBymutiCode?muticode=ac32abcc-235f-424a-9553-55ab647c86443',
			    success:function(data){
			    	$.ajax( {
						type : "post",
						url : "${ctx }web/travelController/saveManageImg",
						data : $("#form").serialize(),
						dataType : "text",
						async : false,
						success : function(data) {
							window.location.href="${ctx}//web/travelController/getManageImg?programaCode=1256se5-qe2c-52e4-a6ce-11505ca05dc9";
						}
					});
					msgBox("保存成功", "pass", 2600);
				 }
			});


				
			
			
				




			
		}

		function toIndex(){
			window.open("${ctx}tourism/strage/frontIndex");
		}
	</script>

	<!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>

