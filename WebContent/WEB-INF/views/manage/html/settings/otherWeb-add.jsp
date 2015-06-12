<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge"/>
    <title>系统设置-运营管理-其他页面管理-新建页面</title>
    <%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    
    <script src="../../resources/plugin/respond.min.js" type="text/javascript"></script>
    <!-- 网页文本编辑器插件:需替换为UEeditro
	<script src="../../resources/plugin/ckeditor/ckeditor.js" type="text/javascript"></script> -->
	
</head>

<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
		<span><a>系统设置</a> -</span>
		<span><a>运营管理</a> -</span>
		<span><a href="otherWeb.html" target="_self">其他页面管理</a> -</span>
		<span><a>新建页面</a></span>
    </div>

    <!-- 模块管理 { -->
    <form id="member-add-form" action="otherWebadd" method="post">
		<div class="filament_solid_ddd pos-rela mt30">

			<div class="formLine mt20">
				<label>文章标题:</label>
				<input name="contentTitle" id="articleTitle" type="text" class="w-320" />
				<span class="reqItem">*</span>
			</div>

			<div class="formLine">
				<label class="pos_r_t5">正文:</label>
					<script id="contents" class="ueEditor_cont" name="content"  style="height:500px;" type="text/plain"></script>
			</div>
			
			<!-- SEO信息 { -->
			<div class="contClassify mt20">
				<h2 class="title">SEO信息</h2>

				<div class="formLine">
					<label class="w-auto ft-w-b ml40" style="margin-left: 40px;">&lt;Keywords&gt;标签(关键字):</label>
					<input name="keywords"type="text" maxlength="" id="keywords" class="w-320">
					<span class="txt-suggest ml5">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
				</div>
			</div><!-- } SEO信息 -->
		</div>
		
				
		<!-- 按钮 -->
		<div class="formLine mt20 mb30">
			<div class="saveOper">
				<button id="saveForm" class="btn-sure sureSave mr60" type="button">保存</button>
				<button id="preview" class="btn-sure ml20 mr200" type="button">预览</button>
			</div>
		</div>
    </form>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="../../resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="../../resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="../../resources/js/base.js" type="text/javascript"></script>
<script src="../../resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="../../resources/js/dataValidation.js" type="text/javascript"></script>

<!-- 实例化编辑器 -->
    <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.all.js"></script>
	<script type="text/javascript"> 
	    var ue = UE.getEditor('contents');
	</script>
<script type="text/javascript">
    $("#saveForm").on('click',function(){	
       $("#member-add-form").attr('target','_self');
        $("#member-add-form").attr('action','otherWebadd.html');
        
      
    });
    $("#preview").on('click',function(){
    	 $("#member-add-form").attr('target','_blank');
        $("#member-add-form").attr('action','${ctx}portal/app/setting/preother.html');
        $("#member-add-form").submit();
    });
    
    //======================================
	//				数据验证
	//======================================
	
	// 文章标题
	$("#articleTitle").blur(function() {
		var this_val = $(this).val(),
			reg_val = $.VLDTOR.IsArticle(this_val),
			val_range =  inputRange(this, 2, 30);
		// 验证信息方法
		valid_txtBox(this, reg_val && val_range, "字符长度为2-30位", "right");
	});
	
	// 关键字
	$("[name='keywords']").blur(function() {
		var this_val = $(this).val(),
			reg_val = $.VLDTOR.IsEnCnNum_comma(this_val),
			val_range =  inputRange(this, 2, 20);
		// 验证信息方法
		valid_txtBox_create(this, reg_val && val_range || this_val == "", "只能为2-20位的中、英文、数字和逗号", "top");
		if(this_val!=''){
		   var len=this_val.split(',');
		   if(len.length>5){
		      msgBox("最多录入五组关键字","erro","2600");
		   }
		}
		
	});

	// 富文本内容验证
	ue.addListener( 'contentChange', function() {
		valid_richTxt(this,"#contents",2,10000,"内容必须在2-10000个字符之间");
	});
	ue.addListener( 'focus', function() {
		valid_richTxt(this,"#contents",2,10000,"内容必须在2-10000个字符之间");
	});
	
	// 保存验证
	$(".sureSave").click(function() {
		// 失焦验证
		$("[name='keywords']").blur();

		// 富文本验证
		var ueCont = ue.getContentTxt();
		if(ueCont == "") {
			valid_txtBox_create("#contents", false, "内容必须在2-10000个字符之间", "top-right");
		}
		var this_val=$('#keywords').val();
        if(this_val!=''){
		   var len=this_val.split(',');
		   if(len.length>5){
		      msgBox("最多录入五组关键字","erro","2600");
		      return;
		   }
		}
		// 是否含有错误信息
		var hasErr = $(".errMesg").length > 0;

		// 含有验证不通过的项
		if(hasErr) {
			msgBox("您填写的信息有误，请检查！", "erro");
			return;
		}
		// 验证通过提交表单
		else {
			msgBox("新建成功！", "pass");
			$("#member-add-form").submit();
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