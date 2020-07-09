<%--
  Created by IntelliJ IDEA.
  User: Rajaneesh Munaganti
  Date: 03-07-2020
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="row borders" style="position: relative">
    <div class="col-lg-12 col-md-12 col-12">
        <div class="row" onmouseover="loadTotals()">
            <div class="col-3 col-md-3 col-6 ">
                <div class="covid-cases total-cases">
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-12"><span>Confirmed</span></div>
                        <div class="col-lg-6 col-md-6 col-12"><span>${totalCasesDTO.totalCases}</span></div>
                    </div>


                </div>
            </div>
            <div class="col-3 col-md-3 col-6 ">
                <div class="covid-cases active-cases">

                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-12"><span>Active</span></div>
                        <div class="col-lg-6 col-md-6 col-12"><span>${totalCasesDTO.activeCases}</span></div>
                    </div>

                </div>
            </div>
            <div class="col-3 col-md-3 col-6 ">

                <div class="covid-cases recovered-cases">
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-12"><span>Recovered</span></div>
                        <div class="col-lg-6 col-md-6 col-12"><span>${totalCasesDTO.recoveredCases}</span></div>
                    </div>

                </div>

            </div>
            <div class="col-3 col-md-3 col-6 ">

                <div class="covid-cases deceased-cases">
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-12"><span>Deceased</span></div>
                        <div class="col-lg-6 col-md-6 col-12"><span>${totalCasesDTO.deceasedCases}</span></div>
                    </div>

                </div>

            </div>
        </div>

    </div>
</div>
<div class="row borders">
    <div class="col-lg-6 col-md-12">
        <div class="row covid-datatable">

            <div class="col-lg-12 col-md-12 col-12 borders">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="thead">
                        <tr>
                            <th scope="col">State/UT</th>
                            <th scope="col" class="borders total-cases">Confirmed</th>
                            <th scope="col" class="borders active-cases">Active</th>
                            <th scope="col" class="borders recovered-cases">Recovered</th>
                            <th scope="col" class="borders deceased-cases">Deceased</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${totalStateCasesDTOS}" var="totalStateCasesDTO">
                            <tr onmouseover="loadStateTotals('${totalStateCasesDTO.state}')">
                                <td>${totalStateCasesDTO.state}</td>
                                <td class="borders total-cases">${totalStateCasesDTO.totalCases}</td>
                                <td class="borders active-cases">${totalStateCasesDTO.activeCases}</td>
                                <td class="borders recovered-cases">${totalStateCasesDTO.recoveredCases}</td>
                                <td class="borders deceased-cases">${totalStateCasesDTO.deceasedCases}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-6 col-md-6 col-12 borders " style="background: azure;">
        <div clas="row">
            <div class="col-lg-12 col-md-12 col-12 my-2">
                <div id="chartContainerTotals" style="height: 100px; max-width: 920px; margin: 0px auto;"></div>

            </div>
        </div>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-12 m-2">
                <div id="chartContainerActives" style="height: 100px; max-width: 920px; margin: 0px auto;"></div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12 col-md-12 m-2">
                <div id="chartContainerRecovered" style="height: 100px; max-width: 920px; margin: 0px auto;"></div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-12 m-2">
                <div id="chartContainerDeceased" style="height: 100px; max-width: 920px; margin: 0px auto;"></div>
            </div>
        </div>
    </div>

</div>
</div>



