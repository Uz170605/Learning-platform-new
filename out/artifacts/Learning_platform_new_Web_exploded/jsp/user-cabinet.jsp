<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2/27/2022
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Cabinet</title>
</head>
<body>
<c:set var="user_id" value="${userId}"
  <button type="submit"
          class="btn btn-success"><a href="/userPanel/myCourses${user_id}">My courses</a></button>
          class="btn btn-success"><a href="/userPanel/allCourses${user_id}">My courses</a></button>
</c:set>
</body>
</html>
