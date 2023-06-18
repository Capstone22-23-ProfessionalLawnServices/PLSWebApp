function addAccountClick() {
    window.location = "/account/add";
}

function createAccount() {
    let currentUrl = window.location.href;
    let url = "";
    let username = document.getElementById("username") == null
        ? "":document.getElementById("username").value;
    let password = document.getElementById("password") == null
        ? "":document.getElementById("password").value;

    let workerId = currentUrl.substring(currentUrl.lastIndexOf('/') + 1);
    url = "/worker-account/create-account/" + workerId + "?username=" + username + "&password=" + password;

    fetch(url, {
        method: 'POST'
    })
        .then(response => {
            window.location.href = "/workers";
        })
        .catch(error => {
            alert("There was an issue with the fetch request.")
        });
}