<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <title>Java Enterprise (Topjava)</title>
</head>
<body>
<h3>Проект <a href="https://github.com/JavaWebinar/topjava09" target="_blank">Java Enterprise (Topjava)</a></h3>

<form >
    <p><label>
        <select name="id" size="2" multiple>
            <option selected value="1">1</option>
            <option value="2">2</option>
        </select>
    </label>
        <input type="submit" value="Отправить"></p>
</form>

<hr>
<ul>
    <li><a href="users">User List</a></li>
    <li><a href="meals">Meal List</a></li>
</ul>
</body>
