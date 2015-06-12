<%--<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/page/include.inc.jsp"%>
<!doctype html>
<html lang="zh-cn">
<head>
	<base href="<%= basePath%>">
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<title>睿峰苹果在线教育——支付</title>
	<%@ include file="/WEB-INF/jsp/common/front/common.jsp"%></head>
<body>
	<%@ include file="/WEB-INF/jsp/common/front/head.jsp"%>
	<div class="pay">
		<div class="container">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="pull-left">
						尊敬的：${myuser.nikename }
						<span style="margin-left: 15px;">您购买的课程：${course.name}</span>
					</div>
					<div class="pull-right">
						需支付 <c:if test="${course.price!=0}">&yen;${course.price} 元 或者 ${course.price} 金币</c:if>
						<c:if test="${course.score!=0}"><c:if test="${course.price!=0}">或者</c:if> ${course.score} 积分</c:if>
					</div>
				</div>
			</div>
			<div class="clearfix">
				<div class="pull-left">请选择支付方式：</div>
				<div class="pull-left">
					<!-- Nav tabs -->
					<ul class="nav nav-tabs" role="tablist">
						<c:if test="${course.price!=0 && course.score == 0}">
							<li class="active">
								<a href="#pay" role="tab" data-toggle="tab">在线支付</a>
							</li>
						</c:if>
						<c:if test="${course.price!=0 && course.score != 0}">
							<li class="active">
								<a href="#pay" role="tab" data-toggle="tab">在线支付</a>
							</li>
							<li >
								<a href="#score" role="tab" data-toggle="tab">积分支付</a>
							</li>
						</c:if>
						<c:if test="${course.score!=0 && course.price == 0}">
							<li class="active">
								<a href="#score" role="tab" data-toggle="tab">积分支付</a>
							</li>
						</c:if>
					</ul>
				</div>
			</div>
			<!-- Tab panes -->
			<div class="tab-content pay-type">
			<c:if test="${course.price!=0}">
				<div class="tab-pane active" id="pay">
					<form action="alipay/pay.html" method="post" id="payForm">
						<input type="hidden" value="${course.id}" name="cid"/>
						<input type="hidden" name="broadCastURL" value="${broadCastURL }" />
						<input type="radio" name="defaultBankNumber" checked="checked" style="display:none;">
						<div class="form-group active z-radio" data-action="alipay/pay.html">
							<i class="zhifu-radio"></i>使用
							<span>支付宝</span>
							<img src = "WebPage/dist/images/payicons/zhifubao.jpg" />
							[<a href = "http://abc.alipay.com/i/index.htm"  target="_blank">使用帮助</a>]
						</div>
						<div class="form-group z-radio" data-action="tenpay/pay.html">
							<i class="zhifu-radio"></i>使用
							<span>财付通</span>
							<img src = "WebPage/dist/images/payicons/caifutong.jpg" />
							[<a href = "http://help.tenpay.com/helpcenter/index.shtml"  target="_blank">使用帮助</a>]
						</div>
						<div class="form-group z-radio" data-action="quickpay/pay.html">
							<i class="zhifu-radio"></i>使用
							<span>银联电子支付公司</span>
							<img src = "WebPage/dist/images/payicons/yinlian.jpg" />
							[<a href = "http://www.chinapay.com/"  target="_blank">使用帮助</a>]
						</div>
						<div class="form-group z-checkbox" data-action="quickpay/pay.html">
							<input type="checkbox" name="defaultJinbiNumber" style="display:none;">
							<i class="zhifu-checkbox"></i>使用
							<span>金币</span>
							<img src = "WebPage/dist/images/payicons/jinbi.jpg" />
						</div>
						<div class="form-group" style="border-bottom:none;">
							<p>中国银联仅为客户交易提供支付信息转接渠道，客户与丙方的交易争议或纠纷与中国银联无关</p>
							<p>客户继续交易的行为视同客户同意并接受上述声明</p>
						</div>
						<div class="form-group text-right grade-opt" style="border-bottom:none;">
							<button type="submit" class="btn btn-send">确认购买</button>
							<button type="button" class="btn btn-cancel">取消购买</button>
						</div>
					</form>
				</div>
				<div class="tab-pane" id="score">
					<form action = "scorepay.html" method = "post">
						<input type = "hidden" name = "cid" value = "${course.id}" />
						<input type="hidden" name="broadCastURL" value="${broadCastURL }" />
						<div class="panel panel-default text-center" style="font-size:16px;padding: 30px;">
							<div class="panel-body">
								<p>购买该课程需要支付${course.score}积分。</p>
								<c:if test="${course.score>myuser.score}">你的积分不足。</c:if>
							</div>
						</div>
						<div class="form-group text-right grade-opt" style="border-bottom:none;">
							<c:if test="${course.score<=myuser.score}"><button type="submit" class="btn btn-send">确认购买</button></c:if>
							<button type="button" class="btn btn-cancel">取消购买</button>
						</div>
					</form>
				</div>
			</c:if>
			<c:if test="${course.price == 0}">
				<div class="tab-pane active" id="score">
					<form action = "scorepay.html" method = "post">
						<input type = "hidden" name = "cid" value = "${course.id}" />
						<input type="hidden" name="broadCastURL" value="${broadCastURL }" />
						<div class="panel panel-default text-center" style="font-size:16px;padding: 30px;">
							<div class="panel-body">
								<p>购买该课程需要支付${course.score}积分。</p>
								<c:if test="${course.score>myuser.score}">你的积分不够。</c:if>
							</div>
						</div>
						<div class="form-group text-right grade-opt" style="border-bottom:none;">
							<c:if test="${course.score<=myuser.score}"><button type="submit" class="btn btn-send">确认购买</button></c:if>
							<button type="button" class="btn btn-cancel">取消购买</button>
						</div>
					</form>
				</div>
			</c:if>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/common/front/loginWindow.jsp"%>
	<script>
	$(function(){
		$('.btn-cancel').click(function() {
			var broadCastURL = '${broadCastURL}';
			if (broadCastURL != null && broadCastURL != ''){
				window.location.href = '${broadCastURL}';
			} else {
				window.location.href = '<%=basePath%>/courseIntro/execute.html?cid=${course.id}';
			}
		})
		$('.pay-type .z-radio').click(function(){
			$(this).addClass('active').siblings('.z-radio').removeClass('active')
			$('#payForm').attr('action', $(this).attr('data-action'));
		})
		$('.pay-type .z-checkbox').click(function(){
			var $this = $(this);
			if ($this.hasClass('active')) {
				$this.removeClass('active')
				$this.children('input[type="checkbox"]').prop('checked', false)
			}else{
				$this.addClass('active')
				$this.children('input[type="checkbox"]').prop('checked', true)
			}
		})
	})
	</script>
</body>
</html>--%>