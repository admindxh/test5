<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
		<title>游西藏-团游管理-团游信息管理-修改团游123</title>
		<%@include file="/common-html/common.jsp"%>
		<link rel="stylesheet"
			href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
		<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
		<link rel="stylesheet"
			href="${ctxManage}/resources/css/travel/formWeb.css" />
		<link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
		<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
			type="text/javascript"></script>
		<link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
		<script src="${ctxManage}/resources/plugin/respond.min.js"
			type="text/javascript"></script>
		<%--		<script src="${ctxManage}/resources/plugin/ckeditor/ckeditor.js"--%>
		<%--			type="text/javascript"></script>--%>
		<style>
/* 闭合浮动 */
.floatfix:after {
	content: "";
	display: table;
	clear: both; . pickfiles { display : inline-block;
	vertical-align: middle;
}
</style>
	</head>

	<body>
		<!-- main { -->
		<div class="main">
			<!-- 页面位置-->
			<div class="location">
				<label>
					您当前位置为:
				</label>
				<span><a>游西藏</a> -</span>
				<span><a>团游管理</a> -</span>
				<span><a href="tour-group.html" target="_self">团游信息管理</a> -</span>
				<span><a href="#" target="_self">修改团游</a> </span>
			</div>

			<!-- 模块管理 { -->
			<form action="saveOrUpdateGroupTravel" method="post" id="myForm">
				<!-- 基本信息 { -->
				<div>
					<h2 class="mt40">
						团游信息:
					</h2>

					<div class="filament_solid_ddd ov-au mt20">

						<div class="formLine">
							<label>
								相关目的地:
							</label>
							<div id="dest" class="chooseDest formLine-middle-group">
								<c:forEach var="destination" items="${destinationList}">
									<span class="formEleGroup-squad"> <c:set var="desclass"
											value="" /> <c:set var="ischecked" value="" /> <c:forEach
											items="${myDestinationList}" var="myDes">
											<c:if test="${myDes.code==destination.code}">
												<c:set var="ischecked" value="checked='checked'" />
												<c:set var="desclass" value="class='des'" />
											</c:if>
										</c:forEach> <input value="${destination.code}"
											${ischecked} ${desclass} name="dest" type="checkbox">
										<label class="lbl_check">
											${destination.destinationName}
										</label> </span>
								</c:forEach>
							</div>
							<span class="reqItem">*</span>
						</div>
						<div class="destScenic formLine nowrap">
							<label class="formLine-small-group-lbl">
								相关景点:
							</label>
							<!-- 景点选择 -->
							<div class="formLine-large-group">
								<c:forEach var="desandvo" items="${desandviewList}">
									<div class="formLine-small-group hide">
										<span class="region">${desandvo.desName}</span>
										<span class="scenic"> <c:forEach var="viewinfo"
												items="${desandvo.views}">


												<c:set var="ischecked" value="" />
												<c:forEach items="${myViewList}" var="myDes">
													<c:if test="${myDes.code==viewinfo[1]}">
														<c:set var="ischecked" value="checked='checked'" />

													</c:if>
												</c:forEach>


												<span class="formEleGroup-squad"> <input name="vies"
														value="${viewinfo[1]}" ${ischecked } type="checkbox">
													<label class="lbl_check"
														style="width: 280px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; text-align: left;">
														${viewinfo[0]}
													</label> </span>
											</c:forEach> </span>
									</div>
								</c:forEach>
							</div>
							<!-- } 景点选择 -->
						</div>

						<c:if test="${not empty groupTravel.code}">
							<div class="formLine mt10" style="display: inline-block;">
								<label>
									团游编号:
								</label>
								<label style="width: auto;">
									${groupTravel.code}
								</label>
							</div>
						</c:if>
						<input type="hidden" name="code" value="${groupTravel.code}" />
						<div class="formLine" style="display: inline-block;">
							<label>
								是否官方推荐:
							</label>
							<div id="isOfficial" class="select-base">
								<c:if test="${groupTravel.isRecommend == 0}">
									<i class="w-200">非官方推荐</i>
								</c:if>
								<c:if test="${groupTravel.isRecommend != 0}">
									<i class="w-200">官方推荐</i>
								</c:if>
								<dl>
									<dt inputValue="1" inputName="isRecommend">
										官方推荐
									</dt>
									<dt inputValue="0" inputName="isRecommend">
										非官方推荐
									</dt>
								</dl>

								<input id="isRecommend" type="hidden" value="1"
									name="isRecommend" />
							</div>
							<span class="reqItem">*</span>
						</div>

						<div class="formLine mt10">
							<label>
								团游标题:
							</label>
							<input id="commercialName" type="text" class="w-260"
								value="${groupTravel.name}" name="name" />
							<span class="reqItem">*</span>
						</div>

						<div class="formLine mt10">
							<label>
								参考价格:
							</label>
							<input id="price" type="text" class="w-260"
								value="${groupTravel.price}" name="gtPrice" />
							<span class="txt-suggest f13"></span>
						</div>

						<div class="formLine">
							<label class="pos_r_t5">
								团游介绍:
							</label>
							<%--							<span class="dataVal va_t"> <textarea id="editor1"--%>
							<%--									name="detail" cols="80" rows="10" class="ckeditor"--%>
							<%--									style="display: inline-block">--%>
							<%--                              ${groupTravel.detail}--%>
							<%--                            </textarea> </span>--%>
							<script id="editor1" class="ueEditor_cont" name="detail"
								style="height:500px;" type="text/plain">${groupTravel.detail}</script>
							<span class="reqItem">*</span>
						</div>

						<div class="formLine mt20">
							<div class="mt10 disp-ib">
								<%--								<label>--%>
								<%--									收藏数:--%>
								<%--								</label>--%>
								<%--								<input type="text" class="w-100 number"--%>
								<%--									value="${groupTravel.collectNum}" name="gtCollectNum" />--%>
								<%--								<button type="button" class="btn-base">--%>
								<%--									恢复默认--%>
								<%--								</button>--%>
								<%--							</div>--%>
								<%----%>
								<%--							<div class="disp-ib ml50">--%>
								<%--								<label>--%>
								<%--									查看数:--%>
								<%--								</label>--%>
								<%--								<input type="text" class="w-100 number"--%>
								<%--									value="${groupTravel.viewNum}" name="gtViewNum" />--%>
								<%--								<button type="button" class="btn-base">--%>
								<%--									恢复默认--%>
								<%--								</button>--%>
								<%--							</div>--%>
							</div>

							<div class="formLine mt20">
								<div>
									<label>
										团游图片1:
									</label>
									<div id="pic1" class="pickfiles imgNode">
										请上传图片
									</div>
									<span class="txt-suggest">推荐尺寸——230 * 175，支持jpg/png格式</span>
								</div>
								<div class="mt20 mb20">
									<label class="va_t">
										缩略图:
									</label>
									<c:if test="${not empty imgs[0].url}">
										<img id="pre1" class="va_t" src="${ctx}/${imgs[0].url}"
											alt="" style="height:175px; width:230px" />
									</c:if>
									<c:if test="${empty imgs[0].url}">
										<img id="pre1" class="va_t"
											src="${ctxManage}/resources/img/ele/loadImg_default_c.jpg"
											alt="" style="height:175px; width:230px" />
									</c:if>
									<input type="hidden" class="url" id="hiddenId1" name="urls"
										value="${imgs[0].url}" />
									<input type="hidden" class="imgCodes" name="imgCodes"
										value="${imgs[0].code }" />
								</div>
								<div style="border-bottom: 1px solid #ddd" class="ml30 mr30"></div>
							</div>

							<div class="formLine mt20">
								<div>
									<label>
										团游图片2:
									</label>
									<div id="pic2" class="pickfiles imgNode">
										请上传图片
									</div>
									<span class="txt-suggest">推荐尺寸——230 * 175，支持jpg/png格式</span>
								</div>
								<div class="mt20 mb20">
									<label class="va_t">
										缩略图:
									</label>
									<c:if test="${not empty imgs[1].url}">
										<img id="pre2" class="va_t" src="${ctx}/${imgs[1].url}"
											alt="" style="height:175px; width:230px" />
									</c:if>
									<c:if test="${empty imgs[1].url}">
										<img id="pre2" class="va_t"
											src="${ctxManage}/resources/img/ele/loadImg_default_c.jpg"
											alt="" style="height:175px; width:230px" />
									</c:if>
									<input type="hidden" id="hiddenId2" class="url" name="urls"
										value="${imgs[1].url}" />
									<input type="hidden" class="imgCodes" name="imgCodes"
										value="${imgs[1].code}" />
								</div>
								<div style="border-bottom: 1px solid #ddd" class="ml30 mr30"></div>
							</div>

							<div class="formLine mt20">
								<div>
									<label>
										团游图片3:
									</label>
									<div id="pic3" class="pickfiles imgNode">
										请上传图片
									</div>
									<span class="txt-suggest">推荐尺寸——230 * 175，支持jpg/png格式</span>
								</div>
								<div class="mt20 mb20">
									<label class="va_t">
										缩略图:
									</label>
									<c:if test="${not empty imgs[2].url}">
										<img id="pre3" class="va_t" src="${ctx}/${imgs[2].url}"
											alt="" style="height:175px; width:230px" />
									</c:if>
									<c:if test="${empty imgs[2].url}">
										<img id="pre3" class="va_t"
											src="${ctxManage}/resources/img/ele/loadImg_default_c.jpg"
											alt="" style="height:175px; width:230px" />
									</c:if>
									<input type="hidden" id="hiddenId3" class="url" name="urls"
										value="${imgs[2].url}" />
									<input type="hidden" class="imgCodes" name="imgCodes"
										value="${imgs[2].code}" />
								</div>
								<div style="border-bottom: 1px solid #ddd" class="ml30 mr30"></div>
							</div>

							<div class="formLine mt20">
								<div>
									<label>
										团游图片4:
									</label>
									<div id="pic4" class="pickfiles imgNode">
										请上传图片
									</div>
									<span class="txt-suggest">推荐尺寸——230 * 175，支持jpg/png格式</span>
								</div>
								<div class="mt20 mb20">
									<label class="va_t">
										缩略图:
									</label>
									<c:if test="${not empty imgs[3].url}">
										<img id="pre4" class="va_t" src="${ctx}/${imgs[3].url}"
											alt="" style="height:175px; width:230px" />
									</c:if>
									<c:if test="${empty imgs[3].url}">
										<img id="pre4" class="va_t" src="${ctxManage}/resources/img/ele/loadImg_default_c.jpg"
											alt="" style="height:175px; width:230px" />
									</c:if>
									<input type="hidden"  id="hiddenId4"class="url" name="urls"
										value="${imgs[3].url}" />
									<input type="hidden" class="imgCodes" name="imgCodes"
										value="${imgs[3].code}" />
								</div>
								<div style="border-bottom: 1px solid #ddd" class="ml30 mr30"></div>
							</div>

							<div class="formLine mt20">
								<div>
									<label>
										团游图片5:
									</label>
									<div id="pic5" class="pickfiles imgNode">
										请上传图片
									</div>
									<span class="txt-suggest">推荐尺寸——230 * 175，支持jpg/png格式</span>
								</div>
								<div class="mt20 mb20">
									<label class="va_t">
										缩略图:
									</label>
									<c:if test="${not empty imgs[4].url}">
										<img id="pre5" class="va_t" src="${ctx}/${imgs[4].url}"
											alt="" style="height:175px; width:230px" />
									</c:if>
									<c:if test="${empty imgs[4].url}">
										<img id="pre5" class="va_t" src="${ctxManage}/resources/img/ele/loadImg_default_c.jpg"
											alt="" style="height:175px; width:230px" />
									</c:if>
									<input type="hidden" id="hiddenId5" class="url" name="urls"
										value="${imgs[4].url}" />
									<input type="hidden" class="imgCodes" name="imgCodes"
										value="${imgs[4].code}" />
								</div>
								<div style="border-bottom: 1px solid #ddd" class="ml30 mr30"></div>
							</div>

							<div class="formLine mt20">
								<div>
									<label>
										团游图片6:
									</label>
									<div id="pic6" class="pickfiles imgNode">
										请上传图片
									</div>
									<span class="txt-suggest">推荐尺寸——230 * 175，支持jpg/png格式</span>
								</div>
								<div class="mt20 mb20">
									<label class="va_t">
										缩略图:
									</label>
									<c:if test="${not empty imgs[5].url}">
										<img id="pre6" class="va_t" src="${ctx}/${imgs[5].url}"
											alt="" style="height:175px; width:230px" />
									</c:if>
									<c:if test="${empty imgs[5].url}">
										<img id="pre6" class="va_t" src="${ctxManage}/resources/img/ele/loadImg_default_c.jpg"
											alt="" style="height:175px; width:230px" />
									</c:if>
									<input type="hidden" id="hiddenId6" class="url" name="urls"
										value="${imgs[5].url}" />
									<input type="hidden" class="imgCodes" name="imgCodes"
										value="${imgs[5].code}" />
								</div>
							</div>

						</div>
					</div>
					<!-- } 基本信息 -->

					<!-- 团游预订信息 { -->
					<div>
						<h2 class="mt100">
							团游预订信息:
						</h2>

						<div class="filament_solid_ddd ov-au mt20">
							<div class="formLine">
								<input class="ml50" id="xcjd" type="checkbox" />
								<label for="xcjd" class="w-auto">
									关联携程对应酒店信息
								</label>
							</div>

							<div class="formLine">
								<label>
									相关链接:
								</label>
								<input id="relLink" type="text" class="w-260" name="ctripUrl"
									value="${groupTravel.ctripUrl}" />
							</div>
						</div>
					</div>
					<!-- } 团游预订信息 -->

					<!-- SEO信息 { -->
					<div class="contClassify">
						<h2 class="title">页面SEO信息</h2>
						<div class="filament_solid_ddd ov-au mt30">
							<div class="formLine mt20 mb20">
								<label class="w-180 ft-w-b ml40">&lt;Title&gt;标签:</label>
								<input type="text" maxlength="" class="w-320 seoInfo" name="tdkTitle" value="${groupTravel.tdkTitle }">
							</div>
							<div class="formLine mt20 mb20">
								<label class="w-180 ft-w-b ml40">&lt;Description&gt;标签:</label>
								<input type="text" maxlength="" class="w-320 seoInfo" name="tdkDescription" value="${groupTravel.tdkDescription }">
							</div>
							<div class="formLine mt20 mb20">
								<label class="w-180 ft-w-b ml40">&lt;Keywords&gt;标签(关键字):</label>
								<input type="text" maxlength="" class="w-320 seoInfo" name="tdkKeywords" value="${groupTravel.tdkKeywords }">
								<span class="txt-suggest ml20">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
							</div>
						</div>
					</div>
					<!-- } SEO信息 -->

					<div class="txt-ac mt40">
						<button id="addCommerInfo" class="btn-sure" type="button">
							保存
						</button>
						<button class="btn-sure ml100" type="button" onclick="back()">
							取消
						</button>
					</div>
			</form>
			<!-- } 模块管理 -->
		</div>
		<!-- } main -->

		<!-- JS引用部分 -->
		<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/base-form.js"></script>
		<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/dataValidation.js"
			type="text/javascript"></script>
		<script src="${ctx}/common-js/common.js" type="text/javascript"></script>
		<!-- 实例化编辑器 -->
		<script type="text/javascript"
			src="${ctxManage}/resources/plugin/ueditor/ueditor.config.js"></script>
		<script type="text/javascript"
			src="${ctxManage}/resources/plugin/ueditor/ueditor.all.js"></script>
		<script type="text/javascript">
	    var ue1 = UE.getEditor('editor1');
	</script>
		<script type="text/javascript">

	/* 页面加载成功后，显示目的地对应景点信息 */
	$(function(){
		var checkedTrue = $(".chooseDest .formEleGroup-squad").children("input[name='dest']:checked"),
				parentIndex = [];
		for(var i=0; i<checkedTrue.length; i++){
			parentIndex[i] = checkedTrue.eq(i).parent().index();
			$(".destScenic").find(".formLine-small-group").eq(parentIndex[i]).css("display", "inline-block");
		}
	});
    //=============================================
    // 				 数据有效性验证
    //=============================================

    /* 团游标题 */
    $("#commercialName").blur(function() {
        var this_val = $(this).val();
        valid_txtBox(this, $.VLDTOR.IsArticle(this_val) && inputRange(this,2,30), "团游标题应为长度在2-30之间的字符", "right")
    });

    /* 参考价格 */
    $("#price").blur(function() {
        var this_val = $(this).val();
        valid_txtBox_create(this, $.VLDTOR.IsCnNum(this_val) || this_val == "", "参考价格只能为空或中文、正整数", "right");
        if (this_val.length > 0) {
            var parter = /(^0*)/g;
            var str = this_val.replace(parter, "");
            if (str == '') {
                $(this).val(0);
            } else {
                $(this).val(str);
            }
        }
    });

    /* 相关链接 */
    $("#relLink").blur(function() {
        var this_val = $(this).val();
        valid_txtBox_create(this, $.VLDTOR.IsWebUrl(this_val), "只能为空或字母、数字及常用符号", "right");
    });

    /* 相关目的地 */
    $(document).on('click',"#dest input[type='checkbox']", function () {
        var inputChecked_len = $("#dest input[type='checkbox']:checked").length > 0;
        if(inputChecked_len){
			modifErrMesg("#dest");
        }else{
			valid_txtBox("#dest", false, "请选择一个相关目的地", "right");
        }
    });
	
    /* 相关景点点击验证 */
    $(document).on("click", ".scenic input[type='checkbox']", function() {
        // 相关景点遍历设置值
        var inputChecked_len = $(".scenic input[type='checkbox']:checked").length > 0;
        if(inputChecked_len){
			removeErrMesg(".scenic")
        }else{
            creatErrMesg(".scenic", "请选择一个或多个相关景点", "right");
        }
    });

    /* SEO验证 */
    $("[name='keyWord']").blur(function () {
        var $this = $(this),
                thisVal = $this.val();
        valid_txtBox_create(this, ($.VLDTOR.IsEnCnNum_comma(thisVal) && inputRange(this,2,20)) || thisVal == "",'只能为空或者字母、数字、中文，且长度在2-20',"right");
    });
    /* SEO 验证 */
    $(".seoInfo").blur(function () {
    	var $this = $(this),
    		thisVal = $this.val();
    	valid_txtBox_create(this, ($.VLDTOR.IsEnCnNum_comma(thisVal) && inputRange(this,2,20)) || thisVal == "",'只能为空或字母、数字及中文，且长度在2-20','top');
    });
	/* 团游介绍（富文本验证） */
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

        var DestChecked_len = $("#dest input[type='checkbox']:checked").length > 0;
        if(DestChecked_len){
            $("#dest").next(".errMesg").remove();
        }else{
            creatErrMesg("#dest", "请选择一个相关目的地", "right");
        }

        // 相关景点遍历设置值
        var inputChecked_len = $(".scenic input[type='checkbox']:checked").length > 0;
        if(inputChecked_len){
            $(".scenic").next(".errMesg").remove();
        }else{
            creatErrMesg(".scenic", "请选择一个或多个相关景点", "right");
        }

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
            msgBox("保存成功！", "pass");
            $("#myForm").submit();
        }
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

			$(function(){
				$('#relLink').attr('disabled',true);

				$("#xcjd").on('click',function(){
					if($(this)[0].checked){
						$('#relLink').attr('disabled',false);
					}else{
						$('#relLink').attr('disabled',true);
					}
				})
			});
		</script>

		<!-- 利用空闲时间预加载指定页面 -->
		<link rel="prefetch">
		<!-- IE10+ -->
		<link rel="next">
		<!-- Firefox -->
		<link rel="prerender">
		<!-- Chrome -->
		<script src="${ctxManage}/webuploader/webuploader.js"
			type="text/javascript"></script>


