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

    <title>Кинотеатр</title>
</head>
<body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
    function reloadDate(val) {
        let urlHall = '<%=request.getContextPath()%>/hall.do?session_id=' + val;
        $.ajax({
            type: 'GET',
            url: urlHall,
            dataType: 'json'
        }).done(function (data) {
            setDefault();
            for (var ticket of data) {
                let elementId = ticket.row + "." + ticket.cell;
                let elem = document.getElementById(elementId);
                elem.hidden = true;
            }
            // alert(val);
        })
    }

    function updateTicketsInfo() {
        var rt1 = document.getElementById('session1');
        var rt2 = document.getElementById('session2');
        var rt3 = document.getElementById('session3');

        if (rt1.checked) {
            reloadDate(1);
        }
        if (rt2.checked) {
            reloadDate(2);
        }
        if (rt3.checked) {
            reloadDate(3);
        }
    }

    setInterval(updateTicketsInfo, 30000);

    function setDefault() {
        document.getElementById(1.1).hidden = false;
        document.getElementById(1.2).hidden = false;
        document.getElementById(1.3).hidden = false;
        document.getElementById(2.1).hidden = false;
        document.getElementById(2.2).hidden = false;
        document.getElementById(2.3).hidden = false;
        document.getElementById(3.1).hidden = false;
        document.getElementById(3.2).hidden = false;
        document.getElementById(3.3).hidden = false;
    }

    $(document).ready(reloadDate(1));
</script>
<div class="container">
    <div class="card-body">
        <h4>
            Бронирование месте на сеанс
        </h4>
        <form id="my_form" action="<%=request.getContextPath()%>/payment.jsp" method="get">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th style="width: 120px;">Ряд / Место</th>
                    <th>1</th>
                    <th>2</th>
                    <th>3</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th>1</th>
                    <td><input required type="radio" name="place" value="1.1" id="1.1"> Ряд 1, Место 1</td>
                    <td><input required type="radio" name="place" value="1.2" id="1.2"> Ряд 1, Место 2</td>
                    <td><input required type="radio" name="place" value="1.3" id="1.3"> Ряд 1, Место 3</td>
                </tr>
                <tr>
                    <th>2</th>
                    <td><input required type="radio" name="place" value="2.1" id="2.1"> Ряд 2, Место 1</td>
                    <td><input required type="radio" name="place" value="2.2" id="2.2"> Ряд 2, Место 2</td>
                    <td><input required type="radio" name="place" value="2.3" id="2.3"> Ряд 2, Место 3</td>
                </tr>
                <tr>
                    <th>3</th>
                    <td><input required type="radio" name="place" value="3.1" id="3.1"> Ряд 3, Место 1</td>
                    <td><input required type="radio" name="place" value="3.2" id="3.2"> Ряд 3, Место 2</td>
                    <td><input required type="radio" name="place" value="3.3" id="3.3"> Ряд 3, Место 3</td>
                </tr>
                </tbody>
            </table>
            <div class="form-check">
                <input required type="radio" name="session_id" id="session1" value="1" onclick="reloadDate(this.value);" checked>
                <label class="form-check-label" for="session1">
                    Сеанс №1
                </label>
                <input required type="radio" name="session_id" id="session2" value="2" onclick="reloadDate(this.value);">
                <label class="form-check-label" for="session2">
                    Сеанс №2
                </label>
                <input required type="radio" name="session_id" id="session3" value="3" onclick="reloadDate(this.value);">
                <label class="form-check-label" for="session3">
                    Сеанс №3
                </label>
            </div>
            <button type="submit" class="btn btn-success">Оплатить</button>
        </form>
        <c:if test="${not empty error}">
            <div style="color:red; font-weight: bold; margin: 30px 0;">
                <c:out value="${error}"/>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>