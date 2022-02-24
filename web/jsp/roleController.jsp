<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Akbarali
  Date: 2/4/2022
  Time: 5:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        .button {
            border: none;
            color: #4CAF50;
            padding: 16px 32px;
            text-align: left;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            transition-duration: 0.4s;
            cursor: pointer;
        }

        .button1 {
            background-color: white;
            color: black;
            border: 2px solid #bcf8e1;
        }

        .button1:hover {
            background-color: rgba(175, 175, 174, 0.6);
            color: black;
        }

        .button2 {
            background-color: white;
            color: black;
            border: 2px solid #008CBA;
        }

        .button2:hover {
            background-color: #008CBA;
            color: white;
        }

    </style>
    <title>
        courses
    </title>

    <link rel="icon" href=
            "/assets/images/img_4.png"
          type="image/x-icon">


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body style="width: 90%; margin: 0 auto; background: white">

<%@include file="/jsp/navbar.jsp" %>

<div class="row">
    <c:forEach var="role" items="${roles}">
        <div class="col-md-4 mt-4">
            <form action="/login/roleController/${role.id}"  <c:url value="/users"/> >
                <div class="card">
                    <button class="button button1 p-2" name="backType" value="main">
                        <div class="card-body pl-0 pr-0 pt-4 pb-4" style="text-align: center">
                            <p class="card-title ">
                                <b>${role.name}</b>
                            </p>
                        </div>
                    </button>
                </div>
            </form>
        </div>
    </c:forEach>
</div>


</body>
</html>