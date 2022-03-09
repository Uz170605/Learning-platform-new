<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
         <div class="col-md-10 offset-1 mt-5">
             <div class="row">
          <a href="/lessons/addLesson/${moduleId}" class="btn btn-success mr-3">+Add</a>
             </div>
              <div class="row mt-4">
              <table class="table table-hover table-responsive-sm table-striped">
                  <thead>
                  <tr>
                      <th scope="col">#</th>
                      <th scope="col">Title</th>
                      <th scope="col">Videos</th>
                      <th scope="col">Tasks</th>
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
                      <td><a href="/lessons/viewVideo/${lesson.id}" class="btn btn-success
                      mr-3">Videos</a></td>
                      <td><a href="/lessons/viewTask/${lesson.id}"
                             class="btn btn-success
                      mr-3">Tasks</a></td>
                      <td><a href="/lessons/editModuleId/${lesson.id}" class="btn btn-warning">Edit
                      </a></td>
                      <td><a href="/lessons/delete/${lesson.id}" class="btn btn-danger">Delete
                      </a></td>
                  </tr>
                </c:forEach>
                  </tbody>
              </table>
              </div>
              <div class="row">
                  <div class="col-md-4"></div>
                  <div class="col-md-4 ">
                      <c:set var = "button" scope = "session" value = "${buttonCount}"/>
                      <c:forEach var = "i" begin = "1" end = "${button}">
                          <a href="/lessons/page/${moduleId}/${i}"
                             class="btn btn-primary">${i}</a>
                      </c:forEach>
                  </div>
              </div>
              <div class="row " style="margin-top: 15px;">
              <a href="/modules/courses_modules"  class="btn btn-primary">Back</a>
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
