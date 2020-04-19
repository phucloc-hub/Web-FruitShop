<%-- 
    Document   : update
    Created on : Feb 25, 2020, 9:28:14 AM
    Author     : Loc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Home</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

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
        <div class="container">

            <div class="row" style="background-color: #33ff33">
                <div class="col-md-8" style="background-color: #6666ff">

                    <form action="updateAdmin">

                        Name : ${param.name}<br>
                        <input type="hidden" name="txtName" value="${param.name}" />
                        image <input type="file" name="txtImage" value="${param.image}" /><br>
                        <input type="hidden" name="txtOldImage" value="${param.image}" />
                        Description <textarea  name="txtDescription">${param.description}</textarea><br>
                        Price <input required pattern="[0-9]{1,3}[.]{0,1}[0-9]{0,1}" title="Price should only contain positive numbers. e.g. from 1 to 999" 
                                     type="text" name="txtPrice" value="${param.price}" /><br>
                        CreateDate : ${param.createDate}<br>
                        
                        Quantity <input required pattern="[0-9]{1,3}" title="Quantity should only contain positive numbers. e.g. from 1 to 999"
                                        type="text" name="txtQuantity" value="${param.quantity}" /><br>

                        CategoryID  <select name="cbCategory">

                            <option value="F"  ${ param.categoryID eq 'F' ? 'selected' : ''}>Food</option>
                            <option value="D" ${ param.categoryID eq 'D' ? 'selected' : ''} >Drink</option>

                        </select> <br>

                        Status <select name="cbStatus">

                            <option value="Active"  ${ param.status eq 'Active' ? 'selected' : ''}>Active</option>
                            <option value="Inactive" ${ param.status eq 'Inactive' ? 'selected' : ''} >Inactive</option>

                        </select> <br>

                        <input class="btn-secondary" type="submit" value="updateAdmin" name="btAction" />

                    </form>



                </div>
            </div>
        </div>



    </body>
</html>
