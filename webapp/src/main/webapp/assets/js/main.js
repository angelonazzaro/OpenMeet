(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    const forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})()

/**
 * Get webapp base url which is the same as the one return by request.getContextPath()
 *
 * @return the base url
 * */
function getBaseURL() {
    /**
     * Gets the origin url.
     * Example: http://localhost:8080/Gradle___com_openmeet___openmeet_webapp_1_0_SNAPSHOT_war/ will return http://localhost:8080
     */
    const originURL = window.location.origin;
    // Gets the remaining url string and takes out the "/Gradle___com_openmeet___openmeet_webapp_1_0_SNAPSHOT_war/"
    const explodedURL = window.location.pathname.split("/")[1];

    return originURL + "/" + explodedURL + "/";
}


// Toggles the Loading Animation on forms
function toggleLoadingAnimation(start = true) {
    const submitBtn = $("button[type='submit']");
    const submitBtnSpan = $("button[type='submit'] > span");
    const submitBtnSpinner = $("button[type='submit'] .spinner-border");

    if (start) {
        submitBtn.attr('disabled', true);
        submitBtnSpan.hide();
        submitBtnSpinner.show();
    } else {
        submitBtn.attr('disabled', false);
        submitBtnSpinner.hide();
        submitBtnSpan.show();
    }
}

// Makes the password visible or not visible when clicking on the eye icon
function togglePasswordIcon(toggleElement, icon, passwordElement) {
    let togglePWD = false;

    toggleElement.on('click', function () {
        togglePWD = !togglePWD;
        let iconClass = "fa-eye-slash", inputType = "text";
        if (!togglePWD) {
            iconClass = "fa-eye";
            inputType = "password";
        }
        passwordElement.attr('type', inputType);
        /**
         * eyeIcon.attr("class") returns the element's class attribute value. Each class is separated
         * by a white space.
         * eyeIcon.attr("class").split(" ") returns an array of strings, the class we need is the second string
         * which can be fa-eye or fa-eye-slash.
         * We toggle between fa-eye or fa-eye-slash to display if the password is visible or not
         */
        icon.removeClass(icon.attr("class").split(" ")[1]).addClass(iconClass);
    });
}