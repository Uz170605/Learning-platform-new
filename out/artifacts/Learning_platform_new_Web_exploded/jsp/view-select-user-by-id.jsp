<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Azizjon
  Date: 14.02.2022
  Time: 0:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Select User</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
</head>
<body style="background-color: rgba(19,213,246,0.1);">

<div class="row mt-5 ml-0 mr-0"  style="height: 400px;">
    <div class="col-md-6 offset-3" style="background-color: white; border-radius:10px ;border: 2px solid gray;box-shadow: 5px 10px 8px #888888;z-index: 11;">
        <p class="mt-5"><b>First name:</b> ${user.firstName}</p>
        <p><b>Last name: </b> ${user.lastName}</p>
        <p><b>Phone number:</b> ${user.phoneNumber}</p>
        <p><b>Email:</b> ${user.email}</p>
        <p><b>Password: </b>${user.password}</p>
        <p><b>Bio:</b> </p>
        <p>${user.bio}</p>
        <h6 style="color: #00cc00;"><b>User roles</b></h6>
        <c:forEach var="role" items="${user.roles}">
            <p>${role.name}</p>
        </c:forEach>
        <a href="/users" class="btn btn-primary mt-3 mb-5"> Back </a>
    </div>
</div>





















<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
