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
    <title>Courses</title>
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



               <div>
                    <a href="/courses/addCourse" class="btn btn-success"> + Add</a>

                   <a href="<c:url value="/courses/test"/>" class="btn btn-success">
                       <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                            class="bi bi-house" viewBox="0 0 16 16">
                           <path fill-rule="evenodd"
                                 d="M2 13.5V7h1v6.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V7h1v6.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5zm11-11V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"></path>
                           <path fill-rule="evenodd"
                                 d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z"></path>
                       </svg>
                       Main</a>

                    <a href="<c:url value="/courses"/>" class="btn btn-success">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                             class="bi bi-house" viewBox="0 0 16 16">
                            <path fill-rule="evenodd"
                                  d="M2 13.5V7h1v6.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V7h1v6.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5zm11-11V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"></path>
                            <path fill-rule="evenodd"
                                  d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z"></path>
                        </svg>
                        Course main</a>

                    <form action="/courses" class="d-flex mt-4">
                        <input class="form-control me-2" type="search" name="text" placeholder="Search"
                               aria-label="Search">
                        <button class="btn btn-outline-warning" name="search" type="submit">Search</button>
                    </form>
               </div>


        <div class="row mt-4">
            <table class="table table-hover table-responsive-sm table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Module</th>
                    <th scope="col">Price</th>
                    <th scope="col">Is active</th>
                    <th scope="col">Edit</th>
                    <th scope="col">Delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="course" step="1" items="${courseList}">
                    <tr>
                        <th scope="row"> ⚫️</th>
                        <td><a href="/courses/courseAllData/${course.id}?backType=courseMain"
                               style="color: black;">${course.name}</a></td>
                        <td>
                            <c:forEach var="module" items="${course.module}">
                            <span><a href="/users/userAllData/${module.id}"
                                     style="color: black">${module.name} </a>,
                            </span>
                            </c:forEach>
                        </td>
                        <td>${course.price}</td>
                        <td>${course.active == true ?"Activ":"No activ"}</td>
                        <td><a href="/courses/${course.id}" class="btn btn-warning">Edit</a></td>
                            <%--                      <td><a href="courses/delete/${course.id}" class="btn btn-danger" >Delete</a></td>--%>
                        <td>
                            <button class="btn btn-danger"
                                    onclick="makeDELETErequest('/courses/${course.id}')"> Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


<c:if test="${size > 6}">


    <div class="d-flex justify-content-around">
            <%--    ROW OCHILDI--%>
        <div class="row">

            <div class="col-md-4 mt-5 d-flex">
                <c:forEach var="j" begin="1" end="${size2   }">
                    <a href="<c:url value="/courses?currentPage=${j}"/>"
                       class="btn btn-info m-2 ">${j}</a>

                </c:forEach>
            </div>
        </div>
            <%--    ROW YOPILDI--%>
    </div>
</c:if>


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
            {method: 'DELETE'}
        ).then(response => location.reload())
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
