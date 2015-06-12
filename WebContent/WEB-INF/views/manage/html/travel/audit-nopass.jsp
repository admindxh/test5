<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="rimiTag" uri="/rimi-tags" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>游西藏-攻略管理-待审核的攻略</title>
    <%@include file="/common-html/common.jsp" %>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>

    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <script type="text/javascript">
        $(function () {
            var pageInfo = '<div class="paging-info">' +
                    '<span class="disp-ib">当前第&nbsp;${pager.currentPage }&nbsp;页</span>' +
                    '<span class="disp-ib">共&nbsp;${pager.totalPage }&nbsp;页</span>' +
                    '<span class="disp-ib">共&nbsp;${pager.totalCount }&nbsp;条</span>' +
                    '</div>';
            $("#pageInfo").append(pageInfo);
        });

    </script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>游西藏</a> -</span>
        <span><a>攻略管理</a> -</span>
        <span><a href="#" target="_self">待审核的攻略</a></span>
    </div>

    <!-- 模块管理 { -->
    <div class="tableManage pos-rela">
        <!-- 搜索查询栏 { -->
        <form id="listForm" method="post" action="${ctx}/web/readTibetSgCheckMgController/list">
            <div class="searchTools">

                <!-- 攻略类型 -->
                <div class="select-base ml-10">
                    <i class="w-110">${proText }</i>
                    <dl>
                        <dt inputValue="" inputName="proCode" inputTextValue="全部版块" inputTextName="proCodeText"> 全部版块
                        </dt>
                        <c:forEach var="program" items="${programsList}">
                            <dt inputValue="${program.code}" inputName="proCode"
                                inputTextValue="${program.programaName}"
                                inputTextName="proCodeText">${program.programaName}</dt>
                        </c:forEach>
                    </dl>
                    <input id="proCode" type="hidden" value="${proCode }" name="proCode"/>
                    <input id="proCodeText" type="hidden" value="${proText }" name="proCodeText"/>
                </div>

                <!-- 审核状态 -->
                <div class="select-base ml-10">
                    <i class="w-110">${stateText }</i>
                    <dl>
                        <dt inputValue="" inputName="state">全部状态</dt>
                        <dt inputValue="0" inputName="state">未审核</dt>
                        <dt inputValue="1" inputName="state">已审核</dt>
                        <dt inputValue="-1" inputName="state">审核未通过</dt>
                    </dl>
                    <input id="state" type="hidden" value="${state}" name="state"/>
                </div>

                <!-- 会员名/手机/邮箱/攻略标题 -->
                <input type="text" placeholder="会员名/手机/邮箱/攻略标题" name="keyWord" value="${keyWord }" class="ml-10">

                <!-- 查询 -->
                <button type="button" class="btn-search" onclick="javascript:$('#listForm').submit()"><!--查询--></button>

            </div>
            <!-- } 搜索查询栏 -->
        </form>
        <!-- 数据列表 -->
        <table border="0" cellpadding="0" cellspacing="0" class="dataTable">
            <thead>
            <tr>
                <th>攻略类型</th>
                <th>会员名</th>
                <th>手机号</th>
                <th>邮箱</th>
                <th>攻略标题</th>
                <th>审核状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="dynamicDataMg" items="${pager.dataList}" varStatus="status">
                <tr>
                    <td>${dynamicDataMg.programaName }</td>
                    <td><span class="passName" style="max-width: 150px;display: block;overflow: hidden;text-overflow: ellipsis;">${dynamicDataMg.name}</span></td>
                    <td>${dynamicDataMg.mobile}</td>
                    <td>${dynamicDataMg.email}</td>
                    <td><span class="passInfo" style="display: block;overflow: hidden;text-overflow: ellipsis;max-width: 500px;">
							<c:if test="${dynamicDataMg.state==1}">
                                <a href="${ctx }/${dynamicDataMg.url}" target="_blank">${dynamicDataMg.contentTitle}</a>
                            </c:if>
							<c:if test="${dynamicDataMg.state!=1}">
                                ${dynamicDataMg.contentTitle}
                            </c:if>
							</span>
                    <td>${dynamicDataMg.stateName}</td>
                    <c:if test="${dynamicDataMg.state==1}">
                        <td>
                            <rimiTag:perm url="web/readTibetSgCheckMgController/updateUI"><a
                                    class="disable">审核</a></rimiTag:perm>
                        </td>
                    </c:if>
                    <c:if test="${dynamicDataMg.state!=1}">
                        <td><rimiTag:perm url="web/readTibetSgCheckMgController/updateUI">
                            <a href='javascript:void(0)'
                               onclick="addUI('${ctx}/web/readTibetSgCheckMgController/updateUI?contentCode=${dynamicDataMg.code }')">审核</a>
                        </rimiTag:perm>

                        </td>
                    </c:if>
                </tr>

            </c:forEach>
            <c:if test="${empty pager.dataList}">
                <tr>
                    <td colspan="7" style="text-align: center;color: red;">
                        暂无相关攻略
                    </td>
                </tr>
            </c:if>
            </tbody>
        </table>
        <div class="paging">
            <!-- 表格分页 -->
            <%@include file="/common-html/pager.jsp" %>
        </div>
    </div>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch">
<!-- IE10+ -->
<link rel="next">
<!-- Firefox -->
<link rel="prerender">
<!-- Chrome -->
</body>
</html>
