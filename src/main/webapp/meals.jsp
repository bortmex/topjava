<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>

    <h3>userId: ${userId}</h3>
    <h3>userName: ${userName}</h3>

        <h3>FilterDate:</h3>
            <form method="post" action="meals?action=filter">
        <table border="1" cellpadding="10" cellspacing="0" bordercolor="white">
            <tr>
                <td>От даты: </td>
                <td><input type="date" name="fromDate" value="${startDate}"> </td>
                <td>До даты: </td>
                <td><input type="date" name="toDate" value="${endDate}"> </td>
            </tr>
            <tr>
                <td>От времени: </td>
                <td><input type="time" name="fromTime" value="${startTime}"></td>
                <td>До времени: </td>
                <td><input type="time" name="toTime" value="${endTime}"> </td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td><input type="submit"
                           value="Отфильтровать"></td>
            </tr>
        </table>
    </form>

    <h2>Meal list</h2>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <c:if test="${!empty meals}">
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>

        </c:forEach>
    </table>
    </c:if>
</section>
</body>
</html>
