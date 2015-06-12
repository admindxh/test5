<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String port = ":" + request.getServerPort();
	if ("80".equals(""+request.getServerPort())) {
		port = "";
	}
	String basePath = request.getScheme() + "://" + request.getServerName() + port + path + "/";
	request.setAttribute("ctxFrontContentStaticUtil", basePath);
%>
<script>
    /***点击日志类型**
     public final static String 	ACTION_CLICK="click";//点击类型,查看类型
     public final static String  ACTION_FAVATE="favate";   //收藏数量类型
     public final static String  ACTION_PRAISE="praise";   //赞数类型
     public final static String  ACTION_REPLY="reply";//评论类型
     public final static String  ACTION_HREF="href";//外链数量（商户）
     */

    /**
     * 页面浏览统计
     * 邓晓辉
     protype 栏目类型 (改内容属于什么类型)
     tableName 改内容来源于什么表 比如 content 表，商户表等
     code 内容 code 值
     actionType 操作类型 收藏，点击，赞等类型   click,favate,praise,reply，href（商户的外链点击类型比如立即预定）
     */
    function frontContentStatic(protype,tableName,code,actionType){
        $.ajax({
            url:"${ctxFrontContentStaticUtil}web/frontContentStatisController/staticsSystemLog",
            data: {protype:protype,tableName:tableName,code:code,actionType:actionType},
            type:"POST",
            success:function(data){}
        });
    }
</script>