<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
	String path_ = request.getContextPath().trim().equals("/")?request.getContextPath():request.getContextPath()+"/";
	String port_  =  ":"+request.getServerPort() ;
	if("80".equals( ""+request.getServerPort())){
	    port_ = ""  ;
	}
	String basePath_ = request.getScheme() + "://" + request.getServerName() + port_ + path_;
	request.setAttribute("ctx_", basePath_);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <%@include file="/common-html/common.jsp" %>
    <title>天上社区-天上社区首页显示-最赞回复显示管理</title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <script type="text/javascript">
        //显示最新
        function showNew() {
            window.location.href = "${ctx}web/bbsReplyControoler/showNew";
        }
    </script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>天上社区</a> -</span>
        <span><a href="${ctx}/web/tssq/index" target="_self">天上社区首页显示</a> -</span>
        <span><a>最赞回复显示管理</a></span>
    </div>

    <!-- 查看按钮 -->
    <div class="searchManage">
        <button type="button" class="btn-sure" onclick="showNew()">显示最赞</button>
        <button type="button" class="btn-sure" onclick="savePraise('preview','0')">预览</button>
        <button type="button" class="btn-sure"
                onclick="javascript:window.open('${ctx}community/frontIndex')">查看
        </button>
        <button type="button" class="btn-sure" onclick="savePraise('save','1')">保存</button>
    </div>

    <!-- 模块管理 { -->
    <div class="muduleManage filament_solid_ddd">
        <form id="saveForm" method="post" action="${ctx}/web/bbsReplyControoler/save">
            <!-- 回复一 { -->
            <div class="contClassify reply1">
                <h2 class="title">回复1</h2>

                <div class="formGroup">
                    <div class="left">
                        <div class="formLine">
                            <label>回复人:</label>
                            <input id="" name="replyname" value="${list[0].replyname }" type="text"
                                   class="w-260 replyMan">
                        </div>
                        <div class="formLine">
                            <label>帖子链接:</label>
                            <input id="" name="urls" value="${list[0].url }" type="text" class="w-260 cardLink">
                        </div>
                        <div class="formLine">
                            <label>赞数:</label>
                            <input id="" name="praise" maxlength="7" value="${list[0].praise }" type="text"
                                   class="w-260 zanNum">
                        </div>
                    </div>
                    <div class="right">
                        <div class="formLine">
                            <label class="pos_r_t5">回复内容:</label>
                            <textarea class="w-464 h-124 textNotNull"
                                      name="replycontent">${list[0].replycontent }</textarea>
                        </div>
                    </div>
                    <input type="hidden" value="${list[0].code}" name="codes" class="w-640">
                </div>
            </div>
            <!-- } 回复一 -->

            <!-- 回复二 { -->
            <div class="contClassify reply2">
                <h2 class="title">回复2</h2>

                <div class="formGroup">
                    <div class="left">
                        <div class="formLine">
                            <label>回复人:</label>
                            <input id="" name="replyname" value="${list[1].replyname }" type="text"
                                   class="w-260 replyMan">
                        </div>
                        <div class="formLine">
                            <label>帖子链接:</label>
                            <input id="" name="urls" value="${list[1].url }" type="text" class="w-260 cardLink">
                        </div>
                        <div class="formLine">
                            <label>赞数:</label>
                            <input id="" name="praise" maxlength="7" value="${list[1].praise }" type="text"
                                   class="w-260 zanNum">
                        </div>
                    </div>
                    <div class="right">
                        <div class="formLine">
                            <label class="pos_r_t5">回复内容:</label>
                            <textarea class="w-464 h-124 textNotNull"
                                      name="replycontent">${list[1].replycontent }</textarea>
                        </div>
                    </div>
                    <input type="hidden" value="${list[1].code}" name="codes" class="w-640">
                </div>
            </div>
            <!-- } 回复二 -->

            <!-- 回复三 { -->
            <div class="contClassify reply3">
                <h2 class="title">回复3</h2>

                <div class="formGroup">
                    <div class="left">
                        <div class="formLine">
                            <label>回复人:</label>
                            <input id="" name="replyname" value="${list[2].replyname }" type="text"
                                   class="w-260 replyMan">
                        </div>
                        <div class="formLine">
                            <label>帖子链接:</label>
                            <input id="" name="urls" value="${list[2].url }" type="text" class="w-260 cardLink">
                        </div>
                        <div class="formLine">
                            <label>赞数:</label>
                            <input id="" name="praise" maxlength="7" value="${list[2].praise }" type="text"
                                   class="w-260 zanNum">
                        </div>
                    </div>
                    <div class="right">
                        <div class="formLine">
                            <label class="pos_r_t5">回复内容:</label>
                            <textarea class="w-464 h-124 textNotNull"
                                      name="replycontent">${list[2].replycontent }</textarea>
                        </div>
                    </div>
                    <input type="hidden" value="${list[2].code}" name="codes" class="w-640">
                </div>
            </div>
            <!-- } 回复三 -->

            <!-- 回复四 { -->
            <div class="contClassify reply4">
                <h2 class="title">回复4</h2>

                <div class="formGroup">
                    <div class="left">
                        <div class="formLine">
                            <label>回复人:</label>
                            <input id="" name="replyname" value="${list[3].replyname }" type="text"
                                   class="w-260 replyMan">
                        </div>
                        <div class="formLine">
                            <label>帖子链接:</label>
                            <input id="" name="urls" value="${list[3].url }" type="text" class="w-260 cardLink">
                        </div>
                        <div class="formLine">
                            <label>赞数:</label>
                            <input id="" name="praise" maxlength="7" value="${list[3].praise }" type="text"
                                   class="w-260 zanNum">
                        </div>
                    </div>
                    <div class="right">
                        <div class="formLine">
                            <label class="pos_r_t5">回复内容:</label>
                            <textarea class="w-464 h-124 textNotNull"
                                      name="replycontent">${list[3].replycontent }</textarea>
                        </div>
                    </div>
                    <input type="hidden" value="${list[3].code}" name="codes" class="w-640">
                </div>
            </div>
            <!-- } 回复四 -->

            <!-- 回复五 { -->
            <div class="contClassify reply5">
                <h2 class="title">回复5</h2>

                <div class="formGroup">
                    <div class="left">
                        <div class="formLine">
                            <label>回复人:</label>
                            <input id="" name="replyname" value="${list[4].replyname }" type="text"
                                   class="w-260 replyMan">
                        </div>
                        <div class="formLine">
                            <label>帖子链接:</label>
                            <input id="" name="urls" value="${list[4].url }" type="text" class="w-260 cardLink">
                        </div>
                        <div class="formLine">
                            <label>赞数:</label>
                            <input id="" name="praise" maxlength="7" value="${list[4].praise }" type="text"
                                   class="w-260 zanNum">
                        </div>
                    </div>
                    <div class="right">
                        <div class="formLine">
                            <label class="pos_r_t5">回复内容:</label>
                            <textarea class="w-464 h-124 textNotNull"
                                      name="replycontent">${list[4].replycontent }</textarea>
                        </div>
                    </div>
                    <input type="hidden" value="${list[4].code}" name="codes" class="w-640">
                </div>
            </div>
            <!-- } 回复五 -->


            <!-- 操作 -->
            <div class="formLine">
                <div class="saveOper">
                    <button type="button" onclick="savePraise('preview','0')" class="btn-sure mr30">预览</button>
                    <button type="button" onclick="savePraise('save','1')" class="btn-sure mr100">保存</button>
                </div>
            </div>
        </form>
    </div>
    <!-- } 模块管理 -->
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
<script>
    //回复人名字验证
    $(".replyMan").blur(function () {
        var $this = $(this);
        var thisVal = $this.val();
        var returnVal = $.VLDTOR.IsArticle(thisVal);
        var returnLen = inputRange(this, 2, 30);
        if (returnVal && returnLen || thisVal == "") {
            $this.next('span.errMesg').remove();
        } else {
            creatErrMesg(this, "只能为字母、数字及中文,且长度在2-30之间", "right");
        }
    });
    //帖子链接验证
    $(".cardLink").blur(function () {
        var $this = $(this);
        var thisVal = $this.val();
        var returnVal = $.VLDTOR.IsHTTP(thisVal);
        if (returnVal || thisVal == "") {
            $this.next('span.errMesg').remove();
        } else {
            creatErrMesg(this, "请输入以http(s)://开头的链接", "right");
        }
    });
    //点赞数验证
    $(".zanNum").blur(function () {
        var $this = $(this);
        var thisVal = $this.val();
        var returnVal = $.VLDTOR.IsNum(thisVal);
        if (returnVal || thisVal == "") {
            $this.next('span.errMesg').remove();
        } else {
            creatErrMesg(this, "只能为空或正整数", "right");
        }
    });
    //回复内容验证
    $(".textNotNull").blur(function () {
        var $this = $(this);
        var thisVal = $this.val();
        var returnVal = $.VLDTOR.IsArticle(thisVal);
        valid_txtBox_create(this, (returnVal && inputRange(this, 2, 2000)) || thisVal == "", "内容只能为字母、数字及中文，且长度在2-2000", "right");
    });

    //显示保存
    function savePraise(saveType, avaliable) {
//        //获取回复1的值
//        var replayMan1 = $(".reply1 input.replyMan").val() != "",
//                replayLink1 = $(".reply1 input.cardLink").val() != "",
//                replayZan1 = $(".reply1 input.zanNum").val() != "",
//                replayCon1 = $(".reply1 textarea.textNotNull").text() != "",
//                loadReplyVal1 = replayMan1 || replayLink1 || replayZan1 || replayCon1;
//        //获取回复2的值
//        var replayMan2 = $(".reply2 input.replyMan").val() != "",
//                replayLink2 = $(".reply2 input.cardLink").val() != "",
//                replayZan2 = $(".reply2 input.zanNum").val() != "",
//                replayCon2 = $(".reply2 textarea.textNotNull").text() != "",
//                loadReplyVal2 = replayMan2 || replayLink2 || replayZan2 || replayCon2;
//        //获取回复3的值
//        var replayMan3 = $(".reply3 input.replyMan").val() != "",
//                replayLink3 = $(".reply3 input.cardLink").val() != "",
//                replayZan3 = $(".reply3 input.zanNum").val() != "",
//                replayCon3 = $(".reply3 textarea.textNotNull").text() != "",
//                loadReplyVal3 = replayMan3 || replayLink3 || replayZan3 || replayCon3;
//        //获取回复4的值
//        var replayMan4 = $(".reply4 input.replyMan").val() != "",
//                replayLink4 = $(".reply4 input.cardLink").val() != "",
//                replayZan4 = $(".reply4 input.zanNum").val() != "",
//                replayCon4 = $(".reply4 textarea.textNotNull").text() != "",
//                loadReplyVal4 = replayMan4 || replayLink4 || replayZan4 || replayCon4;
//        //获取回复5的值
//        var replayMan5 = $(".reply5 input.replyMan").val() != "",
//                replayLink5 = $(".reply5 input.cardLink").val() != "",
//                replayZan5 = $(".reply5 input.zanNum").val() != "",
//                replayCon5 = $(".reply5 textarea.textNotNull").text() != "",
//                loadReplyVal5 = replayMan5 || replayLink5 || replayZan5 || replayCon5;
//        //如果回复1-5有值，执行验证
//        if (loadReplyVal1) {
//            $(".reply1 .replyMan").blur();
//            $(".reply1 .cardLink").blur();
//            $(".reply1 .zanNum").blur();
//            $(".reply1 .textNotNull").blur();
//        }
//        if (loadReplyVal2) {
//            $(".reply2 .replyMan").blur();
//            $(".reply2 .cardLink").blur();
//            $(".reply2 .zanNum").blur();
//            $(".reply2 .textNotNull").blur();
//        }
//        if (loadReplyVal3) {
//            $(".reply3 .replyMan").blur();
//            $(".reply3 .cardLink").blur();
//            $(".reply3 .zanNum").blur();
//            $(".reply3 .textNotNull").blur();
//        }
//        if (loadReplyVal4) {
//            $(".reply4 .replyMan").blur();
//            $(".reply4 .cardLink").blur();
//            $(".reply4 .zanNum").blur();
//            $(".reply4 .textNotNull").blur();
//        }
//        if (loadReplyVal5) {
//            $(".reply5 .replyMan").blur();
//            $(".reply5 .cardLink").blur();
//            $(".reply5 .zanNum").blur();
//            $(".reply5 .textNotNull").blur();
//        }
        // 定义各个回复显示的组
        var formGroup = $(".formGroup"),
            formGroup_len = formGroup.length;
        for(var i = 0; i < formGroup_len; i++) {
            // “回复内容”文本域
            var reply_val = formGroup.eq(i).find("textarea[name='replycontent']").val();
            // 回复内容不为空
            if(reply_val != "") {
                var reply_info = formGroup.eq(i).find("input[type='text']"),
                    reply_info_len = reply_info.length;
                for(var j = 0; j < reply_info_len; j++) {
                    var reply_info_val = reply_info.eq(j).val();
                    if(reply_info_val == "") {
                        creatErrMesg($(reply_info.eq(j)), "已填写内容的组，内容必须填写完整", "right");
                    } else {
                        removeErrMesg($(reply_info.eq(j)));
                    }
                }
            }
            // 回复的内容为空则检查其他信息填写情况
            else {
                var noVal = true;
                        reply_info = formGroup.eq(i).find("input[type='text']"),
                        reply_info_len = reply_info.length;
                for(var j = 0; j < reply_info_len; j++) {
                    var reply_info_val = reply_info.eq(j).val();
                    if(reply_info_val != "") {
                        noVal = false;
                    }
                }
                // 文本框内含有已填写的值
                if(noVal == false) {
                    creatErrMesg(formGroup.eq(i).find("textarea[name='replycontent']"), "已填写内容的组，内容必须填写完整", "right");
                    var reply_info = formGroup.eq(i).find("input[type='text']"),
                            reply_info_len = reply_info.length;
                    for(var k = 0; k < reply_info_len; k++) {
                        var reply_info_val = reply_info.eq(k).val();
                        if(reply_info_val == "") {
                            creatErrMesg($(reply_info.eq(k)), "已填写内容的组，内容必须填写完整", "right");
                        } else {
                            removeErrMesg($(reply_info.eq(k)));
                        }
                    }
                } else {
                    removeErrMesg(formGroup.eq(i).find("textarea[name='replycontent']"));
                    removeErrMesg(formGroup.eq(i).find("input[type='text']"));
                }
            }
        }

        //判断是否出现验证错误提示
        if ($('span.errMesg').length > 0) {
            msgBox("输入的内容有误，请检查！", "erro", 1000);
        } else {
            //此处发送数据到后台
            $("#saveForm").attr("action", "${ctx}/web/bbsReplyControoler/save?saveType=" + saveType + "&avaliable=" + avaliable);
            if (saveType == 'preview') {
                $("#saveForm").attr("target", "_blank");
            } else {
                $("#saveForm").attr("target", "");

            }
            $("#saveForm").submit();
            if (saveType != 'preview') {
                msgBox("保存成功!", "pass", 5000);
            }
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
