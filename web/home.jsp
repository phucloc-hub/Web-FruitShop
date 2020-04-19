<%-- 
    Document   : home
    Created on : Feb 20, 2020, 9:13:42 AM
    Author     : Loc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <!--<head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>JSP Page</title>
        </head>-->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    

    <title>Home</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        // Style for notification box
        .notification {
            background-color: #555;
            color: white;
            text-decoration: none;
            padding: 15px 26px;
            position: relative;
            display: inline-block;
            border-radius: 2px;

        }

        .notification:hover {
            background: red;
        }

        .notification .badge {
            position: absolute;
            top: -10px;
            right: -10px;
            padding: 5px 10px;
            border-radius: 50%;
            background-color: red;
            color: white;
        }

    </style>

    <body>
        <!--<h1>Hello World!</h1>-->

        <c:set var="fullname" value="${sessionScope.FULLNAME}"/>
        <c:if test="${empty fullname}">
            <div class="loginform" style="background-color: aliceblue;align-content: flex-end; float: left; border: 2px;width: 100%">
                <a href="login.html">Login?</a><br>

            </div>     
        </c:if>    


        <c:if test="${not empty fullname}">
            <div style="background-color: aliceblue;color: red;width: 100%;float: left;height: 43px">
                Hello ${fullname} |  <form action="logout"> <input type="submit" value="Logout" name="btAction" style="border: 5px;border-bottom-color: black;border-radius: 5px" />
                </form>


            </div>


        </c:if>



        <c:set var="LISTPRODUCT" value="${sessionScope.EACHPAGEPRODUCT}"/>
        <c:set var="ROLE" value="${sessionScope.USERKIND}"/>


        <c:if test="${ROLE eq 'Member'}">

            <a href="viewCart.jsp" class="notification">
                <span>Your Cart</span>
                <span class="badge">3</span>
            </a>
            <header>
                <a href="history.jsp"><i class="fa fa-history" style="font-size:36px"></i></a>
            </header>
        </c:if>



        <div class="container">

            <div class="row">
                <div class="col-md-8">
                    <c:if test="${ROLE eq 'Admin'}">
                        <a class="btn btn-info" href="addProduct.jsp"> Create Product </a>
                    </c:if>
                    <c:set var="NORECORD" value="${requestScope.NORECORD}"/>
                    <c:if test="${NORECORD ne 'NORECORD'}">
                        <c:if test="${not empty LISTPRODUCT}">  

                            <form action="deleteAdmin">
                                <!-- FORM DELETE -->

                                <c:if test="${ROLE eq 'Admin'}">
                                    <input class="btn-dark" onclick="if (!confirm('Are you sure?')) {
                                                return false
                                            }" type="submit" value="DeleteAdmin" name="btAction" />
                                </c:if>
                                <c:forEach var="PRODUCT" items="${LISTPRODUCT}">


                                    <div class="col-md-4">
                                        <img src="${PRODUCT.image}" alt="${PRODUCT.name}" height="70%" width="70%">



                                        <c:if test="${ROLE eq 'Member'}">

                                            <c:url var="urlBuy" value="addtoCart">
                                                <c:param name="foodName" value="${PRODUCT.name}"/>                      
                                                <c:param name="price" value="${PRODUCT.price}"/>
                                            </c:url>

                                            <c:if test="${PRODUCT.quantity gt 0}">
                                                <a class="btn btn-dark" href="${urlBuy}"> Buy </a>
                                            </c:if>

                                        </c:if>

                                        <font style="color:red"> ${PRODUCT.name} </font>

                                        <c:if test="${ROLE eq 'Admin'}">
                                            <c:if test="${PRODUCT.status eq 'Active'}">
                                                <input type="checkbox" name="chkdeleted" value="${PRODUCT.name}" />
                                            </c:if>
                                        </c:if>

                                    </div>
                                    <div class="col-md-8">
                                        ${PRODUCT.description}</br>
                                        Price: ${PRODUCT.price}</br>
                                        CreateDate: ${PRODUCT.createDate}</br>
                                        Category:
                                        <c:if test="${PRODUCT.categoryID eq 'F'}" >

                                            <font style="color: blue"> Food </font>

                                        </c:if>
                                        <c:if test="${PRODUCT.categoryID eq 'D'}" >

                                            <font style="color: green"> Drink </font>

                                        </c:if>
                                        <c:if test="${PRODUCT.categoryID ne 'D'}" >
                                            <c:if test="${PRODUCT.categoryID ne 'F'}" >
                                                <font style="color: red"> ${PRODUCT.categoryID} </font>
                                            </c:if>
                                        </c:if>
                                        <font style="color: #cc0000"> ${PRODUCT.quantity} </font> left!        

                                        <c:if test="${ROLE eq 'Admin'}"> 

                                            <!-- UPDATE LINK -->
                                            <c:url var="urlRewritting" value="update.jsp">
                                                <c:param name="name" value="${PRODUCT.name}"/>
                                                <c:param name="image" value="${PRODUCT.image}"/>
                                                <c:param name="description" value="${PRODUCT.description}"/>
                                                <c:param name="price" value="${PRODUCT.price}"/>
                                                <c:param name="createDate" value="${PRODUCT.createDate}"/>
                                                <c:param name="categoryID" value="${PRODUCT.categoryID}"/>
                                                <c:param name="status" value="${PRODUCT.status}" />
                                                <c:param name="quantity" value="${PRODUCT.quantity}"/>
                                            </c:url>


                                            <a class="btn btn-dark" style="text-decoration: none" href="${urlRewritting}">-update-</a>

                                        </c:if>



                                    </div>
                                    --------------------------*****--------------------------
                                </c:forEach>

                                <!-- END FORM DELETE -->
                            </form>

                            <div class="col-md-4">
                                <!-- END OF SHOW DATA EACH PAGE   -->
                                <c:set var="MaxP" value="${sessionScope.MAXPAGE}"/>

                                <c:forEach var="i" begin="1" end="${MaxP}">
                                    <div class="col-mb-4">
                                        <form action="paging">
                                            <input class="btn-info" type="submit" value="${i}" name="btAction" />
                                            <input type="hidden" name="PageIndex" value="${i}" />
                                        </form> 
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>  
                    </c:if>
                    <c:if test="${NORECORD eq 'NORECORD'}">
                        <font style="color: red"> NO RECORD </font>
                    </c:if>
                </div>




                <c:if test="${ROLE ne 'Admin'}">
                    <div class="col-md-4">
                        <div class="card my-4">
                            <h5 class="card-header">Search By Name</h5>
                            <div class="card-body">
                                <div class="input-group">

                                    <form action="search" method="POST">


                                        <input type="text" name="txtName" pattern="[a-zA-Z]{1,30}" title="Name should contain at least 1 character!" value="${param.txtName}" class="form-control" placeholder="Search for name...">
                                        <span class="input-group-btn">
                                            <button class="btn btn-secondary" type="submit" name="btAction" value="Search">Go!</button>   
                                            <input type="hidden" name="choice" value="1" />
                                        </span>
                                    </form>


                                </div>
                            </div>
                        </div>
                        <div class="card my-4">
                            <h5 class="card-header">Search By Range</h5>
                            <div class="card-body">
                                <div class="input-group">

                                    <form action="search" method="POST">

                                        <input type="text" required="required" name="txtFrom" value="${param.txtFrom}" pattern="[0-9]{1,3}[.]{0,1}[0-9]{0,1}"
                                               title="Price should only contain positive numbers. e.g. from 1 to 999" class="form-control" placeholder="From...">

                                        <input type="text" required="required" name="txtTo" value="${param.txtTo}" pattern="[0-9]{1,3}[.]{0,1}[0-9]{0,1}"
                                               title="Price should only contain positive numbers. e.g. from 1 to 999" class="form-control" placeholder="To...">
                                        <span class="input-group-btn">
                                            <button class="btn btn-secondary" type="submit" name="btAction" value="Search">Go!</button>   
                                            <input type="hidden" name="choice" value="2" />
                                        </span>
                                    </form>


                                </div>
                            </div>
                        </div>
                        <div class="card my-4">
                            <h5 class="card-header">Search By Category</h5>
                            <div class="card-body">
                                <div class="input-group">

                                    <form action="search" method="POST">


                                        <input type="text" name="txtCategory" pattern="[a-zA-Z]{1,30}" title="Category should contain at least 1 character!" value="${param.txtCategory}" class="form-control" placeholder="Search for name...">
                                        <span class="input-group-btn">
                                            <button class="btn btn-secondary" type="submit" name="btAction" value="Search">Go!</button>   
                                            <input type="hidden" name="choice" value="3" />
                                        </span>
                                    </form>


                                </div>
                            </div>
                        </div>
                    </div>

                </c:if>
                <c:if test="${ROLE eq 'Admin'}">
                    <div class="card my-4">
                        <h5 class="card-header">Search</h5>
                        <div class="card-body">
                            <div class="input-group">

                                <form action="searchAdmin" method="POST">

                                    <select name="cbCategory">

                                        <option selected value="Food">Food</option>
                                        <option value="Drink">Drink</option>   

                                    </select>

                                    <select name="cbStatus">

                                        <option selected value="Active">Active</option>
                                        <option value="Inactive">Inactive</option>   

                                    </select>
                                    <span class="input-group-btn">
                                        <button class="btn btn-secondary" type="submit" name="btAction" value="searchAdmin">Go!</button>   
                                    </span>
                                </form>


                            </div>
                        </div>
                    </div>


                </c:if>

            </div>

        </div>




    </body>

</html>
