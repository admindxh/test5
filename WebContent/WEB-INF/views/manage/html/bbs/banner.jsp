<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>天上社区-天上社区首页显示-Banner管理</title>
	<%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css" />
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    //表单顺序提交
    function submit(preview){
            //.log(preview);
        if(preview==1){
	        	  $.ajax( {
	         			type : "post",
	         			url : "${ctx }web/tssq/saveContent",
	         			data : $("#form").serialize(),
	         			dataType : "text",
	         			async : false,
	         			success : function(data) {
	            			 //.log(data);
	         		}
	          	   });	        
		  }else{
		     	  $.ajax( {
		      			type : "post",
		      			url : "${ctx }web/tssq/saveContent",
		      			data : $("#form").serialize(),
		      			dataType : "text",
		      			async : false,
		      			success : function(data) {
		         			 //.log(data);
	       				 window.open("${ctx }community/frontIndex");
		      		}
		       	   });
		       }	  
     	}
	    function previewTs(p){
	         $("#form").attr("target","_blank");
	         $("#form").attr("action","${ctx }web/tssq/saveContent?preview=1");
	    	 $("#form").submit();		
	     }
   
    </script>
</head>
<body>
<!-- main { -->
<div class="main">
        <!-- 页面位置-->
        <div class="location">
            <label>您当前位置为:</label>
            <span><a>天上社区</a> -</span>
            <span><a href="${ctx }/community/frontIndex" target="_self">天上社区首页显示</a> -</span>
            <span><a href="#" target="_self">Banner管理</a></span>
        </div>

        <!-- 模块管理 { -->
        <div class="muduleManage filament_solid_ddd">
          <form id="form" method="post" enctype="multipart/form-data">
          <input type="hidden" name="programaCode" value="${programaCode}">
            <div class="posidSet filament_solid_ccc">
                <div class="show">
                    <div class="posidSet-unit">
                        <i>图片1</i>
                        <div class="left">
                            <div class="formLine">
                                <label>图片:</label>
                                <div id="pickfiles1" class="btn-uploadImg pickfiles imgNode">请上传图片</div> 
                                <span class="txt-suggest ml20">推荐尺寸：861 * 367</span>
                            </div>
                            <div class="formLine">
                                <label>链接至:</label>
                                <input class="toLink" name="content" type="text" maxlength="" value="${contentList[0].content }">
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow1" class="tsBanner"
                            <c:if test="${not empty contentList[0].url }">src="${ctx }${contentList[0].url }"</c:if>
                            <c:if test="${empty contentList[0].url}">src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg"</c:if>
                            title="缩略图名称" alt="请上传图片" style="width: 394px;height: 168px">
                            <input type="hidden" class="url" name="url" value="${contentList[0].url}" />
                        </div>
                    </div>
                    <div class="posidSet-unit">
                        <i>图片2</i>
                        <div class="left">
                            <div class="formLine">
                                <label>图片:</label>
                                <div id="pickfiles2" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：861 * 367</span>
                            </div>
                            <div class="formLine mt10">
                                <label>链接至:</label>
                                <input class="toLink" name="content" type="text" maxlength="" value="${contentList[1].content }">
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow2" class="tsBanner"
                            <c:if test="${not empty contentList[1].url }">src="${ctx }${contentList[1].url }"</c:if>
                            <c:if test="${empty contentList[1].url}">src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg"</c:if>
                            title="缩略图名称" alt="请上传图片" style="width: 394px;height: 168px">
                            <input type="hidden" class="url" name="url" value="${contentList[1].url}" />
                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>图片3</i>
                        <div class="left">
                            <div class="formLine">
                                <label>图片:</label>
                                <div id="pickfiles3" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：861 * 367</span>
                            </div>
                            <div class="formLine mt10">
                                <label>链接至:</label>
                                <input class="toLink" name="content" type="text" maxlength="" value="${contentList[2].content }">
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow3" class="tsBanner"
                            <c:if test="${not empty contentList[2].url }">src="${ctx }${contentList[2].url }"</c:if>
                            <c:if test="${empty contentList[2].url}">src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg"</c:if>
                             title="缩略图名称" alt="请上传图片" style="width: 394px;height: 168px">
                            <input type="hidden" class="url" name="url" value="${contentList[2].url}" />
                        </div>
                    </div>

                    <div class="posidSet-unit">
                        <i>图片4</i>
                        <div class="left">
                            <div class="formLine">
                                <label>图片:</label>
                                <div id="pickfiles1" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：861 * 367</span>
                            </div>
                            <div class="formLine mt10">
                                <label>链接至:</label>
                                <input class="toLink" name="content" type="text" maxlength="" value="${contentList[3].content }">
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow4" class="tsBanner"
                            <c:if test="${not empty contentList[3].url }">src="${ctx }${contentList[3].url }"</c:if>
                            <c:if test="${empty contentList[3].url}">src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg"</c:if>
                            title="缩略图名称" alt="请上传图片" style="width: 394px;height: 168px"">
                            <input type="hidden" class="url" name="url" value="${contentList[3].url}" />
                        </div>
                    </div>
                                        <div class="posidSet-unit">
                        <i>图片5</i>
                        <div class="left">
                            <div class="formLine">
                                <label>图片:</label>
                                <div id="pickfiles1" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                                <span class="txt-suggest ml20">推荐尺寸：861 * 367</span>
                            </div>
                            <div class="formLine mt10">
                            	
                                <label>链接至:</label>
                                <input class="toLink" name="content" type="text" maxlength="" value="${contentList[4].content }">
                            </div>
                        </div>
                        <div class="right mt30">
                            <label>缩略图:</label>
                            <img id="imgshow5" class="tsBanner"
                            <c:if test="${not empty contentList[4].url }">src="${ctx }${contentList[4].url }"</c:if>
                            <c:if test="${empty contentList[4].url}">src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg"</c:if>
                            title="缩略图名称" alt="请上传图片" style="width: 394px;height: 168px">
                            <input type="hidden" class="url" name="url" value="${contentList[4].url}" />
                        </div>
                    </div>
                    <!-- 保存 -->
                    <div class="saveOper">
                        <button type="button" class="btn-sure" onclick="previewTs(0)" >预览</button>
                        <button id="save" type="button" class="save btn-sure ml20" >保存</button>
                    </div>
                </div>
            </div>
            </form>
        </div><!-- } 模块管理 -->
</div><!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
<script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
<script src="${ctxManage}/webuploader/upload.js" type="text/javascript"></script>
<script type="text/javascript">

    // 链接blur验证
    $(".toLink").blur(function() {
//        var $this = $(this),
//                thisVal = $this.val();
//        var returnVal = $.VLDTOR.IsWebUrl(thisVal);
//        valid_txtBox(this,returnVal && $.trim(thisVal) != "","只能为字母、数字及常用符号","right");
        var this_val = $(this).val(),
            isNull = this_val == "",
            reg_test = $.VLDTOR.IsHTTP(this_val);
        if(isNull || reg_test) {
            removeErrMesg(this);
        } else {
            creatErrMesg(this, "请输入以http(s)://开头的链接", "right");
        }
    });

    // 保存验证提交
    $("#save").click(function() {
		// 验证图片是否有上传
    	var inputUrl = $("input.url");
    	for(var i=0; i<inputUrl.length; i++){
			if(inputUrl.eq(i).val() == ""){
				msgBox("还有图片未上传！", "erro", 1000);
				return false;
			}
       	}
        
        // 让全部输入框执行一次失焦验证事件
        $(".toLink").blur();
        var regSpan_len = $("span.errMesg").length > 0;
        // 通过验证
        if(regSpan_len) {
            msgBox("输入的内容有误，请检查！", "erro", 2600);
        } else {
        	submit(1);
            msgBox("保存成功！", "pass", 2600);
        	//window.open("${ctx }community/frontIndex");
        }
    });
</script>

<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch"> <!-- IE10+ -->
<link rel="next"> <!-- Firefox -->
<link rel="prerender"> <!-- Chrome -->
</body>
</html>
