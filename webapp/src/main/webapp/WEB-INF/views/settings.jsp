<%@ page import="com.openmeet.webapp.dataLayer.moderator.Moderator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% Moderator user = (Moderator) request.getSession().getAttribute("user"); %>
<% String profilePic = user.getProfilePic(); %>

<% if (profilePic == null || profilePic.length() > 0) { %>
    <% profilePic = request.getContextPath() + "/assets/imgs/special/userplaceholder.png"; %>
<% } %>

<style>
    #preview {
        display: block;

        width: 180px;
        height: 180px;

        object-fit: cover;
    }
</style>

<div class="container h-100">
    <div class="h-100 row align-items-center justify-content-center">
        <div class="col-12 mb-5 mb-md-0 row">
            <h1 class="py-2 pb-lg-3 mb-3">Settings</h1>
            <form class="needs-validation col-12 row flex-column align-items-center" no-validate>
                <div class="col-md-6 col-sm-12 mb-4">
                    <div class="form-group d-flex flex-column align-items-center">
                        <img src="<%= profilePic %>" alt="profile pic preview" class="mb-1" id="preview">
                        <div style="flex-basis: 100%; width: 100%">
                            <label for="profile-pic" class="form-label">Profile Picture</label>
                            <input type="file" name="profilePic" id="profile-pic" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-sm-12 mb-4">
                    <h4 class="pb-2">Account Info</h4>
                    <div class="row pb-3 mb-4">
                        <div class="input-group input-group-md has-validation col">
                            <span class="input-group-text" id="name-addon"><i class="fa-solid fa-user-astronaut"></i></span>
                            <input type="text" name="name" id="name" value="<%= user.getName() %>" required class="form-control" placeholder="Name" aria-label="Name" aria-describedby="name-addon" maxlength="35" required/>
                            <div class="valid-feedback"></div>
                            <div class="invalid-feedback">
                                Your name cannot exceed 35 characters.
                            </div>
                        </div>
                        <div class="input-group input-group-md has-validation col">
                            <span class="input-group-text" id="surname-addon"><i class="fa-solid fa-user-astronaut"></i></span>
                            <input type="text" name="surname" id="surname" value="<%= user.getSurname() %>" required class="form-control" placeholder="Surname" aria-label="Surname" aria-describedby="name-addon" maxlength="35" required/>
                            <div class="valid-feedback"></div>
                            <div class="invalid-feedback">
                                Your surname cannot exceed 35 characters.
                            </div>
                        </div>
                    </div>
                    <div class="row pb-3 mb-4">
                        <div class="input-group input-group-md has-validation col">
                            <span class="input-group-text" id="email-addon"><i class="fa-solid fa-envelope"></i></span>
                            <input type="text" id="email" required class="form-control" value="<%= user.getEmail() %>" placeholder="Email address" aria-label="Email address" aria-describedby="email-addon" disabled/>
                        </div>
                    </div>
                    <div class="row pb-3 mb-4">
                        <div class="input-group input-group-md has-validation col">
                            <span class="input-group-text" id="lock-addon"><i class="fa-solid fa-lock"></i></span>
                            <input type="text" name="password" id="password" class="form-control" placeholder="Password" aria-label="Password" aria-describedby="passwordHelpBlock" />
                            <span class="input-group-text toggle-pwd-icon" id="password-toggle"><i class="fa-solid fa-eye"></i></span>
                            <div id="passwordHelpBlock" class="form-text">
                                Your password must be 8-16 characters long, contain letters and numbers, special characters and must not contain spaces or emoji.
                            </div>
                            <div class="valid-feedback"></div>
                            <div class="invalid-feedback">
                                The password does not meet the specified criterias.
                            </div>
                        </div>
                    </div>
                </div>
                <p id="error-msg" class="text-danger" style="display: none"></p>
                <button type="submit" class="text-center btn btn-lg btn-primary w-100 mb-4">
                    <span>Sign in</span>
                    <div class="spinner-border text-light" style="display: none" role="status">
                        <span class="visually-hidden">Loading...</span>
                    </div>
                </button>
            </form>
        </div>
    </div>
</div>