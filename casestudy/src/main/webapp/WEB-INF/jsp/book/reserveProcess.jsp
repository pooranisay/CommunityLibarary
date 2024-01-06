<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<jsp:include page="../include/header.jsp" />
<!DOC TYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Reservation Process:</title>
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

        .btn-primary {
            width: 150px;
        }
    </style>
<script>

        function handleRemoveButtonClick(status, reservationId) {
            if (status === 'Reserved' || status === 'Reserved-in progress') {
                // Perform the removal action or call the existing function
                var confirmDelete = confirm("Are you sure you want to remove this reservation?");
                                           if (confirmDelete) {
                           // Assuming you want to submit a form with the reservationId
                                   var form = document.forms["unReserveForm"];
                                   form.action = "/book/adminUnReserve";  // Set the form action
                                   form.method = "get";  // Set the form method
                                   // Set the reservationId
                                   var reservationIdInput = form.elements["reservationId"];
                                   if (reservationIdInput) {
                                       reservationIdInput.value = reservationId;
                                   } else {
                                       // If the input field is not found, create and append it to the form
                                       reservationIdInput = document.createElement("input");
                                       reservationIdInput.type = "hidden";
                                       reservationIdInput.name = "reservationId";
                                       reservationIdInput.value = reservationId;
                                       form.appendChild(reservationIdInput);
                                   }
                                   form.submit();

                                return true;
                            } else {
                                // If the user clicks "Cancel", do nothing
                                console.log("Form not submitted");
                                return false; // Prevent form submission
                            }
            }
            else {
            // If the user clicks "Cancel", do nothing
            console.log("Form not submitted");
            return false; // Prevent form submission
        }
    }


</script>
</head>
<body>
    <div class="container">
    <c:if test="${empty bookVar}">
    No Reservation Request
    </c:if>
<c:if test="${not empty bookVar}">
    <section class="bg-light1 pb-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-12">
                    <h3 class="text-center pt-3 pb-3">You Have  ${fn:length(bookVar)} Reservation Request :</h3>
                    <table id="tablestyle">
                        <tr>
                            <th>Name</th>
                            <th>Author</th>
                            <th>Image</th>
                            <th>Status</th>
                            <th>Ready for pickup</th>
                            <th>Requested By</th>
                            <th>Remove Request/Unreserve</th>
                        </tr>

                        <c:forEach var="book" items="${bookVar}">
                            <tr>
                                <td>${book.name}</td>
                                <td>${book.author}</td>
                                <td><img src="${book.imageUrl}" style="max-width:100px;"></td>
                                <td>
                                    <c:choose>
                                      <c:when test="${empty book.reservations}">
                                           Available
                                      </c:when>
                                        <c:otherwise>
                                              <c:forEach var="reservation" items="${book.reservations}">
                                                   ${reservation.status}
                                              </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                  <c:choose>
                                  <c:when test="${empty book.reservations}">
                                      <!-- <c:forEach var="reservation" items="${book.reservations}">
                                       <c:if test="${reservation.status eq 'Reserved'}">
                                        <button type="button" class="btn btn-primary"> Approve </button>
                                        </c:if>
                                      </c:forEach> --!>
                                    </c:when>
                                      <c:otherwise>
                                          <form action="/book/confirmReserve" method="get">
                                              <input type="hidden" name="bookId" value="${book.id}" />
                                              <c:forEach var="reservation" items="${book.reservations}">
                                                  <input type="hidden" name="reservationId" value="${reservation.id}" />
                                              </c:forEach>
                                              <button type="submit" class="btn btn-primary">
                                                  Approve
                                              </button>
                                          </form>
                                      </c:otherwise>
                                  </c:choose>

                                </td>

                                <td>
                                <c:forEach var="reservation" items="${book.reservations}">${reservation.user.email}
                                </c:forEach>
                                </td>
                                <td>
                                    <form action="/book/adminUnReserve" name="unReserveForm" method="get">
                                        <input type="hidden" name="bookId" value="${book.id}">
                                            <c:choose>
                                             <c:when test="${not empty book.reservations}">
                                                <c:forEach var="reservation" items="${book.reservations}">
                                                    <input type="hidden" name="reservationId" value="${reservation.id}">
                                                    <button type="button" class="btn btn-primary btn-extend-due-date" onclick="handleRemoveButtonClick('${reservation.status}', ${reservation.id})">
                                                        Reject
                                                    </button>
                                                </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <button type="button" disabled=true class="btn btn-primary btn-extend-due-date">
                                                         Reject
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>

                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                    <!-- Pagination links
                    <c:if test="${totalPages > 0}">
                        <ul class="pagination justify-content-center">
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" href="?page=1">First</a>
                            </li>
                            <c:forEach begin="1" end="${totalPages}" var="page">
                                <li class="page-item ${currentPage == page ? 'active' : ''}">
                                    <a class="page-link" href="?page=${page}">${page}</a>
                                </li>
                            </c:forEach>
                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="?page=${totalPages}">Last</a>
                            </li>
                        </ul>
                    </c:if>-->
                </div>
            </div>
        </div>
    </section>
</c:if>
</div>
<jsp:include page="../include/footer.jsp" />
