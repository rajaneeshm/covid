<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Rajaneesh Munaganti
  Date: 06-07-2020
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${empty caseDTO}">
        <div class="container m-4 p-4">
            <div class="row">
                <div class="col-lg-4 col-md-4 col-12 text-cetner">

                </div>
                <div class="col-lg-4 col-md-4 col-12 text-cetner">
                    <div class="form-group">
                        <label>Case Not Found</label>
                    </div>
                </div>
                <div class="col-lg-4 col-md-4 col-12 text-cetner">

                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-12 text-cetner">
                <h4 class="page-title">Case Details</h4>

            </div>
        </div>
        <div class="row ">

            <div class="col-lg-12 col-md-12 col-12">
                <div class="add-case-frm">
                    <form:form modelAttribute="caseDTO" action="${pageContext.request.contextPath}/updateCase" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <form:hidden path="patientnumber"></form:hidden>
                        <div class="row newCaseFormRow">
                            <div class="col-4">
                                <div class="form-group">
                                    <label>Status</label>
                                    <c:choose>
                                        <c:when test="${caseDTO.currentstatus eq 'Hospitalized'}">
                                            Is ${caseDTO.currentstatus}
                                            <form:select path="currentstatus" class="form-control">
                                                <%-- <option value="Hospitalized">Hospitalized</option>--%>
                                                <option value="Recovered">Recovered</option>
                                                <option value="Deceased">Deceased</option>
                                            </form:select>
                                        </c:when>
                                        <c:otherwise>
                                            <form:input path="currentstatus" class="form-control" disabled="true"></form:input>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                    <label>Date Detected</label>
                                    <form:input class="form-control" id="dateannounced" path="dateannounced" type="date" disabled="true"></form:input>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                    <label>Recovered/Deceased On</label>
                                    <c:choose>
                                        <c:when test="${caseDTO.currentstatus eq 'Hospitalized'}">
                                            <form:input id="statuschangedate" class="form-control" path="statuschangedate" required="true" type="date"></form:input>
                                        </c:when>
                                        <c:otherwise><form:input id="statuschangedate" class="form-control" path="statuschangedate" disabled="true" type="date"></form:input></c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                        <div class="row newCaseFormRow">
                            <div class="col-4">
                                <div class="form-group">
                                    <label>Age</label>
                                    <form:input id="statuschangedate" class="form-control" path="agebracket" disabled="true"></form:input>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                    <label>Sex</label>
                                    <form:input path="gender" disabled="true" class="form-control" name="gender"></form:input>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                    <label>Notes</label>
                                    <form:input id="notes" class="form-control" path="notes" disabled="true"></form:input>
                                </div>
                            </div>
                        </div>
                        <div class="row newCaseFormRow">
                            <div class="col-4">
                                <div class="form-group">
                                    <label>State</label>
                                    <form:input disabled="true" path="detectedstate" class="form-control" name="detectedstate"></form:input>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                    <label>District</label>
                                    <form:input path="detecteddistrict" disabled="true" class="form-control" name="detecteddistrict"></form:input>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                    <label>City</label>
                                    <form:input path="detectedcity" disabled="true" class="form-control" name="detectedcity"></form:input>
                                </div>
                            </div>
                        </div>

                        <div class="row newCaseFormRow">
                            <div class="col-3"></div>
                            <div class="col-6">
                                <div class="form-group">
                                    <c:choose>
                                        <c:when test="${caseDTO.currentstatus eq 'Hospitalized'}">
                                            <form:button id="Save" class="form-control btn btn-primary" name="Update" type="submit" value="Update">Update</form:button>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="col-3"></div>
                        </div>
                    </form:form>
                </div>
            </div>

        </div>

    </c:otherwise>
</c:choose>
