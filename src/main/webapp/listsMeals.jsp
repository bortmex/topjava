<%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 08.12.2016
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="f" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meals list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meals list</h2>

    <table class="item-table">

        <thead>
        <tr style="background-color: gray;">
            <td style="width: 30px;">АЙди</td>
            <td style="width: 30px;">Время</td>
            <td style="width: 80px;">Наименование</td>
            <td style="width: 80px;">Калории</td>
            <th style="width: 80px;" colspan=2>Action</th>
        </tr>
        </thead>

        <c:forEach var="meal" items="${list}">
            <tr  bgcolor="${meal.isExceed() ? "red" : "#adff2f"}" >
                <td> ${meal.id}</td>
                <td><c:out value="${f:formatLocalDateTime(meal.dateTime, 'HH:mm:ss MM/dd/uuuu')}"/></td>
                <td> ${meal.description}</td>
                <td> ${meal.calories}</td>
                <td><a href="meal?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
                <td><a href="meal?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
   </table>

<h2>Add Meals</h2>

<p><a href="meal?action=insert">Add Meal</a></p>

</body>
</html>
