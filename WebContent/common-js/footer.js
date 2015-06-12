seajs.use(['jquery', 'goup/1.0.0/jquery.goup.js', 'placeholder/jquery.placeholder.js'], function($){
    $(function(){
        $('input').placeholder()
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
                url : "${ctx }web/surguestion/save",
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
        })


        /**
         * 页面加载成功时，查询已赞 && 已收藏的信息
         */
        var zanActive = $(".zan_active");	// 获取页面所有赞按钮
        var collectActive = $(".collect_active");	// 获取页面所有收藏按钮

        if(zanActive.length > 0){	// 判断页面中是否有已赞按钮，如果有
            //zanActive.removeAttr('onclick');	// 移除按钮的onclick属性
        }
        if(collectActive.length > 0){	// 判断页面中是否有已收藏按钮，如果有
            collectActive.removeAttr('onclick');	// 移除按钮的onclick属性
        }
    })
})

window.alert = function(text,fn){
    var _template = $('<div class="rimi-alert-mark"></div><div class="rimi-alert"><div class="rimi-alert-header"></div><div class="rimi-alert-body">' + text + '</div><div class="rimi-alert-bottom"><button class="btn btn-danger btn-sm mzl-alert-close" id="mzl-sure">确定</button></div></div>');
    $('body').append(_template);
    _template.find('.mzl-alert-close').on('click',function(){
        _template.remove();
        if(this.id == 'mzl-sure') {
            if (typeof fn === 'function') {
                fn();
            }else {
                eval(fn)
            }
        }
    })
}
function chklen(obj,max){
    var val=obj.value;
    if(val.length>max){
        obj.value=val.substring(0,max);
    }
}
/**
 * 微信二维码弹出层
 * @param ur
 */
function wx(ur){
    var _tempNode = $('<div class="rimi-alert-mark"></div><div class="wx-main"><img src='+ ur +' alt="weixin" /><i class="wx-close"></i></div>');
    $('body').append(_tempNode);
    _tempNode.find('.wx-close').on('click',function(){
        _tempNode.remove();
    })
}

//百度流量页面统计
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?577bcb4d93163c5343aba29db1bc982c";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
//国双科技流量统计
window.onload=function(){
	window.GridsumSnapshotID=0
}

/**
 * url 重写
 * @param url
 * @param arrays
 * @return
 */
function urlrewriter(url,arrays){
	var  urlresult  = url ;
	var arr  = arrays;
	for ( var i = 0; i < arr.length; i++) {
		var  param  = arr[i];
		urlresult+=param;
	}
	return urlresult;
}




