<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

        .btn-primary {
            width: 150px;
        }
    </style>
 <script>
                // Function to set the selected value in the dropdown
                function setSelectedValue() {
                    // Get the dropdown element
                    var dropdown = document.getElementById("categoryId");

                    // Get the selected index
                    var selectedIndex = dropdown.selectedIndex;

                    // Set the selected value as a local storage variable
                    localStorage.setItem("selectedValue", selectedIndex);
                }

                // Function to load the selected value from local storage

            </script>
<section>
    <div class="container pt-5">

        <h1 class="pb-3">Book Search</h1>
        <form action="/book/search" method="GET">
            <label for="search"><b>Book Name:</b></label>
            <input type="text" id="search" name="search" placeholder="Search by book name" value="${search}" />
            <input type="text" id="author" name="author" placeholder="Search by author" value="${author}" />
            <label for="category"><b>Category:</b></label>
                <select id="categoryId" name="categoryId">
                                       <option value="" selected>Select a category</option>
                                       <option value="1">Children Book</option>
                                       <option value="2">Education Book</option>
                                       <option value="3">Fantasy Book</option>
                                       <option value="4">Science Fiction Book</option>
                                       <option value="5">CookBook</option>
                                       <option value="6">Technology/Computers Book</option>
                                       <option value="7">Non-Fiction Book</option>
                </select>
            <button type="submit" class="btn btn-primary" onclick="setSelectedValue()">Search</button>
        </form>

        <c:if test="${not empty bookVar}">
            <section class="bg-light1 pb-5">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-12">
                            <h3 class="text-center pt-5">Books Found ${bookVar.size()}</h3>
                            <table id="tablestyle">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Author</th>
                                        <th>Image</th>
                                        <th>Category</th>
                                        <th>Reservation Start Date</th>
                                        <th>Reservation End Date</th>
                                        <th>Reserve</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${bookVar}" var="book">
                                        <tr>
                                            <td>${book.name}</td>
                                            <td>${book.author}</td>
                                            <td><img src="${book.imageUrl}" style="max-width:100px"></td>
                                            <td>${book.category.description}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty book.reservations}">
                                                        <c:forEach items="${book.reservations}" var="reservation">
                                                            ${reservation.checkoutDate}
                                                        </c:forEach>
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty book.reservations}">
                                                        <c:forEach items="${book.reservations}" var="reservation">
                                                            ${reservation.dueDate}
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>No reservation</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><c:choose>
                                                        <c:when test="${not empty book.reservations and book.reservations[0].status eq 'Reserved' or book.reservations[0].status eq 'Reserved-in progress' }">
                                                            <button type="submit" class="btn btn-primary" disabled>
                                                                Reserve
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form action="/book/reserve/${book.id}" method="post">
                                                                <input type="hidden" name="bookId" value="${book.id}" />
                                                                <button type="submit" class="btn btn-primary">
                                                                    Reserve
                                                                </button>
                                                            </form>
                                                        </c:otherwise>
                                                    </c:choose>
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
</section>

<jsp:include page="../include/footer.jsp"/>
