<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>系统设置-会员管理</title>
    <%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>系统设置</a> -</span>
        <span><a>会员管理</a></span>
    </div>

    <!-- 数据操作 -->
    <div class="searchManage">
        <a href="javascript:void(0)" target="_self" class="btn-anchor"
           onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/member/delete','codes')">删除</a>
        <a href="javascript:void(0)" target="_self" class="btn-anchor" onclick="exports();">批量导出</a>
        <button type="button" class="btn-sure" onclick="addUI('${ctx}/web/member/add')">新建会员</button>
    </div>

    <!-- 模块管理 { -->
    <div class="tableManage pos-rela">
        <!-- 搜索查询栏 { -->
        <form id="listForm" method="post" action="${ctx }web/member/list">
            <div class="searchTools">
                <!-- 会员名/手机/邮箱/攻略标题 -->
                <input type="text" name="keywords" value="${keywords}" maxlength="50" id="keywords"
                       placeholder="会员名/手机/邮箱"/>

                <!-- 查询 -->
                <button type="button" class="btn-search" onclick="javascript:$('#listForm').submit()"><!--查询--></button>

                <!-- 攻略类型 -->
                <div class="select-base ml-20">
                    <i class="w-110" id="chkStatus">账号状态</i>
                    <dl>
                        <dt inputValue="" inputName="status">账号状态</dt>
                        <dt inputValue="0" inputName="status">已 停 用</dt>
                        <dt inputValue="1" inputName="status">已 启 用</dt>
                    </dl>
                    <input id="status" type="hidden" value="${status }" name="status"/>
                </div>
                <!-- 攻略类型 -->
                <div class="select-base ml-20">
                    <i class="w-110" id="chkMembertype">账号类型</i>
                    <dl>
                        <dt inputValue="" inputName="memberType">账号类型</dt>
                        <dt inputValue="1" inputName="memberType">用户注册</dt>
                        <dt inputValue="0" inputName="memberType">管理员创建</dt>
                    </dl>
                    <input id="memberType" type="hidden" value="${memberType}" name="memberType"/>
                </div>
            </div>
            <!-- } 搜索查询栏 -->
        </form>
        <!-- 数据列表 -->
        <table border="0" cellpadding="0" cellspacing="0" class="dataTable">
            <thead>
            <tr>
                <th class="w-120">
                    <input type="checkbox" id="allChk" name="dataCheck" class="allCheck mr5" onchange="checkAll(this);">
                    全选
                </th>
                <th>注册时间</th>
                <th>昵称</th>
                <th>账号类型</th>
                <th>邮箱</th>
                <th>手机号</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="mem" items="${pager.dataList}" varStatus="status">
                <tr>
                    <td>
                        <input type="checkbox" name="dataCheck" class="dataCheck" onchange="cancelAll();" value="${mem.code}">

                    </td>
                    <td><fmt:formatDate value="${mem.createTime}" pattern="yyyy-MM-dd"/></td>
                    <td><a href="${ctx}/web/member/detail?code=${mem.code}">${mem.name}</a></td>
                    <td><c:if test="${mem.memberType eq '1'}">用户注册</c:if><c:if
                            test="${mem.memberType eq '0'}">管理员创建</c:if></td>
                    <td><a href="${ctx}/web/member/detail?code=${mem.code}"><c:if test="${mem.isBind eq '1'}"> ${mem.email}</c:if></a></td>
                    <td><a href="${ctx}/web/member/detail?code=${mem.code}"><c:if test="${mem.isVerified eq '1'}">${mem.mobile}</c:if></a></td>
                    <td>
                        <span class="maxW340"><c:if
                                test="${mem.status eq '1'}">已启用<a href="javascript:void(0);" onclick="changeState('limit','${mem.code }');" class="ml5">停用</a></c:if><c:if
                                test="${mem.status eq '0'}">已停用<a href="javascript:void(0);" onclick="changeState('wakeup','${mem.code }');" class="ml5">启用</a></c:if></span>
                    </td>

                    <td>
                        <a href="javascript:void(0)"
                           onclick="update('${ctx}/web/member/edit','code=${mem.code}')">修改</a>
                        <a href="javascript:void(0)"
                           onclick="deletebySingle('${mem.code}','${ctx}/web/member/deleteSingle','code')">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${not empty pager.dataList}">
            <div class="paging">
                <%@include file="/common-html/pager.jsp" %>
            </div>
        </c:if>
        <c:if test="${empty pager.dataList}">
            <div style="text-align: center;font-size: large;font-weight: bold;margin-top: 10px">
                <span>暂无数据</span>
            </div>
        </c:if>
    </div>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->


<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
<script type="text/javascript">
    window.onload = function () {
        if (${saveflag=='1'}) {
            msgBox("保存成功！", "pass", 2600);
        }
        if (${saveflag=='0'}) {
            msgBox("操作失败！", "erro", 2600);
        }
        if (${updateflag=='1'}) {
            msgBox("修改成功！", "pass", 2600);
        }
        if (${updateflag=='0'}) {
            msgBox("操作失败！", "erro", 2600);
        }
        if (${delflag=='1'}) {
            msgBox("删除成功！", "pass", 2600);
        }
        if (${delflag=='0'}) {
            msgBox("删除失败！", "erro", 2600);
        }
        if (${codeEmpty=='1'}) {
            msgBox("ID不能为空！", "erro", 2600);
        }
        if (${wakeup=='1'}) {
            msgBox("启用成功！", "pass", 2600);
        }
        if (${wakeup=='0'}) {
            msgBox("启用失败！", "erro", 2600);
        }
        if (${limit=='1'}) {
            msgBox("停用成功！", "pass", 2600);
        }
        if (${limit=='0'}) {
            msgBox("停用失败！", "erro", 2600);
        }
        if (${status!=''}) {
            if (${status =='0'}) {
                $('#chkStatus').text('已 停 用');
            }
            if (${status =='1'}) {
                $('#chkStatus').text('已 启 用');
            }
        }
        if (${memberType!=''}) {
            if (${memberType=='0'}) {
                $('#chkMembertype').text('管理员创建');
            }
            if (${memberType=='1'}) {
                $('#chkMembertype').text('用户注册');
            }
        }
    }
    function checkAll(obj) {
        if (obj.checked) {
            var chk = document.getElementsByName("dataCheck");
            for (var i = 0; i < chk.length; i++) {
                chk[i].checked = true;
            }
        } else {
            var chk = document.getElementsByName("dataCheck");
            for (var i = 0; i < chk.length; i++) {
                chk[i].checked = false;
            }
        }
    }
   function	cancelAll(){
     var chk=$(".dataCheck");
   
     for(var i=0;i<chk.length;i++){
        if(!chk[i].checked){
            $("#allChk").prop("checked",false);//.attr("checked","false");
            break;
        }else{
          $("input[type='checkbox'].allCheck").prop("checked","true");
        }
     }
   }
    function changeState(obj, code) {
        if (code == '') {
            msgBox("ID不能为空！", "erro", 2600);
        }
        var status = $('#status').val();
        var memberType = $('#memberType').val();
        var keywords = $('#keywords').val();
        if (obj == 'wakeup') {
            var cf = window.confirm("确定启用该用户？");
            if (cf) {
                location.href = '${ctx}/web/member/changeState?type=wakeup&code=' + code + '&keywords=' + keywords + '&status=' + status + '&memberType=' + memberType + '&currentPage=' +${pager.currentPage};
            }
        }
        if (obj == 'limit') {
            var cf = window.confirm("确定停用该用户？(禁用后该用户将无法登陆)");
            if (cf) {
                location.href = '${ctx}/web/member/changeState?type=limit&code=' + code + '&keywords=' + keywords + '&status=' + status + '&memberType=' + memberType + '&currentPage=' +${pager.currentPage};
            }
        }
    }
    function exports() {
        var codes = $("input[name='dataCheck']:checked");
        //判断是否有复选框选中导出
        if (codes.length == 0) {
            var msg = '确定导出列表数据？';
            confirm_custom(msg, function my() {
                var keywords = $('#keywords').val();
                var memberType = $('#memberType').val();
                var status = $('#status').val();
                $.ajax({
                    type: "post",
                    url: "${ctx}/web/member/writeexcel",
                    data: {keywords: keywords, memberType: memberType, status: status, type: 'bylis'},
                    dataType: "json",
                    async: true,
                    success: function (data) {
                        if (data == '1') {
                            location.href = '${ctx}/web/member/export';
                        }
                    }
                })
            })
        } else {//导出复选框选中数据
            var msg = '确定导出选中的数据？';
            confirm_custom(msg, function my() {
                var codesString = new Array();// code字符串
                $(codes).each(
                        function (i) {
                            codesString.push($(this).val());
                        }
                );

                $.ajax({
                    type: "post",
                    traditional: true,
                    url: "${ctx}/web/member/writeexcel",
                    data: {"codes": codesString, "type": 'bychk'},
                    dataType: "json",
                    async: true,
                    success: function (data) {
                        if (data == '1') {
                            location.href = '${ctx}/web/member/export';
                        }
                    }
                })
            });
        }
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
