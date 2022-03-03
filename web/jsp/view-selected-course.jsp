<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Akbarali
  Date: 2/28/2022
  Time: 3:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.rawgit.com/harvesthq/chosen/gh-pages/chosen.jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.css">
    <script src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>
</head>
<body>
<div class="row mt-5 ml-0 mr-0" style="height: 400px;">
    <div class="col-md-6 offset-3 "
         style="background-color: white; border-radius:10px ;border: 2px solid gray;box-shadow: 5px 10px 8px #888888;z-index: 11;">
        <div class="form-group">
            <h3 style="text-align: center">Name: ${course.name} </h3>
            <br>
            <div>

                <iframe width="400" height="200" src="${course.lessonVideoPath}"
                        title="YouTube video player" frameborder="0"
                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                        allowfullscreen>
                </iframe>
<%--                <div style="width: 400px; height: 200px">--%>
<%--                    ${course.lessonVideoPath}--%>
<%--                </div>--%>
            </div>
            <br>
            <hr>
            <h4>Description: ${course.description} </h4>
            <h4>Module name: ${course.moduleName} </h4>
            <h4>Module price: ${course.modulePrice} </h4>
            <hr>
            <h3 style="text-align: center">Authors</h3>
            <c:forEach items="${course.userDtos}" var="author">
                <h4>Full name: ${author.firstName} ${author.lastName}</h4>
                <h4>Phone number: ${author.phoneNumber}</h4>
                <h4>Email: ${author.email}</h4>
            </c:forEach>
            <hr>
            <h4>Lesson title: ${course.lessonTitle} </h4>
            <%--                        <input hidden name="messageId" value="${message.courseId}">--%>
            <div style="text-align: center">
                <a href="<c:url value="/admin/accept?messageId=${course.courseId}"/>" class="btn btn-success">
                    accept</a>
                <a href="<c:url value="/admin/reject?messageId=${course.courseId}"/>" class="btn btn-success">
                    reject</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
