/**
 * Created by liaohuan on 2014/12/8.
 */
/* 表格全选按钮功能 */
$("input[type='checkbox'].allCheck").click(function() {
    dataAllCheck(".allCheck");
});

/* 数据表内非全选复选框的功能 */
$(document).on('click','input[type="checkbox"].dataCheck',function() {
    var $this = $(this);
    // 若含有选中标识符
    if($this.parents("li").hasClass("liChecked")) {
        $this.parents("li").removeClass("liChecked");
    }
    // 若不含该标识符
    else {
        $this.parents("li").addClass("liChecked");
    }
    var checkedLi_len = $this.parents("ul").find("li.liChecked").length;
    // 若不存在，则消除全选按钮效果
    if(checkedLi_len == 0) {
        $("input[type='checkbox'].allCheck").prop("checked", false);
    }
    var li_len = $this.parents("ul").find("li").length;
    // 若选中的复选框个数等于表格当前行数，则将全选按钮设为选中效果
    if(checkedLi_len == li_len) {
        $("input[type='checkbox'].allCheck").prop("checked", true);
    } else {
        $("input[type='checkbox'].allCheck").prop("checked", false);
    }
});

//===================================================
//					方法函数定义部分
//===================================================

/**
 * 功能：数据全选复选框（动态生成的table）
 * 参数：1.表单元素标识符
 **/
function dataAllCheck(checkbox) {
    $("input[type='checkbox'].dataCheck").prop("checked", $(checkbox).prop("checked"));
    // 若存在class标识符则移除，否则就添加该class标识符
    if ($("input[type='checkbox'].dataCheck").parents("li").hasClass('liChecked').length <= 0) {
    	$("input[type='checkbox'].dataCheck").parents("li").addClass("liChecked");
    } else {
        
        $("input[type='checkbox'].dataCheck").parents("li").removeClass("liChecked");
    }
}


function strClip(clas,num){
    var tempNode = $(clas).text();
    $(clas).text(tempNode.substr(0,num-2)+"...");
}
