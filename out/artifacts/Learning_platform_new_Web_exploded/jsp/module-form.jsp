<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 15.02.2022
  Time: 8:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Module</title> <title>Welcome</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.rawgit.com/harvesthq/chosen/gh-pages/chosen.jquery.min.js"></script>


    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.css">
    <script src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>
</head>
<body style="background-color: rgba(19,213,246,0.1);">
<div class="row mt-5 ml-0 mr-0" style="height: 400px;">
    <div class="col-md-6 offset-3 " style="background-color: white; border-radius:10px ;border:
    2px solid gray;box-shadow: 5px 10px 8px #888888;z-index: 11;">
        <form action="/modules" method="post" class="mt-5 mb-5">
            <div class="form-group">
                <input hidden value="${selectModule.id ==null ? null:selectModule.id}" name="id"
                       type="text" class="form-control">
            </div>
            <div class="form-group">
                <label for="moduleName">Module Name: </label>
                <input value="${selectModule.name}" name="moduleName" type="text" class="form-control"
                       id="moduleName"
                       placeholder="Enter module name here">
            </div>
            <div class="form-group">

                <label for="modulePrice">Module Price: </label>
                <input value="${selectModule.price}" name="modulePrice" type="number" class="form-control"
                       id="modulePrice"
                       placeholder="Enter module price here">
            </div>
            <div class="form-group">
                <label for="authors">Authors: </label>
                <select required=${selectModule.id != null ? "": "required" }  aria-invalid="true"
                        id="authors" placeholder="Search author name" multiple name="authorsId">
                    <c:forEach var="authors" items="${authors}">
                        <option  value="${authors.id}" >${authors.firstName}
                                ${authors.lastName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="LessonTitle">Lesson Title</label>
                <input value="${selectLesson.title}" name="lessonTitle" type="text" class="form-control"
                id="LessonTitle" placeholder="Enter lesson title">
            </div>
            <div class="form-group">
                <label for="lessonVidePath">Video Path:</label>
                <input name="lessonVidePath" type="text" class="form-control"
                id="lessonVidePath" placeholder="Enter video path">
            </div>
            <button type="submit" class="btn btn-success">Save</button>


        </form>
    </div>
</div>
<script>
    $(document).ready(function (){
        var multipleCancelButton = new Choices('#authors',{
            removeItemButton: true,
            maxItemCount: 5,
            searchResultLimit: 5,
            renderChoiceLimit: 5
        });
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
