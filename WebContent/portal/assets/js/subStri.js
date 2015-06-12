/**
 * Created by liaohuan on 2014/11/19.
 */
define(function(require, exports, module) {
    var $ = require('jquery');
    exports.strClip = function(ele,num){
        var tempNode = $(ele);
        for(var i = 0;i < tempNode.length;i++){
        	var tempText = $.trim(tempNode.eq(i).text());
        	if(tempText.length >= num){
        		tempNode.eq(i).text(tempText.substr(0,num-3)+"...");
        	}
        }
    }
});