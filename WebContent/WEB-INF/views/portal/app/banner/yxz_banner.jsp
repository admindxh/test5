<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/mainfrontcommon.jsp" %>
<script id="seajsnode" src="${ctxPortal}modules/seajs/seajs/2.2.1/sea.js"></script>
<div id="banner">
    <div class="banner-item" style="background-image: url(${ctxIndex}${bannerImage[0].url});">
        <div class="banner-content">
            <div class="banner-right">
                <div class="secondary-menu">
                    <ul>
                        <li class="location-hover clearfix"  onclick="javascript:window.open('${ctx }tourism/view/list')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}assets/icon/location_pin.png" alt="热门景点"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>热门景点</span>
                            </div>
                        </li>
                        <li class="bookmark-hover clearfix"  onclick="javascript:window.open('${ctx }tourism/strage/intoTraval/c1d94bbe-792e-11e4-b6ce-005056a05bc9.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}assets/icon/bookmark.png" alt="综合攻略"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>综合攻略</span>
                            </div>
                        </li>
                        <li class="car-hover clearfix"  onclick="javascript:window.open('${ctx }tourism/strage/intoTraval/c1d87c3d-792e-11e4-b6ce-005056a05bc9.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}assets/icon/car.png" alt="自驾游攻略"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>自驾游攻略</span>
                            </div>
                        </li>
                        <li class="bicyle-hover clearfix"  onclick="javascript:window.open('${ctx }tourism/strage/intoTraval/c1d8e87d-792e-11e4-b6ce-005056a05bc9.html')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}assets/icon/bicycle.png" alt="骑行攻略"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>骑行攻略</span>
                            </div>
                        </li>
                        <li class="store-hover clearfix"  onclick="javascript:window.open('${ctx }tourism/merchant/index')">
                            <div class="menu-icon">
                                <div></div>
                                <img src="${ctxPortal}assets/icon/store.png" alt="商户"/>
                            </div>
                            <div class="menu-info">
                                <div></div>
                                <span>商户</span>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script>seajs.use('${ctxPortal}assets/css/tourism/yxz_header.css');</script>