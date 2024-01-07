<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<style>
    body {
        margin: 0;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        color: #333; /* Set a default text color */
    }

    .blurry-background {
        background: url('../pub/image/library.jpg') no-repeat center center fixed;
        background-size: cover;
        filter: blur(8px);
        height: 100vh;
        position: fixed;
        width: 100%;
        margin: 0;
        overflow: hidden;
    }

    .card-container {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .card {
        background-color: rgba(255, 255, 255, 0.95);
        padding: 20px;
        border-radius: 15px;
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
        max-width: 500px; /* Increased maximum width */
        width: 100%;
        text-align: center;
    }

    .card img {
        max-width: 100%;
        height: auto;
        border-radius: 10px;
        max-height: 300px;
        margin-bottom: 15px;
    }

    .reservation-details {
        margin-top: 20px;
        background-color: rgba(255, 255, 255, 0.95);
        padding: 20px;
        border-radius: 15px;
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
        max-width: 500px; /* Increased maximum width */
        width: 100%;
    }

    h3 {
        color: #007bff;
    }

    .card-text {
        margin-bottom: 10px;
    }
</style>

<div class="blurry-background"></div>

<div class="card-container">
    <!-- Card Display -->
    <div class="card text-dark">
        <img class="card-img-top" src="${book.imageUrl}" alt="Book Cover">
        <div class="card-body">
            <h5 class="card-title"><a href="/book/detail?id=${book.id}" class="text-decoration-none text-dark">${book.name}</a></h5>
            <p class="card-text"><b>Author:</b> ${book.author}</p>
            <p class="card-text"><b>Category:</b> ${book.category.description}</p>
            <!-- Reservation Details-->
            <div class="reservation-details">
                <h3>Reservation Details:</h3>
                <c:choose>
                    <c:when test="${not empty book.reservations}">
                        <c:forEach var="reservation" items="${book.reservations}">
                            <p class="card-text"><b>Reserved By:</b> ${reservation.user.email}</p>
                            <p class="card-text"><b>Reserved Until:</b> ${reservation.dueDate}</p>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <!-- No Reservations -->
                        <p><b>Reservation Status:</b> No Reservation</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../include/footer.jsp"/>
