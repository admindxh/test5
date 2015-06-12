<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib   prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container taste">
    <div class="row">
        <!-- 精读西藏 -->
        <div class="col-xs-9 taste-info">
            <h2 class="taste-title"><span class="sr-only">精读西藏</span></h2>
            <div class="taste-info-header">
                <c:forEach var="pos" items="${posid}">
                    <div class="info-img clearfix">
                        <div class="img-item"><a target="_blank" href="${ctx}${pos.url}"><img src="${ctx}${pos.title}" alt="${pos.contentTitle}"/></a></div>
                        <div class="written-message ">
                            <h5><a target="_blank" href="${ctx}${pos.url}">${pos.contentTitle}</a></h5>
                            <p class="js_sub_posid">${pos.contetNotHtml}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="clearfix taste-info-footer">
                <div class="taste-footer-left">
                    <h3 class="tt">节日 FESTIVA</h3>
                    <div class="taste-festiva">
                        <div class="w283">
                            <a target="_blank" href="${ctx}${infoDisplay[0].url}">  <img src="${ctx}${infoDisplay[0].title}" alt="${infoDisplay[0].contentTitle}"/></a>
                        </div>
                        <div class="clearfix festiva-info">
                            <a target="_blank" href="${ctx}${infoDisplay[0].url}">
                                <h5 class="taste-footer-h5">${infoDisplay[0].contentTitle}</h5>
                            </a>
                            <p class="js_sub_news">${infoDisplay[0].contetNotHtml}</p>
                            <a target="_blank" class="info-more" href="${ctx}culture/list/1010.html"></a>
                        </div>
                    </div>
                </div>
                <div class="taste-footer-center">
                    <h3 class="tt">历史 HISTORY</h3>
                    <div class="taste-festiva">
                        <div class="w283">
                            <a target="_blank" href="${ctx}${infoDisplay[1].url}"> <img src="${ctx}${infoDisplay[1].title}" alt="${infoDisplay[1].contentTitle}"/></a>
                        </div>
                        <div class="clearfix festiva-info">
                            <a target="_blank" href="${ctx}${infoDisplay[1].url}">
                                <h5 class="taste-footer-h5">${infoDisplay[1].contentTitle}</h5>
                            </a>
                            <p class="js_sub_news">${infoDisplay[1].contetNotHtml}</p>
                            <a target="_blank" class="info-more" href="${ctx}culture/list/1020.html"></a>
                        </div>
                    </div>
                </div>
                <div class="taste-footer-right">
                    <h3 class="tt">风俗 CUSTOMS</h3>
                    <div class="taste-festiva">
                        <div class="w283">
                            <a target="_blank" href="${ctx}${infoDisplay[2].url}">
                                <img src="${ctx}${infoDisplay[2].title}" alt="${infoDisplay[2].contentTitle}"/></a>
                        </div>
                        <div class="clearfix festiva-info">
                            <a target="_blank" href="${ctx}${infoDisplay[2].url}">
                                <h5 class="taste-footer-h5">${infoDisplay[2].contentTitle}</h5>
                            </a>
                            <p class="js_sub_news">${infoDisplay[2].contetNotHtml}</p>
                            <a target="_blank" class="info-more" href="${ctx}culture/list/1030.html"></a>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- 精读西藏 End -->
        <!-- 西藏动态 -->
        <div class="col-xs-3 news">
            <div class="clearfix news-title">
                <h2><span class="sr-only">西藏动态</span><a target="_blank" href="${ctx}culture/list/1120.html"></a></h2>
                <%--<a target="_blank" href="${ctx}culture/list/1120.html">更多MORE</a>
            --%></div>
            <div class="news-info">
                <c:forEach var="news" items="${dynamicList}">
                    <dl>
                        <dt>
                            <c:if test="${news.isOfficial==0}">
                                <img src="${ctxPortal }/assets/icon/recommend.png"/>
                            </c:if>
                            <span style="vertical-align: middle;"><a target="_blank" href="${ctx}${news.url}"> ${news.contentTitle }</a></span></dt>
                        <dd class="js_sub_news">${news.contetNotHtml }</dd>
                        <dd class="news-time">${news.dateFormat}</dd>
                    </dl>
                </c:forEach>
            </div>
        </div><!-- 西藏动态 End -->
    </div>
    <!-- 名人、艺术 -->
    <div class="clearfix feature-mode">
        <div class="celebrity">
            <h3 class="tt">名人 CELEBRITY</h3>
            <div class="clearfix">
                <div class="fl-w309">
                    <a target="_blank" href="${ctx}${infoDisplay[3].url}">
                        <img src="${ctx}${infoDisplay[3].title}" alt="${infoDisplay[3].contentTitle}"/>
                    </a>
                </div>
                <div class="celebrity-info">
                    <dl>
                        <dt><a target="_blank" href="${ctx}${infoDisplay[3].url}"> ${infoDisplay[3].contentTitle}</a></dt>
                        <dd class="js_sub_info">${infoDisplay[3].contetNotHtml}</dd>
                    </dl>
                    <ul>
                        <li><a target="_blank" href="${ctx}${infoDisplay[4].url}">${infoDisplay[4].contentTitle}</a></li>
                        <li><a target="_blank" href="${ctx}${infoDisplay[5].url}">${infoDisplay[5].contentTitle}</a></li>
                        <li><a target="_blank" href="${ctx}${infoDisplay[6].url}">${infoDisplay[6].contentTitle}</a></li>
                    </ul>
                    <a target="_blank" href="${ctx}culture/list/1070.html"></a>
                </div>
            </div>
        </div>
        <div class="celebrity">
            <h3 class="tt">宗教 RELIGION</h3>
            <div class="clearfix">
                <div class="fl-w309">
                    <a target="_blank" href="${ctx}${infoDisplay[7].url}">
                        <img src="${ctx}${infoDisplay[7].title}" alt="${infoDisplay[7].contentTitle}"/>
                    </a>
                </div>
                <div class="celebrity-info">
                    <dl>
                        <dt><a target="_blank" href="${ctx}${infoDisplay[7].url}"> ${infoDisplay[7].contentTitle}</a></dt>
                        <dd class="js_sub_info">${infoDisplay[7].contetNotHtml}</dd>
                    </dl>
                    <ul>
                        <li><a target="_blank" href="${ctx}${infoDisplay[8].url}">${infoDisplay[8].contentTitle}</a></li>
                        <li><a target="_blank" href="${ctx}${infoDisplay[9].url}">${infoDisplay[9].contentTitle}</a></li>
                        <li><a target="_blank" href="${ctx}${infoDisplay[10].url}">${infoDisplay[10].contentTitle}</a></li>
                    </ul>
                    <a target="_blank" href="${ctx}culture/list/1040.html"></a>
                </div>
            </div>
        </div>
    </div>
    <div class="clearfix feature-mode">
        <div class="celebrity food-art">
            <h3 class="tt">美食 FOOD</h3>
            <div class="clearfix">
                <div class="fl-w309">
                    <a target="_blank" href="${ctx}${infoDisplay[11].url}">   <img src="${ctx}${infoDisplay[11].title}" alt="${infoDisplay[11].contentTitle}"/></a>
                </div>
                <div class="celebrity-info">
                    <dl>
                        <dt><a target="_blank" href="${ctx}${infoDisplay[11].url}">${infoDisplay[11].contentTitle}</a></dt>
                        <dd class="js_sub_info">${infoDisplay[11].contetNotHtml}</dd>
                    </dl>
                    <ul>
                        <li><a target="_blank" href="${ctx}${infoDisplay[12].url}">${infoDisplay[12].contentTitle}</a></li>
                        <li><a target="_blank" href="${ctx}${infoDisplay[13].url}">${infoDisplay[13].contentTitle}</a></li>
                        <li><a target="_blank" href="${ctx}${infoDisplay[14].url}">${infoDisplay[14].contentTitle}</a></li>
                    </ul>
                    <a target="_blank" href="${ctx}culture/list/1050.html"></a>
                </div>
            </div>
        </div>
        <div class="celebrity food-art">
            <h3 class="tt">艺术 ART</h3>
            <div class="clearfix">
                <div class="fl-w309">
                    <a target="_blank" href="${ctx}${infoDisplay[15].url}"> <img src="${ctx}${infoDisplay[15].title}" alt="${infoDisplay[15].contentTitle}"/></a>
                </div>
                <div class="celebrity-info">
                    <dl>
                        <dt><a target="_blank" href="${ctx}${infoDisplay[15].url}">${infoDisplay[15].contentTitle}</a></dt>
                        <dd class="js_sub_info">${infoDisplay[15].contetNotHtml}</dd>
                    </dl>
                    <ul>
                        <li><a target="_blank" href="${ctx}${infoDisplay[16].url}">${infoDisplay[16].contentTitle}</a></li>
                        <li><a target="_blank" href="${ctx}${infoDisplay[17].url}">${infoDisplay[17].contentTitle}</a></li>
                        <li><a target="_blank" href="${ctx}${infoDisplay[18].url}">${infoDisplay[18].contentTitle}</a></li>
                    </ul>
                    <a target="_blank" href="${ctx}culture/list/1090.html"></a>
                </div>
            </div>
        </div>
    </div><!-- 名人、艺术 End -->
    <div class="row">
        <div class="col-xs-9 spec-anipla">
            <div class="spec">
                <h3 class="tt">特产 SPECIALTIES</h3>
                <div class="spec-info">
                    <div class="clearfix spec-top">
                        <div class="fl-w267">
                            <a target="_blank" href="${ctx}culture/${infoDisplay[19].summary}/${infoDisplay[19].code}.html">
                                <img src="${ctx}${infoDisplay[19].title}" alt="${infoDisplay[19].contentTitle}"/>
                            </a>
                        </div>
                        <dl>
                            <dt><a target="_blank" href="${ctx}${infoDisplay[19].url}">${infoDisplay[19].contentTitle}</a></dt>
                            <dd class="js_sub_info">${infoDisplay[19].contetNotHtml}</dd>
                        </dl>
                    </div>
                    <div class="clearfix spec-bottom">
                        <ul class="spec-left">
                            <li><a target="_blank" href="${ctx}${infoDisplay[20].url}">${infoDisplay[20].contentTitle}</a></li>
                            <li><a target="_blank" href="${ctx}${infoDisplay[21].url}">${infoDisplay[21].contentTitle}</a></li>
                            <li><a target="_blank" href="${ctx}${infoDisplay[22].url}">${infoDisplay[22].contentTitle}</a></li>
                        </ul>
                        <ul class="spec-right">
                            <li><a target="_blank" href="${ctx}${infoDisplay[23].url}">${infoDisplay[23].contentTitle}</a></li>
                            <li><a target="_blank" href="${ctx}${infoDisplay[24].url}">${infoDisplay[24].contentTitle}</a></li>
                            <li><a target="_blank" href="${ctx}${infoDisplay[25].url}">${infoDisplay[25].contentTitle}</a></li>
                        </ul>
                        <a target="_blank" href="${ctx}culture/list/1100.html"></a>
                    </div>
                </div>
            </div>
            <div class="spec anipla">
                <h3 class="tt">动植物 ANIMALS AND PLANTS</h3>
                <div class="spec-info">
                    <div class="clearfix spec-top">
                        <div class="fl-w267">
                            <a target="_blank" href="${ctx}${infoDisplay[26].url}"> <img src="${ctx}${infoDisplay[26].title}" alt="${infoDisplay[26].contentTitle}"/></a>
                        </div>
                        <dl>
                            <dt><a target="_blank" href="${ctx}${infoDisplay[26].url}">${infoDisplay[26].contentTitle}</a></dt>
                            <dd class="js_sub_info">${infoDisplay[26].contetNotHtml}</dd>
                        </dl>
                    </div>
                    <div class="clearfix spec-bottom">
                        <ul class="spec-left">
                            <li><a target="_blank" href="${ctx}${infoDisplay[27].url}">${infoDisplay[27].contentTitle}</a></li>
                            <li><a target="_blank" href="${ctx}${infoDisplay[28].url}">${infoDisplay[28].contentTitle}</a></li>
                            <li><a target="_blank" href="${ctx}${infoDisplay[29].url}">${infoDisplay[29].contentTitle}</a></li>
                        </ul>
                        <ul>
                            <li><a target="_blank" href="${ctx}${infoDisplay[30].url}">${infoDisplay[30].contentTitle}</a></li>
                            <li><a target="_blank" href="${ctx}${infoDisplay[31].url}">${infoDisplay[31].contentTitle}</a></li>
                            <li><a target="_blank" href="${ctx}${infoDisplay[32].url}">${infoDisplay[32].contentTitle}</a></li>
                        </ul>
                        <a target="_blank" href="${ctx}culture/list/1060.html"></a>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-3 dress-geo" style="margin-top: 10px;">
            <div class="dress">
                <h3 class="tt">服饰 DRESS</h3>
                <div class="dress-info" >
                    <div class="clearfix dress-top"  style="margin-top: 19px;">
                        <div class="fl-w154">
                            <a target="_blank" href="${ctx}${infoDisplay[33].url}"> <img src="${ctx}${infoDisplay[33].title}" alt="${infoDisplay[33].contentTitle}"/></a>
                        </div>
                        <dl>
                            <dt><a target="_blank" href="${ctx}${infoDisplay[33].url}">${infoDisplay[33].contentTitle}</a></dt>
                            <dd class="js_sub_info">${infoDisplay[33].contetNotHtml}</dd>
                        </dl>
                    </div>
                    <ul>
                        <li><a href="${ctx}${infoDisplay[34].url}">${infoDisplay[34].contentTitle}</a></li>
                    </ul>
                    <a target="_blank" href="${ctx}culture/list/1080.html"></a>
                </div>
            </div>
            <div class="dress geo" style="margin-top: -8px;">
                <h3 class="tt">地理 GEOGRAPHY</h3>
                <div class="dress-info">
                    <div class="clearfix dress-top">
                        <div class="fl-w154">
                            <a target="_blank" href="${ctx}${infoDisplay[35].url}">  <img src="${ctx}${infoDisplay[35].title}" alt="${infoDisplay[35].contentTitle}"/></a>
                        </div>
                        <dl>
                            <dt><a target="_blank" href="${ctx}${infoDisplay[35].url}">${infoDisplay[35].contentTitle}</a></dt>
                            <dd class="js_sub_info">${infoDisplay[35].contetNotHtml}</dd>
                        </dl>
                    </div>
                    <ul>
                        <li><a target="_blank" href="${ctx}culture/list/1010.html">${infoDisplay[36].contentTitle}</a></li>
                    </ul>
                    <a target="_blank" href="${ctx}culture/list/1110.html"></a>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    seajs.use('${ctxPortal}/assets/css/culture/taste.css');
    seajs.use(['slick/slick.js', 'bootstrap/3.3.1/js/tooltip'], function(a){
        $('.taste-info-header').slick({
            autoplay:true,
            dots: true
        });
    });
    seajs.use('${ctxPortal}/assets/js/subStri.js',function(str){
        str.strClip('.js_sub_posid',200);
        str.strClip('.js_sub_news',40);
        str.strClip('.js_sub_info',40);
    });
</script>