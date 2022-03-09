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
    <title>Tasks</title>
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
         <td> <a href="/lessons/addVideo/${lessonId}" class="btn btn-success mr-3">+Add</a> </td>


<%--          <td><a href="/upload/manualData" class="btn btn-success">Add Manual</a></td>--%>
                 </tr>
             </table>
             </div>
          <div class="row mt-4">
              <table class="table table-hover table-responsive-sm table-striped">
                  <thead>
                  <tr>
                      <th scope="col">#</th>
                      <th scope="col">Video path</th>
                      <th scope="col">Edit</th>
                      <th scope="col">Delete</th>

                  </tr>
                  </thead>
                  <tbody>
                <c:forEach var="video" step="1" items="${videoList}">
                  <tr>
                      <th scope="row"> ⚫️ </th>
                      <td><a href="/download/viewVideo/${lessonId}" style="color:
                      black">${video.video_path}</a></td>

                      <td><c:if test="${video != null}">
                          <a href="/lessons/editAttachment/${video.id}" class="btn btn-warning">Edit
                          </a>
                      </c:if></td>
                      <td><c:if test="${video != null}">
                          <a href="/lessons/deleteAttachment/${video.id}"
                             class="btn btn-danger">Delete
                          </a>
                      </c:if></td>
                  </tr>
                </c:forEach>
                  </tbody>
              </table>
              <div class="row " style="margin-top: 50px;">
              <a href="/lessons/byModuleId/${moduleId}"  class="btn btn-primary">Back</a>
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
