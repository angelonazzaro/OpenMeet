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
const errorMsgParagraph = $("#error-msg");
const submitBtn = $("button[type='submit']");
const submitBtnSpan = $("button[type='submit'] > span");
const submitBtnSpinner = $("button[type='submit'] .spinner-border");
$("form").on('submit', function (e) {
    e.preventDefault(); // stop form from submitting
    // disable submit button so the user can't submit the form multiple times while the request is being processed
    submitBtn.attr("disabled", true);
    // start spinner animation 
    submitBtnSpan.hide();
    submitBtnSpinner.show();

    $.ajax({
        url: getBaseURL() + "login",
        type: "POST",
        data: $(this).serialize()
    }).done((response) => {
        if (response.status === "error") {
            errorMsgParagraph.show().text(response.message);
        } else {
            // redirect to dashboard
            window.location.assign(response.redirectTo);
        }
    }).fail(() => {
        errorMsgParagraph.show().text("An error occurred. Please try again later.");
    }).always(() => {
        // end spinner animation
        submitBtnSpinner.hide();
        submitBtnSpan.show();
        // restore submit button
        submitBtn.attr("disabled", false);
    });
})