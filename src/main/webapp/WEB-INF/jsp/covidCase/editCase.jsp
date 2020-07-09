<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Rajaneesh Munaganti
  Date: 06-07-2020
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <div class="row m-3 p-3">
        <div class="col-lg-3 col-md-3 col-12"></div>
        <div class="col-lg-6 col-md-6 col-12 borders">
            <form:form modelAttribute="case" action="${pageContext.request.contextPath}/getCase" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-group">
                    <label>Please Enter Patient Number</label>
                    <form:input class="form-control" path="patientnumber" required="true" type="number"></form:input>
                </div>
                <div class="form-group">
                    <form:button class="btn btn-primary">Get Data</form:button>
                </div>
            </form:form>
        </div>
        <div class="col-lg-3 col-md-3 col-12"></div>
    </div>
</div>
