<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE HTML >
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>读西藏-西藏文化传播信息管理</title>
    <%@include file="/common-html/common.jsp" %>
    <script src="../../resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="../../resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="../../resources/css/base.css"/>
    <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>读西藏</a> -</span>
        <span><a href="#" target="_self">西藏文化传播信息管理</a></span>
    </div>
    <!-- 数据操作 -->
    <div class="searchManage">
        <rimiTag:perm url="cultural-info-creat.html">
            <a href="cultural-info-creat.html" target="_self" class="btn-anchor">新建</a>
        </rimiTag:perm>
        <button id="deletes" type="button" class="btn-sure">批量删除</button>
    </div>

    <!-- 模块管理 { -->
    <div class="tableManage pos-rela">
        <form id="paramsfrm">
            <!-- 搜索查询栏 { -->
            <div class="searchTools">

                <!-- 关键字 -->
                <label>关键字:</label>
                <input id="keyshow"  type="text" placeholder="编号/标题">
				<input id="keyhide" name="keyword" type="hidden" >	
                <!-- 查询 -->
                <button type="button" class="btn-search" id="btnsearch"><!--查询--></button>

                <!-- 快捷查询 { -->
                <div class="shortcutSearch">
                    <label class="va_m">类型:</label>
                    <!-- 包含模块 -->
                    <div class="select-base">
                        <input name="contentType" type="hidden" value="2000">
                        <i class="w-110">全部</i>
                        <dl>
                            <dt name="2000">全部</dt>
                            <dt name="2010">音乐</dt>
                            <dt name="2020">小说</dt>
                            <dt name="2030">游戏</dt>
                            <dt name="2040">其他</dt>
                        </dl>
                    </div>
                    <!-- 快捷筛选 -->
                    <div class="select-base ml-10">
                        <input name="orderType" type="hidden" value="100">
                        <i class="w-140">快捷筛选</i>
                        <dl>
                            <dt name="100">按时间</dt>
                            <dt name="201">按查看量</dt>
                            <dt name="202">按评论量</dt>
                            <dt name="203">按被赞次数</dt>
                            <dt name="204">按收藏次数</dt>
                            <dt name="205">按评分</dt>
                        </dl>
                    </div>
                </div>
                <!-- } 快捷查询 -->
            </div>
            <!-- } 搜索查询栏 -->
        </form>
        <form id="deletefrm" action="culturedelete" method="post">
            <!-- 数据列表 -->
            <table border="0" cellpadding="0" cellspacing="0" class="dataTable">
                <thead>
                <tr>
                    <th>
                        <input type="checkbox" name="dataCheck" class="allCheck">
                        全选
                    </th>
                    <th>创建时间</th>
                    <th>编号</th>
                    <th>类型</th>
                    <th>名称</th>
                    <th>作者</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody ms-important="infoView" id="tbodyList">
				 <tr ms-if="!data.size()">
                	<td></td>
                	<td></td>
                	<td>
                	<p style="color: red;text-align: center;">暂无相关搜索数据</p>
                	<td>
                	<td></td>
                	<td></td><td></td>
                </tr>
                <tr ms-repeat="data">
                    <td>
                        <input type="checkbox" name="codes" ms-attr-value="{{el.code}}"
                               class="dataCheck">
                    </td>
                    <td>{{el.createTime | date('yyyy-MM-dd HH:mm')}}</td>
                    <td>{{el.code}}</td>
                    <td>{{el.typeName }}</td>
                    <td>
                        <div ms-if="el.isOfficial==0" class="tuijian">推荐</div>
                        <a ms-href="${ctx}culture/culture_detail/{{el.code}}.html" target="_new">
            					<span class="maxW460">
            						{{el.contentTitle}}
            					</span>
                        </a>
                    </td>
                    <td>{{el.authorCode}}</td>
                    <td>
                        <a ms-href="${ctx}culture/culture_detail/{{el.code}}.html" target="_new">前台查看</a>
                        <rimiTag:perm url="cultural-info-edit.html">
                            <a ms-href="cultural-info-edit.html?code={{el.code}}">编辑</a>
                        </rimiTag:perm>
                        <rimiTag:perm url="culturedelete">
                            <a href="javascript:void(0);" ms-click="deleteView(el.code)">删除</a>
                        </rimiTag:perm>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
        <!-- 表格分页 -->
        <div id="paging">
            <!--             <button type="button" class="btn-base_min">首页</button> -->
            <!--             <button type="button" class="btn-base_min">上一页</button> -->
            <!--             <button type="button" class="btn-base_min">下一页</button> -->
            <!--             <button type="button" class="btn-base_min">末页</button> -->
        </div>

    </div>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->
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
        data: [],
        deleteView: function (code) {
            _deleteView(code)
        }
    });
    avalon.ready(function () {
        avalon.scan();
    });
    $("#btnsearch").click(function(){
    	$("#keyhide").val($("#keyshow").val());
    	loadInfoList(1);
    });
    function loadInfoList(currentPage) {
        var thisCall = loadInfoList;
        var url = "infosearch.action";
        // {activityCode:'${activity.code }',currentPage: currentPage, pageSize: 2}
        var params = $('#paramsfrm').serialize() + "&currentPage=" + currentPage + "&pageSize=20";
        $.post(url, params, function (response) {
            servicesPage("paging", response.currentPage, response.totalPage, thisCall);
            infoVM.data = response.dataList;
        }, 'json');
    }
    //}

    $(function () {
        loadInfoList(1);
        /* 延迟0.3秒为IE8表格添加隔行变色效果 */
        setTimeout("trAlternateColor('.dataTable')",600);
    });
    $(".select-base dl dt").on('click', function () {
        setTimeout(function () {
            loadInfoList(1);
        }, 10);
    });

    function deletes() {
        var temp = $("#tbodyList tr td").children('input:checked');
        temp.prop("checked", true);
        $("#deletefrm").submit();
    }
</script>
<script>
    //======================================
    //				删除类别
    //======================================
    // 弹出准备删除框
    $(document).on("click", ".dataTable .dele", function () {
        deleteView(code);
    });
    // 删除
    function _deleteView(code) {
        popupLayer(
                '',
                '<div style="width: 320px; text-align:center; margin: 0 auto;">您确定要删除？</div>',
                '<button type="button" class="btn-sure sureDele mr15">确定</button>' +
                '<button type="button" class="btn-sure noDele cancel ml15">取消</button>'
        );
        $(document).one('click', '.sureDele', function () {
            var url = "culturedelete?codes=" + code;
            window.location.href = url;
            closePopup();
        });
    }
    // 取消删除
    $(document).on("click", ".noDele", function () {
        var deleTr = $(".dataTable").find("tr.dele-waiting");
        // 消除删除状态
        deleTr.removeClass("dele-waiting");
    });

    // 批量确定删除
    $(document).on("click", ".confirm", function () {
        deletes();
        closePopup();
    });
    $("#deletes").on('click', function () {
        var trChecked = $('.dataTable').find("tr.trChecked");
        if (trChecked.length) {
            popupLayer(
                    '',
                    '<div style="width: 320px; text-align:center; margin: 0 auto;">您确定要删除？</div>',
                    '<button type="button" class="btn-sure confirm mr15">确定</button>' +
                    '<button type="button" class="btn-sure cancel ml15">取消</button>'
            );
        } else {
            msgBox("请勾选需要删除的板块", "info", 1000);
        }
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

