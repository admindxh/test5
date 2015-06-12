<%@ page language="java" contentType="text/html;charset=UTF-8" import="com.rimi.ctibet.domain.LogUser" pageEncoding="UTF-8" %>
<%@include file="/common-html/commonTag.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<base target="_blank" href="${ctx }">
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="keywords" content="西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化"/>
	<meta name="description" content="天上西藏网为您提供全面靠谱的西藏旅游资讯，旅游攻略，西藏自助游，西藏自驾游，西藏骑行，全面权威的西藏文化介绍。打造权威的西藏文化旅游网。"/>
	<title>【天上西藏】西藏旅游-自驾米林</title>
	<meta name="keywords" content="西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化，西藏徒步，西藏攻略，西藏音乐，西藏视频"/>
	<meta name="description" content="天上西藏网为您提供全面靠谱的西藏旅游资讯，包括旅游攻略，西藏自助游，西藏自驾游，西藏骑行，西藏徒步信息，全面权威的西藏文化介绍。打造权威的西藏文化旅游网。"/>
	<link rel="stylesheet" href="http://111.11.197.13/portal/modules/bootstrap/3.3.1/css/bootstrap.min.css" />
	<style>
		.banner{
			position: relative;
			height: 505px;
			min-width: 1200px;
		}
		.b-pic{
			position: absolute;
			width: 100%;
			height: 505px;
			background: url(portal/assets/images/1.jpg) center center no-repeat;
			-webkit-background-size: cover;
			background-size: cover;
		}
		.b-point-wrap{
			position: relative;
			height: 505px;
			width: 1200px;
			margin-left: auto;
			margin-right: auto;
		}
		.b-point{
			position: absolute;
			bottom: -38px;
			width: 100%;
			height: 420px;
			background: url(portal/assets/images/2.png) center center no-repeat;
			-webkit-background-size: cover;
			background-size: cover;
			z-index: 1;
		}
		.m-wrap{
			position: relative;
			font-family:'Microsoft Yahei';
		}
		.mw1{
			width: 100%;
			height: 978px;
			background: url(portal/assets/images/b1.png) center center no-repeat;
			min-width: 1200px;
		}
		.mw2{
			width: 100%;
			height: 1537px;
			background: url(portal/assets/images/b2.png) center center no-repeat;
			min-width: 1200px;
		}
		.mw3{
			width: 100%;
			height: 300px;
			background: url(portal/assets/images/b3.png) center center no-repeat;
			min-width: 1200px;
		}
		.text-cloud{
			margin-left: auto;
			margin-right: auto;
			width: 1045px;
			height: 164px;
			background: url(portal/assets/images/3.png) right center no-repeat;
		}
		.text-cloud h3{
			font-size: 18px;
		  color: white;
		  padding: 40px 30px;
		  margin: 0;
		  margin-left: 45px;
		  line-height: 30px;
		}
		.layout{
			margin-left: auto;
			margin-right: auto;
			width: 1045px;
		}
		.layout-inner{
			margin-left: 45px;
		}
		.heading{
			height: 48px;
			width: 1000px;
			padding-left: 155px;
			color:#333;
			line-height: 42px;
			font-size: 30px;
			background: url(portal/assets/images/hbg.png) 0 0 no-repeat;
			margin: 0;
		}
		.mt35{
			margin-top: 35px;
		}
		.pt35{
			padding-top: 35px;
		}
		.spot-item{
			margin-top: 48px;
		}
		.mb48{
			margin-bottom: 48px;
		}
		.spot-item img{
			float: left;
		}
		.si-text{
			position: relative;
			margin-left: 510px;
			height: 264px;
		}
		.si-text .text-heading{
			margin: 0;
			font-size: 30px;
			border-bottom: 2px solid #2fb460;
			padding-bottom: 10px;
		}
		.text-heading small{
			display: block;
			font-size: 18px;
			color: #333;
			margin-top: 5px;
		}
		.th-text{
			margin-top: 10px;
			font-size: 14px;
			color: #808080;
		}
		.si-more{
			position: absolute;
			left: 0;
			bottom: 0;
			width: 186px;
			height: 39px;
			background: url(portal/assets/images/db.png) 0 0 no-repeat;
		}
		.tel{
			position: absolute;
		  left: 0;
		  bottom: 80px;
		  height: 18px;
		  padding-left: 35px;
		  background: url(portal/assets/images/p.png) left center no-repeat;
		  font-size: 16px;
		  color: #0c9c42;
		  margin: 0;
		  line-height: 18px;
		}
		.list-wrap{
			overflow: hidden;
		}
		.list-wrap ul{
			margin-left: -20px;
		}
		.lu-item{
			width: 490px;
			height: 460px;
			border-bottom: 1px dashed #b4b4b4;
			float: left;
			margin-left: 20px;
		}
		.lu-item.adjust{
			height: 490px;
			border-bottom: none;
		}
		.lu-heading{
			margin-top: 50px;
		  height: 44px;
		  line-height: 44px;
		  color: white;
		  font-size: 16px;
		  background: url(portal/assets/images/cloud-header.png) 0 0 no-repeat;
		  margin-bottom: 35px;
		  padding-left: 70px;
		  white-space: nowrap;
		  overflow: hidden;
		  text-overflow: ellipsis;
		  width: 385px;
		}
		.lu-text{
			margin-top: 20px;
			font-size: 14px;
			color: #808080;
			height: 100px;
		}
		.adjust .lu-text{
			height: 150px;
		}
		.services{
			position: relative;
			margin-top: 30px;
			font-size: 14px;
			color: #808080;
		}
		.s-more{
			position: absolute;
			top: -2px;
			right: 0;
			width: 92px;
			height: 24px;
			background: url(portal/assets/images/d.png) 0 0 no-repeat;
		}
		.links a{
			font-size: 18px;
			color:#333333;
			text-decoration: underline;
			margin-top: 50px;
			margin-right: 80px;
			height: 32px;
			line-height: 32px;
			padding-left: 22px;
			background: url(portal/assets/images/i.png) left center no-repeat;
			display: inline-block;
			*display: inline;
			*zoom: 1;
		}
		#qrcode{
			display: none;
		}
		.navbar{
		margin-bottom: 0;}
	</style>
	<script id="seajsnode" src="portal/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
        // Set configuration
        seajs.config({
            alias: {
            	"jquery": "jquery/jquery/1.11.1/jquery.js",
                "common/css": "${ctx }portal/assets/css/common.css",
                "footer/css": "${ctx }portal/assets/css/footer.css",
                "activity/css": "${ctxPortal }/assets/css/activity/activity.css",
                "goup": "${ctx }portal/modules/goup/1.0.0/jquery.goup.js"
            }
        });
        seajs.use(['common/css','footer/css','activity/css','jquery','goup']);
    </script>
