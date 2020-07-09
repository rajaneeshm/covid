<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Rajaneesh Munaganti
  Date: 06-07-2020
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function enableOrDisableUpdatedDate() {
        var selected = document.getElementById("currentstatus").value;
        console.log(selected)
        if (selected == 'Recovered') {
            document.getElementById("statuschangedate").disabled = false;
        }
        if (selected == 'Deceased') {
            document.getElementById("statuschangedate").disabled = false;
        }
        if (selected == 'Hospitalized') {
            document.getElementById("statuschangedate").disabled = true;
        }

    }
</script>


<div class="row">
    <div class="col-lg-12 col-md-12 col-12 text-cetner">
        <h4 class="page-title">Add Cases</h4>

    </div>
</div>
<div class="row ">

    <div class="col-lg-12 col-md-12 col-12">
        <div class="add-case-frm">
            <form:form modelAttribute="covidCase" action="${pageContext.request.contextPath}/addNewCase" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="row newCaseFormRow">
                    <div class="col-lg-4 col-md-4 col-12">
                        <div class="form-group">
                            <label>Status</label>
                            <form:select path="currentstatus" class="form-control" id="currentstatus" name="currentstatus"
                                         onchange="enableOrDisableUpdatedDate()">
                                <option value="Hospitalized">Hospitalized</option>
                               <%-- <option value="Recovered">Recovered</option>
                                <option value="Deceased">Deceased</option>--%>
                            </form:select>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4 col-12">
                        <div class="form-group">
                            <label>Date Detected</label><form:input class="form-control" id="dateannounced" required="true"
                                                                    path="dateannounced" type="date" value=""></form:input>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4 col-12">
                        <div class="form-group">
                            <label>Recovered/Deceased On</label><form:input id="statuschangedate" class="form-control"
                                                                            path="statuschangedate" type="date" disabled="true" value=""></form:input>
                        </div>
                    </div>
                </div>

                <div class="row newCaseFormRow">
                    <div class="col-lg-4 col-md-4 col-12">
                        <div class="form-group">
                            <label>Age</label>
                            <form:select path="agebracket" class="form-control" name="agebracket">
                                <option value="0-10">0-10</option>
                                <option value="10-20">10-20</option>
                                <option value="20-30">20-30</option>
                                <option value="30-40">30-40</option>
                                <option value="40-50">40-50</option>
                                <option value="50-60">50-60</option>
                                <option value="60-70">60-70</option>
                                <option value="70-80">70-80</option>
                                <option value="80-90">80-90</option>
                                <option value="90-100">90-100</option>
                            </form:select>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4 col-12">
                        <div class="form-group">
                            <label>Sex</label>
                            <form:select path="gender" class="form-control" name="gender">
                                <option value="M">Male</option>
                                <option value="F">Female</option>
                            </form:select>


                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4 col-12">
                        <div class="form-group">
                            <label>Notes</label>
                            <form:input id="notes" class="form-control" path="notes" type="text" value=""></form:input>
                        </div>
                    </div>
                </div>
                <div class="row newCaseFormRow">
                    <div class="col-lg-4 col-md-4 col-12">
                        <div class="form-group">
                            <label>State</label>
                            <form:select path="detectedstate" class="form-control" name="detectedstate">
                                <option value="Delhi">Delhi</option>
                                <option value="Telangana">Telangana</option>
                                <option value="Andhra Pradesh">Andhra Pradesh</option>
                                <option value="Jammu and Kashmir">Jammu and Kashmir</option>
                                <option value="Tamil Nadu">Tamil Nadu</option>
                                <option value="Punjab">Punjab</option>
                                <option value="Rajasthan">Rajasthan</option>
                                <option value="Uttar Pradesh">Uttar Pradesh</option>
                                <option value="Haryana">Haryana</option>
                                <option value="Karnataka">Karnataka</option>
                                <option value="Ladakh">Ladakh</option>
                                <option value="Maharashtra">Maharashtra</option>
                                <option value="Kerala">Kerala</option>
                                <option value="Gujarat">Gujarat</option>
                                <option value="Chandigarh">Chandigarh</option>
                                <option value="Puducherry">Puducherry</option>
                                <option value="Chhattisgarh">Chhattisgarh</option>
                                <option value="Uttarakhand">Uttarakhand</option>
                                <option value="Odisha">Odisha</option>
                                <option value="West Bengal">West Bengal</option>
                                <option value="Madhya Pradesh">Madhya Pradesh</option>
                                <option value="Himachal Pradesh">Himachal Pradesh</option>
                                <option value="Bihar">Bihar</option>
                                <option value="Manipur">Manipur</option>
                                <option value="Mizoram">Mizoram</option>
                                <option value="Goa">Goa</option>
                                <option value="Andaman and Nicobar Islands">Andaman and Nicobar Islands</option>
                                <option value="Jharkhand">Jharkhand</option>
                                <option value="Assam">Assam</option>
                                <option value="Arunachal Pradesh">Arunachal Pradesh</option>
                                <option value="Tripura">Tripura</option>
                                <option value="Meghalaya">Meghalaya</option>
                            </form:select>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4 col-12">
                        <div class="form-group">
                            <label>District</label>
                            <form:input path="detecteddistrict" class="form-control" name="detecteddistrict" type="text"
                                        value=""></form:input>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4 col-12">
                        <div class="form-group">
                            <label>City</label>
                            <form:input path="detectedcity" class="form-control" name="detectedcity" type="text" value=""></form:input>
                        </div>
                    </div>
                </div>

                <div class="row newCaseFormRow">
                    <div class="col-lg-3 col-md-3 col-12"></div>

                    <div class="col-lg-6 col-md-6 col-12">

                        <div class="form-group">
                            <form:button id="Save" class="form-control btn btn-primary" name="Save" type="submit" value="save">Save</form:button>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-3 col-12"></div>
                </div>
            </form:form>
        </div>
    </div>

</div>
