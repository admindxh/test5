<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
		<title>系统设置-运营管理-广告位管理-广告区修改</title>
		<%@include file="/common-html/common.jsp" %>
		<link rel="stylesheet"
			href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
		<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
		<link rel="stylesheet"
			href="${ctxManage}/resources/css/home/imgDescr.css" />
		<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/plugin/respond.min.js"
			type="text/javascript"></script>
			 <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
	</head>
	<body>
		<!-- main { -->
		<div class="main">
				<!-- 页面位置-->
				<div class="location">
					<label>
						您当前位置为:
					</label>
					<span><a>系统设置</a> -</span>
					<span><a>运营管理</a> -</span>
					<span><a>广告位管理</a> -</span>
					<span><a>广告区修改</a> </span>
				</div>

				<!-- 模块管理 { -->
				<div class="muduleManage filament_solid_ddd">
					<div class="searchManage">
						<button type="button" id="save" class="saveSet btn-sure">
							保存
						</button>
					</div>
					<form method="post" action="updateAdArea"  id="adverForm" name="adverForm">
						<div class="show">
							<div id="content"></div>
						<!-- 广告位管理 { -->
						<input type="hidden" name="codes" id="deleteCodes">
						<input type="hidden" name="proCode" id="proCode" value="${proCode}">
					  <c:if test="${empty ads}">
					  <div class="wrapImg">
							<div class="posidSet-unit">
								<i>广告位${status.index+1}</i>
								<div class="left perc60">
									<div class="formLine">
										<label>图片:</label>
										<div class="btn-uploadImg pickfiles imgNode" type="button" id="pic${status.index+1}">请上传图片</div>
										<span class="txt-suggest ml20">推荐尺寸：1024 * 768</span>
									</div>
									<div class="formLine">
										<label>链接至:</label>
										<input class="toLink clear" id="" name="urls"   type="text" maxlength="100" value="${d.url }">
										<span class="reqItem">*</span>
									</div>
								</div>
								<div class="right perc40">
									<label>缩略图:</label>
									<%-- <img  id="pre${status.index+1}" src="<c:if test="${not empty d.imgurl}">${ctx}/</c:if>${d.imgurl}" 
										title="缩略图名称" alt="请上传图片" class="style-d  imgclear"> --%>
									<img id="pre${status.index+1}" src="${ctxManage}/resources/img/ele/loadImg_default_d.jpg" title="缩略图名称" alt="请上传图片" class="style-d imgclear">
									<input type="hidden" class="url  clear" name="imgUrls" value="${d.imgurl}"  id="hiddenId${status.index+1}"/>
								</div>
								<div class="addImgDescr">
									<button type="button" class="btn-base delDesc">删除</button>
							    </div>
							</div>
						</div>
						</c:if>
						   <c:forEach items="${ads}" var="d" varStatus="status">
							   <div class="wrapImg">
							 		<div class="posidSet-unit">
										<i>广告位${status.index+1}</i>
										<div class="left perc60">
											<div class="formLine">
												<label>图片:</label>
												<div class="btn-uploadImg pickfiles imgNode" type="button" id="pic${status.index+1}">请上传图片</div>
												<span class="txt-suggest ml20">推荐尺寸：1024 * 768</span>
											</div>
											<div class="formLine">
												<label>链接至:</label>
												<input class="toLink clear" id="" name="urls"   type="text" maxlength="100"
													value="${d.url }">
												<span class="reqItem">*</span>
											</div>
										</div>
										<div class="right perc40">
											<label>缩略图:</label>
											<img  id="pre${status.index+1}" src="${ctx}/${d.imgurl}" 
												title="缩略图名称" alt="请上传图片" class="style-d  imgclear">
												<input type="hidden" class="url  clear" name="imgUrls" value="${d.imgurl}"  id="hiddenId${status.index+1}"/>
												<input type="hidden" class="url  clear" name="codes2" value="${d.code}" />
										</div>
									</div>
									<div class="addImgDescr" code="${d.code }">
										<button type="button" class="btn-base delDesc">删除</button>
								    </div>
							   </div>
						   </c:forEach>
							<div class="addImgDescr" >
								<button id="addDesc" type="button" onclick="addImg()" class="btn-base">增加</button>
							</div>
					
						</div>
					</form>
				</div>
				<!-- } 广告位管理 -->
		</div>
		<!-- } 模块管理 -->
		<!-- JS引用部分 -->
		<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
			type="text/javascript"></script>
		<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
		<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
		<script src="${ctxManage}/resources/js/dataValidation.js"></script>
		<script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
		<script>
			//全局计数器
			var gloab_unit_len = (${fn:length(ads)}==0)?1:${fn:length(ads)};
			$(function(){
				//删除事件
				$(document).on('click', 'button.delDesc', function(){
					var codeee=$(this).parent().attr("code");
				    var $wrapImg = $(this).parents('.wrapImg');
				    var $wrapper = $wrapImg.siblings('.wrapImg');
				    var imgUrls=document.getElementsByName("imgUrls");
					if($wrapper.length === 0){
					    var procode=$('#proCode').val();
					    var src= $wrapImg.find('.imgclear').attr('src');
					 	if(src!=''){
					  		var con=window.confirm("确定删除此广告位相关资源？");
				  			if(con){
						  		if($("#deleteCodes").val()!=""){
									$("#deleteCodes").val($("#deleteCodes").val()+",")
								}
							  	$("#deleteCodes").val($("#deleteCodes").val()+codeee);
							  	$.ajax({
					     			type : "post",
					     			url : "<%=basePath%>web/adArea/deleteAdArea",
					     			data : {proCode:procode,codes:$("#deleteCodes").val()},
					     			dataType : "json",
					     			async : true,
					     			success : function(data) {
					     				//alert(data.data);
					     				if(data.data=='1'){
					     					$wrapImg.remove();
					     					$("#addDesc").click();
			     			          		/* $wrapImg.find('.imgclear').attr('src','');
				        			      	$wrapImg.find('.toLink').attr('value','');
				        			      	msgBox("图片删除成功", "pass", 2600);
				        			      	for(var i=0;i<imgUrls.length;i++){
							                	imgUrls[i].value='';
							          		} */
				        		     	}else{
				        		          	msgBox("图片删除失败", "erro", 2600);
				        		     	}
			     	        		}
				     			});
		 					}		
				   		}		
					}else{
				 		var con=window.confirm("确定删除此广告位？");
				 		if(con){
				  			if($("#deleteCodes").val()!=""){
								$("#deleteCodes").val($("#deleteCodes").val()+",");
							}
					  		$("#deleteCodes").val($("#deleteCodes").val()+codeee);
							$wrapper.each(function(i, item){
								$(item).find('.posidSet-unit>i').text('广告位' + (i+1))
							});
							$wrapImg.remove();
					 	}
					}
				})
			})
		</script>
		<!-- } main -->
		<script type="text/javascript">
				function addImg(){
					var ad_len = $(".posidSet-unit").length;
					var unit_len = gloab_unit_len;
                    //添加图片 
					var imgs = '<div class="wrapImg"><div class="posidSet-unit">' +
					'<i>广告位' + (ad_len+1) + '</i>' +
					'<div class="left perc60">' +
						'<div class="formLine">' +
							'<label>图片:</label>&nbsp;' +
							'<div class="btn-uploadImg pickfiles imgNode" type="button" id="pic'+(unit_len + 1)+'">点击上传图片</div>' +
							'<span class="txt-suggest ml20" style="margin-left: 20px;">推荐尺寸：1024 * 768</span>' +
						'</div>' +
						'<div class="formLine">' +
							'<label>链接至:</label>&nbsp;' +
							'<input type="text" value="" maxlength="100"  name="urls" id="" class="toLink">' +
							'&nbsp;<span class="reqItem">*</span>' +
						'</div>' +
					'</div>' +
					'<div class="right perc40">' +
						'<label>缩略图:</label>' +
						'<img class="style-d" alt="请上传图片" id="pre'+(unit_len + 1)+'" title="缩略图名称" src="${ctxManage}/resources/img/ele/loadImg_default_d.jpg">' +
						'<input type="hidden" class="url  clear" id="hiddenId'+(unit_len + 1)+'" name="imgUrls" value="" />'+
					'</div>' +
				'</div><div class="addImgDescr"><button type="button" class="btn-base delDesc">删除</button></div></div>';
				//$(".wrapImg:last").after(imgs);
				$(".addImgDescr #addDesc").parent().before(imgs);
				cteateUploder('pic'+(unit_len + 1),'pre'+(unit_len + 1),'hiddenId'+(unit_len + 1));
				gloab_unit_len++;
			}
		 function check(){
		     //.log(1);
	         var urls=document.getElementsByName("urls");
	         var imgUrls=document.getElementsByName("imgUrls");
	         for(var i=0;i<urls.length;i++){
	           if(urls[i].value==''){
	              msgBox("链接不能为空！", "erro", 2600);
	              urls[i].focus();
	              return false;
	           }else{
                var reg=/^[^\s\u4e00-\u9fa5]*$/;
                
                if(!reg.test(urls[i].value)){
                   msgBox("链接格式不正确！", "erro", 2600);
	               urls[i].focus();
	               return false;
                }	           
	           }
	         }
	        for(var i=0;i<imgUrls.length;i++){
	           if(imgUrls[i].value==''){
	              msgBox("有图片未上传！", "erro", 2600);
	              return false;
	           }
	         }
	      }
		</script>
		
		<script type="text/javascript">
		
				$(function(){
					var unit_len = $(".posidSet-unit").length;
					for(var i= 1;i<=unit_len;i++){
						cteateUploder('pic'+i,'pre'+i,'hiddenId'+i);
						}
			   })
		//======================================
		//				数据验证
		//======================================
		
		// 链接
		$(".toLink").blur(function() {
			var this_val = $(this).val(),
				reg_val = $.VLDTOR.IsWebUrl(this_val);
			// 验证信息方法
			valid_txtBox(this, reg_val && this_val != "", "链接只能为字母、数字等常用字符", "right");
		});
		
		// 保存验证
		$("#save").click(function() {
			// 失焦验证
			$(".toLink").blur();
           var c= check();
           if(c==false){
             return;
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
				msgBox("修改成功！", "pass");
				$("#adverForm").submit();
			}
		}); 
		</script>
		<!-- 上传组件 -->
	<script type="text/javascript">
	  	function addEvent(up, preId, urlHiddenId){
	  		up.on( 'uploadSuccess', function(file, response) {
				$.ajax( {
					type:"post",
					url : contextPath+"/web/imageController/getUrl",
					dataType : "text",
					async : false,
					success : function(data) {
					$("#"+urlHiddenId).val(data);
					$("#"+preId).attr("src", "${ctx}"+data);//预览img
					up.reset();
				},
				error : function() {
					msgBox("数据异常！", "erro");
				}
				});
		    });
	  		 up.on('error', function(handler) {
				if (handler == "Q_EXCEED_NUM_LIMIT") {
					msgBox("超出最大张数！", "erro");
				}
				else if(handler=="Q_TYPE_DENIED"){
		  			msgBox("上传格式错误", "erro");
				}
				else if (handler == "F_DUPLICATE") {
					msgBox("文件重复！", "erro");
				}
		
				else if (handler == "Q_EXCEED_SIZE_LIMIT") {
					msgBox("文件超过大小,请将上传文件大小控制在400kb以内", "erro");
				}
			});
	  	}
		function cteateUploder(pickId, preId, urlHiddenId){
	  		var contextPath = "${ctx}";
	  		var options_={
				swf :  contextPath+'/manage/webuploader/Uploader.swf',
				server : contextPath+'/web/imageController/uploadImage',
				runtimeOrder : "flash",
				accept : {extensions : 'jpg,jpeg,bmp,png,gif'},
				pick : {
					id:'#'+pickId,
					multiple:false
				},
				fileVal : 'file',
				auto : true,
				resize: false,
				// 验证文件总数量, 超出则不允许加入队列x
				fileNumLimit : 1,
				fileSizeLimit : 400 * 1024
			};
			addEvent(new WebUploader.Uploader(options_),preId,urlHiddenId);
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
