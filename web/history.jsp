<%-- 
    Document   : history
    Created on : Feb 27, 2020, 10:34:37 AM
    Author     : Loc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    </head>
    <body>
        <c:set var="fullname" value="${sessionScope.FULLNAME}"/>
        <c:if test="${not empty fullname}">
            <div style="background-color: aliceblue;color: red;width: 100%;float: left;height: 43px">
                Hello ${fullname} |  <form action="logout"> <input type="submit" value="Logout" name="btAction" style="border: 5px;border-bottom-color: black;border-radius: 5px" />
                </form>


            </div>

        </c:if>
        <c:if test="${empty fullname}">
            <c:redirect url="home.jsp"/>
        </c:if>

        <a class="btn btn-dark" href="home.jsp">HOME</a>
        <div class="container" >

            <div class="row" >
                <div class="col-md-8">
                    <c:set var="HISTORY" value="${sessionScope.HISTORY}"/>
                    <c:if test="${not empty HISTORY}">

                        <table border="1">
                            <thead>
                                <tr>
                                    <th>ProductName</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>Total</th>
                                    <th>BuyDate</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${HISTORY}">
                                    <tr>
                                        <td>${item.productName}</td>
                                        <td>${item.quantity}</td>
                                        <td>${item.price}</td>
                                        <td>${item.total}</td>
                                        <td>${item.buyDate}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${empty HISTORY}">

                        No Record

                    </c:if>




                </div>

                <div class="card my-4">
                    <h5 class="card-header">Search</h5>
                    <div class="card-body">
                        <div class="input-group">

                            <form action="searchUserHistory" method="POST">

                                <select name="cbUserchoice">

                                    <option selected value="1">Name</option>
                                    <option value="2">Date</option>   

                                </select><br>
                                Date: <input type="date" id="birthday" name="txtDate">
                                <br>
                                Name: <input type="text" name="txtProductName" value="${param.txtProductName}" />
                                <span class="input-group-btn">
                                    <button class="btn btn-secondary" type="submit" name="btAction" value="searchUserHistory">Go!</button>   
                                </span>


                            </form>
                             

                        </div>
                    </div>
                </div>    

            </div>
        </div>

    </body>
</html>
