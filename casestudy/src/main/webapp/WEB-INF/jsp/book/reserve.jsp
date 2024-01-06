<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Reservations</title>
    <style>
        #tablestyle {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        #tablestyle th, #tablestyle td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        #tablestyle th {
            background-color: #f2f2f2;
        }

        .btn-ready-for-pickup {
            width: 250px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h3>Reservations Added Successfully</h3>
        <h6>Here is the Reservation Details:</h6>

        <c:if test="${empty reservation.user.email}">
            <p>No reservations.</p>
        </c:if>

        <c:if test="${not empty reservation.user.email}">
            <table id="tablestyle">
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Reserved by</th>
                    <th>Reserved Until</th>
                    <th>Status</th>
                    <th>Ready For Pickup</th>
                </tr>
                <tr>
                                        <td>${reservation.book.name}</td>
                                        <td>${reservation.book.author}</td>
                                        <td>${reservation.user.email}</td>
                                        <td>${reservation.dueDate}</td>
                                        <td>${reservation.status}</td>
                                        <td>
                                           <c:choose>
                                               <c:when test="${reservation.status eq 'Reserved-in progress'}">
                                                   <button type="submit" class="btn btn-primary btn-ready-for-pickup" disabled>
                                                       book status not ready for pick-up
                                                   </button>
                                               </c:when>
                                               <c:otherwise>
                                                   <button type="submit" class="btn btn-primary btn-ready-for-pickup">
                                                       book status ready for  pick-up
                                                   </button>
                                               </c:otherwise>
                                           </c:choose>
                                                    <input type="hidden" name="_method" value="PUT">
                                                    <input type="hidden" name="bookId" value="${reservation.book.id}">
                                                    <input type="hidden" name="userId" value="${reservation.user.id}">

                                        </td>
                                    </tr>
            </table>
        </c:if>
    </div>
    <div>
    <a href=
    </div>
<jsp:include page="../include/footer.jsp"/>
