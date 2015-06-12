    // Set configuration
    var officalModel = "off";
    var userModel = "user";
    var currentModel = officalModel;
    var officalCurrentPage = 1;
    var userCurrentPage = 1;

    seajs.use(['jquery','avalon','paginator', 'fancybox'],function($){
    	
    	function checkHttp(response){
    		for(var i= 0, length = response.dataList.length; i<length; i++){
		  		var _data = response.dataList[i]
		  		if(_data.url.indexOf('http') != 0){
		  			_data.url = basePath_ + _data.url
		  		}
		  	}
    	}
    	
    	//window.$ = $;
        $("#official").on('click', function () {
            $(this).addClass('active');
            $("#user-upload").removeClass('active');
            $("#official-list").addClass('dis-show');
            $("#userUpload-list").removeClass('dis-show');
            getoffUploadList(officalCurrentPage);
        });
        $("#user-upload").on('click', function () {
            $(this).addClass('active');
            $("#official").removeClass('active');
            $("#official-list").removeClass('dis-show');
            $("#userUpload-list").addClass('dis-show');
    		getuserUploadList(userCurrentPage);
        });
    	function servicesPage(pageId, currentPage, totalPage, call_){
    		var options = {
    	     	currentPage: currentPage || 1,
    	     	totalPages: totalPage || 1,
    	     	//removeClass:'paging-white',
    	     	onPageChanged: function(e,oldPage,newPage){
    	     		call_(newPage);
                    if (oldPage > newPage) {
                        offUploadVM.$index = offUploadVM.$cacheData[offUploadVM.$currentPage].length
                        userUploadVM.$index = userUploadVM.$cacheData[userUploadVM.$currentPage].length
                        $('.fa-prev').trigger('click')
                    }else{
                        offUploadVM.$index = -1
                        userUploadVM.$index = -1
                        $('.fa-next').trigger('click')
                    }
    	    	}
    		}
    		//分页按钮
    		$('#'+pageId).bootstrapPaginator(options);
    	}
        var _flag = true; // true: 官方， false: 用户
    	//官方
    	var offUploadVM = avalon.define({
    		$id:'offUploadView',
    		data:[],
    		$cacheData:{},
            $index:-1,
            $currentPage: 1,
            $totalPage: 1,
            showGallery: function(vmodel, index){
                _flag = true
                offUploadVM.$index = index
                _showGallery(vmodel, index)
            }
    	});
        window.offUploadVM = offUploadVM
    	function getoffUploadList(currentPage){
            offUploadVM.$currentPage = currentPage
    		var thisCall = getoffUploadList;
    		var url=basePath_+"tourism/atlas/loadDestinationAtlas";
    		var params = {
    				pageSize: 32,
    				currentPage: currentPage,
    				type: ATLAS_OFFICAL_,
    				destinationCode:destinationCode_
    		};
    		$.post(url, params, function(response){
    		  	servicesPage("offUploadPaging", response.currentPage, response.totalPage, thisCall);
    		  	checkHttp(response);
    		  	offUploadVM.data = offUploadVM.$cacheData[response.currentPage] = response.dataList;
    		  	avalon.scan();
    		  	officalCurrentPage = response.currentPage;
                offUploadVM.$currentPage = response.currentPage
                offUploadVM.$totalPage = response.totalPage
    		}, 'json');
    	}

    	//用户
    	var userUploadVM = avalon.define({
    		$id:'userUploadView',
    		data:[],
    		$cacheData:{},
            $index:-1,
            $currentPage: 1,
            $totalPage: 1,
            showGallery: function(vmodel, index){
                _flag = false
                userUploadVM.$index = index
                _showGallery(vmodel, index)
            }
    	});
        window.userUploadVM = userUploadVM
    	function getuserUploadList(currentPage){
    		var thisCall = getuserUploadList;
    		var url=basePath_+"tourism/atlas/loadDestinationAtlas";
    		var params = {
    				pageSize: 32,
    				currentPage: currentPage,
    				type: ATLAS_USER_,
    				destinationCode:destinationCode_
    		};
    		$.post(url, params, function(response){
    		  	servicesPage("userUploadPaging", response.currentPage, response.totalPage, thisCall);
    		  	checkHttp(response);
    		  	userUploadVM.data = userUploadVM.$cacheData[response.currentPage] = response.dataList;
    		  	avalon.scan();
    		  	userCurrentPage = response.currentPage;
                userUploadVM.$currentPage = response.currentPage
                userUploadVM.$totalPage = response.totalPage
    		}, 'json');
    	}

    	function loadData(){
    		getoffUploadList(1);
    	}
    	loadData();

        // 预览大图
        // 下一张
        $(document).on('click', '.fa-next', function(){
            var _data = _flag ? offUploadVM.data : userUploadVM.data, // 当前图片列表数据
                _index = _flag ? offUploadVM.$index : userUploadVM.$index; // 当前显示图片下标索引
            _index++;
            var _last = _data.length === _index, // 当前图片是否为当前分页数据的最后一张
                _next = _flag ? (offUploadVM.$currentPage < offUploadVM.$totalPage) : (userUploadVM.$currentPage < userUploadVM.$totalPage);
            if (_last) {
                // 最后一张，如果当前不为最后一页，则加载下一页数据
                if (_next) {
                    $(_flag?'#offUploadPaging':'#userUploadPaging').bootstrapPaginator('showNext')
                }
            }else{
                var vmodel = _data[_index]; // 下一张图片对象
                if (_flag) {
                    offUploadVM.$index = _index;
                }else{
                    userUploadVM.$index = _index
                }
                _showGallery(vmodel, _index)
            }
        })
        // 上一张
        $(document).on('click', '.fa-prev', function(){
            var _data = _flag ? offUploadVM.$cacheData[offUploadVM.$currentPage] : userUploadVM.$cacheData[userUploadVM.$currentPage], // 当前图片列表数据
                _index = _flag ? offUploadVM.$index : userUploadVM.$index; // 当前显示图片下标索引
            _index--;
            var _first = _index === -1, // 当前图片是否为当前分页数据的第一张
                _prev = _flag ? (offUploadVM.$currentPage > 1) : (userUploadVM.$currentPage > 1);
            if (_first) {
                // 第一张，如果当前不为第一页，则加载上一页数据
                if (_prev) {
                    $(_flag ? '#offUploadPaging' : '#userUploadPaging').bootstrapPaginator('showPrevious')
                }
            } else {
                var vmodel = _data[_index]; // 下一张图片对象
                if (_flag) {
                    offUploadVM.$index = _index;
                } else {
                    userUploadVM.$index = _index
                }
                _showGallery(vmodel, _index)
            }
        })
        function _showGallery(vmodel, index){
            var option = {
                href: vmodel.url,
                title: vmodel.name,
                code: vmodel.code
            }
            $.fancybox(option, {
                padding:5,
                openEffect: 'elastic',
                openSpeed: 150,
                closeEffect: 'elastic',
                closeSpeed: 150,
                prevEffect : 'fade',
                nextEffect : 'fade',
                minWidth: 300,
		        minHeight: 300,
                autoCenter:true,
                autoResize:true,
                modal:false,
                arrows:false,
                helpers: {
                    title: {
                        type: 'inslid'
                    }
                },
                afterShow: function(){
                    $.fancybox.reposition()
                    $.fancybox.update()
                    $(".fancybox-like").hide()
                }
            });
        }
    });
