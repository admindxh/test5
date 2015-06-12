<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"    %>
<%
    String path = request.getContextPath();
    String port = ":" + request.getServerPort();
    if ("80".equals(""+request.getServerPort())) {
        port = "";
    }
    String basePath = request.getScheme() + "://" + request.getServerName() + port + path + "/";
    request.setAttribute("ctxPageUtil",basePath);
%>
<script>
    define(['jquery'], function(require, exports, module) {
        /**
         * 页面浏览统计
         * 邓晓辉
         */
        function  pageViewStatis(){
            $.ajax({
                url:"${ctxPageUtil}portal/pageViewController/pageViewStatistics",
                data: {page:window.location.href},
                type:"POST",
                success:function(data){}
            });
        }
        $(function(){
            //页面统计
            pageViewStatis();
        })
    })
</script>