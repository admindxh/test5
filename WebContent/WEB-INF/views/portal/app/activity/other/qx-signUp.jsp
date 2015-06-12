<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common-html/commonTag.jsp" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=0.8,maximum-scale=1.5"/>
    <meta name="author" content="huanl">
    <meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏"/>
    <meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！"/>
    <title>天上西藏-感受西藏魅力，体验西藏生活-天上西藏官网</title>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">

    <link rel="stylesheet" href="${ctxPortal}/assets/css/activity/qx-signUp.css"/>
</head>
<body>
<header>
    <h1 class="sign-title">
        <img src="${ctxPortal}/assets/icon/tj-icon.png" alt="提交报名资料"/>
        <span>提交报名资料</span>
    </h1>
</header>
<div class="sign-up">
    <form id="enroll_form" method="post">
    	<input type="hidden" name="activityCode" value="${activity.code }">
        <input type="hidden" name="OCS" value="${OCS }">
        
    	<c:forEach var="obj" items="${listEnrollForm }" varStatus="sta">
    		<c:if test="${obj.isRequire==0 }">
                <c:set var="req" value=" "/>
            </c:if>
            <c:if test="${obj.isRequire==1 }">
                <c:set var="req" value="*"/>
            </c:if>
            <c:choose>
            	<c:when test="${obj.propertyType==PROPERTY_TYPE_TEXT }">
            		<!-- 文本 -->
            		<div class="form-group">
			            <div class="sign-name">
			                <span class="mandatory">${req}</span>
			                <label>${obj.property}</label>
			            </div>
			            <div class="sign-inp">
			            	<input type="text" class="form-control baomyz" placeholder="请输入2-30个字符"
								proType="${obj.propertyType }"
                                prototypeName="${obj.property}"
                                isRequire="${obj.isRequire}"
                                name="listMemberEnrollDetail[${sta.index }].propertyValue"
			            	>
			            	<input 
			            		type="hidden" class="form-control"
                            	name="listMemberEnrollDetail[${sta.index }].enrollFormCode"
                           	    value="${obj.code }"
                            >
			            </div>
			        </div>
			        <!-- 文本 -->
            	</c:when>
            	<c:when test="${obj.propertyType==PROPERTY_TYPE_NUMBER }">
            		<!-- 数字-->
            		<div class="form-group">
			            <div class="sign-name">
			                <span class="mandatory">${req}</span>
			                <label>${obj.property}</label>
			            </div>
			            <div class="sign-inp">
			            	<input type="text" class="form-control baomyz" placeholder="最多输入30个数字"
			            		proType="${obj.propertyType }" 
                            	prototypeName="${obj.property}"
                            	isRequire="${obj.isRequire}"
                              	name="listMemberEnrollDetail[${sta.index }].propertyValue"
			            	>
                            <input type="hidden" maxlength="30" class="form-control"
                                name="listMemberEnrollDetail[${sta.index }].enrollFormCode"
                                value="${obj.code }"
                            >
			            </div>
			        </div>
            		<!-- 数字-->
            	</c:when>
            	<c:when test="${obj.propertyType==PROPERTY_TYPE_SELECT }">
            		<!-- 下拉框-->
            		<div class="form-group">
	            		<div class="sign-name">
			                <span class="mandatory">${req }</span>
			                <label>${obj.property}</label>
			            </div>
			            <div class="dropdown sign-inp">
			                <input type="hidden" class="baomyz" 
			                	proType="${obj.propertyType }"
			                	prototypeName="${obj.property}"
		                        isRequire="${obj.isRequire}"
		                        name="listMemberEnrollDetail[${sta.index }].propertyValue"
							>
							<input type="hidden" class="form-control"
	                            name="listMemberEnrollDetail[${sta.index }].enrollFormCode"
	                            value="${obj.code }"
	                        >
			                <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
		                        <label><span class="sign-cont">请选择</span><span class="sign-down"></span></label>
			                </button>
			                <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
			                    <c:forEach var="op" items="${obj.options }">
	                           		<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value='${op}'>${op}</a></li>
	                           	</c:forEach>
			                </ul>
			            </div>
		            </div>
            		<!-- 下拉框-->
            	</c:when>
            	<c:otherwise>
            	</c:otherwise>
            </c:choose>
    	</c:forEach>
        <div class="sign-tj">
            <input id="J_enroll" type="button" value="提交" />
        </div>
    </form>
</div>
<script src="${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js"></script>
<script src="${ctxPortal}/modules/bootstrap/3.3.1/js/_dropdown.js"></script>
<script src="${ctxPortal}/assets/js/activity/qx-signUp.js"></script>
<script type="text/javascript">

	$("#J_enroll").click(function(){
		pay();
	});

	//支付
	function pay() {
        if ('${isEnd}' == 'yes') {
            alert("活动已结束");
        } else {
            var url = "${ctx}portal/order/checkPay";
            $.post(url, {activityCode: '${activity.code}', OCS: '${OCS }'}, function (response) {
                if (response == 'need_login') {
                    alert("请先登录");
                    //window.location.href="${ctx}activity/mobileEnroll?code=${activity.code }&OCS=${OCS}"
                } else if (response == 'need_enroll') {
                	if(yanz()){
                		
                		
                		var $form = $('#enroll_form');
                        var url = '${ctx}portal/memberEnrollDetailController/addMemberEnrollDetail';
                        //alert($form.serialize());
                        if (yanz()) {
                            $.post(url, $form.serialize(), function (data) {
                                if (data == 'success') {
                                    //alert("保存成功");
                                	window.location.href="${ctx}/alipay/pay?activityCode=${activity.code}"
                                } else if (data == 'error') {
                                    alert("保存失败");
                                } else if (data == 'need_login') {
                                    alert("请先登录");
                                }
                            });
                        }
                		
                		
                		
                	}
                } else if (response == 'already_pay') {
                    alert("已经支付");
                } else if (response == 'error') {
                    alert("生成订单发生错误");
                } else if (response == 'to_pay') {
                    window.location.href="${ctx}/alipay/pay?activityCode=${activity.code}"
                }
            });
        }
	}
	
	//报名验证必填
	function yanz(){
	    var temp = false;
	    var msg = "";
	    var inputNode = $("input.baomyz");
	    var regName = /^(\S|\s){2,30}$/,
	            regNum = /^[0-9]{1,30}$/;
	    for (var i = 0; i < inputNode.length; i++) {
	        var val = inputNode.eq(i).val();
	        var prototypeName = inputNode.eq(i).attr("prototypeName");
	        inputNode.eq(i).val($.trim(val))
	        if (inputNode.eq(i).attr("isRequire") == 1) {
	            if (inputNode.eq(i).attr("proType") == "${PROPERTY_TYPE_TEXT}") {
	                if (!regName.test(val) || $.trim(val) == "") {
	                    temp = true;
	                    msg = prototypeName + ",需要输入2-30个字符";
	                    break;
	                }
	            } else if (inputNode.eq(i).attr("proType") == "${PROPERTY_TYPE_NUMBER}") {
	                if (!regNum.test(val)) {
	                    temp = true;
	                    msg = prototypeName + ",最多输入30个数字";
	                    break;
	                }
	            } else {
	                if (val == "") {
	                    temp = true;
	                    msg = prototypeName + ",为必填项";
	                    break;
	                }
	            }
	        }
	    }
	    if (temp) {
	        alert(msg);
	        return false;
	    }
	    return true;
	}
</script>
</body>
</html>