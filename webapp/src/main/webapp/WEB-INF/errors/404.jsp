<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>

<%@ include file="../templates/header.jsp"%>

<style>
    div > img {
        max-width: 100%;
        object-fit: cover;
    }

    @media screen and (max-width: 600px) {
        div > img {
            max-width: 75%;
        }
    }
</style>

<main class="h-100 w-100 d-flex flex-column align-items-center justify-content-center" style="background-color: white">
    <div class="text-center" style="width: 300px">
        <img src="<%= request.getContextPath() %>/assets/imgs/special/errors/404-icon.png" alt="404 error">
    </div>
    <h1 style="font-size: calc(2.65rem + 1vw) !important">404</h1>
    <h2>Sorry, Match Not Found!</h2>
    <a href="<%= request.getContextPath() %>/" class="btn btn-danger mt-3">Find Match <i class="fa-solid fa-heart"></i></a>
</main>

<%@include file="../templates/scripts/bottom_scripts.jsp"%>
