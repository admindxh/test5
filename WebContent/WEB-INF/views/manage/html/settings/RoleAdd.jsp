<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%
    String path = request.getContextPath();
    String port = ":" + request.getServerPort();
    if ("80".equals("" + request.getServerPort())) {
        port = "";
    }
    String basePath = request.getScheme() + "://"
            + request.getServerName() + port + path + "/";
    request.setAttribute("ctx", basePath);
    request.setAttribute("ctxManage", basePath + "/manage/");
    request.setAttribute("ctxPortal", basePath + "portal/"); //xiangwq
%>
<!doctype html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta class="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>系统设置-角色权限管理-角色管理-新建角色</title>
    <link rel="stylesheet"
          href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet"
          href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <link rel="stylesheet"
          href="${ctxManage}/resources/css/settings/role.css"/>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
            type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/respond.min.js"
            type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
    <script src="${ctxManage}/resources/js/dataValidation.js"
            type="text/javascript"></script>
    <script type="text/javascript">

        $(function () {
            var roleAccessString = "${accessString}";
            var listRoleString = $("input[name='value_text']");
            $(listRoleString).each(function (i) {
                var url = $(this).val();
                url = url.split('_');
                url = url[0];
                var exit = roleAccessString.indexOf($(this).val());
                if (exit != -1) {
                    $(this).next(".custom_ckb").removeClass("none part").addClass("checked");
                    $(this).attr('checked', 'true');
                }
            })
        })


    </script>
</head>

