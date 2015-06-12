<%@ page language="java" import="java.util.*" pageEncoding="GB18030" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>������-��������ҳ��ʾ-�̻�����</title>
    <%@include file="/common-html/commonxz.jsp" %>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js"
            type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base-tabel.js"
            type="text/javascript"></script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- ҳ��λ��-->
    <div class="location">
        <label>
            ����ǰλ��Ϊ:
        </label>
        <span><a>������</a> -</span>
        <span><a href="travel.html" target="_self">��������ҳ��ʾ</a> -</span>
        <span><a>�̻�����</a> </span>
    </div>

    <!-- �鿴��ť -->
    <div class="operManage">
        <button type="button" class="lookup btn-sure" onclick="javascript:window.open('${ctx}tourism/strage/frontIndex')">
            �鿴
        </button>
    </div>

    <!-- ģ����� { -->
    <div class="muduleManage filament_solid_ddd">
        <form method="post" id="myForm">
            <!-- �̻��Ƽ�λһ { -->
            <div class="contClassify">
                <h2 class="title">
                    �̻��Ƽ�λһ
                </h2>
                <input type="hidden" value="" name="programaCode1"/>

                <div class="formLine mt15">
                    <label>
                        �Ƽ�λ����:
                    </label>


                    <div class="select-base ml-10">
                        <c:if test="${not empty mt1}">
                            <i class="w-110">${mt1.name}</i>
                        </c:if>
                        <c:if test="${empty mt1}">
                            <i class="w-110">ȫ�����</i>
                        </c:if>
                        <dl>
                            <dt inputValue="" inputName="type">
                                ȫ�����
                            </dt>
                            <c:forEach var="type" items="${typeList}">
                                <dt inputValue="${type.code}" inputName="mm1.merchantTypeCode">
                                        ${type.name}
                                </dt>
                            </c:forEach>
                        </dl>
                        <input id="type" type="hidden" value="${mm1.merchantTypeCode}"
                               name="mm1.merchantTypeCode"/>
                    </div>
                </div>
            </div>

            <div class="formLine">
                <label>
                    �̻�1����:
                </label>
                <input id="" name="mm1.merchantUrl1" type="text"
                       class="w-260 to-link" value="${mm1.merchantUrl1}">
                <label class="w-auto">
                    ����:
                </label>
                <input name="mm1.merchantCode1" type="text" class="w-200 number"
                       value="${mm1.merchantCode1}">
            </div>
            <div class="formLine">
                <label>
                    �̻�2����:
                </label>
                <input id="" name="mm1.merchantUrl2" type="text"
                       class="w-260 to-link" value="${mm1.merchantUrl2}">
                <label class="w-auto">
                    ����:
                </label>
                <input name="mm1.merchantCode2" type="text" class="w-200 number"
                       value="${mm1.merchantCode2}">
            </div>
            <div class="formLine">
                <label>
                    �̻�3����:
                </label>
                <input id="" name="mm1.merchantUrl3" type="text"
                       class="w-260 to-link" value="${mm1.merchantUrl3}">
                <label class="w-auto">
                    ����:
                </label>
                <input name="mm1.merchantCode3" type="text" class="w-200 number"
                       value="${mm1.merchantCode3}">
            </div>
            <div class="formLine">
                <label>
                    �̻�4����:
                </label>
                <input id="" name="mm1.merchantUrl4" type="text"
                       class="w-260 to-link" value="${mm1.merchantUrl4}">
                <label class="w-auto">
                    ����:
                </label>
                <input name="mm1.merchantCode4" type="text" class="w-200 number"
                       value="${mm1.merchantCode4}">
            </div>
    </div>
    <!-- } �̻��Ƽ�λһ -->

    <!-- �̻��Ƽ�λ�� { -->
    <div class="contClassify">
        <h2 class="title">
            �̻��Ƽ�λ��
        </h2>
        <input type="hidden" value="" name="programaCode2"/>

        <div class="formLine mt15">
            <label>
                �Ƽ�λ����:
            </label>

            <div class="select-base ml-10">
                <c:if test="${not empty mt2}">
                    <i class="w-110">${mt2.name}</i>
                </c:if>
                <c:if test="${empty mt2}">
                    <i class="w-110">ȫ�����</i>
                </c:if>
                <dl>
                    <dt inputValue="" inputName="type">
                        ȫ�����
                    </dt>
                    <c:forEach var="type" items="${typeList}">
                        <dt inputValue="${type.code}" inputName="mm2.merchantTypeCode">
                                ${type.name}
                        </dt>
                    </c:forEach>
                </dl>
                <input id="type" type="hidden" value="${mm2.merchantTypeCode}"
                       name="mm2.merchantTypeCode"/>
            </div>
        </div>

        <div class="formLine">
            <label>
                �̻�1����:
            </label>
            <input id="" name="mm2.merchantUrl1" type="text"
                   class="w-260 to-link" value="${mm2.merchantUrl1}">
            <label class="w-auto">
                ����:
            </label>
            <input name="mm2.merchantCode1" type="text" class="w-200 number"
                   value="${mm2.merchantCode1}">
        </div>
        <div class="formLine">
            <label>
                �̻�2����:
            </label>
            <input id="" name="mm2.merchantUrl2" type="text"
                   class="w-260 to-link" value="${mm2.merchantUrl2}">
            <label class="w-auto">
                ����:
            </label>
            <input name="mm2.merchantCode2" type="text" class="w-200 number"
                   value="${mm2.merchantCode2}">
        </div>
        <div class="formLine">
            <label>
                �̻�3����:
            </label>
            <input id="" name="mm2.merchantUrl3" type="text"
                   class="w-260 to-link" value="${mm2.merchantUrl3}">
            <label class="w-auto">
                ����:
            </label>
            <input name="mm2.merchantCode3" type="text" class="w-200 number"
                   value="${mm2.merchantCode3}">
        </div>
        <div class="formLine">
            <label>
                �̻�4����:
            </label>
            <input id="" name="mm2.merchantUrl4" type="text"
                   class="w-260 to-link" value="${mm2.merchantUrl4}">
            <label class="w-auto">
                ����:
            </label>
            <input name="mm2.merchantCode4" type="text" class="w-200 number"
                   value="${mm2.merchantCode4}">
        </div>
    </div>
    <!-- } �̻��Ƽ�λ�� -->

    <!-- �̻��Ƽ�λ�� { -->
    <div class="contClassify">
        <h2 class="title">
            �̻��Ƽ�λ��
        </h2>
        <input type="hidden" value="" name="programaCode3"/>

        <div class="formLine mt15">
            <label>
                �Ƽ�λ����:
            </label>

            <div class="select-base ml-10">
                <c:if test="${not empty mt3}">
                    <i class="w-110">${mt3.name}</i>
                </c:if>
                <c:if test="${empty mt3}">
                    <i class="w-110">ȫ�����</i>
                </c:if>
                <dl>
                    <dt inputValue="" inputName="type">
                        ȫ�����
                    </dt>
                    <c:forEach var="type" items="${typeList}">
                        <dt inputValue="${type.code}" inputName="mm3.merchantTypeCode">
                                ${type.name}
                        </dt>
                    </c:forEach>
                </dl>
                <input id="type" type="hidden" value="${mm3.merchantTypeCode}"
                       name="mm3.merchantTypeCode"/>
            </div>
        </div>

        <div class="formLine">
            <label>
                �̻�1����:
            </label>
            <input id="" name="mm3.merchantUrl1" type="text"
                   class="w-260 to-link" value="${mm3.merchantUrl1}">
            <label class="w-auto">
                ����:
            </label>
            <input name="mm3.merchantCode1" type="text" class="w-200 number"
                   value="${mm3.merchantCode1}">
        </div>
        <div class="formLine">
            <label>
                �̻�2����:
            </label>
            <input id="" name="mm3.merchantUrl2" type="text"
                   class="w-260 to-link" value="${mm3.merchantUrl2}">
            <label class="w-auto">
                ����:
            </label>
            <input name="mm3.merchantCode2" type="text" class="w-200 number"
                   value="${mm3.merchantCode2}">
        </div>
        <div class="formLine">
            <label>
                �̻�3����:
            </label>
            <input id="" name="mm3.merchantUrl3" type="text"
                   class="w-260 to-link" value="${mm3.merchantUrl3}">
            <label class="w-auto">
                ����:
            </label>
            <input name="mm3.merchantCode3" type="text" class="w-200 number"
                   value="${mm3.merchantCode3}">
        </div>
        <div class="formLine">
            <label>
                �̻�4����:
            </label>
            <input id="" name="mm3.merchantUrl4" type="text"
                   class="w-260 to-link" value="${mm3.merchantUrl4}">
            <label class="w-auto">
                ����:
            </label>
            <input name="mm3.merchantCode4" type="text" class="w-200 number"
                   value="${mm3.merchantCode4}">
        </div>
    </div>
    <!-- } �̻��Ƽ�λ�� -->

    <!-- �̻��Ƽ�λ�� { -->
    <div class="contClassify">
        <h2 class="title">
            �̻��Ƽ�λ��
        </h2>
        <input type="hidden" value="" name="programaCode4"/>

        <div class="formLine mt15">
            <label>
                �Ƽ�λ����:
            </label>

            <div class="select-base ml-10">
                <i class="w-112">����</i>

                <input id="type" type="hidden" value="gt"
                       name="mm4.merchantTypeCode"/>
            </div>
        </div>

        <div class="formLine">
            <label>
                �̻�1����:
            </label>
            <input id="" name="mm4.merchantUrl1" type="text"
                   class="w-260 to-link" value="${mm4.merchantUrl1}">
            <label class="w-auto">
                ����:
            </label>
            <input name="mm4.merchantCode1" type="text" class="w-200 number"
                   value="${mm4.merchantCode1}">
        </div>
        <div class="formLine">
            <label>
                �̻�2����:
            </label>
            <input id="" name="mm4.merchantUrl2" type="text"
                   class="w-260 to-link" value="${mm4.merchantUrl2}">
            <label class="w-auto">
                ����:
            </label>
            <input name="mm4.merchantCode2" type="text" class="w-200 number"
                   value="${mm4.merchantCode2}">
        </div>
        <div class="formLine">
            <label>
                �̻�3����:
            </label>
            <input id="" name="mm4.merchantUrl3" type="text"
                   class="w-260 to-link" value="${mm4.merchantUrl3}">
            <label class="w-auto">
                ����:
            </label>
            <input name="mm4.merchantCode3" type="text" class="w-200 number"
                   value="${mm4.merchantCode3}">
        </div>
        <div class="formLine">
            <label>
                �̻�4����:
            </label>
            <input id="" name="mm4.merchantUrl4" type="text"
                   class="w-260 to-link" value="${mm4.merchantUrl4}">
            <label class="w-auto">
                ����:
            </label>
            <input name="mm4.merchantCode4" type="text" class="w-200 number"
                   value="${mm4.merchantCode4}">
        </div>
    </div>
    <!-- } �̻��Ƽ�λ�� -->

    <!-- ���� -->
    <div class="formLine">
        <div class="saveOper">
            <button type="button" class="btn-sure mr30" onclick="preView()">Ԥ��</button>
            <button type="submit" class="save btn-sure mr100" onclick="save()">����</button>
        </div>
    </div>
    </form>
</div>
<!-- } ģ����� -->
</div>
<!-- } main -->
<script src="${ctxManage}/resources/js/dataValidation.js"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
<script>
    function preView() {
        $("#myForm").attr("action", "intoFrontTourmMerchantPrev");
        $("#myForm").attr("target", "_blank");
        $("#myForm").submit();
    }
    $(".to-link").blur(function () {
        var thisVal = $(this).val(),
                nextVal = $(this).siblings('.number').val(),
                nextValLen = nextVal.length > 0;
        var regTest = $.VLDTOR.IsWebUrl(thisVal),
                regNull = $.trim(thisVal) != "",
                thisValClip = thisVal.split('code=')[1];
        if (nextValLen) {
            valid_txtBox_create(this, regTest, '����Ϊ���Ļ�ո�', 'top');
            if (regTest && regNull) {
                if (thisValClip) {
                    $(this).siblings('.number').val(thisValClip);
                }
            }
        } else {
            valid_txtBox_create(this, regTest && regNull, '����Ϊ���Ļ�ո�,�����ű���һ��', 'top');
            if (regTest && regNull) {
                $(this).siblings('.number').next('span.errMesg').remove();
                if (thisValClip) {
                    $(this).siblings('.number').val(thisValClip);
                }
            }
        }
    });
    function save(){
    	 $.ajax( {
    			url : "saveMerchantManage",
    			dataType : "json",
    			type:"post",
    			data : $("#myForm").serialize(),
    			async : false,
    			success : function(data) {
        			alert(data.msg);
    				location.href="${ctx}/web/merchant/gotoMerchantManage";
    			}
    		}); 
    }

</script>
<!-- ���ÿ���ʱ��Ԥ����ָ��ҳ�� -->
<link rel="prefetch">
<!-- IE10+ -->
<link rel="next">
<!-- Firefox -->
<link rel="prerender">
<!-- Chrome -->
</body>
</html>

