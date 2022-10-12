<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<jsp:useBean id="cmnUtilFunc" class="ru.javawebinar.topjava.util.CommonUtilFunc"/>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<hr>
<h2>Meals</h2>
<h3><a href="index.html">Home</a></h3>
<c:set var="mealList" value='${requestScope["meals"]}'/>
<table>
    <tr>
        <th>Description</th>
        <th>Date</th>
        <th>Calories</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${mealList}" var="meal" varStatus="status">
            <tr style="color: ${meal.excess ? "red" : "green"}">
                <td>${meal.description}</td>
                <td>${cmnUtilFunc.getFormatDateTime(meal.dateTime)}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
    </c:forEach>
</table>
</body>
</html>