    seajs.use(["jquery", "avalon", "paginator", basePathPortal+'/modules/slick/slick.js'], function() {
    	// 热门景点列表
    	var spotModel = avalon.define({
    		$id: 'hotspots',
    		spots:[]
    	})

    	var _temp = []
    	for (var i = 0; i<18; i++) {
    	}
    	var _spots = [], count = 0, tempArray = []
    	for (var i = 0; i < _temp.length; i++) {
    		count++
  			tempArray.push(_temp[i])
    		if (count == 10) {
    			_spots.push(tempArray)
    			count = 0
    			tempArray = []
    		}
    	};
			_spots.push(tempArray)
			spotModel.spots = _spots
    	var $sub;
    	$('[data-toggle="dropdown"]').click(function() {
    		var $this = $(this),
    			$sub = $this.siblings('.dropdown-menu');
    		$('.dropdown-menu').not($sub).hide()
    		$toggle = $sub.is(':visible');
    		if ($toggle) {
    			$sub.hide();
    		} else {
    			$sub.show()
    		}
    	})
    	$(document).on('click', '.dropdown-menu>li', function() {
    			var $this = $(this),
    				$name = $this.data('name'),
    				$code = $this.data('code'),
    				$text = $this.text(),
    				$parent = $this.parent();
    			$('input[name="' + $name + '"]').val($code);
    			$parent.hide().siblings('button').children('.text').text($text);
    		})
    		//
    		//分页
    	function servicesPage(pageId, currentPage, totalPage, call_) {
    		var options = {
    				currentPage: currentPage || 1,
    				totalPages: totalPage || 1,
    				//removeClass:'paging-white',
    				onPageChanged: function(e, oldPage, newPage) {
    					call_(newPage);
    				}
    			}
    			//分页按钮
    		$('#' + pageId).bootstrapPaginator(options);
    	}
    	var allActivityVM = avalon.define({
    		$id: 'allActivityView',
    		data: [],
    		$cacheData: {}
    	});

    	function getAllActivityList(currentPage) {
    		var thisCall = getAllActivityList;
    		//判断缓存没有就请求服务器
    		if (allActivityVM.$cacheData[currentPage]) {
    			allActivityVM.data = allActivityVM.$cacheData[currentPage];
    		} else {
    			$.post(basePath_+'tourism/merchant/avalonGroupTravelList', {
    				rule: rule_,
    				type: type_,
    				viewCode: viewCode_,
    				destinationCode: destinationCode_,
    				keyWord: keyWord_,
    				currentPage: currentPage,
    				pageSize: 10
    			}, function(response) {
    				servicesPage("allActivityPage", response.currentPage, response.totalPage, thisCall);
    				allActivityVM.data = allActivityVM.$cacheData[currentPage] = response.dataList;
    				var  reuslt  = response.dataList.length==0;
				  	if(reuslt){
				  		    $("#allActivityPage").remove();
							$(".bd-heading").append("<div class='nodata'></div>");		
					  	};
    			}, 'json');
    		}
    	}
    	avalon.scan();

    	function loadData() {
    		getAllActivityList(1);
    	}
    	loadData();
    });


    //================排序====================
    function searchByOrder(obj) {
    		var $obj = $(obj);
    		var rule = $obj.attr('id');
    		var viewCode = $("#viewCode").val();
    		var destinationCode = $("#destinationCode").val();
    		var keyWord = $("#keyWord").val();
    		var type = "";
    		if (type_) type = type_;
    		//console.log(rule + "-----" + destinationCode + "-------" + viewCode + "-----------" + keyWord + "--------" + type);
    		$.ajax({
    			type: "POST",
    			url: basePath_+"tourism/merchant/merchantList",
    			data: {
    				rule: rule,
    				viewCode: viewCode,
    				destinationCode: destinationCode,
    				keyWord: keyWord,
    				type: type
    			},
    			dataType: "json",
    			success: function(data) {
    			}
    		})
    	}
    	//================根据目的地获取景点================
    function getViews(obj) {
    	var $obj = $(obj);
    	var destinationCode = $obj.attr('data-code');
    	$.ajax({
    		type: "POST",
    		url: "getViews",
    		data: {
    			destinationCode: destinationCode
    		},
    		dataType: "json",
    		success: function(data) {
    		}
    	})
    }
		
				function clickAderSta(aderCode, hrefUrl) {
					frontContentStatic('781bc9785-1231-12a4-bab-0211056a05bce', 'ad_area', aderCode, 'click');
					window.open(hrefUrl);
				}
		
	  seajs.use('map', function(myMap){
	  	// 初始化景点地图
          myMap.init(destinationName_)
	  	  myMap.mapChange(destinationName_);
	  })

   /**
     * ajax 请求路径 查询 目的地对应的  景点
     * @param destinationCode
     * @return
     */
    function  ajaxGetViewByDescode(destinationCode){
    	//
    	var vl = $(destinationCode).attr('data-code');//请求值
        $.ajax(
            {
                url: basePath_+"/tourism/merchant/getViewByDescode",
                data: "destinationCode=" + vl,
                type: 'POST',
                success: function (data) {
                    //解析json  js inputTextValue="${destination.destinationName}" inputTextName="destinationCodeText"
                    var viewsJSON = $.parseJSON(data);
                    var htmlViews = '<li role="presentation" inputValue="" inputName="viewCode" inputTextValue="全部景点" inputTextName="viewCodeText"><a href="javascript:void(0);" role="menuitem" tabindex="-1">全部景点</a></li>';
                    $(viewsJSON).each(function (i) {
                        htmlViews = htmlViews + "<li role='presentation' inputValue='" + this.code + "'  inputName='viewCode'  inputTextValue='" + this.viewName + "' inputTextName='viewCodeText' ><a href='javascript:void(0);' role='menuitem' tabindex='-1'>" + this.viewName + "</a></li>";
                    });
                    $("#viewName").html("选择景点");
                    $("#appendView").html(htmlViews);
                    bindingSubmit()
                }
            }
        );
   	 }

    /**
     * 绑定事件
     * @return
     */
    function bindingSubmit(){
    	
    	// 下拉框  选择内容，并隐藏选择菜单(点击) 同时触发 绑定查询条件值
        $(".dropdown ul li").click(function () {
            //同时获取当前 dt的 value 值  ,然后开始绑定
            var inputValue = $(this).attr("inputValue");
            var inputName = $(this).attr("inputName");

            var inputTextName = $(this).attr("inputTextName");
            var inputTextValue = $(this).attr("inputTextValue");

            var myMethod = $(this).attr("myMethod");
            if (inputTextName != undefined) {
                $("input[name='" + inputTextName + "']").val(inputTextValue);
            }
            if (inputName != undefined) {
                $("input[name='" + inputName + "']").val(inputValue);

                //ajax处理
                if (myMethod) {
                    //调用自己的方法
                    var obj = "input[name='" + inputName + "']";
                    var ajaxMethodString = myMethod + '("' + obj + '")';
                    eval(ajaxMethodString);
                }
            }
        });
    }
    function  onclickHref(el,type){
    	var  href   =  el.ctripUrl;
    	if(href){
	    	frontContentStatic(type,'merchant',el.code,'href');
	    }else{
				href=basePath_+el.url;
		 }
		window.open(href);

	}
  	
		
		    function adver(href,code){
				    frontContentStatic('781bc9785-1231-12a4-bab-0211056a05bce','content',code,'click');
				    window.open(href);
			    }
