<%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 08.12.2016
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <td style="width: 30px;">Время</td>
            <td style="width: 80px;">Наименование</td>
            <td style="width: 80px;">Калории</td>
            <%--<td style="width: 80px;">action</td>
            <td style="width: 80px;">action</td>--%>

        </tr>
        </thead>

        <c:forEach var="meal" items="${list}">

            <tr>
                <c:if test="${meal.isExceed()==true}"> <td bgcolor="red"> ${meal.getDateTime()}</td></c:if>
                <c:if test="${meal.isExceed()==false}"> <td bgcolor="#adff2f" > ${meal.getDateTime()}</td></c:if>
                <c:if test="${meal.isExceed()==true}"> <td bgcolor="red"> ${meal.getDescription()}</td></c:if>
                <c:if test="${meal.isExceed()==false}"> <td bgcolor="#adff2f" > ${meal.getDescription()}</td></c:if>
                <c:if test="${meal.isExceed()==true}"> <td bgcolor="red"> ${meal.getCalories()}</td></c:if>
                <c:if test="${meal.isExceed()==false}"> <td bgcolor="#adff2f" > ${meal.getCalories()}</td></c:if>
                <%--<td><a href="<c:url value='/edit/${users.id}'/>">Edit</a></td>
                <td><a href="<c:url value='/remove/${users.id}'/>">Delete</a></td>--%>
            </tr>
        </c:forEach>
   </table>

</body>
</html>
