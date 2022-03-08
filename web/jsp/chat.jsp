<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Akbarali
  Date: 14.02.2022
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            margin: 0;
            font-size: 28px;
            font-family: Arial, Helvetica, sans-serif;
        }

        .header {
            position: fixed;
            top: 0;
            z-index: 1;
            width: 100%;
            background-color: #f1f1f1;
        }

        .header h2 {
            text-align: center;
        }

        .progress-container {
            width: 100%;
            height: 8px;
            background: #ccc;
        }

        .progress-bar {
            height: 8px;
            background: #04AA6D;
            width: 0%;
        }

        .content {
            padding: 100px 0;
            margin: 50px auto 0 auto;
            width: 80%;
        }
    </style>


    <title>User form</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.rawgit.com/harvesthq/chosen/gh-pages/chosen.jquery.min.js"></script>


    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.css">
    <script src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>
</head>
<body style="background-color: rgba(19,213,246,0.1);" >
<div class="row mt-5 ml-0 mr-0 " style="height: 400px;" >
    <div class="col-md-6 offset-3 "
         style="background-color: white; border-radius:10px ;border: 2px solid gray;box-shadow: 5px 10px 8px #888888;z-index: 11;">
        <form action="<c:url value="/chat/student"/>" method="POST" class="mt-5 mb-5" enctype="multipart/form-data">


            <div >
                <h2>CHAT</h2>
                <div class="progress-container">
                    <div class="progress-bar" id="myBar"></div>
                </div>
            </div>

            <div class="content " >
                <c:forEach var="msg" items="${chat.messages}">
                    <c:if test="${msg.role == 'USER'}">

                        <h4 style="text-align: right">
                                ${msg.message}
                        </h4>

                        <h6 style="text-align: right">
                                ${msg.time}
                        </h6>
                    </c:if>

                    <c:if test="${msg.role=='MENTOR'}">
                        <h4>
                                ${msg.message}

                        </h4>
                        <h7>
                                ${msg.time}
                        </h7>
                    </c:if>
                </c:forEach>
            </div>

            <div class="form-group ">
                <div class=" d-flex">
                    <input name="message" type="text" class="form-control" id="LessonVideo"
                           placeholder="text...">

                        <input type="file" name="file" id="imageUpload"
                               accept="application/pdf">

                    <button type="submit" class="btn btn-success">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                             class="bi bi-send" viewBox="0 0 16 16">
                            <path d="M15.854.146a.5.5 0 0 1 .11.54l-5.819 14.547a.75.75 0 0 1-1.329.124l-3.178-4.995L.643 7.184a.75.75 0 0 1 .124-1.33L15.314.037a.5.5 0 0 1 .54.11ZM6.636 10.07l2.761 4.338L14.13 2.576 6.636 10.07Zm6.787-8.201L1.591 6.602l4.339 2.76 7.494-7.493Z"></path>
                        </svg>
                    </button>
                </div>

            </div>

        </form>
    </div>

</div>

<script>
    // When the user scrolls the page, execute myFunction
    window.onscroll = function() {myFunction()};

    function myFunction() {
        var winScroll = document.body.scrollTop || document.documentElement.scrollTop;
        var height = document.documentElement.scrollHeight - document.documentElement.clientHeight;
        var scrolled = (winScroll / height) * 100;
        document.getElementById("myBar").style.width = scrolled + "%";
    }
</script>


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
