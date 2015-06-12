/**
 * Created by liaohuan on 2014/11/25.
 */
function $(id){
    return document.getElementById(id);
}
$("js_meg").onclick = function(){
    this.style.backgroundColor = "#ff6600";
    this.style.color = "#fff";
    $("meg").style.display = "block";
    $("js_tag").style.backgroundColor = "#ffcc99";
    $("js_tag").style.color = "#000";
    $("tag").style.display = "none";
};
$("js_tag").onclick = function(){
    this.style.backgroundColor = "#ff6600";
    this.style.color = "#fff";
    $("tag").style.display = "block";
    $("js_meg").style.backgroundColor = "#ffcc99";
    $("js_meg").style.color = "#000";
    $("meg").style.display = "none";
};