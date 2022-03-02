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
    <script src=
                    "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js">
    </script>
</head>
<body>
<div class="row ml-0 mr-0">
    <div class="col-md-10 offset-1 mt-5">
        <a href="${pageContext.request.contextPath}/upload/manual" class="btn btn-success"> + Add</a>
        <div class="row mt-4">
            <table class="table table-hover table-responsive-sm table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">File name</th>
                    <th scope="col">Lesson name</th>
                    <th scope="col">Delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="file" step="1" items="${fileList}">
                    <tr>
                        <th scope="row"> ⚫️ </th>
                        <td><a href="/upload/userData/${file.id}">${file.fileName}</a></td>
                        <td><a href="/lessons/lessonAllData/${file.lessonDto.id}">${file.lessonDto.title}</a></td>
                        <td><button class="btn btn-danger"
                                    onclick="makeDELETErequest('/upload/${file.id}')"> Delete </button></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <a href="/lessons" class="btn btn-primary mt-5"> Back </a>
    </div>
</div>


<script>
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
