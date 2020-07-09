# covid
Notes :  Developed with Trail version of CanvasJS Chart License , for more information https://canvasjs.com/license/

Overview:
----------

Application tracks the covid-19 cases of a country.


Application Technologies :
---------------------------
          Java 1.8
          Spring boot 2.3.1
          Redis for Caching
          JSP with JSTL
          Bootstrap 4 with Jquery
          CanvasJs(java script library for Graph, Trail Version)
          
          
How To Run:
------------

           Start Redis Server
           install maven and build
             mvn clean install
           Run generated war from commandline
             java -jar xxxx.war
             
Test Data:
-----------
           Login with admin (adminuser/adminuser)
           localhost:8033/addtestdata/100
                if need to add 1 record localhost:8033/addtestdata/1
                if need to add 1000 record localhost:8033/addtestdata/1000
                

           