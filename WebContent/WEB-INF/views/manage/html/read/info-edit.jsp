<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
<%@include file="/common-html/common.jsp" %>
    <title>读西藏-读西藏信息管理-编辑读西藏信息</title>
    <script src="../../resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="../../resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="../../resources/css/base.css" />
    <link rel="stylesheet" href="../../resources/css/travel/formWeb.css" />
    <link rel="stylesheet" href="../../resources/plugin/datepicker.css" />
    <style>
        .mt-40{
            margin-top: -40px;
        }
        .floatfix:after{
            content:"";
            display:table;
            clear:both;
        }
    </style>
           <script type="text/javascript">
    var ispost=false;
		window.onbeforeunload= function(){
			 if(ispost)return;
				event.returnValue="正在编辑中";
		  }
	</script>
    <!-- 网页文本编辑器插件 -->
    <script src="../../resources/plugin/ckeditor/ckeditor.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxManage}//webuploaderbase/webuploader.min.js"></script>
<script type="text/javascript" src="${ctxManage}//webuploader/checkflash2.js"></script>
</head>
<body>
<!-- main { -->
<div class="main">
     <form id="creatRead" name="" method="post">
        <!-- 页面位置-->
        <div class="location">
            <label>您当前位置为:</label>
            <span><a>读西藏</a> -</span>
            <span><a href="infoManage.html" target="_self">读西藏信息管理</a> -</span>
            <span><a href="#" target="_self">编辑读西藏信息</a></span>
        </div>
		<!-- 隐形数据 -->
		<input name="dates" type="hidden" value="${culture.createTime }">
       	<input name="authorCode" type="hidden" value="${culture.authorCode }">
       	<input name="comment" type="hidden" value="${culture.others.comment}">
		<input name="rlcomment" type="hidden" value="${culture.others.rlcomment}">
		<input name="rlpraise" type="hidden" value="${culture.others.rlpraise}">
        <input name="rlview" type="hidden" value="${culture.others.rlview}">
        <input name="rlfavorite" type="hidden" value="${culture.others.rlfavorite}">
        <input name="type" value="info" type="hidden">
        <!-- 数据操作 -->
        <div class="searchManage">
            <button id="preview" type="button" class="btn-sure">预览</button>
            <button id="saveForm" type="button" class="btn-sure">保存</button>
        </div>
        <!-- 模块管理 { -->
        <div class="muduleManage details filament_solid_ddd">
            <div>
                <div class="formLine floatfix">
                    <div>
                    	<input type="hidden" name="code"   value="${culture.code}">
                        <label>编号:</label><span class="ml10">${culture.code}</span>
                    </div>
                    <div class="mt20">
                        <label>查看:</label>
                        <input name="view"  type="text" maxlength="7" value="${culture.others.view}" class="w-100 number">
                        <button class="btn-base" onclick="setDefault(this,${culture.others.rlview})"  type="button">恢复系统值</button>
                        <label class="ml30">收藏:</label>
                        <input name="favorite" type="text" value="${culture.others.favorite}" maxlength="7" class="w-100 number">
                        <button class="btn-base" onclick="setDefault(this,${culture.others.rlfavorite})"  type="button">恢复系统值</button>
                        <label>赞:</label>
                        <input name="praise" type="text" value="${culture.others.praise}" maxlength="7" class="w-100 number">
                        <button class="btn-base" onclick="setDefault(this,${culture.others.rlpraise})" type="button">恢复系统值</button>
                    </div>
                    <!-- 文章标题 { -->
                    <div class="mt20">
                        <label>文章标题:</label>
                        <input type="text" maxlength="30"  name="contentTitle" value="${culture.contentTitle}" class="w-260 article-title">
                        <label>序号:</label>
                        <input type="text"maxlength="7" name="sortNum" value="${culture.sortNum}" class="w-200 number ">
                        <!-- 是否推荐 { -->
                        <label class="ml30">是否推荐:</label>
                        <!-- 判断值显示 -->
                        <c:if test="${culture.isOfficial=='1'}" var="rs" scope="request"> 
                        <input id="yes" value="0" type="radio" name="isOfficial" class="ml50"  ><label for="yes" class="lbl_check">是</label>
                        <input id="no" value="1" type="radio" name="isOfficial" class="ml50" checked="true"  ><label for="no" class="lbl_check">否</label>
                    	</c:if>
                    	 <c:if test="${culture.isOfficial!='1'}" var="rs" scope="request"> 
                        <input id="yes" value="0" type="radio" name="isOfficial" class="ml50"  checked="true" ><label for="yes" class="lbl_check">是</label>
                        <input id="no" value="1" type="radio" name="isOfficial" class="ml50"  ><label for="no" class="lbl_check">否</label>
                    	</c:if>
                    </div>

                    <!-- 所属类型 { -->
                    <div class="mt20">
                        <label>所属类型：</label>
                        <div class="select-base">
                            <input name="contentType" type="hidden" value="${culture.contentType }">
                            <i class="w-140">西藏动态</i>
                            <dl>
                                <dt name="1120">西藏动态</dt>
                                <dt name="1010">节日</dt>
                                <dt name="1020">历史</dt>
                                <dt name="1030">风俗</dt>
                                <dt name="1040">宗教</dt>
                                <dt name="1050">美食</dt>
                                <dt name="1060">动植物</dt>
                                <dt name="1070">名人</dt>
                                <dt name="1080">服饰</dt>
                                <dt name="1090">艺术</dt>
                                <dt name="1100">特产</dt>
                                <dt name="1110">地理</dt>
                            </dl>
                        </div>
                    </div>

                    <!-- 图片上传 { -->
                    <div class="float_l mt20 w45p">
                        <label>标题缩略图</label>
                        <input id="imgurl" type="hidden" name="title" value="${culture.title}">
                        <div id="btnuploadfile"  onclick="checkUploader()"  class="btn-base uploadImg" type="button">点击上传图片</div>
                        <span class="txt-suggest ml20">推荐上传大小510*330</span>
                    </div>
                    <!-- 图片缩略图 { -->
                    <div class="w50p mr40 mt-40 float_r">
                        <img id="previewimage" style="width:220px;height:140px;" src="../../../${culture.title}" title="缩略图名称" alt="请上传图片" class="style-b">
                    </div>
                </div>
                <!-- 正文内容 { -->
                <div class="formLine ">
                    <label class="pos_r_t5">正文:</label>
                    <script id="content" class="ueEditor_cont" name="content"  style="height:500px;" type="text/plain">${culture.content}</script>
                </div>
            </div>
        </div>
        <!-- } 模块管理 -->
        <!-- SEO信息 { -->
		<div class="contClassify">
			<h2 class="title">页面SEO信息</h2>
			<div class="filament_solid_ddd ov-au mt30">
				<div class="formLine mt20 mb20">
					<label class="w-180 ft-w-b ml40">&lt;Title&gt;标签:</label>
					<input type="text" maxlength="" class="w-320 seoInfo" name="tdkTitle" value="${culture.tdkTitle }">
				</div>
				<div class="formLine mt20 mb20">
					<label class="w-180 ft-w-b ml40">&lt;Description&gt;标签:</label>
					<input type="text" maxlength="" class="w-320 seoInfo" name="tdkDescription" value="${culture.tdkDescription }">
				</div>
				<div class="formLine mt20 mb20">
					<label class="w-180 ft-w-b ml40">&lt;Keywords&gt;标签(关键字):</label>
					<input type="text" maxlength="" class="w-320 seoInfo" name="tdkKeywords" value="${culture.tdkKeywords }">
					<span class="txt-suggest ml20">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
				</div>
			</div>
		</div>
		<!-- } SEO信息 -->
    </form>
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="../../resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="../../resources/js/base.js" type="text/javascript"></script>
<script src="../../resources/plugin/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="../../resources/js/base-form.js"></script>
<script src="../../resources/js/activity/activity-creat.js" type="text/javascript"></script>
<script>
  function initUploader(param1, param2, pkid, posid)//多个参数可改为 定义一个对象 传入  	
		{
			function callback(file, resp)//文件 ，服务器返回的数据 	
			{
			this.reset();
			////.log(resp);
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
					dir : 'upload/image',
					username : param2
				},//上传文件时 提交的数据     
				server : '${ctxMRead}uploadimage.html',
				runtimeOrder:'flash,html5',
				pick : {
					id : pkid,
					multiple : false
				},
				compress:null//{ width: 452,height: 292, quality: 80,allowMagnify: true, crop: true,preserveHeaders: true,noCompressIfLarger: true, compressSize: 2*1024*1024}
				
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
	function setDefault(a,number)
	{
		if($(a).prev('.errMesg')){
			$(a).prev().prev().val(number);
			setTimeout(function(){
				$(a).prev('.errMesg').remove();	
			},200);
		}
		if($(a).prev('.number')){
			$(a).prev().val(number);	
		}
	};
</script>
<!-- 配置文件 -->
	  <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.config.js"></script>
	  <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.all.js"></script>
	  <!-- 实例化编辑器 -->
	  <script type="text/javascript"> 
	      var ue = UE.getEditor('content',{
              autoHeightEnabled: false,
              initialFrameHeight: 350
          });
	  </script>
	  <script src="../../resources/plugin/jquery.rimi.validation.js"></script>
	<script src="../../resources/js/dataValidation.js"></script>
<script>
// 富文本内容验证
ue.addListener( 'contentChange', function() {
    valid_richTxt(this,"#content",2,20000,"内容必须在2-2w个字符之间");
});
ue.addListener( 'focus', function() {
    valid_richTxt(this,"#content",2,20000,"内容必须在2-2w个字符之间");
});
$(".article-title").blur(function () {
    var $this = $(this);
    var thisVal = $this.val();
    var regTest = $.VLDTOR.IsArticle(thisVal);
    valid_txtBox_create(this,regTest && inputRange(this,2,30),'字符长度应在2-30内','top');
});

$(".seoInfo").blur(function () {
    var $this = $(this),
            thisVal = $this.val(),
            regTest = $.VLDTOR.IsEnCnNum_comma(thisVal);
    valid_txtBox_create(this,(regTest && inputRange(this,2,30)) || thisVal == "","只能为空或字母、数字及中文，且长度在2-30","top");
});
$(".number").blur(function(){
    var $this = $(this);
    var thisVal = $this.val();
    var regTest = $.VLDTOR.IsNum(thisVal);
    valid_txtBox_create(this, $.VLDTOR.IsNum(thisVal) || thisVal == "","只能为空或正整数","top");
    if(thisVal == ""){
		$this.val('0');    	
    }
});
    $("#saveForm").on('click',function(){               //保存按钮
    	 $(".article-title").blur();
         $(".number").blur();
         $(".seoInfo").blur();
         var regErrMesg = $(".errMesg").length > 0;
         var ueCont = ue.getContentTxt();
         if(ueCont == "") {
             valid_txtBox_create("#content", false, "内容必须在2-2w个字符之间", "top-right");
         }
         if(regErrMesg){
             msgBox("输入信息有误，请检查！","erro",1000);
         }else{
		ispost=true;        	 
        	 $("#creatRead").attr('target','_self');
             $("#creatRead").attr('action','infoupdate.html');
             ////.log($("#creatRead").attr('action'));
             $('#creatRead').submit();
         }
    });
    $("#preview").on('click',function(){                //预览按钮
    	 $(".article-title").blur();
         $(".number").blur();
         $(".seoInfo").blur();
         var regErrMesg = $(".errMesg").length > 0;
         var ueCont = ue.getContentTxt();
         if(ueCont == "") {
             valid_txtBox_create("#content", false, "内容必须在2-2w个字符之间", "top-right");
         }
         if(regErrMesg){
             msgBox("输入信息有误，请检查！","erro",1000);
         }else{
	    	 $("#creatRead").attr('target','_blank');
	         $("#creatRead").attr('action','${ctx}portal/app/culture/preview.html');
	         $('#creatRead').submit();
        }
    });
</script>
<script>
$(function(){
	var $select = $('.select-base'), $val = $select.find('input[name="contentType"]').val(),
		$text = $select.find('dt[name="' + $val + '"]').text()
		$select.find('i').text($text)
})

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