<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 02.03.2022
  Time: 3:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User form</title>
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
    <div class="col-md-6 offset-3 "
         style="background-color: white; border-radius:10px ;border: 2px solid gray;box-shadow: 5px 10px 8px #888888;z-index: 11;">
        <form action="/modules/editModule" method="POST" class="mt-5 mb-5"
              enctype="multipart/form-data">

            <input name="moduleId" hidden value="${module.moduleId}">

            <div class="form-group">
                <label for="ModuleName">Moudle name </label>
                <input value="${module.moduleName}" name="moduleName" type="text"
                       class="form-control"
                       id="ModuleName"
                       placeholder="Enter Module name">
            </div>


            <div class="form-group">
                <label for="bio">Price: </label>
                <textarea name="modulePrice" type="number" class="form-control"
                          id="bio"
                          placeholder="Enter module price here">${module.modulePrice}</textarea>
            </div>
            <div class="form-group">
            <button type="submit" class="btn btn-success">Save</button>
                <a href="/modules/courses_modules" class="btn btn-primary ml-3" style="width: 60px; height: 38px; text-align: center;"> Back </a>
            </div>
        </form>
    </div>

</div>


<script>
    $(document).ready(function () {
        var multipleCancelButton = new Choices('#mentor', {
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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
