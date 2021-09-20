<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <title>Регистрация пользователя</title>
    <script>
        function checkFormFields() {
            let name = $("#name").val();
            let email = $("#email").val();
            let pass = $("#pass").val();
            if ((name === "") || (email === "") || pass === "") {
                alert("Не заполнено поле(я)");
                return false;
            } else {
                return true;
            }
        }
    </script>
</head>
<body>
<div class="container pt-3">
    <div class="card-body">
        <form action="<%=request.getContextPath()%>/reg.do" method="post">
            <div class="form-group">
                <label for="name">Имя пользователя</label>
                <input type="text" class="form-control" name="name" id="name">

            </div>
            <div class="form-group">
                <label for="email">"Электронная почта"</label>
                <input type="text" class="form-control" name="email" id="email">
            </div>
            <div class="form-group">
                <label for="pass">"Пароль"</label>
                <input type="text" class="form-control" name="password" id="pass">
            </div>
            <div class="container">
                <button type="submit" class="btn btn-primary" onclick="return checkFormFields();">Зарегистрироваться</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
