<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
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
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Оплата</title>
</head>
<body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<%
    String place = request.getParameter("place");
    char row = place.charAt(0);
    char cell = place.charAt(2);
    String sessionId = request.getParameter("session_id");

    HttpSession sc = request.getSession();
    sc.setAttribute("session_id", sessionId);
    sc.setAttribute("row", row);
    sc.setAttribute("cell", cell);
%>
<div class="container">
    <div class="row pt-3">
        <h3>
            Вы выбрали:
            сеанс <%=sessionId%>
            ряд <%=row%>
            место <%=cell%>
            сумма : 500 рублей.
        </h3>
    </div>
    <div class="form-group">
        <c:if test="${not empty error}">
            <div style="color:red; font-weight: bold; margin: 30px 0;">
                <c:out value="${error}"/>
            </div>
        </c:if>
    </div>
    <div class="row">
        <form id="my_form" action="<%=request.getContextPath()%>/hall.do" method="post">
            <div class="form-group">
                <a class="nav-link" href="<%=request.getContextPath()%>/index.jsp">Вернуться к выбору места и сеанса</a>
            </div>
            <div class="form-group">
                <label for="username">ФИО</label>
                <input required type="text" class="form-control" id="username" name="username" placeholder="ФИО">
            </div>
            <div class="form-group">
                <label for="email">Эл.почта</label>
                <input required type="text" class="form-control" id="email" name="email" placeholder="Эл.почта">
            </div>
            <div class="form-group">
                <label for="phone">Номер телефона</label>
                <input required type="text" class="form-control" id="phone" name="phone" placeholder="Номер телефона">
            </div>
            <button type="submit" class="btn btn-success">Оплатить</button>
        </form>
    </div>
</div>
</body>
</html>