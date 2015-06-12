<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
		<title>游西藏-商户管理-商户信息管理</title>
		<style>
/* 闭合浮动 */
.floatfix:after {
	content: "";
	display: table;
	clear: both;
}

.ord {
    text-decoration: none;
    color: #666
}
</style>
	</head>
	<body>
		<%@include file="/common-html/commonxz.jsp"%>
		<!-- main { -->
		<div class="main">
			<!-- 页面位置-->
			<div class="location mb40">
				<label>
					您当前位置为:
				</label>
				<span> <a>游西藏</a> - <a>商户管理</a> - <a href="#" target="_self">商户信息管理</a>
				</span>
			</div>

			<!-- 数据操作 -->
			<div class="searchManage">
				<rimiTag:perm url="web/merchant/intoMerchantAddUI">
					<a href="javascript:;" target="_self" class="btn-anchor"
						onclick="addUI('${ctx}/web/merchant/intoMerchantAddUI')">添加商户</a>
				</rimiTag:perm>
				<rimiTag:perm url="web/merchant/deleteMerchant">
					<button type="button" class="btn-sure"
						onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/merchant/deleteMerchants','code')">
						批量删除
					</button>
				</rimiTag:perm>
			</div>

			<!-- 搜索 -->
			<div class="searchTools mb20">
				<form action="merchantList" method="post" id="listForm">
					<%--地区搜索--%>
					<%--					<div class="select-base ml-10">--%>
					<%--						<i class="w-110">全部地区</i>--%>
					<%--						<dl>--%>
					<%--							<dt inputValue="" inputName="areaCode">--%>
					<%--								全部地区--%>
					<%--							</dt>--%>
					<%--							<c:forEach var="area" items="${areaList}">--%>
					<%--								<dt inputValue="${area.code}" inputName="areaCode">--%>
					<%--									${area.viewName}--%>
					<%--								</dt>--%>
					<%--							</c:forEach>--%>
					<%--						</dl>--%>
					<%--						<input id="areaCode" type="hidden" value="" name="areaCode" />--%>
					<%--					</div>--%>
					<%--目的地搜索 --%>
					<div class="select-base ml-10">
						<c:if test="${not empty distination}">
							<i>${distination.destinationName}</i>
						</c:if>
						<c:if test="${empty distination}">
							<i class="w-110">全部目的地</i>
						</c:if>
						<dl>
							<dt inputValue="" inputName="destinationCode">
								全部目的地
							</dt>
							<c:forEach var="destination" items="${destinationList}">
								<dt inputValue="${destination.code}" inputName="destinationCode">
									${destination.destinationName}
								</dt>
							</c:forEach>
						</dl>
						<input id="destinationCode" type="hidden"
							value="${distination.code }" name="destinationCode" />
					</div>
					<%--类别搜索 --%>
					<div class="select-base ml-10">
						<c:if test="${not empty type}">
							<i>${type.name}</i>
						</c:if>
						<c:if test="${empty type}">
							<i class="w-110">全部类别</i>
						</c:if>
						<dl>
							<dt inputValue="" inputName="type">
								全部类别
							</dt>
							<c:forEach var="type" items="${typeList}">
								<dt inputValue="${type.code}" inputName="type">
									${type.name}
								</dt>
							</c:forEach>
						</dl>
						<input id="type" type="hidden" value="${type.code}" name="type" />
					</div>

					<%--是否官方搜索 --%>
					<div class="select-base ml-10">
						<i class="w-120">
                            <c:choose>
								<c:when test="${isOffice == 1}">
                                    官方推荐
                                </c:when>
								<c:when test="${isOffice == 0}">
                                    非官方推荐
                                </c:when>
								<c:otherwise>
                                    推荐类型
                                </c:otherwise>
							</c:choose>
                        </i>
						<%--<c:if test="${isOffice == 1}"><i class="w-120">官方推荐</i></c:if>--%>
						<%--<c:if test="${isOffice == 0}"><i class="w-120">非官方推荐</i></c:if>--%>
						<%--<c:if test="${empty isOffice}"><i class="w-120">非官方推荐</i></c:if>--%>
						<dl>
                            <dt inputValue="2" inputName="isOffice">
                                推荐类型
                            </dt>
							<dt inputValue="1" inputName="isOffice">
								官方推荐
							</dt>
							<dt inputValue="0" inputName="isOffice">
								非官方推荐
							</dt>
						</dl>
						<input id="isOffice" type="hidden" value="${isOffice}"
							name="isOffice" />
					</div>
					<div class="select-base float_r">
						<c:if test="${empty rule}">
							<i class="w-140">快捷筛选</i>
						</c:if>
						<c:if test="${rule=='view'}">
							<i class="w-140">按查看量</i>
						</c:if>
						<c:if test="${rule=='reply'}">
							<i class="w-140">按评论量</i>
						</c:if>
						<c:if test="${rule=='collect'}">
							<i class="w-140">按收藏次数</i>
						</c:if>
						<dl>
							<dt>
								<a href="javascript:void(0);" class="ord" ord="merchantList?rule=view">按查看量</a>
							</dt>
							<dt>
								<a href="javascript:void(0);" class="ord" ord="merchantList?rule=reply">按评论量</a>
							</dt>
							<dt>
								<a href="javascript:void(0);" class="ord" ord="merchantList?rule=collect">按收藏次数</a>
							</dt>
						</dl>
					</div>
					<input id="keyWord" type="text" maxlength="30" class="w-260" name="keyWord"
						value="${keyWord}">
						<input type="hidden" value="${rule}" name="rule">
					<button type="submit" class="btn-search"></button>
				</form>

			</div>

			<!-- 模块管理 { -->
			<div class="tableManage pos-rela">

				<!-- 数据列表 -->
				<table border="0" cellpadding="0" cellspacing="0" class="dataTable">
					<thead>
						<tr>
							<th class="center">
								<input type="checkbox" name="dataCheck" class="allCheck">
								全选
							</th>
							<th class="center">
								编号
							</th>
							<th class="center">
								所属目的地
							</th>
							<th class="center">
								所属类别
							</th>
							<th class="center">
								商户名称
							</th>
							<th class="center">
								是否官方推荐
							</th>
							<th class="center">
								操作
							</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${empty pager.dataList}">
							<tr>
								<td>
									暂无数据
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty pager.dataList}">
							<c:forEach items="${pager.dataList}" var="merchant">
								<tr>
									<td class="center">
										<input type="checkbox" class="dataCheck" name="dataCheck"
											value="${merchant.code}">
									</td>
									<td class="center">
										${merchant.code}
									</td>
									<td class="center">
										${merchant.destinationname}
									</td>
									<td class="center">
										${merchant.name}
									</td>
									<td class="center">
										<a href="${ctx}${merchant.url}" target="_blank">${merchant.merchantname}</a>
									</td>
									<c:if test="${merchant.isrecommend != 1}">
										<td class="center">
											非官方推荐
										</td>
									</c:if>
									<c:if test="${merchant.isrecommend == 1}">
										<td class="center">
											官方推荐
										</td>
									</c:if>
									<td class="center">
										<rimiTag:perm url="web/merchant/intoMerchantUpdateUI">
											<a
												href="${ctx}/web/merchant/intoMerchantUpdateUI?code=${merchant.code}">修改</a>
										</rimiTag:perm>
										<rimiTag:perm url="web/merchant/deleteMerchant">
											<a href="#" onclick="deleteM('${merchant.code}')">删除</a>
										</rimiTag:perm>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<!-- 表格分页 -->
				<div class="paging">
					<%@include file="/common-html/pager.jsp"%>
				</div>

			</div>
			<!-- } 模块管理 -->
		</div>
		<!-- } main -->
		<script>

            $(document).on("click",".ord",function() {
                var isOffice = $("#isOffice").val();
                var destinationCode = $("#destinationCode").val();
                var type = $("#type").val();
                var keyWord = $("#keyWord").val();
                var hrefInfo = $(this).attr("ord");
                location.href = hrefInfo+"&isOffice="+isOffice+"&destinationCode="+destinationCode+"&type="+type+"&keyWord="+keyWord;
            });

		 //删除的确认框
		function deleteM(plateCode){
			  popupLayer(
      				'',
      				'<div style="text-align:center">是否删除该商户</div>',
      				'<div class="formLine">' +
      				'<button type="button" class="btn-sure sureDelOtherMud mr20">确定</button>' +
      				'<button type="button" class="btn-sure cancel ml20">取消</button>' +
      				'</div>'
      	  );
      	  $(document).off("click",".sureDelOtherMud").on("click",".sureDelOtherMud",function(){
               $.ajax( {
       			url : "${ctx}/web/merchant/deleteMerchant",
       			dataType : "json",
       			type:"post",
       			data : {
       				"code" : plateCode
       			},
       			async : false,
       			success : function(data) {
           			////.log("success");
       				location.href="${ctx}/web/merchant/merchantList";
       			},
       			error : function(data) {
           			////.log("error");
       				location.href="${ctx}/web/merchant/merchantList";
       			}
       		});
      	  closePopup();
          	});
		}

$(function(){
	var  pageInfo =  '<div class="paging-info">'+
			    		'<span class="disp-ib">当前第&nbsp;${pager.currentPage }&nbsp;页</span>'+
			    		'<span class="disp-ib">共&nbsp;${pager.totalPage }&nbsp;页</span>'+
			    		'<span class="disp-ib">共&nbsp;${pager.totalCount }&nbsp;条</span>' +
			    		'</div>';
	$("#pageInfo").append(pageInfo);
	//
});
</script>
		<!-- 利用空闲时间预加载指定页面 -->
		<link rel="prefetch">
		<!-- IE10+ -->
		<link rel="next">
		<!-- Firefox -->
		<link rel="prerender">
		<!-- Chrome -->
	</body>
</html>

