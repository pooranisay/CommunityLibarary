<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-light2 pt-3 pb-2">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">User Registration</h1>
            </div>
        </div>
    </div>
</section>

<body style="background-image: url('../pub/image/library.jpg'); background-size: cover; background-position: center; background-repeat: no-repeat; margin: 0; padding: 0;color: #3498db;">
<section class="pt-3 pb-1">
    <div class="container" style="background-color: rgba(0, 0, 0, 0.7); padding: 30px; border-radius: 15px;">
        <div class="row justify-content-center">
            <div class="col-6">
                <!-- the action attribute on the form tag is the URL that the form will submit to when then user clicks the submit button -->
                <form method="get" action="/auth/registerSubmit" onsubmit="return validateForm()">

                    <div class="mt-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="text" class="form-control" id="email" name="email" aria-describedby="emailHelp" value="${form.email}">
                    </div>
                    <c:if test="${errors.hasFieldErrors('email')}">
                        <div style="color:red">
                            <c:forEach items="${errors.getFieldErrors('email')}" var="error">
                                ${error.defaultMessage}<br>
                            </c:forEach>
                        </div>
                    </c:if>


                    <div class="mt-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="text" class="form-control" id="password" name="password" value="${form.password}">
                    </div>
                    <c:if test="${errors.hasFieldErrors('password')}">
                        <div style="color:red">
                            <c:forEach items="${errors.getFieldErrors('password')}" var="error">
                                ${error.defaultMessage}<br>
                            </c:forEach>
                        </div>
                    </c:if>


                    <div class="mt-3">
                        <label for="confirmPassword" class="form-label">Confirm Password</label>
                        <input type="text" class="form-control" id="confirmPassword" name="confirmPassword" value="${form.confirmPassword}">
                    </div>
                    <c:if test="${errors.hasFieldErrors('confirmPassword')}">
                        <div style="color:red">
                            <c:forEach items="${errors.getFieldErrors('confirmPassword')}" var="error">
                                ${error.defaultMessage}<br>
                            </c:forEach>
                        </div>
                    </c:if>
                     <div class="mt-3">
                                            <label for="resident">Are you a Creekslanding resident, Monroe?</label>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="resident" id="residentYes" value="yes" checked>
                                                <label class="form-check-label" for="residentYes">
                                                    Yes
                                                </label>
                                            </div>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="resident" id="residentNo" value="no">
                                                <label class="form-check-label" for="residentNo">
                                                    No
                                                </label>
                                            </div>
                                        </div>
                    <div class="mt-3">
                        <label>
                          <input type="checkbox" id="agree" name="agree" required>
                          I agree to the terms and conditions
                        </label>
                    </div>

                    <button type="submit" class="btn btn-primary mt-4">Submit</button>
                </form>
            </div>
        </div>
    </div>
</section>
<script>
       function validateForm() {
           // Check if the checkbox is checked
           var agreeCheckbox = document.getElementById("agree");

           if (!agreeCheckbox.checked) {
               // The checkbox is not checked, show an alert and prevent form submission
               alert("Please check the 'I agree to the terms and conditions' box");
               return false;
           }

           // Check if the user has selected a resident option
           var residentYes = document.querySelector('input[name="resident"][value="yes"]');
           var residentNo = document.querySelector('input[name="resident"][value="no"]');

           if (!residentYes.checked && !residentNo.checked) {
               // No resident option selected, show an alert and prevent form submission
               alert("Please select whether you are a Creekslanding resident.");
               return false;
           }

           if (residentNo.checked) {
               // User is not a Creekslanding resident, show an alert and prevent form submission
               alert("Only Creekslanding residents are eligible for registration.");
               return false;
           }

           // All validations passed, proceed with the form submission
           return true;
       }

    </script>
<jsp:include page="../include/footer.jsp"/>