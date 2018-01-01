function sendJson(url, method, jsonObj, success, error) {
    $.ajax({
        url: url,
        method: method,
        contentType: 'application/json; charset=UTF-8',
        data: jsonObj,
        dataType: 'json',
        success: success,
        error: error
    });
}