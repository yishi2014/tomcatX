(function ($) {
    var _ajax=$.ajax;
    $.ajax=function (opt) {
        var fn={
            error:function (XMLhttpRequest,textStatus,errorThrown) {

            },
            success:function (data,textStatus) {


            }
        };
        if(opt.err_callbacks){
            fn.error=opt.error;
        }
        if(opt.success){
            fn.success=opt.success;
        }
        var _opt=$.extend(opt,{
            error:function (XMLHttpRequest,textStatus,errorThrown) {
                fn.error(XMLHttpRequest,errorThrown);
            },
            success:function (data,textStatus,xhr) {
                fn.success(data,textStatus);
            }
            ,
            statusCode:{
                307:function (xhr) {
                   var data=JSON.parse(xhr.responseText);
                   // console.log(data.data.url);
                   top.window.location.href=data.data.url;
                }
            }
        });
        _ajax(_opt);

    }
})(jQuery);

function getCookie(name) {
    if(document.cookie.length>0){
        c_start=document.cookie.indexOf(name+"=");
        if(c_start!=-1){
            c_start=c_start+name.length+1;
        }
        c_end=document.cookie.indexOf(";",c_start);
        if(c_end==-1){
            c_end=document.cookie.length;
        }
        return unescape(document.cookie.substring(c_start,c_end));
    }
    return null;

}
if(getCookie("sso.token")==null){
    window.location="login.html";
}