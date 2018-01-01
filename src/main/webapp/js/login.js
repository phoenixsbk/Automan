function login(usr, pwd) {
    $.ajax({
        url: '/rest/auth/login/' + usr,
        method: 'POST',
        headers: {
            AMPass: pwd
        },
        success: function(data) {
            var token = data.token;
            console.log(token);
            window.location.replace("/index.html");
        },
        error: function(err) {
            console.log(err);
        }
    });
}

$(function () {
    $('#loginBtn').on('click', function() {
        var usr = $('#userField').val();
        var pwd = $('#passField').val();
        if (usr && pwd) {
            login(usr, pwd);
        }
    });
});