<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>天上社区-帖子&回复管理-待审核的帖子&回复</title>
    <style>
        /* 闭合浮动 */
        .floatfix:after {
            content: "";
            display: table;
            clear: both;
        }

        .bor_t {
            border-top: 1px solid #ccc;
        }

        .w96p {
            width: 96%;
        }

        .main ul li {
            float: none;
        }

        .main .paging ul li {
            float: right;
        }
    </style>
</head>
<body>
<%@include file="/common-html/commonxz.jsp" %>
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
        type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
        type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-tabel.js"
        type="text/javascript"></script>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>
            您当前位置为:
        </label>
        <span><a>天上社区</a> -</span>
        <span><a>帖子&回复管理</a> -</span>
				<span><a href="#" target="_self">待审核的帖子&回复</a>
				</span>
    </div>

    <!-- 数据操作 -->
    <div class="searchManage">
        <rimiTag:perm url="web/tssq/deletePost">
            <button type="button" class="btn-sure"
                    onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/tssq/deletePost','code')">
                批量删除
            </button>
        </rimiTag:perm>
    </div>

    <!-- 模块管理 { -->
    <div class="tableManage pos-rela">
        <!-- 搜索查询栏 { -->
        <div class="searchTools formLine">
            <form method="post" action="uncheckedPostList">
                <%--隐藏域用来存放类型--%>
                <input type="hidden" id="hiddenType"/>

                <!-- 关键字 -->
                <label class="w-auto">
                    关键字:
                </label>
                <input type="text" placeholder="标题、内容" name="keyWord" value="${keyWord}">

                <div class="select-base ml-20">
                    <i class="w-140">按版块筛选</i>
                    <dl>
                        <dt inputValue="" inputName="programaCode">
                            全部版块
                        </dt>
                        <c:forEach var="plate" items="${plates}">
                            <dt inputValue="${plate.code}" inputName="programaCode">
                                    ${plate.programaName}
                            </dt>
                        </c:forEach>
                    </dl>
                    <input id="programaCode" type="hidden" value=""
                           name="programaCode"/>
                </div>

                <input type="hidden" value="${type}" name="type">

                <button type="submit" class="btn-search"></button>
            </form>
        </div>
        <!-- } 搜索查询栏 -->

        <div class="txt-ac">
            <a id="postType" class="btn-base" type="button"
               href="uncheckedPostList?type=post"
               <c:if test="${type eq 'post' }">style="background:#c91212;color:#fff;"</c:if>  >帖子</a>
            <a id="replyType" class="btn-base" type="button"
               href="uncheckedPostList?type=reply"
               <c:if test="${type eq 'reply' }">style="background:#c91212;color:#fff;"</c:if> >回复</a>
        </div>

        <!-- 数据列表 -->
        <div class="formLine">
            <input type="checkbox" name="dataCheck" class="allCheck">
            <label class="w-auto">
                全选
            </label>
        </div>
        <form method="post" action="uncheckedPostList" id="listForm">
            <ul class="mt10">
                <c:forEach items="${pager.dataList}" var="post">
                    <li class="pt10 bor_t">
                        <input type="checkbox" name="dataCheck"
                               class="dataCheck float_l" value="${post.code}">

                        <div class=" disp-ib w96p ml15">
                            <div>
                                <label class="w-auto">
                                        ${post.programaname}
                                </label>
                                <span> > </span>
                                <a href="${ctx}/${post.url}" target="_blank" class="ml10">${post.contenttitle}</a>
                                <span class="ft-c_red ml10">待审核</span>

                                <div class="float_r">
                                    <rimiTag:perm url="web/tssq/passPost">
                                        <a class="btn-base_min" href="passPost?code=${post.code}&from=${type}">通过</a>
                                    </rimiTag:perm>
                                    <rimiTag:perm url="web/tssq/passPost">
                                        <a class="btn-base_min" href="returnPost?code=${post.code}&from=${type}">退回</a>
                                    </rimiTag:perm>
                                    <rimiTag:perm url="web/tssq/deletePost">
                                        <a class="btn-base_min" href="#" onclick="deletePost('${post.code}','${type}')">删除</a>
                                    </rimiTag:perm>
                                </div>
                            </div>
                            <p class="mt15 ft-c_gray f15">
                                <span>${post.name}</span>
                                <span>发表于</span>
                                <span>${post.createtime}</span>
                            </p>

                            <div class="mt15" style="word-wrap: break-word;">
                                <div class="p-info">
                                        ${post.content}
                                </div>
                                <div class="p-info-all" style="display:none;">
                                        ${post.allContent}
                                </div>
                                <a href="javascript:void(0);" target="_blank" class="float_r mt10 mb5 zhankai">展开</a>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
            <c:if test="${empty pager.dataList}">
                暂无数据
            </c:if>
            <!-- 表格分页 -->
            <div class="paging">
                <%@include file="/common-html/pager.jsp" %>
            </div>
        </form>
    </div>
    <!-- } 模块管理 -->
</div>
<!-- } main -->
<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch">
<!-- IE10+ -->
<link rel="next">
<!-- Firefox -->
<link rel="prerender">
<!-- Chrome -->
<script>
    //删除待审帖子
    function deletePost(code, type) {
        popupLayer(
                '',
                '<div style="text-align:center">是否删除该帖子</div>',
                '<div class="formLine">' +
                '<button type="button" class="btn-sure sureDelOtherMud mr20">确定</button>' +
                '<button type="button" class="btn-sure cancelDelOtherMud cancel ml20">取消</button>' +
                '</div>'
        );
        $(document).one("click", ".sureDelOtherMud", function () {
            $.ajax({
                url: "${ctx}/web/tssq/deleteSinglePost",
                dataType: "json",
                type: "post",
                data: {
                    "code": code,
                    "type": type
                },
                async: false,
                success: function (data) {
                    //.log("sdsddsds");
                    location.href = "${ctx}/web/tssq/uncheckedPostList?type=${type}";
                },
                error: function (data) {
                    //.log("sdsddsds");
                    location.href = "${ctx}/web/tssq/uncheckedPostList?type=${type}";
                }
            });
            closePopup();
        });
    }

    var tempInfo = $('.p-info').children('p');
    function strClip(ele, num) {
        var tempNode = ele.text();
        if (tempNode.length >= num) {
            ele.text(tempNode.substr(0, num - 3) + "...");
        }
    }
    for (var i = 0; i < tempInfo.length; i++) {
        strClip(tempInfo.eq(i), 110);
    }

    $(document).on('click', '.zhankai', function () {
        var $this = $(this),
        	thisTxt = $this.text();
		if(thisTxt == "展开"){
			$this.prevAll('.p-info').hide();
            $this.prevAll('.p-info-all').show();
            $this.text('收起');
		}
		if(thisTxt == "收起"){
			$this.prevAll('.p-info').show();
            $this.prevAll('.p-info-all').hide();
			$this.text('展开');
   		}
    });

    $("#postType").click(function () {
        $("#hiddenType").val("post");
    });
    $("#replyType").click(function () {
        $("#hiddenType").val("reply");
    });

    $(function () {
        var pageInfo = '<div class="paging-info">' +
                '<span class="disp-ib">当前第&nbsp;${pager.currentPage }&nbsp;页</span>' +
                '<span class="disp-ib">共&nbsp;${pager.totalPage }&nbsp;页</span>' +
                '<span class="disp-ib">共&nbsp;${pager.totalCount }&nbsp;条</span>' +
                '</div>';
        $("#pageInfo").append(pageInfo);
        //

    });

</script>
</body>
</html>
