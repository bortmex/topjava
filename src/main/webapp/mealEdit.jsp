<%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 11.12.2016
  Time: 22:29
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 08.12.2016
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h2>Add Meals</h2>

<form method="POST" action='meal' name="frmAddMeal">

    Meal ID : <input type="text" readonly="readonly" name="mealId"
                     value="<c:out value="${meal.id}" />" /> <br />

    DATE ("HH:mm:ss MM/dd/uuuu") : <input
        type="datetime" name="dob"
        value="${meal.dateTime}" /> <br />

    Description : <input type="text" name="mealdescription"
                         value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br /> <input
        type="submit" value="Отправить" />

</form>

</body>
</html>
