



// TODO ajax 传字符串格式参数
function ajaxUtilParam(param, url) {

    var resultData = {};

    $.ajax({
        type: 'POST',
        url: url,
        data: param,
        dataType: 'json',// 响应数据类型
        contentType: "application/x-www-form-urlencoded; charset=utf-8",// 请求数据类型
        async: false,
        success: function (data) {
            resultData = data;
        },
        error: function (data) {
            resultData = {code:"FAIL"};
        }
    });

    return resultData;
}


// TODO ajax 传json格式参数
function ajaxUtilJson(param, url) {

    var resultData = {};

    $.ajax({
        type: 'POST',
        url: url,
        data: JSON.stringify(param),// 必须是JSON格式的字符串，不能JSON类型数据
        dataType: 'json',// 响应数据类型
        contentType: "application/json; charset=utf-8",// 请求数据类型
        async: false,
        success: function (data) {
            resultData = data;
        },
        error: function (data) {
            resultData = {code:"FAIL"};
        }
    });

    return resultData;
}

// TODO ajax 上传文件
function ajaxUtilFile(param, url) {

    var resultData = {};

    $.ajax({
        type: "POST",
        url: url,
        data: param,
        dataType: 'json',// 响应数据类型
        contentType: false,
        async: false,
        cache: false,
        processData: false,
        success: function(data) {
            resultData = data;
        },
        error: function(data) {
            resultData = {code:"FAIL"};
        }

    });

    return resultData;
}
