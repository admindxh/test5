		seajs.use(['common/css','footer/css']);
		seajs.use(['jquery', 'avalon','player', 'paginator','artDialog/6.0.0/dialog', 'raty/jquery.raty.js', 'ion.rangeSlider/js/ion.rangeSlider.js'], function($, avalon, music) {
			var shareModel = avalon.define({
				$id: 'share',
				info: {}
			})
			window.shareModel = shareModel;
			var storyModel = avalon.define({
				$id: "storyView",
				// 分页数据
				$data:{},
				$type:'story',
				list:[],
				rendered: function(){
					initScore($(this).find('.m-score'))
				},
				$cacheData:{},
				order: function(type,ordrtype){
					order.call(this,type,ordrtype)
				}
			})
			
			var gameModel = avalon.define({
				$id: "gameView",
				// 分页数据
				$type:'game',
				$data:{},
				list:[],
				rendered: function(){
					initScore($(this).find('.m-score'))
				},
				$cacheData:{},
				order: function(type,ordrtype){
					order.call(this,type,ordrtype)
				}
			});
			$('#shareDialog .cd-close').click(function(){
				$('.sdmark, #shareDialog').hide()
			})
			var musicModel = avalon.define({
				$id: "musicView",
				// 分页数据
				$data:{},
				list:[],
				$type:'music',
				_config:{},
				rendered: function(a,b,c){
					$(".J_irs").ionRangeSlider({
						min: 0,
				    	max: 100,
				    	hide_min_max: true,
				    	hide_from_to: true,
				    	disable: true,
				    	onChange: function (data) {
				    		var attr_mid = data.input.data('mid')
				    		$('#' + attr_mid).jPlayer("playHead", data.from)
				    		// console.log(attr_mid, data.from)
					    }
					});
					if(b){
						music.init();
					}
					initScore($(this).find('.m-score'))
				},
				$cacheData:{},
				favorite: function(code) {
					
					favorite(code,this);
				},
				praise: function(code) {
					praise(code,this);
				},
				share: function(el) {
					$('.sdmark, #shareDialog').show()
					shareModel.info = el.$model
				},
				order: function(type, ordrtype) {
					order.call(this, type, ordrtype)
				}
			});
			var otherlistModel = avalon.define({
				$id: "otherlist",
				// 分页数据
				$data:{},
				list:[],
				$type:'other',
				rendered: function(){
					initScore($(this).find('.m-score'))
				},
				$cacheData:{},
				order: function(type,ordrtype){
					order.call(this,type,ordrtype)
				}
			});
		
			function initScore(el){
				$(el).raty({
						readOnly: true,
						space: true,
						score: function() {
							var $this = $(this),
								score = $this.data('score') || ''
								$this.append('&nbsp;&nbsp;<span>' + score + '</span>')
							return (score);
						},
						half: true,
						starHalf: basePathPortal+'assets/icon/star_half.png',
						starOff:  basePathPortal+'assets/icon/star_zero.png',
						starOn:   basePathPortal+'assets/icon/star_full.png'
						
					})
			}
		$(function(){
			avalon.scan();
		});
		function Map() {
			this.container = new Object();
		}
		Map.prototype.put = function(key, value) {
			this.container[key] = value;
		}
		Map.prototype.get = function(key) {
			return this.container[key];
		}
		Map.prototype.keySet = function() {
			var keyset = new Array();
			var count = 0;
			for ( var key in this.container) {
				if (key == 'extend') {
					continue;
				}
				keyset[count] = key;
				count++;
			}
			return keyset;
		}
		Map.prototype.size = function() {
			var count = 0;
			for ( var key in this.container) {

				if (key == 'extend') {
					continue;
				}
				count++;
			}
			return count;
		}
		Map.prototype.remove = function(key) {
			delete this.container[key];
		}
		Map.prototype.toString = function() {
			var str = "";
			for ( var i = 0, keys = this.keySet(), len = keys.length; i < len; i++) {
				str = str + keys[i] + "=" + this.container[keys[i]] + ";\n";
			}
			return str;
		}
		
		window.orders=new Map();
		function order(type,orderType)
		{
			$(this).addClass('active').siblings().removeClass('active');
			$(this).addClass('down').siblings().removeClass('down');
			switch (type) {
			case 1:
				loadDate(1,2010,musicModel, '#musicPage',orderType);
				window.orders.put(2010,orderType);
				break;
			case 2:
				loadDate(1,2020,storyModel, '#storyPage',orderType);
				window.orders.put(2020,orderType);
			break;
			case 3:
				loadDate(1,2030,gameModel, '#gamePage',orderType);
				window.orders.put(2030,orderType);
				break;
			case 4:
				loadDate(1,2040,otherlistModel, '#otherPage',orderType);
				window.orders.put(2040,orderType);
				break;
			}
		}	
		 window.hasData=true;
		 window.isEnd=true;
		function loadDate(currentPage,type,vmodel, ele,orderType){
			var page = currentPage || 1;
			var $input = $('#searchkey');
			$val = $input.val();
			var key=$val||"";
			var ot=orderType||window.orders.get(type);
			$.post(encodeURI('list2?'+Math.random()+'&orderType='+ot+'&type=' + type + '&pageSize=6&currentPage=' + page+'&keywords='+key), function(rep){
				createDatePage(rep.currentPage, rep.totalPage, type,vmodel, ele);
				vmodel.list = vmodel.$cacheData[page] = rep.dataList;
				if(rep.dataList.length>0){
						$("#nodata").hide();
						window.hasData=true;
				}
				window.isEnd=true;
			}, 'json');
		}
		$(function(){
		loadDate(1,2010,musicModel, '#musicPage',100);
		loadDate(1,2020,storyModel, '#storyPage',100);
		loadDate(1,2030,gameModel, '#gamePage',100);
		loadDate(1,2040,otherlistModel, '#otherPage',100);
		});
		$('#searchfrm').on('submit', function(){
			var $input1_val = $('#searchkey1').val(),
				$input = $('#searchkey');
			$input.val($input1_val);
			window.hasData=false;
			if(!window.isEnd){
				alert("请稍等正在努力为您加载数据！");
				return false;
			}
			window.isEnd=false;
			storyModel.list.clear();
			musicModel.list.clear();
			gameModel.list.clear();
			otherlistModel.list.clear();
			//('找不到和您的查询 "'+$val+'" 相符的内容或信息。');
			loadDate(1,2020,storyModel, '#storyPage',100);
			loadDate(1,2030,gameModel, '#gamePage',100);
			loadDate(1,2040,otherlistModel, '#otherPage',100);
			loadDate(1,2010,musicModel, '#musicPage',100);
			if(!window.hasData){
				$("#nodata").show();	
			}
			return false;
		});
		function createDatePage(currentPage, totalPage, type,vmodel, ele){
			var options = {
		        currentPage: currentPage || 1,
		        totalPages: totalPage || 1,
	        	onPageChanged: function(e,oldPage,newPage){
		        		loadDate(newPage,type,vmodel);
	        	}
	      	}
	      	$(ele).bootstrapPaginator(options)
		}
		});

		seajs.use('artDialog/6.0.0/dialog', function(dialog){
	    var elem = document.getElementById('mobileDialog')
	    var d = dialog({
	      fixed: true,
	      content: elem,
	      padding:0
	    })
	    // 关闭
	    $('.cd-close').on('click', function(event) {
	      event.preventDefault()
	      d.close()
	    })
	    // 显示
	    $(document).on('click', '[data-toggle="mobile"]', function(event) {
	      event.preventDefault()
	      d.showModal()
	    })
	  });
	
			seajs.use(['avalon', 'jquery'], function(avalon) {
			var hotModel = avalon.define({
				$id: "hotView",
				list:[]			
			})
			var otherModel = avalon.define({
				$id: "otherView",
				list:[]			
			})
			function loadOthersData(model,params)
			{
				$.get(encodeURI('others?'+Math.random()+'&'+params), function(rep){
					model.list= rep.dataList;
				}, 'json');
			}
			$(function(){
				
			var params1='pageSize=5&currentPage=1&contentType=2000&orderType=201';
			var params2='pageSize=5&currentPage=1&contentType=1000&orderType=201';
			loadOthersData(hotModel,params1);
			loadOthersData(otherModel,params2);
			seajs.use(basePathPortal+'assets/js/subStri.js',function(str){
	            setTimeout(function(){
	            	str.strClip('.js_sub_summary',100);
	            },4000);
	        });
			});
		});
	function favorite(code,el) {
		var $el = $(el);
		
		if (!checkPortalUserLongin()) {
		//	alert("请登录");
			$('[data-toggle="login"]').click();
		} else {
			$.ajax({
				cache : true,
				type : "POST",
				url : 'favorite',
				data : {
					code : code
				},
				dataType : "json",
				error : function(error) {
				//console.log(error);
				},
				success : function(data) {
					//       //console.log(data);
					if (3 == data.state) {
						alert("你没有登录，请登录！");
						$('[data-toggle="login"]').click();
					} else if (4 == data.state) {
						alert("你已收藏过了");
						$el.removeClass("active");
					} else if (0 == data.state) {
						alert("收藏成功！");
						$el.removeClass("active");
						frontContentStatic('2000',
								'content', contentCode_, 'favate');
			//			window.location.reload();
					} else {
					}
				}
			});
		}//
	}

	function praise(code,el) {
		var $el = $(el);
		$.ajax({
			cache : true,
			type : "POST",
			url : 'praise',
			data : {
				code : code
			},
			dataType : "json",
			error : function(error) {
		//		//console.log(error);
			},
			success : function(data) {
				if (4 == data.state) {
					alert("你已赞过了");
					$el.removeClass("active");
				} else if (0 == data.state) {
					alert("赞成功！");
					$el.removeClass("active");
					frontContentStatic('2000', 'content',
							contentCode_, 'praise');
				//	window.location.reload();
				} else {
				}
			}
		});
	}			
	
		    function adver(href,code){
				    frontContentStatic('78abc9785-123c-1s24-bab-0211056a05bcs','content',code,'click');
				    window.open(href);
				    }
	window._bd_share_config = {
		"common": {
			"bdSnsKey": {},
			"bdText": "",
			"bdMini": "2",
			"bdMiniList": false,
			"bdStyle": "1",
			"bdSize": "24",
			onBeforeClick: function(cmd, config){
				config.bdUrl = basePath_ + shareModel.info.url
				config.bdPic = basePath_ + shareModel.info.title
				config.bdText = '#天上西藏音乐分享#歌曲：' + shareModel.info.contentTitle +' 歌手：' + shareModel.info.authorCode
				config.bdDesc = shareModel.info.contetNotHtml;
				return config;
			}
		},
		"share" : {
				"bdSize" : 24,
				"bdCustomStyle": basePathPortal+'/assets/css/common.css'
			}
	};
	with(document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];
