// Get webapp base url which is the same as the one return by request.getContextPath()
function getBaseURL() {
    // Gets the origin url -> ex. https://www.github.com
    const originURL = window.location.origin;
    // Gets the remaining url string and takes out the "/openmeet_war_exploded/"
    const explodedURL = window.location.pathname.split("/")[1];

    return originURL + "/" + explodedURL + "/";
}