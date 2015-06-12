<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/common-html/mainfrontcommon.jsp" %>
<style>
    h5 a,h5,h3,h3 a{overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
</style>
<div class="container content-header" style="height: 360px; overflow: hidden;">
    <div class="row">
        <div class="col-xs-9 left circulation">
            <div class="img-box clearfix">
                <div class="box-left">
                    <div class="img-show"><a href="${homeRecomOneList[0].hyperlink}" target="_blank">
                        <img src="${ctxIndex }${homeRecomOneList[0].url}" alt="${homeRecomOneList[0].name}"/></a>
                    </div>
                    <div class="box-info">
                        <h3><a href="${homeRecomOneList[0].hyperlink}" target="_blank">${homeRecomOneList[0].name}</a></h3>
                    </div>
                </div>
                <div class="box-right">
                    <div class="box-top clearfix">
                        <div class="box-item-1">
                            <div class="img-show"><a href="${homeRecomOneList[1].hyperlink}" target="_blank">
                                <img src="${ctxIndex}${homeRecomOneList[1].url}" alt="${homeRecomOneList[1].name}"/></a>
                            </div>
                            <div class="box-info box-info-even">
                                <h3><a href="${homeRecomOneList[1].hyperlink}" target="_blank">${homeRecomOneList[1].name}</a></h3>
                            </div>
                        </div>
                        <div class="box-item-1">
                            <div class="img-show"><a href="${homeRecomOneList[2].hyperlink}" target="_blank">
                                <img src="${ctxIndex }${homeRecomOneList[2].url}" alt="${homeRecomOneList[2].name}"/></a>
                            </div>
                            <div class="box-info box-info-odd">
                                <h3><a href="${homeRecomOneList[2].hyperlink}" target="_blank">${homeRecomOneList[2].name}</a></h3>
                            </div>
                        </div>
                        <div class="box-item-1">
                            <div class="img-show"><a href="${homeRecomOneList[3].hyperlink}" target="_blank">
                                <img src="${ctxIndex }${homeRecomOneList[3].url}" alt="${homeRecomOneList[3].name}"/></a>
                            </div>
                            <div class="box-info box-info-odd">
                                <h3><a href="${homeRecomOneList[3].hyperlink}" target="_blank">${homeRecomOneList[3].name}</a></h3>
                            </div>
                        </div>
                    </div>
                    <div class="box-bottom clearfix">
                        <div class="box-item-1">
                            <div class="img-show"><a href="${homeRecomOneList[4].hyperlink}" target="_blank">
                                <img src="${ctxIndex }${homeRecomOneList[4].url}" alt="${homeRecomOneList[4].name}"/></a>
                            </div>
                            <div class="box-info box-info-odd">
                                <h3><a href="${homeRecomOneList[4].hyperlink}" target="_blank">${homeRecomOneList[4].name}</a></h3>
                            </div>
                        </div>
                        <div class="box-item-2">
                            <div class="img-show"><a href="${homeRecomOneList[5].hyperlink}" target="_blank">
                                <img src="${ctxIndex }${homeRecomOneList[5].url}" alt="${homeRecomOneList[5].name}"/></a>
                            </div>
                            <div class="box-info box-info-even">
                                <h3><a href="${homeRecomOneList[5].hyperlink}" target="_blank">${homeRecomOneList[5].name}</a></h3>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="img-box clearfix img-box1">
                <div class="box-right">
                    <div class="box-top">
                        <div class="img-show">
                            <a href="${homeRecomTwoList[0].hyperlink}" target="_blank">
                                <img src="${ctxIndex }${homeRecomTwoList[0].url}" alt="${homeRecomTwoList[0].name}"/></a>
                        </div>
                        <div class="box-mc"></div>
                        <div class="box-info box-info-even">
                            <h3><a href="${homeRecomTwoList[0].hyperlink}" target="_blank">${homeRecomTwoList[0].name}</a></h3>
                        </div>
                    </div>
                    <div class="box-bottom clearfix">
                        <div class="box-item-1">
                            <div class="img-show">
                                <a href="${homeRecomTwoList[1].hyperlink}" target="_blank">
                                    <img src="${ctxIndex }${homeRecomTwoList[1].url}" alt="${homeRecomTwoList[1].name}"/></a>
                            </div>
                            <div class="box-mc"></div>
                            <div class="box-info box-info-odd">
                                <h3><a href="${homeRecomTwoList[1].hyperlink}" target="_blank">${homeRecomTwoList[1].name}</a></h3>
                            </div>
                        </div>
                        <div class="box-item-2">
                            <div class="img-show">
                                <a href="${homeRecomTwoList[2].hyperlink}" target="_blank">
                                    <img src="${ctxIndex }${homeRecomTwoList[2].url}" alt="${homeRecomTwoList[2].name}"/></a>
                            </div>
                            <div class="box-mc"></div>
                            <div class="box-info box-info-even">
                                <h3><a href="${homeRecomTwoList[2].hyperlink}" target="_blank">${homeRecomTwoList[2].name}</a></h3>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box-left">
                    <div class="img-show">
                        <a href="${homeRecomTwoList[3].hyperlink}" target="_blank">
                            <img src="${ctxIndex }${homeRecomTwoList[3].url}" alt="${homeRecomTwoList[3].name}"/></a>
                    </div>
                    <div class="box-mc"></div>
                    <div class="box-info">
                        <h3><a href="${homeRecomTwoList[3].hyperlink}" target="_blank">${homeRecomTwoList[3].name}</a></h3>
                    </div>
                </div>
            </div>
            <div class="img-box clearfix img-box2">
                <div class="img-l">
                    <div class="box-top clearfix">
                        <div class="box-left">
                            <div class="img-show">
                                <a href="${homeRecomThreeList[0].hyperlink}" target="_blank">
                                    <img src="${ctxIndex }${homeRecomThreeList[0].url}" alt="${homeRecomThreeList[0].name}"/></a>
                            </div>
                            <div class="box-mc"></div>
                            <div class="box-info">
                                <h3 ><a href="${homeRecomThreeList[0].hyperlink}" target="_blank">${homeRecomThreeList[0].name}</a></h3>
                            </div>
                        </div>
                        <div class="box-right">
                            <div class="img-show">
                                <a href="${homeRecomThreeList[1].hyperlink}" target="_blank">
                                    <img src="${ctxIndex }${homeRecomThreeList[1].url}" alt="${homeRecomThreeList[1].name}"/></a>
                            </div>
                            <div class="box-mc"></div>
                            <div class="box-info box-info-even">
                                <h3><a href="${homeRecomThreeList[1].hyperlink}" target="_blank">${homeRecomThreeList[1].name}</a></h3>
                            </div>
                        </div>
                    </div>
                    <div class="box-bottom clearfix">
                        <div class="box-left">
                            <div class="img-show">
                                <a href="${homeRecomThreeList[3].hyperlink}" target="_blank">
                                    <img src="${ctxIndex }${homeRecomThreeList[3].url}" alt="${homeRecomThreeList[3].name}"/></a>
                            </div>
                            <div class="box-mc"></div>
                            <div class="box-info">
                                <h3><a href="${homeRecomThreeList[3].hyperlink}" target="_blank">${homeRecomThreeList[3].name}</a></h3>
                            </div>
                        </div>
                        <div class="box-right">
                            <div class="img-show">
                                <a href="${homeRecomThreeList[4].hyperlink}" target="_blank">
                                    <img src="${ctxIndex }${homeRecomThreeList[4].url}" alt="${homeRecomThreeList[4].name}"/></a>
                            </div>
                            <div class="box-mc"></div>
                            <div class="box-info box-info-even">
                                <h3><a href="${homeRecomThreeList[4].hyperlink}" target="_blank">${homeRecomThreeList[4].name}</a></h3>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="img-r">
                    <div class="box-top">
                        <div class="img-show">
                            <a href="${homeRecomThreeList[2].hyperlink}" target="_blank">
                                <img src="${ctxIndex }${homeRecomThreeList[2].url}" alt="${homeRecomThreeList[2].name}"/></a>
                        </div>
                        <div class="box-mc"></div>
                        <div class="box-info">
                            <h3><a href="${homeRecomThreeList[2].hyperlink}" target="_blank">${homeRecomThreeList[2].name}</a></h3>
                        </div>
                    </div>
                    <div class="box-bottom">
                        <div class="img-show">
                            <a href="${homeRecomThreeList[5].hyperlink}" target="_blank">
                                <img src="${ctxIndex }${homeRecomThreeList[5].url}" alt="${homeRecomThreeList[5].name}"/></a>
                        </div>
                        <div class="box-mc"></div>
                        <div class="box-info">
                            <h3><a href="${homeRecomThreeList[5].hyperlink}" target="_blank">${homeRecomThreeList[5].name}</a></h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-3 right">
            <div class="air-ticket">
                <div class="air-header">
                    <img src="${ctxPortal}assets/icon/aircraft.png"/>
                    <h5>机票搜索<br/>TICKET SEARCH</h5>
                    <a href="http://flights.ctrip.com/" target="_blank">
                        <span>更多</span><img src="${ctxPortal}assets/icon/more_white.png"/></a>
                </div>
                <div class="colour-bar"></div>
                <form target="_blank" id="form1" method="post" action="${ctx}portal/airPlane/OrderAirPlane">
	                <div class="air-content">
	                    <div id="start">
	                        <label>出发日期</label><input type="text" name="date" value="${ct }" readonly="readonly"/>
	                    </div>
	                    <div style="position: relative;">
	                        <label>出发城市</label><input type="text" name="start" value="" id="J_CityFloat" autocomplete="off" readonly="readonly"/>
                            <div class="float">
                                <div class="hot-city">
                                    <label>热门</label>
                                </div>
                                <div class="link-city" id="startcity">
                                    <input type="hidden" name="start">
                                    <a href="javascript:;" data-code="BJS">北京</a>
                                    <a href="javascript:;" data-code="SHA">上海</a>
                                    <a href="javascript:;" data-code="SZX">深圳</a>
                                    <a href="javascript:;" data-code="CAN">广州</a>
                                    <a href="javascript:;" data-code="TAO">青岛</a>
                                    <a href="javascript:;" data-code="CTU">成都</a>
                                    <a href="javascript:;" data-code="HGH">杭州</a>
                                    <a href="javascript:;" data-code="WUH">武汉</a>
                                    <a href="javascript:;" data-code="TSN">天津</a>
                                    <a href="javascript:;" data-code="DLC">大连</a>
                                    <a href="javascript:;" data-code="XMN">厦门</a>
                                    <a href="javascript:;" data-code="CKG">重庆</a>
                                </div>
                            </div>
	                    </div>
	                    <div style="position: relative;">
	                        <label>到达城市</label><input type="text" name="" value="" id="e_CityFloat" autocomplete="off" readonly="readonly" />
                            <div class="float">
                                <div class="link-city" id="destcity">
                                    <input type="hidden" name="dest">
                                    <a href="javascript:;" data-code="拉萨">拉萨贡嘎机场</a>
                                    <a href="javascript:;" data-code="林芝">林芝米林机场</a>
                                    <a href="javascript:;" data-code="昌都">昌都邦达机场</a>
                                </div>
                            </div>
	                    </div>
	                    <button type="button" onclick="orderAir()"></button>
	                </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}common-js/tsctheader.js" type="text/javascript"></script>