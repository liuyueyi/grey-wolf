$(function () {
    var URL = window.URL || window.webkitURL;

    $('#chooseBtn').click(function () {
        $('#inputBtn').click();
    });


    var chooseFile;
    $('#inputBtn').change(function () {
        var files = this.files;
        var file;

        var imgUrl;

        if (files && files.length) {
            file = files[0];
            chooseFile = file;

            $('#imgUrl').val(file.name);

            if (/^image\/\w+$/.test(file.type)) {
                if (imgUrl) {
                    URL.revokeObjectURL(imgUrl);
                }

                imgUrl = URL.createObjectURL(file);
                $('#preImg').attr("src", imgUrl);
            } else {
                window.alert('Please choose an image file.');
            }
        }

        console.info("file :", file);
    });


    // 上传文件
    $('#uploadBtn').click(function () {
        var file = chooseFile;

        if (!file) {
            alert("请选择要上传的图片");
        }


        var formData = new FormData();
        var name = file.name;
        formData.append("image", file);
        formData.append("name", name);

        $.ajax({
            url: '/img/upload',
            type: 'post',
            cache: false,
            data: formData,
            processData: false,
            contentType: false,
            xhrFields: {
                withCredentials: true
            },
            success: function (data) {
                console.info("success", data);

                if (data.status.code == 1001) {
                    var imgUrl = data.result.url;
                    var imgPath = data.result.path;

                    $('#upImg').attr("src", imgUrl);
                    $('#upImgInfo').html("<h4>图片上传时间: " + getNowFormatDate()
                        + "</h4>&nbsp;&nbsp;图片urls: &nbsp;&nbsp;<a href='" + imgUrl +"' target='_blank'>" + imgUrl +"</a>"
                        + "</br>&nbsp;&nbsp;图片path: &nbsp;&nbsp;" + imgPath
                        + "</br>&nbsp;&nbsp;图片name: &nbsp;&nbsp;" + name
                    );
                    file = null;
                } else {
                    alert(data.status.msg);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.info(XMLHttpRequest, textStatus, errorThrown);
                alert("上传失败");
            }
        });
    });
});


function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    return date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
}