// 图片上传demo

//图片标签
$(function() {   
	var $imgWrap = $('#ImageSrc'),
	// Web Uploader实例
	uploader,	
	//从页面获取mcode 图集code
	_code= $('#mcode').val();
	//父code
	_pcode = $('#pcode').val();
	
	
	// 初始化Web Uploader
	uploader = WebUploader.create( {
		
		// 自动上传。
		auto : true,

		// swf文件路径
		swf :  contextPath+'/manage/webuploader/Uploader.swf',

		// 文件接收服务端。
		server : contextPath+'/web/imageController/uploadandsave?mcode='+_code,
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : {
			id : '.pickfile',
			multiple : true
		},

		// 验证文件总数量, 超出则不允许加入队列x
		fileNumLimit : 10,
		fileSizeLimit : 1 * 10000* 10000,
		// 设置文件上传域的name
		fileVal : 'file',

		// 只允许选择文件，可选。
		accept : {
			title : 'Images',
			extensions : 'gif,jpg,jpeg,bmp,png',
			mimeTypes : 'image/*'
		},
		runtimeOrder : "flash"
	});
	
	// 文件加入队列之前触发
	// 因为设置了 fileNumLimit等于1，需要重置一下当前队列，否则不能更改
	uploader.on('beforeFileQueued', function(file) {
	})

	// 当有文件添加进来的时候
	uploader.on('fileQueued', function(file) {
		var $error = $('<div class="error"></div>')
		$img = $imgWrap.find('img');
		$imgWrap.append($error) 
		console.log(" 当有文件添加进来的时候"+file);
		});

	// 文件上传过程中创建进度条实时显示。
	uploader.on('uploadProgress', function(file, percentage) {
		var $percent = $imgWrap.find('.progress');
		$percent.show().find('.progress-bar').css('width',percentage * 100 + '%');
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on('uploadSuccess', function(file) {
		console.log("添加成功时-------》"+file);
		//window.location.href=contextPath+"/web/destinationController/imageManaege?option=1&&destinationCode="+_pcode;
	})
	
	
	// 文件上传失败，现实上传出错。
	uploader.on('uploadError', function(file) {
		var $error = $imgWrap.find('div.error');
		$error.show().text('上传失败');
	});

	uploader.on('uploadBeforeSend', function(obj, data) {
		//data.id = _userId
	})

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on('uploadFinished', function() {
		window.location.href=contextPath+"/web/destinationController/imageManaege?option=1&&destinationCode="+_pcode;
	});
	uploader.on('error', function(handler) {
		if (handler == "Q_EXCEED_NUM_LIMIT") {
			alert("超出最大张数");
		}
		if (handler == "F_DUPLICATE") {
			alert("文件重复");
		}

		if (handler == "Q_EXCEED_SIZE_LIMIT") {
			//alert("文件大小超出限制");
			layer.msg('文件大小超出限制', 2, 8);
		}
	});

});