<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>
            您当前位置为:
        </label>
        <span><a>系统设置</a> -</span>
        <span><a>角色权限管理</a> -</span>
        <span><a href="${ctx}/web/role/roleList" target="_self">角色管理</a> -</span>
                <span><a>
                    <c:choose>
                        <c:when test="${not empty role.id}">
                            编辑角色
                        </c:when>
                        <c:otherwise>
                            新建角色
                        </c:otherwise>
                    </c:choose>
                </a></span>
    </div>
    <form method="post" action="saveOrUpdateRole" id="myForm">
        <!-- 模块管理 { -->
        <div class="mt20">
            <!-- 角色名 -->
            <div class="formLine">
                <label class="w-auto">
                    角色名:
                </label>
                <input type="hidden" class="w-260" name="code" id="code" value="${role.code}">
                <input type="hidden" class="w-260" name="id" value="${role.id}">
                <input id="roleName" type="text" class="w-260" name="name" value="${role.name}">
                <span class="reqItem">*</span>
            </div>

            <!-- 角色权限 { -->
            <div class="role-authority">
                <div class="formLine">
                    <label class="w-auto">
                        角色权限:
                    </label>
                </div>

                <table border="1" cellpadding="0" cellspacing="0"
                       class="role-authority-set">
                    <!-- 门户首页 { -->
                    <tr class="split-line">
                        <td>
                            <div class="auth_item w-auto">
                                <input class="home" type="checkbox" name="value_text"
                                       value="web/index/intoPage?path=manage/html/home/home_门户首页">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    门户首页
                                </label>
                            </div>

                        </td>
                        <td>
                            <div class="auth_item w-auto">
                                <input class="home-manage" type="checkbox" name="value_text"
                                       value="home/home.html_门户首页管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    门户首页管理
                                </label>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="auth_item">
                                <input class="home-manage-set" type="checkbox"
                                       name="value_text"
                                       value="web/homeController/getManageImg?programaCode=e43cb722-75d6-11e4-b6ce-005056a05bc9_首页banner管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    首页-Banner管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="home-manage-set" type="checkbox"
                                       name="value_text"
                                       value="web/indexXzDtManagerController/saveUI_首页-西藏动态列表管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    首页-西藏动态列表管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="home-manage-set" type="checkbox"
                                       name="value_text"
                                       value="web/indexGlDtManagerController/saveUI_首页-游西藏攻略管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    首页-游西藏攻略管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="home-manage-set" type="checkbox"
                                       name="value_text"
                                       value="web/homeController/getManageImg?programaCode=1323f0e2-75da-11e4-b6ce-005056a05bc9_首页-图说西藏管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    首页-图说西藏管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="home-manage-set" type="checkbox"
                                       name="value_text"
                                       value="web/homeController/getManageImg?programaCode=132a2285-75da-11e4-b6ce-005056a05bc9_首页-读西藏管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    首页-读西藏管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="home-manage-set" type="checkbox"
                                       name="value_text"
                                       value="web/homeController/getManageImg?programaCode=13334a3a-75da-11e4-b6ce-005056a05bc9_首页-浮窗管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    首页-浮窗管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="home-manage-set" type="checkbox"
                                       name="value_text"
                                       value="web/homeController/getManageImg?programaCode=13173f79-75da-11e4-b6ce-005056a05bc9_首页-推荐位管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    首页-推荐位管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="home-manage-set" type="checkbox"
                                       name="value_text"
                                       value="web/indexDzDtManagerController/saveUI_首页-帖子列表管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    首页-帖子列表管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="home-manage-set" type="checkbox"
                                       name="value_text"
                                       value="web/homeController/getManageImg?programaCode=1320eb90-75da-11e4-b6ce-005056a05bc9_首页-景点管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    首页-景点管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="home-manage-set" type="checkbox"
                                       name="value_text"
                                       value="web/indexSpDtManagerController/saveUI_首页-视频专区管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    首页-视频专区管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="home-manage-set" type="checkbox"
                                       name="value_text"
                                       value="web/indexWhMgController/saveUI_首页-西藏文化传播管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    首页-西藏文化传播管理
                                </label>
                            </div>
                        </td>
                    </tr>
                    <!-- } 门户首页 -->
                    <!-- 游西藏 { -->
                    <tr>
                        <td class="split-line" rowspan="9">
                            <div class="auth_item w-auto">
                                <input class="travel" type="checkbox" name="value_text"
                                       value="web/index/intoPage?path=manage/html/travel/travel_游西藏">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    游西藏
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item w-auto">
                                <input class="travel-home" type="checkbox" name="value_text"
                                       value="default/travel/manage_游西藏首页显示">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    游西藏首页显示
                                </label>
                            </div>
                        </td>
                        <td colspan="2">
                            <%--									<div class="auth_item">--%>
                            <%--										<input class="travel-home-set" type="checkbox"--%>
                            <%--											name="value_text" value="default/travel/manage_游西藏首页显示">--%>
                            <%--										<i class="custom_ckb none"></i>--%>
                            <%--										<label>--%>
                            <%--											游西藏首页显示--%>
                            <%--										</label>--%>
                            <%--									</div>--%>

                            <div class="auth_item">
                                <input class="travel-home-set" type="checkbox"
                                       name="value_text"
                                       value="web/travelController/getManageImg?programaCode=bc22ed19-b2bc-42cd-a8c9-beb96e25ed89_推荐位管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    推荐位管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-home-set" type="checkbox"
                                       name="value_text"
                                       value="web/readTibetZhMgController/list_综合攻略管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    综合攻略管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-home-set" type="checkbox"
                                       name="value_text"
                                       value="web/readTibetZjyMgController/list_自驾游攻略管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    自驾游攻略管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-home-set" type="checkbox"
                                       name="value_text"
                                       value="web/travelController/getManageImg?programaCode=1256se5-qe2c-52e4-a6ce-11505ca05dc9_Banner管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    Banner管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-home-set" type="checkbox"
                                       name="value_text"
                                       value="web/travelController/getManageImg?programaCode=25b327a5-7e8c-12e4-b6ce-005056b896a3_热门景点管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    热门景点管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-home-set" type="checkbox"
                                       name="value_text"
                                       value="web/readTibetQxMgController/list_骑行攻略管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    骑行攻略管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-home-set" type="checkbox"
                                       name="value_text"
                                       value="web/merchant/gotoMerchantManage_商户管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    商户管理
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="2">
                            <div class="auth_item w-auto">
                                <input class="travel-strategy" type="checkbox"
                                       name="value_text" value="default/strategy/manage_攻略管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    攻略管理
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="travel-strategy-noPass" type="checkbox"
                                       name="value_text"
                                       value="web/readTibetSgCheckMgController/list_待审核的攻略">
                                <i class="custom_ckb none"></i>
                                <label>
                                    待审核的攻略
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="travel-strategy-noPass-set" type="checkbox"
                                       name="value_text"
                                       value="web/readTibetSgCheckMgController/updateUI_攻略审核">
                                <i class="custom_ckb none"></i>
                                <label>
                                    攻略审核
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item">
                                <input class="travel-strategy-pass" type="checkbox"
                                       name="value_text"
                                       value="web/readTibetSgPassMgController/list_已审核的攻略">
                                <i class="custom_ckb none"></i>
                                <label>
                                    已审核的攻略
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="travel-strategy-pass-set" type="checkbox"
                                       name="value_text"
                                       value="web/readTibetSgPassMgController/saveUI_新建攻略">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建攻略
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-strategy-pass-set" type="checkbox"
                                       name="value_text"
                                       value="web/readTibetSgPassMgController/updateUI_编辑攻略">
                                <i class="custom_ckb none"></i>
                                <label>
                                    编辑攻略
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-strategy-pass-set" type="checkbox"
                                       name="value_text"
                                       value="web/readTibetSgPassMgController/delete_删除攻略">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除攻略
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="2">
                            <div class="auth_item w-auto">
                                <input class="travel-dest" type="checkbox" name="value_text"
                                       value="default/destination/manage_目的地管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    目的地管理
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="travel-dest-info" type="checkbox"
                                       name="value_text"
                                       value="web/destinationController/getDestinationPager_目的地信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    目的地信息管理
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="travel-dest-info-set" type="checkbox"
                                       name="value_text"
                                       value="web/destinationController/forDestinationAdd_新建目的地">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建目的地
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-dest-info-set" type="checkbox"
                                       name="value_text"
                                       value="web/destinationController/destinationEdit_编辑目的地">
                                <i class="custom_ckb none"></i>
                                <label>
                                    编辑目的地
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-dest-info-set" type="checkbox"
                                       name="value_text"
                                       value="web/destinationController/deleteDestination_删除目的地">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除目的地
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-dest-info-set" type="checkbox"
                                       name="value_text"
                                       value="web/destinationController/imageManaege_目的地图集管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    目的地图集管理
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item">
                                <input class="travel-dest-scenInfo" type="checkbox"
                                       name="value_text"
                                       value="web/viewController/getViewPager_景点信息管理">
                                <input class="travel-dest-scenInfo" type="checkbox"
                                       name="value_text" value="web/viewController/listView_景点信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    景点信息管理
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="travel-dest-scenInfo-set" type="checkbox"
                                       name="value_text" value="web/viewController/viewEdit_新建景点">
                                <input class="travel-dest-scenInfo-set" type="checkbox"
                                       name="value_text" value="web/viewController/forAddView_新建景点">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建景点
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-dest-scenInfo-set" type="checkbox"
                                       name="value_text" value="web/viewController/viewEdit_编辑景点">
                                <input class="travel-dest-scenInfo-set" type="checkbox"
                                       name="value_text" value="web/viewController/forEditView_编辑景点">
                                <i class="custom_ckb none"></i>
                                <label>
                                    编辑景点
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-dest-scenInfo-set" type="checkbox"
                                       name="value_text" value="web/viewController/deleteView_删除景点">
                                <input class="travel-dest-scenInfo-set" type="checkbox"
                                       name="value_text" value="web/viewController/deleteViews_删除景点">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除景点
                                </label>
                            </div>
                            <%--           %%%    null   ****  _____                           --%>
                            <div class="auth_item">
                                <input class="travel-dest-scenInfo-set" type="checkbox"
                                       name="value_text"
                                       value="web/viewController/forViewPictureManage_景点图集管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    景点图集管理
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="split-line" rowspan="4">
                            <div class="auth_item w-auto">
                                <input class="travel-commer" type="checkbox" type="checkbox"
                                       name="value_text" value="default/merchant/manage_商户管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    商户管理
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="travel-commer-show" type="checkbox"
                                       name="value_text"
                                       value="web/merchant/banner_商户汇总页显示">
                                <i class="custom_ckb none"></i>
                                <label>
                                    商户汇总页显示
                                </label>
                            </div>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item">
                                <input class="travel-commer-category" type="checkbox"
                                       name="value_text"
                                       value="web/merchant/getMerchantTypeList_商户类别管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    商户类别管理
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="travel-commer-category-set" type="checkbox"
                                       name="value_text"
                                       value="web/merchant/saveOrUpdateMerchantType_新建商户类别">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建商户类别
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-commer-category-set" type="checkbox"
                                       name="value_text"
                                       value="web/merchant/deleteMerchantType_删除商户类别">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除商户类别
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item">
                                <input class="travel-commer-info" type="checkbox"
                                       name="value_text" value="web/merchant/merchantList_商户信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    商户信息管理
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="travel-commer-info-set" type="checkbox"
                                       name="value_text"
                                       value="web/merchant/intoMerchantAddUI_新建商户信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建商户信息
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-commer-info-set" type="checkbox"
                                       name="value_text"
                                       value="web/merchant/intoMerchantUpdateUI_编辑商户信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    编辑商户信息
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-commer-info-set" type="checkbox"
                                       name="value_text" value="web/merchant/deleteMerchant_删除商户信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除商户信息
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr class="split-line">
                        <td>
                            <div class="auth_item">
                                <input class="travel-commer-cluster" type="checkbox"
                                       name="value_text"
                                       value="web/groupTravel/searchGroupTravel_团游信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    团游信息管理
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="travel-commer-cluster-set" type="checkbox"
                                       name="value_text"
                                       value="web/groupTravel/gotoSaveOrUpdateGroupTravel_新建团游信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建团游信息
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-commer-cluster-set" type="checkbox"
                                       name="value_text"
                                       value="web/groupTravel/gotoSaveOrUpdateGroupTravel_编辑团游信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    编辑团游信息
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="travel-commer-cluster-set" type="checkbox"
                                       name="value_text"
                                       value="web/groupTravel/deleteGroupTravel_删除团游信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除团游信息
                                </label>
                            </div>
                        </td>
                    </tr>
                    <!-- } 游西藏 -->
                    <!-- 读西藏 { -->
                    <tr>
                        <td class="split-line" rowspan="3">
                            <div class="auth_item w-auto">
                                <input class="read" type="checkbox" name="value_text"
                                       value="read/read.html_读西藏">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    读西藏
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item w-auto">
                                <input class="read-home" type="checkbox" name="value_text"
                                       value="read/read.html_读西藏首页显示">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    读西藏首页显示
                                </label>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="auth_item">
                                <input class="read-home-set" type="checkbox" name="value_text"
                                       value="read/banner.html_Banner管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    Banner管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="read-home-set" type="checkbox" name="value_text"
                                       value="read/posid.html_读西藏-推荐位管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    读西藏-推荐位管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="read-home-set" type="checkbox" name="value_text"
                                       value="read/dynamicList.html_西藏动态显示管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    西藏动态显示管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="read-home-set" type="checkbox" name="value_text"
                                       value="read/infoDisplay.html_读西藏信息显示管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    读西藏信息显示管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="read-home-set" type="checkbox" name="value_text"
                                       value="read/cultural.html_西藏文化传播显示管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    西藏文化传播显示管理
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item w-auto">
                                <input class="read-info" type="checkbox" name="value_text"
                                       value="read/infoManage.html_读西藏信息管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    读西藏信息管理
                                </label>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="auth_item">
                                <input class="read-info-set" type="checkbox" name="value_text"
                                       value="read/info-creat.html_新建读西藏信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建读西藏信息
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="read-info-set" type="checkbox" name="value_text"
                                       value="info-edit.html_编辑读西藏信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    编辑读西藏信息
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="read-info-set" type="checkbox" name="value_text"
                                       value="deletes.html_删除读西藏信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除读西藏信息
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr class="split-line">
                        <td>
                            <div class="auth_item">
                                <input class="read-culture" type="checkbox" name="value_text"
                                       value="read/cultural-trans.html_西藏文化传播信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    西藏文化传播信息管理
                                </label>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="auth_item">
                                <input class="read-culture-set" type="checkbox"
                                       name="value_text" value="cultural-info-creat.html_新建西藏文化传播信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建西藏文化传播信息
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="read-culture-set" type="checkbox"
                                       name="value_text" value="cultural-info-edit.html_编辑西藏文化传播信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    编辑西藏文化传播信息
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="read-culture-set" type="checkbox"
                                       name="value_text" value="culturedelete_删除西藏文化传播信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除西藏文化传播信息
                                </label>
                            </div>
                        </td>
                    </tr>
                    <!-- } 读西藏 -->
                    <!-- 看西藏 { -->
                    <tr>
                        <td class="split-line" rowspan="3">
                            <div class="auth_item w-auto">
                                <input class="see" type="checkbox" type="checkbox"
                                       name="value_text" value="see/see.html_看西藏">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    看西藏
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item w-auto">
                                <input class="see-home" type="checkbox" name="value_text"
                                       value="see/see.html_看西藏首页显示">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    看西藏首页显示
                                </label>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="auth_item">
                                <input class="see-home-set" type="checkbox" name="value_text"
                                       value="web/mutiController/banner_Banner管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    Banner管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="see-home-set" type="checkbox" name="value_text"
                                       value="web/mutiController/showMuti_图说西藏显示管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    图说西藏显示管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="see-home-set" type="checkbox" name="value_text"
                                       value="see/video-display.html_视频显示管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    视频显示管理
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item w-auto">
                                <input class="see-info" type="checkbox" name="value_text"
                                       value="web/mutiController/getMutiList_图说西藏信息管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    图说西藏信息管理
                                </label>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="auth_item">
                                <input class="see-info-set" type="checkbox" name="value_text"
                                       value="web/mutiController/mutiEdit_新建西藏信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建西藏信息管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="see-info-set" type="checkbox" name="value_text"
                                       value="web/mutiController/mutiEdit_编辑西藏信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    编辑西藏信息管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="see-info-set" type="checkbox" name="value_text"
                                       value="web/mutiController/deleteMuti_删除西藏信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除西藏信息管理
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr class="split-line">
                        <td>
                            <div class="auth_item">
                                <input class="see-video" type="checkbox" name="value_text"
                                       value="see/video-infomessage.html_视频专区信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    视频专区信息管理
                                </label>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="auth_item">
                                <input class="see-video-set" type="checkbox" name="value_text"
                                       value="video-creat.html_新建视频专区信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建视频专区信息管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="see-video-set" type="checkbox" name="value_text"
                                       value="video-edit.html_编辑视频专区信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    编辑视频专区信息管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="see-video-set" type="checkbox" name="value_text"
                                       value="deletes_删除视频专区信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除视频专区信息管理
                                </label>
                            </div>
                        </td>
                    </tr>
                    <!-- } 看西藏 -->
                    <!-- 活动&专题 { -->
                    <tr>
                        <td class="split-line" rowspan="3">
                            <div class="auth_item w-auto">
                                <input class="activity" type="checkbox" name="value_text"
                                       value="web/activityBannerManageController/forActivityManage_活动&专题">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    活动&专题
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item w-auto">
                                <input class="activity-home" type="checkbox" name="value_text"
                                       value="web/activityBannerManageController/forActivityManage_活动&专题首页显示">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    活动&专题首页显示
                                </label>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="auth_item">
                                <input class="activity-home-set" type="checkbox"
                                       name="value_text"
                                       value="web/activityBannerManageController/forActivityBannerManage_Banner管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    Banner管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="activity-home-set" type="checkbox"
                                       name="value_text"
                                       value="web/activityBannerManageController/forSpecailShowManage_专题显示管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    专题显示管理
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="split-line" rowspan="2">
                            <div class="auth_item w-auto">
                                <input class="activity-info" type="checkbox" name="value_text"
                                       value="default/activity&theme/infoManage_活动&专题信息管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    活动&专题信息管理
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="activity-info-activ" type="checkbox"
                                       name="value_text"
                                       value="web/activityController/showList_活动信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    活动信息管理
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="activity-info-activ-set" type="checkbox"
                                       name="value_text"
                                       value="web/activityController/forAddActivity_新建活动信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建活动信息
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="activity-info-activ-set" type="checkbox"
                                       name="value_text"
                                       value="web/activityController/forEditActivity_编辑活动信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    编辑活动信息
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="activity-info-activ-set" type="checkbox"
                                       name="value_text"
                                       value="web/activityController/deleteActivity_删除活动信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除活动信息
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="activity-info-activ-set" type="checkbox"
                                       name="value_text"
                                       value="web/activityController/forActivityManage_管理活动">
                                <i class="custom_ckb none"></i>
                                <label>
                                    管理活动
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr class="split-line">
                        <td>
                            <div class="auth_item">
                                <input class="activity-info-special" type="checkbox"
                                       name="value_text"
                                       value="web/specialController/showSpecialList_专题信息管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    专题信息管理
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="activity-info-special-set" type="checkbox"
                                       name="value_text"
                                       value="web/specialController/forAddSpecial_新建专题信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建专题信息
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="activity-info-special-set" type="checkbox"
                                       name="value_text"
                                       value="web/specialController/forEditSpecial_编辑专题信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    编辑专题信息
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="activity-info-special-set" type="checkbox"
                                       name="value_text"
                                       value="web/specialController/deleteSpecial_删除专题信息">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除专题信息
                                </label>
                            </div>
                        </td>
                    </tr>
                    <!-- } 活动&专题 -->
                    <!-- 天上社区 { -->
                    <tr>
                        <td rowspan="4">
                            <div class="auth_item w-auto">
                                <input class="bbs" type="checkbox" name="value_text"
                                       value="web/index/intoPage?path=manage/html/bbs/bbs_天上社区">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    天上社区
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="bbs-home" type="checkbox" name="value_text"
                                       value="web/tssq/index_天上社区首页显示">
                                <i class="custom_ckb none"></i>
                                <label>
                                    天上社区首页显示
                                </label>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="auth_item">
                                <input class="bbs-home-set" type="checkbox" name="value_text"
                                       value="web/tssq/banner_Banner管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    Banner管理
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="bbs-home-set" type="checkbox" name="value_text"
                                       value="web/bbsReplyControoler/saveUI_最赞回复显示管理">
                                <i class="custom_ckb none"></i>
                                <label>
                                    最赞回复显示管理
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item w-auto">
                                <input class="bbs-forum" type="checkbox" name="value_text"
                                       value="web/tssq/plateList_论坛版块管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    论坛版块管理
                                </label>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="auth_item">
                                <input class="bbs-forum-set" type="checkbox" name="value_text"
                                       value="web/tssq/gotoSaveOrUpdatePlate_新建论坛版块">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建论坛版块
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="bbs-forum-set" type="checkbox" name="value_text"
                                       value="web/tssq/gotoSaveOrUpdatePlate_编辑论坛版块">
                                <i class="custom_ckb none"></i>
                                <label>
                                    编辑论坛版块
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="bbs-forum-set" type="checkbox" name="value_text"
                                       value="web/tssq/deletePlate_删除论坛版块">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除论坛版块
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="2">
                            <div class="auth_item w-auto">
                                <input class="bbs-reply" type="checkbox" name="value_text"
                                       value="default/post$reply/manage_帖子&回复管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    帖子&回复管理
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="bbs-reply-nopass" type="checkbox"
                                       name="value_text"
                                       value="web/tssq/uncheckedPostList_待审核的帖子&回复">
                                <i class="custom_ckb none"></i>
                                <label>
                                    待审核的帖子&回复
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="bbs-reply-nopass-set" type="checkbox"
                                       name="value_text" value="web/tssq/passPost_审核帖子&回复">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    审核帖子&回复
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="bbs-reply-nopass-set" type="checkbox"
                                       name="value_text" value="web/tssq/deletePost_删除帖子&回复">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">
                                    删除帖子&回复
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item">
                                <input class="bbs-reply-pass" type="checkbox"
                                       name="value_text"
                                       value="web/passPostController/searchPost_已审核的帖子&回复">
                                <i class="custom_ckb none"></i>
                                <label>
                                    已审核的帖子&回复
                                </label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="bbs-reply-pass-set" type="checkbox"
                                       type="checkbox" name="value_text"
                                       value="web/passPostController/addUI_新建帖子">
                                <i class="custom_ckb none"></i>
                                <label>
                                    新建帖子
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="bbs-reply-pass-set" type="checkbox"
                                       name="value_text"
                                       value="web/passPostController/updateUI_编辑帖子">
                                <i class="custom_ckb none"></i>
                                <label>
                                    编辑帖子
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="bbs-reply-pass-set" type="checkbox"
                                       name="value_text"
                                       value="web/passPostController/deletePost_删除帖子">
                                <i class="custom_ckb none"></i>
                                <label>
                                    删除帖子
                                </label>
                            </div>

                            <div class="auth_item">
                                <input class="bbs-reply-pass-set" type="checkbox"
                                       name="value_text"
                                       value="web/passPostController/managerReply_管理回复">
                                <i class="custom_ckb none"></i>
                                <label>
                                    管理回复
                                </label>
                            </div>
                        </td>
                    </tr>
                    <!-- } 天上社区 -->
                    <!-- 骑行专区 { -->
                    <tr>
                    	<td rowspan="7">
                    		<div class="auth_item w-auto">
                                <input class="ride" type="checkbox" name="value_text" value="web/ride/lists_骑行专区">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">骑行专区</label>
                            </div>
                    	</td>
                    	<td>
                    		<div class="auth_item w-auto">
                                <input class="ride-home" type="checkbox" name="value_text" value="web/ridecd/list_骑行专区首页">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">骑行专区首页</label>
                            </div>
                    	</td>
                    	<td colspan="2">
                    		<div class="auth_item">
                                <input class="ride-home-banner" type="checkbox" name="value_text" value="web/homeController/getManageImg?programaCode=er5tyq3h632-75e6-11e4-byce-005ajya05bc9_banner管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">banner管理</label>
                            </div>
                            <div class="auth_item">
                                <input class="ride-home-rec" type="checkbox" name="value_text" value="web/ridecommon/list_骑行首页相关推荐">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">骑行首页相关推荐</label>
                            </div>
                    	</td>
                    </tr>
                    <tr>
                    	<td rowspan="3">
                    		<div class="auth_item w-auto">
                                <input class="ride-equ" type="checkbox" name="value_text" value="web/ride/zb_装备推荐">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">装备推荐</label>
                            </div>
                    	</td>
                    	<td colspan="2">
                    		<div class="auth_item w-auto">
                                <input class="ride-equ-home" type="checkbox" name="value_text" value="web/homeController/getManageImg?programaCode=er5gh3hl32-75e6-11e4-byce-005a56a05bc9_骑行装备首页">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">骑行装备首页</label>
                            </div>
                    	</td>
                    </tr>
                    <tr>
                    	<td colspan="2">
                    		<div class="auth_item w-auto">
                                <input class="ride-equ-class" type="checkbox" name="value_text" value="cycling/types_装备类型管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">装备类型管理</label>
                            </div>
                    	</td>
                    </tr>
                    <tr>
                    	<td colspan="2">
                    		<div class="auth_item w-auto">
                                <input class="ride-equ-masg" type="checkbox" name="value_text" value="web/equip/list_装备管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">装备管理</label>
                            </div>
                    	</td>
                    </tr>
                    <tr>
                    <td rowspan="2">
                    		<div class="auth_item w-auto">
                                <input class="ride-star" type="checkbox" name="value_text" value="web/service/listsite_星级服务">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">星级服务</label>
                            </div>
                    	</td>
                    	<td colspan="2">
                    		<div class="auth_item w-auto">
                                <input class="ride-star-line" type="checkbox" name="value_text" value="web/rideLine/forRideLineList_骑行路线管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">骑行路线管理</label>
                            </div>
                    	</td>
                    </tr>
                    <tr>
                    	<td colspan="2">
                    		<div class="auth_item w-auto">
                                <input class="ride-star-site" type="checkbox" name="value_text" value="web/serviceSite/list_骑行路线站点管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">骑行路线站点管理</label>
                            </div>
                    	</td>
                    </tr>
                    <tr>
                    	<td colspan="3">
                    		<div class="auth_item w-auto">
                                <input class="ride-dind" type="checkbox" name="value_text" value="web/common-order/page_订单管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">订单管理</label>
                            </div>
                    	</td>
                    </tr>
                    <!-- } 骑行专区 -->
                    <!-- 系统设置 { -->
                    <tr>
                        <td rowspan="12">
                            <div class="auth_item w-auto">
                                <input class="set" type="checkbox" name="value_text" value="web/member/list_系统设置">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">系统设置</label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item w-auto">
                                <input class="set-member" type="checkbox" name="value_text" value="web/member/listmanager_会员管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">会员管理</label>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="auth_item">
                                <input class="set-member-set" type="checkbox" name="value_text" value="web/member/changeState_启用/停用账号">
                                <i class="custom_ckb none"></i>
                                <label>启用/停用账号</label>
                            </div>

                            <div class="auth_item">
                                <input class="set-member-set" type="checkbox" name="value_text" value="web/member/add_新建账号">
                                <i class="custom_ckb none"></i>
                                <label>新建账号</label>
                            </div>

                            <div class="auth_item">
                                <input class="set-member-set" type="checkbox" name="value_text" value="web/member/edit_编辑账号">
                                <i class="custom_ckb none"></i>
                                <label>编辑账号</label>
                            </div>

                            <div class="auth_item">
                                <input class="set-member-set" type="checkbox" name="value_text" value="web/member/deleteSingle_删除账号">
                                <i class="custom_ckb none"></i>
                                <label>删除账号</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item w-auto">
                                <input class="set-sensi" type="checkbox" name="value_text" value="web/badwords/list_敏感词管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">敏感词管理</label>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="auth_item">
                                <input class="set-sensi-set" type="checkbox" name="value_text" value="web/badwords/save_添加敏感词">
                                <i class="custom_ckb none"></i>
                                <label>添加敏感词</label>
                            </div>

                            <div class="auth_item">
                                <input class="set-sensi-set" type="checkbox" name="value_text" value="web/badwords/deleteSingle_删除敏感词">
                                <i class="custom_ckb none"></i>
                                <label>删除敏感词</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item w-auto">
                                <input class="set-score" type="checkbox" name="value_text" value="web/scoreManagerController/getListScoreManager_积分管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">积分管理</label>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="auth_item">
                                <input class="set-score-set" type="checkbox" name="value_text" value="web/scoreManagerController/updateScore_修改积分">
                                <i class="custom_ckb none"></i>
                                <label>修改积分</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="3">
                            <div class="auth_item w-auto">
                                <input class="set-oprt" type="checkbox" name="value_text" value="settings/otherWeb.html_运营管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">运营管理</label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="set-oprt-other" type="checkbox" name="value_text" value="settings/otherWeb.html_其他页面管理">
                                <i class="custom_ckb none"></i>
                                <label>其他页面管理</label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="set-oprt-other-set" type="checkbox" name="value_text" value="manage/html/settings/otherWeb-add_新增页面">
                                <i class="custom_ckb none"></i>
                                <label>新增页面</label>
                            </div>
                            <div class="auth_item">
                                <input class="set-oprt-other-set" type="checkbox" name="value_text" value="manage/html/settings/otherWeb-edit_编辑页面">
                                <i class="custom_ckb none"></i>
                                <label>编辑页面</label>
                            </div>
                            <div class="auth_item">
                                <input class="set-oprt-other-set" type="checkbox" name="value_text" value="manage/html/settings/otherdeletes_删除页面">
                                <i class="custom_ckb none"></i>
                                <label>删除页面</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item">
                                <input class="set-oprt-adver" type="checkbox" name="value_text" value="web/adArea/adArealist_广告位管理">
                                <i class="custom_ckb none"></i>
                                <label>广告位管理</label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="set-oprt-adver-set" type="checkbox" name="value_text" value="web/adArea/gotoUpdateAdArea_编辑广告位">
                                <i class="custom_ckb none"></i>
                                <label>编辑广告位</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item">
                                <input class="set-oprt-opinion" type="checkbox" name="value_text" value="web/surguestion/list_建议与意见管理">
                                <i class="custom_ckb none"></i>
                                <label>建议与意见管理</label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="set-oprt-opinion-set" type="checkbox" name="value_text" value="web/surguestion/delete_删除建议/意见">
                                <i class="custom_ckb none"></i>
                                <label>删除建议/意见</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="3">
                            <div class="auth_item w-auto">
                                <input class="set-reply" type="checkbox" name="value_text" value="web/replyManageController/forReplyManageSettingList_评价&回复管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">评价&回复管理</label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="set-reply-setting" type="checkbox" name="value_text" value="web/replyManageController/forReplyManageSettingList_评价&回复设置">
                                <i class="custom_ckb none"></i>
                                <label>评价&回复设置</label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="set-reply-setting-save" type="checkbox" name="value_text" value="web/replyManageController/updateReplyManageSetting_保存评论设置">
                                <i class="custom_ckb none"></i>
                                <label>保存评论设置</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item">
                                <input class="set-reply-waitrep" type="checkbox" name="value_text" value="web/replyManageController/forReviewReplyManageList_待审核的评价&回复">
                                <i class="custom_ckb none"></i>
                                <label>待审核的评价&回复</label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="set-reply-waitrep-set" type="checkbox" name="value_text" value="web/replyManageController/reviewReply_通过评论">
                                <i class="custom_ckb none"></i>
                                <label>通过评论</label>
                            </div>
                            <div class="auth_item">
                                <input class="set-reply-waitrep-set" type="checkbox" name="value_text" value="web/replyManageController/deleteReply_删除评论">
                                <i class="custom_ckb none"></i>
                                <label>删除评论</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item">
                                <input class="set-reply-passrep" type="checkbox" name="value_text" value="web/replyManageController/forAlreadyReviewReplyManage_已审核的评价&回复">
                                <i class="custom_ckb none"></i>
                                <label>已审核的评价&回复</label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="set-reply-passrep-set" type="checkbox" name="value_text" value="web/replyManageController/deleteReply_删除评论">
                                <i class="custom_ckb none"></i>
                                <label>删除评论</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="2">
                            <div class="auth_item w-auto">
                                <input class="set-bill" type="checkbox" name="value_text" value="web/order/forPayContentList_账单管理">
                                <i class="custom_ckb none"></i>
                                <label class="w-auto">账单管理</label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="set-bill-pay" type="checkbox" name="value_text" value="web/order/forPayContentList_支付内容管理">
                                <i class="custom_ckb none"></i>
                                <label>支付内容管理</label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="set-bill-pay-set" type="checkbox" name="value_text" value="web/order/forEditOrder_编辑订单">
                                <i class="custom_ckb none"></i>
                                <label>编辑订单</label>
                            </div>
                            <div class="auth_item">
                                <input class="set-bill-pay-set" type="checkbox" name="value_text" value="web/order/deleteOrder_删除订单">
                                <i class="custom_ckb none"></i>
                                <label>删除订单</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="auth_item">
                                <input class="set-bill-order" type="checkbox" name="value_text" value="web/order/forOrderManageList_所有订单管理">
                                <i class="custom_ckb none"></i>
                                <label>所有订单管理</label>
                            </div>
                        </td>
                        <td>
                            <div class="auth_item">
                                <input class="set-bill-order-set" type="checkbox" name="value_text" value="web/order/forEditOrder_编辑订单">
                                <i class="custom_ckb none"></i>
                                <label>编辑订单</label>
                            </div>
                            <div class="auth_item">
                                <input class="set-bill-order-set" type="checkbox" name="value_text" value="web/order/deleteOrder_删除订单">
                                <i class="custom_ckb none"></i>
                                <label>删除订单</label>
                            </div>
                        </td>
                    </tr>
                    
                    <tr>
                        <td rowspan="1">
                            <div class="auth_item">
                                <input class="set-data" type="checkbox" name="value_text" value="web/data_数据管理">
                                <i class="custom_ckb none"></i>
                                <label>数据管理</label>
                            </div>
                        </td>
                        <td  colspan="2">
                            <div class="auth_item">
                                <input class="set-data-set" type="checkbox" name="value_text" value="web/cacheManagerController/intoCacheList_缓存管理">
                                <i class="custom_ckb none"></i>
                                <label>缓存管理</label>
                            </div>
                            <div class="auth_item">
                                <input class="set-data-set" type="checkbox" name="value_text" value="ctrip/intCtripPage_携程酒店同步">
                                <i class="custom_ckb none"></i>
                                <label>携程酒店同步</label>
                            </div>
                        </td>
                    </tr>
                    
                   
                    <!-- } 系统设置 -->
                </table>
            </div>
            <!-- } 角色权限-->

            <!-- 操作按钮 -->
            <div class="formLine mt30">
                <div class="saveOper">
                    <button id="saveRole" type="button" class="btn-sure mr50">
                        保存
                    </button>
                    <button id="cancelRole" type="reset" class="btn-sure mr50" onclick="back()">
                        取消
                    </button>
                </div>
            </div>
    </form>
</div>
<!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->

<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
        type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js"
        type="text/javascript"></script>
<script src="${ctxManage}/resources/js/settings/role.js"
        type="text/javascript"></script>
<script type="text/javascript">
    //======================================
    //				数据验证
    //======================================

    // 角色名
/*    $("input[name=name]").blur(function () {
        //.log($(this).val());
        var $this = $(this),
                thisVal = $this.val(),
                reg_val = $.VLDTOR.IsEnCnNum(thisVal),
                val_range = inputRange(this, 2, 30);
                console.log(thisVal);
        // 验证信息方法
        valid_txtBox(this, reg_val && val_range, "只能为2-30位的中、英文和数字", "right");
    });*/

    // 保存验证
    $("#saveRole").click(function () {
        // 失焦验证
        $("[name='name']").blur();

        // 是否含有错误信息
        var hasErr = $(".errMesg").length > 0;

        // 含有验证不通过的项
        if (hasErr) {
            msgBox("角色名称填写有误，请检查！", "erro");
            return;
        }
        // 验证通过提交表单
        else {
            msgBox("保存成功！", "pass");
            // 此处需要为form添加这个ID
            $("#myForm").submit();
        }
    });
</script>

<script type="text/javascript">
    function back() {
        popupLayer(
                '',
                '<div style="width: 320px; text-align:center; margin: 0 auto;">返回将不保存数据，是否返回？</div>',
                '<button type="button" class="btn-sure sure mr15">确定</button>' +
                '<button type="button" class="btn-sure cancel ml15">取消</button>'
        );
        $(document).one('click', '.sure', function () {
            javascript:history.back(-1);
            closePopup();
        });
    }
    //角色名重复判断
    $("#roleName").blur(function () {
        var $this = $(this),
                thisVal = $this.val();
                reg_val = $.VLDTOR.IsEnCnNum(thisVal),
                val_range = inputRange(this, 2, 30);        
        // 验证信息方法 
        if(valid_txtBox(this, reg_val && val_range, "只能为2-30位的中、英文和数字", "right")){
            $.ajax({
            url: "${ctx}/web/role/isRoleNameRepeat",
            dataType: "json",
            type: "post",
            data: {"roleName": thisVal, "code": $("#code").val()},
            async: false,
            success: function (data) {
//                          console.log(data);
                if (data) {
                    errMesg($this, "角色名重复", "right");
                } else {
                    $this.next('.reqItem').removeClass('errMesg').text('*');
                }
            }
        })
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
