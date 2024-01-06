<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<style>
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
        justify-content: space-evenly;
        align-items: center;
        height: 100vh;
    }

    .card {
        z-index: 1;
        background-color: rgba(255, 255, 255, 0.9); /* Slightly less transparent white background */
        padding: 30px; /* Increased padding for better spacing */
        border-radius: 15px; /* Increased border radius for a smoother look */
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2); /* Adjusted shadow for a subtle lift */
        max-width: 400px;
    }

    .reservation-details {
        background-color: rgba(255, 255, 255, 0.9);
        padding: 30px;
        border-radius: 15px;
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
        max-width: 400px;
    }
</style>

<div class="blurry-background"></div>

<div class="card-container">
    <!-- Card Display -->
    <div class="card text-dark" style="width: 18rem;">
        <img class="card-img-top" src="${book.imageUrl}" alt="Card image cap">
        <div class="card-body">
            <h5 class="card-title"><a href="/book/detail?id=${book.id}" class="text-decoration-none text-dark">${book.name}</a></h5>
            <h2>Book Details</h2>
            <p class="card-text"><b>Author:</b> ${book.author}</p>
            <p class="card-text"><b>Category:</b> ${book.category.description}</p>
        </div>
    </div>

    <!-- Reservation Details Card -->
    <div class="card reservation-details">
        <div class="card-body">
            <h3 class="card-title">Reservation Details:</h3>
            <c:choose>
                <c:when test="${not empty book.reservations}">
                    <c:forEach var="reservation" items="${book.reservations}">
                        <p class="card-text"><b>Book Name:</b> ${book.name}</p>
                        <p class="card-text"><b>Author:</b> ${book.author}</p>
                        <p class="card-text"><b>Reserved By:</b> ${reservation.user.email}</p>
                        <p class="card-text"><b>Reserved Until:</b> ${reservation.dueDate}</p>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <!-- No Reservations -->
                    <p class="card-text"><b>Book Name:</b> ${book.name}</p>
                    <p class="card-text"><b>Author:</b> ${book.author}</p>
                    <p class="card-text"><b>Reservation Status:</b> No Reservation</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="../include/footer.jsp"/>
