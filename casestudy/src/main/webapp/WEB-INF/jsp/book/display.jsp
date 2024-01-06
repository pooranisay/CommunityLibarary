<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.js"></script>
<jsp:include page="../include/header.jsp"/>

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
</style>



<c:if test="${not empty bookVar}">
    <section class="bg-light1 pb-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-12">
                    <h3 class="text-center pt-5 pb-2">Book Details:</h3>
                    <table id="tablestyle" class="dataTable">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Author</th>
                                <th>Image</th>
                                <th>Status</th>
                                <th>Edit</th>


                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="book" items="${bookVar}">
                                <tr>
                                    <td>${book.id}</td>
                                    <td><a href="/book/detail?id=${book.id}">${book.name}</td>
                                    <td>${book.author}</td>
                                    <td><img src="${book.imageUrl}" style="max-width:100px;"></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty book.reservations}">
                                                Available
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="reservation" items="${book.reservations}">
                                                    ${empty reservation.status ? 'Available' : reservation.status}
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty book.reservations}">
                                                <c:forEach var="reservation" items="${book.reservations}">
                                                    <c:choose>
                                                        <c:when test="${reservation.status eq 'Reserved' or reservation.status eq 'Reserved-in progress'}">
                                                            <a href="/book/edit/${book.id}" style="pointer-events: none; color: grey;">Edit</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="/book/edit/${book.id}">Edit</a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="/book/edit/${book.id}">Edit</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                </tr>
                            </c:forEach>
                        </tbody>
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
                    </c:if> -->
                </div>
            </div>
        </div>
    </section>
</c:if>

<script>
    $(document).ready(function() {
       $('.dataTable').DataTable({
                   "order": [[0, "asc"]],
                   "columnDefs": [
                    { "orderable": false, "targets": [3,4, 5] } // Disable sorting for the 3rd (Image) and 4th (Status) columns
                           ],
               });
               checkReservationDueDate();
           });
</script>

<jsp:include page="../include/footer.jsp"/>
