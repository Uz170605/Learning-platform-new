<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 14.02.2022
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modules</title>
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
            <c:when test="${param.message !=null}">
                <h1 style="color: #00cc00; font-family: 'Comic Sans MS'; text-align: center">${param.message}</h1>
            </c:when>
        </c:choose>
        <a href="/modules/addModule" class="btn btn-success">+Add</a>
        <form action="${pageContext.request.contextPath}/modules?search" class="d-flex" style="width:100%; margin-top: 20px;">
                <input type="search" name="search" style="width: 85%;" class="form-control"
                       placeholder="Search......">
                <a href="/modules" class="btn btn-primary ml-3" style="width: 150px; height: 40px;">  Reset  </a>
        </form>
        <div class="row mt-4 mr-0 ml-0">
            <table class="table table-hover table-responsive-sm table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Module name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Status</th>
                    <th scope="col">Add Lessons</th>
                    <th scope="col">Edit</th>
                    <th scope="col">Delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="module" step="1" items="${moduleList}">
                    <tr>
                        <th scope="row"> 📀 </th>
                        <td><a href="/modules/moduleAllData/${module.id}"style="color:
                        black;">${module.name}
                        </a>
                        </td>
<%--                        <td>${module.active == true ? "Active":"No active"}</td>--%>
                        <td>${module.price}</td>
                        <td>${module.status}</td>
                        <td>
                            <c:forEach var="lesson" items="${module.lessons}">
                                <span><a href="/lessons/${lesson.id}"
                                         style="color: black">${lesson.title}</a>
                                </span>
                            </c:forEach>
                        </td>
                        <td><a href="/modules/${module.id}" class="btn btn-warning">Edit
                        </a> </td>
                        <td><a href="/modules/delete/${module.id}" class="btn btn-success">Delete
                        </a>
                        </td>
                        <td>
                            <button class="btn btn-danger"
                    onclick="makeDELETErequest('//${module.id}')">Send</button>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row">
            <div class="col-md-4 offset-4">
                <c:forEach var="j" begin="1" end="${page}">
                    <a class="btn btn-primary mr-2" href="/modules?page=${(j-1)*3}">${j}</a>
                </c:forEach>
            </div>
        </div>
<a href="/courses/test" class="btn btn-primary mt-3"> Back </a>
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
