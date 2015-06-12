<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!doctype html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>系统设置-运营管理-其他页面管理</title>
    <%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="../../resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="../../resources/css/base.css"/>
    <script src="../../resources/plugin/respond.min.js" type="text/javascript"></script>
</head>

<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>系统设置</a> -</span>
        <span><a>运营管理</a> -</span>
        <span><a>其他页面管理</a> </span>
    </div>

    <!-- 数据操作 -->
    <div class="searchManage">
        <a href="otherWeb-add.html" class="btn-anchor">新建</a>
    </div>

    <!-- 搜索查询栏 { -->
    <div class="searchTools">
        <form id="paramsfrm">
            <label class="va_m">关键字:</label>
            <input type="text" name="keyword"/>

            <button onclick="loadInfoList(1)" type="button" class="btn-search"><!--查询--></button>
        </form>
    </div>
    <!-- } 搜索查询栏 -->
    <!-- 模块管理 { -->
    <div class="tableManage">
        <!-- 数据列表 -->
        <table border="0" cellpadding="0" cellspacing="0" class="dataTable">
            <thead>
            <tr>
                <th class="w-50p">标题</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ms-important="infoView" id="tbodyList">
            <tr ms-repeat="data">
                <td><a target="_blank" ms-href="<%=basePath %>portal/app/setting/other.html?code={{el.code}}">{{el.contentTitle}}</a>
                </td>
                <td>
                    <a ms-href="otherWeb-edit.html?code={{el.code}}">编辑</a>

                    <a ms-if="el.contentType=='4010'" ms-click="whetherDelTr(el.code)">删除</a>
                </td>
            </tr>
            </tbody>
        </table>

        <!-- 表格分页 -->
        <div id="infoPage" class="paging">
        </div>

    </div>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="../../resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="../../resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="../../resources/js/base.js" type="text/javascript"></script>
<script src="../../resources/js/base-tabel.js" type="text/javascript"></script>
<script src="${ctxManage}resources/js/avalon.js" type="text/javascript"></script>
<script src="${ctxManage}resources/js/bootstrap-paginator.js" type="text/javascript"></script>

<script type="text/javascript">
    function servicesPage(pageId, currentPage, totalPage, call_) {
        var options = {
            currentPage: currentPage || 1,
            totalPages: totalPage || 1,
            itemTexts: function (type, page, current) {
                switch (type) {
                    case "first":
                        return "首页";
                    case "prev":
                        return "上一页";
                    case "next":
                        return "下一页";
                    case "last":
                        return "末页";
                    case "page":
                        return page;
                }
            },
            onPageChanged: function (e, oldPage, newPage) {
                call_(newPage);
            }
        };
        $('#' + pageId).bootstrapPaginator(options);
    }

    //加载作品列表{
    var infoVM = avalon.define({
        $id: 'infoView',
        data: []

    });
    $(function () {
        avalon.scan();
    });
    function loadInfoList(currentPage) {
        var thisCall = loadInfoList;
        var url = "otherlist.html";
        // {activityCode:'${activity.code }',currentPage: currentPage, pageSize: 2}
        var params = $('#paramsfrm').serialize() + "&currentPage=" + currentPage + "&pageSize=20";
        $.post(url, params, function (response) {
            servicesPage("infoPage", response.currentPage, response.totalPage, thisCall);
            infoVM.data = response.dataList;
        }, 'json');
    }
    //}
    $(function () {
        loadInfoList(1);
        /* 延迟0.3秒为IE8表格添加隔行变色效果 */
        setTimeout("trAlternateColor('.dataTable')",300);
    });
    $(".select-base dl dt").on('click', function () {
        setTimeout(function () {
            loadInfoList(1);
        }, 10);

    });

    /**
     * 功能：数据删除提示和状态附加
     * 参数：无
     **/
    function whetherDelTr(code) {
        // 弹出层内容
        popupLayer(
                '',
                '<div class="txt-ac">是否删除该条数据？</div>',
                '<a href="otherdeletes.html?codes=' +
                code +
                '" class="btn-anchor sureDeleTr mr15" onClick="closePopup()">确定</a>' +
                '<button type="button" class="btn-sure noDeleTr cancel ml15">取消</button>'
        );
    }
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
