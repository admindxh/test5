<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="rimi" uri="/rimi-tags" %>
<%@ taglib prefix="udj" uri="/user-defined-jstl" %>
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
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>游西藏-商户管理-团游信息管理</title>
    <link rel="stylesheet"
          href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet"
          href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
            type="text/javascript"></script>
    <style>
        /* 闭合浮动 */
        .floatfix:after {
            content: "";
            display: table;
            clear: both;
        }

        .ord {
            text-decoration: none;
            color: #666
        }
    </style>
    <script src="${ctxManage}/resources/plugin/respond.min.js"
            type="text/javascript"></script>
    <script src="${ctx}/common-js/common.js"
            type="text/javascript"></script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location mb40">
        <label>
            您当前位置为:
        </label>
        <span><a>游西藏</a> -</span>
        <span><a>商户管理</a> -</span>
        <span><a href="#" target="_self">团游信息管理</a> </span>
    </div>

    <!-- 数据操作 -->
    <div class="searchManage">
        <rimi:perm url="gotoSaveOrUpdateGroupTravel">
            <a href="gotoSaveOrUpdateGroupTravel" target="_self"
               class="btn-anchor">添加团游</a>
        </rimi:perm>
        <rimi:perm url="web/groupTravel/deleteGroupTravel">
            <button type="button" class="btn-sure"
                    onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/web/groupTravel/deleteGroupTravels','code')">
                批量删除
            </button>
        </rimi:perm>
    </div>
    <!-- 搜索 -->
    <form action="searchGroupTravel" method="post">
        <div class="searchTools mb20">
            <div class="mt10">
                <label class="w-auto">
                    关键字:
                </label>
                <input id="keyWord" type="text" maxlength="30" class="w-160" name="keyWord"
                       value="${keyWord}" placeholder="请输入名称进行关键字搜索"/>
                <button type="submit" class="btn-search va_m"></button>
                <!-- 相关目的地 -->
                <div class="select-base ml-10">
                    <i class="w-140">${destinationCodeText }</i>
                    <dl>
                        <dt inputValue="" myMethod="ajaxGetViewByDescode" inputName="destinationCode"
                            inputTextValue="全部地区" inputTextName="destinationCodeText">全部地区
                        </dt>
                        <c:forEach var="destination" items="${destination}">
                            <dt myMethod="ajaxGetViewByDescode" inputValue="${destination.code}"
                                inputName="destinationCode" inputTextValue="${destination.destinationName}"
                                inputTextName="destinationCodeText">${destination.destinationName}</dt>
                        </c:forEach>
                    </dl>
                    <input id="destinationCode" type="hidden" value="${destinationCode }" name="destinationCode"/>
                    <input id="destinationCodeText" type="hidden" value="${destinationCodeText }"
                           name="destinationCodeText"/>
                </div>

                <!-- 相关景点 -->
                <div class="select-base ml-10">
                    <i class="w-140">${viewCodeText }</i>
                    <dl id="appendView">
                        <dt inputValue="" inputName="viewCode" inputTextValue="全部景点" inputTextName="viewCodeText">全部景点
                        </dt>
                        <c:forEach var="view" items="${viewList}">
                            <dt inputValue="${view.code}" inputName="viewCode">${view.viewName}</dt>
                        </c:forEach>
                    </dl>
                    <input id="viewCode" type="hidden" value="${viewCode }" name="viewCode"/>
                    <input id="viewCodeText" type="hidden" value="${viewCodeText}" name="viewCodeText"/>
                </div>
                <div class="select-base">
                    <i class="w-120">
                        <c:choose>
                            <c:when test="${isRecommend == 1}">
                                官方推荐
                            </c:when>
                            <c:when test="${isRecommend == 0}">
                                非官方推荐
                            </c:when>
                            <c:otherwise>
                                推荐类型
                            </c:otherwise>
                        </c:choose>
                    </i>
                    <%--<c:if test="${isRecommend == 1}"><i class="w-120">官方推荐</i></c:if>--%>
                    <%--<c:if test="${isRecommend == 0}"><i class="w-120">非官方推荐</i></c:if>--%>
                    <%--<c:if test="${empty isRecommend}"><i class="w-120">推荐类型</i></c:if>--%>
                    <dl>
                        <dt>
                            推荐类型
                        </dt>
                        <dt inputValue="1" inputName="isRecommend">
                            官方推荐
                        </dt>
                        <dt inputValue="0" inputName="isRecommend">
                            非官方推荐
                        </dt>

                    </dl>
                    <input id="isRecommend" type="hidden" value="${isRecommend}" name="isRecommend"/>
                </div>
                <div class="select-base float_r">
                    <i class="w-140">
                        <c:choose>
                            <c:when test="${rule eq 'view'}">
                                按查看量
                            </c:when>
                            <c:when test="${rule eq 'praise'}">
                                按评论量
                            </c:when>
                            <c:when test="${rule eq 'collect'}">
                                按收藏次数
                            </c:when>
                            <c:otherwise>
                                快捷筛选
                            </c:otherwise>
                        </c:choose>
                    </i>
                    <dl>
                        <dt>
                            <a href="javascript:void(0);" class="ord" ord="orderByGroupTravel?rule=view">按查看量</a>
                        </dt>
                        <dt>
                            <a href="javascript:void(0);" class="ord" ord="orderByGroupTravel?rule=praise">按评论量</a>
                        </dt>
                        <dt>
                            <a href="javascript:void(0);" class="ord" ord="orderByGroupTravel?rule=collect">按收藏次数</a>
                        </dt>
                    </dl>
                </div>
            </div>
        </div>
    </form>

