<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/rimi-tags"  prefix="rimi"%>
<%@include  file="/common-html/mainfrontcommon.jsp"%>
<!--============== 游西藏 ============-->
<div class="youxz container">
    <div class="header xz-heading">
       <a href="${ctx }tourism/strage/intoTraval" target="_blank"> <h2 class="title">
            <span class="sr-only">游西藏</span>
        </h2></a>
        <div class="more">
            <a href="${ctx }tourism/strage/intoTraval" target="_blank">更多 MORE</a>
        </div>
    </div>
    <!--=========== content ===========-->
    <div class="content">
        <div class="row">
            <div class="col-xs-9">
                <div class="left">
                    <h3 class="xiaotitle">攻略游记 RAIDERS&BLOGS</h3>
                    <button class="btn submit-btn" type="button" onclick="uploadStrage()"></button>
                    <!--========== 攻略的信息 ==========-->
                    <c:forEach items="${strageList}" var="strage">
                        <div class="gl-info">
                            <div class="media gl-img">
                                <a href="${ctxIndex}${strage.url}" target="_blank" class="media-left">
                                    <img  style="width:201px ;height:120px " src="${strage.imgUrl}" alt="${strage.contentTitle }"/>
                                    <c:if test="${strage.isOfficial==1}">
                                        <div class="ctibet"></div>
                                    </c:if>
                                </a>
                                <div class="media-body info">
                                    <h4 class="media-heading"><a href="${ctxIndex }${strage.url}" target="_blank" >${strage.contentTitle }</a></h4>
                                    <p class="zuozhe">
                                        <c:if test=""></c:if>
                                        <img src="${ctxPortal}static/images/tx_1.png" alt="作者"/>
                                        <span>作者：</span><span class="zuozname">
	                                <c:if test="${strage.isOfficial==1}">${strage.authorCode }</c:if>
	                                <c:if test="${strage.isOfficial!=1}"><a  href="${ctx }member/show/${strage.createuserCode}.html" target="_blank">${strage.authorCode }</a></c:if>
	                                </span></p>
                                    <div class="js_info">
                                    	<c:set var="s_content" value="${fn:trim(strage.content)}" />
                                        <p><a href="${ctxIndex }${strage.url}" target="_blank">${fn:substring(s_content, 0, 65)}${fn:length(s_content)>65?'···':''}</a></p>
                                        <%-- <p><a href="${ctxIndex }${strage.url}" target="_blank">${strage.content }</a></p> --%>
                                    </div>
                                </div>
                                <div class="pinlun">
                                    <div class="zan">
                                       <button type="button"  onclick="isRecored('${strage.code}',this)"
                                            <rimi:IsPraiseTag code="${strage.code}">class="zan_active"</rimi:IsPraiseTag>
                                            indexSgType="${strage.programaCode }" ></button>
                                        <p>${strage.falsePraise }</p>
                                    </div> 
                                    <div class="souc">
                                        <div class="guanc" > <img src="${ctxPortal}assets/icon/eye.png" title="查看数"/><span>${strage.falseViewCount }</span></div>
                                        <div  class="xiny"> <img src="${ctxPortal}assets/icon/fav_b.png" title="收藏数"/><span>${strage.falseFavite }</span></div>
                                    </div> 
                                </div>
                            </div>
                            <div class="jiange"></div>
                        </div>
                    </c:forEach><!--========== 攻略的信息 end ==========-->
                </div>
            </div>
            <div class="col-xs-3">
                <div class="right">
                    <h3 class="xiaotitle">景点 SCENIC SPOT</h3>
                    <div class="jingd-img-first">     
                        <a href="${ctxIndex }/${homeViewList[0].hyperlink}"  target="_blank"> <img  style="width: 310px;height: 344px;" src="${ctxIndex}${homeViewList[0].url}" alt="${homeViewList[0].name }"/></a>
                        <div class="img-info">
                        	<div class="mengc"></div>
                            <div class="dingw"><a href="${ctxIndex }/${homeViewList[0].hyperlink}"  target="_blank">${homeViewList[0].name }</a></div>
                        </div>
                    </div>
                    <div class="jd-left">
                        <div class="jingd-img-second">
                           <a href="${ctxIndex }/${homeViewList[1].hyperlink}"  target="_blank"> <img style="width: 113px;height: 160px;" src="${ctxIndex}${homeViewList[1].url}" alt="${homeViewList[1].name }"/></a>
                            <div class="img-info">
                                <div class="mengc"></div>
                                <div class="dingw"><a href="${ctxIndex }/${homeViewList[1].hyperlink}"  target="_blank">${homeViewList[1].name}</a></div>
                            </div>
                        </div>
                        <div class="jingd-img-second jingd-img-third">
                            <a href="${ctxIndex }/${homeViewList[2].hyperlink}"  target="_blank"><img style="width: 113px;height: 105px;"  src="${ctxIndex}${homeViewList[2].url}" alt="${homeViewList[2].name }"/></a>
                            <div class="img-info">
                                <div class="mengc"></div>
                                <div class="dingw"> <a href="${ctxIndex }/${homeViewList[2].hyperlink}"  target="_blank">${homeViewList[2].name}</a></div>
                            </div>
                        </div>
                    </div>
                    <div class="jd-right">
                        <div class="jd-right-top">
                            <div class="jd-img-four">
                               <a href="${ctxIndex }/${homeViewList[3].hyperlink}"  target="_blank"> <img width="140" height="72" src="${ctxIndex}${homeViewList[3].url}" alt="${homeViewList[3].name }"/></a>
                                <div class="img-info">
                                    <div class="mengc"></div>
                                    <div class="dingw"><a href="${ctxIndex }/${homeViewList[3].hyperlink}"  target="_blank">${homeViewList[3].name}</a></div>
                                </div>
                            </div>
                        </div>
                        <div class="jd-img-six">
                            <a href="${ctxIndex }/${homeViewList[4].hyperlink}"  target="_blank"><img style="width: 187px;height: 167px;" src="${ctxIndex}${homeViewList[4].url}" alt="${homeViewList[4].name }"/></a>
                            <div class="img-info">
                                <div class="mengc"></div>
                                <div class="dingw"><a href="${ctxIndex }/${homeViewList[4].hyperlink}"  target="_blank">${homeViewList[4].name}</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div><!--=========== content end ===========-->
</div>
<script type="text/javascript">
    //上传攻略
    function  uploadStrage(){
        if(checkPortalUserLongin()){
            var  href  = window.location.href;
            window.open('${ctx}tourism/upload/intoStragePage?sourceHref='+href);
        }else{
            $('[data-toggle="login"]').click();
        }
    }
</script><!--============== 游西藏 end ============-->