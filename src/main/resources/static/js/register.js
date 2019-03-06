$(function () {
    $('#doc-vld-msg').validator({
        onValid: function (validity) {
            $(validity.field).closest('.am-form-group').find('.am-alert').hide();
        },

        onInValid: function (validity) {

            var $field = $(validity.field);
            var $group = $field.closest('.am-form-group');
            var $alert = $group.find('.am-alert');
            // 使用自定义的提示信息 或 插件内置的提示信息
            var msg = $field.data('validationMessage') || this.getValidationMessage(validity);

            if (!$alert.length) {
                $alert = $('<div class="am-alert am-alert-danger"></div>').hide().appendTo($group);
            }
            $alert.html(msg).show();
        },
        validate: function (validity) {
            var name = $('#doc-vld-name-2-1').val();
            // Ajax 验证
            if ($(validity.field).is('#doc-vld-name-2-1')) {
                $(validity.field).closest('.am-form-group').find('.am-alert').hide();
                var $field = $(validity.field);
                var $group = $field.closest('.am-form-group');
                var $alert = $group.find('.am-alert');

                // 异步操作必须返回 Deferred 对象
                $.ajax({
                    type: "POST",
                    url: '/admin/checkName',
                    cache: false, //实际使用中请禁用缓存
                    dataType: 'json',
                    data: {
                        name: name
                    },
                    success: function (data) {
                        if (data['status'] == 400) {
                            $alert = $('<div class="am-alert am-alert-danger"></div>').hide().appendTo($group);
                            $alert.html("用户名已存在").show();

                        }
                    },
                });
            }


        }

    });
});


function register() {
    var name = $('#doc-vld-name-2-1').val();
    var password = $('#doc-vld-password-2-1').val();
    var email = $('#doc-vld-email-2-1').val();
    $.ajax({
        type: "POST",
        url: '/admin/register',
        cache: false, //实际使用中请禁用缓存
        dataType: 'json',
        data: {
            name: name,
            password: password,
            email: email,
        },
        success: function (data) {
            if (data['status'] == 200) {
                alert("注册成功,去登录试试吧!")
                window.location.href = "/login.html";
            } else {

                alert("注册失败")
            }
        },
    });

}