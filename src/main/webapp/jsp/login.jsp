<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Login</title>
    <!-- Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/login.css" rel="stylesheet">
</head>

<body>
<form class="form-horizontal" role="form" action="login" method="post">
    <div class="form-group" style="margin-top: 40px">
        <label for="inputEmail3" class="col-sm-2 control-label">Email</label>

        <div class="col-sm-10">
            <input type="text" name = "j_username" class="form-control" id="inputEmail3" placeholder="Email" style="width: 60%">
        </div>
    </div>
    <div class="form-group">
        <label for="inputPassword3" class="col-sm-2 control-label">Password</label>

        <div class="col-sm-10">
            <input type="password" name = "j_password" class="form-control" id="inputPassword3" placeholder="Password" style="width: 60%">
        </div>
    </div>
    <!--div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <div class="checkbox">
                <label>
                    <input type="checkbox"> Remember me
                </label>
            </div>
        </div>
    </div-->
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">Sign in</button>
        </div>
    </div>
</form>
</body>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script  src="/js/bootstrap.min.js"></script>
</html>
