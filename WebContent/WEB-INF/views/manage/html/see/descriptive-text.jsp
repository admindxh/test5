<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>看西藏-看西藏首页显示-图说西藏显示管理</title>
	<%@include file="/common-html/common.jsp" %>
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    //表单顺序提交
    function submit(preview){
            ////.log(preview);
        if(preview==1){
	        	  $.ajax( {
	         			type : "post",
	         			url : "${ctx }web/mutiController/saveShowMuti",
	         			data : $("#form").serialize(),
	         			dataType : "text",
	         			async : false,
	         			success : function(data) {
	            			 ////.log(data);
	         		}
	          	   });	        
		  }else{
		     	 /* $.ajax( {
		      			type : "post",
		      			url : "${ctx }web/mutiController/previewShowmuti",
		      			data : $("#form").serialize(),
		      			dataType : "text",
		      			async : false,
		      			success : function(data) {
		         			 ////.log(data);
	       				window.open("${ctx }web/homeController/previewManageFront");
		      		}
		       	   });*/
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
        <span><a href="${ctx}manage/html/see/see.html" target="_self">看西藏首页显示</a> -</span>
        <span><a href="#" target="_self">图说西藏显示管理</a></span>
    </div>
    <!-- 模块管理 { -->
    <div class="muduleManage filament_solid_ddd">

        <!-- 管理按钮 -->
        <div class="searchManage">
            <button type="button" id="shownew" class="btn-sure">显示最新</button>
            <button type="button" id="showhot" class="btn-sure">显示最热</button>
            <button type="button" id="showindex" class="btn-sure">查看</button>
        </div>
       <form method="post" action="${ctx }portal/frontMutiController/predistext" id="form" target="new">
       <input id="programaCode" name="programaCode" type="hidden" value="${programaCode }">
       <input id="pcode" name="pcode" type="hidden" value="14dba551-cb5b-4631-b5ef-b3838670b3a9"/>
        <!-- 图说西藏1 { -->
        <div class="contClassify">
            <h2 class="title">图说西藏1</h2>
            <div id="muti1" class="formLine mt15">
                <label>链接:</label>
                <input name="url" type="text" value="${contentList[0].url }" class="w-320 toLink">
                <label  class="w-auto">或编号:</label>
                <input name="content" type="text" value="${contentList[0].content}"  class="w-200 number">
            </div>
        </div>
        <!-- 图说西藏2 { -->
        <div class="contClassify">
            <h2 class="title">图说西藏2</h2>

            <div id="muti2" class="formLine mt15">
                <label>链接:</label>
                <input name="url" type="text" value="${contentList[1].url }" class="w-320 toLink">
                <label  class="w-auto">或编号:</label>
                <input name="content" type="text" value="${contentList[1].content}"  class="w-200 number">
            </div>
        </div>

        <!-- 图说西藏3 { -->
        <div class="contClassify">
            <h2 class="title">图说西藏3</h2>

            <div id="muti3" class="formLine mt15">
                <label>链接:</label>
                <input name="url" type="text" value="${contentList[2].url }" class="w-320 toLink">
                <label  class="w-auto">或编号:</label>
                <input name="content" type="text" value="${contentList[2].content}"  class="w-200 number">
            </div>
        </div>

        <!-- 保存 -->
        <div class="formLine">
            <div class="saveOper">
                <button id="preview" type="button" class="btn-sure mr30">预览</button>
                <button id="save" type="button" class="save btn-sure mr40">保存</button>
            </div>
        </div>
    </form>
    </div>
    <!-- } 模块管理 -->
</div>
<!-- } main -->
 
<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
<script type="text/javascript">

	//点击查看最新
	$("#shownew").click(function(){
	         ////.log("in shownew");
	         var _pcode = $("#pcode").val();
	     	  $.ajax( {
      			type : "post",
      			url : "${ctx }web/mutiController/shownew",
      			dataType : "json",
      			data : { programaCode:_pcode },
      			async : false,
      			success : function(json) {
	      			//先把当前div的内容清空
         			for(var i=1;i<4;i++){
						$("#muti"+i).html("");
         			}
         			//再重新添加内容
         			var list = json.data;
         			////.log(list);
         			for(var i=1;i<4;i++){
						$("#muti"+i).append(
			                "<label>链接:</label>"+
			                "<input name='url' type='text' value='"+list[i-1].hyperlink+"' class='w-320 toLink'>"+
			                "<label  class='w-auto'>或编号:</label>"+
			                "<input name='content' type='text' value='"+list[i-1].code+"'  class='w-200 number'>"
							);
         			}
	      		}
	       	   });
	         });
	//点击查看最热按钮
	$("#showhot").click(function(){
	        	////.log("in showhot");
	            var _pcode = $("#pcode").val();
		     	  $.ajax( {
	      			type : "post",
	      			url : "${ctx }web/mutiController/showhot",
	      			dataType : "json",
	      			data : { programaCode:_pcode },
	      			async : false,
	      			success : function(json) {
	   	      			if(json.msg=="1"){
						alert("数据量不足!");	
	   	   	      	 	}else{
	   		      			//先把当前div的内容清空
	   	         			for(var i=1;i<4;i++){
	   							$("#muti"+i).html("");
	   	         			}
	   	         			//再重新添加内容
	   	         			var list = json.data;
	   	         			for(var i=1;i<4;i++){
	   							$("#muti"+i).append(
	   				                "<label>链接:</label>"+
	   				                "<input name='url' type='text' value='"+list[i-1].hyperlink+"' class='w-320 toLink'>"+
	   				                "<label  class='w-auto'>或编号:</label>"+
	   				                "<input name='content' type='text' value='"+list[i-1].code+"'  class='w-200 number'>"
	   								);
	   	         			}
	   	   	      	 	}
		      		}
		       	   });
	        });
	  //点击跳到主页
	$("#showindex").click(
	   function(){
		   window.open("${ctx }portal/frontMutiController/toHome");
	       }
		    );
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
            msgBox("保存成功！", "pass", 2600);
         //	   window.open("${ctx }portal/frontMutiController/toHome");
        }
    });
    // 预览验证提交
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
          //  msgBox("保存成功！", "pass", 2600);
        }
    });
   
    
    
    $(".toLink").blur(function () {
    	var thisVal = $(this).val(),
    			nextVal = $(this).siblings('.number').val(),
    			nextValLen = nextVal.length > 0;
    	var regTest = $.VLDTOR.IsWebUrl(thisVal),
    			regNull = $.trim(thisVal) != "",
    			thisValClip = thisVal.split('code=')[1];
    	if(nextValLen){
    		valid_txtBox_create(this,regTest,'不能为中文或空格','top');
    		if(regTest && regNull){
    		//$(this).siblings('.number').val(thisValClip);
    		}
    	}else{
    		valid_txtBox_create(this,regTest && regNull,'不能为中文或空格,且与编号必填一项','top');
    		if(regTest && regNull){
    			$(this).siblings('.number').next('span.errMesg').remove();
    			//$(this).siblings('.number').val(thisValClip);
    		}
    	}
    });
    $(".number").blur(function () {
    	var thisVal = $(this).val(),
    			prevVal = $(this).siblings('.toLink').val(),
    			preValLen = prevVal.length > 0;
    	var regTest = $.VLDTOR.IsEnNum(thisVal),
    			regNull = $.trim(thisVal) != "";
    	if(preValLen){
			valid_txtBox_create(this,(regTest && inputRange(this,1,30)) || thisVal == "", '只能为空或数字及字母，且长度在1-30','top');
		}else{
			valid_txtBox_create(this,regTest && inputRange(this,1,30) && regNull,'只能为1-30位的数字及字母,且与链接必填一项','top');
			if(regTest && regNull){
				$(this).siblings('.toLink').next('span.errMesg').remove();
			}
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

