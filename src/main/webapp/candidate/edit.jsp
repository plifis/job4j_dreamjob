<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.model.Post" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page import="ru.job4j.dream.model.City" %>
<%@ page import="java.text.SimpleDateFormat" %>
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    <title>Работа мечты</title>
</head>
    <script>
       function checkFormFields() {
        let name = $("#nameCandidate").val();
        if (name === "") {
            alert("Не заполнено поле");
            return false;
        } else {
            return true;
        }
       }
    </script>
    <script>
    function loadCity() {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/dreamjob/city.do',
            data_type: 'json'
        }).done( function(data) {
            let arrJson = JSON.parse(data);
            for (let i = 0; i < arrJson.length; i++) {
                    $('#datalist').append('<option value="' + arrJson[i].id + '">' + arrJson[i].name + '</option>')
            }
            }
        ).fail( function() {
        alert("error");
        });
        }
    </script>

<body onload="loadCity();">
<%
    String id = request.getParameter("id");
    Candidate can = new Candidate(0, "", 0);
    if (id != null) {
        can = PsqlStore.instOf().findCandidateById(Integer.parseInt(id));
    }
    City city = new City(0, "");
    if (id != null) {
         city = PsqlStore.instOf().findCityById(can.getCityId());
    }
%>
<div class="container pt-3">
    <div class="row">
            <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
        </ul>
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить кандидата</a>
            </li>
        </ul>
        <ul>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
            </li>
        </ul>

        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id ==null) {%>
                Новый кандидат.
                <%} else  {%>
                Редактирование кандидата.
                <% } %>
            </div>

            <div class="card-body">
                <form action="<%= request.getContextPath()%>/candidates.do?id=<%=can.getId()%>" method="post">
                    <div class="form-group">
                        <label>Имя</label>
                        <input type="text" class="form-control" name="name" value="<%=can.getName()%>" id="nameCandidate">
                    </div>
                    <div class="form-data">
                        <label>Город</label>
                        <input type="text" list="datalist" name="cityId" value="<%=city.getName()%>">
                        <datalist id="datalist"></datalist>
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="return checkFormFields();">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>