</div>
</div>
<!-- 模块管理 { -->
<div class="tableManage pos-rela">
    <form method="post" action="searchGroupTravel" id="listForm"></form>
    <!-- 数据列表 -->
    <table border="0" cellpadding="0" cellspacing="0" class="dataTable">
        <thead>
        <tr>
            <th class="center">
                <input type="checkbox" name="dataCheck" class="allCheck">
                全选
            </th>
            <th class="center">
                团游编号
            </th>
            <th class="center">
                相关目的地
            </th>
            <th class="center">
                团游标题
            </th>
            <th class="center">
                是否官方推荐
            </th>
            <th class="center">
                操作
            </th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty pager.dataList}">
            <tr>
                <td>暂无数据</td>
            </tr>
        </c:if>
        <c:if test="${not empty pager.dataList}">
            <c:forEach items="${pager.dataList}" var="groupTravel">
                <tr>
                    <td class="center">
                        <input type="checkbox" name="dataCheck" class="dataCheck" value="${groupTravel.code}">
                    </td>
                    <td class="center">
                            ${groupTravel.code}
                    </td>
                    <td class="ellipsis maxW1100" >
                            ${udj:subString(groupTravel.desname,20)}
                    </td>
                    <td class="center">
                        <a href="${ctx}${groupTravel.url}" target="_blank">${groupTravel.name}</a>
                    </td>
                    <td class="center">
                        <c:if test="${groupTravel.isRecommend ==1}">官方推荐</c:if>
                        <c:if test="${groupTravel.isRecommend ==0}">非官方推荐</c:if>
                    </td>
                    <td class="center">
                        <rimi:perm url="web/groupTravel/gotoSaveOrUpdateGroupTravel">
                            <a
                                    href="${ctx}/web/groupTravel/gotoSaveOrUpdateGroupTravel?code=${groupTravel.code}">修改</a>
                        </rimi:perm>
                        <rimi:perm url="web/groupTravel/deleteGroupTravel">
                            <a
                                    href="#" onclick="deleteGt('${groupTravel.code}',this)">删除</a>
                        </rimi:perm>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
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
<script>

    $(document).on("click",".ord",function() {
        var isRecommend = $("#isRecommend").val();
        var destinationCode = $("#destinationCode").val();
        var type = $("#type").val();
        var keyWord = $("#keyWord").val();
        var viewCode = $("#viewCode").val();
        var hrefInfo = $(this).attr("ord");
        location.href = hrefInfo+"&isRecommend="+isRecommend+"&destinationCode="+destinationCode+"&type="+type+"&keyWord="+keyWord+"&viewCode="+viewCode;
    });

		 /**
	     * ajax 请求路径 查询 目的地对应的  景点
	     * @param destinationCode
	     * @return
	     */
	    function  ajaxGetViewByDescode(destinationCode){
	        var vl = $(destinationCode).val();//请求值
	    	$.ajax(
		    			{
		    				url:"${ctx}/web/readTibetSgPassMgController/getViewByDescode",
		    				data:"destinationCode="+vl,
		    			    type: 'POST',
		    				success:function(data){
			    			   var  viewsJSON  =$.parseJSON(data);
							   var  htmlViews  = '<dt inputValue="" inputName="viewCode"    inputTextValue="全部景点" inputTextName="viewCodeText">全部景点</dt>';
								$(viewsJSON).each(function(i){
									htmlViews = htmlViews +"<dt inputValue='"+this.code+"'  inputName='viewCode'  inputTextValue='"+this.viewName+"' inputTextName='viewCodeText' >"+this.viewName+"</dt>";		 
								});	
			    			   $("#appendView").html(htmlViews);
			    			   bindingSubmit();
		    				}
		        	   }
	    	    );
	   	 }
	    $(function(){
			var  pageInfo =  '<div class="paging-info">'+
					    		'<span class="disp-ib">当前第&nbsp;${pager.currentPage }&nbsp;页</span>'+
					    		'<span class="disp-ib">共&nbsp;${pager.totalPage }&nbsp;页</span>'+
					    		'<span class="disp-ib">共&nbsp;${pager.totalCount }&nbsp;条</span>' +
					    		'</div>';
			$("#pageInfo").append(pageInfo);
			//
			
		});
			</script>
	</body>
