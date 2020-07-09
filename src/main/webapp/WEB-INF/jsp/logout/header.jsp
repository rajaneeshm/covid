<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Rajaneesh Munaganti
  Date: 03-07-2020
  Time: 07:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<nav class="navbar navbar-expand-xl navbar-light header-site-bg">
    <a class="navbar-brand" href="${contextPath}/dashboard"><img class="logo-img img-fluid" src="${contextPath}/img/logo.png" alt="" height="30" width="40"></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse"  style="justify-content: flex-end;">

        <ul class="navbar-nav mr-0">
            <li class="nav-item"><a href="${pageContext.request.contextPath}/login" class="nav-link">Login</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/registration" class="nav-link">Register</a></li>

        </ul>
    </div>
</nav>

