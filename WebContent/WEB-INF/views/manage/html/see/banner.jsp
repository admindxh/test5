<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>看西藏-看西藏首页显示-banner管理</title>
    <%@include file="/common-html/common.jsp" %>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css" />
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    //表单顺序提交
    function submit(preview){
            ////.log(preview);
            //保存
        if(preview==1){
	        	  $.ajax( {
	         			type : "post",
	         			url : "${ctx }web/mutiController/saveContent",
	         			data : $("#form").serialize(),
	         			dataType : "text",
	         			async : false,
					success : function(data) {
									if (data === "error") {
										msgBox("编号或链接错误!<br>请认真填写信息，谢谢合作！",
												"erro", 5000);
									} else {
										msgBox("保存成功！", "pass", 2600);
									}
								}
							});
						} else {
							//预览
							$("#form").submit();
						}
					}
				</script>
</head>
<body>
<!-- main { -->
<div class="main">
        <!-- 页面位置-->
        <div class="location">
            <label>您当前位置为:</label>
            <span><a>看西藏</a> -</span>
            <span><a href="../../manage/html/see/see.html" target="_self">看西藏首页显示</a> -</span>
            <span><a href="banner.html" target="_self">Banner管理</a></span>
        </div>
        <!-- 模块管理 { -->
        <div class="muduleManage filament_solid_ddd">
          <form id="form" action="${ctx }portal/frontMutiController/prebanner" method="post" target="new" enctype="multipart/form-data">
          <input type="hidden" name="programaCode" value="${programaCode}">
            <div class="posidSet filament_solid_ccc">
                <div class="show">
                    <div class="posidSet-unit">
                        <i>图片1</i>
                        <div class="left">
                            <div class="formLine">
                                <label>图片:</label>
                                <div id="pick1" class="btn-base btn-uploadImg pickfiles imgNode">请上传图片</div> 
                                <span class="txt-suggest ml20">推荐尺寸：555 * 300</span>
                            </div>
                            <div class="formLine mt10">
                                <label>链接至:</label>
                                <input class="toLink" name="content" type="text" maxlength="" value="${contentList[0].content }">
                                <label class="w_auto">编号:</label>
                                <input type="text" name="contentTitle" value="${contentList[0].contentTitle }" class="w-200 number"/>
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow1"
                            <c:if test="${not empty contentList[0].url }">src="${ctx }${contentList[0].url }"</c:if>
                            <c:if test="${empty contentList[0].url}">src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg"</c:if>
                            title="缩略图名称" alt="请上传图片" class="see-banner" style="width: 333px;height: 180px;">
                            <input id="inputv1" type="hidden" class="url" name="url" value="${contentList[0].url}" />
                        </div>
                    </div>
                    <div class="posidSet-unit">
                        <i>图片2</i>
                        <div class="left">
                            <div class="formLine">
                                <label>图片:</label>
                                <div id="pick2" class="btn-base btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：555 * 300</span>
                            </div>
                            <div class="formLine mt10">
                                <label>链接至:</label>
                                <input class="toLink" name="content" type="text" maxlength="" value="${contentList[1].content }">
                                <label class="w_auto">编号:</label>
                                <input type="text" name="contentTitle" value="${contentList[1].contentTitle }" class="w-200 number"/>
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow2"
                            <c:if test="${not empty contentList[1].url }">src="${ctx }${contentList[1].url }"</c:if>
                            <c:if test="${empty contentList[1].url}">src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg"</c:if>
                            title="缩略图名称" alt="请上传图片" class="see-banner" style="width: 333px;height: 180px;">
                            <input id="inputv2" type="hidden" class="url" name="url" value="${contentList[1].url}" />
                        </div>
                    </div>
                    <div class="posidSet-unit">
                        <i>图片3</i>
                        <div class="left">
                            <div class="formLine">
                                <label>图片:</label>
                                <div id="pick3" class="btn-base btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：555 * 300</span>
                            </div>
                            <div class="formLine mt10">
                                <label>链接至:</label>
                                <input class="toLink" name="content" type="text" maxlength="" value="${contentList[2].content }">
                                <label class="w_auto">编号:</label>
                                <input type="text" name="contentTitle" value="${contentList[2].contentTitle }" class="w-200 number"/>
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow3"
                            <c:if test="${not empty contentList[2].url }">src="${ctx }${contentList[2].url }"</c:if>
                            <c:if test="${empty contentList[2].url}">src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg"</c:if>
                             title="缩略图名称" alt="请上传图片" class="see-banner" style="width: 333px;height: 180px;">
                            <input id="inputv3" type="hidden" class="url" name="url" value="${contentList[2].url}" />
                        </div>
                    </div>
                    <div class="posidSet-unit">
                        <i>图片4</i>
                        <div class="left">
                            <div class="formLine">
                                <label>图片:</label>
                                <div id="pick4" class="btn-base btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：555 * 300</span>
                            </div>
                            <div class="formLine mt10">
                                <label>链接至:</label>
                                <input class="toLink" name="content" type="text" maxlength="" value="${contentList[3].content }">
                                <label class="w_auto">编号:</label>
                                <input type="text" name="contentTitle" value="${contentList[3].contentTitle }" class="w-200 number"/>
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow4" 
                            <c:if test="${not empty contentList[3].url }">src="${ctx }${contentList[3].url }"</c:if>
                            <c:if test="${empty contentList[3].url}">src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg"</c:if>
                            title="缩略图名称" alt="请上传图片" class="see-banner" style="width: 333px;height: 180px;">
                            <input id="inputv4" type="hidden" class="url" name="url" value="${contentList[3].url}" />
                        </div>
                    </div>
                    <!-- 保存 -->
                    <div class="saveOper">
                        <button id="preview" type="button" class="btn-sure"  >预览</button>
                        <button id="save" type="button" class="save btn-sure ml20" >保存</button>
                    </div>
                </div>
            </div>
            </form>
        </div><!-- } 模块管理 -->
</div><!-- } main -->
<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxManage}//webuploaderbase/webuploader.min.js"></script>
<script type="text/javascript" src="${ctxManage}//webuploader/checkflash2.js"></script>
<script type="text/javascript">
    // 保存验证提交
    $("#save").click(function() {
        // 让全部输入框执行一次失焦验证事件
        $(".toLink").blur();
        $(".number").blur();
        var regSpan_len = $("span.errMesg").length > 0;
        // 通过验证
        if(regSpan_len) {
            msgBox("输入的内容有误，请检查！", "erro", 2600);
        } else {
        	submit(1);
        	//window.open("${ctx }portal/frontMutiController/toHome");
        }
    });

    $("#preview").click(function() {
        // 让全部输入框执行一次失焦验证事件
        $(".toLink").blur();
        $(".number").blur();
        var regSpan_len = $("span.errMesg").length > 0;
        // 通过验证
        if(regSpan_len) {
            msgBox("输入的内容有误，请检查！", "erro", 2600);
        } else {
        	submit(0);
           // msgBox("保存成功！", "pass", 2600);
        }
    });
    
    
