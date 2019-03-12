function checkName() {

    var name = $("#username").val();

    if (name == null || name.length == 0) {
        swal("用户名不能为空!", "", "warning");
        return false;
    }

    $.ajax({
        type: "POST",
        url: '/admin/checkName',
        cache: false, //实际使用中请禁用缓存
        dataType: 'json',
        data: {
            name: name
        },
        success: function (data) {
            if (data['status'] == 200) {

                swal("用户名不存在!", "", "info");
                // alert("用户名不存在")
            }
        },
        error: function () {

        }
    });

}