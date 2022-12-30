<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Login - OpenMeet</title>
    <!-- boostrap css cdn -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <!-- jquery cdn -->
    <script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>

    <style>
        #toggle-pwd {
            cursor: pointer;
            background-color: transparent;
        }
    </style>
</head>
<body>

<div class="d-flex flex-column position-relative vh-100 pt5">
    <div class="container h-100">
        <div class="row h-100 align-items-center justify-content-center">
            <div class="col-md-6 mb-5 mb-md-0">
                <div class="card rounded py-md-3 py-lg-4 px-lg-4 px-xl-5">
                    <div class="card-body">
                        <h1 class="py-2 pb-lg-3 mb-3">Sign in to OpenMeet</h1>
                        <form action="#">
                            <div class="row pb-3 mb-4">
                                <div class="input-group input-group-lg">
                                    <span class="input-group-text" id="email-addon"><i class="fa-solid fa-envelope"></i></span>
                                    <input type="email" name="email" id="email" class="form-control" placeholder="Email address" aria-label="Email address" aria-describedby="email-addon"/>
                                </div>
                            </div>
                            <div class="row pb-3 mb-4">
                                <div class="input-group input-group-lg">
                                    <span class="input-group-text" id="password-addon"><i class="fa-solid fa-lock"></i></span>
                                    <input type="password" name="password" id="password" style="border-right: 0;" class="form-control" placeholder="Password" aria-label="Password" aria-describedby="password-addon"/>
                                    <span class="input-group-text" id="toggle-pwd"><i class="fa-solid fa-eye"></i></span>
                                </div>
                            </div>
                            <div class="form-check pb-3 mb-4">
                                <input type="checkbox" name="keep-me-signed-in" id="keep-me-signed-in-checkbox" class="form-check-input">
                                <label for="keep-me-signed-in-checkbox" class="form-check-label">Keep in me signed in</label>
                            </div>
                            <button type="submit" class="btn btn-lg btn-primary w-100 mb-4">Sign in</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- bootstrap scripts cdns -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
<!-- fontawesome kit v6 -->
<script src="https://kit.fontawesome.com/bedafbbe2b.js" crossorigin="anonymous"></script>
<!-- custom js -->
<script>
    const passwordInputElement = $("input[type='password']");
    const eyeIcon = $("#toggle-pwd > i");
    let togglePWD = false;

    $("#toggle-pwd").on('click', function () {
        togglePWD = !togglePWD;

        let iconClass = "fa-eye-slash", inputType = "text";

        if (!togglePWD) {
            iconClass = "fa-eye";
            inputType = "password";
        }

        passwordInputElement.attr('type', inputType);
        /**
         * eyeIcon.attr("class") returns the element's class attribute value. Each class is separated
         * by a white space.
         * eyeIcon.attr("class").split(" ") returns an array of strings, the class we need is the second string
         * which can be fa-eye or fa-eye-slash.
         * We toggle between fa-eye or fa-eye-slash to display if the password is visible or not
         */
        eyeIcon.removeClass(eyeIcon.attr("class").split(" ")[1]).addClass(iconClass);
    });
</script>
</body>
</html>