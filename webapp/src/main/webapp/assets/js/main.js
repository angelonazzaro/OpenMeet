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

// Get webapp base url which is the same as the one return by request.getContextPath()
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