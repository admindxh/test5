<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE HTML>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
<%@include file="/common-html/common.jsp" %>
    <title>游西藏-目的地管理-目的地信息管理-目的地添加</title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
    <script type="text/javascript">
       function submit(){
         $("#form").submit();
         window.location.href="${ctx}/web/destinationController/getDestinationPager";

       }
    
    </script>
</head>
<body>
		<!-- main { -->
		<div class="main">
		    <!-- 页面位置-->
		    <div class="location">
		        <label>您当前位置为:</label>
		        <span><a>游西藏</a> -</span>
		        <span><a>目的地管理</a> -</span>
		        <span><a href="${ctx}/web/destinationController/getDestinationPager" target="_self">目的地信息管理</a> -</span>
		        <span><a href="${ctx}/web/destinationController/destinationEdit" target="_self">目的地添加</a></span>
		    </div>
		
		    <!-- 模块管理 { -->
		        <form method="post" action="${ctx}/web/destinationController/saveDestination" id="form">
		            <!-- 基本信息 { -->
		            <div>
		                <h2 class="mt40">目的地基本信息:</h2>
		
		                <div class="filament_solid_ddd ov-au mt30">
		
		                    <div class="formLine mt20">
		                        <label class="w-140">目的地名称:</label>
		                        <input id="destName" name="destinationName" type="text" class="w-260"/>
		                        <span class="reqItem">*</span>
		                    </div>
		
		                    <div class="formLine">
								<label class="w-140">目的地主图:</label>
								<div id="pickfiles" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
								<span class="txt-suggest ml20">必须是1900 * 360尺寸的jpg/png格式</span>
		                    </div>
			                <div class="formLine">
								<label class="w-140" style="vertical-align: top;">缩略图:</label>
								 <img id="imgshow" class="desImg" style="width:1200px;height:360px;" src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg" alt=""/>
								 <input type="hidden" class="url" name="destinationImage">
							</div>		                    
		
		                    <div class="formLine mt20 mb20">
		                        <label class="w-140 float_l">目的地简介:</label>
		                        <textarea id="destIntro" name="destinationIntroduce" id="" cols="70" rows="10" class="ml10 pl5"></textarea>
		                        <span class="reqItem">*</span>
		                    </div>
		                </div>
		            </div>
		            <!-- } 基本信息 -->

		            <!-- 机票预订信息 { -->
		            <div>
		                <h2 class="mt100">目的地机票预订信息-默认到达城市:</h2>
		
		                <div class="filament_solid_ddd ov-au mt30">
		                    <div class="formLine mt20 mb20">
		                        <input id="las" type="radio" name="jp" class="ml50 mr10"  value="0"/><label for="las" class="w-auto lbl_check">拉萨贡嘎机场</label>
		                        <input id="linz" type="radio" name="jp" class="ml50 mr10" value="1"/><label for="linz" class="w-auto lbl_check">林芝米林机场</label>
		                        <input id="changd" type="radio" name="jp" class="ml50 mr10" value="2"/><label for="changd" class="w-auto lbl_check">昌都邦达机场</label>
		                        <input id="ali" type="radio" name="jp" class="ml50 mr10" value="3"/><label for="ali" class="w-auto lbl_check">阿里昆莎机场</label>
		                        <input id="rikz" type="radio" name="jp" class="ml50 mr10" value="4"/><label for="rikz" class="w-auto lbl_check">日喀则和平机场</label>	
		                    </div>
		                </div>
		            </div>
		            <!-- } 机票预订信息 -->
		
		            <!-- SEO信息 { -->
		            <div>
		                <h2 class="mt100">页面SEO信息</h2>
		
		                <div class="filament_solid_ddd ov-au mt30">
		                    <div class="formLine mt20 mb20">
		                        <label class="w-160">&lt;Keywords&gt;标签:</label>
		                        <input id="keyWord" type="text" name="keyword" maxlength="20" class="w-320">
		                    </div>
		                </div>
		            </div>
		            <!-- } SEO信息 -->
		
		            <div class="txt-ac mt40">
		                <button class="btn-sure" type="button">保存</button>
		            </div>
		        </form><!-- } 模块管理 -->
		</div><!-- } main -->
		
		<!-- JS引用部分 -->
		<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
		<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
		<script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
        <script src="${ctxManage}/webuploader/upload.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
	<script type="text/javascript">
    /* 目的地名称验证 */
    $("#destName").blur(function () {
        var $this = $(this),
       			thisNext = $this.next(),
                thisVal = $this.val();
     // 执行验证并设置验证结果的状态
		if($.VLDTOR.IsArticle(thisVal) && inputRange(this,2,30)){
			thisNext.removeClass('errMesg').text('*');
			//验证目的地是否重复
    		$.ajax({
    			type:'post',
    			data:{desName:thisVal},
    			url:'${ctx}/web/destinationController/validDesRt',
    			success:function(data){
					if(data=="true"){
						valid_txtBox("#destName", false, "目的地重复", "right")	
						VLDRST.NameType  = false;							
					}else{
						valid_txtBox("#destName", true, "", "right")	
						VLDRST.NameType  = true;
					}
				}
   			});
		}else{
			thisNext.addClass('errMesg').text("名称只能为中英文和数字，且长度在2-30");
			VLDRST.NameType = false;
		}
    });

    /* 目的地介绍验证 */
    $("#destIntro").blur(function () {
        var $this = $(this),
                thisVal = $this.val();

        // 执行验证并设置验证结果的状态
        if (valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,800), "字母、汉字、常见标点符号,且长度在2-800", "right")) {
            VLDRST.NonNull = true;
        } else {
            VLDRST.NonNull = false;
        }
        
       
       
		

        
    });
    $("#keyWord").blur(function () {
        var $this = $(this),
                thisVal = $this.val();
        if(valid_txtBox_create(this, ($.VLDTOR.IsEnCnNum_comma(thisVal) && inputRange(this,2,20)) || thisVal == "",'只能为空或中英文、数字,且长度在2-20','right')){
            VLDRST.ShowTitile = true;
        }else{
            VLDRST.ShowTitile = false;
        }
    });

    /* 保存验证 */
    $(".btn-sure").click(function () {
        $("#destName").blur();
        $("#destIntro").blur();
        $("#keyWord").blur();
        
        
     	// 是否上传图片
		var hasImage = $(".url").val();
		if(hasImage==''){
			creatErrMesg("#pickfiles", "请至少上传一张图片", "top");
			return false;
		} else {
			removeErrMesg("#pickfiles");
			//return false;
		}

        // 验证通过
        if (VLDRST.NameType && VLDRST.NonNull && VLDRST.ShowTitile) {
            msgBox("保存成功！", "pass", 2600);
            $("#form").submit();
        }
        // 验证未通过
        else {
            msgBox("输入的内容有误，请检查！", "erro", 2600);
            return false;
        }
    });
</script>
		<!-- 利用空闲时间预加载指定页面 -->
		<link rel="prefetch"> <!-- IE10+ -->
		<link rel="next"> <!-- Firefox -->
		<link rel="prerender"> <!-- Chrome -->
</body>
</html>
