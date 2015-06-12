<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1" />
<%@ include file="/common-html/common.jsp" %>
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
	<title>系统设置-数据统计-目的地&景点统计</title>
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
	<link rel="stylesheet" href="${ctxManage}/resources/plugin/datepicker.css" />
	<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
	
</head>

<body>
	<!-- main { -->
	<div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a>系统设置</a> -</span>
			<span><a>数据统计</a> -</span>
			<span><a>目的地&景点统计</a></span>
		</div>

		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<div class="searchTools">
			<form action="${ctx}/web/desAndViewStatisController/intoDesAndViewStatis" method="post" id="form1">
				<label class="va_m">开始时间:</label>
				<input id="startDate" value="${start }" name="start" type="text" class="w-120">
				<label class="va_m ml10">结束时间:</label>
				<input id="endDate" type="text" value="${end }" name="end" class="w-120">

				<!-- 相关目的地 -->
						<div class="select-base ml15">
							<i class="w-140">${destinationCodeText }</i>
							<dl> 
								<dt  inputValue="" myMethod="ajaxGetViewByDescode" inputName="destinationCode"  inputTextValue="全部地区" inputTextName="destinationCodeText">全部地区</dt>
								<c:forEach var="destination" items="${destinationList}">
									<dt  myMethod="ajaxGetViewByDescode"  inputValue="${destination.code}" inputName="destinationCode"  inputTextValue="${destination.destinationName}" inputTextName="destinationCodeText">${destination.destinationName}</dt>
								</c:forEach>
							</dl>
							<input id="destinationCode" type="hidden" value="${destinationCode}" name="destinationCode"/>
							<input id="destinationCodeText" type="hidden" value="${destinationCodeText }" name="destinationCodeText"/>
						</div>
						<!-- 相关景点 -->
						<div class="select-base m110">
							<i class="w-140"   id="tv">${viewCodeText }</i>
							<dl  id="appendView">
								<dt inputValue="" inputName="viewCode"  inputTextValue="全部景点" inputTextName="viewCodeText">全部景点</dt>
								<c:forEach var="view" items="${viewList}">
									<dt  inputValue="${view.code}" inputName="viewCode"   >${view.viewName}</dt>
								</c:forEach>
							</dl>
							<input id="viewCode" type="hidden" value="${viewCode }" name="viewCode"/>
							<input id="viewCodeText" type="hidden" value="${viewCodeText}" name="viewCodeText"/>
						</div>
				<!-- 查询 -->
				<button type="button" onclick="javascript:$('#form1').submit()" class="btn-search">
					<!--查询-->
				</button>
				</form>
			</div><!-- } 搜索查询栏 -->
			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th>统计开始时间</th>
						<th>统计截止时间</th>
						<th>目的地</th>
						<th>目的地去过数</th>
						<th>目的地想去数</th>
						<th>景点</th>
						<th>景点去过数</th>
						<th>景点想去数</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${start }</td>
						<td>${end }</td>
						<td>${andViewStatisVo.desName }</td>
						<td>
						<c:if test="${empty andViewStatisVo.desWantCount }">0</c:if>
						<c:if test="${not  empty andViewStatisVo.desWantCount }">${andViewStatisVo.desWantCount }</c:if>
						
						</td>
						<td>
						<c:if test="${empty andViewStatisVo.desGoCount }">0</c:if>
						<c:if test="${not  empty andViewStatisVo.desGoCount }">${andViewStatisVo.desGoCount }</c:if>
						</td>
						<td>${andViewStatisVo.viewName }</td>
						<td>
						<c:if test="${empty andViewStatisVo.viewGoCount }">0</c:if>
						<c:if test="${not  empty andViewStatisVo.viewGoCount }">${andViewStatisVo.viewGoCount }</c:if>
						</td>
						<td>
							<c:if test="${empty andViewStatisVo.viewWantCount }">0</c:if>
							<c:if test="${not  empty andViewStatisVo.viewWantCount }">${andViewStatisVo.viewWantCount }</c:if>
						</td>
					</tr>
				</tbody>
			</table>

		</div>
		<!-- } 模块管理 -->
	</div>
	<!-- } main -->

	<!-- JS引用部分 -->
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base-form.js"></script>
	<script src="${ctxManage}/resources/plugin/bootstrap-datepicker.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
	<script type="text/javascript">
		// 运行时间选择控件
		datePicker("startDate", "endDate");
	</script>
<script type="text/javascript">
    /**
     * ajax 请求路径 查询 目的地对应的  景点
     * @param destinationCode
     * @return
     */
    function  ajaxGetViewByDescode(destinationCode){
        var vl = $(destinationCode).val();//请求值
        $.ajax(
    			{
    				url:"${ctx}/web/readTibetSgPassMgController/getViewByDescode",
    				data:"destinationCode="+vl,
    			    type: 'POST',
    				success:function(data){
	    			  //解析json  js inputTextValue="${destination.destinationName}" inputTextName="destinationCodeText"
	    			   var  viewsJSON  =$.parseJSON(data);
					   var  htmlViews  = '<dt inputValue="" inputName="viewCode"    inputTextValue="全部景点" inputTextName="viewCodeText">全部景点</dt>';
						$(viewsJSON).each(function(i){
							htmlViews = htmlViews +"<dt inputValue='"+this.code+"'  inputName='viewCode'  inputTextValue='"+this.viewName+"' inputTextName='viewCodeText' >"+this.viewName+"</dt>";		 
						});	


						
	    			   $("#appendView").html(htmlViews);
	    			   $("#tv").text('全部景点');
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
	<!-- 利用空闲时间预加载指定页面 -->
	<link rel="prefetch">
	<!-- IE10+ -->
	<link rel="next">
	<!-- Firefox -->
	<link rel="prerender">
	<!-- Chrome -->
</body>

</html>