</head>
<body>
	<header class="navbar navbar-dark" id="top" role="banner">
	    <div class="container">
	        <div class="navbar-header pull-left">
	            <a href="${ctx}home" class="navbar-brand">
	                <img src="${ctxPortal }/assets/icon/logo-white.png" alt="logo"></a>
	        </div>
	        <div class="pull-right login-and-share bdsharebuttonbox" style="position: relative;padding-right:135px;">
	            <c:choose>
	                <c:when test="${empty lgUser}">
	                    <a href="<%=basePath %>portal/registerController/register">注册</a>
	                    <a id="login-btn" href="#loginModal" data-toggle="login">登录</a>
	                </c:when>
	                <c:otherwise>
	                    <div class="pull-left" style="margin-top: 9px;margin-right: 5px;position: relative;"><span
	                            class="badge" style="position: absolute; top: -5px; right: -15px; z-index: 1; "></span>
	                        <img width="40px" height="40px" src="${ctx}${lgUser.pic}" alt="用户头像"/>
	                        <i class="umark umark_b"></i></div>
	                    <a href="${ctx}member/show/${lgUser.code}.html"
	                       class="pull-left">${lgUser.username }</a>
	                    <a href="javascript:void(0);" onclick="logout();" class="pull-left">退出</a>
	                </c:otherwise>
	            </c:choose>
	            <div style="position: absolute;right:0;top: 0;">
	                <a href="#" class="share weixin" data-cmd="weixin" title="分享到微信">微信</a>
	                <a href="#" class="share weibo" data-cmd="tsina" title="分享到微博">微博</a>
	                <a href="#" class="share qzone" data-cmd="qzone" title="分享到QQ空间">QQ空间</a>
	            </div>
	        </div>
	    </div>
	</header>
	<%-- <jsp:include page="${''}/portal/headerFooterController/header"/> --%>
	<div class="banner" role="banner">
		<div class="b-pic"><h1 style="visibility: hidden;">自驾米林</h1></div>
		<div class="b-point-wrap">
			<div class="b-point"><h2 style="visibility: hidden;">云端上的桃花源</h2></div>
		</div>
	</div>
	<!-- main -->
	<div class="m-wrap">
		<div class="mw1">
			<div class="text-cloud">
				<h3>
					坐落在西藏东南部、林芝地区西南部、雅鲁藏布江中下游的药洲米林，是国道318醉美景观的延伸、省道306醉美景观的核心、藏东环线醉美景观的焦点、浏览藏东南空中美景的落脚点。湿润的气候、2950米的海拔、80%的含氧量、洁净的空气、极高的森林覆盖率和全年不超过30摄氏度的温度，米林在这里等着您！
				</h3>
			</div>
			<div class="layout">
				<div class="layout-inner">
					<h2 class="heading mt35">旗舰景点</h2>
					<ul class="list-unstyled">
						<li class="spot-item clearfix">
							<a target="_blank" href="http://www.ctibet.cn/tourism/view/detail?code=VIEW4232103162118241"><img src="portal/assets/images/i1.jpg" width="470" height="264" alt="图片描述"></a>
							<div class="si-text mb48">
								<h3 class="text-heading">雅鲁藏布大峡谷旅游区AAAA<small>（到世界最大峡谷，观中国最美山峰）</small></h3>
								<p class="th-text">雅鲁藏布大峡谷全长504.6公里，极值深度6009米，是世界上最大的峡谷。其水由固态的万年冰雪到沸腾的温泉，从涓涓细流到帘帘飞瀑；其山从遍布热带雨林到直入云天的皑皑白雪，景观十分神奇。</p>
								<p class="tel">服务咨询电话：13908982199</p>
								<a target="_blank" href="http://www.ctibet.cn/tourism/view/detail?code=VIEW4232103162118241" class="si-more"><span class="sr-only">查看详情</span></a>
							</div>
						</li>
						<li class="spot-item clearfix">
							<a target="_blank" href="http://www.ctibet.cn/tourism/view/detail?code=VIEW4234458461052147"><img src="portal/assets/images/i2.jpg" width="470" height="264" alt="图片描述"></a>
							<div class="si-text">
								<h3 class="text-heading">南伊沟旅游风景区AAAA<small>（仙境米林——南伊沟）</small></h3>
								<p class="th-text">南伊沟风景区位于米林县南伊乡，距离米林县城7公里，有“中国绿色峰级森林浴场”、“地球上最高的绿色秘境”的美誉，是藏医药发源地，生态保护完好，气候湿润，种子植物720余种，藏药材资源十分丰富。</p>
								<p class="tel">服务咨询电话：15889044555 </p>
								<a target="_blank" href="http://www.ctibet.cn/tourism/view/detail?code=VIEW4234458461052147" class="si-more"><span class="sr-only">查看详情</span></a>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="mw2">
			<div class="layout">
				<div class="layout-inner pt35">
					<h2 class="heading">米林深度游</h2>
					<div class="list-wrap">
						<ul class="list-unstyled clearfix">
							<li class="lu-item">
								<h3 class="lu-heading" title="南迦巴瓦峰大本营及那拉措徒步自然之旅">南迦巴瓦峰大本营及那拉措徒步自然之旅</h3>
								<a href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427787025840.html" class="lu-pic"><img src="portal/assets/images/i3.jpg" width="486" height="134" alt="南迦巴瓦峰大本营及那拉措徒步自然之旅"></a>
								<div class="lu-text">远眺雅鲁藏布江S型弯，面对面仰望南迦巴瓦峰，穿梭杜鹃花林、享受天脉神湖的宁静。若在虫草季节，在神湖旁边的草坝上随处皆有虫草相伴。</div>
								<p class="services">服务电话：18189988858 <a target="_blank" href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427787025840.html" class="s-more"><span class="sr-only">查看更多</span></a></p>
							</li>
							<li class="lu-item">
								<h3 class="lu-heading" title="雅鲁藏布大峡谷徒步墨脱探险体验游">雅鲁藏布大峡谷徒步墨脱探险体验游</h3>
								<a target="_blank" href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427790816272.html" class="lu-pic"><img src="portal/assets/images/i4.jpg" width="486" height="134" alt="雅鲁藏布大峡谷徒步墨脱探险体验游"></a>
								<div class="lu-text">
									<p>1、墨脱是中国最后一个通公路的县城，被誉为“莲花盛开的地方”。徒步墨脱也是徒步探险路线的“王中之王”。</p>
									<p>2、此线路基本涵盖了大峡谷地区的人文及自然风光。</p>
									3、行程当中,在翻越多雄拉山时还可以欣赏到“一山有四季”的不同自然景观。
								</div>
								<p class="services">服务电话：18189988858 <a href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427790816272.html" class="s-more"><span class="sr-only">查看更多</span></a></p>
							</li>
							<li class="lu-item">
								<h3 class="lu-heading" title="藏东环线自驾体验之旅 ">藏东环线自驾体验之旅 </h3>
								<a target="_blank" href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427792223815.html" class="lu-pic"><img src="portal/assets/images/i5.jpg" width="486" height="134" alt="藏东环线自驾体验之旅 "></a>
								<div class="lu-text">
									体验从原始森林到高山牧场、高耸的雪山、清澈的河流、碧蓝的湖泊；体验不同海拔的美景与生长植物的变化，在虫草季节，就在行驶的路边即可亲身体验高山采挖虫草。
								</div>
								<p class="services">服务电话：18989044567 <a href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427792223815.html" class="s-more"><span class="sr-only">查看更多</span></a></p>
							</li>
							<li class="lu-item">
								<h3 class="lu-heading" title="米林才召沟徒步（或自驾）体验游  ">米林才召沟徒步（或自驾）体验游  </h3>
								<a target="_blank" href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427783990849.html" class="lu-pic"><img src="portal/assets/images/i6.jpg" width="486" height="134" alt="米林才召沟徒步（或自驾）体验游  "></a>
								<div class="lu-text">
									走进原始森林，探寻藏医药文化渊源，体验珞巴民俗文化，走访珞巴人家，品尝珞巴餐、珞巴特色装饰拍照留念，体验篝火活动。
								</div>
								<p class="services">服务电话：18989044567 <a target="_blank" href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427783990849.html" class="s-more"><span class="sr-only">查看更多</span></a></p>
							</li>
							<li class="lu-item adjust">
								<h3 class="lu-heading" title="雅鲁藏布大峡谷转加拉朝圣生态体验游  ">雅鲁藏布大峡谷转加拉朝圣生态体验游  </h3>
								<a target="_blank" href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427786637071.html" class="lu-pic"><img src="portal/assets/images/i7.jpg" width="486" height="134" alt="雅鲁藏布大峡谷转加拉朝圣生态体验游  "></a>
								<div class="lu-text">
									<p>1、到世界最大的峡谷—雅鲁藏布大峡谷；观中国最美的雪山—南迦巴瓦峰；领略世界最高江河—雅鲁藏布江两岸极致美景。转加拉徒步朝圣之旅，打开天堂与地狱的秘境之门，体验雪山、森林、峡谷、湍流、寺庙等丰富的人文传说。</p>2、索松村—少为人知的南迦巴瓦最佳观景点；洞不弄—万年人头石。赤白村——个仅有两户人家的村庄；加拉村—雅鲁藏布大峡谷最后一个村庄；加拉白垒雪山—与南伽巴瓦雪山的兄弟传说。
								</div>
								<p class="services">服务电话：18189988858 <a target="_blank" href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427786637071.html" class="s-more"><span class="sr-only">查看更多</span></a></p>
							</li>
							<li class="lu-item adjust">
								<h3 class="lu-heading" title="米林三日游散客线路  ">米林三日游散客线路  </h3>
								<a target="_blank" href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427792983818.html" class="lu-pic"><img src="portal/assets/images/i8.jpg" width="486" height="134" alt="米林三日游散客线路  "></a>
								<div class="lu-text">
									<p>DAY1：拉萨/八一--色苏庄园----米林机场---米林县城-  南伊沟珞巴民俗村--天边牧场(住南伊沟或米林县县城)</p><p>DAY2：米林县县城--江河汇流--羌纳寺—雅鲁藏布大峡谷景区（派转运站）--佛掌沙丘--魔湖--大渡卡遗址--大峡谷第一村--大桑树--情比石坚--南迦巴瓦峰观景台--亲水台--直白村（住派镇景区）</p>DAY3：前往八一/拉萨/成都
								</div>
								<p class="services">服务电话：18189988858/13638904890 <a target="_blank" href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427792983818.html" class="s-more"><span class="sr-only">查看更多</span></a></p>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="mw3">
			<div class="layout">
				<div class="layout-inner pt35">
					<h2 class="heading">实用信息</h2>
					<div class="links">
						<a target="_blank" href="http://www.ctibet.cn/culture/detail/CUL427793548510.html">自驾去米林示意图</a>
						<a target="_blank" href="http://www.ctibet.cn/culture/detail/CUL427793979428.html">走进米林导游图</a>
						<a target="_blank" href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427866229327.html">米林县旅游服务信息表 </a>
						<a target="_blank" href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427794311187.html">米林全年观景指南</a>
						<a target="_blank" href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427866777848.html">自助前往米林</a>
						<a target="_blank" href="http://www.ctibet.cn/tourism/strage/detail/yxzgl427867162974.html">温馨小贴士</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="${''}/portal/headerFooterController/footer"></jsp:include>
	<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
	<script>
		function logout() {
	        var confirm = window.confirm("是否退出");
	        if (confirm) {
	            window.location.href = '<%=basePath %>portal/clientLog/logout';
	        }
	    }
	    window._bd_share_config = {
	        "common": {
	            //"bdText": $("#share").text(), // 分享的内容
	            "bdText" : "自驾米林", // 分享的内容
	            "bdDesc": "", // 分享的摘要
	            'bdComment':'自驾米林',
	            "bdUrl": "", // 分享的Url地址
	            "bdPic": "" // 分享的图片
	        },
	        "share": {
	            "bdSize": 24,
	            "bdCustomStyle": '${ctxPortal}/assets/css/common.css'
	        }
	    };
	    with (document)0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)]; 
	</script>
</body>
</html>