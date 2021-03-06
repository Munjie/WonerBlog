function saveUserImg() {
    var show_user_img = $('#show_user_img');
    var formData = new FormData();
    formData.append('user-pic', $('#user-pic')[0].files[0]);
    if (!validate_img($('#user-pic'))) {
        return;
    }
    $.ajax({
        url: '/uploadUserImg',
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        async: false,
        success: function (data) {
            if (data['status'] == 200) {
                swal("头像更换成功!", "", "success");
                show_user_img.empty();
                var img = $('<img class="am-img-circle am-img-thumbnail" src=" ' + data['imgUrl'] + '" alt="/" ></img>');
                show_user_img.append(img)
                setTimeout("location.reload()", 1000);//页面刷新

            } else {
                swal("图片更换失败!", "", "error");

            }
        },

    });
}

function nameFocus() {
    document.getElementById('user-name').innerText = "";

}

function checkName() {
    var name = $("#user-name").val();
    if (name == null || name.length == 0) {
        swal("请输入用户名", "", "warning");
        return false;
    }
    $.ajax({
        type: "POST",
        url: '/checkNameForUpdate',
        cache: false, //实际使用中请禁用缓存
        dataType: 'json',
        data: {
            name: name
        },
        success: function (result) {
            if (result && result.success) {
                swal("保存成功!", "", "success");

            } else {
                if (result.msg) {
                    swal(result.msg, "", "warning");

                }
            }
        },
    });

}


function updateUserInfo() {
    var id = $("#user-id").val();
    var name = $("#user-name").val();
    var email = $("#user-email").val();
    var phone = $("#user-phone").val();
    var qq = $("#user-QQ").val();
    var wechat = $("#user-wechat").val();
    var birth = $("#user-birth").val();


    $.ajax({
        url: '/updateUserInfo',
        type: 'POST',
        cache: false,
        dataType: 'json',
        async: false,
        data: {
            id: id,
            name: name,
            email: email,
            phone: phone,
            qq: qq,
            wechat: wechat,
            birth: birth,
        },
        success: function (result) {
            if (result && result.success) {
                swal("保存成功!", "", "success");
                setTimeout("location.reload()", 1000);//页面刷新

            } else {
                if (result.msg) {
                    swal(result.msg, "", "warning");

                }
            }
        },
        error: function () {
            swal("保存请求失败!", "", "error");
        }

    });
}


//限制上传文件的类型和大小
function validate_img(ele) {
    // 返回 KB，保留小数点后两位
    //alert((ele.files[0].size/(1024*1024)).toFixed(2));
    var file = ele.val();
    if (!/.(gif|jpg|jpeg|png|GIF|JPG|bmp)$/.test(file)) {

        alert("图片类型必须是.gif,jpeg,jpg,png,bmp中的一种");
        return false;
    } else {
        //alert((ele.files[0].size).toFixed(2));
        //返回Byte(B),保留小数点后两位
        if (((ele[0].files[0].size).toFixed(2)) >= (2 * 1024 * 1024)) {

            swal("请上传小于2M的图片!", "", "warning");
            return false;
        } else return true;
    }
    return false;

}
