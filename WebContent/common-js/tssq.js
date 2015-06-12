seajs.use(['jquery', 'slick/slick.js'], function ($) {
        $(".praised .header div.huif").on('click', function () {
            $("#huif").css("display", "block");
            $("#beiz").css("display", "none");
        })
        $(".praised .header div.beiz").on('click', function () {
            $("#beiz").css("display", "block");
            $("#huif").css("display", "none");
        })
        $(".lunt").on('click', function () {
            $("#lunt").css("display", "block");
            $("#post-rank").css("display", "none");
        })
        $(".post-rank").on('click', function () {
            $("#lunt").css("display", "none");
            $("#post-rank").css("display", "block");
        })


        var $slide = $('.lunb');
        var $thumb = $('.slick-dots').children('li');
        $slide.slick({
            autoplay: true,
            arrows: false,
            dots: true,
            slide: '.watch'
            /*onBeforeChange: function (slide) {
                setTimeout(function () {
                    $thumb.eq(slide.currentSlide).addClass('active').siblings().removeClass('active');
                }, 0)
            }*/
        });

        $thumb.on('click', function (event) {
            event.preventDefault();
            $slide.slickGoTo($(this).index())
        })
    })
    
    
    
    
    
    function clickAderSta(aderCode, hrefUrl) {
        frontContentStatic('3452871b-7422-33b2-b6de-349202605b2a', 'ad_area', aderCode, 'click');
        window.open(hrefUrl);
    }

    //截取字符串，多余的用省略号显示
    function strClip(ele, num) {
        var tempNode = ele.text();
        if (tempNode.length >= num) {
            ele.text(tempNode.substr(0, num - 2) + "...");
        }
    }

    seajs.use('jquery', function () {
        $(function () {
            var str50 = $(".str50 p");
            for (var i = 0; i < str50.length; i++) {
                strClip(str50.eq(i), 50);
            }

            /* 点赞之后按钮变灰 */
            $(document).on('click', '.zan', function () {
                var $this = $(this),
                thisCode = $this.attr('data-code');
                imCode = $this.attr('im-code'); 
                isRecoredTssq(thisCode, $this,imCode);
            })
        });

        $(document).on('click', '.zhankai', function () {
            var $this = $(this),
                    pNode = $this.prev().prev().prev(),
                    pInfo = $this.prev('.infoHidden').text();
            pNode.text(pInfo);

        });

        $('.huif').on('click', function () {
            $(this).addClass('huif-active');
            $('.beiz').removeClass('beiz-active');
        });

        $('.beiz').on('click', function () {
            $(this).addClass('beiz-active');
            $('.huif').removeClass('huif-active');
        });

        $('.lunt').on('click', function () {
            $(this).addClass('lunt-active');
            $('.post-rank').removeClass('post-rank-active');
        });

        $('.post-rank').on('click', function () {
            $(this).addClass('post-rank-active');
            $('.lunt').removeClass('lunt-active');
        });
    })
    
    
    
    /**
     * 判断当前电脑是否登录  首页
     * @return
     */
    function isRecoredTssq(code,vs,imCode){
        $.ajax({
            url:ctx+"/web/praiseController/isRecored",
            data:"code="+code,
            type: 'POST',
            success:function(data){
                if(data=="true"){
                    //表示改电脑已经点击赞了
                    alert('已赞');
                }else {
                    //就保存
                	alert('点赞成功');
                    clickPraiseFronttssq(code, vs, imCode);
                    $(vs).addClass("zan_active");
                    $(vs).removeAttr('onclick');
                }
            }}
        )
    }
    /**
     * 点击赞
     * @return
     */
    function clickPraiseFronttssq(code,vs,imCode){
        $.ajax({
            url:ctx+"/web/praiseController/clickPraiseFront",
            data:{"code":code,"imCode":imCode},
            dataType:'json',
            type: 'POST',
            success:function(data){
                $(vs).parent().find("label").html(data);
            }}
        )
    }