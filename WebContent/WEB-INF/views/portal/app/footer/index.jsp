<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
	String port = ":" + request.getServerPort();
	if ("80".equals(""+request.getServerPort())) {
		port = "";
	}
	String basePath = request.getScheme() + "://" + request.getServerName() + port + path + "/";
%>
<div class="footer">
    <div class="footer-head">
        <div class="container">
            <div class="footer-caitiao"></div>
        </div>
    </div>
    <div class="container">
        <div class="web-link">
            <ul>
                <li style="margin-top: 10px;"><img src="<%=basePath %>portal/assets/icon/foo-logo.png" alt="天上西藏"/></li>
                <%--<li ><a href="http://old.ctibet.cn" target="_blank">天上西藏旧站</a></li>--%>
            </ul>
            <ul>
                <li class="footer-title">关于我们</li>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH419762354254" target="_blank">天上西藏介绍</a></li>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH419760232787" target="_blank">联系我们</a></li>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH419759737798" target="_blank">合作伙伴</a></li>
            </ul>
            <ul>
                <li class="footer-title">网站条款</li>
                <%-- <li><a href="<%=basePath %>portal/app/setting/other.html?code=OTH419757907940">会员条款</a></li> --%>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH419818320092" target="_blank">社区指南</a></li>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH419818345596" target="_blank">免责声明</a></li>
            </ul>
            <ul>
                <li class="footer-title">帮助中心</li>
                <%-- <li><a href="<%=basePath %>portal/app/setting/other.html?code=OTH419818368129">新手上路</a></li>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH420661564474" >使用帮助</a></li> --%>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH420661584018" target="_blank">网站地图</a></li>
                <li><a id="sug" style="cursor: pointer">意见与建议</a></li>
            </ul>
            <ul>
                <li class="footer-title">进藏须知</li>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH420661609896" target="_blank">身体健康准备</a></li>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH420661626299" target="_blank">如何缓解高原反应</a></li>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH420661647570" target="_blank">证件准备</a></li>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH420661664453" target="_blank">出行的防护与安全</a></li>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH420661692583" target="_blank">风俗与禁忌</a></li>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH420661716704" target="_blank">骑行注意事项</a></li>
                <li><a href="<%=basePath %>portal/app/setting/other.html/OTH420661737045" target="_blank">自驾准备事项</a></li>
            </ul>
            <ul>
                <li class="footer-title">关注我们</li>
                <li class="footer-gz"><a href="javascript: wx('<%=basePath %>portal/assets/icon/wx-code.jpg');"><img src="<%=basePath %>portal/assets/icon/weixin.png" alt="weixin" /></a></li>
                <li class="footer-gz"><a href="http://www.weibo.com/u/2854434354#_loginLayer_1421980591418" target="_blank">
                    <img src="<%=basePath %>portal/assets/icon/xinlang.png" alt="weibo" />
                </a></li>
            </ul>
            <ul class="web-link-ul-last">
                <li class="footer-title">移动应用</li>
                <li class="footer-gz">
                    <a href="javascript:void(0);">
                        <img src="<%=basePath %>portal/assets/icon/apple.png" alt="iphone"/>
                    </a>
                </li>
                <li class="footer-gz">
                    <a href="<%=basePath %>portal/app/setting/other.html/OTH420661788294" target="_blank">
                        <img src="<%=basePath %>portal/assets/icon/android.png" alt="android"/>
                    </a>
                </li>
            </ul>
        </div>
        <p class="state">
            &copy; Company 2014 – Copyright by
            <a href="http://10086.cn/xz/" target="_blank">中国移动通信集团西藏有限公司</a>&nbsp;藏ICP备09000139号
        </p>
    </div>
    <div id="ground-floor" class="ground-floor"></div>
    <div id="suggestions" class="suggestions">
        <p class="yj">意见反馈</p>
        <p>我们倾听你的声音</p>
        <textarea name="content" id="content" onkeyup="chklen(this,500);" cols="62" rows="7" placeholder="欢迎提出宝贵的意见和建议。抱歉我们无法逐一回复，但我们会认真阅读，你的支持是对我们最大的鼓励和帮助"></textarea>
        <button class="sug-sub" id="subtn">提交</button>
        <button id="colse-sug" class="sug-close"></button>
    </div>
</div>
<script id="seajsnode" src="<%=basePath %>portal/modules/seajs/seajs/2.2.1/sea.js"></script>

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
<script src="${ctx }common-js/footer.js" type="text/javascript"></script>