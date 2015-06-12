<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>系统设置-数据统计-销售数据统计</title>
    <%@ include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/datepicker.css"/>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
</head>

<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>系统设置</a> -</span>
        <span><a>数据统计</a> -</span>
        <span><a>销售数据统计（外链）</a></span>
    </div>

    <!-- 模块管理 { -->
    <div class="tableManage pos-rela">
        <!-- 搜索查询栏 { -->
        <div class="searchTools">
            <form id="listForm" method="post" action="${ctx }/web/frontContentStatisController/intoFrontMerchantStatis"
                  class="disp-b">
                <label class="va_m">开始时间:</label>
                <input id="startDate" name="start" value="${start }" type="text" class="w-120">
                <label class="va_m ml10">结束时间:</label>
                <input id="endDate" name="end" value="${end}" type="text" class="w-120">

                <label class="va_m ml10">商户类型:</label>
                <div class="select-base ml10" style="display: inline-block">
                    <i class="w-140">${ proTypeText}</i>
                    <dl>
                        <dt inputValue="" inputName="proType" inputTextValue="全部类型" inputTextName="proTypeText">全部类型
                        </dt>
                        <c:forEach var="bs" items="${baselist}">
                            <dt inputValue="${bs.code}" inputName="proType" inputTextValue="${bs.name }"
                                inputTextName="proTypeText">${bs.name}</dt>
                        </c:forEach>
                    </dl>
                    <input id="proType" type="hidden" value="${proType}" name="proType"/>
                    <input id="proTypeText" type="hidden" value="${proTypeText }" name="proTypeText"/>
                </div>

                <label class="va_m ml10">商户名称:</label>
                <input type="text" value="${merchantName}" name="merchantName" class="w-120" placeholder="请输入商户名称"/>

                <!-- 查询 -->
                <button type="button" class="btn-search" onclick="javascript:$('#listForm').submit()">
                    <!--查询-->
                </button>
            </form>
        </div>
        <!-- } 搜索查询栏 -->

        <!-- 精确搜索详情显示 { -->
        <div class="filament_solid_ddd pos-rela mt20 mb20">
            <div class="formGroup detail">

                <span class="dataDetail number">${start }</span>
                <label class="va_m">至</label>
                <span class="dataDetail number">${end }</span>
                <label class="va_m">内</label>

                <span class="dataDetail">${ proTypeText}</span>

                <label class="va_m ml20">查看:</label>
                <span class="dataDetail number">${contentVo.clickCount }</span>

                <label class="va_m ml20">点击外链:</label>
                <span class="dataDetail number">${contentVo.hrefCount }</span>

                <label class="va_m ml20">收藏:</label>
                <span class="dataDetail number">${contentVo.favateCount }</span>

                <label class="va_m ml20">评论:</label>
                <span class="dataDetail number">${contentVo.replyCount }</span>

            </div>
        </div>
        <!-- } 精确搜索详情显示 -->


        <!-- 数据列表 -->
        <table border="0" cellpadding="0" cellspacing="0" class="dataTable">
            <thead>
            <tr>
                <th class="w-180">统计开始日期</th>
                <th class="w-180">统计结束日期</th>
                <th>商户类型</th>
                <th>商户名称</th>
                <th>查看数</th>
                <th>点击外链数</th>
                <th>收藏数</th>
                <th>评论数</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${result.dataList}" var="ob">
                <tr>
                    <td>${start }</td>
                    <td>${end }</td>
                    <td>${ob.proName }</td>
                    <td>
                        <a href="${ctx }/${ob.url}" target="_blank">
                            <span class="maxW340">${ob.merchantName }</span>
                        </a>
                    </td>
                    <td>${ob.clickCount }</td>
                    <td>${ob.hrefCount }</td>
                    <td>${ob.favateCount }</td>
                    <td>${ob.replyCount }</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- 表格分页 -->
        <div class="paging">
            <%@include file="/common-html/pager.jsp" %>
        </div>


    </div>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->

<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-form.js"></script>
<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
<script src="${ctxManage}/resources/plugin/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
    // 运行时间选择控件
    datePicker("startDate", "endDate");
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
