<%--
  Created by IntelliJ IDEA.
  User: Rajaneesh Munaganti
  Date: 03-07-2020
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="container">
    <div class="row loginForm">
        <div class="col-lg-3 col-md-3 col-12 "></div>
        <div class="col-lg-6 col-md-6 col-12 ">
            <div class="login-from  borders mt-4">
                <form method="POST" action="${contextPath}/login" class="form-signin">
                    <h2 class="form-heading m-3">Log in</h2>
                    <div class="form-group m-5">
                        <input name="username" type="text" class="form-control" placeholder="Username" autofocus="true">
                    </div>
                    <div class="form-group m-5">
                        <input name="password" type="password"  class="form-control" placeholder="Password">
                        <span></span>
                    </div>
                    <div class="form-group m-5">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
                        <div class="in-out-msg"> <span>${error}</span></div>
                    </div>
                    <h4 class="text-center"><a href="${contextPath}/registration">Create an account</a></h4>
                </form>
            </div>
        </div>
        <div class="col-lg-3 col-md-3 col-12 "></div>
    </div>
</div>


