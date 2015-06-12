<%@ page language="java" contentType="text/html;charset=UTF-8" import="com.rimi.ctibet.domain.LogUser" pageEncoding="UTF-8" %>
<%@include file="/common-html/commonTag.jsp" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=0.8,maximum-scale=1.5"/>
    <meta name="author" content="huanl">
    <meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏"/>
    <meta name="description"
          content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！"/>
    <title>天上西藏-感受西藏魅力，体验西藏生活-天上西藏官网</title>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPortal}/assets/css/activity/qx-detail-mobile.css"/>
    <style type="text/css">
    	.hm-t-container{
			display:none;
		}
    </style>
</head>
<body>
    <header class="navbar navbar-dark" id="top" role="banner">
        <div class="container login-and-share">
        <% 
        if(null!=request.getParameter("exit")){
        	session.removeAttribute("logUser");
        }
        LogUser lgUser =(LogUser)session.getAttribute("logUser");
        if(null!=lgUser)
        	request.setAttribute("lgUser", lgUser);
        %>
            <c:choose>
                <c:when test="${empty lgUser}">
                    <a href="<%=basePath %>portal/registerController/register">注册</a>
                    <a href="<%=basePath %>portal/clientLog/loginPage?back_url=<%=basePath %>activity/mobileDetail?code=ACT4222013853974417">登录</a>
                </c:when>
                <c:otherwise>
                    <div class="pull-left">
                        <span class="badge"></span>
                        <img width="40px" height="40px" src="${ctx}${lgUser.pic}" alt="用户头像"/>
                        <i class="umark umark_b"></i>
                    </div>
                    <a class="pull-left">${lgUser.username }</a>
                    <form id="clearLogin" method="post"><input name="exit" value="exit" type="hidden">
                    </form>
                    <a href="javascript:void(0);" onclick="logout();" class="pull-left">退出</a>
                </c:otherwise>
            </c:choose>
            <div class="pull-right bdsharebuttonbox">
                <a href="#" class="share weixin" data-cmd="weixin" title="分享到微信">微信</a>
                <a href="#" class="share weibo" data-cmd="tsina" title="分享到微博">微博</a>
                <a href="#" class="share qzone" data-cmd="qzone" title="分享到QQ空间">QQ空间</a>
            </div>
        </div>
    </header>
    <div class="main">
        <div class="header">
            <h1 class="qx-logo"><img src="${ctxPortal}/static/images/qx-logo.png" alt="骑行吧，去西藏"/></h1>
            <h2>在路上与你同行，就有不一样的骑行体验！</h2>
            <div class="act-time">
                <p><strong>活动时间：2015年1月1日——2015年10月1日</strong></p>
            </div>
            <div class="qx-intro">
                <p>每一年，每一天均有无数骑行爱好者在去西藏的路上……<br/><br/>
                    年轻人将它视为自己的成年礼，中年人将它视为生命的沉淀，而老年人则视其为自己的人生挑战。每一个理由都支撑着你出发，每一次进藏都值得喝彩！<br/><br/>
                    然而，无秩序无保障的骑行导致每年川藏线均有上百人伤亡和失踪。<br/><br/>
                    为了进一步服务进藏人群，中国移动西藏公司、中国移动云南公司联合公益组织成都梦想骑行俱乐部，为骑   行、自驾人群提供更加完善的服务，通过线上统一报   名、组织骑行、线路服务导航、路书精细设计，线下救援、物资配送、食宿优惠等,全面服务进藏人群，安全更有保障！<br/><br/>
                    走，让我们一起骑着单车去西藏！</p>
            </div>
            <div class="sponsor">
                <img src="${ctxPortal}/assets/icon/ctibet-icon.png" alt="天上西藏"/><img
                    src="${ctxPortal}/assets/icon/mobile-icon.png" alt="中国移动"/><img
                    src="${ctxPortal}/assets/icon/qx-icon.png" alt="梦想骑行"/>
            </div>
        </div>
        <div class="content">
            <div class="organizers">
                <h3>主办方：中国移动西藏公司\中国移动云南公司</h3>
                <h3>承办方：成都梦想骑行俱乐部</h3>
            </div>
            <div class="qx-info">
                <div class="item-1">
                    <h3 class="info-title act-req"><img src="${ctxPortal}/assets/icon/qx-icon-1.png" alt="活动要求"/><span>活动要求</span></h3>
                    <section class="fc_fff">
                    	<h4>一、线上报名</h4>
                    	<p>天上西藏官方网站本报名网页为指定官方报名点。（官方合作报名点详见下文）</p>
                    	<h4>二、线下确认</h4>
                    	<p>骑行俱乐部将电话联系报名人，进行报名确认，并集中培训相关知识及体能测试。通过者领取捐助物资，从川藏或滇藏两条线路开始旅程前往拉萨。</p>
                    </section>
                </div>
                <div class="item-2">
                    <h3 class="info-title act-req mt-40"><img src="${ctxPortal}/assets/icon/qx-icon-2.png" alt="公益活动"/><span>公益活动(自愿参与)</span></h3>
                    <section class="fc_fff">
                    	<p class="mt-30">每个报名的骑行者个人身负1KG的衣物或文具，并准备一堂自己擅长的课程等，从川藏、滇藏线两条线路以骑行旅行方式前往拉萨，沿途捐助西藏的贫困群体，主办方将根据报名者自身条件、课程类型等综合因素，择优让其给西藏地区贫困学生上一堂自己准备的课程和进行环保活  动，通过面对面交流传递正能量；最后完成骑行任务。同时，将自己骑行途中产生的垃圾妥善打理，带往下一个骑行服务站，把洁净和绿色还给西藏。</p>
                    	<mark>*备注：捐助物资均由主办方及合作赞助单位提供</mark>
                    </section>
                </div>
                <div class="item-3">
                    <h3 class="info-title act-req mt-40"><img src="${ctxPortal}/assets/icon/qx-icon-3.png" alt="享受服务"/><span>享受服务</span></h3>
                    <section class="fc_fff">
                    	<table border="1" cellpadding="5" cellspacing="0" class="col3 mt-30">
                    		<tr>
                    			<th>项目</th>
                    			<th>价值<br><span class="smart">（元）</span></th>
                    			<th>内容</th>
                    		</tr>
                    		<tr>
                    			<td>骑行咨询</td>
                    			<td>50</td>
                    			<td>讲解/观看川藏线上路况全程视频,进行紧急情况自我户外救援措施等。</td>
                    		</tr>
                    		<tr>
                    			<td>体检</td>
                    			<td>20</td>
                    			<td>免费体能测试，检测心率、血压、血脂这三个高原反应指标。</td>
                    		</tr>
                    		<tr>
                    			<td>保险</td>
                    			<td>100</td>
                    			<td>1W医疗、10W伤故、免费救援。</td>
                    		</tr>
                    		<tr>
                    			<td>饮料</td>
                    			<td>80</td>
                    			<td>13瓶乐虎。</td>
                    		</tr>
                    		<tr>
                    			<td>服务站点</td>
                    			<td>230</td>
                    			<td>318沿线23个骑行服务站提供补给。服务站还作为紧急协助救援联络点对突发事件进行紧急支援，同时保证提供参与人员住宿。</td>
                    		</tr>
                    		<tr>
                    			<td>住宿安排</td>
                    			<td>100</td>
                    			<td>安排沿途住宿九折优惠讲解/观看川藏线上路况全程视频,进行紧急情况自我户外救援措施等。</td>
                    		</tr>
                    		<tr>
                    			<td>完成奖牌</td>
                    			<td>50</td>
                    			<td>进藏之路必将充满坎坷荆棘，一路格桑花奖章给您最美的记忆。</td>
                    		</tr>
                    		<tr>
                    			<td>完成勋章</td>
                    			<td>50</td>
                    			<td>为了奖励那些追逐梦想和自由的勇士，成功完成（推行也将视为完成）川藏骑行、滇藏骑行将分别获得川藏勇士勋章、滇藏勇士勋章。</td>
                    		</tr>
                    		<tr>
                    			<td>完成证书</td>
                    			<td>5</td>
                    			<td>成功到达拉萨骑将行人员获得纪念证书，设计费、材料费、打印费。</td>
                    		</tr>
                    		<tr>
                    			<td>终点拍照</td>
                    			<td>5</td>
                    			<td>合影留念。</td>
                    		</tr>
                    		<tr>
                    			<td>合计</td>
                    			<td><span style="text-decoration:line-through;">690元</span></td>
                    			<td>实收报名费<em>198元！</em></td>
                    		</tr>
                    	</table>
                    </section>
                </div>
                <div class="item-4">
                    <h3 class="info-title act-req mt-60"><img src="${ctxPortal}/assets/icon/qx-icon-4.png" alt="官方合作渠道报名"/><span>官方合作渠道报名</span></h3>
                    <section class="fc_fff">
                    	<table border="1" cellpadding="5" cellspacing="0" class="col3 mt-30">
                    		<tr>
								<th colspan="3">在官方合作渠道报名，立省8元!<br><span class="smart">(官方合作渠道：全国各高校户外运动联络点，同步开展校园宣传！线下报名请咨询各高校学生会或骑行社。)</span></th>
                    		</tr>
                    		<tr>
                    			<th>省份</th>
                    			<th>数量<br><span class="smart">（所）</span></th>
                    			<th>高校</th>
                    		</tr>
                    		<tr>
                    			<td>广西</td>
                    			<td>5</td>
                    			<td>广西师范大学、广西河池学院、 广西大学、桂林理工大学、广西工学院</td>
                    		</tr>
                    		<tr>
                    			<td>广东</td>
                    			<td>4</td>
                    			<td>华南师范大学、广东工业大学 、中山大学（珠海校区）、香港浸会大学（珠海）</td>
                    		</tr>
                    		<tr>
                    			<td>福建</td>
                    			<td>3</td>
                    			<td>厦门大学、集美大学、福州师范大学</td>
                    		</tr>
                    		<tr>
                    			<td>浙江</td>
                    			<td>5</td>
                    			<td>浙江师范大学、中国美术学院 、浙江大学（紫金港校区）、台州学院、宁波大学</td>
                    		</tr>
                    		<tr>
                    			<td>上海</td>
                    			<td>2</td>
                    			<td>上海同济大学、上海复旦大学</td>
                    		</tr>
                    		<tr>
                    			<td>山东</td>
                    			<td>10</td>
                    			<td>齐鲁工业大学、青岛海洋大学 、鲁东大学、中国石油大学（青岛）、山东建筑工程大学、山东师范学院、山东交通学院、山东旅游职业技术学院、山东技师学院、山东电子职业技术学院</td>
                    		</tr>
                    		<tr>
                    			<td>江苏</td>
                    			<td>5</td>
                    			<td>南京理工大学、 苏州大学 、扬州大学 、东南大学、南京农业大学</td>
                    		</tr>
                    		<tr>
                    			<td>江西</td>
                    			<td>4</td>
                    			<td>南昌航空航天大学 、南昌大学 、东华理工学院 、华东交通大学</td>
                    		</tr>
                    		<tr>
                    			<td>湖南</td>
                    			<td>3</td>
                    			<td>长沙电力职业技术学院、湖南司法警官职业学院 、长沙理工大学</td>
                    		</tr>
                    		<tr>
                    			<td>四川</td>
                    			<td>13</td>
                    			<td>四川大学、四川大学锦城学院、四川大学旅游学院、西南交通大学、西华大学、四川林业大学、四川农业大学、四川工商职业技术学院、电子科技大学、西南民族大学、四川民族学院、乐山师范学院、宜宾师范学院</td>
                    		</tr>
                    		<tr>
                    			<td>重庆</td>
                    			<td>8</td>
                    			<td>重庆交通大学、长江师范学院、重庆第一师范大学、重庆第二师范大学、四川美术学院、重庆师范大学、重庆警官职业学院、西南大学</td>
                    		</tr>
                    		<tr>
                    			<td>河南</td>
                    			<td>4</td>
                    			<td>铁道警官高等专科学校、郑州大学、郑州大学体育学院 、河南司法警官职业学院</td>
                    		</tr>
                    		<tr>
                    			<td>北京</td>
                    			<td>3</td>
                    			<td>中央美术学院、北京体育大学、北京航空航天大学</td>
                    		</tr>
                    		<tr>
                    			<td>陕西</td>
                    			<td>2</td>
                    			<td>西安外国语大学、中国人民解放军航空工程学院</td>
                    		</tr>
                    		<tr>
                    			<td>河北</td>
                    			<td>2</td>
                    			<td>河北大学、华北电力大学</td>
                    		</tr>
                    		<tr>
                    			<td>天津</td>
                    			<td>3</td>
                    			<td>天津传媒学院、天津师范大学、天津体育学院</td>
                    		</tr>
                    		<tr>
                    			<td>东北</td>
                    			<td>4</td>
                    			<td>东北大学、沈阳工业大学、辽宁大学、海军大连舰艇学院</td>
                    		</tr>
                    	</table>
                    </section>
                    <section class="fc_fff">
                    	<table border="1" cellpadding="5" cellspacing="0" class="mt-30">
                    		<tr>
                    			<th>合作骑行俱乐部</th>
                    			<th>地址</th>
                    		</tr>
                    		<tr>
                    			<td>成都乐哈氏骑行俱乐部</td>
                    			<td>成都市太平园横二街76号</td>
                    		</tr>
                    		<tr>
                    			<td>成都骑迹单车俱乐部</td>
                    			<td>成都市同心路8号</td>
                    		</tr>
                    		<tr>
                    			<td>拉萨骓驰单车骑行俱乐部</td>
                    			<td>拉萨江苏路南侧4-1号</td>
                    		</tr>
                    		<tr>
                    			<td>杭州捷成单车俱乐部</td>
                    			<td>杭州市下城区白石路100号</td>
                    		</tr>
							<tr>
								<td>云南昆明培力国际青年旅舍</td>
								<td>云南盘龙区双龙街道办事处麦冲村152号</td>
							</tr>
							<tr>
								<td>天津自行车骑行联盟</td>
								<td></td>
							</tr>
							<tr>
								<td>大连爱卡科技</td>
								<td>沙河口区迎春街45号</td>
							</tr>
							<tr>
								<td>上海闸北区鸿兴路雅驰车店</td>
								<td>上海闸北鸿兴路33号</td>
							</tr>
							<tr>
								<td>三亚自行车运动协会</td>
								<td>解放路898号</td>
							</tr>
						</table>
                    </section>
                </div>
                <div class="item-5">
                    <h3 class="info-title act-req mt-60"><img src="${ctxPortal}/assets/icon/qx-icon-5.png" alt="免责声明"/><span>免责声明</span></h3>
                    <section class="fc_fff">
                    	<p>1.“骑行吧，去西藏！”活动免责声明“骑行吧，去西藏！”活动是广大旅游爱好者自愿参加的非营利性自助活动。由于活动为自由组合性质，一旦出现事故，活动中任何非事故当事人将不承担事故任何责任，但有互相尽力援助的义务。所以您必须慎重考虑，自愿报名。</p>
                    	<p>2. 活动组织方免责声明凡报名参加“骑行吧，去西藏！”活动者均应当是具有完全民事行为能力的人，在行程中应当对自己的人身及财产负有保护义务，如在活动中因不可抗力及其他意外事故给其人身及财产造成损害，组织者不承担任何赔偿责任，由受损害人依据法律规定和本声明依法解决。但考虑到旅途中可能发生的危险情况，活动组织方为每位参加者购买一份意外保险。</p>
                   		<p>3. 参加活动者免责声明“骑行吧，去西藏！”活动属于自助的出行活动，当由于意外事故和急性疾病等不可预测因素造成身体损伤时，团队的其他成员有义务尽力救助，但如果产生了实质性损害，团队的其他成员不负担任何赔偿责任。团队中的任何一个队员应该本着“尽力救助，风险自担”的原则参加活动。</p>
                    	<mark class="txt-a_l">* 一旦您参加“骑行吧，去西藏！”活动，本声明自动生效并表明你接受本声明。</mark>
                    </section>
                </div>
                <div class="item-6">
                    <h3 class="info-title act-req mt-60"><img src="${ctxPortal}/assets/icon/qx-icon-1.png" alt="活动关注"/><span>活动关注</span></h3>
                    <section class="ac-att">
                        <div>
                            <a target="_blank" href="http://weibo.com/u/2854434354">
                                <img src="${ctxPortal}/assets/icon/ctibet-icon.png" alt="天上西藏官方微博"/>
                                <p>天上西藏<br>官方微博</p>
                            </a>
                        </div>
                        <div>
                            <a target="_blank" href="http://www.10086.cn/xz/">
                                <img src="${ctxPortal}/assets/icon/mobile-icon.png" alt="中国移动西藏公司官方网站"/>
                                <p>中国移动西藏<br>公司官方网站</p>
                            </a>
                        </div>
                        <div>
                            <a target="_blank" href="http://10086.cn/yn/">
                                <img src="${ctxPortal}/assets/icon/mobile-icon.png" alt="中国移动云南公司官方网站"/>
                                <p>中国移动云南<br>公司官方网站</p>
                            </a>
                        </div>
                        <div>
                            <a target="_blank" href="http://weibo.com/u/5223358186">
                                <img src="${ctxPortal}/assets/icon/qx-icon.png" alt="成都梦想骑行俱乐部官方微博"/>
                                <p>成都梦想骑行<br>俱乐部官方微博</p>
                            </a>
                        </div>
                    </section>
					<section class="ac-qq">
						<div>
							<p>欢迎加入QQ群：</p>
							<p>骑行中国——川藏线群 345385468</p>
							<p>骑行中国——滇藏线群 346980776</p>
						</div>
					</section>
                </div>
                <div class="item-7">
                    <h3 class="info-title act-req mt-60"><img src="${ctxPortal}/assets/icon/qx-icon-3.png" alt="官方合作伙伴"/><span>官方合作伙伴</span></h3>
                    <section class="partner">
                        <img src="${ctxPortal}/assets/icon/qx-icon_add_1.jpg" alt="梦想骑行"/>
                        <img src="${ctxPortal}/assets/icon/qx-icon_add_2.jpg" alt="热带雨林"/>
                        <img src="${ctxPortal}/assets/icon/qx-icon_add_3.jpg" alt="乐虎"/>
                    </section>
                </div>
                <div class="item-8">
                    <h3 class="info-title act-req mt-60"><img src="${ctxPortal}/assets/icon/qx-icon-6.png" alt="骑行必备用品提示"/><span style="margin-left:46px;">骑行必备用品提示</span></h3>
                    <section class="fc_fff">
                    	<p class="mt-30">必备用品：背包、背包罩、睡袋（依个人习惯，非必需品）、防潮垫、地席、高帮登山鞋、登山杖、头灯、雨具（非雨伞）、水壶或水袋、纸巾或湿纸巾、个人洗漱用具、身份证。</p>
                   		<p>服装：冲锋衣裤、抓绒衣裤、排汗内衣、一次性内裤若干、棉运动袜（多带几双）、雪镜或太阳镜、遮阳帽、保暖手套（最好防水）、雪套。</p>
                   		<p>其他：相机、防蛇药、消炎药（百服宁）、止泻药（泻立停，黄连素，佛派酸）、云南白药、好的快（喷雾剂）、扶他林、晕车药、抗高原反应的药物（肌肝、红景天、红参）、创可贴、腰包、电池、指北针、口哨。</p>
                   		<p>通讯工具：手机、电池、充电宝。</p>
                    </section>
                </div>
            </div>
        </div>
        <div class="footer">
        	<h2>报名须知<span class="enTitle">NOTICE</span></h2>
        	<h3>参与条件CONDITION</h3>
        	<ul>
        		<li>性格开朗，积极向上，思想进步</li>
        		<li>具备较好的语言表达能力和团队协作精神</li>
        		<li>喜爱户外运动，有长途骑行的经验和强健的体魄</li>
        		<li>有新闻从业经验和较高摄影技巧者优先</li>
        		<li>以团队及个人方式报名皆可</li>
        	</ul>
        	<h3>特别提醒REMIND</h3>
        	<ul>
        		<li>一旦您参加“骑行吧，去西藏！”活动，本声明自动生效并表明你接受本声明。</li>
        		<li class="red">本次报名需要支付费用198元。</li>
        	</ul>
        	<h3>活动时间TIME</h3>
        	<ul>
        		<li>2015年1月1日-2015年10月1日</li>
        	</ul>
        </div>
        <a href="javascript:void(0);" onclick="pay();" class="repllyBtn"><!--我要报名--></a>
        <a id="J_newwindow" target="_mobile_qx" href="" style="display: none;"></a>
    </div>
    <script src="${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js"></script>
    <script>
	  	//支付
		function pay() {
	        if ('${isEnd}' == 'yes') {
	            alert("活动已结束");
	        } else {
	            var url = "${ctx}portal/order/checkPay";
	            $.post(url, {activityCode: '${activity.code}', OCS: '${OCS }'}, function (response) {
	                if (response == 'need_login') {
	                    //$("#J_newwindow").attr("href","${ctx}portal/clientLog/loginPage");
	                    //document.getElementById("J_newwindow").click();
	                	window.location.href="${ctx}portal/clientLog/loginPage?back_url=" + window.location.href;
	                } else if (response == 'need_enroll') {
	                    //alert("请先报名");
	                	window.location.href="${ctx}activity/mobileEnroll?code=${activity.code }&OCS=${OCS}"
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
	  	// 退出
	  	function  logout(){
	  		$("#clearLogin").submit();
	  	}
        window._bd_share_config = {
            "common": {
                //"bdText": $("#share").text(), // 分享的内容
                "bdText" : "${activity.name}", // 分享的内容
                "bdDesc": "", // 分享的摘要
                'bdComment':'骑行吧，去西藏！史上最有保障的川滇骑行，即可报名啦！',
                "bdUrl": "", // 分享的Url地址
                "bdPic": "${ctx}${activity.img}" // 分享的图片
            },
            "share": {
                "bdSize": 24,
                "bdCustomStyle": "${ctxPortal}/assets/css/activity/qx-detail-mobile.css"
            }
        };
        with (document)0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];
        
       //百度流量页面统计
        var _hmt = _hmt || [];
        (function() {
          var hm = document.createElement("script");
          hm.src = "//hm.baidu.com/hm.js?577bcb4d93163c5343aba29db1bc982c";
          var s = document.getElementsByTagName("script")[0]; 
          s.parentNode.insertBefore(hm, s);
        })();
        //国双科技流量统计
        window.onload=function(){
        	window.GridsumSnapshotID=0
        }
        
    </script>
    <!-- Gridsum tracking code begin. -->
<script type='text/javascript'>
       (function(){
              var s = document.createElement('script');
              s.type = 'text/javascript';
              s.async = true;
              s.src = (location.protocol == 'https:' ? 'https://ssl.' : 'http://static.') + 'gridsumdissector.com/js/Clients/GWD-000781-E149DE/gs.js';
              var firstScript = document.getElementsByTagName('script')[0];
              firstScript.parentNode.insertBefore(s, firstScript);
       })();
</script>
<!--Gridsum tracking code end. -->
</body>
</html>