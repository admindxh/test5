<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>天上社区-帖子&回复管理-已审核的帖子&回复</title>
	<%@include file="/common-html/common.jsp" %>
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <style>
        /* 闭合浮动 */
        .floatfix:after{
            content:"";
            display:table;
            clear:both;
        }
        .bor_t{
            border-top: 1px solid #ccc;
        }
        .w96p{
            width: 96%;
        }
        .bgc_red{
            background-color: red;
        }
        .cu_p{
            cursor: pointer;
        }
    </style>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <script type="text/javascript">
			//msgBox("数据添加完成！", "info", 1200);		
		function searchOrder(order){
					var order  = $(order).val();
					var orderText  = $("#orderText").val();
					window.location.href='${ctx}/web/passPostController/searchPost?order='+order+"&orderText="+orderText+
							"&"+$('#listForm').serialize();
        }
		/**
	     * ajax 请求路径 查询 目的地对应的  景点
	     * @param destinationCode
	     * @return
	     */
	    function  ajaxSaveContentInfo(contentCode,bt){
		    	var thisParent = $(this).parents("div[class^='save']"),
				browNum = thisParent.find("input[id^='browsNum']"),
				pariNum = thisParent.find("input[id^='praiseNum']");
				// 失焦验证
				browNum.blur();
				pariNum.blur();
		
				var regSpan = $('span.errMesg').length > 0;
				// 验证通过
				if(regSpan) {
					msgBox("输入的内容有误，请检查！", "erro");
				}
				// 验证未通过
				else {
					var   params    =$("."+bt).find("input");
					var   contentCode  = contentCode ;
					 var  viewCount= $($(params).get(0)).val();
				     var  praiseCount   =  $($(params).get(1)).val();
				     window.location.href="${ctx}/web/passPostController/saveContentInfo?currentPage=${pager.currentPage}&contentCode="+contentCode+"&praiseCount="+praiseCount+"&viewCount="+viewCount;
					msgBox("保存成功！", "pass");
				}
	   	}
	   	 //
	   	 function restorePraise(contentCode,bt){
	   		var   params    =$("."+bt).find("input");
			var   contentCode  = contentCode ;
		     var  praiseCount   =  $($(params).get(1)).val();
	   		 $.ajax(
		 			{
		 				url:"${ctx}/web/passPostController/restorePraise",
		 				data:"contentCode="+contentCode+"&praiseCount="+praiseCount,
		 			    type: 'POST',
		 				success:function(data){
		 					//msgBox("修改成功！", "info", 800);
		 					if(data==""){
									data=0;
			 					}
		 					$($(params).get(1)).val(data);
		 					msgBox("默认赞成功！", "info", 3000);
		 				}
		     	   }
			    );

		   	 }
	   	 
	   	 function restoreView(contentCode,bt){
		   		var   params    =$("."+bt).find("input");
				var   contentCode  = contentCode ;
			     var  viewCount   =  $($(params).get(1)).val();
		   		 $.ajax(
			 			{
			 				url:"${ctx}/web/passPostController/restoreView",
			 				data:"contentCode="+contentCode+"&praiseCount="+viewCount,
			 			    type: 'POST',
			 				success:function(data){
			 					//msgBox("修改成功！", "info", 800);
			 					if(data==""){
									data=0;
			 					}
			 					$($(params).get(0)).val(data);
			 					msgBox("默认浏览量成功！", "info", 800);
			 				}
			     	   }
				    );

		 }
		//置顶			
	   	 function retoreStick(isTop,contentCode){
		   	   if(isTop==1){
						msg  = "您确定要置顶帖子吗？";
			   	   }else{
						msg  = "您确定要取消置顶吗？";
				   	   }
		   	   confirm_custom(msg,function  retoreMyfun(){
	  		  	   window.location.href="${ctx}/web/passPostController/retoreStick?currentPage=${pager.currentPage}&isTop="+isTop+"&contentCode="+contentCode;
		   	   });
		 }
		 //管理回复
		 function  managerReply(){
	
		window.location.href="${ctx}/web/passPostController/";
		}
		
		$(function(){
			var  pageInfo =  '<div class="paging-info">'+
					    		'<span class="disp-ib">当前第&nbsp;${pager.currentPage }&nbsp;页</span>'+
					    		'<span class="disp-ib">共&nbsp;${pager.totalPage }&nbsp;页</span>'+
					    		'<span class="disp-ib">共&nbsp;${pager.totalCount }&nbsp;条</span>' +
					    		'</div>';
	    	$("#pageInfo").append(pageInfo);
			//
	    	
		});

		 
    </script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>天上社区</a> -</span>
        <span><a>帖子&回复管理</a> -</span>
        <span><a href="${ctx }/web/passPostController/searchPost" target="_self">已审核的帖子&回复</a></span>
    </div>
    <!-- 数据操作 -->
    <div class="searchManage">
    <rimiTag:perm url="web/passPostController/addUI">
        <a href="javascript:void(0)" class="btn-anchor" target="_self" onclick="addUI('${ctx}/web/passPostController/addUI')">新建帖子</a>
        </rimiTag:perm>
        <rimiTag:perm url="web/passPostController/deletePost">
        <a href="javascript:void(0)" class="btn-anchor" target="_self"  onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/passPostController/deletePost','contentCode','删除帖子则相关的回复会一并删除，确认要删除吗?')">批量删除</a>
        </rimiTag:perm>
    </div>
    <!-- 模块管理 { -->
    <div class="tableManage pos-rela">
        <!-- 搜索查询栏 { -->
        <form id="listForm" method="post" action="${ctx}/web/passPostController/searchPost">
	        <div class="searchTools formLine">
	            <!-- 关键字 -->
	            <label class="w-auto">关键字:</label>
	            	
	            <input type="text" placeholder="标题,内容"
	              <c:if test="${empty  color}"> value="${keyword}"</c:if>
	            
	              style="color: #000000;"  name="keyword">
	            
	            <div class="select-base ml-20">
	                <i class="w-140">${programcodeText }</i>
	                <dl>
						<dt inputValue="" inputName="programcode"  inputTextValue="全部板块" inputTextName="programcodeText">全部板块</dt>
						<c:forEach var="plate" items="${plates}">
							<dt inputValue="${plate.code}" inputName="programcode" inputTextValue="${plate.programaName}" inputTextName="programcodeText">${plate.programaName}</dt>
				    	</c:forEach>
				   </dl>
				  <input id="programcode" type="hidden" value="${programcode}" name="programcode"/>
				  <input id="order" type="hidden" value="${order}" name="order"/>
				  <input id="programcodeText" type="hidden" value="${programcodeText }" name="programcodeText"/>
				  
				  
				  
				  
	            </div>
				<button type="button" class="btn-search" onclick="javascript:$('#listForm').submit()"><!--查询--></button>
	            <div class="shortcutSearch">
	
	                <!-- 快捷筛选 -->
	                <div class="select-base mt5">
	                    <i class="w-140">${orderText }</i>
	                    <dl>
	                        <dt myMethod="searchOrder" inputValue="createTime" inputName="order"  inputTextValue="按发布时间" inputTextName="orderText">按发布时间</dt>
	                        <dt myMethod="searchOrder" inputValue="replyTime" inputName="order" inputTextValue="按最新回复时间" inputTextName="orderText">按最新回复时间</dt>
	                        <dt myMethod="searchOrder" inputValue="viewcount" inputName="order" inputTextValue="按浏览量" inputTextName="orderText">按浏览量</dt>
	                        <dt myMethod="searchOrder"  inputValue="praisecount" inputName="order" inputTextValue="按赞数" inputTextName="orderText">按赞数</dt>
	                    </dl>
	                    <input value="" name="order" id="order" type="hidden" />
	                    <input value="${orderText }" id="orderText" name="orderText" id="orderText" type="hidden" />
	                </div>
	            </div>
	         </div><!-- } 搜索查询栏 -->
	   </form>
        <!-- 数据列表 -->
        <div class="formLine">
            <input type="checkbox" name="dataCheck1" class="allCheck">
            <label class="w-auto">全选</label>
        </div>

        <ul class="mt10">
        	<c:forEach items="${pager.dataList}" var="post" varStatus="status">
            <li class="pt10 bor_t bbs-forumList">
                <input type="checkbox" name="dataCheck" value="${post.code }" class="dataCheck float_l mt5">
                <div class=" disp-ib w96p ml15 ">
                    <div class="save${status.index+1}">
                        <label>${post.pname}</label>
                        <span></span>
                        <a href="${ctx }${post.url}" target="_blank" class="ml10 disp-ib w210 " style="overflow: hidden;text-overflow:ellipsis;white-space: nowrap;">${post.ctitle}</a>
                        	<c:if test="${post.isTop==1}">
	                        <a  class="ml10 ft-c_white filament_solid_ddd p2 bgc_red cu_p"  onclick="retoreStick(0,'${post.code}')">取消全站置顶</a>
                        	</c:if>
                        	<c:if test="${empty post.isTop ||post.isTop==0}">
                        		<a  class="ft-c_green ml20 cu_p"  onclick="retoreStick(1,'${post.code}')">全站置顶</a>
                        	
                        	</c:if>
                        <label>浏览量:</label>
                        <input type="text" name="falseViewcount" value="${post.falseViewcount}" class="w80 pl5" maxlength="7"/>
                        <a target="_self" class="btn-base_min"  onclick="restoreView('${post.code}','save${status.index+1}')">恢复默认值</a>

                        <label class="ml10">赞:</label>
                        <input type="text"  name="falsepraise" value="${post.falsepraise}" class="w80 pl5" maxlength="7"/>
                        <a target="_self" class="btn-base_min" onclick="restorePraise('${post.code}','save${status.index+1}')">恢复默认值</a>

                        <div class="float_r bbs-operaForum">
                            <button type="button" class="btn-base_min"  onclick="ajaxSaveContentInfo('${post.code}','save${status.index+1}')">保存</button>
                            <rimiTag:perm url="web/passPostController/deletePost">
                            <button type="button" class="btn-base_min"   onclick="deletebySingle('${post.code}','${ctx}/web/passPostController/deletePost','contentCode','删除帖子则相关的回复会一并删除，确认要删除吗?')">删除</button>
                            </rimiTag:perm>
                            <rimiTag:perm url="web/passPostController/managerReply">
                            	 <button type="button" class="btn-base_min" onclick="update('${ctx}/web/passPostController/managerReply','contentCode=${post.code}')">管理回复</button>
                            </rimiTag:perm>
                            <rimiTag:perm url="web/passPostController/updateUI">
                             	<button type="button" class="btn-base_min" onclick="update('${ctx}/web/passPostController/updateUI','contentCode=${post.code}')">修改</button>
                            </rimiTag:perm>
                        </div>
                    </div>
                    <p class="mt15 ft-c_gray f15">
                        <span>${post.userName}</span>
                        <span>发表于</span>
                        <span>
                        	<fmt:formatDate value="${post.ctime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </span>
                    </p>
                    <p class="mt15">
                    ${fn:substring(post.content,0,100)}
                    <c:if test="${fn:length(post.content)>30}">
                    ...
                    </c:if>
                    
                    </p>
                    <a href="${ctx}${post.url}" target="_blank" class="float_r mt10 mb5">打开</a>
                </div>
          		  </li>
            </c:forEach>
            <c:if test="${empty pager.dataList}">
            	<li class="pt10 bor_t bbs-forumList" style="text-align: center;color:red;">
							     暂无相关帖子      	
            	</li>
            </c:if>
        </ul>
        <%--<!-- 表格分页 -->
        <div class="paging">
            <button type="button" class="btn-base_min">首页</button>
            <button type="button" class="btn-base_min">上一页</button>
            <button type="button" class="btn-base_min">下一页</button>
            <button type="button" class="btn-base_min">末页</button>
        </div>
    --%></div><!-- } 模块管理 -->
    <div class="" style="clear: both;"></div>
    <div class="paging">
    	
    	<%@include file="/common-html/pager.jsp" %>
    </div>
