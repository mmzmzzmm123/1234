
$(function() {
    validateKickout();
    validateRule();
    $('.imgcode').click(function() {
        var url = ctx + "captchaImage2?type=" + captchaType + "&s=" + Math.random();
        $(".imgcode").attr("src", url);
        //
        // this.codeUrl = "data:image/gif;base64," + res.img;
        // this.loginForm.uuid = res.uuid;
    });
});

$.validator.setDefaults({
    submitHandler: function() {
        login();
    }
});

function login() {
    $.modal.loading($("#btnSubmit").data("loading"));
    var username = $.common.trim($("input[name='username']").val());
    var password = $.common.trim($("input[name='password']").val());
    var validateCode = $("input[name='validateCode']").val();
    var rememberMe = $("input[name='rememberme']").is(':checked');
    var dataParame ={
        "username": username,
        "password": password,
        "code": validateCode,
        "rememberMe": rememberMe
    }
    $.ajax({
        type: "post",
        url: ctx + "login",
        contentType: "application/json",
        data: JSON.stringify(dataParame),
        success: function(r) {
            if (r.code == 200) {
                const cookieName = 'Authorization';
                const cookieValue = r.token;
                const expirationTime = 60 * 720; // 1 day in seconds
                const expirationDate = new Date();
                expirationDate.setTime(expirationDate.getTime() + (expirationTime * 1000));
                document.cookie = `${cookieName}=${cookieValue}; expires=${expirationDate.toUTCString()}; path=/`;
                window.location.href = '/demo/chatgpt/chat';
            } else {
            	$('.imgcode').click();
            	$(".code").val("");
            	$.modal.msg(r.msg);
            }
            $.modal.closeLoading();
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            username: {
                required: icon + "请输入您的用户名",
            },
            password: {
                required: icon + "请输入您的密码",
            }
        }
    })
}

function validateKickout() {
    if (getParam("kickout") == 1) {
        layer.alert("<font color='red'>您已在别处登录，请您修改密码或重新登录</font>", {
            icon: 0,
            title: "系统提示"
        },
        function(index) {
            //关闭弹窗
            layer.close(index);
            if (top != self) {
                top.location = self.location;
            } else {
                var url = location.search;
                if (url) {
                    var oldUrl = window.location.href;
                    var newUrl = oldUrl.substring(0, oldUrl.indexOf('?'));
                    self.location = newUrl;
                }
            }
        });
    }
}

function getParam(paramName) {
    var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}