</script>
<script type="text/javascript">
function initUploader( pkid, previewimage,inputvalue)//多个参数可改为 定义一个对象 传入  	
{
	function callback(file, resp)//文件 ，服务器返回的数据 	
	{
	////.log(resp.dataList);
	var filepath=resp.dataList[0];
	$(previewimage).attr("src","${ctx}"+filepath+"?id="+Math.random());
	$(inputvalue).val(filepath);
	}
	var opt = {
		auto : true,//自动上传 ，加载完文件就上传，     
		swf : '${ctxManage}/webuploader/Uploader.swf',
		fileSingleSizeLimit:1*400*1024,
		accept:{extensions:'gif,jpg,jpeg,bmp,png'},
		formData : {
			dir : "upload/banner",
		},//上传文件时 提交的数据     
		server : '${ctxMRead}uploadimage.html',
		runtimeOrder:'flash,html5',
		pick : {
			id : pkid,
			multiple : false
		},
		resize : false
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
	initUploader("#pick1", "#imgshow1","#inputv1");
	initUploader("#pick2", "#imgshow2","#inputv2");
	initUploader("#pick3", "#imgshow3","#inputv3");
	initUploader("#pick4", "#imgshow4","#inputv4");
	}else
	{
		$(document).on("click",["#pick1","#pick2","#pick3","#pick4"],function(){
			msgBox("请安装最新的flash插件", "erro", 2000);
		});	
	}
});

$(".toLink").blur(function () {
	var thisVal = $(this).val(),
			nextVal = $(this).siblings('.number').val(),
			nextValLen = nextVal.length > 0;
	var regTest = $.VLDTOR.IsWebUrl(thisVal),
			regNull = $.trim(thisVal) != "";
			//获取 code
			var  ct  = thisVal.split('.html');
			var r  = ct.length;
	            ct  = ct[0];
			var indexct  = ct.lastIndexOf('/')+1;
	             ct  = ct.substr(indexct);
			thisValClip = ct;
	if(nextValLen){
		valid_txtBox_create(this,regTest,'不能为中文或空格','top');
		if(regTest && regNull){
			if(r==2){
		         $(this).siblings('.number').val(thisValClip);
			}
		}
	}else{
		valid_txtBox_create(this,regTest && regNull,'不能为中文或空格,且与编号必填','top');
		if(regTest && regNull){
			$(this).siblings('.number').next('span.errMesg').remove();
			if(r==2){
			  $(this).siblings('.number').val(thisValClip);
			}
		}
	}
});
$(".number").blur(function () {
	var thisVal = $(this).val(),
			prevVal = $(this).siblings('.toLink').val(),
			preValLen = prevVal.length > 0;
	var regTest = $.VLDTOR.IsEnNum(thisVal),
			regNull = $.trim(thisVal) != "";
	if(prevVal){
		valid_txtBox_create(this,(regTest && inputRange(this,1,30)) || thisVal == "", '只能为空或数字及字母，且长度在1-30','top');
	}if(!thisVal){
		valid_txtBox_create(this,regTest && inputRange(this,1,30) && regNull,'只能为1-30位的数字及字母,且与链接必填','top');
		if(regTest && regNull){
			$(this).siblings('.toLink').next('span.errMesg').remove();
		}
	}
}); 
function loadDefault(){
	  $.ajax( {
			type : "post",
			url : "${ctx }web/mutiController/default",
			data : $("#form").serialize(),
			dataType : "text",
			async : false,
			success : function(data) {
  		if(data==="error")
  		{
  			msgBox("编号或链接错误!<br>请认真填写信息，谢谢合作！", "erro", 5000);	
  		}else
  		{
  			 msgBox("保存成功！", "pass", 2600);	
  		}
		}
	   });	      
	
}

</script>
<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch"> <!-- IE10+ -->
<link rel="next"> <!-- Firefox -->
<link rel="prerender"> <!-- Chrome -->
</body>
</html>
