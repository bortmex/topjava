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
            <td style="width: 10px;">ID</td>
            <td style="width: 30px;">Время</td>
            <td style="width: 80px;">Наименование</td>
            <td style="width: 80px;">Калории</td>
            <th style="width: 80px;" colspan=2>Action</th>
        </tr>
        </thead>

        <c:forEach var="meal" items="${list}">
            <tr>
                <c:if test="${meal.isExceed()==true}"> <td bgcolor="red"> ${meal.id}</td></c:if>
                <c:if test="${meal.isExceed()==false}"> <td bgcolor="#adff2f" > ${meal.id}</td></c:if>
                <c:if test="${meal.isExceed()==true}"> <td bgcolor="red"> ${meal.dateTime.toLocalDate()}</td></c:if>
                <c:if test="${meal.isExceed()==false}"> <td bgcolor="#adff2f" > ${meal.dateTime.toLocalDate()}</td></c:if>
                <c:if test="${meal.isExceed()==true}"> <td bgcolor="red"> ${meal.description}</td></c:if>
                <c:if test="${meal.isExceed()==false}"> <td bgcolor="#adff2f" > ${meal.description}</td></c:if>
                <c:if test="${meal.isExceed()==true}"> <td bgcolor="red"> ${meal.calories}</td></c:if>
                <c:if test="${meal.isExceed()==false}"> <td bgcolor="#adff2f" > ${meal.calories}</td></c:if>
                <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
                <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
   </table>

<h2>Add Meals</h2>

<form method="POST" action='meals' name="frmAddMeal">
    DATE ("HH:mm:ss MM/dd/uuuu") : <input
        type="datetime" name="dob"
        value="<fmt:formatDate pattern="MM/dd/yyyy" value="${meal.dateTime}" />" /> <br />

    Description : <input type="text" name="mealdescription"
                     value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br /> <input
        type="submit" value="Отправить" />

</form>

</body>
</html>