<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
        type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-tabel.js"
        type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
<script src="${ctxManage}/webuploader/webuploader.js"
        type="text/javascript"></script>
<script src="${ctxManage}/webuploader/upload.js"
        type="text/javascript"></script>
<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch">
<!-- IE10+ -->
<link rel="next">
<!-- Firefox -->
<link rel="prerender">
<!-- Chrome -->
<script>
    //删除的确认框
    function deleteGt(plateCode, el) {
        popupLayer(
                '',
                '<div style="text-align:center">是否删除该团游</div>',
                '<div class="formLine">' +
                '<button type="button" class="btn-sure sureDelOtherMud mr20">确定</button>' +
                '<button type="button" class="btn-sure cancelDelOtherMud cancel ml20">取消</button>' +
                '</div>'
        );
        $(document).one("click", ".sureDelOtherMud", function () {
            var $tr = $(el).parents('tr');
            $.ajax({
                url: "${ctx}/web/groupTravel/deleteGroupTravel",
                dataType: "json",
                type: "post",
                data: {
                    "code": plateCode
                },
                async: false,
                success: function (data) {
                    //.log("sdsddsds");
                    location.href = "${ctx}/web/groupTravel/searchGroupTravel";
                }
            });
            closePopup();
        });
    }

    $(function () {
        var pageInfo = '<div class="paging-info">' +
                '<span class="disp-ib">当前第&nbsp;${pager.currentPage }&nbsp;页</span>' +
                '<span class="disp-ib">共&nbsp;${pager.totalPage }&nbsp;页</span>' +
                '<span class="disp-ib">共&nbsp;${pager.totalCount }&nbsp;条</span>' +
                '</div>';
        $("#pageInfo").append(pageInfo);
        //

    });

    /**
     * ajax 请求路径 查询 目的地对应的  景点
     * @param destinationCode
     * @return
     */
    function ajaxGetViewByDescode(destinationCode) {
        var vl = $(destinationCode).val();//请求值
        $.ajax(
                {
                    url: "${ctx}/web/readTibetSgPassMgController/getViewByDescode",
                    data: "destinationCode=" + vl,
                    type: 'POST',
                    success: function (data) {
                        var viewsJSON = $.parseJSON(data);
                        var htmlViews = '<dt inputValue="" inputName="viewCode"    inputTextValue="全部景点" inputTextName="viewCodeText">全部景点</dt>';
                        $(viewsJSON).each(function (i) {
                            htmlViews = htmlViews + "<dt inputValue='" + this.code + "'  inputName='viewCode'  inputTextValue='" + this.viewName + "' inputTextName='viewCodeText' >" + this.viewName + "</dt>";
                        });
                        $("#appendView").html(htmlViews);
                        bindingSubmit();
                    }
                }
        );
    }
</script>
</body>
</html>