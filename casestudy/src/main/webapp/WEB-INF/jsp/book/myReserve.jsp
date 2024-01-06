<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>
<!DOC TYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Reservation Details:</title>
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
    <script>
        function confirmUnreserve() {
            var confirmDelete = confirm("Are you sure you want to remove this reservation?");
            if (confirmDelete) {
                // If the user clicks "OK", submit the form
                console.log("Form submitted");
                var form = document.forms["unReserveForm"];
                form.submit();
                return true;
            } else {
                // If the user clicks "Cancel", do nothing
                console.log("Form not submitted");
                return false; // Prevent form submission
            }
        }
    </script>

</head>
<body>
    <div class="container">




        <c:if test="${not empty successMessage}">
                   <!-- Content to display when 'success' parameter is empty -->
                              <div class="alert alert-success">${successMessage}</div>


               </c:if>

               <c:if test="${not empty errorMessage}">
                   <!-- Content to display when 'error' parameter is present and not empty -->
                             <div class="alert alert-danger w-100 mb-0">${errorMessage}</div>


               </c:if>

            <c:choose>
                       <c:when test="${not empty bookVar}">
                           <h3>Reservation Details:</h3>
            <table id="tablestyle">
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Reserved by</th>
                    <th>Reserved Until</th>
                    <th>Status</th>
                    <th>Book Status Ready For Pickup?</th>
                    <th>Book Reserve-Extend</th>
                    <th>Remove From List</th>
                </tr>
                              <c:forEach items="${bookVar}" var="book">
                                                    <tr>
                                        <td>${book.name}</td>
                                        <td>${book.author}</td>
                                        <td>
                                     <c:forEach var="reservation" items="${book.reservations}">
                                          ${reservation.user.email}
                                        </c:forEach>

                                         </td>

                                         <td>
                                          <c:forEach var="reservation" items="${book.reservations}">
                                           ${reservation.dueDate}
                                           </c:forEach>

                                           </td>

                                        <td>
                                            <c:forEach var="reservation" items="${book.reservations}">

                                                        ${reservation.status}


                                        </td>
                                        <td>
                                           <c:choose>
                                               <c:when test="${reservation.status eq 'Reserved-in progress'}">
                                                   <button type="submit" class="btn btn-primary btn-ready-for-pickup" disabled>
                                                       No
                                                   </button>
                                               </c:when>
                                               <c:otherwise>
                                                   <button type="submit" class="btn btn-primary btn-ready-for-pickup">
                                                       Yes
                                                   </button>
                                                   </c:otherwise>
                                                    </c:choose></td>
                                                   <td>
                                                   <form action="/book/extend" method="get">
                                                    <input type="hidden" name="bookId" value="${book.id}">
                                                    <input type="hidden" name="reservationId" value="${reservation.id}">
                                                    <c:choose>
                                                    <c:when test="${reservation.status eq 'Reserved-in progress'}">
                                                     <button type="submit" class="btn btn-primary btn-extend-due-date" disabled>
                                                     Extend Not Available</button>
                                                     </c:when>
                                                     <c:otherwise>
                                                     <button type="submit" class="btn btn-primary btn-extend-due-date" >
                                                     Extend For One Week</button>

                                                     </c:otherwise>
                                                   </c:choose>
                                                   </form>
                                                   </td>


                                           <td>
                                           <form action="/book/unReserve" name="unReserveForm" method="get">
                                               <input type="hidden" name="bookId" value="${book.id}">
                                               <input type="hidden" name="reservationId" value="${reservation.id}">
                                               <button type="button" class="btn btn-primary btn-un-reserve" onclick="return confirmUnreserve()">
                                                   Return
                                               </button>
                                           </form>
                                           </c:forEach>
                                                    <input type="hidden" name="_method" value="PUT">
                                                    <input type="hidden" name="bookId" value="${book.id}">
                                                    <input type="hidden" name="userId" value="${book.id}">

                                        </td>
                                    </tr>

                                                                    </c:forEach>
            </table>
     </c:when>
             <c:otherwise>
                 <h3>No Reservations</h3>
             </c:otherwise>
         </c:choose>
     </div>

<jsp:include page="../include/footer.jsp"/>
