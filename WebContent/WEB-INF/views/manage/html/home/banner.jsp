<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
    <title>Banner管理</title>
    <%@include file="/common-html/common.jsp" %>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/banner.css" />
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
    <script type="text/javascript">
   function preview(){
    	// 是否上传图片
			var hasImage = $("#url").val();
			if(!hasImage){
				msgBox('请上传图片','erro');
				return  false;
			} 
    	 $.ajax( {
    			type : "post",
    			url : "${ctx }web/homeController/previewManageImg",
    			data : $("#form").serialize(),
    			dataType : "text",
    			async : false,
    			success : function(data) {
       			 //.log(data);
    		}
         });

     	 var  form  = $('<form style="display" target="_blank" id="open" action="${ctx}web/homeController/previewManageFront" method="post"></form>');
		$("body").append(form);
     	$("#open").submit();
    	 //window.open("${ctx }web/homeController/previewManageFront");
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
			    url:'${ctx}/web/imageController/deleteImageBymutiCode?muticode=77ffa15c-0192-4ded-91f5-9ac29a651ee4',
			    success:function(data){
			    	$.ajax( {
		     			type : "post",
		     			url : "${ctx }web/homeController/saveManageImg",
		     			data : $("#form").serialize(),
		     			dataType : "text",
		     			async : false,
		     			success : function(data) {
     	 msgBox("保存成功", "pass", 2600);
        window.location.href="${ctx }/web/homeController/getManageImg?programaCode=e43cb722-75d6-11e4-b6ce-005056a05bc9";
			     			
		     		 }
		           });
				}


			});


          
          }
      
     </script> 
    </head>
    <body>
     
                         
    <!-- main { -->
    <div class="main">
    	<!-- 页面位置-->
    	<div class="location">
    		<label>您当前位置为:</label> 
    		<span><a href="#" target="_self">Banner管理</a></span>
    	</div>
   <!-- 表单{ -->
    <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data" id="form">
    	<!-- 模块管理 { -->
    	<div class="muduleManage filament_solid_ddd">
    		<div class="searchManage">
					<button type="button" class="lookup btn-sure"  onclick="javascript:window.open('${ctx}')">查看</button>
				</div>
    		<!-- 上传图片 { -->
    		<div class="mud-upload" id="uploader">
    			<label class="lbl-base">请上传图片:</label>			
				<div id="pickfiles" class="btn-uploadImg pickfiles imgNode">请上传图片</div>  
    			<span class="txt-suggest ml120">推荐尺寸：1900 * 360</span>
    		</div>
    		<!-- } 上传图片 -->
    		
    		<!-- 缩略图 { -->
    		<div class="mud-thumbnail" id="ImageSrc">
    			<h3>缩略图:</h3>
    			
    			<img id="imgshow1"
    			
    			<c:if test="${empty imageList[0][0].url}">
    				 src="${ctx}//portal/static/default/square.png"
    			</c:if>
    			<c:if test="${not empty imageList}">
    				src="${ctx}/${imageList[0][0].url }"
    			</c:if>	
    			 title="图片名称" alt="banner图片" style="width: 1330px; height: 252px;">
    			 
    		    <input type="hidden" class="url" id="url" name="url" 
    		    
    		    
    		   <c:if test="${empty imageList[0][0]}">
    				 value="/portal/static/default/square.png"
    			</c:if>
    			<c:if test="${not empty imageList}">
    				value="${imageList[0][0].url }"
    			</c:if>	
    		    
    		    
    		    
    		     />
    		</div><!-- } 缩略图 -->
    		<!-- 按钮 { -->
    		<div class="mud-button">
    		<input type="hidden" name="mutiCode" id="muti" value="${mutiList[0].code }"/>
    		<input type="hidden" name="code" value="${imageList[0][0].code }"/>
    		<input type="hidden" name="isPreview" value="1"/>
    		   <button type="button" class="btn-sure mr30" onclick="preview()">预览</button>
    		   <button type="button" class="btn-sure"  onclick="submit1()">确定</button>
    		</div><!-- } 按钮 -->
    	</div><!-- } 模块管理 -->
       </form><!-- }表单 -->
    </div><!-- } main -->

  
     <!-- JS引用部分 -->
    
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
    <script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
    <script src="${ctxManage}/webuploader/upload.js" type="text/javascript"></script>
        <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
        <script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
	
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->


</body>
</html>