<script type="text/javascript">

    //加载事件创建上传
    $(function () {
        for (var i = 1; i <=6; i++) {
            cteateUploder('pic' + i, 'pre' + i, 'hiddenId' + i);
        }
    })


</script>
<script type="text/javascript">
    function cteateUploder(pickId, preId, urlHiddenId) {
        var contextPath = "${ctx}";
        var options_ = {
            swf: contextPath + '/manage/webuploader/Uploader.swf',
            server: contextPath + '/web/imageController/uploadImage',
            runtimeOrder: "flash",
            accept: {extensions: 'jpg,jpeg,bmp,png,gif'},
            pick: {
                id: '#' + pickId,
                multiple: false
            },
            fileVal: 'file',
            auto: true,
            resize: false,
            fileSizeLimit:400 * 1024,
            compress:null
        };
        addEvent(new WebUploader.Uploader(options_), preId, urlHiddenId);
    }
    function addEvent(up, preId, urlHiddenId) {
        up.on('uploadSuccess', function (file, response) {
            $.ajax({
                type: "post",
                url: contextPath + "/web/imageController/getUrl",
                dataType: "text",
                async: false,
                success: function (data) {
                    $("#" + urlHiddenId).val(data);
                    $("#" + preId).attr("src", "${ctx}" + data);//预览img
                    var uploadBtn = $("#"+preId).parents('.posidSet-unit').find('.btn-uploadImg ')[0];
                    removeErrMesg(uploadBtn);
                },
                error: function () {
                    alert("数据异常");
                }
            });
        });
        up.on('error', function (type) {
            //alert("错误信息:"+type);
            if (type == "Q_EXCEED_SIZE_LIMIT" || type == "F_EXCEED_SIZE") {
            	msgBox("文件超过大小,请将上传文件大小控制在400kb以内","erro", 1000);
            } else if (type == "Q_TYPE_DENIED") {
            	msgBox("上传格式错误，或者是文件大小必须大于0kb");
            } else {
            	msgBox("上传错误，请重试。");
            }
        });
    }
</script>
	</body>

</html>