
    function logout() {
        var confirm = window.confirm("是否退出");
        if (confirm) {
            window.location.href = basePath_+'portal/clientLog/logout';
        }
    }
    seajs.use(['common/css', 'activity/css', 'footer/css', 'fancybox/css']);
    seajs.use(['jquery', 'avalon', 'fancybox', 'artDialog/6.0.0/dialog', 'paginator'], function ($, avalon) {
        avalon.define({
            $id: "view"
        })
    });
    seajs.use(['jquery', "bootstrap/3.3.1/js/dropdown.js"], function ($) {
        $('[data-toggle="dropdown"]').dropdown();
    });
    seajs.use(basePathPortal+'/assets/js/tourism/strategy_list.js');
    seajs.use(['jquery', 'artDialog/6.0.0/dialog'], function ($, dialog) {
        var elem = document.getElementById('voteDialog')
        var d = dialog({
            fixed: true,
            content: elem,
            padding: 0
        })
        // 关闭 投票结果
        $('.cd-close').on('click', function (event) {
            event.preventDefault()
            d.close()
        })
        // 显示 投票结果
        $('[data-toggle="vote"]').on('click', function (event) {
            event.preventDefault()
            d.showModal()
        })
    })

    //分页
    seajs.use(["avalon", "jquery", "paginator", "dateUtil"], function (avalon, $) {
        //分页
        function servicesPage(pageId, currentPage, totalPage, call_) {
            var options = {
                currentPage: currentPage || 1,
                totalPages: totalPage || 1,
                removeClass: 'paging-white',
                onPageChanged: function (e, oldPage, newPage) {
                    call_(newPage);
                }
            }
            //分页按钮
            $('#' + pageId).bootstrapPaginator(options);
        }

        // { 存放其他模块数据
        var entriesVM = avalon.define({
            $id: 'entriseView',
            data: [],
            $cacheData: {}
        });
        //获取其他模块数据
        function getOtherBlockList(currentPage) {
            var thisCall = getOtherBlockList;
            //判断缓存没有就请求服务器
            if (entriesVM.$cacheData[currentPage]) {
                entriesVM.data = entriesVM.$cacheData[currentPage];
            } else {
                $.post(basePath_+'portal/activityController/showOtherBlockList', {
                    activityCode: activityCode_,
                    currentPage: currentPage,
                    pageSize: 4
                }, function (response) {
                    servicesPage("entriesPage", response.currentPage, response.totalPage, thisCall);
                    entriesVM.data = entriesVM.$cacheData[currentPage] = response.dataList;
                }, 'json');
            }
        }

        // }
        var _actProductCode;
        var actProductVM = avalon.define({
            $id: "actProductView",
            data: [],
            $index: 0,
            $currentPage: 1,
            $totalPage: 1,
            showBox: function (vmodel, index) {
                actProductVM.$index = index
                var option = {};
                option.href = basePath_ + vmodel.url;

                option.title = vmodel.name
                option.memberName = "BY " + vmodel.memberName
                option.code = _actProductCode = vmodel.code;
                _showBox(option);
            },
            $cacheData: {},
            $like: {}
        });
        window.actProductVM = actProductVM
        // 绑定点赞事件
        $(document).on('click', '.fancybox-like', function () {
            var $this = $(this)
            $.post(basePath_+'portal/activityProductController/clickLike', {code: _actProductCode}, function (response) {
                if (response == "success") {
                    $this.addClass('like-active')
                    actProductVM.$like[_actProductCode] = _actProductCode;
                }
                if (response == "already") {
                    $this.addClass('like-active')
                    actProductVM.$like[_actProductCode] = _actProductCode;
                }
            });
        });
        $(document).on('click', '.fa-prev', function () {
            var c_index = actProductVM.$index;
            var c_page = actProductVM.$currentPage;
            if (c_index == 0) {
                if (c_page != 1) {
                    getActProductList(c_page - 1, "prev");
                }
            } else {
                $('#actProductPage').bootstrapPaginator("show", c_page);
                actProductVM.$index = c_index - 1;
                var _data = actProductVM.data[c_index - 1]
                var option = {};
                option.href = basePath_ + _data.url;
                option.title = _data.name
                option.memberName = "BY " + _data.memberName
                option.code = _actProductCode = _data.code;
                _showBox(option);
            }
        })
        $(document).on('click', '.fa-next', function () {
            var c_index = actProductVM.$index;
            var c_page = actProductVM.$currentPage;
            var t_page = actProductVM.$totalPage;
            if (c_index == actProductVM.data.length - 1) {
                if (c_page < t_page) {
                    getActProductList(c_page + 1, "next");
                }
            } else {
                $('#actProductPage').bootstrapPaginator("show", c_page);
                actProductVM.$index = c_index + 1;
                var _data = actProductVM.data[c_index + 1]
                var option = {};
                option.href = basePath_ + _data.url;
                option.title = _data.name;
                option.memberName = "BY " + _data.memberName
                option.code = _actProductCode = _data.code;
                _showBox(option);
            }
        })
        function _showBox(option) {
            var data = [];
            $.fancybox(option, {
                padding: 5,
                openEffect: 'elastic',
                openSpeed: 150,
                closeEffect: 'elastic',
                closeSpeed: 150,
                prevEffect: 'fade',
                nextEffect: 'fade',
                modal: false,
                arrows: false,
                minWidth: 500,
                minHeight: 500,
                helpers: {
                    title: {
                        type: 'inslid'
                    }
                },
                afterShow: function (e) {
                    $.fancybox.reposition();
                    $.fancybox.update();
                    $('.fancybox-title.fancybox-title-inslid-wrap').append('<span style="margin-top: 0;" class="child">' + option.memberName + '</span>')
                    if (actProductVM.$like[_actProductCode]) {
                        $(".fancybox-like").addClass('like-active')
                    }
                }
            });
        }

        window.$ = $
        //获取作品数据
        function getActProductList(currentPage, type) {
            var thisCall = getActProductList;
            if (actProductVM.$cacheData[currentPage]) {
                actProductVM.data = actProductVM.$cacheData[currentPage];
                actProductVM.$currentPage = currentPage;
                if (type == 'next') {
                    actProductVM.$index = -1
                    $('.fa-next').trigger('click')
                }
                if (type == 'prev') {
                    actProductVM.$index = actProductVM.data.length
                    $('.fa-prev').trigger('click')
                }
            } else {
                $.post(basePath_+'portal/activityProductController/showActivityDetailActivityProductList', {
                    activityCode: activityCode_,
                    currentPage: currentPage,
                    pageSize: 4
                }, function (response) {
                    actProductVM.$currentPage = response.currentPage
                    actProductVM.$totalPage = response.totalPage
                    servicesPage("actProductPage", response.currentPage, response.totalPage, thisCall);
                    actProductVM.data = actProductVM.$cacheData[currentPage] = response.dataList;

                    if (type == 'next') {
                        actProductVM.$index = -1
                        $('.fa-next').trigger('click')
                    }
                    if (type == 'prev') {
                        actProductVM.$index = actProductVM.data.length
                        $('.fa-prev').trigger('click')
                    }
                }, 'json');
            }
        }

        //进入页面初始列表
        function servicesLoader() {
            getOtherBlockList(1);
            getActProductList(1);
        }

//        servicesLoader();
    });



    function addEvent(up, urlHiddenId, fileNameHiddenId) {
        up.on('uploadSuccess', function (file, response) {
            $("#" + fileNameHiddenId).siblings(".file-ok").remove();
            $("#" + urlHiddenId).val(response.filePath);
            $("#" + fileNameHiddenId).val(file.name);

            var name = file.name.replace("." + file.ext, "");
            if (name.length > 5) {
                name = name.substr(0, 5) + "...";
            }
            file.name = name + file.ext;
            var o = "";
            o += "<div class='file-ok'>"
            o += "	<img src='"+basePathPortal+"/assets/icon/ac_success.png' alt=''/>"
            o += "	<span class='ac-success'>已成功</span><br />"
            o += "	<span class='ac-document'>" + file.name + "</span>"
            o += "</div>";
            $("#" + urlHiddenId).parent().append(o);
            this.reset();
        });
        up.on('uploadError', function (file, reason) {
            //$.alert("上传格式错误");
            _alert.show("上传格式错误");
        });
        up.on('error', function (type) {
            //alert("错误信息:"+type);
            if (type == "Q_EXCEED_SIZE_LIMIT" || type == "F_EXCEED_SIZE") {
                _alert.show("文件超过大小");
            } else if (type == "Q_TYPE_DENIED") {
                _alert.show("上传格式错误");
            } else if (type == "Q_EXCEED_NUM_LIMIT") {
                _alert.show("最多上传5张图片，请重新选择文件。");
            } else {
                _alert.show("上传错误，请重试。");
            }
        });
    }
    function cteateUploder(pickId, fileExt, urlHiddenId, fileNameHiddenId, floderName, fileSingleSizeLimit, compress) {
        var options_ = {
            swf: basePath_ + '/manage/webuploader/Uploader.swf',
            server: basePath_ + '/portal/activityController/fileUpload',
            runtimeOrder: "flash",
            accept: {extensions: fileExt},
            formData: {
                activityCode:activityCode_,
                floderName: floderName
            },
            pick: {
                id: '#' + pickId,
                multiple: false
            },
            fileVal: 'files',
            auto: true,
            resize: false,
            fileSingleSizeLimit: fileSingleSizeLimit,
            compress: compress
        };
        var uploader = new WebUploader.Uploader(options_);
        addEvent(uploader, urlHiddenId, fileNameHiddenId);
        return uploader;
    }

    //初始化上传组件
    var fileSingleSizeLimit = 2 * 1024 * 1024;
    var banner_compress = {
        width: 1140,
        height: 420,
        crop: true
    };
    var other_compress = {
        width: 188,
        height: 188,
        crop: true
    };

    var uploaderArray = new Array();
    function deleteUpload() {
        for (var i = 0; i < uploaderArray.length; i++) {
            uploaderArray[i].destroy();
        }
        uploaderArray = new Array();
    }
    function initUpload() {
        //报名表单
        var text = PROPERTY_TYPE_TEXT;
        var number = PROPERTY_TYPE_NUMBER;
        var select = PROPERTY_TYPE_SELECT;
        for (var i = 0; i < listEnrollForm_.length; i++) {
            var obj = listEnrollForm_[i];
            if (obj.propertyType != text && obj.propertyType != number && obj.propertyType != select) {
                var enrollHiddenUrl = "enrollHiddenUrl" + i;
                var enrollHiddenFileName = "enrollHiddenFileName" + i;
                var up = cteateUploder("enrollPickId" + i, obj.propertyType.split(".").join(""), enrollHiddenUrl, enrollHiddenFileName, 'enrollform', fileSingleSizeLimit, false);
                uploaderArray.push(up);
            }
        }
    }
    var actUploder;
    seajs.use(['jquery'], function ($) {
        $(function () {
            //打开报名窗
            $("#apply-btn").on('click', function () {
                if (isEnd_ == 'yes') {
                    _alert.show("活动已结束");
                } else {
                    var height = $("#sign-up").height();
                    $(".sign-left").height(height);

                    var url = basePath_+"portal/memberEnrollDetailController/checkEnroll";
                    var data = {activityCode: activityCode_};
                    $.post(url, data, function (response) {
                        if (response == "") {
                            $("#shielding-layer").addClass('dis-show').removeClass('dis-hidden');
                            $("#apply").addClass('dis-show').removeClass('dis-hidden');
                            $("#sign-up").addClass('dis-show').removeClass('dis-hidden');
                            initUpload();
                        } else {
                            if (response == '请先登录') {
                                $('[data-toggle="login"]').trigger('click');
//                            $('#login-btn').click();
                            } else {
                                _alert.show(response);
                            }
                            // 打开 登录框
                        }
                    });
                }
            });

            //支付
            function pay() {
                if (isEnd_ == 'yes') {
                    _alert.show("活动已结束");
                } else {
                    var url = basePath_+"portal/order/checkPay";
                    $.post(url, {activityCode: activityCode_, OCS: OCS_}, function (response) {
                        if (response == 'need_login') {
                            ///	alert("请先登录");
                            // 打开 登录框
                            $('[data-toggle="login"]').trigger('click');
                        } else if (response == 'need_enroll') {
                            //alert("请先报名");
                            var height = $("#sign-up").height();
                            $("#sign-up .sign-left").height(height);
                            $("#shielding-layer").addClass('dis-show').removeClass('dis-hidden');
                            $("#apply").addClass('dis-show').removeClass('dis-hidden');
                            $("#sign-up").addClass('dis-show').removeClass('dis-hidden');
                            initUpload();
                            $(window).scrollTop(0);
                        } else if (response == 'already_pay') {
                            _alert.show("已经支付");
                        } else if (response == 'error') {
                            _alert.show("生成订单发生错误");
                        } else if (response == 'to_pay') {
                            $("#payForm").attr("action", basePath_+"/alipay/pay");
                            $("#payForm").submit();
                        }
                    });
                }
            }

            //报名支付
            $('.J_pay').on('click', function () {
                pay();
            })
            //报名后支付
            $('#J_PayAfterEnroll').on('click', function () {
                pay();
            })
            //立即购买
            $('#J_NowPay').on('click', function () {
                pay();
            })
            //关闭
            $("#apply-close").on('click', function () {
                deleteUpload();
                $("#shielding-layer").addClass('dis-hidden').removeClass('dis-show');
                $("#apply").addClass('dis-hidden').removeClass('dis-show');
            });

        })
    })

    $(document).on('click', '.off', function () {
        var thisparent = $(this).parents('.sign-up');
        thisparent.addClass('dis-hidden').removeClass('dis-show');
        $("#shielding-layer").addClass('dis-hidden').removeClass('dis-show');
    });

    //报名提交
    function addEnrollForm() {
        $("#apply input[name='files']").attr('disabled', "true");
        var $form = $('#enrollForm');
        var url = basePath_+'portal/memberEnrollDetailController/addMemberEnrollDetail';
        //alert($form.serialize());
        if (yanz()) {
            $.post(url, $form.serialize(), function (data) {
                if (data == 'success') {
                    $("#apply-close").click();
                    $("#sign-up").addClass('dis-hidden').removeClass('dis-show');
                    $("#pay-money").addClass('dis-show').removeClass('dis-hidden');
                    _alert.show("保存成功");
                } else if (data == 'error') {
                    $("#apply input[name='files']").removeAttr("disabled");
                    _alert.show("保存失败");
                } else if (data == 'need_login') {
                    $("#apply input[name='files']").removeAttr("disabled");
                    _alert.show("请先登录");
                }
            });
        }
    }

    //报名验证必填
    function yanz() {
        var temp = false;
        var msg = "";
        var inputNode = $("input.baomyz");
        var regName = /^(\S|\s){2,30}$/,
                regNum = /^[0-9]{1,30}$/;
        for (var i = 0; i < inputNode.length; i++) {
            var val = inputNode.eq(i).val();
            var prototypeName = inputNode.eq(i).attr("prototypeName");
            inputNode.eq(i).val($.trim(val))
            if (inputNode.eq(i).attr("isRequire") == 1) {
                if (inputNode.eq(i).attr("proType") == PROPERTY_TYPE_TEXT) {
                    if (!regName.test(val) || $.trim(val) == "") {
                        temp = true;
                        msg = prototypeName + ",需要输入2-30个字符";
                        break;
                    }
                } else if (inputNode.eq(i).attr("proType") == PROPERTY_TYPE_NUMBER) {
                    if (!regNum.test(val)) {
                        temp = true;
                        msg = prototypeName + ",最多输入30个数字";
                        break;
                    }

                } else {
                    if (val == "") {
                        temp = true;
                        msg = prototypeName + ",为必填项";
                        break;
                    }
                }
            }
        }


        if (temp) {
            _alert.show(msg);
            return false;
        }
        return true;
    }



    window._bd_share_config = {
        "common": {
            //"bdText": $("#share").text(), // 分享的内容
            "bdText" : share_activity_name, // 分享的内容
            "bdDesc": "", // 分享的摘要
            'bdComment':'骑行吧，去西藏！史上最有保障的川滇骑行，即可报名啦！',
            "bdUrl": "", // 分享的Url地址
            "bdPic": share_activity_img // 分享的图片
        },
        "share": {
            "bdSize": 24,
            "bdCustomStyle": basePathPortal+'/assets/css/common.css'
        }
    };
    with (document)0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)]; 
