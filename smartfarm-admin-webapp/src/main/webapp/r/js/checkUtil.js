//字符串校验
function stringCheck(formName,value) {
    if(value == '') {
        Notify("请输入"+formName+"！", 'top-right', '4000', 'danger', 'fa-bolt', true);
        return false;
    }
    return true;
}

//正整数校验
function intCheck(formName,value) {
    if(value == '') {
        Notify("请输入"+formName+"！", 'top-right', '4000', 'danger', 'fa-bolt', true);
        return false;
    }
    var reg = /^\+?[1-9][0-9]*$/;
    if(!reg.test(value)){
        Notify(formName+"请输入正整数！", 'top-right', '4000', 'danger', 'fa-bolt', true);
        return false;
    }
    return true;
}

//两位小数校验
function doubleCheck(formName,value) {
    if(value == '') {
        Notify("请输入"+formName+"！", 'top-right', '4000', 'danger', 'fa-bolt', true);
        return false;
    }
    var reg = /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/;
    //^(([1-9]{1}\d*)|(0{1}))(\.\d{1,2})?$
    if(!reg.test(value)){
        Notify(formName+"最多输入两位小数！", 'top-right', '4000', 'danger', 'fa-bolt', true);
        return false;
    }
    return true;
}

//图片校验
function picCheck(formName,picName) {
    var Imgs = document.querySelector('input[name="'+picName+'"]').files;
    if(Imgs.length == 0){
        Notify("请上传"+formName+"！", 'top-right', '4000', 'danger', 'fa-bolt', true);
        return false;
    }
    for(var i = 0;i < Imgs.length;i++){
        if(parseInt(Imgs[0].size / 1024) > 600){
            Notify(formName+"太大，请限制图片大小在600k内！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return false;
        }
    }
    return true;
}

//手机号码校验
function phoneCheck(formName,value) {
    if(value == '') {
        Notify("请输入"+formName+"！", 'top-right', '4000', 'danger', 'fa-bolt', true);
        return false;
    }
    var reg = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/;
    if(!reg.test(formName)){
        Notify(formName+"请输入11位的电话号码！", 'top-right', '4000', 'danger', 'fa-bolt', true);
        return false;
    }
    return true;
}


//邮箱校验
function emailCheck(formName,value) {
    if(value == '') {
        Notify("请输入"+formName+"！", 'top-right', '4000', 'danger', 'fa-bolt', true);
        return false;
    }
    var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(!reg.test(formName)){
        Notify(formName+"请输入正确的邮箱！", 'top-right', '4000', 'danger', 'fa-bolt', true);
        return false;
    }
    return true;
}



