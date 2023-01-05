togglePasswordIcon($(".toggle-pwd-icon"), $(".toggle-pwd-icon > i"), $("input[type='password']"));

const errorMsgParagraph = $("#error-msg");
$("form").on('submit', function (e) {
    e.preventDefault(); // stop form from submitting

    toggleLoadingAnimation();

    $.ajax({
        url: getBaseURL() + "login",
        type: "POST",
        data: $(this).serialize()
    }).done((response) => {
        if (response.status === "error") {
            errorMsgParagraph.show().text(response.message);
        } else {
            // redirect to dashboard
            window.location.replace(response.redirectTo);
        }
    }).fail(() => {
        errorMsgParagraph.show().text("An error occurred. Please try again later.");
    }).always(() => {
        toggleLoadingAnimation(false);
    });
})