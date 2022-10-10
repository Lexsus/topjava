<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<hr>
<h2>Meals</h2>
<h3><a href="index.html">Home</a></h3>
<c:set var = "mealList"  value = '${requestScope["meals"]}'/>
<c:out value = "${mealList}"/>
<table>
    <tr>
        <th>Description</th>
        <th>Date</th>
        <th>Calories</th>
    </tr>
    <c:forEach items="${mealList}" var="meal" varStatus="status">
        <c:if test="${meal.excess}">
            <tr bgcolor="red">
                <td>${meal.description}</td>
                <td>${meal.dateTime.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss"))}</td>
                <td>${meal.calories}</td>
            </tr>
        </c:if>

        <c:if test="${!meal.excess}">
            <tr bgcolor="green">
                <td>${meal.description}</td>
                <td>${meal.dateTime.format( DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss"))}</td>
                <td>${meal.calories}</td>
            </tr>
        </c:if>
    </c:forEach>
</table>
</body>
</html>