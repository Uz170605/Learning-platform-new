<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <form action="/lessons/addVideo" method="post" class="mt-5 mb-5">
            <c:if test="${selectedVideo== null}">
                <div class="form-group">
                    <input hidden value="${lessonId}" name="lesson_id" type="text"
                           class="form-control">
                </div>
                <div class="form-group">
                    <label for="lessonName">Video link: </label>
                    <input  name="video_path" type="text" class="form-control"
                            id="lessonName"
                            placeholder="Enter video link here">
                </div>
            </c:if>
            <c:if test="${selectedVideo!=null}">
                <div class="form-group">
                    <input hidden value="${selectedVideo.id}" name="id" type="text"
                           class="form-control">
                </div>
                <div class="form-group">
                    <input hidden value="${selectedVideo.lesson_id}" name="lesson_id" type="text"
                           class="form-control">
                </div>
                <div class="form-group">
                    <label for="lessonName2">Video link: </label>
                    <input  name="video_path" value="${selectedVideo.video_path}" type="text"
                            class="form-control"
                            id="lessonName2"
                            placeholder="Enter video link here">
                </div>
            </c:if>

            <button type="submit" class="btn btn-success">Save</button>
    <a href="/lessons/viewVideo/${lessonId}" class="btn btn-success
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
