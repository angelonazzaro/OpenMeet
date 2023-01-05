const form = document.querySelector("form");
const errorMsgParagraph = $("#error-msg");
form.addEventListener('submit', function (e) {
    e.preventDefault(); // stop form from submitting

    toggleLoadingAnimation();

    const formData = new FormData(form);
    const profilePic = fileUploader.files.length > 0 ? fileUploader.files[0] : "";
    formData.append("profilePic", profilePic);

    $.ajax({
        url: getBaseURL() + "settings",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false
    }).done((response) => {
        if (response.status === "error") {
            errorMsgParagraph.show().text(response.message);
        } else {
            // redirect to dashboard
            // window.location.reload();
        }
    }).fail(() => {
        errorMsgParagraph.show().text("An error occurred. Please try again later.");
    }).always(() => {
        toggleLoadingAnimation(false);
    });
})