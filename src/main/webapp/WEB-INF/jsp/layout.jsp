<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>



<!DOCTYPE html>
<html lang="en">
<head>
    <c:set var="contextPath" value="${appUrl}"/>
    <meta charset="utf-8">
    <title>Covid-19 Tracker</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <!-- CSS only -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="${appUrl}/css/style.css">

    <!-- JS, Popper.js, and jQuery -->
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script>


    <script>
        var dataPoints = [];
        var dataPoints_totals = [];
        var dataPoints_totals_Cumulative = [];
        var dataPoints_active = [];
        var dataPoints_active_Cumulative = [];
        var dataPoints_recovered = [];
        var dataPoints_recovered_Cumulative = [];
        var dataPoints_deceased = [];
        var dataPoints_deceased_Cumulative = [];
        var chart;
        var option_totals = {
            animationEnabled: true,
            title:{
                text: "Totals"
            },
            toolTip: {
                shared: true
            },
            axisY: {
                gridThickness: 0,
            },
            axisY2: {
                gridThickness: 0,
            },
            axisX: {
                labelFormatter: function (e) {
                    return CanvasJS.formatDate(e.value, "DD MMM");
                },
            },
            data: [{
                type: "spline",
                name: "DailyTotal",
                color: "#f75252",
                showInLegend: true,
                dataPoints: dataPoints_totals
            },
                {
                    type: "spline",
                    name: "Cumulative",
                    color: "rgb(161,35,33)",
                    showInLegend: true,
                    dataPoints: dataPoints_totals_Cumulative
                }]

        };
        var option_active = {

            zoomEnabled: true,
            title: {
                text: "Active"
            }, toolTip: {
                shared: true
            },
            animationEnabled: true,
            axisY: {
                gridThickness: 0,
            },
            axisY2: {
                gridThickness: 0,
            },
            axisX: {
                labelFormatter: function (e) {
                    return CanvasJS.formatDate(e.value, "DD MMM");
                },
            },
            dataPointWidth: 3,
            data: [
                {
                    type: "spline",
                    name: "DailyTotal",
                    showInLegend: true,
                    color: "#74c674",
                    xValueType: "dateTime",
                    dataPoints: dataPoints_active
                },{
                    type: "spline",
                    name: "Cumulative",
                    showInLegend: true,
                    color: "rgb(36,148,21)",
                    xValueType: "dateTime",
                    dataPoints: dataPoints_active_Cumulative
                }
            ]
        };
        var option_recovered = {
            zoomEnabled: true,
            title: {
                text: "Recovered"
            },
            animationEnabled: true,
            axisY: {
                gridThickness: 0,

            },
            axisY2: {
                gridThickness: 0,

            },
            axisX: {
                labelFormatter: function (e) {
                    return CanvasJS.formatDate(e.value, "DD MMM");
                },
            },
            dataPointWidth: 3,
            data: [
                {
                    type: "spline",
                    name: "DailyTotal",
                    showInLegend: true,
                    color:"#54a8c6",
                    xValueType: "dateTime",
                    dataPoints: dataPoints_recovered
                } ,{
                    type: "spline",
                    name: "Cumulative",
                    showInLegend: true,
                    color:"rgb(39,47,148)",
                    xValueType: "dateTime",
                    dataPoints: dataPoints_recovered_Cumulative
                }
            ]
        };
        var option_deceased = {
            zoomEnabled: true,
            title: {
                text: "Deceased"
            },
            animationEnabled: true,
            axisY: {
                gridThickness: 0,
            },
            axisY2: {
                gridThickness: 0,
            },
            axisX: {
                labelFormatter: function (e) {
                    return CanvasJS.formatDate(e.value, "DD MMM");
                },
            },
            dataPointWidth: 3,
            data: [
                {
                    type: "spline",
                    name: "DailyTotal",
                    showInLegend: true,
                    color:"rgba(121,33,16,0.48)",
                    xValueType: "dateTime",
                    dataPoints: dataPoints_deceased
                },{
                    type: "spline",
                    name: "Cumulative",
                    showInLegend: true,
                    color:"rgb(0,0,0)",
                    xValueType: "dateTime",
                    dataPoints: dataPoints_deceased_Cumulative
                }
            ]
        };
        var options = {
            animationEnabled: true,
            theme: "light2",
            title: {
                text: "Simple Line Chart"
            },

            data: [{
                type: "column",
                indexLabelFontSize: 16,
                dataPoints: dataPoints
            }]
        };

        function totalCases(data) {
            dataPoints_totals.length = 0;
            dataPoints_active.length = 0;
            dataPoints_recovered.length = 0;
            dataPoints_deceased.length = 0;
            dataPoints_totals_Cumulative.length = 0;
            dataPoints_active_Cumulative.length = 0;
            dataPoints_recovered_Cumulative.length = 0;
            dataPoints_deceased_Cumulative.length = 0;

            for (var i = 0; i < data.length; i++) {
                dataPoints_totals.push({
                    x: new Date(data[i].recordedOn),
                    y: data[i].totalCases
                });
                dataPoints_active.push({
                    x: new Date(data[i].recordedOn),
                    y: data[i].activeCases
                });
                dataPoints_recovered.push({
                    x: new Date(data[i].recordedOn),
                    y: data[i].recoveredCases
                });
                dataPoints_deceased.push({
                    x: new Date(data[i].recordedOn),
                    y: data[i].deceasedCases
                });


                dataPoints_totals_Cumulative.push({
                    x: new Date(data[i].recordedOn),
                    y: data[i].totalCasesCumulative
                });
                dataPoints_active_Cumulative.push({
                    x: new Date(data[i].recordedOn),
                    y: data[i].activeCasesCumulative
                });
                dataPoints_recovered_Cumulative.push({
                    x: new Date(data[i].recordedOn),
                    y: data[i].recoveredCasesCumulative
                });
                dataPoints_deceased_Cumulative.push({
                    x: new Date(data[i].recordedOn),
                    y: data[i].deceasedCasesCumulative
                });
            }
            chart = new CanvasJS.Chart("chartContainerTotals", option_totals);
            chart.render();
            chart = new CanvasJS.Chart("chartContainerActives", option_active);
            chart.render();
            chart = new CanvasJS.Chart("chartContainerDeceased", option_deceased);
            chart.render();
            chart = new CanvasJS.Chart("chartContainerRecovered", option_recovered);
            chart.render();
        }

        window.onload = function () {
            loadTotals();

        }
        function toggleDataSeries(e) {
            if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible ){
                e.dataSeries.visible = false;
            } else {
                e.dataSeries.visible = true;
            }
            chart.render();
        }

        function loadTotals() {
            $.ajax({
                method: "GET",
                url: "/getTotalCasesByDay",
                dataType: 'json',
                headers: {"X-CSRF-Token": token},
                success: totalCases
            });
        }

        function loadStateTotals(state) {
            $.ajax({
                method: "GET",
                url: "/getTotalStateCasesByDay/" + state,
                dataType: 'json',

                headers: {"X-CSRF-Token": token},
                success: totalCases
            });

        }


    </script>


</head>
<body>
<input type="hidden" id="contextPath" value="${appUrl}">
<tiles:insertAttribute name="header"></tiles:insertAttribute>
<div class="body_main container-fluid">
    <tiles:insertAttribute name="body"></tiles:insertAttribute>
</div>


<tiles:insertAttribute name="footer"></tiles:insertAttribute>


</body>

</html>

