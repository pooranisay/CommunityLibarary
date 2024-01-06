<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<body style="background-image: url('../pub/image/library.jpg'); background-size: cover; background-position: center; background-repeat: no-repeat; margin: 0; padding: 0; color: #ffffff; min-height: 100vh; display: flex; flex-direction: column;">

    <div class="container flex-grow-1 d-flex justify-content-center align-items-center" style="background-color: rgba(0, 0, 0, 0.7); padding: 30px; border-radius: 15px;">

        <div class="text-center">
            <h1 class="display-4" style="font-family: 'Roboto', sans-serif; font-weight: 700; color: #3498db;">Welcome to Community Library</h1>
            <p class="lead" style="font-family: 'Roboto', sans-serif; font-weight: 400;">
                Explore the pleasure of reading and sharing with our community library. Dive into our diverse book collection and join us in fostering a culture of knowledge sharing.
            </p>
            <p>Whether you aim to contribute books, borrow from our library, or partake in community events, we're committed to cultivating a positive and enriching reading experience for everyone.</p>

            <a href="/home/aboutus" class="btn btn-light btn-lg" style="color: #3498db; background-color: #ffffff;">Learn More</a>
            <a class="btn btn-primary btn-lg" href="/home/homesearch" role="button">Get Started</a>
        </div>
    </div>

    <!-- Bootstrap JS and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <!-- Footer -->
    <jsp:include page="../include/footer.jsp"/>
</body>
