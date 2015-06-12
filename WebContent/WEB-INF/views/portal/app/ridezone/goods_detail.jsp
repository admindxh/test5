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
            "jquery": "jquery/jquery/1.11.1/jquery.js"
        }
    });
	</script>
</head>
<body>
	<%-- <jsp:include page="${root}/portal/headerFooterController/header"/> --%>
	<jsp:include page="${root}/ride/ridePropgram"></jsp:include>
	<div class="container">
		<div class="place" style="margin-top: 60px;">
			<ul>
				<li class="location">当前位置：</li>
				<li>
					<a href="${ctx}ride">骑行专区</a>
				</li>
				<li class="slipt"></li>
				<li>
					<a href="${ctx}ride/eqindex/list">装备推荐</a>
				</li>
				<li class="slipt"></li>
				<li>
					<a href="${ctx}ride/eqindex/more/${pro.code}.html">${pro.programaName }</a>
				</li>
				<li class="slipt"></li>
				<li style="color: red;">${equipment.name }</li>
			</ul>
		</div>
		<form method="post" id="buyForm" action="${ctx}ride/eqindex/create-order">
			<div class="goods-detail clearfix">
				<div class="gd-pic">
					<a href="#">
						<img src="${ctx}${equipment.smallimg }" alt="gd1.jpg" width="400" height="300"></a>
				</div>
				<div class="gd-desc">
					<h1 class="gd-heading">${equipment.name }</h1>
					<p class="gd-intro">${equipment.summary }</p>
					<div class="gd-text clearfix"> <em>价格：</em>
						<span>&yen;<strong class="J_Price">${equipment.price}</strong></span>
					</div>
					<div style="${equipment.payType eq '2'?'display: none;':''}" class="gd-text clearfix"> <em>数量：</em>
						<span class="range">
							<a href="javascript:;" class="J_Minus disable">-</a>
							<input type="text" class="range-text J_Value" value="1" name="num">
							<input type="hidden" value="${equipment.code}" name="code">
							<a href="javascript:;" class="J_Plus">+</a>
						</span>
						<em>件 (库存${equipment.count }件)</em>
					</div>
					<c:if test="${equipment.payType eq '1'}">
						<button type="button" class="now-buy J_buy">购买</button>
					</c:if>
					<c:if test="${equipment.payType eq '2'}">
						<button type="button" class="now-buy" onclick="javascript:window.location.href='${equipment.payurl}'">购买</button>
					</c:if>
				</div>
			</div>
		</form>
		<h2 class="gd-h">商品详情</h2>
		<div class="goods-content">${equipment.content }</div>
	</div>
	<jsp:include page="${root}/portal/headerFooterController/footer"/>
	<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
	<script>
		seajs.use('jquery', function($){
			$(function(){
				var $value = $('.J_Value'),
				$plus = $('.J_Plus'),
				$minus = $('.J_Minus'),
				$price = $('.J_Price'),
				oldValue,
				_price = parseInt($price.text());
				$plus.on('click', function(event) {
					event.preventDefault();
					var left = ${equipment.count };
					var number = parseInt($value.val());
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
					if(event.keyCode == 37 || event.keyCode == 39){
						
					}else{
						var number = isNaN(parseInt($value.val())) ? oldValue : parseInt($value.val()||1);
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
				}).on('keydown', function(e){
					oldValue = parseInt($value.val())
				});
				function _count(number){
					$price.text(number*_price)
				}
			})
			
			$(".J_buy").click(function(){
				var url = "${ctx}portal/clientLog/checkLogin";
				$.post(url,{},function(response){
					if(response.msg=="needlogin"){
						$('[data-toggle="login"]').click();
					}else{
						var left = ${equipment.count };
						if($('.J_Value').val()>left){
							alert("库存不足");
							return false;
						}
						if(left==0){
							alert("没有库存");
							return false;
						}
						$("#buyForm").submit();
					}
				}, 'json');
			});
			
			
		})
	</script>
</body>
</html>