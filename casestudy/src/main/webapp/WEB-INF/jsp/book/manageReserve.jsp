<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.js"></script>
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

</head>
<body>
    <div class="container">
    <c:if test="${empty bookVar}">
    No Reservation
    </c:if>
<c:if test="${not empty bookVar}">
    <section class="bg-light1 pb-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-12">
                    <h3 class="text-center pt-3 pb-3">Reserved Details</h3>
                    <table id="tablestyle" class="dataTable">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Author</th>
                            <th>Image</th>
                            <th>Status</th>
                            <th> reserved by</th>
                            <th>Reservation End Date</th>

                        </tr>
                    </thead>
                    <tbody>
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
                                <c:forEach var="reservation" items="${book.reservations}">${reservation.user.email}
                                </c:forEach>
                                </td>
                                <td>
                                    <c:forEach var="reservation" items="${book.reservations}">${reservation.dueDate}
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>


                </div>
            </div>
        </div>
    </section>
</c:if>
</div>
<script>
    $(document).ready(function() {
        $('.dataTable').DataTable({
            "order": [[0, "asc"]],
            "columnDefs": [
             { "orderable": false, "targets": [2, 3] } // Disable sorting for the 3rd (Image) and 4th (Status) columns
                    ],
        });
        checkReservationDueDate();
    });
    <!-- ... (previous code) ... -->




        function checkReservationDueDate() {
            // Get the current date
            var currentDate = new Date();

                // Iterate through all reservation end dates
                $('.dataTable tbody tr').each(function() {
                    var dueDateText = $(this).find('td:eq(5)').text();
                    var dueDate = new Date(dueDateText);

                    if (dueDate < currentDate) {
                        // Due date is greater than the current date, change the text color to red
                        $(this).find('td:eq(5)').css('color', 'red');
                    }
                });
        }
    </script>

    <!-- ... (remaining code) ... -->

</script>
<jsp:include page="../include/footer.jsp" />
