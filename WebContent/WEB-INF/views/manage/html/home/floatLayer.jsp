<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
    <title>门户首页-浮窗管理</title>
	<%@include file="/common-html/common.jsp" %>
     <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css" />
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
       <script type="text/javascript">
       
	     function preview(){
		     var src  = $("#imgshow1").attr("src").split("${ctx }")[1];
		     $("#url").val(src);
	         $("#form").attr("action","${ctx }web/homeController/previewManageFront");
	         $("#form").attr("target","_blank");
	         $("#form").submit();
	     }
	     
	     function submit1(){
	    	  $("#form").attr("${ctx }web/homeController/saveManageImg");
		    	  $.ajax( {
		     			type : "post",
		     			url : "${ctx }web/homeController/saveManageImg",
		     			data : $("#form").serialize(),
		     			dataType : "text",
		     			async : false,
		     			success : function(data) {
		        			 //.log(data);
		        			 msgBox("保存成功","pass",1500);
		        			 window.location.href="${ctx }web/homeController/getManageImg?programaCode=13334a3a-75da-11e4-b6ce-005056a05bc9";
		     		}
		       });
	
	      }
        
     </script> 
    
</head>


<body>
    <!-- main { -->
    <div class="main">
		<form  action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data" id="form">
			<!-- 页面位置-->
			<div class="location">
				<label>您当前位置为:</label>
				<span><a>门户首页</a> -</span>
				<span><a href="#" target="_self">浮窗管理</a></span>
			</div>
			
			<!-- 查看按钮 -->
			<div class="searchManage">
			   	<input type="hidden" name="mutiCode"  value="${mutiList[0].code }"/>
                <input type="hidden" name="code" value="${imageList[0][0].code }"/>
                <input type="hidden" name="isPreview" value="1"/>
                <button type="button" class="btn-sure"  onclick="preview()">预览</button>
				<button id="save" type="button" class="save btn-sure" onclick="submit1()">保存</button>
			</div>

			<!-- 模块管理 { -->
			<div class="muduleManage filament_solid_ddd">
				
				<div class="formLine">
					<label>是否显示:</label>
					<input name="isshow" type="radio" value="1" onclick="javascript:$(this).attr('checked','checked')" class="ml10" 
					<c:if test="${empty imageList[0][0].isshow ||imageList[0][0].isshow=='1'}">checked</c:if>
					
					 />
					 
					<label  class="w_auto">是</label>
					<input  name="isshow" type="radio" onclick="javascript:$(this).attr('checked','checked')" value="0" 
					<c:if test="${imageList[0][0].isshow=='0'}">checked</c:if>
					
					class="ml30" />
					<label     class="w_auto"  >否</label>
				</div>
				<div class="formLine">
					<label>缩略图:</label>
					<div id="pickfiles" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
					<span class="txt-suggest ml20">推荐尺寸：240 * 240</span>
				</div>
				<div class="formLine">
					<label></label>
					<img id="imgshow1" style="width:240px;height:240px;"
					<c:if test="${empty imageList}">
    				 src="${ctx}//portal/static/default/square.png"
    				</c:if>
    				<c:if test="${not empty imageList}">
    				src="${ctx}/${imageList[0][0].url }"
    				</c:if>	
					title="图片名称" alt="banner图片">
				    <input type="hidden" class="url" id="url" name="url" 
				    
				    <c:if test="${empty imageList}">
    				 value="/portal/static/default/square.png"
    				</c:if>
    				<c:if test="${not empty imageList}">
    				value="${imageList[0][0].url }"
    				</c:if>	
				    
				    />
				</div>
			</div><!-- } 模块管理 -->
    	</form>
    </div><!-- } main -->

    <!-- JS引用部分 -->
   
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
    <script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
    <script src="${ctxManage}/webuploader/upload.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/dataValidation.js"></script>
	<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
	<script><%--
		$(".toLink").blur(function () {
			var thisVal = $(this).val();
			var regTest = $.VLDTOR.IsWebUrl(thisVal);
			var regReturn = valid_txtBox_create(this,regTest || thisVal == "","只能为空或以“http、https、ftp”开头的网址格式","right");
			if(regReturn){
				VLDRST.ToLink = true;
			}else{
				VLDRST.ToLink = false;
			}
		});
		$("#save").click(function () {
			$(".toLink").blur();
			if(VLDRST.ToLink){
				msgBox("保存成功","pass",1500);
			}else{
				// Do Something
				msgBox("填写的信息有误，请检查","erro",1000);
			}
		})
	--%></script>
	
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>

