
    seajs.use(['header/css', 'footer/css']);
 	seajs.use(['avalon','paginator','artDialog/6.0.0/dialog'], function(avalon) {
			var dataModel = avalon.define({
				$id: "indexView",
				// 分页数据
				$data:{},
				list:[],
				$type:100,
				$cacheData:{},
				order: function(type,ordrtype){
					order.call(this,type,ordrtype)
				}
			});
		function loadData(currentPage,vmodel, ele,orderType){
			var page = currentPage || 1
			$.get(encodeURI(basePath_+'portal/app/discover/list?'+Math.random()+'&orderType='+orderType+ '&pageSize=24&currentPage=' + page), function(rep){
				createDatePage(rep.currentPage, rep.totalPage, vmodel, ele)
				vmodel.list = vmodel.$cacheData[page] = rep.dataList;
				vmodel.$type=orderType;
			}, 'json')
		}
		window.dataModel = dataModel;
		$(function(){
			loadData(1,dataModel, '#paging',100);
			avalon.scan();
		});
		window.loadData=loadData;
		function createDatePage(currentPage, totalPage, vmodel, ele){
			var options = {
		        currentPage: currentPage || 1,
		        totalPages: totalPage || 1,
	        	onPageChanged: function(e,oldPage,newPage){
		        		loadData(newPage,vmodel,ele,vmodel.$type);
	        	}
	      	};
	      	$(ele).bootstrapPaginator(options);
		}
		});
    seajs.use('jquery',function(){
        $(".listing li").on('click',function(){
        	var type = $(this).attr('id')
        	dataModel.$type=type;
        	loadData(1,dataModel,'#paging',type);
            $(this).addClass('down').prevAll('li').removeClass('down');
            $(this).nextAll('li').removeClass('down');
        });
    });
    
