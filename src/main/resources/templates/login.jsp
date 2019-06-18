<form>
    <input id="user" placeholder="请输入账号">
    <input id="password" placeholder="请输入密码">

</form>
<button onclick="login()">登陆</button>
<script type="text/javascript">
    function login(){
        $.post("login.do?m=login",
            {"user":$("#user").val(),
            "password":$("#password").val()}),
            function (s) {
                var num = eval(s);
                if(num=="1"){
                    window.location.href="mainPage.jsp";
                }else {
                    alert("账号密码错误")
                }
            }
    }
</script>