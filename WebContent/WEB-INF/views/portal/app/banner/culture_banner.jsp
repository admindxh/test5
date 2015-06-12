<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/common.jsp" %>
<div id="culture-banner" style="height: 358px;">
    <div class="banner-item" style="background-image: url(${ctx}${banner.imageUrl});">
        <div class="banner-content">
            <div class="banner-right">
                <div class="clearfix culture-menu">
                    <ul>
                        <li class="menu-news clearfix" onclick="javascript:window.open('${ctx}culture/list/1120.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}/assets/icon/culture_menu_news.png" alt="西藏动态"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>西藏动态</span>
                            </div>
                        </li>
                        <li class="menu-calendar clearfix" onclick="javascript:window.open('${ctx}culture/list/1010.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}/assets/icon/culture_menu_calendar.png" alt="节日"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>节日</span>
                            </div>
                        </li>
                        <li class="menu-literature clearfix" onclick="javascript:window.open('${ctx}culture/list/1020.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}/assets/icon/culture_menu_literature.png" alt="历史"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>历史</span>
                            </div>
                        </li>
                        <li class="menu-custom clearfix" onclick="javascript:window.open('${ctx}culture/list/1030.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}/assets/icon/culture_menu_custom.png" alt="风俗"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>风俗</span>
                            </div>
                        </li>
                        <li class="menu-religion clearfix" onclick="javascript:window.open('${ctx}culture/list/1040.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}/assets/icon/culture_menu_religion.png" alt="宗教"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>宗教</span>
                            </div>
                        </li>
                        <li class="menu-food clearfix" onclick="javascript:window.open('${ctx}culture/list/1050.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}/assets/icon/culture_menu_food.png" alt="美食"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>美食</span>
                            </div>
                        </li>
                        <li class="menu-plant clearfix" onclick="javascript:window.open('${ctx}culture/list/1060.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}/assets/icon/culture_menu_plant.png" alt="动植物"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>动植物</span>
                            </div>
                        </li>
                    </ul>
                    <ul>
                        <li class="menu-celebrity clearfix" onclick="javascript:window.open('${ctx}culture/list/1070.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}/assets/icon/culture_menu_celebrity.png" alt="名人"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>名人</span>
                            </div>
                        </li>
                        <li class="menu-shirt clearfix" onclick="javascript:window.open('${ctx}culture/list/1080.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}/assets/icon/culture_menu_shirt.png" alt="服饰"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>服饰</span>
                            </div>
                        </li>
                        <li class="menu-art clearfix" onclick="javascript:window.open('${ctx}culture/list/1090.html')" >
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}/assets/icon/culture_menu_art.png" alt="艺术"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>艺术</span>
                            </div>
                        </li>
                        <li class="menu-honey clearfix" onclick="javascript:window.open('${ctx}culture/list/1100.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}/assets/icon/culture_menu_honey.png" alt="特产"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>特产</span>
                            </div>
                        </li>
                        <li class="menu-timer clearfix" onclick="javascript:window.open('${ctx}culture/list/1110.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}/assets/icon/culture_menu_timer.png" alt="地理"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>地理</span>
                            </div>
                        </li>
                        <li class="menu-diss clearfix" onclick="javascript:window.open('${ctx}culture/cultural')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}/assets/icon/culture_menu_diss.png" alt="西藏文化传播"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>西藏<br/>文化<br/>传播</span>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script>seajs.use('${ctxPortal}/assets/css/culture/culture_banner.css');</script>