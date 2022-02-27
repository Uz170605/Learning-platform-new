<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Azizjon
  Date: 14.02.2022
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User form</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.rawgit.com/harvesthq/chosen/gh-pages/chosen.jquery.min.js"></script>


    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.css">
    <script src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>
</head>
<body style="background-color: rgba(19,213,246,0.1);">

<h1 style="color: red; text-align: center">
    ${error}
</h1>
<div class="row mt-5 ml-0 mr-0" style="height: 400px;">
    <div class="col-md-6 offset-3 " style="background-color: white; border-radius:10px ;border: 2px solid gray;box-shadow: 5px 10px 8px #888888;z-index: 11;" >
        <form action="/login" method="post" class="mt-5 mb-5">

            <div class="form-group">
                <label for="UserName">First name: </label>
                <input name="firstName" type="text" class="form-control" id="UserName"
                       placeholder="Enter first name here">
            </div>
            <div class="form-group">
                <label for="lastName">Last name: </label>
                <input  name="lastName" type="text" class="form-control"
                       id="lastName"
                       placeholder="Enter last name here">
            </div>

            <div class="form-group">
                <label for="phoneNumber">Phone number: </label>
                <input  name="phoneNumber" type="tel" class="form-control"
                       id="phoneNumber"
                       placeholder="Enter phone number here">
            </div>

            <div class="form-group">
                <label for="email">Email : </label>
                <input  name="email" type="email" class="form-control"
                       id="email"
                       placeholder="Enter email here">
            </div>

            <div class="form-group">
                <label for="password">Password: </label>
                <input name="password" type="password" class="form-control"
                       id="password"
                       placeholder="Enter password here">
            </div>

            <div class="form-group">
                <label for="bio">Bio: </label>
                <textarea  name="bio" type="text" class="form-control"
                           id="bio" placeholder="Enter course description here"></textarea>
            </div>




            <button type="submit" class="btn btn-success">Register</button>
        </form>
    </div>

</div>