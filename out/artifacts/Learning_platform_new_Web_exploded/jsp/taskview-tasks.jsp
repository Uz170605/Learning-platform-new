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

    <link rel = "icon" href =
            "/assets/images/img_4.png"
          type = "image/x-icon">


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body style="width: 90%; margin: 0 auto; background: white">

<%@include file="/jsp/navbar.jsp" %>

<%--<div class="d-flex justify-content-around">--%>
<%--    ROW OCHILDI--%>

<c:if test="${searchGoogle == 'searchGoogle'}">
    <div class="row">
        <div class="col-md-4 mt-4 offset-4">
            <form action="${pageContext.request.contextPath}/courses/courseAllData/${course.id}"  <c:url value="/users"/> >
                <div class="card" >
                    <div class="card-body pl-0 pr-0 pt-4 pb-4">
                        <p class="card-title" style="text-align: center">
                            <b style="text-align:center; margin-right: 5%;"> Not found:
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-emoji-smile-upside-down" viewBox="0 0 16 16">
                                    <path d="M8 1a7 7 0 1 0 0 14A7 7 0 0 0 8 1zm0-1a8 8 0 1 1 0 16A8 8 0 0 1 8 0z"/>
                                    <path d="M4.285 6.433a.5.5 0 0 0 .683-.183A3.498 3.498 0 0 1 8 4.5c1.295 0 2.426.703 3.032 1.75a.5.5 0 0 0 .866-.5A4.498 4.498 0 0 0 8 3.5a4.5 4.5 0 0 0-3.898 2.25.5.5 0 0 0 .183.683zM7 9.5C7 8.672 6.552 8 6 8s-1 .672-1 1.5.448 1.5 1 1.5 1-.672 1-1.5zm4 0c0-.828-.448-1.5-1-1.5s-1 .672-1 1.5.448 1.5 1 1.5 1-.672 1-1.5z"/>
                                </svg>
                            </b>
                        <p>
                        <p class="card-title " style="text-align: center">
                            <b style="text-align: center">Google link: </b>
                            <a href="https://www.google.com/search?q=${condition}" class="card-title">
                                ${condition}
                            </a>
                        </p>
                    </div>
                </div>
            </form>
        </div>
    </div>
</c:if>

<div class="row">
<c:forEach var="course" items="${courseList}">
    <div class="col-md-4 mt-4">

        <form action="/courses/courseAllData/${course.id}"  <c:url value="/users"/> >
            <div class="card">
                <button class="button button1 p-2" name="backType" value="main">
                    <img src="<c:url value='/assets/images/img.png'/>" alt="Avatar" style="width:100%">
                    <div class="card-body pl-0 pr-0 pt-4 pb-4">
                        <p class="card-title  ">
                            <b style="text-align:right"> Name: </b><c:out value="${course.name}"/><p>
                    </p>
                        <p class="card-title ">
                            <b>Price: </b><c:out value="${course.price}"/><p>
                    </p>

                        <p class="card-title ">
                            <b>Authors: </b>
                            <c:forEach var="author" items="${course.authors}">
                                <a href="/users/userAllData/${author.id}?backType=main" class="card-title">
                                        ${author.firstName.substring(0, 1)} ${author.lastName}
                                </a>
                            </c:forEach>
                        </p>

                            <%--                            <a href="<c:url value="#"--%>
                            <%--                             class="btn btn-warning">Edit</a>--%>
                            <%--                            <a href="<c:url value="/delete?id=${course.id}"/>" class="btn btn-danger ml-5">Delete</a>--%>
                    </div>
                </button>
            </div>
        </form>
            <%--                <c:out value="${ketmon.name}"/><p>--%>
    </div>
</c:forEach>
<%--    ROW YOPILDI--%>
</div>
<%--</div>--%>


<c:if test="${size > 6}">


    <div class="d-flex justify-content-around">
            <%--    ROW OCHILDI--%>
        <div class="row">

            <div class="col-md-4 mt-5 d-flex">
                <c:forEach var="j" begin="1" end="${size1   }">

                    <c:set var="inter" value="${interval}"/>

                    <c:if test="${condition == 'main'}">
                        <a href="<c:url value="/courses/test?currentPage=${j}&condition=main"/>"
                           class="btn btn-info m-2 ">${j}</a>
                    </c:if>

                    <c:if test="${condition == 'true' || condition == 'false'}">
                        <a href="<c:url value="/courses/test?currentPage=${j}&condition=${condition}"/>"
                           class="btn btn-info m-2 ">${j}</a>
                    </c:if>

                    <c:if test="${condition != 'main' && condition!='status' && !(condition == 'true' || condition == 'false')}">
                        <a href="<c:url value="/courses/test?currentPage=${j}&text=${condition}"/>"
                           class="btn btn-info m-2 ">${j}</a>
                    </c:if>


                    <%--                    <div class="card-body">--%>

                    <%--                    </div>--%>
                </c:forEach>
            </div>
        </div>
            <%--    ROW YOPILDI--%>
    </div>
</c:if>


</body>
</html>
