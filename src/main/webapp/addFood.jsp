<%--
  Created by IntelliJ IDEA.
  User: kisso
  Date: 4/4/2026
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding food</title>
</head>
<body>
    <form method="post">
        <table>
            <thead>
                <tr>
                    <th>Food</th>
                    <th>Restaurant</th>
                    <th>Price (HUF)</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><input name="name" id="name" type="text"></td>
                    <td><input name="restaurant" id="restaurant" type="text"></td>
                    <td><input name="price" id="price" type="number"></td>
                    <td><input type="submit" value="OK"></td>
                </tr>
            </tbody>
        </table>
    </form>
</body>
</html>
