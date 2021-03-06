<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="rimiTag" uri="/rimi-tags"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>管理员首页</title>
	<%@include file="/common-html/common.jsp" %>
	<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/admin-home.css" /><link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
</head>
<body>
	<!-- 当前页面位置 -->
	<div class="location">
		<label>您当前位置为:</label>
		<span><a>后台首页</a></span>
	</div>
    <!-- main { -->
    <div class="admin-main">
		<!-- 统计图表 -->
		<div id="echart"></div>

		<!-- 统计数据 -->
		<div id="numStatus">
			<ul>
				<li class="boder_r">
					<p>注册数</p>
					<span>${memTotal }</span>
				</li>
				<li class="boder_r">
					<p>评论量</p>
					<span>${replyTotal }</span>
				</li>
			</ul>
		</div>
<!-- 待处理信息 { -->
		<div id="waitAudit">
			<!-- 全部待处理 帖子/回复数 -->
			<div class="waitAudit_1">
				<span class="waitAudit_title">全部待处理&nbsp;帖子/回复数</span>
				<span class="waitAudit_info">
					<span class="info_num">${uncheckedpostandreplynum }</span>
                    <rimiTag:perm url="web/tssq/uncheckedPostList">
					    <a href="${ctx}/web/tssq/uncheckedPostList" class="btn_fff">处理</a>
                    </rimiTag:perm>
				</span>
			</div>

			<!-- 全部待处理 评价/回复数 -->
			<div class="waitAudit_2">
				<span class="waitAudit_title">全部待处理&nbsp;评价/回复数</span>
				<span class="waitAudit_info">
					<span class="info_num">${allReplyCount }</span>
				</span>
			</div>

			<!-- 全部待处理 评价/回复数——详细数据 -->
			<div class="waitAudit_2_detail">
				<!-- 景点 -->
				<div class="detail_infoBlock">
					<div class="audit_icon_1"></div>
					<div class="audit_intro">待处理的<br>评价&回复数</div>
                    <rimiTag:perm url="web/replyManageController/forReviewReplyManageList">
					    <a href="${ctx }/web/replyManageController/forReviewReplyManageList?replyType=view_reply" class="btn_0a8">处理</a>
                    </rimiTag:perm>
					<div class="info_num2">${viewCount }</div>
				</div>

				<!-- 商户 -->
				<div class="detail_infoBlock ml-15">
					<div class="audit_icon_2"></div>
					<div class="audit_intro">待处理的<br>评价&回复数</div>
                    <rimiTag:perm url="web/replyManageController/forReviewReplyManageList">
					    <a href="${ctx }/web/replyManageController/forReviewReplyManageList?replyType=merchant_reply" class="btn_0a8">处理</a>
                    </rimiTag:perm>
					<div class="info_num2">${merchantCount }</div>
				</div>

				<!-- 读西藏 -->
				<div class="detail_infoBlock mt-18">
					<div class="audit_icon_3"></div>
					<div class="audit_intro">待处理的<br>评价&回复数</div>
                    <rimiTag:perm url="web/replyManageController/forReviewReplyManageList">
					    <a href="${ctx }/web/replyManageController/forReviewReplyManageList?replyType=read_tibet_reply" class="btn_0a8">处理</a>
                    </rimiTag:perm>
					<div class="info_num2">${readTibetCount }</div>
				</div>

				<!-- 攻略 -->
				<div class="detail_infoBlock ml-15 mt-18">
					<div class="audit_icon_4"></div>
					<div class="audit_intro">待处理的<br>评价&回复数</div>
                    <rimiTag:perm url="web/replyManageController/forReviewReplyManageList">
					    <a href="${ctx }/web/replyManageController/forReviewReplyManageList?replyType=strategy_reply" class="btn_0a8">处理</a>
                    </rimiTag:perm>
					<div class="info_num2">${strategyCount }</div>
				</div>
			</div>
		</div><!-- } 待处理信息 -->
	</div><!-- } main -->

    <!-- JS引用部分 -->
  
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
	<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/echarts/echarts.js" type="text/javascript"></script>
	<script type="text/javascript">
		var    stdate  = "${stdate}";//前7天日期
		var    pv  = "${pv}";//pv 值
		var    acnumber  = "${acnumber}";//活跃数
		require.config({
			paths: {
				echarts: '${ctxManage}/resources/plugin/echarts'
			}
		});

		require(
			[
				'echarts',
				'echarts/chart/bar',
				'echarts/chart/line'
			],
			function (ec) {
				//--- 折柱 ---
				var myChart = ec.init(document.getElementById('echart'));
				myChart.setOption({
					title : {
						text: '数值',
						x: 24,
						textStyle: {
							fontSize: 18,
							fontWeight: 'bolder',
							color: '#990000'
						}
					},
					tooltip : {
						trigger: 'axis'
					},
					legend: {
						data:['PV','活跃用户'],
						itemWidth: 30,
						itemHeight: 22,
						itemGap: 50,
						y: 'bottom',
						selectedMode: true,
						textStyle: {
							color: '#333',
							fontFamily: '微软雅黑',
							fontWeight: 'bold',
							fontSize: 14
						}
					},
					toolbox: {
						show : false
//						feature : {
//							mark : {show: true},
//							dataView : {show: true, readOnly: false},
//							magicType : {show: true, type: ['line', 'bar']},
//							restore : {show: true},
//							saveAsImage : {show: true}
//						}
					},
					grid: {
						x: 50,
						y: 40,
						x2: 20,
						y2: 66,
						borderColor: '#f6f6f6'
					},
					calculable : true,
					xAxis : [
						{
							type : 'category',
							boundaryGap: false,
							// 轴线
							axisLine : {
								show: true,
								lineStyle: {
									color: '#f6f6f6',
									type: 'solid',
									width: 1
								}
							},
							// 轴标记
							axisTick : {
								show: false,
								length: 5,
								lineStyle: {
									color: '#f6f6f6',
									type: 'solid',
									width: 1
								}
							},
							axisLabel : {
								show:true,
								textStyle: {
									color: '#c3c3c3',
									fontFamily: 'arial',
									fontSize: 12
								}
							},
							data : eval('('+stdate+')')
						}
					],
					yAxis : [
						{
							type : 'value',
							splitArea : {show : false},
							axisLine : {
								show: true,
								lineStyle: {
									color: '#e3e3e3',
									type: 'solid',
									width: 1
								}
							},
							axisLabel : {
								show:true,
								textStyle: {
									color: '#c3c3c3',
									fontFamily: 'arial',
									fontSize: 12
								}
							}
						}
					],
					series : [
						{
							name:'PV',
							type:'line',
							data : eval('('+pv+')')
						},
						{
							name:'活跃用户',
							type:'line',
							data :eval('('+acnumber+')')
						}
					]
				});
			}
		);
		var win_h = $(window).width();
		//.log(win_h);

	</script>


	<!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>