<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
	<title>活动&专题-活动&专题信息管理-编辑活动</title>
	<%@ include file="/common-html/common.jsp" %>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/datepicker.css" />
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
	<!-- 网页文本编辑器插件 -->
	<%-- <script src="${ctxManage}/resources/plugin/ckeditor/ckeditor.js" type="text/javascript"></script> --%>
	<script type="text/javascript">
	    var ispost=false;
		window.onbeforeunload= function(){
			if(ispost)return;
			event.returnValue="活动正在编辑中";
	    }
   </script>
</head>

<body>
	<!-- main { -->
	<div class="main">
		<form id="saveForm" name="" method="post" >
			<input type="hidden" name="activityCode" value="${activityCode }">
			<input type="hidden" name="enrollNoticeCode" value="${enrollNotice.code }">
			<!-- 页面位置-->
			<div class="location">
				<label>您当前位置为:</label>
				<span><a>活动&专题</a> -</span>
				<span><a>活动&专题信息管理</a> -</span>
				<span><a href="${ctx}web/activityController/showList" target="_self">活动信息管理</a> -</span>
				<span><a href="#" target="_self">编辑活动</a></span>
			</div>

			<!-- 数据操作 -->
			<div class="searchManage">
				<button type="button" class="btn-sure preview">预览</button>
				<button type="button" class="btn-sure save1">修改</button>
			</div>

			<!-- 模块管理 { -->
			<div class="muduleManage details filament_solid_ddd">
				<!-- 活动类型 { -->
				<div class="contClassify bt-n">
					<!--<h2 class="title">活动类型</h2>-->
					<div class="formLine">
						<label class="w-auto">类型:</label>
						<span class="dataVal">活动</span>
						<span class="reqItem">*</span>
						<!-- 选择显示版块 -->
						<div class="activModule formLine-middle-group" style="vertical-align:middle; margin-left:20px;display: none;">
							<span class="formEleGroup-squad">
								<c:if test="${activity.isUpload==0 }">
									<input name="isUpload" type="checkbox" value="1">
								</c:if>
								<c:if test="${activity.isUpload==1 }">
									<input name="isUpload" type="checkbox" value="1" checked="checked">
								</c:if>
								<label class="lbl_check">上传</label>
							</span>
							<span class="formEleGroup-squad">
								<c:if test="${activity.isVote==0 }">
									<input name="isVote" type="checkbox" value="1">
								</c:if>
								<c:if test="${activity.isVote==1 }">
									<input name="isVote" type="checkbox" value="1" checked="checked">
								</c:if>
								<label class="lbl_check">投票</label>
							</span>
							<%-- <span class="formEleGroup-squad">
								<c:if test="${activity.isPay==0 }">
									<input name="isPay" type="checkbox" value="1">
								</c:if>
								<c:if test="${activity.isPay==1 }">
									<input name="isPay" type="checkbox" value="1" checked="checked">
								</c:if>
								<label class="lbl_check">支付</label>
							</span> --%>
							<br>
							<span class="formEleGroup-squad applyItem">
								<c:if test="${activity.isEnroll==0 }">
									<input name="isEnroll" type="checkbox" value="1">
								</c:if>
								<c:if test="${activity.isEnroll==1 }">
									<input name="isEnroll" type="checkbox" value="1" checked="checked">
								</c:if>
								<label class="lbl_check"l>报名</label>
							</span>
							<span class="formEleGroup-squad applyPay">
								<c:if test="${activity.isEnrollPay==0 }">
									<input name="isEnrollPay" type="checkbox" value="1" disabled="disabled">
								</c:if>
								<c:if test="${activity.isEnrollPay==1 }">
									<input name="isEnrollPay" type="checkbox" value="1" checked="checked">
								</c:if>
								<label class="lbl_check">报名费支付</label>
							</span>
						</div>
					</div>
					
					<div class="formLine">
						<label class="w-auto">官网是否显示:</label>
						<span class="formEleGroup-squad">
							<input name="description" type="radio" value="1" ${activity.description eq '1'?'checked="checked"':'' }>
							<label class="lbl_check">是</label>
						</span>
						<span class="formEleGroup-squad">
							<input name="description" type="radio" value="0" ${activity.description eq '0'?'checked="checked"':'' }>
							<label class="lbl_check">否</label>
						</span>
					</div>

				</div>
				<!-- } 活动类型 -->

				<!-- 基本信息 { -->
				<div class="contClassify">
					<h2 class="title">基本信息</h2>

					<div class="formLine">
						<label>活动名称:</label>
						<input type="text" maxlength="30" class="w-260" name="name" value="${activity.name }">
						<span class="reqItem">*</span>

						<label>序号:</label>
						<input id="sortNum" type="text" maxlength="7" name="sortNum" value="${activity.sortNum }">
						<!-- <span class="reqItem">*</span> -->
					</div>

					<div class="formLine">
						<label class="pos_r_t5">活动简介:</label>
						<%-- <span class="dataVal va_t">
							<textarea id="editor1" name="summary" cols="80" rows="10" class="ckeditor" style="display:inline-block">${activity.summary }</textarea>
						</span> --%>
						<script id="editor1" class="ueEditor_cont" name="summary"  style="height:500px;" type="text/plain">${activity.summary }</script>
						<span class="reqItem">*</span>
					</div>
					
					<div class="formLine mt20">
						<label>缩略图:</label>
						<!-- <button id="bannerPickId" type="button" class="btn-base uploadImg">点击上传图片</button> -->
						<div id="imgPickId" class="btn-base uploadImg">点击上传图片</div>
						<span class="txt-suggest ml20">推荐尺寸：317 * 229</span>
						<span class="reqItem">*</span>
					</div>
					<div class="formLine">
						<label class="pos_r_t5">缩略图预览：</label>
						<img width="317" height="229" id="imgPreId" src="${ctx}${activity.img}" title="缩略图名称" alt="请上传图片" class="va_t">
						<input id="imgHiddenId" type="hidden" name="img" value="${activity.img}">
					</div>

					<div class="formLine mt20">
						<label>Banner图:</label>
						<!-- <button id="bannerPickId" type="button" class="btn-base uploadImg">点击上传图片</button> -->
						<div id="bannerPickId" class="btn-base uploadImg">点击上传图片</div>
						<span class="txt-suggest ml20">推荐尺寸：1903 * 500</span>
						<span class="reqItem">*</span>
					</div>
					<div class="formLine">
						<label class="pos_r_t5">Banner图预览：</label>
						<img width="1140" height="300" id="bannerPreId" src="${ctx}${activity.bannerImg}"
                             title="缩略图名称" alt="请上传图片" class="va_t" style="width: 951px;height: 250px;max-height: 100% !important;">
						<input id="bannerHiddenId" type="hidden" name="bannerImg" value="${activity.bannerImg}">
					</div>

					<div class="formLine mt20">
						<label>开始时间:</label>
						<input id="startDate" type="text" maxlength="10" name="startDate1" value="<fmt:formatDate type="both" value="${activity.startDate }" pattern="yyyy-MM-dd" />">
						<span class="reqItem">*</span>
						
						<label>截止时间:</label>
						<input id="endDate" type="text" maxlength="10" name="endDate1" value="<fmt:formatDate type="both" value="${activity.endDate }" pattern="yyyy-MM-dd" />">
						<span class="reqItem">*</span>
					</div>

				</div><!-- } 基本信息 -->
				
				<!-- SEO信息 { -->
				<div class="contClassify">
					<h2 class="title">SEO信息</h2>
					<div class="filament_solid_ddd ov-au mt30">
						<div class="formLine mt20 mb20">
							<label class="w-180 ft-w-b ml40">&lt;Title&gt;标签:</label>
							<input type="text" maxlength="" class="w-320 seoInfo" name="tdkTitle" value="${activity.tdkTitle }">
						</div>
						<div class="formLine mt20 mb20">
							<label class="w-180 ft-w-b ml40">&lt;Description&gt;标签:</label>
							<input type="text" maxlength="" class="w-320 seoInfo" name="tdkDescription" value="${activity.tdkDescription }">
						</div>
						<div class="formLine mt20 mb20">
							<label class="w-180 ft-w-b ml40">&lt;Keywords&gt;标签(关键字):</label>
							<input type="text" maxlength="" class="w-320 seoInfo" name="tdkKeywords" value="${activity.tdkKeywords }">
							<span class="txt-suggest ml20">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
						</div>
					</div>
				</div>
				<!-- } SEO信息 -->

				<!-- 报名相关 { -->
				<div id="applyModule" class="contClassify">
					<h2 class="title">报名相关</h2>
					
					<div class="formLine">报名须知:</div>
					
					<div class="formLine">
						<h3 class="mudle-title">模块一</h3>
						<label>名称:</label>
						<input type="text" maxlength="15" class="w-200" name="name1" value="${enrollNotice.name1 }">
					</div>
					
					<div class="formLine">
						<label class="pos_r_t5">内容:</label>
						<%-- <span class="dataVal va_t">
							<textarea id="apply1" name="summary1" cols="80" rows="10" class="ckeditor" style="display:inline-block">${enrollNotice.summary1 }</textarea>
						</span> --%>
						<script id="apply1" class="ueEditor_cont" name="summary1"  style="height:500px;" type="text/plain">${enrollNotice.summary1 }</script>
					</div>
					
					<div class="formLine">
						<h3 class="mudle-title">模块二</h3>
						<label>名称:</label>
						<input type="text" maxlength="15" class="w-200" name="name2" value="${enrollNotice.name2 }">
					</div>
					
					<div class="formLine">
						<label class="pos_r_t5">内容:</label>
						<%-- <span class="dataVal va_t">
							<textarea id="apply1" name="summary2" cols="80" rows="10" class="ckeditor" style="display:inline-block">${enrollNotice.summary2 }</textarea>
						</span> --%>
						<script id="apply2" class="ueEditor_cont" name="summary2"  style="height:500px;" type="text/plain">${enrollNotice.summary2 }</script>
					</div>
					
					<div class="formLine">
						<h3 class="mudle-title">模块三</h3>
						<label class="pos_r_t5">名称:</label>
						<input type="text" maxlength="15" class="w-200" name="name3" value="${enrollNotice.name3 }">
					</div>
					
					<div class="formLine">
						<label>内容:</label>
						<%-- <span class="dataVal va_t">
							<textarea id="apply1" name="summary3" cols="80" rows="10" class="ckeditor" style="display:inline-block">${enrollNotice.summary3 }</textarea>
						</span> --%>
						<script id="apply3" class="ueEditor_cont" name="summary3"  style="height:500px;" type="text/plain">${enrollNotice.summary3 }</script>
					</div>
					
					<div class="formLine mt20">
						<label class="pos_r_t5">报名表单:</label>
						<div class="formLine-middle-group tableManage w-1024">
							<table id="applyForm" border="0" cellpadding="0" cellspacing="0" class="dataTable applyList">
								<thead>
									<tr>
										<th class="pl10">字段编号</th>
										<th>字段名称</th>
										<th>字段类型</th>
										<th>是否必填</th>
										<th>操作</th>
										<th class="w-24">
											<button id="addApply" type="button" title="添加" class="btn-add"></button>
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="obj" varStatus="sta" items="${listEnrollForm }">
										<!--  -->
										<tr>
											<td>${obj.sortNum==0?sta.index+1:obj.sortNum}</td>
											<td>${obj.property}<input type="hidden" class="enrollFrom_property" value="${obj.property }"></td>
											<td>${obj.propertyTypeName}<input
												type="hidden" class="enrollFrom_propertyTypeName" value="${obj.propertyTypeName }"><input 
												type="hidden" class="enrollFrom_propertyType" value="${obj.propertyType }"><input 
												type="hidden" class="enrollFrom_fie_option" value="${obj.propertyOptions }"><input 
												type="hidden" class="enrollFrom_req" value="${obj.isRequire }"><input
												type="hidden" class="enrollFrom_sortNum" value="${obj.sortNum }"><input
												type="hidden" class="enrollFrom_code" value="${obj.code }"></td>
											<td>${obj.isRequire==0?'否':'是'}</td>
											<td>
												<a class="applyInfo_edit">编辑</a>
												<a class="applyInfo_dele">删除</a>
											</td>
											<td></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
				</div><!-- } 报名相关 -->
				
				<!-- 报名费支付相关 { -->
				<div id="payModule" class="contClassify">
					<h2 class="title w-140">报名费支付相关</h2>

					<div class="formLine">
						<label>报名费用:</label>
						<input id="money" name="money" type="text" maxlength="" class="w-110 mr5" value="${activity.money }">元
						<span class="reqItem">*</span>
					</div>
				</div>
				<!-- }报名费支付相关 -->
				
				
				<!-- 投票相关 { -->
				<div id="voteModule" class="contClassify">
					<h2 class="title">投票相关</h2>
					
					<%-- <div class="formLine">
						<label>投票名称:</label>
						<input type="text" class="w-110" name="voteName" value="${activity.voteName }">
						<!-- <span class="reqItem">*</span> -->
					</div> --%>
					<div class="formLine">
						<label>投票名称:</label>
						<input id="voteName" name="voteName" type="text" maxlength="30" class="w-320" value="${activity.voteName }">
						<span class="reqItem">*</span>
					</div>
					
					<div class="formLine">
						<label>新建选项:</label>
						<div id="voteNum" class="optionChoose select-base">
							<i class="w-100">${fn:length(listVoteOption)}</i>
							<dl>
								<dt>1</dt>
								<dt>2</dt>
								<dt>3</dt>
								<dt>4</dt>
								<dt>5</dt>
								<dt>6</dt>
								<dt>7</dt>
								<dt>8</dt>
							</dl>
						</div>
						<span class="reqItem">*</span>
					</div>
					
					<!--对应显示的选项数-->
					<div class="optionSet formLine" style="margin-left:62px">
						<c:forEach var="obj" varStatus="sta" items="${listVoteOption }">
							<div class="formLine">
								<label>选项${sta.index+1}:</label>&nbsp;<input type="text" name="options" maxlength="" class="w-260 voteOption_option" value="${obj.options }">&nbsp;<span class="reqItem">*</span>
								<input type="hidden" class="voteOption_code" value="${obj.code }">
							</div>
						</c:forEach>
					</div>
					
					<div class="formLine">
						<label class="pos_r_t5">投票简介:</label>
						<%-- <span class="dataVal va_t">
							<textarea id="editor2" name="voteSummary" cols="80" rows="10" class="ckeditor" style="display:inline-block">${activity.voteSummary }</textarea>
						</span> --%>
						<script id="editor2" class="ueEditor_cont" name="voteSummary"  style="height:500px;" type="text/plain">${activity.voteSummary }</script>
						<span class="reqItem">*</span>
					</div>
					
				</div><!-- } 投票相关 -->
				
				<!-- 其他模块 { -->
				<div class="contClassify">
					<h2 class="title">其他模块</h2>
					
					<div class="otherMudle mt20">
						<!-- 添加按钮 -->
						<button id="addMudle" type="button" title="添加" class="btn-add"></button>
						
						<!-- 顶部模块名称 -->
						<div class="top">
							<div class="formLine">
								<label>模块名称:</label>
								<input id="mudleName-0" name="otherBlock" type="text" class="w-320" value="${activity.otherBlock }">
								
							</div>
						</div>
						
						<c:forEach var="obj" varStatus="sta" items="${listImage }">
							<div class="formGroup">
								<!-- 删除按钮 -->
								<button id="delMudle-${sta.index }" type="button" title="删除" class="btn-delete"></button>
								<!-- 左侧设置 -->
								<div class="left">
									<div class="formLine">
										<label>图片:</label>
										<%-- <button id="otherblockPickId${sta.index }" type="button" class="btn-base uploadImg">点击上传图片</button> --%>
										<div id="otherblockPickId${sta.index }" class="btn-base uploadImg">点击上传图片</div>
										<span class="txt-suggest">推荐尺寸为大于317 * 229的正方形</span>
									</div>
									<div class="formLine">
										<label>名称:</label>
										<input id="mud_name-${sta.index }" name="mud_name-${sta.index }" type="text" class="w-320 otherblock_name" value="${obj.name }">
									</div>
									<div class="formLine">
										<label>连接:</label>
										<input id="mud_link-${sta.index }" name="mud_link-${sta.index }" type="text" class="w-320 otherblock_hyperlink" value="${obj.hyperlink }">
									</div>
								</div>
								<!-- 右侧缩略图 -->
								<div class="right">
									<label>缩略图:</label>
									<img width="221" height="160" id="otherblockPreId${sta.index }" src="${ctx}${obj.url}" title="缩略图名称" alt="请上传图片" class="va_m">
									<input class="otherblock_url" type="hidden" id="otherblockUrlHiddenId${sta.index }" value="${obj.url}">
								</div>
							</div>
						</c:forEach>
						
					</div>
				</div>
				<!-- } 其他模块 -->


				<!-- 确认按钮 -->
				<%--
				<div class="formLine saveOper">
					<button type="button" class="btn-base mr30">修改</button>
					<button type="button" class="btn-base" onclick="back();">取消</button>
				</div>
				--%>

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
	<script src="${ctxManage}/resources/js/activity/activity-creat.js" type="text/javascript"></script>
	<script src="${ctxManage}resources/plugin/respond.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
	
	<!-- 实例化编辑器 -->
    <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.all.js"></script>
	<script type="text/javascript"> 
	    var ue1 = UE.getEditor('editor1',{autoHeightEnabled: false,initialFrameHeight: 350,maximumWords:20000});
	    var ue2 = UE.getEditor('editor2',{maximumWords:300,autoHeightEnabled: false,initialFrameHeight: 350});
	    var ap1 = UE.getEditor('apply1',{maximumWords:300,autoHeightEnabled: false,initialFrameHeight: 350});
	    var ap2 = UE.getEditor('apply2',{maximumWords:300,autoHeightEnabled: false,initialFrameHeight: 350});
	    var ap3 = UE.getEditor('apply3',{maximumWords:300,autoHeightEnabled: false,initialFrameHeight: 350});
	</script>
	<!-- jsp -->
	<script src="${ctx}manage/webuploaderbase/webuploader.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/webuploader/checkflash.js" type="text/javascript"></script>
	<script type="text/javascript">
		function back(){
			popupLayer(
				'',
				'<div style="width: 320px; text-align:center; margin: 0 auto;">是否返回？</div>',
				'<button type="button" class="btn-sure sure mr15">确定</button>' +
				'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				javascript:history.back(-1);
				closePopup();
			});
		}
	</script>
	
	<script type="text/javascript">
		// 计数器叠加
		COUNTER = ${fn:length(listImage)} || 1;
	</script>
	<!-- 上传组件 -->
	<script type="text/javascript">
	  	function addEvent(up, preId, urlHiddenId){
	  		up.on( 'uploadSuccess', function(file, response) {
				$("#"+urlHiddenId).val(response.filePath);
				$("#"+preId).attr("src", "${ctx}"+response.filePath);//预览img
				this.reset();
		    });
	  		up.on( 'uploadError', function(file, reason) {
	  			msgBox("上传格式错误", "info", 1200);
	  		});
	  		up.on( 'error', function(type) {
				//alert("错误信息:"+type);
				if(type=="Q_EXCEED_SIZE_LIMIT" || type=="F_EXCEED_SIZE"){
					msgBox("文件超过大小,请将上传文件大小控制在400kb以内", "erro");
				}else if(type=="Q_TYPE_DENIED"){
					msgBox("上传格式错误", "info", 1200);
	  			}else{
	  				msgBox("上传错误，请重试。", "info", 1200);
	  			}
		    });
	  	}
		function cteateUploder(pickId, preId, urlHiddenId, floderName, fileSingleSizeLimit, compress){
	  		var contextPath = "${ctx}";
	  		var options_={
				swf :  contextPath+'/manage/webuploader/Uploader.swf',
				server : contextPath+'/web/activityController/fileUpload',
				runtimeOrder : "flash",
				accept : {extensions : 'jpg,jpeg,bmp,png',mimeTypes: 'image/*'},
				formData:{
					activityCode: '${activityCode }',
					floderName: floderName
				},
				pick : {
					id:'#'+pickId,
					multiple:false
				},
				fileVal : 'files',
				auto : true,
				resize: false,
				fileSingleSizeLimit: fileSingleSizeLimit,
				compress: compress
			};
			addEvent(new WebUploader.Uploader(options_),preId,urlHiddenId);
		}
		var fileSingleSizeLimit = 1 * 400 * 1024;
		var img_compress = {
		    width: 317,
		    height: 229,
		    crop: true
		};
		var banner_compress = {
		    width: 1903,
		    height: 500,
		    crop: true
		};
		var other_compress = {
		    width: 317,
		    height: 229,
		    crop: true
		};
	</script>
	
	<!-- 其他模块添加事件 并初始化其组件 -->
	<script type="text/javascript">
		/* 其他模块——添加模块 */
		//$(document).on("click", "#addMudle", function() {
		/* $("#addMudle").click(function(){
			addOtherMudle(this, "${ctx}");
			// 消息提示
			msgBox("模块添加成功！", "info");
			//初始化其他模块图片上传组件
			var pickId="otherblockPickId" + COUNTER.OhterMud;
			var preId="otherblockPreId" + COUNTER.OhterMud;
			var urlHiddenId="otherblockUrlHiddenId" + COUNTER.OhterMud;
			cteateUploder(pickId, preId, urlHiddenId, "other", fileSingleSizeLimit, other_compress);
			// 计数器叠加
			COUNTER.OhterMud += 1;
		}); */
		$("#addMudle").click(function(){
			var mudle_len = $(".otherMudle").find(".formGroup").length;
			var allow_mudle_len = 60;
			if(mudle_len < allow_mudle_len) {
				addOtherMudle(this, "${ctx}");
				// 消息提示
				msgBox("模块添加成功！", "info");
				//初始化其他模块图片上传组件
				var pickId="otherblockPickId" + COUNTER;
				var preId="otherblockPreId" + COUNTER;
				var urlHiddenId="otherblockUrlHiddenId" + COUNTER;
				cteateUploder(pickId, preId, urlHiddenId, "other", fileSingleSizeLimit, other_compress);
				// 计数器叠加
				COUNTER += 1;
			}
			// 超过限制添加最大长度
			else {
				msgBox("最多只能添加" + allow_mudle_len + "个模块！", "erro");
				//creatErrMesg($("#mudleName-0").next(".reqItem"), "已超过添加的最大长度", "right");
				$("#mudleName-0").next(".reqItem").next(".errMesg").css({
					"left": "496px",
					"top": "3px"
				});
				return;
			}
		});
	</script>

	<!-- 初始化上传组件 -->
	<script type="text/javascript">
		//初始化banner上传组件
		ue1.addListener( 'ready', function() {
			cteateUploder("imgPickId", "imgPreId", "imgHiddenId", "img", fileSingleSizeLimit, img_compress);
			cteateUploder("bannerPickId", "bannerPreId", "bannerHiddenId", "banner", fileSingleSizeLimit, banner_compress);
		});
		//初始化第一个其他模块上传组件
		//cteateUploder("otherblockPickId0", "otherblockPreId0", "otherblockUrlHiddenId0", "other");
		var listImage = ${listImageJson};
		for (var i = 0; i < listImage.length; i++) {
			var obj = listImage[i];
			var pickId="otherblockPickId" + i;
			var preId="otherblockPreId" + i;
			var urlHiddenId="otherblockUrlHiddenId" + i;
			cteateUploder(pickId, preId, urlHiddenId, "other", fileSingleSizeLimit, other_compress);
		}
		
	</script>
	
	<!-- 保存前处理参数的 name属性 -->
	<script type="text/javascript">
		//报名表单部分
		function initEnrollFromName(){
			for(var i=0; i<$(".enrollFrom_property").length; i++){
				$(".enrollFrom_property").eq(i).attr("name","listEnrollForm["+i+"].property");
			}
			for(var i=0; i<$(".enrollFrom_propertyType").length; i++){
				$(".enrollFrom_propertyType").eq(i).attr("name","listEnrollForm["+i+"].propertyType");
			}
			for(var i=0; i<$(".enrollFrom_propertyTypeName").length; i++){
				$(".enrollFrom_propertyTypeName").eq(i).attr("name","listEnrollForm["+i+"].propertyTypeName");
			}
			for(var i=0; i<$(".enrollFrom_fie_option").length; i++){
				$(".enrollFrom_fie_option").eq(i).attr("name","listEnrollForm["+i+"].propertyOptions");
			}
			for(var i=0; i<$(".enrollFrom_req").length; i++){
				$(".enrollFrom_req").eq(i).attr("name","listEnrollForm["+i+"].isRequire");
			}
			for(var i=0; i<$(".enrollFrom_sortNum").length; i++){
				$(".enrollFrom_sortNum").eq(i).attr("name","listEnrollForm["+i+"].sortNum");
			}
			for(var i=0; i<$(".enrollFrom_code").length; i++){
				var sortNum = $(".enrollFrom_sortNum").eq(i).parent().prev().prev().text();
				$(".enrollFrom_sortNum").eq(i).val(sortNum);
				$(".enrollFrom_code").eq(i).attr("name","listEnrollForm["+i+"].code");
			}
		}
		//其他模块部分
		function initOtherBlockName(){
			for(var i=0; i<$(".otherblock_name").length; i++){
				$(".otherblock_name").eq(i).attr("name","listImage["+i+"].name");
			}
			for(var i=0; i<$(".otherblock_hyperlink").length; i++){
				$(".otherblock_hyperlink").eq(i).attr("name","listImage["+i+"].hyperlink");
			}
			for(var i=0; i<$(".otherblock_url").length; i++){
				$(".otherblock_url").eq(i).attr("name","listImage["+i+"].url");
			}
		}
		//选项部分
		function initVoteOptionName(){
			var ops = $(".voteOption_option");
			for(var i=0; i<ops.length;i++){
				var op = ops.eq(i);
				op.attr("name","listVoteOption["+i+"].options");
				op.nextAll("input").attr("name","listVoteOption["+i+"].code");
			}
		}
		
	</script>
	
	<script type="text/javascript">
		function save(){
			initVoteOptionName();
			initEnrollFromName();
			initOtherBlockName();
			$("input[type='file']").attr('disabled',"true");
			var form = $("#saveForm");
			form.attr("target", "_self");
			form.attr("action", "${ctx }web/activityController/editActivity");
			ispost  = true;
			form.submit();
		}
		function pre(){
			initEnrollFromName();
			initOtherBlockName();
			//$("input[type='file']").attr('disabled',"true");
			var form = $("#saveForm");
			//form.attr("target", "_blank");
			form.attr("target", "_preActivity");
			form.attr("action", "${ctx }web/activityController/previewActivity");
			ispost  = true;
			form.submit();
		}
		
	</script>
	
	<script type="text/javascript">
		/**** 数据有效性验证 ****/
		 // 活动名称
		$("[name='name']").blur(function () {
			var this_val = $(this).val(),
				testPass = $.VLDTOR.IsArticle(this_val),
				lenRange = inputRange(this, 2, 30);
			// 验证信息方法
			valid_txtBox(this, testPass && lenRange, "只能是长度为2-30位的中文、英文和数字", "top");
		});
	
		 // 序号
		$("[name='sortNum']").blur(function () {
			var this_val = $(this).val(),
				testPass = $.VLDTOR.IsNum(this_val),
				lenRange = inputRange(this, 1, 7);
			valid_txtBox_create(this, testPass && lenRange, "只能为1-7位正整数", "top");
		});
	
		$("#startDate").blur(function () {
			var begin_val = $(this).val(),
					begin_val_num = parseInt(begin_val.replace(/-/ig,"")),
					end_val = $("#endDate").val(),
					end_val_num = parseInt(end_val.replace(/-/ig,""));
			if((begin_val_num > end_val_num) && $.trim(begin_val) != "" && $.trim(end_val_num) != "") {
				valid_txtBox(this, false, "开始日期不能晚于结束日期", "top");
			} else if((begin_val_num <= end_val_num) && $.trim(begin_val) != "" && $.trim(end_val_num) != "") {
				modifErrMesg(this);
				modifErrMesg("#endDate");
			}
		});
		$("#endDate").blur(function () {
			var end_val = $(this).val(),
					end_val_num = parseInt(end_val.replace(/-/ig,"")),
					begin_val = $("#startDate").val(),
					begin_val_num = parseInt(begin_val.replace(/-/ig,""));
			if((begin_val_num > end_val_num) && $.trim(begin_val) != "" && $.trim(end_val_num) != "") {
				valid_txtBox(this, false, "结束日期不能早于开始日期", "top");
			} else if((begin_val_num <= end_val_num) && $.trim(begin_val) != "" && $.trim(end_val_num) != "") {
				modifErrMesg(this);
				modifErrMesg("#startDate");
			}
		});
	
		 // 开始/结束时间
		$("#startDate, #endDate").blur(function () {
			var todayDate = new Date(),
					cre_year = todayDate.getFullYear(),
					cre_moon = todayDate.getMonth() + 1,
					cre_moon_str = cre_moon.toString(),
					cre_date = todayDate.getDate(),
					cre_date_str = cre_date.toString(),
					cre_day = "";
			if(cre_moon_str.length == 1) {
				cre_moon_str = "0" + cre_moon_str;
			}
			if(cre_date_str.length == 1) {
				cre_date_str = "0" + cre_date_str;
			}
			cre_day = parseInt(cre_year + cre_moon_str + cre_date_str);
	
			var this_val = $(this).val(),
				this_val_num = parseInt(this_val.replace(/-/ig,"")),
				testPass = $.VLDTOR.IsDate(this_val);
			if($.trim(this_val) == "") {
				valid_txtBox(this, false, "请选择一个日期", "top");
			} else if(this_val_num < cre_day) {
				valid_txtBox(this, false, "选择的日期不能早于今天", "top");
			} else {
				valid_txtBox(this, testPass, "日期格式错误（建议使用控件选择）", "top");
			}
		});
	
	
		 // 报名费用
		$("[name='money']").blur(function () {
			var this_val = $(this).val(),
				testPass = $.VLDTOR.IsPrice(this_val)  && inputRange(this, 1, 7),
				// 是否勾选报名付费
				isEnrollPay_ckd = $("[name='isEnrollPay']").prop("checked");
			if (isEnrollPay_ckd) {
				valid_txtBox(this, testPass, "只能为正整数", "right");
			} else {
				removeErrMesg(this);
			}
		});
	
		 // 投票名称
//		$("#voteName").blur(function () {
//			var this_val = $(this).val(),
//				testPass = $.VLDTOR.IsArticle(this_val),
//				lenRange = inputRange(this, 2, 30),
//				// 是否勾选投票选项
//				isVote_ckd = $("[name='isVote']").prop("checked");
//			if (isVote_ckd) {
//				valid_txtBox_create(this, testPass && lenRange, "只能是长度为2-30位的中文、英文和数字常用符号", "right");
//			} else {
//				removeErrMesg(this);
//			}
//		});
		// 投票名称
		$("#voteName").blur(function () {
			var this_val = $(this).val(),
					testPass = $.VLDTOR.IsArticle(this_val),
					lenRange = inputRange(this, 2, 30);
			valid_txtBox(this, testPass && lenRange, "只能是长度为2-30位的中文、英文和数字常用符号", "right");
		});
	
		 // 新建投票选项
		$(document).on("blur", "[name='options']", function () {
			var this_val = $(this).val(),
				testPass = $.VLDTOR.IsArticle(this_val) && inputRange(this, 2, 30),
					vote = $("[name='isVote']"),
					vote_isCkd = vote.prop("checked");
			if(vote_isCkd) {
				valid_txtBox(this, testPass, "只能为2-30位的数字、字母、中文及常见符号", "right");
			} else {
				removeErrMesg(this);
			}
		});
	
		 // 报名须知——模块一、二、三
		$("[name='name1'],[name='name2'],[name='name3']").blur(function () {
			var this_val = $(this).val(),
				testPass = $.VLDTOR.IsArticle(this_val),
				lenRange = inputRange(this, 2, 30);
			valid_txtBox_create(this, testPass && lenRange || $.trim(this_val) == "", "只能是长度为2-30位的中文、英文和数字常用符号", "right");
		});
	
		 // 报名表单设置——字段编号
		$(document).on("blur", "[id='fieldNumber']", function () {
			var this_val = $(this).val(),
					testPass = $.VLDTOR.IsNum(this_val) && this_val>0,
					tabel_tr = $("#applyForm").find("tr"),
					tabel_num_len = tabel_tr.length;

			// 遍历出同名字段编号
			/* for(var i = 0; i < tabel_num_len - 1; i++) {
				var tr_class = tabel_tr.eq(i + 1).attr("class"),
					this_num = $.trim(tabel_tr.eq(i + 1).children("td:eq(0)").text());
				if(tr_class == "editting") {
					break;
				} else if(this_num == this_val) {
					valid_txtBox(this, false, "字段编号不能重复", "top");
					return;
				}
			} */

			// 验证不通过
			valid_txtBox(this, testPass, "编号只能为大于0的数字", "top");
		});
	
		 // 报名表单设置——字段名称
		$(document).on("blur", "[id='fieldName']", function () {
			var this_val = $(this).val(),
				testPass = $.VLDTOR.IsEnCnNum(this_val),
				lenRange = inputRange(this, 2, 30),
					tabel_tr = $("#applyForm").find("tr"),
					tabel_fied_len = tabel_tr.length;
	
			// 遍历出同名字段名称
			for(var i = 0; i < tabel_fied_len - 1; i++) {
				var tr_class = tabel_tr.eq(i + 1).attr("class");
				var this_fied = $.trim(tabel_tr.eq(i + 1).children("td:eq(1)").text());
				if(tr_class == "editting") {
					continue;
				}else if(this_fied == this_val) {
					valid_txtBox(this, false, "字段名称已经存在", "top");
					return;
				}
			}
			// 验证不通过
			valid_txtBox(this, testPass && lenRange, "只能是长度为2-30位的中文、英文和数字", "top");
		});
	
		 // 报名表单设置——字段类型（消除验证信息）
		$(document).on("click", "#addFiedType dt", function () {
			var this_hasErro = $(this).parents("#addFiedType").next(".errMesg").length > 0;
			// 消除验证错误消息
			if(this_hasErro) {
				$(this).parents("#addFiedType").next(".errMesg").remove();
				$(this).parents("#addFiedType").after('&nbsp;<span class="reqItem">*</span>');
			}
		});
	
		 // keywords
		$("[name='keywords']").blur(function () {
			var this_val = $(this).val(),
				isNull = $.trim(this_val) == "",
				testPass = $.VLDTOR.IsEnCnNum_comma(this_val),
				lenRange = inputRange(this, 2, 20);
			// 验证不通过
			if ((!testPass || !lenRange) && !isNull) {
				creatErrMesg(this, "只能是长度为2-20位的中英文、数字和英文逗号（选填）", "right");
			}
			// 验证通过
			else {
				removeErrMesg(this);
			}
		});
	
		 // 其他模块名称
		$("#mudleName-0").blur(function () {
			var this_val = $(this).val(),
				testPass = $.VLDTOR.IsEnCnNum(this_val),
				lenRange = inputRange(this, 2, 30),
				// 是否勾选投票选项
				isVote_ckd = $("[name='isVote']").prop("checked");
			if (isVote_ckd) {
				valid_txtBox(this, (testPass && lenRange)||this_val=="", "只能是长度为2-30位的中文、英文和数字", "right");
			} else {
				removeErrMesg(this);
			}
		});
	
		 // 新建其他模块名称
		$(document).on("blur", "[id^='mud_name-']", function () {
			var this_val = $(this).val(),
				testPass = $.VLDTOR.IsEnCnNum(this_val),
				lenRange = inputRange(this, 2, 30),
				// 是否勾选投票选项
					isVote_ckd = $("[name='isVote']").prop("checked");
			// 验证不通过
			if (isVote_ckd) {
				valid_txtBox(this, (testPass && lenRange)||this_val=="", "只能是长度为2-30位的中文、英文和数字", "right");
			} else {
				removeErrMesg(this);
			}
		});
	
		 // 新建其他模块链接
		$(document).on("blur", "[id^='mud_link-']", function () {
			var this_val = $(this).val(),
				isNull = $.trim(this_val) == "",
				testPass = $.VLDTOR.IsHTTP(this_val);
	
			// 验证不通过
			if (!testPass && !isNull) {
				creatErrMesg(this, "请输入以http(s)://开头的格式", "right");
			}
			// 验证通过
			else {
				removeErrMesg(this);
			}
		});
	
		/**** 富文本验证 ****/
	
		// 活动简介
		ue1.addListener( 'contentChange', function() {
			valid_richTxt(this,"#editor1",2,20000,"内容必须在2-20000个字符之间");
		});
		ue1.addListener( 'focus', function() {
			valid_richTxt(this,"#editor1",2,20000,"内容必须在2-20000个字符之间");
		});
	
		// 投票简介
		ue2.addListener( 'contentChange', function() {
			valid_richTxt(this,"#editor2",2,300,"内容必须在2-300个字符之间");
		});
		ue2.addListener( 'focus', function() {
			valid_richTxt(this,"#editor2",2,300,"内容必须在2-300个字符之间");
		});
	
		// 报名须知一
		UE.getEditor('apply1').addListener( 'focus', function() {
			var ueCont = this.getContentTxt();
			if(ueCont != "") {
				valid_richTxt(this, "#apply1", 2, 300, "内容必须在2-300个字符之间");
			}
		});
	
		// 报名须知二
		UE.getEditor('apply2').addListener( 'focus', function() {
			var ueCont = this.getContentTxt();
			if(ueCont != "") {
				valid_richTxt(this, "#apply2", 2, 300, "内容必须在2-300个字符之间");
			}
		});
	
		// 报名须知三
		UE.getEditor('apply3').addListener( 'focus', function() {
			var ueCont = this.getContentTxt();
			if(ueCont != "") {
				valid_richTxt(this, "#apply3", 2, 300, "内容必须在2-300个字符之间");
			}
		});
		/* SEO 验证 */
		$(".seoInfo").blur(function () {
			var $this = $(this),
				thisVal = $this.val();
			valid_txtBox_create(this, ($.VLDTOR.IsEnCnNum_comma(thisVal) && inputRange(this,2,20)) || thisVal == "",'只能为空或字母、数字及中文，且长度在2-20','top');
		});
		/**** 提交保存验证 ****/
		function submitForm(call_){
			// 检查投票是否选中
			var vote = $("[name='isVote']"),
					vote_isCkd = vote.prop("checked");
			// 投票选中时的验证
			if(vote_isCkd) {
				var voteOption = $("#voteNum"),
						voteOpt_txt = voteOption.children("i").text();
				if(voteOpt_txt == "选项个数") {
					valid_txtBox("#voteNum", false, "请选择投票选项个数", "right");
				}
			} else {
				modifErrMesg("#voteNum");
			}

			var hasApplyFormItem = $("#applyForm").children("tbody").find("tr").length > 0;
//			if(hasApplyFormItem) {
//				removeErrMesg("#applyForm");
//			} else {
			valid_txtBox_create(".tableManage", hasApplyFormItem, "报名表单至少需要添加一项", "bottom");
//			}
	
			// 失焦验证
			$("[name='name'],[name='sortNum'], #startDate, #endDate, [name='money'], [name='voteName'], [name='options'], [name='name1'],[name='name2'],[name='name3'], [name='keywords'], #mudleName-0, [id^='mud_name-'], [id^='mud_link-']").blur();
			/* SEO 验证 */
			$(".seoInfo").blur();
			// 富文本验证
			var ueCont1 = ue1.getContentTxt(),
					ueCont2 = ue2.getContentTxt();
			if(ueCont1 == "") {
				valid_txtBox_create("#editor1", false, "内容必须在2-5000个字符之间", "top-right");
			}
			if(ueCont2 == "") {
				valid_txtBox_create("#editor2", false, "内容必须在2-300个字符之间", "top-right");
			}
	
			// 时间控件选择时间判断
			var sDate = $("#startDate").val(),
				sDate_num = sDate.replace(/\-/g,""),
				eDate = $("#endDate").val(),
				eDate_num = eDate.replace(/\-/g,"");
			if(sDate == "" || eDate == "") {
				if(sDate == "") {
					valid_txtBox("#startDate", false, "请选择开始时间", "top");
				} else {
					valid_txtBox("#endDate", false, "请选择结束时间", "top");
				}
			} else if(eDate_num < sDate_num) {
				valid_txtBox("#startDate", false, "开始时间不能晚于结束时间", "top");
				valid_txtBox("#endDate", false, "结束时间不能早于开始时间", "top");
			} else {
				modifErrMesg("#startDate, #endDate");
			}
			
			// 其他图片上传验证
			/* var hiddenInput = $("input.otherblock_url");
			for(var i = 0; i < hiddenInput.length; i++) {
				var buttonNode = hiddenInput.eq(i).parents('.formGroup').find("[id^=otherblockPickId]")[0];
				if(hiddenInput.eq(i).val() == ""){
					creatErrMesg(buttonNode,"请上传一张图片","top");
				} else {
					removeErrMesg(buttonNode);
				}
			} */

			// 剔除隐藏的错误项
			var erro_len = $("span.errMesg").length,
					showErroNum = 0;
			for(var i = 0; i < erro_len; i++) {
				var erro_info = $("span.errMesg").eq(i),
						erro_disp = $(erro_info).parents(".contClassify").css("display");
				if(erro_disp != "none") {
					showErroNum++;
				}
			}

			// 检查错误项
			var erro = showErroNum > 0;
	
			// 含有错误
			if (erro) {
				// 消息盒子
				msgBox("填写的信息有误，请检查", "erro");
				return false;
			}
			// 通过验证
			else {
				if($("#money").val()==""){
					$("#money").val(0);
				}
				// 提交表单
				msgBox("添加成功！", "pass");
				call_();
			}
		}
		/* $(".saveOper button:first").click(function () {
			if(submitForm(save)) {
				msgBox("保存成功！", "pass");
			}
		});
		$(".save1").click(function () {
			if(submitForm(save)) {
				msgBox("保存成功！", "pass");
			}
		});
		$(".preview").click(function () {
			submitForm(pre);
		}); */
		$(".saveOper button:first").click(function () {
			msgBox("保存成功！", "pass");
			submitForm(save);
		});
		$(".save1").click(function () {
			ispost=true;	
			msgBox("保存成功！", "pass");
			submitForm(save);
		});
		$(".preview").click(function () {
			ispost=true;
			submitForm(pre);
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
