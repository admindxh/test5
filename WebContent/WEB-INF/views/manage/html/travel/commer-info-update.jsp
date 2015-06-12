<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
<%@include file="/common-html/common.jsp" %>
	<title>游西藏-商户管理-商户信息管理-商户修改</title>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
	<link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/ckeditor/ckeditor.js" type="text/javascript"></script>
	<style>
		/* 闭合浮动 */
		.floatfix:after {
			content: "";
			display: table;
			clear: both;
		}
		.pickfiles{
			display: inline-block;
			vertical-align: middle;
		}
		.webuploader-pick{
			background-color: #fbfbfb;
			border: 1px solid #9c9c9c;
			border-radius: 3px;
			text-align: center;
			font: normal 14px/32px "微软雅黑";
			color: #8c8c8c;
		}
		.webuploader-pick-hover{
		    background: none repeat scroll 0 0 #817171;
   			color: #e3e3e3;
		}
	</style>
	<script type="text/javascript">
	/**
     * ajax 请求路径 查询 目的地对应的  景点
     * @param destinationCode
     * @return
     */
    function  ajaxGetViewByDescode(destinationCode){
        var vl = $(destinationCode).val();//请求值
        
		var desText  = $("#qy").html();
		$("#showDes").val(desText);
    	$.ajax(
	    			{
	    				url:"${ctx}/web/readTibetSgPassMgController/getViewByDescode",
	    				data:"destinationCode="+vl,
	    			    type: 'POST',
	    				success:function(data){
		    			  //解析json  js
<%--		    			 $("#desId").html('');--%>
		    			   var  viewsJSON  =$.parseJSON(data);
//		    			   console.log(data);
						   var htmlViews  =   '<div class="float_l" > <label class="w-auto" id="">'+desText+'</label> </div>';		
							$(viewsJSON).each(function(i){
								htmlViews  +=  '<div class="ml20 float_l">  <input name="views" value="'+this.code+'" id="view'+i+'" type="checkbox" /> <label class="w-auto" for="view'+i+'" >'+this.viewName+'</label></div>';
							});	
		    			    $("#desId").html(htmlViews);
		    			    if(viewsJSON.length==0){
		    			    	$("#desId").html('暂无景点')}
		    			    chooseSelect();
	    				}
	        	   }
    	    );
   	 }
    function chooseSelect(){
  		$("#appendView").click(function() {
  			select_base.select(this);
  			select_base.hide_click(this);
  			//同时获取当前 dt的 value 值  ,然后开始绑定
  			var inputValue = $(this).attr("inputValue");
  			var inputName = $(this).attr("inputName");
  			
  			var inputTextName = $(this).attr("inputTextName");
  			var inputTextValue = $(this).attr("inputTextValue");
  			
  			var myMethod = $(this).attr("myMethod");
  			if(inputTextName!=undefined){
  				$("input[name='"+inputTextName+"']").val(inputTextValue);
  			}
  			if (inputName!=undefined) {
  				$("input[name='"+inputName+"']").val(inputValue);
  				
  				//ajax处理
  				if (myMethod) {
  					//调用自己的方法
  					var  obj  = "input[name='"+inputName+"']";
  					var    ajaxMethodString   =  myMethod+'("'+obj+'")';
  					eval(ajaxMethodString);
  				}
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
			<span><a>游西藏</a> -</span>
			<span><a>商户管理</a> -</span>
			<span><a href="${ctx}web/merchant/merchantList" target="_self">商户信息管理</a> -</span>
			<span><a href="#" target="_self">商户修改</a></span>
		</div>
		<!-- 模块管理 { -->
		<form id="form1" action="${ctx}/web/merchant/intoMerchantUpdate" method="post">
			<!-- 基本信息 { -->
			<input type="hidden"  value="${merchant.code }" name="code"/>
			<div>
				<h2 class="mt40">商户基本信息:</h2>

				<div class="filament_solid_ddd ov-au mt20">

					<div class="formLine mt10 ">
						<label>所属目的地:</label>

						<div id="dest" class="select-base">
							<i class="w-200" id="qy">${destination.destinationName }</i>
							<dl> 
								<dt  inputValue="" myMethod="ajaxGetViewByDescode" inputName="destinationCode">全部地区</dt>
								<c:forEach var="destination" items="${destinationList}">
									<dt  myMethod="ajaxGetViewByDescode"  inputValue="${destination.code}" inputName="distination">${destination.destinationName}</dt>
								</c:forEach>
							</dl>
							<input id="destinationCode" type="hidden" value="${destination.code }" name="distination"/>
						</div>
					</div>
					<div class="formLine f15 floatfix">
						<label class="va_m">相关景点:</label>
						<div id="desId" class="scenic filament_solid_ddd disp-ib floatfix p10 va_m">
									<div class="float_l">
								<label class="w-auto">${destination.destinationName }</label>
							</div>
							<c:forEach items="${views}" var="view" varStatus="status">
								<div class="float_l ml20">
									<input name="views" checked="checked" id="view${status.index }" value="${view.code }" type="checkbox" />
									<label class="w-auto" for="view${status.index }">${view.viewName }</label>
								</div>
							
							</c:forEach>
							
							
						</div>
					</div>
					<div class="formLine mt10 ">
						<label>是否官方推荐:</label>

						<div id="isOfficial" class="select-base">
							<i class="w-200">
								<c:if test="${merchant.isRecommend==1}">官方推荐</c:if>
								<c:if test="${merchant.isRecommend==0}">非官方推荐</c:if>
							</i>
							<dl>
								<dt  inputValue="1" inputName="isRecommend">官方推荐</dt>
								<dt  inputValue="0" inputName="isRecommend">非官方推荐</dt>
							</dl>
							
							<input id="isRecommend" type="hidden" value="${merchant.isRecommend }" name="isRecommend"/>
						</div>
					</div>

					<div class="formLine mt10 ">
						<label>商户类型:</label>

						<div id="commercialType" class="select-base">
							<input id="" name="" type="hidden">
							<i class="w-200">${merchantType2.name }</i>
							<dl>
								<c:forEach var="mt" items="${merchantType}">
									<dt   inputValue="${mt.code}" inputName="merchantType">${mt.name}</dt>
								</c:forEach>
							</dl>
							<input id="merchantType" type="hidden" value="${merchantType2.code}" name="merchantType"/>
						</div>
					</div>

					<div class="formLine mt10">
						<label>商户名称:</label>
						<input id="commercialName" value="${merchant.merchantName }" name="merchantName" type="text" class="w-260" />
					</div>

					<div class="formLine mt10">
						<label>商户地址:</label>
						<div id="province" class="select-base">
						<input id="" name="" type="hidden">
						<i class="w-140">西藏自治区</i>
						<dl>
							<dt>
								西藏自治区
							</dt>
						</dl>
					</div>
						<input type="text" value="${destination.destinationName }"  disabled="disabled" name="" id="showDes">
						<input id="detailAddr" name="merchantSummary" value="${merchant.merchantSummary }" type="text" class="w-260" placeholder="请输入详细地址" style="text-align: left"/>
					</div>
					<div class="formLine mt10">
						<label>参考价格:</label>
						<input id="price" value="${merchant.price }" name="price" type="text" class="w-260" />
						<span class="f13">提示：如果您选择关联携程，系统将自动获取携程的参考价格</span>
					</div>

					<div class="formLine">
						<label class="pos_r_t5">商户介绍:</label>
<%--						<span class="dataVal va_t">--%>
<%--							<textarea id="editor1" name="merchantDetail" cols="80" rows="10" class="ckeditor"--%>
<%--                                      style="display:inline-block">--%>
<%--                                      ${merchant.merchantDetail }--%>
<%--                            </textarea>--%>
<%--						</span>--%>
						 <script id="editor1" class="ueEditor_cont" name="merchantDetail"  style="height:500px;" type="text/plain">${merchant.merchantDetail }</script>
					</div>

					<div class="formLine mt20">
						<div>
							<label>商户图片1:</label>
							<div id="pickfiles${x}" class="pickfiles imgNode">请上传图片</div> 
							<span class="txt-suggest">推荐尺寸——230 * 175，支持jpg/png格式</span>
						</div>
						<div class="mt20 mb20">
							<label class="va_t">缩略图:</label>
							<img id="merchant1" class="va_t" src="${ctx }/${imgs[0].url}" alt=""  style="width: 230px;height: 175px;"/>
							<input type="hidden" class="url" name="urls" value="${imgs[0].url}" />
							<input type="hidden" class="imgCodes" name="imgCodes" value="${imgs[0].code }" />
						</div>
						<div style="border-bottom: 1px solid #ddd" class="ml30 mr30"></div>
					</div>

					<div class="formLine mt20">
						<div>
							<label>商户图片2:</label>
							<div id="pickfiles${x}" class="pickfiles imgNode">请上传图片</div>
							<span class="txt-suggest">推荐尺寸——230 * 175，支持jpg/png格式</span>
						</div>
						<div class="mt20 mb20">
							<label class="va_t">缩略图:</label>
							<img  id="merchant2" class="va_t" src="${ctx}/${imgs[1].url}" alt=""  style="width: 230px;height: 175px;"/>
							<input type="hidden" class="url" name="urls" value="${imgs[1].url}" />
							<input type="hidden" class="imgCodes" name="imgCodes" value="${imgs[1].code}" />
						</div>
						<div style="border-bottom: 1px solid #ddd" class="ml30 mr30"></div>
					</div>

					<div class="formLine mt20">
						<div>
							<label>商户图片3:</label>
							<div id="pickfiles${x}" class="pickfiles imgNode">请上传图片</div>
							<span class="txt-suggest">推荐尺寸——230 * 175，支持jpg/png格式</span>
						</div>
						<div class="mt20 mb20">
							<label class="va_t">缩略图:</label>
							<img id="merchant3" class="va_t" src="${ctx }/${imgs[2].url}" alt=""  style="width: 230px;height: 175px;"/>
							<input type="hidden" class="url" name="urls" value="${imgs[2].url}" />
							<input type="hidden" class="imgCodes" name="imgCodes" value="${imgs[2].code}" />
						</div>
						<div style="border-bottom: 1px solid #ddd" class="ml30 mr30"></div>
					</div>

					<div class="formLine mt20">
						<div>
							<label>商户图片4:</label>
							<div id="pickfiles${x}" class="pickfiles imgNode">请上传图片</div>
							<span class="txt-suggest">推荐尺寸——230 * 175，支持jpg/png格式</span>
						</div>
						<div class="mt20 mb20">
							<label class="va_t">缩略图:</label>
							<img id="merchant4" class="va_t" src="${ctx }/${imgs[3].url}" alt=""  style="width: 230px;height: 175px;"/>
							<input type="hidden" class="url" name="urls" value="${imgs[3].url}" />
							<input type="hidden" class="imgCodes" name="imgCodes" value="${imgs[3].code}" />
						</div>
						<div style="border-bottom: 1px solid #ddd" class="ml30 mr30"></div>
					</div>

					<div class="formLine mt20">
						<div>
							<label>商户图片5:</label>
							<div id="pickfiles${x}" class="pickfiles imgNode">请上传图片</div>
							<span class="txt-suggest">推荐尺寸——230 * 175，支持jpg/png格式</span>
						</div>
						<div class="mt20 mb20">
							<label class="va_t">缩略图:</label>
							<img id="marchant5" class="va_t" src="${ctx }/${imgs[4].url}" alt=""  style="width: 230px;height: 175px;"/>
							<input type="hidden" class="url" name="urls" value="${imgs[4].url}" />
							<input type="hidden" class="imgCodes" name="imgCodes" value="${imgs[4].code}" />
						</div>
						<div style="border-bottom: 1px solid #ddd" class="ml30 mr30"></div>
					</div>

					<div class="formLine mt20">
						<div>
							<label>商户图片6:</label>
							<div id="pickfiles${x}" class="pickfiles imgNode">请上传图片</div>
							<span class="txt-suggest">推荐尺寸——230 * 175，支持jpg/png格式</span>
						</div>
						<div class="mt20 mb20">
							<label class="va_t">缩略图:</label>
							<img id="marchant6" class="va_t" src="${ctx }/${imgs[5].url}" alt=""  style="width: 230px;height: 175px;"/>
							<input type="hidden" class="url" name="urls" value="${imgs[5].url}" />
							<input type="hidden" class="imgCodes" name="imgCodes" value="${imgs[5].code}" />
						</div>
					</div>

				</div>
			</div>
			<!-- } 基本信息 -->

			<!-- 商户预订信息 { -->
			<div>
				<h2 class="mt100">商户预订信息:</h2>

				<div class="filament_solid_ddd ov-au mt20">
					<div class="formLine">
						<input class="ml50"  name="" id="xcjd" type="checkbox" />
						<label for="xcjd" class="w-auto">关联携程对应酒店信息</label>
					</div>
					<div class="formLine">
						<label>相关链接:</label>
						<input id="relLink" value="${merchant.ctripUrl }" name="ctripUrl" type="text" class="w-260" />
					</div>
				</div>
			</div>
			<!-- } 商户预订信息 -->
			
			<!-- 商户活动链接 { -->
			<div>
				<h2 class="mt100">商户活动链接:</h2>
				<div class="filament_solid_ddd ov-au mt20">
					<div class="formLine">
						<label>相关链接:</label>
						<input id="activityUrl" name="activityUrl" value="${merchant.activityUrl }" type="text" class="w-260" />
					</div>
				</div>
			</div>
			<!-- } 商户活动信息 -->

			<!-- SEO信息 { -->
			<div class="contClassify">
				<h2 class="mt20">页面SEO信息</h2>
				<div class="filament_solid_ddd ov-au mt30">
					<div class="formLine mt20 mb20">
						<label class="w-180 ft-w-b ml40">&lt;Title&gt;标签:</label>
						<input type="text" maxlength="" class="w-320 seoInfo" name="tdkTitle" value="${merchant.tdkTitle }">
					</div>
					<div class="formLine mt20 mb20">
						<label class="w-180 ft-w-b ml40">&lt;Description&gt;标签:</label>
						<input type="text" maxlength="" class="w-320 seoInfo" name="tdkDescription" value="${merchant.tdkDescription }">
					</div>
					<div class="formLine mt20 mb20">
						<label class="w-180 ft-w-b ml40">&lt;Keywords&gt;标签(关键字):</label>
						<input type="text" maxlength="" class="w-320 seoInfo" name="tdkKeywords" value="${merchant.tdkKeywords }">
						<span class="txt-suggest ml20">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
					</div>
				</div>
			</div>
			<!-- } SEO信息 -->

			<div class="txt-ac mt40">
				<button id="addCommerInfo" class="btn-sure" type="button">修改</button>
				<button class="btn-sure ml100" type="button" onclick="back()">取消</button>
			</div>
		</form>
		<!-- } 模块管理 -->
	</div>
	<!-- } main -->

	<!-- JS引用部分 -->
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
	<script src="${ctx}/common-js/common.js" type="text/javascript"></script>
	<script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
    <script src="${ctxManage}/webuploader/upload.js" type="text/javascript"></script> 
    <!-- 实例化编辑器 -->
    <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.all.js"></script>
	<script type="text/javascript">
	    var ue1 = UE.getEditor('editor1');
	</script>
	<script type="text/javascript">
		//=============================================
		// 				 数据有效性验证
		//=============================================
		
		/* 自定义下拉菜单消除验证 */
		$("#dest dt, #province dt, #district dt").click(function() {
			var $this = $(this),
				thisParent = $this.parents(".select-base");
			thisParent.next(".errMesg").remove();
		});

		/* 相关景点选择验证 */
		$(document).on("click", "#desId input[id^='view']", function() {
			var ckb = $("#desId").find("input[id^='view']"),
					ckb_len = ckb.length;
			for(var i = 0; i < ckb_len; i++) {
				var isCkd = ckb.eq(i).prop("checked");
				if(isCkd) {
					removeErrMesg("#desId");
					break;
				} else {
					creatErrMesg("#desId","请至少选择一个景点", "top");
				}
			}
		});
		/* 相关景点点击验证 */
		/*$(document).on("click", ".scenic input[type='checkbox']", function() {
			// 相关景点遍历设置值
			var inputChecked_len = $(".scenic input[type='checkbox']:checked").length > 0;
			if(inputChecked_len){
				$(".scenic:last").next(".errMesg").remove();
			}else{
				creatErrMesg(".scenic:last", "请选择一个或多个相关景点", "right");
			}
		});*/
		
		/* 商户名称 */
		$("#commercialName").blur(function() {
			var this_val = $(this).val();
			valid_txtBox_create(this, inputRange(this,2,30), "商户名称应为长度在2-30位的字符", "right")
		});
		
		/* 参考价格 */
		$("#price").blur(function() {
			var this_val = $(this).val();
			valid_txtBox_create(this, ($.VLDTOR.IsCnNum(this_val) && inputRange(this,1,7)) || this_val == "", "参考价格只能为空或正整数、中文且最多七位数", "top");
            /*if (this_val.length > 0) {
                var parter = /(^0*)/g;
                var str = this_val.replace(parter, "");
                if (str == '') {
                    $(this).val(0);
                } else {
                    $(this).val(str);
                }
            }*/
		});
		
		/* 相关链接 */
		$("#relLink").blur(function() {
			var this_val = $(this).val();
			valid_txtBox_create(this, $.VLDTOR.IsWebUrl(this_val) || this_val == "", "只能为以“http、https、ftp”开头的网址格式", "right");
		});
		
		/* 活动链接 */
		$("#activityUrl").blur(function() {
			var this_val = $(this).val();
			valid_txtBox_create(this, $.VLDTOR.IsHTTP(this_val) || this_val == "", "只能为以“http、https、ftp”开头的网址格式", "right");
		});
		
		/* 详细地址 */
		$("#detailAddr").blur(function() {
			var this_val = $(this).val();
			valid_txtBox_create(this, ($.VLDTOR.IsArticle(this_val) && inputRange(this,2,50)) || this_val == "", "只能为空或中英文、数字，且长度在2-50", "right");
		});
		
		/* SEO验证 */
		$(".seoInfo").blur(function () {
			var $this = $(this),
					thisVal = $this.val();
			valid_txtBox_create(this, ($.VLDTOR.IsEnCnNum_comma(thisVal) && inputRange(this,2,20)) || thisVal == "",'只能为空或中英文、数字，且长度在2-20','right');
		});

		$(function(){
			if($("#relLink").val().length != 0){
				$("#xcjd")[0].checked = true;
			}else{
				$("#xcjd")[0].checked = false;
			}
			
			if($("#xcjd")[0].checked){
				$("#relLink").attr('disabled',false);
			}else{
				$("#relLink").attr('disabled',true);
			}


			$("#xcjd").on('click',function(){
				if($(this)[0].checked){
					$('#relLink').attr('disabled',false);
				}else{
					$('#relLink').attr('disabled',true);
				}
			})
		});

		/* 商户介绍（富文本验证） */
		ue1.addListener( 'contentChange', function() {
			valid_richTxt(this,"#editor1",2,5000,"内容必须在2-5000个字符之间");
		});
		ue1.addListener( 'focus', function() {
			valid_richTxt(this,"#editor1",2,5000,"内容必须在2-5000个字符之间");
		});
		
		/* 添加按钮验证 */
		$("#addCommerInfo").click(function() {
            // 失焦验证
            $("#commercialName").blur();
            $("#price").blur();
            $("#relLink").blur();
            $("#detailAddr").blur();
            $(".seoInfo").blur();
            // 自定义下拉框验证
            var dest = $("#dest"),
                dest_substr = dest.find("i").text().substr(0,3),
                commmer = $("#commercialType"),
                commmer_substr = commmer.find("i").text().substr(0,3),
                province = $("#province"),
                province_substr = province.find("i").text().substr(0,3);

            // 所属目的地
            if(dest_substr == "请选择") {
                valid_customSelect("#dest", "请选择目的地区域", "right");
            } else {
                modifErrMesg("#dest");
            }

			/* 相关景点选择验证 */
			$(document).on("click", "#desId input[id^='view']", function() {
				var ckb = $("#desId").find("input[id^='view']"),
						ckb_len = ckb.length;
				for(var i = 0; i < ckb_len; i++) {
					var isCkd = ckb.eq(i).prop("checked");
					if(isCkd) {
						removeErrMesg("#desId");
						break;
					} else {
						creatErrMesg("#desId","请至少选择一个景点", "top");
					}
				}
			});

            // 商户类型
            if(commmer_substr == "请选择") {
                valid_customSelect("#commercialType", "请选择商户类型", "right");
            } else {
                modifErrMesg("#commercialType");
            }

            // 省
            if(province_substr == "请选择") {
                creatErrMesg("#province", "请选择省份", "top");
            } else {
                removeErrMesg("#province");
            }

//            // 相关景点遍历设置值
//            var inputChecked_len = $(".scenic input[type='checkbox']:checked").length > 0;
//            if(inputChecked_len){
//                $(".scenic:last").next(".errMesg").remove();
//            }else{
//                modifErrMesg(".scenic:last", "请选择一个或多个相关景点", "right");
//            }

            // 商户介绍（富文本验证）
            var ueCont1 = ue1.getContentTxt();
            if(ueCont1 == "") {
                valid_txtBox_create("#editor1", false, "内容必须在2-5000个字符之间", "top-right");
            }

            var regSpan_len = $("span.errMesg").length > 0;
            // 验证结果操作
            if(regSpan_len) {
                msgBox("输入的内容有误，请检查！", "erro");
            } else {
                // Do Something
                 $("#form1").submit();
            }

		});
		
	</script>

	<script type="text/javascript">
		function back(){
			popupLayer(
					'',
					'<div style="width: 320px; text-align:center; margin: 0 auto;">返回将不保存数据，是否返回？</div>',
					'<button type="button" class="btn-sure sure mr15">确定</button>' +
					'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				javascript:history.back(-1);
				closePopup();
			});
		}
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