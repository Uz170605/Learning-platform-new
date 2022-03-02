<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 02.03.2022
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Module form</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.rawgit.com/harvesthq/chosen/gh-pages/chosen.jquery.min.js"></script>


    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.css">
    <script src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>
</head>
<body  style="background-color: rgba(19,213,246,0.1);">
<div class="row mt-5 ml-0 mr-0" style="height: 400px;">
    <div class="col-md-6 offset-3 " style="background-color: white; border-radius:10px ;border:
    2px solid gray;box-shadow: 5px 10px 8px #888888;z-index: 11;">
        <form action="/modules/addModule" method="post" class="mt-5 mb-5" enctype="multipart/form-data">

            <div class="form-group">
                <label for="ModuleName">Module name </label>
                <input  name="moduleName" type="text" class="form-control" id="ModuleName"
                        placeholder="Enter module name">
            </div>

            <div class="form-group">
                <label for="author">Author: </label>
            <select  id="author"placeholder="Search mentor" multiple name="authorsId">
                <c:forEach var="author" items="${authors}">
                    <option value="${author.id}" >${author.firstName} ${author.lastName}</option>
                </c:forEach>
            </select>
            </div>


            <div class="form-group">
                <label for="ModulePrice">Module Price</label>
                <input name="modulePrice" type="number" class="form-group" id="ModulePrice">
            </div>


            <div class="form-group">
                <label for="LessonTitle">Lesson Tittle</label>
                <input name="lessonTitle" type="text" class="form-group" id="LessonTitle">
            </div>


            <div class="form-group">
            <label for="LessonVideoPath" >Lesson Video Path</label>
            <input name="lessonVideoPath" type="text" class="form-group" id="LessonVideoPath">
            </div>


            <div class="form-group">
                <label for="TaskTitle" >Task Title</label>
                <input name="taskTitle" type="text" class="form-group" id="TaskTitle">
            </div>


            <div class="form-group">
                <label for="TaskDegree">Task Degree</label>
                <input name="taskDegree" type="number" class="form-group" id="TaskDegree">
            </div>

            <div class="form-group">
                <label for="TaskGrade">Task Grade</label>
                <input name="taskGrade" type="number" class="form-group" id="TaskGrade">
            </div>

            <div class="form-group">
                <label for="TaskBody">Task Body</label>
                <textarea name="taskBody" id="TaskBody" placeholder="Enter task body...."></textarea>
            </div>
            <button type="submit" class="btn btn-success">Save</button>
        </form>
    </div>
</div>
<script>
    $(document).ready(function (){
        var multipleCancelButton = new Choices('#author',{
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
