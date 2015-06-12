<%@page import="com.rimi.ctibet.domain.CommonOrder"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/commonTag.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="author" content="quansenx">
	<meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
	<meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
	<title>推荐详情_天上西藏</title>
	<link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctxPortal}/assets/css/common.css" />
	<link rel="stylesheet" href="${ctxPortal}/assets/css/footer.css" />
	<link rel="stylesheet" href="${ctxPortal}/assets/css/goods.css" />
	<!--[if lt IE 9]>
    <script src="${ctxPortal }modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal }modules/fix/respond.min.js"></script>
    <![endif]-->
	<script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
	<script>
		seajs.config({
	        alias: {
	        	"Validform": "${ctxPortal}/modules/Validform/5.3.2/Validform.min.js",
	            "jquery": "jquery/jquery/1.11.1/jquery.js"
	        }
	    });
	</script>
</head>
<body>
	<%-- <jsp:include page="${root}/portal/headerFooterController/header"/> --%>
	<jsp:include page="${root}/ride/ridePropgram"></jsp:include>
	<div class="container" style="margin-top: 40px;">
		<h1 class="gd-h">购买清单</h1>
		<div class="goods-detail goods-buy clearfix">
			<div class="gd-pic">
				<a href="#">
					<img src="${ctx}${equipment.smallimg }" alt="gd1.jpg" width="220" height="160"></a>
			</div>
			<div class="gd-desc">
				<h1 class="gd-heading">${equipment.name }</h1>
				<div class="gd-text clearfix"> <em>数量：</em>
					<span class="range">
						<a href="javascript:;" class="J_Minus disable">-</a>
						<input type="text" class="range-text J_Value" value="${num }">
						<a href="javascript:;" class="J_Plus">+</a>
					</span>
					<em>件 (库存${equipment.count }件)</em>
				</div>
				<div class="gd-text clearfix"> <em>单价：&yen;<strong id="J_Price">${equipment.price}</strong></em> </div>
				<div class="gd-text clearfix"> <em>总价：</em>
					<span>&yen;<strong class="J_Price">${equipment.price * num}</strong></span>
				</div>
			</div>
		</div>
		<div class="goods-form">
			<form id="payform" method="post" action="${ctx}alipay/commonPay">
				<input type="hidden" name="code" value="<%=CommonOrder.createEquipmentCode()%>">
				<input type="hidden" name="name" value="${equipment.name}">
				<input type="hidden" name="note" value="${equipment.summary}">
				<input type="hidden" name="type" value="<%=CommonOrder.TYPE_EQUIPMENT%>">
				<input type="hidden" name="source" value="<%=CommonOrder.SOURCE_OFFICAL%>">
				<input type="hidden" name="goodsCode" value="${equipment.code }">
				<input type="hidden" id="J_memberCode" name="memberCode">
				<div class="form-group">
					<label for="contact ">收货人</label>
					<input type="text" class="form-control" id="contact " placeholder="收货人" name="receiver" dataType="*2-200" nullmsg="请输入收货人"></div>
				<div class="form-group">
					<label for="phone">联系电话</label>
					<input type="text" class="form-control" id="phone" placeholder="联系电话" name="mobile" dataType="m" nullmsg="请输入联系电话" errormsg="请输入正确的联系号码"></div>
				<div class="form-group">
					<label for="address">送货地址</label>
					<input type="text" class="form-control" id="address" style="width: 600px;" placeholder="送货地址" name="address" dataType="*" nullmsg="请输入送货地址"></div>
				<div class="form-group">
					<label for="postcode">邮编</label>
					<input type="text" class="form-control" id="postcode" placeholder="邮编" name="zipcode" dataType="p" nullmsg="请输入6位数字的邮政编码"></div>
				<div class="form-group">
					<label for="mode ">支付方式</label>
					<div class="ui-radio">
						<i class="radio"></i>
						<span class="mode-alipay"></span>
					</div>
				</div>
				<div class="total clearfix">
					<div class="gd-text pull-left"> <em>应付总额：</em>
						<span>&yen;<strong class="J_Price">${equipment.price * num}</strong></span>
						<input type="hidden" id="J_money" name="money" value="${equipment.price * num}">
						<input type="hidden" id="J_num" name="num" value="${num}">
					</div>
					<button type="submit" class="btn-order">提交订单</button>
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="${root}/portal/headerFooterController/footer"/>
	<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
	<script>
		seajs.use(['jquery','Validform'], function($){

			$(function(){
				var $value = $('.J_Value'), // 数量输入表单 input
				$plus = $('.J_Plus'), // +
				$minus = $('.J_Minus'), // -
				$price = $('.J_Price'), // 总价
				_price = parseInt($('#J_Price').text()); // 单价
				//console.log(_price)
				$plus.on('click', function(event) {
					event.preventDefault();
					var number = parseInt($value.val());
					var left = ${equipment.count };
					if(number<left){
						$value.val(++number);
						$minus.removeClass('disable')
						_count(number);
					}else{
						//alert("最多只能购买" + left + "件");
					}
				});
				$minus.on('click', function(event) {
					event.preventDefault();
					var number = parseInt($value.val());
					--number;
					if (number <= 1) {
						$value.val(1)
						number = 1
						$minus.addClass('disable')
					} else {
						$value.val(number)
					}
					_count(number);
				});
				$value.on('keyup', function(event) {
					event.preventDefault();
					if(event.keyCode != 37 && event.keyCode != 39){
						var number = parseInt($value.val()||1);
						if (number <= 1) {
							$value.val(1)
							number = 1
							$minus.addClass('disable')
						} else {
							$value.val(number)
							$minus.removeClass('disable')
						}
						_count(number);
					}
				});
				function _count(number){
					$price.text(number*_price)
					$("#J_money").val(number*_price);
					$("#J_num").val(number);
				}
			})
			
			function updateNum(){
				var params = {'code':'${equipment.code}', 'num':$(".J_Value").val()};
				$.ajax({
					type: "post",
					url: "${ctx}ride/eqindex/updateEquipmentNum",
					data : params,
					async: false,
					cache: false,
					success: function(response){
						if(response=="success"){
							//成功
						}
					}
				});
			}
			
			var va = $('#payform').Validform({
				tiptype: 4,
				postonce:true,
				callback: function(form){
					var left = ${equipment.count };
					if($('.J_Value').val()>left){
						alert("库存不足");
						va.resetStatus();
						return false;
					}
					if(left==0){
						alert("没有库存");
						return false;
					}
					var rect = false
					var url = "${ctx}portal/clientLog/checkLogin";
					$.ajax({
						type: "post",
						url: url,
						dataType: "json",
						async: false,
						cache: false,
						success: function(response){
							if(response.msg=="needlogin"){
								$('[data-toggle="login"]').click();
								rect = false
							}else{
								$("#J_memberCode").val(response.user.code);
								updateNum();
								rect = true
							}
						}
					});
					return rect;
				}
			});
			
		});
	</script>
</body>
</html>