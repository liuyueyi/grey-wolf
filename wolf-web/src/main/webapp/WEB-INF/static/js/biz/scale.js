$(function () {
    var URL = window.URL || window.webkitURL;

    $('#chooseBtn').click(function () {
        $('#inputBtn').click();
    });


    $('#getImg').click(function () {
        $('#preImg').attr("src", $('#imgUrl').val());
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
        var w = $('#dataWidth').val();
        var h = $('#dataHeight').val();
        var ratio = $('#dataRatio').val();
        var quality = $('#dataQuality').val();

        var formData = new FormData();

        if (typeof(file) != 'undefined' && file) {
            var name = file.name;
            formData.append("image", file);
            formData.append("name", name);
        } else {
            var path = $('#imgUrl').val();
            formData.append("path", path);
        }

        if (w > 0) {
            formData.append("w", w);
        }

        if (h > 0) {
            formData.append("h", h);
        }

        if (ratio > 0) {
            formData.append("ratio", ratio);
        }

        if (quality > 0) {
            formData.append("quality", quality);
        }

        console.log("argument : ", formData);

        $.ajax({
            url: '/img/scale',
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
                    $('#upImgInfo').html("<h4>图片压缩时间: " + getNowFormatDate()
                        + "</h4>&nbsp;&nbsp;图片urls: &nbsp;&nbsp;<a href='" + imgUrl + "' target='_blank'>" + imgUrl + "</a>"
                        + "</br>&nbsp;&nbsp;图片path: &nbsp;&nbsp;" + imgPath
                        + "</br>&nbsp;&nbsp;图片name/原图: &nbsp;&nbsp;" + $('#imgUrl').val()
                    );
                    file = null;
                } else {
                    alert(data.status.msg);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.info(XMLHttpRequest, textStatus, errorThrown);
                alert("压缩失败");
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