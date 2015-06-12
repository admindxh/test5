<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
	<%@include file="/common-html/common.jsp" %>
    <title>天上社区-帖子&回复管理-已审核的帖子&回复-发布帖子页</title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/datepicker.css" />
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
				event.returnValue="帖子正在编辑中";
		  }
	</script>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <!-- 网页文本编辑器插件 -->
    <script src="${ctxManage}/resources/plugin/ckeditor/ckeditor.js" type="text/javascript"></script>
	
</head>

<body>
<!-- main { -->
<div class="main">
    <form id="creatRead" name="" action="" method="post">
        <!-- 页面位置-->
        <div class="location">
            <label>您当前位置为:</label>
            <span><a>天上社区</a> -</span>
            <span><a>帖子&回复管理</a> -</span>
            <span><a href="${ctx }/web/passPostController/searchPost" target="_self">已审核的帖子&回复</a> -</span>
            <span><a href="#" target="_self">新建帖子</a></span>
        </div>
        <!-- 数据操作 -->
        <div class="searchManage">
            <button id="preview" type="button" class="btn-sure" >预览</button>
            <button id="saveForm"  class="btn-sure"  type="button">保存</button>
        </div>
        <!-- 模块管理 { -->
        <div class="muduleManage details filament_solid_ddd">
            <div>
                <!-- 文章标题 { -->
                <div class="formLine floatfix">
                    <label>帖子标题:</label>
                    <input type="text"  name="contentTitle" class="w-260">
                    <label>发布人:</label>
                    <input type="text" value="天上西藏官方 " name="authorCode" class="w-260" readonly="readonly">
					
                    <!-- 所属类型 { -->
                    <div class="formLine mt20">
                        <label>所属板块:</label>
                        <div id="culType" class="select-base">
                             <i class="w-140">按版块筛选</i>
			                <dl>
								<c:forEach var="plate" items="${plates}">
									<dt inputValue="${plate.code}" inputName="programaCode">${plate.programaName}</dt>
						    	</c:forEach>
						   </dl>
				        <input id="programaCode" type="hidden" value="" name="programaCode"/>
                        </div>
                        <!-- 是否置顶 { -->
                        <label class="ml150">是否置顶:</label>
                        <input id="yes" type="radio"  name="isTop" value="1" class="ml50"><label for="yes" class="lbl_check">是</label>
                        <input id="no" type="radio" name="isTop" value="0" class="ml50" checked="true"><label for="no" class="lbl_check">否</label>
                    </div>

                </div>
                <!-- } 文章标题 -->

                <!-- 正文内容 { -->
                <div class="formLine ">
					<label  class="ueEditor_lbl pos_r_t5">内容:</label>
								<script id="content" class="ueEditor_cont" name="content"  style="height:500px;" type="text/plain"></script>	
                </div>
                <!-- 正文内容 } -->
                
                <!-- SEO信息 { -->
				<div>
					<h2 class="mt100">页面SEO信息</h2>
					<div class="filament_solid_ddd ov-au mt30">
						<div class="formLine mt20 mb20">
							<label class="w-180 ft-w-b ml40">&lt;Title&gt;标签:</label>
							<input type="text" maxlength="" class="w-320 seoInfo" name="tdkTitle">
						</div>
						<div class="formLine mt20 mb20">
							<label class="w-180 ft-w-b ml40">&lt;Description&gt;标签:</label>
							<input type="text" maxlength="" class="w-320 seoInfo" name="tdkDescription">
						</div>
						<div class="formLine mt20 mb20">
							<label class="w-180 ft-w-b ml40">&lt;Keywords&gt;标签(关键字):</label>
							<input type="text" maxlength="" class="w-320 seoInfo" name="tdkKeywords">
							<span class="txt-suggest ml20">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
						</div>
					</div>
				</div>
				<!-- } SEO信息 -->
                
                
            </div>

        </div>
        <!-- } 模块管理 -->
    </form>
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-form.js"></script>
<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
<script src="${ctxManage}/resources/js/activity/activity-creat.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
<script>
</script>



 	<!-- 配置文件 -->
  <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.config.js"></script>
  <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.all.js"></script>
  <!-- 实例化编辑器 -->
  <script type="text/javascript"> 
      var ue = UE.getEditor('content');
      ue.setOpt({
          wordCount:true,
          maximumWords:5000,
          wordCountMsg:ue.options.wordCountMsg || ue.getLang("wordCountMsg"),
          wordOverFlowMsg:ue.options.wordOverFlowMsg || ue.getLang("wordOverFlowMsg")
      });
     
  </script>

<script>
//		$("#saveForm").on('click', function () { //保存按钮
//			$("#creatRead").attr('action', '#');
//			//.log($("#creatRead").attr('action'))
//		});
//		$("#preview").on('click', function () { //预览按钮
//			$("#creatRead").attr('action', '#');
//		});

		//=============================================
		// 				 数据有效性验证
		//=============================================

		/* 富文本内容验证 */
		ue.addListener( 'contentChange', function() {
			valid_richTxt(this,"#content",2,5000,"内容必须在2-5000个字符之间");
			
		});
		ue.addListener( 'focus', function() {
			valid_richTxt(this,"#content",2,5000,"内容必须在2-5000个字符之间");
		});
		
		$("input[name='contentTitle']").blur(function () {
			var this_val = $(this).val();
			valid_txtBox_create(this, $.VLDTOR.IsArticle(this_val) && inputRange(this,2,30), "不能为纯空格，且长度在2-30", "top");
		});
		
		/* 发帖人 */
		$("input[name='authorCode']").blur(function () {
			var this_val = $(this).val();
			valid_txtBox_create(this, $.VLDTOR.IsArticle(this_val) && inputRange(this,2,30), "不能为纯空格，且长度在2-30", "top");
		});
		
		/* “所属板块”选择后提示消除 */
		$("#culType dt").click(function() {
			var $this = $(this),
				thisParent = $this.parents("#culType");
			thisParent.next(".errMesg").remove();
		});

		/* SEO 验证 */
		$(".seoInfo").blur(function () {
			var $this = $(this),
					thisVal = $this.val();
			valid_txtBox_create(this, ($.VLDTOR.IsEnCnNum_comma(thisVal) && inputRange(this,2,20)) || thisVal == "",'只能为空或字母、数字及中文，且长度在2-20','top');
		});
		
		
		/* 保存验证 */
		$("#saveForm").click(function() {
			$("#creatRead").attr("target","_self");
			/* 失焦验证 */
			$("input[name='contentTitle']").blur();
			$("input[name='authorCode']").blur();

			// 富文本验证
			var ueCont = ue.getContentTxt();
			ueCont  = $.trim(ueCont);
			if(ueCont == "") {
				valid_txtBox_create("#content", false, "内容必须在2-5000个字符之间", "top-right");
				return false;
			}
			var count = ue.getContentLength(true);
            if (count > 5000) {
            	msgBox("内容必须在2-5000个字符之间！", "erro");
            	return false;
            }
			/* 下拉菜单验证 */
			var slt = $("#culType.select-base"),
				slt_substr = $.trim(slt.children("i").text());
			if(slt_substr == "按版块筛选") {
				creatErrMesg(slt, "请选择所属模块", "top");
			} else {
				slt.next(".errMesg").remove();
			}

			// 检查错误项
			var erro = $("span.errMesg").length > 0;
			if(erro){
				msgBox("输入的内容有误，请检查！", "erro");
				return false;
			}else{
				ispost = true;
				save('${ctx}/web/passPostController/savePost','creatRead');
				msgBox("保存成功！", "pass");
				return true;
			}
		});
		/* 保存验证 */
		$("#preview").click(function() {
			/* 失焦验证 */
			$("input[name='contentTitle']").blur();
			$("input[name='authorCode']").blur();
			$(".seoInfo").blur();
			// 富文本验证
			var ueCont = ue.getContentTxt();
			if(ueCont == "") {
				valid_txtBox_create("#content", false, "内容必须在2-5000个字符之间", "top-right");
			}
			
			/* 下拉菜单验证 */
			var slt = $("#culType.select-base"),
				slt_substr = $.trim(slt.children("i").text());
			if(slt_substr == "按版块筛选") {
				creatErrMesg(slt, "请选择所属模块", "top");
			} else {
				slt.next(".errMesg").remove();
			}

			// 检查错误项
			var erro = $("span.errMesg").length > 0;
			if(erro){
				msgBox("输入的内容有误，请检查！", "erro");
				return false;
			}else{
				ispost = true;
				$("#creatRead").attr("target","_blank");
				save('${ctx}/web/passPostController/priewPost','creatRead');
				return true;
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
