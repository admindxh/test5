<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<!DOCTYPE html>
<html>
<head>
     <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>游西藏-攻略管理-已审核的攻略</title>
	<%@include file="/common-html/common.jsp" %>
     <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
     <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <script type="text/javascript">
    $(function(){
		var  pageInfo =  '<div class="paging-info">'+
				    		'<span class="disp-ib">当前第&nbsp;${pager.currentPage }&nbsp;页</span>'+
				    		'<span class="disp-ib">共&nbsp;${pager.totalPage }&nbsp;页</span>'+
				    		'<span class="disp-ib">共&nbsp;${pager.totalCount }&nbsp;条</span>' +
				    		'</div>';
    	$("#pageInfo").append(pageInfo);
	});
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
						  // var  htmlViews  = '<dt inputValue="" inputName="viewCode"    inputTextValue="全部景点" inputTextName="viewCodeText">全部景点</dt>';
						   var  htmlViews  = '';
							$(viewsJSON).each(function(i){
								htmlViews = htmlViews +"<dt inputValue='"+this.code+"'  inputName='viewCode'  inputTextValue='"+this.viewName+"' inputTextName='viewCodeText' >"+this.viewName+"</dt>";		 
							});	
		    			   $("#appendView").html(htmlViews);
		    			   $("#vt").text('全部景点');
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
    
	//msgBox("数据添加完成！", "info", 1200);		
	function searchOrder(order){
        var keyWord = $("#keyWord").val();
        var isOfficialText = $("#isOfficialText").val();
        var proCodeText = $("#proCodeText").val();
        var destinationCodeText = $("#destinationCodeText").val();
        var isOfficial = $("#isOfficial").val();
        var proCode = $("#proCode").val();
        var destinationCode = $("#destinationCode").val();
        var viewCode = $("#viewCode").val();
        var viewCodeText = $("#viewCodeText").val();

				var order  = $(order).val();
				var orderText  = $("#orderText").val();
				window.location.href='${ctx}/web/readTibetSgPassMgController/list?order='+order+"&orderText="+orderText+"&keyWord="+keyWord+"&isOfficial="+isOfficial+"&isOfficialText="+isOfficialText+"&proCode="+proCode+"&proCodeText="+proCodeText+"&destinationCodeText="+destinationCodeText+"&destinationCode="+destinationCode+"&viewCode="+viewCode+"&viewCodeText="+viewCodeText+"&res=0";
    }
    
    
    </script>
    
    
