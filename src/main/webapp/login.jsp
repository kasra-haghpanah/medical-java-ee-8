<%--
  Created by IntelliJ IDEA.
  User: kasra.haghpanah
  Date: 13/01/2017
  Time: 12:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AngularJs With Java EE</title>
</head>
<body>

<form action="/j_security_check" method="POST" >
    <div>Username:<input name="j_username" value=""/></div>
    <div>Password:<input name="j_password" value=""/></div>
    <input type="submit" value="Login"/>
    <input type="reset" value="Reset">
</form>

</body>
</html>
