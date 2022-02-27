<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Azizjon
  Date: 20.02.2022
  Time: 1:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Video upload</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.rawgit.com/harvesthq/chosen/gh-pages/chosen.jquery.min.js"></script>


    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.css">
    <script src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>
</head>
<body style="background-color: rgba(19,213,246,0.1);">
<div class="row ml-0 mr-0" style="margin-top: 50px;">
  <div class="col-md-4 offset-1">
<form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">

        <div class="form-group">
            <label for="videoUpload"><b>Select a file to upload:</b></label>
                <input type="file" name="file" id="videoUpload" accept="video/mp4" >
        </div>
    <div class="form-group">
        <label for="selectLesson"><b>Select lesson:</b></label>
        <select class="custom-select custom-select-md mb-3" name="lessonId" id="selectLesson">
            <c:forEach var="lesson" items="${lesson}">
                <option value="${lesson.id}">${lesson.title}</option>
            </c:forEach>
        </select>
    </div>


    <div class="form-group">
            <input type="submit" value="Upload File" class="btn btn-success">
        <a href="/upload/videoData" class="btn btn-primary ml-3" style="width: 60px;">    Back    </a>
        </div>
</form>
</div>
  </div>












<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
