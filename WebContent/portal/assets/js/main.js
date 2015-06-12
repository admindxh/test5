define(function(require, exports, module) {
	var $ = require('jquery');
	var avalon = require('avalon');
    console.log($);

    var testModel = avalon.define({
		$id:'test',
		list:[{name:new Date()-0},{name:new Date()-0},{name:new Date()-0}]
	})
	module.exports = testModel
});