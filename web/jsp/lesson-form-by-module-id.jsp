<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Azizjon
  Date: 10.02.2022
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
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
    <div class="col-md-6 offset-3 " style="background-color: white; border-radius:10px ;border: 2px solid gray;box-shadow: 5px 10px 8px #888888;z-index: 11;" >

        <form action="/lessons/addLessonToModule" method="get" class="mt-5 mb-5">
            <c:if test="${selectLesson == null}" >
                <div class="form-group">
                    <label for="lessonName"> Lesson Title: </label>
                    <input  name="lessonTitle" type="text" class="form-control"
                            id="lessonName" value="${selectLesson.title}"
                            placeholder="Enter lesson title here">
                </div>
                <div class="form-group">
                    <input hidden value="${moduleId}" name="moduleId" type="text"
                           class="form-control">
                </div>
                <h5>Add video:</h5>
                <div class="form-group">
                    <label for="lessonName2">Video link: </label>
                    <input  name="lessonVideoPath" type="text" class="form-control"
                            id="lessonName2"
                            placeholder="Enter video link here">
                </div>
                <h5>Add task:</h5>
                <div class="form-group">
                    <label for="lessonNamet">Title: </label>
                    <input  name="taskTitle" type="text" class="form-control"
                            id="lessonNamet"
                            placeholder="Enter task title here">
                </div>
                <div class="form-group">
                    <label for="lessonName1">Difficulty degree: </label>
                    <input  name="taskDegree" type="number" class="form-control"
                            id="lessonName1"
                            placeholder="Enter task title here">
                </div>
                <div class="form-group">
                    <label for="lessonName12">Grade: </label>
                    <input  name="taskGrade" type="number" class="form-control"
                            id="lessonName12"
                            placeholder="Enter students grade">
                </div>
                <div class="form-group">
                    <label  for="lessonName4">Task body: </label>
                    <textarea id="lessonName4" name="taskBody" type="text" class="form-control"
                              placeholder="Enter task body here">
        </textarea>
                </div>
            </c:if>
            <c:if test="${selectLesson!= null}">
                <div class="form-group">
                    <input hidden value="${selectLesson.id}" name="lessonId" type="text" class="form-control">
                </div>
                <div class="form-group">
                    <label for="lessonName11"> Lesson Title: </label>
                    <input  name="lessonTitle" type="text" class="form-control"
                            id="lessonName11" value="${selectLesson.title}"
                            placeholder="Enter lesson title here">
                </div>
                <div class="form-group">
                    <input hidden value="${moduleId}" name="moduleId" type="text"
                           class="form-control">
                </div>
            </c:if>
            <button type="submit" class="btn btn-success">Save</button>
            <a href="/lessons/byModuleId/${moduleId}" class="btn btn-success
                      mr-3">Back</a>
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