</head>
<body>
		<!-- main { -->
    <div class="main">
		<!-- 页面位置-->
		<div class="location">
			<label>您当前位置为:</label>
			<span><a href="travel.html" target="_self">游西藏</a> -</span>
			<span><a target="_self">攻略管理</a> -</span>
			<span><a href="#" target="_self">已审核的攻略</a></span>
		</div>
		
		<!-- 数据操作 -->
		<div class="searchManage">
		<rimiTag:perm url="web/readTibetSgPassMgController/saveUI">
			<a href="javascript:void(0)" target="_self" class="btn-anchor" onclick="addUI('${ctx}/web/readTibetSgPassMgController/saveUI')">新建攻略</a>
			</rimiTag:perm>
			<rimiTag:perm url="web/readTibetSgPassMgController/delete">
			<button type="button" class="btn-sure" onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/readTibetSgPassMgController/delete','contentCode')">批量删除</button>
			</rimiTag:perm>
		</div>
	
		<!-- 模块管理 { -->
		<div class="tableManage pos-rela">
			<!-- 搜索查询栏 { -->
			<form id="listForm" method="post" action="${ctx}/web/readTibetSgPassMgController/list">
					<div class="searchTools">
						<!-- 会员名/手机/邮箱/攻略标题 -->
						<input type="text" value="${keyWord}" name="keyWord" id="keyWord" placeholder="会员名/手机/邮箱/攻略标题" />
						<!-- 攻略类型 -->
						<div class="select-base ml-20">
							<i class="w-110">${isOfficialText }</i>
							<dl>
								<dt   inputValue="" inputName="isOfficial"  inputTextValue="全部类型" inputTextName="isOfficialText">全部类型</dt>
								<dt   inputValue="0" inputName="isOfficial" inputTextValue="用户上传" inputTextName="isOfficialText">用户上传</dt>
								<dt   inputValue="1" inputName="isOfficial"  inputTextValue="官方发布" inputTextName="isOfficialText">官方发布</dt>
							</dl>
							<input id="isOfficial" type="hidden" value="${isOfficial }" name="isOfficial"/>
							<input id="isOfficialText" type="hidden" value="${isOfficialText }" name="isOfficialText"/>
						</div>
						
						<!-- 所属版块 -->
						<div class="select-base ml-10">
							<i class="w-110">${proText }</i>
							<dl>
								<dt  inputValue="" inputName="proCode" inputTextValue="全部版块" inputTextName="proCodeText">全部版块</dt>
								<c:forEach var="program" items="${programsList}">
									<dt  inputValue="${program.code}"  inputName="proCode"   inputTextValue="${program.programaName}" inputTextName="proCodeText">${program.programaName}</dt>
								</c:forEach>
							</dl>
							<input id="proCode" type="hidden" value="${proCode }" name="proCode"/>
							<input id="proCodeText" type="hidden" value="${proText }" name="proCodeText"/>
						</div>
						 
						<!-- 相关目的地 -->
						<div class="select-base ml-10">
							<i class="w-140">${destinationCodeText }</i>
							<dl> 
								<dt  inputValue="" myMethod="ajaxGetViewByDescode" inputName="destinationCode" inputTextValue="全部地区" inputTextName="destinationCodeText">全部地区</dt>
								<c:forEach var="destination" items="${destinationList}">
									<dt  myMethod="ajaxGetViewByDescode"  inputValue="${destination.code}" inputName="destinationCode" inputTextValue="${destination.destinationName}" inputTextName="destinationCodeText">${destination.destinationName}</dt>
								</c:forEach>
							</dl>
							<input id="destinationCode" type="hidden" value="${destinationCode }" name="destinationCode"/>
							<input id="destinationCodeText" type="hidden" value="${destinationCodeText }" name="destinationCodeText"/>
						</div>
						
						<!-- 相关景点 -->
						<div class="select-base ml-10">
							<i class="w-140"  id="vt">${viewCodeText }</i>
							<dl  id="appendView">
								<%--<dt inputValue="" inputName="viewCode"  inputTextValue="全部景点" inputTextName="viewCodeText" >全部景点</dt>
								--%><c:forEach var="view" items="${viewList}">
									<dt  inputValue="${view.code}" inputName="viewCode"  inputTextValue="${view.viewName}" inputTextName="viewCodeText">${view.viewName}</dt>
								</c:forEach>
							</dl>
							<input id="viewCode" type="hidden" value="${viewCode }" name="viewCode"/>
							<input id="viewCodeText" type="hidden" value="${viewCodeText}" name="viewCodeText"/>
						</div>
						<!-- 查询 -->
						<button type="button" class="btn-search"  onclick="javascript:$('#listForm').submit()"><!--查询--></button>
						 <!-- 快捷筛选 -->
	                 <div class="shortcutSearch">
			                <!-- 快捷筛选 -->
			                <div class="select-base">
			                    <i class="w-140">${orderText }</i>
			                    <dl>
			                        <dt myMethod="searchOrder" inputValue="createTime" inputName="order"  inputTextValue="按时间" inputTextName="orderText">按时间</dt>
			                        <dt myMethod="searchOrder" inputValue="favatecount" inputName="order" inputTextValue="按收藏人数" inputTextName="orderText">按收藏人数</dt>
			                        <dt myMethod="searchOrder" inputValue="praisecount" inputName="order" inputTextValue="按点赞人数" inputTextName="orderText">按点赞人数</dt>
			                        <dt myMethod="searchOrder"  inputValue="replycount" inputName="order" inputTextValue="按评论数" inputTextName="orderText">按评论数</dt>
			                    </dl>
			                    <input value="${order }" name="order" id="order" type="hidden" />
			                    <input value="${orderText }" id="orderText" name="orderText" id="orderText" type="hidden" />
			                </div>
	           		 </div>
					</div><!-- } 搜索查询栏 -->
			</form>
			<!-- 数据列表 -->
			<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
				<thead>
					<tr>
						<th>
							<input type="checkbox" name="dataCheck" class="allCheck">
							全选
						</th>
						<th>排序</th>
						<th>编号</th>
						<th>创建日期</th>
						<th>会员名</th>
						<th>手机号</th>
						<th>邮箱</th>
						<th>攻略类型</th>
						<th>所属板块</th>
						<th>攻略标题</th>
						<%--<th>攻略内容</th>
						--%><th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="dynamicDataMg" items="${pager.dataList}" varStatus="status">
						<tr>
							<td>
								<input type="checkbox" name="dataCheck" class="dataCheck" value="${dynamicDataMg.code}">
							</td>
							<td>${status.index+1}</td>
							<td><span class="ellipsis maxW120">${dynamicDataMg.code}</span></td>
							<td><fmt:formatDate value="${dynamicDataMg.createTime}"  pattern="yyyy-MM-dd" /></td>
							<td><span class="passName ellipsis maxW120">${dynamicDataMg.name}</span></td>
							<td>${dynamicDataMg.mobile}</td>
							<td><span class="ellipsis maxW120">${dynamicDataMg.email}</span></td>
							<td>${dynamicDataMg.isOfficialName}</td>
							<td>${dynamicDataMg.programaName}</td>
							<td><span class="passInfo ellipsis maxW120"><a href="${ctxIndex}${dynamicDataMg.url}" target="_blank">${dynamicDataMg.contentTitle}</a></span>
							</td>
							<td>
								<a  href="${ctxIndex }${dynamicDataMg.url}" target="_blank" title="${dynamicDataMg.contentTitle}">查看</a>
								<rimiTag:perm url="web/readTibetSgPassMgController/updateUI">
								<a  href="javascript:void(0)" onclick="update('${ctx}/web/readTibetSgPassMgController/updateUI','contentCode=${dynamicDataMg.code}')">编辑</a>
								</rimiTag:perm>
								<rimiTag:perm url="web/readTibetSgPassMgController/delete">
								<a  href="javascript:void(0)"   onclick="deletebySingle('${dynamicDataMg.code}','${ctx}/web/readTibetSgPassMgController/delete','contentCode')">删除</a>
								</rimiTag:perm>
						 	</td>
						</tr>
					</c:forEach>
					 <c:if test="${empty pager.dataList}">
		            	<tr>
			            	        <td colspan="11" style="text-align: center;color:red;">
										     暂无相关攻略
								    </td>      	
		            	</tr>
           			 </c:if>
					</tbody>
				</table>
				<div class="paging">
					<%@include file="/common-html/pager.jsp" %>
				</div>
		</div><!-- } 模块管理 -->
    </div><!-- } main -->

    <!-- JS引用部分 -->
   
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
	
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>