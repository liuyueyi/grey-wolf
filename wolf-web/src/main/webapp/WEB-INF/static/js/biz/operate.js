$(function () {

    'use strict';

    var console = window.console || {
            log: function () {
            }
        };
    var URL = window.URL || window.webkitURL;
    var $image = $('#image');
    var $download = $('#download');
    var $dataX = $('#dataX');
    var $dataY = $('#dataY');
    var $dataHeight = $('#dataHeight');
    var $dataWidth = $('#dataWidth');
    var $dataRotate = $('#dataRotate');
    var $dataScaleX = $('#dataScaleX');
    var $dataScaleY = $('#dataScaleY');
    var options = {
        aspectRatio: 16 / 9,
        preview: '.img-preview',
        crop: function (e) {
            console.info("hahahha");
            $dataX.val(Math.round(e.x));
            $dataY.val(Math.round(e.y));
            $dataHeight.val(Math.round(e.height));
            $dataWidth.val(Math.round(e.width));
            $dataRotate.val(e.rotate);
            $dataScaleX.val(e.scaleX);
            $dataScaleY.val(e.scaleY);
        }
    };
    var originalImageURL = $image.attr('src');
    var uploadedImageURL;


    // Tooltip
    $('[data-toggle="tooltip"]').tooltip();


    // Cropper
    $image.on({
        'build.cropper': function (e) {
            console.log(e.type);
        },
        'built.cropper': function (e) {
            console.log(e.type);
        },
        'cropstart.cropper': function (e) {
            console.log(e.type, e.action);
        },
        'cropmove.cropper': function (e) {
            console.log(e.type, e.action);
        },
        'cropend.cropper': function (e) {
            console.log(e.type, e.action);
        },
        'crop.cropper': function (e) {
            console.log(e.type, e.x, e.y, e.width, e.height, e.rotate, e.scaleX, e.scaleY);
        },
        'zoom.cropper': function (e) {
            console.log(e.type, e.ratio);
        }
    }).cropper(options);


    // Buttons
    if (!$.isFunction(document.createElement('canvas').getContext)) {
        $('button[data-method="getCroppedCanvas"]').prop('disabled', true);
    }

    if (typeof document.createElement('cropper').style.transition === 'undefined') {
        $('button[data-method="rotate"]').prop('disabled', true);
        $('button[data-method="scale"]').prop('disabled', true);
    }


    // Download
    if (typeof $download[0].download === 'undefined') {
        $download.addClass('disabled');
    }


    // Options
    $('.docs-toggles').on('change', 'input', function () {
        var $this = $(this);
        var name = $this.attr('name');
        var type = $this.prop('type');
        var cropBoxData;
        var canvasData;

        if (!$image.data('cropper')) {
            return;
        }

        if (type === 'checkbox') {
            options[name] = $this.prop('checked');
            cropBoxData = $image.cropper('getCropBoxData');
            canvasData = $image.cropper('getCanvasData');

            options.built = function () {
                $image.cropper('setCropBoxData', cropBoxData);
                $image.cropper('setCanvasData', canvasData);
            };
        } else if (type === 'radio') {
            options[name] = $this.val();
        }

        $image.cropper('destroy').cropper(options);
    });


    // Methods
    $('.docs-buttons').on('click', '[data-method]', function () {
        var $this = $(this);
        var data = $this.data();
        var $target;
        var result;

        if ($this.prop('disabled') || $this.hasClass('disabled')) {
            return;
        }

        if ($image.data('cropper') && data.method) {
            data = $.extend({}, data); // Clone a new one

            if (typeof data.target !== 'undefined') {
                $target = $(data.target);

                if (typeof data.option === 'undefined') {
                    try {
                        data.option = JSON.parse($target.val());
                    } catch (e) {
                        console.log(e.message);
                    }
                }
            }

            if (data.method === 'rotate') {
                $image.cropper('clear');
            }

            result = $image.cropper(data.method, data.option, data.secondOption);

            if (data.method === 'rotate') {
                $image.cropper('crop');
            }

            switch (data.method) {
                case 'scaleX':
                case 'scaleY':
                    $(this).data('option', -data.option);
                    break;

                case 'getCroppedCanvas':
                    if (result) {

                        // Bootstrap's Modal
                        $('#getCroppedCanvasModal').modal().find('.modal-body').html(result);

                        if (!$download.hasClass('disabled')) {
                            $download.attr('href', result.toDataURL('image/jpeg'));
                        }
                    }

                    break;

                case 'destroy':
                    if (uploadedImageURL) {
                        URL.revokeObjectURL(uploadedImageURL);
                        uploadedImageURL = '';
                        $image.attr('src', originalImageURL);
                    }

                    break;
            }

            if ($.isPlainObject(result) && $target) {
                try {
                    $target.val(JSON.stringify(result));
                } catch (e) {
                    console.log(e.message);
                }
            }

        }
    });


    // Keyboard
    $(document.body).on('keydown', function (e) {

        if (!$image.data('cropper') || this.scrollTop > 300) {
            return;
        }

        switch (e.which) {
            case 37:
                e.preventDefault();
                $image.cropper('move', -1, 0);
                break;

            case 38:
                e.preventDefault();
                $image.cropper('move', 0, -1);
                break;

            case 39:
                e.preventDefault();
                $image.cropper('move', 1, 0);
                break;

            case 40:
                e.preventDefault();
                $image.cropper('move', 0, 1);
                break;
        }

    });


    // Import image
    var $inputImage = $('#inputImage');

    if (URL) {
        $inputImage.change(function () {
            var files = this.files;
            var file;

            if (!$image.data('cropper')) {
                return;
            }

            if (files && files.length) {
                file = files[0];

                if (/^image\/\w+$/.test(file.type)) {
                    if (uploadedImageURL) {
                        URL.revokeObjectURL(uploadedImageURL);
                    }

                    uploadedImageURL = URL.createObjectURL(file);
                    $image.cropper('destroy').attr('src', uploadedImageURL).cropper(options);
                    $inputImage.val('');
                } else {
                    window.alert('Please choose an image file.');
                }
            }
        });


        // 根据输入的值,对图片进行操作
        $('#activeBtn').click(function () {
            $image.cropper('rotateTo', $dataRotate.val());
        });


        var newImgUrl;
        // 输入图片地址,下载图片
        $('#downImg').click(function () {
            newImgUrl = $('#imgUrl').val();
            if (newImgUrl && newImgUrl.length) {
                URL.revokeObjectURL(newImgUrl);
            }

            $image.cropper('destroy').attr('src', newImgUrl).cropper(options);
        });


        // 将操作后的图片上传到cdn
        $('#doneImg').click(function () {
            if (!newImgUrl) {
                alert("请输入mgj的图片地址!");
                return;
            }

            var x = $dataX.val();
            if (x < 0) {
                x = 0;
            }
            var y = $dataY.val();
            if (y < 0) {
                y = 0
            }
            var w = $dataWidth.val();
            var h = $dataHeight.val();
            var rotate = $dataRotate.val();
            // 上下翻转
            var flip = $dataScaleY.val() == -1;
            // 镜像
            var flop = $dataScaleX.val() == -1;

            var path = newImgUrl;

            var params = {
                'x': x,
                'y': y,
                'w': w,
                'h': h,
                'path': path,
                'operateOrder': ''
            };

            // 图片编辑先后顺序: 旋转, 镜像, 上下翻转, 裁图
            if (rotate % 360 != 0) {
                params.operateOrder = "rotate_";
                params.angle = rotate;
            }

            if (flop) {
                params.operateOrder += "flop_";
            }

            if (flip) {
                params.operateOrder += "flip_";
            }

            params.operateOrder += "cut";

            console.log(params);

            $.ajax({
                url: '/img/operate',
                type: 'post',
                cache: false,
                data: params,
                xhrFields: {
                    withCredentials: true
                },
                success: function (data) {
                    console.info("success", data);
                    var imgUrl = data.result.url;
                    var imgPath = data.result.path;

                    if (data.status.code == 1001) {
                        $('#upImg').attr("src", imgUrl);
                        $('#upImgInfo').html("<h4>图片编辑时间: &nbsp;&nbsp;" + getNowFormatDate()
                            + "</h4><hr/>" +

                            "操作类型: <span style='word-wrap:break-word;word-break:break-all'>"
                            + params.operateOrder
                            + "&nbsp;w: " + w + "&nbsp;h:" + h + "&nbsp;x: " + x + "&nbsp;h: " + h + "&nbsp;rotate:" + rotate
                            + "</span><hr/>" +


                            "图片urls: <span style='word-wrap:break-word;word-break:break-all'>" +
                            "&nbsp;&nbsp;" + "<a href='" + imgUrl + "' target='_blank'>" + imgUrl + "</a>"
                            + "</span><hr/>" +

                            "图片path: <span style='word-wrap:break-word;word-break:break-all'>" +
                            "&nbsp;&nbsp;" + imgPath
                            + "</span>"
                        );
                        $('#upImgView').removeClass('hidden');
                    } else {
                        alert(data.status.msg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.info(XMLHttpRequest, textStatus, errorThrown);
                    alert("图片处理失败");
                }
            });

        });

    } else {
        $inputImage.prop('disabled', true).parent().addClass('disabled');
    }

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
