<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="author" content="quansenx">
    <meta name=“keywords” content="西藏骑行攻略，西藏骑行注意事项，西藏骑行计划，骑行318，川藏线，川藏线骑行攻略，骑行西藏，318川藏线" />
    <meta name=“description” content="骑行专区：为驴友提供高质量的西藏骑行分享交流专区，包括西藏骑行攻略，西藏骑行注意事项，西藏骑行计划，西藏骑行约伴，骑行西藏，318川藏线，骑行318，川藏线骑行攻略等西藏骑行分享。" />
    <title>骑车去西藏_骑行专区-天上西藏官网</title>
    <%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPortal}/assets/css/building.css"/>
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
        // Set configuration
        seajs.config({
            alias: {
                "jquery":"${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js",
                "avalon":"${ctxPortal}/modules/avalon/1.3.7/avalon.js",
                "common/css":"${ctxPortal}/assets/css/common.css",
                "footer/css":"${ctxPortal}/assets/css/footer.css"
            }
        });
        seajs.use(['common/css','footer/css']);
        seajs.use('avalon', function (avalon) {
            avalon.define({
                $id:"buildView",
                showTag:false
            })
        })
    </script>
</head>
<body ms-controller="buildView">
    <!-- 登陆弹出框 -->
 <jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"/>
 <jsp:include page="${root}portal/headerFooterController/header" flush="ture"/> 
    <!-- content -->
    <div class="content clearfix">
        <div>
            <img src="${ctxPortal}/assets/icon/robot.png"/>
        </div>
    </div>

    <!-- footer -->
 	<jsp:include page="${root}portal/headerFooterController/footer" flush="ture"/>
</body>
</html>