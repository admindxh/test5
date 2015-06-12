<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML >
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <%@include file="/common-html/common.jsp" %>
    <title>读西藏首页-Banner管理</title>
    <link rel="stylesheet" href="../../resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="../../resources/css/base.css" />
    <link rel="stylesheet" href="../../resources/css/read/banner.css" />
    <script src="../../resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctxManage}//webuploaderbase/webuploader.min.js"></script>
<script type="text/javascript" src="${ctxManage}//webuploader/checkflash2.js"></script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
   <form id="creatRead" name="" method="post">
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>读西藏</a> -</span>
     	<span><a href="../read/read.html" target="_self">读西藏首页显示</a> -</span>
        <span><a href="#" target="_self">Banner管理</a></span>
    </div>
    <!-- 模块管理 { -->
    <div class="muduleManage filament_solid_ddd">
        <!-- 上传图片 { -->
        <div class="mud-upload">
            <label class="lbl-base">上传图片:</label>
            <div id="btnuploadfile" type="button" class="btn-base btn-uploadImg">点击上传</div>
            <span class="txt-suggest ml120">推荐尺寸：1872 * 358</span>
            <a href="${ctx}portal/app/culture.html" class="btn-anchor float_r" target="new">查看</a>
        </div><!-- } 上传图片 -->
        <!-- 缩略图 { -->
		<input id="imgurl" type="hidden" name="path" value="${programa.imageUrl}" />
        <div class="mud-thumbnail">
            <h3>缩略图:</h3>
			<c:if test="${not empty programa.imageUrl}">
            <img id="previewimage" style="width: 1123px;height: 214px;" src="${ctx}${programa.imageUrl}" title="banner图片" alt="banner图片">
			</c:if>
			<c:if test="${empty programa.imageUrl}">
            <img id="previewimage" style="width: 1123px;height: 214px;" src="../../resources/img/ele/loadImg_default_a.jpg" title="banner图片" alt="banner图片">
			</c:if>
        </div><!-- } 缩略图 -->

        <!-- 按钮 { -->
        <div class="mud-button">
            <button  id="preview" type="submit" class="btn-sure mr30">预览</button>
            <button id="saveForm"  type="submit" class="btn-sure">保存</button>
        </div><!-- } 按钮 -->
    </div><!-- } 模块管理 -->
    </form>
</div><!-- } main -->

<!-- JS引用部分 -->

<script src="../../resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="../../resources/js/base.js" type="text/javascript"></script>
<script type="text/javascript">

var rs='${rs}';
if(rs=="ok")
{
	  msgBox("保存成功！", "pass", 2600);
	  window.open("${ctx }portal/app/culture.html", '_blank');
}
    $("#saveForm").on('click',function(){
       $("#creatRead").attr('target','_self');
        $("#creatRead").attr('action','banner.html');
        ////.log($("#creatRead").attr('action'))
    });
    $("#preview").on('click',function(){
    	 $("#creatRead").attr('target','_blank');
        $("#creatRead").attr('action','${ctxApp}culture.html');
    });
    function initUploader(param1, param2, pkid, posid)//多个参数可改为 定义一个对象 传入  	
		{
			function callback(file, resp)//文件 ，服务器返回的数据 	
			{
			////.log(resp.dataList);
			var filepath=resp.dataList[0];
			$("#previewimage").attr("src","../../../"+filepath+"?id="+Math.random());
			$("#imgurl").val(filepath);
			}
			var opt = {
				auto : true,//自动上传 ，加载完文件就上传，     
				swf : '../../webuploader/Uploader.swf',
				fileSingleSizeLimit:1*400*1024,
				accept:{extensions:'gif,jpg,jpeg,bmp,png'},
				formData : {
					dir : "upload/banner",
					username : param2
				},//上传文件时 提交的数据     
				server : '${ctxMRead}uploadimage.html',
				runtimeOrder:'flash,html5',
				pick : {
					id : pkid,
					multiple : false
				},
				compress:null//{ width: 1433,height: 768, quality: 80,allowMagnify: true, crop: true,preserveHeaders: true,noCompressIfLarger: true, compressSize: 2*1024*1024}
				
			};
			uploader = new WebUploader.Uploader(opt);
			uploader.on('uploadSuccess', callback);//成功的回调函数   
			uploader.on('error', function(type) {//出错的回调函数  
				uploader.reset(); 
				if(type=="Q_EXCEED_SIZE_LIMIT" || type=="F_EXCEED_SIZE"){
					msgBox("文件超过大小,请将上传文件大小控制在400kb以内", "erro");
				}else if(type=="Q_TYPE_DENIED"){
					msgBox("上传格式错误", "info", 1200);
	  			}else{
	  				msgBox("上传错误，请重试。", "info", 1200);
	  			}
			});
		}
    $(function(){
    	if(!checkFlash())
    	{
			initUploader("文件1", "用户1", "#btnuploadfile", "#pos1");
    	}else
    	{
    		$("#btnuploadfile").on("click",function(){
    			msgBox("请安装最新的flash插件", "erro", 2000);
    		});	
    	}
    });
</script>
<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch"> <!-- IE10+ -->
<link rel="next"> <!-- Firefox -->
<link rel="prerender"> <!-- Chrome -->
</body>
</html>