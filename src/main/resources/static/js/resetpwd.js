//验证邮箱
function checkEmail() {
    var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
    var email = $('#register_email').val();
    if (email === "") { //输入不能为空
        swal("邮箱不能为空!", "", "warning");
        return false;
    } else if (!reg.test(email)) { //正则验证不通过，格式不对
        swal("输入邮箱不正确!", "", "warning");
        return false;
    } else {
        $.ajax({
            type: "POST",
            url: '/checkEmail',
            cache: false, //实际使用中请禁用缓存
            dataType: 'json',
            data: {
                email: email
            },
            success: function (data) {
                if (data['status'] == 400) {
                    swal(data['msg'], "", "info");
                    return false;
                }
            },
        });
    }

}

function sendEmailCode() {
    var email = $('#register_email').val();
    $.ajax({
        type: "POST",
        url: '/sendEmailCode',
        cache: false, //实际使用中请禁用缓存
        dataType: 'json',
        data: {
            email: email
        },
        success: function (data) {
            if (data['status'] == 400) {
                swal(data['msg'], "", "error");
                return false;
            }
        },
    });

}

//验证邮箱验证码
function checkEmailCode() {
    var reg = /^[0-9]*$/;
    var emailCode = $('#email_code').val();
    if (emailCode === "") { //输入不能为空
        swal("验证码不能为空!", "", "warning");
        return false;
    } else if (!reg.test(emailCode)) {
        swal("验证码有误!", "", "warning");
        return false;

    } else {

        $.ajax({
            type: "POST",
            url: '/checkEmailCode',
            cache: false, //实际使用中请禁用缓存
            dataType: 'json',
            data: {
                emailCode: emailCode
            },
            success: function (data) {
                if (data['status'] == 400) {
                    swal(data['msg'], "", "warning");
                    return false;
                }
            },
        });
    }
}

function restPwd() {
    var password = $('#new_password').val();
    var email = $('#register_email').val();
    $.ajax({
        type: "POST",
        url: '/resetPwd',
        cache: false, //实际使用中请禁用缓存
        dataType: 'json',
        data: {
            password: password,
            email: email
        },
        success: function (data) {
            if (data['status'] == 400) {
                swal(data['msg'], "", "error");

            } else {
                swal(data['msg'], "", "success");
            }
        },
    });


}

$(function () {
    $('#doc-prompt-toggle').on('click', function () {
        $('#my-prompt').modal({
            relatedTarget: this,

            onConfirm: function (e) {
                sendEmailCode();
                $('#my-emiial-code').modal({
                    relatedTarget: this,
                    onConfirm: function (e) {

                        $('#my-new-pwd').modal({
                            relatedTarget: this,
                            onConfirm: function (e) {
                                restPwd();

                            },
                            onCancel: function (e) {
                                alert('厉害啦!');
                            }
                        });

                    },

                });
            },

        });
    });
});