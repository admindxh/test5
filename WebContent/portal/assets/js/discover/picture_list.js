 
    seajs.use(['header/css', 'footer/css']);
    seajs.use(['jquery', 'avalon', 'paginator'], function($, avalon) {
    	var dataModel=avalon.define({
            $id: "indexView",
            list:[],
            showTag:false
        });
        function loadData(currentPage,vmodel, ele,orderType){
			var page = currentPage || 1
			$.get(encodeURI(basePath_+'portal/frontMutiController/list?'+Math.random()+'&action='+orderType+ '&pageSize=24&currentPage=' + page), function(rep){
				createDatePage(rep.currentPage, rep.totalPage, vmodel, ele)
				//console.log(rep.dataList)
				vmodel.list  = rep.dataList;
				if(!vmodel.list.size()){
					$("#nopicture").show();
				 }else{
					 $("#nopicture").hide();
				}
				seajs.use(basePathPortal+'assets/js/subStri.js',function(str){
		            setTimeout(function(){
		            	str.strClip('.js_sub_summary',58);
		            	str.strClip('.js_sub_title',18);
		            },100);
		        });
			}, 'json')
			
		}
        $(function(){
	        loadData(1,dataModel, '#paging',1);
        	avalon.scan();
        });
		function createDatePage(currentPage, totalPage, vmodel, ele){
			var options = {
		        currentPage: currentPage || 1,
		        totalPages: totalPage || 1,
	        	onPageChanged: function(e,oldPage,newPage){
		        		loadData(newPage,vmodel,ele,vmodel.type);
	        	}
	      	};
	      	$(ele).bootstrapPaginator(options);
		}
		 $(".listing li").on('click',function(){
	            $(this).addClass('down').prevAll('li').removeClass('down');
	            $(this).nextAll('li').removeClass('down');
	            var thisValue = $(this).attr('value');
	            loadData(1,dataModel, '#paging',thisValue);
	        });
	});

