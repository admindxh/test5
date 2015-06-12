<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
	<%@include file="/common-html/common.jsp" %>
    <title>天上社区-帖子&回复管理-已审核的帖子&回复-帖子回复管理页</title>
    <link rel="stylesheet" href="${ctxManage}//resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}//resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}//resources/css/travel/formWeb.css"/>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <style>
        /* 闭合浮动 */
        .floatfix:after{
            content:"";
            display:table;
            clear:both;
        }
        .bor-t{
            border-top: 1px dashed #ddd;
        }
        .bor-t_red{
            border-top: 5px solid darkred;
        }
        .zan{
            font-size: 15px;
            color: #fff;
            border: none;
            -webkit-border-radius: 3px;
            -moz-border-radius: 3px;
            border-radius: 3px;
            background-color: darkred;
            padding: 3px 12px;
        }
        .zan:hover{
            background-color: #ba0000;
        }
        .stick{
            width: 66px;
            height: 60px;
            background: url("${ctxManage}//resources/img/other/u65.png") no-repeat;
            -webkit-border-radius: 50%;
            -moz-border-radius: 50%;
            border-radius: 50%;
            line-height: 60px;
            text-align: center;
            font-weight: 700;
            color: darkred;
            position: absolute;
            top: 15px;
            right: 500px;
            cursor: default;
        }
        .quote{
            background-color: #dca7a7;
            color: #000;
        }
        .page{
            float: right;
<%--            margin-top: 20px;--%>
        }
        .disp-ib{
        	display:inline-block;
        }
    </style>
    <script src="${ctxManage}//resources/plugin/respond.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    function restorePraise(contentCode,bt){
   		var   params    =$("."+bt).find("input");
		var   contentCode  = contentCode ;
	     var  praiseCount   =  $($(params).get(0)).val();
   		 $.ajax(
	 			{
	 				url:"${ctx}/web/passPostController/restorePraiseFalse",
	 				data:"contentCode="+contentCode+"&praiseCount="+praiseCount,
	 			    type: 'POST',
	 				success:function(data){
	 					//msgBox("修改成功！", "info", 800);
	 					$($(params).get(0)).val(data);
	 					msgBox("默认赞成功！", "info", 3000);
	 				}
	     	   }
		    );

	   	 }
    /**
     * ajax 保存 赞
     * @param destinationCode
     * @return
     */
    function  ajaxSaveContentInfo(postCode,contentCode,bt){
         var   params    =$("."+bt).find("input");
			var   contentCode  = contentCode ;
			 var  praiseCount= $($(params).get(0)).val();
			 params.blur();
			 var hasErro = params.next(".errMesg").length > 0;
			 if(hasErro) {
				 return;
			 } else {
		         window.location.href="${ctx}/web/passPostController/savemanager?currentPage=${replyPager.currentPage}&postCode="+postCode+"&contentCode="+contentCode+"&praiseCount="+praiseCount;
	    			
			 }
   	 }
  	 function  deleteReply(url){
				window.location.href=url;
  	  	 }
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
            <span><a href="${ctx }/web/passPostController/searchPost" target="_self">已审核的帖子&回复</a> -</span>
            <span class="inviName"><a href="${ctx }/${c.url}" target="_blank">${c.contentTitle }</a></span>
        </div>

        <!-- 模块管理 {  contentCode-->
        <div class="muduleManage floatfix">
			<form  id="listForm" method="post" action="${ctx}/web/passPostController/managerReply">
			<input type="hidden" name="contentCode" value="${c.code }">
            <!-- 置顶 { -->
            <div class="pos-rela">
                <div class="filament_solid_ddd">

                    <div class=" va_m w130 formLine disp-ib ml20">
                        <p>查看数:<span>${pv[0]}</span></p>
                        <p>回复数:<span>${pv[1]}</span></p>
                    </div>
                    <h2 class="va_m disp-ib f28">${c.contentTitle}</h2>
                </div>
				
                <!-- 置顶图标 -->
                <div class="stick" 
                	<c:if test="${c.isTop!='1'}">
                		style='display:none;'
                	</c:if>
                >置顶</div>

                <div class="filament_solid_ddd floatfix bor-t_red">
                    <a href="#" class="disp-ib ml20 mt20 pb2">${user.username }</a>
                    <label class="float_r mt20 mr30">1楼</label>
                    <div class="mt10 bor-t">
                        <div class="disp-ib ml20 mt20 va_t">
                            <div>
                            	<c:if test="${empty user.pic}">
                                <img src="${ctx}/portal/static/images/shouye_39.png" alt="" class="bbs-userpic"/>
                                </c:if>
                                <c:if test="${not empty user.pic}">
                                <img src="${ctx}/${user.pic }" alt="" class="bbs-userpic"/>
                                </c:if>
                            </div>
                            <p class="disp-ib txt-ac"><span>22</span><br/>主题</p>
                            <p class="disp-ib txt-ac ml10"><span>${user.score}</span><br/>积分</p>
                            <a class="disp-b mt10 txt-ac ft-c_red">朝圣者LV1</a>
                        </div>
                        <div style="word-wrap: break-word;" class="ml30 mt30 p10 disp-ib w80p va_t">
                           		${c.content}
                        </div>
                    </div>

                    <div class="bor-t mt80 floatfix">
                        <div class="float_r mt10 mr50 mb30">
                            <label>${praise.falsePraise}</label>
                           <button type="button" class="zan">赞</button>
                    </div>
                </div>
            </div>
            <!-- } 置顶 -->

			<c:forEach var="r" items="${replyPager.dataList}" varStatus="status">
						   <div class="bor-t_red">
                <div class="floatfix">
                    <a href="#" class="disp-ib ml20 mt20 pb2">${r.replyerName}</a>
                    <label class="float_r mt20 mr30">${status.index+2}楼</label>
                     
                    <div class="mt10 bor-t">
                        <div class="disp-ib ml20 mt20 va_t">
                            <div>
                               <c:if test="${empty r.postuserpic}">
                                <img src="${ctx}/portal/static/images/shouye_39.png" alt="" class="bbs-userpic"/>
                                </c:if>
                                <c:if test="${not empty r.postuserpic}">
                                <img src="${ctx }/${r.postuserpic }" alt="" class="bbs-userpic"/>
                                </c:if>
                            </div>
                            <p class="disp-ib txt-ac"><span>22</span><br/>主题</p>
                            <p class="disp-ib txt-ac ml10"><span>${r.score }</span><br/>积分</p>
                            <a class="disp-b mt10 txt-ac ft-c_red">朝圣者LV2</a>
                        </div>
                        <div class="ml30 mt15 p10 disp-ib w80p va_t">
                         			${r.content}
                        </div>
                    </div>

                    <div class="bor-t mt80 floatfix">
                        <div class="float_r mt5 mr50 mb20 formLine save${status.index+1}">
                            <button  type="button" class="btn-base"   onclick="deleteReply('${ctx}/web/passPostController/deleteReply?currentPage=${replyPager.currentPage}&contentCode=${r.code}&postCode=${c.code }')">删除</button>
                            <input type="text" class="w-100  number" value="${r.falsepraise}"/>
                            <label class="w-auto va_m">赞</label>
                            <a target="_self" href="javascript:void(0)" onclick="restorePraise('${r.code}','save${status.index+1}')"  class="va_m ml10">恢复系统值</a>
                            <a target="_self" href="javascript:void(0)" class="va_m ml5" onclick="ajaxSaveContentInfo('${c.code}','${r.code}','save${status.index+1}')">保存</a>
                        </div>
                    </div>
                </div>
           	 </div>
			</c:forEach>
			<c:if test="${empty replyPager.dataList}">
					
				 <div class="bor-t_red" style="color:red;text-align: center;"><div class="mt10 bor-t"> <br/>暂无回复信息</div></div>
			</c:if>
			
            <!-- } 4楼 -->
			<div  style="clear: both;"></div>
            <!-- 表格分页 -->
            <div class="paging">
           	  <%@include file="/common-html/pager.jsp" %>
           	 </div>
		</form>
        </div>
        <!-- } 模块管理 -->
    </div>
    
 
 		<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
 		<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	 <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
 		
 		
 		
 		 <script src="${ctxManage}/resources/js/dataValidation.js"></script>
    <script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
    <script>
        $('input.number').blur(function () {
            var thisVal = $(this).val();
            var regTest = $.VLDTOR.IsNum(thisVal);
            valid_txtBox_create(this, regTest, "只能为数字", "bottom");
        });
        $(function(){
			var  pageInfo =  '<div class="paging-info">'+
					    		'<span class="disp-ib">当前第&nbsp;${replyPager.currentPage }&nbsp;页</span>'+
					    		'<span class="disp-ib">共&nbsp;${replyPager.totalPage }&nbsp;页</span>'+
					    		'<span class="disp-ib">共&nbsp;${replyPager.totalCount }&nbsp;条</span>' +
					    		'</div>';
	    	$("#pageInfo").append(pageInfo);
			//
	    	
		});
    </script>
</body>
