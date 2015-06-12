<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common-html/common.jsp" %>
<%@taglib uri="/rimi-tags" prefix="rimi" %>
<script type="text/javascript">
    function goneandwant(_option, _destinationCode) {
        var _cuserCode = '${logUser}';
        if (!_cuserCode) {
            $('[data-toggle="login"]').click();
            return;
        }
        $.ajax({
            url: contextPath + "/web/destinationController/goneandwant",
            dataType: "text",
            type: "post",
            data: {
                option: _option,
                destinationCode: _destinationCode,
                cuserCode: _cuserCode
            },
            async: false,
            success: function (data) {
                if (data == 'exit') {
                    if (_option == 1) {
                        $("#btngone").addClass("but-active");
                    } else {
                        $("#btnwant").addClass("but-active");
                    }

                    //alert('您已经收藏该目的地了!');
                    return;
                }
                if (_option == 1) {
                    $("#gone").text(data);
                    $("#btngone").addClass("but-active");
                } else {
                    $("#want").text(data);
                    $("#btnwant").addClass("but-active");
                }
            }
        });
    }
</script>
<!-- banner -->
<div id="banner">
    <div class="banner"
         style="background-image: url('${ctx}${destination.destinationImage}');filter: progid:DXImageTransform.Microsoft.AlphaImageLoader( src='${ctx}${destination.destinationImage}', sizingMethod='scale');">
        <div class="container">
            <div class="banner-left">
                <div>
                    <button id="btngone" onclick="goneandwant(1,'${destination.code}')"
                            <rimi:WanAndGo wananddes="gone" desCode="${destination.code}"
                                           userCode="${logUser.code}">class="but-active"</rimi:WanAndGo>
                            >
                        <span>去过</span>
                        <span id="gone">${destination.falseGoneCount }</span>
                    </button>
                    <button id="btnwant" onclick="goneandwant(0,'${destination.code}')"
                            <rimi:WanAndGo wananddes="wanna" desCode="${destination.code}"
                                           userCode="${logUser.code}">class="but-active"</rimi:WanAndGo>
                            >
                        <span>想去</span>
                        <span id="want">${destination.falseWantCount }</span>
                    </button>
                </div>
            </div>
            <div class="banner-right">
                <div class="secondary-menu">
                    <ul>
                        <%--<a href="${ctx }tourism/view/forView" target="_blank">景点</a>  --%>
                        <li class="clearfix location-hover"
                            onclick="javascript:window.open('${ctx }tourism/view/forView/${destination.code }.html')"></li>
                        <%--<a href="${ctx }tourism/atlas/forPhotoGallery?destinationCode=${destination.code}" target="_blank">图片集</a>--%>
                        <li class="clearfix bookmark-hover"
                            onclick="javascript:window.open('${ctx }tourism/atlas/forPhotoGallery/${destination.code}.html')"></li>
                        <%--<a href="${ctx }tourism/strage/intoTraval?des=${destination.code }" target="="_blank">攻略&游记</a>--%>
                        <li class="clearfix car-hover"
                            onclick="javascript:window.window.open('${ctx }tourism/strage/intoTravaldes/${destination.code }.html')"></li>
                        <%--<a href="${ctx }tourism/merchant/merchantList?destinationCode=${destination.code }" target="_blank">商户</a>--%>
                        <li class="clearfix bicyle-hover"
                            onclick="javascript:window.window.open('${ctx }tourism/merchant/merchantListdes/${destination.code }.html')"></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- JS引用部分 -->
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
<script>
    seajs.use('${ctxPortal }/assets/css/tourism/des_banner.css');
</script>