<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
    <title>门户首页-景点管理</title>
	<%@include file="/common-html/common.jsp" %>
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css" /> 
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
    <style>
    	#form6{display:none;}
    </style>
  </head>
  
<body>
    <!-- main { -->
    <div class="main">
			<!-- 页面位置-->
			<div class="location">
				<label>您当前位置为:</label>
				<span><a>门户首页</a> -</span>
				<span><a href="#" target="_self">景点管理</a></span>
			</div>
			
			<!-- 模块管理 { -->
			<div class="muduleManage filament_solid_ddd">
				<!-- 查看按钮 -->
				<div class="searchManage">
					<button type="button" class="lookup btn-sure" onclick="javascript:window.open('${ctx}')">查看</button>
				</div>

				<!-- 景点设置 { -->
				<div class="show">
				 <!-- form1 -- form 6{ -->
				 <c:forEach var="x" begin="1" end="6" step="1" varStatus="status">
				  <form action="${ctx }web/homeController/saveManageImg" method="post" enctype="multipart/form-data" id="form${x}">
					<div class="posidSet-unit">
						<i>图片${x}</i>
						<div class="left">
							<div class="formLine">
								<label>图片${x}:</label>
								<div id="pickfiles${x}" class="btn-uploadImg pickfiles imgNode">请上传图片</div> 
								<span class="txt-suggest ml20">
                                    <c:choose>
                                        <c:when test="${status.index == 1}">
                                            推荐尺寸：250 * 300
                                        </c:when>
                                        <c:when test="${status.index == 2}">
                                            推荐尺寸：200 * 244
                                        </c:when>
                                        <c:when test="${status.index == 3}">
                                            推荐尺寸：300 * 270
                                        </c:when>
                                        <c:when test="${status.index == 5}">
                                            推荐尺寸：280 * 280
                                        </c:when>
                                        <c:otherwise>
                                            推荐尺寸：140 * 72
                                        </c:otherwise>
                                    </c:choose>
                                </span>
							</div>
							<div class="formLine dropdown">
								<label>显示景点:</label>
								<div class="select-base">
									<input id="destinationCode${x}" type="hidden" value="${imageList[0][x-1].destinationCode }" name="destinationCode"/>
								    <i class="w-140"><c:if test="${empty imageList[0][x-1].name}">请选择地区</c:if><c:if test="${not  empty imageList[0][x-1]}">${imageList[0][x-1].summary }</c:if></i>
									<dl> 
										<c:forEach var="destination" items="${destinationList}">
											<dt name="${destination.code}"  count="${x}" myMethod="ajaxGetViewByDescode"  inputValue="${destination.code}" inputId="destinationCode${x}">${destination.destinationName}</dt>
										</c:forEach>
									</dl>
								</div>
								<div class="select-base ml-10">
								    <input id="viewCode${x}" type="hidden" value="${imageList[0][x-1].viewCode }" name="viewCode"/>
									<i id="viewSelectI${x}" class="w-140"  onclick="ajaxGetViewByDescodeChose(this,'${x }')"><c:if test="${empty imageList[0][x-1]}">请选择景点</c:if><c:if test="${not  empty imageList[0][x-1]}">${imageList[0][x-1].name }</c:if></i>
									<dl id="appendView${x}">
										<!--  <c:forEach var="view" items="${viewList}">
								            	<dt count="${x}" inputValue="${view.code}" inputId="viewCode${x}">${view.viewName}</dt>
								        </c:forEach>  -->
							        </dl>
								</div>
							</div>
						</div>
						<div class="right">
							<label>缩略图:</label>
							<img id="imgshow${x}"
                            <c:choose>
                                <c:when test="${status.index == 1}">
                                     style="width:125px;height:150px;"
                                </c:when>
                                <c:when test="${status.index == 2}">
                                     style="width:120px;height:146px;"
                                </c:when>
                                <c:when test="${status.index == 3}">
                                     style="width:150px;height:135px;"
                                </c:when>
                                <c:when test="${status.index == 6}">
                                     style="width:140px;height:140px;"
                                </c:when>
                                <c:otherwise>
                                     style="width:130px;height:144px;"
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${empty imageList[0][x-1].url}">
                                src="${ctx}//portal/static/default/square.png"
                            </c:if>
                            <c:if test="${not empty imageList[0][x-1].url}">
                                src="${ctx}/${imageList[0][x-1].url }"
                            </c:if>
							 title="缩略图名称" alt="请上传图片">
						    <input type="hidden" class="url" name="url" id="img_hiddenUrl${x}"
						    
						    <c:if test="${empty imageList[0][x-1].url}">
						    		value="/portal/static/default/square.png"
						    	</c:if>
						    	<c:if test="${not empty imageList[0][x-1].url}">
						    		value="${imageList[0][x-1].url }"
						    	</c:if>	
						    
						     />
						</div>
					</div>
					<input type="hidden" name="mutiCode" id="muti" value="${mutiList[0].code }"/>
             		<input type="hidden" name="code" value="${imageList[0][x-1].code }"/>
             		<input type="hidden" name="isPreview" value="1"/>
					</form>
					</c:forEach>
                 <!-- }from1 -- from6 -->
				 
					<!-- 保存 -->
					<div class="saveOper">
				     	<button type="button" class="btn-sure mr30"  onclick="submit(0)">预览</button>
						<button id="save" type="button" class="save btn-sure" onclick="submit(1)">保存</button>
					</div>
				</div><!-- } 景点设置 -->
					
			</div><!-- } 模块管理 -->
    </div><!-- } main -->

    <!-- JS引用部分 -->
    
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
    <%-- <script src="${ctxManage}/webuploader/upload.js" type="text/javascript"></script> --%>
    
    	<script src="${ctxManage}/resources/js/dataValidation.js"></script>
	<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
	<script type="text/javascript">
		
	</script>
	<script>
	// 如果有错误提示，则下拉菜单选择后将其消除
	$(document).on('click','.select-base dl dt', function () {
		var errMsg = $(this).parents('.select-base').next('span.errMesg');
		var errMsgLen = $(this).parents('.select-base').next('span.errMesg').length > 0;
		if(errMsgLen){
			errMsg.remove();
		}
	});
	//初始化目的地下拉框
	$(function(){
		var divs = $(".select-base");
		for(var i=0; i < divs.length; i++){
			var div = divs.eq(i);
			var value = div.find("input").val();
			var dtValue = div.find("dt[name='"+value+"']").text();
			if(dtValue){
				div.find("i").text(dtValue);
			}
		}
	});
	</script>
	<script type="text/javascript">
		//加载事件创建上传
		function cteateUploder(pickId, preId,urlHiddenId){
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
				fileSizeLimit : 400 * 1024,
				compress: null
			};
	  		addEvent(new WebUploader.Uploader(options_),preId,urlHiddenId);
		}
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
					 
				},
				error : function() {
					alert("数据异常");
				}
				});
		    });
	  		up.on( 'error', function(type) {
	  		//alert("错误信息:"+type);
				 if (type == "Q_EXCEED_SIZE_LIMIT" || type == "F_EXCEED_SIZE") {
                msgBox("文件超过大小,请将上传文件大小控制在400kb以内", "erro");
	            } else if (type == "Q_TYPE_DENIED") {
	                msgBox("上传格式错误或者上传大小不能为0kb","erro");
	            } else {
	                msgBox("上传错误，请重试。","erro");
	            }
		    });
	  	}
		$(function() {
			for (var i = 1; i <= 6; i++) {
				//debugger;
				cteateUploder('pickfiles' + i, 'imgshow' + i, 'img_hiddenUrl' + i);
			}
		})
	</script>
	<script type="text/javascript">
		//表单顺序提交
		function submit(preview){
//			var dropDown = $(".dropdown"),
//					dropDown_len = dropDown.length;
//
//			// 进行遍历验证
//			for (var i = 0; i < dropDown_len; i++) {
//				var region = dropDown.eq(i).find(".select-base:first"),
//						scenerySpot = dropDown.eq(i).find(".select-base:last"),
//						region_val = region.children("i").text(),
//						region_def = region_val.substr(0, 3),
//						scenerySpot_val = scenerySpot.children("i").text(),
//						scenerySpot_def = scenerySpot_val.substr(0, 3);
//
//				// 地区未选择（为默认值）
//				if ("请选择" == region_def) {
//					if (region.next(".errMesg").length == 0) {
//						creatErrMesg(region, "请选择地区", "bottom");
//					}
//				}
//				// 地区已选择
//				else {
//					region.next(".errMesg").remove();
//				}
//
//				// 景点未选择
//				if ("请选择" == scenerySpot_def) {
//					if (scenerySpot.next(".errMesg").length == 0) {
//						creatErrMesg(scenerySpot, "请选择景点", "bottom");
//					}
//				}
//				// 景点已选择
//				else {
//					scenerySpot.next(".errMesg").remove();
//				}
//			}

			var regSpan = $("span.errMesg").length > 0;
			if(regSpan){
				msgBox("还有未选择的选项，请检查!","erro",1000);
			}else{
				if(preview==1){
					for(var i=1;i<=6;i++){
						$.ajax( {
							type : "post",
							url : "${ctx }web/homeController/saveManageImg",
							data : $("#form"+i).serialize(),
							dataType : "text",
							async : false,
							success : function(data) {
							}
						});
					}
					msgBox("保存成功！", "pass", 2600);
					window.location.href="${ctx}/web/homeController/getManageImg?programaCode=1320eb90-75da-11e4-b6ce-005056a05bc9";
				}else{
					for(var i=1;i<=6;i++){
						$.ajax( {
							type : "post",
							url : "${ctx }web/homeController/previewManageImg",
							data : $("#form"+i).serialize(),
							dataType : "text",
							async : false,
							success : function(data) {
							}
						});
					}
					 var  form  = $('<form style="display" target="_blank" id="open" action="${ctx}web/homeController/previewManageFront" method="post"></form>');
						$("body").append(form);
				     	$("#open").submit();
				}

			}
		}


		/**
		 * 加载事件
		 */
		$(function(){
			binding();
		});

		/**
		 * 绑定事件
		 * @return
		 */
		function binding(){

			// 下拉框  选择内容，并隐藏选择菜单(点击) 同时触发 绑定查询条件值
			$(".select-base dt").click(function() {
				select_base.select(this);
				select_base.hide_click(this);
				//同时获取当前 dt的 value 值  ,然后开始绑定
				var inputValue = $(this).attr("inputValue");
				var inputId = $(this).attr("inputId");
				var myMethod = $(this).attr("myMethod");
				var count  = $(this).attr("count");
				if (inputId!=undefined) {
					//	$("input[name='"+inputName+"']").val(inputValue);
					$("#"+inputId).val(inputValue);
					value =  $("#"+inputId).val();
					//ajax处理
					if (myMethod) {
						//调用自己的方法
						var    ajaxMethodString   =  myMethod+'("'+value+'","'+count+'")';
						eval(ajaxMethodString);
					}
				}


			});

		}

		/**
		 * ajax 请求路径 查询 目的地对应的  景点
		 * @param destinationCode
		 * @return
		 */
		function  ajaxGetViewByDescode(vl,count){
			

			$("#viewCode" + count).val("");
			
			$("#viewSelectI" + count).text("请选择");
			//.log(count);
			$.ajax(
					{
						url:"${ctx}/web/readTibetSgPassMgController/getViewByDescode",
						data:"destinationCode="+vl,
						type: 'POST',
						success:function(data){
							//解析json  js
							var  viewsJSON  =$.parseJSON(data);
							var  htmlViews  = '';
							$(viewsJSON).each(function(i){
								htmlViews = htmlViews +"<dt inputValue='"+this.code+"'  inputName='viewCode' inputId='viewCode"+count+"' >"+this.viewName+"</dt>";
							});
							$("#appendView"+count).html(htmlViews);
							$(".ml-10 dt").click(function() {
								select_base.select(this);
								select_base.hide_click(this);
								//同时获取当前 dt的 value 值  ,然后开始绑定
								var inputValue = $(this).attr("inputValue");
								var inputId = $(this).attr("inputId");
								var myMethod = $(this).attr("myMethod");
								var count  = $(this).attr("count");
								if (inputId!=undefined) {
									//	$("input[name='"+inputName+"']").val(inputValue);
									$("#"+inputId).val(inputValue);
									value =  $("#"+inputId).val();
									//ajax处理
									if (myMethod) {
										//调用自己的方法
										var    ajaxMethodString   =  myMethod+'("'+value+'","'+count+'")';
										eval("("+ajaxMethodString+")");
									}
								}
							});
						}
					}
			);
		}

		/**
		 * ajax 请求路径 查询 目的地对应的  景点
		 * @param destinationCode
		 * @return
		 */
		function  ajaxGetViewByDescodeChose(vl,count){
			var    selectDivs  = $(vl).parent().parent().find(".select-base").get(0);
		    var    des = $(selectDivs).find("input").val(); 
		    	
			//.log(count);
			$.ajax(
					{
						url:"${ctx}/web/readTibetSgPassMgController/getViewByDescode",
						data:"destinationCode="+des,
						type: 'POST',
						success:function(data){
							//解析json  js
							var  viewsJSON  =$.parseJSON(data);
							var  htmlViews  = '';
							$(viewsJSON).each(function(i){
								htmlViews = htmlViews +"<dt inputValue='"+this.code+"'  inputName='viewCode' inputId='viewCode"+count+"' >"+this.viewName+"</dt>";
							});
							$("#appendView"+count).html(htmlViews);
							$(".ml-10 dt").click(function() {
								select_base.select(this);
								select_base.hide_click(this);
								//同时获取当前 dt的 value 值  ,然后开始绑定
								var inputValue = $(this).attr("inputValue");
								var inputId = $(this).attr("inputId");
								var myMethod = $(this).attr("myMethod");
								var count  = $(this).attr("count");
								if (inputId!=undefined) {
									//	$("input[name='"+inputName+"']").val(inputValue);
									$("#"+inputId).val(inputValue);
									value =  $("#"+inputId).val();
									//ajax处理
									if (myMethod) {
										//调用自己的方法
										var    ajaxMethodString   =  myMethod+'("'+value+'","'+count+'")';
										eval("("+ajaxMethodString+")");
									}
								}
							});
						}
					}
			);
		}


	</script>
	
	
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>


