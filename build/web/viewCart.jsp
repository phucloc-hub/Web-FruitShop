<%-- 
    Document   : viewCart
    Created on : Feb 26, 2020, 10:37:49 AM
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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
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
        <div class="container" style="background-color: #66ffff">

            <div class="row" style="background-color: #33ff33">
                <div class="col-md-8" style="background-color: #6666ff">
                    <c:set var="CART" value="${sessionScope.CART}"/>
                    <c:set var="Thanks" value="${requestScope.Thanks}"/>
                    <c:if test="${not empty CART}">
                        <c:if test="${empty Thanks}">
                            <div class="card-footer text-muted"> Your Cart </div>
                            <table border="1">
                                <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Amount</th>
                                        <th>Price</th>
                                        <th>Total</th>
                                        <th>Delete</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <c:set var="ERROR_QUANTITY" value="${requestScope.ERROR}"/>
                                        <c:if test="${not empty ERROR_QUANTITY}">
                                        <font style="color: red"> ${ERROR_QUANTITY} is not enough  </font>
                                    </c:if>
                                    <c:forEach var="item"  items="${CART.items}">
                                        <c:set var="Total" value="${Total + item.value.total}"/>
                                        <tr>
                                            <td>${item.value.productName}</td>
                                            <td>
                                                ${item.value.quantity} 
                                                <c:url var="urlplus" value="updateQuantity">
                                                    <c:param name="btAction" value="plus"/>
                                                    <c:param value="${item.value.quantity}" name="quantity"/>
                                                    <c:param value="${item.key}" name="productName"/>
                                                </c:url>
                                                <c:url var="urlminus" value="updateQuantity">
                                                    <c:param name="btAction" value="minus"/>
                                                    <c:param value="${item.value.quantity}" name="quantity"/>
                                                    <c:param value="${item.key}" name="productName"/>
                                                </c:url>
                                                <a href="${urlplus}"> <i class="glyphicon glyphicon-plus-sign"></i> </a>
                                                <a href="${urlminus}"> <i class="glyphicon glyphicon-minus-sign"> </i></a>

                                            </td>
                                            <td>${item.value.price}</td>
                                            <td>${item.value.total}</td>
                                            <td>
                                                <!-- URL REWRITING REMOVE FOR EACH ITEM -->

                                                <c:url var="urlRemove" value="removeItem">
                                                    <c:param name="productName" value="${item.value.productName}"/>

                                                </c:url>
                                                <a onclick="return confirm('Are you sure?')" class="btn btn-info" href="${urlRemove}"> <i class="glyphicon glyphicon-remove"></i> </a>

                                            </td>

                                        </tr>
                                        
                                </c:forEach>


                                <tr>
                                    <th>
                                        Total: ${Total} $
                                    </th>
                                </tr>

                                </tbody>

                            </table>
                            <form action="checkOut">
                                <input type="submit" value="checkOut" name="btAction" />
                            </form>           
                        </c:if>
                            <c:if test="${not empty Thanks}">
                                <font style="color: yellow"> Thank you </font>
                            </c:if>
                            
                    </c:if>
                    <c:if test="${empty CART}">
                        No Record
                    </c:if>



                </div>

            </div>

        </div>





    </body>
</html>
