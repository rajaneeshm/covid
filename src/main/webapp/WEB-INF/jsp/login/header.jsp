<%--
  Created by IntelliJ IDEA.
  User: Rajaneesh Munaganti
  Date: 03-07-2020
  Time: 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<nav class="navbar navbar-expand-xl navbar-light  header-site-bg">
    <a class="navbar-brand" href="${contextPath}/dashboard"><img class="logo-img img-fluid" src="${contextPath}/img/logo.png" alt="" height="40" width="40"></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse" style="justify-content: flex-end;">
        <sec:authorize access="hasAuthority('ADMIN')">
            <ul class="nav navbar-nav mx-auto">
                <li class="nav-item active">
                    <a  class="nav-link" href="${contextPath}/addNewCase">Add New Case</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${contextPath}/editCase">Edit Case</a>
                </li>
            </ul>
        </sec:authorize>
        <ul class="nav navbar-nav mr-0  navbar-right">
            <li class="nav-item">
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <form id="logoutForm" method="POST" action="${contextPath}/logout">
                        <input type="hidden" id="token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>

                    <h5 class="welcome-text font-weight-normal">Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h5>
                </c:if>
            </li>

        </ul>
    </div>
</nav>
