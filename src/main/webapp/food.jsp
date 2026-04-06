<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/favourite.tld" %>

<html>
<head>
    <title>Food</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/AddFood"> Add food</a>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Restaurant</th>
                <th>Food</th>
                <th>Price</th>
                <th>Functions</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="food" items="${model}">
            <tr>
                <td><c:out value="${food.id}"/></td>
                <td><c:out value="${food.restaurantName}"/></td>
                <td><c:out value="${food.foodName}"/></td>
                <td><c:out value="${food.price}"/></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/DeleteFood">
                        <input name="id" id="deleteid" type="hidden" value="${food.id}"/>
                        <input type="submit" value="Delete"/>
                    </form>
                </td>
                <td>
                    <form method="post">
                        <input name="id" id="favouriteid" type="hidden" value="${food.id}"/>
                        <input type="submit" value="Favourite"/>
                    </form>
                </td>
                <td>
                    <form method="get">
                        <input name="id" id="xmlid" type="hidden" value="${food.id}"/>
                        <input type="submit" value="XML log"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <f:favourite/>
</body>
</html>
