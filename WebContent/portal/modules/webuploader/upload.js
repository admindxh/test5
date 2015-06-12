define(function(require, exports, module) {
	require('webuploader');
	require('jquery');
	$(function(){
	    if(checkFlash()!=0){
	      $('#btnuploadfile').on('click',function(){
	         alert("请下载安装最新版本的flash插件");
	      })
	      return;
	    }else{
		// 初始化Web Uploader
		var uploader = WebUploader.create({
		    // 选完文件后，是否自动上传。
		    auto: true,
		    // swf文件路径
		    swf: contextPath +'/modules/webuploader/Uploader.swf',
		    // 文件接收服务端。
		    server: contextPath + '/userAccountSet/uploadPic',
		    // 选择文件的按钮。可选。
		    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
		    pick: '#btnuploadfile',
		    // 设置文件上传域的name
		    fileVal:'file',
		    // 验证文件总数量, 超出则不允许加入队列x
			fileNumLimit : 1,
			fileSizeLimit : 1 * 1024 * 1024,
		    // 只允许选择图片文件。
		    accept: {
		        title: 'Images',
		        extensions: 'gif,jpg,jpeg,bmp,png',
		        mimeTypes: 'image/*'
		    }
		});
		uploader.on('fileQueued', function(file){
			
		})
		// 上传进度
		//uploader.on('uploadProgress', function(file, percentage ){
		//	console.log(percentage)
		//})
		/**
		*	object {Object}
		*  ret {Object}服务端的返回数据，json格式，如果服务端不是json格式，从ret._raw中取数据，自行解析。
		**/
		uploader.on('uploadAccept', function(object, ret){
			if(ret.map.status != 1){
				return false
			}
			var imgUrl = contentPath + ret.map.url
			$('.preview-container img').attr('src', imgUrl)
			$('#target').attr('src', imgUrl).css({
				width: 'auto',
				height: 'auto'
			});
			$('#imgUrl').val(ret.map.url);
			initJcrop()
		})

		uploader.on('beforeFileQueued', function(file){
			 this.reset()
		})
		uploader.on('error', function(handler) {
			if (handler == "Q_EXCEED_NUM_LIMIT") {
				alert("超出最大张数");
			}
			if (handler == "F_DUPLICATE") {
				alert("文件重复");
			}
	
			if (handler == "Q_EXCEED_SIZE_LIMIT") {
				alert("文件大小超出限制");
			}
			if(handler=="Q_TYPE_DENIED"){
  			    alert("上传格式错误");
			}
	   });
	  }
	});
  function checkFlash() {
	var version;
	try {
		version = navigator.plugins['Shockwave Flash'];
		version = version.description;
	} catch (ex) {
		try {
			version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash').GetVariable('$version');
		} catch (ex2) {
			version = '0.0';
		}
	}
	version = version.match(/\d+/g);
	version = parseFloat(version[0] + '.' + version[1], 10);
	//alert(version);
	if (version!=0 && version < 11.4 ) {
		return 1;
	} else if(version==0){
		return 2;
	}
	return 0;
  }
});