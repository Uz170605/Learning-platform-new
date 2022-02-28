<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Azizjon
  Date: 09.02.2022
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lessons</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    <script src=
                    "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js">
    </script>
</head>

<body>
     <div class="row ml-0 mr-0">
         <div class="col-md-8 offset-2 mt-5">
             <c:choose>
                 <c:when test="${param.message != null}">
                     <h1 style="color: #00cc00; font-family: 'Comic Sans MS'; text-align: center">${param.message}</h1>
                 </c:when>
             </c:choose>
             <div class="row">
             <table>
                 <tr>
         <td> <a href="/lessons/addLesson/${moduleId}" class="btn btn-success mr-3">+Add</a> </td>


<%--          <td><a href="/upload/manualData" class="btn btn-success">Add Manual</a></td>--%>
                 </tr>
             </table>
             </div>
          <div class="row mt-4">
              <table class="table table-hover table-responsive-sm table-striped">
                  <thead>
                  <tr>
                      <th scope="col">#</th>
                      <th scope="col">Title</th>
<%--                      <th scope="col">Lesson module</th>--%>
                      <th scope="col">Add Video</th>
                      <th scope="col">Add Task</th>
                      <th scope="col">Edit</th>
                      <th scope="col">Delete</th>

                  </tr>
                  </thead>
                  <tbody>
                <c:forEach var="lesson" step="1" items="${lessonList}">
                  <tr>
                      <th scope="row"> ⚫️ </th>
                      <td><a href="/download/viewVideo/${lesson.id}" style="color:
                      black">${lesson.title}</a></td>
<%--                      <td>--%>
<%--                          <c:forEach var="author" items="${lesson.authors}">--%>
<%--                              <span><a href="/users/userAllData/${author.id}" style="color: black">${author.firstName} ${author.lastName}</a>,</span>--%>
<%--                          </c:forEach>--%>
<%--                      </td>--%>
<%--                      <td>${lesson.price}</td>--%>
<%--                      <td>${lesson.active == true ?"Activ":"No activ"}</td>--%>
<%--                      <td><a href="/modules/moduleAllData/${lesson.moduleDto.id}"--%>
<%--                      >${lesson.moduleDto.name}</a>--%>
<%--                      </td>--%>
<%--                      <td>${lesson.task}</td>--%>
<%--                      <td>${lesson.guide}</td>--%>
                      <td><a href="/lessons/addVideo/${lesson.id}" class="btn btn-success
                      mr-3">Add Video</a></td>
                      <td><a href="/lessons/addTask/${lesson.id}"
                             class="btn btn-success
                      mr-3">Add Task</a></td>

                      <td><a href="/lessons/${lesson.id}" class="btn btn-warning">Edit</a></td>
<%--                      <td><a href="lessons/delete/${lesson.id}" class="btn btn-danger" >Delete</a></td>--%>
                      <td><a href="/lessons/delete/${lesson.id}" class="btn btn-danger">Delete
                      </a></td>
                  </tr>
                </c:forEach>
                  </tbody>
              </table>
              <div class="row">
                  <div class="col-md-4 offset-4">
                      <c:set var = "button" scope = "session" value = "${buttonCount}"/>
                      <c:forEach var = "i" begin = "1" end = "${button}">
                          <a href="/lessons/page/${i}"  class="btn btn-success">${i}</a>
                      </c:forEach>
                  </div>
              </div>
              <div class="row " style="margin-top: 50px;">
              <a href="/modules"  class="btn btn-primary">Back</a>
              </div>
          </div>
         </div>
     </div>












     <script>
         function makePUTrequest() {
             $.ajax({
                 url: 'test.html',
                 type: 'PUT',
                 success: function (result) {
                     // Do something with the result
                 }
             });
         }

         function makeDELETErequest(url) {
          fetch(
              url,
              {method:'DELETE'}
          ).then(response => location.reload())
         }
     </script>
     <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
     <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
     <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
