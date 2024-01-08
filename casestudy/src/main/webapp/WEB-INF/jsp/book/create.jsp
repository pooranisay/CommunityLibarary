<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp" />

<section class="bg-light-purple text-dark pt-2 pb-1">
    <div class="container text-center">
        <h1 class="m-0">Add/Update Books</h1>
    </div>
</section>

<section class="pt-4 pb-4 bg-light">
    <div class="container">
        <c:if test="${not empty success}">
            <div class="row justify-content-center">
                <div class="col-6 text-center">
                    <div class="alert alert-success" role="alert">
                        ${success}
                    </div>
                </div>
            </div>
        </c:if>

        <!-- Use POST method for form submissions that modify data -->
        <form method="post" action="/book/createSubmit" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${form.id}">
            <div class="row mt-4">
                <div class="col-md-6">
                    <label for="name" class="form-label">Book Name</label>
                    <input type="text" class="form-control" id="name" name="name" aria-describedby="nameHelp" value="${form.name}">
                    <div id="firstNameHelp" class="form-text">Please enter the book name</div>
                    <c:if test="${errors.hasFieldErrors('name')}">
                        <div style="color:red">
                            <c:forEach items="${errors.getFieldErrors('name')}" var="error">
                                ${error.defaultMessage}<br>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>

                <div class="col-md-6">
                    <label for="author" class="form-label">Author</label>
                    <input type="text" class="form-control" id="author" name="author" value="${form.author}">
                    <c:if test="${errors.hasFieldErrors('author')}">
                        <div style="color:red">
                            <c:forEach items="${errors.getFieldErrors('author')}" var="error">
                                ${error.defaultMessage}<br>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
            </div>

            <div class="row mt-4">
                <div class="col-md-6">
                    <label for="imageFile" class="form-label">Image File</label>
                    <input type="file" class="form-control" id="imageFile" name="file" accept="image/*">
                    <c:if test="${errors.hasFieldErrors('imageUrl')}">
                        <div style="color:red">
                            <c:forEach items="${errors.getFieldErrors('imageUrl')}" var="error">
                                ${error.defaultMessage}<br>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>

                <div class="col-md-6">
                    <label for="categoryId" class="form-label">Category</label>
                    <select class="form-select" id="categoryId" name="categoryId" value="categoryId">
                        <option value="">Select a category</option>
                        <option value="1">Children Book</option>
                        <option value="2">Education Book</option>
                        <option value="3">Fantasy Book</option>
                        <option value="4">Science Fiction Book</option>
                        <option value="5">CookBook</option>
                        <option value="6">Technology/Computers Book</option>
                        <option value="7">Non-Fiction Book</option>
                    </select>
                </div>
            </div>

            <div class="text-center mt-4">
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>

        </form>
    </div>
</section>

<jsp:include page="../include/footer.jsp" />
