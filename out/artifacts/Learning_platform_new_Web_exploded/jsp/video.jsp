<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Akbarali
  Date: 2/21/2022
  Time: 1:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>

        header {
            position: relative;
            background-color: black;
            height: 75vh;
            min-height: 25rem;
            width: 100%;
            overflow: hidden;
        }

        header video {
            position: absolute;
            top: 50%;
            left: 50%;
            min-width: 100%;
            min-height: 100%;
            width: auto;
            height: auto;
            z-index: 0;
            -ms-transform: translateX(-50%) translateY(-50%);
            -moz-transform: translateX(-50%) translateY(-50%);
            -webkit-transform: translateX(-50%) translateY(-50%);
            transform: translateX(-50%) translateY(-50%);
        }

        header .container {
            position: relative;
            z-index: 2;
        }

        header .overlay {
            position: absolute;
            top: 0;
            left: 0;
            height: 100%;
            width: 100%;
            background-color: black;
            opacity: 0.5;
            z-index: 1;
        }

        /* Media Query for devices withi coarse pointers and no hover functionality */

        /* This will use a fallback image instead of a video for devices that commonly do not support the HTML5 video element */

        @media (pointer: coarse) and (hover: none) {
            header {
                background: url('https://source.unsplash.com/XT5OInaElMw/1600x900') black no-repeat center center scroll;
            }

            header video {
                display: none;
            }
        }

        .card {
            background-repeat: no-repeat;
            background-position: 50% 50%;
            background-size: cover;
            width: 3000px;
            height: 300px;
        }


        body {
            margin: 0;
        }

        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            width: 25%;
            background-color: #ffffff;
            position: fixed;
            height: 100%;
            overflow: auto;
        }

        li a {
            display: block;
            color: #000;
            padding: 8px 16px;
            text-decoration: none;
        }

        li a.active {
            background-color: #c8f6d5;
            color: #160b18;
        }

        li a:hover:not(.active) {
            background-color: #555;
            color: white;
        }

    </style>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Title</title>
</head>
<body style="background: #ffffff">


<ul style="background: lavender">
    <li>
        <h3 style="text-align: center;  color: white;
  text-shadow: 1px 1px 2px black,
   0 0 25px blue, 0 0 5px
    darkblue;">
            OUR TEAM
        </h3>
    </li>
    <li>
        <a class="active" href="/lessons" style="color: white; background: lightslategrey">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                 class="bi bi-house" viewBox="0 0 16 16">
                <path fill-rule="evenodd"
                      d="M2 13.5V7h1v6.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V7h1v6.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5zm11-11V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"></path>
                <path fill-rule="evenodd"
                      d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z"></path>
            </svg>
            Home
        </a>
    </li>
    <li><a href="#news">News</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#about">About</a></li>
</ul>

<div style="margin-left:25%;padding:1px 16px;height:1000px;">
    <h5 style="text-align: center"> ${data.courseDto.name} > ${data.moduleDto.name} >  ${lesson.title}    </h5>


    <div class="row">
    <div class="col-md-4 mt-4 ">
        <div class="card" style="width: 800px; height:700px; background: #f0fbfc">
            <video width="800" height="400"  controls>
                <source src="<c:url value="/assets/fileUpload/lessonVideo/${videoPath}"/>
                " type="video/mp4">
            </video>
            <div class="card-body">

                <h3 class="card-text">${lesson.title}</h3>
                <h5>Qo'llanma: </h5>
                <spring:url value="/download/pdfDownload" var = "downloadURL"/>
                <a href="<c:url value="/download/pdfDownload/${lesson_id}"/>" class="btn btn-primary">file download</a>


                <h5 class="mt-3">Vazifalar</h5>
                <spring:url value="/download/docDownload" var = "downloadURL"/>
                <a href="<c:url value="/download/docDownload/${lesson_id}"/>" class="btn btn-primary">file download</a>
            </div>
        </div>
    </div>
    </div>
<%--    <div class="row">--%>
<%--        <div class="col-md-8">--%>
<%--            <c:forEach var="j" begin="1" end="10">--%>
<%--                <div class="row">--%>
<%--                    <div class="col-md-4 ">--%>
<%--                        <div class="card" style="width: 18rem;">--%>
<%--                            <div class="card-body">--%>
<%--                                <h5 class="card-title">Card title</h5>--%>
<%--                                <p class="card-text">Some quick example text to build on the card title and make up the--%>
<%--                                    bulk--%>
<%--                                    of--%>
<%--                                    the--%>
<%--                                    card's content.</p>--%>
<%--                                <a href="#" class="btn btn-primary">Go somewhere</a>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </c:forEach>--%>
<%--        </div>--%>
<%--    </div>--%>


</div>


</body>
</html>
