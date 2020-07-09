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
    <div class="row p-5 m-5">


        <div class="col-6 borders ">
            <div class="form-group p-4 m-4">
                <label>New Case Created with Patient Id : </label>
                <label>${patientNumber}</label>
            </div>
        </div>


        <div class="col-6 borders">
            <div class="form-group p-4 m-4">
                <label> <a href="${pageContext.request.contextPath}/addNewCase">Create One More Case.</a> </label>
            </div>
        </div>


    </div>

</div>