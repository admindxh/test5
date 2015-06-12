/**
 * Created by liaohuan on 2014/11/24.
 */
function clearValue(event){
    var src = event.srcElement || event.target;
    src.value = "";
    src.style.color = "#000";
}
function addDredge(){
    location.href = "add_guidance.html";
}