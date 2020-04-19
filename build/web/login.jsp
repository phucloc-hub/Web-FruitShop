<%-- 
    Document   : login
    Created on : Feb 20, 2020, 9:52:17 AM
    Author     : Loc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Login Page</title>
    </head>

    <body>

        <div style="margin: 10%;align-content: center">

            <form action="login">
                Username <input style="border-radius: 10px" class="form-control" placeholder="Your email" type="text" name="txtEmail" value="${param.txtEmail}" />
                Password <input style="border-radius: 10px" class="form-control" placeholder="Your password" type="text" name="txtPassword" value="${param.txtPassword}" />

                <input type="submit" value="Login" name="btAction" />
                <input type="reset" value="Reset" />
                <div class="btn btn-dark" > <a href="home.jsp" style="text-decoration: none"> Home Page </a> </div>

                <c:if test="${empty sessionScope.FULLNAME}">
                    <font color="red"> Cannot found this account!! </font>

                </c:if>
            </form>


        </div>
        <!--<h1>Hello World!</h1>-->


    </body>
</html>
