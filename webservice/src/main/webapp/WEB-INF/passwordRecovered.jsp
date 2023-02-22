<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<%= request.getContextPath() %>/assets/imgs/icons/logo.png">
    <title>Your password has been reset</title>

</head>
<body>
<style>
    div {
        width: 75%;

        position: absolute;
        top: 35%;
        left: 50%;
        transform: translate(-50%, -35%);

        text-align: center;
    }
</style>
<div>
    <h1>Your password has been reset</h1>
    <p>You will be receiving an email with your new password soon.</p>
</div>
</body>
</html>