</div><!-- } main -->

<!-- JS引用部分 -->

<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/bbs/audit-wait.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js"></script>
 <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
	<script type="text/javascript">
	/* 为文本框遍历添加ID */
	inputAddId();
	
	/* 浏览量验证 */
	$(document).on("blur", "input[id^='browsNum']", function() {
		var this_val = $(this).val();
		valid_txtBox_create(this, $.VLDTOR.IsNum(this_val), "浏览量只能为数字", "bottom");
	});
	
	/* 赞(被赞数)验证 */
	$(document).on("blur", "input[id^='praiseNum']", function() {
		var this_val = $(this).val();
		valid_txtBox_create(this, $.VLDTOR.IsNum(this_val), "被赞数只能为数字", "bottom");
	});
	
<%--	/* 保存验证 */--%>
<%--	$(document).on("click", "button[id^='save_btn']", function() {--%>
<%--		--%>
<%--	});--%>
	
	/* 为文本框和按钮添加ID */
	function inputAddId() {
		var saveDiv = $("div[class^='save']"),
			save_len = saveDiv.length;
		for(var i = 0; i < save_len; i++) {
			// 浏览量添加ID
			saveDiv.eq(i).find("input[type='text']:first").attr("id", "browsNum" + parseInt(i + 1));
			// 被赞数添加ID
			saveDiv.eq(i).find("input[type='text']:last").attr("id", "praiseNum" + parseInt(i + 1));
			// 保存添加ID
			saveDiv.eq(i).find(".btn-base_min:eq(0)").attr("id", "save_btn"  + parseInt(i + 1));
		}
		
	}
	</script>
<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch"> <!-- IE10+ -->
<link rel="next"> <!-- Firefox -->
<link rel="prerender"> <!-- Chrome -->
</body>
</html>
