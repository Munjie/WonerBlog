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
        success: function (data) {
            if (data['status'] == 200) {
                show_user_img.empty();
                var img = $('<img class="am-img-circle am-img-thumbnail" src=" ' + data['imgUrl'] + '" alt="/" ></img>');
                show_user_img.append(img)

            } else {
                alert("图片更改失败");
            }
        },

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

            alert("请上传小于2M的图片");
            return false;
        } else return true;
    }
    return false;

}
