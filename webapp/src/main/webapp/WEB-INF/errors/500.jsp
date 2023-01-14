<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>

<%@ include file="../templates/header.jsp" %>

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
        <img src="<%= request.getContextPath() %>/assets/imgs/special/errors/500-icon.png" alt="500 error">
    </div>
    <h1 style="font-size: calc(2.65rem + 1vw) !important">500</h1>
    <h2>Sorry, We need to reschedule our date &#10083;</h2>
</main>

<%@include file="../templates/scripts/bottom_scripts.jsp" %>