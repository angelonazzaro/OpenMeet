<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="d-flex flex-column position-relative vh-100 pt5">
    <div class="container h-100">
        <div class="row h-100 align-items-center justify-content-center">
            <div class="col-md-6 mb-5 mb-md-0">
                <div class="card rounded py-md-3 py-lg-4 px-lg-4 px-xl-5">
                    <div class="card-body">
                        <h1 class="py-2 pb-lg-3 mb-3">Sign in to OpenMeet</h1>
                        <form class="needs-validation" novalidate>
                            <div class="row pb-3 mb-4">
                                <div class="input-group input-group-md has-validation">
                                    <span class="input-group-text" id="email-addon"><i class="fa-solid fa-envelope"></i></span>
                                    <input type="email" name="email" id="email" required class="form-control" placeholder="Email address" aria-label="Email address" aria-describedby="email-addon"/>
                                    <div class="valid-feedback"></div>
                                    <div class="invalid-feedback">
                                        Please provide a valid email address.
                                    </div>
                                </div>
                            </div>
                            <div class="row pb-3 mb-4">
                                <div class="input-group input-group-md has-validation">
                                    <span class="input-group-text" id="password-addon"><i class="fa-solid fa-lock"></i></span>
                                    <input type="password" name="password" id="password" required style="border-right: 0;" class="form-control" placeholder="Password" aria-label="Password" aria-describedby="password-addon"/>
                                    <span class="input-group-text" id="toggle-pwd"><i class="fa-solid fa-eye"></i></span>
                                    <div class="valid-feedback"></div>
                                    <div class="invalid-feedback">
                                        Please provide a password.
                                    </div>
                                </div>
                            </div>
                            <div class="form-check pb-3 mb-4">
                                <input type="checkbox" name="keep-me-signed-in" id="keep-me-signed-in-checkbox" class="form-check-input">
                                <label for="keep-me-signed-in-checkbox" class="form-check-label">Keep in me signed in</label>
                            </div>
                            <p id="error-msg" class="text-danger" style="display: none"></p>
                            <button type="submit" class="btn btn-lg btn-primary w-100 mb-4">Sign in</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
