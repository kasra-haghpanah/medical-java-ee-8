<%--
  Created by IntelliJ IDEA.
  User: kasra.haghpanah
  Date: 13/01/2017
  Time: 12:55
  To change this tempconfig.propertieslate use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>

    <meta name="author" content="kasra haghpanah"/>
    <title>AngularJs With Java EE</title>

    <script type="text/javascript" src="/view/js/ajax.js"></script>
    <script type="text/javascript" src="/view/lib/angular.js"></script>
    <script type="text/javascript" src="/view/lib/angular-route.js"></script>
    <script type="text/javascript" src="/view/lib/angular-resource.js"></script>
    <script type="text/javascript" src="/view/lib/angular-translate.js"></script>
    <script type="text/javascript" src="/view/js/config.js"></script>
    <script type="text/javascript" src="/view/js/controller.js"></script>

</head>

<body ng-app="demoApp">

<h1>Hi Fiends!</h1>


<div id="kasra">
    <div ng-view=""></div>
</div>


<div style="width: 100%;border: 1px solid black;text-align: center;height: 20px;">
    <div id="exportbar" style="width: 100%;height: 20px;">
        <div style="width: 100%;height: 20px;color: red;">0%</div>
        <div style="background-color: #6690ff;color:white;text-align: center;width: 0%;;margin-top: -20px;height: 20px;">&nbsp;</div>
    </div>
</div>


<input type="button" value="json" id="json"/>
<input type="button" value="xml" id="xml"/>
<input type="button" value="text-html" id="text-html"/>
<script>

    document.getElementById("json").onclick = function () {
        var data = [{id: 12, firstName: "maral", lastName: "hekmatipour", sex: 0}];
        ajax.sendAsJSON("POST", "/resources/MyRestService/json/getSicks", data, function (data) {
            console.log(data);
        });
    }

    document.getElementById("xml").onclick = function () {
        var data = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><sicks><sick id="0"><firstName>kasra1</firstName><lastName>haghpanah1</lastName><sex>1</sex></sick><sick id="0"><firstName>farah1</firstName><lastName>diba1</lastName><sex>0</sex></sick></sicks>';
        ajax.sendAsXML("POST", "/resources/MyRestService/xml/getSicks", data, function (data) {
            console.log(data);
        });
    }


    document.getElementById("text-html").onclick = function () {
        ajax.send("POST", "view/view/uploadAnotherHost.htm", null, function (data) {console.log(data);});
    }


</script>

</body>
</html>
