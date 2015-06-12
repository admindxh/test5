<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
    <!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="author" content="quansenx">
	<meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
	<meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
<%@include file="/common-html/frontcommon.jsp" %>
	<title>上传攻略&游记_天上西藏</title>
	<link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
	<!--[if lt IE 9]>
	<script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
	<script src="${ctxPortal}/modules/fix/respond.min.js"></script>
	<![endif]-->
	<script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
	<script>
	// Set configuration
	seajs.config({
		alias: {
			"jquery": "jquery/jquery/1.11.1/jquery.js",
			"avalon": "avalon/1.3.7/avalon.js",
			"common/css": "${ctxPortal}/assets/css/common.css",
			"Validform": "${ctxPortal}/modules/Validform/5.3.2/Validform.min.js",
			"footer/css": "${ctxPortal}/assets/css/footer.css"
		}
	});
	</script>
</head>
<body ms-controller="viewSpot">
	<jsp:include page="${root}/portal/headerFooterController/header"></jsp:include>
	<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
	<div class="container">
		<div class="bd">
			<div class="xz-box clearfix" style="margin-top: 60px;">
				<div class="bd-left">
					<div class="xz-heading">
							<h2 class="tn">
								<span class="sr-only">上传攻略&游记</span>
							</h2>
					</div>
					<form class="form-horizontal" method="post" id="form1">
						<div class="scenery-wrap">
							<div class="step-title">
								<i class="step-icon active">1</i>
								这次旅行，你途径了哪些地方？看了哪些风景？
							</div>
							<div class="scenery-content">
								<div class="scenery-inner">
									<!-- 存放选取地区数据 -->
									<input type="hidden"  name="areas" ms-duplex="areas" datatype="*" nullmsg="请选择目的地">
									<!-- 存放选取景点数据 -->
									<input type="hidden" name="spots" ms-duplex="spots" datatype="*" nullmsg="请选择景点 ">
									<div class="scenery-title"><span class="sr-only">请选择您的旅程目的地：</span></div>
									<dl class="scenic-list" ms-repeat="list">
										<dt>
											<a href="javascript:;" class="district" ms-click="pick(el)">
												<i class="choice" ms-class="pick:el.checked"></i>
												{{ el.name }}
											</a>
										</dt>
										<dd class="spots" ms-if="el.items && el.items.size() && el.checked"> <i class="arrow"></i>
											<a href="javascript:;" class="scenic" ms-repeat-spot="el.items" ms-click="pickSpot(spot)"><i class="choice" ms-class="pick:spot.checked"></i>{{ spot.name }}</a>
										</dd>
									</dl>
								</div>
							</div>
							<div class="step-title">
								<i class="step-icon">2</i>
								发布并分享你的旅程
							</div>
							<div class="scenery-content bdl-none">
								<div class="scenery-inner">
									<div class="form-group">
										<label for="title" class="col-sm-2 control-label">题目：</label>
										<div class="col-sm-7">
											<input type="text" errormsg="请输入2-30位字母、数字、汉字及常用标点符号" name="contentTitle" datatype="*2-30" value="${content.contentTitle }"  id="contentTitle"  class="form-control input-sm" nullmsg="请输入题目">
										</div>
										<div class="col-sm-3">
											<div class="dropdown">
												<button class="btn" type="button" data-toggle="dropdown">
													<span class="text"><c:if test="${not empty proName}">${proName }</c:if> <c:if test="${empty proName}">请选择攻略类型</c:if></span>
													<span class="caret"></span>
												</button>

												<ul class="dropdown-menu" role="menu">
													<c:forEach items="${programsList}" var="pro">
														<li role="presentation" data-name="scenic" data-code="1">
															<a target="_blank" role="menuitem" value="${pro.code }" href="javascript:;">${pro.programaName }</a>
														</li>
													</c:forEach>
												</ul>
											</div>
                                            <input type="hidden" id="programaCode" value="${content.programaCode }" name="programaCode"  datatype="*" nullmsg="请选择攻略类型">
										</div>
									</div>
									<div class="form-group">
										<label for="content" class="col-sm-2 control-label">内容：</label>
										<div class="col-sm-10">
												<!-- 加载编辑器的容器 -->
												<textarea id="content" name="content" type="text/plain">${content.content }</textarea>
												<input type="hidden" name=""  errormsg="请输入100-5000个字符	" datatype="*100-5000" id="markInput">
										</div>
									</div>
									<div  class="text-right" style="padding-right:45px;">
									  <input type="hidden"  value="${code}" name="code"/>
									  <input type="hidden"  value="${sourceHref}" id="sourceHref" name="sourceHref"/>
										<button class="btn-comment" type="submit" >发表</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="bd-right">
					<div class="bs-tips"></div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/portal/app/footer/index.jsp"></jsp:include>

	<script>

		seajs.use(['common/css', 'footer/css', 'slick/slick.css']);

		seajs.use(['avalon', 'jquery','Validform'], function(avalon) {
			$("#form1").Validform({
	   			tiptype:3,
	   			datatype:{
					"contentTitle":function(gets,obj,curform,regxp){


					}

	   			},
	   			callback:function(form){
		    			if(checkPortalUserLongin()){
		    	    		$.ajax(
		    						{
		    							 type:'post',
		    							  url:"${ctx }/tourism/upload/update",
		    							  data:$("#form1").serialize(),
		    							  async:true,
		    							  success:function(){
		    									alert('攻略上传成功，请等待管理员审核!');
		    									window.location.href=$("#sourceHref").val();
		    									return false;
		    								},
		    								error:function(){
		    									alert('攻略上传失败!');
		    									window.location.href=$("#sourceHref").val();
		    									return false;
		    	    						}
		    						}
		    			 	 );
		    	    		//$("#form1").submit();
		    	    		return false;

		    			  }else{
		    						 alert('请登录！登录后可以上传');
		    			  }
    		 	 	   return false;
	   			}
   			});


			$(function(){
				  $(document).scroll(function(){
					  $("#edui1_wordcount").empty();
				  });
				});

			var scenicModel = avalon.define({
				$id: "viewSpot",
				list:[],
				areas:["${destCodes}"], // 选取的地区数据
				spots:["${viewCodes}"], // 选取的景点数据
				// 选取地区
				pick: function(vmodel){
					var _checked = !vmodel.checked, _id = vmodel.id
					vmodel.checked = _checked
					// _checked ? scenicModel.areas.push(_id) : scenicModel.areas.remove(_id)
					if(_checked){
						scenicModel.areas.push(_id)
					}else{
						scenicModel.areas.remove(_id)
						if (vmodel.items) {
							vmodel.items.forEach(function(item){
								// 取消选中样式
								item.checked = false
								// 移除该地区已选取的景点信息
								scenicModel.spots.remove(item.id)
							})
						}
					}
				},
				// 选取景点
				pickSpot: function(vmodel){
					var _checked = !vmodel.checked, _id = vmodel.id
					vmodel.checked = _checked
					_checked ? scenicModel.spots.push(_id) : scenicModel.spots.remove(_id)
				}
			})
			scenicModel.list = _list;
			// 表单验证

			// 下拉
			$('[data-toggle="dropdown"]').on('click', function(event) {
				event.preventDefault();
				var $this = $(this),
					$sub = $this.siblings('.dropdown-menu'),
					$text = $this.children('.text'),
					$hidden = $sub.is(':hidden')
					if ($hidden) {
						$sub.stop().slideDown()
					}else{
						$sub.stop().slideUp()
					}
			});
			$(document).on('click','.dropdown-menu li',function(){
					var $this = $(this),
						aText = $this.children('a').text(),
						$thisParent = $this.parent('.dropdown-menu'),
						$sub = $thisParent.siblings('[data-toggle="dropdown"]').children('.text'),
						$hidden = $thisParent.is(':hidden');
					    $sub.text(aText);
					    var  inputValue  = $this.children('a').attr('value');
					    $("#programaCode").val(inputValue);
					    $this.parents('.dropdown').siblings('.Validform_checktip').empty()
					if($hidden){
						$thisParent.stop().slideDown()
					}else{
						$thisParent.stop().slideUp()
							}

				})
		})
	  var _list = eval('${list}');

	</script>

  <script type="text/javascript" src="${ctxPortal}/modules/ueditor/1.4.3/ueditor.config.js"></script>
  <!-- 编辑器源码文件 -->
  <script type="text/javascript" src="${ctxPortal}/modules/ueditor/1.4.3/ueditor.all.js"></script>
  <!-- 实例化编辑器 -->
  <script type="text/javascript">
    var ue = UE.getEditor('content', {
    	initialFrameHeight:350,
    	autoHeightEnabled:false,
    	toolbars: [
    		[
    			'insertimage', //多图上传
    		]
    	],
    	wordCount:false
    });
    ue.addListener("contentChange", function(e) {
    	//$("#edui1_wordcount").empty();
    	var _this = this;
  		$('#markInput').val(ue.getContentTxt()).focus().blur();
    })
  // $("#edui1_wordcount").empty();
  </script>
  	<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
</body>
</html>