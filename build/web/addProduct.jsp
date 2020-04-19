<%-- 
    Document   : addProduct
    Created on : Feb 25, 2020, 4:14:15 PM
    Author     : Loc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Food</title>
    </head>
    <body>
        <c:set var="fullname" value="${sessionScope.FULLNAME}"/>
        <c:if test="${empty fullname}">
            <c:redirect url="home.jsp"/>
        </c:if>
        <c:if test="${not empty fullname}">
            <div style="background-color: aliceblue;color: red;width: 100%;float: left;height: 43px">
                Hello ${fullname} |  <form action="logout"> <input type="submit" value="Logout" name="btAction" style="border: 5px;border-bottom-color: black;border-radius: 5px" />
                </form>


            </div>

        </c:if>

        <a class="btn btn-dark" href="home.jsp">HOME</a>
        <div class="container" style="background-color: #66ffff">

            <div class="row" style="background-color: #33ff33">
                <div class="col-md-8" style="background-color: #6666ff">

                    <form action="addAdmin">

                        Name : 
                        <input required type="text" name="txtName" value="${param.txtName}" />
                        <c:set var="ExistName" value="${requestScope.ExistName}"/>
                            
                       
                        <c:if test="${ExistName eq 'ExistName'}">
                            <font style="color: red"> This name has existed! </font>
                        </c:if><br>
                        image <input required type="file" name="txtImage" value="${param.txtImage}" /><br>
                        Description <textarea required  name="txtDescription">${param.txtDescription}</textarea><br>
                        Price <input required pattern="[0-9]{1,3}[.]{0,1}[0-9]{0,1}" title="Price should only contain positive numbers. e.g. from 1 to 999" 
                                     type="text" name="txtPrice" value="${param.txtPrice}" /><br>


                        Quantity <input required pattern="[0-9]{1,3}" title="Quantity should only contain positive numbers. e.g. from 1 to 999"
                                        type="text" name="txtQuantity" value="${param.txtQuantity}" /><br>

                        CategoryID  <select name="cbCategory">

                            <option value="F" selected>Food</option>
                            <option value="D" >Drink</option>

                        </select> <br>

                        <input class="btn-secondary" type="submit" value="addAdmin" name="btAction" />

                    </form>



                </div>
            </div>
        </div>




    </body>
</html>
