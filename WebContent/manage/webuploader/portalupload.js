define(function(require, exports, module) {
	require('webuploader');
	require('jquery');
	$(function(){
		// 初始化Web Uploader
		var uploader = WebUploader.create({
		    // 选完文件后，是否自动上传。
		    auto: true,
		    // swf文件路径
		    swf: './Uploader.swf',
		    // 文件接收服务端。
		    server: contextPath + '/userAccountSet/uploadPic',
		    // 选择文件的按钮。可选。
		    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
		    pick: '#btnuploadfile',
		    // 设置文件上传域的name
		    fileVal:'file',
		    // 只允许选择图片文件。
		    accept: {
		        title: 'Images',
		        extensions: 'gif,jpg,jpeg,bmp,png',
		        mimeTypes: 'image/*'
		    }
		});
		uploader.on('fileQueued', function(file){
			console.log(file)
		})
		// 上传进度
		uploader.on('uploadProgress', function(file, percentage ){
			console.log(percentage)
		})
		/**
		*	object {Object}
		*  ret {Object}服务端的返回数据，json格式，如果服务端不是json格式，从ret._raw中取数据，自行解析。
		**/
		uploader.on('uploadAccept', function(object, ret){
			if(ret.map.status != 1){
				return false
			}
			$('.img-browsing img').attr('src', contentPath + ret.map.url)
			$('#target').attr('src', contentPath + ret.map.url).css({
				'width':'380px',
				'height':'380px',
				'display': 'block',
				'visibility': 'visible'
				
			})
			initJcrop()
		})
		
		uploader.on('startUpload', function(){
			console.log('开始上传')
		})
	});
});