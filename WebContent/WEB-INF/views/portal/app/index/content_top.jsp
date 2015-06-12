<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/mainfrontcommon.jsp" %>
<div class="container">
	<div class="gallery">
		<div class="row">
			<div class="col-xs-9">
				<div class="gallery-box overflow">
					<h2 class="ho-hig">
						<span class="sr-only">精彩西藏</span>
					</h2>
					<!-- 第一屏 -->
					<div class="gallery-item clearfix">
						<div class="gallery-lg">
							<a href="${homeRecomOneList[0].hyperlink}"   target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomOneList[0].url}" alt="${homeRecomOneList[0].name}" width="501px" height="540px"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a target="_blank" href="${homeRecomOneList[0].hyperlink}">
										<div class="bd-title">${homeRecomOneList[0].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-xs">
							<a href="${homeRecomOneList[1].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomOneList[1].url}" alt="${homeRecomOneList[1].name}"  width="113px" height="160px"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomOneList[1].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomOneList[1].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-xs">
							<a href="${homeRecomOneList[2].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomOneList[2].url}" alt="${homeRecomOneList[2].name}" width="113px" height="160px"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomOneList[2].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomOneList[2].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-xs">
							<a href="${homeRecomOneList[3].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomOneList[3].url}" alt="${homeRecomOneList[3].name}" width="113px" height="160px"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomOneList[3].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomOneList[3].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-xs">
							<a href="${homeRecomOneList[4].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomOneList[4].url}" alt="${homeRecomOneList[4].name}" width="113px" height="160px"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomOneList[4].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomOneList[4].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-sm">
							<a href="${homeRecomOneList[5].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomOneList[5].url}" alt="${homeRecomOneList[5].name}" width="236px" height="160px"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomOneList[5].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomOneList[5].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-md">
							<a href="${homeRecomOneList[6].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomOneList[6].url}" alt="${homeRecomOneList[6].name}" width="360px" height="200px"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomOneList[6].hyperlink}" target="_blank">
										<div class="bd-title text-overflow">${homeRecomOneList[6].name}</div>
									</a>
								</div>
							</div>
						</div>
					</div>
					<!-- 第二屏 -->
					<div class="gallery-item second clearfix">
						<div class="gallery-lg">
							<a href="${homeRecomTwoList[0].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomTwoList[0].url}" alt="${homeRecomTwoList[0].name}" width="500" height="350"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomTwoList[0].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomTwoList[0].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-sm">
							<a href="${homeRecomTwoList[1].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomTwoList[1].url}" alt="${homeRecomTwoList[1].name}" width="220" height="180"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomTwoList[1].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomTwoList[1].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-xs">
							<a href="${homeRecomTwoList[2].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomTwoList[2].url}" alt="${homeRecomTwoList[2].name}" width="130" height="180"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomTwoList[2].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomTwoList[2].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-md">
							<a href="${homeRecomTwoList[3].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomTwoList[3].url}" alt="${homeRecomTwoList[3].name}" width="360" height="350"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomTwoList[3].hyperlink}" target="_blank">
										<div class="bd-title text-overflow">${homeRecomTwoList[3].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-xs">
							<a href="${homeRecomTwoList[4].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomTwoList[4].url}" alt="${homeRecomTwoList[4].name}" width="130" height="180"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomTwoList[4].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomTwoList[4].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-xs">
							<a href="${homeRecomTwoList[5].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomTwoList[5].url}" alt="${homeRecomTwoList[5].name}" width="130" height="180"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomTwoList[5].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomTwoList[5].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-sm">
							<a href="${homeRecomTwoList[6].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomTwoList[6].url}" alt="${homeRecomTwoList[6].name}" width="220" height="180"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomTwoList[6].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomTwoList[6].name}</div>
									</a>
								</div>
							</div>
						</div>
					</div>
					<!-- 第三屏 -->
					<div class="gallery-item page-3rd clearfix">
						<div class="gallery-xs">
							<a href="${homeRecomThreeList[0].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomThreeList[0].url}" alt="${homeRecomThreeList[0].name}" width="160" height="230"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomThreeList[0].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomThreeList[0].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-xs">
							<a href="${homeRecomThreeList[1].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomThreeList[1].url}" alt="${homeRecomThreeList[1].name}" width="160" height="230"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomThreeList[1].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomThreeList[1].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-xs">
							<a href="${homeRecomThreeList[2].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomThreeList[2].url}" alt="${homeRecomThreeList[2].name}" width="160" height="230"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomThreeList[2].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomThreeList[2].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-md">
							<a href="${homeRecomThreeList[3].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomThreeList[3].url}" alt="${homeRecomThreeList[3].name}" width="360" height="350"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomThreeList[3].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomThreeList[3].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-lg">
							<a href="${homeRecomThreeList[4].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomThreeList[4].url}" alt="${homeRecomThreeList[4].name}" width="500" height="300"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomThreeList[4].hyperlink}" target="_blank">
										<div class="bd-title">${homeRecomThreeList[4].name}</div>
									</a>
								</div>
							</div>
						</div>
						<div class="gallery-sm">
							<a href="${homeRecomThreeList[5].hyperlink}" target="_blank">
								<img class="img-rounded" src="${ctxIndex}${homeRecomThreeList[5].url}" alt="${homeRecomThreeList[5].name}" width="360" height="180"></a>
							<div class="gallery-mark">
								<div class="mark-bg"></div>
								<div class="mark-bd">
									<a href="${homeRecomThreeList[5].hyperlink}" target="_blank">
										<div class="bd-title text-overflow">${homeRecomThreeList[5].name}</div>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-3">
				<div class="list">
					<h2 class="ho-hea">
						<a href="${ctx }culture/list/1120.html" target="_blank"></a>
						<span class="sr-only">西藏动态</span>
					</h2>
					<c:forEach var="dy" items="${dyDataList}">
                    <div class="list-item">
                        <a href="${ctx}${dy.url}" target="_blank" title="${dy.contentTitle}">
                            <h3 class="list-item-heading text-overflow <c:if test="${dy.isOfficial==0}"> best </c:if>" ><c:if test="${dy.isOfficial==0}"><i class="best-icon"></i></c:if>${dy.contentTitle}</h3>
                        </a>
                        <p class="list-item-text text-overflow js_pInfo">${dy.content}</p>
                    </div>
					</c:forEach>
				</div>
				<div class="list-mix">
					<h2 class="ho-com">
						<a href="${ctx }community" target="_blank"></a>
						<span class="sr-only">社区推荐</span>
					</h2>
					<c:forEach var="dy" items="${postList}">
					<a href="${ctx}${dy.url}" class="link-mix" target="_blank" title="【${dy.programaCode}】${dy.contentTitle}">
						<span class="light">【${dy.programaCode}】</span>${dy.contentTitle}
					</a>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</div>