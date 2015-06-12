  <%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/frontcommon.jsp" %>
    <div class="container tourist">
        <div class="row">
            <div class="col-xs-9 tourist-left">
                <div class="header">
                    <div class="left"></div>
                    <div class="right"><a href="${ctx }/tourism/view/forView"  target="_blank">更多MORE</a></div>
                </div>
                <div class="content">
                    <div class="clearfix content-top">
                        <div>
                            <a href="${ctx}${hotviewList[0].linkUrl}"  target="_blank"><img src="${ctx}${hotviewList[0].viewImage}" alt="${hotviewList[0].viewName}"/></a>
                            <div class="img-info">
                                <div class="scene-name">${hotviewList[0].viewName}</div>
                                <div class="scene-go">
                                    <p>去过：<span>${hotviewList[0].goneCount}</span></p>
                                    <p>想去：<span>${hotviewList[0].wantCount}</span></p>
                                </div>
                            </div>
                        </div>
                        <div>
                            <a href="${ctx}${hotviewList[1].linkUrl}" target="_blank"><img src="${ctx}/${hotviewList[1].viewImage}" alt="${hotviewList[1].viewName}"/></a>
                            <div class="img-info img-info-top-2">
                                <div class="scene-name">${hotviewList[1].viewName}</div>
                                <div class="scene-go">
                                    <p>去过：<span>${hotviewList[1].goneCount}</span></p>
                                    <p>想去：<span>${hotviewList[1].wantCount}</span></p>
                                </div>
                            </div>
                        </div>
                        <div>
                            <a href="${ctx}${hotviewList[2].linkUrl}" target="_blank"><img src="${ctx}/${hotviewList[2].viewImage}" alt="${hotviewList[2].viewName}"/></a>
                            <div class="img-info img-info-top-3">
                                <div class="scene-name">${hotviewList[2].viewName}</div>
                                <div class="scene-go">
                                    <p>去过：<span>${hotviewList[2].goneCount}</span></p>
                                    <p>想去：<span>${hotviewList[2].wantCount}</span></p>
                                </div>
                            </div>
                        </div>
                        <div>
                            <a href="${ctx}${hotviewList[3].linkUrl}"><img src="${ctx}/${hotviewList[3].viewImage}" alt="${hotviewList[3].viewName}"/></a>
                            <div class="img-info img-info-top-4">
                                <div class="scene-name">${hotviewList[3].viewName}</div>
                                <div class="scene-go">
                                    <p>去过：<span>${hotviewList[3].goneCount}</span></p>
                                    <p>想去：<span>${hotviewList[2].wantCount}</span></p>
                                </div>
                            </div>
                        </div>
                        <div>
                            <a href="${ctx}${hotviewList[4].linkUrl}" target="_blank"><img src="${ctx}/${hotviewList[4].viewImage}" alt="${hotviewList[4].viewName}"/></a>
                            <div class="img-info img-info-top-5">
                                <div class="scene-name">${hotviewList[4].viewName}</div>
                                <div class="scene-go">
                                    <p>去过：<span>${hotviewList[4].goneCount}</span></p>
                                    <p>想去：<span>${hotviewList[4].wantCount}</span></p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix content-top content-footer">
                        <div>
                            <a href="${ctx}${hotviewList[5].linkUrl}" target="_blank"><img src="${ctx}/${hotviewList[5].viewImage}" alt="${hotviewList[5].viewName}"/></a>
                            <div class="img-info img-info-bottom-1">
                                <div class="scene-name">${hotviewList[5].viewName}</div>
                                <div class="scene-go">
                                    <p>去过：<span>${hotviewList[5].goneCount}</span></p>
                                    <p>想去：<span>${hotviewList[5].wantCount}</span></p>
                                </div>
                            </div>
                        </div>
                        <div>
                            <a href="${ctx}${hotviewList[6].linkUrl}" target="_blank"><img src="${ctx}/${hotviewList[6].viewImage}" alt="${hotviewList[6].viewName}"/></a>
                            <div class="img-info img-info-bottom-2">
                                <div class="scene-name">${hotviewList[6].viewName}</div>
                                <div class="scene-go">
                                    <p>去过：<span>${hotviewList[6].goneCount}</span></p>
                                    <p>想去：<span>${hotviewList[6].wantCount}</span></p>
                                </div>
                            </div>
                        </div>
                        <div>
                            <a href="${ctx}${hotviewList[7].linkUrl}" target="_blank"><img src="${ctx}/${hotviewList[7].viewImage}" alt="${hotviewList[7].viewName}"/></a>
                            <div class="img-info img-info-bottom-3">
                                <div class="scene-name">${hotviewList[7].viewName}</div>
                                <div class="scene-go">
                                    <p>去过：<span>${hotviewList[7].goneCount}</span></p>
                                    <p>想去：<span>${hotviewList[7].wantCount}</span></p>
                                </div>
                            </div>
                        </div>
                        <div>
                            <a href="${ctx}${hotviewList[8].linkUrl}" target="_blank"><img src="${ctx}/${hotviewList[8].viewImage}" alt="${hotviewList[8].viewName}"/></a>
                            <div class="img-info img-info-bottom-4">
                                <div class="scene-name">${hotviewList[8].viewName}</div>
                                <div class="scene-go">
                                    <p>去过：<span>${hotviewList[8].goneCount}</span></p>
                                    <p>想去：<span>${hotviewList[8].wantCount}</span></p>
                                </div>
                            </div>
                        </div>
                        <div>
                            <a href="${ctx}${hotviewList[9].linkUrl}" target="_blank"><img src="${ctx}/${hotviewList[9].viewImage}" alt="${hotviewList[9].viewName}"/></a>
                            <div class="img-info img-info-bottom-5">
                                <div class="scene-name">${hotviewList[9].viewName}</div>
                                <div class="scene-go">
                                    <p>去过：<span>${hotviewList[9].goneCount}</span></p>
                                    <p>想去：<span>${hotviewList[9].wantCount}</span></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xs-3 tourist-right">
                <div class="header">
                    <div class="left">${merchantOneMap.merchantType }</div>
                    <div class="right"><a href="${ctx }/tourism/merchant/merchantList" target="_blank">更多MORE</a></div>
                </div>
                <div class="content">
                    <h5>${merchantOneMap.merchantList[0].merchantName}</h5>
                    <div class="media message">
                        <a class="media-left" target="_blank" href="${ctx }/${merchantOneMap.merchantUrlList[0]}">
                            <img src="${ ctx}/${merchantOneMap.merchantList[0].merchantImage}">
							<c:if test="${merchantOneMap.merchantList[0].isRecommend eq 1}">
							<img class="ctibet" src="${ctxPortal}assets/icon/ctibet_yxz.png"/>
							</c:if>	
                        </a>
                        <div class="media-body">
                            <p>	${merchantOneMap.merchantList[0].merchantSummary}</p>
                        </div>
                        <div class="loving">
                            <img src="${ctxPortal}assets/icon/star.png"/>
                            <label>${merchantOneMap.merchantPraiseList[0].falsePraise}</label>
                        </div>
                    </div>
                    <div class="line"></div>
                </div>
                <div class="content content-mart">
                    <h5>${merchantOneMap.merchantList[1].merchantName}</h5>
                    <div class="media message">
                        <a class="media-left" target="_blank" href="${ctx }/${merchantOneMap.merchantUrlList[1]}">
                            <img src="${ ctx}/${merchantOneMap.merchantList[1].merchantImage}">
							<c:if test="${merchantOneMap.merchantList[1].isRecommend eq 1}">
							<img class="ctibet" src="${ctxPortal}assets/icon/ctibet_yxz.png"/>
							</c:if>	                            
                        </a>
                        <div class="media-body">
                            <p>${merchantOneMap.merchantList[1].merchantSummary}</p>
                        </div>
                        <div class="loving">
                            <img src="${ctxPortal}/assets/icon/star.png"/>
                            <label>${merchantOneMap.merchantPraiseList[1].falsePraise}</label>
                        </div>
                    </div>
                    <div class="line"></div>
                </div>
                <div class="content content-mart">
                    <h5>${merchantOneMap.merchantList[2].merchantName}</h5>
                    <div class="media message">
                        <a class="media-left" target="_blank" href="${ctx }/${merchantOneMap.merchantUrlList[2]}">
                            <img src="${ctx}/${merchantOneMap.merchantList[2].merchantImage}">
							<c:if test="${merchantOneMap.merchantList[2].isRecommend eq 1}">
							<img class="ctibet" src="${ctxPortal}assets/icon/ctibet_yxz.png"/>
							</c:if>	
                        </a>
                        <div class="media-body">
                            <p>${merchantOneMap.merchantList[2].merchantSummary}</p>
                        </div>
                        <div class="loving">
                            <img src="${ctxPortal}/assets/icon/star.png"/>
                            <label>${merchantOneMap.merchantPraiseList[2].falsePraise}</label>
                        </div>
                    </div>
                    <div class="line"></div>
                </div>
                <div class="content content-mart">
                    <h5>${merchantOneMap.merchantList[3].merchantName}</h5>
                    <div class="media message">
                        <a class="media-left" target="_blank" href="${ctx }/${merchantOneMap.merchantUrlList[0]}">
                            <img src="${ctx}/${merchantOneMap.merchantList[3].merchantImage}">
							<c:if test="${merchantOneMap.merchantList[3].isRecommend eq 1}">
							<img class="ctibet" src="${ctxPortal}assets/icon/ctibet_yxz.png"/>
							</c:if>	                            
                        </a>
                        <div class="media-body">
                            <p>${merchantOneMap.merchantList[3].merchantSummary}</p>
                        </div>
                        <div class="loving">
                            <img src="${ctxPortal}/assets/icon/star.png"/>
                            <label>${merchantOneMap.merchantPraiseList[3].falsePraise}</label>
                        </div>
                    </div>
                    <div class="line"></div>
                </div>
            </div>
        </div>
    </div>

    <script>
        seajs.use('${ctxPortal}/assets/css/tourism/tourist.css');
    </script>