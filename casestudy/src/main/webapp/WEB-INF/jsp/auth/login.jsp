<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<body style="background-image: url('../pub/image/library.jpg'); background-size: cover; background-position: center; background-repeat: no-repeat; margin: 0; padding: 0; color: #3498db;">

    <section class="bg-transparent pt-3 pb-3">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0"><b>Community Library Management</b></h1>
            </div>
        </div>
    </section>

    <c:if test="${param['error'] eq ''}">
        <section class="pt-5">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-6">
                        <div class="alert alert-danger w-100 mb-0">Invalid Username or Password</div>
                    </div>
                </div>
            </div>
        </section>
    </c:if>

    <section class="bg-transparent pt-3 pb-3">
        <div class="container" style="background-color: rgba(0, 0, 0, 0.7); padding: 30px; border-radius: 15px;">
            <div class="row justify-content-center">
                <div class="col-6">
                    <h2 class="text-center">Login Form</h2>
                    <form method="post" action="/auth/loginSubmit" onsubmit="return validateForm()">

                        <div class="mt-3">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" class="form-control" id="username" name="username">
                        </div>

                        <div class="mt-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password">
                        </div>

                        <button type="submit" class="btn btn-primary mt-4">Login</button>
                    </form>

                    <p class="mt-3 text-center">Don't have an account? <a href="/auth/register">Sign up</a></p>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <jsp:include page="../include/footer.jsp"/>
</body>
