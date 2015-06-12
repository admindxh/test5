
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="author" content="quansenx">
	<meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
	<meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
	<%@include file="/common-html/frontcommon.jsp" %>
	<title>帖子发表页_天上西藏</title>
	<link rel="stylesheet" href="${ctxPortal}modules/bootstrap/3.3.1/css/bootstrap.min.css" />
	
<%--	<%@include file="/common-html/common.jsp" %>--%>
	<!--[if lt IE 9]>
	<script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
	<script src="${ctxPortal}modules/fix/respond.min.js"></script>
	<![endif]-->
	<script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
	<script>
		// Set configuration
		seajs.config({
			alias: {
			 "jquery": "jquery/jquery/1.11.1/jquery.js",
             "avalon": "avalon/1.3.7/avalon.js",
             "Validform": "${ctxPortal}/modules/Validform/5.3.2/Validform.min.js",
             "common/css": "${ctxPortal}/assets/css/common.css",
             "footer/css": "${ctxPortal}/assets/css/footer.css"
			}
		});
	 	seajs.use(['common/css', 'footer/css']);
        seajs.use(['jquery', 'Validform'], function($) {
        	$("#myForm").Validform({
	   			tiptype:3,
	   			callback: function(form){
        		//alert("发帖成功，进入审核！");
	   			}
	   		})
		})
	</script>
</head>
<body>
		<jsp:include page="${root}/portal/headerFooterController/header"></jsp:include>
		<jsp:include page="../login/login_modal.jsp"></jsp:include>
	<div class="container">
		<div class="bd">
			<div class="place">
				<ul>
					<li class="location">当前位置：</li>
					<li>
						<a href="#">天上社区</a>
					</li>
					
					<li class="slipt"></li>
					<li class="active">发表帖子</li>
				</ul>
			</div>
			<div class="clearfix">
				<div class="bd-left">
					<div class="bdl-item">
						<div class="bd-heading">
							<h2 class="posts">发帖子 POSTS </h2>
						</div>
						<div class="nt-content">
						<form <c:if test="${flag eq 'save'}">action="${ctx}/community/savePost"</c:if> 
						      <c:if test="${flag eq 'update'}">action="${ctx}/community/updatePost"</c:if> method="post" id="myForm">
						    <input type="hidden" name="fromUc" value="${fromUc }">
						    <input type="hidden" name="code" value="${code }"/>
						    <input type="hidden" name="id" value="${content.id }">
							<div class="nt-posted clearfix">
								<span class="pull-left">帖子标题：</span>
								<div style="width: 650px;float: right;">
									<input type="text" class="form-control" name="contentTitle" value="${content.contentTitle }" datatype="*2-30" nullmsg="请输入帖子标题" errormsg="请输入2到30个字符">
								</div>
							</div>
							<div class="nt-posted clearfix">
								<span class="pull-left">帖子分类：</span>
								<div class="dropdown current-state">
									<div style="width: 650px;float: right;">
									 <select name="programaCode"  style="width: 200px;float: left;text-align:center;">
	                                     <c:forEach items="${pro}" var="pro">
	                                        <option value="${pro.code }" <c:if test="${content.programaCode eq pro.code}">selected="selected"</c:if>>${pro.programaName }</option>
	                                     </c:forEach> 	
	                                  </select>						
									 </div>
								 </div>
							</div>
							
							<div class="nt-posted">
								帖子内容：
								<div class="wrap-content">
									<!-- 加载编辑器的容器 -->
									<textarea id="content" name="content">${content.content }</textarea>
									<input type="hidden" id="markInput" datatype="*" nullmsg="请输入帖子内容"/>
								</div>
							</div>
							<div class="text-right btns-posted">
								<a onclick="pre()" href="#" class="btn">预览</a>
								<div class="btn" onclick="savePost()">发表</div>
							</div>
							</form>
						</div>
					</div>
				</div>
				<div class="bd-right">
					<div class="bdr-item">
						<div class="bd-heading">
							<h2 class="post-sm">发帖声明 STATEMENT</h2>
						</div>
						<div class="stem">
							<p>1、本站严禁发布带有敏感字、涉及政治、色情的帖子，一经发现，永久封号。</p>
							<p>2、图片上传不超过2M</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<jsp:include page="../footer/index.jsp"/>
	
	<script>
	//预览效果
	function pre(){
		$("#markInput").val(ue.getContentTxt());
		var textInfo = ue.getContentTxt().length;
		var contentTitleVal = $("[name=contentTitle]").val();
		var title_length = contentTitleVal.length;
		if(title_length <= 30 && title_length >= 2){
		}else{
			alert("您的标题内容须在2-30个字符！");
			return false;
		}
		if(textInfo <= 2000 && textInfo >= 2){
			 $("#myForm").attr("action","${ctx}/community/prePostDetail").attr("target","_blank").submit();
		}else{
			alert("您的发帖内容须在2-30个字符！");
			return false;
		}
      
	}
	//发表完后提示
	function savePost(){
		$("#markInput").val(ue.getContentTxt());
		var textInfo = ue.getContentTxt().length;
		var contentTitleVal = $("[name=contentTitle]").val();
		var title_length = contentTitleVal.length;
		if(title_length <= 30 && title_length >= 2){
		}else{
			alert("您的标题内容须在2-30个字符！");
			return false;
		}
		var count = ue.getContentLength(true);
		if(count <= 5000 && count >= 2){
			 alert("发帖成功，进入审核！");
			 $("#myForm").submit()
		}else{
			alert("您的发帖内容须在2-5000个字符！");
			return false;
		}


		
		
       
	}
	
	</script>
	<!-- 配置文件 -->
  <script type="text/javascript" src="${ctxPortal}/modules/ueditor/1.4.3/ueditor.config.js"></script>
<%--  <script>--%>
<%--  window.UEDITOR_CONFIG.serverUrl = 'community/saveImg'--%>
<%--  </script>--%>
  <!-- 编辑器源码文件 -->
  <script type="text/javascript" src="${ctxPortal}/modules/ueditor/1.4.3/ueditor.all.js"></script>
  <!-- 实例化编辑器 -->
  <script type="text/javascript">
      var ue = UE.getEditor('content', {
    	  toolbars: [["unlink","link","cleardoc","simpleupload","insertimage","emotion"]],
          wordCount:false,
          autoHeightEnabled:false, 
          initialFrameHeight:500
          });
<%--      ue.addListener("contentChange", function(e) {--%>
<%--      	var _this = this;--%>
<%--   		$('#markInput').val(ue.getContentTxt()).focus().blur();--%>
<%--      })--%>
<%--      --%>
      $(document).ready(function(){
          setTimeout(function(){
              var oldWidth = $("#edui1_iframeholder").width();
              $("#edui1_iframeholder").css("width",oldWidth-2+"px");
          },500);
      });
  </script>
  <jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
</body>
</html>