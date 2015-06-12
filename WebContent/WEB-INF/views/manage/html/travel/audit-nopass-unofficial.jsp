<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
<%@include file="/common-html/common.jsp" %>
    <title>游西藏-攻略管理-待审核的攻略-非官方攻略审核</title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
    <!-- 网页文本编辑器插件 -->
    <script src="${ctxManage}/resources/plugin/ckeditor/ckeditor.js" type="text/javascript"></script>
</head>
<body>
    <!-- main { -->
    <div class="main">
		<form id="form1"  method="post" action="${ctx}/web/readTibetSgCheckMgController/update">
			<!-- 页面位置-->
			<input type="hidden" name="code" value="${content.code}">
			
			<div class="location">
				<label>您当前位置为:</label>
				<span><a>游西藏</a> -</span>
				<span><a>攻略管理</a> -</span>
				<span><a href="audit-nopass.html" target="_self">待审核的攻略</a> -</span>
				<span><a href="#" target="_self">非官方攻略审核</a></span>
			</div>

			<!-- 模块管理 { -->
			<div class="muduleManage details filament_solid_ddd">
				<div class="formLine">
					<label>上传时间:</label>
					<span class="dataVal">
						<fmt:formatDate value="${content.createTime}" pattern="yyyy-MM-dd"/>
					</span>
				</div>
				<div class="formLine">
					<label>攻略标题:</label>
					<span class="dataVal">${content.contentTitle }</span>
				</div>
				<div class="formLine">
					<label>所属板块:</label>
					<span class="dataVal">${content.programaCode}</span>
				</div>
				<div class="formLine">
					<label>会员名:</label>
					<span class="dataVal">${user.name }</span>
				</div>
				<div class="formLine">
					<label>关联目的地:</label>
					<c:forEach items="${desName}" var="des">
						<span class="dataVal">${des}</span>
					</c:forEach>
				</div>
				<div class="formLine">
					<label>关联景点:</label>
					<c:forEach items="${viewName}" var="view">
						<span class="dataVal">${view}</span>
					</c:forEach>
				</div>
				<!-- 网页文本编辑器 -->
				<div class="formLine">
					<label>攻略详细介绍:</label>
					   <script id="content" class="ueEditor_cont" name="content"  style="height:500px;" type="text/plain">
					   	${content.content}
					   </script>
				</div>
				<div class="formLine">
					<label>是否审核通过:</label>
					<input id="audit-pass"  value="1" type="radio" name="state" class="ml10" checked>
					<label for="audit-pass" class="lbl_check">通过</label>
					<input id="audit-nopass" value="-1" type="radio" name="state" class="ml20">
					<label for="audit-nopass"  class="lbl_check">退回</label>
				</div>
				<!-- 确认按钮 -->
				<div class="formLine saveOper">
					<button type="button" class="btn-base sureSave mr30">确认</button>
					<button type="button" class="btn-base"  onclick="back()">取消</button>
				</div>
				
			</div><!-- } 模块管理 -->
    	</form>
    </div><!-- } main -->

    <!-- JS引用部分 -->
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
    
     <!-- 配置文件 -->
  <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.config.js"></script>
  <script type="text/javascript" src="${ctxManage}/resources/plugin/ueditor/ueditor.all.js"></script>
  <!-- 实例化编辑器 -->
  <script type="text/javascript"> 
      var ue = UE.getEditor('content',{
  		autoHeightEnabled:false

      });
      $(".sureSave").click(function(){saveData()});
      function saveData(){
          var r  =  $("input[name='state']:checked").val();
          var t  = '';
          if(r==1){
						t = "确定通过审核";
              }else{
						t= "确定退回";
                  }
    	  popupLayer(
					'',
					'<div style="width: 320px; text-align:center; margin: 0 auto;">'+t+',是否返回？</div>',
					'<button type="button" class="btn-sure sure mr15">确定</button>' +
					'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure', function(){
				//javascript:history.back(-1);
				closePopup();
			    save('${ctx}/web/readTibetSgCheckMgController/update','form1');
			});
         }
  </script>

	<script type="text/javascript">
		function back(){
			popupLayer(
					'',
					'<div style="width: 320px; text-align:center; margin: 0 auto;">返回将不保存数据，是否返回？</div>',
					'<button type="button" class="btn-sure sure1 mr15">确定</button>' +
					'<button type="button" class="btn-sure cancel ml15">取消</button>'
			);
			$(document).one('click', '.sure1', function(){
				//javascript:history.back(-1);
				window.location.href="${ctx}/web/readTibetSgCheckMgController/list?menu=1";
				closePopup();
			});
		}
	</script>
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>
