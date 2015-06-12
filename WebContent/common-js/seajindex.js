seajs.use(['common/css', 'content/top/css', 'youxz/css','tusxz/css','duxz/css','index_culture/css','footer/css']);
seajs.use(ctxPortal+'modules/slick/slick.css');
seajs.use(['jquery', ctxPortal+'modules/slick/slick.js','lazyload/jquery.lazyload.js'], function($){
    $('.gallery-box').slick({
        autoplay:true,
        dots: true,
        onInit: function(){
            $(this.$slider).removeClass('overflow')
        }
    });
   
});
seajs.use(['jquery', ctxPortal+'modules/bootstrap/3.3.1/js/tooltip'], function($){
    $('[data-toggle="tooltip"]').tooltip()
});
seajs.use('slick/slick.css');
seajs.use(['jquery', 'slick/slick.js'], function($){
    $('.circulation').slick({
        autoplay:true,
        dots: true
    });
});
seajs.use(['jquery', 'bootstrap/3.3.1/js/tooltip'], function($){
    $('[data-toggle="tooltip"]').tooltip()
});
seajs.use('slick/slick.css');
seajs.use(['jquery',  ctxPortal+'modules/slick/slick.js'], function($){
    $('.taste-info-header').slick({
        autoplay:true,
        dots: true
    });
    var $control = $('.lunbo-control>ul>li');
    $('.img-thumb').slick({
        autoplay:true,
        fade: true,
        onAfterChange: function(slide){
            $control.removeClass('active').eq(slide.currentSlide).addClass('active')
        }
    });
    $control.click(function(event) {
        $('.img-thumb').slickGoTo($(this).index())
    });
});
seajs.use(['jquery', 'bootstrap/3.3.1/js/tooltip'], function($){
    $('[data-toggle="tooltip"]').tooltip()
});
// Set configuration
seajs.use(['common/css', 'discover/css', 'footer/css', 'slick/slick.css']);
seajs.use(['avalon', 'slick/slick.js'], function(avalon) {
    avalon.define({
        $id: "view",
        showTag: true,
        cls:['hotel', 'food', 'shyl', 'cxty']
    });
    $('#slide').slick({
        autoplay: true,
        dots: true,
        arrows: false,
        fade: true
    })
});
seajs.use(['jquery','avalon'],function(){
    $(function(){
        avalon.scan();
    });
});
var string;
seajs.use(ctxPortal+'assets/js/subStri.js',function(str){
    //console.log(str);
    str.strClip('.js_sub_summary',120);
    str.strClip('.js_sub_summary2',35);
    str.strClip('.js_sub_title',18);
    str.strClip('.js_sub_summary3',25);
    str.strClip('.js_sub_summary4',20);
    string = str;
});
seajs.use('jquery', function($){
    $('.hot-tags>a').on('click', function(){
        var $text = $(this).text();
        $('#searchkey').val($text);
        $('#searchfrm').submit();
    });
});
seajs.use(ctxPortal+'/assets/css/index_culture.css');
seajs.use('jquery', function(){
    $('.clt-other>a').on('click', function(){
        var $index = $(this).index();
        $(this).addClass('active').siblings().removeClass('active');
        $(this).parents('.xz-heading').siblings('.music').eq($index).show().siblings('.music').hide()
    })
});
seajs.use(['jquery','raty/jquery.raty.js'],function($){
    $('.m-score').raty({
        readOnly: true,
        space: true,
        score: function() {
            var $this = $(this),
                score = $this.data('score') || '';
            $this.append('&nbsp;&nbsp;<span>' + score + '</span>');
            return (score);
        },
        half: true,
        starHalf: ctxPortal+'/assets/icon/star_half.png',
        starOff: ctxPortal+'/assets/icon/star_zero.png',
        starOn: ctxPortal+'/assets/icon/star_full.png'
    })
});