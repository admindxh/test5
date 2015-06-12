seajs.use(['jquery', 'goup/1.0.0/jquery.goup.js', 'placeholder/jquery.placeholder.js'], function($){
    $(function(){
        $('input').placeholder()
        $.goup()
        $("#sug").on('click',function(){
            $("#ground-floor").addClass('dis-show');
            $("#suggestions").addClass('dis-show');
        });
        $("#colse-sug").on('click',function(){
            $("#ground-floor").removeClass('dis-show');
            $("#suggestions").removeClass('dis-show');
            $('#content').val('');
        });
        $('#subtn').on('click',function(){
            var content=$('#content').val();
            if(content==''){
                alert("请输入内容");
                return;
            }
            $.ajax({
                type : "post",
                url : contentPath+"web/surguestion/save",
                data : {content:content},
                dataType : "json",
                async : true,
                success : function(data) {
                    if(data.status=='1'){
                        alert("您的建议已经提交");
                        $("#ground-floor").removeClass('dis-show');
                        $("#suggestions").removeClass('dis-show');
                        $('#content').val('');
                    }
                    if(data.status=='0'){
                        alert("保存失败，请刷新后重新操作");
                    }
                }
            })
        });
        /**
         * 页面加载成功时，查询已赞 && 已收藏的信息
         */
        var zanActive = $(".zan_active"); // 获取页面所有赞按钮
        var collectActive = $(".collect_active"); // 获取页面所有收藏按钮
        if(zanActive.length > 0){	// 判断页面中是否有已赞按钮，如果有
            //zanActive.removeAttr('onclick'); // 移除按钮的onclick属性
        }
        if(collectActive.length > 0){ // 判断页面中是否有已收藏按钮，如果有
            collectActive.removeAttr('onclick'); // 移除按钮的onclick属性
        }
    })
});
window.alert = function(text,fn){
    var _template = $('<div class="rimi-alert-mark"></div><div class="rimi-alert"><div class="rimi-alert-header"></div><div class="rimi-alert-body">' + text + '</div><div class="rimi-alert-bottom"><button class="btn btn-danger btn-sm mzl-alert-close" id="mzl-sure">确定</button></div></div>');
    $('body').append(_template);
    _template.find('#mzl-sure').on('click',function(){
        _template.remove();
        if(this.id == 'mzl-sure') {
            if (typeof fn === 'function') {
                fn();
                console.log(11);
            }else {
                console.log(22);
                eval(fn)
            }
        }
    })
};
function chklen(obj,max){
    var val=obj.value;
    if(val.length>max){
        obj.value=val.substring(0,max);
    }
}
seajs.use(['jquery', 'easing/1.3/jquery.easing.js'], function($) {
    $(function(){
        //收藏本页代码 keleyi.com
        $("#fav").click(function(){
            URL=window.location.href;
            title=document.title;
            try{
                window.external.addFavorite(URL, title);
            }catch (e){
                try {
                    window.sidebar.addPanel(title, URL, "");
                }catch (e){
                    alert("加入收藏失败，请使用Ctrl+D进行添加");
                }
            }
        });
        $('.qr-close').click(function(event) {
            var _parent = $(this).parent();
            _parent.slideUp(function () {
                _parent.remove();
            });
        });

        $(window).scroll(function () {
            var $this = $(this);
            if($this.scrollTop() >= 200){
                $("#qrcode").slideDown();
            }else{
                $("#qrcode").slideUp();
            }
        });

        var index  = programNam;
        $("#topNavbar li").eq(index).addClass("active");
        var $floating = $('#floating'), $topNavbar = $('#topNavbar'), $actived = $topNavbar.children('li.active');
        $topNavbar.children('li').mouseenter(function(event) {
            _animateTo($(this))
        });
        $topNavbar.mouseleave(function(event) {
            _animateTo($actived)
        });
        _animateTo($actived);
        function _animateTo(ele){
            if (!ele.length) {
                $floating.stop().animate({width: 0}).siblings().removeClass('active');
                return false;
            }
            var $ele = ele,
                $w = $ele.width(),
                $h = $ele.height(),
                $offset = $ele.position(),
                $left = $offset.left;
            if ($ele.index() == 1) {
                $ele.addClass('active menu-arrow').siblings().removeClass('active')
            }else{
                $ele.addClass('active').siblings().removeClass('active menu-arrow')
            }
            $floating.stop().animate({left:$left, width: $w},'fast')
        }
        $.ajax({
            type : "post",
            url : contentPath+"member/userinfo/getUnReadCount",
            dataType : "json",
            async : true,
            success : function(data) {
                if(data.status=='1'){
                    if(data.msg=='0'){
                        $('.badge').remove();
                    }else{
                        $('.badge').show();
                        $('.badge').text(data.msg);
                    }
                }
            }
        })
    });
});
/**
 * 退出
 */
function logout(){
    var _alert = new Alert();
    _alert.show("是否退出", function(){
        window.location.href=contentPath+'portal/clientLog/logout';
    })
}
function toUserCenter(){
    if(!checkPortalUserLongin()){
        alert('请 先登录!');
        // 打开 登录框
        $('[data-toggle="login"]').click();
        return ;
    }
    window.location.href=contentPath+'member/userinfo/getAllMyMsg';
}
/**
 * 判断当前会员用户是否登录
 * @return
 */
function checkPortalUserLongin(){
    var  user  = logUser;
    if (user) {
        return true;
    }else{
        return false;
    }
}