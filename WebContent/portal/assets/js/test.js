define(function(require, exports, module) {
  	// 模块代码
  	var jquery = require('jquery');
  	var testModel = require('./main');
  	console.log(testModel.list.$model)
  	setTimeout(function(){
  		testModel.list = []
  	}, 1000)
});