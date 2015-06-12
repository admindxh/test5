/**
* 功能：投票控制功能
* 日期：2014年12月5日
* 作者：weiye
**/ 
/* 页面加载完成后执行 */
vote_set();

/* 通过输入内容触发票数计算 */
 $(".formLine.vote").children(".vote-right").children("input[type='text']").keyup(function() {
	 vote_set();
 });

/* 恢复默认投票 */
$("#vote_default").click(function() {
	var voteItem =  $(".formLine.vote");
	voteItem.eq(0).children(".vote-right").children("input[type='text']").val(10);
	voteItem.eq(1).children(".vote-right").children("input[type='text']").val(10);
	voteItem.eq(2).children(".vote-right").children("input[type='text']").val(10);
	voteItem.eq(3).children(".vote-right").children("input[type='text']").val(10);
	vote_set();
});

/* 获取单项投票数 */
function vote_set() {

	// 获取投票项及个数
	var voteItem =  $(".formLine.vote"),
		voteItem_len = $(".formLine.vote").length,
		vote_total = 0;

	// 获取投票各项的投票数，并计算总共投票数
	for( var i = 0; i < voteItem_len; i++ ) {

		// 单项投票数
		var voteNum = parseInt(voteItem.eq(i).children(".vote-right").children("input[type='text']").val());

		// 计算总共投票数
		vote_total += parseInt(voteNum);
	}

	// 设置出投票总数
	$("#vote_total").val(vote_total);

	// 根据总票数设置各项的百分比色条
	for ( var i = 0; i < voteItem_len; i++ ) {

		// 单项投票数和单项票数占总票数百分比
		var voteNum = parseInt(voteItem.eq(i).children(".vote-right").children("input[type='text']").val()),
			thisVotePerc = (voteNum / vote_total) * 100,
			percResult = parseInt(thisVotePerc.toFixed(1));
		if(voteNum == 0 && vote_total == 0) {
			percResult = 0;
		}

		//设置出百分比色条和百分比数
		voteItem.eq(i).children(".vote-left").find(".vote-box-bar").css("width", percResult + "%");
		voteItem.eq(i).children(".vote-left").find(".vote-perc").text(percResult + "%");
	}
}