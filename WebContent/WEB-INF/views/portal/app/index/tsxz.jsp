<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common-html/mainfrontcommon.jsp" %>
<!--==================== 图说西藏 ==========================-->
<div class="container tusxz">
    <div class="row">
        <div class="col-xs-9">
            <!--====== 左边幻灯片模块 ======-->
            <div class="left">
                <!-- 头部标题 -->
                <div class="header">
                    <a href="${ctx }discover/picturelist" target="_blank"> <h2 class="bg-title" style="margin-top:0;">
                        <span class="sr-only">图说西藏</span>
                    </h2>
                    </a>
                    <div class="more">
                        <a href="${ctx }discover/picturelist" target="_blank">更多MORE</a>
                    </div>
                </div>
                <!-- 头部标题 end -->
                <!-- 幻灯片 -->
                <div class="img-lunbo">
                    <div class="img-thumb">
                        <c:forEach items="${homePictureList}" var="pic" varStatus="status">
                            <div>
                                <a href="${pic.hyperlink}" target="_blank"><img src="${ctxIndex}${pic.url}" alt="${pic.name }" style="width:870px;height: 401px;"/></a>
                                <div class="lunbo-title">
                                    <span>${pic.name }</span>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="lunbo-control">
                        <ul>
                            <c:forEach items="${homePictureList}" var="pic" varStatus="status">
                                <li <c:if test="${status.index==0 }">class="active"</c:if> >
                                    <img style="width:103px;height:85px;" src="${ctxIndex }${pic.smimg }" alt="${pic.name }"/>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div><!-- 幻灯片 end-->
            </div><!--====== 左边幻灯片模块 end ======-->
        </div>
        <div class="col-xs-3">
            <!--====== 右边视频区 ======-->
            <div class="right">
                <!-- 头部标题 -->
                <div class="header xz-heading">
                     <a href="${ctx }discover/videolist" target="_blank"><h2 class="bg-title">
                        <span class="sr-only">视频专区</span>
                    </h2></a>
                    <div class="more">
                        <a href="${ctx }discover/videolist" target="_blank">更多MORE</a>
                    </div>
                </div><!-- 头部标题 end -->
                <!-- 视频 -->
                <div class="right-content">
                    <div class="video-top">
                        <div>
                            <a href="${ctx }${viedioList[0].url}" target="_blank">
                                <img style="width:310px;height:200px;"
                                     <c:if test="${not  empty  viedioList[0].imgUrl}">src="${viedioList[0].imgUrl}"</c:if>
                                     <c:if test="${empty  viedioList[0].imgUrl }">src="${ctx}portal/static/default/square.png"</c:if> alt="${viedioList[0].contentTitle}"/>
                            </a>
                            <div onclick="javascript:window.open('${ctx }${viedioList[0].url}')"></div>
                        </div>
                        <p><a target="_blank" href="${ctx }${viedioList[0].url}">${viedioList[0].contentTitle}</a></p>
                    </div>
                    <div class="video-conter">
                        <div class="video-conter-all video-conter-left">
                            <div>
                                <a href="${ctx }${viedioList[1].url}" target="_blank">
                                    <img style="width:98px;height:60px;" src="${viedioList[1].imgUrl}" alt="${viedioList[1].contentTitle}"/></a>
                                <div onclick="javascript:window.open('${ctx }${viedioList[1].url}')"></div>
                            </div>
                            <p><a target="_blank" href="${ctx }${viedioList[1].url}">${viedioList[1].contentTitle}</a></p>
                        </div>
                        <div class="video-conter-all video-conter-cent">
                            <div>
                                <a href="${ctx }${viedioList[2].url}" target="_blank">
                                    <img style="width:98px;height:60px;" src="${viedioList[2].imgUrl}" alt="${viedioList[2].contentTitle}"/></a>
                                <div onclick="javascript:window.open('${ctx }${viedioList[2].url}')"></div>
                            </div>
                            <p><a target="_blank" href="${ctx }${viedioList[2].url}">${viedioList[2].contentTitle}</a></p>
                        </div>
                        <div class="video-conter-all video-conter-right">
                            <div>
                                <a href="${ctx }${viedioList[3].url}" target="_blank">
                                    <img style="width:98px;height:60px;" src="${viedioList[3].imgUrl}" alt="${viedioList[3].contentTitle}"/></a>
                                <div onclick="javascript:window.open('${ctx }${viedioList[3].url}')"></div>
                            </div>
                            <p>
                                <a style="width:98px;height:60px;"
                                   href="${ctx }${viedioList[3].url}">${viedioList[3].contentTitle}</a>
                            </p>
                        </div>
                    </div>
                    <div class="video-conter video-footer">
                        <div class="video-conter-all video-conter-left">
                            <div><a href="${ctx }${viedioList[4].url}" target="_blank">
                                <img style="width:98px;height:60px;" src="${viedioList[4].imgUrl}" alt="${viedioList[4].contentTitle}"/></a>
                                <div onclick="javascript:window.open('${ctx }${viedioList[4].url}')"></div>
                            </div>
                            <p><a target="_blank" href="${ctx }${viedioList[4].url}">${viedioList[4].contentTitle}</a></p>
                        </div>
                        <div class="video-conter-all video-conter-cent">
                            <div>
                                <a href="${ctx }${viedioList[5].url}" target="_blank">
                                    <img style="width:98px;height:60px;" src="${viedioList[5].imgUrl}" alt="${viedioList[5].contentTitle}"/></a>
                                <div onclick="javascript:window.open('${ctx }${viedioList[5].url}')"></div>
                            </div>
                            <p><a target="_blank" href="${ctx }${viedioList[5].url}">${viedioList[5].contentTitle}</a></p>
                        </div>
                        <div class="video-conter-all video-conter-right">
                            <div><a href="${ctx }${viedioList[6].url}" target="_blank">
                                <img style="width:98px;height:60px;" src="${viedioList[6].imgUrl}" alt="${viedioList[6].contentTitle}"/></a>
                                <div onclick="javascript:window.open('${ctx }${viedioList[6].url}')"></div>
                            </div>
                            <p><a target="_blank" href="${ctx }${viedioList[6].url}">${viedioList[6].contentTitle}</a></p>
                        </div>
                    </div>
                </div>
            </div><!-- 视频 end -->
        </div><!--====== 右边视频区 end ======-->
    </div>
</div><!--==================== 图说西藏 end ==========================-->