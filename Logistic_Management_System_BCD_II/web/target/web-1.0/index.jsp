<%--
  Created by IntelliJ IDEA.
  User: oshada kavintha
  Date: 5/7/2024
  Time: 6:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logistic Management System | Login</title>
    <link rel="stylesheet" href="css/bootstrap.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<div>
    <form action="login" method="post">
<%--    <form>--%>
        <table>
            <tr>
                <th>Email</th>
                <td><input type="text" name="email" id="email"></td>
            </tr>
            <tr>
                <th>Password</th>
                <td><input type="password" name="password" id="password"></td>
            </tr>
            <tr>
                <th></th>
<%--                <td><input type="submit" value="Login" onclick="login();"></td>--%>
                <td><input type="submit" value="Login"></td>
            </tr>
        </table>
    </form>
</div>

<script src="js/main.js"></script>
<script src="js/bootstrap.js"></script>
</body>
</html>
