<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<head>
    <title>Meal</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<html>
<body>
<section>
    <%--<h2>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>--%>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <c:url var="addAction" value="/meal/add"/>
    <form:form action="${addAction}" commandName="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><fmt:message key="meals.datetime"/>:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="meals.description"/>:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="meals.calories"/>:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories"></dd>
        </dl>
        <td colspan="2">
            <c:if test="${!empty meal.id}">
                <input type="submit"
                       value=<fmt:message key="meals.edit"/>/>
            </c:if>
            <c:if test="${empty meal.id}">
                <input type="submit"
                       value=<fmt:message key="meals.add"/>/>
            </c:if>
        </td>
        <button onclick="window.history.back()">Cancel</button>
    </form:form>
</section>
</body>
</html>